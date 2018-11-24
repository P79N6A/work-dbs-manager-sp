package com.dcdzsoft.business.op;

import javax.sql.RowSet;

import com.dcdzsoft.EduException;
import com.dcdzsoft.business.ActionBean;
import com.dcdzsoft.constant.ErrorCode;
import com.dcdzsoft.dao.OPOperatorDAO;
import com.dcdzsoft.dto.business.InParamOPOperOnlineQry;
import com.dcdzsoft.dto.function.OPOperOnline;
import com.dcdzsoft.dto.function.OPOperator;
import com.dcdzsoft.sda.db.JDBCFieldArray;
import com.dcdzsoft.sda.db.PreparedWhereExpression;
import com.dcdzsoft.sda.db.RowSetUtils;
import com.dcdzsoft.sda.db.WhereExpression;
import com.dcdzsoft.util.StringUtils;

/**
 * <p>
 * Title: 自提柜后台运营系统
 * </p>
 * <p>
 * Description: 管理员在线信息查询
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: dcdzsoft
 * </p>
 * 
 * @author zhengxy
 * @version 1.0
 */

public class OPOperOnlineQry extends ActionBean {

	public RowSet doBusiness(InParamOPOperOnlineQry in) throws EduException {
		utilDAO = this.getUtilDAO();
		commonDAO = this.getCommonDAO();
		dbSession = this.getCurrentDBSession();
		RowSet rset = null;

		// 1. 验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(in.OperID))
			throw new EduException(ErrorCode.ERR_PARMERR);

		// 2. 调用CommonDAO.isOnline(管理员编号)判断管理员是否在线。
		OPOperOnline operOnline = null;
		try {
			operOnline = commonDAO.isOnline(in.OperID);
		} catch (EduException e) {
			OPOperatorDAO operDAO = daoFactory.getOPOperatorDAO();
			JDBCFieldArray whereCols = new JDBCFieldArray();
			whereCols.add("UserID", in.OperID);
			RowSet rset1 = operDAO.select(whereCols);
			if (RowSetUtils.rowsetNext(rset1)) {
				operOnline = commonDAO.isOnline(RowSetUtils.getStringValue(
						rset1, "OperID"));
			} else {
				throw e;
			}
		}

		if (StringUtils.isNotEmpty(in.UserID)) {
			OPOperatorDAO operDAO = daoFactory.getOPOperatorDAO();
			OPOperator oper = new OPOperator();
			oper.OperID = in.UserID;

			try {
				oper = operDAO.find(oper);
			} catch (EduException e) {
				JDBCFieldArray whereCols2 = new JDBCFieldArray();
				whereCols2.add("UserID", in.UserID);
				RowSet rset2 = operDAO.select(whereCols2);
				if (RowSetUtils.rowsetNext(rset2)) {
					in.UserID = RowSetUtils.getStringValue(rset2, "OperID");
				} 
			}
		}

		// 加入权限判断
		String limitSQL = "";
		// limitSQL = commonDAO.getQueryZoneLimitSQL(operOnline.OperID,
		// operOnline.ZoneID);
		limitSQL += " AND OperType>=" + operOnline.OperType;// 只能查询操作员类型更低级的操作员信息

		
		PreparedWhereExpression whereSQL = new PreparedWhereExpression();
		whereSQL.checkAdd("OperID", in.UserID);
		//whereSQL.checkAdd("OperName", in.OperName);
		if(StringUtils.isNotEmpty(in.OperName)){
		    whereSQL.add("OperName", " LIKE ", "%" + in.OperName + "%");
		}
		whereSQL.checkAdd("ZoneID", in.ZoneID);
		whereSQL.checkAdd("OperType", in.OperType);

		String sql = "SELECT * FROM V_OperOnline a WHERE 1=1 "+ whereSQL.getPreparedWhereSQL() + limitSQL;

		java.util.LinkedList list = new java.util.LinkedList();
		list.add("LoginInTime DESC");

		rset = dbSession.executeQuery(sql, list, in.recordBegin, in.recordNum, whereSQL);

		return rset;
	}
}
