package com.dcdzsoft.business.ap;

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
import com.dcdzsoft.config.ErrorMsgConfig;
import com.dcdzsoft.constant.*;
import com.dcdzsoft.business.ActionBean;

/**
 * <p>Title: 自提柜后台运营系统</p>
 * <p>Description: 个人信息更新 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class APCustomerUpdate extends ActionBean
{

	/**
	 * 用户更新虚拟POBox地址
	 * @param p1
	 *  CustomerMobile 个人客户手机  (必选，不可更改)
	 *  Keyword      验证码     (必选)
	 *  Months       使用时间
	 *  LockerID     柜号（保留）
	 *  CustomerName  姓名（保留）
	 *  CustomerEmail 电子邮箱（保留）
	 *  
	 * @return
	 *  Status       0-失败；1-成功
	 *  Address	     个人客户虚拟POBox地址
	 *  Validity	 有效期（yyyy-MM-dd）
	 * @throws com.dcdzsoft.EduException
	 */
    public OutParamAPCustomerUpdate doBusiness(InParamAPCustomerUpdate in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        OutParamAPCustomerUpdate out = new OutParamAPCustomerUpdate();

		//1.验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(in.CustomerMobile) ||
				StringUtils.isEmpty(in.Keyword))
			throw new EduException(ErrorCode.ERR_PARMERR);

		//2.调用CommonDAO.isOnline(操作员编号)判断操作员是否在线。
		//OPOperOnline operOnline = commonDAO.isOnline(in.OperID);

		//3.调用UtilDAO.getCurrentDateTime()获得系统日期时间。
		java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();
		java.sql.Date sysDate = DateUtils.toSQLDate(sysDateTime);
		
		/*if(!commonDAO.isPhoneNumber(in.CustomerMobile)){
			throw new EduException(ErrorCode.ERR_BUSINESS_MOBILEFORMATERROR);
		}*/
		/////个人客户
		IMCustomerDAO customerDAO = daoFactory.getIMCustomerDAO();
		String customerID = "";
		JDBCFieldArray whereCols0 = new JDBCFieldArray();
		whereCols0.add("Mobile", in.CustomerMobile);
		if(customerDAO.isExist(whereCols0) > 0){
			RowSet rset = customerDAO.select(whereCols0);
			if (RowSetUtils.rowsetNext(rset)) {//同一个手机只能有一条记录
				customerID = RowSetUtils.getStringValue(rset, "CustomerID");
			}
		}else{
			JDBCFieldArray whereCols = new JDBCFieldArray();
			whereCols.add("CustomerID", in.CustomerMobile);
			if(customerDAO.isExist(whereCols) > 0){
				customerID = in.CustomerMobile;
			}else{
				//out.Status = 0;//更新失败
				//out.POBoxAddress = "Mobile number is not registered!";//手机不存在，请注册
				return null;
			}
		}
		
		IMCustomer customer = new IMCustomer();
		customer.CustomerID = customerID;
		customer = customerDAO.find(customer);
		if(!customer.ActiveCode.equals(in.Keyword)){
			out.Status = 0;//更新失败
			out.POBoxAddress = "Verification code error！";//+in.Keyword
			//验证码只能使用一次
			String ActiveCode = RandUtils.generateNumber(4);//4位验证码
			JDBCFieldArray setCols = new JDBCFieldArray();
			setCols.add("ActiveCode", ActiveCode);
			
			JDBCFieldArray whereCols = new JDBCFieldArray();
			whereCols.add("CustomerID", customer.CustomerID);//CustomerMobile唯一
			customerDAO.update(setCols, whereCols);
			
			throw new EduException(ErrorCode.ERR_MOBILE_VCODE_INVALID);
		}
		
		//更新用户地址
		JDBCFieldArray setCols = new JDBCFieldArray();
        JDBCFieldArray whereCols2 = new JDBCFieldArray();
        switch(customer.Status){
        case "1"://1-正常,更改柜体
        	if(!in.LockerID.equals(customer.TerminalNo)){
        		//#start 修改柜体绑定的柜体
				//customer.CustomerName = in.CustomerName;
				customer.Email        = in.CustomerEmail;
				in.Months             = 0;//更改柜体，不修改有效期
				customer = commonDAO.activeOrUpdatePOBoxAddress(customer, in.LockerID, in.Months);
				//#end
        	}else{
                setCols.add("ActiveCode", RandUtils.generateNumber(4));//4位验证码
                setCols.checkAdd("Email", in.CustomerEmail);
                //setCols.checkAdd("CustomerName", in.CustomerName);
                setCols.add("LastModifyTime", sysDateTime);
                
                whereCols2.add("CustomerID", customer.CustomerID);
                customerDAO.update(setCols, whereCols2);
        	}
        	out.Status = 1;
        	out.POBoxAddress = customer.Address;
        	out.Validity = DateUtils.dateToString(new java.sql.Date(customer.Validity.getTime()));
        	break;
        case "2"://2-失效	，重新获得试用期限
        	//#start 重新获取POBox使用期限
			//customer.CustomerName = in.CustomerName;
			customer.Email        = in.CustomerEmail;
			if(in.Months < 1){//延期至少一个月
				throw new EduException(ErrorCode.ERR_PARMERR);
			}
			customer = commonDAO.activeOrUpdatePOBoxAddress(customer, in.LockerID, in.Months);
			//#end
			
        	out.Status = 1;
        	out.Validity = DateUtils.dateToString(new java.sql.Date(customer.Validity.getTime()));
        	out.POBoxAddress = customer.Address;
        	break;
        default:
        	out.Status = 0;
        	out.Validity = DateUtils.dateToString(new java.sql.Date(customer.Validity.getTime()));
			out.POBoxAddress = SMSManager.getInstance().sendReminderActiveSMS(0, customer.Mobile,customer.CustomerID, out.Validity);
        	break;
        }
        
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
