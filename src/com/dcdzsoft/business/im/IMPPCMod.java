package com.dcdzsoft.business.im;


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
 * <p>Description: 包裹处理中心信息修改 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class IMPPCMod extends ActionBean
{

    public int doBusiness(InParamIMPPCMod in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        int result = 0;

		//1.	验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(in.OperID)
			||StringUtils.isEmpty(in.PPCID)
			||StringUtils.isEmpty(in.PPCName))
			throw new EduException(ErrorCode.ERR_PARMERR);

		in.PPCName = StringUtils.normalizeName(in.PPCName);
		
		//2.	调用CommonDAO.isOnline(管理员编号)判断管理员是否在线。
		OPOperOnline operOnline = commonDAO.isOnline(in.OperID);

		//3.	调用UtilDAO.getCurrentDateTime()获得系统日期时间。
		java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();
		java.sql.Date sysDate = DateUtils.toSQLDate(sysDateTime);

        //4.	调用IMPostalProcessingCenterDAO.isExist()判断包裹处理中心名称是否存在，如果存在返回ERR_PPCNAMEEXISTS。
        IMPostalProcessingCenterDAO postalProcessingCenterDAO = daoFactory.getIMPostalProcessingCenterDAO();

        JDBCFieldArray whereCols00 = new JDBCFieldArray();
        whereCols00.add("PPCName", in.PPCName);
        whereCols00.add("PPCID", "<>", in.PPCID);
        if (postalProcessingCenterDAO.isExist(whereCols00) > 0){
            throw new EduException(ErrorCode.ERR_PPCNAMEEXISTS);
        }
        
        
        IMPostalProcessingCenter ppc = new IMPostalProcessingCenter();
        ppc.PPCID = in.PPCID;
        ppc = postalProcessingCenterDAO.find(ppc);
        if(StringUtils.isEmpty(in.Password) || !in.Password.equals(ppc.Password)){//重新生成UserKey
            //#start 更新需重新生成接入方秘钥, 更新UserKey映射
            //取原UserKey
            String userKeyOld = ppc.Password;//记录旧的UserKey
            //删除原UserKey映射
            APUserKeyMapDAO userKeyMapDAO = daoFactory.getAPUserKeyMapDAO();
            JDBCFieldArray whereCols2 = new JDBCFieldArray();
            whereCols2.add("UserKey", userKeyOld);
            userKeyMapDAO.delete(whereCols2);
            
            //生成新的Userkey
            String userKey = SecurityUtils.md5(StringUtils.getUUID());
            in.Password = userKey;
            
            //插入新UserKey映射
            APUserKeyMap userKeyMap = new APUserKeyMap();
            userKeyMap.KeyID = SecurityUtils.md5(userKey);//二次加密作为Key编号
            userKeyMap.UserKey = userKey;
            userKeyMap.UserID  = in.PPCID;
            userKeyMap.UserType = SysDict.APP_USER_TYPE_PPC_SERVER;
            userKeyMap.CreateTime = sysDateTime;
            userKeyMapDAO.insert(userKeyMap);
            //#end
        }
        
        
		//5.	调用IMPostalProcessingCenterDAO. Update()更新包裹处理中心信息。
        JDBCFieldArray setCols = new JDBCFieldArray();
        JDBCFieldArray whereCols1 = new JDBCFieldArray();
        setCols.add("PPCName", in.PPCName);
        setCols.add("Password", in.Password);
        setCols.add("Username", in.Username);
        setCols.add("PPCServerIP", in.PPCServerIP);
        setCols.add("PPCServerURL", in.PPCServerURL);
        setCols.add("PPCServerPassword", in.PPCServerPassword);
        setCols.add("PPCServerUsername", in.PPCServerUsername);
        setCols.add("Remark", in.Remark);

        whereCols1.add("PPCID", in.PPCID);

        result = postalProcessingCenterDAO.update(setCols, whereCols1);
        
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
