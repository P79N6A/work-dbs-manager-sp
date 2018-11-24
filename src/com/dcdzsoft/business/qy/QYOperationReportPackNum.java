package com.dcdzsoft.business.qy;

import java.util.HashMap;
import java.util.Map;

import javax.sql.RowSet;

import com.dcdzsoft.EduException;
import com.dcdzsoft.business.ActionBean;
import com.dcdzsoft.dto.business.InParamQYOperationReportPackNum;
import com.dcdzsoft.dto.function.OPOperOnline;
import com.dcdzsoft.sda.db.PreparedWhereExpression;
import com.dcdzsoft.sda.db.RowSetUtils;
import com.dcdzsoft.util.DateUtils;

/**
 * <p>
 * Title: 智能自助包裹柜系统
 * </p>
 * <p>
 * Description: 运营报表包裹数量统计
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: dcdzsoft
 * </p>
 * 
 * @author 王中立
 * @version 1.0
 */

public class QYOperationReportPackNum extends ActionBean {

	public Map<String, String> doBusiness(InParamQYOperationReportPackNum in)
			throws EduException {
		utilDAO = this.getUtilDAO();
		commonDAO = this.getCommonDAO();
		dbSession = this.getCurrentDBSession();
		Map<String, String> map = new HashMap();
		RowSet rset = null;
		// 2.调用UtilDAO.getCurrentDateTime()获得系统日期时间。
		java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();
		java.sql.Date sysDate = DateUtils.toSQLDate(sysDateTime);

		// 3.调用CommonDAO.isOnline(操作员编号)判断操作员是否在线。
		OPOperOnline operOnline = commonDAO.isOnline(in.OperID);
		/* String limitSQL = ""; */

		String limitSQL = commonDAO.getQueryZoneLimitSQL(operOnline.OperID,
				operOnline.ZoneID);

		String sql = "";

		if ("1".equals(in.QueryFlag)) {// 0：查询ZoneID，或者TerminalNo填入下拉框，1：查询包裹数量。
			PreparedWhereExpression whereSQL = new PreparedWhereExpression();
			sql = "SELECT ZoneID,ZoneName FROM IMZone a WHERE 1=1 "
					+ whereSQL.getPreparedWhereSQL() + limitSQL;
			rset = dbSession.executeQuery(sql, whereSQL);
			StringBuffer strBuff1 = new StringBuffer();
			StringBuffer strBuff2 = new StringBuffer();
			while (RowSetUtils.rowsetNext(rset)) {
				strBuff1.append(RowSetUtils.getStringValue(rset, "ZoneID"))
						.append(",");
				strBuff2.append(RowSetUtils.getStringValue(rset, "ZoneName"))
						.append(",");
			}
			map.put("ZoneID", strBuff1.toString());
			map.put("ZoneName", strBuff2.toString());
			return map;
		} else if ("2".equals(in.QueryFlag)) {
			PreparedWhereExpression whereSQL = new PreparedWhereExpression();
			whereSQL.checkAdd("ZoneID", in.ZoneID);
			sql = "SELECT TerminalNo,TerminalName FROM TBTerminal a WHERE 1=1 "
					+ whereSQL.getPreparedWhereSQL() + limitSQL;
			rset = dbSession.executeQuery(sql, whereSQL);
			StringBuffer strBuff1 = new StringBuffer();
			StringBuffer strBuff2 = new StringBuffer();
			while (RowSetUtils.rowsetNext(rset)) {
				strBuff1.append(RowSetUtils.getStringValue(rset, "TerminalNo"))
						.append(",");
				strBuff2.append(
						RowSetUtils.getStringValue(rset, "TerminalName"))
						.append(",");
			}
			map.put("TerminalNo", strBuff1.toString());
			map.put("TerminalName", strBuff2.toString());
			return map;
		}

		// /////////////////////
		PreparedWhereExpression whereSQL0 = new PreparedWhereExpression();
		whereSQL0.checkAdd("TerminalNo", in.TerminalNo);
		// emptyBoxes
		sql = "SELECT FreeSmallNum,FreeMediumNum,FreeLargeNum FROM V_TBBoxStat a WHERE 1=1 "
				+ whereSQL0.getPreparedWhereSQL();
		rset = dbSession.executeQuery(sql, whereSQL0);
		while (RowSetUtils.rowsetNext(rset)) {
			map = add(map, "FreeSmallNum",
					RowSetUtils.getIntValue(rset, "FreeSmallNum"));
			map = add(map, "FreeMediumNum",
					RowSetUtils.getIntValue(rset, "FreeMediumNum"));
			map = add(map, "FreeLargeNum",
					RowSetUtils.getIntValue(rset, "FreeLargeNum"));
		}

		// //////////////////////////////////////
		PreparedWhereExpression whereSQL = new PreparedWhereExpression();
		whereSQL.checkAdd("ZoneID", in.ZoneID);
		whereSQL.checkAdd("TerminalNo", in.TerminalNo);

		// PTReadyPackage
		sql = "SELECT * FROM V_QYOperReportPackNumReady a WHERE 1=1 "
				+ whereSQL.getPreparedWhereSQL() + limitSQL;
		rset = dbSession.executeQuery(sql, whereSQL);
		while (RowSetUtils.rowsetNext(rset)) {
			map = add(map, "ListedPack",
					RowSetUtils.getIntValue(rset, "ListedPack"));
			map = add(map, "ListedPackTimeOut",
					RowSetUtils.getIntValue(rset, "ListedPackTimeOut"));
			map = add(map, "ListedPackTimeOut1",
					RowSetUtils.getIntValue(rset, "ListedPackTimeOut1"));
			map = add(map, "ListedPackTimeOut2",
					RowSetUtils.getIntValue(rset, "ListedPackTimeOut2"));
			map = add(map, "ListedPackTimeOut3",
					RowSetUtils.getIntValue(rset, "ListedPackTimeOut3"));

			map = add(map, "ReceivedPack",
					RowSetUtils.getIntValue(rset, "ReceivedPack"));
			map = add(map, "ReceivedPackTimeOut",
					RowSetUtils.getIntValue(rset, "ReceivedPackTimeOut"));
			map = add(map, "ReceivedPackTimeOut1",
					RowSetUtils.getIntValue(rset, "ReceivedPackTimeOut1"));
			map = add(map, "ReceivedPackTimeOut2",
					RowSetUtils.getIntValue(rset, "ReceivedPackTimeOut2"));
			map = add(map, "ReceivedPackTimeOut3",
					RowSetUtils.getIntValue(rset, "ReceivedPackTimeOut3"));
			map = add(map, "TransferListPack",
					RowSetUtils.getIntValue(rset, "TransferListPack"));
			map = add(map, "TransferListPackTimeOut",
					RowSetUtils.getIntValue(rset, "TransferListPackTimeOut"));
			map = add(map, "TransferListPackTimeOut1",
					RowSetUtils.getIntValue(rset, "TransferListPackTimeOut1"));
			map = add(map, "TransferListPackTimeOut2",
					RowSetUtils.getIntValue(rset, "TransferListPackTimeOut2"));
			map = add(map, "TransferListPackTimeOut3",
					RowSetUtils.getIntValue(rset, "TransferListPackTimeOut3"));

			map = add(map, "PackageListedPack",
					RowSetUtils.getIntValue(rset, "PackageListedPack"));
			map = add(map, "PackageListedPackTimeOut",
					RowSetUtils.getIntValue(rset, "PackageListedPackTimeOut"));
			map = add(map, "PackageListedPackTimeOut1",
					RowSetUtils.getIntValue(rset, "PackageListedPackTimeOut1"));
			map = add(map, "PackageListedPackTimeOut2",
					RowSetUtils.getIntValue(rset, "PackageListedPackTimeOut2"));
			map = add(map, "PackageListedPackTimeOut3",
					RowSetUtils.getIntValue(rset, "PackageListedPackTimeOut3"));
			map = add(map, "AssignedPack",
					RowSetUtils.getIntValue(rset, "AssignedPack"));
			map = add(map, "AssignedPackTimeOut",
					RowSetUtils.getIntValue(rset, "AssignedPackTimeOut"));
			map = add(map, "AssignedPackTimeOut1",
					RowSetUtils.getIntValue(rset, "AssignedPackTimeOut1"));
			map = add(map, "AssignedPackTimeOut2",
					RowSetUtils.getIntValue(rset, "AssignedPackTimeOut2"));
			map = add(map, "AssignedPackTimeOut3",
					RowSetUtils.getIntValue(rset, "AssignedPackTimeOut3"));
			map = add(map, "ScheduledPack",
					RowSetUtils.getIntValue(rset, "ScheduledPack"));
			map = add(map, "ScheduledPackTimeOut",
					RowSetUtils.getIntValue(rset, "ScheduledPackTimeOut"));
			map = add(map, "ScheduledPackTimeOut1",
					RowSetUtils.getIntValue(rset, "ScheduledPackTimeOut1"));
			map = add(map, "ScheduledPackTimeOut2",
					RowSetUtils.getIntValue(rset, "ScheduledPackTimeOut2"));
			map = add(map, "ScheduledPackTimeOut3",
					RowSetUtils.getIntValue(rset, "ScheduledPackTimeOut3"));
			map = add(map, "IntransitOutPack",
					RowSetUtils.getIntValue(rset, "IntransitOutPack"));
			map = add(map, "IntransitOutPackTimeOut",
					RowSetUtils.getIntValue(rset, "IntransitOutPackTimeOut"));
			map = add(map, "IntransitOutPackTimeOut1",
					RowSetUtils.getIntValue(rset, "IntransitOutPackTimeOut1"));
			map = add(map, "IntransitOutPackTimeOut2",
					RowSetUtils.getIntValue(rset, "IntransitOutPackTimeOut2"));
			map = add(map, "IntransitOutPackTimeOut3",
					RowSetUtils.getIntValue(rset, "IntransitOutPackTimeOut3"));
			map = add(map, "PackageOutPack",
					RowSetUtils.getIntValue(rset, "PackageListedPack"));
			map = add(map, "PackageOutPackTimeOut",
					RowSetUtils.getIntValue(rset, "PackageListedPackTimeOut"));
			map = add(map, "PackageOutPackTimeOut1",
					RowSetUtils.getIntValue(rset, "PackageListedPackTimeOut1"));
			map = add(map, "PackageOutPackTimeOut2",
					RowSetUtils.getIntValue(rset, "PackageListedPackTimeOut2"));
			map = add(map, "PackageOutPackTimeOut3",
					RowSetUtils.getIntValue(rset, "PackageListedPackTimeOut3"));

		}

		// PTDeliverHistory
		sql = "SELECT * FROM V_QYOperReportPackNumHistory a WHERE 1=1 "
				+ whereSQL.getPreparedWhereSQL() + limitSQL;
		rset = dbSession.executeQuery(sql, whereSQL);
		while (RowSetUtils.rowsetNext(rset)) {
			map = add(map, "ReturnedPack",
					RowSetUtils.getIntValue(rset, "ReturnedPack"));
			map = add(map, "ReturnedPackTimeOut",
					RowSetUtils.getIntValue(rset, "ReturnedPackTimeOut"));
			map = add(map, "ReturnedPackTimeOut1",
					RowSetUtils.getIntValue(rset, "ReturnedPackTimeOut1"));
			map = add(map, "ReturnedPackTimeOut2",
					RowSetUtils.getIntValue(rset, "ReturnedPackTimeOut2"));
			map = add(map, "ReturnedPackTimeOut3",
					RowSetUtils.getIntValue(rset, "ReturnedPackTimeOut3"));
			map = add(map, "MissingPack",
					RowSetUtils.getIntValue(rset, "MissingPack"));
			map = add(map, "MissingPackTimeOut",
					RowSetUtils.getIntValue(rset, "MissingPackTimeOut"));
			map = add(map, "MissingPackTimeOut1",
					RowSetUtils.getIntValue(rset, "MissingPackTimeOut1"));
			map = add(map, "MissingPackTimeOut2",
					RowSetUtils.getIntValue(rset, "MissingPackTimeOut2"));
			map = add(map, "MissingPackTimeOut3",
					RowSetUtils.getIntValue(rset, "MissingPackTimeOut3"));

			map = add(map, "IntransitBackPack",
					RowSetUtils.getIntValue(rset, "IntransitBackPack"));
			map = add(map, "IntransitBackPackTimeOut",
					RowSetUtils.getIntValue(rset, "IntransitBackPackTimeOut"));
			map = add(map, "IntransitBackPackTimeOut1",
					RowSetUtils.getIntValue(rset, "IntransitBackPackTimeOut1"));
			map = add(map, "IntransitBackPackTimeOut2",
					RowSetUtils.getIntValue(rset, "IntransitBackPackTimeOut2"));
			map = add(map, "IntransitBackPackTimeOut3",
					RowSetUtils.getIntValue(rset, "IntransitBackPackTimeOut3"));

		}

		sql = "SELECT * FROM V_QYOperReportPackNumInBox a WHERE 1=1 "
				+ whereSQL.getPreparedWhereSQL() + limitSQL;
		rset = dbSession.executeQuery(sql, whereSQL);
		while (RowSetUtils.rowsetNext(rset)) {
			map = add(map, "DroppedPack",
					RowSetUtils.getIntValue(rset, "DroppedPack"));
			map = add(map, "DroppedPackTimeOut",
					RowSetUtils.getIntValue(rset, "DroppedPackTimeOut"));
			map = add(map, "DroppedPackTimeOut1",
					RowSetUtils.getIntValue(rset, "DroppedPackTimeOut1"));
			map = add(map, "DroppedPackTimeOut2",
					RowSetUtils.getIntValue(rset, "DroppedPackTimeOut2"));
			map = add(map, "DroppedPackTimeOut3",
					RowSetUtils.getIntValue(rset, "DroppedPackTimeOut3"));
			map = add(map, "DDroppedPack",
					RowSetUtils.getIntValue(rset, "DDroppedPack"));
			map = add(map, "DDroppedPackTimeOut",
					RowSetUtils.getIntValue(rset, "DDroppedPackTimeOut"));
			map = add(map, "DDroppedPackTimeOut1",
					RowSetUtils.getIntValue(rset, "DDroppedPackTimeOut1"));
			map = add(map, "DDroppedPackTimeOut2",
					RowSetUtils.getIntValue(rset, "DDroppedPackTimeOut2"));
			map = add(map, "DDroppedPackTimeOut3",
					RowSetUtils.getIntValue(rset, "DDroppedPackTimeOut3"));
			map = add(map, "ExpiredPack",
					RowSetUtils.getIntValue(rset, "ExpiredPack"));
			map = add(map, "ExpiredPackTimeOut",
					RowSetUtils.getIntValue(rset, "ExpiredPackTimeOut"));
			map = add(map, "ExpiredPackTimeOut1",
					RowSetUtils.getIntValue(rset, "ExpiredPackTimeOut1"));
			map = add(map, "ExpiredPackTimeOut2",
					RowSetUtils.getIntValue(rset, "ExpiredPackTimeOut2"));
			map = add(map, "ExpiredPackTimeOut3",
					RowSetUtils.getIntValue(rset, "ExpiredPackTimeOut3"));
			map = add(map, "MExpiredPack",
					RowSetUtils.getIntValue(rset, "MExpiredPack"));
			map = add(map, "MExpiredPackTimeOut",
					RowSetUtils.getIntValue(rset, "MExpiredPackTimeOut"));
			map = add(map, "MExpiredPackTimeOut1",
					RowSetUtils.getIntValue(rset, "MExpiredPackTimeOut1"));
			map = add(map, "MExpiredPackTimeOut2",
					RowSetUtils.getIntValue(rset, "MExpiredPackTimeOut2"));
			map = add(map, "MExpiredPackTimeOut3",
					RowSetUtils.getIntValue(rset, "MExpiredPackTimeOut3"));

		}

		// CounterPackage
		sql = "SELECT * FROM V_QYOperReportPackCounterNum a WHERE 1=1 "
				+ whereSQL.getPreparedWhereSQL() + limitSQL;
		rset = dbSession.executeQuery(sql, whereSQL);
		while (RowSetUtils.rowsetNext(rset)) {
			map = add(map, "CDroppedPack",
					RowSetUtils.getIntValue(rset, "CDroppedPack"));
			map = add(map, "CDroppedPackTimeOut",
					RowSetUtils.getIntValue(rset, "CDroppedPackTimeOut"));
			map = add(map, "CDroppedPackTimeOut1",
					RowSetUtils.getIntValue(rset, "CDroppedPackTimeOut1"));
			map = add(map, "CDroppedPackTimeOut2",
					RowSetUtils.getIntValue(rset, "CDroppedPackTimeOut2"));
			map = add(map, "CDroppedPackTimeOut3",
					RowSetUtils.getIntValue(rset, "CDroppedPackTimeOut3"));
			map = add(map, "CIntransitOutPack",
					RowSetUtils.getIntValue(rset, "CIntransitOutPack"));
			map = add(map, "CIntransitOutPackTimeOut",
					RowSetUtils.getIntValue(rset, "CIntransitOutPackTimeOut"));
			map = add(map, "CIntransitOutPackTimeOut1",
					RowSetUtils.getIntValue(rset, "CIntransitOutPackTimeOut1"));
			map = add(map, "CIntransitOutPackTimeOut2",
					RowSetUtils.getIntValue(rset, "CIntransitOutPackTimeOut2"));
			map = add(map, "CIntransitOutPackTimeOut3",
					RowSetUtils.getIntValue(rset, "CIntransitOutPackTimeOut3"));
			map = add(map, "CExpiredPack",
					RowSetUtils.getIntValue(rset, "CExpiredPack"));
			map = add(map, "CExpiredPackTimeOut",
					RowSetUtils.getIntValue(rset, "CExpiredPackTimeOut"));
			map = add(map, "CExpiredPackTimeOut1",
					RowSetUtils.getIntValue(rset, "CExpiredPackTimeOut1"));
			map = add(map, "CExpiredPackTimeOut2",
					RowSetUtils.getIntValue(rset, "CExpiredPackTimeOut2"));
			map = add(map, "CExpiredPackTimeOut3",
					RowSetUtils.getIntValue(rset, "CExpiredPackTimeOut3"));
			map = add(map, "CMExpiredPack",
					RowSetUtils.getIntValue(rset, "CMExpiredPack"));
			map = add(map, "CMExpiredPackTimeOut",
					RowSetUtils.getIntValue(rset, "CMExpiredPackTimeOut"));
			map = add(map, "CMExpiredPackTimeOut1",
					RowSetUtils.getIntValue(rset, "CMExpiredPackTimeOut1"));
			map = add(map, "CMExpiredPackTimeOut2",
					RowSetUtils.getIntValue(rset, "CMExpiredPackTimeOut2"));
			map = add(map, "CMExpiredPackTimeOut3",
					RowSetUtils.getIntValue(rset, "CMExpiredPackTimeOut3"));
			map = add(map, "CPackageListedPack",
					RowSetUtils.getIntValue(rset, "CPackageListedPack"));
			map = add(map, "CPackageListedPackTimeOut",
					RowSetUtils.getIntValue(rset, "CPackageListedPackTimeOut"));
			map = add(map, "CPackageListedPackTimeOut1",
					RowSetUtils.getIntValue(rset, "CPackageListedPackTimeOut1"));
			map = add(map, "CPackageListedPackTimeOut2",
					RowSetUtils.getIntValue(rset, "CPackageListedPackTimeOut2"));
			map = add(map, "CPackageListedPackTimeOut3",
					RowSetUtils.getIntValue(rset, "CPackageListedPackTimeOut3"));
		}
		/*
		 * sql = "SELECT * FROM V_QYOperReportPackCounterNum WHERE 1=1 " +
		 * whereSQL.getPreparedWhereSQL() + limitSQL; rset =
		 * dbSession.executeQuery(sql, whereSQL); while
		 * (RowSetUtils.rowsetNext(rset)) { map = add(map,
		 * "CounterReceivcePack", RowSetUtils.getIntValue(rset,
		 * "CounterReceivcePack")); map = add(map, "CounterReceivcePackTimeOut",
		 * RowSetUtils.getIntValue(rset, "CounterReceivcePackTimeOut")); map =
		 * add(map, "CounterReturnPack", RowSetUtils.getIntValue(rset,
		 * "CounterReturnPack")); map = add(map, "CounterReturnPackTimeOut",
		 * RowSetUtils.getIntValue(rset, "CounterReturnPackTimeOut"));
		 * 
		 * map = add(map, "CounterPickupPack", RowSetUtils.getIntValue(rset,
		 * "CounterPickupPack")); map = add(map, "CounterPickupPackTimeOut",
		 * RowSetUtils.getIntValue(rset, "CounterPickupPackTimeOut"));
		 * 
		 * }
		 */
		// for (Map.Entry<String, Integer> entry : map.entrySet()) {
		// String key = entry.getKey();
		// Integer value = entry.getValue();
		// System.out.println("key= " + key + " and value= " + value);
		// }
		return map;
	}

	public Map<String, String> add(Map<String, String> map, String key,
			Integer value) {
		if (map.get(key) != null) {
			int value2 = Integer.valueOf(map.get(key));
			Integer valueSum = value2 + value;
			map.put(key, valueSum.toString());
		} else {
			map.put(key, value.toString());
		}
		return map;
	}
}
