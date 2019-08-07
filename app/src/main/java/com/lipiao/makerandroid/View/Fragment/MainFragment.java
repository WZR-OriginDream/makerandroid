package com.lipiao.makerandroid.View.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.lipiao.makerandroid.Base.BaseFragment;
import com.lipiao.makerandroid.Base.BasePresenter;
import com.lipiao.makerandroid.R;
import com.lipiao.makerandroid.Utils.GlideImageLoader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class MainFragment extends Fragment {

    View rootView;

    private Handler mHandler = new Handler();
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
        initView();
        return rootView;
    }

    private void initView() {
        images.add("https://wanandroid.com/blogimgs/0b712568-6203-4a03-b475-ff55e68d89e8.jpeg");
        images.add("https://www.wanandroid.com/blogimgs/50c115c2-cf6c-4802-aa7b-a4334de444cd.png");
        images.add("https://www.wanandroid.com/blogimgs/62c1bd68-b5f3-4a3c-a649-7ca8c7dfabe6.png");
        images.add("https://www.wanandroid.com/blogimgs/90c6cc12-742e-4c9f-b318-b912f163b8d0.png");
        titles.add("标题1");
        titles.add("标题2");
        titles.add("标题3");
        titles.add("标题4");
        banner.setImages(images).setImageLoader(new GlideImageLoader());
        //设置banner样式 显示圆形指示器和标题（水平显示
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        //设置标题集合（当banner样式有显示title时）
        banner.setBannerTitles(titles);
        //设置轮播时间
        banner.setDelayTime(3000);
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

}
