package com.zhny.gr.wisdomcity.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.zhny.gr.wisdomcity.R;
import com.zhny.gr.wisdomcity.util.SPData;


/**
 * Created by czm on 2017/5/2.
 */

public class HttpUrl_dialog extends Dialog implements View.OnClickListener {

    private Context context;
    private EditText url;
    private RelativeLayout btnEnter, btnCancle;
    int title;

    public HttpUrl_dialog(Context context, int title) {
        super(context);
        this.context = context;
        this.title = title;
        setContentView(R.layout.dialog_httpurl);
        intoView();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void intoView() {
        setTitle("修改" + context.getString(title) + "URL");
        url = (EditText) findViewById(R.id.url_edit);
        url.setText(SPData.getInstance(context).ReadSp(context.getString(title), "http", "http://"));
        btnEnter = (RelativeLayout) findViewById(R.id.btnEnter);
        btnCancle = (RelativeLayout) findViewById(R.id.btnCancle);
        btnEnter.setOnClickListener(this);
        btnCancle.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnEnter:
                SPData.getInstance(context).WriteSp(context.getString(title), url.getText().toString(), "http");
                break;
            case R.id.btnCancle:
                break;
        }
        cancel();
    }
}
