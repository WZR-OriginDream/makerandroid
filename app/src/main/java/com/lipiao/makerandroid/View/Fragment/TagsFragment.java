package com.lipiao.makerandroid.View.Fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.lipiao.makerandroid.Bean.RespondBean.ProjectCategoryBean;
import com.lipiao.makerandroid.R;
import com.lipiao.makerandroid.Utils.FragmentFactoryUtil;
import com.lipiao.makerandroid.Utils.HttpUtil;
import com.lipiao.makerandroid.View.Adapter.TabPagerAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 导向与项目碎片复用
 * 因为结构相似tags+fragments
 */
public class TagsFragment extends Fragment {


    static String userNumber;//账号
    View rootView;
    String TAG = "TagsFragment";
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

    AlertDialog alertDialog;//模拟加载数据


    //项目碎片bean类集合 项目类型
    private List<ProjectCategoryBean.DataBean> projectCategoryDataBeanList;


    public TagsFragment() {
        // Required empty public constructor
    }

    public static TagsFragment newInstance(String FragmentKind,String username) {
        TagsFragment fragment = new TagsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("userNumber", username);
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
        //获取fragmentKind类型值 判断是初始化何种碎片 使用断言判空
        assert getArguments() != null;
        strFragmentKind = getArguments().getString("fragmentKind");
        userNumber = getArguments().getString("userNumber");//获取账号
        initFragment();
        return rootView;
    }

    private void initFragment() {
        switch (strFragmentKind) {
            case "leadFragment":
                initLeadFragment();
                initView();
                break;
            case "projectFragment":
                initProjectFragment();
                break;
        }
    }

    //抛出异常，initProjectFragment需重用此方法（加载数据）
    private void initView() throws NullPointerException {
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
        alertDialog = new AlertDialog.Builder(Objects.requireNonNull(getActivity()))
                .setMessage("获取项目数据中...")
                .setTitle("Maker——IoT").create();
        alertDialog.show();
        //接口参数 page cid
        Call<ResponseBody> call = HttpUtil.getWanAndroidService().getProjectCategory();
        //网络请求
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String strBack = response.body().string();
                    //处理数据
                    JSONObject jsonObject = null;
                    ProjectCategoryBean projectCategoryBean;
                    jsonObject = new JSONObject(strBack);
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
                        //Log.d(TAG, "initProjectFragment: category" + category);
                        titles[i] = category;//初始化titles，用于传入适配器，与tag名称一致
                        tabLayout.addTab(tabLayout.newTab().setText(category));//作为Tab添加至tabLayout中
                        //根据类别CID 此项目类别json为id对应访问文章的接口种为cid 新建对应的项目分类碎片
                        fragments.add(FragmentFactoryUtil.initFragment(categoryCID, userNumber));//作为碎片初始化信息传入碎片工厂工具类初始化碎片的函数中
                    }
                    //获取完数据后UI操作
                    initView();
                    //主线程将alertDialog提示隐藏
                    Objects.requireNonNull(getActivity()).runOnUiThread(() -> alertDialog.hide());
                } catch (IOException | JSONException e) {
                    Log.d(TAG, "初始化项目数据失败");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(TAG, "初始化项目数据失败");
            }
        });

    }

    //初始化导向碎片界面数据 titles，fragments
    private void initLeadFragment() {

        tabLayout.addTab(tabLayout.newTab().setText("知识体系"));
        tabLayout.addTab(tabLayout.newTab().setText("拓维导航"));

        titles = new String[]{"知识体系", "拓维导航"};
        fragments = new ArrayList<>();
        for (int i = 0; i < titles.length; i++) {
            fragments.add(FragmentFactoryUtil.initFragment(i,userNumber));
        }

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