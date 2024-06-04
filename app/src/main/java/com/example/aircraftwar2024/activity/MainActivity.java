package com.example.aircraftwar2024.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.aircraftwar2024.R;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn1 = (Button) findViewById(R.id.musicon);
        Button btn2 = (Button) findViewById(R.id.musicoff);
        Button btn3 = (Button) findViewById(R.id.start);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        if (v.getId() == R.id.musicoff){
            Toast.makeText(this, "Music is off", Toast.LENGTH_SHORT).show();
        } else if (v.getId() == R.id.musicon) {
            Toast.makeText(this, "Music is on", Toast.LENGTH_SHORT).show();
        } else if (v.getId() == R.id.start) {
            Intent intent = new Intent(MainActivity.this, RecordActivity.class);
            intent.putExtra("user","test");
            startActivity(intent);
        }
    }




}