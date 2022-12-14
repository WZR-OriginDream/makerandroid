package com.lipiao.makerandroid.View.Fragment;

import android.content.Intent;
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
import com.lipiao.makerandroid.Bean.ViewBean.ArticleBean;
import com.lipiao.makerandroid.Bean.RespondBean.BannerBean;
import com.lipiao.makerandroid.Bean.RespondBean.TopArticleBean;
import com.lipiao.makerandroid.R;
import com.lipiao.makerandroid.Utils.DateUtil;
import com.lipiao.makerandroid.Utils.GlideImageLoader;
import com.lipiao.makerandroid.Utils.HttpUtil;
import com.lipiao.makerandroid.View.Activity.WebActivity;
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

    static String userNumber;//账号

    String TAG = "MainFragment";
    View rootView;

    //碎片中使用butterknife略有不同
    private Unbinder unbinder;

    @BindView(R.id.banner)
    Banner banner;

    ArrayList<String> images = new ArrayList<>();//图片资源集合
    ArrayList<String> titles = new ArrayList<>();//标题
    ArrayList<String> webURLs = new ArrayList<>();//web链接

    private List<BannerBean.DataBean> bannerDataBeanList;
    private List<TopArticleBean.DataBean> topDataBeanList;
    AlertDialog alertDialog;//模拟加载数据

    //top article
    static TopArticleAdapter topArticleAdapter;
    static List<ArticleBean> mList = new ArrayList<>();

    @BindView(R.id.rv_top_article)
    RecyclerView mRecyclerView;

    static final int BANNER = 1;
    static final int TOPARTICLE = 2;


    public MainFragment() {
        // Required empty public constructor
    }

    public static MainFragment newInstance(String username) {
        MainFragment fragment = new MainFragment();
        Bundle bundle = new Bundle();
        bundle.putString("userNumber", username);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_main, container, false);
        //返回一个Unbinder值（进行解绑），注意这里的this不能使用getActivity()
        unbinder = ButterKnife.bind(this, rootView);
        assert getArguments() != null;//判空处理
        userNumber = getArguments().getString("userNumber");//获取账号
        Log.d(TAG, "onCreateView: userNumber "+ userNumber);
        initData();

        return rootView;
    }

    private void initData() {
        alertDialog = new AlertDialog.Builder(Objects.requireNonNull(getActivity()))
                .setMessage("获取首页数据中...")
                .setTitle("Maker——IoT").create();
        alertDialog.show();
        //LogUtil.d(TAG, "initData 验证是否重复加载碎片所需数据");

        //banner所需数据images titles
        //接口参数 无
        Call<ResponseBody> callBanner = HttpUtil.getWanAndroidService().getBannerData();
        //网络请求
        callBanner.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String strBack = response.body().string();
                    //处理数据
                    JSONObject jsonObject = null;
                    jsonObject = new JSONObject(strBack);
                    Gson gson = new Gson();
                    BannerBean bannerBean;
                    bannerBean = gson.fromJson(jsonObject.toString(), BannerBean.class);

                    bannerDataBeanList = bannerBean.getData();
                    //初始化bannerSimpleBeanList
                    for (int i = 0; i < bannerDataBeanList.size(); i++) {
                        images.add(bannerDataBeanList.get(i).getImagePath());
                        titles.add(bannerDataBeanList.get(i).getTitle());
                        //统一成https
                        // webURLs.add(bannerDataBeanList.get(i).getUrl());
                        if (bannerDataBeanList.get(i).getUrl().contains("https")){
                            webURLs.add(bannerDataBeanList.get(i).getUrl());
                        }else {
                            String strhttp = bannerDataBeanList.get(i).getUrl();
                            //http修改为https webAgent需使用https 使用StringBuffer完成
                            //http://www.wanandroid.com/blog/show/2658 //第五个位置添加s
                            StringBuffer strhttps = new StringBuffer();
                            strhttps.append(strhttp).insert(4, "s");
                            webURLs.add(strhttps+"");
                        }
                        //LogUtil.d(TAG,bannerDataBeanList.get(i).getUrl());
                    }
                    //获取完数据后UI操作
                    //主线程将alertDialog提示隐藏
                    Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            initView(BANNER);
                            alertDialog.hide();
                        }
                    });
                } catch (IOException | JSONException e) {
                    Log.d(TAG, "初始化项目数据成功");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(TAG, "初始化项目数据失败");
            }
        });

        //topArticle所需数据mList
        //接口参数 无
        Call<ResponseBody> callTopArticle = HttpUtil.getWanAndroidService().getTopArticle();
        callTopArticle.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String strBack = null;
                try {
                    strBack = response.body().string();
                    //处理数据
                    JSONObject jsonObject = null;
                    jsonObject = new JSONObject(strBack);
                    Gson gson = new Gson();
                    TopArticleBean topArticleBean;
                    topArticleBean = gson.fromJson(jsonObject.toString(), TopArticleBean.class);
                    topDataBeanList = topArticleBean.getData();
                    //初始化mList
                    for (int i = 0; i < topDataBeanList.size(); i++) {
                        //文章所需数据 mList
                        ArticleBean articleBean = new ArticleBean(
                                "" + topDataBeanList.get(i).getAuthor(),
                                "" + topDataBeanList.get(i).getTitle(),
                                "" + DateUtil.timeStampDate(topDataBeanList.get(i).getPublishTime() + ""),
                                "" + topDataBeanList.get(i).getChapterName(),
                                "" + topDataBeanList.get(i).getLink()
                        );
                        mList.add(articleBean);
                    }
                    //获取完数据后UI操作
                    //主线程将alertDialog提示隐藏
                    Objects.requireNonNull(getActivity()).runOnUiThread(() -> {
                        initView(TOPARTICLE);
                        alertDialog.hide();
                    });

                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }

    private void initView(int i) {
        switch (i) {
            case BANNER:
                initBannerView();
                break;
            case TOPARTICLE:
                initTopArticleView();
                break;
        }
    }

    private void initTopArticleView() {
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
        //点击事件
        topArticleAdapter.setOnItemClickListener((view, position) -> {
            Intent intent = new Intent(getActivity().getApplicationContext(), WebActivity.class);
            intent.putExtra("webURL", mList.get(position).getWebURL());
            intent.putExtra("userNumber", userNumber);
            startActivity(intent);
        });
//为RecyclerView对象mRecyclerView设置adapter
        mRecyclerView.setAdapter(topArticleAdapter);
    }

    private void initBannerView() {
        banner.setImages(images).setImageLoader(new GlideImageLoader());
        //设置banner样式 显示圆形指示器和标题（水平显示
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        //设置标题集合（当banner样式有显示title时）
        banner.setBannerTitles(titles);
        //lambda表达式简化
        banner.setOnBannerListener(position -> {
            Intent intent = new Intent(getActivity().getApplicationContext(), WebActivity.class);
            intent.putExtra("webURL",webURLs.get(position));
            intent.putExtra("userNumber",userNumber);
            //LogUtil.d(TAG,"banner "+webURLs.get(position)+" "+position);
            startActivity(intent);
        });
        //设置轮播时间
        banner.setDelayTime(2500);
        banner.start();
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
