package com.dcdzsoft.business.ap;

import javax.sql.RowSet;

import com.dcdzsoft.EduException;
import com.dcdzsoft.sda.db.*;
import com.dcdzsoft.sms.SMSManager;
import com.dcdzsoft.util.*;
import com.dcdzsoft.dto.function.*;
import com.dcdzsoft.dto.business.*;
import com.dcdzsoft.dao.*;
import com.dcdzsoft.dao.common.*;
import com.dcdzsoft.constant.*;
import com.dcdzsoft.business.ActionBean;

/**
 * <p>Title: 自提柜后台运营系统</p>
 * <p>Description: 柜体PoBox信息查询 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class APLockerPoBoxQry extends ActionBean
{

	/**
	 * 柜体PoxBox服务状态查询
	 * @param p1
	 * @param LockerID
	 * @return
	 *  RegisterFlag 注册标志（0-不提供注册；1-可用于注册）
	 *  LockerID    当前柜号
	 *  LockerName  柜体名称
	 *  Location    柜体位置
	 *  RegisterLimit 激活期限（天数）,即未激活或已失效用户信息保存的时间
	 *  Message 返回消息：RegisterFlag=0时，消息内容为不支持注册提醒；
	 *                 RegisterFlag=1，消息内容为Application  Guide
	 * @throws com.dcdzsoft.EduException
	 */
    public OutParamAPLockerPoBoxQry doBusiness(InParamAPLockerPoBoxQry in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        OutParamAPLockerPoBoxQry out = new OutParamAPLockerPoBoxQry();

		//1.验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(in.LockerID))
			throw new EduException(ErrorCode.ERR_PARMERR);


		//2.调用CommonDAO.isOnline(操作员编号)判断操作员是否在线。
		//OPOperOnline operOnline = commonDAO.isOnline(in.OperID);
		
		TBTerminalDAO terminalDAO = daoFactory.getTBTerminalDAO();
		TBTerminal terminal = new TBTerminal();
		terminal.TerminalNo = in.LockerID;
		
		try{
			terminal = terminalDAO.find(terminal);
			out.LockerID = terminal.TerminalNo;
			out.Location = terminal.Location;
			out.LockerName = terminal.TerminalName;
			out.AppRegisterLimit = terminal.AppRegisterLimit;
			if("1".equals(terminal.AppRegisterFlag)&& terminal.AppRegisterLimit>0){
				out.AppRegisterFlag = 1;
				out.Message         = SMSManager.getInstance().getApplcationGuide(terminal.AppRegisterLimit);//"Application Guide:"
				out.Message         = out.Message.replaceAll("\n", "<br>");
			}else{
				out.AppRegisterFlag = 0;
				out.Message         = "The Service Not Available!";
			}
		}catch(EduException e){
			out.AppRegisterFlag = 0;
			out.Message         = "The Service Not Available!";
		}
		
        return out;
    }
}
