package com.example.aircraftwar2024.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.aircraftwar2024.R;

public class OnlineResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_result);

        ActivityManager.getActivityManager().addActivity(this);
        int selfScore = getIntent().getIntExtra("selfScore",0);
        int opponentScore = getIntent().getIntExtra("opponentScore",0);

        TextView selfScoreText = (TextView) findViewById(R.id.selfText);
        TextView opponentScoreText = (TextView) findViewById(R.id.opponentText);
        selfScoreText.setText("你的分数："+selfScore);
        opponentScoreText.setText("对手分数："+opponentScore);

        Button btn1 = findViewById(R.id.back_to_main);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityManager.getActivityManager().finishActivity(OnlineResultActivity.this);
                Intent intent = new Intent(OnlineResultActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }


}