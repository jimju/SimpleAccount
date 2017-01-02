package com.jim.account.model;

import com.jim.account.bean.AccountBean;

import java.util.List;


/**
 * Created by Administrator on 2016/12/19.
 */
public interface AccountModel {
    /**
     * 查询所有的账单记录
     * @return
     */
    List<AccountBean> queryAllAccounts();
    /**
     * 查询所有的账单记录根据项目分组
     * @return
     */
    List<AccountBean> queryAllGroupByProject();

    /**
     * 根据类型分组
     * @return
     */
    List<AccountBean> queryAllGroupByNormal();

    /**
     * 根据Id查询
     * @param id
     * @return
     */
    AccountBean queryAccountById(int id);

    /**
     * 根据具体时间查询
     * @param date
     * @return
     */
    List<AccountBean> queryAccountsByDate(String date);

    /**
     * 根据月份查询
     * @param date
     * @return
     */
    List<AccountBean> queryAccountsByMonth(String date);

    /**
     * 根据年份查询
     * @param date
     * @return
     */
    List<AccountBean> queryAccountsByYear(String date);

    /**
     * 根据项目查询
     * @param project
     * @return
     */
    List<AccountBean> queryAccountsByProject(String project);

    /**
     * 根据类型查询
     * @param normal
     * @return
     */
    List<AccountBean> queryAccountsByNormal(String normal);

    /**
     * 根据时间区域查询
     * @param startDate
     * @param endDate
     * @return
     */
    List<AccountBean> queryAccountsBetweenDate(String startDate,String endDate);

    /**
     * 根据时间获取总花费
     * @param date
     * @return
     */
    double getCountByDate(String date);

    /**
     * 根据年份获取总花费
     * @param month
     * @return
     */
    double getCountByMonth(String month);

    /**
     * 根据年份获取总花费
     * @param year
     * @return
     */
    double getCountByYear(String year);

    /**
     * 根据项目获取总花费
     * @param project
     * @return
     */
    double getCountByProject(String project);

    /**
     * 根据类型获取总花费
     * @param normal
     * @return
     */
    double getCountByNormal(String normal);

    /**
     * 根据时间区域获取总花费
     * @param start
     * @param end
     * @return
     */
    double getCountBetWeenDay(String start,String end);

    /**
     * 插入一条账单
     * @param bean
     */
    long insertAllAccounts(AccountBean bean);

    /**
     * 修改一条账单
     * @param bean
     */
    int updateAccount(AccountBean bean);

    /**
     * 删除一条账单
     * @param id
     */
    int deleteAccount(int id);

}
