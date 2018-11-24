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
 * <p>Description: 管理员日志查询 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class OPOperLogQry extends ActionBean
{

    public RowSet doBusiness(InParamOPOperLogQry in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        RowSet rset = null;

        //1.	验证输入参数是否有效，如果无效返回-1。
        if (StringUtils.isEmpty(in.OperID))
            throw new EduException(ErrorCode.ERR_PARMERR);

        //2.	如果recordBegin<=0 或者recordNum<=0 返回参数错误。


        //3.	调用CommonDAO.isOnline(管理员编号)判断管理员是否在线。
        OPOperOnline operOnline = commonDAO.isOnline(in.OperID);
        
        //加入权限判断
        String limitSQL = "";
      	//limitSQL = commonDAO.getQueryZoneLimitSQL(operOnline.OperID, operOnline.ZoneID);
        
      	//limitSQL += " AND OperType>="+operOnline.OperType;//只能查询操作员类型更低级的操作员信息
        
		//4.	调用CommonDAO.operLogQry(开始日期, 结束日期, 被操作的管理员编号，功能编号)查询管理员日志。
        PreparedWhereExpression whereSQL = new PreparedWhereExpression();
        whereSQL.checkAdd("OperID", in.ByOperID);
        whereSQL.checkAdd("UserID", in.UserID);
        whereSQL.checkAdd("OperName", in.OperName);
        whereSQL.checkAdd("OperType",in.OperType);
        whereSQL.checkAdd("FunctionID", in.FactFunctionID);
        whereSQL.checkAdd("TerminalNo", in.TerminalNo);
        whereSQL.checkAdd("OccurDate", ">=", in.BeginDate);
        whereSQL.checkAdd("OccurDate", "<=", in.EndDate);

        String sql = "SELECT * FROM V_OperatorLog a WHERE 1=1 " + whereSQL.getPreparedWhereSQL()+limitSQL;

        java.util.LinkedList list = new java.util.LinkedList();
        list.add("OperLogID DESC");

        rset = dbSession.executeQuery(sql, list, in.recordBegin, in.recordNum, whereSQL);

        return rset;
    }
}
