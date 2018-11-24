package com.dcdzsoft.business.qy;

import java.sql.Timestamp;
import java.util.Date;

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
 * <p>Title: 智能自助包裹柜系统</p>
 * <p>Description: 运营报表按订单号统计 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author 王中立
 * @version 1.0
 */

public class QYOperationReportByPack extends ActionBean
{

    public RowSet doBusiness(InParamQYOperationReportByPack in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        RowSet rset = null;

		//1.验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(in.OperID))
			throw new EduException(ErrorCode.ERR_PARMERR);


		//2.调用CommonDAO.isOnline(操作员编号)判断操作员是否在线。
		OPOperOnline operOnline = commonDAO.isOnline(in.OperID);


		//3.调用UtilDAO.getCurrentDateTime()获得系统日期时间。
		java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();
		java.sql.Date sysDate = DateUtils.toSQLDate(sysDateTime);

		String reportID = DateUtils.timestampToString(sysDateTime);
		String limitSQL = "";
		String viewName = "V_QYOperationReportTimeout";
		String sql = "";
		Timestamp beginTime;
		Timestamp endTime;
		if(in.BeginDate != null)
		{
			beginTime = new Timestamp(in.BeginDate.getTime());
			endTime = new Timestamp(in.EndDate.getTime());
		}else{
			beginTime = DateUtils.addTimeStamp(sysDateTime, -30);
			endTime = sysDateTime;
		}
		
		PreparedWhereExpression whereSQL = new PreparedWhereExpression();
		whereSQL.checkAdd("LastModifyTime", ">=", beginTime);
	    whereSQL.checkAdd("LastModifyTime", "<=", endTime); 
	    
		//2:按操作员UserID统计
		whereSQL.checkAdd("PackageID", in.PackageID);
		whereSQL.checkAdd("OperID", in.UserID);
		if(StringUtils.isNotEmpty(in.OperName)){
		    whereSQL.add("OperName", " LIKE ", "%" + in.OperName + "%");
		}
		whereSQL.checkAdd("ZoneID", in.ZoneID);
		sql = "SELECT '" + reportID + "' AS ReportID"
		   +",PackageID,OperID ,OperName,OperTypeName"
		   + ",ZoneID,ZoneName,ItemStatus,ItemStatusName"
		   +",StatusTime,StatusTimeOut,LastModifyTime"
		   +"  FROM "+viewName
		   +"  WHERE 1=1 ";
		sql = sql + whereSQL.getPreparedWhereSQL()+ limitSQL;
		
		java.util.LinkedList list = new java.util.LinkedList();
        list.add("PackageID,ItemStatus ASC");
        rset = dbSession.executeQuery(sql, list, in.recordBegin, in.recordNum, whereSQL);

        return rset;
    }
}
