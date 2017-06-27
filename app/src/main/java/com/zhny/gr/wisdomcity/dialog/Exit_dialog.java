package com.zhny.gr.wisdomcity.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.zhny.gr.wisdomcity.R;
import com.zhny.gr.wisdomcity.util.CollectActivity;


/**
 * Created by czm on 2017/5/2.
 */

public class Exit_dialog extends Dialog implements View.OnClickListener {

    private Context context;
    private RelativeLayout btnEnter, btnCancle;

    public Exit_dialog(Context context) {
        super(context);
        setContentView(R.layout.dialog_exit);
        this.context = context;
        intoView();
    }

    private void intoView() {
        String appName = context.getResources().getString(R.string.app_name);
        setTitle("你确定要退出" + appName + "吗");
        btnEnter = (RelativeLayout) findViewById(R.id.btnEnter);
        btnCancle = (RelativeLayout) findViewById(R.id.btnCancle);
        btnEnter.setOnClickListener(this);
        btnCancle.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnEnter:
                CollectActivity.clearAllActivity();
                break;
        }
        cancel();
    }

}
