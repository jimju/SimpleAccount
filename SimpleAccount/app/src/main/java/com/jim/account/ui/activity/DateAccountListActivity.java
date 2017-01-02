package com.jim.account.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.jim.account.R;
import com.jim.account.bean.AccountBean;
import com.jim.account.db.AccountDbHelper;
import com.jim.account.model.AccountModel;
import com.jim.account.model.imp.AccountModelImp;
import com.jim.account.ui.adapter.AccoutsAdapter;
import com.jim.account.ui.fragment.GlideFragment;

import java.util.List;

public class DateAccountListActivity extends AccountListActivity {

    RecyclerView mRecyclerView;
    TextView mTextViewPay;
    AccountModel mModel;
    private AccoutsAdapter mAdapter;
    private String mDate; //日期
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_account_list);
        initDatas();
        initViews();
    }

    private void initViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(mDate);
        toolbar.setNavigationIcon(R.mipmap.abc_ic_ab_back_mtrl_am_alpha);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DateAccountListActivity.this.finish();
            }
        });
        mTextViewPay = (TextView) findViewById(R.id.textview_pay_activity_date);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_account_date_activity);
        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DateAccountListActivity.this,AccountNewActivity.class);
                intent.putExtra(AccountDbHelper.AccountColum.TIME,mDate);
                startActivityForResult(intent,ACCOUNT_CODE);
            }
        });
        getAccounts();
    }

    private void initDatas() {
        mModel = new AccountModelImp(this);
        mDate = getIntent().getStringExtra(AccountDbHelper.AccountColum.TIME);
    }

    @Override
    public void refreshList() {
        getAccounts();
        GlideFragment.toRefresh = true;
    }

    //从数据库中获取数据
    private void getAccounts(){
        List<AccountBean> list = mModel.queryAccountsByDate(mDate);
        mAdapter = new AccoutsAdapter(list, this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
        double todayPay = mModel.getCountByDate(mDate);
        mTextViewPay.setText(String.valueOf(todayPay));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ACCOUNT_CODE && resultCode == RESULT_OK){
            refreshList();
        }
    }
}
