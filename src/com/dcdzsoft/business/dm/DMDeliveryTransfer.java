package com.dcdzsoft.business.dm;

import java.io.File;
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
import com.dcdzsoft.config.ApplicationConfig;
import com.dcdzsoft.constant.*;
import com.dcdzsoft.business.ActionBean;

/**
 * <p>Title: 自提柜后台运营系统</p>
 * <p>Description: 订单数据转运到PPC </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class DMDeliveryTransfer extends ActionBean
{

    public OutParamPrintData doBusiness(InParamDMDeliveryTransfer in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        OutParamPrintData out = new OutParamPrintData();

		//1.	验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(in.OperID)
			||StringUtils.isEmpty(in.PackageID)
			||StringUtils.isEmpty(in.PPCID)
			||StringUtils.isEmpty(in.ItemStatus))
			throw new EduException(ErrorCode.ERR_PARMERR);


		//2.	调用CommonDAO.isOnline(管理员编号)判断管理员是否在线。
		OPOperOnline operOnline = commonDAO.isOnline(in.OperID);


		//3.	调用UtilDAO.getCurrentDateTime()获得系统日期时间。
		java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();
		java.sql.Date sysDate = DateUtils.toSQLDate(sysDateTime);
		
		if(!SysDict.ITEM_STATUS_COLLECT_INWARD_RECEIVED.equals(in.ItemStatus)){//Pane6 寄件订单发送
			throw new EduException(ErrorCode.ERR_OPERAT_EXISTSFORBIDITEM);
		}
		
		//AZC
		IMZoneDAO zoneDAO = daoFactory.getIMZoneDAO();
		IMZone zone = new IMZone();
		zone.ZoneID = operOnline.ZoneID;
		zone = zoneDAO.find(zone);
		
		//PPC
		IMPostalProcessingCenterDAO ppcDAO = daoFactory.getIMPostalProcessingCenterDAO();
		IMPostalProcessingCenter  ppc = new IMPostalProcessingCenter();
		ppc.PPCID = in.PPCID;
		ppc  = ppcDAO.find(ppc);
		
		//4.	解析订单号。
        String[][] itemsCodeAndTime = commonDAO.decodeBatchPackageID(in.PackageID); 
        
		//#start 订单信息检查，筛选符合条件的订单 ;有效订单数为0，  关闭打印开关
        String nowStatus = SysDict.ITEM_STATUS_COLLECT_INWARD_RECEIVED;
        String nextStatus = SysDict.ITEM_STATUS_COLLECT_TRANSFER;
		DMHistoryItemDAO historyItemDAO = daoFactory.getDMHistoryItemDAO();
		java.util.List<DMHistoryItem> historyItemList = new java.util.LinkedList<DMHistoryItem>();
		for(String[] itemCodeAndTime:itemsCodeAndTime){
			String itemCode   = itemCodeAndTime[0];
			String createtime = itemCodeAndTime[1];
			if(StringUtils.isEmpty(itemCode)|| StringUtils.isEmpty(createtime)){
				continue;
			}
			DMHistoryItem historyItem = new DMHistoryItem();
			historyItem.PackageID = itemCode;
			historyItem.CreateTime = java.sql.Timestamp.valueOf(createtime);
        	try{
        		historyItem = historyItemDAO.find(historyItem);
			}catch(EduException e){
				continue;
			}
        	
			//状态检查 
			if(!in.ItemStatus.equals(historyItem.ItemStatus)){
				continue;
        	}
			
			historyItemList.add(historyItem);
			
			JDBCFieldArray setCols = new JDBCFieldArray();
	        JDBCFieldArray whereCols = new JDBCFieldArray();
	        
	        //#start更新订单状态
	        setCols.add("ItemStatus", nextStatus);
	        setCols.add("LastModifyTime", sysDateTime);
	        
	        whereCols.add("PackageID", historyItem.PackageID);
	        whereCols.add("CreateTime", historyItem.CreateTime);
	        whereCols.add("ItemStatus", nowStatus);
	        int count = historyItemDAO.update(setCols, whereCols);
	        if(count<=0){
	        	continue;
	        }
	        //#end
	        
	        //"Transfer("+zone.ZoneName+"=>"+ppc.PPCName+")"
            String[] itemDesc= {zone.ZoneName, ppc.PPCName};
            StringBuffer itemStatusDescription = new StringBuffer(Constant.getAction(Constant.ACTION_CODE_TRANSFER, itemDesc));
            
	        //#start 记录订单状态更新信息
    		DMItemLifeCycle itemLifeCycle = new DMItemLifeCycle();
            itemLifeCycle.PackageID  = historyItem.PackageID;
            itemLifeCycle.ItemStatus = nextStatus;
            itemLifeCycle.OperatorID    = operOnline.OperID;
            itemLifeCycle.OperatorType = ""+operOnline.OperType;
            itemLifeCycle.LastModifyTime = sysDateTime;
            itemLifeCycle.Remark = itemStatusDescription.toString();
            commonDAO.addItemLifeCycle(itemLifeCycle);
            //#end 
	        
	        //#start  调用CommonDAO.addOperatorLog(OperID，功能编号，系统日期时间，“”)
	        itemStatusDescription.append(",LastStatus="+historyItem.ItemStatus);
			OPOperatorLog log = new OPOperatorLog();
			log.OperID = in.OperID;
			log.FunctionID = in.getFunctionID();
			log.OccurTime = sysDateTime;
			log.StationAddr = operOnline.LoginIPAddr;
			log.Remark = "itemCode="+historyItem.PackageID+","+itemStatusDescription.toString();

			commonDAO.addOperatorLog(log);
			//#end
    	}
    	
    	//#end 
		/**
		 * 打印报表处理
		 */
    	//打印报表参数初始化
		int itemTotal = 0;
		String logoUri = "notfund.png";
		String documentID = "";
		String azcinfo = "";
    	String createBy = "";
		String to = "";//
		String from = "";
		
		//检查是否需要打印：
		boolean isPrint = true;/*commonDAO.checkPrintFlag(itemsCodeAndTime, nowStatus, 
            		zone.MandatoryFlag, SysDict.MANDATORY_REPORT4_CODE,ErrorCode.ERR_MANDATORY_REPORT4_PRINT);*/
		
    	if(historyItemList.size()<=0){
    		isPrint = false;
    	}
		
        //获取报表数据
        if(isPrint){
        	//DocumentID
        	documentID = commonDAO.getNextOrderID(SysDict.ORDER_ID_TYPE_TRANSFER, operOnline.ZoneID, ppc.PPCID);
            //commonDAO.updateZoneCounter(operOnline.ZoneID, SysDict.COUNTER_TYPE_AZC_TRANSFER);
        	
        	//#start  获取打印报表数据列表
            TBBoxTypeDAO boxTypeDAO = daoFactory.getTBBoxTypeDAO();
        	//PMPostmanDAO postmanDAO = daoFactory.getPMPostmanDAO();
            Iterator<DMHistoryItem> historyItemIre = historyItemList.iterator();
	    	 while(historyItemIre.hasNext()){
	    		 DMHistoryItem historyItem = historyItemIre.next();
        		
        		//BoxSize
             	TBBoxType boxType = new TBBoxType();
            	boxType.BoxType = historyItem.ParcelSize;
            	try{
            		boxType = boxTypeDAO.find(boxType);
            	}catch(EduException e){
            		boxType.BoxTypeName = "";
            	}
            	
    	        //更新订单打印报表流水号
            	JDBCFieldArray whereCols = new JDBCFieldArray();
            	JDBCFieldArray setCols = new JDBCFieldArray();
            	if((historyItem.PrintedFlag&SysDict.MANDATORY_REPORT4_CODE)!=SysDict.MANDATORY_REPORT4_CODE){
	        		//修改打印状态
	        		int PrintedFlag = (historyItem.PrintedFlag|SysDict.MANDATORY_REPORT4_CODE);
		        	setCols.add("PrintedFlag", PrintedFlag);
	        	}
            	setCols.add("ReportOrderID", documentID);
		        setCols.add("LastModifyTime", sysDateTime);
		        
		        whereCols.add("PackageID", historyItem.PackageID);
		        whereCols.add("CreateTime", historyItem.CreateTime);
		        
		        historyItemDAO.update(setCols, whereCols);
            	
		        historyItem.ReportOrderID = documentID;
		        
    	        //#start 订单数据加入发送队列进行发送
                String transferType = "";
    	        if(historyItem.ItemStatus.equals(SysDict.ITEM_STATUS_COLLECT_INWARD_RECEIVED)){
    	        	transferType = SysDict.TRANSFER_TYPE_SEND_MAIL;
    	        }/*else if (historyItem.ItemStatus.equals(SysDict.ITEM_STATUS_COLLECT_RETURNED)){
    	        	transferType = SysDict.TRANSFER_TYPE_MAIL_RETURNED;
    	        }*/
    	        
    	        commonDAO.addItemStatusUpdate(in.PPCID, nextStatus, historyItem, transferType);
    	        //#end 
    	        
            	itemTotal++;
            	//
            	ItemBean itemBean = new ItemBean();
            	itemBean.setId(itemTotal);
            	itemBean.setBoxSize(boxType.BoxTypeName);
            	itemBean.setItemCode(historyItem.PackageID);
            	//itemBean.setStaff(postman.PostmanName);
            	//itemBean.setRemark(historyPack.CustomerMobile);
            	
            	out.ItemCodeList.add(itemBean);
        	}
        	//#end 
        	
        	//#start 获取打印报表参数
        	//AZC.Code-AZC.Name
            /*IMZoneDAO zoneDAO = daoFactory.getIMZoneDAO();
            IMZone zone = new IMZone();
            zone.ZoneID  =  operOnline.ZoneID;
            zone = zoneDAO.find(zone);*/
            
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
    		from = zone.ZoneID+"\n"+zone.ZoneName;
    		
    		//TO:
    		to = ppc.PPCID+"\n"+ppc.PPCName;
        	//#end
        }
    	
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
