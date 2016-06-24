package com.leday.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.leday.R;
import com.leday.Util.LogUtil;
import com.leday.entity.Weather;

import java.util.List;

/**
 * Created by Administrator on 2016/6/2.
 */
public class weatheradapter extends BaseAdapter {

    private List<Weather> mList;
    private LayoutInflater mInflater;

    public weatheradapter(Context context, List<Weather> data) {
        mInflater = LayoutInflater.from(context);
        this.mList = data;
    }

    @Override
    public int getCount() {
        LogUtil.e("size", mList.size() + "");
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
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.itemlist_main, null);
            viewHolder.txtdate = (TextView) convertView.findViewById(R.id.item_date);
            viewHolder.txtnight = (TextView) convertView.findViewById(R.id.item_night);
            viewHolder.txtday = (TextView) convertView.findViewById(R.id.item_day);
            viewHolder.txttemper = (TextView) convertView.findViewById(R.id.item_temper);
            viewHolder.txtweek = (TextView) convertView.findViewById(R.id.item_week);
            viewHolder.txtwind = (TextView) convertView.findViewById(R.id.item_wind);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.txtdate.setText(mList.get(position).getDate());
        viewHolder.txtnight.setText(mList.get(position).getNight());
        viewHolder.txtday.setText(mList.get(position).getDayTime());
        viewHolder.txttemper.setText(mList.get(position).getTemperature());
        viewHolder.txtweek.setText(mList.get(position).getWeek());
        viewHolder.txtwind.setText(mList.get(position).getWind());
        return convertView;
    }

    public class ViewHolder {
        public TextView txtdate, txtnight, txtday, txttemper, txtweek, txtwind;
    }
}