package com.jim.account.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.jim.account.R;
import com.jim.account.ui.base.BaseFragment;
import com.jim.account.ui.widget.chart.anim.Anim;
import com.jim.account.ui.widget.chart.data.LineChartData;
import com.jim.account.ui.widget.chart.view.LineChart;

/**
 * Created by zhuzhu on 2016/12/17.
 */

public class FormCountFragment extends BaseFragment {

    LineChart mLineChart;
    LineChartData mLineChartData;
    ListView mListView;
    @Override
    protected int getLayout() {
        return R.layout.fragment_form_count;
    }

    public static FormCountFragment newInstance() {
        
        Bundle args = new Bundle();
        
        FormCountFragment fragment = new FormCountFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initViews(View view) {
        super.initViews(view);
        mLineChart = (LineChart) view.findViewById(R.id.linechart_fragment_count_form);
        mListView = (ListView) view.findViewById(R.id.nestedlistview_fragment_count_form);
        initTest();
    }

    void initTest(){
        float[] y = {100f,56.6f,33.3f,99.6f,77.8f,66.6f,21.9f,100f,56.6f,33.3f,99.6f,77.8f,66.6f,21.9f,100f,56.6f,33.3f,99.6f,77.8f,66.6f,21.9f,100f,56.6f,33.3f,99.6f,77.8f,66.6f,21.9f};
        String[] x = {"12-01","12-02","12-03","12-04","12-05","12-06","12-07","12-08","12-09","12-10","12-11","12-12","12-13","12-14","12-15","12-16","12-17","12-18","12-19","12-20","12-21"};
        mLineChartData = LineChartData.builder().setXdata(x).setYdata(y).setAnimType(Anim.ANIM_ALPHA).setYpCount(5).setXpCount(10).build();
        mLineChart.setChartData(mLineChartData);
    }
}
