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
 * <p>Description: 服务商查询数量 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class IMCompBoxRightQryCount extends ActionBean
{

    public int doBusiness(InParamIMCompBoxRightQryCount in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        int result = 0;

		//1.	验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(in.OperID))
			throw new EduException(ErrorCode.ERR_PARMERR);


		//2.	调用CommonDAO.isOnline(管理员编号)判断管理员是否在线。
		OPOperOnline operOnline = commonDAO.isOnline(in.OperID);

		//检测CompanyID是否存在
		IMCompanyDAO companyDAO = daoFactory.getIMCompanyDAO();
		IMCompany company = new IMCompany();
		company.CompanyID = in.CompanyID;
		if(!companyDAO.isExist(company)){
			throw new EduException(ErrorCode.ERR_COMPANYNOTEXISTS);
		}
		
		String wheresql = " WHERE 1=1 ";
		if(StringUtils.isNotEmpty(in.TerminalNo)){
			wheresql += " AND TerminalNo="+StringUtils.addQuote(in.TerminalNo);
		}
		
		String selectsql = "SELECT COUNT(BoxNo) FROM V_TBBox a ";
		
		String limitsql = "";
		if("1".equals(in.QryType)){//1-查询可选箱
			//查询可选箱，需要Master权限
			OPOperSpeRightDAO operSpeRightDAO = daoFactory.getOPOperSpeRightDAO();
	        OPOperSpeRight operSpeRight = new OPOperSpeRight();
	        operSpeRight.OperID = operOnline.OperID;
	        operSpeRight.SpePrivID = SysDict.SPECIAL_ACCESS_DATA_QUERY_MASTER;
            if(operSpeRightDAO.isExist(operSpeRight)){
            	limitsql = " AND NOT EXISTS(SELECT BoxNo From IMCompanyBoxRight b WHERE b.CompanyID="+StringUtils.addQuote(in.CompanyID)
    					+ " AND b.TerminalNo=a.TerminalNo AND b.BoxNo=a.BoxNo"
    					+ ") ";
            }else{
            	limitsql = " AND 1=2";
            }
			
		}else{
			//查询已选箱
			limitsql = " AND EXISTS(SELECT BoxNo From IMCompanyBoxRight b WHERE b.CompanyID="+StringUtils.addQuote(in.CompanyID)
					+ " AND b.TerminalNo=a.TerminalNo AND b.BoxNo=a.BoxNo"
					+ ") ";
		}
		//wheresql在limitsql之前
		String sql = selectsql +wheresql+limitsql;
		
		result = dbSession.executeQueryCount(sql);
		
        return result;
    }
}
