package com.dcdzsoft.business.sc;

import javax.sql.RowSet;

import com.dcdzsoft.EduException;
import com.dcdzsoft.sda.db.*;
import com.dcdzsoft.util.*;
import com.dcdzsoft.dto.function.*;
import com.dcdzsoft.dto.business.*;
import com.dcdzsoft.dao.*;
import com.dcdzsoft.dao.common.*;
//import com.dcdzsoft.guotong.GuotongManager;
import com.dcdzsoft.config.ApplicationConfig;
import com.dcdzsoft.constant.*;
import com.dcdzsoft.business.ActionBean;
import com.dcdzsoft.businessproxy.BusinessProxy;

/**
 * <p>Title: 智能自助包裹柜系统</p>
 * <p>Description: 人工同步柜体信息 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author 王中立
 * @version 1.0
 */

public class SCSyncTerminalInfo extends ActionBean
{
    public int doBusiness(InParamSCSyncTerminalInfo in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        int result = 0;

		//1.	验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(in.OperID)
			||StringUtils.isEmpty(in.TerminalNo))
			throw new EduException(ErrorCode.ERR_PARMERR);


		//2.	调用CommonDAO.isOnline(操作员编号)判断操作员是否在线。
		OPOperOnline operOnline = commonDAO.isOnline(in.OperID);

		//3.	调用UtilDAO.getCurrentDateTime()获得系统日期时间。
		java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();
		
		// 调用CommonDAO.addOperatorLog(OperID，功能编号，系统日期时间，“”)
		OPOperatorLog log = new OPOperatorLog();
		log.OperID = in.OperID;
		log.FunctionID = in.getFunctionID();
		log.OccurTime = sysDateTime;
		log.StationAddr = operOnline.LoginIPAddr;
		log.Remark = "";

		commonDAO.addOperatorLog(log);
		
		{
			String PROXY_PACKAGE_PREFIX = "com.dcdzsoft.businessproxy.";
			String PROXY_CLASS_NAME = PROXY_PACKAGE_PREFIX + ApplicationConfig.getInstance().getInterfacePackage();
			try
			{
				Class proxyClass = Class.forName(PROXY_CLASS_NAME);
				BusinessProxy businessProxy = (BusinessProxy)proxyClass.newInstance();
				
				businessProxy.doBusiness(in, in.TerminalNo);
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}

        return result;
    }
}
