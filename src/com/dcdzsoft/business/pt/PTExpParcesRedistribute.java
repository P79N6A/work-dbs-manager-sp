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
 * <p>Description: 超期包裹重新投递 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class PTExpParcesRedistribute extends ActionBean
{

    public String doBusiness(InParamPTExpParcesRedistribute in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();

		//1.	验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(in.OperID)
			//||StringUtils.isEmpty(in.ZoneID)
			||StringUtils.isEmpty(in.ItemStatus)
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
		
        
        if(!SysDict.ITEM_STATUS_DROP_RETURNED.equals(in.ItemStatus)){
        	throw new EduException(ErrorCode.ERR_OPERAT_EXISTSFORBIDITEM);
        }
        
        in.ItemStatus = SysDict.ITEM_STATUS_DROP_RETURNED;
        String nowStatus  = in.ItemStatus;
		String nextStatus = SysDict.ITEM_STATUS_DROP_RECEIVED;
		//#start 订单信息检查，筛选符合条件的订单 ：订单状态为:returned
		PTDeliverHistoryDAO historyPackDAO = daoFactory.getPTDeliverHistoryDAO();
		java.util.List<PTDeliverHistory> historyPackList = new java.util.LinkedList<PTDeliverHistory>();
		for(String[] itemCodeAndTime:itemsCodeAndTime){
			String itemCode   = itemCodeAndTime[0];
			String createtime = itemCodeAndTime[1];
			if(StringUtils.isEmpty(itemCode)|| StringUtils.isEmpty(createtime)){
				continue;
			}
        	PTDeliverHistory historyPack = new PTDeliverHistory();
        	historyPack.PackageID = itemCode;
        	historyPack.CreateTime = java.sql.Timestamp.valueOf(createtime);
        	try{
        		historyPack = historyPackDAO.find(historyPack);
			}catch(EduException e){
				continue;
			}
        	
			//状态检查 
			if(!in.ItemStatus.equals(historyPack.ItemStatus)){
				throw new EduException(ErrorCode.ERR_OPERAT_EXISTSFORBIDITEM);
  				//continue;
        	}
			
			JDBCFieldArray setCols = new JDBCFieldArray();
	        JDBCFieldArray whereCols = new JDBCFieldArray();
	        
	        //#start 创建新的待投递订单,更新订单状态
	        //待投递订单
			PTReadyPackageDAO readyPackDAO = daoFactory.getPTReadyPackageDAO();
			PTReadyPackage readyPack = new PTReadyPackage();
			readyPack.CreateTime  = sysDateTime;
			readyPack.PackageID   = historyPack.PackageID;
			readyPack.PPCID       = historyPack.PPCID;
			readyPack.ZoneID      = historyPack.ZoneID;
			readyPack.CustomerMobile = historyPack.CustomerMobile;
			readyPack.CustomerID     = historyPack.CustomerID;
			readyPack.CompanyID      = historyPack.CompanyID;
			readyPack.RefNo          = historyPack.RefNo;
			readyPack.PrintedFlag    = 0;
			readyPack.ReportOrderID  = "";
	        
			readyPack.BoxType     = historyPack.ParcelSize;
			readyPack.ParcelSize  = historyPack.ParcelSize;
			readyPack.DropNum     = historyPack.DropNum;
			readyPack.ItemStatus  = SysDict.ITEM_STATUS_DROP_RECEIVED;
	        
			readyPack.Remark      = historyPack.Remark;
	        
	        readyPackDAO.insert(readyPack);
	        //#end
	        
    		//记录订单信息，用于打印
	        historyPackList.add(historyPack);
	        
	        setCols.add("RunStatus", SysDict.ITEM_RUN_STATUS_RE_DISTRIBUTE);
	        setCols.add("LastModifyTime", sysDateTime);
	        
	        whereCols.add("PackageID", historyPack.PackageID);
	        whereCols.add("CreateTime", historyPack.CreateTime);
	        whereCols.add("RunStatus", "!=",SysDict.ITEM_RUN_STATUS_RE_DISTRIBUTE);
	        
	        historyPackDAO.update(setCols, whereCols);
	        
	        //"Redistribute("+zone.ZoneName+",No."+readyPack.DropNum+")"
	        String[] itemDesc= {zone.ZoneName,""+readyPack.DropNum};
            
	        //#start 记录订单状态更新信息
    		PTItemLifeCycle itemLifeCycle = new PTItemLifeCycle();
            itemLifeCycle.PackageID  = historyPack.PackageID;
            itemLifeCycle.ItemStatus = nextStatus;
            itemLifeCycle.OperatorID    = operOnline.OperID;
            itemLifeCycle.OperatorType = ""+operOnline.OperType;
            itemLifeCycle.LastModifyTime = sysDateTime;
            itemLifeCycle.ZoneID = historyPack.ZoneID;
            itemLifeCycle.LastStatus = historyPack.ItemStatus;
            itemLifeCycle.StatusTime = ((int)((sysDateTime.getTime() - historyPack.LastModifyTime.getTime()) / 60000L));
            commonDAO.addItemLifeCycle(Constant.ACTION_CODE_REDISTRIBUTE, itemLifeCycle, itemDesc);
            //#end
	        
            //#start 订单数据加入发送队列进行发送(状态更新)
        	commonDAO.addItemStatusUpdate(readyPack.PPCID, nextStatus, readyPack, "");
	        //#end
			
    	}
    	//#end 
		
        //检查是否需要打印：
		boolean isPrint = true;/*commonDAO.checkPrintFlag(itemsCodeAndTime, nowStatus, 
	        zone.MandatoryFlag, SysDict.MANDATORY_REPORT7_CODE,ErrorCode.ERR_MANDATORY_REPORT7_PRINT);*/
		
		String documentID = "";
        //获取报表数据
        if(historyPackList.size()>0 && isPrint){
        	//DocumentID
        	documentID = commonDAO.getNextOrderID(SysDict.ORDER_ID_TYPE_REDISTRIBUTE, operOnline.ZoneID, operOnline.ZoneID);
            //commonDAO.updateZoneCounter(operOnline.ZoneID, SysDict.COUNTER_TYPE_AZC_REDISTRIBUTE);
            
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
    
}
