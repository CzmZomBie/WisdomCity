package com.zhny.gr.wisdomcity.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;


import com.zhny.gr.wisdomcity.activity.activityhome.BaseActivity;

import java.util.List;


/**
 * 创建日期：2016/11/2 0002 on 11:05
 * By:Czm Administrator
 */
public class PhotoAdapter extends PagerAdapter {

    private Context context;
    private int postion;

    List<String> paths;

    public PhotoAdapter(Context context, List<String> paths) {
        this.context = context;
        this.paths = paths;
    }

    @Override
    public int getCount() {
        return paths.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = new ImageView(container.getContext());
        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        BaseActivity.XImage(imageView, paths.get(position));
//        imageView.setBackgroundColor((int)R.color.black);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT));
        RelativeLayout relativeLayout = new RelativeLayout(container.getContext());
        relativeLayout.addView(imageView);
        relativeLayout.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
//        relativeLayout.setBackgroundColor((int)R.color.black);
        relativeLayout.setLayoutParams(new ViewGroup.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        container.addView(relativeLayout, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
//        container.setBackgroundColor(+R.color.black);
        return relativeLayout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

}
