package com.app.pfh.gank.model;

import android.graphics.Bitmap;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * 图片类，用于保存
 */
public class Image extends RealmObject{

    @PrimaryKey
    private String id;
    private  String author;
    private String time;
    private String url;

    public Image() {
    }

    public Image(String id, String author, String time, String url) {
        this.id = id;
        this.author = author;
        this.time = time;
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
