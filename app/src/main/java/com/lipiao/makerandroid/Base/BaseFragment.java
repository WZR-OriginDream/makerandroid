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
 * 1.选择表示层
 * 2.实现懒加载
 */

public abstract class BaseFragment<V, T extends BasePresenter<V>> extends Fragment {
    //表示层的引用
    public T bannerPresenter;

    public final String TAG = getClass().getSimpleName();
    public boolean mHaveLoadData; // 表示是否已经请求过数据
    public boolean mLoadDataFinished; // 表示数据是否已经请求完毕
    private View mRootView;

    // 表示开始加载数据, 但不表示数据加载已经完成
    public abstract void loadDataStart();

    // 表示找控件完成, 给控件们设置数据不会报空指针了
    public boolean mViewInflateFinished;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        if (mRootView != null) {
            return mRootView;
        }
        mRootView = initRootView(inflater, container, savedInstanceState);
        findViewById(mRootView);
        mViewInflateFinished = true;
        return mRootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "onViewCreated");
    }

    protected abstract void findViewById(View view);

    protected abstract View initRootView(LayoutInflater inflater, ViewGroup container,
                                         Bundle savedInstanceState);

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

    @Override
    public void onDestroyView() {
        Log.d(TAG, "onDestroyView");
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        Log.d(TAG, "onDetach");
        super.onDetach();
    }

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


    //提供回调方法，让子类fragment选择表示层
    protected abstract T createPresenter();
}

