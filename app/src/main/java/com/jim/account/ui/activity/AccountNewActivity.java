package com.jim.account.ui.activity;

import android.text.TextUtils;
import android.widget.CheckBox;
import android.widget.Toast;

import com.jim.account.R;
import com.jim.account.bean.AccountBean;
import com.jim.account.db.AccountDbHelper;
import com.jim.account.model.AccountModel;
import com.jim.account.model.imp.AccountModelImp;
import com.jim.account.utils.DateUtils;

/**
 * Created by zhuzhu on 2016/12/26.
 */

public class AccountNewActivity extends AccountActivity {

    @Override
    protected void initViews() {
        super.initViews();
        //设置日期
        String date = getIntent().getStringExtra(AccountDbHelper.AccountColum.TIME);
        mTextViewTime.setText(TextUtils.isEmpty(date)?String.format("%d-%d-%d", DateUtils.getYear(), DateUtils.getMonth(), DateUtils.getDay()):date);
    }

    /**
     * 提交账单
     *
     * @param result
     */
    @Override
    public void onConfirm(double result) {
        if (result < 0.01) {
            Toast.makeText(AccountNewActivity.this, R.string.text_toast_nopay, Toast.LENGTH_LONG).show();
            return;
        }
        try {
            CheckBox checkBox = (CheckBox) findViewById(R.id.checkbox_normal_activity_account);
            AccountBean bean = new AccountBean();
            bean.setProject(mTextViewType.getText().toString());
            bean.setImageId(mCurrentImageId);
            bean.setTime(mTextViewTime.getText().toString());
            bean.setPay(result);
            String remark = mTextViewRemark.getText().toString().trim();
            bean.setRemark(remark.equals("备注") ? "" : remark);
            bean.setNormal(checkBox.isChecked() ? "Y" : "N");
            AccountModel model = new AccountModelImp(AccountNewActivity.this);
            model.insertAllAccounts(bean);
            setResult(RESULT_OK);
        } catch (Exception e) {
            Toast.makeText(AccountNewActivity.this, R.string.text_account_fail, Toast.LENGTH_LONG).show();
        }
        finish();
    }

}
