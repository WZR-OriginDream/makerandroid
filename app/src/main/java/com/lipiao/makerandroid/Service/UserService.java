package com.lipiao.makerandroid.Service;

import com.lipiao.makerandroid.Bean.RespondBean.MessageBean;
import com.lipiao.makerandroid.Bean.RespondBean.UserCollectArticleBean;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface UserService {

    //表示提交表单数据，@Field注解键名
    //适用于数据量少的情况
    @POST("login")
    @FormUrlEncoded
    Call<MessageBean> login(@Field("userNumber") String userNumber, @Field("password") String password);

    @FormUrlEncoded
    @POST("register")
    Call<MessageBean> register(@Field("userNumber") String userNumber, @Field("password") String password);

    @FormUrlEncoded
    @POST("addArticle")
    Call<MessageBean> addArticle(@Field("userNumber") String userNumber, @Field("collectUrl") String collectUrl, @Field("collectTitles") String collectTitles);

    @FormUrlEncoded
    @POST("findAllArticle")
    Call<UserCollectArticleBean> findAllArticle(@Field("userNumber") String userNumber);

    @FormUrlEncoded
    @POST("deleteArticle")
    Call<MessageBean> deleteArticle(@Field("userNumber") String userNumber, @Field("collectUrl") String collectUrl);
}
