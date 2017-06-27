package com.zhny.gr.wisdomcity.fragment;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;


import com.zhny.gr.wisdomcity.R;
import com.zhny.gr.wisdomcity.view.EffectNoDrawBridge;
import com.zhny.gr.wisdomcity.view.MainUpView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by czm on 2017/6/1 0001.
 */
@ContentView(R.layout.leader_referral)
public class GM_LeaderReferral extends BaseFragment_v4 {

    private volatile static GM_LeaderReferral GMLeaderReferral;

    public GM_LeaderReferral() {
    }

    public static GM_LeaderReferral getInstance() {
        if (GMLeaderReferral == null) {
            synchronized (GM_LeaderReferral.class) {
                if (GMLeaderReferral == null) {
                    GMLeaderReferral = new GM_LeaderReferral();
                }
            }
        }
        return GMLeaderReferral;
    }

    @ViewInject(R.id.mainUpView1)
    private MainUpView mainUpView1;
    private View view, focusView, mOldView;

    @Event(value = {R.id.item1, R.id.item2, R.id.item3, R.id.item4}, type = View.OnClickListener.class)
    private void onClick(View view){
//        startActivity(NewsActivity.class);
    }


    @Event(value = {R.id.item1, R.id.item2, R.id.item3, R.id.item4}, type = View.OnFocusChangeListener.class)
    private void onFocus(View view, boolean hasFocus) {
        if (view == null) {
            return;
        }
        if (hasFocus) {
            if (isfirst) {
                isfirst = false;
                mainUpView1.setEffectBridge(new EffectNoDrawBridge());
                EffectNoDrawBridge bridget = (EffectNoDrawBridge) mainUpView1.getEffectBridge();
                bridget.setTranDurAnimTime(200);
                bridget.setVisibleWidget(false);
                // 设置移动边框的图片.
                mainUpView1.setUpRectResource(R.drawable.leader_pb);
            }
            focusView = view;
            // 移动方框缩小的距离. L  T  R  B
            mainUpView1.setDrawUpRectPadding(new Rect(-8, -8, -10, -9));
            mainUpView1.setFocusView(view, mOldView, 1.0f);
            mOldView = view;
        } else {
            isfirst = true;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private boolean isfirst = true;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
    }



    @Override
    protected void loadData(boolean isVisibleToUser) {

    }
}
