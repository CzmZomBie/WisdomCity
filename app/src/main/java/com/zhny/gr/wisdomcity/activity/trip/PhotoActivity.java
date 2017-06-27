package com.zhny.gr.wisdomcity.activity.trip;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.widget.ImageView;


import com.zhny.gr.wisdomcity.R;
import com.zhny.gr.wisdomcity.activity.activityhome.BaseActivity;
import com.zhny.gr.wisdomcity.adapter.PhotoAdapter;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by czm on 2017/5/26 0026.
 */

@ContentView(R.layout.bitphoto)
public class PhotoActivity extends BaseActivity {

    @ViewInject(R.id.photo)
    ImageView photo;

    @ViewInject(R.id.phototpager)
    ViewPager viewPager;

    @ViewInject(R.id.last)
    ImageView last;
    @ViewInject(R.id.next)
    ImageView next;

    List<String> piclist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        piclist = getIntent().getStringArrayListExtra("photos");
        last.bringToFront();
        next.bringToFront();
        viewPager.setAdapter(new PhotoAdapter(this, piclist));
        viewPager.setCurrentItem(i = getIntent().getExtras().getInt("postion"));
    }

    int i = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_LEFT:
                last();
                break;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                next();
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void next() {
        i++;
        if (i == piclist.size()) {
            i = 0;
        }
        viewPager.setCurrentItem(i);
    }

    private void last() {
        i--;
        if (i == -1) {
            i = piclist.size() - 1;
        }
        viewPager.setCurrentItem(i);
    }

}
