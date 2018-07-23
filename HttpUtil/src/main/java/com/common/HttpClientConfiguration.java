package com.common;

import org.apache.http.conn.ssl.SSLSocketFactory;

import java.util.Map;

/**
 * author : taowang
 * date :2018/7/23
 * description:
 **/
public class HttpClientConfiguration {
    private int connectTimeout;
    private int responseTimeout;
    private int retry;
    private int retryTimeout;
    private int maxConnections;
    private Map<String, String> headers;
    private boolean isUseLoger;





}
