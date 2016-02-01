package com.app.pfh.gank.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

//好像每天的分类会变化，暂时不获取每天的数据
//或者可以现则固定的（android，ios，福利，休息视频）
public class DayGoods {
    @SerializedName("iOS")
    private ArrayList<Good> iosGoods;
    @SerializedName("Android")
    private ArrayList<Good> androidGoods;
    @SerializedName("瞎推荐")
    private ArrayList<Good> recommend;
    @SerializedName("福利")
    private ArrayList<Good> benefit;
    @SerializedName("休息视频")
    private ArrayList<Good> restVideo;
    @SerializedName("拓展资源")
    private ArrayList<Good> expandRes;
    @SerializedName("App")
    private ArrayList<Good> app;
    @SerializedName("前端")
    private ArrayList<Good> qianduan;

    public ArrayList<Good> getIosGoods() {
        return iosGoods;
    }

    public void setIosGoods(ArrayList<Good> iosGoods) {
        this.iosGoods = iosGoods;
    }

    public ArrayList<Good> getAndroidGoods() {
        return androidGoods;
    }

    public void setAndroidGoods(ArrayList<Good> androidGoods) {
        this.androidGoods = androidGoods;
    }

    public ArrayList<Good> getRecommend() {
        return recommend;
    }

    public void setRecommend(ArrayList<Good> recommend) {
        this.recommend = recommend;
    }

    public ArrayList<Good> getBenefit() {
        return benefit;
    }

    public void setBenefit(ArrayList<Good> benefit) {
        this.benefit = benefit;
    }

    public ArrayList<Good> getRestVideo() {
        return restVideo;
    }

    public void setRestVideo(ArrayList<Good> restVideo) {
        this.restVideo = restVideo;
    }

    public ArrayList<Good> getExpandRes() {
        return expandRes;
    }

    public void setExpandRes(ArrayList<Good> expandRes) {
        this.expandRes = expandRes;
    }
}
