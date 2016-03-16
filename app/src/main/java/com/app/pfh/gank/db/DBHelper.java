package com.app.pfh.gank.db;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.webkit.WebView;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "cache.db"; //数据库名称
    private static final int DB_VERSION = 3; //数据库版本
    //保存收藏的表
    private static final String CREATE_TABLE2 = "create table if not exists Good" +
            "(" +
            "_id text ," +
            "who varchar," +
            "publishedAt text," +
            "desc text," +
            "type text," +
            "url text )";

    //保存json数据的表
    private static final String CREATE_TABLE = "create table if not exists json(type text,jsonText text)";



    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
        Log.e("Gank", "json表创建成功！");
        db.execSQL(CREATE_TABLE2);
        Log.e("Gank","收藏表创建成功！");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
