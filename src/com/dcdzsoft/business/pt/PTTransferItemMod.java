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

public class PTTransferItemMod extends ActionBean
{

    public int doBusiness(InParamPTTransferItemMod in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        int result = 0;

		//1.	验证输入参数是否有效，如果无效返回-1。
		if (in.WaterID == 0 
			||StringUtils.isEmpty(in.SendStatus))
			throw new EduException(ErrorCode.ERR_PARMERR);

		//2.	调用CommonDAO.isOnline(管理员编号)判断操作员是否在线。


		//3.	调用UtilDAO.getSysDateTime()获得系统日期时间。
		java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();
		
		if(StringUtils.isNotEmpty(in.Remark) && in.Remark.length()> 250){
		    in.Remark = in.Remark.substring(0, 250);
		}
		/////////////////
		PTDeliverItemTransferDAO itemTransferDAO = daoFactory.getPTDeliverItemTransferDAO();
		JDBCFieldArray setCols = new JDBCFieldArray();
		JDBCFieldArray whereCols = new JDBCFieldArray();
		setCols.add("TransferStatus",in.SendStatus);
		setCols.add("Remark", in.Remark);
		setCols.add("LastModifyTime", sysDateTime);
		
		whereCols.add("WaterID", in.WaterID);
			
		itemTransferDAO.update(setCols, whereCols);
		
		//更新发送流水中的发送状态
		PTTransferItemWaterDAO trasnferWaterDAO = daoFactory.getPTTransferItemWaterDAO();
		JDBCFieldArray setCols1 = new JDBCFieldArray();
        JDBCFieldArray whereCols1 = new JDBCFieldArray();
        setCols1.add("TransferStatus",in.SendStatus);
        setCols1.add("Remark", in.Remark);
        setCols1.add("LastModifyTime", sysDateTime);
        
        whereCols1.add("WaterID", in.TransferWaterID);
            
        trasnferWaterDAO.update(setCols1, whereCols1);
		
        return result;
    }
    
    /**
     * 修改发送失败的状态，以便重新发送
     * @return
     * @throws EduException
     */
    public int doModFailSendStatus(InParamPTTransferItemMod in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        
        //3.	调用UtilDAO.getSysDateTime()获得系统日期时间。
      	java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();
      	java.sql.Date sysDate = DateUtils.toSQLDate(sysDateTime);
      		
        PTDeliverItemTransferDAO itemTransferDAO = daoFactory.getPTDeliverItemTransferDAO();
		JDBCFieldArray setCols = new JDBCFieldArray();
		JDBCFieldArray whereCols = new JDBCFieldArray();
		
		setCols.add("TransferStatus",SysDict.TRANSFER_STATUS_UNSENT);
		setCols.add("LastModifyTime", sysDateTime);
		
		whereCols.add("TransferStatus", SysDict.TRANSFER_STATUS_FAILURE);
			
		itemTransferDAO.update(setCols, whereCols);
		return 0;
    }

}
