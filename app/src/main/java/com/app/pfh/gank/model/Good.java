package com.app.pfh.gank.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


public class Good {

//            "who":"Jason",
//            "publishedAt":"2015-08-21T04:09:13.777Z",
//            "desc":"着色的加载视图库",
//            "type":"Android",
//            "url":"https://github.com/recruit-lifestyle/ColoringLoading",
//            "used":true,
//            "_id":"55d5fd6b00b0af5bde3a9f82",
//            "createdAt":"2015-08-20T16:16:43.941Z",
//            "updatedAt":"2015-08-21T04:09:14.358Z"
    private String who;
    private String publishedAt;
    private String desc;
    private String type;
    private String url;
    private boolean used;
    private String _id;
    private String createdAt;
    private String _ns;
    private String source;

    public String get_ns() {
        return _ns;
    }

    public void set_ns(String _ns) {
        this._ns = _ns;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Good() {
    }

    public Good(String who, String publishedAt, String desc, String type, String url, String _id) {
        this.who = who;
        this.publishedAt = publishedAt;
        this.desc = desc;
        this.type = type;
        this.url = url;
        this._id = _id;
    }

    public Good(String who, String publishedAt, String desc, String type, String url, boolean used, String _id, String createdAt, String updatedAt) {
        this.who = who;
        this.publishedAt = publishedAt;
        this.desc = desc;
        this.type = type;
        this.url = url;
        this.used = used;
        this._id = _id;
        this.createdAt = createdAt;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }


//    @Override
//    public String toString() {
//        return "Good{" +
//                "who='" + who + '\'' +
//                ", publishedAt='" + publishedAt + '\'' +
//                ", desc='" + desc + '\'' +
//                ", type='" + type + '\'' +
//                ", url='" + url + '\'' +
//                ", used=" + used +
//                ", _id='" + _id + '\'' +
//                ", createdAt='" + createdAt + '\'' +
//                ", updatedAt='" + updatedAt + '\'' +
//                '}';
//    }
}
