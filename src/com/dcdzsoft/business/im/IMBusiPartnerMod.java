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
 * <p>Description: 商业伙伴信息修改 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class IMBusiPartnerMod extends ActionBean
{

    public int doBusiness(InParamIMBusiPartnerMod in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        int result = 0;

		//1.	验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(in.BPartnerID))
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

		//4.	调用IMBusinessPartnerDAO.isExist()判断商业伙伴名称是否存在，如果存在返回ERR_BUSINESSPARTNERNAMEEXISTS。
		IMBusinessPartnerDAO businessPartnerDAO = daoFactory.getIMBusinessPartnerDAO();
		
		JDBCFieldArray whereCols2 = new JDBCFieldArray();
        whereCols2.add("BPartnerID", in.BPartnerID);
        if(businessPartnerDAO.isExist(whereCols2) <= 0){
            throw new EduException(ErrorCode.ERR_BUSINESSPARTNERNOTEXISTS);
        }
        
		if(StringUtils.isNotEmpty(in.BPartnerName)){
			in.BPartnerName = StringUtils.normalizeName(in.BPartnerName);
			
			JDBCFieldArray whereCols0 = new JDBCFieldArray();
	        whereCols0.add("BPartnerName", in.BPartnerName);
	        whereCols0.add("BPartnerID", "<>", in.BPartnerID);
	        if (businessPartnerDAO.isExist(whereCols0) > 0){
	            throw new EduException(ErrorCode.ERR_BUSINESSPARTNERNAMEEXISTS);
	        }
		}
        
        
        if(StringUtils.isNotEmpty(in.CollectZoneID)){
        	IMCollectZoneDAO collectZoneDAO = daoFactory.getIMCollectZoneDAO();
            IMCollectZone collectZone = new IMCollectZone();
            collectZone.CollectZoneID = in.CollectZoneID;
            if(!collectZoneDAO.isExist(collectZone)){
            	throw new EduException(ErrorCode.ERR_COLLECTZONENOTEXISTS);
            }
        }        
        
        in.Address = commonDAO.getAddressHtmlToText(in.Address);
        
        if(in.Discount>100){
        	in.Discount = 100;
        }else if(in.Discount < 0){
        	in.Discount = 0;
        }
        in.CreditLimit = (in.CreditLimit<0?0:in.CreditLimit);
        if(!"1".equals(in.CreditFlag) && !"0".equals(in.CreditFlag)){
        	in.CreditFlag = "";
        }
        if(!"1".equals(in.ReturnServiceFlag) && !"0".equals(in.ReturnServiceFlag)){
        	in.CreditFlag = "";
        }
        if(!"1".equals(in.CollectionServiceFlag) && !"0".equals(in.CollectionServiceFlag)){
        	in.CollectionServiceFlag = "";
        }
        
		//5.	调用IMBusinessPartnerDAO. Update()更新商业伙伴信息。
        JDBCFieldArray setCols = new JDBCFieldArray();
        JDBCFieldArray whereCols = new JDBCFieldArray();
        setCols.checkAdd("BPartnerName", in.BPartnerName);
        setCols.checkAdd("CollectZoneID", in.CollectZoneID);
        setCols.checkAdd("Address", in.Address);
        setCols.checkAdd("Email", in.Email);
        setCols.checkAdd("Mobile", in.Mobile);
        //setCols.add("Username", in.Username);
        //setCols.add("Balance", in.Balance);
        setCols.checkAdd("CreditFlag", in.CreditFlag);
        setCols.add("CreditLimit", in.CreditLimit);
        setCols.add("Discount", in.Discount);
        setCols.checkAdd("CollectionServiceFlag", in.CollectionServiceFlag);
        setCols.checkAdd("ReturnServiceFlag", in.ReturnServiceFlag);
        setCols.add("LastModifyTime", sysDateTime);
        setCols.checkAdd("Remark", in.Remark);
        if(in.Latitude != 0.0){
        	setCols.add("Latitude", in.Latitude);
        }
        if(in.Longitude != 0.0){
        	setCols.add("Longitude", in.Longitude);
        } 

        whereCols.add("BPartnerID", in.BPartnerID);

        result = businessPartnerDAO.update(setCols, whereCols);

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
