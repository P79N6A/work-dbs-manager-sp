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
 * <p>Description: 预定时间发送的短消息查询 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class MBScheduleMsgQry extends ActionBean
{

    public RowSet doBusiness(InParamMBScheduleMsgQry in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        RowSet rset = null;

		//1.验证输入参数是否有效，如果无效返回-1。

        //3.调用UtilDAO.getSysDateTime()获得系统日期时间。
        java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();
        java.sql.Date sysDate = DateUtils.toSQLDate(sysDateTime);

        MBPwdShortMsgDAO shortMsgDAO = daoFactory.getMBPwdShortMsgDAO();
        
        JDBCFieldArray whereCols = new JDBCFieldArray();
        whereCols.checkAdd("CustomerMobile", in.CustomerMobile);
        whereCols.checkAdd("TerminalNo", in.TerminalNo);
        whereCols.checkAdd("MsgType", in.MsgType);
        //whereCols.add("ScheduleDateTime", "<=", sysDateTime);
        whereCols.add("SendStatus", "0");//0-未发送
        rset = shortMsgDAO.select(whereCols);

        return rset;
    }
}
