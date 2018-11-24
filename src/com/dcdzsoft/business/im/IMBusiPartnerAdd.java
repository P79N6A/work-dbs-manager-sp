package com.dcdzsoft.business.im;

import java.util.UUID;

import javax.sql.RowSet;

import com.dcdzsoft.EduException;
import com.dcdzsoft.sda.db.*;
import com.dcdzsoft.sda.security.SecurityUtils;
import com.dcdzsoft.sms.SMSManager;
import com.dcdzsoft.util.*;
import com.dcdzsoft.dto.function.*;
import com.dcdzsoft.dto.business.*;
import com.dcdzsoft.dao.*;
import com.dcdzsoft.dao.common.*;
import com.dcdzsoft.email.EmailSenderManager;
import com.dcdzsoft.config.ApplicationConfig;
import com.dcdzsoft.constant.*;
import com.dcdzsoft.business.ActionBean;

/**
 * <p>Title: 自提柜后台运营系统</p>
 * <p>Description: 商业伙伴信息增加 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class IMBusiPartnerAdd extends ActionBean
{

    public int doBusiness(InParamIMBusiPartnerAdd in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        int result = 0;

		//1.	验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(in.BPartnerName)
			||StringUtils.isEmpty(in.CollectZoneID)
			||StringUtils.isEmpty(in.Username))
			throw new EduException(ErrorCode.ERR_PARMERR);

		//2.	调用CommonDAO.isOnline(管理员编号)判断管理员是否在线。
		OPOperOnline operOnline = null;
		if(StringUtils.isNotEmpty(in.OperID)){
			operOnline = commonDAO.isOnline(in.OperID);
		}

		if(StringUtils.isNotEmpty(in.Mobile)){
        	if(!commonDAO.isPhoneNumber(in.Mobile))
        		throw new EduException(ErrorCode.ERR_BUSINESS_MOBILEFORMATERROR);
        }
		
		if(StringUtils.isNotEmpty(in.Email)){
        	if(!commonDAO.isEmail(in.Email))
        		throw new EduException(ErrorCode.ERR_BUSINESS_EMAILFORMATERROR);
        }
		//3.	调用UtilDAO.getCurrentDateTime()获得系统日期时间。
		java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();
		java.sql.Date sysDate = DateUtils.toSQLDate(sysDateTime);

		//4.	调用IMBusinessPartnerDAO.isExist()判断商业伙伴名称是否存在，如果存在返回ERR_BUSINESSPARTNERNAMEEXISTS。
		IMBusinessPartnerDAO businessPartnerDAO = daoFactory.getIMBusinessPartnerDAO();

        in.BPartnerName = StringUtils.normalizeName(in.BPartnerName);
        
        JDBCFieldArray whereCols0 = new JDBCFieldArray();
        whereCols0.add("BPartnerName", in.BPartnerName);
        if (businessPartnerDAO.isExist(whereCols0) > 0){
            throw new EduException(ErrorCode.ERR_BUSINESSPARTNERNAMEEXISTS);
        }
        
        //检查登录用户名
        in.Username = StringUtils.normalizeName(in.Username);
        if(!commonDAO.checkUserName(in.Username)){//UserID作为登录用户名
        	throw new EduException(ErrorCode.ERR_USERNAMEEXISTS);
        }
        
        //
        IMCollectZoneDAO collectZoneDAO = daoFactory.getIMCollectZoneDAO();
        IMCollectZone collectZone = new IMCollectZone();
        collectZone.CollectZoneID = in.CollectZoneID;
        if(!collectZoneDAO.isExist(collectZone)){
        	throw new EduException(ErrorCode.ERR_COLLECTZONENOTEXISTS);
        }
        
		//5.	生成商业伙伴编号。
        IMBusinessPartner obj = new IMBusinessPartner();
        
        if(StringUtils.isEmpty(in.BPartnerID)){
        	//8位编码
        	obj.BPartnerID = "B"+RandUtils.generateNumber(5);
        }else{
        	obj.BPartnerID = in.BPartnerID;
        }
        
        if(businessPartnerDAO.isExist(obj)){
            throw new EduException(ErrorCode.ERR_BUSINESSPARTNEREXISTS);
        }
        
        //生成登入密码
        if(StringUtils.isEmpty(in.Password)){
        	in.Password = RandUtils.generateString(6);//6位密码
        }
        
        in.Address = commonDAO.getAddressHtmlToText(in.Address);
        
        if(in.Balance<0 || in.Balance > 100){
        	in.Balance = 0;
        }
        if(in.Discount>100){
        	in.Discount = 100;
        }else if(in.Discount < 0){
        	in.Discount = 0;
        }
        in.CreditLimit = (in.CreditLimit<0?0:in.CreditLimit);
        if(!"1".equals(in.CreditFlag)){
        	in.CreditFlag = "0";
        }
        if(!"1".equals(in.CollectionServiceFlag)){
        	in.CollectionServiceFlag = "0";
        }
        if(!"1".equals(in.ReturnServiceFlag)){
        	in.ReturnServiceFlag = "0";
        }

		//6.	调用IMBusinessPartnerDAO.insert()插入商业伙伴信息。
        obj.BPartnerName = in.BPartnerName;
        obj.CollectZoneID = in.CollectZoneID;
        obj.Address      = in.Address;
        obj.Email        = in.Email;
        obj.Mobile       = in.Mobile;
        obj.Username     = in.Username;
        obj.Password     = SecurityUtils.md5(in.Password);//MD5 加密
        //obj.Balance      = 0;
        obj.CreditFlag   = in.CreditFlag;
        obj.CreditLimit  = in.CreditLimit;
        obj.Discount     = in.Discount;
        obj.CollectionServiceFlag = in.CollectionServiceFlag;    
        obj.ReturnServiceFlag     = in.ReturnServiceFlag;
        obj.CreateTime   = sysDateTime;
        obj.Remark       = in.Remark;
        obj.Latitude     = in.Latitude;
        obj.Longitude    = in.Longitude;
        
        result = businessPartnerDAO.insert(obj);
        
        if(in.Balance > 0.0){
        	UUID uuid = UUID.randomUUID();
    		String tradeID = uuid.toString().replace("-", "");
        	//#start 更新账户余额
            commonDAO.doBusinessPartnerTopUp(obj, in.Balance, tradeID, "free");	
            //#end
        }
        if(StringUtils.isNotEmpty(in.ServiceID)){
        	IMBPartnerServiceRightDAO serviceRightDAO = daoFactory.getIMBPartnerServiceRightDAO();
        	
        	//删除
        	JDBCFieldArray whereCols = new JDBCFieldArray();
        	whereCols.add("BPartnerID", obj.BPartnerID);
        	serviceRightDAO.delete(whereCols);
        	
        	IMBPartnerServiceRight serviceRight = new IMBPartnerServiceRight();
        	serviceRight.BPartnerID = obj.BPartnerID;
        	
        	String[] serviceArray = in.ServiceID.split(",");
        	for(String serviceID: serviceArray){
        		serviceRight.ServiceID = serviceID;
        		serviceRightDAO.insert(serviceRight);
        	}
        }
        
        //send email
        if(StringUtils.isNotEmpty(in.Email)
        		&&StringUtils.isNotEmpty(ApplicationConfig.getInstance().getEmailAddress())){
    		EmailSenderManager.getInstance().sendWebPortalLoginAccount(in.BPartnerName, in.Username, in.Password, in.Email);
        }
        
        //发送短信密码
        ControlParam ctrlParam = ControlParam.getInstance();
        if(StringUtils.isNotEmpty(ApplicationConfig.getInstance().getSendShortMsg()) 
        		&&StringUtils.isNotEmpty(in.Mobile)
        		&&"1".equals(ctrlParam.getSendBPPwdSMS()))
        {
        	SMSManager.getInstance().sentBPInitPwd(in.Username, in.Password,in.Mobile);
        }
        // 调用CommonDAO.addOperatorLog(OperID，功能编号，系统日期时间，“”)
        if(operOnline != null){
        	OPOperatorLog log = new OPOperatorLog();
            log.OperID = in.OperID;
            log.FunctionID = in.getFunctionID();
            log.OccurTime = sysDateTime;
            log.StationAddr = operOnline.LoginIPAddr;
            log.Remark = in.BPartnerName;

            commonDAO.addOperatorLog(log);
        }

        return result;
    }
}
