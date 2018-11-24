package com.dcdzsoft.business.pt;

import javax.sql.RowSet;

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
 * <p>Description: 修改订单超时时间 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class PTModExpiredTime extends ActionBean
{

    public int doBusiness(InParamPTModExpiredTime in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        int result = 0;

		//1.	验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(in.PackageID))
			throw new EduException(ErrorCode.ERR_PARMERR);
		
		//2.	调用CommonDAO.isOnline(操作员编号)判断操作员是否在线。
		OPOperOnline operOnline = commonDAO.isOnline(in.OperID);

        //4.	调用UtilDAO.getSysDateTime()获得系统日期时间。
        java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();
        java.sql.Date sysDate = DateUtils.toSQLDate(sysDateTime);
        
        //5.	解析订单号。
      	String[][] itemsCodeAndTime = commonDAO.decodeBatchPackageID(in.PackageID);
      	
      	if(in.ExpiredTime == null){
        	in.ExpiredTime = sysDate;
		}
      	if(in.ReminderTime == null){
      		in.ReminderTime = DateUtils.addDate(sysDate, 1);//DateUtils.addTimeStamp(sysDateTime, 1)
      	}
        if("1".equals(in.ModType)){//仅修改催领时间
        	result = commonDAO.modReminderTime(operOnline.OperID, itemsCodeAndTime, in.TerminalNo, in.ReminderTime);
        }else{
        	if("2".equals(in.ModType)){
        		result = commonDAO.modReminderTime(operOnline.OperID, itemsCodeAndTime, in.TerminalNo, in.ReminderTime);
        	}
        	result = commonDAO.modExpireTime(operOnline.OperID, itemsCodeAndTime, in.TerminalNo, new java.sql.Timestamp(in.ExpiredTime.getTime()));//in.ExpiredTime
        }      
        
        ///推送到设备端   ?????zxy
        //com.dcdzsoft.businessproxy.PushBusinessProxy.doBusiness(in);

        // 调用CommonDAO.addOperatorLog(OperID，功能编号，系统日期时间，“”)
        OPOperatorLog log = new OPOperatorLog();
        log.OperID = in.OperID;
        log.FunctionID = in.getFunctionID();
        log.OccurTime = sysDateTime;
        log.StationAddr = operOnline.LoginIPAddr;
        log.Remark = "";

        commonDAO.addOperatorLog(log);
        
        return result;
    }
}
