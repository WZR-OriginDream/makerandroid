package com.lipiao.makerandroid.View.Fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.AgentWebConfig;
import com.just.agentweb.DefaultWebClient;
import com.lipiao.makerandroid.Bean.WeatherBean;
import com.lipiao.makerandroid.R;
import com.lipiao.makerandroid.Service.WeatherService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import butterknife.BindView;
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

    AgentWeb mAgentWeb;
    @BindView(R.id.ll_agent_web)
    LinearLayout linearLayout;


    public UserFragment() {
        // Required empty public constructor
    }

    public static UserFragment newInstance() {
        return new UserFragment();
    }

    //使用agent web
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        mAgentWeb = AgentWeb.with(this)
//                .setAgentWebParent(linearLayout, new LinearLayout.LayoutParams(-1, -1))
//                .useDefaultIndicator()
//                .createAgentWeb()
//                .ready()
//                .go("http://www.jd.com");
        mAgentWeb = AgentWeb.with(this)//
                .setAgentWebParent((LinearLayout) view, -1, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))//传入AgentWeb的父控件。
                .useDefaultIndicator(-1, 3)//设置进度条颜色与高度，-1为默认值，高度为2，单位为dp。
                .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK) //严格模式 Android 4.2.2 以下会放弃注入对象 ，使用AgentWebView没影响。
                .setMainFrameErrorView(R.layout.agentweb_error_page, -1) //参数1是错误显示的布局，参数2点击刷新控件ID -1表示点击整个布局都刷新， AgentWeb 3.0.0 加入。
                .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.ASK)//打开其他页面时，弹窗质询用户前往其他应用 AgentWeb 3.0.0 加入。
                .interceptUnkownUrl() //拦截找不到相关页面的Url AgentWeb 3.0.0 加入。
                .createAgentWeb()//创建AgentWeb。
                .ready()//设置 WebSettings。
                .go("https://blog.csdn.net/qq_42391904"); //WebView载入该url地址的页面并显示。


        AgentWebConfig.debug();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_userr, container, false);
        //返回一个Unbinder值（进行解绑），注意这里的this不能使用getActivity()
        unbinder = ButterKnife.bind(this, rootView);
        //demoForRetrofit();
        //agentWeb();

        return rootView;
    }

    private void agentWeb() {


//        //activity
//        mAgentWeb = AgentWeb.with(this)
//                .setAgentWebParent((LinearLayout) rootView, new LinearLayout.LayoutParams(-1, -1))
//                .useDefaultIndicator()
//                .createAgentWeb()
//                .ready()
//                .go("http://www.jd.com");
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