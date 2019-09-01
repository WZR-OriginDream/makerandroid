package com.lipiao.makerandroid.View.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lipiao.makerandroid.Bean.NavigationDataBean;
import com.lipiao.makerandroid.Bean.SystemBean;
import com.lipiao.makerandroid.Bean.TagsSimpleBean;
import com.lipiao.makerandroid.R;
import com.lipiao.makerandroid.Utils.HttpUtil;
import com.lipiao.makerandroid.View.Adapter.TagsAdapter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 导向碎片
 * 知识体系与拓维导航复用同一个模板碎片
 *
 */
public class LeadFragment extends Fragment {

    String TAG = "LeadFragment";
    String strKind;
    View rootView;
    //碎片中使用butterknife略有不同
    private Unbinder unbinder;

    @BindView(R.id.srl_sf)
    RefreshLayout refreshLayout;


    @BindView(R.id.rv_fragment_system)
    RecyclerView mRecyclerView;
    TagsAdapter tagsAdapter;
    //知识体系
    private List<SystemBean.DataBean> systemDataBeanList;
    private List<TagsSimpleBean> systemSimpleBeanList = new ArrayList<>();
    //拓维导航
    private List<NavigationDataBean.DataBean> naviDataBeanList;
    //简化Bean类与知识体系的相同，复用此类实例systemSimpleBeanList

    //无参构造
    public LeadFragment() {
        // Required empty public constructor
    }

    //分类，知识体系和拓维导航两种碎片leadFragment中包含两个碎片
    public static LeadFragment newInstance(String arg) {
        LeadFragment fragment = new LeadFragment();
        Bundle bundle = new Bundle();
        bundle.putString("kind", arg);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_system, container, false);
        //返回一个Unbinder值（进行解绑），注意这里的this不能使用getActivity()
        unbinder = ButterKnife.bind(this, rootView);
        //获取kind类型值
        strKind = getArguments().getString("kind");
        initView();
        initData();
        return rootView;
    }
    //初始化RecyclerView RefreshLayout
    private void initView() {

        //top rv
        mRecyclerView.setHasFixedSize(true);
        //平常的水平一个item布局的流
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        //利用refreshLayout手动刷新UI界面
        refreshLayout.autoRefreshAnimationOnly();
        refreshLayout.finishRefresh(1100/*,false*/);//传入false表示刷新失败//手动设置动画时长为一秒
    }

    private void initData() {
        mRecyclerView.setHasFixedSize(true);
        //平常的水平一个item布局的流
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        //利用refreshLayout手动刷新UI界面
        refreshLayout.autoRefreshAnimationOnly();
        refreshLayout.finishRefresh(1600/*,false*/);//传入false表示刷新失败//手动设置动画时长为一秒

        switch (strKind) {
            case "tree":
                initTree();
                break;
            case "navigation":
                initNavigation();
                break;
        }


        //实例化MyAdapter并传入mList对象
        tagsAdapter = new TagsAdapter(systemSimpleBeanList);
        Log.d(TAG, "initData: " + systemSimpleBeanList.size());
//为RecyclerView对象mRecyclerView设置adapter
        mRecyclerView.setAdapter(tagsAdapter);
    }

    //初始化拓维导航碎片
    private void initNavigation() {
        //获取拓维导航数据（标题） 接口参数 无
        Call<ResponseBody> call = HttpUtil.getWanAndroidService().getNavigation();
        //网络请求
        call.enqueue(new Callback<ResponseBody>() {
            //请求成功
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String strBack = response.body().string();
                    //处理数据
                    JSONObject jsonObject = null;
                    NavigationDataBean navigationDataBean;
                    jsonObject = new JSONObject(strBack);
                    Gson gson = new Gson();
                    navigationDataBean = gson.fromJson(jsonObject.toString(), NavigationDataBean.class);
                    naviDataBeanList = navigationDataBean.getData();

                    //通过其get()方法可以获得其中的对象
                    for (int i = 0; i < naviDataBeanList.size(); i++) {
                        NavigationDataBean.DataBean naviDataBean = naviDataBeanList.get(i);
                        //Log.d(TAG, "@@@@@title: " + naviDataBean.getName());

                        List<NavigationDataBean.DataBean.ArticlesBean> articlesBeanList = naviDataBean.getArticles();
                        String[] mVals = new String[articlesBeanList.size()];
                        for (int index = 0; index < articlesBeanList.size(); index++) {
                            mVals[index] = articlesBeanList.get(index).getTitle();
                            //Log.d(TAG, "child: " + articlesBeanList.get(index).getTitle());
                        }
                        TagsSimpleBean systemSimpleBean = new TagsSimpleBean(naviDataBean.getName(), mVals);
                        systemSimpleBeanList.add(systemSimpleBean);
                    }
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
            //请求失败
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }

    //初始化知识体系碎片
    private void initTree() {
        //获取知识体系数据（标题） 接口参数 无
        Call<ResponseBody> call = HttpUtil.getWanAndroidService().getTree();
        //网络请求
        call.enqueue(new Callback<ResponseBody>() {
            //请求成功
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String strBack = response.body().string();
                    JSONObject jsonObject = null;
                    SystemBean systemBean;
                    jsonObject = new JSONObject(strBack);
                    Gson gson = new Gson();
                    systemBean = gson.fromJson(jsonObject.toString(), SystemBean.class);
                    systemDataBeanList = systemBean.getData();
                    //通过其get()方法可以获得其中的对象
                    for (int i = 0; i < systemDataBeanList.size(); i++) {
                        SystemBean.DataBean systemDataBean = systemDataBeanList.get(i);
                        //Log.d(TAG, "@@@@@title: " + systemDataBean.getName());

                        List<SystemBean.DataBean.ChildrenBean> childrenBeanList = systemDataBean.getChildren();
                        String[] mVals = new String[childrenBeanList.size()];
                        for (int index = 0; index < childrenBeanList.size(); index++) {
                            mVals[index] = childrenBeanList.get(index).getName();
                            //Log.d(TAG, "child: " + childrenBeanList.get(index).getName());
                        }
                        TagsSimpleBean systemSimpleBean = new TagsSimpleBean(systemDataBean.getName(), mVals);
                        systemSimpleBeanList.add(systemSimpleBean);
                    }
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }

            //请求失败
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getActivity(), "初始化知识体系失败", Toast.LENGTH_SHORT).show();
            }
        });

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
