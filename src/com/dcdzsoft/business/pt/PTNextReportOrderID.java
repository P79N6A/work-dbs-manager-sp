package com.dcdzsoft.business.pt;

import javax.sql.RowSet;

import oracle.net.aso.i;

import com.dcdzsoft.EduException;
import com.dcdzsoft.sda.db.*;
import com.dcdzsoft.util.*;
import com.dcdzsoft.dto.function.*;
import com.dcdzsoft.dto.business.*;
import com.dcdzsoft.dao.*;
import com.dcdzsoft.dao.common.*;
import com.dcdzsoft.constant.*;
import com.dcdzsoft.business.ActionBean;

/**
 * <p>Title: 自提柜后台运营系统</p>
 * <p>Description: 获取下一报告单号 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class PTNextReportOrderID extends ActionBean
{

    public String doBusiness(InParamPTNextReportOrderID in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        OutParamPTNextReportOrderID out = new OutParamPTNextReportOrderID();

		//1.	验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(in.OperID)
			||StringUtils.isEmpty(in.ReportType))
			throw new EduException(ErrorCode.ERR_PARMERR);
		
		//2.	调用CommonDAO.isOnline(管理员编号)判断管理员是否在线。
		OPOperOnline operOnline = commonDAO.isOnline(in.OperID);

		//3.	调用UtilDAO.getCurrentDateTime()获得系统日期时间。
		java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();

		if(StringUtils.isEmpty(in.ZoneID)){
			in.ZoneID = operOnline.ZoneID;
		}
		String orderIDType = "";
		String from = "";
		String to = "";
		switch(in.ReportType){
		case SysDict.PRINT_REPORT_TYPE_2:
			orderIDType = SysDict.ORDER_ID_TYPE_DROP;
			//
			from = in.ZoneID;
			to   = in.TerminalNo;
			
			break;
		case SysDict.PRINT_REPORT_TYPE_3:
			orderIDType = SysDict.ORDER_ID_TYPE_RETURN;
			from = in.TerminalNo;
			to   = in.ZoneID;
			break;
		case SysDict.PRINT_REPORT_TYPE_4:
			orderIDType = SysDict.ORDER_ID_TYPE_TRANSFER;
			from = in.ZoneID;
			to   = in.PPCID;
			break;
		case SysDict.PRINT_REPORT_TYPE_6:
			orderIDType = SysDict.ORDER_ID_TYPE_RECEIVED;
			from = in.PPCID;
			to   = in.ZoneID;
			break;
		case SysDict.PRINT_REPORT_TYPE_7:
			orderIDType = SysDict.ORDER_ID_TYPE_REDISTRIBUTE;
			from = in.ZoneID;
			to   = in.ZoneID;
			break;
		default:
			orderIDType = "";
		}
		//
		String reportOrderID = commonDAO.getNextOrderID(orderIDType, from, to, false);

        return reportOrderID;
    }
    
}
