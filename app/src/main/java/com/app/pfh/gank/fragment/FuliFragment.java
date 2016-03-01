package com.app.pfh.gank.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.app.pfh.gank.R;
import com.app.pfh.gank.adapter.FuliAdapter;
import com.app.pfh.gank.model.FenLeiData;
import com.app.pfh.gank.model.Good;
import com.app.pfh.gank.ui.ui.FenLei_activity;
import com.app.pfh.gank.ui.ui.Fuli_viewer_activity;
import com.app.pfh.gank.utils.HttpUtils;
import com.app.pfh.gank.utils.UrlUtils;
import com.google.gson.Gson;
import com.loopj.android.http.TextHttpResponseHandler;

import java.util.List;

import cz.msebera.android.httpclient.Header;

public class FuliFragment extends  BaseFragment{
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager linearLayoutManager;
    private FuliAdapter fuliAdapter;
    private boolean isLoading;
    private int mCurrentPage = 1;

    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_common, container, false);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
        linearLayoutManager = new LinearLayoutManager(mActivity);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        fuliAdapter = new FuliAdapter(mActivity);
        mRecyclerView.setAdapter(fuliAdapter);
        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();
                int totalItemCount = linearLayoutManager.getItemCount();
                //剩下2个item加载更多，dy>0表示向下滑动
                if (lastVisibleItemPosition >= totalItemCount - 2 && dy > 0) {
                    Log.e(UrlUtils.TAG, "要没啦" + isLoading);
                    if (isLoading) {
                        Toast.makeText(mActivity, "已经再加载了！", Toast.LENGTH_SHORT);
                    } else {
                        loadMore();
                    }
                }
            }
        });

        fuliAdapter.setOnItemClickLitener(new FuliAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Good good,int positon) {
                List<Good> fuliList = fuliAdapter.getFuliList();
                Log.e(UrlUtils.TAG,fuliList.get(0).toString());
                String[] urls = new String[fuliList.size()];
                for (int i=0;i<urls.length;i++){
                    urls[i] = fuliList.get(i).getUrl();
                }
                Intent intent = new Intent(mActivity, Fuli_viewer_activity.class);
                intent.putExtra("urls",urls);
                intent.putExtra("currItem", positon);
                startActivity(intent);
            }
        });
        return view;
    }

    private void refresh() {
        mCurrentPage = 1;
        fuliAdapter.removeData();
        loadData(mCurrentPage);
    }

    @Override
    protected void initData() {
        loadData(mCurrentPage);
    }

    private void loadData(final int page) {
        mSwipeRefreshLayout.setRefreshing(true);
        if (HttpUtils.isConnected(mActivity)) {
            Log.e(UrlUtils.TAG,"有网！");
            HttpUtils.get(UrlUtils.getFenLeiUrl(UrlUtils.fuli, page), new TextHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    Log.e(UrlUtils.TAG, "Fuli加载json数据失败"+"url:"+UrlUtils.getFenLeiUrl(UrlUtils.fuli, page)+"response:"+responseString);
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    Log.e(UrlUtils.TAG, responseString);
                    parseResponse(responseString);
                }
            });

        } else {
            Toast.makeText(mActivity, "没有网络", Toast.LENGTH_SHORT);
            Log.e(UrlUtils.TAG, "没有网！");
            //TODO 加载数据库里的数据
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    private void loadMore() {
        mCurrentPage += 1;
        loadData(mCurrentPage);
        isLoading = false;
    }

    private void parseResponse(String responseString) {
        Gson gson = new Gson();
        FenLeiData fenLeiData = gson.fromJson(responseString, FenLeiData.class);
        List<Good> goodList = fenLeiData.getResults();
        //TODO　存到数据库
        fuliAdapter.addData(goodList);
        mSwipeRefreshLayout.setRefreshing(false);

    }
}
