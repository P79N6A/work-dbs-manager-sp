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
import com.dcdzsoft.email.EmailInfo;
import com.dcdzsoft.email.EmailSenderManager;
import com.dcdzsoft.config.ApplicationConfig;
import com.dcdzsoft.constant.*;
import com.dcdzsoft.business.ActionBean;

/**
 * <p>Title: 自提柜后台运营系统</p>
 * <p>Description: 确认包裹丢失，订单信息邮件发给沙特法务部门 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class PTConfirmParcelsMissing extends ActionBean
{

    public String doBusiness(InParamPTConfirmParcelsMissing in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();

		//1.验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(in.OperID)
			||StringUtils.isEmpty(in.PackageID)
			||StringUtils.isEmpty(in.ItemStatus))
			throw new EduException(ErrorCode.ERR_PARMERR);

		
		//2.调用CommonDAO.isOnline(管理员编号)判断管理员是否在线。
		OPOperOnline operOnline = commonDAO.isOnline(in.OperID);

		IMZoneDAO zoneDAO = daoFactory.getIMZoneDAO();
        IMZone zone = new IMZone();
        zone.ZoneID  =  operOnline.ZoneID;
        zone = zoneDAO.find(zone);
        
		//3.调用UtilDAO.getCurrentDateTime()获得系统日期时间。
		java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();
		java.sql.Date sysDate = DateUtils.toSQLDate(sysDateTime);


		//解析订单号。
		String[][] itemsCodeAndTime = commonDAO.decodeBatchPackageID(in.PackageID);	  
		
		//#start 确认包裹丢失，将订单信息邮件发送给沙特法务部门
		String nowStatus  = SysDict.ITEM_STATUS_DROP_MISSING;
		String nextStatus = SysDict.ITEM_STATUS_DROP_LOSE;
			
		PTDeliverHistoryDAO historyPackDAO = daoFactory.getPTDeliverHistoryDAO();
		java.util.List<PTDeliverHistory> historyPackList = new java.util.LinkedList<PTDeliverHistory>();
    	for(String[] itemCodeAndTime:itemsCodeAndTime){
			String itemCode = itemCodeAndTime[0];
			String createtime = itemCodeAndTime[1];
			if(StringUtils.isEmpty(itemCode)|| StringUtils.isEmpty(createtime)){
				continue;
			}
			//#start 查询订单信息
			PTDeliverHistory historyPack = new PTDeliverHistory();
			historyPack.PackageID = itemCode;
			historyPack.CreateTime = java.sql.Timestamp.valueOf(createtime);
			try{
				historyPack = historyPackDAO.find(historyPack);
			}catch(EduException e){
				continue;
			}
			if(!historyPack.ItemStatus.equals(nowStatus)){//missing
				throw new EduException(ErrorCode.ERR_OPERAT_EXISTSFORBIDITEM);
  				//continue;
			}
			//#end
			
	        //记录订单数据，进行报表打印
	        historyPackList.add(historyPack);
	        
			//#start 查询投递记录,发送到
			/*java.sql.Date beginDate = new java.sql.Date(historyPack.CreateTime.getTime());
			java.sql.Date endDate   = new java.sql.Date(historyPack.LastModifyTime.getTime());
			OutParamItemDetail itemDetail = commonDAO.getItemRecordDetail(historyPack.PackageID, "0", beginDate, endDate, 9);
			
			
			//发送订单记录
			try {
				EmailSenderManager.sendMissingParcelDetailInfo(itemDetail);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				System.err.println(e.getMessage());
				throw new EduException(ErrorCode.ERR_BUSINESS_SENDSMSFAIL);
			}*/
			//#end
			
			//#start  更新订单状态
			JDBCFieldArray setCols = new JDBCFieldArray();
	        JDBCFieldArray whereCols = new JDBCFieldArray();

	        setCols.add("RunStatus", SysDict.ITEM_RUN_STATUS_PARCEL_MISSING);
	        setCols.add("ItemStatus", nextStatus);
	        setCols.add("LastModifyTime", sysDateTime);
	        
	        whereCols.add("PackageID", itemCode);
	        whereCols.add("CreateTime", historyPack.CreateTime);
	        whereCols.add("ItemStatus", nowStatus);
	        //whereCols.checkAdd("TerminalNo", TerminalNo);
	        
	        int count = historyPackDAO.update(setCols, whereCols);
	        if(count<=0){
	        	continue;
	        }
	        //#end
	        
            //"Confirm Lose("zone.ZoneName+")"
            String[] itemDesc= {zone.ZoneName};
            
	        //#start记录订单状态更新信息
            PTItemLifeCycle itemLifeCycle = new PTItemLifeCycle();
            itemLifeCycle.PackageID  = itemCode;
            itemLifeCycle.ItemStatus = nextStatus;
            itemLifeCycle.OperatorID    = in.OperID;
            itemLifeCycle.OperatorType  = ""+operOnline.OperType;      
            itemLifeCycle.LastModifyTime = sysDateTime;
            itemLifeCycle.ZoneID = historyPack.ZoneID;
            itemLifeCycle.LastStatus = historyPack.ItemStatus;
            itemLifeCycle.StatusTime = ((int)((sysDateTime.getTime() - historyPack.LastModifyTime.getTime()) / 60000L));
            commonDAO.addItemLifeCycle(Constant.ACTION_CODE_LOST, itemLifeCycle, itemDesc);
            //#end
            
            //#start 订单数据加入发送队列进行发送(状态更新)
        	commonDAO.addItemStatusUpdate(historyPack.PPCID, nextStatus, historyPack,  SysDict.TRANSFER_TYPE_MISSING);
	        //#end
        	
        }
    	//#end
		
		//检查是否需要打印：
		boolean isPrint = true;//默认需要打印
		
		String documentID = "";
		
        //获取报表数据
        if(historyPackList.size() >0 && isPrint){
        	//DocumentID
        	documentID = commonDAO.getNextOrderID(SysDict.ORDER_ID_TYPE_LOSE, operOnline.ZoneID, operOnline.ZoneID);
        	
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
