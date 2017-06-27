package com.zhny.gr.wisdomcity.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhny.gr.wisdomcity.R;


/**
 * 创建日期：2016/10/27 0021 on 14:12
 * By:Czm Administrator
 */
public class DialogFactory {

    private static volatile DialogFactory instance;

    public static DialogFactory getInstance() {
        if (instance == null) {
            synchronized (DialogFactory.class) {
                if (instance == null) {
                    instance = new DialogFactory();
                }
            }
        }
        return instance;
    }

    public Dialog getLoadingDialog(Context context, int res) {
        LinearLayout linearLayout = (LinearLayout) (LayoutInflater.from(context).inflate(R.layout.dialog_loading, null)
                .findViewById(R.id.dialog_view));
        ((TextView) linearLayout.findViewById(R.id.tv_loading_dialog)).setText(res);
        Dialog dialog = new Dialog(context, R.style.dialog);
        dialog.setCancelable(true);
        dialog.setContentView(linearLayout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return dialog;
    }

}
