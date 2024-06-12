package com.example.aircraftwar2024.ranking;

import android.content.Context;
import android.widget.Toast;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UserDaoImpl implements UserDao{
    private String dir;
    private Context context;
    //模拟数据库数据
    private List<User> users;
    public UserDaoImpl(Context context, int gameType) throws IOException {
        users = new ArrayList<User>();
        this.context = context;
        if(gameType==1){
            dir = "EasyData.txt";
        }
        else if(gameType==2){
            dir = "NormalData.txt";
        }
        else{
            dir = "HardData.txt";
        }

        try{
            FileInputStream fis = context.openFileInput(dir);
            BufferedReader br = new BufferedReader(new InputStreamReader(fis, StandardCharsets.UTF_8));
            String name,time,score;
            User user;
            while((score = br.readLine())!=null){
                name = br.readLine();
                time = br.readLine();
                user = new User(Integer.parseInt(score),name,time);
                users.add(user);
            }
            br.close();
            fis.close();
        }catch (FileNotFoundException e){
        }
    }

    //获取所有用户
    @Override
    public List<User> getAllUsers() {
        return users;
    }

    //新增用户
    @Override
    public void doAdd(User user) {
        users.add(user);
        Collections.sort(users);
        try {
            this.update();
            Toast.makeText(context, "Save successfully", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update() throws IOException {
        OutputStreamWriter osw = null;
        try {
            osw = new OutputStreamWriter(context.openFileOutput(dir, Context.MODE_PRIVATE), StandardCharsets.UTF_8);
            for(User user: users){
                osw.write(Integer.toString(user.getScore()));
                osw.append("\r\n");
                osw.write(user.getUserName());
                osw.append("\r\n");
                osw.write(user.getTime());
                osw.append("\r\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if(osw!=null) osw.close();
        }
    }

    @Override
    public void doDelete(int num){
        users.remove(users.get(num));
        try {
            this.update();
            Toast.makeText(context, "Save successfully", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}