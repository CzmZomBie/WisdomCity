package com.zhny.gr.wisdomcity.util;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import java.lang.reflect.Type;

/**
 * json工具类
 * Created by lsw on 2016/11/15.
 */

public class JsonHelper {

    /**
     * 根据TypeToken类型解析Json，如果非Json格式返回null
     *
     * @param json
     * @param type
     * @param <T>
     * @return
     */
    public static <T> T getObjectT(String json, Type type) {
        if (TextUtils.isEmpty(json)) {
            return null;
        }
        try {
            new JsonParser().parse(json);

        } catch (JsonParseException e) {
            return null;
        }
        Gson gson = new Gson();
        return gson.fromJson(json, type);
    }


    public static <T> String toJsonT(T t) {
        Gson gson = new Gson();
        return gson.toJson(t);
    }
}
