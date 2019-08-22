package com.lipiao.makerandroid;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.lipiao.makerandroid.View.Fragment.LeadFragment;
import com.lipiao.makerandroid.View.Fragment.MainFragment;
import com.lipiao.makerandroid.View.Fragment.ProjectFragment;
import com.lipiao.makerandroid.View.Fragment.SystemFragment;
import com.lipiao.makerandroid.View.Fragment.UserFragment;
import com.ycl.tabview.library.TabView;
import com.ycl.tabview.library.TabViewChild;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.tabView)
    TabView tabView;

    List<TabViewChild> tabViewChildList = new ArrayList<>();
    private long lastClickBackTime = System.currentTimeMillis() - 3000;
    private List<Fragment> mList = new ArrayList<>();
    private MainFragment mainFragment;
    private ProjectFragment projectFragment;
    private LeadFragment leadFragment;
    //private SystemFragment systemFragment;
    private UserFragment userFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //绑定初始化ButterKnife
        ButterKnife.bind(this);

        //先判空后添加
        if (mainFragment == null) {
            mainFragment = MainFragment.newInstance();
            tabViewChildList.add(new TabViewChild(R.mipmap.main, R.mipmap.main_no, "首页", mainFragment));
        }
        if (leadFragment == null) {
            leadFragment = leadFragment.newInstance();
            tabViewChildList.add(new TabViewChild(R.mipmap.lead, R.mipmap.lead_no, "导向", leadFragment));
        }
        if (projectFragment == null) {
            projectFragment = ProjectFragment.newInstance();
            tabViewChildList.add(new TabViewChild(R.mipmap.project, R.mipmap.project_no, "项目", projectFragment));
        }
        if (userFragment == null) {
            userFragment = userFragment.newInstance();
            tabViewChildList.add(new TabViewChild(R.mipmap.user, R.mipmap.user_no, "我的", userFragment));
        }
        //getSupportFragmentManager() 需要基类继承自AppCompatActivity
        tabView.setTabViewChild(tabViewChildList, getSupportFragmentManager());

    }

    //退出提醒 两次back退出
    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - lastClickBackTime > 2000) { // 后退阻断
            Toast.makeText(this, "再点一次退出", Toast.LENGTH_LONG).show();
            lastClickBackTime = System.currentTimeMillis();
        } else { // 关掉app
            //System.exit(0);//完全退出  再次启动很慢
            finish();//保留进程 再次启动较快
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
