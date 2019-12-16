package com.lipiao.makerandroid.View.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.lipiao.makerandroid.Bean.CollectArticleBean;
import com.lipiao.makerandroid.Bean.RvAboutBean;
import com.lipiao.makerandroid.R;
import com.lipiao.makerandroid.Utils.GlideImageLoader;
import com.lipiao.makerandroid.Utils.LogUtil;
import com.lipiao.makerandroid.Utils.SqliteUtils;
import com.lipiao.makerandroid.View.Activity.WebActivity;
import com.lipiao.makerandroid.View.Adapter.AboutAdapter;
import com.lipiao.makerandroid.View.NoScrollListView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 用户界面碎片
 * 包含几个简要信息
 * 博客地址
 * Maker-IoT官网地址
 */
public class UserFragment extends Fragment {

    String TAG = "UserFragment";
    View rootView;
    RecyclerView rvAbout;//关于
    NoScrollListView lvCollect;//收藏 不可滑动listView
    AboutAdapter aboutAdapter;
    List<RvAboutBean> rvAboutBeanList = new ArrayList<>();
    Banner banner;//头部banner 轮播一些团队照片
    ArrayList<String> images = new ArrayList<>();//图片资源集合

    static List<CollectArticleBean> collectArticleBeanList = new ArrayList<>();//收藏文章的集合
    static List<String> arrStr;//收藏文章的标题 字符串数组
    AlertDialog alertDialog;
    static ArrayAdapter<String> adapter;

    public UserFragment() {
        // Required empty public constructor
    }

    public static UserFragment newInstance() {
        return new UserFragment();
    }

    //创建碎片视图
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_user, container, false);
        initView();
        initData();
        fetchData();
        return rootView;
    }

    //重新获取焦点时 重新加载数据（因为可能用户有新的收藏文章 需要重新从sqLite数据库中查询并显示）
    @Override
    public void onResume() {
        super.onResume();
        initData();
        fetchData();
    }

    //碎片是否可见 可见下重新加载数据
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.d(TAG, "setUserVisibleHint: " + isVisibleToUser);
        if (isVisibleToUser) {
            //用户可见 获取数据并将数据放到视图控件中
            initData();
            fetchData();
        }
    }

    //实例化控件
    public void initView() {
        banner = rootView.findViewById(R.id.user_fragment_banner);
        rvAbout = rootView.findViewById(R.id.rv_user_fragment_about);
        lvCollect = rootView.findViewById(R.id.lv_collect_article);
    }

    //获取数据
    protected void initData() throws NullPointerException {
        LogUtil.d(TAG, "initData");

        //初始化收藏的文章
        collectArticleBeanList = new ArrayList<>();//空初始化收藏的文章集合
        collectArticleBeanList = SqliteUtils.queryAll();

        //空初始化 关于集合
        rvAboutBeanList = new ArrayList<>();
        //images
        images.add("https://m.qpic.cn/psb?/V11CZbvM3uqJkN/IyHQgHfi5z4gd4Y3YE5KHYSAqNg5flnXUyQnhXG8Avw!/b/dLYAAAAAAAAA&bo=gAegBYAHoAURBzA!&rf=viewer_4");
        images.add("https://m.qpic.cn/psb?/V11CZbvM3uqJkN/TuYokHTU9arCWsbljxyI7ncMvG2WZ3prhC56WhmNkhI!/b/dEgBAAAAAAAA&bo=wAOAAsADgAIRFyA!&rf=viewer_4");
        images.add("https://m.qpic.cn/psb?/V11CZbvM3uqJkN/o*Muhty0A0D*7kh0ckdk1wurKk1*jUvXcFbiRyM880M!/b/dE0BAAAAAAAA&bo=wAOAAsADgAIRFyA!&rf=viewer_4");
        images.add("https://m.qpic.cn/psb?/V11CZbvM3uqJkN/tx4y8qUlR8geQQWfOXQSW7hhanEZd*CfN5fs9mVihjk!/b/dDYBAAAAAAAA&bo=wAOAAsADgAIRFyA!&rf=viewer_4");

        banner.setImages(images).setImageLoader(new GlideImageLoader());
        //设置banner样式 不显示指示器和标题
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置轮播时间
        banner.setDelayTime(2500);
        banner.start();

        //top rv
        rvAbout.setHasFixedSize(true);
        //平常的水平一个item布局的流
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvAbout.setLayoutManager(layoutManager);
//初始化mList
        //https://developer.huaweicloud.com/hero/forum.php?mod=group&fid=831
        RvAboutBean iotAssociation = new RvAboutBean(R.mipmap.iot, "湖科大物联网协会", "https://blog.csdn.net/qq_42391904");
        RvAboutBean huaweiHero = new RvAboutBean(R.mipmap.huawei, "湖科大华为HERO联盟", "https://developer.huaweicloud.com/hero/forum.php?mod=group&fid=831");
        RvAboutBean myBlog = new RvAboutBean(R.mipmap.csdn, "CSDN博客", "https://blog.csdn.net/qq_42391904");
        RvAboutBean Maker = new RvAboutBean(R.mipmap.makerlogo, "Maker-IoT官网", "https://blog.csdn.net/qq_42391904");
        RvAboutBean mayun = new RvAboutBean(R.mipmap.mayun, "码云首页", "https://gitee.com/lipiaoMKX");
        RvAboutBean hexo = new RvAboutBean(R.mipmap.me, "个人博客网站", "https://lipiaomkx.gitee.io/");

        rvAboutBeanList.add(Maker);
        rvAboutBeanList.add(huaweiHero);
        rvAboutBeanList.add(iotAssociation);
        rvAboutBeanList.add(myBlog);
        rvAboutBeanList.add(mayun);
        rvAboutBeanList.add(hexo);
        //实例化MyAdapter并传入rvAboutBeanList对象
        aboutAdapter = new AboutAdapter(rvAboutBeanList, getContext());

        //自定义点击事件
        aboutAdapter.setOnAboutItemClickListener((view, position) -> {
            //根据URL创建web活动
            Intent intent = new Intent(getActivity(), WebActivity.class);
            intent.putExtra("webURL", rvAboutBeanList.get(position).getAboutUrlStr());
            startActivity(intent);
        });
        //为RecyclerView对象mRecyclerView设置adapter
        rvAbout.setAdapter(aboutAdapter);

    }

    //将数据放到视图控件中
    public void fetchData() {
        //处理前判空
        if (collectArticleBeanList.size() != 0) {
            Log.d(TAG, "fetchData: 收藏文章listview加载");
            //初始化一个大小和收藏文章集合大小一致的 字符串数组
            arrStr = new ArrayList<>();
            for (CollectArticleBean collectArticleBean : collectArticleBeanList) {
                arrStr.add(collectArticleBean.getCollectTitles());//i 先用后加1
            }
            adapter = new ArrayAdapter<String>(Objects.requireNonNull(getActivity()),
                    android.R.layout.simple_list_item_1, arrStr);//新建并配置ArrayAapeter
            lvCollect.setAdapter(adapter);
            setListViewHeightBasedOnChildren(lvCollect);//解决ScrollView嵌套ListView只显示一条的问题
            //点击事件
            lvCollect.setOnItemClickListener((adapterView, view, i1, l) -> {
                //根据URL创建web活动
                Intent intent = new Intent(getActivity(), WebActivity.class);
                intent.putExtra("webURL", collectArticleBeanList.get(i1).getCollectUrl());
                startActivity(intent);
            });
            //长按事件
            lvCollect.setOnItemLongClickListener((adapterView, view, i14, l) -> {
                alertDialog = new AlertDialog.Builder(Objects.requireNonNull(UserFragment.this.getActivity()))
                        .setMessage("是否取消收藏该文章 ?")
                        .setPositiveButton("确定", (dialogInterface, i13) -> {
                            //删除数据 提示插入是否成功 隐藏对话框
                            String backStr = SqliteUtils.delete(collectArticleBeanList.get(i14).getCollectUrl());
                            Toast.makeText(UserFragment.this.getContext(), backStr, Toast.LENGTH_SHORT).show();
                            //删除数据源后 更新recycleView界面
                            Log.d(TAG, "fetchData: " + i14);
                            arrStr.remove(i14);//删除数据源,移除集合中当前下标的数据
                            adapter.notifyDataSetChanged();//刷新被删除的地方
                        })
                        .setNegativeButton("取消", (dialogInterface, i12) -> {
                            //只隐藏对话框即可
                            alertDialog.hide();
                        })
                        .create();
                alertDialog.show();
                return true;//表示此事件已经消费，不会触发单击事件
            });
        }//判空if

    }

    /**
     * 解决ScrollView嵌套ListView只显示一条的问题
     *
     * @param listView
     */
    public void setListViewHeightBasedOnChildren(ListView listView) {
        // 获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
            // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            // 计算子项View 的宽高
            listItem.measure(0, 0);
            // 统计所有子项的总高度
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() *
                (listAdapter.getCount() - 1));
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
        listView.invalidate();
    }

}
