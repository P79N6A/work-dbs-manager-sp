package com.dcdzsoft.business.pm;

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
 * <p>Description: 邮递员删除 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class PMPostmanDel extends ActionBean
{

    public int doBusiness(InParamPMPostmanDel in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        int result = 0;

		//1.	验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(in.OperID)
			||StringUtils.isEmpty(in.PostmanID))
			throw new EduException(ErrorCode.ERR_PARMERR);

		//2.	调用CommonDAO.isOnline(管理员编号)判断管理员是否在线。
		OPOperOnline operOnline = commonDAO.isOnline(in.OperID);

		//3.	调用UtilDAO.getCurrentDateTime()获得系统日期时间。
		java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();

        //表关联检查，如果存在，不允许删除。
        
		MBBindMobileWaterDAO mobileWaterDAO = daoFactory.getMBBindMobileWaterDAO();
		JDBCFieldArray whereCols0 = new JDBCFieldArray();
        whereCols0.add("PostmanID",in.PostmanID);
        if(mobileWaterDAO.isExist(whereCols0) > 0){
            throw new EduException(ErrorCode.ERR_FORBIDDELPOSTMAN);
        }
        
        MBReminderWaterDAO reminderWarterDAO = daoFactory.getMBReminderWaterDAO();
        if(reminderWarterDAO.isExist(whereCols0) > 0){
            throw new EduException(ErrorCode.ERR_FORBIDDELPOSTMAN);
        }
        //在箱表
        PTInBoxPackageDAO inboxPackDAO = daoFactory.getPTInBoxPackageDAO();
        JDBCFieldArray whereCols0_1 = new JDBCFieldArray();
        whereCols0_1.add("DropAgentID",in.PostmanID);//PostmanID

        if(inboxPackDAO.isExist(whereCols0_1) > 0){
            throw new EduException(ErrorCode.ERR_FORBIDDELPOSTMAN);
        }
        //投递历史表
        PTDeliverHistoryDAO historyDAO = daoFactory.getPTDeliverHistoryDAO();
        if(historyDAO.isExist(whereCols0_1) > 0){
            throw new EduException(ErrorCode.ERR_FORBIDDELPOSTMAN);
        }
        //寄件历史表
        DMHistoryItemDAO dmHistoryDAO = daoFactory.getDMHistoryItemDAO();
        JDBCFieldArray whereCols0_3 = new JDBCFieldArray();
        whereCols0_3.add("CollectionAgentID",in.PostmanID);//PostmanID
        if(dmHistoryDAO.isExist(whereCols0_3)>0){
        	throw new EduException(ErrorCode.ERR_FORBIDDELPOSTMAN);
        }
        
		//5.	删除柜体权限。
        PMPostmanLockerRightDAO manTerminalRightDAO = daoFactory.getPMPostmanLockerRightDAO();
        JDBCFieldArray whereCols1 = new JDBCFieldArray();
        whereCols1.add("PostmanID", in.PostmanID);

        manTerminalRightDAO.delete(whereCols1);

		//6.	调用PMPostmanDAO.delete()删除投递员信息。
        PMPostmanDAO postmanDAO = daoFactory.getPMPostmanDAO();
        //JDBCFieldArray whereCols2 = new JDBCFieldArray();
        //whereCols2.add("PostmanID", in.PostmanID);
        //result = postmanDAO.delete(whereCols2);
        PMPostman postman = new PMPostman();
        postman.PostmanID = in.PostmanID;
		
        postman = postmanDAO.find(postman);
		
		result = postmanDAO.delete(postman);
        
        // 调用CommonDAO.addOperatorLog(OperID，功能编号，系统日期时间，“”)
        OPOperatorLog log = new OPOperatorLog();
        log.OperID = in.OperID;
        log.FunctionID = in.getFunctionID();
        log.OccurTime = sysDateTime;
        log.StationAddr = operOnline.LoginIPAddr;
        log.Remark = in.PostmanID;

        commonDAO.addOperatorLog(log);

        return result;
    }
}
