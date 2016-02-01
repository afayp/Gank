package com.app.pfh.test;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/1/29.
 */
public class Fragment3 extends Fragment{

    private List<String> mData = new ArrayList<>();
    private ListView mListView;
    private ArrayAdapter mAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        initData();
        View view = inflater.inflate(R.layout.fragment_3, container, false);
        mListView = (ListView) view.findViewById(R.id.id_listview3);
        mAdapter = new ArrayAdapter(getContext(), R.layout.item_layout,R.id.item_tv,mData);
        mListView.setAdapter(mAdapter);
        return view;

    }

    private void initData() {
        for(int i=0;i<30;i++){
            mData.add("item"+i);
        }
    }
}
