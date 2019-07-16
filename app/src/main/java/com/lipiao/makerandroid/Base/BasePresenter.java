package com.lipiao.makerandroid.Base;

import java.lang.ref.WeakReference;

/**
 * Presenter表示层基类
 */
public class BasePresenter<T> {

    //view层的引用
    protected WeakReference<T> mViewRef;

    //进行绑定
    public void attachView(T view) {
        mViewRef = new WeakReference<>(view);
    }

    //进行解绑
    public void detachView(T view) {
        mViewRef.clear();
    }
}
