package com.lipiao.makerandroid.View.Fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lipiao.makerandroid.Base.LazyLoadFragment;
import com.lipiao.makerandroid.Bean.RvAboutBean;
import com.lipiao.makerandroid.R;
import com.lipiao.makerandroid.Utils.GlideImageLoader;
import com.lipiao.makerandroid.Utils.LogUtil;
import com.lipiao.makerandroid.View.Activity.WebActivity;
import com.lipiao.makerandroid.View.Adapter.AboutAdapter;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

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

    String TAG="UserFragment";
    RecyclerView rvAbout;//关于
    RecyclerView rvCollect;//收藏
    AboutAdapter aboutAdapter;
    List<RvAboutBean> rvAboutBeanList = new ArrayList<>();
    Banner banner;
    ArrayList<String> images = new ArrayList<>();//图片资源集合

    public UserFragment() {
        // Required empty public constructor
    }

    public static UserFragment newInstance() {
        return new UserFragment();
    }

    //选择父布局文件
    @Override
    protected int attachLayoutId() {
        return R.layout.fragment_user;
    }

    @Override
    protected void initView(View root) {
        banner=root.findViewById(R.id.user_fragment_banner);
        rvAbout = root.findViewById(R.id.rv_user_fragment_about);
        rvCollect = root.findViewById(R.id.rv_user_fragment_collect);
    }

    @Override
    protected void initData() throws NullPointerException {
        LogUtil.d(TAG,"initData");
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
        RvAboutBean iotAssociation=new RvAboutBean(R.mipmap.iot,"湖科大物联网协会","https://blog.csdn.net/qq_42391904");
        RvAboutBean huaweiHero=new RvAboutBean(R.mipmap.huawei,"湖科大华为HERO联盟","https://developer.huaweicloud.com/hero/forum.php?mod=group&fid=831");
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

    }


}
