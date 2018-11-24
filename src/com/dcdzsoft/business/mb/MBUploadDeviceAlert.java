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
import com.dcdzsoft.businessproxy.MonitorProxy;

/**
 * <p>Title: 自提柜后台运营系统</p>
 * <p>Description: 上传设备警报信息 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class MBUploadDeviceAlert extends ActionBean
{

    public String doBusiness(InParamMBUploadDeviceAlert in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        String result = "";

        //1.	验证输入参数是否有效，如果无效返回-1。
        if (StringUtils.isEmpty(in.AlertType))
            throw new EduException(ErrorCode.ERR_PARMERR);
        
        java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();
        
        commonDAO.insertAlert(in.TerminalNo, in.AlertType, in.AlertLevel, in.BoxNo,"");
        
      	MonitorProxy.broadcastAlert(in.TerminalNo, in.AlertType, in.AlertLevel, in.BoxNo,"");
        
      	
        return result;
    }
}
