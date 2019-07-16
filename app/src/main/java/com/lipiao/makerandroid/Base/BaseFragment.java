package com.lipiao.makerandroid.Base;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Fragment的基类
 */

public abstract class BaseFragment<V, T extends BasePresenter<V>> extends Fragment {
    //表示层的引用
    public T bannerPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //绑定
        bannerPresenter = createPresenter();
        bannerPresenter.attachView((V) this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //解绑
        bannerPresenter.detachView((V) this);
    }


    //提供回调方法，让子类fragment选择表示层
    protected abstract T createPresenter();
}

