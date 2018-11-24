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
 * <p>Description: 服务费用删除 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class IMServiceRateDel extends ActionBean
{

    public int doBusiness(InParamIMServiceRateDel in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        int result = 0;

		//1.	验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(in.OperID)
			||StringUtils.isEmpty(in.ServiceID))
			throw new EduException(ErrorCode.ERR_PARMERR);

		//2.	调用CommonDAO.isOnline(管理员编号)判断管理员是否在线。
		OPOperOnline operOnline = commonDAO.isOnline(in.OperID);

		//3.	调用UtilDAO.getCurrentDateTime()获得系统日期时间。
		java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();
		java.sql.Date sysDate = DateUtils.toSQLDate(sysDateTime);
		
		//揽件订单表
        DMCollectionParcelDAO collectDAO = daoFactory.getDMCollectionParcelDAO();
        JDBCFieldArray whereCols0 = new JDBCFieldArray();
        whereCols0.add("ServiceID",in.ServiceID);
        if(collectDAO.isExist(whereCols0)>0){
        	throw new EduException(ErrorCode.ERR_FORBIDDELETE);
        }
		//揽件历史表
        DMHistoryItemDAO dmHistoryDAO = daoFactory.getDMHistoryItemDAO();
        if(dmHistoryDAO.isExist(whereCols0)>0){
        	throw new EduException(ErrorCode.ERR_FORBIDDELETE);
        }
        //服务账单流水
        PYServiceBillWaterDAO serviceBillWaterDAO = daoFactory.getPYServiceBillWaterDAO();
        if(serviceBillWaterDAO.isExist(whereCols0)>0){
        	throw new EduException(ErrorCode.ERR_FORBIDDELETE);
        }
        //BPartnerServiceRight
        IMBPartnerServiceRightDAO partnerSerivceRightDAO = daoFactory.getIMBPartnerServiceRightDAO();
        if(partnerSerivceRightDAO.isExist(whereCols0)>0){
        	throw new EduException(ErrorCode.ERR_FORBIDDELETE);
        }
        
		//
		IMServiceCounterDAO serviceCounterDAO = daoFactory.getIMServiceCounterDAO();
		JDBCFieldArray whereCols = new JDBCFieldArray();
        whereCols.add("ServiceID", in.ServiceID);
        serviceCounterDAO.delete(whereCols);
        
		//4.	调用IMServiceRateDAO.delete()删除服务费用信息。
		IMServiceRateDAO serviceRateDAO = daoFactory.getIMServiceRateDAO();
        IMServiceRate service = new IMServiceRate();		
        service.ServiceID = in.ServiceID;
		
        service = serviceRateDAO.find(service);
		
        result = serviceRateDAO.delete(service);

		// 调用CommonDAO.addOperatorLog(OperID，功能编号，系统日期时间，“”)
		OPOperatorLog log = new OPOperatorLog();
		log.OperID = in.OperID;
		log.FunctionID = in.getFunctionID();
		log.OccurTime = sysDateTime;
		log.StationAddr = operOnline.LoginIPAddr;
		log.Remark = in.ServiceID;

		commonDAO.addOperatorLog(log);

        return result;
    }
}
