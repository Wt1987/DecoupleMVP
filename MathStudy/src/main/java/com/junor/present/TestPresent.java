package com.junor.present;


import com.junor.bean.TestResponse;
import com.junor.mvp.contract.TestContract;
import com.junor.mvp.model.TestModel;

import mvp.present.BasePresenterImpl;

/**
 * author : taowang
 * date :2018/7/25
 * description:
 **/
public class TestPresent extends BasePresenterImpl<TestContract.LoginView>
        implements TestContract.IMainPresenter, TestContract.onGetData {



    private TestContract.LoginView mLoginView;
    private TestModel model=new TestModel();


    @Override
    public void login() {

        mLoginView = getView();
        model.setListener(this);
        //getLifecycleProvider()不能在初始化present时调用，在任何方法内调用都是可以的

        model.setmLifecycleProvider(getLifecycleProvider());
        model.loginIn();
    }



    @Override
    public void onSuccess(TestResponse data) {
        mLoginView.loginSuccess(data);
    }

    @Override
    public void onFail(String error) {
        mLoginView.loginFail(error);
    }

}
