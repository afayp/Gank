package com.app.pfh.gank.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Toast;

import com.app.pfh.gank.R;
import com.app.pfh.gank.adapter.CommonAdapter;
import com.app.pfh.gank.db.DBHelper;
import com.app.pfh.gank.db.Dao;
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
import me.drakeet.materialdialog.MaterialDialog;

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
//                intent.putExtra("desc", good.getDesc());
//                intent.putExtra("url", good.getUrl());
                intent.putExtra("good",good);
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(final Good good) {

                final MaterialDialog materialDialog = new MaterialDialog(mActivity);
                materialDialog.setTitle("提示");
                materialDialog.setMessage("确定收藏吗");
                materialDialog.setPositiveButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        materialDialog.dismiss();

                    }
                });
                materialDialog.setNegativeButton("收藏", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(good.get_id() != null){
                            DBHelper dbHelper = new DBHelper(mActivity);
                            SQLiteDatabase db = dbHelper.getWritableDatabase();
                            Dao dao = Dao.getInstance(mActivity);
                            if(!dao.isExists(good)){
                                db.execSQL("INSERT INTO Good VALUES(" +
                                        "'" + good.get_id() + "'," +
                                        "'" + good.getWho() + "'," +
                                        "'" + good.getPublishedAt() + "'," +
                                        "'" + good.getDesc() + "'," +
                                        "'" + good.getType() + "'," +
                                        "'" + good.getUrl() + "'"
                                        + ")");
                                db.close();
                                Toast.makeText(mActivity,"收藏成功！",Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(mActivity,"已收藏！",Toast.LENGTH_SHORT).show();

                            }
                        }
                        materialDialog.dismiss();
                    }
                });
                materialDialog.show();
            }
        });
        return view;
    }

    private void refresh() {
        mCurrentPage = 1;
        commonAdapter.removeData();
        loadData(mCurrentPage);
        mSwipeRefreshLayout.setRefreshing(false);

    }

    @Override
    protected void initData() {
        loadData(mCurrentPage);
    }

    private void loadData(final int page) {
        if (HttpUtils.isConnected(mActivity)) {
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
//            DBHelper dbHelper = new DBHelper(mActivity);
//            SQLiteDatabase db = dbHelper.getWritableDatabase();
//            Cursor cursor = db.rawQuery("SELECT jsonText FROM Good", null);
//            while (cursor.moveToNext()){
//                String response = cursor.getString(cursor.getColumnIndex("josnText"));
//
//            }
//            db.close();
        }
        mSwipeRefreshLayout.setRefreshing(false);
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
        //保存到数据库
//        DBHelper dbHelper = new DBHelper(mActivity);
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        String sql = "INSERT INTO Good(jsonText) VALUES(" + "'"+ responseString + "')";
//        db.execSQL(sql);
//        db.close();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
