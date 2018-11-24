package com.dcdzsoft.business.sm;

import java.util.Date;

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
 * <p>Description: 系统时间同步 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class SMSysTimeSync extends ActionBean
{

    public long doBusiness(InParamSMSysTimeSync in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();

		//1.验证输入参数是否有效，如果无效返回-1。
		//3.调用UtilDAO.getCurrentDateTime()获得系统日期时间。
		java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();
        //应用服务器与数据库服务器时间同步
        long offset = sysDateTime.getTime()-(new Date().getTime());
        ControlParam.getInstance().setDbServerAppServerTimeOffset(offset);
        return offset;
    }
}
