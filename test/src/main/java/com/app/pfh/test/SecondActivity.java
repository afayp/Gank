package com.app.pfh.test;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class SecondActivity extends AppCompatActivity {


    private SwipeRefreshLayout swipeRefreshLayout;
    private ListView mListView;
    private List<String> mData = new ArrayList<String>();
    private ArrayAdapter adapter;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    mData.addAll(Arrays.asList("woshi5", "woshi6", "woshi7"));
                    adapter.notifyDataSetChanged();
                    swipeRefreshLayout.setRefreshing(false);
                    break;
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_xialashuaxin);
        initViews();
        mData.add("woshi1");
        mData.add("woshi2");
        mData.add("woshi3");
        mData.add("woshi4");
        adapter = new ArrayAdapter(SecondActivity.this, android.R.layout.simple_list_item_1, mData);
        mListView.setAdapter(adapter);
        swipeRefreshLayout.setColorSchemeColors(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                handler.sendEmptyMessageDelayed(1, 3000);
            }
        });
    }

    private void initViews() {
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.id_swipe);
        mListView = (ListView) findViewById(R.id.id_lv);
    }


}
