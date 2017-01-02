package com.jim.account.model;

import com.jim.account.bean.AccountBean;

import java.io.File;
import java.util.List;

/**
 * Created by jimju on 2016/12/27.
 */

public interface AccountXlsModel {
    void writeXls(List<AccountBean> tables, File file);

    List<AccountBean> readXls(File file);

    File[] getXlsFiles();
}
