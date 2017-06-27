package com.zhny.gr.wisdomcity.util;

import android.util.Log;

/**
 * Created by czm on 2017/5/4.
 */

public class LogUtil {

    private static final String TAG = "LogUtil";
    private static final boolean LOGSTEATE=true;

    public static final void d(String TAG,String log){
        if(!LOGSTEATE)return;
        Log.d(TAG, log);
    }

    public static final void e(String TAG,String log){
        if(!LOGSTEATE)return;
        Log.e(TAG, log );
    }
    public static final void i(String TAG,String log){
        if(!LOGSTEATE)return;
        Log.i(TAG, log);
    }
    public static final void w(String TAG,String log){
        if(!LOGSTEATE)return;
        Log.w(TAG, log );
    }

    public static final void m(String TAG,String log){
        if(!LOGSTEATE)return;
        Log.d(TAG, "m() called with: log = [" + log + "]");
    }

    public static final void r(String TAG,String log){
        if(!LOGSTEATE)return;
        Log.d(TAG, "r() returned: " + log);
    }

}
