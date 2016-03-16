package com.app.pfh.gank.db;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.app.pfh.gank.model.CommonGoodForStore;
import com.app.pfh.gank.model.Good;

import java.util.ArrayList;
import java.util.List;

public class Dao {

    private SQLiteDatabase db;
    private static Dao dao;

    private Dao(Context context) {
        DBHelper dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public synchronized static Dao getInstance(Context context) {
        if (dao == null) {
            dao = new Dao(context);
        }
        return dao;
    }

    public void saveGood(Good good) {
        if(isExists(good)){
            Log.e("Gank","已经存在！");
            return;
        }
        db.execSQL("INSERT INTO Good VALUES(" +
                "'" + good.get_id() + "'," +
                "'" + good.getWho() + "'," +
                "'" + good.getPublishedAt() + "'," +
                "'" + good.getDesc() + "'," +
                "'" + good.getType() + "'," +
                "'" + good.getUrl() + "'"
                + ")");
        db.close();
        Log.e("Gank", "开始存：" + good.toString());

    }

    public List<Good> getGoodFromDB() {
        List<Good> goodList = new ArrayList<Good>();
        Cursor cursor = db.rawQuery("SELECT * FROM Good",null);
//        Cursor cursor = db.query("Good", null, null, null, null, null, null);
        Log.e("Gank", "开始查询！");
        if (cursor.moveToFirst()) {
            do {
                Good good = new Good();
                good.set_id(cursor.getString(cursor.getColumnIndex("_id")));
                good.setWho(cursor.getString(cursor.getColumnIndex("who")));

                Log.e("Gank", "开始查询！who = " + cursor.getString(cursor.getColumnIndex("who")));

                good.setPublishedAt(cursor.getString(cursor.getColumnIndex("publishedAt")));
                good.setDesc(cursor.getString(cursor.getColumnIndex("desc")));
                good.setType(cursor.getString(cursor.getColumnIndex("type")));
                good.setUrl(cursor.getString(cursor.getColumnIndex("url")));
                goodList.add(good);
            } while (cursor.moveToNext());
        }


        if (cursor != null) {
            cursor.close();
        }
        db.close();
        return goodList;
    }

    public void deleteGood(Good good){
        db.execSQL("delete from Good where _id = " + "'" + good.get_id() + "'");

    }

    public boolean isExists(Good good){
        Cursor cursor = db.rawQuery("select _id from Good where _id = " + "'" + good.get_id() + "'", null);
        if(cursor.getCount() > 0){
            return true;//已经有了
        }
        return false;
    }


}
