package com.jim.account.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2016/12/19.
 */
public class AccountDbHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "accountDb";
    private static final int VERSION = 1;
    public static final String ACCOUNT_TABLE_NAME = "account_table";
    private static final String CREATE_ACCOUNT_SQL = "create table if not exists account_table("
            + "id integer primary key,"
            + "project varchar,"
            + "time date,"
            + "pay double,"
            + "normal varchar,"
            + "imageid integer,"
            + "remark varchar)";

    public AccountDbHelper(Context context) {
        super(context, DB_NAME, null, VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_ACCOUNT_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public static class AccountColum  {
        public static final String PROJECT = "project";
        public static final String TIME = "time";
        public static final String PAY = "pay";
        public static final String NORMAL = "normal";
        public static final String REMARK = "remark";
        public static final String IMAGE_ID = "imageid";
        public static final String ID = "id";

        public static final String[] COLUMS = {ID,PROJECT,TIME,PAY,NORMAL,IMAGE_ID,REMARK};

    }
}
