package com.dcdzsoft.business.tb;

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
 * <p>Description: 箱体信息查询 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class TBBoxQry extends ActionBean
{

    public RowSet doBusiness(InParamTBBoxQry in) throws EduException
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

        String limitSQL = "";//utilDAO.getLimitSQL(operOnline.DepartmentID);
        
        PreparedWhereExpression whereSQL = new PreparedWhereExpression();
        whereSQL.checkAdd("TerminalNo", in.TerminalNo);
        whereSQL.checkAdd("DeskNo", in.DeskNo);
        whereSQL.checkAdd("BoxNo", in.BoxNo);
        whereSQL.checkAdd("BoxStatus", in.BoxStatus);
        whereSQL.checkAdd("LockStatus", in.LockStatus);
        whereSQL.checkAdd("FaultStatus", in.FaultStatus);
        whereSQL.checkAdd("TerminalName"," LIKE ", "%" + in.TerminalName + "%");

        String sql = "SELECT * FROM V_TBBox a WHERE 1=1 " + whereSQL.getPreparedWhereSQL() + limitSQL;

        java.util.LinkedList list = new java.util.LinkedList();
        list.add("TerminalNo,DeskNo,DeskBoxNo");

        rset = dbSession.executeQuery(sql, list, in.recordBegin, in.recordNum, whereSQL);

        return rset;
    }
}
