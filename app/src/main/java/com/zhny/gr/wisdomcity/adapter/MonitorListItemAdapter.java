package com.zhny.gr.wisdomcity.adapter;

import android.content.Context;

import com.zhny.gr.wisdomcity.R;
import com.zhny.gr.wisdomcity.bean.Town;

import java.util.List;


/**
 * Created by tong on 2017/5/4.
 */

public class MonitorListItemAdapter extends FastAdapter<Town> {

    public MonitorListItemAdapter(Context context, List<Town> datas) {
        super(context, datas, R.layout.item_monitor_item_list);
    }


    @Override
    public void convert(ViewHolder holder, Town town, int position) {
        holder.setText(R.id.tv_item, town.getIpc_name());
    }
}
