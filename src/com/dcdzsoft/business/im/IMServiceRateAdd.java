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
 * <p>Description: 服务费用增加 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class IMServiceRateAdd extends ActionBean
{

    public int doBusiness(InParamIMServiceRateAdd in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        int result = 0;

		//1.	验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(in.OperID)
			||StringUtils.isEmpty(in.ServiceName)
			||StringUtils.isEmpty(in.ServicePrefix)
			||(in.ServicePrefix.length()!=2)//固定2Bytes
			||(in.ServiceAmtSmall < 0)
			||(in.ServiceAmtMed < 0)
			||(in.ServiceAmtBig < 0)
			||(in.ServiceAmt < 0)
			||(in.ReturnAmt < 0))
			throw new EduException(ErrorCode.ERR_PARMERR);
		
		int maxValue = 999999;
        if(StringUtils.isEmpty(in.ExtraServiceID)){
        	in.ExtraServiceID = "";
        	maxValue = 99999999;//8 digits
        }else if(in.ExtraServiceID.length() == 1){
        	maxValue = 9999999;//7 digits
        }else if(in.ExtraServiceID.length() == 2){
        	maxValue = 999999;//6 digits
        }else{
        	throw new EduException(ErrorCode.ERR_PARMERR);
        }
        
        in.ServicePrefix = in.ServicePrefix.toUpperCase();//默认大写
        
		//2.	调用CommonDAO.isOnline(管理员编号)判断管理员是否在线。
		OPOperOnline operOnline = commonDAO.isOnline(in.OperID);

		//3.	调用UtilDAO.getCurrentDateTime()获得系统日期时间。
		java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();
		java.sql.Date sysDate = DateUtils.toSQLDate(sysDateTime);

		//4.	调用IMServiceRateDAO.isExist()判断服务费用名称是否存在，如果存在返回ERR_SERVICENAMEEXISTS。
		IMServiceRateDAO serviceRateDAO = daoFactory.getIMServiceRateDAO();

        in.ServiceName = StringUtils.normalizeName(in.ServiceName);
        
        JDBCFieldArray whereCols0 = new JDBCFieldArray();
        whereCols0.add("ServiceName", in.ServiceName);
        if (serviceRateDAO.isExist(whereCols0) > 0){
            throw new EduException(ErrorCode.ERR_SERVICENAMEEXISTS);
        }

		//5.	生成服务费用编号。
        IMServiceRate obj = new IMServiceRate();
        
        if(StringUtils.isEmpty(in.ServiceID)){
        	//4位编码
        	String maxID = "";
            int iMaxID = 0;
            
            JDBCFieldArray whereCols1 = new JDBCFieldArray();
            whereCols1.add("ServiceID", "<>","9999");
            maxID = serviceRateDAO.selectFunctions("MAX(ServiceID)", whereCols1);
            
            if (StringUtils.isEmpty(maxID)) {
                obj.ServiceID = "0001";
            } else {
            	iMaxID = NumberUtils.parseInt(maxID) + 1;
                obj.ServiceID = StringUtils.leftPad(String.valueOf(iMaxID), 4, '0');
            }
        	/*obj.ServiceID = RandUtils.generateNumber(4);*/
        }else{
        	obj.ServiceID = in.ServiceID;
        }
        
        if(serviceRateDAO.isExist(obj)){
            throw new EduException(ErrorCode.ERR_SERVICEEXISTS);
        }
        
        if(!SysDict.ACTIVE_FLAG_YES.equals(in.Active)){
        	in.Active = SysDict.ACTIVE_FLAG_NO;
        }
        
		//6.	调用IMServiceRateDAO.insert()插入服务费用信息。
        obj.ServiceName   = in.ServiceName;
        obj.ServiceAmt    = in.ServiceAmt;
        obj.ReturnAmt     = in.ReturnAmt;
        obj.ServicePrefix = in.ServicePrefix;
        obj.ExtraServiceID = in.ExtraServiceID;
        obj.ServiceAmtSmall = in.ServiceAmtSmall;
        obj.ServiceAmtMed   = in.ServiceAmtMed;
        obj.ServiceAmtBig   = in.ServiceAmtBig;
        obj.Active  = in.Active;
        obj.CreateTime   = sysDateTime;
        obj.Remark       = in.Remark;
        
        result = serviceRateDAO.insert(obj);
        if(result==1&&(maxValue>=999999)){
        	//
            IMServiceCounterDAO serviceCounterDAO = daoFactory.getIMServiceCounterDAO();
            IMServiceCounter counter = new IMServiceCounter();
            counter.ServiceID = obj.ServiceID;
            counter.CounterType = SysDict.COUNTER_TYPE_SERVICE_MASTER;
            counter.CntValue  = 0;
            counter.CntMaxValue = maxValue;
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
