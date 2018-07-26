package com.junor;

import android.app.Application;

import com.util.HttpClientConfiguration;
import com.util.HttpDirector;

import java.util.HashMap;
import java.util.Map;

/**
 * author : taowang
 * date :2018/7/25
 * description:
 **/
public class StudyApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        initHttp();
    }

    private void initHttp(){

        Map<String, String> headers = new HashMap<>();
        headers.put("test","test1");
        Map<String, String> map2 = new HashMap<>();
        map2.put("mpa","mpatest1");
        HttpClientConfiguration mConfig = new HttpClientConfiguration.Builder()
                .connectTimeout(30)
                .isUseLoger(true)
                .responseTimeout(30)
                .commonParamsInterceptor(map2)
                .headers(headers)
                .baseUrl("http://api.1196u.cn/")
                .retry(8).build();
        HttpDirector.getInstance().initRetrofitFormConfig(mConfig);
    }
}
