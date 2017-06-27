package com.zhny.gr.wisdomcity.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.zhny.gr.wisdomcity.MyApplication;

/**
 * Created by czm on 2017/5/2.
 */

public class SPData {

    private volatile static SPData singleon;

    private SPData() {
    }

    private SPData(Context context) {
    }

    public static SPData getInstance(Context context) {
        if (singleon == null) {
            synchronized (SPData.class) {
                if (singleon == null) {
                    singleon = new SPData(context);
                }
            }
        }
        return singleon;
    }

    private static final String DEFAULT_SHAREDPREFERENCES_NAME = "WifPoject";

    /**
     * 保存用
     *
     * @param key   就是key了
     * @param value 就是你的保存的数据
     * @param name  这个数据集合名字 就是表名
     */
    public void WriteSp(String key, String value, String name) {
        SharedPreferences sharedPreferences = MyApplication.getContext().getSharedPreferences(name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    /**
     * 保存用
     *
     * @param key   就是key了
     * @param value 就是你的保存的数据
     */
    public void WriteSp(String key, String value) {
        SharedPreferences sharedPreferences = MyApplication.getContext().getSharedPreferences(DEFAULT_SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    /**
     * 取出用
     *
     * @param key 你的key
     * @return
     */
    public String ReadSp(String key) {
        SharedPreferences sharedPreferences = MyApplication.getContext().getSharedPreferences(DEFAULT_SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, "NULL");
    }

    /**
     * 取出用
     *
     * @param key  你的key
     * @param name 集合名
     * @return
     */
    public String ReadSp(String key, String name) {
        SharedPreferences sharedPreferences = MyApplication.getContext().getSharedPreferences(name, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, "NULL");
    }

    /**
     * 取出用
     *
     * @param key  你的key
     * @param name 集合名
     * @param tips 如果数据中是空的就提示这个String
     * @return
     */
    public String ReadSp(String key, String name, String tips) {
        SharedPreferences sharedPreferences = MyApplication.getContext().getSharedPreferences(name, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, tips == null ? "NULL" : tips);
    }


}
