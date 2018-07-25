package com.junor.mvp.model;

import com.junor.bean.LoginResponse;
import com.junor.http.LoginHttpApiService;
import com.junor.mvp.contract.LoginContract;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.util.HttpDirector;


import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * author : taowang
 * date :2018/7/21
 * description:
 **/
public class LoginModel {

    private LoginContract.onGetData listener;

    private LoginHttpApiService mHttpApiService ;

    private LifecycleProvider mLifecycleProvider ;


    public void setmLifecycleProvider(LifecycleProvider mLifecycleProvider) {
        this.mLifecycleProvider = mLifecycleProvider;
    }

    public LoginModel(){
        mHttpApiService = HttpDirector.getInstance().getApiRetrofit().create(LoginHttpApiService.class);
    }

    public void setListener(LoginContract.onGetData listener) {
        this.listener = listener;
    }

    @SuppressWarnings("unchecked")
    public void loginIn() {

        mHttpApiService
                .login("android","\t{\"loginAccount\":\"ceshi\",\"password\":\"123\"}"
                        ,"1","token\tU2R2UXVLbkdBRGpIT3ZhVUhxbnFMYVN4QUlj" +
                                "a05MMVNKbHQzZVJUaTBHWG56amZibkd4SDVEdGMwdUVDbGdRZFpxZXZKVnJL" +
                                "aWcyUUYyd3J5eG1DV0ElM0QlM0Q="
                        ,0)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(mLifecycleProvider.<LoginResponse>bindToLifecycle())
                .subscribe(new Observer<LoginResponse>(){

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(LoginResponse loginResponse) {
                        listener.onSuccess(loginResponse);
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onFail(e.getLocalizedMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });







    }
}
