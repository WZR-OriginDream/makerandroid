package com.lipiao.makerandroid.SharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;

/**
 * 保存账号密码 参考书上125
 */
public class SaveAccount {
    public static boolean saveAccountInfo(Context context, String userNumber, String password) {
        SharedPreferences sp = context.getSharedPreferences("data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("userNumber", userNumber);
        editor.putString("password", password);
        editor.commit();
        return true;
    }

    public static Map<String, String> getUserInfo(Context context) {
        SharedPreferences sp = context.getSharedPreferences("data", Context.MODE_PRIVATE);
        String userNumber = sp.getString("userNumber", null);//缺省值为null
        String password = sp.getString("password", null);//缺省值为null
        Map<String, String> userMap = new HashMap<>();
        userMap.put("userNumber", userNumber);
        userMap.put("password", password);
        return userMap;
    }
}
