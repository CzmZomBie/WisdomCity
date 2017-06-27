package com.zhny.gr.wisdomcity.adapter;

import android.content.Context;
import android.widget.LinearLayout;

import com.zhny.gr.wisdomcity.R;
import com.zhny.gr.wisdomcity.bean.Town;

import java.util.List;


/**
 * Created by tong on 2017/5/8.
 */

public class TownAdapter extends FastAdapter<Town> {


    public TownAdapter(Context context, List<Town> datas) {
        super(context, datas, R.layout.tv_item_tab);
    }

    @Override
    public void convert(ViewHolder holder, Town tabItem, int position) {
        holder.setText(R.id.tv_item, tabItem.getName());

        LinearLayout ll = holder.getView(R.id.ll_all_item);


    }
}
