package com.dcdzsoft.business.im;

import java.util.Calendar;

import javax.sql.RowSet;

import com.dcdzsoft.EduException;
import com.dcdzsoft.sda.db.*;
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
 * <p>Description: 个人客户信息增加 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class IMCustomerAdd extends ActionBean
{
	/**
	 * @param in  
	 * @return
	 * @throws EduException
	 */
    public int doBusiness(InParamIMCustomerAdd in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        int result = 0;

		//1.	验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(in.CustomerName)
			||StringUtils.isEmpty(in.Mobile)
			||StringUtils.isEmpty(in.TerminalNo))
			throw new EduException(ErrorCode.ERR_PARMERR);

		//2.	调用CommonDAO.isOnline(管理员编号)判断管理员是否在线。
		OPOperOnline operOnline = null;
		if(StringUtils.isNotEmpty(in.OperID)){
			operOnline = commonDAO.isOnline(in.OperID);
		}

		if(StringUtils.isNotEmpty(in.Email)){
        	if(!commonDAO.isEmail(in.Email))
        		throw new EduException(ErrorCode.ERR_BUSINESS_EMAILFORMATERROR);
        }
		
		//3.	调用UtilDAO.getCurrentDateTime()获得系统日期时间。
		java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();
		java.sql.Date sysDate = DateUtils.toSQLDate(sysDateTime);

		IMCustomerDAO customerDAO = daoFactory.getIMCustomerDAO();
		
		//#start用户手机号检查
		if(!commonDAO.isPhoneNumber(in.Mobile))
    		throw new EduException(ErrorCode.ERR_BUSINESS_MOBILEFORMATERROR);
    	
		
		JDBCFieldArray whereCols1 = new JDBCFieldArray();
        whereCols1.add("Mobile",in.Mobile);

        if(customerDAO.isExist(whereCols1) > 0)
            throw new EduException(ErrorCode.ERR_CUSTOMER_EXISTSBINDMOBILE);
		//#end
        
        //#start用户绑定卡检查（身份证）
        if("2".equals(ControlParam.getInstance().getCustomerPOBoxBandBode()))//2-一个身份证号一个POBox地址
        {
        	JDBCFieldArray whereCols0 = new JDBCFieldArray();
            whereCols0.add("IDCard",in.IDCard);

            if(customerDAO.isExist(whereCols0) > 0)
                throw new EduException(ErrorCode.ERR_CUSTOMER_EXISTSBINDINGCARD);
        }
        //#end
        
        if(in.Months<1){
        	in.Months = 1;
        }
        if(!"1".equals(in.Active)){
        	in.Active  = "0";
        }
        
		IMCustomer customer = new IMCustomer();//客户对象
		//#start 创建但未激活，设置有效期
		if(StringUtils.isNotEmpty(in.CustomerID)){
			//外部系统添加
			customer.CustomerID = in.CustomerID;
			customer.Validity   = sysDateTime;
			
		}else{
			customer.CustomerID = in.Mobile;//未激活，使用手机号作为客户编号
			customer.Validity   = DateUtils.addTimeStamp(sysDateTime, ControlParam.getInstance().getCustomerExtendLimitDays());
		}
		if(customerDAO.isExist(customer)){
            throw new EduException(ErrorCode.ERR_CUSTOMEREXISTS);
        }
		
		customer.Status       = SysDict.CUSTOMER_STATUS_INACTIVE;//注册未激活
    	customer.Address      = "";
    	in.CustomerName       = StringUtils.normalizeName(in.CustomerName);
        customer.CustomerName = in.CustomerName;
        customer.TerminalNo   = in.TerminalNo;
        customer.Mobile       = in.Mobile;
        customer.Email        = in.Email;
        customer.Active       = in.Active;
        customer.IDCard       = in.IDCard;
        customer.keepMonths   = in.Months;//注册使用的月数
        customer.CreateTime   = sysDateTime;
        customer.LastModifyTime = sysDateTime;
        customer.Remark       = in.Remark;
        customer.ActiveCode   = RandUtils.generateNumber(4);//更新验证码
        customerDAO.insert(customer);
    	//#end
    	
		//#start 检查是否需要付费
		if("2".equals(ControlParam.getInstance().getCustomerPOBoxRateFree())){
			int rate = ControlParam.getInstance().getPoBoxMonthlyRate();
			if(rate > 0 && "1".equals(in.Active)){
				in.Active = "0";//如果需要付费，需要Customer完成付费后才能激活
			}
		}
		//#end
		
        switch(in.Active){
        case "1":
        	//不需付费，直接激活
        	customer = commonDAO.activeOrUpdatePOBoxAddress(customer, in.TerminalNo, in.Months);
        	break;
        default:
        	//短信通知用户激活
        	java.sql.Date validityDate = new java.sql.Date(customer.Validity.getTime());
        	SMSManager.getInstance().sendReminderActiveSMS(1,customer.Mobile,customer.CustomerID, validityDate.toString());
        	break;
        }
        
        customer = customerDAO.find(customer);
        
        switch(customer.Status){
        case SysDict.CUSTOMER_STATUS_INACTIVE:
        	result = 0;//0-Inactive;
        	break;
        case SysDict.CUSTOMER_STATUS_NORMAL:
        	result = 1;//1-Normal;
        	break;
        default :
        	result = 2;//2-Invalid
        	break;
        }
        // 调用CommonDAO.addOperatorLog(OperID，功能编号，系统日期时间，“”)
        OPOperatorLog log = new OPOperatorLog();
        log.OperID = in.OperID;
        log.FunctionID = in.getFunctionID();
        log.OccurTime = sysDateTime;
        log.StationAddr = "";
        log.Remark = in.CustomerName;

        commonDAO.addOperatorLog(log);

        return result;
    }
}
