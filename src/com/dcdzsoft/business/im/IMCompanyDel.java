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
 * <p>Description: 服务商删除 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class IMCompanyDel extends ActionBean
{

    public int doBusiness(InParamIMCompanyDel in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        int result = 0;

		//1.	验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(in.OperID)
			||StringUtils.isEmpty(in.CompanyID))
			throw new EduException(ErrorCode.ERR_PARMERR);

		//2.	调用CommonDAO.isOnline(管理员编号)判断管理员是否在线。
		OPOperOnline operOnline = commonDAO.isOnline(in.OperID);

		//3.	调用UtilDAO.getCurrentDateTime()获得系统日期时间。
		java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();

		//默认saudi post 不允许删除
		if(in.CompanyID.equals(Constant.COMPANY_SAUDIPOST_ID)){
			throw new EduException(ErrorCode.ERR_FORBIDDELCOMPANY);
		}
		
		//4.	调用IMZoneDAO.isExist()判断服务商的分拣中心是否存在，如果存在返回ERR_FORBIDDELCOMPANY。
		IMZoneDAO zoneDAO = daoFactory.getIMZoneDAO();
        JDBCFieldArray whereColsCompanyID = new JDBCFieldArray();
        whereColsCompanyID.add("CompanyID", in.CompanyID);
        whereColsCompanyID.add("ZoneID", "!=", in.CompanyID+Constant.ZONE_VIRTUAL_ID);//虚拟分拣区域
        
        if (zoneDAO.isExist(whereColsCompanyID) > 0){
            throw new EduException(ErrorCode.ERR_FORBIDDELCOMPANY);
        }
        
        //服务商管理员
        JDBCFieldArray whereColsZoneID = new JDBCFieldArray();
        whereColsZoneID.add("ZoneID", in.CompanyID+Constant.ZONE_VIRTUAL_ID);//服务商管理员所属虚拟分拣区域
        OPOperatorDAO operatorDAO = daoFactory.getOPOperatorDAO();
        if (operatorDAO.isExist(whereColsZoneID) > 0){
            throw new EduException(ErrorCode.ERR_FORBIDDELCOMPANY);
        }
        //删除虚拟分拣区域
        zoneDAO.delete(whereColsZoneID);
        
        //5.	删除包裹处理中心账户。
        IMPostalProcessingCenterDAO postalProcessingCenterDAO = daoFactory.getIMPostalProcessingCenterDAO();
        whereColsCompanyID = new JDBCFieldArray();
        whereColsCompanyID.add("CompanyID", in.CompanyID);
        postalProcessingCenterDAO.delete(whereColsCompanyID);
        
        //6.	删除格口权限。
        IMCompanyBoxRightDAO companyBoxRightDAO = daoFactory.getIMCompanyBoxRightDAO();
        companyBoxRightDAO.delete(whereColsCompanyID);
        
		//7.	调用IMCompanyDAO.delete()删除服务商信息。
		IMCompanyDAO companyDAO = daoFactory.getIMCompanyDAO();
		IMCompany company = new IMCompany();
		company.CompanyID = in.CompanyID;
		
		company = companyDAO.find(company);
		
		result = companyDAO.delete(company);

		// 调用CommonDAO.addOperatorLog(OperID，功能编号，系统日期时间，“”)
        OPOperatorLog log = new OPOperatorLog();
        log.OperID = in.OperID;
        log.FunctionID = in.getFunctionID();
        log.OccurTime = sysDateTime;
        log.StationAddr = operOnline.LoginIPAddr;
        log.Remark = in.CompanyID;

        commonDAO.addOperatorLog(log);

        return result;
    }
}
