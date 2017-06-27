package com.zhny.gr.wisdomcity.activity.activityhome;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;


import com.zhny.gr.wisdomcity.R;
import com.zhny.gr.wisdomcity.activity.WebActivity;
import com.zhny.gr.wisdomcity.activity.administration.GovenrnmentActivity;
import com.zhny.gr.wisdomcity.activity.monitor.MonitorMainActivity;
import com.zhny.gr.wisdomcity.activity.trip.Tourism;
import com.zhny.gr.wisdomcity.dialog.Exit_dialog;
import com.zhny.gr.wisdomcity.dialog.HttpUrl_dialog;
import com.zhny.gr.wisdomcity.util.MyTimeUtils;
import com.zhny.gr.wisdomcity.util.SPData;
import com.zhny.gr.wisdomcity.view.EffectNoDrawBridge;
import com.zhny.gr.wisdomcity.view.MainUpView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by czm on 2017/5/27 0027.
 */
@ContentView(R.layout.ac_nanhuavillage)
public class NanhuaHomeActivity extends BaseActivity {

    @ViewInject(R.id.mainUpView1)
    private MainUpView mainUpView1;

    HttpUrl_dialog httpUrl_dialog;
    Exit_dialog exit = null;

    @ViewInject(R.id.time1)
    TextView time1;
    @ViewInject(R.id.time2)
    TextView time2;
    @ViewInject(R.id.time3)
    TextView time3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainUpView1 = (MainUpView) findViewById(R.id.mainUpView1);
        mainUpView1.setEffectBridge(new EffectNoDrawBridge());
        EffectNoDrawBridge bridget = (EffectNoDrawBridge) mainUpView1.getEffectBridge();
        bridget.setVisibleWidget(false);
        time1.setText(MyTimeUtils.getCurrentTime("yyyy:MM:dd") + MyTimeUtils.getWeek());
        time2.setText("农历" + MyTimeUtils.getLunarMonth() + MyTimeUtils.getLunarDay());
        new Thread(time).start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        handlerTimes.post(handlerTime);
    }

    @Override
    protected void onStop() {
        super.onStop();
        handlerTimes.removeCallbacks(handlerTime);
    }

    Thread time = new Thread(new Runnable() {
        @Override
        public void run() {
            time3.setText(MyTimeUtils.getCurrentTime("HH:mm"));
        }
    });

    private Handler handlerTimes = new Handler();
    Runnable handlerTime = new Runnable() {
        public void run() {
            time3.setText(MyTimeUtils.getCurrentTime("HH:mm"));
            handlerTimes.postDelayed(handlerTime, 30000);//1000表示更新的频率 单位毫秒
        }
    };


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == 7) {
            switch (focusView.getId()) {
                case R.id.nongmao:
                    httpUrl_dialog = new HttpUrl_dialog(this, R.string.nongmao);
                    break;
                case R.id.jiuye:
                    httpUrl_dialog = new HttpUrl_dialog(this, R.string.jiuye);
                    break;
                case R.id.sannong:
                    httpUrl_dialog = new HttpUrl_dialog(this, R.string.sanon);
                    break;
            }
            if (focusView.getId() == R.id.nongmao || focusView.getId() == R.id.jiuye || focusView.getId() == R.id.sannong) {
                httpUrl_dialog.show();
            }
        }
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                if (exit == null) {
                    exit = new Exit_dialog(this);
                }
                exit.show();
                break;
        }
        return super.onKeyDown(keyCode, event);
    }


    View focusView, mOldView;

    @Event(value = {R.id.travel, R.id.dangjian, R.id.xueliang, R.id.nongmao,
            R.id.jiuye, R.id.sannong, R.id.zhengwu, R.id.banshi},
            type = View.OnFocusChangeListener.class)
    private void OnFocus(View view, Boolean hasFocus) {
        if (view == null) {
            return;
        }
        if (hasFocus) {
            focusView = view;
            // 移动方框缩小的距离. L  T  R  B
            mainUpView1.setFocusView(view, mOldView, 1.15f);

            view.bringToFront();
            mOldView = view;
        }
    }

    @Event(value = {R.id.travel, R.id.dangjian, R.id.xueliang, R.id.nongmao,
            R.id.jiuye, R.id.sannong, R.id.zhengwu, R.id.banshi},
            type = View.OnClickListener.class)
    private void onClick(View view) {
        if (!isConn(this)) {
            toastLongShow("请检查网络连接后使用");
            return;
        }
        switch (view.getId()) {
            case R.id.travel:
                startActivity(Tourism.class);
                return;
            case R.id.dangjian:
                toastLongShow("党建云");
                return;
            case R.id.xueliang:
                startActivity(MonitorMainActivity.class);
                return;
            case R.id.nongmao:
                WebActivity.startActivity(this, SPData.getInstance(this).ReadSp(getString(R.string.nongmao), "http"));
                break;
            case R.id.jiuye:
                WebActivity.startActivity(this, SPData.getInstance(this).ReadSp(getString(R.string.jiuye), "http"));
                break;
            case R.id.sannong:
                WebActivity.startActivity(this, SPData.getInstance(this).ReadSp(getString(R.string.sanon), "http"));
                break;
            case R.id.zhengwu:
                startActivity(GovenrnmentActivity.class);
                return;
            case R.id.banshi:
//                startActivity(WorkHelpHomeActivity.class);
                break;
        }
    }

    public static boolean isConn(Context context) {
        boolean bisConnFlag = false;
        ConnectivityManager conManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo network = conManager.getActiveNetworkInfo();
        if (network != null) {
            bisConnFlag = conManager.getActiveNetworkInfo().isAvailable();
        }
        return bisConnFlag;
    }


}
