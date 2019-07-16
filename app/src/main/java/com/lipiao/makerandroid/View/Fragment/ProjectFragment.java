package com.lipiao.makerandroid.View.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lipiao.makerandroid.R;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ProjectFragment extends Fragment {
    View rootView;
    //碎片中使用butterknife略有不同
    private Unbinder unbinder;

    @BindView(R.id.srl_pf)
    RefreshLayout refreshLayout;
    //SmartRefreshLayout smartRefreshLayout;

    public ProjectFragment() {
        // Required empty public constructor
    }

    public static ProjectFragment newInstance() {
        return new ProjectFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_project, container, false);
        //返回一个Unbinder值（进行解绑），注意这里的this不能使用getActivity()
        unbinder = ButterKnife.bind(this, rootView);
        initData();
        initView();
        initListener();
        return rootView;
    }


    private void initView() {

    }

    private void initData() {

    }

    private void initListener() {
        refreshLayout.setPrimaryColorsId(R.color.colorPrimaryDark, android.R.color.white);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(1500);
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadMore(1500/*,false*/);//传入false表示加载失败
            }
        });

    }
}

