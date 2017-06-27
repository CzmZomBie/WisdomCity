package com.zhny.gr.wisdomcity.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.zhny.gr.wisdomcity.R;
import com.zhny.gr.wisdomcity.util.SPData;


/**
 * Created by czm on 2017/5/2.
 */

public class Affairs_dialog extends Dialog implements View.OnClickListener {

    private Context context;
    private EditText url;
    private RelativeLayout btnEnter, btnCancle;

    public Affairs_dialog(Context context) {
        super(context);
        this.context = context;
        setContentView(R.layout.dialog_affairs);
        intoView();
    }

    private void intoView() {
        setTitle("修改URL");
        url = (EditText) findViewById(R.id.url_edit);
        url.setText(SPData.getInstance(context).ReadSp("URL", "http", "https://"));
        btnEnter = (RelativeLayout) findViewById(R.id.btnEnter);
        btnCancle = (RelativeLayout) findViewById(R.id.btnCancle);
        btnEnter.setOnClickListener(this);
        btnCancle.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnEnter:
                SPData.getInstance(context).WriteSp("URL", url.getText().toString(), "http");
                break;
            case R.id.btnCancle:
                break;
        }
        cancel();
    }
}
