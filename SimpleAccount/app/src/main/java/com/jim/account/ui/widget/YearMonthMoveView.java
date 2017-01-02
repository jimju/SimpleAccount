package com.jim.account.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jim.account.R;

import java.util.Calendar;

/**
 * Created by jim on 2016/12/12.
 */

public class YearMonthMoveView extends RelativeLayout {
    public YearMonthMoveView(Context context) {
        this(context, null);
    }

    public YearMonthMoveView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public YearMonthMoveView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews(context);
    }

    ImageView mLeftArrow, mRighArrow;
    TextView mDate;
    int mYear, mMonth;
    OnDateChangedListener mDateChangedListener;

    private void initViews(Context context) {
        mLeftArrow = new ImageView(context);
        mRighArrow = new ImageView(context);
        mDate = new TextView(context);
        LayoutParams lParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        LayoutParams rParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        LayoutParams dParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rParams.addRule(ALIGN_PARENT_RIGHT);
        dParams.addRule(CENTER_IN_PARENT);
        mRighArrow.setLayoutParams(rParams);
        mLeftArrow.setLayoutParams(lParams);
        mDate.setLayoutParams(dParams);
        mRighArrow.setImageResource(R.mipmap.red_right_arrow);
        mLeftArrow.setImageResource(R.mipmap.red_left_arrow);
        addView(mDate);
        addView(mLeftArrow);
        addView(mRighArrow);

        //获取年月
        Calendar calendar = Calendar.getInstance();
        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH)+1;
        setDate(mYear,mMonth);
    }

    public void setDate(int year, int month) {
        mYear = year;
        mMonth = month;
        mDate.setText(String.format("%d - %d", mYear, mMonth));
        mRighArrow.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mMonth++;
                if (mMonth > 12) {
                    mYear++;
                    mMonth = 1;
                }
                mDate.setText(String.format("%d - %d", mYear, mMonth));
                if (mDateChangedListener != null) {
                    mDateChangedListener.ondateChanged(mYear, mMonth);
                }
            }
        });
        mLeftArrow.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mMonth--;
                if (mMonth <= 0) {
                    mYear--;
                    mMonth = 12;
                }
                mDate.setText(String.format("%d - %d", mYear, mMonth));
                if (mDateChangedListener != null) {
                    mDateChangedListener.ondateChanged(mYear, mMonth);
                }
            }
        });
        if (mDateChangedListener != null) {
            mDateChangedListener.ondateChanged(mYear, mMonth);
        }
    }

    public void setDateChangedListener(OnDateChangedListener dateChangedListener) {
        this.mDateChangedListener = dateChangedListener;
    }

    public interface OnDateChangedListener {
        void ondateChanged(int year, int month);
    }


}
