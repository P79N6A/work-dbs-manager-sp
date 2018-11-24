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
 * <p>Description: 服务商增加 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class IMCompanyAdd extends ActionBean
{

    public int doBusiness(InParamIMCompanyAdd in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        int result = 0;

		//1.	验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(in.OperID)
			||StringUtils.isEmpty(in.CompanyName)
			||StringUtils.isEmpty(in.CompanyType)
			)//||StringUtils.isEmpty(in.MasterFlag)
			throw new EduException(ErrorCode.ERR_PARMERR);

		//2.	调用CommonDAO.isOnline(管理员编号)判断管理员是否在线。
		OPOperOnline operOnline = commonDAO.isOnline(in.OperID);

		//3.	调用UtilDAO.getCurrentDateTime()获得系统日期时间。
		java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();
		java.sql.Date sysDate = DateUtils.toSQLDate(sysDateTime);

		//4.	调用IMCompanyDAO.isExist()判断服务商名称是否存在，如果存在返回ERR_COMPANYNAMEEXISTS。
		IMCompanyDAO companyDAO = daoFactory.getIMCompanyDAO();

        in.CompanyName = StringUtils.normalizeName(in.CompanyName);
        
        JDBCFieldArray whereCols0 = new JDBCFieldArray();
        whereCols0.add("CompanyName", in.CompanyName);
        if (companyDAO.isExist(whereCols0) > 0)
            throw new EduException(ErrorCode.ERR_COMPANYNAMEEXISTS);

		//5.	生成服务商编号。
        //2位序号
        String maxCompanyID = "";
        int iMaxCompanyID = 0;
        IMCompany obj = new IMCompany();
        
        JDBCFieldArray whereCols1 = new JDBCFieldArray();
        whereCols1.add("CompanyID", "<>", Constant.COMPANY_MAX_ID);//"99"
        maxCompanyID = companyDAO.selectFunctions("MAX(CompanyID)", whereCols1);
        
        if (StringUtils.isEmpty(maxCompanyID)) {
            obj.CompanyID = Constant.COMPANY_START_ID;//"01"
        } else {
        	iMaxCompanyID = NumberUtils.parseInt(maxCompanyID) + 1;
            obj.CompanyID = StringUtils.leftPad(String.valueOf(iMaxCompanyID), 2, '0');
        }
        
        JDBCFieldArray whereCols2 = new JDBCFieldArray();
        whereCols2.add("CompanyID", in.CompanyID);
        if (companyDAO.isExist(whereCols2) > 0){
            throw new EduException(ErrorCode.ERR_COMPANYEXISTS);
        }
		
        in.Address = commonDAO.getAddressHtmlToText(in.Address);
        if(!"1".equals(in.MasterFlag)){
        	in.MasterFlag = "0";
        }
        if(!"1".equals(in.Feedback)){
        	in.Feedback = "0";
        }
		//6.	调用IMCompanyDAO.insert()插入服务商信息。
        obj.CompanyName = in.CompanyName;
        obj.CompanyType = in.CompanyType;
        obj.MasterFlag  = in.MasterFlag;
        obj.Address     = in.Address;
        obj.Email       = in.Email;
        obj.Mobile      = in.Mobile;
        obj.Feedback    = in.Feedback;
        obj.LogoUrl     = in.LogoUrl;
        obj.Slogan      = in.Slogan;
        obj.SMS_Notification = in.SMS_Notification;
        obj.CreateTime  = sysDateTime;
        obj.Remark      = in.Remark;
        
        ControlParam ctrlParam = ControlParam.getInstance();
        if(in.ExpiredDays < 1){
        	obj.ExpiredDays  = NumberUtils.parseInt(ctrlParam.getRecoveryTimeout());//回收超时时间（单位：天数）
        	
        }else{
        	obj.ExpiredDays = in.ExpiredDays;
        }
        if(in.ReminderDays < 1){

        	obj.ReminderDays = NumberUtils.parseInt(ctrlParam.getReminderDays());//催领天数
        }else{
        	obj.ReminderDays  = in.ReminderDays;
        }
        if(in.ReminderTime==null){
        	obj.ReminderTime = "12:00:00";//只取时间，默认十二点
        }else{
        	obj.ReminderTime = in.ReminderTime;
        }
        
        result = companyDAO.insert(obj);
        
        //虚拟分拣区域--服务商的管理员所属的分拣区域
        if(result==1){
        	IMZoneDAO zoneDAO = daoFactory.getIMZoneDAO();
        	IMZone zone = new IMZone();
        	zone.ZoneID      = obj.CompanyID+Constant.ZONE_VIRTUAL_ID;//000-虚拟分拣区域
        	if(zoneDAO.isExist(zone)){
        		zoneDAO.delete(zone);
        	}
        	zone.ZoneName      = obj.CompanyName;
        	zone.CompanyID     = obj.CompanyID;
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
