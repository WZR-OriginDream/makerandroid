package com.lipiao.makerandroid.Utils;

import com.lipiao.makerandroid.Service.UserService;
import com.lipiao.makerandroid.Service.WanAndroidService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 网络请求工具类
 * 基于单例模式-使用饿汉式
 * 网络请求库:Retrofit
 * 服务文件：WanAndroidService.java
 * app中所有网络请求的baseURL:https://www.wanandroid.com/
 */
public class HttpUtil {

    String TAG = "HttpUtil";
    private static HttpUtil httpUtil ;
    private static WanAndroidService wanAndroidService;
    private static UserService userService;
    private static Retrofit retrofitWanAndroid;//WanAndroidService对应的Retrofit实体类
    private static Retrofit retrofitUser;//userService对应的Retrofit实体类

    private HttpUtil() {
        LogUtil.d(TAG, "验证是否单例生效,app使用过程中HttpUtil只初始化一次即可");

        //初始化WanAndroidService对应的Retrofit实体类
        retrofitWanAndroid = new Retrofit.Builder()
                .baseUrl("https://www.wanandroid.com/")
                .build();
        wanAndroidService = retrofitWanAndroid.create(WanAndroidService.class);

        //初始化userService对应的Retrofit实体类
        retrofitUser = new Retrofit.Builder()
                .baseUrl("http://101.201.238.193:8081/user/")
                .addConverterFactory(GsonConverterFactory.create()) // 设置数据解析器
                .build();
        userService = retrofitUser.create(UserService.class);

    }

    public static HttpUtil getInstance() {
        if (httpUtil!=null){
            return httpUtil;
        }else {
            return new HttpUtil();
        }
    }

    //对外提供 wanAndroidService服务的 get方法 HttpUtil.getWanAndroidService()即可
    public static WanAndroidService getWanAndroidService() {
        return wanAndroidService;
    }

    //对外提供 UserService服务的 get方法 HttpUtil.getUserService()即可
    public static UserService getUserService() {
        return userService;
    }
}
