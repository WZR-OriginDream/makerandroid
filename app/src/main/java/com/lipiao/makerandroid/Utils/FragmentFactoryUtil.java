package com.lipiao.makerandroid.Utils;

import android.support.v4.app.Fragment;

import com.lipiao.makerandroid.View.Fragment.LeadFragment;
import com.lipiao.makerandroid.View.Fragment.ProjectCategoryFragment;

import java.util.HashMap;
import java.util.Map;

//碎片工厂工具类，适用于tags+fragment（导向碎片和项目碎片复用，用于生成子碎片）
public class FragmentFactoryUtil {
    /**
     * 2.1导向碎片——知识体系
     */
    public static final int TAB_TREE = 0;
    /**
     * 2.2导向碎片——拓维导航
     */
    public static final int TAB_NAVIGATION = 1;

    /**
     * 项目分类碎片根据所传递过来的CID （也是int类型） 来生成对应的碎片
     */


    //保存碎片信息，以便复用，防止重复加载资源
    //导向碎片中的碎片(以int类型区分)
    private static Map<Integer, Fragment> leadFragmentMap = new HashMap<>();
    //项目碎片中的碎片(以String类型区分)
   // private static Map<String, Fragment> projectFragmentMap = new HashMap<>();

    //根据下标创建碎片 适用于导向碎片中创建碎片
    public static Fragment initFragment(int index,String userNumber) {
        Fragment fragment = leadFragmentMap.get(index);
        //如果之前没有创建，就创建新的
        if (fragment == null) {
            switch (index) {
                case TAB_TREE:
                    fragment = LeadFragment.newInstance("tree",userNumber);//知识体系
                    break;
                case TAB_NAVIGATION:
                    fragment = LeadFragment.newInstance("navigation",userNumber);//拓维导航
                    break;
                default:
                    fragment = ProjectCategoryFragment.newInstance(index,userNumber);//根据项目分类CID来创建fragment

            }
            //把创建的fragment存起来
            leadFragmentMap.put(index, fragment);
        }
        return fragment;
    }

}
