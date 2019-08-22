package com.lipiao.makerandroid.View.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lipiao.makerandroid.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;


public class ProjectCategoryFragment extends Fragment {
    View rootView;
    String TAG="ProjectCategoryFragment";
    int intProjectCategoryCID;//项目种类编号，访问接口需要
    //碎片中使用butterknife略有不同
    private Unbinder unbinder;

    public ProjectCategoryFragment() {
        // Required empty public constructor
    }

    public static ProjectCategoryFragment newInstance(int intProjectCategoryCID) {
        ProjectCategoryFragment fragment = new ProjectCategoryFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("categoryCid",intProjectCategoryCID);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //试图文件复用fragment_project  由于结构一致
        rootView = inflater.inflate(R.layout.fragment_project, container, false);
        //返回一个Unbinder值（进行解绑），注意这里的this不能使用getActivity()
        unbinder = ButterKnife.bind(this, rootView);
        //获取fragmentKind类型值 判断是初始化何种碎片
        intProjectCategoryCID = getArguments().getInt("categoryCid");
        initData();
        initView();
        initListener();
        return rootView;
    }

    private void initListener() {

    }

    private void initView() {

    }

    private void initData() {

    }
}
