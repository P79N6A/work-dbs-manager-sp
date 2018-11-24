package com.dcdzsoft.business.ap;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import com.dcdzsoft.config.ApplicationConfig;
import com.dcdzsoft.constant.*;
import com.dcdzsoft.business.ActionBean;

/**
 * <p>Title: 智能自助包裹柜系统</p>
 * <p>Description: 投递员注册 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author 王中立
 * @version 1.0
 */

public class APPostmanRegister extends ActionBean
{

    public String doBusiness(InParamAPPostmanRegister in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        
		//1.	验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(in.Mobile)
			||StringUtils.isEmpty(in.PostmanName)
			//||StringUtils.isEmpty(in.CompanyID)
			||StringUtils.isEmpty(in.ZoneID)
			||StringUtils.isEmpty(in.Password))
			throw new EduException(ErrorCode.ERR_PARMERR);

		if(!commonDAO.isPhoneNumber(in.Mobile))
			throw new EduException(ErrorCode.ERR_BUSINESS_MOBILEFORMATERROR);
		
		//3.	调用UtilDAO.getCurrentDateTime()获得系统日期时间。
		java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();
		java.sql.Date sysDate = DateUtils.toSQLDate(sysDateTime);
		
		in.PostmanName = StringUtils.normalizeName(in.PostmanName);
        ///////////////////生成投递员编号///////////////////////////
        PMPostmanDAO postmanDAO = daoFactory.getPMPostmanDAO();
        PMPostman obj = new PMPostman();
        obj.PostmanID = in.Mobile;
        
        if(postmanDAO.isExist(obj))
    		throw new EduException(ErrorCode.ERR_OPERHAVEEXIST);
        
        IMZoneDAO zoneDAO = daoFactory.getIMZoneDAO();
		IMZone zone = new IMZone();
		zone.ZoneID = in.ZoneID;
		
		if (!zoneDAO.isExist(zone)){
            throw new EduException(ErrorCode.ERR_IMZONENODATA);
        }
		
        if(StringUtils.isNotEmpty(in.IDCard))
        {
        	JDBCFieldArray whereCols0 = new JDBCFieldArray();
            whereCols0.add("BindCardID",in.IDCard);

            if(postmanDAO.isExist(whereCols0) > 0)
                throw new EduException(ErrorCode.ERR_BUSINESS_EXISTSBINDINGCARD);
        }
        
        obj.BindCardID = in.IDCard;
        
        String pwd = "";
        if(StringUtils.isEmpty(in.Password))
        	pwd = SecurityUtils.md5(RandUtils.generateNumber(6));
        else
        	pwd = in.Password;
        
        //插入投递员信息
        obj.ZoneID = in.ZoneID;
        obj.PostmanName = in.PostmanName;
        obj.PostmanType = SysDict.POSTMAN_TYPE_POST;
        obj.Password = pwd;
        obj.OfficeTel = "";
        obj.Mobile = in.Mobile;
        obj.Email = "";
        obj.IDCard = in.IDCard;
        obj.InputMobileFlag = SysDict.INPUTMOBILE_FLAG_LOCAL;
        obj.PostmanStatus = "0";  //注册未激活
        obj.Remark = "";
        obj.DropRight     = "1";
        obj.ReturnRight   = "1";
        obj.CollectRight  = "0";
        obj.DADRight      = "0";
        obj.CreateManID   = "181818";
        obj.CreateTime    = sysDateTime;
        obj.CreateDate    = sysDate;

        postmanDAO.insert(obj);

		// 调用CommonDAO.addOperatorLog(OperID，功能编号，系统日期时间，“”)
		OPOperatorLog log = new OPOperatorLog();
		log.OperID = in.Mobile;
		log.FunctionID = in.getFunctionID();
		log.OccurTime = sysDateTime;
		log.StationAddr = "";
		log.Remark = "";

		commonDAO.addOperatorLog(log);


        return in.Mobile;
    }
     
}
