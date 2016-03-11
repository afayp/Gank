package com.app.pfh.gank.ui.ui;


import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.app.pfh.gank.R;
import com.app.pfh.gank.db.DBHelper;
import com.app.pfh.gank.db.Dao;
import com.app.pfh.gank.model.Good;
import com.app.pfh.gank.utils.HttpUtils;
import com.app.pfh.gank.utils.UrlUtils;

public class Webview_activity extends AppCompatActivity {

//    private Toolbar mToolbar;
    private WebView mWebView;
//    private String mUrl;
//    private String mDesc;
    private Good good;
    private ProgressBar mProgressBar;
    private FloatingActionButton floatingActionButton;
    private CoordinatorLayout coordinatorLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
//        mUrl = getIntent().getStringExtra("url");
//        mDesc = getIntent().getStringExtra("desc");
        good = (Good) getIntent().getSerializableExtra("good");
        initView();
        initData();


    }

    private void initView() {
        mProgressBar = (ProgressBar) findViewById(R.id.progressbar);
//        mToolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(mToolbar);
//        mToolbar.setTitle(good.getDesc());
//
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackPressed();
//            }
//        });
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.cooridnator);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.floatingactionbutton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Dao.getInstance(Webview_activity.this).saveGood(good);
                if(good.get_id() != null){
                    DBHelper dbHelper = new DBHelper(Webview_activity.this);
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    Dao dao = Dao.getInstance(Webview_activity.this);
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
                        Snackbar.make(coordinatorLayout,"收藏成功！",Snackbar.LENGTH_SHORT).show();
                    }else {
                        Snackbar.make(coordinatorLayout,"已经收藏过了！",Snackbar.LENGTH_SHORT).show();
                    }
                }


            }
        });


        mWebView = (WebView) findViewById(R.id.webview);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.getSettings().setDatabaseEnabled(true);
        mWebView.getSettings().setAppCacheEnabled(true);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                mProgressBar.setVisibility(View.VISIBLE);

            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mProgressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //设置不调用第三方游览器
                view.loadUrl(url);
                return true;
            }
        });
    }

    private void initData() {
        if (HttpUtils.isConnected(Webview_activity.this)) {
            mWebView.loadUrl(good.getUrl());
        } else {
            //TODO
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            if(mWebView.canGoBack()){
                //返回上一个页面，而不是退出
                mWebView.goBack();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.webview_toolbar_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                Log.e(UrlUtils.TAG,"点击了home");
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
