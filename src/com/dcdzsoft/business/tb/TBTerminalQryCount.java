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
 * <p>Description: 柜体信息查询数量 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class TBTerminalQryCount extends ActionBean
{

    public int doBusiness(InParamTBTerminalQryCount in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        int result = 0;

        //1.	验证输入参数是否有效，如果无效返回-1。
        if (StringUtils.isEmpty(in.OperID))
            throw new EduException(ErrorCode.ERR_PARMERR);

        //2.	调用CommonDAO.isOnline(管理员编号)判断管理员是否在线。
        String limitSQL = "";
        if(StringUtils.isNotEmpty(in.OperID))
        {
        	OPOperOnline operOnline = commonDAO.isOnline(in.OperID);
        	
        	limitSQL = commonDAO.getQueryLockerLimitSQL(operOnline.OperID, operOnline.ZoneID);
        }

        PreparedWhereExpression whereSQL = new PreparedWhereExpression();
        //whereSQL.checkAdd("DepartmentID", in.DepartmentID);
        whereSQL.checkAdd("TerminalNo", in.TerminalNo);
        if(StringUtils.isNotEmpty(in.TerminalName))
           whereSQL.add("TerminalName", " LIKE ", "%" + in.TerminalName + "%");

        whereSQL.checkAdd("RegisterFlag", in.RegisterFlag);
        whereSQL.checkAdd("TerminalStatus", in.TerminalStatus);
        whereSQL.checkAdd("TerminalType", in.TerminalType);
        
        String sql = "SELECT COUNT(TerminalNo) FROM V_TBTerminal a WHERE 1=1 " + whereSQL.getPreparedWhereSQL() + limitSQL;

        result = dbSession.executeQueryCount(sql,whereSQL);

        return result;
    }
}
