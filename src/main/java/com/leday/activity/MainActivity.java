package com.leday.activity;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.BounceInterpolator;

import com.baidu.appx.BDBannerAd;
import com.baidu.appx.BDInterstitialAd;
import com.baidu.appx.BDSplashAd;
import com.leday.R;
import com.leday.Util.LogUtil;
import com.leday.Util.PreferenUtil;
import com.leday.Util.UpdateUtil;
import com.leday.application.MyApplication;

public class MainActivity extends BaseActivity implements View.OnClickListener, View.OnLongClickListener {

    private DisplayMetrics mDisplayMetric;

    private BDSplashAd splashAd;
    public static final String SDK_APP_KEY = "N5Q9a1aXalqHCEq2GG1DeZN4GTzewsNs";
    private static final String SDK_SPLASH_AD_ID = "naPtaeihvie9NR1jzjWRDjTO";

    @Override
    protected void onStop() {
        super.onStop();
        MyApplication.getHttpQueue().cancelAll("updateutil");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new UpdateUtil(this).checkUpdate();
        initView();
    }

    private void initView() {
        FloatingActionButton mFab1 = (FloatingActionButton) findViewById(R.id.fab1_activity_main);
        FloatingActionButton mFab2 = (FloatingActionButton) findViewById(R.id.fab2_activity_main);
        FloatingActionButton mFab3 = (FloatingActionButton) findViewById(R.id.fab3_activity_main);

        mFab1.setOnClickListener(this);
        mFab2.setOnClickListener(this);
        mFab3.setOnClickListener(this);
        mFab1.setOnLongClickListener(this);
        mFab2.setOnLongClickListener(this);
        mFab3.setOnLongClickListener(this);
        initDisplay();
        showThankOnce();
    }

    /**
     * 根据preference字段展示一次感谢
     */
    private void showThankOnce() {
        boolean isThank = PreferenUtil.contains(MainActivity.this, "thankonce");
        if (!isThank) {
            PreferenUtil.put(MainActivity.this, "thankonce", "20160801");
            new AlertDialog.Builder(MainActivity.this).setTitle("一封简短的感谢信").setMessage(
                    "说心里话，没有美工，功能也暂还很简单的一个应用,\n" +
                            "能一直获得您的支持，在下非常感激,\n" +
                            "您觉得Le该如何改进呢？\n" +
                            "真挚希望能收到您的意见\n" +
                            "具体请看 -> '关于'板块。\n" +
                            "或许，Le可以成为您的定制应用哦!"
            ).setPositiveButton("知道啦", null).show();
        }
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
                Animator mAnimator1 = ObjectAnimator.ofFloat(v, "translationY", 0, mDisplayMetric.heightPixels / 2 - mDisplayMetric.heightPixels / 8);
                mAnimator1.setDuration(2000);
                mAnimator1.setInterpolator(new BounceInterpolator());
                mAnimator1.start();
                ShowSnack(v, "today", "#336699");
                break;
            case R.id.fab2_activity_main:
                Animator mAnimator2 = ObjectAnimator.ofFloat(v, "translationY", 0, mDisplayMetric.heightPixels / 2 - mDisplayMetric.heightPixels / 8);
                mAnimator2.setDuration(2000);
                mAnimator2.setInterpolator(new BounceInterpolator());
                mAnimator2.start();
                ShowSnack(v, "star", "#ffffff");
                break;
            case R.id.fab3_activity_main:
                Animator mAnimator3 = ObjectAnimator.ofFloat(v, "translationY", 0, mDisplayMetric.heightPixels / 2 - mDisplayMetric.heightPixels / 8);
                mAnimator3.setDuration(2000);
                mAnimator3.setInterpolator(new BounceInterpolator());
                mAnimator3.start();
                ShowSnack(v, "wechat", "#339966");
                break;
        }
    }

    //抽出方法Snack
    private void ShowSnack(View v, final String content, String color) {
        Snackbar.make(v, "长按小球2秒进入下一页", Snackbar.LENGTH_LONG)
                .setActionTextColor(Color.parseColor(color))
                .setAction("或者戳我", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToTabActivity(content);
            }
        }).show();
    }

    @Override
    public boolean onLongClick(View v) {
        switch (v.getId()) {
            case R.id.fab1_activity_main:
                ToTabActivity("today");
                break;
            case R.id.fab2_activity_main:
                ToTabActivity("star");
                break;
            case R.id.fab3_activity_main:
                ToTabActivity("wechat");
                break;
        }
        return true;
    }

    private void ToTabActivity(String content) {
        Intent intent = new Intent(MainActivity.this, TabActivity.class);
        intent.putExtra("content", content);
        startActivity(intent);
    }

    /**
     * 以下是百度集成广告
     * @param view
     */
    public void clickone(View view) {
        createSplashAd();
        LogUtil.e("what1?", "1");
    }

    public void clicktwo(View view) {
        // 如果本地无广告可用，需要下载广告，待下次启动使用
        if (!splashAd.isLoaded()) {
            LogUtil.e("what2?", "2");
            splashAd.loadAd();
        }
    }

    public void clickthree(View view) {
        LogUtil.e("what3?", "3");
        // 展示开屏广告
        if (splashAd.isLoaded()) {
            splashAd.showAd();
        }
    }

    public void clickfour(View view) {
        // 销毁广告对象
        if (splashAd != null) {
            LogUtil.e("what4?", "4");
            splashAd.destroy();
            splashAd = null;
        }
    }

    private void createSplashAd() {
        if (splashAd == null) {
            LogUtil.e("what?", "0");
            splashAd = new BDSplashAd(this, SDK_APP_KEY, SDK_SPLASH_AD_ID);
            splashAd.setAdListener(new AdListener("Splash"));
        }
    }

    private class AdListener implements BDBannerAd.BannerAdListener, BDInterstitialAd.InterstitialAdListener,
            BDSplashAd.SplashAdListener {
        private String stringTag;

        public AdListener(String tag) {
            this.stringTag = tag;
        }

        @Override
        public void onAdvertisementDataDidLoadFailure() {
            LogUtil.e(stringTag, "    ad did load failure");
        }

        @Override
        public void onAdvertisementDataDidLoadSuccess() {
            LogUtil.e(stringTag, "    ad did load success");
        }

        @Override
        public void onAdvertisementViewDidClick() {
            LogUtil.e(stringTag, "    ad view did click");
        }

        @Override
        public void onAdvertisementViewDidShow() {
            LogUtil.e(stringTag, "    ad view did show");
        }

        @Override
        public void onAdvertisementViewWillStartNewIntent() {
            LogUtil.e(stringTag, "    ad view will new intent");
        }

        @Override
        public void onAdvertisementViewDidHide() {
            LogUtil.e(stringTag, "    ad view did hide");
        }
    }
}