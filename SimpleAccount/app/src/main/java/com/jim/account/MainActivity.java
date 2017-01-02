package com.jim.account;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.jim.account.bean.AccountBean;
import com.jim.account.model.AccountModel;
import com.jim.account.model.InfoModel;
import com.jim.account.model.imp.AccountModelImp;
import com.jim.account.model.imp.PreferencesInfoModel;
import com.jim.account.ui.activity.AccountListActivity;
import com.jim.account.ui.activity.AccountNewActivity;
import com.jim.account.ui.activity.FormActivity;
import com.jim.account.ui.activity.SettingActivity;
import com.jim.account.ui.adapter.AccoutsAdapter;
import com.jim.account.ui.fragment.GlideFragment;
import com.jim.account.utils.Navigator;
import com.jim.account.utils.XlsUitls;

import java.util.Calendar;
import java.util.List;

public class MainActivity extends AccountListActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    TextView mTextviewMonthPay, mTextviewMonthName, mTextviewTodayPay, mTextViewNickName, mTextViewSign;
    RecyclerView mRecyclerView;
    private AccoutsAdapter mAdapter;
    private DrawerLayout mDrawerLayout;
    private Navigator mNavigator;


    private final static int SETTING_CODE = 0x12;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        mNavigator = new Navigator(getSupportFragmentManager(), R.id.framelayout_mainactivity_content);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(MainActivity.this, AccountNewActivity.class), ACCOUNT_CODE);
            }
        });

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mTextviewMonthName = (TextView) findViewById(R.id.textview_mainactivity_month_name);
        mTextviewMonthPay = (TextView) findViewById(R.id.textview_mainactivity_month_pay);
        mTextviewTodayPay = (TextView) findViewById(R.id.textview_mainactivity_today_pay);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_mainactivity);
        if (navigationView.getHeaderCount() > 0) {
            View v = navigationView.getHeaderView(0);
            mTextViewNickName = (TextView) v.findViewById(R.id.textview_menu_name);
            mTextViewSign = (TextView) v.findViewById(R.id.textview_menu_describe);
            setUserInfo();
        }

        findViewById(R.id.relativelayout_month_activity_account).setOnClickListener(this);
        getAccounts();


    }

    @Override
    public void onBackPressed() {

        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_index) {
            mNavigator.gotToTheRootFragmentBack();
        } else if (id == R.id.nav_glide) {
            if (!(mNavigator.getActiveFragment() instanceof GlideFragment)) {
                mNavigator.goTo(GlideFragment.newInstance());
            }
        } else if (id == R.id.nav_form) {
            startActivity(new Intent(MainActivity.this, FormActivity.class));
        } else if (id == R.id.nav_excel) {
            int os = Build.VERSION.SDK_INT;
            if (os < 23)
                XlsUitls.exportExcel(MainActivity.this);

            //android 6.0 读写 SD卡权限
            if (os > 22) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                }
                if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 2);
                }
                XlsUitls.exportExcel(MainActivity.this);
            }
        } else if (id == R.id.nav_setting) {
            startActivityForResult(new Intent(MainActivity.this, SettingActivity.class), SETTING_CODE);
        } else if (id == R.id.nav_logout) {
            finish();
        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ACCOUNT_CODE && resultCode == RESULT_OK) {
            refreshList();
        }
        if (requestCode == SETTING_CODE && resultCode == RESULT_OK) {
            setUserInfo();
        }
    }

    //从数据库中获取数据
    private void getAccounts() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DATE);
        AccountModel model = new AccountModelImp(this);
        String date = String.format("%d-%d-%d", year, month, day);
        List<AccountBean> list = model.queryAccountsByDate(date);
        mAdapter = new AccoutsAdapter(list, this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);

        double monthPay = model.getCountByMonth(String.format("%d-%d-", year, month));
        double todayPay = model.getCountByDate(date);
        mTextviewMonthName.setText(getResources().getString(R.string.text_month_pay, month));
        mTextviewMonthPay.setText(monthPay + "");
        mTextviewTodayPay.setText(todayPay + "");
    }

    public void openDrawer() {
        mDrawerLayout.openDrawer(GravityCompat.START);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.relativelayout_month_activity_account:
                if (!(mNavigator.getActiveFragment() instanceof GlideFragment)) {
                    mNavigator.goTo(GlideFragment.newInstance());
                }
                break;
        }
    }

    private void setUserInfo() {
        InfoModel model = new PreferencesInfoModel(this);
        mTextViewSign.setText(model.getUserSign());
        mTextViewNickName.setText(model.getNickName());
    }

    @Override
    public void refreshList() {
        getAccounts();
    }
}
