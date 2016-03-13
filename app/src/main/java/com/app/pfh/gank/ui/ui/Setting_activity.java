package com.app.pfh.gank.ui.ui;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.app.pfh.gank.R;
import com.app.pfh.gank.utils.PrefsUtils;
import com.app.pfh.gank.view.MyItemSelectView;

import me.drakeet.materialdialog.MaterialDialog;

public class Setting_activity extends AppCompatActivity {

    private Toolbar mToolbar;
    private int themeId;
    private MaterialDialog materialDialog;
    private MyItemSelectView theme_setting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        themeId = PrefsUtils.getThemeId(this);
        switch (themeId) {
            case PrefsUtils.THEMEID_BLUE:
                setTheme(R.style.AppThemeBlue);
                break;
            case PrefsUtils.THEMEID_PURPLE:
                setTheme(R.style.AppThemePurple);
                break;
            case PrefsUtils.THEMEID_RED:
                setTheme(R.style.AppThemeRed);
                break;
        }
        setContentView(R.layout.activity_setting);
        initView();
    }

    private void initView() {
        mToolbar = (Toolbar) findViewById(R.id.setting_toolbar);
        theme_setting = (MyItemSelectView) findViewById(R.id.theme_setting);
        mToolbar.setTitle("设置");
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.mipmap.ic_arrow_back_white_24dp);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                onBackPressed();
                //如果用户更换了主题，那么原来的activity的主题也得换了，这里从重新创建一个新的activity出来
                Intent intent = new Intent(Setting_activity.this, FenLei_activity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
                finish();
            }
        });

        theme_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChangeThemeDialog();
            }
        });
    }

    private void showChangeThemeDialog() {
        materialDialog = new MaterialDialog(this);
        materialDialog.setTitle("更换主题");
        View view = getLayoutInflater().inflate(R.layout.theme_selection, null);
        RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.radiogrup);

        RadioButton blue = (RadioButton) view.findViewById(R.id.theme_blue);
        blue.setTextColor(getResources().getColor(R.color.colorPrimary));
        blue.setHighlightColor(getResources().getColor(R.color.colorPrimary));


        RadioButton purple = (RadioButton) view.findViewById(R.id.theme_purple);
        purple.setTextColor(getResources().getColor(R.color.colorPrimaryDarkPurple));

        RadioButton red = (RadioButton) view.findViewById(R.id.theme_red);
        red.setTextColor(getResources().getColor(R.color.colorPrimaryRed));

        switch (themeId) {
            case PrefsUtils.THEMEID_BLUE:
                blue.setChecked(true);
                break;
            case PrefsUtils.THEMEID_PURPLE:
                purple.setChecked(true);
                break;
            case PrefsUtils.THEMEID_RED:
                red.setChecked(true);
                break;
        }
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.theme_blue:
                        PrefsUtils.changeTheme(Setting_activity.this, PrefsUtils.THEMEID_BLUE);
                        break;
                    case R.id.theme_purple:
                        PrefsUtils.changeTheme(Setting_activity.this, PrefsUtils.THEMEID_PURPLE);
                        break;
                    case R.id.theme_red:
                        PrefsUtils.changeTheme(Setting_activity.this, PrefsUtils.THEMEID_RED);
                        break;
                }
                materialDialog.dismiss();
                restartActivity();

            }
        });
        materialDialog.setContentView(view);
        materialDialog.setNegativeButton("取消", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialDialog.dismiss();
            }
        });
        materialDialog.show();
    }

    /**
     * 重新启动这个activity，因为要换主题了
     */
    private void restartActivity() {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
        overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Setting_activity.this, FenLei_activity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
        finish();
    }
}
