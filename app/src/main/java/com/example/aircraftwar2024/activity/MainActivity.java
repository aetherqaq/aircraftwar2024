package com.example.aircraftwar2024.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.aircraftwar2024.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static boolean isOnline;
    private boolean musicFlag = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn1 = findViewById(R.id.musicon);
        Button btn2 = findViewById(R.id.musicoff);
        Button btn3 = findViewById(R.id.start);
        Button btn4 = findViewById(R.id.online);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.musicoff) {
            musicFlag = false;
            Toast.makeText(this, "Music is off", Toast.LENGTH_SHORT).show();
        } else if (v.getId() == R.id.musicon) {
            musicFlag = true;
            Toast.makeText(this, "Music is on", Toast.LENGTH_SHORT).show();
        } else if (v.getId() == R.id.start) {
            isOnline = false;
            Intent intent = new Intent(MainActivity.this, OfflineActivity.class);
            intent.putExtra("user", "test");
            intent.putExtra("musicFlag", musicFlag);
            startActivity(intent);
        } else if (v.getId() == R.id.online) {
            isOnline = true;
            Toast.makeText(this, "少女哭泣乐队", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, OnlineActivity.class);
            intent.putExtra("musicFlag", musicFlag);
            startActivity(intent);
        }
    }
}
