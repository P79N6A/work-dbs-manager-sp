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
 * <p>Description: 打印确认包裹丢失报告 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class PTPrintConfirmLost extends ActionBean
{

    public OutParamPrintData doBusiness(InParamPTPrintConfirmLost in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        OutParamPrintData out = new OutParamPrintData();

		//1.验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(in.OperID)
			||StringUtils.isEmpty(in.ReportID))
			throw new EduException(ErrorCode.ERR_PARMERR);

		//2.调用CommonDAO.isOnline(管理员编号)判断管理员是否在线。
		OPOperOnline operOnline = commonDAO.isOnline(in.OperID);


		//3.调用UtilDAO.getCurrentDateTime()获得系统日期时间。
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
		//TBBoxTypeDAO boxTypeDAO = daoFactory.getTBBoxTypeDAO();
    	PMPostmanDAO postmanDAO = daoFactory.getPMPostmanDAO();
		PTDeliverHistoryDAO historyPackDAO = daoFactory.getPTDeliverHistoryDAO();
    	JDBCFieldArray whereCols0 = new JDBCFieldArray();
    	whereCols0.add("LastModifyTime", ">=", DateUtils.addTimeStamp(sysDateTime, -30));//打印30天以内的订单
    	whereCols0.add("ItemStatus", SysDict.ITEM_STATUS_DROP_LOSE);
    	whereCols0.add("ReportOrderID", in.ReportID);
        
        RowSet rset = historyPackDAO.select(whereCols0);
        while (RowSetUtils.rowsetNext(rset)) {
        	String itemcode = RowSetUtils.getStringValue(rset, "PackageID");
        	java.sql.Timestamp createtime = RowSetUtils.getTimestampValue(rset, "CreateTime");
        	String ReturnAgentID = RowSetUtils.getStringValue(rset, "ReturnAgentID");
        	int PrintedFlag = RowSetUtils.getIntValue(rset, "PrintedFlag");
        	String CustomerMobile = RowSetUtils.getStringValue(rset, "CustomerMobile");
    		//MissedBy
	        PMPostman postman = new PMPostman();
	        postman.PostmanID = ReturnAgentID;
	        try{
	        	postman = postmanDAO.find(postman);
	        }catch(EduException e){
	        	postman.PostmanName = "";
	        }
	        
           	itemTotal++;
        	//
        	ItemBean itemBean = new ItemBean();
        	itemBean.setId(itemTotal);
        	itemBean.setItemCode(itemcode);
        	//itemBean.setBoxSize(boxType.BoxTypeName);
        	//itemBean.setStaff(postman.PostmanName);
        	//itemBean.setRemark(historyPack.CustomerMobile);
        	
        	out.ItemCodeList.add(itemBean);
        	
        	//更新打印开关
        	/*JDBCFieldArray setCols = new JDBCFieldArray();
	        JDBCFieldArray whereCols = new JDBCFieldArray();
        	if((PrintedFlag&SysDict.MANDATORY_REPORT3_CODE)!=SysDict.MANDATORY_REPORT3_CODE){
        		//修改打印状态
        		PrintedFlag = (PrintedFlag|SysDict.MANDATORY_REPORT3_CODE);
	        	setCols.add("PrintedFlag", PrintedFlag);
	        	setCols.add("LastModifyTime", sysDateTime);
	        	
	        	whereCols.add("PackageID", itemcode);
		        whereCols.add("CreateTime", createtime);
		        
		        historyPackDAO.update(setCols, whereCols);
        	}*/
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
		from = "";
		
		//TO:
		to = "";//TO: AZC
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
