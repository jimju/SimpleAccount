package com.jim.account.model.imp;

import com.jim.account.bean.AccountBean;
import com.jim.account.model.AccountXlsModel;
import com.jim.account.utils.PathUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class JxlAccoutXlsModel implements AccountXlsModel {

	private final String[] title = {"序号","项目","时间","金额","常规","备注"};
	private final static String TABLE_NAME = "表1";

	/**
	 * 写入xls文件
	 * @param tables
	 * @param file
     */
	@Override
	public void writeXls(List<AccountBean> tables, File file) {
		// TODO Auto-generated method stub
		try {
			WritableWorkbook workbook = Workbook.createWorkbook(file);
			WritableSheet sheet = workbook.createSheet(TABLE_NAME, 0);
			WritableFont wfont = new WritableFont(WritableFont.createFont("微软雅黑"),16);
			WritableCellFormat font = new WritableCellFormat(wfont);
			WritableFont wfont1 = new WritableFont(WritableFont.createFont("宋体"),14);
			WritableCellFormat font1 = new WritableCellFormat(wfont1);
			for (int i = 0; i < title.length; i++) {
				sheet.addCell(new Label(i,0,title[i],font));
			}
			for (int i = 0; i < tables.size() ; i++) {
				AccountBean bean = tables.get(i);
				/*for (int j = 0; j < cells.length; j++) {
					sheet.addCell(new Label(j,i+1,cells[j],font1));
				}*/
				sheet.addCell(new Label(0,i+1,String.valueOf(i+1),font1));
				sheet.addCell(new Label(1,i+1,bean.getProject(),font1));
				sheet.addCell(new Label(2,i+1,bean.getTime(),font1));
				sheet.addCell(new Label(3,i+1,String.valueOf(bean.getPay()),font1));
				sheet.addCell(new Label(4,i+1,bean.getNormal(),font1));
				sheet.addCell(new Label(5,i+1,bean.getRemark(),font1));
			}
			workbook.write();
			workbook.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//读取xls文件
	@Override
	public List<AccountBean> readXls(File file) {
		// TODO Auto-generated method stub

		try {
			Workbook workbook = Workbook.getWorkbook(file);
			Sheet sheet = workbook.getSheet(0);
			List<AccountBean> list = new ArrayList<>();
			for (int i = 1; i < sheet.getRows(); i++) {
				AccountBean bean = new AccountBean();
				bean.setProject(sheet.getColumn(1)[i].getContents());
				bean.setTime(sheet.getColumn(2)[i].getContents());
				bean.setPay(sheet.getColumn(3)[i].getContents());
				bean.setNormal(sheet.getColumn(4)[i].getContents());
				bean.setRemark(sheet.getColumn(5)[i].getContents());
				list.add(bean);
			}
			return list;
		} catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public File[] getXlsFiles() {
		if (PathUtils.getXlsSDPath() == null){
			return null;
		}
		File file = new File( PathUtils.getXlsSDPath());
		File[] files = file.listFiles();
		return files;
	}


}
