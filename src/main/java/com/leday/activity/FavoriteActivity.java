package com.leday.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.leday.R;

import java.util.ArrayList;

//TODO 将收藏的表在此查询载入
public class FavoriteActivity extends Activity {

    private ListView mListView;
    private ArrayList<String> mDataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        initView();
    }

    private void initView() {
        mListView = (ListView) findViewById(R.id.listview_activity_favoriter);
        for (int i = 0; i < 10; i++) {
            mDataList.add("收藏" + i);
        }
        ArrayAdapter mAdapter = new ArrayAdapter(FavoriteActivity.this, android.R.layout.simple_list_item_1, mDataList);
        mListView.setAdapter(mAdapter);
    }

    public void close(View view) {
        finish();
    }
}