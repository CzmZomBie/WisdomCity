package com.zhny.gr.wisdomcity.activity.administration;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.zhny.gr.wisdomcity.R;
import com.zhny.gr.wisdomcity.adapter.ViewpageAdapter;
import com.zhny.gr.wisdomcity.fragment.GM_Evolve;
import com.zhny.gr.wisdomcity.fragment.GM_LeaderReferral;
import com.zhny.gr.wisdomcity.fragment.GM_Measure;
import com.zhny.gr.wisdomcity.fragment.GM_Newcondition;
import com.zhny.gr.wisdomcity.fragment.GM_ReadPolicy;
import com.zhny.gr.wisdomcity.fragment.GM_Suffer;
import com.zhny.gr.wisdomcity.fragment.ILoadCallBack;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by czm on 2017/6/1 0001.
 */
@ContentView(R.layout.zhenwu)
public class GovenrnmentActivity extends FragmentActivity implements ILoadCallBack {

    private static final String TAG = "GovenrnmentActivity";

    private static final String[] CHANNELS = new String[]{"领导介绍", "最新动态", "读政策", "学经验", "跟进展", "看措施"};
    private List<String> mDataList = Arrays.asList(CHANNELS);
    private List<Fragment> fragments = new ArrayList<>();

    private ViewPager mViewPager;
    GM_Newcondition gm_newcondition = new GM_Newcondition().getInstance();
    @ViewInject(R.id.loadLayout)
    ProgressBar loadBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        mViewPager = (ViewPager) findViewById(R.id.bodyViewpage);
        fragments.add(new GM_LeaderReferral().getInstance());
        fragments.add(gm_newcondition);
        fragments.add(new GM_ReadPolicy().getInstance());
        fragments.add(new GM_Suffer().getInstance());
        fragments.add(new GM_Evolve().getInstance());
        fragments.add(new GM_Measure().getInstance());
        mViewPager.setAdapter(new ViewpageAdapter(getSupportFragmentManager(), fragments));
        initMagicIndicator1();
        updateFocusable(mViewPager);
        gm_newcondition.setCallBack(this);
    }

    private void updateFocusable(View view) {
        if (view == null) {
            return;
        }
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.requestFocusFromTouch();
    }

    private void initMagicIndicator1() {
        MagicIndicator magicIndicator = (MagicIndicator) findViewById(R.id.magicIndicator);
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mDataList == null ? 0 : mDataList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new ColorTransitionPagerTitleView(context);
                simplePagerTitleView.setText(mDataList.get(index));
                simplePagerTitleView.setNormalColor(Color.parseColor("#99717981"));
                simplePagerTitleView.setTextSize(24);
                simplePagerTitleView.setPadding(24, 0, 24, 0);
                simplePagerTitleView.setSelectedColor(R.color.bule_wt);
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mViewPager.setCurrentItem(index);
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
//                LinePagerIndicator indicator = new LinePagerIndicator(context);
//                indicator.setLineHeight(8);
                LinePagerIndicator indicator = new LinePagerIndicator(context);
//                indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                indicator.setLineHeight(8);
                indicator.setStartInterpolator(new AccelerateInterpolator());
                indicator.setColors(Color.parseColor("#437db7"));
                return indicator;
            }
        });
        magicIndicator.setNavigator(commonNavigator);
        LinearLayout titleContainer = commonNavigator.getTitleContainer(); // must after setNavigator
        titleContainer.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        titleContainer.setDividerPadding(UIUtil.dip2px(this, 15));
        titleContainer.setDividerDrawable(getResources().getDrawable(R.drawable.simple_splitter));
        ViewPagerHelper.bind(magicIndicator, mViewPager);
    }

    @Override
    public void executeStartRequest() {
        loadBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void executeFinishRequest() {
        loadBar.setVisibility(View.INVISIBLE);
    }
}
