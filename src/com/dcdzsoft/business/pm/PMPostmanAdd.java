package com.dcdzsoft.business.pm;

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
 * <p>Title: 自提柜后台运营系统</p>
 * <p>Description: 邮递员信息增加 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class PMPostmanAdd extends ActionBean
{

    public int doBusiness(InParamPMPostmanAdd in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        int result = 0;

		//1.	验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(in.OperID)
			||StringUtils.isEmpty(in.ZoneID)
			||StringUtils.isEmpty(in.PostmanName)
			||StringUtils.isEmpty(in.PostmanType))
			throw new EduException(ErrorCode.ERR_PARMERR);

        
		//2.	调用CommonDAO.isOnline(管理员编号)判断管理员是否在线。
		OPOperOnline operOnline = commonDAO.isOnline(in.OperID);

		//3.	调用UtilDAO.getCurrentDateTime()获得系统日期时间。
		java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();
		java.sql.Date sysDate = DateUtils.toSQLDate(sysDateTime);

        //4.	调用IMZoneDAO.isExist()判断分拣区域编号是否存在，如果不存在返回ERR_IMZONENODATA。
		IMZoneDAO zoneDAO = daoFactory.getIMZoneDAO();
		IMZone zone = new IMZone();
		zone.ZoneID = in.ZoneID;
		
        if (!zoneDAO.isExist(zone)){
            throw new EduException(ErrorCode.ERR_ZONEOFPOSTMANNOTEXISTS);
        }
        
        //揽件区域
        if(StringUtils.isNotEmpty(in.CollectZoneID)){
        	IMCollectZoneDAO collectZoneDAO = daoFactory.getIMCollectZoneDAO();
        	IMCollectZone collectZone = new IMCollectZone();
        	collectZone.CollectZoneID = in.CollectZoneID;
        	if (!collectZoneDAO.isExist(collectZone)){
        		in.CollectZoneID = "";
            }
        }
        
        in.PostmanName = StringUtils.normalizeName(in.PostmanName);
		//5.	生成邮递员编号。
        PMPostmanDAO postmanDAO = daoFactory.getPMPostmanDAO();
        PMPostman obj = new PMPostman();
        
        if(StringUtils.isEmpty(in.PostmanID)){
        	//10位：AZC#+xxxxx
        	/*String maxID = "";
            int iMaxID   = 0;
        	JDBCFieldArray whereCols1 = new JDBCFieldArray();
	        //whereCols1.add("ZoneID", in.ZoneID);
	        maxID = postmanDAO.selectFunctions("MAX(PostmanID)", whereCols1);
	        if (StringUtils.isEmpty(maxID)) {
	            obj.PostmanID = in.ZoneID +  "00001";
	        } else {
	        	if(NumberUtils.parseInt(maxID)>0){
	        		iMaxID = NumberUtils.parseInt(maxID) + 1;
		            obj.PostmanID = StringUtils.leftPad(String.valueOf(iMaxID), 10, '0');
	        	}else{
	        		String postmanID = in.ZoneID+RandUtils.generateString(5);//new String(in.ZoneID+RandUtils.generateString(5));
	            	obj.PostmanID = postmanID;
	        	}
	        }*/
            String postmanID = in.ZoneID+RandUtils.generateString(5);//new String(in.ZoneID+RandUtils.generateString(5));
            obj.PostmanID = postmanID;
        }else{
        	obj.PostmanID = in.PostmanID;
        }
        
        if(postmanDAO.isExist(obj)){
            throw new EduException(ErrorCode.ERR_POSTMANEXISTS);
        }
        
        //绑定卡号验证
        if(StringUtils.isNotEmpty(in.BindCardID))
        {
        	JDBCFieldArray whereCols0 = new JDBCFieldArray();
            whereCols0.add("BindCardID",in.BindCardID);

            if(postmanDAO.isExist(whereCols0) > 0)
                throw new EduException(ErrorCode.ERR_BUSINESS_EXISTSBINDINGCARD);
        }
        
        if(StringUtils.isNotEmpty(in.BindMobile))
        {
        	if(!commonDAO.isPhoneNumber(in.BindMobile))
        		throw new EduException(ErrorCode.ERR_BUSINESS_MOBILEFORMATERROR);
        	
        	JDBCFieldArray whereCols0 = new JDBCFieldArray();
            whereCols0.add("BindMobile",in.BindMobile);

            if(postmanDAO.isExist(whereCols0) > 0)
                throw new EduException(ErrorCode.ERR_BUSINESS_EXISTSBINDMOBILE);
            
            //绑定手机流水
            MBBindMobileWaterDAO bindMobileWaterDAO = daoFactory.getMBBindMobileWaterDAO();
            MBBindMobileWater bindMobileWater = new MBBindMobileWater();
            bindMobileWater.VerifyKey  = StringUtils.getUUID();
            bindMobileWater.BindMobile = in.BindMobile;
            bindMobileWater.PostmanID  = obj.PostmanID;
            bindMobileWater.LastModifyTime = sysDateTime;
            bindMobileWater.ExpiredTime    = new java.sql.Timestamp(0);
            bindMobileWater.Remark   = ""+in.PostmanName+","+in.Remark;
            bindMobileWaterDAO.insert(bindMobileWater);
        }

        //生成登入密码
        if(StringUtils.isEmpty(in.Password)){
        	in.Password = RandUtils.generateNumber(4);//4位密码，沙特要求
        }
        
        if(StringUtils.isEmpty(in.InputMobileFlag)){
            in.InputMobileFlag = SysDict.INPUTMOBILE_FLAG_LOCAL;
        }
        switch(in.PostmanType){
        case SysDict.POSTMAN_TYPE_POST:
            if(!"0".equals(in.DropRight) && !"1".equals(in.DropRight)){
                in.DropRight = SysDict.POSTMAN_RIGHT_YES;
            }
            
            if(!"0".equals(in.ReturnRight) && !"1".equals(in.ReturnRight)){
                in.ReturnRight = SysDict.POSTMAN_RIGHT_YES;
            }
            if(!"0".equals(in.CollectRight) && !"1".equals(in.CollectRight)){
                in.CollectRight = SysDict.POSTMAN_RIGHT_NO; 
            }
            in.DADRight = SysDict.POSTMAN_RIGHT_NO;
        	break;
        case SysDict.POSTMAN_TYPE_DAD:
        case SysDict.POSTMAN_TYPE_DAD_BUSINESSPARTNER:
        case SysDict.POSTMAN_TYPE_DAD_SP:
            if(!"0".equals(in.DADRight) && !"1".equals(in.DADRight)){
                in.DADRight     = SysDict.POSTMAN_RIGHT_YES;
            }
            if(!"0".equals(in.ReturnRight) && !"1".equals(in.ReturnRight)){
                in.ReturnRight  = SysDict.POSTMAN_RIGHT_NO;
            }
            in.DropRight    = SysDict.POSTMAN_RIGHT_NO;
            in.CollectRight = SysDict.POSTMAN_RIGHT_NO;
            if(SysDict.POSTMAN_TYPE_DAD_SP.equals(in.PostmanType)){
                in.InputMobileFlag = SysDict.INPUTMOBILE_FLAG_NETWORK;//订单转入标志，网络获取用户手机号
            }
        	break;
        case SysDict.POSTMAN_TYPE_RETURN:
        	in.DADRight     = SysDict.POSTMAN_RIGHT_NO;
        	if(!"0".equals(in.ReturnRight) && !"1".equals(in.ReturnRight)){
        	    in.ReturnRight  = SysDict.POSTMAN_RIGHT_YES;
        	}
        	in.DropRight    = SysDict.POSTMAN_RIGHT_NO;
        	in.CollectRight = SysDict.POSTMAN_RIGHT_NO;
        	break;
        case SysDict.POSTMAN_TYPE_TRANSFER:  //转运人员
        	in.DADRight     = SysDict.POSTMAN_RIGHT_NO;
        	in.ReturnRight  = SysDict.POSTMAN_RIGHT_NO;
        	in.DropRight    = SysDict.POSTMAN_RIGHT_NO;
        	in.CollectRight = SysDict.POSTMAN_RIGHT_NO;
        	break;
        default:
        	in.PostmanType = SysDict.POSTMAN_TYPE_POST;
        	in.DADRight     = SysDict.POSTMAN_RIGHT_NO;
        	in.ReturnRight  = SysDict.POSTMAN_RIGHT_NO;
        	in.DropRight    = SysDict.POSTMAN_RIGHT_NO;
        	in.CollectRight = SysDict.POSTMAN_RIGHT_NO;
        	break;
        }
		//6.	调用PMPostmanDAO.insert()插入邮递员信息。
        obj.ZoneID      = in.ZoneID;
        obj.CollectZoneID = in.CollectZoneID;
        obj.PostmanName = in.PostmanName;
        obj.PostmanType = in.PostmanType;
        obj.Password    = SecurityUtils.md5(in.Password); //MD5 加密
        obj.OfficeTel   = in.OfficeTel;
        obj.BindCardID  = in.BindCardID;
        obj.BindMobile  = in.BindMobile;
        obj.Mobile      = in.Mobile;
        obj.Email       = in.Email;                    
        obj.ContactAddress = in.ContactAddress;
        obj.InputMobileFlag = in.InputMobileFlag;
        obj.PostmanStatus = "0";
        obj.DropRight     = in.DropRight;
        obj.ReturnRight   = in.ReturnRight;
        obj.CollectRight  = in.CollectRight;
        obj.DADRight      = in.DADRight;
        obj.CreateManID   = in.OperID;
        obj.Remark        = in.Remark;
        obj.CreateTime    = sysDateTime;
        obj.CreateDate    = sysDate;

        result = postmanDAO.insert(obj);
        
        //发送短信密码
        ControlParam ctrlParam = ControlParam.getInstance();
        if(StringUtils.isNotEmpty(ApplicationConfig.getInstance().getSendShortMsg()) 
        		&&StringUtils.isNotEmpty(in.BindMobile)
        		&&"1".equals(ctrlParam.getRegSentPwdSms()))
        {
        	SMSManager.getInstance().sentPostManPwd(in.Password,in.BindMobile);
        }

        // 调用CommonDAO.addOperatorLog(OperID，功能编号，系统日期时间，“”)
        OPOperatorLog log = new OPOperatorLog();
        log.OperID = in.OperID;
        log.FunctionID = in.getFunctionID();
        log.OccurTime = sysDateTime;
        log.StationAddr = operOnline.LoginIPAddr;
        log.Remark = in.PostmanName;

        commonDAO.addOperatorLog(log);

        return result;
    } 
}
