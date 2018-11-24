package com.dcdzsoft.business.pt;

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
 * <p>Title: 智能自助包裹柜系统</p>
 * <p>Description: 添加转运包裹到清单 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author 王中立
 * @version 1.0
 */

public class PTTransportPackAdd extends ActionBean
{

    public int doBusiness(InParamPTTransportPackAdd in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        int result = 0;

		//1.验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(in.OperID)
			||StringUtils.isEmpty(in.PackageID))
			throw new EduException(ErrorCode.ERR_PARMERR);


		//2.调用CommonDAO.isOnline(管理员编号)判断管理员是否在线。
		OPOperOnline operOnline = commonDAO.isOnline(in.OperID);

		//3.调用UtilDAO.getCurrentDateTime()获得系统日期时间。
		java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();
		java.sql.Date sysDate = DateUtils.toSQLDate(sysDateTime);


		//4.解析订单号。
		String[][] itemsCodeAndTime = commonDAO.decodeBatchPackageID(in.PackageID);

		//5.更新订单状态。
		String nextStatus = SysDict.ITEM_STATUS_DROP_TRANSFERLIST;
		String nowStatus  = in.ItemStatus;
		String terminalNo = in.TerminalNo;
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
			JDBCFieldArray setCols = new JDBCFieldArray();
            JDBCFieldArray whereCols = new JDBCFieldArray();
            setCols.add("ItemStatus", nextStatus);
	        setCols.add("LastModifyTime", sysDateTime);
	        whereCols.add("PackageID", readyPack.PackageID);
	        whereCols.add("ItemStatus", nowStatus);            
	        readyPackDAO.update(setCols, whereCols);
			//#end 
            
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
            itemLifeCycle.StatusTime = ((int)((sysDateTime.getTime() - readyPack.LastModifyTime.getTime())/60000L));
            
            commonDAO.addItemLifeCycle(Constant.ACTION_CODE_ADD2ORDER, itemLifeCycle, null);
            //#end
            
            //#start 订单数据加入发送队列进行发送(状态更新)
        	commonDAO.addItemStatusUpdate(readyPack.PPCID, nextStatus, readyPack, "");
	       // #end

        }
    	//#end
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
