package com.dcdzsoft.business.mb;

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
 * <p>Description: 待催领信息查询 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class MBReminderMsgQry extends ActionBean
{

    public RowSet doBusiness(InParamMBReminderMsgQry in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        RowSet rset = null;

		//1.	验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(in.TerminalNo))
			throw new EduException(ErrorCode.ERR_PARMERR);

		//2.	调用CommonDAO.isOnline(管理员编号)判断操作员是否在线。
		if(StringUtils.isNotEmpty(in.OperID)){
			OPOperOnline operOnline = commonDAO.isOnline(in.OperID);
		}

		//3.	调用UtilDAO.getSysDateTime()获得系统日期时间。
		java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();
		java.sql.Date sysDate = DateUtils.toSQLDate(sysDateTime);
		
		PreparedWhereExpression whereSQL = new PreparedWhereExpression();
		
		whereSQL.add("TerminalNo", in.TerminalNo);
		whereSQL.add("ReminderDateTime", "<=",sysDateTime);
		whereSQL.add("ExpiredTime", ">",sysDateTime);
		whereSQL.addSQL(" AND (");
		whereSQL.addSQL(" ItemStatus<>"+StringUtils.addQuote(SysDict.ITEM_STATUS_DROP_EXPIRED));
		whereSQL.addSQL(" AND ItemStatus<>"+StringUtils.addQuote(SysDict.ITEM_STATUS_DROP_M_EXPIRED));
		whereSQL.addSQL(" )");
		String sql = "SELECT TerminalNo,PackageID FROM PTInBoxPackage a WHERE 1=1 " + whereSQL.getPreparedWhereSQL();

		rset = dbSession.executeQuery(sql, whereSQL);

        return rset;
    }
}
