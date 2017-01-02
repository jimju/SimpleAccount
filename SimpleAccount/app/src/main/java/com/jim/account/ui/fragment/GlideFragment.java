package com.jim.account.ui.fragment;

import android.content.Intent;
import android.content.res.Resources;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import com.jim.account.R;
import com.jim.account.db.AccountDbHelper;
import com.jim.account.model.AccountModel;
import com.jim.account.model.InfoModel;
import com.jim.account.model.imp.AccountModelImp;
import com.jim.account.model.imp.PreferencesInfoModel;
import com.jim.account.ui.activity.DateAccountListActivity;
import com.jim.account.ui.adapter.AbsCalendarAdapter;
import com.jim.account.ui.adapter.AccountCalendarAdapter;
import com.jim.account.ui.adapter.CalendarAdapter;
import com.jim.account.ui.base.BaseFragment;
import com.jim.account.ui.widget.YearMonthMoveView;
import com.jim.account.utils.DateUtils;
import com.jim.account.utils.NumberUtils;

import java.util.Calendar;

/**
 * Created by jimju on 2016/12/12.
 * 支出流水
 */

public class GlideFragment extends BaseFragment implements YearMonthMoveView.OnDateChangedListener, CalendarAdapter.DayClickListenner {
    private Calendar mCalendar = Calendar.getInstance();
    GridView mGridView;
    YearMonthMoveView mYearMonthView;
    TextView mTextViewBudget,mTextViewSurplus,mTextViewPay;
    Resources mRes;
    public static boolean toRefresh = false;
    @Override
    protected int getLayout() {
        return R.layout.fragment_glide;
    }

    public static GlideFragment newInstance(){
        return new GlideFragment();
    }

    @Override
    protected int getToolbarId() {
        return R.id.toolbar;
    }

    @Override
    public boolean hasCustomToolbar() {
        return true;
    }

    @Override
    protected String getTitle() {
        return getString(R.string.fragment_glide_title,mCalendar.get(Calendar.YEAR));
    }

    @Override
    protected void initViews(View view) {
        super.initViews(view);
        mRes = getActivity().getResources();
        mGridView = (GridView) view.findViewById(R.id.gridview_fragment_glide_calendar);
        mYearMonthView = (YearMonthMoveView) view.findViewById(R.id.yearmonthview_fragment_glide);
        mYearMonthView.setDateChangedListener(this);
        mTextViewBudget = (TextView) view.findViewById(R.id.textview_budget_fragment_glide);
        mTextViewPay = (TextView) view.findViewById(R.id.textview_pay_fragment_glide);
        mTextViewSurplus = (TextView) view.findViewById(R.id.textview_surplus_fragment_glide);
        ondateChanged(mCalendar.get(Calendar.YEAR),mCalendar.get(Calendar.MONTH)+1);
        setPayInfo(DateUtils.getYear(),DateUtils.getMonth());
    }

    @Override
    public void ondateChanged(int year, int month) {
//        Log.d("ondateChanged",String.format("%d - %d",year,month));
        AbsCalendarAdapter adapter = new AccountCalendarAdapter(getActivity(),year,month);
        mGridView.setAdapter(adapter);
        adapter.setDayClickListennerListener(this);
        setPayInfo(year,month);
    }

    @Override
    public void onDayClick(int year, int month, int day, View convertView) {
//        Toast.makeText(getActivity(),String.format("%d - %d - %d",year,month,day),Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getActivity(), DateAccountListActivity.class);
        intent.putExtra(AccountDbHelper.AccountColum.TIME,String.format("%d-%d-%d",year,month,day));
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (toRefresh){
            ondateChanged(mCalendar.get(Calendar.YEAR),mCalendar.get(Calendar.MONTH)+1);
        }
    }

    /**
     * 账单信息
     * @param year
     * @param month
     */
    private void setPayInfo(int year,int month){
        AccountModel model = new AccountModelImp(getActivity());
        InfoModel infoModel = new PreferencesInfoModel(getActivity());
        //预算
        double budget = infoModel.getBudget();
        String sBudget = NumberUtils.format2point(budget);
        mTextViewBudget.setText(String.format("%s %s",sBudget,mRes.getString(R.string.money_unit)));
        String date = String.format("%d-%d-",year,month);
        //花费
        double pay = model.getCountByMonth(date);
        String sPay = NumberUtils.format2point(pay);
        mTextViewPay.setText(String.format("%s %s",sPay,mRes.getString(R.string.money_unit)));
        //结余
        double surplus = budget - pay;
        String sSurplus = NumberUtils.format2point(surplus);
        mTextViewSurplus.setTextColor(surplus>0?mRes.getColor(R.color.text_green):mRes.getColor(R.color.text_gray));
        mTextViewSurplus.setText(String.format("%s %s",sSurplus,mRes.getString(R.string.money_unit)));
    }

}
