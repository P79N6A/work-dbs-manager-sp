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
 * <p>Description: 服务商信息修改 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class IMCompanyMod extends ActionBean
{

    public int doBusiness(InParamIMCompanyMod in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        int result = 0;

		//1.	验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(in.OperID)
			||StringUtils.isEmpty(in.CompanyID)
			||StringUtils.isEmpty(in.CompanyName)
			||StringUtils.isEmpty(in.CompanyType)
			||StringUtils.isEmpty(in.MasterFlag))
            throw new EduException(ErrorCode.ERR_PARMERR);

		//2.	调用CommonDAO.isOnline(管理员编号)判断管理员是否在线。
		OPOperOnline operOnline = commonDAO.isOnline(in.OperID);

		//3.	调用UtilDAO.getCurrentDateTime()获得系统日期时间。
		java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();

		//4.	调用IMCompanyDAO.isExist()判断服务商名称是否存在，如果存在返回ERR_COMPANYNAMEEXISTS。
		
		in.CompanyName = StringUtils.normalizeName(in.CompanyName);
		
		IMCompanyDAO companyDAO = daoFactory.getIMCompanyDAO();
        JDBCFieldArray whereCols0 = new JDBCFieldArray();
        whereCols0.add("CompanyName", in.CompanyName);
        whereCols0.add("CompanyID", "<>", in.CompanyID);
        if (companyDAO.isExist(whereCols0) > 0)
            throw new EduException(ErrorCode.ERR_COMPANYNAMEEXISTS);
        
        ControlParam ctrlParam = ControlParam.getInstance();
        if(in.ExpiredDays < 1){
        	in.ExpiredDays  = NumberUtils.parseInt(ctrlParam.getRecoveryTimeout());//回收超时时间（单位：天数）
        	
        }
        if(in.ReminderDays < 1){

        	in.ReminderDays = NumberUtils.parseInt(ctrlParam.getReminderDays());//催领天数
        }
        if(in.ReminderTime==null){
        	in.ReminderTime = "12:00:00";//只取时间，默认十二点
        }
        
        in.Address = commonDAO.getAddressHtmlToText(in.Address);
        if(!"1".equals(in.MasterFlag)){
        	in.MasterFlag = "0";
        }
        if(!"1".equals(in.Feedback)){
        	in.Feedback = "0";
        }
        
        //5.	调用IMCompanyDAO. Update()更新服务商信息。
        JDBCFieldArray setCols = new JDBCFieldArray();
        JDBCFieldArray whereCols = new JDBCFieldArray();
        setCols.add("CompanyName", in.CompanyName);
        setCols.add("CompanyType", in.CompanyType);
        setCols.add("MasterFlag", in.MasterFlag);
        setCols.add("Address", in.Address);
        setCols.add("Email", in.Email);
        setCols.add("Mobile", in.Mobile);
        setCols.add("Feedback", in.Feedback);
        setCols.add("LogoUrl", in.LogoUrl);
        setCols.add("Slogan", in.Slogan);
        setCols.add("SMS_Notification", in.SMS_Notification);
        setCols.add("ExpiredDays", in.ExpiredDays);
        setCols.add("ReminderDays", in.ReminderDays);
        setCols.add("ReminderTime", in.ReminderTime);
        setCols.add("LastModifyTime", sysDateTime);
        setCols.add("Remark", in.Remark);

        whereCols.add("CompanyID", in.CompanyID);

        result = companyDAO.update(setCols, whereCols);

        //虚拟分拣区域名称与服务商名称保持一致
        IMZoneDAO zoneDAO = daoFactory.getIMZoneDAO();
        IMZone zone = new IMZone();
        zone.ZoneID      = in.CompanyID+Constant.ZONE_VIRTUAL_ID;//000-虚拟分拣区域
        
        JDBCFieldArray whereCols1 = new JDBCFieldArray();
        
        whereCols1.add("ZoneName", in.CompanyName);
        whereCols1.add("ZoneID", zone.ZoneID);
        if(zoneDAO.isExist(whereCols1)<=0){
        	if(zoneDAO.isExist(zone)){
        		zoneDAO.delete(zone);
        	}
        	zone.ZoneName      = in.CompanyName;
        	zone.CompanyID     = in.CompanyID;
        	zone.Description   = "";
        	zone.CollectCharge = 0.0;
        	zone.MandatoryFlag = 255;//255 R1~R8强制打印
            zone.CreateTime    = sysDateTime;
            zone.LastModifyTime= sysDateTime;
            zone.Remark      = "";
            
            zoneDAO.insert(zone);
        }
        
        
        // 调用CommonDAO.addOperatorLog(OperID，功能编号，系统日期时间，“”)
        OPOperatorLog log = new OPOperatorLog();
        log.OperID = in.OperID;
        log.FunctionID = in.getFunctionID();
        log.OccurTime = sysDateTime;
        log.StationAddr = operOnline.LoginIPAddr;
        log.Remark = in.CompanyName;

        commonDAO.addOperatorLog(log);

        return result;
    }
}
