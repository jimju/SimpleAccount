package com.jim.account.ui.base;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.jim.account.R;

/**
 * Created by jimju on 2016/12/23.
 * 带返回的Activity
 */

public abstract class BaseBackActivity extends AppCompatActivity {
    protected Toolbar mToolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        initBar();
        initViews();
    }

    protected void initViews() {
    }

    private void initBar() {
        if(!hasCustomToolbar()) return;
        mToolbar = (Toolbar)findViewById(getToolbarId());
        mToolbar.setTitle(getTitle());
        mToolbar.setNavigationIcon(R.mipmap.abc_ic_ab_back_mtrl_am_alpha);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });

    }

    protected @IdRes
    int getToolbarId(){
        return R.id.toolbar;
    }

    public boolean hasCustomToolbar(){
        return false;
    }

    protected String getBarTitle(){
        return "";
    }

    protected abstract  @LayoutRes
    int getLayout();


}
