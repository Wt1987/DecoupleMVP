package com.junor.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.junor.bean.LoginResponse;
import com.junor.mvp.contract.LoginContract;
import com.junor.present.TestPresent;

import math.junior.com.juniormathstudy.R;
import mvp.common.StudyRxActivity;

public class TestActivity extends StudyRxActivity<TestPresent, LoginContract.LoginView>
        implements LoginContract.LoginView{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        findViewById(R.id.tv_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getPresenter().login();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * 创建的方法
     * 用于创建presenter
     *
     * @return
     */
    @Override
    protected TestPresent InitPresenter() {
        return new TestPresent();
    }

    /**
     * 获取布局id
     *
     * @return
     */
    @Override
    public int getLayoutId() {
        return R.layout.login;
    }

    @Override
    protected void initData() {

    }



    @Override
    public void loginSuccess(LoginResponse data) {

        showToast(data.resultMap.chineseName);
    }

    @Override
    public void loginFail(String data) {
        showToast(data);
    }

    @Override
    public void setPresenter() {

    }
}
