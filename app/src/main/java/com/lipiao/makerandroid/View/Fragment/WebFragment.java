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
import com.lipiao.makerandroid.R;
import com.lipiao.makerandroid.Utils.LogUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * 项目文章使用得web碎片
 * 基于AgentWeb+Fragment
 */
public class WebFragment extends Fragment {
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

    public static WebFragment newInstance(String webURL) {
        WebFragment fragment = new WebFragment();
        Bundle bundle = new Bundle();
        bundle.putString("webURL", webURL);
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
        //demoForRetrofit();
        //agentWeb();

        return rootView;
    }


    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {


            switch (v.getId()) {
                case R.id.iv_back:
                    // true表示AgentWeb处理了该事件
                    if (!mAgentWeb.back()) {
                        WebFragment.this.getActivity().finish();
                    }
                    break;
                case R.id.iv_finish:
                    WebFragment.this.getActivity().finish();
                    break;
                case R.id.iv_more:
                    showPoPup(v);
                    break;
                default:
                    break;

            }
        }

    };

    /**
     * 显示更多菜单
     *
     * @param view 菜单依附在该View下面
     */
    private void showPoPup(View view) {
        if (mPopupMenu == null) {
            mPopupMenu = new PopupMenu(this.getActivity(), view);
            mPopupMenu.inflate(R.menu.toolbar_menu);
            mPopupMenu.setOnMenuItemClickListener(mOnMenuItemClickListener);
        }
        mPopupMenu.show();
    }

    /**
     * 菜单事件
     */
    private PopupMenu.OnMenuItemClickListener mOnMenuItemClickListener = new PopupMenu.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {

            switch (item.getItemId()) {

                case R.id.refresh:
                    if (mAgentWeb != null) {
                        mAgentWeb.getUrlLoader().reload(); // 刷新
                    }
                    return true;

                case R.id.copy:
                    if (mAgentWeb != null) {
                        toCopy(WebFragment.this.getContext(), mAgentWeb.getWebCreator().getWebView().getUrl());
                    }
                    return true;
                case R.id.default_browser:
                    if (mAgentWeb != null) {
                        openBrowser(mAgentWeb.getWebCreator().getWebView().getUrl());
                    }
                    return true;
                case R.id.default_clean:
                    toCleanWebCache();
                    return true;
                case R.id.error_website:
                    loadErrorWebSite();
                    return true;
                default:
                    return false;
            }

        }
    };

    /**
     * 测试错误页的显示
     */
    private void loadErrorWebSite() {
        if (mAgentWeb != null) {
            mAgentWeb.getUrlLoader().loadUrl("http://www.unkownwebsiteblog.me");
        }
    }

    /**
     * 清除 WebView 缓存
     */
    private void toCleanWebCache() {

        if (this.mAgentWeb != null) {

            //清理所有跟WebView相关的缓存 ，数据库， 历史记录 等。
            this.mAgentWeb.clearWebCache();
            Toast.makeText(getActivity(), "已清理缓存", Toast.LENGTH_SHORT).show();
            //清空所有 AgentWeb 硬盘缓存，包括 WebView 的缓存 , AgentWeb 下载的图片 ，视频 ，apk 等文件。
//            AgentWebConfig.clearDiskCache(this.getContext());
        }

    }

    /**
     * 复制字符串
     *
     * @param context
     * @param text
     */
    private void toCopy(Context context, String text) {

        ClipboardManager mClipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        mClipboardManager.setPrimaryClip(ClipData.newPlainText(null, text));

    }

    /**
     * 打开浏览器
     *
     * @param targetUrl 外部浏览器打开的地址
     */
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


    /**
     * onDestroyView中进行解绑操作
     */
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