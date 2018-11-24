package com.dcdzsoft.business.op;

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
 * <p>Description: 查询管理员信息 </p>
 * <p>Copyright: Copyright (c) 2015</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class OPOperatorQry extends ActionBean
{

    public RowSet doBusiness(InParamOPOperatorQry in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        RowSet rset = null;
        OPOperOnline operOnline = null;
        String limitSQL = "";
        //1.	验证输入参数是否有效，如果无效返回-1。
        if (StringUtils.isEmpty(in.OperID)){
			throw new EduException(ErrorCode.ERR_PARMERR);
		}else if(!"SendBoxUsedEmail".equals(in.OperID)){
		//2.调用CommonDAO.isOnline(管理员编号)判断操作员是否在线。
			operOnline = commonDAO.isOnline(in.OperID);
	        //加入权限判断
	      	limitSQL = commonDAO.getQueryZoneLimitSQL(operOnline.OperID, operOnline.ZoneID);
	        
	      	limitSQL += " AND OperType>="+operOnline.OperType;//只能查询操作员类型更低级的操作员信息
	      	
		}

      	//3.	查询并返回结果集
        WhereExpression where = new WhereExpression();
        if (StringUtils.isNotEmpty(in.UserID))
            where.add("UserID", in.UserID, true);

        if (StringUtils.isNotEmpty(in.ByOperID))//管理员查询自己的信息，add by zxy
            where.add("OperID", in.ByOperID, true);
        
        if (StringUtils.isNotEmpty(in.OperName))
            where.addSQL(" AND " + utilDAO.getUpperSQL("OperName") + " LIKE " + StringUtils.addQuote("%" + in.OperName.toUpperCase() + "%"));

        where.add("OperType", in.OperType, true);
        where.add("ZoneID", in.ZoneID, true);
        where.add("OperStatus", in.OperStatus, true);
        where.add("RoleID", in.RoleID, true);
        
        if (StringUtils.isNotEmpty(in.Email))
            where.add("UpperEmail", in.Email.toUpperCase(), true);

        String sql = "SELECT * FROM V_Operator a WHERE 1=1 " + where.getWhereSQL() + limitSQL;

        java.util.LinkedList list = new java.util.LinkedList();

        if (Constant.USER_STATUS_NOTACTIVATION.equals(in.OperStatus))
            list.add("CreateTime");
        else
            list.add("CreateTime DESC");

        rset = dbSession.executeQuery(sql, list, in.recordBegin, in.recordNum);

        return rset;
    }
}
