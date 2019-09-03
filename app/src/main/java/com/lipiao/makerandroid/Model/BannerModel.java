package com.lipiao.makerandroid.Model;

import com.lipiao.makerandroid.Bean.BannerBean;

import java.util.List;

/**
 * MVP模式重构项目 （暂时不写了，能力有限）
 * 模型层
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
