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
 * <p>Description: 服务费用修改 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class IMServiceRateMod extends ActionBean
{

    public int doBusiness(InParamIMServiceRateMod in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        int result = 0;

		//1.	验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(in.OperID)
			||StringUtils.isEmpty(in.ServiceID)
			||StringUtils.isEmpty(in.ServiceName))
			throw new EduException(ErrorCode.ERR_PARMERR);

		//2.	调用CommonDAO.isOnline(管理员编号)判断管理员是否在线。
		OPOperOnline operOnline = commonDAO.isOnline(in.OperID);

		//3.	调用UtilDAO.getCurrentDateTime()获得系统日期时间。
		java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();
		java.sql.Date sysDate = DateUtils.toSQLDate(sysDateTime);

		//4.	调用IMServiceRateDAO.isExist()判断服务费用名称是否存在，如果存在返回ERR_SERVICENAMEEXISTS。
		IMServiceRateDAO serviceDAO = daoFactory.getIMServiceRateDAO();
		
		JDBCFieldArray whereCols0 = new JDBCFieldArray();
        whereCols0.add("ServiceName", in.ServiceName);
        whereCols0.add("ServiceID", "<>", in.ServiceID);
        if (serviceDAO.isExist(whereCols0) > 0){
            throw new EduException(ErrorCode.ERR_SERVICENAMEEXISTS);
        }
        if(!SysDict.ACTIVE_FLAG_YES.equals(in.Active)){
        	in.Active = SysDict.ACTIVE_FLAG_NO;
        }
        
        IMServiceRate service = new IMServiceRate();
        service.ServiceID = in.ServiceID;
        
        service = serviceDAO.find(service);
        int maxValue = 0;
        if(StringUtils.isNotEmpty(in.ExtraServiceID)){
        	if(service.ExtraServiceID.equals(in.ExtraServiceID)){
        		maxValue = 0;
        	}else if(in.ExtraServiceID.length() == 1){
            	maxValue = 9999999;//7 digits
            }else if(in.ExtraServiceID.length() == 2){
            	maxValue = 999999;//6 digits
            }else{
            	in.ExtraServiceID = "";
            	maxValue = 99999999;
            }
        }
		//5.	调用IMServiceRateDAO. Update()更新服务费用信息。
        JDBCFieldArray setCols = new JDBCFieldArray();
        JDBCFieldArray whereCols = new JDBCFieldArray();
        setCols.add("ServiceName", in.ServiceName);
        if(StringUtils.isNotEmpty(in.ServicePrefix)&& in.ServicePrefix.length() == 2){
        	setCols.add("ServicePrefix", in.ServicePrefix);
        }
        if(in.ServiceAmt>=0){
        	setCols.add("ServiceAmt", in.ServiceAmt);
        }
        if(in.ServiceAmtSmall>=0){
        	setCols.add("ServiceAmtSmall", in.ServiceAmtSmall);
        }
        if(in.ServiceAmtMed>=0){
        	setCols.add("ServiceAmtMed", in.ServiceAmtMed);
        }
        if(in.ServiceAmtBig>=0){
        	setCols.add("ServiceAmtBig", in.ServiceAmtBig);
        }
        if(in.ReturnAmt>=0){
        	setCols.add("ReturnAmt", in.ReturnAmt);
        }
        if(maxValue>=999999){
        	setCols.add("ExtraServiceID", in.ExtraServiceID);
        }
        setCols.add("Active", in.Active);
        setCols.add("Remark", in.Remark);
        whereCols.add("ServiceID", in.ServiceID);

        result = serviceDAO.update(setCols, whereCols);
        if((result == 1)&&(maxValue>=999999)){
        	
        	IMServiceCounterDAO serviceCounterDAO = daoFactory.getIMServiceCounterDAO();
        	JDBCFieldArray whereCols1 = new JDBCFieldArray();
            whereCols1.add("ServiceID", in.ServiceID);
            serviceCounterDAO.delete(whereCols1);
            
            IMServiceCounter counter = new IMServiceCounter();
            counter.ServiceID = service.ServiceID;
            counter.CntValue  = 0;
            counter.CntMaxValue = maxValue;
            counter.CounterType = SysDict.COUNTER_TYPE_SERVICE_MASTER;
            counter.CounterName = "SMCounter";
             
            serviceCounterDAO.insert(counter);
        }
        
		// 调用CommonDAO.addOperatorLog(OperID，功能编号，系统日期时间，“”)
		OPOperatorLog log = new OPOperatorLog();
		log.OperID = in.OperID;
		log.FunctionID = in.getFunctionID();
		log.OccurTime = sysDateTime;
		log.StationAddr = operOnline.LoginIPAddr;
		log.Remark = in.ServiceName;

		commonDAO.addOperatorLog(log);

        return result;
    }
}
