package com.zhny.gr.wisdomcity.activity.monitor;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;


import com.zhny.gr.wisdomcity.R;
import com.zhny.gr.wisdomcity.widget.MyVideoViewLayout;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;


/**
 * Created by tong on 2017/5/8.
 */
@ContentView(R.layout.ac_landscape)
public class LandScapeActivity extends Activity {

    @ViewInject(R.id.myvideoView_layout)
    MyVideoViewLayout viewLayout;

    private int type;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);

        String url = getIntent().getStringExtra("url");
        type = getIntent().getIntExtra("type", -1);
        if (url != null) {
            Log.i("MyTAG", url);
            if (type == MonitorActivity.TYPE_RECORD) {
            }

            viewLayout.start(url);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewLayout.stop();
    }
}
