package com.junor.http;

import com.junor.bean.LoginResponse;
import com.junor.constants.UrlConstants;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * author : taowang
 * date :2018/7/23
 * description:
 **/
public interface LoginHttpApiService {

    /**
     * @return
     */
    @POST(UrlConstants.test_Login)
    @FormUrlEncoded
    Observable<LoginResponse> login(@Field("p") String type
            , @Field("data") String data
            , @Field("v") String version
            , @Field("token") String token
            , @Field("reqId") int id);


    /**
     * @return
     */
    @GET(UrlConstants.test_Login)
    Observable<LoginResponse> TEST(@Query("p") String type);
}
