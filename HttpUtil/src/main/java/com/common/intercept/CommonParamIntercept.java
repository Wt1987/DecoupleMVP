package com.common.intercept;

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
public class CommonParamIntercept implements Interceptor {

    public Map<String, String> commonParam;

    public CommonParamIntercept(Map<String, String> commonParam) {
        this.commonParam = commonParam;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();

        HttpUrl.Builder modifiedUrlBuilder = request.url().newBuilder();
        HttpUrl modifiedUrl ;
        String method = request.method();
        if ("GET".equals(method)) {

            for (Map.Entry<String, String> entry : commonParam.entrySet()) {

                modifiedUrlBuilder.addQueryParameter(entry.getKey(), entry.getValue());
            }
            modifiedUrl = modifiedUrlBuilder.build();
            return chain.proceed(request.newBuilder().url(modifiedUrl).build());

        } else {
            if (request.body() instanceof FormBody) {
                FormBody.Builder bodyBuilder = new FormBody.Builder();

                FormBody formBody = (FormBody) request.body();

                for (int i = 0; i < formBody.size(); i++) {
                    bodyBuilder.addEncoded(formBody.encodedName(i), formBody.encodedValue(i));
                }

                for (Map.Entry<String, String> entry : commonParam.entrySet()) {

                    bodyBuilder.addEncoded(entry.getKey(), entry.getValue());
                }
                request = request.newBuilder().post(formBody).build();
            }

        }
        return chain.proceed(request);
    }
}