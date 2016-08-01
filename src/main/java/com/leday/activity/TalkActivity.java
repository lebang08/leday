package com.leday.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.leday.R;
import com.leday.Util.TalkHttpUtils;
import com.leday.Util.ToastUtil;
import com.leday.adapter.TalkAdapter;
import com.leday.application.MyApplication;
import com.leday.entity.Talk;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TalkActivity extends AppCompatActivity {

    private EditText mInputMsg;
    private Button mSendMsg;

    private ListView mMsgs;
    private TalkAdapter mAdapter;
    private List<Talk> mDatas;

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            //等待接收，子线程完成数据的返回
            Talk fromMessge = (Talk) msg.obj;
            mDatas.add(fromMessge);
            mAdapter.notifyDataSetChanged();
            mMsgs.setSelection(mDatas.size() - 1);
        }

        ;
    };

    @Override
    protected void onStop() {
        super.onStop();
        MyApplication.getHttpQueue().cancelAll("GET");
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_talk);

        initView();
        initDatas();
        // 初始化事件
        initListener();
    }

    private void initView() {
        mMsgs = (ListView) findViewById(R.id.id_listview_msgs);
        mInputMsg = (EditText) findViewById(R.id.id_input_msg);
        mSendMsg = (Button) findViewById(R.id.id_send_msg);
    }

    private void initDatas() {
        mDatas = new ArrayList<>();
        mDatas.add(new Talk("客官，您好，我是挨骂替身小图灵", Talk.Type.INCOMING, new Date()));
        mAdapter = new TalkAdapter(this, mDatas);
        mMsgs.setAdapter(mAdapter);
    }

    private void initListener() {
        mSendMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String toMsg = mInputMsg.getText().toString();
                if (TextUtils.isEmpty(toMsg)) {
                    ToastUtil.showMessage(TalkActivity.this, "发送消息不能为空哦!");
                    return;
                }

                Talk toMessage = new Talk();
                toMessage.setDate(new Date());
                toMessage.setType(Talk.Type.OUTCOMING);
                toMessage.setMsg(toMsg);
                //数据中加入toMessage对象，发送消息
                mDatas.add(toMessage);
                mAdapter.notifyDataSetChanged();
                mMsgs.setSelection(mDatas.size() - 1);

                //发送后置空消息EditText中的消息
                mInputMsg.setText("");

                //开启子线程接收fromMessage的消息
                new Thread() {
                    public void run() {
                        Talk fromMessage = TalkHttpUtils.sendMessage(toMsg);
                        Message msg = Message.obtain();
                        msg.obj = fromMessage;
                        mHandler.sendMessage(msg);
                    }

                    ;
                }.start();
            }
        });
    }
}