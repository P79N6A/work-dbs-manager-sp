package com.dcdzsoft.business.pt;

import java.util.Iterator;

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
 * <p>Description: R6报告打印（确认接收投递包裹） </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class PTPrintConfirmation extends ActionBean
{

    public OutParamPrintData doBusiness(InParamPTPrintConfirmation in) throws EduException
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
		String ppcid = "";
			
		TBBoxTypeDAO boxTypeDAO = daoFactory.getTBBoxTypeDAO();
		PTReadyPackageDAO readyPackDAO = daoFactory.getPTReadyPackageDAO();
    	JDBCFieldArray whereCols0 = new JDBCFieldArray();
    	//whereCols0.add("LastModifyTime", ">=", DateUtils.addTimeStamp(sysDateTime, -30));//打印30天以内的订单
    	whereCols0.add("ItemStatus", SysDict.ITEM_STATUS_DROP_RECEIVED);
    	whereCols0.add("ReportOrderID", in.ReportID);
        
    	String zoneIDDes = "";
        RowSet rset = readyPackDAO.select(whereCols0);
        while (RowSetUtils.rowsetNext(rset)) {
        	zoneIDDes = RowSetUtils.getStringValue(rset, "ZoneIDDes"); 
        	String itemcode = RowSetUtils.getStringValue(rset, "PackageID");
        	java.sql.Timestamp createtime = RowSetUtils.getTimestampValue(rset, "CreateTime");
        	String boxtype = RowSetUtils.getStringValue(rset, "BoxType");
        	int PrintedFlag = RowSetUtils.getIntValue(rset, "PrintedFlag");
        	if(StringUtils.isEmpty(ppcid)){
        		ppcid = RowSetUtils.getStringValue(rset, "PPCID");
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
        	if((PrintedFlag&SysDict.MANDATORY_REPORT6_CODE)!=SysDict.MANDATORY_REPORT6_CODE){
        		//修改打印状态
        		PrintedFlag = (PrintedFlag|SysDict.MANDATORY_REPORT6_CODE);
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
		
        IMPostalProcessingCenterDAO ppcDAO = daoFactory.getIMPostalProcessingCenterDAO();
	    IMPostalProcessingCenter ppc = new IMPostalProcessingCenter();
	    ppc.PPCID = ppcid;
	    ppc = ppcDAO.find(ppc);
	        
        IMZoneDAO zoneDAO = daoFactory.getIMZoneDAO();
        IMZone zone = new IMZone();
        zone.ZoneID  =  operOnline.ZoneID;
        zone = zoneDAO.find(zone);
        
    	IMZone zone1 = new IMZone(); //从PTreadyPackage中查出的转运包裹的目的地AZC编号
        if(StringUtils.isNotEmpty(zoneIDDes)){
        	zone1.ZoneID = zoneIDDes;
        	zone1 = zoneDAO.find(zone1);
        }
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
		if(StringUtils.isEmpty(zoneIDDes)){
			from = ppc.PPCID+"\n"+ppc.PPCName;
		}else{
			from = zone1.ZoneID+"\n"+zone1.ZoneName;
		}
		//TO:
		to = zone.ZoneID+"\n"+zone.ZoneName;
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
