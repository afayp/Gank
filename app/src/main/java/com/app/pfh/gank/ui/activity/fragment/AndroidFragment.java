package com.app.pfh.gank.ui.activity.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.app.pfh.gank.R;
import com.app.pfh.gank.adapter.AndroidIOSFragmentAdapter;
import com.app.pfh.gank.model.Good;
import com.app.pfh.gank.utils.HttpUtils;
import com.app.pfh.gank.utils.JsonParser;
import com.app.pfh.gank.utils.UrlUtils;

import java.util.ArrayList;
import java.util.List;


public class AndroidFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{


    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    //每次加一页
    private int currentpage = 1;

    private List<Good> mGoodList = new ArrayList<Good>();
    private List<Good> mAddGoodList;
    private AndroidIOSFragmentAdapter mAdapter;
    private int lastVisibleItem;
    private static String mType = UrlUtils.ANDROID;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_androidios, container, false);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.id_android_swipe);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.id_android_recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mSwipeRefreshLayout.setColorSchemeColors(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mAdapter = new AndroidIOSFragmentAdapter(getContext(),mGoodList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnScrollListener(scrollListener);
        //怎么样才能
        reloadData();
        return view;
    }

//    @Override
//    public void onActivityCreated(Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        reloadData();
//    }

    //全部重新加载，重第一页开始
    private void reloadData() {
        mSwipeRefreshLayout.setRefreshing(true);
        mGoodList.clear();
        mAdapter.notifyDataSetChanged();
        currentpage =1;
        loadData(currentpage);
    }

    //上拉加载更多,再加载一页
    private void loadMore(){
        currentpage+=1;
        loadData(currentpage);
    }


    //根据当前页数加载
    private void loadData(int page) {
        HttpUtils.volleyGet(UrlUtils.getFenLeiUrl(mType,page), new HttpUtils.VolleyCallback() {
            @Override
            public void onSuccess(String response) {
                //新增加的数据
                mAddGoodList = JsonParser.getGoodListFromFenLei(response);
                mGoodList.addAll(mAddGoodList);
                mAdapter.notifyDataSetChanged();
                mAddGoodList.clear();
                mSwipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onError(Exception e) {
                mSwipeRefreshLayout.setRefreshing(false);
                Toast.makeText(getContext(),"网络错误！请重试",Toast.LENGTH_LONG).show();
            }
        });

    }
    @Override
    //下拉刷新
    public void onRefresh() {
        reloadData();
    }

    private RecyclerView.OnScrollListener scrollListener = new RecyclerView.OnScrollListener() {

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            lastVisibleItem = ((LinearLayoutManager)recyclerView.getLayoutManager()).findLastVisibleItemPosition();
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == RecyclerView.SCROLL_STATE_IDLE
                    && lastVisibleItem + 1 == mAdapter.getItemCount()) {
                mSwipeRefreshLayout.setRefreshing(true);
                loadMore();
            }

        }
    };
}
