package com.leday.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.leday.Impl.ListViewHightImpl;
import com.leday.R;
import com.leday.Util.ToastUtil;
import com.leday.activity.TalkActivity;

import java.util.ArrayList;
import java.util.List;

public class FragmentD extends Fragment implements AdapterView.OnItemClickListener, View.OnClickListener {

    private FloatingActionButton mFab;

    private ListView mListView;
    private List<String> mData = new ArrayList<>();
    private ArrayAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_d, container, false);
        initView(view);
        DoEvent();
        return view;
    }

    private void initView(View view) {
        mFab = (FloatingActionButton) view.findViewById(R.id.fab_fragment_d);
        mListView = (ListView) view.findViewById(R.id.listview_fragment_d);

        mFab.setOnClickListener(this);
        mListView.setOnItemClickListener(this);
    }

    private void initData() {
        mData.add("程序有话说");
        mData.add("关于应用");
        mData.add("想吐槽");
        mData.add("a.找替身吐槽");
        mData.add("b.找程序吐槽");
        mData.add("关于数据");
        mData.add("本应用内数据均来自公共API");
        mData.add("对数据的真实性、客观性不负责");
        mData.add("但对时效性负责");
        mData.add("如果你有好的建议或意见");
        mData.add("欢迎戳我(或本表第五栏)");
        mData.add("关于初衷");
        mData.add("原本有个idea");
        mData.add("就抽空搭建了这个APP，结果项目又来了");
        mData.add("所以......");
        mData.add("或许今后会继续更新");
        mData.add("但目前");
        mData.add("权当是支持谷歌的Material Design了");
    }

    private void DoEvent() {
        initData();
        mAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, mData);
        mListView.setAdapter(mAdapter);
        new ListViewHightImpl(mListView).setListViewHeightBasedOnChildren();
    }

    public static final boolean isApkInstalled(Context context, String packageName) {
        try {
            context.getPackageManager().getApplicationInfo(packageName, PackageManager.GET_SHARED_LIBRARY_FILES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position == 4 || position == 10) {
            boolean isExist = isApkInstalled(getActivity(), "com.tencent.mobileqq");
            if (isExist == false) {
                ToastUtil.showMessage(getActivity(), "手机安装腾讯QQ才可以对话哦");
                return;
            } else {
                String urltwo = "mqqwpa://im/chat?chat_type=wpa&uin=443664360";
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(urltwo)));
                return;
            }
        } else {
            Snackbar.make(view, "你戳了我，想干嘛？", Snackbar.LENGTH_SHORT).setAction("吐槽", mySnackOnclick()).show();
        }
    }

    @Override
    public void onClick(View v) {
        Snackbar.make(v, "觉得程序是个傻逼", Snackbar.LENGTH_LONG).setAction("就戳我", mySnackOnclick()).show();
    }

    @NonNull
    private View.OnClickListener mySnackOnclick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), TalkActivity.class));
            }
        };
    }
}