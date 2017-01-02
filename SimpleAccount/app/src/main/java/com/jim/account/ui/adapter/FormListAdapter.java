package com.jim.account.ui.adapter;

import android.content.Context;

import com.jim.account.R;
import com.jim.account.bean.AccountFormBean;
import com.jim.account.utils.NumberUtils;

import java.util.List;

/**
 * Created by zhuzhu on 2016/12/23.
 */

public class FormListAdapter extends BaseListAdapter<AccountFormBean> {
    private float[] mCounts;
    private String mUint;
    public FormListAdapter(Context context, List<AccountFormBean> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
        mUint = mCxt.getResources().getString(R.string.money_unit);
    }

    @Override
    public void convert(AdapterHolder helper, AccountFormBean item, int position) {
        super.convert(helper, item, position);
        helper.setText(R.id.tv_item_project,item.getProject());
        helper.setText(R.id.tv_item_precent,NumberUtils.format2point(item.getPercent())+"%");
        helper.setText(R.id.tv_item_count,String.format("%s %s", NumberUtils.format2point(item.getCount()),mUint));
        helper.getView(R.id.view_item_color).setBackgroundColor(item.getColor());
    }

}
