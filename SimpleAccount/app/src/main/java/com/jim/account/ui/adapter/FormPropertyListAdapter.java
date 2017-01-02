package com.jim.account.ui.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;

import com.jim.account.R;
import com.jim.account.bean.AccountFormBean;
import com.jim.account.utils.NumberUtils;

import java.util.List;

/**
 * Created by zhuzhu on 2016/12/23.
 */

public class FormPropertyListAdapter extends BaseListAdapter<AccountFormBean> {
    private String mUint;
    private Resources mRes;
    public FormPropertyListAdapter(Context context, List<AccountFormBean> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
        mRes = context.getResources();
        mUint = mRes.getString(R.string.money_unit);
    }

    @Override
    public void convert(AdapterHolder helper, AccountFormBean item, int position) {
        super.convert(helper, item, position);
        //项目
        helper.setText(R.id.tv_item_project,item.getNormal().equals("Y")?mRes.getString(R.string.text_normal):mRes.getString(R.string.text_not_normal));
//        helper.setText(R.id.tv_item_precent,item.getPercent()+"%");
        helper.getView(R.id.tv_item_precent).setVisibility(View.INVISIBLE);
        //花费
        helper.setText(R.id.tv_item_count,String.format("%s %s", NumberUtils.format2point(item.getCount()),mUint));
        //颜色
        helper.getView(R.id.view_item_color).setBackgroundColor(item.getColor());
    }

}
