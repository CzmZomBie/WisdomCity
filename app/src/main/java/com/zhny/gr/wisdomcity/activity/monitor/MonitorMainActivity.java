package com.zhny.gr.wisdomcity.activity.monitor;

import android.content.Intent;
import android.net.NetworkRequest;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.zhny.gr.wisdomcity.R;
import com.zhny.gr.wisdomcity.activity.activityhome.BaseActivity;
import com.zhny.gr.wisdomcity.adapter.CountryAdapter;
import com.zhny.gr.wisdomcity.adapter.TownAdapter;
import com.zhny.gr.wisdomcity.bean.Town;
import com.zhny.gr.wisdomcity.util.HttpRequest;
import com.zhny.gr.wisdomcity.util.IRequestCallBack;
import com.zhny.gr.wisdomcity.util.JsonHelper;
import com.zhny.gr.wisdomcity.util.NetWork;
import com.zhny.gr.wisdomcity.util.NetWorkPort;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.http.annotation.HttpResponse;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

@ContentView(R.layout.ac_monitormain)
public class MonitorMainActivity extends BaseActivity {
    @ViewInject(R.id.gridView)
    GridView gridView;
    @ViewInject(R.id.listView)
    ListView listView;
    @ViewInject(R.id.rl_ps_gv)
    RelativeLayout rlGv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getTokenData();
//        setListener();
    }

    private List<Town> townList;
    private String token;
    private List<Town> countryList;


    public void getTokenData() {
        RequestParams requestParams = new RequestParams(NetWorkPort.LOGIN_URLS);
        requestParams.addBodyParameter("username", "ccbn");
        requestParams.addBodyParameter("password", "ccbn");
        x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject ob = new JSONObject(result);
                    token = ob.get("token").toString();
                    Log.e(TAG, "onSuccess: " + token);
                    getTownData();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e(TAG, "onError: ");
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Log.e(TAG, "onCancelled: ");
            }

            @Override
            public void onFinished() {
                Log.e(TAG, "onFinished: ");
            }
        });
    }


    public void getTownData() {
        new Thread(getThread1).start();
    }

    private Thread getThread1 = new Thread() {
        public void run() {
            String result = HttpRequest.getTownData(token);
            Message msg = handler.obtainMessage();
            msg.obj = result;
            msg.what = 0x101;
            handler.sendMessage(msg);
        }
    };

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.obj == null) {
                return;
            }
            if (msg.what == 0x101) {
                List<Town> townList = JsonHelper.getObjectT(msg.obj.toString(), new TypeToken<List<Town>>() {
                }.getType());
                setTownList(townList);
            }
            if (msg.what == 0x102) {
                List<Town> townList = JsonHelper.getObjectT(msg.obj.toString(), new TypeToken<List<Town>>() {
                }.getType());
                setCountryList(townList);
            }
            super.handleMessage(msg);
        }
    };
//
    private void setCountryList(List<Town> townList) {
        rlGv.setVisibility(View.GONE);
        this.countryList = townList;
        CountryAdapter adapter = new CountryAdapter(this, countryList);
        gridView.setAdapter(adapter);
        adapter.notifyDataSetChanged();


    }

    private void setTownList(List<Town> townList) {
        this.townList = townList;
        TownAdapter adapter = new TownAdapter(this, townList);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        getCountryData(0);
    }

    @Event(value = {R.id.listView, R.id.gridView}, type = AdapterView.OnItemClickListener.class)
    private void onGridItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.listView:
                getCountryData(position);
                break;
            case R.id.gridView:
                Town town = countryList.get(position);
                Intent intent = new Intent(getContext(), MonitorActivity.class);
                intent.putExtra("token", token).putExtra("id", town.getId());
                startActivity(intent);
                break;
        }
    }

    private void getCountryData(int position) {
        rlGv.setVisibility(View.VISIBLE);
        Town town = townList.get(position);
        this.townId = town.getId();
        new Thread(getCountryThread).start();
    }

    private String townId;
    private Thread getCountryThread = new Thread() {
        public void run() {
            String result = HttpRequest.getVillageData(token, townId);
            Message msg = handler.obtainMessage();
            msg.obj = result;
            msg.what = 0x102;
            handler.sendMessage(msg);
        }
    };


}
