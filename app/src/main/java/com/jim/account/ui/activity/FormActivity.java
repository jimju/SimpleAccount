package com.jim.account.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.jim.account.R;
import com.jim.account.ui.adapter.FormViewPagerAdapter;
import com.jim.account.ui.fragment.FormPropertyFragment;
import com.jim.account.ui.fragment.FormTypeFragment;

import java.util.Arrays;

public class FormActivity extends AppCompatActivity {
    ViewPager mViewPager;
    Fragment[] mFragments = {FormTypeFragment.newInstance(),FormPropertyFragment.newInstance()};
    String[] mTitles = {"类型","属性"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_activity);
        setupToolbar();
        initViews();
    }

    private void initViews() {
        mViewPager = (ViewPager) findViewById(R.id.viewpager_activity_form);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mViewPager.setAdapter(new FormViewPagerAdapter(getSupportFragmentManager(), Arrays.asList(mFragments),mTitles));
        tabLayout.setupWithViewPager(mViewPager);
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.mipmap.abc_ic_ab_back_mtrl_am_alpha);
        toolbar.setTitle(R.string.activity_form_title);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
