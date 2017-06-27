package com.zhny.gr.wisdomcity.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.zhny.gr.wisdomcity.R;
import com.zhny.gr.wisdomcity.activity.activityhome.BaseActivity;
import com.zhny.gr.wisdomcity.bean.DestinationBean;
import com.zhny.gr.wisdomcity.util.NetWorkPort;

import java.util.List;

/**
 * Created by czm on 2017/6/13 0013.
 */

public class TourtableAdapter extends RecyclerView.Adapter<TourtableAdapter.Holder> implements View.OnClickListener, View.OnFocusChangeListener {

    private static final String TAG = "TourtableAdapter";

    private Context context;
    List<DestinationBean> destinations;

    public TourtableAdapter(Context context, List<DestinationBean> destinations) {
        this.context = context;
        this.destinations = destinations;
    }

    @Override
    public TourtableAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sceneryitem, parent, false);
        TourtableAdapter.Holder vh = new TourtableAdapter.Holder(view);
        return vh;
    }

    TourtableAdapter.Holder holder;
    int position;

    @Override
    public void onBindViewHolder(TourtableAdapter.Holder holder, int position) {
        this.holder = holder;
        this.position=position;
        String urlPic = destinations.get(position).getPicPaths().replace("127.0.0.1:8080", NetWorkPort.IP_PORT2);
        if (urlPic.indexOf(",") != -1) {
            urlPic = urlPic.substring(0, urlPic.indexOf(","));
        }
        Log.e(TAG, "onBindViewHolder: " + urlPic);
        BaseActivity.XImage(holder.sceneryImage, urlPic);
        holder.titleText.setText(destinations.get(position).getTitle());
        holder.contentText.setText(destinations.get(position).getDescription());
//        holder.contentText.setText(Html.fromHtml(toDBC(destinations.get(position).getDescription())));
        holder.mLayout.setTag(position);
        holder.mLayout.setOnClickListener(this);
        holder.mLayout.setOnFocusChangeListener(this);
    }

    @Override
    public int getItemCount() {
        return destinations.size();
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(v, (int) v.getTag());
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus && mOnItemSelectListener != null) {
            mOnItemSelectListener.onItemSelect(v, (int) v.getTag());
        }

    }

    static class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        MyItemClickListener mListener;
        ImageView sceneryImage;
        TextView titleText, contentText;
        LinearLayout mLayout;
        RelativeLayout message;

        public Holder(final View view) {
            super(view);
            mLayout = (LinearLayout) view.findViewById(R.id.item1);
            sceneryImage = (ImageView) view.findViewById(R.id.image);
            titleText = (TextView) view.findViewById(R.id.title);
            contentText = (TextView) view.findViewById(R.id.contentText);
            message = (RelativeLayout) view.findViewById(R.id.item1_message);
        }


        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.onItemClick(v, getPosition());
            }
        }
    }

    private OnItemClickListener mOnItemClickListener = null;
    private OnItemSelectListener mOnItemSelectListener = null;

    public static interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public static interface OnItemSelectListener {
        void onItemSelect(View view, int position);
    }


    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public void setOnItemSelectListener(OnItemSelectListener listener) {
        this.mOnItemSelectListener = listener;
    }

}