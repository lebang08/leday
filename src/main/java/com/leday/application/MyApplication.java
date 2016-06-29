package com.leday.application;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Administrator on 2016/6/8.
 */
public class MyApplication extends Application {

    private static MyApplication mInstance = null;
    private static RequestQueue mQueue;

    public static MyApplication getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        mQueue = Volley.newRequestQueue(getApplicationContext());
    }
    public static RequestQueue getHttpQueue() {
        return mQueue;
    }
}