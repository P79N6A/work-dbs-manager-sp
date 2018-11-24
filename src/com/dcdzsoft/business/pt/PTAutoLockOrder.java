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
 * <p>Description: 自动锁定过期订单 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class PTAutoLockOrder extends ActionBean
{

    public int doBusiness(InParamPTAutoLockOrder in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        int result = 0;

		//1.	验证输入参数是否有效，如果无效返回-1。
        PTInBoxPackageDAO inboxPackDAO = daoFactory.getPTInBoxPackageDAO();
        
        java.sql.Date sysDate = utilDAO.getCurrentDate();
    	java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();
		
    	//#start投递在箱订单，直投订单自动超期
		/*JDBCFieldArray setCols = new JDBCFieldArray();
		JDBCFieldArray whereCols = new JDBCFieldArray();
		
		setCols.add("ItemStatus", SysDict.ITEM_STATUS_DROP_EXPIRED);
		setCols.add("ExpiredTime", sysDateTime);

		whereCols.add("ExpiredTime", "<=",sysDateTime);
		String itemStatus = "";//Dropped and D-Dropped
        itemStatus += SysDict.ITEM_STATUS_DROP_DROPPED;
        itemStatus +=","+SysDict.ITEM_STATUS_DROP_D_DROPPED;
        whereCols.addSQL(utilDAO.getFlagInSQL("ItemStatus", itemStatus));
		inboxPackDAO.update(setCols,whereCols);	*/
    	////////
    	JDBCFieldArray setCols = new JDBCFieldArray();
    	JDBCFieldArray whereCols = new JDBCFieldArray();
    	String itemStatus = "5,6";//Dropped and D-Dropped
    	whereCols.add("ExpiredTime", "<=",sysDateTime);
    	whereCols.addSQL(utilDAO.getFlagInSQL("ItemStatus", itemStatus));
    	RowSet rset = inboxPackDAO.select(whereCols);
    	while(RowSetUtils.rowsetNext(rset)){
    	    String itemCode = RowSetUtils.getStringValue(rset, "PackageID");
    	    //#start记录订单状态更新信息
            PTItemLifeCycle itemLifeCycle = new PTItemLifeCycle();
            itemLifeCycle.PackageID  = itemCode;
            itemLifeCycle.ItemStatus = SysDict.ITEM_STATUS_DROP_EXPIRED;
            itemLifeCycle.OperatorID    = in.OperID;
            itemLifeCycle.OperatorType = "0";
            itemLifeCycle.LastModifyTime = sysDateTime;
            itemLifeCycle.Remark = "Expired";
            commonDAO.addItemLifeCycle(itemLifeCycle);
            //#end
    	}
    	//
    	setCols.add("ItemStatus", SysDict.ITEM_STATUS_DROP_EXPIRED);
        setCols.add("ExpiredTime", sysDateTime);
        inboxPackDAO.update(setCols,whereCols);
		//#end
		
		//每天执行一次 解除箱锁定
		if("1".equals(ControlParam.getInstance().getRenewBoxAndLockedOldBox())){
            TBServerBoxDAO boxDAO = daoFactory.getTBServerBoxDAO();

            JDBCFieldArray setCols3 = new JDBCFieldArray();
            JDBCFieldArray whereCols3 = new JDBCFieldArray();

            setCols3.add("BoxStatus", "0");//1-锁定，2-故障，3-故障锁定
            whereCols3.add("BoxStatus", "1");//原始预定的箱号

            result = boxDAO.update(setCols3, whereCols3);
            
            JDBCFieldArray setCols4 = new JDBCFieldArray();
            JDBCFieldArray whereCols4 = new JDBCFieldArray();

            setCols4.add("BoxStatus", "2");//1-锁定，2-故障，3-故障锁定
            whereCols4.add("BoxStatus", "3");//原始预定的箱号

            result = boxDAO.update(setCols4, whereCols4);
        }
        return result;
    }
}
