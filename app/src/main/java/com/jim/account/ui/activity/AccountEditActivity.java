package com.jim.account.ui.activity;

import android.widget.CheckBox;
import android.widget.Toast;

import com.jim.account.R;
import com.jim.account.bean.AccountBean;
import com.jim.account.db.AccountDbHelper;
import com.jim.account.model.AccountModel;
import com.jim.account.model.imp.AccountModelImp;

/**
 * Created by zhuzhu on 2016/12/26.
 */

public class AccountEditActivity extends AccountActivity {
    private AccountBean mData;
    private AccountModel mModel;
    @Override
    protected void initDatas() {
        super.initDatas();
        mModel = new AccountModelImp(this);
        int id = getIntent().getIntExtra(AccountDbHelper.AccountColum.ID,-1);
        mData = mModel.queryAccountById(id);
    }

    @Override
    protected void initViews() {
        super.initViews();
        //设置日期
        mTextViewTime.setText(mData.getTime());
        mTextViewCalculate2.setText(String.valueOf(mData.getPay()));
        mTextViewRemark.setText(mData.getRemark());
        mTextViewTime.setText(mData.getTime());
        mImageViewIcon.setImageResource(mData.getImageId());
        mTextViewType.setText(mData.getProject());
    }

    /**
     * 提交账单
     *
     * @param result
     */
    @Override
    public void onConfirm(double result) {
        if (result < 0.01) {
            Toast.makeText(AccountEditActivity.this, R.string.text_toast_nopay,Toast.LENGTH_LONG).show();
            return;
        }
        try {
            CheckBox checkBox = (CheckBox) findViewById(R.id.checkbox_normal_activity_account);
            checkBox.setChecked(mData.getNormal().equals("Y"));
            AccountBean bean = new AccountBean();
            bean.setId(mData.getId());
            bean.setProject(mTextViewType.getText().toString());
            bean.setImageId(mCurrentImageId);
            bean.setTime(mTextViewTime.getText().toString());
            bean.setPay(result);
            String remark = mTextViewRemark.getText().toString().trim();
            bean.setRemark(remark.equals("备注") ? "" : remark);
            bean.setNormal(checkBox.isChecked() ? "Y" : "N");
            mModel.updateAccount(bean);
            setResult(RESULT_OK);
        } catch (Exception e) {
            Toast.makeText(AccountEditActivity.this, R.string.text_account_fail, Toast.LENGTH_LONG).show();
        }
        finish();
    }

}
