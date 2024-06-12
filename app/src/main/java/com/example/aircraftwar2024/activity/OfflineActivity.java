package com.example.aircraftwar2024.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.view.View;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.example.aircraftwar2024.R;

public class OfflineActivity extends AppCompatActivity implements View.OnClickListener{
    private boolean musicFlag;
    private String userName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline);
        ActivityManager.getActivityManager().addActivity(this);
        userName = getIntent().getStringExtra("user");
        musicFlag = getIntent().getBooleanExtra("musicFlag",true);
        Button btn1 = (Button) findViewById(R.id.easy_button);
        Button btn2 = (Button) findViewById(R.id.normal_button);
        Button btn3 = (Button) findViewById(R.id.hard_button);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        if (v.getId() == R.id.easy_button){
            Toast.makeText(this, "Easy Mode", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(OfflineActivity.this, GameActivity.class);
            intent.putExtra("gameType",1);
            intent.putExtra("user",userName);
            intent.putExtra("musicFlag",musicFlag);
            startActivity(intent);
        } else if (v.getId() == R.id.normal_button) {
            Toast.makeText(this, "Normal Mode", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(OfflineActivity.this, GameActivity.class);
            intent.putExtra("gameType",2);
            intent.putExtra("user",userName);
            intent.putExtra("musicFlag",musicFlag);
            startActivity(intent);
        } else if (v.getId() == R.id.hard_button) {
            Toast.makeText(this, "Hard Mode", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(OfflineActivity.this, GameActivity.class);
            intent.putExtra("gameType",3);
            intent.putExtra("user",userName);
            intent.putExtra("musicFlag",musicFlag);
            startActivity(intent);
        }
    }

}