package com.dcdzsoft.business.tb;

import javax.sql.RowSet;

import com.dcdzsoft.EduException;
import com.dcdzsoft.business.ActionBean;
import com.dcdzsoft.constant.ControlParam;
import com.dcdzsoft.dto.business.InParamTBTerminalListQry;
import com.dcdzsoft.dto.function.OPOperOnline;
import com.dcdzsoft.sda.db.PreparedWhereExpression;
import com.dcdzsoft.util.StringUtils;

/**
 * <p>
 * Title: 自提柜后台运营系统
 * </p>
 * <p>
 * Description: 柜体信息列表查询
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

public class TBTerminalListQry extends ActionBean {

	public RowSet doBusiness(InParamTBTerminalListQry in) throws EduException {
		utilDAO = this.getUtilDAO();
		commonDAO = this.getCommonDAO();
		dbSession = this.getCurrentDBSession();
		RowSet rset = null;

		// 1. 验证输入参数是否有效，如果无效返回-1。
		// if (StringUtils.isEmpty(in.OperID))
		// throw new EduException(ErrorCode.ERR_PARMERR);

		// 2. 调用CommonDAO.isOnline(管理员编号)判断管理员是否在线。
		String limitSQL = "";

		if (StringUtils.isNotEmpty(in.OperID)) {
			OPOperOnline operOnline = commonDAO.isOnline(in.OperID);
			limitSQL += commonDAO.getQueryLockerLimitSQL(operOnline.OperID,
					operOnline.ZoneID);
		}
		if (StringUtils.isNotEmpty(in.CompanyID)) {
			limitSQL += " AND EXISTS(SELECT TerminalNo FROM IMCompanyBoxRight b1 WHERE b1.CompanyID="
					+ StringUtils.addQuote(in.CompanyID)
					+ " AND b1.TerminalNo = a.TerminalNo)";
		}
		if (StringUtils.isNotEmpty(in.ZoneID)) {
			limitSQL += " AND EXISTS(SELECT TerminalNo FROM IMZoneLockerRight b2 WHERE b2.ZoneID="
					+ StringUtils.addQuote(in.ZoneID)
					+ " AND b2.TerminalNo = a.TerminalNo) ";
		}
		// ///////////////////////////////////////////////////////////
		PreparedWhereExpression whereSQL = new PreparedWhereExpression();
		whereSQL.checkAdd("TerminalType", in.TerminalType);
		// whereSQL.checkAdd("TerminalStatus", in.TerminalStatus);
		// 柜体查询：0-提供所有，1-提供已注册柜体，2-提供正常在线柜体
		if (StringUtils.isNotEmpty(in.TerminalStatus)) {
			whereSQL.addSQL(utilDAO.getFlagInSQL("a.TerminalStatus",
					in.TerminalStatus));
		} else {
			if ("1".equals(ControlParam.getInstance().getLockerListQry())) {
				whereSQL.add("RegisterFlag", "1");
			} else if ("2"
					.equals(ControlParam.getInstance().getLockerListQry())) {
				String terminalStatus = "0,5";// 0-表示正常，5-表示存在箱故障，但柜体正常
				whereSQL.add("RegisterFlag", "1");
				whereSQL.addSQL(utilDAO.getFlagInSQL("a.TerminalStatus",
						terminalStatus));
			}
		}

		String sql = "SELECT a.TerminalNo,a.TerminalName,"
				+ "a.TerminalStatus,a.TerminalStatusName,"
				+ "a.Location,a.Address FROM V_TBTerminal a WHERE 1=1 "
				+ whereSQL.getPreparedWhereSQL() + limitSQL
				+ " ORDER BY TerminalNo";

		rset = dbSession.executeQuery(sql, whereSQL);

		return rset;
	}
}
