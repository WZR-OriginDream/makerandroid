package com.lipiao.makerandroid.View.Activity;


import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.lipiao.makerandroid.Bean.RespondBean.MessageBean;
import com.lipiao.makerandroid.MainActivity;
import com.lipiao.makerandroid.R;
import com.lipiao.makerandroid.SharedPreferences.SaveAccount;
import com.lipiao.makerandroid.Utils.HttpUtil;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


//登陆
public class LoginActivity extends AppCompatActivity {
    static final String TAG = "LoginActivity";

    Map<String, String> userInfo;
    FloatingActionButton floatingActionButton;//登陆按钮
    static Context context;
    EditText etUsername;
    EditText etPassword;
    String userNumber;//账号
    String password;//密码
    private long lastClickBackTime = System.currentTimeMillis() - 3000;//三秒间隔 间隔内两次返回提示用户是否退出

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = getBaseContext();
        userInfo = SaveAccount.getUserInfo(this);//获取SharedPreferences中的数据
        // SharedPreferences 70行保存
        //初始化 全局网络请求工具类 时间戳格式化工具类  SQLite数据库工具类 均为单例
        initView();
        HttpUtil.getInstance();
    }

    private void initView() {
        floatingActionButton = findViewById(R.id.fb_login);
        etUsername = findViewById(R.id.et_user_number_login);
        etPassword = findViewById(R.id.et_password_login);
        etUsername.setText(userInfo.get("userNumber"));
        etPassword.setText(userInfo.get("password"));

    }

    //登录按钮 onclick属性对应的函数
    public void login(View view) {
        userNumber = etUsername.getText().toString().trim();
        password = etPassword.getText().toString().trim();
        userInfo = SaveAccount.getUserInfo(this);
        Log.d(TAG,userInfo.get("userNumber")+"  "+userInfo.get("password"));
        if(userNumber.equals(userInfo.get("userNumber"))&&password.equals(userInfo.get("password"))){
            Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(context, MainActivity.class);
            intent.putExtra("userNumber", userNumber);
            intent.putExtra("password", password);
            startActivity(intent);
            finish();
        }else {
            Toast.makeText(context, "输入错误，请重新输入！", Toast.LENGTH_LONG).show();
            etUsername.setText("");
            etPassword.setText("");
        }

        //Log.d("LoginActivity", "login: " + userNumber + password);
//        Call<MessageBean> call = HttpUtil.getUserService().login(userNumber, password);
//        call.enqueue(new Callback<MessageBean>() {
//            @Override
//            public void onResponse(Call<MessageBean> call, Response<MessageBean> response) {
////                Log.d(TAG, "onResponse: " + response.body().getMessage());
//                String strBack = response.body().getMessage();
//                if (strBack.equals("loginSuccess")) {
//                    Toast.makeText(context, "登录成功!", Toast.LENGTH_LONG).show();
//                    SaveAccount.saveAccountInfo(context, userNumber, password);//保存账号密码
//                    //将数据转到MainActivity
//                    Intent intent = new Intent(context, MainActivity.class);
//                    intent.putExtra("userNumber", userNumber);
//                    intent.putExtra("password", password);
//                    startActivity(intent);
//                    finish();
//                } else {
//                    Toast.makeText(context, "登录失败!" + strBack, Toast.LENGTH_LONG).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<MessageBean> call, Throwable t) {
//                Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
//                Log.d(TAG, "onResponse: error");
//                Log.d(TAG, t.getMessage());
//            }
//        });

    }

    //注册按钮 onclick属性对应的函数
    public void register(View view) {
        startActivity(new Intent(context, RegisterActivity.class));
    }

    //退出提醒 两次back退出
    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - lastClickBackTime > 2000) { // 后退阻断
            Toast.makeText(this, "再点击退出 Android博客阅读app", Toast.LENGTH_LONG).show();
            lastClickBackTime = System.currentTimeMillis();
        } else { // 关掉app
            System.exit(0);//完全退出  再次启动很慢
//            finish();//保留进程 再次启动较快 但有不确定的bug没解决
        }
    }

}