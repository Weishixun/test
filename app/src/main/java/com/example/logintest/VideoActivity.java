package com.example.logintest;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;

public class VideoActivity extends AppCompatActivity {

    private SurfaceView surfaceView;
    private MediaPlayer mediaPlayer;
    private String URL ;
    private int position;//记录位置

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        //初始化
        mediaPlayer = new MediaPlayer();
        surfaceView = findViewById(R.id.surface_view);
        //设置屏幕常亮
        surfaceView.getHolder().setKeepScreenOn(true);
        //添加回调接口
        surfaceView.getHolder().addCallback(callback);
    }


    private SurfaceHolder.Callback callback = new SurfaceHolder.Callback() {
        @Override
        public void surfaceCreated(SurfaceHolder holder) {

        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                position = mediaPlayer.getCurrentPosition();
                mediaPlayer.stop();
            }

        }
    };

    public void play() {
        try {
            mediaPlayer.reset();//重置
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
          Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.test);
          mediaPlayer.setDataSource(this, uri);
          mediaPlayer.setDisplay(surfaceView.getHolder());
            //视频输出到SurfaceView上
            mediaPlayer.prepare();//使用同步方式
            mediaPlayer.start();//开始播放

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(VideoActivity.this, "路径错误", Toast.LENGTH_SHORT).show();
        }
    }

    public void start(View view) {
        play();
    }

    public void pause(View view) {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            position = mediaPlayer.getCurrentPosition();
        }
    }

    public void goOn(View view) {
        mediaPlayer.seekTo(position);
        mediaPlayer.start();
    }

    public void stop(View view) {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            position = mediaPlayer.getCurrentPosition();

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //释放资源
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }
}




