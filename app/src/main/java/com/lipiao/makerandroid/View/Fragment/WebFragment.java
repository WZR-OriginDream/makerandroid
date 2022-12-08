package com.lipiao.makerandroid.View.Fragment;


import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.PopupMenu;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.just.agentweb.AgentWeb;
import com.just.agentweb.AgentWebConfig;
import com.just.agentweb.DefaultWebClient;
import com.just.agentweb.WebChromeClient;
import com.lipiao.makerandroid.Bean.RespondBean.MessageBean;
import com.lipiao.makerandroid.Bean.RespondBean.UserCollectArticleBean;
import com.lipiao.makerandroid.R;
import com.lipiao.makerandroid.Utils.HttpUtil;
import com.lipiao.makerandroid.Utils.LogUtil;
import com.lipiao.makerandroid.Utils.SqliteUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * 项目文章使用得web碎片
 * 基于AgentWeb+Fragment
 */
public class WebFragment extends Fragment {

    String userNumber;//WebActivity传来的userNumber
    View rootView;
    String TAG = "WebFragment";
    String strWebURL;//文章url
    //碎片中使用butterknife略有不同
    private Unbinder unbinder;

    AgentWeb mAgentWeb;
    @BindView(R.id.ll_agent_web)
    LinearLayout linearLayout;


    @BindView(R.id.iv_back)
    ImageView mBackImageView;
    @BindView(R.id.iv_finish)
    ImageView mFinishImageView;
    @BindView(R.id.toolbar_title)
    TextView mTitleTextView;
    @BindView(R.id.iv_more)
    ImageView mMoreImageView;

    private PopupMenu mPopupMenu;

    public WebFragment() {
        // Required empty public constructor
    }

    public static WebFragment newInstance(String webURL,String userNumber) {
        WebFragment fragment = new WebFragment();
        Bundle bundle = new Bundle();
        bundle.putString("webURL", webURL);
        bundle.putString("userNumber", userNumber);
        fragment.setArguments(bundle);
        return fragment;
    }

    //使用agent web
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LogUtil.d(TAG, strWebURL);
        //可用
        mAgentWeb = AgentWeb.with(this)//
                .setAgentWebParent((LinearLayout) linearLayout, -1, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))//传入AgentWeb的父控件。
                .useDefaultIndicator(-1, 3)//设置进度条颜色与高度，-1为默认值，高度为2，单位为dp。
                .setWebChromeClient(mWebChromeClient)
                .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK) //严格模式 Android 4.2.2 以下会放弃注入对象 ，使用AgentWebView没影响。
                .setMainFrameErrorView(R.layout.agentweb_error_page, -1) //参数1是错误显示的布局，参数2点击刷新控件ID -1表示点击整个布局都刷新， AgentWeb 3.0.0 加入。
                .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.ASK)//打开其他页面时，弹窗质询用户前往其他应用 AgentWeb 3.0.0 加入。
                .interceptUnkownUrl() //拦截找不到相关页面的Url AgentWeb 3.0.0 加入。
                .createAgentWeb()//创建AgentWeb。
                .ready()//设置 WebSettings。
                .go(strWebURL); //WebView载入该url地址的页面并显示。

        AgentWebConfig.debug();

        initView(view);

    }

    private void initView(View view) {
        mBackImageView.setOnClickListener(mOnClickListener);
        mFinishImageView.setOnClickListener(mOnClickListener);
        mMoreImageView.setOnClickListener(mOnClickListener);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_web, container, false);
        //返回一个Unbinder值（进行解绑），注意这里的this不能使用getActivity()
        unbinder = ButterKnife.bind(this, rootView);
        strWebURL = getArguments().getString("webURL");
        userNumber=getArguments().getString("userNumber");
        Log.d(TAG, "onCreateView: userNumber "+userNumber);
        return rootView;
    }


    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.iv_back://返回
                    // true表示AgentWeb处理了该事件
                    if (!mAgentWeb.back()) {
                        WebFragment.this.getActivity().finish();
                    }
                    break;
                case R.id.iv_finish://关闭此网页
                    WebFragment.this.getActivity().finish();
                    break;
                case R.id.iv_more://弹出菜单
                    showPoPup(v);
                    break;
                default:
                    break;

            }
        }

    };

    //显示更多菜单
    private void showPoPup(View view) {
        if (mPopupMenu == null) {
            mPopupMenu = new PopupMenu(this.getActivity(), view);
            mPopupMenu.inflate(R.menu.toolbar_menu);
            mPopupMenu.setOnMenuItemClickListener(mOnMenuItemClickListener);
        }
        mPopupMenu.show();
    }

    //菜单对应的点击事件
    private PopupMenu.OnMenuItemClickListener mOnMenuItemClickListener = new PopupMenu.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.collect_article://收藏文章
                    if (mAgentWeb != null) {
                        String collectUrl = mAgentWeb.getWebCreator().getWebView().getUrl();//网址
                        String collectTitles = mAgentWeb.getWebCreator().getWebView().getTitle();//网页标题
                        //插入
                        Log.d(TAG, "onMenuItemClick: " + collectUrl + " " + collectTitles);
                        String backStr = SqliteUtils.insert(collectUrl, collectTitles);
//                        Call<MessageBean> callBanner = HttpUtil.getUserService().addArticle(userNumber, collectUrl, collectTitles);
//                        callBanner.enqueue(new Callback<MessageBean>() {
//                            @Override
//                            public void onResponse(Call<MessageBean> call, Response<MessageBean> response) {
//                                Log.d(TAG, "onResponse: " + response.body().getMessage());
//                            }
//
//                            @Override
//                            public void onFailure(Call<MessageBean> call, Throwable t) {
//
//                            }
//                        });
                        Toast.makeText(getContext(), backStr, Toast.LENGTH_SHORT).show();
                    }
                    return true;
                case R.id.copy://复制文章链接
                    if (mAgentWeb != null) {
                        toCopy(WebFragment.this.getContext(), mAgentWeb.getWebCreator().getWebView().getUrl());
                    }
                    return true;
                case R.id.default_browser://使用系统浏览器打开
                    if (mAgentWeb != null) {
                        openBrowser(mAgentWeb.getWebCreator().getWebView().getUrl());
                    }
                    return true;
                default:
                    return false;
            }

        }
    };


    //复制字符串 用系统浏览器打开前 先复制链接
    private void toCopy(Context context, String text) {
        ClipboardManager mClipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        mClipboardManager.setPrimaryClip(ClipData.newPlainText(null, text));
    }

    //打开浏览器 使用隐式意图 设置其动作Action为android.intent.action.VIEW打开浏览器
    private void openBrowser(String targetUrl) {
        if (TextUtils.isEmpty(targetUrl) || targetUrl.startsWith("file://")) {
            Toast.makeText(this.getContext(), targetUrl + " 该链接无法使用浏览器打开。", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri mUri = Uri.parse(targetUrl);
        intent.setData(mUri);
        startActivity(intent);
    }

    //agentWeb固定写法
    protected com.just.agentweb.WebChromeClient mWebChromeClient = new WebChromeClient() {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            //  super.onProgressChanged(view, newProgress);
            Log.i(TAG, "onProgressChanged:" + newProgress + "  view:" + view);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            if (mTitleTextView != null && !TextUtils.isEmpty(title)) {
                //若网页标题大于10的字符长度，则截取前十字符，后面内容用...代替
                if (title.length() > 10) {
                    title = title.substring(0, 10).concat("...");
                }
            }
            mTitleTextView.setText(title);
        }
    };


    //onDestroyView中进行unbind解绑操作
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mAgentWeb.getWebLifeCycle().onDestroy();
        unbinder.unbind();
    }

    @Override
    public void onResume() {
        mAgentWeb.getWebLifeCycle().onResume();//恢复
        super.onResume();
    }

    @Override
    public void onPause() {
        mAgentWeb.getWebLifeCycle().onPause(); //暂停应用内所有WebView ， 调用mWebView.resumeTimers();/mAgentWeb.getWebLifeCycle().onResume(); 恢复。
        super.onPause();
    }

}