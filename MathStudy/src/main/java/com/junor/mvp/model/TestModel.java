package com.junor.mvp.model;

import android.app.Activity;

import com.api.IdeaHttpApi;
import com.common.bean.BaseResultEntity;
import com.dialog.DefaultHttpUiShow;
import com.junor.bean.TestResponse;
import com.junor.http.TestHttpApiService;
import com.junor.mvp.contract.TestContract;
import com.rx.RxResultHelper;
import com.rx.RxResultSubscriber;
import com.rx.RxSchedulersHelper;
import com.trello.rxlifecycle2.LifecycleProvider;

/**
 * author : taowang
 * date :2018/7/25
 * description:
 **/
public class TestModel {

    private TestContract.onGetData listener;

    private TestHttpApiService mHttpApiService ;

    private LifecycleProvider mLifecycleProvider ;

    private Activity mActivity;


    public void setmLifecycleProvider(LifecycleProvider mLifecycleProvider) {
        this.mLifecycleProvider = mLifecycleProvider;
    }

    public TestModel(){
        mHttpApiService = IdeaHttpApi.getApiService(TestHttpApiService.class);
    }

    public void setListener(TestContract.onGetData listener) {
        this.listener = listener;
    }

    public void setmActivity(Activity mActivity) {
        this.mActivity = mActivity;
    }

    @SuppressWarnings("unchecked")
    public void loginIn() {


        mHttpApiService
                .login("android","\t{\"loginAccount\":\"ceshi\",\"password\":\"123\"}"
                        ,"1","token\tU2R2UXVLbkdBRGpIT3ZhVUhxbnFMYVN4QUlj" +
                                "a05MMVNKbHQzZVJUaTBHWG56amZibkd4SDVEdGMwdUVDbGdRZFpxZXZKVnJL" +
                                "aWcyUUYyd3J5eG1DV0ElM0QlM0Q="
                        ,0)
                .compose(mLifecycleProvider.<TestResponse>bindToLifecycle())
                .compose(RxSchedulersHelper.<BaseResultEntity<TestResponse>>inMainCommand())
                .compose(RxResultHelper.<TestResponse>handleResult())
                .compose(DefaultHttpUiShow.<TestResponse>applyProgressBar(mActivity))
                .subscribe(new RxResultSubscriber<TestResponse>(){
                    @Override
                    public void _onNext(TestResponse testResponse) {
                        listener.onSuccess(testResponse);
                    }

                    @Override
                    public void _onError(String msg) {
                        listener.onFail(msg);
                    }

                });



    }
}
