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
 * <p>Description: 包裹服务中心增加 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class IMPPCAdd extends ActionBean
{

    public int doBusiness(InParamIMPPCAdd in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        int result = 0;

		//1.	验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(in.OperID)
			||StringUtils.isEmpty(in.PPCName)
			||StringUtils.isEmpty(in.CompanyID))
			throw new EduException(ErrorCode.ERR_PARMERR);

		//2.	调用CommonDAO.isOnline(管理员编号)判断管理员是否在线。
		OPOperOnline operOnline = commonDAO.isOnline(in.OperID);

		IMCompanyDAO companyDAO = daoFactory.getIMCompanyDAO();
		IMCompany company = new IMCompany();
		company.CompanyID = in.CompanyID;
		if(!companyDAO.isExist(company)){
			throw new EduException(ErrorCode.ERR_IMCOMPANYNODATA);
		}
		
		//3.	调用UtilDAO.getCurrentDateTime()获得系统日期时间。
		java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();
		java.sql.Date sysDate = DateUtils.toSQLDate(sysDateTime);

		//4.	调用IMPostalProcessingCenterDAO.isExist()判断包裹处理中心名称是否存在，如果存在返回ERR_PPCNAMEEXISTS。
		IMPostalProcessingCenterDAO postalProcessingCenterDAO = daoFactory.getIMPostalProcessingCenterDAO();
		
		in.PPCName = StringUtils.normalizeName(in.PPCName);
        
        JDBCFieldArray whereCols0 = new JDBCFieldArray();
        whereCols0.add("PPCName", in.PPCName);
        //whereCols0.add("CompanyID", in.CompanyID);//同一服务商的包裹处理中心名称不允许相同
        if (postalProcessingCenterDAO.isExist(whereCols0) > 0)
            throw new EduException(ErrorCode.ERR_PPCNAMEEXISTS);

		//5.	生成包裹处理中心编号。
        IMPostalProcessingCenter obj = new IMPostalProcessingCenter();
        
        if(StringUtils.isEmpty(in.PPCID)){
        	//5位：CompanyID+xxx
        	String maxID = "";
            int iMaxID = 0;
            
            JDBCFieldArray whereCols1 = new JDBCFieldArray();
	        whereCols1.add("CompanyID", in.CompanyID);
            maxID = postalProcessingCenterDAO.selectFunctions("MAX(PPCID)", whereCols1);
            
            if (StringUtils.isEmpty(maxID)) {
                obj.PPCID = in.CompanyID +  "001";
            } else {
            	iMaxID = NumberUtils.parseInt(maxID) + 1;
                obj.PPCID = StringUtils.leftPad(String.valueOf(iMaxID), 5, '0');
            }
        	/*obj.PPCID = RandUtils.generateNumber(5);*/
        }else{
        	obj.PPCID = in.PPCID;
        }
        
        if(postalProcessingCenterDAO.isExist(obj)){
            throw new EduException(ErrorCode.ERR_PPCEXISTS);
        }
        
        //#start生成接入方秘钥, 添加userKey映射
        UUID uuid = UUID.randomUUID();
        String userKey = SecurityUtils.md5(uuid.toString());//成接入方秘钥
        in.Password = userKey;
        
        APUserKeyMapDAO userKeyMapDAO = daoFactory.getAPUserKeyMapDAO();
        APUserKeyMap userKeyMap = new APUserKeyMap();
        userKeyMap.KeyID = SecurityUtils.md5(userKey);//二次加密作为Key编号
        userKeyMap.UserKey = userKey;
        userKeyMap.UserID  = obj.PPCID;
        userKeyMap.UserType = SysDict.APP_USER_TYPE_PPC_SERVER;
        userKeyMap.CreateTime = sysDateTime;
        userKeyMapDAO.insert(userKeyMap);
        //#end
        
		//6.	调用IMPostalProcessingCenterDAO.insert()插入服务商信息。
        obj.PPCName      = in.PPCName;
        obj.CompanyID    = in.CompanyID;
        obj.Username     = in.Username;
        obj.Password     = in.Password; 
        obj.PPCServerIP  = in.PPCServerIP;
        obj.PPCServerURL = in.PPCServerURL;
        obj.PPCServerUsername = in.PPCServerUsername;
        obj.PPCServerPassword = in.PPCServerPassword;
        obj.CreateTime   = sysDateTime;
        obj.Remark       = in.Remark;
        
        result = postalProcessingCenterDAO.insert(obj);
        
        
		// 调用CommonDAO.addOperatorLog(OperID，功能编号，系统日期时间，“”)
		OPOperatorLog log = new OPOperatorLog();
		log.OperID = in.OperID;
		log.FunctionID = in.getFunctionID();
		log.OccurTime = sysDateTime;
		log.StationAddr = operOnline.LoginIPAddr;
		log.Remark = in.PPCName;

		commonDAO.addOperatorLog(log);

        return result;
    }
}
