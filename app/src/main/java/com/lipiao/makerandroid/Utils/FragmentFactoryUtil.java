package com.lipiao.makerandroid.Utils;

import android.support.v4.app.Fragment;

import com.lipiao.makerandroid.View.Fragment.SystemFragment;

import java.util.HashMap;
import java.util.Map;

//碎片工厂工具类，适用于tags+fragment
public class FragmentFactoryUtil {
    /**
     * 知识体系
     */
    public static final int TAB_RECOMMEND = 0;
    /**
     * 拓维导航
     */
    public static final int TAB_CATEGORY = 1;

    private static Map<Integer, Fragment> mFragmentMap = new HashMap<>();

    public static Fragment creatFragment(int index){
        Fragment fragment = mFragmentMap.get(index);
        //如果之前没有创建，就创建新的
        if (fragment == null){
            switch (index){
                case TAB_RECOMMEND:
                    fragment = new SystemFragment();
                    break;
                case TAB_CATEGORY:
                    fragment = new SystemFragment();
                    break;
            }
            //把创建的fragment存起来
            mFragmentMap.put(index,fragment);
        }
        return fragment;
    }
}
