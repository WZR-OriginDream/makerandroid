package com.lipiao.makerandroid.presenter;

import com.lipiao.makerandroid.Base.BasePresenter;
import com.lipiao.makerandroid.bean.BannerBean;
import com.lipiao.makerandroid.model.BannerModel;
import com.lipiao.makerandroid.model.BannerModelImpl;
import com.lipiao.makerandroid.View.BannerView;

import java.util.List;

/**
 * 表示层
 * 需要model层与view层引用
 */
//泛型
public class BannerPresenter<T extends BannerView> extends BasePresenter<T> {

    //2.model层引用
    BannerModel bannerModel=new BannerModelImpl();

    //3.构造方法
    public BannerPresenter() {

    }

    //4.执行操作
    public void fetch(){
        if(mViewRef!=null){
            mViewRef.get().showLoading();
            if(bannerModel!=null){
                bannerModel.loadingBanner(new BannerModel.BannerOnLoadListener() {
                    @Override
                    public void onComplete(List<BannerBean> bannerBeans) {
                        //有中介过程 需重新判断
                        if (mViewRef!=null){
                            mViewRef.get().showBanner(bannerBeans);
                        }
                    }
                });
            }
        }
    }
}
