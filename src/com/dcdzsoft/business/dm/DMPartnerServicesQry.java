package com.dcdzsoft.business.dm;

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
 * <p>Description: 商业伙伴可选服务查询 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class DMPartnerServicesQry extends ActionBean
{

    public java.util.List<OutParamDMPartnerServicesQry> doBusiness(InParamDMPartnerServicesQry in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        java.util.List<OutParamDMPartnerServicesQry> outList = new java.util.LinkedList<OutParamDMPartnerServicesQry>();

        

		//1.验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(in.BPartnerID))
			throw new EduException(ErrorCode.ERR_PARMERR);

		//3.调用UtilDAO.getCurrentDateTime()获得系统日期时间。
		java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();
		java.sql.Date sysDate = DateUtils.toSQLDate(sysDateTime);

		
        String limitsql = "";//commonDAO.getQueryCompanyLimitSQL(operOnline.OperID, operOnline.ZoneID);
		
		PreparedWhereExpression whereSQL = new PreparedWhereExpression();
        whereSQL.add("BPartnerID", in.BPartnerID); 
        
        String sql = "SELECT * FROM V_IMBPartnerServiceRight WHERE 1=1 " + whereSQL.getPreparedWhereSQL() + limitsql;
        
        RowSet rset = dbSession.executeQuery(sql,whereSQL);
        while (RowSetUtils.rowsetNext(rset)) {
        	OutParamDMPartnerServicesQry out = new OutParamDMPartnerServicesQry();
        	out.ServiceID = RowSetUtils.getStringValue(rset, "ServiceID");
        	out.ServiceName = RowSetUtils.getStringValue(rset, "ServiceName");
        	out.ServiceAmtSmall = RowSetUtils.getDoubleValue(rset, "ServiceAmtSmall");
        	out.ServiceAmtMed = RowSetUtils.getDoubleValue(rset, "ServiceAmtMed");
        	out.ServiceAmtBig = RowSetUtils.getDoubleValue(rset, "ServiceAmtBig");
			outList.add(out);
        }
        return outList;
    }
}
