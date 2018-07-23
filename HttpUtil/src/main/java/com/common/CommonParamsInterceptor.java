package com.common;

import java.io.IOException;
import java.util.Map;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * author : taowang
 * date :2018/7/23
 * description:
 **/
public class CommonParamsInterceptor implements Interceptor {

    public Map<String, String> commonParamsMap;

    public CommonParamsInterceptor(Map<String, String> commonParamsMap) {
        this.commonParamsMap = commonParamsMap;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        //获取到request
        Request request = chain.request();
        //获取到方法
        String method = request.method();
        if ("GET".equals(method)) {
            HttpUrl httpUrl = request.url();
            String url = httpUrl.toString();
            int index = url.indexOf("?");
            if (index > 0) {
                for (Map.Entry<String, String> entry : commonParamsMap.entrySet()) {
                    url = url + "&" + entry.getKey() + "=" + entry.getValue() ;
                }
            } else {
                int tempStep = 0 ;
                for (Map.Entry<String, String> entry : commonParamsMap.entrySet()) {

                    if(tempStep == 0){
                        url = "?" + url + entry.getKey() + "=" + entry.getValue() ;
                        tempStep ++;
                    }
                    url = url + "&" + entry.getKey() + "=" + entry.getValue() ;

                }

            }

            request = request.newBuilder().url(url).build();
        }else if ("POST".equals(method)){

        }
        return chain.proceed(request);
    }
}
