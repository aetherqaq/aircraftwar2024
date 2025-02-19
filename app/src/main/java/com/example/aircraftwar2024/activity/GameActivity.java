package com.example.aircraftwar2024.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.example.aircraftwar2024.R;
import com.example.aircraftwar2024.game.BaseGame;
import com.example.aircraftwar2024.game.EasyGame;
import com.example.aircraftwar2024.game.HardGame;
import com.example.aircraftwar2024.game.MediumGame;
import com.example.aircraftwar2024.activity.ActivityManager;
import com.example.aircraftwar2024.ranking.User;
import com.example.aircraftwar2024.ranking.UserDaoImpl;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


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
                    Date date = new Date();
                    @SuppressLint("SimpleDateFormat") DateFormat format = new SimpleDateFormat("MM-dd HH:mm");
                    String time = format.format(date);
                    int score = (int)msg.obj;
                    UserDaoImpl.userDao.doAdd(new User(score,userName,time));
                    //Toast.makeText(GameActivity.this,"GameOver",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(GameActivity.this,RecordActivity.class);
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

        try {
            UserDaoImpl.userDao = new UserDaoImpl(this,gameType);
        } catch (IOException e) {
            throw new RuntimeException(e);
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

    public static void setScreenHeight(int screenHeight) {
        GameActivity.screenHeight = screenHeight;
    }

    public static void setScreenWidth(int screenWidth) {
        GameActivity.screenWidth = screenWidth;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


}