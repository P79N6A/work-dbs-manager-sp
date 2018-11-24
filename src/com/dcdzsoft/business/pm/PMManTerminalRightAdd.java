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
 * <p>Description: 邮递员柜体权限增加 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class PMManTerminalRightAdd extends ActionBean
{

    public int doBusiness(InParamPMManTerminalRightAdd in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        int result = 0;

		//1.	验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(in.OperID)
			||StringUtils.isEmpty(in.PostmanID)
			||StringUtils.isEmpty(in.TerminalNo))
			throw new EduException(ErrorCode.ERR_PARMERR);

		//2.	调用CommonDAO.isOnline(管理员编号)判断管理员是否在线。
		OPOperOnline operOnline = commonDAO.isOnline(in.OperID);

		//3.	调用UtilDAO.getCurrentDateTime()获得系统日期时间。
		java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();
		java.sql.Date sysDate = DateUtils.toSQLDate(sysDateTime);

		//4.	调用IMZoneDAO.isExist()判断邮递员编号是否存在，如果不存在返回ERR_PMPOSTMANNODATA。
        PMPostmanDAO postmanDAO = daoFactory.getPMPostmanDAO();
        PMPostman postman = new PMPostman();
        postman.PostmanID = in.PostmanID;
        
        postman = postmanDAO.find(postman);
        
        //5.	检查分拣中心柜体权限
        IMZoneLockerRightDAO zoneLockerRightDAO = daoFactory.getIMZoneLockerRightDAO();
        JDBCFieldArray whereCols1 = new JDBCFieldArray();
        whereCols1.add("ZoneID", postman.ZoneID);
        whereCols1.add("TerminalNo", in.TerminalNo);
		
        if (zoneLockerRightDAO.isExist(whereCols1) <= 0){
        	throw new EduException(ErrorCode.ERR_ZONELOCKERRIGHTNOTEXIST);
        }
        
		//6.	调用TBTerminalDAO.isExist()判断柜体编号是否存在，如果不存在返回ERR_TBTERMINALNODATA。
        TBTerminalDAO terminalDAO = daoFactory.getTBTerminalDAO();
        TBTerminal terminal = new TBTerminal();
        terminal.TerminalNo = in.TerminalNo;
		
        if (!terminalDAO.isExist(terminal)){
            throw new EduException(ErrorCode.ERR_TBTERMINALNODATA);
        }
        
        //7.	调用PMPostmanLockerRightDAO.isExist()判断记录是否存在，如果存在返回ERR_POSTMANLOCKERRIGHTHAVEEXIST。
        PMPostmanLockerRightDAO postmanLockerRightDAO = daoFactory.getPMPostmanLockerRightDAO();
        PMPostmanLockerRight postmanLockerRight = new PMPostmanLockerRight();
        postmanLockerRight.PostmanID  = in.PostmanID;
        postmanLockerRight.TerminalNo = in.TerminalNo;
        
        if (postmanLockerRightDAO.isExist(postmanLockerRight)){
            throw new EduException(ErrorCode.ERR_POSTMANLOCKERRIGHTHAVEEXIST);
        }
        
        //8.	调有PMPostmanLockerRightDAO.insert()插入权限信息。
        result = postmanLockerRightDAO.insert(postmanLockerRight);
        
		// 调用CommonDAO.addOperatorLog(OperID，功能编号，系统日期时间，“”)
		OPOperatorLog log = new OPOperatorLog();
		log.OperID = in.OperID;
		log.FunctionID = in.getFunctionID();
		log.OccurTime = sysDateTime;
		log.StationAddr = operOnline.LoginIPAddr;
		log.Remark = "PostmanID="+in.PostmanID+",LockerID="+in.TerminalNo;

		commonDAO.addOperatorLog(log);

        return result;
    }
}
