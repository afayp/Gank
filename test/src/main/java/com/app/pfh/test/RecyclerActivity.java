package com.app.pfh.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class RecyclerActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private List<String> mData = new ArrayList<String>();
    private StaggeredHomeAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclerview_layout);
        initData();
        mRecyclerView = (RecyclerView) findViewById(R.id.id_recyler);
        /**
         * 线性布局（listview）：
         LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
         默认垂直（上下滚动），可设置水平横向滚动
         linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
         mRecyclerView.setLayoutManager(linearLayoutManager);
         Grid布局:
         GridLayoutManager gridLayoutManager = new GridLayoutManager(this,3);
         mRecyclerView.setLayoutManager(gridLayoutManager);
         如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
         mRecyclerView.setHasFixedSize(true);
         */
        //瀑布流：
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        //添加分割线
//        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.addItemDecoration(new DividerGridItemDecoration(this));
        //动画：
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new StaggeredHomeAdapter(this,mData);
        //设置监听：
        mAdapter.setOnItemClickLitener(new StaggeredHomeAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(RecyclerActivity.this, position + " click",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View view, int position) {
                Toast.makeText(RecyclerActivity.this, position + " click",
                        Toast.LENGTH_SHORT).show();
            }
        });

        mRecyclerView.setAdapter(mAdapter);
    }

    private void initData() {
        for (int i = 'A'; i < 'z'; i++){
            mData.add("" +(char)i);
        }
    }
}
