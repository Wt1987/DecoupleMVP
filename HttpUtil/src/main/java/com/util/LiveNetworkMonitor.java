package com.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * author : taowang
 * date :2018/7/27
 * description:
 **/
public class LiveNetworkMonitor {
    private final Context applicationContext;

    public LiveNetworkMonitor(Context context) {
        applicationContext = context.getApplicationContext();
    }

    public boolean isConnected() {
        ConnectivityManager cm =
                (ConnectivityManager) applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }

}
