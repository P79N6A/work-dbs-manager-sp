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
 * <p>Description: 寄件订单数量查询(eLocker web Portal) </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class DMDeliveryItemsQryCount extends ActionBean
{

    public int doBusiness(InParamDMDeliveryItemsQryCount in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        int result = 0;

		//1.验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(in.BPartnerID))
			throw new EduException(ErrorCode.ERR_PARMERR);


		//2.调用CommonDAO.isOnline(管理员编号)判断管理员是否在线。
		//OPOperOnline operOnline = commonDAO.isOnline(in.OperID);

		//3.调用UtilDAO.getCurrentDateTime()获得系统日期时间。
		java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();
		java.sql.Date sysDate = DateUtils.toSQLDate(sysDateTime);

		PreparedWhereExpression whereSQL = new PreparedWhereExpression();
		
		java.sql.Date date =null;
		if(StringUtils.isNotEmpty(in.BeginDate)){//
        	date = commonDAO.strToDate(in.BeginDate);
        	if(date!=null){
        		whereSQL.checkAdd("CreateTime", ">=", new java.sql.Timestamp(date.getTime()));
        	}
        }
        if(StringUtils.isNotEmpty(in.EndDate)){
        	date = commonDAO.strToDate(in.EndDate);
        	if(date!=null){
        		whereSQL.checkAdd("CreateTime", "<=", DateUtils.addTimeStamp(new java.sql.Timestamp(date.getTime()), 1));//
        	}
        }
        
        if(StringUtils.isNotEmpty(in.PackageID)){//模糊查询
        	whereSQL.addSQL(" AND (PackageID ");
        	whereSQL.addSQL(" LIKE '"+in.PackageID+"%'");
        	whereSQL.addSQL(" )");
        }
        whereSQL.add("BPartnerID", in.BPartnerID);
        whereSQL.checkAdd("CustomerMobile", in.CustomerMobile);
        if(StringUtils.isNotEmpty(in.CustomerName)){//模糊查询
        	whereSQL.addSQL(" AND (CustomerName ");
        	whereSQL.addSQL(" LIKE '"+in.CustomerName+"%'");
        	whereSQL.addSQL(" )");
        }
        if(StringUtils.isNotEmpty(in.ItemStatus)){
        	whereSQL.addSQL(utilDAO.getFlagInSQL("a.ItemStatus", in.ItemStatus));
        }
        
        String viewName = "V_DMAllDeliveryItems";
        String limitSQL = "";//commonDAO.getQueryZoneLimitSQL(operOnline.OperID, operOnline.ZoneID);
        
		String sql = "SELECT COUNT(PackageID) FROM " + viewName + " a WHERE 1=1 " + whereSQL.getPreparedWhereSQL() + limitSQL;
        result = dbSession.executeQueryCount(sql,whereSQL);
        return result;
    }
}
