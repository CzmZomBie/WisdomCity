package com.zhny.gr.wisdomcity.widget;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.VideoView;

import com.zhny.gr.wisdomcity.R;


/**
 * Created by tong on 2017/5/9.
 */

public class MyVideoViewLayout extends LinearLayout {

    private VideoView videoView;
    private ProgressBar progressBar;
    private boolean isPrepared = false;


    final Handler handler = new Handler();
    private int old_duration = 0;
    Runnable runnable = new Runnable() {
        public void run() {
            if (videoView == null)
                return;
            if (!videoView.isPlaying() || !isPrepared) {
                return;
            }
            int duration = videoView.getCurrentPosition();
            if (old_duration == duration && videoView.isPlaying()) {
                progressBar.setVisibility(View.VISIBLE);
            } else {
                progressBar.setVisibility(View.GONE);
            }
            old_duration = duration;

            handler.postDelayed(runnable, 1000);
        }
    };

    public MyVideoViewLayout(Context context) {
        this(context, null);
    }

    public MyVideoViewLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyVideoViewLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        View view = LayoutInflater.from(context).inflate(R.layout.item_myvideoview, this);

        initViews(view);

        initLIstener();

    }

    private void initLIstener() {
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                isPrepared = true;
                handler.postDelayed(runnable, 0);
            }
        });
    }

    public void start(String url) {
        videoView.setVideoURI(Uri.parse(url));
        videoView.requestFocus();
        videoView.start();
    }

    public void stop() {
        videoView.pause();
        handler.removeCallbacks(runnable);
    }


    private void initViews(View view) {
        videoView = (VideoView) view.findViewById(R.id.videoView);
        progressBar = (ProgressBar) view.findViewById(R.id.ps);
    }


}
