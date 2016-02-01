package com.app.pfh.gank.utils;


import com.app.pfh.gank.model.FenLeiData;
import com.app.pfh.gank.model.Good;
import com.google.gson.Gson;

import java.util.ArrayList;

public class JsonParser {

    public static  ArrayList<Good> getGoodListFromFenLei(String response){
        Gson gson = new Gson();
        FenLeiData fenLeiData = gson.fromJson(response, FenLeiData.class);
        ArrayList<Good> goodList  = fenLeiData.getResults();
        return goodList;
    }


    //先不用
    public Good getGoodListFromDayData(String response){
        return null;
    }

}
