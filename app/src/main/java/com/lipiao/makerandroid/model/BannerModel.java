package com.lipiao.makerandroid.model;

import com.lipiao.makerandroid.bean.BannerBean;

import java.util.List;

/**
 * 用于加载数据
 */
public interface BannerModel {
    //加载Banner
    void loadingBanner(BannerOnLoadListener bannerOnLoadListener);

    //设计一个内部回调接口
    interface BannerOnLoadListener{
        void onComplete(List<BannerBean> bannerBeans);
    }
}
