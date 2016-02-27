package com.app.pfh.gank.ui.ui;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.app.pfh.gank.R;
import com.app.pfh.gank.utils.HttpUtils;

public class Webview_activity extends AppCompatActivity {

    private Toolbar mToolbar;
    private WebView mWebView;
    private String mUrl;
    private String mDesc;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ProgressBar mProgressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        mUrl = getIntent().getStringExtra("url");
        mDesc = getIntent().getStringExtra("desc");
        initView();
        initData();


    }

    private void initView() {
        mProgressBar = (ProgressBar) findViewById(R.id.progressbar);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mWebView = (WebView) findViewById(R.id.webview);
        mToolbar.setTitle(mDesc);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

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
//                mSwipeRefreshLayout.setRefreshing(false);
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
            mWebView.loadUrl(mUrl);
        } else {

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
}
