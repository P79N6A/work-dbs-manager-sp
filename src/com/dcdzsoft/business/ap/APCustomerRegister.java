package com.dcdzsoft.business.ap;

import javax.sql.RowSet;

import com.dcdzsoft.EduException;
import com.dcdzsoft.sda.db.*;
import com.dcdzsoft.sms.SMSManager;
import com.dcdzsoft.util.*;
import com.dcdzsoft.dto.function.*;
import com.dcdzsoft.dto.business.*;
import com.dcdzsoft.dao.*;
import com.dcdzsoft.dao.common.*;
import com.dcdzsoft.config.ErrorMsgConfig;
import com.dcdzsoft.constant.*;
import com.dcdzsoft.business.ActionBean;

/**
 * <p>Title: 自提柜后台运营系统</p>
 * <p>Description: 个人信息注册 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class APCustomerRegister extends ActionBean
{

    public OutParamAPCustomerRegister doBusiness(InParamAPCustomerRegister in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        OutParamAPCustomerRegister out = new OutParamAPCustomerRegister();

		//1.验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(in.CustomerMobile)
			||StringUtils.isEmpty(in.LockerID))
			throw new EduException(ErrorCode.ERR_PARMERR);

		//2.调用CommonDAO.isOnline(操作员编号)判断操作员是否在线。
		//OPOperOnline operOnline = commonDAO.isOnline(in.OperID);

		//3.调用UtilDAO.getCurrentDateTime()获得系统日期时间。
		java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();
		java.sql.Date sysDate = DateUtils.toSQLDate(sysDateTime);
		
		if(!commonDAO.isPhoneNumber(in.CustomerMobile))
    		throw new EduException(ErrorCode.ERR_BUSINESS_MOBILEFORMATERROR);
		
		//检查柜体是否允许注册
		TBTerminalDAO terminalDAO = daoFactory.getTBTerminalDAO();
		TBTerminal terminal = new TBTerminal();
		terminal.TerminalNo = in.LockerID;
		try{
			terminal = terminalDAO.find(terminal);
			if(!"1".equals(terminal.AppRegisterFlag)){
				//out.Status = 0;
				//out.Message = ErrorMsgConfig.getLocalizedMessage(ErrorCode.ERR_MOBILE_NOTALLOW);
				//return out;
				throw new EduException(ErrorCode.ERR_MOBILE_NOTALLOW);
			}
		}catch(EduException e){
			//out.Status = 0;
			//out.Message = ErrorMsgConfig.getLocalizedMessage(ErrorCode.ERR_MOBILE_NOTALLOW);
			//return out;
			throw new EduException(ErrorCode.ERR_MOBILE_NOTALLOW);
		}
				
		/////个人客户编号
		IMCustomerDAO customerDAO = daoFactory.getIMCustomerDAO();
		JDBCFieldArray whereCols = new JDBCFieldArray();
		whereCols.add("Mobile", in.CustomerMobile);
		if(customerDAO.isExist(whereCols) > 0){
			String customerID = "";
			RowSet rset = customerDAO.select(whereCols);
			if (RowSetUtils.rowsetNext(rset)) {//同一个手机只能有一条记录
				customerID = RowSetUtils.getStringValue(rset, "CustomerID");
			}
			IMCustomer customer = new IMCustomer();
			customer.CustomerID = customerID;
			customer = customerDAO.find(customer);
			
			java.sql.Date validityDate = new java.sql.Date(customer.Validity.getTime());
			switch(customer.Status){
			case SysDict.CUSTOMER_STATUS_INACTIVE://0-未激活
				out.Status = 1;
				out.Message = SMSManager.getInstance().sendReminderActiveSMS(0, in.CustomerMobile,customer.CustomerID, validityDate.toString());
				return out;
			case SysDict.CUSTOMER_STATUS_NORMAL://1-正常
				out.Status = 1;
				out.Message = ""+customer.Address;
				return out;
			case SysDict.CUSTOMER_STATUS_INVALID://2-失效
				out.Status = 1;
				out.Message = SMSManager.getInstance().sendReminderExtendSMS(0, in.CustomerMobile, customer.CustomerID, validityDate.toString());
				return out;
			default:
				out.Status = 1;
				out.Message = "";
				return out;
			}
		}
		if(in.Months <= 0){
			in.Months = 1;
		}
		
		//未注册，或超过更新期限，用户信息已删除
		IMCustomer customer = new IMCustomer();
		customer.CustomerID   = in.CustomerMobile;//未激活状态，用户手机作为用户ID
		customer.CustomerName = in.CustomerName;
		customer.Email        = in.CustomerEmail;
		customer.IDCard       = in.CustomerIDCard;
		customer.TerminalNo   = in.LockerID;
		customer.ActiveCode   = "0000";//初始激活码，用于测试
		customer.Active       = "0";
		customer.Status       = SysDict.CUSTOMER_STATUS_INACTIVE;//用户状态：0-未激活，1-正常，2-失效
		customer.Validity     = DateUtils.addTimeStamp(sysDateTime, terminal.AppRegisterLimit);
		customer.keepMonths   = in.Months;
		customer.Mobile       = in.CustomerMobile;
		customer.CreateTime   = sysDateTime;
		customerDAO.insert(customer);
		java.sql.Date validityDate = DateUtils.addDate(sysDate,  terminal.AppRegisterLimit);
		
		
		out.Status = 1;//1-手机号已注册
		//发送通知激活短信
		out.Message = SMSManager.getInstance().sendReminderActiveSMS(0, in.CustomerMobile,customer.CustomerID, validityDate.toString());//in.CustomerMobile+"手机号已注册，请到附近的邮局激活！";
		
		// 调用CommonDAO.addOperatorLog(OperID，功能编号，系统日期时间，“”)
		OPOperatorLog log = new OPOperatorLog();
		log.OperID = in.CustomerMobile;
		log.FunctionID = in.getFunctionID();
		log.OccurTime = sysDateTime;
		log.StationAddr = "";
		log.Remark = "";

		commonDAO.addOperatorLog(log);

        return out;
    }
}
