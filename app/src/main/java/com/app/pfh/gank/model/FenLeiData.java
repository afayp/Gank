package com.app.pfh.gank.model;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/1/28.
 */
public class FenLeiData {

    private Boolean error;
    private ArrayList<Good> results;

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public ArrayList<Good> getResults() {
        return results;
    }

    public void setResults(ArrayList<Good> results) {
        this.results = results;
    }
}
