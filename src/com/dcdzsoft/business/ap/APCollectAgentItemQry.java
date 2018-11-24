package com.dcdzsoft.business.ap;

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
 * <p>Description: 揽件员查询包裹订单信息 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class APCollectAgentItemQry extends ActionBean
{

	/**
	 * @param in
	 * @return
	 * @throws EduException
	 */
    public OutParamAPCollectAgentItemQry doBusiness(InParamAPCollectAgentItemQry in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        OutParamAPCollectAgentItemQry out = new OutParamAPCollectAgentItemQry();

		//1.验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(in.PackageID))
			throw new EduException(ErrorCode.ERR_PARMERR);

		//3.调用UtilDAO.getCurrentDateTime()获得系统日期时间。
		java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();
		java.sql.Date sysDate = DateUtils.toSQLDate(sysDateTime);

        //#start 设置SQL
        PreparedWhereExpression whereSQL = new PreparedWhereExpression();
        whereSQL.add("PackageID", in.PackageID);
        
        String viewName = "V_DMAllDeliveryItems";
        String limitSQL = "";//commonDAO.getQueryZoneLimitSQL(operOnline.OperID, operOnline.ZoneID);
		String sql = "SELECT * FROM " + viewName 
				+ " a WHERE 1=1 " + whereSQL.getPreparedWhereSQL() + limitSQL;
        
		java.util.List<String> orderByField = new java.util.LinkedList<String>();
        orderByField.add("CreateTime DESC");

        RowSet rset = dbSession.executeQuery(sql, orderByField, 0, 0, whereSQL);
        if (RowSetUtils.rowsetNext(rset)) {
        	out.ItemCode = RowSetUtils.getStringValue(rset, "PackageID");
        	out.CreateTime = Constant.dateFromat.format(RowSetUtils.getTimestampValue(rset, "CreateTime"));
        	out.PartnerID = RowSetUtils.getStringValue(rset, "BPartnerID");
        	out.PartnerName = RowSetUtils.getStringValue(rset, "BPartnerName");
        	out.ParcelSize = RowSetUtils.getStringValue(rset, "ParcelSize");
        	out.ParcelSizeName = RowSetUtils.getStringValue(rset, "ParcelSizeName");
        	out.CollectionFlag = RowSetUtils.getStringValue(rset, "CollectionFlag");
        	out.CollectionFlagName = RowSetUtils.getStringValue(rset, "CollectionFlagName");
        	out.ReturnFlag = RowSetUtils.getStringValue(rset, "ReturnFlag");
        	out.ReturnFlagName = RowSetUtils.getStringValue(rset, "ReturnFlagName");
        	out.ItemStatus = RowSetUtils.getStringValue(rset, "ItemStatus");
        	out.ItemStatusName = RowSetUtils.getStringValue(rset, "ItemStatusName");
        	out.CollectRegionID = RowSetUtils.getStringValue(rset, "CollectZoneID");
        	out.CollectRegionName  = RowSetUtils.getStringValue(rset, "CollectZoneName");
        	out.CustomerAddress = RowSetUtils.getStringValue(rset, "CustomerAddress");
        	out.CustomerMobile = RowSetUtils.getStringValue(rset, "CustomerMobile");
        }else{
        	throw new EduException(ErrorCode.ERR_COLLECT_ITEM_NOT_EXISTS);
        }
        //#end

        return out;
    }
}
