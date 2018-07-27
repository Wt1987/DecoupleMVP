package com.junor.http;

import com.common.bean.BaseResultEntity;
import com.junor.bean.TestResponse;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * author : taowang
 * date :2018/7/25
 * description:
 **/
public interface TestHttpApiService {

    /**
     * @return
     */
    @POST("api/account/login")
    @FormUrlEncoded
    Observable<BaseResultEntity<TestResponse>> login(@Field("p") String type
            , @Field("data") String data
            , @Field("v") String version
            , @Field("token") String token
            , @Field("reqId") int id);


}
