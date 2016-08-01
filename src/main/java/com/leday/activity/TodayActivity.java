package com.leday.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.leday.R;
import com.leday.Util.LogUtil;
import com.leday.application.MyApplication;
import com.squareup.picasso.Picasso;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TodayActivity extends AppCompatActivity {

    private TextView mContent;
    private ViewFlipper mViewFlipper;

    private String locale_id;
    private static final String URL = "http://v.juhe.cn/todayOnhistory/queryDetail.php?key=776cbc23ec84837a647a7714a0f06bff&e_id=";

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
        locale_id = intent.getStringExtra("locale_id");

        mContent = (TextView) findViewById(R.id.content_activity_today);
        mViewFlipper = (ViewFlipper) findViewById(R.id.viewflipper_activity_today);

//        mViewFlipper.setAutoStart(true);
        mViewFlipper.startFlipping();
    }

    private void getJson() {
        StringRequest todayactivityrequest = new StringRequest(Request.Method.GET, URL + locale_id, new Response.Listener<String>() {
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
            LogUtil.e("linx1", obj.toString());
            String mcontent = obj.getString("content");
            mContent.setText(mcontent);
            String picnumber = obj.getString("picNo");
            LogUtil.e("linx2", "picnumber = " + picnumber);
            arr = obj.getJSONArray("picUrl");
            LogUtil.e("linx3", arr.toString());
            if (arr.length() == 0) {
                mViewFlipper.setVisibility(View.GONE);
            }
            for (int i = 0; i < arr.length(); i++) {
                obj = arr.getJSONObject(i);
                String imgurl = obj.getString("url");
                ImageView mImgview = new ImageView(TodayActivity.this);
                Picasso.with(TodayActivity.this).load(imgurl).into(mImgview);
                mViewFlipper.addView(mImgview);
                LogUtil.e("linx4", imgurl.toString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}