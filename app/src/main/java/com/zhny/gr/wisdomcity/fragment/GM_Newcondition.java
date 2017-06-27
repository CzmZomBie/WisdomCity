package com.zhny.gr.wisdomcity.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zhny.gr.wisdomcity.R;
import com.zhny.gr.wisdomcity.adapter.NewsContentAdapter;
import com.zhny.gr.wisdomcity.widget.SpaceItemDecoration;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by czm on 2017/6/1 0001.
 */
@ContentView(R.layout.new_condition)
public class GM_Newcondition extends BaseFragment_v4 {

    private volatile static GM_Newcondition leaderReferral;

    public GM_Newcondition() {
    }


    public static GM_Newcondition getInstance() {
        if (leaderReferral == null) {
            synchronized (GM_Newcondition.class) {
                if (leaderReferral == null) {
                    leaderReferral = new GM_Newcondition();
                }
            }
        }
        return leaderReferral;
    }

    private View view;
    @ViewInject(R.id.newsContentView)
    RecyclerView newsContentView;

    NewsContentAdapter newsContentAdapter;


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isfirst = true;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCallBack(true);
        getData();
    }

    private boolean isfirst = true;

    @Override
    protected void loadData(boolean isVisibleToUser) {
        if (isfirst && isVisibleToUser) {
            newsContentView.setLayoutManager(new LinearLayoutManager(getContext()));
            newsContentView.setAdapter(newsContentAdapter = new NewsContentAdapter(getContext(), maps));
            newsContentView.setItemAnimator(new DefaultItemAnimator());
            newsContentView.addItemDecoration(new SpaceItemDecoration(10));
            newsContentAdapter.setOnItemClickListener(new NewsContentAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
//                    startActivity(NewsActivity.class);

                }
            });
            newsContentAdapter.setOnItemSelectedListener(new NewsContentAdapter.OnItemSelectedListener() {
                @Override
                public void onItemSelected(View view, int position) {
                    if (position > newsContentAdapter.getItemCount() - 3 && !isLoading) {
                        isLoading = true;
                        onCallBack(true);
                        mPosition = position;
                        new Thread(thread).start();
                    }
                }
            });
        }
    }

    int mPosition = 0;
    boolean isLoading = false;

    List<Map<String, String>> maps = new ArrayList<>();

    private void getData() {
        for (int i = 0; i < 10; i++) {
            Map<String, String> map = new HashMap<>();
            map.put("title", "我是标题");
            map.put("time", "2017.06.13");
            maps.add(map);
        }
        if (newsContentAdapter != null) {
            newsContentAdapter.notifyItemInserted(mPosition);
        }
        isLoading = false;
        onCallBack(false);
    }

    Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                Thread.sleep(1000);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        getData();
                    }
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    });

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
    }



}
