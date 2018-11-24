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
 * <p>Description: 个人客户信息修改 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class IMCustomerMod extends ActionBean
{

    public int doBusiness(InParamIMCustomerMod in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        int result = 0;

		//1.	验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(in.CustomerID))
			throw new EduException(ErrorCode.ERR_PARMERR);

		if(StringUtils.isNotEmpty(in.Email)){
        	if(!commonDAO.isEmail(in.Email))
        		throw new EduException(ErrorCode.ERR_BUSINESS_EMAILFORMATERROR);
        }
		
		//2.	调用CommonDAO.isOnline(管理员编号)判断管理员是否在线。
		OPOperOnline operOnline = null;
		if(StringUtils.isNotEmpty(in.OperID)){
			operOnline = commonDAO.isOnline(in.OperID);
		}

		//3.	调用UtilDAO.getCurrentDateTime()获得系统日期时间。
		java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();
		java.sql.Date sysDate = DateUtils.toSQLDate(sysDateTime);

		//4.	调用IMCustomerDAO. Update()更新个人客户信息。
		IMCustomerDAO customerDAO = daoFactory.getIMCustomerDAO();
		JDBCFieldArray whereCols = new JDBCFieldArray();
        whereCols.add("CustomerID", in.CustomerID);
        if(customerDAO.isExist(whereCols) <= 0){
        	throw new EduException(ErrorCode.ERR_CUSTOMERNOTEXISTS);
        }
        	
		//#start用户手机号检查
		if(StringUtils.isNotEmpty(in.Mobile)){
			if(!commonDAO.isPhoneNumber(in.Mobile))
	    		throw new EduException(ErrorCode.ERR_BUSINESS_MOBILEFORMATERROR);
			
			JDBCFieldArray whereCols1 = new JDBCFieldArray();
	        whereCols1.add("Mobile",in.Mobile);
	        whereCols1.add("CustomerID", "<>", in.CustomerID);
	        if(customerDAO.isExist(whereCols1) > 0)
	            throw new EduException(ErrorCode.ERR_CUSTOMER_EXISTSBINDMOBILE);
		}
		//#end
        
        //#start用户绑定卡检查（身份证） 
        if(StringUtils.isNotEmpty(in.IDCard) 
        		&& "2".equals(ControlParam.getInstance().getCustomerPOBoxBandBode()))
        {
        	JDBCFieldArray whereCols0 = new JDBCFieldArray();
            whereCols0.add("IDCard",in.IDCard);
            whereCols0.add("CustomerID", "<>", in.CustomerID);
            if(customerDAO.isExist(whereCols0) > 0)
                throw new EduException(ErrorCode.ERR_CUSTOMER_EXISTSBINDINGCARD);
        }
        //#end
        
		in.CustomerName = StringUtils.normalizeName(in.CustomerName);
		if(in.Months<0){
			in.Months = 0;
		}
		if(!"1".equals(in.Active)){
			in.Active = "0";
		}
		
		//更新信息
		JDBCFieldArray setCols0 = new JDBCFieldArray();
        JDBCFieldArray whereCols0 = new JDBCFieldArray();
        if(in.ValidityDate!=null){
			if(sysDate.after(in.ValidityDate)){//已逾期
				setCols0.add("Status", SysDict.CUSTOMER_STATUS_INVALID);
				in.Active = "0";
			}
	        setCols0.add("Validity", new java.sql.Timestamp(in.ValidityDate.getTime()));
		}
        setCols0.add("Active", in.Active);
        setCols0.add("ActiveCode", RandUtils.generateNumber(4));
		setCols0.checkAdd("CustomerName", in.CustomerName);
		setCols0.checkAdd("Email", in.Email);
		setCols0.checkAdd("IDCard", in.IDCard);
		setCols0.checkAdd("keepMonths", in.Months);
        setCols0.add("LastModifyTime", sysDateTime);
        setCols0.checkAdd("Remark", in.Remark);
        
        whereCols0.add("CustomerID", in.CustomerID);
        customerDAO.update(setCols0, whereCols0);
        
        //状态更新
		IMCustomer customer = new IMCustomer();//客户对象
		customer.CustomerID = in.CustomerID;
		customer = customerDAO.find(customer);
		switch(customer.Status){
		case SysDict.CUSTOMER_STATUS_INACTIVE://0-未激活，
			/**
			 * customer未激活:
			 * 激活：生成客户编号和POBox地址
			 */
			if("1".equals(in.Active)){//激活
				if(in.Months == 0){
					in.Months = customer.keepMonths;
				}
				customer = commonDAO.activeOrUpdatePOBoxAddress(customer, in.TerminalNo, in.Months);
			}
			break;
		case SysDict.CUSTOMER_STATUS_NORMAL://1-正常，
			/**
			 * customer已激活
			 * 1)修改绑定柜体； 2)人工过期，修改有效期及状态；
			 */
			if("1".equals(in.Active)){//修改绑定柜体；
				//in.Months = 0;//不修改有效期
				customer = commonDAO.activeOrUpdatePOBoxAddress(customer, in.TerminalNo, in.Months);
			}else{//人工过期
				JDBCFieldArray setCols1 = new JDBCFieldArray();
		        JDBCFieldArray whereCols1 = new JDBCFieldArray();
		        setCols1.add("Status", SysDict.CUSTOMER_STATUS_INVALID);//2-过期
		        setCols1.add("Active", customer.Active);
		        
		        whereCols1.add("CustomerID", in.CustomerID);
		        customerDAO.update(setCols1, whereCols1);
			}
			break;
		case SysDict.CUSTOMER_STATUS_INVALID://2-过期
			/**
			 * customer过期
			 * 1)过期，延长期限；
			 */
			if("1".equals(in.Active)){
				if(sysDateTime.after(customer.Validity)){//已逾期，需要输入延期月数
					if(in.Months < 1){//延期至少一个月
						in.Months = 1;
					}
				}else{
					if(in.Months < 1){//还未逾期，可以重新激活
						in.Months = 0;//不延长延期
					}
				}
				customer = commonDAO.activeOrUpdatePOBoxAddress(customer, in.TerminalNo, in.Months);
			}
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
