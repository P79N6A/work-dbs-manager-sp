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
 * <p>Description: 取消创建的待投递直投订单 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class PTAutoDADCancel extends ActionBean
{

    public int doBusiness(InParamPTAutoDADCancel in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        int result = 0;
        

        //删除一小时前创建的待投递直投订单
        long timeoutMillis = utilDAO.getCurrentDateTime().getTime()-(1000*60*59);//60分钟失效
        //java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();
    	java.sql.Timestamp timeout = new java.sql.Timestamp(timeoutMillis);
    	
    	PTReadyPackageDAO readyPackDAO = daoFactory.getPTReadyPackageDAO();
    	JDBCFieldArray whereCols = new JDBCFieldArray();
    	JDBCFieldArray setCols = new JDBCFieldArray();
    	setCols.add("BoxNo", "");
		whereCols.add("CreateTime", "<", timeout);
		whereCols.add("ItemStatus", SysDict.ITEM_STATUS_DROP_D_CREATE);
		readyPackDAO.update(setCols, whereCols);
		//System.out.println("timeout="+timeout.toString()+",result="+result);
        return result;
    }
}
