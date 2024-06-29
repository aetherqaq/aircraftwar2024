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
        if(bgMP!=null) {
            bgMP.stop();
            bgMP.release();
        }
        bgMP = null;
    }

    public void setMute(boolean mute){
        if (bgMP != null) {
            if (mute) {
                bgMP.setVolume(0, 0);
            } else {
                bgMP.setVolume(1, 1);
            }
        }
    }

    public boolean isPlaying() {
        return bgMP != null && bgMP.isPlaying();
    }
}
