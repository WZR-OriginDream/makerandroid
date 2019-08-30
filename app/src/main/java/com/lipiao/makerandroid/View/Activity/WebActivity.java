package com.lipiao.makerandroid.View.Activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.lipiao.makerandroid.R;
import com.lipiao.makerandroid.View.Fragment.WebFragment;


public class WebActivity extends AppCompatActivity {
    //文章链接
    String webURL;
    String TAG = "WebActivity";
    WebFragment webFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        Intent intent = getIntent();
        webURL = intent.getStringExtra("webURL");
        if (webFragment == null) {
            replaceFragment(webFragment = WebFragment.newInstance(webURL));
        }

    }

    private void replaceFragment(Fragment fragment) {
        /**
         * https://blog.csdn.net/look_Future/article/details/79658861
         * Fragment的生命周期和FragmentTransaction的主要方法
         */
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //add(id, fragment) —— 增加framgent到队列中，并显示该fragment到指定布局中。
        fragmentTransaction.add(R.id.web_fragment, fragment);
        fragmentTransaction.commit();
    }
}