package com.app.pfh.gank.fragment;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragment extends Fragment{
    Activity mActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mActivity = getActivity();
        return initViews(inflater,container,savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mActivity = null;
    }

    protected abstract void initData();


    protected abstract View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);
}
