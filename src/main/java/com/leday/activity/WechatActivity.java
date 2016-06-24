package com.leday.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.leday.R;

public class WechatActivity extends AppCompatActivity {

    private WebView mWebView;
    private String localurl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wechat);

        initView();
        initEvent();
    }

    private void initEvent() {
        mWebView.loadUrl(localurl);

        mWebView.setWebChromeClient(new WebChromeClient());
        mWebView.setWebViewClient(new WebViewClient());
    }

    private void initView() {
        Intent intent = getIntent();
        localurl = intent.getStringExtra("localurl");

        mWebView = (WebView) findViewById(R.id.webview_activity);
    }
}