package com.dcdzsoft.business.mb;

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
 * <p>Description: 发送离线警报信息 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class MBSendOfflineInfo extends ActionBean
{

    public int doBusiness(InParamMBSendOfflineInfo in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        int result = 0;

		//3.调用UtilDAO.getCurrentDateTime()获得系统日期时间。
		java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();
		java.sql.Date sysDate = DateUtils.toSQLDate(sysDateTime);

		///
		long delayInMs = (1000*60*Constant.LOCKER_OFFLINE_DELAY_MINUTES);//延迟10分钟，发送离线通知消息
		long timestamp1 = sysDateTime.getTime()-delayInMs;
		long timestamp2   = sysDateTime.getTime()-delayInMs*2;
		
		//查询柜体签到信息
		MBSignInfoDAO signInfoDAO = daoFactory.getMBSignInfoDAO();
    	JDBCFieldArray whereCols = new JDBCFieldArray();
    	
    	whereCols.add("TerminalNo", in.TerminalNo);
    	whereCols.add("OnlineStatus", "0");//0-离线
    	whereCols.add("LastModifyTime","<=", new java.sql.Timestamp(timestamp1));
    	whereCols.add("LastModifyTime",">", new java.sql.Timestamp(timestamp2));//在离线10~20分钟内发送一次，之后不再发送离线消息
    	
    	if(signInfoDAO.isExist(whereCols)<=0){
    		return 0;
    	}
    	
    	//更新离线的时间，防止主备服务器发送多次
    	JDBCFieldArray setCols1 = new JDBCFieldArray();
    	JDBCFieldArray whereCols1 = new JDBCFieldArray();

    	setCols1.add("LastModifyTime", new java.sql.Timestamp(timestamp2));
    	
    	whereCols1.add("OnlineStatus", "0");//0-离线
    	whereCols1.add("TerminalNo", in.TerminalNo);
    	whereCols1.add("LastModifyTime",">", new java.sql.Timestamp(timestamp2));

    	signInfoDAO.update(setCols1, whereCols1);
    	
        try{
        	commonDAO.doSendAlarmInfo(0,in.TerminalNo, 0);
        }catch(EduException e){}
	    
        return result;
    }
}
