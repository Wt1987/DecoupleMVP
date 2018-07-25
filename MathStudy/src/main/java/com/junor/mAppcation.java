package com.junor;

import android.app.Application;

import com.util.HttpDirector;

/**
 * author : taowang
 * date :2018/7/24
 * description:
 **/
public class mAppcation extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        initHttp();
    }

    private void initHttp(){
        HttpDirector.Builder mBuilder = new HttpDirector.Builder()
                .connectTimeout(30)
                .isUseLoger(true)
                .responseTimeout(30)
                .retry(8);
        HttpDirector.getInstance().initHttpClient(mBuilder);
    }
}
