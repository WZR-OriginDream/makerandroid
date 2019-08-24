package com.lipiao.makerandroid.Service;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherService {

    /**
     * bug记录
     * d对于含有问号？的URL链接拼接 需要使用@Query注解
     * 不含问号？ 可用@Path
     *
     * @param city
     * @return
     */

    @GET("weatherApi")
    Call<ResponseBody> getWeather(@Query("city") String city);
}
