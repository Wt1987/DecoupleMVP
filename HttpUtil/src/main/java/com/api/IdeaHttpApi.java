package com.api;

import com.util.HttpDirector;

/**
 * author : taowang
 * date :2018/7/27
 * description:
 **/
public class IdeaHttpApi {

    public static <T> T getApiService(Class<T> cls) {
        return HttpDirector.getInstance().createReq(cls);
    }


    public static <T> T getDefaultApiService(Class<T> cls) {
        return HttpDirector.getInstance().createReq(cls);
    }


}
