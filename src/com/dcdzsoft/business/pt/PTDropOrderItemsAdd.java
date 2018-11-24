package com.dcdzsoft.business.pt;

import javax.sql.RowSet;

import com.dcdzsoft.EduException;
import com.dcdzsoft.sda.db.*;
import com.dcdzsoft.util.*;
import com.dcdzsoft.util.LockUtil.MyLock;
import com.dcdzsoft.dto.function.*;
import com.dcdzsoft.dto.business.*;
import com.dcdzsoft.dao.*;
import com.dcdzsoft.dao.common.*;
import com.dcdzsoft.constant.*;
import com.dcdzsoft.business.ActionBean;

/**
 * <p>Title: 自提柜后台运营系统</p>
 * <p>Description: 添加订单到投递清单 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class PTDropOrderItemsAdd extends ActionBean
{

    public int doBusiness(InParamPTDropOrderItemsAdd in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        int result = 0;
        //1.	验证输入参数是否有效，如果无效返回-1。
        if (StringUtils.isEmpty(in.OperID)
        	//||StringUtils.isEmpty(in.ZoneID)
			||StringUtils.isEmpty(in.TerminalNo)//订单需为同一个柜体
			||StringUtils.isEmpty(in.PackageID))
            throw new EduException(ErrorCode.ERR_PARMERR);
        
        //2.	调用CommonDAO.isOnline(管理员编号)判断管理员是否在线。
        OPOperOnline operOnline = commonDAO.isOnline(in.OperID);
        
        //操作员分拣区域信息??
        /*IMZoneDAO zoneDAO = daoFactory.getIMZoneDAO();
        IMZone zone = new IMZone();
        zone.ZoneID  =  operOnline.ZoneID;
        zone = zoneDAO.find(zone);*/
        
        //柜体权限检查
        IMZoneLockerRightDAO zoneLockerRightDAO = daoFactory.getIMZoneLockerRightDAO();
        IMZoneLockerRight zoneLockerRight = new IMZoneLockerRight();
        zoneLockerRight.ZoneID     = operOnline.ZoneID;
        zoneLockerRight.TerminalNo = in.TerminalNo;
        if(!zoneLockerRightDAO.isExist(zoneLockerRight)){
        	throw new EduException(ErrorCode.ERR_OPERAT_FORBIDUSERLOCKER);
        }
        
        //3.	调用UtilDAO.getCurrentDateTime()获得系统日期时间。
        java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();

        //4.	解析订单号。
        String[][] itemsCodeAndTime = commonDAO.decodeBatchPackageID(in.PackageID);
        
        //获取报告单号
        /*if(StringUtils.isEmpty(in.ReportOrderID)){//DropOrderId
        	in.ReportOrderID = commonDAO.getNextOrderID(SysDict.ORDER_ID_TYPE_DROP, operOnline.ZoneID, in.TerminalNo);
        }*/
        
        //5.	更新订单状态。
		String nextStatus = SysDict.ITEM_STATUS_DROP_SCHEDULED;
		String nowStatus  = SysDict.ITEM_STATUS_DROP_ASSIGNED;//当前订单状态判断
		String terminalNo = in.TerminalNo;
		//String orderID = in.ReportOrderID;
		
		//#start 更新订单状态。
		PTReadyPackageDAO readyPackDAO = daoFactory.getPTReadyPackageDAO();
    	for(String[] itemCodeAndTime:itemsCodeAndTime){
			String itemCode = itemCodeAndTime[0];
			if(StringUtils.isEmpty(itemCode)){
				continue;
			}
			PTReadyPackage readyPack = new PTReadyPackage();
			readyPack.PackageID = itemCode;
			try{
				readyPack = readyPackDAO.find(readyPack);
			}catch(EduException e){
				throw new EduException(ErrorCode.ERR_OPERAT_SUBMITITEMNOTEXISTS);
				//continue;
			}
			//#start订单状态及设备号检查
			if(!readyPack.ItemStatus.equals(nowStatus)){
				throw new EduException(ErrorCode.ERR_OPERAT_FORBIDSUBMITMULTIITEMSTATUS);
				//continue;
			}
			if(!readyPack.TerminalNo.equals(terminalNo)	){
				throw new EduException(ErrorCode.ERR_OPERAT_FORBIDSUBMITMULTILOCKER);
				//continue;
			}
			//#end 
			
	 		//#start 获取柜体锁,获取空箱
	 		String BoxNo = "";
	 		//根据柜体编号获取柜体类型
	 		TBTerminalDAO terminalDAO = daoFactory.getTBTerminalDAO();
	 		JDBCFieldArray whereColsTerminal = new JDBCFieldArray();
	 		whereColsTerminal.add("TerminalNo", in.TerminalNo);
	 		String terminalType = terminalDAO.selectFunctions("TerminalType", whereColsTerminal);
	 		if(SysDict.TERMINALTYPE_PACKAGE.equals(terminalType)){//如果柜体类型是普通包裹柜，分配箱门
		    	MyLock terminalLock = commonDAO.allocTermianlLock(readyPack.TerminalNo);
		    	try{
		    		synchronized (terminalLock) {
		        		//重新分配空箱
		        		BoxNo = commonDAO.getOneCompFreeBox(readyPack.CompanyID,readyPack.TerminalNo, readyPack.BoxType);
		        	}
		    	}catch(EduException e){
		    		BoxNo = "";
		    	}finally{
		    		commonDAO.releaseTermianlLocks(readyPack.TerminalNo);
		    	}
		    	
		        if(StringUtils.isEmpty(BoxNo)){
		 			throw new EduException(ErrorCode.ERR_NOFREEDBOX);
		 		}
	 		}else{//如果柜体类型是一箱多单型或者投递到投递柜台，不用分配箱门
	 			
	 		}
			JDBCFieldArray setCols = new JDBCFieldArray();
            JDBCFieldArray whereCols = new JDBCFieldArray();
	        setCols.add("ItemStatus", nextStatus);
	        //setCols.add("ReportOrderID", orderID);
	        setCols.add("BoxNo", BoxNo);
	        setCols.add("LastModifyTime", sysDateTime);
	        
	        whereCols.add("PackageID", readyPack.PackageID);
	        whereCols.add("ItemStatus", nowStatus);
            
	        readyPackDAO.update(setCols, whereCols);
	        //#end
	        
	        //"AddDropOrder("+readyPack.TerminalNo+"-"+readyPack.BoxType+"-"+BoxNo+")"
	        String[] itemDesc= {readyPack.TerminalNo,readyPack.BoxType, BoxNo};
            
	        //#start记录订单状态更新信息
            PTItemLifeCycle itemLifeCycle = new PTItemLifeCycle();
            itemLifeCycle.PackageID  = itemCode;
            itemLifeCycle.ItemStatus = nextStatus;
            itemLifeCycle.OperatorID    = in.OperID;
            itemLifeCycle.OperatorType  = ""+operOnline.OperType;
            itemLifeCycle.LastModifyTime = sysDateTime;
            itemLifeCycle.ZoneID = readyPack.ZoneID;
            itemLifeCycle.LastStatus = readyPack.ItemStatus;
            if(readyPack.LastModifyTime == null){
            	readyPack.LastModifyTime = sysDateTime;
            }
            itemLifeCycle.StatusTime = ((int)((sysDateTime.getTime() - readyPack.LastModifyTime.getTime()) / 60000L));
            commonDAO.addItemLifeCycle(Constant.ACTION_CODE_ADD2ORDER, itemLifeCycle, itemDesc);
            //#end
            
            //#start 订单数据加入发送队列进行发送(状态更新)
        	commonDAO.addItemStatusUpdate(readyPack.PPCID, nextStatus, readyPack, "");
	        //#end

        }
    	//#end
		
        

        return result;
    }
}
