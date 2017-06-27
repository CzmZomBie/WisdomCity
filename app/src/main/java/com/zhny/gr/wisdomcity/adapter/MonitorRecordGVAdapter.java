package com.zhny.gr.wisdomcity.adapter;

import android.content.Context;

import com.zhny.gr.wisdomcity.R;
import com.zhny.gr.wisdomcity.bean.Results;

import java.util.List;


/**
 * Created by tong on 2017/5/4.
 */

public class MonitorRecordGVAdapter extends FastAdapter<Results> {

    public MonitorRecordGVAdapter(Context context, List<Results> datas) {
        super(context, datas, R.layout.item_gv_country_picture);
    }


    @Override
    public void convert(ViewHolder holder, Results results, int position) {
        holder.setText(R.id.tv_item, results.getRecording_date()).loadImg(R.id.iv_item, getContext(), results.getScreenshot());
    }
}
