package com.zhny.gr.wisdomcity.activity.trip;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhny.gr.wisdomcity.R;
import com.zhny.gr.wisdomcity.activity.activityhome.BaseActivity;
import com.zhny.gr.wisdomcity.adapter.TourtableAdapter;
import com.zhny.gr.wisdomcity.bean.DestinationBean;
import com.zhny.gr.wisdomcity.util.LogUtil;
import com.zhny.gr.wisdomcity.util.NetWorkPort;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by czm on 2017/5/26 0026.
 */

@ContentView(R.layout.tourtable)
public class Tourtable extends BaseActivity {

    private static final String TAG = "Tourtable ";

    @ViewInject(R.id.recyclerview)
    RecyclerView recyclerView;

    @ViewInject(R.id.loadLayout)
    public ProgressBar loadLayout;


    List<DestinationBean> destinations, loadDatas;
    TourtableAdapter recyclerAdapter;
    private int pageCount = 10, mPosition = 0;
    private boolean isLoading = false, isFister = true, listener = false;
    RequestParams requestParams;
    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        destinations = new ArrayList<>();
        loadDatas = new ArrayList<>();
        type = getIntent().getExtras().getString("type");
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL));
        loadData();
        updateFocusable(recyclerView);
    }


    private void loadData() {
        loadLayout.setVisibility(View.VISIBLE);
        requestParams = new RequestParams(NetWorkPort.CONTENT_LIST_URL);
        requestParams.addBodyParameter("channelIds", type == "" ? "129" : type);
        requestParams.addBodyParameter("count", destinations.size() == 0 ? "" + pageCount : "" + destinations.size() + pageCount);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("channelIds", type == "" ? "129" : type);
        hashMap.put("count", destinations.size() == 0 ? "" + pageCount : "" + destinations.size() + pageCount);

        x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                Type lt = new TypeToken<List<DestinationBean>>() {
                }.getType();
                destinations = gson.fromJson(result, lt);
                recyclerView.setAdapter(recyclerAdapter == null ? recyclerAdapter = new TourtableAdapter(getContext(), destinations) : recyclerAdapter);
                if (!listener) {
                    listener = true;
                    recyclerAdapter.setOnItemClickListener(new TourtableAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("destinationBean", destinations.get(position));
                            startActivity(Scenic.class, bundle);
                        }
                    });
                    recyclerAdapter.setOnItemSelectListener(new TourtableAdapter.OnItemSelectListener() {
                        @Override
                        public void onItemSelect(View view, int position) {
                            mPosition = position;
                            if (position > recyclerAdapter.getItemCount() - 3 && !isLoading) {
                                isLoading = true;
                                getNewData();
                                LogUtil.e(TAG, "onItemSelect: position: " + position);
                                LogUtil.e(TAG, "onItemSelect: count: " + recyclerAdapter.getItemCount());
                            }
                        }
                    });
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                isLoading = false;
                loadLayout.setVisibility(View.GONE);
            }
        });
    }


    private boolean isLoadOver = false;

    private synchronized void getNewData() {
        if (isLoadOver) {
            LogUtil.e(TAG, "getNewData: 所有数据已经加载完毕");
            return;
        }
        loadLayout.setVisibility(View.VISIBLE);
        LogUtil.e(TAG, "getNewData: 加载下一页数据");
        requestParams = new RequestParams("http://192.168.0.251:8080/api/content/list.jspx");
        requestParams.addBodyParameter("channelIds", type == "" ? "129" : type);
        requestParams.addBodyParameter("count", destinations.size() == 0 ? "" + pageCount : "" + destinations.size() + pageCount);
        x.http().post(requestParams, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                Type lt = new TypeToken<List<DestinationBean>>() {
                }.getType();
                loadDatas = gson.fromJson(result, lt);
                if (destinations.size() == loadDatas.size()) {
                    isLoadOver = true;
                }
                for (int i = destinations.size(); i < loadDatas.size(); i++) {
                    destinations.add(loadDatas.get(i));
                }
                recyclerAdapter.notifyItemChanged(mPosition);
                isLoading = false;
            }

            @Override
            public void onError(Throwable throwable, boolean b) {

            }

            @Override
            public void onCancelled(CancelledException e) {

            }

            @Override
            public void onFinished() {
                loadLayout.setVisibility(View.GONE);
            }
        });
    }


}
