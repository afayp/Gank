package com.app.pfh.gank.ui.activity;

import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.app.pfh.gank.GankApplication;
import com.app.pfh.gank.R;
import com.app.pfh.gank.ui.activity.fragment.AndroidFragment;
import com.app.pfh.gank.ui.activity.fragment.FuliFragment;
import com.app.pfh.gank.utils.UrlUtils;

import java.util.ArrayList;
import java.util.List;

public class FenLei_activity extends AppCompatActivity {


    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private NavigationView mNavigationView;

    //viewpager和tablayout用到的fragment和tab标题
    private List<Fragment> mFragments = new ArrayList<Fragment>();
    private List<String> mTabTitles = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fenlei);
        initViews();
        //监听navigation里面的menuitem
        setupDrawerContent(mNavigationView);
        initViewPager();
        initTabLayout();


    }


    private void initViewPager() {
        Fragment fuliFragment = new AndroidFragment();
        Fragment androidFragment = new AndroidFragment();
        Fragment iOSFragment = new AndroidFragment();
        mFragments.add(fuliFragment);
        mFragments.add(androidFragment);
        mFragments.add(iOSFragment);
        mTabTitles.add("福利");
        mTabTitles.add("Android");
        mTabTitles.add("iOS");
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mFragments.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return mTabTitles.get(position);
            }
        });

    }

    private void initTabLayout() {
//        mViewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
    }


    private void initViews() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.id_drawer_layout);
        mToolbar = (Toolbar) findViewById(R.id.id_toolbar);
        mTabLayout = (TabLayout) findViewById(R.id.id_fenlei_tab);
        mViewPager = (ViewPager) findViewById(R.id.id_viewpager);
        mNavigationView = (NavigationView) findViewById(R.id.id_nav);

        mToolbar.setTitle("Category");
        setSupportActionBar(mToolbar);
        //设置左上角那个图标有用
        ActionBar ab = getSupportActionBar();
//        ab.setHomeAsUpIndicator(R.drawable.ic_menu);自己设置那个图标，这里用默认的
        ab.setDisplayHomeAsUpEnabled(true);
        //下面设置点击图标展开navigation
        ActionBarDrawerToggle mActionBarDrawerToggle = new ActionBarDrawerToggle(this,
                mDrawerLayout, mToolbar,R.string.abc_action_bar_home_description,
                R.string.abc_action_bar_home_description_format);
        mActionBarDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);


    }

    private void setupDrawerContent(NavigationView mNavigationView) {
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                item.setCheckable(true);
                mDrawerLayout.closeDrawers();
                switch (item.getItemId()){
                    //待改进！
                    case R.id.nav_daily:
                        Toast.makeText(FenLei_activity.this,"today" ,Toast.LENGTH_LONG).show();
                        break;
                    case R.id.nav_type:
                        Toast.makeText(FenLei_activity.this,"today" ,Toast.LENGTH_LONG).show();
                        break;
                    case R.id.nav_random:
                        Toast.makeText(FenLei_activity.this,"today" ,Toast.LENGTH_LONG).show();
                        break;
                    case R.id.nav_collect:
                        Toast.makeText(FenLei_activity.this,"today" ,Toast.LENGTH_LONG).show();
                        break;
                    case R.id.nav_setting:
                        Toast.makeText(FenLei_activity.this,"today" ,Toast.LENGTH_LONG).show();
                        break;
                    case R.id.nav_info:
                        Toast.makeText(FenLei_activity.this,"today" ,Toast.LENGTH_LONG).show();
                        break;
                }

                return true;
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        GankApplication.getRequestQueue().cancelAll("VolleyGet");
    }
}
