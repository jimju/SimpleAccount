package com.jim.account.ui.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.jim.account.R;

import java.text.DecimalFormat;

/**
 * Created by zhuzhu on 2016/12/17.
 */

public class CalculateView extends FrameLayout implements View.OnClickListener {

    private TextView mText1, mText2, mSign, mConfirm;

    private final static int MAX_POINT = 2;
    private final static int MAX_NUMBER = 8;

    private double mNumber1, mNumber2, mResult;

    private OnInputListener onInputListener;
    private OnConfirmListener onConfirmListener;

    public CalculateView(Context context) {
        this(context, null);
    }

    public CalculateView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CalculateView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void setOnInputListener(OnInputListener onInputListener) {
        this.onInputListener = onInputListener;
    }

    public void setOnConfirmListener(OnConfirmListener onConfirmListener) {
        this.onConfirmListener = onConfirmListener;
    }

    private void init(Context context) {
        inflate(context, R.layout.view_calculate, this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        findViewById(R.id.num0).setOnClickListener(this);
        findViewById(R.id.num1).setOnClickListener(this);
        findViewById(R.id.num2).setOnClickListener(this);
        findViewById(R.id.num3).setOnClickListener(this);
        findViewById(R.id.num4).setOnClickListener(this);
        findViewById(R.id.num5).setOnClickListener(this);
        findViewById(R.id.num6).setOnClickListener(this);
        findViewById(R.id.num7).setOnClickListener(this);
        findViewById(R.id.num8).setOnClickListener(this);
        findViewById(R.id.num9).setOnClickListener(this);
        findViewById(R.id.back).setOnClickListener(this);
        findViewById(R.id.plus).setOnClickListener(this);
        findViewById(R.id.sub).setOnClickListener(this);
        findViewById(R.id.point).setOnClickListener(this);
        mConfirm = (TextView) findViewById(R.id.ok);
        mConfirm.setOnClickListener(this);
    }

    public void setTextView(TextView text1, TextView text2, TextView sign) {
        this.mText1 = text1;
        this.mText2 = text2;
        this.mSign = sign;

        mText1.setText("0");
        mText2.setText("0");
        mText1.setVisibility(INVISIBLE);
    }


    @Override
    public void onClick(View v) {

        if (mText1 == null || mText2 == null || mSign == null)
            return;
        String cacheText2 = mText2.getText().toString().trim();
        String cacheText1 = mText1.getText().toString().trim();
        switch (v.getId()) {
            case R.id.num1:
            case R.id.num2:
            case R.id.num3:
            case R.id.num4:
            case R.id.num5:
            case R.id.num6:
            case R.id.num7:
            case R.id.num8:
            case R.id.num9:
                //计算小数点位置,保留两位小数
//                Log.e("小数点位置",cacheText2.lastIndexOf(".") + "");
                if (cacheText2.contains(".") && cacheText2.length() - cacheText2.lastIndexOf(".") > 2) {
                    return;
                }
                //限制长度
                if (cacheText2.length() > MAX_NUMBER)
                    return;
                //判断缓存结果是否为0
                if (cacheText2.equals("0")) {
                    mText2.setText(((TextView) v).getText());
                } else {
                    mText2.append(((TextView) v).getText());
                }
                if (onInputListener != null) {
                    onInputListener.onNumberInput(Double.parseDouble(cacheText2));
                }
                break;
            case R.id.num0:
                if (cacheText2.equals("0")) {
//                    mText2.setText(((TextView)v).getText());
                } else {
                    mText2.append(((TextView) v).getText());
                }
                break;
            case R.id.sub:
                saveCacheResult();
                mText2.setText("0");
                mSign.setText("-");
                if (onInputListener != null) {
                    onInputListener.onSubInput();
                }
                break;
            case R.id.plus:
                saveCacheResult();
                mText2.setText("0");
                mSign.setText("+");
                if (onInputListener != null) {
                    onInputListener.onPlusInput();
                }
                break;
            case R.id.back:
                //清除数据
                mNumber2 = mNumber1 = mResult = 0;
                mText1.setText("0");
                mText2.setText("0");
                mSign.setText("");
                if (onInputListener != null) {
                    onInputListener.onBackInput();
                }
                break;
            case R.id.point:
                if (!cacheText2.contains(".")) {
                    mText2.append(".");
                }
                if (onInputListener != null) {
                    onInputListener.onPointInput();
                }
                break;
            case R.id.ok:
                if (mConfirm.getText().equals(getContext().getResources().getString(R.string.text_confirm))) {
                    if (mResult == 0 )
                        mResult = Double.parseDouble(mText2.getText().toString().trim());
                    if (onConfirmListener != null){
                        onConfirmListener.onConfirm(mResult);
                    }
                    return;
                }
                //计算结果
                String sign = mSign.getText().toString().trim();
                mNumber1 = Double.parseDouble(mText1.getText().toString().trim());
                mNumber2 = Double.parseDouble(mText2.getText().toString().trim());
                if (TextUtils.isEmpty(sign)) {
                    mResult = Double.parseDouble(mText2.getText().toString());
                } else if (sign.equals("+")) {
                    mResult = mNumber1 + mNumber2;
                } else if (sign.equals("-")) {
                    mResult = mNumber1 - mNumber2;
                }
                mText2.setText(format2point(mResult));
                mText1.setText("0");
                if (onInputListener != null) {
                    onInputListener.onOkInput();
                }
                break;
        }
        if (mText1.getText().equals("0")) {
            mSign.setVisibility(View.INVISIBLE);
            mText1.setVisibility(View.INVISIBLE);
            mConfirm.setText(getContext().getResources().getString(R.string.text_confirm));
        } else {
            mSign.setVisibility(View.VISIBLE);
            mText1.setVisibility(View.VISIBLE);
            mConfirm.setText("=");
        }
    }

    public interface OnInputListener {
        void onNumberInput(double i);

        void onPlusInput();

        void onSubInput();

        void onPointInput();

        void onBackInput();

        void onOkInput();
    }

    public interface OnConfirmListener{
        void onConfirm(double result);
    }

    //保存临时结果
    private void saveCacheResult() {
        mNumber1 = Double.parseDouble(mText1.getText().toString().trim());
        if (Math.abs(mNumber1) < 0.000001) {
            mText1.setText(mText2.getText().toString());
        } else if (mSign.getText().equals("+")) {
            mNumber2 = Double.parseDouble(mText2.getText().toString().trim());
            mResult = mNumber1 + mNumber2;
            mText1.setText(format2point(mResult));
        } else if (mSign.getText().equals("-")) {
            mNumber2 = Double.parseDouble(mText2.getText().toString().trim());
            mResult = mNumber1 - mNumber2;
            mText1.setText(format2point(mResult));
        }
    }

    //限制小数点后两位
    private String format2point(double d) {
        DecimalFormat df = new DecimalFormat("#.00");
        return df.format(d);
    }
}
