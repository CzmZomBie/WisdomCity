package com.zhny.gr.wisdomcity;

import android.app.Application;
import android.content.Context;

import com.tencent.bugly.crashreport.CrashReport;

import org.xutils.x;

/**
 * Created by czm on 2017/4/28.
 */

public class MyApplication extends Application {

    public static Context getContext() {
        return context;
    }

    static Context context;


    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        CrashReport.initCrashReport(getApplicationContext(), "bd6bb66563", false);
        context = this;
    }
}
