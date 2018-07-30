package com.util;

import org.apache.http.conn.ssl.SSLSocketFactory;

import java.util.ArrayList;
import java.util.Map;

import okhttp3.Interceptor;

/**
 * author : taowang
 * date :2018/7/25
 * description:
 **/
public class HttpClientConfiguration {

    public int connectTimeout;
    public int responseTimeout;
    public int retry;
    public int maxConnections;
    public Map<String, String> headers;
    public Map<String, String> commonParams;
    public boolean isUseLogger;
    public String baseUrl;
    public ArrayList<Interceptor> listInterceptor;
    public ArrayList<Interceptor> listNetworkInterceptor;
    public SSLSocketFactory sslSocketFactory;
    /**
     * 构造者网络请求
     */
    public static class Builder {
        private int connectTimeout;
        private int responseTimeout;
        private int retry;
        private int maxConnections;
        private Map<String, String> headers;
        public Map<String, String> commonParams;
        private boolean isUseLogger;
        private String baseUrl;
        private ArrayList<Interceptor> listInterceptor;
        private ArrayList<Interceptor> listNetworkInterceptor;
        private SSLSocketFactory sslSocketFactory;

        public Builder listNetworkInterceptor(ArrayList<Interceptor> listNetworkInterceptor) {

            this.listNetworkInterceptor = listNetworkInterceptor;
            return this;
        }

        public Builder listInterceptor(ArrayList<Interceptor> listInterceptor) {

            this.listInterceptor = listInterceptor;
            return this;
        }

        public Builder connectTimeout(int connectTimeout) {

            this.connectTimeout = connectTimeout;
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

        public Builder commonParams(Map<String, String> commonParams) {

            this.commonParams = commonParams;
            return this;
        }
        public Builder isUseLoger(boolean isUseLoger) {

            this.isUseLogger = isUseLoger;
            return this;
        }

        public HttpClientConfiguration build() {
            return new HttpClientConfiguration(this);
        }

    }

    private HttpClientConfiguration(Builder builder) {
        this.connectTimeout = builder.connectTimeout;
        this.responseTimeout = builder.responseTimeout;
        this.retry = builder.retry;
        this.maxConnections = builder.maxConnections;
        this.headers = builder.headers;
        this.maxConnections = builder.maxConnections;
        this.isUseLogger = builder.isUseLogger;
        this.baseUrl = builder.baseUrl;
        this.listInterceptor = builder.listInterceptor;
        this.listNetworkInterceptor = builder.listNetworkInterceptor;
        this.sslSocketFactory = builder.sslSocketFactory;
        this.commonParams = builder.commonParams;
    }

}
