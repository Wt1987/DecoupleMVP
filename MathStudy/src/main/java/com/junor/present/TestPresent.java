package com.junor.present;


import android.content.Context;

import com.junor.bean.LoginResponse;
import com.junor.mvp.contract.LoginContract;
import com.junor.mvp.model.LoginModel;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import mvp.present.BasePresenterImpl;

/**
 * author : taowang
 * date :2018/7/21
 * description:
 **/
public class TestPresent extends BasePresenterImpl<LoginContract.LoginView>
        implements LoginContract.IMainPresenter, LoginContract.onGetData {


    private LoginContract.LoginView mLoginView;
    private RxAppCompatActivity mRxAppCompatActivity;
    private LoginModel model=new LoginModel();

    public TestPresent(RxAppCompatActivity mRxAppCompatActivity){

        this.mRxAppCompatActivity = mRxAppCompatActivity;
    }


    @Override
    public void login() {

        mLoginView = getView();
        model.setListener(this);
        model.loginIn();
    }



    @Override
    public void onSuccess(LoginResponse data) {
        mLoginView.loginSuccess(data);
    }

    @Override
    public void onFail(String error) {
        mLoginView.loginFail(error);
    }


}
