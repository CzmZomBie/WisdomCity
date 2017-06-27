package com.zhny.gr.wisdomcity.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;


import com.zhny.gr.wisdomcity.R;

import org.xutils.image.ImageOptions;
import org.xutils.x;

/**
 * Created by czm on 2017/5/15.
 */

public abstract class BaseFragment_v4 extends Fragment {

    public  final String TAG = this.getClass().getSimpleName();

    public final void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(getContext(), clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    public void startActivity(Class<?> clz) {
        startActivity(new Intent().setClass(getContext(), clz));
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach: ");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");
        return x.view().inject(this, inflater, container);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated: ");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView: ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach: ");
    }


    private static Toast toast;

    public void showToast(String message) {
        if (toast == null) {
            toast = Toast.makeText(getContext(), message, Toast.LENGTH_SHORT);
        } else {
            toast.setText(message);
        }
        toast.show();
    }

    public void showToast(int res) {
        if (toast == null) {
            toast = Toast.makeText(getContext(), getString(res), Toast.LENGTH_SHORT);
        } else {
            toast.setText(getString(res));
        }
        toast.show();
    }

    public void showLongToast(int res) {
        Toast.makeText(getContext(), getString(res), Toast.LENGTH_SHORT).show();
    }

    public void showLongToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    static ImageOptions imageOptions = null;

    public void XImage(ImageView imagem, String src) {
        if (imagem == null) {
            return;
        }
        if (null == imageOptions) {
            imageOptions = new ImageOptions.Builder()
                    .setFailureDrawableId(R.drawable.icon_error)
                    .build();
        }
        x.image().bind(imagem, src, imageOptions);
    }

    public void XImage(ImageView imagem, String src, int load, int failur) {
        if (null == imageOptions) {
            imageOptions = new ImageOptions.Builder()
                    .setLoadingDrawableId(load)
                    .setFailureDrawableId(failur)
                    .build();
        }
        x.image().bind(imagem, src, imageOptions);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            Log.d(TAG, "setUserVisibleHint: true");
        } else {
            Log.d(TAG, "setUserVisibleHint: false");
        }
        loadData(isVisibleToUser);
    }

    protected abstract void loadData(boolean isVisibleToUser);


    private static ILoadCallBack cback;

    public void setCallBack(ILoadCallBack cb) {
        this.cback = cb;
    }

    public static void onCallBack(boolean isLoading) {
        if (cback == null) {
            return;
        }
        if (isLoading) {
            cback.executeStartRequest();
        } else {
            cback.executeFinishRequest();
        }
    }

}
