package com.lipiao.makerandroid.View.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lipiao.makerandroid.Base.LazyLoadFragment;
import com.lipiao.makerandroid.Bean.ProjectArticleBean;
import com.lipiao.makerandroid.R;
import com.lipiao.makerandroid.View.Adapter.ProjectArticleAdapter;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

//项目分类 一个类别生成一个碎片
public class ProjectCategoryFragment extends LazyLoadFragment {
    View rootView;
    String TAG = "ProjectCategoryFragment";
    int intProjectCategoryCID;//项目种类编号，访问接口需要
    //碎片中使用butterknife略有不同
    private Unbinder unbinder;


    //@BindView(R.id.rv_fragment_system)
    RecyclerView mRecyclerView;

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


//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        //fragment_project_category可与fragment_system复用
//        rootView = inflater.inflate(R.layout.fragment_system, container, false);
//        //返回一个Unbinder值（进行解绑），注意这里的this不能使用getActivity()
//        unbinder = ButterKnife.bind(this, rootView);
//        //获取fragmentKind类型值 判断是初始化何种碎片
//        intProjectCategoryCID = getArguments().getInt("categoryCid");
//        initData();
//        return rootView;
//    }

    @Override
    protected void injectPresenter() {
        Log.d(TAG, "injectPresenter: ");
    }

    @Override
    protected int attachLayoutId() {
        Log.d(TAG, "attachLayoutId: ");
        return R.layout.fragment_system;
    }

    //初始化布局 findviewbyid
    @Override
    protected void initView(View root) {
        Log.d(TAG, "initView: ");
        intProjectCategoryCID = getArguments().getInt("categoryCid");
        mRecyclerView=root.findViewById(R.id.rv_fragment_system);
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
//为RecyclerView对象mRecyclerView设置adapter
        mRecyclerView.setAdapter(projectArticleAdapter);
        projectArticleAdapter.notifyDataSetChanged();
    }

    @Override
    public void fetchData() {
        Log.d(TAG, intProjectCategoryCID+"fetchData: f1的网络请求");
        ProjectArticleBean projectArticleBean = new ProjectArticleBean(
                "" + "星蔚",
                "" + "文章标题demo",
                "" + "2019.08.26",
                "" + "这是个wanandroid项目",
                "" + "https://img-blog.csdnimg.cn/20190806195819928.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQyMzkxOTA0,size_16,color_FFFFFF,t_70"
        );
        mList.add(projectArticleBean);
       // mRecyclerView.notifyAll();
    }

//    private void initData() {
//        mRecyclerView.setHasFixedSize(true);
//        //平常的水平一个item布局的流
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
//        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        mRecyclerView.setLayoutManager(layoutManager);
//
////初始化mList
//        initArticleBeanList();
////实例化MyAdapter并传入mList对象
//        projectArticleAdapter = new ProjectArticleAdapter(mList, getContext());
////为RecyclerView对象mRecyclerView设置adapter
//        mRecyclerView.setAdapter(projectArticleAdapter);
//
//    }
//
//    private void initArticleBeanList() {
////        for (int i = 0; i < 10; i++) {
//        ProjectArticleBean projectArticleBean = new ProjectArticleBean(
//                "" + "星蔚",
//                "" + "文章标题demo",
//                "" + "2019.08.26",
//                "" + "这是个wanandroid项目",
//                "" + "https://img-blog.csdnimg.cn/20190806195819928.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQyMzkxOTA0,size_16,color_FFFFFF,t_70"
//        );
//        mList.add(projectArticleBean);
////        }
//    }


//    private void initArticleBeanList() {
//        // ArticleBean articleBean1 = new ArticleBean("星蔚", "Android基础-四大组件之Service（基础）", "2019年07月11日", "四大组件");
//        //        ArticleBean articleBean2 = new ArticleBean("星蔚", "Android基础-四大组件之activity（基础）", "2019年07月11日", "四大组件");
//        //        mList.add(articleBean1);
//        //        mList.add(articleBean2);
//
//        //初始化网络请求https://www.wanandroid.com/project/list/1/json?cid=294
//        int page = 1;
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("https://www.wanandroid.com/project/list/")
//                .build();
//        WandroidService wandroidService = retrofit.create(WandroidService.class);
//        //接口参数 page cid
//        Call<ResponseBody> call = wandroidService.getProjectArticle(page,intProjectCategoryCID);
//
//        //网络请求
//        call.enqueue(new Callback<ResponseBody>() {
//            //网络请求成功时调用
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                try {
//                    String strBack = response.body().string();
//                    Log.d(TAG, "onFailure: 请求成功\n"+intProjectCategoryCID + "返回的数据如下\n" + strBack);
//                    //处理数据
//                    JSONObject jsonObject = null;
//                    ProjectContentBean projectContentBean;
//                    jsonObject = new JSONObject(strBack);
//                    Gson gson = new Gson();
//                    projectContentBean = gson.fromJson(jsonObject.toString(), ProjectContentBean.class);
//                    /**获取里面的数据
//                     * 打印出来试试 看是否操作成功
//                     * 以获取近五天的天气为例
//                     */
//                    //获取本次结果的文章数量
//                    int articleAcount=projectContentBean.getData().getSize();
//                    for (int i=0;i<articleAcount;i++){
//                        ProjectArticleBean projectArticleBean=new ProjectArticleBean(
//                                ""+projectContentBean.getData().getDatas().get(i).getAuthor(),
//                                ""+projectContentBean.getData().getDatas().get(i).getTitle(),
//                                ""+projectContentBean.getData().getDatas().get(i).getPublishTime(),
//                                ""+projectContentBean.getData().getDatas().get(i).getDesc(),
//                                ""+projectContentBean.getData().getDatas().get(i).getEnvelopePic()
//                        );
//                        mList.add(projectArticleBean);
//                    }
//
//
//
//                } catch (IOException | JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            //网络请求失败时调用
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                Log.d(TAG, "onFailure: 请求失败");
//            }
//        });
//    }
}
