package com.junor.mvp.contract;


import com.junor.bean.TestResponse;

import mvp.iview.IBaseView;
import mvp.present.IBasePresenter;

/**
 * author : taowang
 * date :2018/7/25
 * description:
 **/
public class TestContract {

    public interface LoginView extends IBaseView {

        void loginSuccess(TestResponse data);

        void loginFail(String data);
    }

    public interface IMainPresenter extends IBasePresenter<LoginView> {
        //将一些逻辑处理的方法在此声明
        void login();

    }


    //这是Model的数据获取接口
    public interface onGetData {
        void onSuccess(TestResponse data);

        void onFail(String error);
    }


}
