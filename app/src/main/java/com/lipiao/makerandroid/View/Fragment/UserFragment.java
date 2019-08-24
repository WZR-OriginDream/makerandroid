package com.lipiao.makerandroid.View.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.lipiao.makerandroid.Bean.WeatherBean;
import com.lipiao.makerandroid.R;
import com.lipiao.makerandroid.Service.WeatherService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

//试一下Retrofit的使用 以GET请求天气接口为例
public class UserFragment extends Fragment {
    View rootView;
    String TAG = "UserFragment";
    //碎片中使用butterknife略有不同
    private Unbinder unbinder;

    public UserFragment() {
        // Required empty public constructor
    }

    public static UserFragment newInstance() {
        return new UserFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_userr, container, false);
        //返回一个Unbinder值（进行解绑），注意这里的this不能使用getActivity()
        unbinder = ButterKnife.bind(this, rootView);
        demoForRetrofit();
        return rootView;
    }

    private void demoForRetrofit() {
        //初始化网络请求
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.apiopen.top/")
                .build();
        WeatherService weatherService = retrofit.create(WeatherService.class);
        //接口参数
        String strCity = "长沙";
        Call<ResponseBody> call = weatherService.getWeather(strCity);

        //网络请求
        call.enqueue(new Callback<ResponseBody>() {
            //网络请求成功时调用
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String strBack = response.body().string();
                    Log.d(TAG, "onFailure: 请求成功\n" + "返回的数据如下\n" + strBack);
                    //处理数据
                    JSONObject jsonObject = null;
                    WeatherBean weatherBean;
                    jsonObject = new JSONObject(strBack);
                    Gson gson = new Gson();
                    weatherBean = gson.fromJson(jsonObject.toString(), WeatherBean.class);
                    /**获取里面的数据
                     * 打印出来试试 看是否操作成功
                     * 以获取近五天的天气为例
                     */

                    for (int i = 0; i < 5; i++) {
                        Log.d(TAG, "onResponse: " +
                                "日期" + weatherBean.getData().getForecast().get(i).getDate() +
                                "天气：" + weatherBean.getData().getForecast().get(i).getType() + "\n" +
                                "");
                    }

                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }

            //网络请求失败时调用
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(TAG, "onFailure: 请求失败");
            }
        });
    }

    /**
     * onDestroyView中进行解绑操作
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}