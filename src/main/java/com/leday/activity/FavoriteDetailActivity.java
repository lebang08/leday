package com.leday.activity;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.leday.R;

public class FavoriteDetailActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today);

        initView();
    }

    private void initView() {
        String local_content = getIntent().getStringExtra("local_content");
        String local_title = getIntent().getStringExtra("local_date_title");

        ViewFlipper mViewFlipper = (ViewFlipper) findViewById(R.id.viewflipper_activity_today);
        mViewFlipper.setVisibility(View.GONE);
        TextView mTitle = (TextView) findViewById(R.id.txt_Today_title);
        TextView mContent = (TextView) findViewById(R.id.content_activity_today);
        TextView mUnLike = (TextView) findViewById(R.id.txt_Today_like);
        ImageView mBack = (ImageView) findViewById(R.id.img_today_back);

        mTitle.setText(local_title);
        mContent.setText(local_content);
        mUnLike.setText("取消收藏");

        mUnLike.setOnClickListener(this);
        mBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_Today_like:
                Snackbar.make(view, "抱歉，取消收藏功能暂未实现", Snackbar.LENGTH_SHORT).show();
                break;
            default:
                finish();
                break;
        }
    }
}