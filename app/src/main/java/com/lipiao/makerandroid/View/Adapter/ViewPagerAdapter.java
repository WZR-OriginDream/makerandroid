package com.lipiao.makerandroid.View.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.lipiao.makerandroid.View.Fragment.SimpleFragment;

import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private List<SimpleFragment> list;
    public ViewPagerAdapter(FragmentManager fm, List<SimpleFragment> list) {
        super(fm);
        this.list = list;
    }
    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }
    @Override
    public int getCount() {
        return list.size();
    }

}
