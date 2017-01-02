package com.jim.account.ui.activity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jim.account.R;
import com.jim.account.ui.adapter.AdapterHolder;
import com.jim.account.ui.adapter.BaseListAdapter;
import com.jim.account.ui.adapter.CalendarAdapter;
import com.jim.account.ui.widget.CalculateView;
import com.jim.account.utils.AccountUitls;

import java.util.Arrays;
import java.util.List;

public abstract class AccountActivity extends AppCompatActivity implements View.OnClickListener, CalculateView.OnConfirmListener {
    protected TextView mTextViewRemark, mTextViewTime, mTextViewType, mTextViewSign, mTextViewCalculate1, mTextViewCalculate2;
    protected GridView mGridView;
    protected CalculateView mCalculateView;
    protected RelativeLayout mRelativeLayout;
    protected ImageView mImageViewIcon;
    protected Dialog mTimeDialog;
    protected int mCurrentImageId = R.mipmap.type_big_0;
    int[] images = {R.mipmap.type_big_2, R.mipmap.type_big_4, R.mipmap.type_big_10, R.mipmap.type_big_12, R.mipmap.type_big_37, R.mipmap.type_big_29,
            R.mipmap.type_big_0,R.mipmap.type_big_3, R.mipmap.type_big_11, R.mipmap.type_big_7,R.mipmap.type_big_35, R.mipmap.type_big_1};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        initDatas();
        initViews();
    }

    protected  void initDatas(){};

    protected void initViews() {
        initToobar();
        mTextViewRemark = (TextView) findViewById(R.id.textbutton_remark_activity_account);
        mTextViewTime = (TextView) findViewById(R.id.textbutton_time_activity_acount);
        mTextViewType = (TextView) findViewById(R.id.textview_type_activity_account);

        mTextViewSign = (TextView) findViewById(R.id.textview_sign_activity_account);
        mTextViewCalculate1 = (TextView) findViewById(R.id.textview_calculate_activity_account);
        mTextViewCalculate2 = (TextView) findViewById(R.id.textview_calculate2_activity_account);
        mCalculateView = (CalculateView) findViewById(R.id.calculateview_account_activity);
        mCalculateView.setTextView(mTextViewCalculate1, mTextViewCalculate2, mTextViewSign);


        mRelativeLayout = (RelativeLayout) findViewById(R.id.relativelayout_type_activity_account);
        mImageViewIcon = (ImageView) findViewById(R.id.imageview_icon_activity_account);
        mCalculateView.setOnConfirmListener(this);
        mTextViewType.setOnClickListener(this);
        mTextViewRemark.setOnClickListener(this);
        mTextViewTime.setOnClickListener(this);
        //账单种类
        mGridView = (GridView) findViewById(R.id.gridview_classfy_activity_account);
        String[] names = getResources().getStringArray(R.array.account_classifies);
        mGridView.setAdapter(new AccountGridViewAdapter(this, Arrays.asList(names), R.layout.item_account_activity_gridview));

    }

    private void initToobar() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(R.string.activity_account_title);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.textbutton_remark_activity_account:
                AccountUitls.remarkDialog(AccountActivity.this, new AccountUitls.OnTextConfirmListener() {
                    @Override
                    public void onTextConfirm(String text) {
                        mTextViewRemark.setText(text);
                    }
                });

                break;
            case R.id.textbutton_time_activity_acount:

                mTimeDialog = AccountUitls.dateDialog(AccountActivity.this, new CalendarAdapter.DayClickListenner() {
                    @Override
                    public void onDayClick(int year, int month, int day, View convertView) {
                        mTextViewTime.setText(String.format("%d-%d-%d", year, month, day));
                        mTimeDialog.dismiss();
                    }
                });
                break;
            case R.id.textbutton_type_activity_acount:

                break;
        }
    }



    public class AccountGridViewAdapter extends BaseListAdapter<String> {
        public AccountGridViewAdapter(Context context, List<String> mDatas, int itemLayoutId) {
            super(context, mDatas, itemLayoutId);
        }

        @Override
        public void convert(AdapterHolder helper, final String item, final int position) {
            super.convert(helper, item, position);
            helper.setText(R.id.textview_item_name, item);
            helper.setImageResource(R.id.imageview_item_icon, images[position]);
            final ImageView imageView = helper.getView(R.id.imageview_item_icon);
            helper.getConvertView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AccountUitls.changeType(AccountActivity.this, mRelativeLayout, imageView, mImageViewIcon);
                    mTextViewType.setText(item);
                    mCurrentImageId = images[position];
                }
            });
        }
    }
}
