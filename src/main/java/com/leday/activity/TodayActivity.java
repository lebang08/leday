package com.leday.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.leday.R;
import com.leday.Util.MySingleton;
import com.leday.entity.Today;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TodayActivity extends AppCompatActivity {

    private TextView mContent;
    private ImageView mImg;

    private String locale_id;
    private static final String URL = "http://v.juhe.cn/todayOnhistory/queryDetail.php?key=776cbc23ec84837a647a7714a0f06bff&e_id=";

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
        mImg = (ImageView) findViewById(R.id.img_activity_today);
    }

    private void getJson() {
//        获取Volley请求队列
//        RequestQueue queue = MySingleton.getInstance(this.getApplicationContext()).getRequestQueue();
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
//        Google官方推荐的单例模式请求队列
        MySingleton.getInstance(this.getApplicationContext()).addToRequestQueue(todayactivityrequest);
    }

    private void Dosuccess(String response) {
        JSONObject obj;
        JSONArray arr;
        try {
            obj = new JSONObject(response);
            arr = obj.getJSONArray("result");
            obj = arr.getJSONObject(0);
            Log.e("linx1", obj.toString());
            String mcontent = obj.getString("content");
            Log.e("linx2", mcontent.toString());
            mContent.setText(mcontent);
            arr = obj.getJSONArray("picUrl");
            Log.e("linx3", arr.toString());
            String localimg = arr.getJSONObject(0).getString("url");
            Log.e("linx4", localimg.toString());
//            Volley请求图片
            getBitmap(localimg);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getBitmap(String localimg) {
        ImageRequest imgrequest = new ImageRequest(localimg, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap bitmap) {
                mImg.setImageBitmap(bitmap);
            }
        }, 0, 0, Bitmap.Config.RGB_565, null);
        MySingleton.getInstance(this.getApplicationContext()).addToRequestQueue(imgrequest);
    }
}