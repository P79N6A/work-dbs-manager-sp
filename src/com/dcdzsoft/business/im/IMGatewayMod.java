package com.dcdzsoft.business.im;


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
 * <p>Description: 短信邮件接口信息修改 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class IMGatewayMod extends ActionBean
{

    public int doBusiness(InParamIMGatewayMod in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        int result = 0;

		//1.	验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(in.OperID)
			||StringUtils.isEmpty(in.GatewayID)
			||StringUtils.isEmpty(in.GatewayName))
			throw new EduException(ErrorCode.ERR_PARMERR);

		//2.	调用CommonDAO.isOnline(管理员编号)判断管理员是否在线。
		OPOperOnline operOnline = commonDAO.isOnline(in.OperID);

		//3.	调用UtilDAO.getCurrentDateTime()获得系统日期时间。
		java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();
		
		//4.	调用IMGatewayDAO.isExist()判断短信邮件接口名称是否存在，如果存在返回ERR_GATEWAYNAMEEXISTS。
		IMGatewayDAO gatewayDAO = daoFactory.getIMGatewayDAO();

        JDBCFieldArray whereCols0 = new JDBCFieldArray();
        whereCols0.add("GatewayName", in.GatewayName);
        whereCols0.add("GatewayID", "<>", in.GatewayID);
        if (gatewayDAO.isExist(whereCols0) > 0){
            throw new EduException(ErrorCode.ERR_GATEWAYNAMEEXISTS);
        }

		//5.	调用IMGatewayDAO. Update()更新短信邮件接口信息。
        JDBCFieldArray setCols = new JDBCFieldArray();
        JDBCFieldArray whereCols = new JDBCFieldArray();
        setCols.add("GatewayName", in.GatewayName);
        setCols.add("SMSUsername", in.SMSUsername);
        setCols.add("SMSPassword", in.SMSPassword);
        setCols.add("SMSUsercode", in.SMSUsercode);
        setCols.add("SMSField1", in.SMSField1);
        setCols.add("SMSField2", in.SMSField2);
        setCols.add("SMSField3", in.SMSField3);
        setCols.add("GatewayURL", in.GatewayURL);
        setCols.add("LastModifyTime", sysDateTime);
        setCols.add("Remark", in.Remark);

        whereCols.add("GatewayID", in.GatewayID);

        result = gatewayDAO.update(setCols, whereCols);

        // 调用CommonDAO.addOperatorLog(OperID，功能编号，系统日期时间，“”)
        OPOperatorLog log = new OPOperatorLog();
        log.OperID = in.OperID;
        log.FunctionID = in.getFunctionID();
        log.OccurTime = sysDateTime;
        log.StationAddr = operOnline.LoginIPAddr;
        log.Remark = in.GatewayName;

        commonDAO.addOperatorLog(log);

        return result;
    }
}
