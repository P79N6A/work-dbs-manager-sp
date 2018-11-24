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
 * <p>Description: 电商伙伴信息修改 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class IMEcomPartnerMod extends ActionBean
{

    public int doBusiness(InParamIMEcomPartnerMod in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        int result = 0;

		//1.	验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(in.OperID)
			||StringUtils.isEmpty(in.EPartnerID)
			||StringUtils.isEmpty(in.EPartnerName))
			throw new EduException(ErrorCode.ERR_PARMERR);

		//2.	调用CommonDAO.isOnline(管理员编号)判断管理员是否在线。
		OPOperOnline operOnline = commonDAO.isOnline(in.OperID);

		//3.	调用UtilDAO.getCurrentDateTime()获得系统日期时间。
		java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();

		//4.	调用IMEcommercePartnerDAO.isExist()判断电商伙伴名称是否存在，如果存在返回ERR_ECOMMERCEPARTNERNAMEEXISTS。
		IMEcommercePartnerDAO ecommercePartnerDAO = daoFactory.getIMEcommercePartnerDAO();

        JDBCFieldArray whereCols1 = new JDBCFieldArray();
        whereCols1.add("EPartnerName", in.EPartnerName);
        whereCols1.add("EPartnerID", "<>", in.EPartnerID);
        if (ecommercePartnerDAO.isExist(whereCols1) > 0){
            throw new EduException(ErrorCode.ERR_ECOMMERCEPARTNERNAMEEXISTS);
        }
        
        JDBCFieldArray whereCols0 = new JDBCFieldArray();
        whereCols0.add("Username", in.Username);
        whereCols0.add("EPartnerID", "<>", in.EPartnerID);
        if (ecommercePartnerDAO.isExist(whereCols0) > 0){
            throw new EduException(ErrorCode.ERR_ECOMMERCEPARTNERNAMEEXISTS);
        }
        //#start 更新需重新生成接入方秘钥, 更新UserKey映射
        //取原UserKey
        IMEcommercePartner partner = new IMEcommercePartner();
        partner.EPartnerID = in.EPartnerID;
        partner = ecommercePartnerDAO.find(partner);
        String userKeyOld = partner.Password;//记录旧的UserKey
        
        //生成新的Userkey
        APUserKeyMapDAO userKeyMapDAO = daoFactory.getAPUserKeyMapDAO();
        //UUID uuid = UUID.randomUUID();
        String userKey = "elapi"+RandUtils.generateString(15);//SecurityUtils.md5(uuid.toString());
        JDBCFieldArray whereCols2 = new JDBCFieldArray();
        whereCols2.add("UserKey", userKey);
        if(userKeyMapDAO.isExist(whereCols2)>0){
        	userKey = "elapi"+RandUtils.generateString(15);
        }
        in.Password = userKey;
        
        //删除原UserKey映射
        APUserKeyMap userKeyMap = new APUserKeyMap();
        userKeyMap.KeyID = SecurityUtils.md5(userKeyOld);
        userKeyMap = userKeyMapDAO.find(userKeyMap);
        userKeyMapDAO.delete(userKeyMap);
        
        //插入新UserKey映射
        userKeyMap.KeyID = SecurityUtils.md5(userKey);//MD5加密作为Key编号
        userKeyMap.UserKey = userKey;
        userKeyMap.UserID  = in.EPartnerID;
        //userKeyMap.UserType = SysDict.APP_USER_TYPE_ECOMMERCE_WEBSIT;
        //userKeyMap.CreateTime = sysDateTime;
        userKeyMap.LastModifyTime = sysDateTime;
        userKeyMapDAO.insert(userKeyMap);
        //#end
        
        switch(userKeyMap.UserType){
		case SysDict.APP_USER_TYPE_ECOMMERCE_WEBSIT_NAME:
			// user key for ecommerce partner website
			in.Remark = "[type="+userKeyMap.UserType+": "+SysDict.APP_USER_TYPE_ECOMMERCE_WEBSIT_NAME+"]";
			break;
		case SysDict.APP_USER_TYPE_POS_SYSTEM:
			in.Remark = "[type="+userKeyMap.UserType+": "+SysDict.APP_USER_TYPE_POS_SYSTEM_NAME+"]";
			break;
		case SysDict.APP_USER_TYPE_APP_POSTMAN:
			in.Remark = "[type="+userKeyMap.UserType+": "+SysDict.APP_USER_TYPE_APP_POSTMAN_NAME+"]";
			break;
		case SysDict.APP_USER_TYPE_BUSINESS_WEBSIT:
			in.Remark = "[type="+userKeyMap.UserType+": "+SysDict.APP_USER_TYPE_BUSINESS_WEBSIT_NAME+"]";
			break;
		default :
			break;
		}
        in.Address = commonDAO.getAddressHtmlToText(in.Address);
        
		//5.	调用IMEcommercePartnerDAO. Update()更新电商伙伴信息。
        JDBCFieldArray setCols = new JDBCFieldArray();
        JDBCFieldArray whereCols = new JDBCFieldArray();
        setCols.add("EPartnerName", in.EPartnerName);
        setCols.add("Address", in.Address);
        setCols.add("Email", in.Email);
        setCols.add("Mobile", in.Mobile);
        //setCols.add("Username", in.Username);//不允许修改用户名
        setCols.add("Password", in.Password);
        if(!StringUtils.isEmpty(in.Active)){
        	if(!SysDict.ACTIVE_FLAG_YES.equals(in.Active)){
            	in.Active = SysDict.ACTIVE_FLAG_NO;
            }
        	setCols.add("Active", in.Active);
        }
        //setCols.add("Remark", in.Remark);
        
        whereCols.add("EPartnerID", in.EPartnerID);

        result = ecommercePartnerDAO.update(setCols, whereCols);

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
