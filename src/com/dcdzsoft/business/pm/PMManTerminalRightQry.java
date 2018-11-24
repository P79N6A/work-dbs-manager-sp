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
 * <p>Description: 邮递员柜体权限查询 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class PMManTerminalRightQry extends ActionBean
{

    public RowSet doBusiness(InParamPMManTerminalRightQry in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        RowSet rset = null;

		//1.	验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(in.OperID)
			||StringUtils.isEmpty(in.PostmanID))
			throw new EduException(ErrorCode.ERR_PARMERR);

		//2.	调用CommonDAO.isOnline(管理员编号)判断管理员是否在线。
		OPOperOnline operOnline = commonDAO.isOnline(in.OperID);

		//3.	返回结果集
        String sql = "SELECT a.PostmanID,b.TerminalNo,b.TerminalName"
                + " FROM TBTerminal b JOIN (SELECT PostmanID,TerminalNo FROM PMPostmanLockerRight WHERE PostmanID = " 
                + StringUtils.addQuote(in.PostmanID)
                + " ) a ON(a.TerminalNo = b.TerminalNo)";
        
        rset = dbSession.executeQuery(sql);
        
        return rset;
    }
}
