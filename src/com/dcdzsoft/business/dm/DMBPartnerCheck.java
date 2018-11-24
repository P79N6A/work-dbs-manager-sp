package com.dcdzsoft.business.dm;

import java.util.UUID;

import javax.sql.RowSet;

import com.dcdzsoft.EduException;
import com.dcdzsoft.sda.db.*;
import com.dcdzsoft.sms.SMSManager;
import com.dcdzsoft.util.*;
import com.dcdzsoft.dto.function.*;
import com.dcdzsoft.dto.business.*;
import com.dcdzsoft.dao.*;
import com.dcdzsoft.dao.common.*;
import com.dcdzsoft.config.ApplicationConfig;
import com.dcdzsoft.constant.*;
import com.dcdzsoft.business.ActionBean;

/**
 * <p>Title: 自提柜后台运营系统</p>
 * <p>Description: 验证BP密码 </p>
 * <p>Copyright: Copyright (c) 2015</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class DMBPartnerCheck extends ActionBean
{

    public int doBusiness(String BPartnerID, String Password) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        int result =0;
        
		//1.	验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(BPartnerID)
			||StringUtils.isEmpty(Password))
			throw new EduException(ErrorCode.ERR_PARMERR);
		
		//////商业合作伙伴检查
		IMBusinessPartnerDAO businessPartnerDAO = daoFactory.getIMBusinessPartnerDAO();
		JDBCFieldArray whereCols = new JDBCFieldArray();
    	whereCols.add("BPartnerID", BPartnerID);
    	whereCols.add("Password", Password);
    	if(businessPartnerDAO.isExist(whereCols) <=0){
    		throw new EduException(ErrorCode.ERR_WRONGPWDORUSERID);//
    	}
        return result;
    }

}
