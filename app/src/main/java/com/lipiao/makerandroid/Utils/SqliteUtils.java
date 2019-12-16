package com.lipiao.makerandroid.Utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.lipiao.makerandroid.Bean.CollectArticleBean;
import com.lipiao.makerandroid.sqlite.MyHelper;

import java.util.ArrayList;
import java.util.List;


/**
 * 参照书 P131 - P132
 * 操作SQLite中的数据
 * 1.插入数据
 * 2.删除一数据
 * 3.查所有博客数据
 * （没有修改）
 */
public class SqliteUtils {
    private static String TAG = "SqliteUtils";
    private static MyHelper myHelper;
    private static SQLiteDatabase sqLiteDatabase;
    private static SqliteUtils sqliteUtils;

    //获取唯一可用的对象
    public static SqliteUtils getInstance(Context context) {
        return sqliteUtils = new SqliteUtils(context);
    }

    //将构造函数私有 用于实现单例 使得外部无法使用构造函数实例化
    // 参数activity的上下文context ：用于初始化MyHelper
    private SqliteUtils(Context context) {
        myHelper = new MyHelper(context);//初始化MyHelper
        sqLiteDatabase = myHelper.getWritableDatabase();//由myHelper获取可写的SQLiteDatabase对象
    }

    //插入一条美食数据
    public static String insert(String collectUrl, String collectTitles) {
        try {
            sqLiteDatabase.execSQL("INSERT into collect_article (collectUrl,collectTitles)" +
                    "values (?,?)", new Object[]{collectUrl, collectTitles});
            Log.d(TAG, "insert: 收藏博客成功 " + collectTitles);
            return "收藏博客成功！";
        } catch (Exception e) {
            return "您已收藏过该博客了哦！";
        }
    }

    //删除一条美食数据（参数封装为FoodBean对象的类，具体数据从类中获取）
    public static String delete(String collectUrl) {
        try {
            sqLiteDatabase.execSQL("DELETE FROM collect_article WHERE collectUrl=?",
                    new Object[]{collectUrl});
            Log.d(TAG, "insert: 删除成功 " + collectUrl);
            return "删除博客成功！";
        } catch (Exception e) {
            return "删除博客失败！";
        }
    }


    //查所有美食数据 无参数
    //结果使用Cursor保存
    public static List<CollectArticleBean> queryAll() {
        //创建游标对象
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM collect_article WHERE collectUrl != ?; ", new String[]{"-1"});
        List<CollectArticleBean> collectArticleBeanList=new ArrayList<>();

        //利用游标遍历所有数据对象
        while (cursor.moveToNext()) {
            String collectUrl = cursor.getString(cursor.getColumnIndex("collectUrl"));
            String collectTitles = cursor.getString(cursor.getColumnIndex("collectTitles"));
            CollectArticleBean collectArticleBean=new CollectArticleBean(collectUrl,collectTitles);
            collectArticleBeanList.add(collectArticleBean);
            Log.d(TAG, "queryAll: " +collectArticleBean.toString());
        }
        cursor.close();//关闭游标
        Log.d(TAG, "queryAll: 查询成功");
        return collectArticleBeanList;//返回
    }

    //关闭数据库
    public static void closeSQLite(){
        sqLiteDatabase.close();
    }

}