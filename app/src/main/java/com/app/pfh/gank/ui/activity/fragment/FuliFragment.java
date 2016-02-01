package com.app.pfh.gank.ui.activity.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.app.pfh.gank.R;
import com.app.pfh.gank.adapter.FuliAdapter;
import com.app.pfh.gank.db.RealmHelper;
import com.app.pfh.gank.model.Good;
import com.app.pfh.gank.model.Image;
import com.app.pfh.gank.utils.HttpUtils;
import com.app.pfh.gank.utils.JsonParser;
import com.app.pfh.gank.utils.PicUtils;
import com.app.pfh.gank.utils.UrlUtils;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;


public class FuliFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private FuliAdapter mAdapter;
    private StaggeredGridLayoutManager mStaggeredGridLayoutManager;
    private List<Good> mFuliList = new ArrayList<Good>();
    private List<Good> mAddFuliList = new ArrayList<Good>();
    private int currentPage = 1;
    private String mType = UrlUtils.fuli;
    private Realm realm;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fuli, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.id_fuli_recycler);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.id_fuli_swipe);
        mStaggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mStaggeredGridLayoutManager);
        mSwipeRefreshLayout.setColorSchemeColors(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mAdapter = new FuliAdapter(getContext(), mFuliList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnScrollListener(scrollListener);
        reloadImage();
        return view;
    }

    private void reloadImage() {
        mSwipeRefreshLayout.setRefreshing(true);
        mFuliList.clear();
        mAdapter.notifyDataSetChanged();
        currentPage = 1;
        loadImageFromIntent(currentPage);
    }

    private void loadMore() {
        mSwipeRefreshLayout.setRefreshing(true);
        currentPage += 1;
        loadImageFromIntent(currentPage);
    }


    //从网上加载数据放入数据库
    private void loadImageFromIntent(int currentPage) {
        HttpUtils.volleyGet(UrlUtils.getFenLeiUrl(mType, currentPage), new HttpUtils.VolleyCallback() {
            @Override
            public void onSuccess(String response) {
                mAddFuliList = JsonParser.getGoodListFromFenLei(response);
                //取出图片的url地址等信息放入数据库
                for (int i = 0; i < mAddFuliList.size(); i++) {
                    String url = mAddFuliList.get(i).getUrl();
                    String id = mAddFuliList.get(i).getObjectId();
                    String time = mAddFuliList.get(i).getDesc();
                    String author = mAddFuliList.get(i).getWho();
                    realm= RealmHelper.getRealm(getContext());
                    realm.beginTransaction();
                    Image image = realm.createObject(Image.class);
                    image.setTime(time);
                    image.setId(id);
                    image.setAuthor(author);
                    image.setUrl(url);
                    realm.commitTransaction();
                }
                mFuliList.addAll(mAddFuliList);
                mAdapter.notifyDataSetChanged();
                mAddFuliList.clear();
                mSwipeRefreshLayout.setRefreshing(false);

            }
            @Override
            public void onError(Exception e) {
                mSwipeRefreshLayout.setRefreshing(false);
                Toast.makeText(getContext(), "网络错误！请重试", Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public void onRefresh() {
        reloadImage();
    }

    private RecyclerView.OnScrollListener scrollListener = new RecyclerView.OnScrollListener() {

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                int[] positions = new int[mStaggeredGridLayoutManager.getSpanCount()];
                mStaggeredGridLayoutManager.findLastVisibleItemPositions(positions);
                for (int position : positions) {
                    if (position == mStaggeredGridLayoutManager.getItemCount() - 1) {
                        loadMore();
                        break;
                    }
                }
            }

        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}
