package com.dcdzsoft.business.pt;

import java.util.Calendar;

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

public class PTTransferItemModFailReSend extends ActionBean
{

	/**
     * 修改发送失败的状态，以便重新发送
     * @return
     * @throws EduException
     */
    public int doBusiness(PTTransferItemModFailReSend in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        int result = 0;

		//3.	调用UtilDAO.getSysDateTime()获得系统日期时间。
		java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();
		java.sql.Date sysDate = DateUtils.toSQLDate(sysDateTime);
		
		
		
		PTDeliverItemTransferDAO itemTransferDAO = daoFactory.getPTDeliverItemTransferDAO();
		//发送超时
        long timeout = 1000*60*5;
        java.sql.Timestamp timeoutDateTime = new java.sql.Timestamp((sysDateTime.getTime()-timeout));
        JDBCFieldArray setCols2 = new JDBCFieldArray();
        JDBCFieldArray whereCols2 = new JDBCFieldArray();
        setCols2.add("TransferStatus",SysDict.TRANSFER_STATUS_FAILURE);     
        setCols2.add("LastModifyTime", sysDateTime);
        
        whereCols2.add("LastModifyTime", "<=", timeoutDateTime);
        whereCols2.add("TransferStatus", SysDict.TRANSFER_STATUS_SENTING);
        result = itemTransferDAO.update(setCols2, whereCols2);
		
		//修改发送失败的状态，以便重新发送
		JDBCFieldArray setCols = new JDBCFieldArray();
		JDBCFieldArray whereCols = new JDBCFieldArray();
		setCols.add("TransferStatus",SysDict.TRANSFER_STATUS_UNSENT);		
		setCols.add("LastModifyTime", sysDateTime);
		
		//发送失败的订单
		int sendNumMax = NumberUtils.parseInt(ControlParam.getInstance().getTransferResendTimes());
		if(sendNumMax<Constant.TRANSFER_TO_PPC_SEND_TIMES_MAX){
		    sendNumMax = Constant.TRANSFER_TO_PPC_SEND_TIMES_MAX;//默认值；
		}
		whereCols.add("TransferStatus", SysDict.TRANSFER_STATUS_FAILURE);
		whereCols.addSQL(" AND ReSendNum<="+sendNumMax);//限制发送次数，超过次数不再自动重发
		result = itemTransferDAO.update(setCols, whereCols);
		
        return result;
    }
}
