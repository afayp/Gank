package com.app.pfh.gank.ui.ui;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.app.pfh.gank.R;
import com.app.pfh.gank.adapter.CommonAdapter;
import com.app.pfh.gank.db.DBHelper;
import com.app.pfh.gank.db.Dao;
import com.app.pfh.gank.model.Good;
import com.app.pfh.gank.utils.UrlUtils;

import java.util.ArrayList;
import java.util.List;

import me.drakeet.materialdialog.MaterialDialog;


public class Collection_activity extends AppCompatActivity {

    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;
    private CommonAdapter commonAdapter;
    private Dao dao;
    private List<Good> goodList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        mToolbar = (Toolbar) findViewById(R.id.collection_toolbar);
        mToolbar.setTitle("收藏");
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.mipmap.ic_arrow_back_white_24dp);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
//                Intent intent = new Intent(Collection_activity.this, FenLei_activity.class);
//                startActivity(intent);
//                overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
                finish();
            }
        });
        Log.e("Gank", "collection toolbar ok!");
        dao =Dao.getInstance(this);
        goodList = new ArrayList<>();
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        commonAdapter = new CommonAdapter(this);
        mRecyclerView.setAdapter(commonAdapter);
        initData();
        commonAdapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Good good) {
                Log.e(UrlUtils.TAG, good.toString());
                Intent intent = new Intent(Collection_activity.this, Webview_activity.class);
                intent.putExtra("good", good);
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(final Good good) {
                final MaterialDialog materialDialog = new MaterialDialog(Collection_activity.this);
                materialDialog.setTitle("提示");
                materialDialog.setMessage("确定删除吗");
                materialDialog.setPositiveButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        materialDialog.dismiss();

                    }
                });
                materialDialog.setNegativeButton("删除", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dao.deleteGood(good);
                        initData();
                        materialDialog.dismiss();
                    }
                });
                materialDialog.show();
            }
        });

    }

    private void initData() {
        Log.e("Gank", "initData");
//        List<Good> goodList = dao.getGoodFromDB();
        goodList.clear();
        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Good", null);
        Log.e("Gank",cursor.getCount()+"");
        if(cursor.getCount() != 0){
            Log.e("Gank","开始查询！");
            if (cursor.moveToFirst()) {
                do {
                    Good good = new Good();
                    good.set_id(cursor.getString(cursor.getColumnIndex("_id")));
                    good.setWho(cursor.getString(cursor.getColumnIndex("who")));

                    Log.e("Gank", "开始查询！who = " + cursor.getString(cursor.getColumnIndex("who")));

                    good.setPublishedAt(cursor.getString(cursor.getColumnIndex("publishedAt")));
                    good.setDesc(cursor.getString(cursor.getColumnIndex("desc")));
                    good.setType(cursor.getString(cursor.getColumnIndex("type")));
                    good.setUrl(cursor.getString(cursor.getColumnIndex("url")));
                    goodList.add(good);
                } while (cursor.moveToNext());
            }
            commonAdapter.refreshData(goodList);
        }else {
//            Toast.makeText(Collection_activity.this,"什么都没有啊！",Toast.LENGTH_SHORT).show();
            Log.e("Gank", "什么都没有！");
            Good good = new Good();
            good.setDesc("快去添加收藏吧！");
            good.setWho("afayp");
            good.setPublishedAt("6666-66-66");
            goodList.add(good);
            commonAdapter.refreshData(goodList);
        }
        if (cursor != null) {
            cursor.close();
        }
        db.close();
        Log.e("Gank", "initData 拿到数据" + goodList.get(0).toString());
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.collection_context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        return super.onContextItemSelected(item);
    }
}
