package com.common;

/**
 * author : taowang
 * date :2018/7/23
 * description:
 **/
public interface HttpCallBack<T> {

    void onSuccess(T data);
    void onFailed(T data);
    void onCancle();
    void onError(Object data);
    void onError(String data);
}
