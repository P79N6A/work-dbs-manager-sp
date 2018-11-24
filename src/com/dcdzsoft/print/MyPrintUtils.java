package com.dcdzsoft.print;

import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import com.dcdzsoft.EduException;

public class MyPrintUtils extends com.dcdzsoft.print.PrintUtils {
	public static byte[] printToPdf(Map<String, Object> parameters,
			String reportFilePath) throws EduException {
		byte[] bytes = new byte[0];
		try {
			/*
			 * File file = new File(reportFilePath); if(!file.exists()){
			 * System.out.println("reportFilePath="+reportFilePath); }
			 */
			JRDataSource dataSource = new JREmptyDataSource();
			bytes = JasperRunManager.runReportToPdf(reportFilePath, parameters,
					dataSource);
		} catch (JRException e) {
			e.printStackTrace();
			throw new EduException(e.getMessage());
		}
		return bytes;
	}

	public static byte[] printToPdf(
			com.dcdzsoft.dto.business.OutParamPrintData myDataSource,
			String reportFilePath) throws EduException {
		Map<String, Object> parameters = null;
		JRDataSource dataSource = null;

		if (myDataSource != null) {
			if (myDataSource.ItemCodeList != null) {
				dataSource = new JRBeanCollectionDataSource(
						myDataSource.ItemCodeList);
			} else {
				dataSource = new JREmptyDataSource();
			}
			if (myDataSource.parameters != null) {
				parameters = myDataSource.parameters;
			} else {
				parameters = new HashMap();
			}
		} else {
			parameters = new HashMap();
			dataSource = new JREmptyDataSource();

		}
		byte[] bytes = new byte[0];
		try {
			bytes = JasperRunManager.runReportToPdf(reportFilePath, parameters,
					dataSource);
		} catch (JRException e) {
			e.printStackTrace();
			throw new EduException(e.getMessage());
		}
		return bytes;
	}
}
