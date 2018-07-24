package com.util;

import android.text.TextUtils;

import com.common.CommonParamsInterceptor;
import com.common.HeaderIntercept;
import com.common.RetryIntercept;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.GsonBuilder;
import com.http.IBaseApiService;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * author : taowang
 * date :2018/7/23
 * description:
 **/
public class HttpDirector {

    protected int connectTimeout;
    protected int responseTimeout;
    protected int retry;
    protected int maxConnections;
    protected Map<String, String> headers;
    private boolean isUseLogger;
    protected Map<String, String> commonParamsMap;

    private String baseUrl;

    protected static Retrofit retrofit;

    public void HttpDirector(){

    }


    private HttpDirector(Builder b) {
        if (b.connectTimeout > 0) {
            connectTimeout = b.connectTimeout;
        }

        if (b.responseTimeout > 0) {
            responseTimeout = b.responseTimeout;
        }

        if (b.retry > 0) {
            retry = b.retry;
        }
        if (b.maxConnections > 0) {
            maxConnections = b.maxConnections;
        }
        if (b.headers != null) {
            headers = b.headers;
        }
        if (TextUtils.isEmpty(b.baseUrl)) {
            baseUrl = b.baseUrl;
        }
        if (b.commonParamsMap != null) {
            commonParamsMap = b.commonParamsMap;
        }
        if (isUseLogger) {
            isUseLogger = b.isUseLogger;
        }

        retrofit = new Retrofit.Builder()
                .baseUrl(TextUtils.isEmpty(b.baseUrl) ? UrlConstants.API_SERVER_URL : baseUrl)
                .client(getHttpClientFromBuilder(b))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())   // 新的配置
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                .build();


    }

    private HttpDirector() {

        retrofit = new Retrofit.Builder()
                .baseUrl(UrlConstants.API_SERVER_URL)
                .client(getDefaultHttpClient())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())   // 新的配置
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                .build();

    }


    public static HttpDirector getInstance() {
        return Holder.instance;
    }



    private static class Holder {
        private static final HttpDirector instance = new HttpDirector();
    }


    public  Retrofit initHttpClient(Builder b) {
        if (b.connectTimeout > 0) {
            connectTimeout = b.connectTimeout;
        }

        if (b.responseTimeout > 0) {
            responseTimeout = b.responseTimeout;
        }

        if (b.retry > 0) {
            retry = b.retry;
        }
        if (b.maxConnections > 0) {
            maxConnections = b.maxConnections;
        }
        if (b.headers != null) {
            headers = b.headers;
        }
        if (b.commonParamsMap != null) {
            commonParamsMap = b.commonParamsMap;
        }
        if (isUseLogger) {
            isUseLogger = b.isUseLogger;
        }

        retrofit = new Retrofit.Builder()
                .baseUrl(UrlConstants.API_SERVER_URL)
                .client(getHttpClientFromBuilder(b))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())   // 新的配置
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                .build();
        return retrofit;
    }



    public Retrofit getApiRetrofit(){
        return  retrofit;
    }

    private OkHttpClient getDefaultHttpClient() {
        return new OkHttpClient.Builder()
                .addNetworkInterceptor(new StethoInterceptor())
                .build();
    }

    private OkHttpClient getHttpClientFromBuilder(Builder b) {

        OkHttpClient.Builder mBuilder = new OkHttpClient.Builder();
        mBuilder.addNetworkInterceptor(new StethoInterceptor());

        if (b.connectTimeout > 0) {
            mBuilder.connectTimeout(b.connectTimeout, TimeUnit.SECONDS);
        }

        if (b.responseTimeout > 0) {
            mBuilder.readTimeout(b.responseTimeout, TimeUnit.SECONDS);
        }

        if (b.responseTimeout > 0) {
            mBuilder.writeTimeout(b.responseTimeout, TimeUnit.SECONDS);
        }

        if (b.retry > 0) {
            mBuilder.addInterceptor(new RetryIntercept(b.retry));
        }

        if (b.commonParamsMap != null) {
            commonParamsMap = b.commonParamsMap;
            CommonParamsInterceptor mHeadIntercept = new CommonParamsInterceptor(commonParamsMap);
            mBuilder.addInterceptor(mHeadIntercept);
        }
        if (b.headers != null) {
            headers = b.headers;
            HeaderIntercept mHeaderIntercept = new HeaderIntercept(headers);
            mBuilder.addInterceptor(mHeaderIntercept);
        }
        if (isUseLogger) {
            HttpLoggingInterceptor mHttpLoggingInterceptor = new HttpLoggingInterceptor();
            mHttpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            mBuilder.addInterceptor(mHttpLoggingInterceptor);
        }

        if (b.maxConnections > 0) {
            OkHttpClient mOkHttpClient = mBuilder.build();
            mOkHttpClient.dispatcher().setMaxRequestsPerHost(b.maxConnections);
            return mOkHttpClient;
        } else {
            return mBuilder.build();
        }


    }

    /**
     * 构造者网络请求
     */
    public static class Builder {
        private int connectTimeout;
        private int responseTimeout;
        private int retry;
        private int maxConnections;
        private Map<String, String> headers;
        private boolean isUseLogger;
        private String baseUrl;
        private Map<String, String> commonParamsMap;

        public Builder connectTimeout(int connectTimeout) {

            this.connectTimeout = connectTimeout;
            return this;
        }
        public Builder commonParamsMap(Map<String, String> commonParamsMap) {

            this.commonParamsMap = commonParamsMap;
            return this;
        }

        public Builder responseTimeout(int responseTimeout) {

            this.responseTimeout = responseTimeout;
            return this;
        }

        public Builder retry(int retry) {

            this.retry = retry;
            return this;
        }

        public Builder baseUrl(String baseUrl) {

            this.baseUrl = baseUrl;
            return this;
        }
        public Builder maxConnections(int maxConnections) {

            this.maxConnections = maxConnections;
            return this;
        }

        public Builder headers(Map<String, String> headers) {

            this.headers = headers;
            return this;
        }

        public Builder isUseLoger(boolean isUseLoger) {

            this.isUseLogger = isUseLoger;
            return this;
        }

        public HttpDirector builder() {
            return new HttpDirector(this);
        }

    }


}
