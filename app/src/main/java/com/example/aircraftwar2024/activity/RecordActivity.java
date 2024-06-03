package com.example.aircraftwar2024.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.aircraftwar2024.R;
import com.example.aircraftwar2024.game.BaseGame;
import com.example.aircraftwar2024.game.EasyGame;
import com.example.aircraftwar2024.game.HardGame;
import com.example.aircraftwar2024.game.MediumGame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        int gameType = 1;
        if (getIntent() != null) {
            gameType = getIntent().getIntExtra("gameType", 1);
        }

        if (gameType == 1) {
            ;
        } else if (gameType == 2) {

        } else if (gameType == 3) {

        }

        //获得Layout里面的ListView
        ListView list = (ListView) findViewById(R.id.ListView);

        //生成适配器的Item和动态数组对应的元素
        SimpleAdapter listItemAdapter = new SimpleAdapter(
                this,
                getData(),
                R.layout.activity_item,
                new String[]{"rank","name","score","time"},
                new int[]{R.id.rank,R.id.name,R.id.score,R.id.time});

        //添加并且显示
        list.setAdapter(listItemAdapter);

        //添加单击监听
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                Map<String, Object> clkmap = (Map<String, Object>) arg0.getItemAtPosition(arg2);
                String text = "name"+clkmap.get("name").toString();
                Toast.makeText(RecordActivity.this,text,Toast.LENGTH_SHORT).show();
            }
        });
    }

    private List<Map<String, Object>> getData() {
        ArrayList<Map<String, Object>> listitem = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();

        return listitem;
    }
}