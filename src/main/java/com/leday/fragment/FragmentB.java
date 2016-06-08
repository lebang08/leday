package com.leday.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.leday.Impl.ListViewHightImpl;
import com.leday.R;
import com.leday.activity.CookActivity;
import com.leday.activity.TodayActivity;
import com.leday.entity.Today;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class FragmentB extends Fragment implements AdapterView.OnItemClickListener {

    private ListView mListView;
    private ArrayAdapter mAdapter;
    private List<String> mDataList = new ArrayList<>();
    private List<Today> mTodayList = new ArrayList<>();

    private Calendar mCalendar = Calendar.getInstance();
    private static final String URL = "http://v.juhe.cn/todayOnhistory/queryEvent.php?key=776cbc23ec84837a647a7714a0f06bff&date=";
    private String URL_TOTAL;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_b, container, false);

        initView(view);
        getJson();
        return view;
    }

    private void initView(View view) {
        mListView = (ListView) view.findViewById(R.id.listview_fragment_b);

        mListView.setOnItemClickListener(this);
    }


    public void getJson() {
        //获取时间,月和日
        int localMonth = mCalendar.get(Calendar.MONTH);
        int localDay = mCalendar.get(Calendar.DAY_OF_MONTH);
        URL_TOTAL = URL + (localMonth + 1) + "/" + localDay;
        //请求网络
        StringRequest todayrequest = new StringRequest(Request.Method.GET, URL_TOTAL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Dosuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        todayrequest.setTag("GET");
        Volley.newRequestQueue(getActivity()).add(todayrequest);
    }

    //请求成功的处理
    private void Dosuccess(String response) {
        JSONObject obj;
        JSONArray arr;
        Today today;
        String merge;
        try {
            obj = new JSONObject(response);
            Log.e("URL_TOTAL", obj.toString());
            arr = obj.getJSONArray("result");
            Log.e("URL_TOTAL", arr.toString());
            for (int i = 0; i <= arr.length(); i++) {
                obj = arr.getJSONObject(i);
                today = new Today();
                today.setDate(obj.getString("date"));
                today.setTitle(obj.getString("title"));
                today.setE_id(obj.getString("e_id"));
                merge = today.getDate() + ": " + today.getTitle();
                mDataList.add(merge);
                mTodayList.add(today);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, mDataList);
        mListView.setAdapter(mAdapter);
        new ListViewHightImpl(mListView).setListViewHeightBasedOnChildren();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getActivity(), TodayActivity.class);
        intent.putExtra("locale_id", mTodayList.get(position).getE_id());
        startActivity(intent);
    }
}