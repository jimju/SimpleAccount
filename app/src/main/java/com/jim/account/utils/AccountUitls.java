package com.jim.account.utils;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.jim.account.R;
import com.jim.account.ui.adapter.CalendarAdapter;
import com.jim.account.ui.widget.YearMonthMoveView;

import java.util.Calendar;


/**
 * Created by Administrator on 2016/12/19.
 */
public class AccountUitls {

    public static Dialog remarkDialog(Context context, final OnTextConfirmListener listener){
        final Dialog dialog = new Dialog(context, android.R.style.Theme_Translucent_NoTitleBar);
        dialog.setContentView(R.layout.account_remark_dialog);
        dialog.findViewById(R.id.layout_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.textview_dialog_comfirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editText = (EditText) dialog.findViewById(R.id.edittext_dialog_text);
                listener.onTextConfirm(editText.getText().toString().trim());
                dialog.dismiss();
            }
        });
        dialog.show();
        dialog.setCancelable(true);
        return dialog;
    }

    public interface OnTextConfirmListener{
        void onTextConfirm(String text);
    }

    /**
     * 长按编辑Dialog
     * @return
     */
    public static AlertDialog editDialog(Context context, DialogInterface.OnClickListener onClickListener){
        AlertDialog dialog = new AlertDialog.Builder(context).setTitle(R.string.text_edit).setItems(R.array.account_edits,onClickListener)
                .setNegativeButton(R.string.text_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).create();
        dialog.show();
        dialog.setCancelable(true);
        return dialog;
    }

    public static Dialog dateDialog(final Context context, final CalendarAdapter.DayClickListenner linstener){
        final Dialog dialog = new Dialog(context, android.R.style.Theme_Translucent_NoTitleBar);
        dialog.setContentView(R.layout.account_time_dialog);
        dialog.findViewById(R.id.layout_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
        dialog.setCancelable(true);
        YearMonthMoveView yearMonthView= (YearMonthMoveView) dialog.findViewById(R.id.yearmonthview_dialog_date);
        final GridView gridView = (GridView) dialog.findViewById(R.id.gridview_dialog_choice_date);
        yearMonthView.setDateChangedListener(new YearMonthMoveView.OnDateChangedListener() {
            @Override
            public void ondateChanged(int year, int month) {
                CalendarAdapter adapter = new CalendarAdapter(context,year,month);
                gridView.setAdapter(adapter);
                adapter.setDayClickListennerListener(linstener);
            }
        });
        Calendar calendar = Calendar.getInstance();
        yearMonthView.setDate(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH)+1);
        return dialog;
    }


    /**
     * ★★★★★gridview图标的动画效果★★★★★
     */
    public static void changeType(Context context, final ViewGroup rl, ImageView startView, final ImageView targetView) {
//      一、创造出执行动画的主题---imageview
        //代码new一个imageview，图片资源是上面的imageview的图片
        // (这个图片就是执行动画的图片，从开始位置出发，经过一个抛物线（贝塞尔曲线），移动到购物车里)
        final ImageView goods = new ImageView(context);
        final Drawable drawable = startView.getDrawable();
        goods.setImageDrawable(drawable);
        int dp10 = DpiUtils.dpToPx(10,context.getResources());
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(5*dp10, 5*dp10);
        rl.addView(goods, params);

//        二、计算动画开始/结束点的坐标的准备工作
        //得到父布局的起始点坐标（用于辅助计算动画开始/结束时的点的坐标）
        int[] parentLocation = new int[2];
        rl.getLocationInWindow(parentLocation);

        //得到商品图片的坐标（用于计算动画开始的坐标）
        int startLoc[] = new int[2];
        startView.getLocationInWindow(startLoc);

        //得到购物车图片的坐标(用于计算动画结束后的坐标)
        int endLoc[] = new int[2];
        targetView.getLocationInWindow(endLoc);

//        三、正式开始计算动画开始/结束的坐标
        //开始掉落的商品的起始点：商品起始点-父布局起始点+该商品图片的一半
        float startX = startLoc[0] - parentLocation[0] + startView.getWidth() / 2;
        float startY = startLoc[1] - parentLocation[1] + startView.getHeight() / 2;

        //商品掉落后的终点坐标：购物车起始点-父布局起始点+购物车图片的1/5
        float toX = endLoc[0] - parentLocation[0] + targetView.getWidth() / 5;
        float toY = endLoc[1] - parentLocation[1];

//        四、计算中间动画的插值坐标（贝塞尔曲线）（其实就是用贝塞尔曲线来完成起终点的过程）
        //开始绘制贝塞尔曲线
        Path path = new Path();
        //移动到起始点（贝塞尔曲线的起点）
        path.moveTo(startX, startY);
        //使用二次萨贝尔曲线：注意第一个起始坐标越大，贝塞尔曲线的横向距离就会越大，一般按照下面的式子取即可
        path.quadTo((startX + toX) / 2, startY, toX, toY);
        //mPathMeasure用来计算贝塞尔曲线的曲线长度和贝塞尔曲线中间插值的坐标，
        // 如果是true，path会形成一个闭环
        final PathMeasure mPathMeasure = new PathMeasure(path, false);
        final float[] mCurrentPosition = new float[2];
        //★★★属性动画实现（从0到贝塞尔曲线的长度之间进行插值计算，获取中间过程的距离值）
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, mPathMeasure.getLength());
        valueAnimator.setDuration(1000);
        // 匀速线性插值器
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // 当插值计算进行时，获取中间的每个值，
                // 这里这个值是中间过程中的曲线长度（下面根据这个值来得出中间点的坐标值）
                float value = (Float) animation.getAnimatedValue();
                // ★★★★★获取当前点坐标封装到mCurrentPosition
                // boolean getPosTan(float distance, float[] pos, float[] tan) ：
                // 传入一个距离distance(0<=distance<=getLength())，然后会计算当前距
                // 离的坐标点和切线，pos会自动填充上坐标，这个方法很重要。
                mPathMeasure.getPosTan(value, mCurrentPosition, null);//mCurrentPosition此时就是中间距离点的坐标值
                // 移动的商品图片（动画图片）的坐标设置为该中间点的坐标
                goods.setTranslationX(mCurrentPosition[0]);
                goods.setTranslationY(mCurrentPosition[1]);
//                Log.e("goodsgoods",mCurrentPosition[0] + "  Y: " + mCurrentPosition[1]);
            }
        });
//      五、 开始执行动画
        valueAnimator.start();

//      六、动画结束后的处理
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            //当动画结束后：
            @Override
            public void onAnimationEnd(Animator animation) {
                rl.removeView(goods);
                targetView.setImageDrawable(drawable);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }



}
