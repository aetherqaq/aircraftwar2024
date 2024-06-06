package com.example.aircraftwar2024.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aircraftwar2024.R;
import com.example.aircraftwar2024.game.BaseGame;
import com.example.aircraftwar2024.game.EasyGame;
import com.example.aircraftwar2024.game.HardGame;
import com.example.aircraftwar2024.game.MediumGame;
import com.example.aircraftwar2024.ranking.User;
import com.example.aircraftwar2024.ranking.UserDaoImpl;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecordActivity extends AppCompatActivity {
    private int gameType = 1;
    private int score = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        if (getIntent() != null) {
            gameType = getIntent().getIntExtra("gameType", 1);
            score = getIntent().getIntExtra("score",0);
        }

        TextView textDifficulty = (TextView) findViewById(R.id.textDifficulty);
        if (gameType == 1) {
            textDifficulty.setText("简单模式");
        } else if (gameType == 2) {
            textDifficulty.setText("普通模式");
        } else if (gameType == 3) {
            textDifficulty.setText("困难模式");
        }

        //获得Layout里面的ListView
        ListView list = (ListView) findViewById(R.id.ListView);

        //生成适配器的Item和动态数组对应的元素
        SimpleAdapter listItemAdapter = null;
        try {
            listItemAdapter = new SimpleAdapter(
                    this,
                    getData(),
                    R.layout.activity_item,
                    new String[]{"rank","name","score","time"},
                    new int[]{R.id.rank,R.id.name,R.id.score,R.id.time});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //添加并且显示
        list.setAdapter(listItemAdapter);

        //添加单击监听
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            }
        });

    }

    private List<Map<String, Object>> getData() throws IOException {
        ArrayList<Map<String, Object>> listitem = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();

        map.put("rank", "排名");
        map.put("name", "用户");
        map.put("score", "得分");
        map.put("time", "时间");
        listitem.add(map);

        UserDaoImpl userDao = new UserDaoImpl(this,gameType);
        String name = "test";
        Date date = new Date();
        @SuppressLint("SimpleDateFormat") DateFormat format = new SimpleDateFormat("MM-dd HH:mm");
        String time = format.format(date);
        userDao.doAdd(new User(score,name,time));
        List<User> userList = userDao.getAllUsers();

        for(int i=0;i<userList.size();i++) {
            User user = userList.get(i);
            map = new HashMap<String, Object>();
            map.put("rank", i+1);
            map.put("name", user.getUserName());
            map.put("score", user.getScore());
            map.put("time", user.getTime());
            listitem.add(map);
        }

        return listitem;
    }
}