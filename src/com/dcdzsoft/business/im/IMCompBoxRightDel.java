package com.dcdzsoft.business.im;

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
 * <p>Description: 服务商箱体权限删除 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class IMCompBoxRightDel extends ActionBean
{

    public int doBusiness(InParamIMCompBoxRightDel in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        int result = 0;

		//1.	验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(in.OperID)
			||StringUtils.isEmpty(in.CompanyID)
			||StringUtils.isEmpty(in.BoxList))
			throw new EduException(ErrorCode.ERR_PARMERR);
		
		//2.	调用CommonDAO.isOnline(管理员编号)判断管理员是否在线。
		OPOperOnline operOnline = commonDAO.isOnline(in.OperID);


		//3.	调用UtilDAO.getCurrentDateTime()获得系统日期时间。
		java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();
		java.sql.Date sysDate = DateUtils.toSQLDate(sysDateTime);
		
		//检测CompanyID是否存在
		IMCompanyDAO companyDAO = daoFactory.getIMCompanyDAO();
		IMCompany company = new IMCompany();
		company.CompanyID = in.CompanyID;
		company = companyDAO.find(company);
		
		//4.	箱门列表格式：” TerminalNo,BoxNo~TerminalNo,BoxNo”
		String[] boxArray = in.BoxList.split("~");
		
		IMZoneLockerRightDAO zoneLockerRightDAO = daoFactory.getIMZoneLockerRightDAO();
		IMCompanyBoxRightDAO companyBoxRightDAO = daoFactory.getIMCompanyBoxRightDAO();
        //IMCompanyBoxRight companyBoxRight = new IMCompanyBoxRight();
        //companyBoxRight.CompanyID  = in.CompanyID;
        for(String box: boxArray){
        	String[] boxAndTerminal = box.split(",");
        	
        	JDBCFieldArray whereCols = new JDBCFieldArray();
        	whereCols.add("CompanyID", in.CompanyID);
        	whereCols.add("TerminalNo", boxAndTerminal[0]);
        	whereCols.add("BoxNo", boxAndTerminal[1]);
        	
            companyBoxRightDAO.delete(whereCols);
            
            JDBCFieldArray whereCols1 = new JDBCFieldArray();
        	whereCols1.add("CompanyID", in.CompanyID);
        	whereCols1.add("TerminalNo", boxAndTerminal[0]);
        	if(companyBoxRightDAO.isExist(whereCols1) <= 0){
        		//公司对该柜体的箱权限为空，则删除该公司所有AZC的对该柜体使用权限
        		JDBCFieldArray whereCols2 = new JDBCFieldArray();
        		whereCols2.add("TerminalNo", boxAndTerminal[0]);
        		whereCols2.addSQL(" AND ZoneID  LIKE '"+in.CompanyID+"%'");
        		zoneLockerRightDAO.delete(whereCols2);
            }
        	
        	
        	//companyBoxRight.TerminalNo = boxAndTerminal[0];
            //companyBoxRight.BoxNo      = boxAndTerminal[1];
        	//companyBoxRightDAO.delete(companyBoxRight);
        }
        
        
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
