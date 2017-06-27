package com.zhny.gr.wisdomcity.activity.monitor;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


import com.zhny.gr.wisdomcity.R;
import com.zhny.gr.wisdomcity.activity.activityhome.BaseActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;
import tv.danmaku.ijk.media.widget.media.IjkVideoView;

/**
 * Created by tong on 2017/5/23.
 */
@ContentView(R.layout.ac_ijkview)
public class IJKViewActivity extends BaseActivity {

    @ViewInject(R.id.ijk_video_view)
    IjkVideoView videoView;
    @ViewInject(R.id.ps)
    ProgressBar progressBar;
    @ViewInject(R.id.rl_all)
    RelativeLayout rlAll;
    @ViewInject(R.id.iv_left)
    ImageView ivLeft;
    @ViewInject(R.id.iv_right)
    ImageView ivRight;
    @ViewInject(R.id.iv_middle)
    ImageView ivMiddle;
    @ViewInject(R.id.ll_bofanqi)
    LinearLayout llBofanqi;
    @ViewInject(R.id.tv_total_duration)
    TextView tvTotalDuration;
    @ViewInject(R.id.tv_current_position)
    TextView tvCurrentPosition;
    @ViewInject(R.id.sb)
    SeekBar seekBar;


    private boolean isSeeking = false;
    private int currentDuration;
    private int totalDuration;
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

            currentDuration = videoView.getCurrentPosition() / 1000;
            int hour = currentDuration / 3600;
            int minute = currentDuration % 3600 / 60;
            int second = currentDuration % 60;
            StringBuilder sb = new StringBuilder();
            sb.append(hour >= 10 ? "" + hour : "0" + hour);
            sb.append(":");
            sb.append(minute >= 10 ? "" + minute : "0" + minute);
            sb.append(":");
            sb.append(second >= 10 ? "" + second : "0" + second);
            tvCurrentPosition.setText(sb.toString());
            seekBar.setProgress(currentDuration);
            handler.postDelayed(runnable, 1000);
        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String url = getIntent().getStringExtra("url");
        if (url != null) {
            Log.i("MyTAG", url);
            IjkMediaPlayer.loadLibrariesOnce(null);
            IjkMediaPlayer.native_profileBegin("libijkplayer.so");
            //AndroidMediaController controller = new AndroidMediaController(this, false);
            //videoView.setMediaController(controller);
            videoView.setVideoURI(Uri.parse(url));
            videoView.requestFocus();
        }

        videoView.setOnPreparedListener(new IMediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(IMediaPlayer iMediaPlayer) {
                isPrepared = true;
                handler.postDelayed(runnable, 0);
                videoView.start();
                ivMiddle.setImageResource(R.drawable.ic_zanting);

                totalDuration = videoView.getDuration() / 1000;
                int hour = totalDuration / 3600;
                int minute = totalDuration % 3600 / 60;
                int second = totalDuration % 60;
                StringBuilder sb = new StringBuilder();
                sb.append(hour >= 10 ? "" + hour : "0" + hour);
                sb.append(":");
                sb.append(minute >= 10 ? "" + minute : "0" + minute);
                sb.append(":");
                sb.append(second >= 10 ? "" + second : "0" + second);

                tvTotalDuration.setText(sb.toString());

                seekBar.setMax(totalDuration);
            }
        });


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (isSeeking) {
                    int currentD = progress;
                    int hour = currentD / 3600;
                    int minute = currentD % 3600 / 60;
                    int second = currentD % 60;
                    StringBuilder sb = new StringBuilder();
                    sb.append(hour >= 10 ? "" + hour : "0" + hour);
                    sb.append(":");
                    sb.append(minute >= 10 ? "" + minute : "0" + minute);
                    sb.append(":");
                    sb.append(second >= 10 ? "" + second : "0" + second);
                    tvCurrentPosition.setText(sb.toString());
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                isSeeking = true;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                isSeeking = false;
                videoView.seekTo(seekBar.getProgress() * 1000);
                if (!videoView.isPlaying()) {
                    videoView.start();
                    ivMiddle.setImageResource(R.drawable.ic_zanting);
                }
            }
        });

    }

    @Event(value = {R.id.iv_middle, R.id.iv_left, R.id.iv_right}, type = View.OnFocusChangeListener.class)
    private void onFocus(View v, boolean hasFocus) {
        ivLeft.setImageResource(R.drawable.ic_kuaitui);
        ivRight.setImageResource(R.drawable.ic_kuaijin);
        if (videoView != null) {
            if (videoView.isPlaying()) {
                ivMiddle.setImageResource(R.drawable.ic_zanting);
            } else {
                ivMiddle.setImageResource(R.drawable.ic_bofan);
            }
        }

        switch (v.getId()) {
            case R.id.iv_left:
                ivLeft.setImageResource(R.drawable.ic_kuaitui_true);
                break;
            case R.id.iv_middle:
                if (videoView != null) {
                    if (videoView.isPlaying()) {
                        ivMiddle.setImageResource(R.drawable.ic_zanting_true);
                    } else {
                        ivMiddle.setImageResource(R.drawable.ic_bofan_true);
                    }
                }
                break;
            case R.id.iv_right:
                ivRight.setImageResource(R.drawable.ic_kuaijin_true);
                break;
        }
    }

    @Event(value = {R.id.rl_all, R.id.iv_middle, R.id.iv_left, R.id.iv_right}, type = View.OnClickListener.class)
    private void onClick(View v) {
        if (videoView == null)
            return;
        int total = videoView.getDuration();
        int pro = videoView.getCurrentPosition();
        Log.i("MyTAG", "total： " + total);
        Log.i("MyTAG", "pro： " + pro);

        switch (v.getId()) {
            case R.id.rl_all:
                if (!isPrepared) {
                    llBofanqi.setVisibility(View.GONE);
                    return;
                }
                if (llBofanqi.getVisibility() == View.VISIBLE) {
                    llBofanqi.setVisibility(View.GONE);
                } else {
                    llBofanqi.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.iv_middle:
                if (videoView.isPlaying()) {
                    videoView.pause();
                    ivMiddle.setImageResource(R.drawable.ic_bofan_true);
                } else {
                    videoView.start();
                    ivMiddle.setImageResource(R.drawable.ic_zanting_true);
                }
                break;
            case R.id.iv_left:
                Toast.makeText(this, "iv_left", Toast.LENGTH_SHORT).show();
                if (pro - 10000 > 0) {
                    pro -= 10000;
                } else {
                    pro = 0;
                }
                videoView.seekTo(pro);
                seekBar.setProgress(pro / 1000);
                break;
            case R.id.iv_right:
                Toast.makeText(this, "iv_right", Toast.LENGTH_SHORT).show();
                if (pro + 10000 < total) {
                    pro += 10000;
                } else {
                    pro = total;
                }
                videoView.seekTo(pro);
                seekBar.setProgress(pro / 1000);
                break;
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        videoView.pause();
        handler.removeCallbacks(runnable);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        IjkMediaPlayer.native_profileEnd();
        handler.removeCallbacks(runnable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        videoView.resume();
    }
}
