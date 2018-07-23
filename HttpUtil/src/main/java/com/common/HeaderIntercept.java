package com.common;

import java.io.IOException;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * author : taowang
 * date :2018/7/23
 * description:
 **/
public class HeaderIntercept implements Interceptor {

    public Map<String, String> headers;

    public HeaderIntercept(Map<String, String> header) {
        this.headers = headers;
    }

    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {

        Request.Builder mBuilder = chain.request()
                .newBuilder();
        if(headers == null){
            return chain.proceed(mBuilder.build());
        }
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            mBuilder.addHeader(entry.getKey(),entry.getValue());
        }
        return chain.proceed(mBuilder.build());

    }
}