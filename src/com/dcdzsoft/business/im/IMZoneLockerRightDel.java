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
 * <p>Description: 分拣区域柜体权限删除 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class IMZoneLockerRightDel extends ActionBean
{

    public int doBusiness(InParamIMZoneLockerRightDel in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        int result = 0;

		//1.	验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(in.OperID)
			||StringUtils.isEmpty(in.ZoneID)
			||StringUtils.isEmpty(in.LockerList))
			throw new EduException(ErrorCode.ERR_PARMERR);

		//2.	调用CommonDAO.isOnline(管理员编号)判断管理员是否在线。
		OPOperOnline operOnline = commonDAO.isOnline(in.OperID);

		//3.	调用UtilDAO.getCurrentDateTime()获得系统日期时间。
		java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();
		java.sql.Date sysDate = DateUtils.toSQLDate(sysDateTime);
		
		IMZoneDAO zoneDAO = daoFactory.getIMZoneDAO();
        IMZone zone = new IMZone();
        zone.ZoneID = in.ZoneID;

        zone = zoneDAO.find(zone);
        
		//4.	自提柜柜号列表：” TerminalNo,TerminalNo,…”
        IMZoneLockerRightDAO zoneLockerRightDAO = daoFactory.getIMZoneLockerRightDAO();
        IMZoneLockerRight zoneLockerRight = new IMZoneLockerRight();
        zoneLockerRight.ZoneID     = in.ZoneID;
        
        TBTerminalDAO terminalDAO = daoFactory.getTBTerminalDAO();
        String[] lockerList = in.LockerList.split(",");
        for(String TerminalNo:lockerList){
        	JDBCFieldArray whereCols4 = new JDBCFieldArray();
            whereCols4.add("ZoneID", in.ZoneID);
            whereCols4.add("TerminalNo", TerminalNo);
            if(terminalDAO.isExist(whereCols4)>0){
            	continue;//柜体所属AZC的权限不允许删除
            }
        	
            zoneLockerRight.TerminalNo = TerminalNo;
            zoneLockerRightDAO.delete(zoneLockerRight);
        }

		// 调用CommonDAO.addOperatorLog(OperID，功能编号，系统日期时间，“”)
		OPOperatorLog log = new OPOperatorLog();
		log.OperID = in.OperID;
		log.FunctionID = in.getFunctionID();
		log.OccurTime = sysDateTime;
		log.StationAddr = operOnline.LoginIPAddr;
		log.Remark = "";

		commonDAO.addOperatorLog(log);


        return result;
    }
}
