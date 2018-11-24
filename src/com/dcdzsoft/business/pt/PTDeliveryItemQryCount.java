package com.dcdzsoft.business.pt;

import com.dcdzsoft.EduException;
import com.dcdzsoft.business.ActionBean;
import com.dcdzsoft.constant.ErrorCode;
import com.dcdzsoft.dto.business.InParamPTDeliveryItemQryCount;
import com.dcdzsoft.dto.function.OPOperOnline;
import com.dcdzsoft.sda.db.PreparedWhereExpression;
import com.dcdzsoft.util.DateUtils;
import com.dcdzsoft.util.StringUtils;

/**
 * <p>
 * Title: 自提柜后台运营系统
 * </p>
 * <p>
 * Description: 投递订单查询数量
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

public class PTDeliveryItemQryCount extends ActionBean {

	public int doBusiness(InParamPTDeliveryItemQryCount in) throws EduException {
		utilDAO = this.getUtilDAO();
		commonDAO = this.getCommonDAO();
		dbSession = this.getCurrentDBSession();
		int result = 0;

		// 1. 验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(in.OperID))
			throw new EduException(ErrorCode.ERR_PARMERR);

		// 2. 调用CommonDAO.isOnline(管理员编号)判断管理员是否在线。

		OPOperOnline operOnline = commonDAO.isOnline(in.OperID);

		// 加入权限判断
		String limitSQL = "";// commonDAO.getQueryZoneLimitSQL(operOnline.OperID,
								// operOnline.ZoneID);

		String viewName = "";
		if ("1".equals(in.PaneFlag)) {
			// 0 - Listed,1- Received
			viewName = "V_PTPane1";
			in.ZoneID = operOnline.ZoneID;
		} else if ("2".equals(in.PaneFlag)) {
			// 2 - Assigned, 3 - Scheduled
			viewName = "V_PTPane2";
			in.ZoneID = operOnline.ZoneID;
		} else if ("3".equals(in.PaneFlag)) {
			// 4 - Intransit-Out, 10 - Intransit-Back
			viewName = "V_PTPane3";
			in.ZoneID = operOnline.ZoneID;
		} else if ("4".equals(in.PaneFlag)) {
			// 5 - Dropped, 6 - D-Dropped, 8 - Expired, 9 - M-Expired
			viewName = "V_PTPane4";
			limitSQL = commonDAO.getQueryZoneLimitSQL(operOnline.OperID,
					operOnline.ZoneID);
		} else if ("5".equals(in.PaneFlag)) {
			viewName = "V_PTPane5";
			// 12 - Missing,11 - Return
			in.ZoneID = operOnline.ZoneID;
		} else if ("7".equals(in.PaneFlag)) {
			viewName = "V_PTPane7";
			limitSQL = commonDAO.getQueryZoneLimitSQL(operOnline.OperID,
					operOnline.ZoneID);
		} else {
			viewName = "V_PTDeliveryRecord";
			limitSQL = commonDAO.getQueryZoneLimitSQL(operOnline.OperID,
					operOnline.ZoneID);
			/*
			 * if(StringUtils.isEmpty(in.ItemStatus)){ limitSQL +=
			 * " AND RunStatus='0'";//0-running 查询在执行的订单 }
			 */
		}

		PreparedWhereExpression whereSQL = new PreparedWhereExpression();
		// whereSQL.checkAdd("PackageID", in.PackageID);
		if (StringUtils.isNotEmpty(in.PackageID)) {
			in.PackageID = in.PackageID.toUpperCase();// 订单号不区分大小写（系统统一保存为大写）
			whereSQL.addSQL(" AND (");
			whereSQL.addSQL(" UPPER(PackageID)="
					+ StringUtils.addQuote(in.PackageID));
			whereSQL.addSQL(" )");
		} else {
			// 按订单查询时，不做时间限制,按订单最后修改时间查询
			whereSQL.checkAdd("LastModifyTime", ">=", in.BeginDate);
			if (in.EndDate != null)
				whereSQL.checkAdd("LastModifyTime", "<=",
						DateUtils.addDate(in.EndDate, 1));
		}
		/* whereSQL.checkAdd("RefNo", in.RefNo); */
		if (StringUtils.isNotEmpty(in.RefNo)) {
			whereSQL.addSQL(" AND (RefNo ");
			whereSQL.addSQL(" LIKE '" + in.RefNo + "%'");
			whereSQL.addSQL(" )");
		}
		whereSQL.checkAdd("PPCID", in.PPCID);

		if ("1".equals(in.PaneFlag)) {
			whereSQL.addSQL("AND (ISNULL(ZoneIDDes,'') = '') AND (ZoneID = "
					+ StringUtils.addQuote(in.ZoneID) + ") OR (ZoneIDDes = "
					+ StringUtils.addQuote(in.ZoneID) + ")");
		} else {
			whereSQL.checkAdd("ZoneID", in.ZoneID);
		}
		// whereSQL.checkAdd("CustomerMobile", in.CustomerMobile);
		if (StringUtils.isNotEmpty(in.CustomerMobile)) {
			whereSQL.addSQL(" AND (CustomerMobile ");
			whereSQL.addSQL(" LIKE '" + in.CustomerMobile + "%'");
			whereSQL.addSQL(" )");
		}
		whereSQL.checkAdd("CustomerID", in.CustomerID);
		whereSQL.checkAdd("CompanyID", in.CompanyID);
		whereSQL.checkAdd("TerminalNo", in.TerminalNo);
		/*
		 * if(StringUtils.isNotEmpty(in.TerminalNo)){//模糊查询
		 * whereSQL.addSQL(" AND (TerminalNo ");
		 * whereSQL.addSQL(" LIKE '"+in.TerminalNo+"%'"); whereSQL.addSQL(" )");
		 * }
		 */
		whereSQL.checkAdd("BoxNo", in.BoxNo);
		whereSQL.checkAdd("RunStatus", in.RunStatus);
		whereSQL.checkAdd("DADFlag", in.DADFlag);
		whereSQL.checkAdd("ReturnOrderID", in.ReturnOrderID);
		// whereSQL.checkAdd("ItemStatus", in.ItemStatus);
		if (StringUtils.isNotEmpty(in.ItemStatus)) {
			whereSQL.addSQL(utilDAO.getFlagInSQL("a.ItemStatus", in.ItemStatus));
		}
		// whereSQL.checkAdd("PostmanID", in.PostmanID);
		if (StringUtils.isNotEmpty(in.PostmanID)) {
			whereSQL.addSQL(" AND (DropAgentID = "
					+ StringUtils.addQuote(in.PostmanID)
					+ " OR ReturnAgentID = "
					+ StringUtils.addQuote(in.PostmanID) + ")");
		}

		String sql = "SELECT COUNT(PackageID) FROM " + viewName
				+ " a WHERE 1=1 " + whereSQL.getPreparedWhereSQL() + limitSQL;
		result = dbSession.executeQueryCount(sql, whereSQL);

		return result;
	}
}
