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
import com.app.pfh.gank.adapter.CommonAdapter;
import com.app.pfh.gank.model.CommonGoodForStore;
import com.app.pfh.gank.model.FenLeiData;
import com.app.pfh.gank.model.Good;
import com.app.pfh.gank.ui.ui.Webview_activity;
import com.app.pfh.gank.utils.HttpUtils;
import com.app.pfh.gank.utils.UrlUtils;
import com.google.gson.Gson;
import com.loopj.android.http.TextHttpResponseHandler;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * 分类下除了福利外的fragment
 */
public class CommonFragment extends BaseFragment {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private CommonAdapter commonAdapter;
    private String mType;
    private int mCurrentPage;
    private LinearLayoutManager linearLayoutManager;
    private boolean isLoading;
    private Realm realm;


    public static CommonFragment newInstance(String type) {
        Bundle bundle = new Bundle();
        bundle.putString("TYPE", type);
        CommonFragment commonFragment = new CommonFragment();
        commonFragment.setArguments(bundle);
        return commonFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //拿从activity传过来的数据
        mType = getArguments().getString("TYPE");
        mCurrentPage = 1;
        realm = Realm.getDefaultInstance();

    }

    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_common, container, false);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mSwipeRefreshLayout.setRefreshing(true);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
        linearLayoutManager = new LinearLayoutManager(mActivity);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        commonAdapter = new CommonAdapter(mActivity);
        mRecyclerView.setAdapter(commonAdapter);
        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();
                int totalItemCount = linearLayoutManager.getItemCount();
                //剩下2个item加载更多，dy>0表示向下滑动
                if (lastVisibleItemPosition >= totalItemCount - 2 && dy > 0) {
                    if (isLoading) {
                        Toast.makeText(mActivity, "已经再加载了！", Toast.LENGTH_SHORT);
                    } else {
                        loadMore();
                    }
                }
            }
        });

        commonAdapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Good good) {
                //TODO
                Log.e(UrlUtils.TAG, good.toString());
                Intent intent = new Intent(mActivity, Webview_activity.class);
                intent.putExtra("desc", good.getDesc());
                intent.putExtra("url", good.getUrl());
                startActivity(intent);
            }
        });
        return view;
    }

    private void refresh() {
        mCurrentPage = 1;
        commonAdapter.removeData();
        loadData(mCurrentPage);
    }

    @Override
    protected void initData() {
        loadData(mCurrentPage);
    }

    private void loadData(final int page) {
        if (HttpUtils.isConnected(mActivity)) {
            Log.e(UrlUtils.TAG,"有网！");
            HttpUtils.get(UrlUtils.getFenLeiUrl(mType, page), new TextHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    Log.e(UrlUtils.TAG, mType +"-Common加载json数据失败"+"url:"+UrlUtils.getFenLeiUrl(mType, page)+" response:"+responseString);
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
//                    Log.e(UrlUtils.TAG,mType+": "+responseString);
                    parseResponse(responseString);
                }
            });

        } else {
            Toast.makeText(mActivity, "没有网络", Toast.LENGTH_SHORT).show();
            //TODO 加载数据库里的数据
//            Log.e(UrlUtils.TAG,"没有网！");
//            RealmResults<CommonGoodForStore> goodList = realm.where(CommonGoodForStore.class).equalTo("type", mType.replaceAll("/","")).findAll();
//            if(goodList.size() >0){
//                List<Good> tempList = new ArrayList<>();
//                for(CommonGoodForStore g : goodList){
//                    Good good = new Good(g.getWho(),g.getPublishedAt(),g.getDesc(),g.getType(),g.getUrl(),g.getObjectId());
//                    tempList.add(good);
//                }
//                commonAdapter.addData(tempList);
//            }else {
//                Toast.makeText(mActivity,"没有网也没有存货啦",Toast.LENGTH_SHORT).show();
//            }
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
        commonAdapter.addData(goodList);
        mSwipeRefreshLayout.setRefreshing(false);
//        realm.beginTransaction();
//        for(Good good : goodList){
//            CommonGoodForStore object = new CommonGoodForStore();
//            object.setWho(good.getWho());
//            object.setType(good.getType());
//            object.setUrl(good.getUrl());
//            object.setDesc(good.getDesc());
//            object.setObjectId(good.get_id());
//            object.setPublishedAt(good.getPublishedAt());
//            object.setIsCollected(false);
//            realm.copyToRealm(object);
//        }
//        realm.commitTransaction();
//        Log.e(UrlUtils.TAG,"保存成功！");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(realm != null){
            realm.close();
        }
    }
}
