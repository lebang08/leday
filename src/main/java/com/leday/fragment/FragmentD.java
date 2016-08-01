package com.leday.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
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
        mData.add("程序想说感谢");
        mData.add("我没有想到");
        mData.add("只做了简单小功能的Le");
        mData.add("能一直获得您的支持");
        mData.add("非常感谢");
        mData.add("作为一名程序");
        mData.add("我会继续把Le送到需要它的地方去");
        mData.add("关于Le的功能：");
        mData.add("目前考虑要增加一些功能");
        mData.add("1.分享内容到微信/微博等社交平台的功能");
        mData.add("2.继续丰富聊天机器人的查询功能");
        mData.add("3.作一个吐槽墙");
        mData.add("4.作一些方块小游戏");
        mData.add("其实我最希望的是");
        mData.add("能真正和您交流意见");
        mData.add("欢迎戳我，或者，本表第一栏");
        mData.add("我们企鹅见");
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

    private void talkQQ(boolean isExist) {
        if (isExist == false) {
            ToastUtil.showMessage(getActivity(), "手机安装腾讯QQ才可以对话哦");
            return;
        } else {
            String urltwo = "mqqwpa://im/chat?chat_type=wpa&uin=443664360";
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(urltwo)));
            return;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position == 0 || position == 15 || position == 16) {
            /**
             *这两个事件，打开QQ
             */
            boolean isExist = isApkInstalled(getActivity(), "com.tencent.mobileqq");
            talkQQ(isExist);
        } else {
            //其余选项事件
            doSnackbar(view, "前往勾搭小图灵", "Go");
        }
    }

    //Snack抽出
    private void doSnackbar(View view, String content, String hint) {
        Snackbar.make(view, content, Snackbar.LENGTH_SHORT).setActionTextColor(Color.parseColor("#ffffff")).setAction(hint, mySnackOnclick()).show();
    }

    @Override
    public void onClick(View v) {
        doSnackbar(v, "前往勾搭小图灵", "Go");
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