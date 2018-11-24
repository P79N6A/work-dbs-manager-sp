package com.dcdzsoft.business.pt;

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
 * <p>Description: 修改订单数据发送状态 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class PTTransferUpdateItemDel extends ActionBean
{
	/**
     * 删除已发送订单更新状态的数据
     * @return
     * @throws EduException
     */
    public int doBusiness(PTTransferUpdateItemDel in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        int result = 0;
        
        //3.	调用UtilDAO.getSysDateTime()获得系统日期时间。
      	java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();
      	java.sql.Date sysDate = DateUtils.toSQLDate(sysDateTime);
      	
      	PTDeliverItemTransferDAO itemTransferDAO = daoFactory.getPTDeliverItemTransferDAO();
        //定期删除订单
        int retainDays = NumberUtils.parseInt(ControlParam.getInstance().getTransferItemRetainDays());
        if(retainDays<15){
            retainDays = 15;
        }
        
        //删除发送成功的记录，发送成功记录至少保留1天
        JDBCFieldArray whereCols1 = new JDBCFieldArray();
        whereCols1.add("TransferStatus", SysDict.TRANSFER_STATUS_SUCCESS);
        whereCols1.add("LastModifyTime", "<=", DateUtils.addTimeStamp(sysDateTime, -(retainDays)));
        result = itemTransferDAO.delete(whereCols1);
      	
        int ReSendNum = NumberUtils.parseInt(ControlParam.getInstance().getTransferResendTimes());
        if(ReSendNum<15){
            ReSendNum = 15;
        }
        //
		JDBCFieldArray whereCols = new JDBCFieldArray();
		whereCols.add("ReSendNum", ">=", ReSendNum);
		whereCols.add("LastModifyTime", "<=", DateUtils.addTimeStamp(sysDateTime, -(retainDays)));
		//
		if(itemTransferDAO.isExist(whereCols)>0){
			itemTransferDAO.delete(whereCols);
		}
        return result;
    }
}
