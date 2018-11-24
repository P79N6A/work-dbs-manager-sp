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
 * <p>Description: 取件密码短消息查询数量 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class MBPwdShortMsgQryCount extends ActionBean
{

    public int doBusiness(InParamMBPwdShortMsgQryCount in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        int result = 0;

        in.PackageStatus = "5,6,8,9";//取件密码，包裹在箱时有效，5-Dropped,6-D-Dropped，8-Expired，9-M-Expired
        
		//1.	验证输入参数是否有效，如果无效返回-1。
		//if (StringUtils.isEmpty(in.OperID))
		//	throw new EduException(ErrorCode.ERR_PARMERR);

		//2.	调用CommonDAO.isOnline(管理员编号)判断操作员是否在线。
        OPOperOnline operOnline = null;
        if(StringUtils.isNotEmpty(in.OperID)){
        	operOnline = commonDAO.isOnline(in.OperID);
        }

		// /////////////////////////////////////////////////////////////
		String limitSQL = "";//utilDAO.getLimitSQL(operOnline.DepartmentID);

		// ///////////////////////////////////////////////////////////
		PreparedWhereExpression whereSQL = new PreparedWhereExpression();
		//whereSQL.checkAdd("DepartmentID", in.DepartmentID);
		whereSQL.checkAdd("TerminalNo", in.TerminalNo);
		whereSQL.checkAdd("PackageID", in.PackageID);
		whereSQL.checkAdd("CustomerMobile", in.CustomerMobile);
		//whereSQL.checkAdd("SendStatus", in.SendStatus);
		if(StringUtils.isNotEmpty(in.SendStatus)){
			whereSQL.addSQL(utilDAO.getFlagInSQL("SendStatus", in.SendStatus));
		}
		//whereSQL.checkAdd("PackageStatus", in.PackageStatus);
		if(StringUtils.isNotEmpty(in.PackageStatus)){
        	whereSQL.addSQL(utilDAO.getFlagInSQL("PackageStatus", in.PackageStatus));
        }
		whereSQL.checkAdd("StoredDate", ">=",in.BeginDate);
		whereSQL.checkAdd("StoredDate", "<=",in.EndDate);
		whereSQL.checkAdd("ReSendNum","<",in.getReSendNum());

		String sql = "SELECT COUNT(*) FROM V_PwdShortMsg a WHERE 1=1 "+ whereSQL.getPreparedWhereSQL() + limitSQL;

		result = dbSession.executeQueryCount(sql,whereSQL);
		
		return result;
	}
}
