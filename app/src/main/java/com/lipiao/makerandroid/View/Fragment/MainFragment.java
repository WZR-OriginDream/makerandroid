package com.lipiao.makerandroid.View.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.lipiao.makerandroid.Base.LazyLoadFragment;
import com.lipiao.makerandroid.Bean.ArticleBean;
import com.lipiao.makerandroid.Bean.BannerBean;
import com.lipiao.makerandroid.Bean.ProjectCategoryBean;
import com.lipiao.makerandroid.R;
import com.lipiao.makerandroid.Utils.GlideImageLoader;
import com.lipiao.makerandroid.Utils.HttpUtil;
import com.lipiao.makerandroid.Utils.LogUtil;
import com.lipiao.makerandroid.View.Adapter.TopArticleAdapter;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//原始的四个碎片自带缓存 无需懒加载 也不会重复加载数据
public class MainFragment extends Fragment {

    String TAG = "MainFragment";
    View rootView;

    //碎片中使用butterknife略有不同
    private Unbinder unbinder;

    @BindView(R.id.banner)
    Banner banner;

    ArrayList<String> images = new ArrayList<String>();//图片资源集合
    ArrayList<String> titles = new ArrayList<String>();//标题


    //项目碎片bean类集合 项目类型
    private List<BannerBean.DataBean> bannerDataBeanList;
    AlertDialog alertDialog;//模拟加载数据

    //top article
    static TopArticleAdapter topArticleAdapter;
    static List<ArticleBean> mList = new ArrayList<>();
    @BindView(R.id.rv_top_article)
    RecyclerView mRecyclerView;

    public MainFragment() {
        // Required empty public constructor
    }

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_main, container, false);
        //返回一个Unbinder值（进行解绑），注意这里的this不能使用getActivity()
        unbinder = ButterKnife.bind(this, rootView);
        initData();

        return rootView;
    }

    private void initData() {
        //文章所需数据 mList
        ArticleBean articleBean1 = new ArticleBean("星蔚", "Android基础-四大组件之Service（基础）", "2019年07月11日", "四大组件");
        ArticleBean articleBean2 = new ArticleBean("星蔚", "Android基础-四大组件之activity（基础）", "2019年07月11日", "四大组件");
        mList.add(articleBean1);
        mList.add(articleBean2);
        alertDialog = new AlertDialog.Builder(Objects.requireNonNull(getActivity()))
                .setMessage("获取项目数据中...")
                .setTitle("Maker——IoT").create();
        alertDialog.show();
        LogUtil.d(TAG, "initData 验证是否重复加载碎片所需数据");
        //banner所需数据images titles

        //接口参数 page cid
        Call<ResponseBody> call = HttpUtil.getWanAndroidService().getBannerData();
        //网络请求
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String strBack = response.body().string();
                    //处理数据
                    JSONObject jsonObject = null;
                    BannerBean bannerBean;
                    jsonObject = new JSONObject(strBack);
                    Gson gson = new Gson();
                    bannerBean = gson.fromJson(jsonObject.toString(), BannerBean.class);

                    bannerDataBeanList = bannerBean.getData();
                    //初始化images titles
                    for (int i=0;i<bannerDataBeanList.size();i++){
                        images.add(bannerDataBeanList.get(i).getImagePath());
                        titles.add(bannerDataBeanList.get(i).getTitle());
                    }
                    //获取完数据后UI操作
                    initView();
                    //主线程将alertDialog提示隐藏
                    Objects.requireNonNull(getActivity()).runOnUiThread(() -> alertDialog.hide());
                } catch (IOException | JSONException e) {
                    Log.d(TAG, "初始化项目数据成功");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(TAG, "初始化项目数据失败");
            }
        });

    }

    private void initView() {
        banner.setImages(images).setImageLoader(new GlideImageLoader());
        //设置banner样式 显示圆形指示器和标题（水平显示
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        //设置标题集合（当banner样式有显示title时）
        banner.setBannerTitles(titles);
        //设置轮播时间
        banner.setDelayTime(3000);
        banner.start();

        //top rv
        mRecyclerView.setHasFixedSize(true);
        //平常的水平一个item布局的流
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

//初始化mList
//        initArticleBeanList();
//实例化MyAdapter并传入mList对象
        topArticleAdapter = new TopArticleAdapter(mList);
//为RecyclerView对象mRecyclerView设置adapter
        mRecyclerView.setAdapter(topArticleAdapter);
    }

    /**
     * onDestroyView中进行解绑操作
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    //优化图片轮播库体验
    @Override
    public void onStart() {
        super.onStart();
        //开始轮播
        banner.startAutoPlay();

    }

    @Override
    public void onStop() {
        super.onStop();
        //结束轮播
        banner.stopAutoPlay();
    }

}
