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
 * <p>Description: 个人客户信息删除 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class IMCustomerDel extends ActionBean
{

    public int doBusiness(InParamIMCustomerDel in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        int result = 0;

		//1.	验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(in.OperID)
			||StringUtils.isEmpty(in.CustomerID))
			throw new EduException(ErrorCode.ERR_PARMERR);

		//2.	调用CommonDAO.isOnline(管理员编号)判断管理员是否在线。
		OPOperOnline operOnline = commonDAO.isOnline(in.OperID);

		//3.	调用UtilDAO.getCurrentDateTime()获得系统日期时间。
		java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();
		java.sql.Date sysDate = DateUtils.toSQLDate(sysDateTime);

		//4.	调用IMCustomerDAO.delete()删除个人客户信息。
		IMCustomerDAO customerDAO = daoFactory.getIMCustomerDAO();
		IMCustomer customer = new IMCustomer();
		customer.CustomerID = in.CustomerID;
		
		customer = customerDAO.find(customer);
		
		//#start 表关联检查
        JDBCFieldArray whereCols0 = new JDBCFieldArray();
        whereCols0.add("CustomerID",in.CustomerID);
        //待投递表
        PTReadyPackageDAO readyPackDAO = daoFactory.getPTReadyPackageDAO();
        if(readyPackDAO.isExist(whereCols0) > 0){
            throw new EduException(ErrorCode.ERR_FORBIDDELETE);
        }
        //#end
        
		result = customerDAO.delete(customer);

        // 调用CommonDAO.addOperatorLog(OperID，功能编号，系统日期时间，“”)
        OPOperatorLog log = new OPOperatorLog();
        log.OperID = in.OperID;
        log.FunctionID = in.getFunctionID();
        log.OccurTime = sysDateTime;
        log.StationAddr = operOnline.LoginIPAddr;
        log.Remark = in.CustomerID;

        commonDAO.addOperatorLog(log);

        return result;
    }
}
