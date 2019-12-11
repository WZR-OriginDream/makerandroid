package com.lipiao.makerandroid.Base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 实现懒加载的fragment基类
 * 继承自BaseFragment
 */
public abstract class LazyLoadFragment extends Fragment {

    // 是否初始化过布局
    protected boolean isViewInitiated;

    //当前界面是否可见
    protected boolean isVisibleToUser;

    //是否加载过数据
    protected boolean isDataInitiated;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isViewInitiated=true;
        prepareFetchData();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        if (isVisibleToUser) {
            prepareFetchData();
        }
    }

    //懒加载
    public abstract void fetchData();

    public void prepareFetchData() {
        prepareFetchData(false);
    }

    //判断懒加载条件
    public void prepareFetchData(boolean forceUpdate) {
        if (isVisibleToUser && isViewInitiated && (!isDataInitiated || forceUpdate)) {
            fetchData();
            isDataInitiated = true;
        }
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(attachLayoutId(), container, false);
        initView(root);
        initData();
        return root;
    }

    //绑定布局文件
    protected abstract int attachLayoutId();

    //初始化布局
    protected abstract void initView(View root);

    //初始化数据
    protected abstract void initData() throws NullPointerException;
}

