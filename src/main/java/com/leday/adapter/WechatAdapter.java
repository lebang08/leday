package com.leday.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.leday.R;
import com.leday.Util.MySingleton;
import com.leday.application.MyApplication;
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
        viewHold.mTitle.setText((position + 1) + ". " + mList.get(position).getTitle());
        viewHold.mAuthor.setText("来自微信: " + mList.get(position).getSource());

//        ImageLoader imgloader = new ImageLoader(MyApplication.getHttpQueue(), null);
//        ImageLoader.ImageListener imglistener = ImageLoader.getImageListener(viewHold.mImg, 0, 0);
//        imgloader.get(mList.get(position).getFirstImg(), imglistener);
        return convertView;
    }

    public class ViewHold {
        public TextView mTitle, mAuthor;
        public ImageView mImg;
    }
}