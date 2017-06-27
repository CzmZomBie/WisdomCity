package com.zhny.gr.wisdomcity.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;


import com.zhny.gr.wisdomcity.R;
import com.zhny.gr.wisdomcity.activity.activityhome.BaseActivity;

import java.util.List;

/**
 * Created by czm on 2017/6/13 0013.
 */

public class PicAdapter extends RecyclerView.Adapter<PicAdapter.Holder> implements View.OnClickListener, View.OnFocusChangeListener {


    private Context context;
    List<String> pics;

    public PicAdapter(Context context, List<String> pics) {
        this.context = context;
        this.pics = pics;
    }

    @Override
    public PicAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        PicAdapter.Holder holder = new PicAdapter.Holder(LayoutInflater.from(context).inflate(R.layout.sceneryitem, parent,
                false));
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.photorectcleritem, parent, false);
        PicAdapter.Holder vh = new PicAdapter.Holder(view);
        return vh;
    }


    @Override
    public void onBindViewHolder(PicAdapter.Holder holder, int position) {
        BaseActivity.XImage(holder.sceneryImage, pics.get(position));
        holder.mLayout.setTag(position);
        holder.mLayout.setOnClickListener(this);
        holder.mLayout.setOnFocusChangeListener(this);
    }

    @Override
    public int getItemCount() {
        return pics.size();
    }


    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
//        注意这里使用getTag方法获取position
            mOnItemClickListener.onItemClick(v, (int) v.getTag());
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus && mOnItemSelectListener != null) {
//        注意这里使用getTag方法获取position
            mOnItemSelectListener.onItemSelected(v, (int) v.getTag());
        }
    }


    class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        MyItemClickListener mListener;
        ImageView sceneryImage;
        RelativeLayout mLayout;

        public Holder(final View view) {
            super(view);
            mLayout = (RelativeLayout) view.findViewById(R.id.layout);
            sceneryImage = (ImageView) view.findViewById(R.id.photoitem);
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