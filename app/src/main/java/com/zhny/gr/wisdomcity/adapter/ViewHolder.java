package com.zhny.gr.wisdomcity.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhny.gr.wisdomcity.R;

import org.xutils.image.ImageOptions;
import org.xutils.x;


/**
 * Created by boyliupan on 2016-01-27.
 */
public class ViewHolder {
    private SparseArray<View> mViews;
    private int mPosition;


    private View mConvertView;

    public View getConvertView() {
        return mConvertView;
    }

    public ViewHolder(Context context, ViewGroup parent, int layoutId, int position) {
        this.mPosition = position;
        this.mViews = new SparseArray<View>();
        mConvertView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        mConvertView.setTag(this);

    }

    /**
     * ViewHolder入口
     *
     * @param context
     * @param convertView
     * @param parent
     * @param layoutId
     * @param position
     * @return
     */
    public static ViewHolder get(Context context, View convertView, ViewGroup parent, int layoutId, int position) {
        if (convertView == null) {
            return new ViewHolder(context, parent, layoutId, position);
        } else {
            ViewHolder holder = (ViewHolder) convertView.getTag();
            holder.mPosition = position;
            return holder;
        }
    }

    /**
     * 通过ViewId获取控件
     *
     * @param viewId
     * @param <T>
     * @return
     */
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    /**
     * 设置TextView的值
     *
     * @param viewId
     * @param value
     * @return
     */
    public ViewHolder setText(int viewId, String value) {
        TextView textView = getView(viewId);
        textView.setText(value);
        return this;
    }

    public ViewHolder setTextSize(int viewId, int size) {
        TextView textView = getView(viewId);
        textView.setTextSize(size);
        return this;
    }

    public ViewHolder setImageResource(int viewId, int resId) {
        ImageView view = getView(viewId);
        view.setImageResource(resId);
        return this;
    }

    public ViewHolder setImageBitmap(int viewId, Bitmap bitmap) {
        ImageView view = getView(viewId);
        view.setImageBitmap(bitmap);
        return this;
    }


    public ViewHolder setAdapter(int viewId, BaseAdapter adapter) {
        AbsListView listView = getView(viewId);
        listView.setAdapter(adapter);
        return this;
    }

    public ViewHolder setTextColor(int viewId, int color) {
        TextView tv = getView(viewId);
        tv.setTextColor(color);
        return this;
    }

    public ViewHolder setVisivility(int viewId, int visivility) {
        View view = getView(viewId);
        view.setVisibility(visivility);
        return this;
    }

    public ViewHolder setClickable(int viewId, boolean clickable) {
        View view = getView(viewId);
        view.setClickable(clickable);
        return this;
    }

    public ViewHolder setBackground(int viewId, int resource) {
        View view = getView(viewId);
        view.setBackgroundResource(resource);
        return this;
    }
    static ImageOptions imageOptions = null;

    public ViewHolder loadImg(int viewId, Context context, String url) {
        ImageView view = getView(viewId);
        if (null == imageOptions) {
            imageOptions = new ImageOptions.Builder()
                    .setFailureDrawableId(R.drawable.loading_fail)
                    .setLoadingDrawableId(R.drawable.loading)
                    .build();
        }
        x.image().bind(view, url, imageOptions);
        return this;
    }

}
