package com.lipiao.makerandroid.View.Fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;

import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lipiao.makerandroid.R;
import com.lipiao.makerandroid.Utils.FragmentFactoryUtil;
import com.lipiao.makerandroid.View.Adapter.TabPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class LeadFragment extends Fragment {
    View rootView;
    //碎片中使用butterknife略有不同
    private Unbinder unbinder;

    @BindView(R.id.tl_user_fragment)
    TabLayout tabLayout;

    @BindView(R.id.vp_user_fragment)
    ViewPager viewPager;

    private TabPagerAdapter tabPagerAdapter;//注意导包
    private String[] titles = {"知识体系", "拓维导航"};
    private List<Fragment> fragments;


    public LeadFragment() {
        // Required empty public constructor
    }

    public static LeadFragment newInstance() {
        return new LeadFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_lead, container, false);
        //返回一个Unbinder值（进行解绑），注意这里的this不能使用getActivity()
        unbinder = ButterKnife.bind(this, rootView);
        initData();
        initView();
        initListener();
        return rootView;
    }


    private void initView() {
        tabLayout.addTab(tabLayout.newTab().setText("知识体系"));
        tabLayout.addTab(tabLayout.newTab().setText("拓维导航"));

        tabPagerAdapter = new TabPagerAdapter(getActivity().getSupportFragmentManager());
        fragments = new ArrayList<>();
        for (int i = 0; i < titles.length; i++) {
            fragments.add(FragmentFactoryUtil.creatFragment(i));
        }
        tabPagerAdapter.setTitles(titles);
        tabPagerAdapter.setFragments(fragments);
        viewPager.setAdapter(tabPagerAdapter);
        //将TabLayout和ViewPager绑定
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        //mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);


        //为指示器添加图片
//        tabLayout.addTab(tabLayout.newTab().setText("知识体系").setIcon(R.mipmap.tree));
//        tabLayout.addTab(tabLayout.newTab().setText("拓维导航").setIcon(R.mipmap.navi));

    }

    private void initData() {

    }

    private void initListener() {

    }
}

