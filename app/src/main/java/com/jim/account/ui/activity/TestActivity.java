package com.jim.account.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jim.account.R;
import com.jim.account.bean.AccountBean;
import com.jim.account.model.AccountXlsModel;
import com.jim.account.model.imp.JxlAccoutXlsModel;
import com.jim.account.utils.PathUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TestActivity extends AppCompatActivity {
    Button mInsert, mQuery;
    TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        initViews();
    }

    private void initViews() {
        final File file = new File(PathUtils.getXlsSDPath(), "test.xls");
        final AccountXlsModel model = new JxlAccoutXlsModel();
        mInsert = (Button) findViewById(R.id.button_insert_test);
        mQuery = (Button) findViewById(R.id.button_query_test);
        mTextView = (TextView) findViewById(R.id.textview_test);
        mQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<AccountBean> list = model.readXls(file);
                if (list != null)
                    mTextView.setText("size : " + list.size() + "\ncontext: " + list.toString());
            }
        });

        mInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AccountBean bean = new AccountBean(1, "测试", "2016-12-12", 12.0, "Y", "测试", R.mipmap.type_big_00);
                List<AccountBean> list = new ArrayList<AccountBean>();
                list.add(bean);
                model.writeXls(list, file);
                Toast.makeText(TestActivity.this, "插入1条数据", Toast.LENGTH_LONG).show();
            }
        });
        findViewById(R.id.button_other_test3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (File f:model.getXlsFiles()){
                    mTextView.append("\n" + f.getName());
                }
            }
        });
    }
}
