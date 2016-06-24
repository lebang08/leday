package com.leday.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.leday.R;
import com.leday.entity.Wechat;

import java.util.List;

/**
 * Created by Administrator on 2016/6/20.
 */
public class WechatAdapter extends BaseAdapter {

    private List<Wechat> mList;
    private LayoutInflater mInflater;

    public WechatAdapter(Context mContext, List<Wechat> mList) {
        this.mList = mList;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHold viewHold;
        if (convertView == null) {
            viewHold = new ViewHold();
            convertView = mInflater.inflate(R.layout.item_wechat, null);
            viewHold.mImg = (ImageView) convertView.findViewById(R.id.item_wechat_img);
            viewHold.mTitle = (TextView) convertView.findViewById(R.id.item_wechat_title);
            viewHold.mAuthor = (TextView) convertView.findViewById(R.id.item_wechat_author);
            convertView.setTag(viewHold);
        } else {
            viewHold = (ViewHold) convertView.getTag();
        }
        viewHold.mImg.setImageResource(R.drawable.star_title);
        viewHold.mTitle.setText( (position+1) + ". " +  mList.get(position).getTitle());
        viewHold.mAuthor.setText("来自微信: " + mList.get(position).getSource());

        //TODO 加载网络图片，不能在主线程，非主线程又不能更新UI
//        URL url = null;
//        try {
//            url = new URL(mList.get(position).getFirstImg());
//            viewHold.mImg.setImageBitmap(BitmapFactory.decodeStream(url.openStream()));
//        } catch (java.io.IOException e) {
//            e.printStackTrace();
//        }
        return convertView;
    }

    public class ViewHold {
        public TextView mTitle, mAuthor;
        public ImageView mImg;
    }
}