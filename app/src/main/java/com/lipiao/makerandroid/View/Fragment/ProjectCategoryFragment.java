package com.lipiao.makerandroid.View.Fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lipiao.makerandroid.Base.LazyLoadFragment;
import com.lipiao.makerandroid.Bean.ProjectArticleBean;
import com.lipiao.makerandroid.Bean.ProjectContentBean;
import com.lipiao.makerandroid.R;
import com.lipiao.makerandroid.Service.WandroidService;
import com.lipiao.makerandroid.Utils.LogUtil;
import com.lipiao.makerandroid.View.Adapter.ProjectArticleAdapter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

//项目分类 一个类别生成一个碎片
public class ProjectCategoryFragment extends LazyLoadFragment {
    String TAG = "ProjectCategoryFragment";

    int intProjectCategoryCID;//项目种类编号，访问接口需要

    RecyclerView mRecyclerView;

    RefreshLayout refreshLayout;

    private List<ProjectArticleBean> mList = new ArrayList<>();

    public ProjectCategoryFragment() {
        // Required empty public constructor
    }

    public static ProjectCategoryFragment newInstance(int intProjectCategoryCID) {
        ProjectCategoryFragment fragment = new ProjectCategoryFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("categoryCid", intProjectCategoryCID);
        fragment.setArguments(bundle);
        return fragment;
    }

    //选择表示层
    @Override
    protected void injectPresenter() {
//        Log.d(TAG, "injectPresenter: ");
    }

    //初始化试图文件xml
    @Override
    protected int attachLayoutId() {
//        Log.d(TAG, "attachLayoutId: ");
        return R.layout.fragment_system;
    }

    //初始化布局 findViewById
    @Override
    protected void initView(View root) {
        Log.d(TAG, "initView: ");
        //获取项目种类cid
        intProjectCategoryCID = getArguments().getInt("categoryCid");

        refreshLayout = root.findViewById(R.id.srl_sf);
        refreshLayout.setReboundDuration(200);//回弹动画

        mRecyclerView = root.findViewById(R.id.rv_fragment_system);
        mRecyclerView.setHasFixedSize(true);
        //平常的水平一个item布局的流
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

    }

    @Override
    protected void initData() throws NullPointerException {
        Log.d(TAG, "initData: ");
        //实例化MyAdapter并传入mList对象
        ProjectArticleAdapter projectArticleAdapter = new ProjectArticleAdapter(mList, getContext());

        //自定义点击事件
        projectArticleAdapter.setOnItemClickListener(new ProjectArticleAdapter.OnItemClickListener() {
            //点击
            @Override
            public void onItemClick(View view, int position) {
                //根据URL创建web碎片

                Toast.makeText(getContext(), "点击事件：查看" + mList.get(position).getPreviewPicUrl(), Toast.LENGTH_SHORT).show();
            }

            //长按
            @Override
            public void onItemLongClick(View view, int position) {
                Toast.makeText(getContext(), "长按事件：收藏" + mList.get(position).getDescribe(), Toast.LENGTH_SHORT).show();
            }
        });


        //为RecyclerView对象mRecyclerView设置adapter
        mRecyclerView.setAdapter(projectArticleAdapter);

    }

    @Override
    public void fetchData() {
        Log.d(TAG, intProjectCategoryCID + "fetchData: f1的网络请求");
        //利用refreshLayout手动刷新UI界面
        refreshLayout.autoRefreshAnimationOnly();
        refreshLayout.finishRefresh(1600/*,false*/);//传入false表示刷新失败//手动设置动画时长为一秒

        //添加网络请求
        //初始化网络请求https://www.wanandroid.com/project/list/1/json?cid=294
        int page = 1;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.wanandroid.com/project/list/")
                .build();
        WandroidService wandroidService = retrofit.create(WandroidService.class);
        //接口参数 page cid
        Call<ResponseBody> call = wandroidService.getProjectArticle(page, intProjectCategoryCID);

        //网络请求
        call.enqueue(new Callback<ResponseBody>() {
            //网络请求成功时调用
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String strBack = response.body().string();
                    //Log.d(TAG, "onFailure: 请求成功\n" + intProjectCategoryCID + "返回的数据如下\n" + strBack);
                    //处理数据
                    JSONObject jsonObject = null;
                    ProjectContentBean projectContentBean;
                    jsonObject = new JSONObject(strBack);
                    Gson gson = new Gson();
                    projectContentBean = gson.fromJson(jsonObject.toString(), ProjectContentBean.class);
                    /**获取里面的数据
                     * 打印出来试试 看是否操作成功
                     * 以获取近五天的天气为例
                     */
                    //获取本次结果的文章数量
                    int articleCount = projectContentBean.getData().getDatas().size();
                    for (int i = 0; i < articleCount; i++) {
                        ProjectArticleBean projectArticleBean = new ProjectArticleBean(
                                "" + projectContentBean.getData().getDatas().get(i).getAuthor(),
                                "" + projectContentBean.getData().getDatas().get(i).getTitle(),
                                "" + projectContentBean.getData().getDatas().get(i).getPublishTime(),
                                "" + projectContentBean.getData().getDatas().get(i).getDesc(),
                                "" + projectContentBean.getData().getDatas().get(i).getEnvelopePic()
                        );
                        //使用工具类
                        // LogUtil.d(TAG, projectArticleBean.outString());
                        mList.add(projectArticleBean);
                    }

                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }

            //网络请求失败时调用
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(TAG, "onFailure: 请求失败");
            }
        });

        initData();//手动在调用一次initData 适用于初次没有数据 加载完数据后在对数据进行UI操作
    }

}
