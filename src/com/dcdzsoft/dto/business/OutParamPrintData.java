package com.dcdzsoft.dto.business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRDataSource;

import com.dcdzsoft.print.ItemBean;

public class OutParamPrintData {
	public List<ItemBean> ItemCodeList = new ArrayList<ItemBean>(); //订单列表
	public Map<String,Object> parameters = new HashMap<String,Object>();
	public JRDataSource dataSource = null;
}
