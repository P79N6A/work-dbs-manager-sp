package com.dcdzsoft.business.dm;

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
 * <p>Description: 寄件订单查询 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class DMBPToCancelItemQry extends ActionBean
{

    public RowSet doBusiness(InParamDMBPToCancelItemQry in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        RowSet rset = null;

		//1.验证输入参数是否有效，如果无效返回-1。

		//3.调用UtilDAO.getCurrentDateTime()获得系统日期时间。
		java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();
		java.sql.Date sysDate = DateUtils.toSQLDate(sysDateTime);

		int days = NumberUtils.parseInt(ControlParam.getInstance().getE1ValidityPeriodDays());
        if(days<1){
            days = 1;
        }
        if(in.EndDate == null){
            in.EndDate = DateUtils.addDate(sysDate, -days);
        }
        
		//////查询为使用的E1订单
		DMCollectionParcelDAO collectionParcelDAO = daoFactory.getDMCollectionParcelDAO();
		JDBCFieldArray whereCols = new JDBCFieldArray();
		whereCols.add("BPartnerID", in.BPartnerID);
		whereCols.addSQL(utilDAO.getFlagInSQL("ItemStatus", "50,51"));//50-created, 51-to be Collected
		if(in.BeginDate != null){
		    whereCols.add("CreateTime", ">=",in.BeginDate);
		}
		whereCols.add("CreateTime", "<=",in.EndDate);
		
        rset = collectionParcelDAO.select(whereCols);
        
        return rset;
    }
}
