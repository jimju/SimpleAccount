package com.jim.account.ui.activity;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by jimju on 2016/12/26.
 */

public abstract class AccountListActivity extends AppCompatActivity {
    public final static int ACCOUNT_CODE = 0x11;
    public abstract void refreshList();
}
