package com.dcdzsoft.business.pm;

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
 * <p>Description: 邮递员列表查询 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class PMPostmanListQry extends ActionBean
{

    public RowSet doBusiness(InParamPMPostmanListQry in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        RowSet rset = null;

		//1.	验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(in.OperID))
			throw new EduException(ErrorCode.ERR_PARMERR);


		//2.	调用CommonDAO.isOnline(管理员编号)判断管理员是否在线。
		OPOperOnline operOnline = commonDAO.isOnline(in.OperID);


		//3.	调用UtilDAO.getCurrentDateTime()获得系统日期时间。
		java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();
		java.sql.Date sysDate = DateUtils.toSQLDate(sysDateTime);
		
		String limitsql = commonDAO.getQueryZoneLimitSQL(operOnline.OperID, operOnline.ZoneID);
		 
        PreparedWhereExpression whereSQL = new PreparedWhereExpression();
        whereSQL.checkAdd("PostmanID", in.PostmanID);
        whereSQL.checkAdd("ZoneID", in.ZoneID);
        whereSQL.checkAdd("CollectZoneID", in.CollectZoneID);
        whereSQL.checkAdd("CompanyID", in.CompanyID);
        whereSQL.checkAdd("PostmanType", in.PostmanType);
        whereSQL.checkAdd("PostmanStatus",in.PostmanStatus);

        String sql = "SELECT PostmanID,PostmanName FROM V_Postman a WHERE 1=1 " 
        + whereSQL.getPreparedWhereSQL() + limitsql;

        java.util.LinkedList list = new java.util.LinkedList();
        list.add("PostmanID DESC");

        rset = dbSession.executeQuery(sql, whereSQL);

        return rset;
    }
}
