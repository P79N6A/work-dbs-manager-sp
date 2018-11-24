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
 * <p>Description: 查询投递订单周期记录 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class PTItemLifeCycleQry extends ActionBean
{

    public RowSet doBusiness(InParamPTItemLifeCycleQry in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        RowSet rset = null;

		//1.	验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(in.PackageID))
			throw new EduException(ErrorCode.ERR_PARMERR);
		
		//2.	调用CommonDAO.isOnline(管理员编号)判断管理员是否在线。
		//OPOperOnline operOnline = commonDAO.isOnline(in.OperID);

		//3.	调用UtilDAO.getCurrentDateTime()获得系统日期时间。
		java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();
		java.sql.Date sysDate = DateUtils.toSQLDate(sysDateTime);

		/*if(in.BeginDate==null){
			in.BeginDate = DateUtils.addDate(sysDate, -60);//查询近2个月的订单记录
		}*/
		if(in.EndDate!=null){
			in.EndDate = DateUtils.addDate(in.EndDate, 1);
		}
		PreparedWhereExpression whereSQL = new PreparedWhereExpression();
        whereSQL.add("PackageID", in.PackageID);
        whereSQL.checkAdd("LastModifyTime", ">=",in.BeginDate);
        whereSQL.checkAdd("LastModifyTime", "<=",in.EndDate);
        String sql = "SELECT * FROM V_ItemHistoryDetail  WHERE 1=1  " 
        + whereSQL.getPreparedWhereSQL() ;//+ " ORDER BY WaterID DESC"
        
        java.util.List orderByField1 = new java.util.LinkedList<String>();
        orderByField1.add("LastModifyTime ASC ");//DESC-倒序，
        
        rset = dbSession.executeQuery(sql, orderByField1, 0, 0, whereSQL);
		
        return rset;
    }
}
