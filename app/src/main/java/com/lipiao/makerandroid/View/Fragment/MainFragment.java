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


public class MainFragment extends BaseFragment {

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
    public void loadDataStart() {
        Log.d(TAG, "loadDataStart");
        // 模拟请求数据
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {

                // 一旦获取到数据, 就应该立刻标记数据加载完成
                mLoadDataFinished = true;
                if (mViewInflateFinished) { // mViewInflateFinished一般都是true

                }
            }
        }, 3000);
    }

    @Override
    protected View initRootView(LayoutInflater inflater, ViewGroup container,
                                Bundle savedInstanceState) {
        Log.d(TAG, "initRootView");
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    protected void findViewById(View view) {

        //绑定控件
        unbinder.unbind();

        if (mLoadDataFinished) { // 一般情况下这时候数据请求都还没完成, 所以不会进这个if
            //为控件赋值数据以及添加监听

            banner.setImageLoader(new GlideImageLoader()).setImages(images);
            //设置banner样式 显示圆形指示器和标题（水平显示
            banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
            //设置标题集合（当banner样式有显示title时）
            banner.setBannerTitles(titles);
            //设置轮播时间
            banner.setDelayTime(3000);
            banner.start();


            smartRefreshLayout.setPrimaryColorsId(R.color.colorPrimaryDark, android.R.color.white);
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
    }


    /**
     * onDestroyView中进行解绑操作
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
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
