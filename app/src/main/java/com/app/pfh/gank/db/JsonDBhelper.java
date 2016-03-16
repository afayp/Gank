package com.app.pfh.gank.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class JsonDBhelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "jsoncache.db"; //数据库名称
    private static final int DB_VERSION = 1; //数据库版本

    //保存json数据的表
    private static final String CREATE_TABLE = "create table if not exists josn(type text,jsonText text)";

    public JsonDBhelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
