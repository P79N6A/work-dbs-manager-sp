package com.dcdzsoft.business.im;

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
 * <p>Description: 服务商箱门权限增加 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class IMCompBoxRightAdd extends ActionBean
{

    public int doBusiness(InParamIMCompBoxRightAdd in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        int result = 0;

		//1.	验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(in.OperID)
			||StringUtils.isEmpty(in.CompanyID)
			||StringUtils.isEmpty(in.BoxList))
			throw new EduException(ErrorCode.ERR_PARMERR);

		//2.	调用CommonDAO.isOnline(管理员编号)判断管理员是否在线。
		OPOperOnline operOnline = commonDAO.isOnline(in.OperID);

		//3.	调用UtilDAO.getCurrentDateTime()获得系统日期时间。
		java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();
		java.sql.Date sysDate = DateUtils.toSQLDate(sysDateTime);

        //4.	调用IMCompanyDAO.isExist()判断服务商编号是否存在，如果不存在返回ERR_IMCOMPANYNODATA。
        IMCompanyDAO companyDAO = daoFactory.getIMCompanyDAO();
        IMCompany company = new IMCompany();
        company.CompanyID = in.CompanyID;
		
        if (!companyDAO.isExist(company)){
            throw new EduException(ErrorCode.ERR_IMCOMPANYNODATA);
        }
		
        //5.	箱门列表格式：” TerminalNo,BoxNo~TerminalNo,BoxNo”
        String[] boxArray = in.BoxList.split("~");
        for(String box: boxArray){
            String[] boxAndTerminal = box.split(",");
            String terminalNo = boxAndTerminal[0];
            String boxNo      = boxAndTerminal[1];
            int res = addBoxRight(company.CompanyID, terminalNo, boxNo);
            if(res>0){
                result += res;
            }
        } 
        
        // 调用CommonDAO.addOperatorLog(OperID，功能编号，系统日期时间，“”)
        OPOperatorLog log = new OPOperatorLog();
        log.OperID = in.OperID;
        log.FunctionID = in.getFunctionID();
        log.OccurTime = sysDateTime;
        log.StationAddr = operOnline.LoginIPAddr;
        log.Remark = "CompanyID="+in.CompanyID+",BoxList="+in.BoxList;

        commonDAO.addOperatorLog(log);

        return result;
    }
    private int addBoxRight(String CompanyID, String TerminalNo, String BoxNo) throws EduException{
    	//5.	调用TBTerminalDAO.isExist()判断设备编号是否存在，如果不存在返回ERR_TBTERMINALNODATA。
        TBTerminalDAO terminalDAO = daoFactory.getTBTerminalDAO();
        TBTerminal terminal = new TBTerminal();
        terminal.TerminalNo = TerminalNo;
		
        if (!terminalDAO.isExist(terminal)){
            throw new EduException(ErrorCode.ERR_TBTERMINALNODATA);
        }

        //6.	调用TBServerBoxDAO.isExist()判断箱门编号是否存在，如果不存在返回ERR_TBSERVERBOXNODATA。
        TBServerBoxDAO serverBoxDAO = daoFactory.getTBServerBoxDAO();
        TBServerBox serverBox = new TBServerBox();
        serverBox.TerminalNo = TerminalNo;
        serverBox.BoxNo      = BoxNo;
		
        if (!serverBoxDAO.isExist(serverBox)){
            throw new EduException(ErrorCode.ERR_TBSERVERBOXNODATA);
        }
        
        //7.	调用IMCompanyBoxRightDAO.isExist()判断记录是否存在，如果存在返回ERR_COMPBOXRIGHTHAVEEXIST。
        IMCompanyBoxRightDAO companyBoxRightDAO = daoFactory.getIMCompanyBoxRightDAO();
        IMCompanyBoxRight companyBoxRight = new IMCompanyBoxRight();
        companyBoxRight.CompanyID  = CompanyID;
        companyBoxRight.TerminalNo = TerminalNo;
        companyBoxRight.BoxNo      = BoxNo;
        
        if (companyBoxRightDAO.isExist(companyBoxRight)){
            throw new EduException(ErrorCode.ERR_COMPBOXRIGHTHAVEEXIST);
        }
        
        //8.	调有IMCompanyBoxRightDAO.insert()插入权限信息。
        int result = companyBoxRightDAO.insert(companyBoxRight);
        
        //虚拟分拣中心增加柜体权限-柜子只能属于一个分拣中心
        /*
        IMZoneLockerRightDAO zoneLockerRightDAO = daoFactory.getIMZoneLockerRightDAO();
        IMZoneLockerRight zoneLockerRight = new IMZoneLockerRight();
        zoneLockerRight.ZoneID     = CompanyID+Constant.ZONE_VIRTUAL_ID;//
        zoneLockerRight.TerminalNo = TerminalNo;
        if(!zoneLockerRightDAO.isExist(zoneLockerRight)){
        	zoneLockerRightDAO.insert(zoneLockerRight);
        }*/
        return result;
    }
}
