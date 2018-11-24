package com.dcdzsoft.business.pm;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
 * <p>Description: 邮递员修改 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class PMPostmanMod extends ActionBean
{

    public int doBusiness(InParamPMPostmanMod in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        int result = 0;

		//1.	验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(in.OperID)
			||StringUtils.isEmpty(in.PostmanID)
			||StringUtils.isEmpty(in.PostmanName)
			)
			throw new EduException(ErrorCode.ERR_PARMERR);

		//2.	调用CommonDAO.isOnline(管理员编号)判断管理员是否在线。
		OPOperOnline operOnline = commonDAO.isOnline(in.OperID);
		
		in.PostmanName = StringUtils.normalizeName(in.PostmanName);
		
		switch(in.PostmanStatus){
        case "0"://正常
            break;
        case "1"://注销
            in.BindMobile = "";
            in.PostmanType = "";
            break;
        case "2"://未激活
            break;
        default:
            in.PostmanStatus = "0";
            break;
        }
		//3.	调用UtilDAO.getCurrentDateTime()获得系统日期时间。
		java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();
		java.sql.Date sysDate = DateUtils.toSQLDate(sysDateTime);

        //检查邮递员编号
        PMPostmanDAO postmanDAO = daoFactory.getPMPostmanDAO();
        PMPostman postman = new PMPostman();
        postman.PostmanID = in.PostmanID;
        postman = postmanDAO.find(postman);
        
        if(StringUtils.isNotEmpty(in.BindCardID) && !in.BindCardID.equals(postman.BindCardID))
        {
        	JDBCFieldArray whereCols0 = new JDBCFieldArray();
            whereCols0.add("BindCardID",in.BindCardID);

            if(postmanDAO.isExist(whereCols0) > 0)
                throw new EduException(ErrorCode.ERR_BUSINESS_EXISTSBINDINGCARD);
        }
        
        if(StringUtils.isNotEmpty(in.BindMobile) && !in.BindMobile.equals(postman.BindMobile))
        {
        	if(!commonDAO.isPhoneNumber(in.BindMobile))
        		throw new EduException(ErrorCode.ERR_BUSINESS_MOBILEFORMATERROR);
        	
        	JDBCFieldArray whereCols0 = new JDBCFieldArray();
            whereCols0.add("BindMobile",in.BindMobile);

            if(postmanDAO.isExist(whereCols0) > 0)
                throw new EduException(ErrorCode.ERR_BUSINESS_EXISTSBINDMOBILE);
            
            //记录新的绑定流水
            MBBindMobileWaterDAO bindMobileWaterDAO = daoFactory.getMBBindMobileWaterDAO();
            MBBindMobileWater bindMobileWater = new MBBindMobileWater();
            bindMobileWater.VerifyKey  = StringUtils.getUUID();
            bindMobileWater.BindMobile = in.BindMobile;
            bindMobileWater.PostmanID  = postman.PostmanID;
            bindMobileWater.LastModifyTime = sysDateTime;
            bindMobileWater.ExpiredTime    = new java.sql.Timestamp(0);
            bindMobileWater.Remark   = ""+in.PostmanName+","+in.Remark;
            bindMobileWaterDAO.insert(bindMobileWater);
        }
        
        if(StringUtils.isNotEmpty(postman.BindMobile) && !postman.BindMobile.equals(in.BindMobile)){
            //绑定手机失效
            MBBindMobileWaterDAO bindMobileWaterDAO = daoFactory.getMBBindMobileWaterDAO();
            JDBCFieldArray setCols1 = new JDBCFieldArray();
            JDBCFieldArray whereCols1 = new JDBCFieldArray();
            setCols1.add("ExpiredTime", sysDateTime);
            setCols1.add("LastModifyTime", sysDateTime);
            
            whereCols1.add("PostmanID",in.PostmanID);
            whereCols1.add("BindMobile",postman.BindMobile);
            whereCols1.add("ExpiredTime","<=", new java.sql.Timestamp(0));
            
            bindMobileWaterDAO.update(setCols1, whereCols1);
        }
        

        if("1".equals(in.InputMobileFlag)){
            in.InputMobileFlag = SysDict.INPUTMOBILE_FLAG_LOCAL;
        }
		//4.	调用IMPostmanDAO.Update()更新信息。
        JDBCFieldArray setCols = new JDBCFieldArray();
        JDBCFieldArray whereCols = new JDBCFieldArray();

        setCols.add("PostmanName",in.PostmanName);
        setCols.add("OfficeTel",in.OfficeTel);
        setCols.add("Mobile",in.Mobile);
        setCols.add("Email",in.Email);
        setCols.checkAdd("ContactAddress",in.ContactAddress);
        setCols.add("BindCardID", in.BindCardID);
        setCols.add("BindMobile", in.BindMobile);
        
        setCols.checkAdd("PostmanStatus",in.PostmanStatus);
        //修改操作员类型
        if(StringUtils.isNotEmpty(in.PostmanType)
        	&& !in.PostmanType.equals(postman.PostmanType)){
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
            default:
            	in.PostmanType = "";
            	in.DropRight = "";
                in.ReturnRight = "";
                in.CollectRight = "";
                in.DADRight = "";
            	break;
            }
        }else{
            in.PostmanType = "";
            in.DropRight = "";
            in.ReturnRight = "";
            in.CollectRight = "";
            in.DADRight = "";
        }
        setCols.checkAdd("DropRight", in.DropRight);
        setCols.checkAdd("ReturnRight", in.ReturnRight);
        setCols.checkAdd("CollectRight", in.CollectRight);
        setCols.checkAdd("DADRight", in.DADRight);
        setCols.checkAdd("PostmanType", in.PostmanType);
        setCols.checkAdd("InputMobileFlag",in.InputMobileFlag);
        setCols.add("LastModifyTime", sysDateTime);
        setCols.add("Remark",in.Remark);

        whereCols.add("PostmanID",in.PostmanID);

        result = postmanDAO.update(setCols,whereCols);

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
