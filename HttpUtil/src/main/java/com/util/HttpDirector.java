package com.util;

import android.text.TextUtils;

import com.common.intercept.HeaderIntercept;
import com.common.intercept.RetryIntercept;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * author : taowang
 * date :2018/7/25
 * description:
 **/
public class HttpDirector {


    private String baseUrl;
    private Retrofit retrofit;


    public static HttpDirector getInstance() {
        return Holder.instance;
    }

    private HttpDirector() {

    }

    private static class Holder {
        private static final HttpDirector instance = new HttpDirector();
    }


    private OkHttpClient getDefaultHttpClient() {
        return new OkHttpClient.Builder()
                .addNetworkInterceptor(new StethoInterceptor())
                .build();
    }


    public Retrofit getApiRetrofit() {
        return retrofit;
    }


    public Retrofit initRetrofitFormConfig(HttpClientConfiguration config) {

        this.baseUrl = config.baseUrl;
        retrofit = new Retrofit.Builder()
                .baseUrl(TextUtils.isEmpty(baseUrl) ? UrlConstants.API_SERVER_URL : baseUrl)
                .client(getHttpClientFromConfig(config))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())   // 新的配置
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                .build();
        return retrofit;
    }

    public <T> T createReq(Class<T> reqServer){
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(TextUtils.isEmpty(baseUrl) ? UrlConstants.API_SERVER_URL : baseUrl)
                    .client(getDefaultHttpClient())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())   // 新的配置
                    .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                    .build();
        }
        return retrofit.create(reqServer);
    }

    public <T> T createReq(Class<T> reqServer ,String baseUrl){
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(getDefaultHttpClient())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())   // 新的配置
                    .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                    .build();
        }
        return retrofit.create(reqServer);
    }

    private OkHttpClient getHttpClientFromConfig(HttpClientConfiguration config) {

        OkHttpClient.Builder mBuilder = new OkHttpClient.Builder();
        mBuilder.addNetworkInterceptor(new StethoInterceptor());

        if (config.connectTimeout > 0) {
            mBuilder.connectTimeout(config.connectTimeout, TimeUnit.SECONDS);
        }

        if (config.responseTimeout > 0) {
            mBuilder.readTimeout(config.responseTimeout, TimeUnit.SECONDS);
        }

        if (config.responseTimeout > 0) {
            mBuilder.writeTimeout(config.responseTimeout, TimeUnit.SECONDS);
        }

        if (config.retry > 0) {
            mBuilder.addInterceptor(new RetryIntercept(config.retry));
        }

        if (config.listInterceptor != null) {
            for(int i = 0 ; i < config.listInterceptor.size() ; i++){
                mBuilder.addInterceptor(config.listInterceptor.get(i));
            }
        }

        if (config.listNetworkInterceptor != null) {
            for(int i = 0 ; i < config.listNetworkInterceptor.size() ; i++){
                mBuilder.addNetworkInterceptor(config.listNetworkInterceptor.get(i));
            }
        }


        if (config.headers != null) {
            HeaderIntercept mHeaderIntercept = new HeaderIntercept(config.headers);
            mBuilder.addInterceptor(mHeaderIntercept);
        }
        if (config.isUseLogger) {
            HttpLoggingInterceptor mHttpLoggingInterceptor = new HttpLoggingInterceptor();
            mHttpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            mBuilder.addInterceptor(mHttpLoggingInterceptor);
        }

        if (config.maxConnections > 0) {
            OkHttpClient mOkHttpClient = mBuilder.build();
            mOkHttpClient.dispatcher().setMaxRequestsPerHost(config.maxConnections);
            return mOkHttpClient;
        } else {
            return mBuilder.build();
        }


    }


    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }
}
