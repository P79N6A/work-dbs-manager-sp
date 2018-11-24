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
 * <p>Description: 分拣区域柜体权限增加 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class IMZoneLockerRightAdd extends ActionBean
{

    public int doBusiness(InParamIMZoneLockerRightAdd in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        int result = 0;

		//1.	验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(in.OperID)
			||StringUtils.isEmpty(in.ZoneID))
			throw new EduException(ErrorCode.ERR_PARMERR);

		//2.	调用CommonDAO.isOnline(管理员编号)判断管理员是否在线。
		OPOperOnline operOnline = commonDAO.isOnline(in.OperID);

		//3.	调用UtilDAO.getCurrentDateTime()获得系统日期时间。
		java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();
		java.sql.Date sysDate = DateUtils.toSQLDate(sysDateTime);

		//4.	判断分拣区域中心编号是否存在，如果不存在返回ERR_IMZONENODATA。
        IMZoneDAO zoneDAO = daoFactory.getIMZoneDAO();
        IMZone zone = new IMZone();
        zone.ZoneID = in.ZoneID;

        zone = zoneDAO.find(zone);
        
        
		//5.	调用TBTerminalDAO.isExist()判断柜体编号是否存在，如果不存在返回ERR_TBTERMINALNODATA。
        IMZoneLockerRightDAO zoneLockerRightDAO = daoFactory.getIMZoneLockerRightDAO();
        TBTerminalDAO terminalDAO = daoFactory.getTBTerminalDAO();
        String[] lockerList = in.LockerList.split(",");
        for(String TerminalNo:lockerList){
        	TBTerminal terminal = new TBTerminal();
        	terminal.TerminalNo = TerminalNo;
        	if (!terminalDAO.isExist(terminal)){
        		throw new EduException(ErrorCode.ERR_TBTERMINALNODATA);
        	}
        	
        	//6.	调用IMZoneLockerRightDAO.isExist()判断记录是否存在，如果存在返回ERR_ZONELOCKERRIGHTHAVEEXIST。    
        	IMZoneLockerRight zoneLockerRight = new IMZoneLockerRight();
            zoneLockerRight.ZoneID     = in.ZoneID;
            zoneLockerRight.TerminalNo = TerminalNo;
            
            if (zoneLockerRightDAO.isExist(zoneLockerRight)){
                throw new EduException(ErrorCode.ERR_ZONELOCKERRIGHTHAVEEXIST);
            }
            
            //7.	调有IMZoneLockerRightDAO.insert()插入权限信息。
            zoneLockerRightDAO.insert(zoneLockerRight);
        }
        
		// 调用CommonDAO.addOperatorLog(OperID，功能编号，系统日期时间，“”)
		OPOperatorLog log = new OPOperatorLog();
		log.OperID = in.OperID;
		log.FunctionID = in.getFunctionID();
		log.OccurTime = sysDateTime;
		log.StationAddr = operOnline.LoginIPAddr;
		log.Remark = "ZoneID="+in.ZoneID+",LockerList="+in.LockerList;

		commonDAO.addOperatorLog(log);

        return result;
    }
}
