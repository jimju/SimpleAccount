package com.jim.account.ui.fragment;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.jim.account.R;
import com.jim.account.bean.AccountBean;
import com.jim.account.bean.AccountFormBean;
import com.jim.account.model.AccountModel;
import com.jim.account.model.imp.AccountModelImp;
import com.jim.account.ui.adapter.FormPropertyListAdapter;
import com.jim.account.ui.base.BaseFragment;
import com.jim.account.ui.widget.chart.anim.Anim;
import com.jim.account.ui.widget.chart.data.HistogramData;
import com.jim.account.ui.widget.chart.view.Histogram;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhuzhu on 2016/12/17.
 */

public class FormPropertyFragment extends BaseFragment {

    Histogram mHistogram;
    HistogramData mHistogramData;
    ListView mListView;
    private int[] mColors;
    @Override
    protected int getLayout() {
        return R.layout.fragment_form_property;
    }

    public static FormPropertyFragment newInstance() {
        
        Bundle args = new Bundle();
        
        FormPropertyFragment fragment = new FormPropertyFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initViews(View view) {
        super.initViews(view);
        mHistogram = (Histogram) view.findViewById(R.id.histogram_fragment_property_form);
        mListView = (ListView) view.findViewById(R.id.nestedlistview_fragment_property_form);
        init();
    }

    void init(){
        mColors = getActivity().getResources().getIntArray(R.array.histogram_color);
        updateData();
    }

    /**
     * 获取账单数据
     */
    private  void updateData(){

        AccountModel model = new AccountModelImp(getActivity());
        List<AccountBean> list =  model.queryAllGroupByNormal();
        if (list.size() < 1){
            return;
        }
        List<AccountFormBean> beans =  new ArrayList<>();
        float[] counts = new float[list.size()];
        for (int i = 0; i < list.size(); i++) {
            counts[i] = (float) model.getCountByNormal(list.get(i).getNormal());
        }
        Resources res = getActivity().getResources();
        String[] x = {res.getString(R.string.text_not_normal),res.getString(R.string.text_normal)};
        mColors = getActivity().getResources().getIntArray(R.array.histogram_color);
        mHistogramData = mHistogramData.builder().setXdata(x).setYdata(counts).setXpCount(counts.length).setYpCount(counts.length).setAnimType(Anim.ANIM_ALPHA).build();
        mHistogram.setChartData(mHistogramData);
        for (int i = 0; i < list.size(); i++){
            AccountBean bean = list.get(i);
//            double precent = NumberUtils.formatPoint(bean.getPay() / mTotal,3) * 100;
            int color = mColors[i%mColors.length];
            beans.add(new AccountFormBean(bean,color,0,counts[i]));
        }

        FormPropertyListAdapter adapter = new FormPropertyListAdapter(getActivity(),beans,R.layout.item_form_account);
        mListView.setAdapter(adapter);
    }

}
