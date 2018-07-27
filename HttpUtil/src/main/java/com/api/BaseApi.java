package com.api;

import com.common.HttpOnNextListener;
import com.common.bean.BaseResultEntity;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.lang.ref.SoftReference;

import io.reactivex.functions.Function;

/**
 * author : taowang
 * date :2018/7/27
 * description:
 **/
public abstract class BaseApi<T> implements Function<BaseResultEntity<T>, T> {
    //rx生命周期管理
    private SoftReference<RxAppCompatActivity> rxAppCompatActivity;
    /*回调*/
    private SoftReference<HttpOnNextListener> listener;
    /*是否能取消加载框*/
    private boolean cancel;
    /*是否显示加载框*/
    private boolean showProgress;
    /*是否需要缓存处理*/
    private boolean cache;
    /*方法-如果需要缓存必须设置这个参数；不需要不用設置*/
    private String mothed;


    public SoftReference<RxAppCompatActivity> getRxAppCompatActivity() {
        return rxAppCompatActivity;
    }

    public void setRxAppCompatActivity(SoftReference<RxAppCompatActivity> rxAppCompatActivity) {
        this.rxAppCompatActivity = rxAppCompatActivity;
    }

    public SoftReference<HttpOnNextListener> getListener() {
        return listener;
    }

    public void setListener(SoftReference<HttpOnNextListener> listener) {
        this.listener = listener;
    }

    public boolean isCancel() {
        return cancel;
    }

    public void setCancel(boolean cancel) {
        this.cancel = cancel;
    }

    public boolean isShowProgress() {
        return showProgress;
    }

    public void setShowProgress(boolean showProgress) {
        this.showProgress = showProgress;
    }

    public boolean isCache() {
        return cache;
    }

    public void setCache(boolean cache) {
        this.cache = cache;
    }

    public String getMothed() {
        return mothed;
    }

    public void setMothed(String mothed) {
        this.mothed = mothed;
    }
}
