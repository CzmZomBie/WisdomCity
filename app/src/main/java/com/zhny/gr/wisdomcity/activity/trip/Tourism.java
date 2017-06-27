package com.zhny.gr.wisdomcity.activity.trip;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhny.gr.wisdomcity.R;
import com.zhny.gr.wisdomcity.activity.activityhome.BaseActivity;
import com.zhny.gr.wisdomcity.bean.TourBean;
import com.zhny.gr.wisdomcity.util.IRequestCallBack;
import com.zhny.gr.wisdomcity.util.NetWork;
import com.zhny.gr.wisdomcity.util.NetWorkPort;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by czm on 2017/5/26 0026.
 */

@ContentView(R.layout.ac_tourism)
public class Tourism extends BaseActivity {

    private View oldView, oldPage, oldText;

    @ViewInject(R.id.page1_m)
    LinearLayout page1;
    @ViewInject(R.id.page2_m)
    LinearLayout page2;
    @ViewInject(R.id.page3_m)
    LinearLayout page3;
    @ViewInject(R.id.page4_m)
    LinearLayout page4;

    @ViewInject(R.id.page1_w)
    ImageView text1;
    @ViewInject(R.id.page2_w)
    ImageView text2;
    @ViewInject(R.id.page3_w)
    ImageView text3;
    @ViewInject(R.id.page4_w)
    ImageView text4;

    @ViewInject(R.id.item_msg1)
    TextView item1_text;
    @ViewInject(R.id.item_msg2)
    TextView item2_text;
    @ViewInject(R.id.item_msg3)
    TextView item3_text;
    @ViewInject(R.id.item_msg4)
    TextView item4_text;

    List<TourBean> tourBeans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final List<TextView> items = new ArrayList<>();
        items.add(item1_text);
        items.add(item2_text);
        items.add(item3_text);
        items.add(item4_text);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("parentId", "128");
        tourBeans = new ArrayList<>();
        NetWork.getInstance(this).request(NetWorkPort.COLUMN_URL, NetWork.POST, hashMap, new IRequestCallBack() {
            @Override
            public void onSuccessCallBack(String msg) {
                Gson gson = new Gson();
                Type lt = new TypeToken<List<TourBean>>() {
                }.getType();
                tourBeans = gson.fromJson(msg, lt);//description
                for (int i = 0; i < 4; i++) {
                    items.get(i).setText(tourBeans.get(i).getDescription());
                }
            }

            @Override
            public void onErrorCallBack(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    @Event(value = {R.id.page1, R.id.page2, R.id.page3, R.id.page4}, type = View.OnClickListener.class)
    private void onClick(View view) {
        int type = 129;
        if (tourBeans.size() != 0) {
            switch (view.getId()) {
                case R.id.page1:
                    if (tourBeans.size() > 0) {
                        type = tourBeans.get(0).getId();
                    }
                    break;
                case R.id.page2:
                    if (tourBeans.size() > 1) {
                        type = tourBeans.get(1).getId();
                    }
                    break;
                case R.id.page3:
                    if (tourBeans.size() > 2) {
                        type = tourBeans.get(2).getId();
                    }
                    break;
                case R.id.page4:
                    if (tourBeans.size() > 3) {
                        type = tourBeans.get(3).getId();
                    }
                    break;
            }
        }
        Bundle bundle = new Bundle();
        bundle.putString("type", "" + type);
        startActivity(Tourtable.class, bundle);
    }

    @Event(value = {R.id.page1, R.id.page2, R.id.page3, R.id.page4}, type = View.OnFocusChangeListener.class)
    private void onFocus(View view, boolean hasFocus) {
        if (view == null) return;
        if (hasFocus) {
            view.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1.5f));
            switch (view.getId()) {
                case R.id.page1:
                    setView(page1, text1);
                    oldPage = page1;
                    oldText = text1;
                    break;
                case R.id.page2:
                    setView(page2, text2);
                    oldPage = page2;
                    oldText = text2;
                    break;
                case R.id.page3:
                    setView(page3, text3);
                    oldPage = page3;
                    oldText = text3;
                    break;
                case R.id.page4:
                    setView(page4, text4);
                    oldPage = page4;
                    oldText = text4;
                    break;
            }
            oldView = view;
        } else {
            if (oldView == null) return;
            oldView.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f));
            if (oldPage == null) return;
            oldPage.setVisibility(View.GONE);
            if (oldText == null) return;
            oldText.setVisibility(View.VISIBLE);
        }
    }

    private void setView(View v1, View v2) {
        v1.setVisibility(View.VISIBLE);
        v2.setVisibility(View.GONE);
    }

}
