package com.dcdzsoft.business.ap;

import javax.sql.RowSet;

import com.dcdzsoft.EduException;
import com.dcdzsoft.sda.db.*;
import com.dcdzsoft.sda.security.SecurityUtils;
import com.dcdzsoft.sms.SMSManager;
import com.dcdzsoft.util.*;
import com.dcdzsoft.dto.function.*;
import com.dcdzsoft.dto.business.*;
import com.dcdzsoft.dao.*;
import com.dcdzsoft.dao.common.*;
import com.dcdzsoft.constant.*;
import com.dcdzsoft.business.ActionBean;

/**
 * <p>Title: 自提柜后台运营系统</p>
 * <p>Description: 获取验证码短信 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class APCustomerGetKeyMsg extends ActionBean
{

    public OutParamAPCustomerGetKeyMsg doBusiness(InParamAPCustomerGetKeyMsg in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        OutParamAPCustomerGetKeyMsg out = new OutParamAPCustomerGetKeyMsg();

		//1.验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(in.CustomerMobile))
			throw new EduException(ErrorCode.ERR_PARMERR);

		if(!commonDAO.isPhoneNumber(in.CustomerMobile)){
			return null;
		}
		//2.调用CommonDAO.isOnline(操作员编号)判断操作员是否在线。
		//OPOperOnline operOnline = commonDAO.isOnline(in.OperID);

		String ActiveCode = RandUtils.generateNumber(4);//4位验证码
		IMCustomerDAO customerDAO = daoFactory.getIMCustomerDAO();
		JDBCFieldArray whereCols = new JDBCFieldArray();
		JDBCFieldArray setCols = new JDBCFieldArray();
		setCols.add("ActiveCode", ActiveCode);
		whereCols.add("Mobile", in.CustomerMobile);//CustomerMobile唯一
		
		customerDAO.update(setCols, whereCols);
		
		//发送验证码短信
		SMSManager.getInstance().sentUpdateVerificationCodeSMS(ActiveCode, in.CustomerMobile);
		out.Status = 1;
		
		//3.调用UtilDAO.getCurrentDateTime()获得系统日期时间。
		java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();
		java.sql.Date sysDate = DateUtils.toSQLDate(sysDateTime);
		
		// 调用CommonDAO.addOperatorLog(OperID，功能编号，系统日期时间，“”)
		OPOperatorLog log = new OPOperatorLog();
		log.OperID = in.CustomerMobile;
		log.FunctionID = in.getFunctionID();
		log.OccurTime = sysDateTime;
		log.StationAddr = "";
		log.Remark = ActiveCode;

		commonDAO.addOperatorLog(log);

        return out;
    }
}
