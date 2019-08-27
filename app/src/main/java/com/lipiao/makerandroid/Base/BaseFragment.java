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
 */
public abstract class BaseFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injectPresenter();
    }

    /**
     * 绑定presenter
     */
    protected abstract void injectPresenter();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(attachLayoutId(), container, false);
        initView(root);
        initData();
        return root;
    }

    /**
     * 绑定布局文件
     *
     * @return 布局文件ID
     */
    protected abstract int attachLayoutId();

    /**
     * 初始化布局
     *
     * @param root
     */
    protected abstract void initView(View root);

    /**
     * 初始化数据
     *
     * @throws NullPointerException
     */
    protected abstract void initData() throws NullPointerException;

}

