package com.app.pfh.gank.ui.ui;


import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.app.pfh.gank.R;
import com.app.pfh.gank.fragment.FuliViewFragment;
import com.app.pfh.gank.utils.UrlUtils;

import java.util.List;

public class Fuli_viewer_activity extends AppCompatActivity{

    private Toolbar mToolbar;
    private ViewPager viewPager;
    private String[] urls;
    private int positon;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fuli);
        Log.e(UrlUtils.TAG, "开始!");
        initData();
        initView();
    }

    private void initData() {
        urls = getIntent().getStringArrayExtra("urls");
        positon = getIntent().getIntExtra("currItem", 1);
        Log.e(UrlUtils.TAG, "拿到url!"+urls[0].toString());

    }

    private void initView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        viewPager = (ViewPager) findViewById(R.id.pager);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.mipmap.ic_arrow_back_white_24dp);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return FuliViewFragment.newInstance(urls[position]);
            }

            @Override
            public int getCount() {
                return urls.length;
            }
        });
        viewPager.setCurrentItem(positon);
    }
}
