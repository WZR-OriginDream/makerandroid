package com.lipiao.makerandroid.Model;

import com.lipiao.makerandroid.Bean.BannerBean;

import java.util.ArrayList;
import java.util.List;

/**
 * MVP模式重构项目 （暂时不写了，能力有限）
 * 模型层
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
