package com.leday.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.leday.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TodayActivity extends AppCompatActivity {

    private TextView mContent;
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
        Volley.newRequestQueue(this).add(todayactivityrequest);
    }

    private void Dosuccess(String response) {
        JSONObject obj;
        JSONArray arr;
        try {
            obj = new JSONObject(response);
            arr = obj.getJSONArray("result");
            obj = arr.getJSONObject(0);
            Log.e("linx1", obj.toString());
            //TODO JSON解析未完全
            mContent.setText(obj.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}