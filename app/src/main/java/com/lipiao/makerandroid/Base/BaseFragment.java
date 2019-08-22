package com.lipiao.makerandroid.Base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Fragment的基类
 * 实现懒加载
 */

public abstract class BaseFragment extends Fragment {

    public final String TAG = getClass().getSimpleName();
    public boolean mHaveLoadData; // 表示是否已经请求过数据
    public boolean mLoadDataFinished; // 表示数据是否已经请求完毕
    private View mRootView;

    // 表示开始加载数据, 但不表示数据加载已经完成
    public abstract void loadDataStart();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        //已经加载过则直接使用

        if (mRootView != null) {
            return mRootView;
        }
        mRootView = initRootView(inflater, container, savedInstanceState);
        return mRootView;
    }


    protected abstract View initRootView(LayoutInflater inflater, ViewGroup container,
                                         Bundle savedInstanceState);


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        Log.d(TAG, "setUserVisibleHint, isVisibleToUser = " + isVisibleToUser);
        super.setUserVisibleHint(isVisibleToUser);
        // 如果还没有加载过数据 && 用户切换到了这个fragment
        // 那就开始加载数据
        if (!mHaveLoadData && isVisibleToUser) {
            loadDataStart();
            mHaveLoadData = true;
        }
    }

//    //提供回调方法，让子类fragment选择表示层
//    protected abstract T createPresenter();
}

