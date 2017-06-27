package com.zhny.gr.wisdomcity.util;

import android.content.Context;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.HashMap;

/**
 * Created by czm on 2017/4/26.
 */
public class NetWork {

    private volatile static NetWork singleonNetWork;

    public NetWork() {
    }


    public static NetWork getInstance(Context context) {
        if (singleonNetWork == null) {
            synchronized (NetWork.class) {
                if (singleonNetWork == null) {
                    singleonNetWork = new NetWork();
                }
            }
        }
        return singleonNetWork;
    }

    public static final int GET = 0;
    public static final int POST = 1;

    //get请求需要修改
    public void request(String src, int postorget, HashMap<String, String> map, final IRequestCallBack IRequestCallBack) {
        if (src == null) {
            return;
        }
        RequestParams requestParams = new RequestParams(src);
        if (null != map) {
            for (String key : map.keySet()) {
                switch (postorget) {
                    case GET:
                        requestParams.addQueryStringParameter(key, map.get(key));
                        break;
                    case POST:
                        requestParams.addBodyParameter(key, map.get(key));
                        break;
                    default:
                        requestParams.addBodyParameter(key, map.get(key));
                        break;
                }
            }
        }
        selectRequest(requestParams, postorget, IRequestCallBack);
    }

    public void request(String src, int postorget, final IRequestCallBack IRequestCallBack) {
        if (src == null) {
            return;
        }
        selectRequest(new RequestParams(src), postorget, IRequestCallBack);
    }

    public void request(RequestParams requestParams, int postorget, final IRequestCallBack IRequestCallBack) {
        if (requestParams == null) {
            return;
        }
        selectRequest(requestParams, postorget, IRequestCallBack);
    }

    private void selectRequest(RequestParams requestParams, int postorget, final IRequestCallBack IRequestCallBack) {
        switch (postorget) {
            case GET:
                get(requestParams, IRequestCallBack);
                break;
            case POST:
                post(requestParams, IRequestCallBack);
                break;
            default:
                post(requestParams, IRequestCallBack);
                break;
        }
    }

    private void post(RequestParams requestParams, final IRequestCallBack IRequestCallBack) {
        x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if (null != IRequestCallBack) {
                    IRequestCallBack.onSuccessCallBack(result);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                if (null != IRequestCallBack) {
                    IRequestCallBack.onErrorCallBack(ex, isOnCallback);
                }
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                if (null != IRequestCallBack) {
                    IRequestCallBack.onFinished();
                }
            }
        });
    }

    private void get(RequestParams requestParams, final IRequestCallBack IRequestCallBack) {
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if (null != IRequestCallBack) {
                    IRequestCallBack.onSuccessCallBack(result);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                if (null != IRequestCallBack) {
                    IRequestCallBack.onErrorCallBack(ex, isOnCallback);
                }
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                if (null != IRequestCallBack) {
                    IRequestCallBack.onFinished();
                }
            }
        });
    }


}
