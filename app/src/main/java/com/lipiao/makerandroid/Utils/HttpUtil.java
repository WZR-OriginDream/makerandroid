package com.lipiao.makerandroid.Utils;

import com.lipiao.makerandroid.Service.WanAndroidService;

import retrofit2.Retrofit;

/**
 * 网络请求工具类
 * 基于单例模式-使用饿汉式
 * 网络请求库:Retrofit
 * 服务文件：WanAndroidService.java
 * app中所有网络请求的baseURL:https://www.wanandroid.com/
 */
public class HttpUtil {

    String TAG = "HttpUtil";
    private static HttpUtil httpUtil = new HttpUtil();
    private static WanAndroidService wanAndroidService;
    private static Retrofit retrofit;

    private HttpUtil() {
        LogUtil.d(TAG, "验证是否单例生效,app使用过程中HttpUtil只初始化一次即可");
        retrofit = new Retrofit.Builder()
                .baseUrl("https://www.wanandroid.com/")
                .build();
        wanAndroidService = retrofit.create(WanAndroidService.class);
    }

    public static HttpUtil getInstance() {
        return httpUtil;
    }

    //对外提供 wanAndroidService服务的 get方法 HttpUtil.getWanAndroidService()即可
    public static WanAndroidService getWanAndroidService() {
        return wanAndroidService;
    }


}
