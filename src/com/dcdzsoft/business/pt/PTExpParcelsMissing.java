package com.dcdzsoft.business.pt;

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
 * <p>Description: 未收到取回的包裹（逾期or未投递） </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class PTExpParcelsMissing extends ActionBean
{

    public String doBusiness(InParamPTExpParcelsMissing in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        

		//1.验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(in.OperID)
			//||StringUtils.isEmpty(in.ZoneID)
			//||StringUtils.isEmpty(in.ItemStatus)
			||StringUtils.isEmpty(in.PackageID))
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

        //4.	解析订单号。
        String[][] itemsCodeAndTime = commonDAO.decodeBatchPackageID(in.PackageID);	
        
        PTDeliverHistoryDAO historyPackDAO = daoFactory.getPTDeliverHistoryDAO();
        java.util.List<PTDeliverHistory> historyPackList = new java.util.LinkedList<PTDeliverHistory>();
        String nextStatus = "";
        String nowStatus = "";
        switch(in.ItemStatus){
        case SysDict.ITEM_STATUS_DROP_INTRANSIT_BACK:
        	//#start逾期取回时丢失
        	nowStatus = SysDict.ITEM_STATUS_DROP_INTRANSIT_BACK;
            nextStatus = SysDict.ITEM_STATUS_DROP_MISSING;
        	
        	for(String[] itemCodeAndTime:itemsCodeAndTime){
    			String itemCode = itemCodeAndTime[0];
    			String createtime = itemCodeAndTime[1];
				if(StringUtils.isEmpty(itemCode)|| StringUtils.isEmpty(createtime)){
					continue;
				}
    			
    			//#start 查询订单信息，更新订单状态
    			PTDeliverHistory historyPack = new PTDeliverHistory();
    			historyPack.PackageID = itemCode;
    			historyPack.CreateTime = java.sql.Timestamp.valueOf(createtime);
    			try{
    				historyPack = historyPackDAO.find(historyPack);
    			}catch(EduException e){
    				continue;
    			}
    			
    	        //记录订单信息，用于打印
    	        historyPackList.add(historyPack);
    	        
    			JDBCFieldArray setCols = new JDBCFieldArray();
    	        JDBCFieldArray whereCols = new JDBCFieldArray();
    	        setCols.add("ItemStatus", nextStatus);
    	        setCols.add("LastModifyTime", sysDateTime);
    	        
    	        whereCols.add("PackageID", itemCode);
    	        whereCols.add("ItemStatus", nowStatus);
    	        //whereCols.checkAdd("TerminalNo", TerminalNo);

    	        int count = historyPackDAO.update(setCols, whereCols);
    	        if(count<=0){
    	        	continue;
    	        }
    	        //#end
    	        
    	        ///////
            	addItemLifeCycleAndStatusUpdate(operOnline, historyPack, nextStatus, sysDateTime);
            }
        	//#end
        	break;
        case SysDict.ITEM_STATUS_DROP_INTRANSIT_OUT:
        	//#start
        	nowStatus = SysDict.ITEM_STATUS_DROP_INTRANSIT_OUT;
            nextStatus = SysDict.ITEM_STATUS_DROP_MISSING;
            
            PTReadyPackageDAO readyPackDAO = daoFactory.getPTReadyPackageDAO();
        	for(String[] itemCodeAndTime:itemsCodeAndTime){
    			String itemCode = itemCodeAndTime[0];
    			if(StringUtils.isEmpty(itemCode)){
    				continue;
    			}
    			
    			//#start 查询订单信息，更新订单状态
    			PTReadyPackage readyPack = new PTReadyPackage();
    			readyPack.PackageID = itemCode;
    			try{
    				readyPack = readyPackDAO.find(readyPack);
    			}catch(EduException e){
    				continue;
    			}
    			
    			//Listed 状态？？？？
    			if(!SysDict.ITEM_STATUS_DROP_INTRANSIT_OUT.equalsIgnoreCase(readyPack.ItemStatus)){
    				throw new EduException(ErrorCode.ERR_OPERAT_EXISTSFORBIDITEM);
	  				//continue;
    			}
    			
    	        //先删除，保证数据不重复??  --预先插入历史订单表？
    	        JDBCFieldArray whereCols1 = new JDBCFieldArray();
    	        whereCols1.add("PackageID", readyPack.PackageID);
    	        whereCols1.add("CreateTime", readyPack.CreateTime);
    	        
    	        historyPackDAO.delete(whereCols1);
    	        
    	        //插入历史记录
    	        PTDeliverHistory historyPack = new PTDeliverHistory();
    	        historyPack.PackageID = readyPack.PackageID;
    	        historyPack.CreateTime = readyPack.CreateTime;
    	        historyPack.ItemStatus = nextStatus;
            	historyPack.RunStatus  = SysDict.ITEM_RUN_STATUS_RUNNING;
            	historyPack.PPCID = readyPack.PPCID;
                historyPack.ZoneID = readyPack.ZoneID;
                historyPack.CompanyID   = readyPack.CompanyID;
                historyPack.RefNo       = readyPack.RefNo;
                historyPack.TerminalNo  = readyPack.TerminalNo;
                historyPack.BoxNo       = readyPack.BoxNo;
                historyPack.PrintedFlag = readyPack.PrintedFlag;
                historyPack.ReportOrderID = readyPack.ReportOrderID;
                historyPack.DropAgentID = readyPack.DropAgentID;
                historyPack.ReturnAgentID = readyPack.DropAgentID;////
                historyPack.CustomerID = readyPack.CustomerID;
                historyPack.CustomerMobile = readyPack.CustomerMobile;
                historyPack.CustomerName = readyPack.CustomerName;
                historyPack.CustomerAddress = readyPack.CustomerAddress;
                historyPack.LastModifyTime = sysDateTime;
                historyPack.Remark = readyPack.Remark;
    	        
    	        historyPackDAO.insert(historyPack);
    	        
    	        //
    	        readyPackDAO.delete(readyPack);
    	        //#end
    	        
    	        //记录订单信息用于打印
    	        historyPackList.add(historyPack);
    	        
    	        ////////////
    	        addItemLifeCycleAndStatusUpdate(operOnline,readyPack, historyPack, nextStatus, sysDateTime);
    	        
            }
        	//#end
        	break;
        default:
			throw new EduException(ErrorCode.ERR_OPERAT_EXISTSFORBIDITEM);
        }    
        
		//检查是否需要打印：
		boolean isPrint = true;//默认需要打印
		
		String documentID = "";
        //获取报表数据
        if(historyPackList.size()>0 && isPrint){
        	//DocumentID
        	documentID = commonDAO.getNextOrderID(SysDict.ORDER_ID_TYPE_MISSING, operOnline.ZoneID, operOnline.ZoneID);
        	
        	//#start  获取打印报表数据列表
            
        	Iterator<PTDeliverHistory> hisortyPackListIre = historyPackList.iterator();
        	while(hisortyPackListIre.hasNext()){
        		PTDeliverHistory historyPack = hisortyPackListIre.next();
        		
    	        //更新订单打印报表流水号
            	JDBCFieldArray whereCols = new JDBCFieldArray();
            	JDBCFieldArray setCols = new JDBCFieldArray();
            	setCols.add("ReportOrderID", documentID);
            	setCols.add("LastModifyTime", sysDateTime);
            	
            	whereCols.add("PackageID", historyPack.PackageID);
    	        whereCols.add("CreateTime", historyPack.CreateTime);
    	        historyPackDAO.update(setCols, whereCols);
            	
            	//#start  调用CommonDAO.addOperatorLog(OperID，功能编号，系统日期时间，“”)        	
    			OPOperatorLog log = new OPOperatorLog();
    			log.OperID = in.OperID;
    			log.FunctionID = in.getFunctionID();
    			log.OccurTime = sysDateTime;
    			log.StationAddr = operOnline.LoginIPAddr;
    			log.Remark = "itemCode="+historyPack.PackageID+",reportid="+documentID;

    			commonDAO.addOperatorLog(log);
    			//#end
        	}
        	//#end 
        	
        }
        return documentID;
    }
    
    private String addItemLifeCycleAndStatusUpdate(OPOperOnline operOnline, PTDeliverHistory historyPack, 
    		String nextStatus, java.sql.Timestamp sysDateTime) throws EduException{
    	
    	//获取回收员名称
        String postmanName = "";
        PMPostmanDAO postmanDAO = daoFactory.getPMPostmanDAO();
        PMPostman postman = new PMPostman();
        postman.PostmanID = historyPack.ReturnAgentID;
        try{
            postman = postmanDAO.find(postman);
            postmanName = postman.PostmanName;
        }catch(EduException e){
            postmanName = "";
        }
        
        //"Missed("+historyPack.ReturnAgentID+"-"+postmanName+")"
        String[] itemDesc= {historyPack.ReturnAgentID, postmanName};
        StringBuffer itemStatusDescription = new StringBuffer(Constant.getAction(Constant.ACTION_CODE_MISSING, itemDesc));
        
        //#start记录订单状态更新信息
        PTItemLifeCycle itemLifeCycle = new PTItemLifeCycle();
        itemLifeCycle.PackageID  = historyPack.PackageID;
        itemLifeCycle.ItemStatus = nextStatus;
        itemLifeCycle.OperatorID    = operOnline.OperID;
        itemLifeCycle.OperatorType  = ""+operOnline.OperType;
        itemLifeCycle.LastModifyTime = sysDateTime;
        itemLifeCycle.Remark = itemStatusDescription.toString();
        itemLifeCycle.ZoneID = historyPack.ZoneID;
        itemLifeCycle.LastStatus = historyPack.ItemStatus;
        itemLifeCycle.StatusTime = ((int)((sysDateTime.getTime() - historyPack.LastModifyTime.getTime()) / 60000L));
        commonDAO.addItemLifeCycle(Constant.ACTION_CODE_MISSING,itemLifeCycle, itemDesc);
        //#end
        
        //#start 订单数据加入发送队列进行发送(状态更新)
    	commonDAO.addItemStatusUpdate(historyPack.PPCID,nextStatus, historyPack, "");
        //#end
    	return postmanName;
    }
    private String addItemLifeCycleAndStatusUpdate(OPOperOnline operOnline,PTReadyPackage readyPack, PTDeliverHistory historyPack, 
    		String nextStatus, java.sql.Timestamp sysDateTime) throws EduException{
    	
    	//获取回收员名称
        String postmanName = "";
        PMPostmanDAO postmanDAO = daoFactory.getPMPostmanDAO();
        PMPostman postman = new PMPostman();
        postman.PostmanID = historyPack.ReturnAgentID;
        try{
            postman = postmanDAO.find(postman);
            postmanName = postman.PostmanName;
        }catch(EduException e){
            postmanName = "";
        }
        
        //"Missed("+historyPack.ReturnAgentID+"-"+postmanName+")"
        String[] itemDesc= {historyPack.ReturnAgentID, postmanName};
        StringBuffer itemStatusDescription = new StringBuffer(Constant.getAction(Constant.ACTION_CODE_MISSING, itemDesc));
        
        //#start记录订单状态更新信息
        PTItemLifeCycle itemLifeCycle = new PTItemLifeCycle();
        itemLifeCycle.PackageID  = historyPack.PackageID;
        itemLifeCycle.ItemStatus = nextStatus;
        itemLifeCycle.OperatorID    = operOnline.OperID;
        itemLifeCycle.OperatorType  = ""+operOnline.OperType;
        itemLifeCycle.LastModifyTime = sysDateTime;
        itemLifeCycle.Remark = itemStatusDescription.toString();
        itemLifeCycle.ZoneID = readyPack.ZoneID;
        itemLifeCycle.LastStatus = readyPack.ItemStatus;
        if(readyPack.LastModifyTime == null){
        	readyPack.LastModifyTime = sysDateTime;
        }
        itemLifeCycle.StatusTime = ((int)((sysDateTime.getTime() - readyPack.LastModifyTime.getTime()) / 60000L));
        commonDAO.addItemLifeCycle(Constant.ACTION_CODE_MISSING,itemLifeCycle, itemDesc);
        //#end
        
        //#start 订单数据加入发送队列进行发送(状态更新)
    	commonDAO.addItemStatusUpdate(historyPack.PPCID,nextStatus, historyPack, "");
        //#end
    	return postmanName;
    }
}
