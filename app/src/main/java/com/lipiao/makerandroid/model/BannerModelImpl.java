package com.lipiao.makerandroid.model;

import com.lipiao.makerandroid.bean.BannerBean;

import java.util.ArrayList;
import java.util.List;

/**
 * bannermodel的实现
 */
public class BannerModelImpl implements BannerModel{

    @Override
    public void loadingBanner(BannerOnLoadListener bannerOnLoadListener) {
        List<BannerBean> bannerData =new ArrayList<BannerBean>();
        //从网络或者从数据库加载数据
//        bannerData.add
        //把data回调至上一次
        bannerOnLoadListener.onComplete(bannerData);

    }
}
