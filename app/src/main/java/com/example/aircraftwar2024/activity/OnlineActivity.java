package com.example.aircraftwar2024.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import com.example.aircraftwar2024.R;
import com.example.aircraftwar2024.game.BaseGame;
import com.example.aircraftwar2024.game.MediumGame;

public class OnlineActivity extends AppCompatActivity {

    private static final String TAG = "OnlineActivity";

    private Socket socket;
    private PrintWriter writer;
    private BufferedReader reader;
    private Handler handler;
    private BaseGame game;
    private int opponentScore = 0;
    private boolean gameOverFlag = false;
    private int selfScore = 0;
    private AlertDialog dialog = null;
    private boolean musicFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online);

        ActivityManager.getActivityManager().addActivity(this);
        musicFlag = getIntent().getBooleanExtra("musicFlag",false);
        game = null;
        gameOverFlag = false;
        // 用于发送接收到的服务器端的消息，显示在界面上
        handler = new Handler(getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Log.d(TAG, "handleMessage");
                if (msg.what == 2) {
                    setGameOverFlag(true);
                }
            }
        };

        getScreenHW();
        // 直接进行连接操作

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("匹配中，请等待……");
        dialog = builder.create();
        dialog.show();
        new Thread(new ClientThread(handler)).start();
    }

    private Context getActivity(){
        return this;
    }

    class ClientThread implements Runnable {
        private Handler handler;

        public ClientThread(Handler handler) {
            this.handler = handler;
        }

        @Override
        public void run() {
            try {
                // 连接到服务器
                socket = new Socket();
                socket.connect(new InetSocketAddress("10.0.2.2", 9999), 5000);
                writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
                        socket.getOutputStream(), StandardCharsets.UTF_8)), true);
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));

                // 创建子线程接收服务端信息
                new Thread(() -> {
                    String msg;
                    try {
                        while ((msg = reader.readLine()) != null) {
                            Log.e(TAG, "Received from server: " + msg);

                            if ("start".equals(msg)) {
                                if(dialog!=null) dialog.dismiss();
                                runOnUiThread(() -> {
                                    game = new MediumGame(getActivity(), handler, musicFlag);
                                    setContentView(game);
                                });
                                writer.println(0);
                            } else if ("close".equals(msg)) {
                                Log.i(TAG, "Game Over");

                                Intent intent = new Intent(OnlineActivity.this, OnlineResultActivity.class);
                                intent.putExtra("selfScore", selfScore);
                                intent.putExtra("opponentScore", opponentScore);
                                startActivity(intent);
                                Log.i(TAG, "Navigating to result activity");
                            } else{
                                opponentScore = Integer.parseInt(msg);
                                runOnUiThread(() -> game.updateOpponentScore(opponentScore));
                                if (!gameOverFlag) {
                                    selfScore = game.getScore();
                                    writer.println(selfScore);
                                } else {
                                    writer.println("dead");
                                }
                            }

                            Thread.sleep(1000); // Send data every second
                        }
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }).start();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public int getOpponentScore() {
        return opponentScore;
    }



    private void setGameOverFlag(boolean gameOverFlag) {
        this.gameOverFlag = gameOverFlag;
    }

    public boolean isGameOverFlag() {
        return gameOverFlag;
    }

    public void getScreenHW() {
        //定义DisplayMetrics 对象
        DisplayMetrics dm = new DisplayMetrics();
        //取得窗口属性
        getDisplay().getRealMetrics(dm);

        GameActivity.setScreenHeight(dm.heightPixels);
        GameActivity.setScreenWidth(dm.widthPixels);

    }
}
