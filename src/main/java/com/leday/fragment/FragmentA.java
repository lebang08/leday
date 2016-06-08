package com.leday.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
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

public class FragmentA extends Fragment {

    private ListView mListView;
    private List<String> mDataList = new ArrayList<>();
    private ArrayAdapter<String> mAdapter;

    private static final String URL = "http://v.juhe.cn/boxoffice/rank.php?key=390b1c633ac041de0008bb73c769063c&area=CN";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_a, container, false);
        initView(view);
        initEvent();
        return view;
    }

    private void initView(View view) {
        mListView = (ListView) view.findViewById(R.id.listview_fragment_a);
    }

    private void initEvent() {
        StringRequest stringReques = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
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
        stringReques.setTag("GET");
//        MyApplication.getHttpQueue().add(stringReques);
        Volley.newRequestQueue(getActivity()).add(stringReques);
    }

    private void Dosuccess(String response) {
        JSONObject obj;
        JSONArray arr;
        String mFilm, name, total;
        try {
            obj = new JSONObject(response);
            arr = obj.getJSONArray("result");
            for (int i = 0; i <= arr.length(); i++) {
                obj = arr.getJSONObject(i);
                name = obj.getString("name");
                total = obj.getString("tboxoffice");
                mFilm = name + "： 总票房 = " + total + "万元";
                mDataList.add(mFilm);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, mDataList);
        mListView.setAdapter(mAdapter);
        setListViewHeightBasedOnChildren(mListView);
    }

    /**
     * 解决嵌套ListView 和 Scrollview的问题
     */
    public void setListViewHeightBasedOnChildren(ListView listView) {
        // 获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
            // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            // 计算子项View 的宽高
            listItem.measure(0, 0);
            // 统计所有子项的总高度
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }
}