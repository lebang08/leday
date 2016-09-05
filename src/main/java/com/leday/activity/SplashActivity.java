package com.leday.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.appx.BDBannerAd;
import com.baidu.appx.BDInterstitialAd;
import com.baidu.appx.BDSplashAd;
import com.leday.R;
import com.leday.Util.LogUtil;
import com.leday.Util.ToastUtil;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends Activity implements View.OnClickListener{

    private TextView mTitle,mTxtPass;
    private ImageView mImg;
    private Timer mTimer = new Timer();
    private final int DO_COUNT = 0;
    private int count;

    private BDSplashAd splashAd;
    private String SDK_APP_KEY = "N5Q9a1aXalqHCEq2GG1DeZN4GTzewsNs";
    private String SDK_SPLASH_AD_ID = "naPtaeihvie9NR1jzjWRDjTO";

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == DO_COUNT){
                mTitle.setText("倒计时" + msg.obj + "秒");
                if ((int)msg.obj == 0){
                    startActivity(new Intent(SplashActivity.this,TabActivity.class));
                    SplashActivity.this.finish();
                    mTimer.cancel();
                }
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        //TODO 8.31改引导做开屏
        if(splashAd != null&&splashAd.isLoaded()){
            splashAd.showAd();
            mImg.setVisibility(View.GONE);
            mTitle.setVisibility(View.GONE);
        }else{
            count = 5;
            mTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Message msg = Message.obtain();
                    count = count - 1;
                    msg.what = DO_COUNT;
                    msg.obj = count;
                    mHandler.sendMessage(msg);
                }
            },100,1000);
        }
    }

    @Override
    protected void onDestroy() {
        if (splashAd != null) {
            splashAd.destroy();
        }
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initView();

        //创建开屏广告并下载
        createSplashAd();
        if (!splashAd.isLoaded()) {
            splashAd.loadAd();
        }
    }

    private void initView() {
        mImg = (ImageView) findViewById(R.id.img_activity_splash);
        mTitle = (TextView) findViewById(R.id.txt_splash_count);
        mTxtPass = (TextView) findViewById(R.id.txt_splash_pass);
        mTxtPass.setOnClickListener(this);
    }


    /**
     * 百度开屏广告
     */
    private void createSplashAd() {
        if (splashAd == null) {
            splashAd = new BDSplashAd(SplashActivity.this, SDK_APP_KEY, SDK_SPLASH_AD_ID);
            splashAd.setAdListener(new SplashActivity.AdListener("Splash-"));
        }
    }

    @Override
    public void onClick(View view) {
        mTimer.cancel();
        startActivity(new Intent(SplashActivity.this,TabActivity.class));
        SplashActivity.this.finish();
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