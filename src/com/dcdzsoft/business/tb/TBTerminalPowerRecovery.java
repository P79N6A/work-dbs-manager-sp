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
 * <p>Description: 柜体电源恢复市电供电 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class TBTerminalPowerRecovery extends ActionBean
{

    public int doBusiness(InParamTBTerminalPowerRecovery in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        int result = 0;

		//1.验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(in.TerminalNo))
			throw new EduException(ErrorCode.ERR_PARMERR);

		java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();
		
		TBTerminalDAO terminalDAO = daoFactory.getTBTerminalDAO();
		
		JDBCFieldArray setCols = new JDBCFieldArray();
        JDBCFieldArray whereCols = new JDBCFieldArray();

        setCols.add("TerminalStatus", SysDict.TERMINAL_STATUS_NORMAL);
        setCols.add("LastModifyTime", sysDateTime);
        if(in.DurationMins>0){
        	java.sql.Timestamp powerFailTime = new java.sql.Timestamp(sysDateTime.getTime()-(in.DurationMins*1000*60));
        	whereCols.add("LastModifyTime", "<", powerFailTime);
        }
        whereCols.add("TerminalNo", in.TerminalNo);
        whereCols.add("TerminalStatus", SysDict.TERMINAL_STATUS_FAULT_POWERFAIL);
        terminalDAO.update(setCols, whereCols);
        return result;
    }
}
