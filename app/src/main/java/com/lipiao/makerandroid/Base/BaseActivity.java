package com.lipiao.makerandroid.Base;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


/**
 * Activity基类
 * 泛型的使用
 * V,T
 * 抽象
 */
public abstract class BaseActivity<V,T extends BasePresenter<V>> extends AppCompatActivity {

    //表示层的引用
    public T bannerPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //绑定
        bannerPresenter=createPresenter();
        bannerPresenter.attachView((V) this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //解绑
        bannerPresenter.detachView((V) this);
    }

    //提供回调方法，让子类activity选择表示层
    protected abstract T createPresenter();
}
