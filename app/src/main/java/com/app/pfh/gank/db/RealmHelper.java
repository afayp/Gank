package com.app.pfh.gank.db;

import android.content.Context;

import com.app.pfh.gank.model.Image;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * 使用realm作为数据库存储
 */
public class RealmHelper {

    //创建一个Realm,一个Realm相当于一个SQLite数据库。它有一个与之对应的文件
    public static Realm getRealm(Context context){
        RealmConfiguration configuration = new RealmConfiguration.Builder(context)
                .name("gank.io")
                .build();
        Realm gankRealm = Realm.getInstance(configuration);
        return gankRealm;
    }



}
