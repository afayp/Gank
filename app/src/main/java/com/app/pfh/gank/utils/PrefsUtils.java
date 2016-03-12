package com.app.pfh.gank.utils;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PrefsUtils {

    public static final int THEMEID_BLUE = 1;
    public static final int THEMEID_PURPLE = 2;
    public static final int THEMEID_RED = 3;

    public static void changeTheme(Context context,int themeId){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().putInt("THEMEID",themeId).commit();
    }

    public static int getThemeId(Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getInt("THEMEID",THEMEID_BLUE);
    }
}
