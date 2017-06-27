package com.zhny.gr.wisdomcity.util;

/**
 * 创建日期：2016/12/23 0023 on 13:57
 * By:Czm Administrator
 */
public interface IRequestCallBack {

    void onSuccessCallBack(String msg);

    void onErrorCallBack(Throwable ex, boolean isOnCallback);

    void onFinished();

}
