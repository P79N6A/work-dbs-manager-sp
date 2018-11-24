package com.dcdzsoft.business.im;

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
 * <p>Description: 自动更新个人客户状态 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class IMCustomerModStatusAuto extends ActionBean
{

	/**
	 * 
	 * @param in
	 *  Mode:业务模式（1-Customer过期，其他-删除过期Customer）
	 * @return
	 * @throws EduException
	 */
    public int doBusiness(InParamIMCustomerModStatusAuto in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        int result = 0;

		//1.验证输入参数是否有效，如果无效返回-1。
		//if (StringUtils.isEmpty(in.OperID))
		//	throw new EduException(ErrorCode.ERR_PARMERR);


		//2.调用CommonDAO.isOnline(管理员编号)判断管理员是否在线。
		//OPOperOnline operOnline = commonDAO.isOnline(in.OperID);

		//3.调用UtilDAO.getCurrentDateTime()获得系统日期时间。
		java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();
		java.sql.Date sysDate = DateUtils.toSQLDate(sysDateTime);
		
		IMCustomerDAO customerDAO = daoFactory.getIMCustomerDAO();
		JDBCFieldArray whereCols = new JDBCFieldArray();
		JDBCFieldArray setCols = new JDBCFieldArray();
		if("1".equals(in.Mode)){
			//1-Customer过期,
			if(StringUtils.isEmpty(in.CustomerID)){
				return 0;
			}
			IMCustomer customer = new IMCustomer();
			customer.CustomerID = in.CustomerID;
			try{
				customer = customerDAO.find(customer);
			}catch(EduException e){
				return 0;
			}
			
			if(sysDateTime.before(customer.Validity)){//
				return 0;
			}
			if(!"1".equals(customer.Status)){
				return 0;
			}
			int days = ControlParam.getInstance().getCustomerExtendLimitDays();
			
			customer.Validity = DateUtils.addTimeStamp(sysDateTime, days);//有效期30天
			setCols.add("Status", SysDict.CUSTOMER_STATUS_INVALID);//无效
	        setCols.add("Active", "0");//Active=0，
	       
	        setCols.add("Validity", customer.Validity);
	        setCols.add("ActiveCode", RandUtils.generateNumber(4));
	        setCols.add("LastModifyTime", sysDateTime);
	        
			whereCols.add("CustomerID", in.CustomerID);
			
			customerDAO.update(setCols, whereCols);
			
			//发送提醒延期短信
			java.sql.Date validityDate = new java.sql.Date(customer.Validity.getTime());
			if(commonDAO.isPhoneNumber(customer.Mobile)){
				SMSManager.getInstance().sendReminderExtendSMS(0, customer.Mobile, customer.CustomerID, validityDate.toString());
			}
			
		}else{
			int days = ControlParam.getInstance().getCustomerExpiredKeepDays();
			
			//whereCols.add("Status", SysDict.CUSTOMER_STATUS_INVALID);
			whereCols.addSQL(" AND Status<>"+ StringUtils.addQuote(SysDict.CUSTOMER_STATUS_NORMAL));
			whereCols.addSQL(" AND Validity<"+ StringUtils.addQuote(DateUtils.addTimeStamp(sysDateTime, -days)));//保留30天
			whereCols.checkAdd("CustomerID", in.CustomerID);
			int count = customerDAO.delete(whereCols);
			if(count<1){
				return 0;
			}
		}
		// 调用CommonDAO.addOperatorLog(OperID，功能编号，系统日期时间，“”)
		OPOperatorLog log = new OPOperatorLog();
		log.OperID = in.OperID;
		log.FunctionID = in.getFunctionID();
		log.OccurTime = sysDateTime;
		log.StationAddr = "";
		log.Remark = "Elocker system Auto";

		commonDAO.addOperatorLog(log);

        return result;
    }
}
