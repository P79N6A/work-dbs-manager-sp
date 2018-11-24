package com.dcdzsoft.business.ap;

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
import com.dcdzsoft.business.api.Comm;

/**
 * <p>Title: 自提柜后台运营系统</p>
 * <p>Description: API用户Key及数据完整行验证 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class APIUserKeyAndDataVerity extends ActionBean
{
    public OutParamAPIUserKeyAndDataVerity doBusiness(InParamAPIUserKeyAndDataVerity in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        OutParamAPIUserKeyAndDataVerity out = new OutParamAPIUserKeyAndDataVerity();

		//#start 参数检测
		if (
			StringUtils.isEmpty(in.Keymd5)
			//||StringUtils.isEmpty(in.Datamd5)
			||StringUtils.isEmpty(in.RequestData))
			return null;
		//#end
		
		APUserKeyMapDAO userKeyMapDAO = daoFactory.getAPUserKeyMapDAO();
		APUserKeyMap userKeyMap = new APUserKeyMap();
		userKeyMap.KeyID = in.Keymd5;//KeyID=MD5(UserKey)
		
		try{
			userKeyMap = userKeyMapDAO.find(userKeyMap);
			//#start 取用户信息
			out.UserID = userKeyMap.UserID;
			out.UserType = userKeyMap.UserType;
			//#end
			
			switch(userKeyMap.UserType){
			case SysDict.APP_USER_TYPE_ECOMMERCE_WEBSIT:
			case SysDict.APP_USER_TYPE_APP_POSTMAN:
			case SysDict.APP_USER_TYPE_POS_SYSTEM:
			case SysDict.APP_USER_TYPE_BUSINESS_WEBSIT:
				IMEcommercePartnerDAO ecommercePartnerDAO = daoFactory.getIMEcommercePartnerDAO();

				IMEcommercePartner ecommercePartner = new IMEcommercePartner();		
				ecommercePartner.EPartnerID = userKeyMap.UserID;
				
				ecommercePartner = ecommercePartnerDAO.find(ecommercePartner);
				if(!"1".equals(ecommercePartner.Active)){//账号未激活
					return null;
				}
				break;
			}
			//#start 检查datamd5
			String privateKey = userKeyMap.UserKey;// 
	        String datamd5Check = Comm.md5(in.RequestData+privateKey);//
	        if(!datamd5Check.equals(in.Datamd5)){
	        	out.DataFalg = 0;//数据不完整或被破坏
	        }else{
	        	out.DataFalg = 1;
	        }
	        //#end
		}catch(EduException e){
			
			System.out.println(e.getMessage());
			return null;
		}
		
        return out;
    }
}
