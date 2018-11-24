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
import com.dcdzsoft.businessproxy.PushBusinessProxy;

/**
 * <p>Title: 自提柜后台运营系统</p>
 * <p>Description: 柜体名称修改（远程） </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class TBTerminalModName extends ActionBean
{

    public int doBusiness(InParamTBTerminalModName in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        int result = 0;

        //1.	验证输入参数是否有效，如果无效返回-1。
        if (StringUtils.isEmpty(in.OperID)
            || StringUtils.isEmpty(in.TerminalNo)
            || StringUtils.isEmpty(in.TerminalName)
            || StringUtils.isEmpty(in.ZoneID)
            || StringUtils.isEmpty(in.City)
            || StringUtils.isEmpty(in.Zipcode))
            throw new EduException(ErrorCode.ERR_PARMERR);

        if (StringUtils.isEmpty(in.RemoteFlag))
            in.RemoteFlag = SysDict.OPER_FLAG_REMOTE;

		//2.	调用CommonDAO.isOnline(管理员编号)判断管理员是否在线。
		OPOperOnline operOnline = commonDAO.isOnline(in.OperID);

		//3.	调用UtilDAO.getCurrentDateTime()获得系统日期时间。
		java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();
		java.sql.Date sysDate = DateUtils.toSQLDate(sysDateTime);
		
        //修改柜体信息
        TBTerminalDAO terminalDAO = daoFactory.getTBTerminalDAO();
        TBTerminal terminal = new TBTerminal();
        terminal.TerminalNo = in.TerminalNo;
        terminal = terminalDAO.find(terminal);
        
        if("1".equals(in.AppRegisterFlag)){
        	if(in.AppRegisterLimit<7){
            	in.AppRegisterLimit = 7;
            }
        }else{
        	in.AppRegisterFlag = "0";
        	//in.AppRegisterLimit = 0;
        }
        
        IMZoneDAO zoneDAO = daoFactory.getIMZoneDAO();
		IMZone zone = new IMZone();
		zone.ZoneID = in.ZoneID;
        zone = zoneDAO.find(zone);
        //柜体所在分拣中心（沙特邮政的分拣中心）分配权限
        IMCompanyBoxRightDAO companyBoxRightDAO = daoFactory.getIMCompanyBoxRightDAO();
        JDBCFieldArray whereCols2 = new JDBCFieldArray();
        whereCols2.add("CompanyID", zone.CompanyID);
        whereCols2.add("TerminalNo", in.TerminalNo);
        if(companyBoxRightDAO.isExist(whereCols2) < 1){
        	//未分配权限，进行Box权限分配
        	TBServerBoxDAO serverBoxDAO = daoFactory.getTBServerBoxDAO();
            JDBCFieldArray whereCols3 = new JDBCFieldArray();
            whereCols3.add("TerminalNo", in.TerminalNo);
            RowSet rset = serverBoxDAO.select(whereCols3);
            while (RowSetUtils.rowsetNext(rset)) {
            	String BoxNo = RowSetUtils.getStringValue(rset, "BoxNo");
            	IMCompanyBoxRight companyBoxRight = new IMCompanyBoxRight();
                companyBoxRight.CompanyID  = zone.CompanyID;
                companyBoxRight.TerminalNo = in.TerminalNo;
                companyBoxRight.BoxNo      = BoxNo;
            	companyBoxRightDAO.insert(companyBoxRight);
            }
        }
        IMZoneLockerRightDAO zoneLockerRightDAO = daoFactory.getIMZoneLockerRightDAO();
        IMZoneLockerRight zoneLockerRight = new IMZoneLockerRight();
        
        if(StringUtils.isNotEmpty(terminal.ZoneID)){
        	//删除原AZC权限，同一柜子不能属于不同的AZC
        	zoneLockerRight.ZoneID     = terminal.ZoneID;
            zoneLockerRight.TerminalNo = in.TerminalNo;
            zoneLockerRightDAO.delete(zoneLockerRight);
        }
        JDBCFieldArray whereCols4 = new JDBCFieldArray();
        whereCols4.add("ZoneID", in.ZoneID);
        whereCols4.add("TerminalNo", in.TerminalNo);
        if(zoneLockerRightDAO.isExist(whereCols4)<1){
             zoneLockerRight.ZoneID     = in.ZoneID;
             zoneLockerRight.TerminalNo = in.TerminalNo;
             zoneLockerRightDAO.insert(zoneLockerRight);
        }
        
        
        /**
         * Locker.Address自动生成：
         * "AZC " +AZC.Code +", "+Locker.LockerID//V_TBTerminal中实现
         * Locker.City+","+Locker.Zipcode
         * "Saudi Arabia"
         * 
         * Customer.Address：
         * "AZC " +AZC.Code +", "+Customer.Code
         * Locker.Address
         * (Locker.City+","+Locker.Zipcode
         * "Saudi Arabia")
         */
        //in.Address = commonDAO.getAddressHtmlToText(in.Address);
        in.Address = "";
        //in.Address += "AZC "+in.ZoneID+", "+in.TerminalNo+"\n";//V_TBTerminal中实现
        in.Address += in.City+", "+in.Zipcode+"\n";//Customer.Address 
        in.Address += "Saudi Arabia";
        //System.out.println("OfBureau="+in.OfBureau);
        JDBCFieldArray whereCols1 = new JDBCFieldArray();
        JDBCFieldArray setCols1 = new JDBCFieldArray();
        setCols1.add("TerminalName", in.TerminalName);
        setCols1.add("Address", in.Address);
        setCols1.add("Location", in.Location);
        setCols1.add("Longitude", in.Longitude);
        setCols1.add("Latitude", in.Latitude);
        setCols1.add("City", in.City);
        setCols1.add("Zipcode", in.Zipcode);
        setCols1.add("ZoneID", in.ZoneID );
        setCols1.add("OfBureau", in.getOfBureau() );
        setCols1.add("AppRegisterFlag", in.AppRegisterFlag);
        setCols1.add("AppRegisterLimit", in.AppRegisterLimit);
        setCols1.add("MaintEmail", in.MaintEmail);
        setCols1.add("MaintMobile", in.MaintMobile);
        setCols1.add("TerminalType", in.TerminalType);
        
        whereCols1.add("TerminalNo", in.TerminalNo);

        result = terminalDAO.update(setCols1, whereCols1);

        // 调用CommonDAO.addOperatorLog(OperID，功能编号，系统日期时间，“”)
        OPOperatorLog log = new OPOperatorLog();
        log.OperID = in.OperID;
        log.FunctionID = in.getFunctionID();
        log.OccurTime = sysDateTime;
        log.StationAddr = operOnline.LoginIPAddr;
        log.Remark = in.TerminalNo + "," + in.TerminalName;

        commonDAO.addOperatorLog(log);

        ///推送到设备端
        try
        {
        	PushBusinessProxy.doBusiness(in);
        }catch(EduException e)
        {
        	
        }

        return result;
    }
}
