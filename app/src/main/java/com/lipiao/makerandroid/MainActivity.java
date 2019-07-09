package com.lipiao.makerandroid;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ycl.tabview.library.TabView;
import com.ycl.tabview.library.TabViewChild;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.tabView)
    TabView tabView;
    //    @BindView(R.id.viewpager_main)
//    ViewPager viewPager;
    List<TabViewChild> tabViewChildList = new ArrayList<>();
    //        tabViewChildList.add(new TabViewChild(R.mipmap.main,R.mipmap.main_no,"首页",  MainFragment.newInstance()));

    private List<Fragment> mList = new ArrayList<>();
    private MainFragment mainFragment;
    private ProjectFragment projectFragment;
    private SystemFragment systemFragment;
    private UserFragment userFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //绑定初始化ButterKnife
        ButterKnife.bind(this);

        //先判空后添加
        if (mainFragment == null) {
            mainFragment = MainFragment.newInstance();
            tabViewChildList.add(new TabViewChild(R.mipmap.main, R.mipmap.main_no, "首页", mainFragment));
        }
        if (systemFragment == null) {
            systemFragment = systemFragment.newInstance();
            tabViewChildList.add(new TabViewChild(R.mipmap.system, R.mipmap.system_no, "体系", systemFragment));
        }
        if (projectFragment == null) {
            projectFragment = ProjectFragment.newInstance();
            tabViewChildList.add(new TabViewChild(R.mipmap.project, R.mipmap.project_no, "项目", projectFragment));
        }
        if (userFragment == null) {
            userFragment = UserFragment.newInstance();
            tabViewChildList.add(new TabViewChild(R.mipmap.user, R.mipmap.user_no, "我的", userFragment));
        }
        tabView.setTabViewChild(tabViewChildList, getSupportFragmentManager());

    }
}
