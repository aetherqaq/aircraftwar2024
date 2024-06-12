package com.example.aircraftwar2024.music;

import android.annotation.TargetApi;
import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

import com.example.aircraftwar2024.R;

import java.util.HashMap;

public class MySoundPool {
    SoundPool mysp;
    HashMap<Integer, Integer> soundPoolMap;
    private boolean isMuted = false;

    public MySoundPool(Context context){
        createSoundPool();

        soundPoolMap = new HashMap<>();
        soundPoolMap.put(1, mysp.load(context, R.raw.bomb_explosion, 1));
        soundPoolMap.put(2, mysp.load(context, R.raw.bullet_hit, 1));
        soundPoolMap.put(3, mysp.load(context, R.raw.game_over, 1));
        soundPoolMap.put(4, mysp.load(context, R.raw.get_supply, 1));
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void createSoundPool() {
        if (mysp == null) {
            // Android 5.0 及 之后版本
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                AudioAttributes audioAttributes = new AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .build();
                mysp = new SoundPool.Builder()
                        .setMaxStreams(10)
                        .setAudioAttributes(audioAttributes)
                        .build();
            } else { // Android 5.0 以前版本
                mysp = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
            }
        }
    }

    public void playBoom(){
        playSound(1);
    }

    public void playBulletHit(){
        playSound(2);
    }

    public void playGameOver(){
        playSound(3);
    }

    public void playGetSupply(){
        playSound(4);
    }

    private void playSound(int soundID) {
        float volume = isMuted ? 0 : 1;
        mysp.play(soundPoolMap.get(soundID), volume, volume, 0, 0, 1.0f);
    }

    public void setMute(boolean mute) {
        isMuted = mute;
    }
}
