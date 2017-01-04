package com.jim.account.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.jim.account.R;


/**
 * Created by Administrator on 2016/12/13.
 */
public class CalendarAdapter extends AbsCalendarAdapter {


    public CalendarAdapter(Context mContext, int year, int month) {
        super(mContext, year, month);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null){
            convertView = mInflater.inflate(R.layout.item_calendar,parent,false);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        }else {
            vh = (ViewHolder) convertView.getTag();
        }
        if (mDates.get(position)>0){
            vh.tvDay.setText(String.valueOf(mDates.get(position)));
            convertView.setClickable(true);
        }else {
            vh.tvDay.setText("");
            convertView.setClickable(false);
            convertView.setFocusable(false);
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mListener != null && ((int)getItem(position)) != 0){
                    mListener.onDayClick(year,month + 1, (Integer) getItem(position),v);
                }
            }
        });
        vh.tvCount.setVisibility(View.VISIBLE);
        return convertView;
    }


}
