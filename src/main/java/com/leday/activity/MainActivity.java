package com.leday.activity;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.view.animation.TranslateAnimation;

import com.leday.R;
import com.leday.Util.LogUtil;
import com.leday.Util.PreferenUtil;
import com.leday.Util.UpdateUtil;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {

    private FloatingActionButton mFab1, mFab2, mFab3;
    private DisplayMetrics mDisplayMetric;

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
        initDisplay();


    }

    /**
     * 测量屏幕数据
     */
    private void initDisplay() {
        mDisplayMetric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(mDisplayMetric);
        LogUtil.e("屏幕尺寸相关",
                mDisplayMetric.heightPixels + "," + mDisplayMetric.widthPixels + "。dpi（X和Y是应该是相同的）xdpi= "
                        + mDisplayMetric.xdpi + ",ydpi = " + mDisplayMetric.ydpi + "。desityx（x的总密度）："
                        + mDisplayMetric.densityDpi + "，密度" + mDisplayMetric.density);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab1_activity_main:
                Animator mAnimator1 = ObjectAnimator.ofFloat(v, "translationY", 0, mDisplayMetric.heightPixels / 2 - 128);
                mAnimator1.setDuration(2000);
                mAnimator1.setInterpolator(new BounceInterpolator());
                mAnimator1.start();
                ShowSnack(v, "#336699");
                break;
            case R.id.fab2_activity_main:
                Animator mAnimator2 = ObjectAnimator.ofFloat(v, "translationY", 0, mDisplayMetric.heightPixels / 2 - 128);
                mAnimator2.setDuration(2000);
                mAnimator2.setInterpolator(new BounceInterpolator());
                mAnimator2.start();
                ShowSnack(v, "#ffffff");
                break;
            case R.id.fab3_activity_main:
                Animator mAnimator3 = ObjectAnimator.ofFloat(v, "translationY", 0, mDisplayMetric.heightPixels / 2 - 128);
                mAnimator3.setDuration(2000);
                mAnimator3.setInterpolator(new BounceInterpolator());
                mAnimator3.start();
                ShowSnack(v, "#339966");
                break;
        }
    }

    //抽出方法Snack
    private void ShowSnack(View v, String color) {
        Snackbar.make(v, "长按小球2秒进入下一页", Snackbar.LENGTH_LONG).setActionTextColor(Color.parseColor(color)).setAction("或者戳我", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToTabActivity();
            }
        }).show();
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