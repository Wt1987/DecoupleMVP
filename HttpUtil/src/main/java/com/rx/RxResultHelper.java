package com.rx;

import com.common.bean.BaseResultEntity;
import com.util.UrlConstants;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.functions.Function;

/**
 * author : taowang
 * date :2018/7/27
 * description:
 **/
public class RxResultHelper {

    public static <T> ObservableTransformer<BaseResultEntity<T>, T> handleResult() {
        return new ObservableTransformer<BaseResultEntity<T>, T>() {
            @Override
            public Observable<T> apply(Observable<BaseResultEntity<T>> tObservable) {
                return tObservable.flatMap(new Function<BaseResultEntity<T>, ObservableSource<T>>() {

                    @Override
                    public ObservableSource<T> apply(BaseResultEntity<T> resultEntity) {

                        if (resultEntity.status == UrlConstants.SUCCESS) {
                            //请求成功
                            return Observable.just(resultEntity.resultMap);

                        } else if (resultEntity.status == UrlConstants.SIGN_OUT) {

                            //添加重登录的处理，需要和服务器协商
                            return Observable.error(new Exception(resultEntity.msg));

                        } else {

                            return Observable.error(new Exception(resultEntity.msg));

                        }

                    }
                });
            }
        };
    }


}
