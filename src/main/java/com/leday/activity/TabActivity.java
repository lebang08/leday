package com.leday.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.leday.R;
import com.leday.fragment.FragmentA;
import com.leday.fragment.FragmentB;
import com.leday.fragment.FragmentC;
import com.leday.fragment.FragmentD;

import java.util.ArrayList;
import java.util.List;

public class TabActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private FragmentPagerAdapter mFragmentAdapter;
    private List<Fragment> mFragmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);

        initTab();
    }

    private void initTab() {
        mViewPager = (ViewPager) findViewById(R.id.viewpager_activity_tab);
        mFragmentList = new ArrayList<>();
        Fragment fragment_a = new FragmentA();
        Fragment fragment_b = new FragmentB();
        Fragment fragment_c = new FragmentC();
        Fragment fragment_d = new FragmentD();
        mFragmentList.add(fragment_a);
        mFragmentList.add(fragment_b);
        mFragmentList.add(fragment_c);
        mFragmentList.add(fragment_d);
        mFragmentAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragmentList.get(position);
            }

            @Override
            public int getCount() {
                return mFragmentList.size();
            }
        };
        mViewPager.setAdapter(mFragmentAdapter);
    }
}