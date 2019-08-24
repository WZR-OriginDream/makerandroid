package com.lipiao.makerandroid.Service;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface WeatherService {
    @GET("?city={location}")
    Call<ResponseBody> getWeather(@Path("location") String location);
}
