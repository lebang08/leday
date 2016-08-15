package com.leday.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.leday.R;
import com.leday.application.MyApplication;
import com.squareup.picasso.Picasso;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TodayActivity extends Activity implements View.OnClickListener {

    private TextView mContent, mTitle, mLike;
    private ImageView mImgBack;
    private ViewFlipper mViewFlipper;

    private String local_id, local_title;
    private static final String URL = "http://v.juhe.cn/todayOnhistory/queryDetail.php?key=776cbc23ec84837a647a7714a0f06bff&e_id=";
    private String localcontent;

    @Override
    protected void onStop() {
        super.onStop();
        MyApplication.getHttpQueue().cancelAll("GET");
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today);

        initView();
        getJson();
    }

    private void initView() {
        Intent intent = getIntent();
        local_id = intent.getStringExtra("local_id");
        local_title = intent.getStringExtra("local_title");

        mContent = (TextView) findViewById(R.id.content_activity_today);
        mTitle = (TextView) findViewById(R.id.txt_Today_title);
        mLike = (TextView) findViewById(R.id.txt_Today_like);
        mImgBack = (ImageView) findViewById(R.id.img_today_back);
        mViewFlipper = (ViewFlipper) findViewById(R.id.viewflipper_activity_today);

        mLike.setOnClickListener(this);
        mImgBack.setOnClickListener(this);
        mTitle.setText(local_title);
//        mViewFlipper.setAutoStart(true);
        mViewFlipper.startFlipping();
    }

    private void getJson() {
        StringRequest todayactivityrequest = new StringRequest(Request.Method.GET, URL + local_id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Dosuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        todayactivityrequest.setTag("GET");
        MyApplication.getHttpQueue().add(todayactivityrequest);
    }

    private void Dosuccess(String response) {
        JSONObject obj;
        JSONArray arr;
        try {
            obj = new JSONObject(response);
            arr = obj.getJSONArray("result");
            obj = arr.getJSONObject(0);
            localcontent = obj.getString("content");
            mContent.setText(localcontent);
            String picnumber = obj.getString("picNo");
            arr = obj.getJSONArray("picUrl");
            if (arr.length() == 0) {
                mViewFlipper.setVisibility(View.GONE);
            }
            for (int i = 0; i < arr.length(); i++) {
                obj = arr.getJSONObject(i);
                String imgurl = obj.getString("url");
                ImageView mImgview = new ImageView(TodayActivity.this);
                Picasso.with(TodayActivity.this).load(imgurl).into(mImgview);
                mViewFlipper.addView(mImgview);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_Today_like:
                //TODO 再做一张表放入数据库中
                Snackbar.make(view, "收藏成功" + localcontent, Snackbar.LENGTH_SHORT).show();
                break;
            case R.id.img_today_back:
                finish();
                break;
        }
    }
}