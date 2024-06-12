package com.example.aircraftwar2024.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.aircraftwar2024.DAO.User;
import com.example.aircraftwar2024.R;
import com.example.aircraftwar2024.game.BaseGame;
import com.example.aircraftwar2024.game.EasyGame;
import com.example.aircraftwar2024.game.HardGame;
import com.example.aircraftwar2024.game.MediumGame;
import com.example.aircraftwar2024.activity.ActivityManager;


public class GameActivity extends AppCompatActivity {
    private static final String TAG = "GameActivity";
    public static final int MSG_SHOW_RANKING = 1;

    private boolean musicFlag;
    private String userName;
    private int gameType = 1;
    public static int screenWidth, screenHeight;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityManager.getActivityManager().addActivity(this);

        Handler handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Log.d(TAG,"handleMessage");
                if (msg.what == 1) {
                        User user = (User)msg.obj;
                        //Toast.makeText(GameActivity.this,"GameOver",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(GameActivity.this,RecordActivity.class);
                        intent.putExtra("user_name", user.getUserName());
                        intent.putExtra("user_score", user.getScore());
                        intent.putExtra("gameType",gameType);
                        //intent.putExtra("user_time", user.getTime());


                        startActivity(intent);

                }
            }
        };


        getScreenHW();
        userName = getIntent().getStringExtra("user");
        musicFlag = getIntent().getBooleanExtra("musicFlag",true);
        if (getIntent() != null) {
            gameType = getIntent().getIntExtra("gameType", 1);
        }

        BaseGame baseGameView = null;
        if (gameType == 1) {
            baseGameView = new EasyGame(this,handler,musicFlag);
        } else if (gameType == 2) {
            baseGameView = new MediumGame(this,handler,musicFlag);
        } else if (gameType == 3) {
            baseGameView = new HardGame(this,handler,musicFlag);
        }

        setContentView(baseGameView);

    }

    private void loadRankingLayout() {
        setContentView(R.layout.activity_record);
    }

    public void getScreenHW() {
        //定义DisplayMetrics 对象
        DisplayMetrics dm = new DisplayMetrics();
        //取得窗口属性
        getDisplay().getRealMetrics(dm);

        //窗口的宽度
        screenWidth = dm.widthPixels;
        //窗口高度
        screenHeight = dm.heightPixels;

        Log.i(TAG, "screenWidth : " + screenWidth + " screenHeight : " + screenHeight);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


}