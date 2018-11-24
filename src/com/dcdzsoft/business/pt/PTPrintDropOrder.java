package com.dcdzsoft.business.pt;


import javax.sql.RowSet;

import com.dcdzsoft.EduException;
import com.dcdzsoft.print.ItemBean;
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
 * <p>Description: R2报告打印（投递清单报告） </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class PTPrintDropOrder extends ActionBean
{

    public OutParamPrintData doBusiness(InParamPTPrintDropOrder in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        OutParamPrintData out = new OutParamPrintData();

		//1.	验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(in.OperID)
			||StringUtils.isEmpty(in.ReportID))
			throw new EduException(ErrorCode.ERR_PARMERR);

		//2.	调用CommonDAO.isOnline(管理员编号)判断管理员是否在线。
		OPOperOnline operOnline = commonDAO.isOnline(in.OperID);

		//3.	调用UtilDAO.getCurrentDateTime()获得系统日期时间。
		java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();
		java.sql.Date sysDate = DateUtils.toSQLDate(sysDateTime);
		
		//打印报表参数初始化
		int itemTotal = 0;
		String logoUri = "notfund.png";
		String documentID = in.ReportID;
		String azcinfo = "";
    	String createBy = "";
		String to = "";//
		String from = "";

		//获取报表数据
		String terminalNo = "";
		String postmanID = "";
		
		TBBoxTypeDAO boxTypeDAO = daoFactory.getTBBoxTypeDAO();
		PTReadyPackageDAO readyPackDAO = daoFactory.getPTReadyPackageDAO();
    	JDBCFieldArray whereCols0 = new JDBCFieldArray();
    	//whereCols0.add("LastModifyTime", ">=", DateUtils.addTimeStamp(sysDateTime, -30));//打印30天以内的订单
    	whereCols0.add("ItemStatus", SysDict.ITEM_STATUS_DROP_INTRANSIT_OUT);
    	whereCols0.add("ReportOrderID", in.ReportID);
        
        RowSet rset = readyPackDAO.select(whereCols0);
        while (RowSetUtils.rowsetNext(rset)) {
        	String itemcode = RowSetUtils.getStringValue(rset, "PackageID");
        	java.sql.Timestamp createtime = RowSetUtils.getTimestampValue(rset, "CreateTime");
        	String boxtype = RowSetUtils.getStringValue(rset, "BoxType");
        	int PrintedFlag = RowSetUtils.getIntValue(rset, "PrintedFlag");
        	if(StringUtils.isEmpty(terminalNo)){
        		terminalNo = RowSetUtils.getStringValue(rset, "TerminalNo");
        	}
        	if(StringUtils.isEmpty(postmanID)){
        		postmanID = RowSetUtils.getStringValue(rset, "DropAgentID");
        	}
        	
        	TBBoxType boxType = new TBBoxType();
        	boxType.BoxType = boxtype;
        	try{
        		boxType = boxTypeDAO.find(boxType);
        	}catch(EduException e){
        		boxType.BoxTypeName = "";
        	}
        	
        	itemTotal++;
        	
        	ItemBean itemBean = new ItemBean();
        	itemBean.setId(itemTotal);
        	itemBean.setBoxSize(boxType.BoxTypeName);
        	itemBean.setItemCode(itemcode);
        	
        	out.ItemCodeList.add(itemBean);
        	
        	//更新打印开关
        	JDBCFieldArray setCols = new JDBCFieldArray();
	        JDBCFieldArray whereCols = new JDBCFieldArray();
        	if((PrintedFlag&SysDict.MANDATORY_REPORT2_CODE)!=SysDict.MANDATORY_REPORT2_CODE){
        		//修改打印状态
        		PrintedFlag = (PrintedFlag|SysDict.MANDATORY_REPORT2_CODE);
	        	setCols.add("PrintedFlag", PrintedFlag);
	        	setCols.add("LastModifyTime", sysDateTime);
	        	
	        	whereCols.add("PackageID", itemcode);
		        whereCols.add("CreateTime", createtime);
		        
		        readyPackDAO.update(setCols, whereCols);
        	}
        	//#start  调用CommonDAO.addOperatorLog(OperID，功能编号，系统日期时间，“”)        	
			OPOperatorLog log = new OPOperatorLog();
			log.OperID = in.OperID;
			log.FunctionID = in.getFunctionID();
			log.OccurTime = sysDateTime;
			log.StationAddr = operOnline.LoginIPAddr;
			log.Remark = ""+documentID+","+itemcode;

			commonDAO.addOperatorLog(log);
			//#end
        }
        
        IMZoneDAO zoneDAO = daoFactory.getIMZoneDAO();
        IMZone zone = new IMZone();
        zone.ZoneID  =  operOnline.ZoneID;
        zone = zoneDAO.find(zone);
        
        //投递员信息
        PMPostmanDAO postmanDAO = daoFactory.getPMPostmanDAO();
        PMPostman postman = new PMPostman();
        postman.PostmanID = postmanID;
        postman = postmanDAO.find(postman);
        
        //柜体信息
        TBTerminalDAO terminalDAO = daoFactory.getTBTerminalDAO();
        TBTerminal terminal = new TBTerminal();
        terminal.TerminalNo  =  terminalNo;
        terminal = terminalDAO.find(terminal);
        
    	//#start 获取打印报表参数
    	//AZC.Code-AZC.Name
        azcinfo = zone.ZoneID+"-"+zone.ZoneName;
        
        //Company LOGO
        IMCompanyDAO companyDAO = daoFactory.getIMCompanyDAO();
        IMCompany company = new IMCompany();
        company.CompanyID = zone.CompanyID;
        company = companyDAO.find(company);
        if(StringUtils.isNotEmpty(company.LogoUrl)){
        	logoUri = commonDAO.getCompanyLogoLocalPath(company.LogoUrl);
        }

        //CreateBy
    	OPOperatorDAO operatorDAO = daoFactory.getOPOperatorDAO();
    	OPOperator opreator = new OPOperator();
		opreator.OperID = operOnline.OperID;
		opreator = operatorDAO.find(opreator);
		
		createBy = opreator.UserID+"-"+opreator.OperName;
		
		//From: 
		from = postman.PostmanID+"\n"+postman.PostmanName;
		
		//TO:
		to = terminal.TerminalNo+"\n"+terminal.TerminalName;//TO: locker
    	//#end
		
		//#start 返回报表数据
		out.parameters.put("AZCInfo", azcinfo);
        out.parameters.put("LogoUri", logoUri);
        out.parameters.put("DocumentID", documentID);
        out.parameters.put("From", from);
        out.parameters.put("To", to);
        out.parameters.put("Staff", createBy);
        out.parameters.put("ItemTotal", String.valueOf(itemTotal));
        //#end
        
        return out;
    }
}
