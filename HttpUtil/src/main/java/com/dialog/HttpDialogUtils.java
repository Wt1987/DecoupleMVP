package com.dialog;

import android.app.Activity;
import android.text.TextUtils;

import math.junior.com.httputil.R;

/**
 * author : taowang
 * date :2018/7/27
 * description:
 **/
public class HttpDialogUtils {
    //  加载进度的dialog
    private CustomProgressDialog mProgressDialog;

    /**
     * 显示ProgressDialog
     */
    public void showProgress(Activity context, String msg) {
        if (context == null || context.isFinishing()) {
            return;
        }
        if (mProgressDialog == null) {
            mProgressDialog = new CustomProgressDialog.Builder(context)
                    .setTheme(R.style.ProgressDialogStyle)
                    .setMessage(TextUtils.isEmpty(msg) ? context.getString(R.string.loading):msg)
                    .build();
        }
        if (!mProgressDialog.isShowing()) {
            mProgressDialog.show();
        }
    }

    /**
     * 显示ProgressDialog
     */
    public void showProgress(Activity activity) {
        if (activity == null || activity.isFinishing()) {
            return;
        }
        if (mProgressDialog == null) {
            mProgressDialog = new CustomProgressDialog.Builder(activity)
                    .setTheme(R.style.ProgressDialogStyle)
                    .build();
        }
        if (!mProgressDialog.isShowing()) {
            mProgressDialog.show();
        }
    }

    /**
     * 取消ProgressDialog
     */
    public void dismissProgress() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }
}
