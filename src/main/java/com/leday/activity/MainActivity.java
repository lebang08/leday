package com.leday.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.leday.R;
import com.leday.Util.LogUtil;
import com.leday.Util.UpdateUtil;
import com.leday.adapter.weatheradapter;
import com.leday.application.MyApplication;
import com.leday.entity.Weather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView mTemper, mWheather, mAir;
    private FloatingActionButton fab;
    private ListView mList;
    private List<Weather> weatherList = new ArrayList<Weather>();

    private String URL = "http://apicloud.mob.com/v1/weather/query?key=135b8ce813980&city=%E5%8E%A6%E9%97%A8";

    @Override
    protected void onStop() {
        super.onStop();
        MyApplication.getHttpQueue().cancelAll("GET");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initView();
        initEvent();
        new UpdateUtil(this).checkUpdate();
    }

    private void initView() {
        mTemper = (TextView) findViewById(R.id.txt_temper);
        mWheather = (TextView) findViewById(R.id.txt_weather);
        mAir = (TextView) findViewById(R.id.txt_air);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        mList = (ListView) findViewById(R.id.listview_mainactivity);
    }

    private void initEvent() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "查看更多功能", Snackbar.LENGTH_LONG)
                        .setAction("点我", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(MainActivity.this, TabActivity.class));
                            }
                        }).show();
            }
        });
        getJson();
    }

    public void getJson() {
//        String parseString = new String(URL.getBytes("ISO-8859-1"), "utf-8");
        StringRequest weatherRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                DoSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                LogUtil.e("Wrong-BACK", "联接错误原因： " + error.getMessage());
            }
        });
        weatherRequest.setTag("GET");
        MyApplication.getHttpQueue().add(weatherRequest);
    }

    private void DoSuccess(String response) {
        JSONObject obj;
        JSONArray arr;
        try {
            obj = new JSONObject(response);
            arr = obj.getJSONArray("result");
            LogUtil.e("arr", "arr :" + arr);
            obj = arr.getJSONObject(0);
            String temperature = obj.getString("temperature");
            String wea = obj.getString("weather");
            String air = obj.getString("airCondition");
            mTemper.setText(temperature);
            mWheather.setText(wea);
            mAir.setText(air);
            arr = obj.getJSONArray("future");
            for (int i = 1; i <= arr.length() - 1; i++) {
                obj = arr.getJSONObject(i);
                Weather weather = new Weather();
                weather.setDate(obj.getString("date"));
                weather.setDayTime(obj.getString("dayTime"));
                weather.setNight(obj.getString("night"));
                weather.setTemperature(obj.getString("temperature"));
                weather.setWeek(obj.getString("week"));
                weather.setWind(obj.getString("wind"));
                weatherList.add(weather);
            }
//            LogUtil.e("future", "futureinfo :" + weatherList);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        weatheradapter adapter = new weatheradapter(MainActivity.this, weatherList);
        mList.setAdapter(adapter);
    }
}