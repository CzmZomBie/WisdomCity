package com.zhny.gr.wisdomcity.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;


import com.zhny.gr.wisdomcity.R;
import com.zhny.gr.wisdomcity.activity.activityhome.BaseActivity;
import com.zhny.gr.wisdomcity.dialog.DialogFactory;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by czm on 2017/5/2.
 */
@ContentView(R.layout.ac_weblayout)
public class WebActivity extends BaseActivity {

    @ViewInject(R.id.webView)
    WebView webView;
    WebSettings webSettings;
    String website;
    Dialog dialog;

    private static Intent intent = null;
    private static Bundle bundle = null;

    //外部调用此方法打开
    public static final void startActivity(Context ac, String URL) {
        if (intent == null) {
            intent = new Intent();
            intent.setClass(ac, WebActivity.class);
        }
        if (bundle == null) {
            bundle = new Bundle();
        } else {
            bundle.clear();
        }
        bundle.putString("URL", URL);
        intent.putExtras(bundle);
        ac.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_weblayout);
        website = getIntent().getStringExtra("URL");
        if (null == website || "http".equals(website) || "".equals(website) || "NULL".equals(website)) {
            toastLongShow("地址为空,请返回主界面设置启动地址");
        }
        intoView();
        loadWebView();
    }

    private void loadWebView() {
        webSettings.setJavaScriptEnabled(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);// 设置 不使用缓存，只获取网络数据
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setDisplayZoomControls(false);
        webSettings.setAllowFileAccess(true); // 允许访问文件
        webSettings.setBuiltInZoomControls(true); // 设置显示缩放按钮
        webSettings.setSupportZoom(true);// 支持缩放
        webSettings.setLoadWithOverviewMode(true);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int mDensity = metrics.densityDpi;
        Log.d("maomao", "densityDpi = " + mDensity);
        if (mDensity == 240) {
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
        } else if (mDensity == 160) {
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
        } else if (mDensity == 120) {
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.CLOSE);
        } else if (mDensity == DisplayMetrics.DENSITY_XHIGH) {
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
        } else if (mDensity == DisplayMetrics.DENSITY_TV) {
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
        } else {
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
        }
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void intoView() {
        dialog = DialogFactory.getInstance().getLoadingDialog(getContext(), R.string.load);
        webView = (WebView) findViewById(R.id.webView);
        webView.loadUrl(website);
        webSettings = webView.getSettings();
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                // TODO Auto-generated method stub
                dialog.show();
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                dialog.cancel();
            }
        });

    }
}
