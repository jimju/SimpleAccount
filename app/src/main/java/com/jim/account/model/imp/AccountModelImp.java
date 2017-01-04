package com.jim.account.model.imp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.jim.account.bean.AccountBean;
import com.jim.account.db.AccountDbHelper;
import com.jim.account.db.AccountDbHelper.AccountColum;
import com.jim.account.model.AccountModel;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2016/12/20.
 */
public class AccountModelImp implements AccountModel {
    private Context mCxt;
    private SQLiteDatabase mDatabase;

    public AccountModelImp(Context context) {
        this.mCxt = context;
        AccountDbHelper helper = new AccountDbHelper(context);
        mDatabase = helper.getWritableDatabase();
    }

    @Override
    public List<AccountBean> queryAllAccounts() {
        Cursor cursor = mDatabase.query(AccountDbHelper.ACCOUNT_TABLE_NAME, AccountColum.COLUMS, null, null, null, null, null, null);
        List<AccountBean> list = cursor.getCount() >0?fillAccount(cursor):new ArrayList<AccountBean>();
        cursor.close();
        return list;
    }

    @Override
    public List<AccountBean> queryAllGroupByProject() {
        Cursor cursor = mDatabase.query(AccountDbHelper.ACCOUNT_TABLE_NAME, AccountColum.COLUMS, null, null, AccountColum.PROJECT, null, null, null);
        List<AccountBean> list = cursor.getCount() >0?fillAccount(cursor):new ArrayList<AccountBean>();
        cursor.close();
        return list;
    }

    @Override
    public List<AccountBean> queryAllGroupByNormal() {
        Cursor cursor = mDatabase.query(AccountDbHelper.ACCOUNT_TABLE_NAME, AccountColum.COLUMS, null, null, AccountColum.NORMAL, null, null, null);
        List<AccountBean> list = cursor.getCount() >0?fillAccount(cursor):new ArrayList<AccountBean>();
        cursor.close();
        return list;
    }

    @Override
    public AccountBean queryAccountById(int id) {
        Cursor cursor = mDatabase.query(AccountDbHelper.ACCOUNT_TABLE_NAME, AccountColum.COLUMS, AccountColum.ID + " = ?", new String[]{String.valueOf(id)}, null, null, null, null);
        List<AccountBean> list = cursor.getCount() >0?fillAccount(cursor):new ArrayList<AccountBean>();
        cursor.close();
        return list.size()>0?list.get(0):null;
    }

    @Override
    public List<AccountBean> queryAccountsByDate(String date) {
        Cursor cursor = mDatabase.query(AccountDbHelper.ACCOUNT_TABLE_NAME, AccountColum.COLUMS, AccountColum.TIME + " = ?", new String[]{date}, null, null, null, null);
        List<AccountBean> list = cursor.getCount() >0?fillAccount(cursor):new ArrayList<AccountBean>();
        cursor.close();
        return list;
    }

    @Override
    public List<AccountBean> queryAccountsByMonth(String date) {
        String where = AccountColum.TIME + " LIKE '"+date+"%'";
        Cursor cursor = mDatabase.query(AccountDbHelper.ACCOUNT_TABLE_NAME, AccountColum.COLUMS,where , null, null, null, null, null);
        List<AccountBean> list = cursor.getCount() >0?fillAccount(cursor):new ArrayList<AccountBean>();
        cursor.close();
        return list;
    }

    @Override
    public List<AccountBean> queryAccountsByYear(String date) {
        String where = AccountColum.TIME + " LIKE '"+date+"%'";
        Cursor cursor = mDatabase.query(AccountDbHelper.ACCOUNT_TABLE_NAME, AccountColum.COLUMS,where , null, null, null, null, null);
        List<AccountBean> list = cursor.getCount() >0?fillAccount(cursor):new ArrayList<AccountBean>();
        cursor.close();
        return list;
    }

    @Override
    public List<AccountBean> queryAccountsByProject(String project) {
        Cursor cursor = mDatabase.query(AccountDbHelper.ACCOUNT_TABLE_NAME, AccountColum.COLUMS, AccountColum.PROJECT + " = ?", new String[]{project}, null, null, null, null);
        List<AccountBean> list = cursor.getCount() >0?fillAccount(cursor):new ArrayList<AccountBean>();
        cursor.close();
        return list;
    }

    @Override
    public List<AccountBean> queryAccountsByNormal(String normal) {
        Cursor cursor = mDatabase.query(AccountDbHelper.ACCOUNT_TABLE_NAME, AccountColum.COLUMS, AccountColum.NORMAL + " = ?", new String[]{normal}, null, null, null, null);
        List<AccountBean> list = cursor.getCount() >0?fillAccount(cursor):new ArrayList<AccountBean>();
        cursor.close();
        return list;
    }

    @Override
    public List<AccountBean> queryAccountsBetweenDate(String startDate, String endDate) {
        Cursor cursor = mDatabase.query(AccountDbHelper.ACCOUNT_TABLE_NAME, AccountColum.COLUMS, AccountColum.TIME + " BETWEEN  ? AND ?", new String[]{startDate,endDate}, null, null, null, null);
        List<AccountBean> list = cursor.getCount() >0?fillAccount(cursor):new ArrayList<AccountBean>();
        cursor.close();
        return list;
    }

    @Override
    public double getCountByDate(String date) {
        List<AccountBean> list = queryAccountsByDate(date);
        return sumAccount(list);
    }

    @Override
    public double getCountByMonth(String month) {
        List<AccountBean> list = queryAccountsByMonth(month);
        return sumAccount(list);
    }

    @Override
    public double getCountByYear(String year) {
        List<AccountBean> list = queryAccountsByYear(year);
        return sumAccount(list);
    }

    @Override
    public double getCountByProject(String project) {
        List<AccountBean> list = queryAccountsByProject(project);
        return sumAccount(list);
    }

    @Override
    public double getCountByNormal(String normal) {
        List<AccountBean> list = queryAccountsByNormal(normal);
        return sumAccount(list);
    }

    @Override
    public double getCountBetWeenDay(String start, String end) {
        List<AccountBean> list = queryAccountsBetweenDate(start,end);
        return sumAccount(list);
    }

    //通过查询计算
    private double sumAccount(List<AccountBean> list ){
        double count  = 0;
        for(AccountBean b:list){
            count += b.getPay();
        }
        return count;
    }
/*  没有能够计算出价格
    @Override
    public double getCountByDate(String date) {
        double count = 0;
        String sql = String.format("SELECT SUM(pay) AS sum FROM account_table WHERE time = '%s' " ,date);
        Cursor cursor = mDatabase.rawQuery(sql,null);
        if (cursor != null) {
            cursor.moveToFirst();
            count = cursor.getColumnIndex("sum");
        }
        cursor.close();
        return count;
    }

    @Override
    public double getCountBetWeenDay(String start, String end) {
        double count = 0;
        String sql = String.format("SELECT time,SUM(pay) AS sum FROM account_table WHERE time BETWEEN  '%s' AND '%s' " ,start,end);
        Cursor cursor = mDatabase.rawQuery(sql,null);
        if (cursor != null) {
            cursor.moveToFirst();
            count = cursor.getColumnIndex("sum");
        }
        cursor.close();
        return count;
    }*/

    @Override
    public long insertAllAccounts(AccountBean bean) {
        ContentValues values = new ContentValues();
        values.put(AccountColum.PROJECT,bean.getProject());
        values.put(AccountColum.PAY,bean.getPay());
        values.put(AccountColum.TIME,bean.getTime());
        values.put(AccountColum.NORMAL,bean.getNormal());
        values.put(AccountColum.IMAGE_ID,bean.getImageId());
        values.put(AccountColum.REMARK,bean.getRemark());
        return mDatabase.insert(AccountDbHelper.ACCOUNT_TABLE_NAME,null,values);
    }

    @Override
    public int updateAccount(AccountBean bean) {
        ContentValues values = new ContentValues();
        values.put(AccountColum.ID,bean.getId());
        values.put(AccountColum.PROJECT,bean.getProject());
        values.put(AccountColum.PAY,bean.getPay());
        values.put(AccountColum.TIME,bean.getTime());
        values.put(AccountColum.NORMAL,bean.getNormal());
        values.put(AccountColum.IMAGE_ID,bean.getImageId());
        values.put(AccountColum.REMARK,bean.getRemark());
        return mDatabase.update(AccountDbHelper.ACCOUNT_TABLE_NAME,values,AccountColum.ID + " = ?",new String[]{String.valueOf(bean.getId())});
    }

    @Override
    public int deleteAccount(int id) {
        return mDatabase.delete(AccountDbHelper.ACCOUNT_TABLE_NAME,AccountColum.ID + " = ?",new String[]{String.valueOf(id)});
    }

    public void insertDemo(){
        ContentValues values = new ContentValues();
        values.put(AccountColum.PROJECT,"晚餐");
        values.put(AccountColum.PAY,"10.0");
        values.put(AccountColum.TIME,"100");
        values.put(AccountColum.NORMAL,"Y");
        values.put(AccountColum.IMAGE_ID,"987654");
        values.put(AccountColum.REMARK,"测试插入记录");
        mDatabase.insert(AccountDbHelper.ACCOUNT_TABLE_NAME,null,values);
    }

    private List<AccountBean> fillAccount(Cursor cursor) {
        List<AccountBean> list = new ArrayList<>();
        cursor.moveToFirst();
        do {
            AccountBean bean = new AccountBean();
            bean.setProject(getColumData(cursor, AccountColum.PROJECT));
            bean.setPay(cursor.getDouble(cursor.getColumnIndex(AccountColum.PAY)));
            bean.setTime(getColumData(cursor, AccountColum.TIME));
            bean.setNormal(getColumData(cursor, AccountColum.NORMAL));
            bean.setImageId(cursor.getInt(cursor.getColumnIndex( AccountColum.IMAGE_ID)));
            bean.setRemark(getColumData(cursor, AccountColum.REMARK));
            bean.setId(cursor.getInt(cursor.getColumnIndex(AccountColum.ID)));
            list.add(bean);
        } while (cursor.moveToNext());
        return list;
    }

    private String getColumData(Cursor cursor, String name) {
        return cursor.getString(cursor.getColumnIndex(name));
    }
}
