package com.app.pfh.gank.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.app.pfh.gank.GankApplication;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.ResponseHandlerInterface;

/**
 * 网络加载返回的json数据的工具类。先使用volley
 */
public class HttpUtils {

    public static String TAG="com.app.pfh.gank";

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(String url ,ResponseHandlerInterface handlerInterface){
        client.get(url,handlerInterface);
    }

    //下载json数据
    public static void volleyGet(String url, final VolleyCallback callback) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        Log.d(TAG,s);
                        callback.onSuccess(s);
                    }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    Log.e(TAG, volleyError.getMessage(), volleyError);
                    callback.onError(volleyError);
                }
            });
        stringRequest.setTag("VolleyGet");
        GankApplication.getRequestQueue().add(stringRequest);

    }

    public interface VolleyCallback{
        void onSuccess(String response);
        void onError(Exception e);
    }

    /**
     * 判断网络是否连接
     *
     * @param context
     * @return
     */
    public static boolean isConnected(Context context)
    {

        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (null != connectivity)
        {

            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (null != info && info.isConnected())
            {
                if (info.getState() == NetworkInfo.State.CONNECTED)
                {
                    return true;
                }
            }
        }
        return false;
    }






}
