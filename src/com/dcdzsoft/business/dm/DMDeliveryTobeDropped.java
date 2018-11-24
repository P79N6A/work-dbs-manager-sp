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
 * <p>Description: 包裹就近派件投递 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class DMDeliveryTobeDropped extends ActionBean
{

	/**
	 * ReturnFlag=1,为退件订单，不允许投递只能发往PPC
	 * @param in
	 * @return
	 * @throws EduException
	 */
    public String doBusiness(InParamDMDeliveryTobeDropped in) throws EduException
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

        IMZoneDAO zoneDAO = daoFactory.getIMZoneDAO();
        IMZone zone = new IMZone();
        zone.ZoneID  =  operOnline.ZoneID;
        zone = zoneDAO.find(zone);
	        
		//3.	调用UtilDAO.getCurrentDateTime()获得系统日期时间。
		java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();
		java.sql.Date sysDate = DateUtils.toSQLDate(sysDateTime);
		
		if(!SysDict.ITEM_STATUS_COLLECT_INWARD_RECEIVED.equals(in.ItemStatus)){
        	throw new EduException(ErrorCode.ERR_OPERAT_EXISTSFORBIDITEM);
        }
		
		IMPostalProcessingCenter ppc = new IMPostalProcessingCenter();
		IMPostalProcessingCenterDAO postalProcessingCenterDAO = daoFactory.getIMPostalProcessingCenterDAO();
        ppc.PPCID = in.PPCID;
        ppc = postalProcessingCenterDAO.find(ppc);
		
		//4.	解析订单号。
        String[][] itemsCodeAndTime = commonDAO.decodeBatchPackageID(in.PackageID); 
        
        //#start 订单信息检查，筛选符合条件的订单 ;新建待投递订单记录，更新寄件订单状态;记录订单状态更新信息;
        String nextStatus = SysDict.ITEM_STATUS_COLLECT_TOBE_DEOPPED;
		PTReadyPackageDAO readyPackDAO = daoFactory.getPTReadyPackageDAO();
        DMHistoryItemDAO historyItemDAO = daoFactory.getDMHistoryItemDAO();
		java.util.List<DMHistoryItem> historyPackList = new java.util.LinkedList<DMHistoryItem>();
		for(String[] itemCodeAndTime:itemsCodeAndTime){
			String itemCode   = itemCodeAndTime[0];
			String createtime = itemCodeAndTime[1];
			if(StringUtils.isEmpty(itemCode)|| StringUtils.isEmpty(createtime)){
				continue;
			}
			DMHistoryItem historyItem = new DMHistoryItem();
			historyItem.PackageID = itemCode;
			historyItem.CreateTime = java.sql.Timestamp.valueOf(createtime);
			
			historyItem = historyItemDAO.find(historyItem);
    		//状态检查 
			if(!SysDict.ITEM_STATUS_COLLECT_INWARD_RECEIVED.equals(historyItem.ItemStatus)){
				throw new EduException(ErrorCode.ERR_OPERAT_EXISTSFORBIDITEM);
        	}
			//退件订单处理
			if("1".equals(historyItem.ReturnFlag)){//ReturnFlag=1,为退件订单，只能发往PPC
				throw new EduException(ErrorCode.ERR_COLLECT_RETURNIEMTSONLYTRANSFER);
			}
			//检查收件柜子所在AZC是否一致
			if(StringUtils.isNotEmpty(historyItem.TerminalNo)){
				TBTerminalDAO terminalDAO = daoFactory.getTBTerminalDAO();
	    		JDBCFieldArray whereCols1 = new JDBCFieldArray();
	    		whereCols1.add("TerminalNo", historyItem.TerminalNo);
		        whereCols1.add("ZoneID", operOnline.ZoneID);
		        if(terminalDAO.isExist(whereCols1)<1){
		        	throw new EduException(ErrorCode.ERR_OPERAT_ITEMBELONGOTDIFFERENTAZC);
		        }
			}
        	
			historyPackList.add(historyItem);
			
			JDBCFieldArray setCols = new JDBCFieldArray();
	        JDBCFieldArray whereCols = new JDBCFieldArray();
	        
	        //#start
	        PTReadyPackage readyPack  = new PTReadyPackage();
	        readyPack.CreateTime      = sysDateTime;
	        readyPack.PackageID       = historyItem.PackageID;
	        readyPack.CustomerAddress = historyItem.CustomerAddress;
	        readyPack.CustomerName    = historyItem.CustomerName;
	        readyPack.CustomerMobile  = historyItem.CustomerMobile;
	        readyPack.CustomerID      = historyItem.CustomerID;
	        readyPack.TerminalNo      = historyItem.TerminalNo;
	        readyPack.ParcelSize      = historyItem.ParcelSize;
	        readyPack.BoxType         = historyItem.ParcelSize;
	        readyPack.ZoneID          = zone.ZoneID;
	        readyPack.CompanyID       = zone.CompanyID;
	        readyPack.PPCID           = ppc.PPCID;
	        readyPack.PrintedFlag     = 0;//初始打印标识
	        readyPack.ReportOrderID   = "";
	        readyPack.LastModifyTime  = sysDateTime;
	        
	        //readyPack.BoxNo       = "0";
	        readyPack.DropNum      = 0;
	        readyPack.ItemStatus   = SysDict.ITEM_STATUS_DROP_RECEIVED;
	        readyPack.Remark       = readyPack.Remark;
	        readyPackDAO.insert(readyPack);
	        
	        //#end
	        
	        //"Redistribute("+zone.ZoneName+",No."+readyPack.DropNum+")"
            String[] itemDesc= {zone.ZoneName,""+readyPack.DropNum};
            
	        //#start 添加投递订单周期记录
            PTItemLifeCycle itemLifeCyclePT = new PTItemLifeCycle();
            itemLifeCyclePT.PackageID  = readyPack.PackageID;
            itemLifeCyclePT.ItemStatus = readyPack.ItemStatus;
            itemLifeCyclePT.OperatorID    = operOnline.OperID;
            itemLifeCyclePT.OperatorType = ""+operOnline.OperType;
            itemLifeCyclePT.LastModifyTime = sysDateTime;
            commonDAO.addItemLifeCycle(Constant.ACTION_CODE_REDISTRIBUTE, itemLifeCyclePT,itemDesc);
            //#end 
	        
            //#start 订单数据加入发送队列进行发送(状态更新)
        	commonDAO.addItemStatusUpdate(readyPack.PPCID, readyPack.ItemStatus, readyPack, "");
	        //#end
        	
	        
	        //#start更新订单状态
	        setCols.add("ItemStatus", nextStatus);
	        setCols.add("LastModifyTime", sysDateTime);
	        
	        whereCols.add("PackageID", historyItem.PackageID);
	        whereCols.add("CreateTime", historyItem.CreateTime);
	        
	        historyItemDAO.update(setCols, whereCols);
	        //#end
	        
	        StringBuffer itemStatusDescription = new StringBuffer(Constant.getAction(Constant.ACTION_CODE_REDISTRIBUTE, itemDesc));
            
	        //#start 添加投递订单周期记录
            DMItemLifeCycle itemLifeCycle = new DMItemLifeCycle();
            itemLifeCycle.PackageID  = historyItem.PackageID;
            itemLifeCycle.ItemStatus = nextStatus;
            itemLifeCycle.OperatorID    = operOnline.OperID;
            itemLifeCycle.OperatorType = ""+operOnline.OperType;
            itemLifeCycle.LastModifyTime = sysDateTime;
            itemLifeCycle.Remark = itemStatusDescription.toString();
            commonDAO.addItemLifeCycle(itemLifeCycle);
            //#end 
            
    	}
		
		//#end
        
		//检查是否需要打印：

        
        //6.	订单报告打印标识检查，存在需要打印而未打印的订单，返回ERR_MANDATORY_REPORT7_PRINT。
		boolean isPrint = true;/*commonDAO.checkPrintFlag(itemsCodeAndTime, nowStatus, 
	        zone.MandatoryFlag, SysDict.MANDATORY_REPORT7_CODE,ErrorCode.ERR_MANDATORY_REPORT7_PRINT);*/
    	if(historyPackList.size()<=0){
    		isPrint = false;
    	}
		
    	String documentID = "";
        //更新订单打印报告编号
        if(isPrint){
        	//DocumentID
        	documentID = commonDAO.getNextOrderID(SysDict.ORDER_ID_TYPE_REDISTRIBUTE, operOnline.ZoneID, operOnline.ZoneID);
            
        	//#start  获取打印报表数据列表
        	Iterator<DMHistoryItem> hisortyPackListIre = historyPackList.iterator();
        	while(hisortyPackListIre.hasNext()){
        		DMHistoryItem historyPack = hisortyPackListIre.next();

    	        //更新订单打印报表流水号
            	JDBCFieldArray whereCols = new JDBCFieldArray();
            	JDBCFieldArray setCols = new JDBCFieldArray();
            	setCols.add("ReportOrderID", documentID);
            	setCols.add("LastModifyTime", sysDateTime);
            	
            	whereCols.add("PackageID", historyPack.PackageID);
    	        whereCols.add("CreateTime", historyPack.CreateTime);
    	        historyItemDAO.update(setCols, whereCols);
            	
        	}
        	//#end 
        }
    	
        return documentID;
    }
}
