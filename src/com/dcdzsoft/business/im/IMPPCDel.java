package com.dcdzsoft.business.im;

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
 * <p>Description: 包裹处理中心删除 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class IMPPCDel extends ActionBean
{

    public int doBusiness(InParamIMPPCDel in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        int result = 0;

		//1.	验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(in.OperID)
			||StringUtils.isEmpty(in.PPCID))
			throw new EduException(ErrorCode.ERR_PARMERR);

        //2.	调用CommonDAO.isOnline(管理员编号)判断管理员是否在线。
        OPOperOnline operOnline = commonDAO.isOnline(in.OperID);

        //3.	调用UtilDAO.getCurrentDateTime()获得系统日期时间。
		java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();
		
		//默认PPC不允许删除
		if(in.PPCID.equals(Constant.DEFAULT_PPC_ID)){
			throw new EduException(ErrorCode.ERR_FORBIDDELPPC);
		}
		
		//#start 表关联检查
		//揽件订单表
        DMCollectionParcelDAO collectDAO = daoFactory.getDMCollectionParcelDAO();
        JDBCFieldArray whereCols0 = new JDBCFieldArray();
        whereCols0.add("PPCID",in.PPCID);
        if(collectDAO.isExist(whereCols0)>0){
        	throw new EduException(ErrorCode.ERR_FORBIDDELETE);
        }
		//揽件历史表
        DMHistoryItemDAO dmHistoryDAO = daoFactory.getDMHistoryItemDAO();
        if(dmHistoryDAO.isExist(whereCols0)>0){
        	throw new EduException(ErrorCode.ERR_FORBIDDELETE);
        }
        //PTDeliverItemTransfer
        PTDeliverItemTransferDAO transferDAO = daoFactory.getPTDeliverItemTransferDAO();
        if(transferDAO.isExist(whereCols0)>0){
        	throw new EduException(ErrorCode.ERR_FORBIDDELETE);
        }
        //待投递表
        PTReadyPackageDAO readyPackDAO = daoFactory.getPTReadyPackageDAO();
        if(readyPackDAO.isExist(whereCols0) > 0){
            throw new EduException(ErrorCode.ERR_FORBIDDELETE);
        }
        //在箱表
        PTInBoxPackageDAO inboxPackDAO = daoFactory.getPTInBoxPackageDAO();
        if(inboxPackDAO.isExist(whereCols0) > 0){
            throw new EduException(ErrorCode.ERR_FORBIDDELETE);
        }
        //投递历史表
        PTDeliverHistoryDAO historyDAO = daoFactory.getPTDeliverHistoryDAO();
        if(historyDAO.isExist(whereCols0) > 0){
            throw new EduException(ErrorCode.ERR_FORBIDDELETE);
        }
        //#end
		        
		//4.	调用IMPostalProcessingCenterDAO.delete()删除包裹处理中心信息。
        IMPostalProcessingCenterDAO postalProcessingCenterDAO = daoFactory.getIMPostalProcessingCenterDAO();
        IMPostalProcessingCenter ppc = new IMPostalProcessingCenter();
        ppc.PPCID = in.PPCID;
		
        ppc = postalProcessingCenterDAO.find(ppc);

        result = postalProcessingCenterDAO.delete(ppc);
        
        //#start 删除UserKey映射
        APUserKeyMapDAO userKeyMapDAO = daoFactory.getAPUserKeyMapDAO();
        JDBCFieldArray whereCols2 = new JDBCFieldArray();
        whereCols2.add("UserKey", ppc.Password);
        userKeyMapDAO.delete(whereCols2);
        //#end
        
		// 调用CommonDAO.addOperatorLog(OperID，功能编号，系统日期时间，“”)
        OPOperatorLog log = new OPOperatorLog();
        log.OperID = in.OperID;
        log.FunctionID = in.getFunctionID();
        log.OccurTime = sysDateTime;
        log.StationAddr = operOnline.LoginIPAddr;
        log.Remark = in.PPCID;

        commonDAO.addOperatorLog(log);

        return result;
    }
}
