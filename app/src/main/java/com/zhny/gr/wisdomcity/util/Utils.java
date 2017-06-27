package com.zhny.gr.wisdomcity.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;


/**
 * Created by czm on 2017/5/3.
 */

public class Utils {

    private static final String TAG = "Utils";

    private volatile static Utils singleon;

    private Utils() {
    }

    public static Utils getInstance() {
        if (singleon == null) {
            synchronized (Utils.class) {
                if (singleon == null) {
                    singleon = new Utils();
                }
            }
        }
        return singleon;
    }

    public static String getVersionName(Context context) {
        try {

            PackageManager manager = null;
            PackageInfo info = null;
            if (manager == null) {
                manager = context.getPackageManager();
                if (info == null) {
                    info = manager.getPackageInfo(context.getPackageName(), 0);
                }
            }
            return info.versionName;
        } catch (Exception e) {
            e.printStackTrace();
            return "null";
        }
    }


    public int getVersionCode(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 获取SDK版本
     */
    public int getSDKVersion() {
        int version = 0;
        try {
            version = Integer.valueOf(android.os.Build.VERSION.SDK);
        } catch (NumberFormatException e) {
        }
        return version;
    }

}
