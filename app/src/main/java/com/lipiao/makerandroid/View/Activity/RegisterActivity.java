package com.lipiao.makerandroid.View.Activity;


import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import com.lipiao.makerandroid.Bean.RespondBean.MessageBean;
import com.lipiao.makerandroid.R;
import com.lipiao.makerandroid.Utils.HttpUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    static final String TAG = "RegisterActivity";
    Context context;
    EditText etUsername;
    EditText etPassword;
    EditText etPassword2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        context = getBaseContext();
        etUsername = findViewById(R.id.et_user_number_register);
        etPassword = findViewById(R.id.et_password_register);
        etPassword2 = findViewById(R.id.et_password_2_register);
    }

    //注册函数
    public void register(View view) {
        //注册时的两次密码一致
        if (etPassword.getText().toString().equals(etPassword2.getText().toString())) {
            String username = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            //Log.d("LoginActivity", "login: " + userNumber + password);
            Call<MessageBean> call = HttpUtil.getUserService().register(username, password);

            call.enqueue(new Callback<MessageBean>() {
                @Override
                public void onResponse(Call<MessageBean> call, Response<MessageBean> response) {
//                Log.d(TAG, "onResponse: " + response.body().getMessage());
                    String strBack=response.body().getMessage();
                    if (strBack.equals("registerSuccess")){
                        Toast.makeText(context, "注册成功!", Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(context, "注册失败!"+strBack, Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<MessageBean> call, Throwable t) {
                    Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
                    Log.d(TAG, "onResponse: error");
                    Log.d(TAG,t.getMessage());
                }
            });
        }else {
            Toast.makeText(context, "两次密码不一致，请重新输入！", Toast.LENGTH_LONG).show();
            etPassword.setText("");
            etPassword2.setText("");
        }
    }

    //返回函数
    public void back(View view) {
        onBackPressed();
    }
}
