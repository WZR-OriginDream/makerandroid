package com.lipiao.makerandroid.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

/**
 * 参考书P129
 * 使用自带的SQLite数据库 保存收藏的博客
 * 数据库名 article
 * 收藏的博客表collect_article
 * <p>
 * 以FoodBean类为参考 保存
 * 均可作为主键（属性均唯一）
 * String collectUrl;//博客网址url 主键
 * String collectTitles;//博客标题
 */
public class MyHelper extends SQLiteOpenHelper {
    public MyHelper(@Nullable Context context) {
        super(context, "article", null, 1);
    }

    //数据库第一次创建时调用
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE collect_article(collectUrl VARCHAR(50) PRIMARY KEY ,collectTitles VARCHAR(50) )");
        // 博客url 主键
        // 博客标题
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
