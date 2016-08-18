package com.leday.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.baidu.appx.BDBannerAd;
import com.baidu.appx.BDInterstitialAd;
import com.baidu.appx.BDSplashAd;
import com.leday.Impl.ListViewHightImpl;
import com.leday.R;
import com.leday.Util.LogUtil;
import com.leday.Util.PreferenUtil;
import com.leday.Util.ToastUtil;
import com.leday.Util.UpdateUtil;
import com.leday.activity.FavoriteActivity;
import com.leday.activity.TalkActivity;
import com.leday.activity.WebFavoriteActivity;

import java.util.ArrayList;
import java.util.List;

public class FragmentD extends Fragment implements AdapterView.OnItemClickListener {

    private ListView mListView;
    private List<String> mData = new ArrayList<>();

    //百度广告相关
    private BDSplashAd splashAd;
    private BDInterstitialAd interstitialAd;
    private String SDK_APP_KEY = "N5Q9a1aXalqHCEq2GG1DeZN4GTzewsNs";
    private String SDK_SPLASH_AD_ID = "naPtaeihvie9NR1jzjWRDjTO";
    private String SDK_INTERSTITIAL_AD_ID = "zkGLtPZvA7uZE6YG2B07QVIO";

    @Override
    public void onStart() {
        //创建百度插屏View
        interstitialAd = new BDInterstitialAd(getActivity(), SDK_APP_KEY, SDK_INTERSTITIAL_AD_ID);
        interstitialAd.setAdListener(new AdListener("Interstitial"));
        super.onStart();
    }

    @Override
    public void onDestroy() {
        if (interstitialAd != null) {
            interstitialAd.destroy();
        }
        if (splashAd != null) {
            splashAd.destroy();
        }
        super.onDestroy();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_d, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mListView = (ListView) view.findViewById(R.id.listview_fragment_d);
        mListView.setOnItemClickListener(this);

        mData.add("图灵问答机器人");
        mData.add("今时今往收藏");
        mData.add("微信微选收藏");
        mData.add("查看版本号");
        mData.add("联系开发者");
        mData.add("小Le推荐");
        ArrayAdapter mAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, mData);
        mListView.setAdapter(mAdapter);
        new ListViewHightImpl(mListView).setListViewHeightBasedOnChildren();
    }

    /**
     * 是否安装某应用
     *
     * @param context
     * @param packageName
     * @return
     */
    public static boolean isApkInstalled(Context context, String packageName) {
        try {
            context.getPackageManager().getApplicationInfo(packageName, PackageManager.GET_SHARED_LIBRARY_FILES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    /**
     * 启动QQ
     *
     * @param isExist
     */
    private void talkQQ(boolean isExist) {
        if (!isExist) {
            ToastUtil.showMessage(getActivity(), "手机安装腾讯QQ才可以对话哦");
        } else {
            String urltwo = "mqqwpa://im/chat?chat_type=wpa&uin=443664360";
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(urltwo)));
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                startActivity(new Intent(getActivity(), TalkActivity.class));
                break;
            case 1:
                startActivity(new Intent(getActivity(), FavoriteActivity.class));
                break;
            case 2:
                startActivity(new Intent(getActivity(), WebFavoriteActivity.class));
                break;
            case 3:
                Snackbar.make(view, "当前版本号是：" + PreferenUtil.get(getActivity(), "localVersion", "1.4"), Snackbar.LENGTH_SHORT)
                        .setActionTextColor(Color.parseColor("#ffffff"))
                        .setAction("点击检查新版本", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                new UpdateUtil(getActivity(), "isNew").checkUpdate();
                            }
                        }).show();
                break;
            case 4:
                //打开腾讯QQ
                boolean isExist = isApkInstalled(getActivity(), "com.tencent.mobileqq");
                talkQQ(isExist);
                break;
            case 5:
                //展示插屏
                if (null == interstitialAd || !interstitialAd.isLoaded()) {
                    LogUtil.e("---- interstitialAd is not ready ----");
                    //下载插屏
                    if (null == interstitialAd) {
                        interstitialAd = new BDInterstitialAd(getActivity(), SDK_APP_KEY, SDK_INTERSTITIAL_AD_ID);
                        interstitialAd.setAdListener(new AdListener("Interstitial"));
                    }
                    interstitialAd.loadAd();
                    interstitialAd.showAd();
                    // 如果本地无广告可用，需要下载广告，待下次启动使用
                    createSplashAd();
                    if (!splashAd.isLoaded()) {
                        splashAd.loadAd();
                    }
                } else {
                    LogUtil.e("---- interstitialAd start to show ----");
                    interstitialAd.showAd();
                    // 如果本地无广告可用，需要下载广告，待下次启动使用
                    createSplashAd();
                    if (!splashAd.isLoaded()) {
                        splashAd.loadAd();
                    }
                }
                break;
            default:
                break;
        }
    }

    private void createSplashAd() {
        if (splashAd == null) {
            LogUtil.e("tab4? + splashAd");
            splashAd = new BDSplashAd(getActivity(), SDK_APP_KEY, SDK_SPLASH_AD_ID);
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