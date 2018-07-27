package com.api;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.common.HttpOnNextListener;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.lang.ref.SoftReference;

/**
 * author : taowang
 * date :2018/7/27
 * description:
 **/
public class DefaultHttpUtilApi<T> implements Subscriber<T> {

    /*是否弹框*/
    private boolean showProgress = true;
    /* 软引用回调接口*/
    private SoftReference<HttpOnNextListener> mSubscriberOnNextListener;
    /*软引用反正内存泄露*/
    private SoftReference<RxAppCompatActivity> mActivity;
    /*加载框可自己定义*/
    private ProgressDialog pd;
    /*请求数据*/
    private BaseApi api;


    @SuppressWarnings("unchecked")
    public DefaultHttpUtilApi(BaseApi api) {
        this.api = api;
        this.mSubscriberOnNextListener = api.getListener();
        this.mActivity = api.getRxAppCompatActivity();
        setShowProgress(api.isShowProgress());
        if (api.isShowProgress()) {
            initProgressDialog(api.isCancel());
        }
    }

    private boolean isShowProgress() {
        return showProgress;
    }

    private void setShowProgress(boolean showPorgress) {
        this.showProgress = showPorgress;
    }

    /**
     * 初始化加载框
     */
    private void initProgressDialog(boolean cancel) {
        Context context = mActivity.get();
        if (pd == null && context != null) {
            pd = new ProgressDialog(context);
            pd.setCancelable(cancel);
            if (cancel) {
                pd.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        dismissProgressDialog();
                    }
                });
            }
        }
    }

    /**
     * 显示加载框
     */
    private void showProgressDialog() {
        if (!isShowProgress()) return;
        Context context = mActivity.get();
        if (pd == null || context == null) return;
        if (!pd.isShowing()) {
            pd.show();
        }
    }


    /**
     * 隐藏
     */
    private void dismissProgressDialog() {
        if (!isShowProgress()) return;
        if (pd != null && pd.isShowing()) {
            pd.dismiss();
        }
    }


    @Override
    public void onSubscribe(Subscription s) {

    }

    @Override
    public void onNext(T t) {

    }

    @Override
    public void onError(Throwable t) {

    }

    @Override
    public void onComplete() {

    }
}