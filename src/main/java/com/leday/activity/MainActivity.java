package com.leday.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.view.animation.TranslateAnimation;

import com.leday.R;
import com.leday.Util.PreferenUtil;
import com.leday.Util.UpdateUtil;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {

    private FloatingActionButton mFab1, mFab2, mFab3;
//    TODO  以下
//    3.代码混淆

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this).setTitle("确认退出吗？").setIcon(android.R.drawable.ic_dialog_info)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        PreferenUtil.remove(MainActivity.this, "localVersion");
                        PreferenUtil.remove(MainActivity.this, "serverVersion");
                        MainActivity.this.finish();
                    }
                }).setNegativeButton("返回", null).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        new UpdateUtil(this).checkUpdate();
    }

    private void initView() {
        mFab1 = (FloatingActionButton) findViewById(R.id.fab1_activity_main);
        mFab2 = (FloatingActionButton) findViewById(R.id.fab2_activity_main);
        mFab3 = (FloatingActionButton) findViewById(R.id.fab3_activity_main);

        mFab1.setOnClickListener(this);
        mFab2.setOnClickListener(this);
        mFab3.setOnClickListener(this);
        mFab1.setOnLongClickListener(this);
        mFab2.setOnLongClickListener(this);
        mFab3.setOnLongClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab1_activity_main:
                TranslateAnimation mTranslate1 = new TranslateAnimation(0, 50, 0, 600);
                mTranslate1.setDuration(2000);
                mTranslate1.setInterpolator(new BounceInterpolator());
                mFab1.startAnimation(mTranslate1);
                Snackbar.make(v, "长按小球2秒进入下一页", Snackbar.LENGTH_LONG).setActionTextColor(Color.parseColor("#336699")).setAction("或者戳我", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ToTabActivity();
                    }
                }).show();
                break;
            case R.id.fab2_activity_main:
                TranslateAnimation mTranslate2 = new TranslateAnimation(0, 0, 0, 600);
                mTranslate2.setDuration(2000);
                mTranslate2.setInterpolator(new BounceInterpolator());
                mFab2.startAnimation(mTranslate2);
                Snackbar.make(v, "长按小球2秒进入下一页", Snackbar.LENGTH_LONG).setActionTextColor(Color.parseColor("#ffffff")).setAction("或者戳我", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ToTabActivity();
                    }
                }).show();
                break;
            case R.id.fab3_activity_main:
                TranslateAnimation mTranslate3 = new TranslateAnimation(0, -50, 0, 600);
                mTranslate3.setDuration(2000);
                mTranslate3.setInterpolator(new BounceInterpolator());
                mFab3.startAnimation(mTranslate3);
                Snackbar.make(v, "长按小球2秒进入下一页", Snackbar.LENGTH_LONG).setActionTextColor(Color.parseColor("#339966")).setAction("或者戳我", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ToTabActivity();
                    }
                }).show();
                break;
        }
    }

    @Override
    public boolean onLongClick(View v) {
        ToTabActivity();
        return true;
    }

    private void ToTabActivity() {
        startActivity(new Intent(MainActivity.this, TabActivity.class));
    }
}