package com.jim.account.ui.activity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.jim.account.R;
import com.jim.account.model.InfoModel;
import com.jim.account.model.imp.PreferencesInfoModel;
import com.jim.account.ui.base.BaseBackActivity;
import com.jim.account.utils.NumberUtils;

public class SettingActivity extends BaseBackActivity implements View.OnClickListener {
    EditText mEditTextNickName,mEditTextBudget,mEditTextRemark;
    Button mButtonSave;
    InfoModel mModel;

    @Override
    protected void initViews() {
        super.initViews();
        mModel = new PreferencesInfoModel(this);
        mEditTextBudget = (EditText) findViewById(R.id.edittext_budget_activity_setting);
        mEditTextNickName = (EditText) findViewById(R.id.edittext_nickname_activity_setting);
        mEditTextRemark = (EditText) findViewById(R.id.edittext_remark_activity_setting);
        mButtonSave = (Button) findViewById(R.id.button_save_activity_setting);
        mButtonSave.setOnClickListener(this);
        mEditTextBudget.setText(String.valueOf(mModel.getBudget()));
        mEditTextNickName.setText(mModel.getNickName());
        mEditTextRemark.setText(mModel.getUserSign());
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_setting;
    }

    @Override
    public boolean hasCustomToolbar() {
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button_save_activity_setting:
                saveInfo();
                setResult(RESULT_OK);
                finish();
                break;
        }
    }

    private void saveInfo(){
        String budget = mEditTextBudget.getText().toString().trim();
        String remark = mEditTextRemark.getText().toString().trim();
        String nickname = mEditTextNickName.getText().toString().trim();
        double dBudget = NumberUtils.formatPoint(budget,2);
        mModel.setBudget(dBudget);
        mModel.setNickName(nickname);
        mModel.setUserSign(remark);
    }


}
