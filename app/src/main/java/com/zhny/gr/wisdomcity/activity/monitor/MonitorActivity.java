package com.zhny.gr.wisdomcity.activity.monitor;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhny.gr.wisdomcity.R;
import com.zhny.gr.wisdomcity.activity.activityhome.BaseActivity;
import com.zhny.gr.wisdomcity.adapter.FastAdapter;
import com.zhny.gr.wisdomcity.adapter.MonitorListAdapter;
import com.zhny.gr.wisdomcity.adapter.MonitorListItemAdapter;
import com.zhny.gr.wisdomcity.adapter.MonitorRecordGVAdapter;
import com.zhny.gr.wisdomcity.adapter.ViewHolder;
import com.zhny.gr.wisdomcity.bean.RecordRoot;
import com.zhny.gr.wisdomcity.bean.Results;
import com.zhny.gr.wisdomcity.bean.Town;
import com.zhny.gr.wisdomcity.util.HttpRequest;
import com.zhny.gr.wisdomcity.util.JsonHelper;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by tong on 2017/5/9.
 */
@ContentView(R.layout.ac_country)
public class MonitorActivity extends BaseActivity {

    @ViewInject(R.id.gridView)
    GridView gridView;
    @ViewInject(R.id.rl_ps)
    RelativeLayout rlPs;
    @ViewInject(R.id.ll_record)
    LinearLayout llRecord;
    @ViewInject(R.id.lv_record)
    ListView lvRecord;
    @ViewInject(R.id.ll_main)
    LinearLayout llMain;
    @ViewInject(R.id.lv_two_buttons)
    ListView lvTwoButtons;
    @ViewInject(R.id.tv_fanhuizhibo)
    TextView tvFanhuizhibo;

    private String token;
    private String id;
    private List<Town> townList;

    private String townItemId;
    private List<Results> recordList;
    private int type = TYPE_JIANKONG;
    public static final int TYPE_JIANKONG = 0x110;
    public static final int TYPE_RECORD = 0x111;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setData();
//        setListener();
    }


    private void setData() {
        token = getIntent().getStringExtra("token");
        id = getIntent().getStringExtra("id");

        if (token != null && id != null) {
            new Thread(getLiveThread).start();
        }

        List buttonList = new ArrayList();
        buttonList.add(getString(R.string.shpinjiankong));
        buttonList.add(getString(R.string.jiankonghuikan));
        lvTwoButtons.setAdapter(new FastAdapter<String>(this, buttonList, R.layout.item_two_buttons) {
            @Override
            public void convert(ViewHolder holder, String s, int position) {
                holder.setText(R.id.tv_item, s);
            }
        });


        tvFanhuizhibo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    tvFanhuizhibo.setBackgroundResource(R.drawable.tab_tv_bg_focus);
                } else {
                    tvFanhuizhibo.setBackgroundResource(R.drawable.tab_tv_bg);
                }
            }
        });

    }

    @Override
    public void onBackPressed() {

        if (type == TYPE_RECORD) {
            type = TYPE_JIANKONG;
            llMain.setVisibility(View.VISIBLE);
            llRecord.setVisibility(View.GONE);
        } else {
            finish();
        }


    }

    private Thread getLiveThread = new Thread() {
        public void run() {
            String result = HttpRequest.getMonitorData(token, id);
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
                setList(townList);
            }
            if (msg.what == 0x102) {
                RecordRoot recordRoot = new Gson().fromJson(msg.obj.toString(), RecordRoot.class);
                setRecordList(recordRoot.getResults());
            }
            super.handleMessage(msg);
        }
    };

    private void setRecordList(List<Results> recordList) {
        this.recordList = recordList;
        MonitorRecordGVAdapter adapter = new MonitorRecordGVAdapter(this, recordList);
        gridView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        rlPs.setVisibility(View.GONE);
    }

    private void setList(List<Town> townList) {
        this.townList = townList;
        MonitorListAdapter adapter = new MonitorListAdapter(this, townList);
        gridView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        MonitorListItemAdapter mAdapter = new MonitorListItemAdapter(this, townList);
        lvRecord.setAdapter(mAdapter);

        rlPs.setVisibility(View.GONE);
    }

    @Event(value = {R.id.gridView, R.id.lv_two_buttons, R.id.lv_record}, type = AdapterView.OnItemClickListener.class)
    private void onGridItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.gridView:
                switch (type) {
                    case TYPE_JIANKONG:
                        if (townList == null) return;
                        startActivity(new Intent(getContext(), LandScapeActivity.class).
                                putExtra("url", townList.get(position).getUrl_auto()).putExtra("type", type));
                        break;
                    case TYPE_RECORD:
                        if (recordList == null) return;
                        startActivity(new Intent(getContext(), IJKViewActivity.class).
                                putExtra("url", recordList.get(position).getUrl_auto()).putExtra("type", type));
                        break;
                }
                break;
            case R.id.lv_two_buttons:
                switch (position) {
                    case 0:
                        if (token != null && MonitorActivity.this.id != null) {
                            rlPs.setVisibility(View.VISIBLE);
                            new Thread(getLiveThread).start();
                        }
                        break;
                    case 1:
                        llMain.setVisibility(View.GONE);
                        llRecord.setVisibility(View.VISIBLE);
                        type = TYPE_RECORD;
                        break;
                }
                break;
            case R.id.lv_record:
                Town town = townList.get(position);
                if (token != null && town.getIpc_id() != null) {
                    townItemId = town.getIpc_id();
                    new Thread(getRecordThread).start();
                }
                break;
        }
    }

    @Event(value = R.id.tv_fanhuizhibo)
    private void onClick(View view) {
        type = TYPE_JIANKONG;
        llMain.setVisibility(View.VISIBLE);
        llRecord.setVisibility(View.GONE);
    }

    private void setListener() {
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (type) {
                    case TYPE_JIANKONG:
                        startActivity(new Intent(getContext(), LandScapeActivity.class).
                                putExtra("url", townList.get(position).getUrl_auto()).putExtra("type", type));
                        break;
                    case TYPE_RECORD:
                        startActivity(new Intent(getContext(), IJKViewActivity.class).
                                putExtra("url", recordList.get(position).getUrl_auto()).putExtra("type", type));
                        break;
                }

            }
        });

        tvFanhuizhibo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = TYPE_JIANKONG;
                llMain.setVisibility(View.VISIBLE);
                llRecord.setVisibility(View.GONE);
            }
        });

        lvTwoButtons.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        if (token != null && MonitorActivity.this.id != null) {
                            rlPs.setVisibility(View.VISIBLE);
                            new Thread(getLiveThread).start();
                        }
                        break;
                    case 1:
                        llMain.setVisibility(View.GONE);
                        llRecord.setVisibility(View.VISIBLE);
                        type = TYPE_RECORD;
                        break;
                }
            }
        });

        lvRecord.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Town town = townList.get(position);
                if (token != null && town.getIpc_id() != null) {
                    townItemId = town.getIpc_id();
                    new Thread(getRecordThread).start();
                }


            }
        });
    }

    private Thread getRecordThread = new Thread() {
        public void run() {
            String result = HttpRequest.getRecordData(token, townItemId);
            Message msg = handler.obtainMessage();
            msg.obj = result;
            msg.what = 0x102;
            handler.sendMessage(msg);
        }
    };


}
