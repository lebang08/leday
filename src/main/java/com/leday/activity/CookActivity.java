package com.leday.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.leday.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CookActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView mListView;
    private ArrayAdapter<String> mCookadapter;
    private List<String> mCategoryList = new ArrayList<>();
    private List<String> mCategoryIdList = new ArrayList<>();

    private static final String URL_COOK = "http://apicloud.mob.com/v1/cook/category/query?key=135b8ce813980";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook);

        initView();
        getJson();
    }

    private void initView() {
        mListView = (ListView) findViewById(R.id.listview_activity_cook);
        mListView.setOnItemClickListener(this);
    }

    public void getJson() {
//        String parseString = new String(URL.getBytes("ISO-8859-1"), "utf-8");
        StringRequest cookRequest = new StringRequest(Request.Method.GET, URL_COOK, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Dosuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Wrong-BACK", "联接错误原因： " + error.getMessage(), error);
            }
        });
//        {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> hashMap = new HashMap<String, String>();
//                hashMap.put("token", "89F862A5EA64D897FB1D05F95C113AD8");
//                return hashMap;
//            }
//        };
        cookRequest.setTag("post");
        Volley.newRequestQueue(this).add(cookRequest);
    }

    private void Dosuccess(String response) {
        //                Log.e("cook0", response);
        JSONObject obj, obj1, obj2;
        JSONArray arr, arr1;
        String mCookname;
        try {
            obj = new JSONObject(response);
//                    Log.e("cook1", obj.toString());
            obj = obj.getJSONObject("result");
//                    Log.e("cook2", obj.toString());
            arr = obj.getJSONArray("childs");
//                    Log.e("cook3", arr.toString());
            for (int i = 0; i <= arr.length(); i++) {
                obj = arr.getJSONObject(i);
                Log.e("cook4", obj.toString());
                arr1 = obj.getJSONArray("childs");
                Log.e("cook5", arr1.toString());
                for (int j = 0; j <= arr1.length(); j++) {
                    obj1 = arr1.getJSONObject(j);
                    Log.e("cook6", obj1.toString());
                    obj2 = obj1.getJSONObject("categoryInfo");
                    Log.e("cook7", obj2.toString());
                    mCookname = obj2.getString("name");
                    mCategoryList.add(mCookname);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mCookadapter = new ArrayAdapter<>(CookActivity.this, android.R.layout.simple_list_item_1, mCategoryList);
        mListView.setAdapter(mCookadapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}