package com.lipiao.makerandroid.View.Fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;

import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.lipiao.makerandroid.Bean.ProjectCategoryBean;
import com.lipiao.makerandroid.R;
import com.lipiao.makerandroid.Utils.FragmentFactoryUtil;
import com.lipiao.makerandroid.View.Adapter.TabPagerAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

//导向与项目碎片复用 结构相似tags+fragments
public class LeadFragment extends Fragment {
    View rootView;
    String TAG = "LeadFragment";
    String strFragmentKind;
    //碎片中使用butterknife略有不同
    private Unbinder unbinder;

    @BindView(R.id.tl_lead_fragment)
    TabLayout tabLayout;

    @BindView(R.id.vp_lead_fragment)
    ViewPager viewPager;

    private TabPagerAdapter tabPagerAdapter;//注意导包
    private String[] titles;
    private List<Fragment> fragments;


    //项目碎片bean类集合 项目类型
    private List<ProjectCategoryBean.DataBean> projectCategoryDataBeanList;


    public LeadFragment() {
        // Required empty public constructor
    }

    public static LeadFragment newInstance(String FragmentKind) {
        LeadFragment fragment = new LeadFragment();
        Bundle bundle = new Bundle();
        bundle.putString("fragmentKind", FragmentKind);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_lead, container, false);
        //返回一个Unbinder值（进行解绑），注意这里的this不能使用getActivity()
        unbinder = ButterKnife.bind(this, rootView);
        //获取fragmentKind类型值 判断是初始化何种碎片
        strFragmentKind = getArguments().getString("fragmentKind");
        initData();
        initView();
        initListener();
        return rootView;
    }


    private void initView() {
        switch (strFragmentKind) {
            case "leadFragment":
                initLeadFragment();
                break;
            case "projectFragment":
                initProjectFragment();
                break;
        }

        //titles，fragments，viewpager初始完后与适配器绑定
        //getChildFragmentManager()解决滑动卡顿 亲测有效
        tabPagerAdapter = new TabPagerAdapter(getChildFragmentManager());
        //tabPagerAdapter = new TabPagerAdapter(getActivity().getSupportFragmentManager());
        tabPagerAdapter.setTitles(titles);
        tabPagerAdapter.setFragments(fragments);
        viewPager.setAdapter(tabPagerAdapter);
        //将TabLayout和ViewPager绑定
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);//设置Tab滚动方式为：可滚动


    }

    //初始化项目碎片界面数据 titles，fragments
    private void initProjectFragment() {



        String t = "{\"data\":[{\"children\":[],\"courseId\":13,\"id\":294,\"name\":\"完整项目\",\"order\":145000,\"parentChapterId\":293,\"userControlSetTop\":false,\"visible\":0},{\"children\":[],\"courseId\":13,\"id\":402,\"name\":\"跨平台应用\",\"order\":145001,\"parentChapterId\":293,\"userControlSetTop\":false,\"visible\":1},{\"children\":[],\"courseId\":13,\"id\":367,\"name\":\"资源聚合类\",\"order\":145002,\"parentChapterId\":293,\"userControlSetTop\":false,\"visible\":1},{\"children\":[],\"courseId\":13,\"id\":323,\"name\":\"动画\",\"order\":145003,\"parentChapterId\":293,\"userControlSetTop\":false,\"visible\":1},{\"children\":[],\"courseId\":13,\"id\":314,\"name\":\"RV列表动效\",\"order\":145004,\"parentChapterId\":293,\"userControlSetTop\":false,\"visible\":1},{\"children\":[],\"courseId\":13,\"id\":358,\"name\":\"项目基础功能\",\"order\":145005,\"parentChapterId\":293,\"userControlSetTop\":false,\"visible\":1},{\"children\":[],\"courseId\":13,\"id\":328,\"name\":\"网络&amp;文件下载\",\"order\":145011,\"parentChapterId\":293,\"userControlSetTop\":false,\"visible\":1},{\"children\":[],\"courseId\":13,\"id\":331,\"name\":\"TextView\",\"order\":145013,\"parentChapterId\":293,\"userControlSetTop\":false,\"visible\":1},{\"children\":[],\"courseId\":13,\"id\":336,\"name\":\"键盘\",\"order\":145015,\"parentChapterId\":293,\"userControlSetTop\":false,\"visible\":1},{\"children\":[],\"courseId\":13,\"id\":337,\"name\":\"快应用\",\"order\":145016,\"parentChapterId\":293,\"userControlSetTop\":false,\"visible\":1},{\"children\":[],\"courseId\":13,\"id\":338,\"name\":\"日历&amp;时钟\",\"order\":145017,\"parentChapterId\":293,\"userControlSetTop\":false,\"visible\":1},{\"children\":[],\"courseId\":13,\"id\":339,\"name\":\"K线图\",\"order\":145018,\"parentChapterId\":293,\"userControlSetTop\":false,\"visible\":1},{\"children\":[],\"courseId\":13,\"id\":340,\"name\":\"硬件相关\",\"order\":145019,\"parentChapterId\":293,\"userControlSetTop\":false,\"visible\":1},{\"children\":[],\"courseId\":13,\"id\":357,\"name\":\"表格类\",\"order\":145022,\"parentChapterId\":293,\"userControlSetTop\":false,\"visible\":1},{\"children\":[],\"courseId\":13,\"id\":363,\"name\":\"创意汇\",\"order\":145024,\"parentChapterId\":293,\"userControlSetTop\":false,\"visible\":1},{\"children\":[],\"courseId\":13,\"id\":380,\"name\":\"ImageView\",\"order\":145029,\"parentChapterId\":293,\"userControlSetTop\":false,\"visible\":1},{\"children\":[],\"courseId\":13,\"id\":382,\"name\":\"音视频&amp;相机\",\"order\":145030,\"parentChapterId\":293,\"userControlSetTop\":false,\"visible\":1},{\"children\":[],\"courseId\":13,\"id\":383,\"name\":\"相机\",\"order\":145031,\"parentChapterId\":293,\"userControlSetTop\":false,\"visible\":1},{\"children\":[],\"courseId\":13,\"id\":310,\"name\":\"下拉刷新\",\"order\":145032,\"parentChapterId\":293,\"userControlSetTop\":false,\"visible\":1},{\"children\":[],\"courseId\":13,\"id\":385,\"name\":\"架构\",\"order\":145033,\"parentChapterId\":293,\"userControlSetTop\":false,\"visible\":1},{\"children\":[],\"courseId\":13,\"id\":387,\"name\":\"对话框\",\"order\":145035,\"parentChapterId\":293,\"userControlSetTop\":false,\"visible\":1},{\"children\":[],\"courseId\":13,\"id\":388,\"name\":\"数据库\",\"order\":145036,\"parentChapterId\":293,\"userControlSetTop\":false,\"visible\":1},{\"children\":[],\"courseId\":13,\"id\":391,\"name\":\"AS插件\",\"order\":145037,\"parentChapterId\":293,\"userControlSetTop\":false,\"visible\":1},{\"children\":[],\"courseId\":13,\"id\":400,\"name\":\"ViewPager\",\"order\":145039,\"parentChapterId\":293,\"userControlSetTop\":false,\"visible\":1},{\"children\":[],\"courseId\":13,\"id\":401,\"name\":\"二维码\",\"order\":145040,\"parentChapterId\":293,\"userControlSetTop\":false,\"visible\":1},{\"children\":[],\"courseId\":13,\"id\":312,\"name\":\"富文本编辑器\",\"order\":145041,\"parentChapterId\":293,\"userControlSetTop\":false,\"visible\":1}],\"errorCode\":0,\"errorMsg\":\"\"}";
        //处理数据
        JSONObject jsonObject = null;
        ProjectCategoryBean projectCategoryBean;
        try {
            jsonObject = new JSONObject(t);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();
        projectCategoryBean = gson.fromJson(jsonObject.toString(), ProjectCategoryBean.class);
        projectCategoryDataBeanList = projectCategoryBean.getData();

        //遍历 从中获取项目分类名称
        //1.将其作为Tab添加至tabLayout中
        //2.将其作为碎片初始化信息传入碎片工厂工具类初始化碎片的函数中
        fragments = new ArrayList<>();
        titles = new String[projectCategoryDataBeanList.size()];//初始化titles String数组的大小
        for (int i = 0; i < projectCategoryDataBeanList.size(); i++) {
            String category = projectCategoryDataBeanList.get(i).getName();//获取项目种类的名称
            int categoryCID = projectCategoryDataBeanList.get(i).getId();
            Log.d(TAG, "initProjectFragment: category" + category);
            titles[i] = category;//初始化titles，用于传入适配器，与tag名称一致
            tabLayout.addTab(tabLayout.newTab().setText(category));//作为Tab添加至tabLayout中
            //根据类别CID 此项目类别json为id对应访问文章的接口种为cid 新建对应的项目分类碎片
            fragments.add(FragmentFactoryUtil.initFragment(categoryCID));//作为碎片初始化信息传入碎片工厂工具类初始化碎片的函数中
        }


    }

    //初始化导向碎片界面数据 titles，fragments
    private void initLeadFragment() {



        tabLayout.addTab(tabLayout.newTab().setText("知识体系"));
        tabLayout.addTab(tabLayout.newTab().setText("拓维导航"));

        titles = new String[]{"知识体系", "拓维导航"};
        fragments = new ArrayList<>();
        for (int i = 0; i < titles.length; i++) {
            fragments.add(FragmentFactoryUtil.initFragment(i));
        }

    }

    private void initData() {

    }

    private void initListener() {

    }

    /**
     * onDestroyView中进行解绑操作
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}

