package com.lipiao.makerandroid.View.Fragment;

import android.view.View;
import com.lipiao.makerandroid.Base.BaseFragment;
import com.lipiao.makerandroid.R;

/**
 * 用户界面碎片
 * 基于BaseFragment
 * 包含几个简要信息
 * 开发作者及博客地址
 * Maker-IoT官网地址
 * 使用的技术栈
 *
 */
public class UserFragment extends BaseFragment {

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
//        root.findViewById(R.id.iv_project_article_preview);

    }

    @Override
    protected void initData() throws NullPointerException {

    }
}
