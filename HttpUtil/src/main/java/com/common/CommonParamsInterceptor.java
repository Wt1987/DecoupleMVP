package com.common;

import java.io.IOException;
import java.util.Map;

import okhttp3.FormBody;
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
            HttpUrl url = request.url();
            HttpUrl.Builder mBuilder = url.newBuilder();
            for (Map.Entry<String, String> entry : commonParamsMap.entrySet()) {
                mBuilder.addEncodedQueryParameter(entry.getKey(),entry.getValue());
            }
            HttpUrl mewUrl = mBuilder.build();
            request = request.newBuilder().url(mewUrl).build();

        }else if (request.body() instanceof FormBody) {
            FormBody.Builder builder = new FormBody.Builder();
            FormBody body = (FormBody) request.body();
            //将以前的参数添加
            for (int i = 0; i < body.size(); i++) {
                builder.add(body.encodedName(i), body.encodedValue(i));
            }
            //追加新的参数
            for (Map.Entry<String, String> entry : commonParamsMap.entrySet()) {
                builder.add(entry.getKey(),entry.getValue());
            }
            //构造新的请求体
            request = request.newBuilder().post(builder.build()).build();

        }
        return chain.proceed(request);
    }
}
