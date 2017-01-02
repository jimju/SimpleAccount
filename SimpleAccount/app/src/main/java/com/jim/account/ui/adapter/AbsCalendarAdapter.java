package com.jim.account.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jim.account.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * Created by Administrator on 2016/12/13.
 */
public abstract class AbsCalendarAdapter extends BaseAdapter {
    protected Context mContext;
    protected int year;
    protected int month;
    protected Calendar mCalendar;
    protected LayoutInflater mInflater;
    protected List<Integer> mDates;
    protected DayClickListenner mListener;
//    private
    public AbsCalendarAdapter(Context mContext, int year, int month) {
        this.mContext = mContext;
        //Calendar中的月是从0开始的，0代表1月，1代表2月。。。
        this.month = month-1;
        this.year = year;
        init();
    }

    private void init() {
        mCalendar = Calendar.getInstance();
        //设置时间为当月的第一天
        mCalendar.set(year,month,1);
        mInflater = LayoutInflater.from(mContext);
        mDates = new ArrayList<>();
        //gridview从第几个开始
        int start = mCalendar.get(Calendar.DAY_OF_WEEK) - 1;
        //计算
        int size = start + mCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        int cday = 0;
        for (int i = 0; i < size; i++) {
            if (i>=start) {
                cday++;
            }
            mDates.add(cday);
        }
    }


    @Override
    public int getCount() {
        return mDates.size();
    }

    @Override
    public Object getItem(int position) {
        return mDates.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    public interface DayClickListenner{
        void onDayClick(int year, int month, int day, View convertView);
    }
    protected class ViewHolder{
        TextView tvDay;
        TextView tvCount;

        public ViewHolder(View itemView) {
            tvDay = (TextView) itemView.findViewById(R.id.tv_item_day);
            tvCount = (TextView) itemView.findViewById(R.id.tv_item_account);
        }
    }

    public void setDayClickListennerListener(DayClickListenner dayClickListenner) {
        this.mListener = dayClickListenner;
    }


}
