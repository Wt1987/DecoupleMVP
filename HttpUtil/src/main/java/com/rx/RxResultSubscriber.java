package com.rx;


import com.exception.ReloginException;
import com.exception.ServiceException;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


/**
 * author : taowang
 * date :2018/7/27
 * description:
 **/
public abstract  class RxResultSubscriber<T> implements Observer<T> {

    public String msg;


    @Override
    public void onNext(T t) {
        _onNext(t);
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();

        if (e instanceof ServiceException) {
            // 服务器异常
            msg = e.getMessage();

        } else if (e instanceof ReloginException) {
            // 踢出登录

        } else if (e instanceof UnknownHostException) {
            msg = "没有网络...";
        } else if (e instanceof SocketTimeoutException) {
            msg = "请求超时...";
        } else {
            msg = "请求失败，请稍后重试...";
        }
        _onError(msg);

    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onSubscribe(Disposable d) {

    }
    public abstract void _onNext(T t);

    public abstract void _onError(String msg);
}
