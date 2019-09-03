package com.lipiao.makerandroid.View;

import com.lipiao.makerandroid.Bean.BannerBean;

import java.util.List;

/**
 * MVP模式重构项目 （暂时不写了，还不会）
 * View层
 * 定义所有的UI逻辑
 * activity访问接口获取数据
 *
 */
public interface BannerView {
    void showLoading();
    //显示数据

    //显示banner轮播图数据（使用回调的方式返回数据）
    void showBanner(List<BannerBean> bannerBeans);
}
