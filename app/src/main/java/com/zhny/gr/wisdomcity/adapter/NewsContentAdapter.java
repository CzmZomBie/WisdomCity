package com.zhny.gr.wisdomcity.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.zhny.gr.wisdomcity.R;

import java.util.List;
import java.util.Map;

/**
 * Created by czm on 2017/6/13 0013.
 */

public class NewsContentAdapter extends RecyclerView.Adapter<NewsContentAdapter.Holder> implements View.OnClickListener, View.OnFocusChangeListener{

    private static final String TAG = "RecyclerAdapter";

    private Context context;
    List<Map<String, String>> map;

    public NewsContentAdapter(Context context, List<Map<String, String>> map) {
        this.context = context;
        this.map = map;
    }


    @Override
    public NewsContentAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.newsitem, parent, false);
        NewsContentAdapter.Holder vh = new NewsContentAdapter.Holder(view);
        return vh;
    }


    @Override
    public void onBindViewHolder(NewsContentAdapter.Holder holder, int position) {
        holder.title.setText(map.get(position).get("title"));
        holder.time.setText(map.get(position).get("time"));
        holder.mLayout.setTag(position);
        holder.mLayout.setOnClickListener(this);
        holder.mLayout.setOnFocusChangeListener(this);
    }


    @Override
    public int getItemCount() {
        return map.size();
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
            mOnItemSelectListener.onItemSelected(v, (Integer) v.getTag());
        }
    }


    class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        MyItemClickListener mListener;
        RelativeLayout mLayout;
        TextView title, time;

        public Holder(final View view) {
            super(view);
            mLayout = (RelativeLayout) view.findViewById(R.id.newbody);
            title = (TextView) view.findViewById(R.id.messagText);
            time = (TextView) view.findViewById(R.id.timeText);
            view.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.onItemClick(v, getPosition());
            }
        }
    }

    private OnItemClickListener mOnItemClickListener = null;
    private OnItemSelectedListener mOnItemSelectListener = null;

    public static interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public static interface OnItemSelectedListener {
        void onItemSelected(View view, int position);
    }


    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public void setOnItemSelectedListener(OnItemSelectedListener listener) {
        this.mOnItemSelectListener = listener;
    }


}