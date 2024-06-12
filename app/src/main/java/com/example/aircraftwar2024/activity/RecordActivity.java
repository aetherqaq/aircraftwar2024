package com.example.aircraftwar2024.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
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
import android.view.View;
import android.widget.Button;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecordActivity extends AppCompatActivity {
    private int gameType = 1;
    private int score = 0;
    private String name = "test";
    private UserDaoImpl userDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        ActivityManager.getActivityManager().addActivity(this);

        Button btn1 = findViewById(R.id.back_to_main);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityManager.getActivityManager().finishActivity(RecordActivity.this);

                // Return to the previous MainActivity instance if it exists
                //ActivityManager.getActivityManager().finishActivity(MainActivity.class);
                Intent intent = new Intent(RecordActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        if (getIntent() != null) {
            gameType = getIntent().getIntExtra("gameType", 1);
            score = getIntent().getIntExtra("user_score",0);
            name = getIntent().getStringExtra("user_name");
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
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("确定删除第"+arg2+"条数据？")
                        .setTitle("提示");
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        userDao.doDelete(arg2-1);
                        //生成适配器的Item和动态数组对应的元素
                        SimpleAdapter listItemAdapter = null;
                        try {
                            listItemAdapter = new SimpleAdapter(
                                    getActivity(),
                                    getData1(),
                                    R.layout.activity_item,
                                    new String[]{"rank","name","score","time"},
                                    new int[]{R.id.rank,R.id.name,R.id.score,R.id.time});
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                        //添加并且显示
                        list.setAdapter(listItemAdapter);
                    }
                });

                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getActivity(), "取消", Toast.LENGTH_SHORT).show();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
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

        userDao = new UserDaoImpl(this,gameType);
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

    private List<Map<String, Object>> getData1() throws IOException {
        ArrayList<Map<String, Object>> listitem = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();

        map.put("rank", "排名");
        map.put("name", "用户");
        map.put("score", "得分");
        map.put("time", "时间");
        listitem.add(map);

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

    private Context getActivity(){
        return this;
    }
}