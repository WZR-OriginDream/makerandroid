package com.lipiao.makerandroid.presenter;

import com.lipiao.makerandroid.bean.BannerBean;
import com.lipiao.makerandroid.model.BannerModel;
import com.lipiao.makerandroid.model.BannerModelImpl;
import com.lipiao.makerandroid.view.BannerView;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * 表示层
 * 需要model层与view层引用
 */
//泛型
public class BannerPresenter<T extends BannerView> {

    //1.model层引用
    BannerModel bannerModel=new BannerModelImpl();
    //2.view层引用
    //BannerView bannerView;
    //防止内存泄漏 采用弱引用
    protected WeakReference<T> mViewRef;
    //3.构造方法
    public BannerPresenter() {
//        mViewRef= new WeakReference<T>(view);
    }
    //为了防止内存泄漏,手动绑定与解绑
    //进行绑定
    public void attachView(T view){
        mViewRef=new WeakReference<T>(view);
    }

    //进行解绑
    public void detachView(){
        mViewRef.clear();
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
