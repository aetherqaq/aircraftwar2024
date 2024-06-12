package com.example.aircraftwar2024.music;

import android.content.Context;
import android.media.MediaPlayer;

import com.example.aircraftwar2024.R;

public class MyMediaPlayer {
    private MediaPlayer bgMP;

    public MyMediaPlayer(Context context, int bgm){
        bgMP = MediaPlayer.create(context, bgm);
    }

    public void start() {
        bgMP.start();
        bgMP.setLooping(true);
    }

    public void pause(){
        bgMP.pause();
    }

    public void continuePlay(){
        int position = bgMP.getCurrentPosition();
        bgMP.seekTo(position);
        bgMP.start();
    }

    public void stop(){
        bgMP.stop();
        bgMP.release();
        bgMP = null;
    }
}
