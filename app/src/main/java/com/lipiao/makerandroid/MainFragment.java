package com.lipiao.makerandroid;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.lipiao.makerandroid.bean.BannerBean;
import com.lipiao.makerandroid.presenter.BannerPresenter;
import com.lipiao.makerandroid.view.BannerView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.header.BezierRadarHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class MainFragment extends Fragment implements BannerView {

    View rootView;
    BannerPresenter bannerPresenter;

    //碎片中使用butterknife略有不同
    private Unbinder unbinder;

    @BindView(R.id.banner)
    Banner banner;

    @BindView(R.id.srl_mf)
    SmartRefreshLayout smartRefreshLayout;
    ArrayList<String> images = new ArrayList<String>();//图片资源集合
    ArrayList<String> titles = new ArrayList<String>();//标题

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
        initView();

        bannerPresenter=new BannerPresenter();
        bannerPresenter.attachView(this);   //绑定
        bannerPresenter.fetch();
        initListener();
        //表示层
//        new BannerPresenter<>(this).fetch();
        return rootView;
    }

    private void initData() {
        images.add("https://www.wanandroid.com/blogimgs/50c115c2-cf6c-4802-aa7b-a4334de444cd.png");
        images.add("https://www.wanandroid.com/blogimgs/ab17e8f9-6b79-450b-8079-0f2287eb6f0f.png");
        images.add("https://www.wanandroid.com/blogimgs/fb0ea461-e00a-482b-814f-4faca5761427.png");
        images.add("https://www.wanandroid.com/blogimgs/62c1bd68-b5f3-4a3c-a649-7ca8c7dfabe6.png");
        images.add("https://www.wanandroid.com/blogimgs/00f83f1d-3c50-439f-b705-54a49fc3d90d.jpg");
        images.add("https://www.wanandroid.com/blogimgs/90cf8c40-9489-4f9d-8936-02c9ebae31f0.png");
        images.add("https://www.wanandroid.com/blogimgs/90c6cc12-742e-4c9f-b318-b912f163b8d0.png");
        images.add("https://www.wanandroid.com/blogimgs/acc23063-1884-4925-bdf8-0b0364a7243e.png");
        titles.add("一起来做个App吧");
        titles.add("看看别人的面经，搞定面试");
        titles.add("兄弟，要不要挑个项目学习下?");
        titles.add("我们新增了一个常用导航Tab~");
        titles.add("公众号文章列表强势上线");
        titles.add("JSON工具");
        titles.add("flutter 中文社区");
        titles.add("微信文章合集");
    }

    /**
     * onDestroyView中进行解绑操作
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        bannerPresenter.detachView();
    }

    //对于图片轮播库 如果你需要考虑更好的体验，可以这么操作
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

    private void initView() {
        banner.setImageLoader(new GlideImageLoader()).setImages(images);
        //设置banner样式 显示圆形指示器和标题（水平显示
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        //设置标题集合（当banner样式有显示title时）
        banner.setBannerTitles(titles);
        //设置轮播时间
        banner.setDelayTime(3000);
        banner.start();

    }

    private void initListener() {

        smartRefreshLayout.setPrimaryColorsId(R.color.colorPrimaryDark, android.R.color.white);
////设置 Footer 为 球脉冲 样式
//        smartRefreshLayout.setRefreshFooter(new BallPulseFooter(getContext()).setSpinnerStyle(SpinnerStyle.Scale));
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(1500);
            }
        });
        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadMore(1500/*,false*/);//传入false表示加载失败
            }
        });


    }

    //加载
    @Override
    public void showLoading() {
//        Toast.makeText(this,"加载成功",Toast.LENGTH_SHORT).show();
    }

    //加载banner
    @Override
    public void showBanner(List<BannerBean> bannerBeans) {
        //设置适配器

    }
}
