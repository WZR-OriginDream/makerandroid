package com.lipiao.makerandroid.View.Fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.lipiao.makerandroid.Base.LazyLoadFragment;
import com.lipiao.makerandroid.Bean.RvAboutBean;
import com.lipiao.makerandroid.R;
import com.lipiao.makerandroid.View.Activity.WebActivity;
import com.lipiao.makerandroid.View.Adapter.AboutAdapter;
import com.lipiao.makerandroid.View.Adapter.ProjectArticleAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户界面碎片
 * 基于BaseFragment
 * 包含几个简要信息
 * 开发作者及博客地址
 * Maker-IoT官网地址
 * 使用的主要技术栈
 */
public class UserFragment extends LazyLoadFragment {


    RecyclerView rvAbout;//关于
    RecyclerView rvCollect;//收藏
    AboutAdapter aboutAdapter;
    List<RvAboutBean> rvAboutBeanList = new ArrayList<>();

    public UserFragment() {
        // Required empty public constructor
    }

    public static UserFragment newInstance() {
        return new UserFragment();
    }

    //选择表示层 暂时不用
    @Override
    protected void injectPresenter() {

    }

    //选择父布局文件
    @Override
    protected int attachLayoutId() {
        return R.layout.fragment_user;
    }

    @Override
    protected void initView(View root) {

        rvAbout = root.findViewById(R.id.rv_user_fragment_about);
        rvCollect = root.findViewById(R.id.rv_user_fragment_collect);

    }

    @Override
    protected void initData() throws NullPointerException {

        //top rv
        rvAbout.setHasFixedSize(true);
        //平常的水平一个item布局的流
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvAbout.setLayoutManager(layoutManager);
//初始化mList

        RvAboutBean myBlog = new RvAboutBean(R.mipmap.csdn, "csdn博客", "https://blog.csdn.net/qq_42391904");
        RvAboutBean Maker = new RvAboutBean(R.mipmap.makerlogo, "Maker-IoT官网", "https://www.baidu.com/");
        RvAboutBean mayun = new RvAboutBean(R.mipmap.mayun, "码云首页", "https://gitee.com/lipiaoMKX");
        RvAboutBean hexo = new RvAboutBean(R.mipmap.me, "个人博客网站", "https://lipiaomkx.gitee.io/");
        rvAboutBeanList.add(myBlog);
        rvAboutBeanList.add(Maker);
        rvAboutBeanList.add(mayun);
        rvAboutBeanList.add(hexo);
        //实例化MyAdapter并传入rvAboutBeanList对象
        aboutAdapter = new AboutAdapter(rvAboutBeanList, getContext());

        //自定义点击事件
        aboutAdapter.setOnAboutItemClickListener((view, position) -> {
            //单击
            //根据URL创建web活动
            //Toast.makeText(getContext(), "点击事件：查看" + mList.get(position).getWebURL(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getActivity(), WebActivity.class);
            intent.putExtra("webURL", rvAboutBeanList.get(position).getAboutUrlStr());
            startActivity(intent);
        });
        //为RecyclerView对象mRecyclerView设置adapter
        rvAbout.setAdapter(aboutAdapter);
    }

    @Override
    public void fetchData() {
//        RvAboutBean myBlog=new RvAboutBean(R.mipmap.csdn,"csdn博客","https://blog.csdn.net/qq_42391904");
//        RvAboutBean Maker=new RvAboutBean(R.mipmap.makerlogo,"Maker-IoT官网","https://www.baidu.com/");
//        RvAboutBean mayun=new RvAboutBean(R.mipmap.mayun,"码云首页","https://gitee.com/lipiaoMKX");
//        RvAboutBean hexo=new RvAboutBean(R.mipmap.me,"个人博客网站","http://lipiaomkx.gitee.io/");
//        rvAboutBeanList.add(myBlog);
//        rvAboutBeanList.add(Maker);
//        rvAboutBeanList.add(mayun);
//        rvAboutBeanList.add(hexo);

    }


}
