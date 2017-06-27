package com.zhny.gr.wisdomcity.adapter;

import android.content.Context;

import com.zhny.gr.wisdomcity.R;
import com.zhny.gr.wisdomcity.bean.Town;

import java.util.List;


/**
 * Created by tong on 2017/5/9.
 */

public class CountryAdapter extends FastAdapter<Town> {

    public CountryAdapter(Context context, List<Town> datas) {
        super(context, datas, R.layout.item_gv_country_main);
    }

    @Override
    public void convert(ViewHolder holder, Town country, int position) {
        holder.setText(R.id.tv_item, country.getName()).
                loadImg(R.id.iv_item, getContext(), country.getImage());

    }
}
