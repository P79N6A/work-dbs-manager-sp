package com.dcdzsoft.business.im;

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
 * <p>Title: 自提柜后台运营系统</p>
 * <p>Description: 分拣区域信息删除 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class IMZoneDel extends ActionBean
{

    public int doBusiness(InParamIMZoneDel in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        int result = 0;

		//1.	验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(in.OperID)
			||StringUtils.isEmpty(in.ZoneID))
			throw new EduException(ErrorCode.ERR_PARMERR);

		//2.	调用CommonDAO.isOnline(管理员编号)判断管理员是否在线。
		OPOperOnline operOnline = commonDAO.isOnline(in.OperID);

		//3.	调用UtilDAO.getCurrentDateTime()获得系统日期时间。
		java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();

		//虚拟分拣区域，不允许删除
		if(in.ZoneID.endsWith(Constant.ZONE_VIRTUAL_ID)){
			throw new EduException(ErrorCode.ERR_FORBIDDELZONE_OPERATOR);
		}
		
		//#start 表关联检查
		//揽件订单表
        DMCollectionParcelDAO collectDAO = daoFactory.getDMCollectionParcelDAO();
        JDBCFieldArray whereCols0 = new JDBCFieldArray();
        whereCols0.add("ZoneID",in.ZoneID);
        if(collectDAO.isExist(whereCols0)>0){
        	throw new EduException(ErrorCode.ERR_FORBIDDELETE);
        }
		//揽件历史表
        DMHistoryItemDAO dmHistoryDAO = daoFactory.getDMHistoryItemDAO();
        if(dmHistoryDAO.isExist(whereCols0)>0){
        	throw new EduException(ErrorCode.ERR_FORBIDDELETE);
        }
        //PTDeliverItemTransfer
        PTDeliverItemTransferDAO transferDAO = daoFactory.getPTDeliverItemTransferDAO();
        if(transferDAO.isExist(whereCols0)>0){
        	throw new EduException(ErrorCode.ERR_FORBIDDELETE);
        }
        //待投递表
        PTReadyPackageDAO readyPackDAO = daoFactory.getPTReadyPackageDAO();
        if(readyPackDAO.isExist(whereCols0) > 0){
            throw new EduException(ErrorCode.ERR_FORBIDDELETE);
        }
        //在箱表
        PTInBoxPackageDAO inboxPackDAO = daoFactory.getPTInBoxPackageDAO();
        if(inboxPackDAO.isExist(whereCols0) > 0){
            throw new EduException(ErrorCode.ERR_FORBIDDELETE);
        }
        //投递历史表
        PTDeliverHistoryDAO historyDAO = daoFactory.getPTDeliverHistoryDAO();
        if(historyDAO.isExist(whereCols0) > 0){
            throw new EduException(ErrorCode.ERR_FORBIDDELETE);
        }
        
        //IMCollectZone
        IMCollectZoneDAO collectZoneDAO = daoFactory.getIMCollectZoneDAO();
        if(collectZoneDAO.isExist(whereCols0)>0){
        	throw new EduException(ErrorCode.ERR_FORBIDDELETE);
        }
        //PMPostmanDAO
        PMPostmanDAO postmanDAO = daoFactory.getPMPostmanDAO();
        if (postmanDAO.isExist(whereCols0) > 0){
            throw new EduException(ErrorCode.ERR_FORBIDDELZONE_POSTMAN);
        }
        //OPOperOnline
        OPOperOnlineDAO operOnlineDAO = daoFactory.getOPOperOnlineDAO();
        if(operOnlineDAO.isExist(whereCols0)>0){
        	throw new EduException(ErrorCode.ERR_FORBIDDELETE);
        }
        //OPOperator
        OPOperatorDAO operatorDAO = daoFactory.getOPOperatorDAO();
        if(operatorDAO.isExist(whereCols0)>0){
        	throw new EduException(ErrorCode.ERR_FORBIDDELETE);
        }
        //TBTerminal
        TBTerminalDAO terminalDAO = daoFactory.getTBTerminalDAO();
        if(terminalDAO.isExist(whereCols0) > 0){
            throw new EduException(ErrorCode.ERR_FORBIDDELETE);
        }
        //#end
        
        JDBCFieldArray whereColsZoneID = new JDBCFieldArray();
        whereColsZoneID.add("ZoneID", in.ZoneID);
		//6.	删除柜体权限。
        IMZoneLockerRightDAO zoneLockerRightDAO = daoFactory.getIMZoneLockerRightDAO();
        zoneLockerRightDAO.delete(whereColsZoneID);
        
		//7.	删除分拣区域计数器。
        IMZoneCounterDAO zoneCounterDAO = daoFactory.getIMZoneCounterDAO();
        zoneCounterDAO.delete(whereColsZoneID);
        
		//8.	调用IMZoneAO.delete()删除分拣区域信息。
		IMZoneDAO zoneDAO = daoFactory.getIMZoneDAO();
		IMZone zone = new IMZone();
		zone.ZoneID = in.ZoneID;
		
		zone = zoneDAO.find(zone);
		
		result = zoneDAO.delete(zone);

        // 调用CommonDAO.addOperatorLog(OperID，功能编号，系统日期时间，“”)
        OPOperatorLog log = new OPOperatorLog();
        log.OperID = in.OperID;
        log.FunctionID = in.getFunctionID();
        log.OccurTime = sysDateTime;
        log.StationAddr = operOnline.LoginIPAddr;
        log.Remark = in.ZoneID;

		commonDAO.addOperatorLog(log);

        return result;
    }
}
