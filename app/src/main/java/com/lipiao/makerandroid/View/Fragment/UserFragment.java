package com.lipiao.makerandroid.View.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lipiao.makerandroid.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public class UserFragment extends Fragment {
    View rootView;
    //碎片中使用butterknife略有不同
    private Unbinder unbinder;

    public UserFragment() {
        // Required empty public constructor
    }

    public static UserFragment newInstance() {
        return new UserFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_user, container, false);
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

    }
}

