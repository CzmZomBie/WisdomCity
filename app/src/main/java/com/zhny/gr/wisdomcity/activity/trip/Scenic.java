package com.zhny.gr.wisdomcity.activity.trip;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.zhny.gr.wisdomcity.R;
import com.zhny.gr.wisdomcity.activity.activityhome.BaseActivity;
import com.zhny.gr.wisdomcity.adapter.PicAdapter;
import com.zhny.gr.wisdomcity.bean.DestinationBean;
import com.zhny.gr.wisdomcity.util.NetWorkPort;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by czm on 2017/5/26 0026.
 */

@ContentView(R.layout.activity_particulars)
public class Scenic extends BaseActivity {

    private static final String TAG = "Photo";

    @ViewInject(R.id.photoRecycler)
    RecyclerView photoRecycler;

    @ViewInject(R.id.articleText)
    TextView articleText;

    @ViewInject(R.id.title)
    TextView titleText;

    DestinationBean destinationBean;
    PicAdapter picAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        destinationBean = (DestinationBean) getIntent().getSerializableExtra("destinationBean");
        articleText.setText(destinationBean.getTxt());
        titleText.setText(destinationBean.getTitle());
        picPaths(destinationBean.getPicPaths());
        photoRecycler.setLayoutManager(new GridLayoutManager(this, 2));
        photoRecycler.setAdapter(picAdapter = new PicAdapter(this, piclist));
        photoRecycler.setItemAnimator(new DefaultItemAnimator());
        picAdapter.setOnItemClickListener(new PicAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("photos", (ArrayList<String>) piclist);
                bundle.putInt("postion", position);
                startActivity(PhotoActivity.class, bundle);
            }
        });
    }


    List<String> piclist = new ArrayList<>();
    List<Integer> integers = new ArrayList<>();

    private void picPaths(String pics) {
        char[] chars = pics.toCharArray();
        char dian = ',';
        for (int i = 0; i < chars.length; i++) {
            if (dian == chars[i]) {
                integers.add(i);
            }
        }

        for (int i = 0; i < integers.size(); i++) {
            switch (i) {
                case 0:
                    piclist.add(getPicPath(pics.substring(0, integers.get(i))));
                    break;
                default:
                    piclist.add(getPicPath(pics.substring(integers.get(i - 1) + 1, integers.get(i))));
                    break;
            }
            if (i == (integers.size() - 1)) {
                piclist.add(getPicPath(pics.substring(integers.get(i) + 1, pics.length())));
            }
        }
        for (String srt : piclist) {
            Log.d(TAG, "picPaths: " + srt);
        }
        if(piclist.size()==0&&pics!=null&&pics!=""){
            piclist.add(getPicPath(pics));
        }
    }

    private String getPicPath(String s) {
        return s.replace("127.0.0.1:8080", NetWorkPort.IP_PORT2);
    }

}
