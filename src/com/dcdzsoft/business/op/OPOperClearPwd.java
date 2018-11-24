package com.dcdzsoft.business.op;

import javax.sql.RowSet;

import com.dcdzsoft.EduException;
import com.dcdzsoft.sda.db.*;
import com.dcdzsoft.util.*;
import com.dcdzsoft.dto.function.*;
import com.dcdzsoft.dto.business.*;
import com.dcdzsoft.dao.*;
import com.dcdzsoft.dao.common.*;
import com.dcdzsoft.config.ApplicationConfig;
import com.dcdzsoft.constant.*;
import com.dcdzsoft.sda.security.SecurityUtils;
import com.dcdzsoft.sms.SMSManager;
import com.dcdzsoft.business.ActionBean;

/**
 * <p>Title: 自提柜后台运营系统</p>
 * <p>Description: 操作员清密 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class OPOperClearPwd extends ActionBean
{

    public int doBusiness(InParamOPOperClearPwd in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        int result = 0;

        //1.	验证输入参数是否有效，如果无效返回-1。
        if (StringUtils.isEmpty(in.OperID)
            || StringUtils.isEmpty(in.OperPassword)
            || StringUtils.isEmpty(in.ByOperID))
            throw new EduException(ErrorCode.ERR_PARMERR);

        //2.	调用CommonDAO.isOnline(操作员编号)判断操作员是否在线。
        OPOperOnline operOnline = commonDAO.isOnline(in.OperID);

        //3.	调用UtilDAO.getSysDateTime()获得系统日期时间。
        java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();
        /*if (in.OperID.compareTo(Constant.DEFAULT_SUPEROPERID) != 0
        		&&in.OperID.compareTo(Constant.DEFAULT_MASTER) != 0){//暂只允许超级管理员和Master可以修改
            throw new EduException(ErrorCode.ERR_OPERNOOPENFUNC);
        }*/

        //4.	调用OPOperatorDAO.isExist(被操作的操作员编号)判断操作员是否存在，如果不存在返回ERR_OPOPERATORNODATA。
        OPOperatorDAO operatorDAO = daoFactory.getOPOperatorDAO();
        
        JDBCFieldArray whereCols0 = new JDBCFieldArray();
        whereCols0.add("OperID", in.ByOperID);
        RowSet rset = operatorDAO.select(whereCols0);
        if (!RowSetUtils.rowsetNext(rset)) {
        	whereCols0 = new JDBCFieldArray();
            whereCols0.add("UserID", in.ByOperID);
            rset = operatorDAO.select(whereCols0);
            if (RowSetUtils.rowsetNext(rset)) {
            	in.ByOperID = RowSetUtils.getStringValue(rset, "OperID");
            } else {
            	 throw new EduException(ErrorCode.ERR_OPOPERATORNODATA);
            }
        } 
        
        OPOperator byOperator = new OPOperator();
        byOperator.OperID = in.ByOperID;
        byOperator = operatorDAO.find(byOperator);
        if (!operatorDAO.isExist(byOperator)) {
            throw new EduException(ErrorCode.ERR_OPOPERATORNODATA);
        }

        //5.	调用OPOperatorDAO.find(操作员编号)查询操作员密码
        OPOperator opOperator = new OPOperator();
        opOperator.OperID = in.OperID;
        opOperator = operatorDAO.find(opOperator);
        if (opOperator.OperPassword.compareTo(SecurityUtils.md5(in.OperPassword)) != 0) {
            throw new EduException(ErrorCode.ERR_OPERPWDWRONG);
        }

        //7.	调用PAControlParamDAO.findPAControlParam(ControlParam. CNST_CTRL_INITOPERPWD,” InitOperPwd”)查询操作员初始密码
        /*PAControlParamDAO controlParamDAO = daoFactory.getPAControlParamDAO();

        PAControlParamDAO paControlDAO = daoFactory.getPAControlParamDAO();
        PAControlParam paControlP = new PAControlParam();
        paControlP.CtrlTypeID = ControlParam.CNST_CTRL_INITOPERPWD;
        paControlP.KeyString = "InitOperPwd";
        paControlP = paControlDAO.find(paControlP);
        String initPassword = paControlP.DefaultValue;*/
        
        String initPassword = RandUtils.generateNumber(6);

        //8.	//操作员初始密码加密
        String initPasswordMd5 = SecurityUtils.md5(initPassword);

        //9.	调用OPOperatorDAO.update(JDBCFieldArray setCols,JDBCFieldArray wherecols)修改操作员信息。

        JDBCFieldArray setCols = new JDBCFieldArray();
        JDBCFieldArray whereCols = new JDBCFieldArray();

        setCols.add("OperPassword", initPasswordMd5);
        whereCols.add("OperID", in.ByOperID);

        result = operatorDAO.update(setCols, whereCols);
        
      //发送短信密码
        ControlParam ctrlParam = ControlParam.getInstance();
        if(StringUtils.isNotEmpty(ApplicationConfig.getInstance().getSendShortMsg()) 
        		&& commonDAO.isPhoneNumber(byOperator.Mobile)
        		&& "1".equals(ctrlParam.getSendOperatorPwdSMS()))
        {
        	SMSManager.getInstance().sentOpterInitPwd(initPassword, byOperator.Mobile);
        }
        // 调用CommonDAO.addOperatorLog(OperID，功能编号，系统日期时间，“”)
        OPOperatorLog log = new OPOperatorLog();
        log.OperID = in.OperID;
        log.FunctionID = in.getFunctionID();
        log.OccurTime = sysDateTime;
        log.StationAddr = operOnline.LoginIPAddr;
        log.Remark = "ByOperID="+in.ByOperID;

        commonDAO.addOperatorLog(log);

        return result;
    }
}
