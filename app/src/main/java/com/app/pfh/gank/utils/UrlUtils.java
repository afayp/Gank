package com.app.pfh.gank.utils;

/**
 * 帮助生成url的类
 */
public class UrlUtils {

    public static final String TAG = "Gank";
    //分类数据: http://gank.avosapps.com/api/data/数据类型/请求个数/第几页
    public static final String GANK_SERVER_IP = "http://gank.avosapps.com/api/data/";

    //每日数据： http://gank.avosapps.com/api/day/年/月/日
    public static final String GANK_DAY_IP = "http://gank.avosapps.com/api/day/";

    //随机数据：http://gank.avosapps.com/api/random/data/分类/个数
    public static final String GANK_RANDOM_IP = "http://gank.avosapps.com/api/random/data/";

    //gank网址
    public static final String GANK_URL = "http://gank.io/";

    public static final String ANDROID = "Android/";
    public static final String IOS = "iOS/";
    public static final String fuli = "福利/";
    public static final String releax = "休息视频/";
    public static final String expand = "拓展资源/";
    public static final String front = "前端/";
    public static final String ALL = "all/";
    //每次请求的个数
    public static final String NUM = "10/";


    public static String getFenLeiUrl(String type ,int page){
        String url = GANK_SERVER_IP+type+NUM+page;
        return url;
    }




}
