package com.dcdzsoft.business.im;

import java.util.UUID;

import javax.sql.RowSet;

import com.dcdzsoft.EduException;
import com.dcdzsoft.sda.db.*;
import com.dcdzsoft.sda.security.SecurityUtils;
import com.dcdzsoft.util.*;
import com.dcdzsoft.dto.function.*;
import com.dcdzsoft.dto.business.*;
import com.dcdzsoft.dao.*;
import com.dcdzsoft.dao.common.*;
import com.dcdzsoft.constant.*;
import com.dcdzsoft.business.ActionBean;

/**
 * <p>Title: 自提柜后台运营系统</p>
 * <p>Description: 电商伙伴信息增加 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class IMEcomPartnerAdd extends ActionBean
{

	/**
	 * @see 电商伙伴表进行扩展，作为Userkey的管理：包括为电商网站，POS系统，APP等分配elocker system API使用秘钥
	 * @param in
	 * @return
	 * @throws EduException
	 */
    public int doBusiness(InParamIMEcomPartnerAdd in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        int result = 0;

		//1.	验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(in.OperID)
			||StringUtils.isEmpty(in.EPartnerName)
			||StringUtils.isEmpty(in.Username)
			||StringUtils.isEmpty(in.UserType))//
			throw new EduException(ErrorCode.ERR_PARMERR);

		//2.	调用CommonDAO.isOnline(管理员编号)判断管理员是否在线。
		OPOperOnline operOnline = commonDAO.isOnline(in.OperID);

		//3.	调用UtilDAO.getCurrentDateTime()获得系统日期时间。
		java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();
		java.sql.Date sysDate = DateUtils.toSQLDate(sysDateTime);

		if(in.Remark == null){
			in.Remark = "";
		}
		
		//4.	调用IMEcommercePartnerDAO.isExist()判断电商伙伴名称是否存在，如果存在返回ERR_ECOMMERCEPARTNERNAMEEXISTS。
		IMEcommercePartnerDAO ecommercePartnerDAO = daoFactory.getIMEcommercePartnerDAO();

		/**
         * 作为Userkey的管理：包括为电商网站，POS系统，APP等分配elocker system API使用秘钥
         */
		in.Username = StringUtils.normalizeName(in.Username);
		JDBCFieldArray whereColsUsername = new JDBCFieldArray();
		whereColsUsername.add("Username", in.Username);
		
		switch(in.getUserType()){
		case SysDict.APP_USER_TYPE_ECOMMERCE_WEBSIT_NAME:
			// user key for ecommerce partner website
			//检查Username
	        if(!commonDAO.checkUserName(in.Username)){//UserID作为登录用户名
	        	throw new EduException(ErrorCode.ERR_USERNAMEEXISTS);
	        }
			in.Remark += "[type="+in.getUserType()+": "+SysDict.APP_USER_TYPE_ECOMMERCE_WEBSIT_NAME+"]";
			break;
		case SysDict.APP_USER_TYPE_POS_SYSTEM:
			//检查Username：POS#作为Username
	        if (ecommercePartnerDAO.isExist(whereColsUsername) > 0){
	            throw new EduException(ErrorCode.ERR_USERNAMEEXISTS);
	        }
			in.Remark += "[type="+in.getUserType()+": "+SysDict.APP_USER_TYPE_POS_SYSTEM_NAME+"]";
			break;
		case SysDict.APP_USER_TYPE_APP_POSTMAN:
			//检查Username：
	        if (ecommercePartnerDAO.isExist(whereColsUsername) > 0){
	            throw new EduException(ErrorCode.ERR_USERNAMEEXISTS);
	        }
			in.Remark += "[type="+in.getUserType()+": "+SysDict.APP_USER_TYPE_APP_POSTMAN_NAME+"]";
			break;
		case SysDict.APP_USER_TYPE_BUSINESS_WEBSIT:
			//检查Username：Business partner ID作为Username：Business输入
			IMBusinessPartnerDAO businessPartnerDAO = daoFactory.getIMBusinessPartnerDAO();

	        JDBCFieldArray whereCols0 = new JDBCFieldArray();
	        whereCols0.add("BPartnerID",  in.Username);
	        if (businessPartnerDAO.isExist(whereCols0) <= 0){
	            throw new EduException(ErrorCode.ERR_USERNOTEXIST);//
	        }
	        in.EPartnerID = in.Username;
			in.Remark += "[type="+in.getUserType()+": "+SysDict.APP_USER_TYPE_BUSINESS_WEBSIT_NAME+"]";
			break;
		default :
			if(!commonDAO.checkUserName(in.Username)){//UserID作为登录用户名
	        	throw new EduException(ErrorCode.ERR_USERNAMEEXISTS);
	        }
			break;
		}

        in.EPartnerName = StringUtils.normalizeName(in.EPartnerName);
        
        JDBCFieldArray whereCols3 = new JDBCFieldArray();
        whereCols3.add("EPartnerName", in.EPartnerName);
        if (ecommercePartnerDAO.isExist(whereCols3) > 0){
            throw new EduException(ErrorCode.ERR_ECOMMERCEPARTNERNAMEEXISTS);
        }
        
		//5.	生成电商伙伴编号。
        IMEcommercePartner obj = new IMEcommercePartner();
        
        if(StringUtils.isEmpty(in.EPartnerID)){
        	//10位编码
        	/*String maxID = "";
            int iMaxID = 0;
            
            JDBCFieldArray whereCols1 = new JDBCFieldArray();
            whereCols1.add("EPartnerID", "<>","9999999999");
            maxID = ecommercePartnerDAO.selectFunctions("MAX(EPartnerID)", whereCols1);
            
            if (StringUtils.isEmpty(maxID)) {
                obj.EPartnerID = "0000000001";
            } else {
            	iMaxID = NumberUtils.parseInt(maxID) + 1;
                obj.EPartnerID = StringUtils.leftPad(String.valueOf(iMaxID), 10, '0');
            }*/
        	obj.EPartnerID = "E"+RandUtils.generateString(9);
        }else{
        	obj.EPartnerID = in.EPartnerID;
        }
        
        if(ecommercePartnerDAO.isExist(obj)){
            throw new EduException(ErrorCode.ERR_ECOMMERCEPARTNEREXISTS);
        }
        
        if(!SysDict.ACTIVE_FLAG_YES.equals(in.Active)){
        	in.Active = SysDict.ACTIVE_FLAG_NO;
        }
        

        //#start生成接入方秘钥, 添加userKey映射
        APUserKeyMapDAO userKeyMapDAO = daoFactory.getAPUserKeyMapDAO();
        //UUID uuid = UUID.randomUUID();
        String userKey = "elapi"+RandUtils.generateString(15);//SecurityUtils.md5(uuid.toString());
        JDBCFieldArray whereCols2 = new JDBCFieldArray();
        whereCols2.add("UserKey", userKey);
        if(userKeyMapDAO.isExist(whereCols2)>0){
        	userKey = "elapi"+RandUtils.generateString(15);
        }
        //UUID uuid = UUID.randomUUID();
        //String userKey = SecurityUtils.md5(uuid.toString());//成接入方秘钥
        in.Password = userKey;
        
        APUserKeyMap userKeyMap = new APUserKeyMap();
        userKeyMap.KeyID = SecurityUtils.md5(userKey);//MD5加密作为Key编号
        userKeyMap.UserKey = userKey;
        userKeyMap.UserID  = obj.EPartnerID;
        userKeyMap.UserType = in.getUserType();//SysDict.APP_USER_TYPE_ECOMMERCE_WEBSIT;
        userKeyMap.CreateTime = sysDateTime;
        userKeyMapDAO.insert(userKeyMap);
        //#end
        
        in.Address = commonDAO.getAddressHtmlToText(in.Address);
        
		//6.	调用IMEcommercePartnerDAO.insert()插入电商伙伴信息。
        obj.EPartnerName = in.EPartnerName;
        obj.Address      = in.Address;
        obj.Email        = in.Email;
        obj.Mobile       = in.Mobile;
        obj.Username     = in.Username;
        obj.Password     = in.Password;
        obj.Active       = in.Active;
        obj.CreateTime   = sysDateTime;
        obj.Remark       = in.Remark;
        
        result = ecommercePartnerDAO.insert(obj);

        // 调用CommonDAO.addOperatorLog(OperID，功能编号，系统日期时间，“”)
        OPOperatorLog log = new OPOperatorLog();
        log.OperID = in.OperID;
        log.FunctionID = in.getFunctionID();
        log.OccurTime = sysDateTime;
        log.StationAddr = operOnline.LoginIPAddr;
        log.Remark = in.EPartnerName;

        commonDAO.addOperatorLog(log);

        return result;
    }
}
