package com.jim.account.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ListView;

import com.jim.account.R;
import com.jim.account.bean.AccountBean;
import com.jim.account.bean.AccountFormBean;
import com.jim.account.model.AccountModel;
import com.jim.account.model.imp.AccountModelImp;
import com.jim.account.ui.adapter.FormListAdapter;
import com.jim.account.ui.base.BaseFragment;
import com.jim.account.ui.widget.chart.anim.Anim;
import com.jim.account.ui.widget.chart.data.PieChartData;
import com.jim.account.ui.widget.chart.view.PieChart;
import com.jim.account.utils.NumberUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FormTypeFragment extends BaseFragment {
    PieChart mPieChart;
    ListView mListView;
    PieChartData mPieChartData;
    private double mTotal;
    private int[] mColors;


    @Override
    protected int getLayout() {
        return R.layout.fragment_form_type;
    }

    @Override
    protected int getToolbarId() {
        return R.id.toolbar;
    }


    @Override
    public boolean hasCustomToolbar() {
        return false;
    }

    public static FormTypeFragment newInstance() {
        Bundle args = new Bundle();
        FormTypeFragment fragment = new FormTypeFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected void initViews(View view) {
        super.initViews(view);
        mColors = getActivity().getResources().getIntArray(R.array.pie_colors);
        mPieChart = (PieChart) view.findViewById(R.id.piechart_fragment_type_form);
        mListView = (ListView) view.findViewById(R.id.nestedlistview_fragment_type_form);
        mPieChartData = PieChartData.builder().setDatas(new float[]{1.0f}).setColors(mColors).setAnimType(Anim.ANIM_ALPHA).build();
        mPieChart.setChartData(mPieChartData);
        updateData();
    }

    /**
     * 获取账单数据
     */
    private  void updateData(){
        AccountModel model = new AccountModelImp(getActivity());
        List<AccountBean> list =  model.queryAllGroupByProject();
        if (list.size() < 1){
            return;
        }
        List<AccountFormBean> beans =  new ArrayList<>();
        float[] counts = new float[list.size()];
        for (int i = 0; i < list.size(); i++) {
            counts[i] = (float) model.getCountByProject(list.get(i).getProject());
        }
        mPieChartData.setDatas(counts);
        mPieChart.update(mPieChartData);
        mTotal = sumArray(counts);
        for (int i = 0; i < list.size(); i++){
            AccountBean bean = list.get(i);
            double precent = NumberUtils.formatPoint(bean.getPay() / mTotal,3) * 100;
            int color = mColors[i%mColors.length];
            beans.add(new AccountFormBean(bean,color,precent,counts[i]));
        }

        FormListAdapter adapter = new FormListAdapter(getActivity(),beans,R.layout.item_form_account);
        mListView.setAdapter(adapter);
    }

    private float sumArray(float[] arr){
        float sum = 0;
        for (float f:arr) {
            sum += f;
        }
        return sum;
    }
}
