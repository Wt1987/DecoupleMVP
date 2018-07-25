package com.junor.activity;


import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.Menu;

import java.util.ArrayList;
import java.util.List;

import base.util.PermissionsResultListener;
import mvp.common.StudyRxActivity;
import mvp.iview.IBaseView;
import mvp.present.IBasePresenter;

/**
 * author : taowang
 * date :2018/7/23
 * description:
 **/
public abstract class BaseActivity<P extends IBasePresenter, V extends IBaseView> extends StudyRxActivity {

    //权限代码可以删除，如何在基础中添加公共方法，按项目需求改变
    protected List<String> mListPermissions = new ArrayList<>();
    protected PermissionsResultListener mPermissionsResultListener;
    protected int mRequestCode;

    protected void checkPermissions(String[] permissions,
                                    int requestCode, PermissionsResultListener listener) {
        //权限不能为空
        if (permissions != null || permissions.length != 0) {
            mPermissionsResultListener = listener;
            mRequestCode = requestCode;
            for (int i = 0; i < permissions.length; i++) {
                if (!isHavePermissions(permissions[i])) {
                    mListPermissions.add(permissions[i]);
                }
            }
            //遍历完后申请
            applyPermissions();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == mRequestCode) {
            if (grantResults.length > 0) {
                mPermissionsResultListener.onSuccessful(grantResults);
            } else {
                mPermissionsResultListener.onFailure();
            }
        }
    }

    //判断权限是否申请
    private boolean isHavePermissions(String permissions) {
        if (ContextCompat.checkSelfPermission(this, permissions) != PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        return true;
    }

    //申请权限
    private void applyPermissions() {
        if (!mListPermissions.isEmpty()) {
            int size = mListPermissions.size();
            ActivityCompat.requestPermissions(this, mListPermissions.toArray(new String[size]), mRequestCode);
        }
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }
}
