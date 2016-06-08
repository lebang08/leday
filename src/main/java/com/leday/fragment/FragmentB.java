package com.leday.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.leday.R;

import java.util.ArrayList;
import java.util.List;

public class FragmentB extends Fragment {

    private ListView mListView;
    private ArrayAdapter mAdapter;
    private List<String> mDataList = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_b, container, false);

        initView(view);
        return view;
    }

    private void initView(View view) {
        mListView = (ListView) view.findViewById(R.id.listview_fragment_b);
    }
}
