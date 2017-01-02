package com.jim.account.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.jim.account.R;
import com.jim.account.bean.AccountBean;
import com.jim.account.model.AccountModel;
import com.jim.account.model.AccountXlsModel;
import com.jim.account.model.imp.AccountModelImp;
import com.jim.account.model.imp.JxlAccoutXlsModel;

import java.io.File;
import java.util.List;

/**
 * Created by jimju on 2016/12/27.
 */

public class XlsUitls {

    public final static String XLS_NAME = "账单表格.xls";

    /**
     * 导出EXCEL表格
     *
     * @param context
     */
    public static void exportExcel(Context context) {
        AccountXlsModel model = new JxlAccoutXlsModel();
        AccountModel accountModel = new AccountModelImp(context);
        File file = new File(PathUtils.getXlsSDPath(), XLS_NAME);
        List<AccountBean> list = accountModel.queryAllAccounts();
        if (list.isEmpty()){
            Toast.makeText(context,R.string.text_not_data,Toast.LENGTH_LONG).show();
            return;
        }
        model.writeXls(list, file);
        if (model.getXlsFiles() != null && model.getXlsFiles().length > 0) {
            exportDialog(context);
        }
    }

    /**
     * 导出成功弹窗
     *
     * @param context
     * @return
     */
    public static AlertDialog exportDialog(final Context context) {
        AlertDialog dialog = new AlertDialog.Builder(context).setTitle(R.string.menu_excel_export).setMessage(R.string.text_export_success).setNegativeButton(R.string.text_cancel,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).setPositiveButton(R.string.text_share, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                shareExcel(context);
            }
        }).create();
        dialog.show();
        dialog.setCancelable(true);
        return dialog;
    }

    /**
     * 分享EXCEL文件
     *
     * @param context
     */
    private static void shareExcel(Context context) {
        Intent share = new Intent(Intent.ACTION_SEND);
        File file = new File(PathUtils.getXlsSDPath(), XLS_NAME);
        share.putExtra(Intent.EXTRA_STREAM,
                Uri.fromFile(file));
        share.setType("*/*");//此处可发送多种文件
        context.startActivity(Intent.createChooser(share, "Share"));
    }
}
