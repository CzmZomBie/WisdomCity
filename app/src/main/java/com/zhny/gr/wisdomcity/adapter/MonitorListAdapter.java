package com.zhny.gr.wisdomcity.adapter;

import android.content.Context;

import com.zhny.gr.wisdomcity.R;
import com.zhny.gr.wisdomcity.bean.Town;

import java.util.List;


/**
 * Created by tong on 2017/5/4.
 */

public class MonitorListAdapter extends FastAdapter<Town> {

    public MonitorListAdapter(Context context, List<Town> datas) {
        super(context, datas, R.layout.item_gv_country_picture);
    }


    @Override
    public void convert(ViewHolder holder, Town town, int position) {
        holder.setText(R.id.tv_item, town.getIpc_name()).loadImg(R.id.iv_item, getContext(), town.getScreenshot());
    }
}
