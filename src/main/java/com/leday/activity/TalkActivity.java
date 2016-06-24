package com.leday.activity;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.leday.R;

public class TalkActivity extends AppCompatActivity {
    private ListView mMsgs;
//    private ChatMessageAdapter mAdapter;
//    private List<ChatMessage> mDatas;

    private EditText mInputMsg;
    private Button mSendMsg;

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            // 等待接收，子线程完成数据的返回
//            ChatMessage fromMessge = (ChatMessage) msg.obj;
//            mDatas.add(fromMessge);
//            mAdapter.notifyDataSetChanged();
//            mMsgs.setSelection(mDatas.size() - 1);
        }

        ;
    };

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
//        mDatas = new ArrayList<ChatMessage>();
//        mDatas.add(new ChatMessage("你好，小慕为您服务", Type.INCOMING, new Date()));
//        mAdapter = new ChatMessageAdapter(this, mDatas);
//        mMsgs.setAdapter(mAdapter);
    }

    private void initListener() {
        mSendMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String toMsg = mInputMsg.getText().toString();
                if (TextUtils.isEmpty(toMsg)) {
                    Toast.makeText(TalkActivity.this, "发送消息不能为空！",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

//                ChatMessage toMessage = new ChatMessage();
//                toMessage.setDate(new Date());
//                toMessage.setMsg(toMsg);
//                toMessage.setType(Type.OUTCOMING);
//                mDatas.add(toMessage);
//                mAdapter.notifyDataSetChanged();
//                mMsgs.setSelection(mDatas.size() - 1);

                mInputMsg.setText("");

                new Thread() {
                    public void run() {
//                        ChatMessage fromMessage = HttpUtils.sendMessage(toMsg);
//                        Message m = Message.obtain();
//                        m.obj = fromMessage;
//                        mHandler.sendMessage(m);
                    }

                    ;
                }.start();
            }
        });
    }
}