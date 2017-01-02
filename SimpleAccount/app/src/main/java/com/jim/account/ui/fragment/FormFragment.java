package com.jim.account.ui.fragment;

import android.view.View;

import com.jim.account.R;
import com.jim.account.ui.base.BaseFragment;

/**
 * Created by zhuzhu on 2016/12/15.
 */

public class FormFragment extends BaseFragment {
    @Override
    protected int getLayout() {
        return R.layout.fragment_form;
    }

    @Override
    public boolean hasCustomToolbar() {
        return true;
    }

    @Override
    protected int getToolbarId() {
        return R.id.toolbar;
    }

    @Override
    protected String getTitle() {
        return "2016年12月";
    }

   public static FormFragment newInstance() {
        FormFragment fragment = new FormFragment();
        return fragment;
    }

    @Override
    protected void initViews(View view) {
        super.initViews(view);

    }
}
