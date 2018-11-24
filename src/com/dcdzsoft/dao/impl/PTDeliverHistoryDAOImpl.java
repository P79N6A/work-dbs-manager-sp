package com.dcdzsoft.dao.impl;

import java.sql.*;
import javax.sql.RowSet;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dcdzsoft.EduException;
import com.dcdzsoft.sda.db.*;
import com.dcdzsoft.util.*;
import com.dcdzsoft.dao.*;
import com.dcdzsoft.dao.common.*;
import com.dcdzsoft.dao.factory.*;
import com.dcdzsoft.dto.function.*;
import com.dcdzsoft.constant.*;

public class PTDeliverHistoryDAOImpl implements PTDeliverHistoryDAO
{
	private static final Log log = LogFactory.getLog(PTDeliverHistoryDAOImpl.class.getName());

	private DBSession dbSession = LocalSessionHolder.getCurrentDBSession();

	private Connection conn = dbSession.getConnection();

	private static final int RSET_FETCH_SIZE = 50;


	public int insert(PTDeliverHistory obj) throws EduException
	{
		PreparedStatement pstmt = null;
		int result = 0;

		String sql = "INSERT INTO PTDeliverHistory(PackageID,CreateTime,ItemStatus,RunStatus,PPCID,ZoneID,CompanyID,RefNo,CustomerID,ParcelSize,CustomerName,CustomerAddress,CustomerMobile,TerminalNo,BoxNo,DropAgentID,DynamicCode,StoredDate,StoredTime,ExpiredTime,OpenBoxKey,TakedWay,TakedTime,ReturnAgentID,DADFlag,TradeWaterNo,PosPayFlag,UploadFlag,DropNum,PrintedFlag,ReportOrderID,LastModifyTime,Remark)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

		SQLLog.logInsert(log, obj);

		try
		{
			pstmt = conn.prepareStatement(sql);
			PreparedStmtUtils.setString(pstmt,1,obj.PackageID);
			PreparedStmtUtils.setTimestamp(pstmt,2,obj.CreateTime);
			PreparedStmtUtils.setString(pstmt,3,obj.ItemStatus);
			PreparedStmtUtils.setString(pstmt,4,obj.RunStatus);
			PreparedStmtUtils.setString(pstmt,5,obj.PPCID);
			PreparedStmtUtils.setString(pstmt,6,obj.ZoneID);
			PreparedStmtUtils.setString(pstmt,7,obj.CompanyID);
			PreparedStmtUtils.setString(pstmt,8,obj.RefNo);
			PreparedStmtUtils.setString(pstmt,9,obj.CustomerID);
			PreparedStmtUtils.setString(pstmt,10,obj.ParcelSize);
			PreparedStmtUtils.setString(pstmt,11,obj.CustomerName);
			PreparedStmtUtils.setString(pstmt,12,obj.CustomerAddress);
			PreparedStmtUtils.setString(pstmt,13,obj.CustomerMobile);
			PreparedStmtUtils.setString(pstmt,14,obj.TerminalNo);
			PreparedStmtUtils.setString(pstmt,15,obj.BoxNo);
			PreparedStmtUtils.setString(pstmt,16,obj.DropAgentID);
			PreparedStmtUtils.setString(pstmt,17,obj.DynamicCode);
			PreparedStmtUtils.setDate(pstmt,18,obj.StoredDate);
			PreparedStmtUtils.setTimestamp(pstmt,19,obj.StoredTime);
			PreparedStmtUtils.setTimestamp(pstmt,20,obj.ExpiredTime);
			PreparedStmtUtils.setString(pstmt,21,obj.OpenBoxKey);
			PreparedStmtUtils.setString(pstmt,22,obj.TakedWay);
			PreparedStmtUtils.setTimestamp(pstmt,23,obj.TakedTime);
			PreparedStmtUtils.setString(pstmt,24,obj.ReturnAgentID);
			PreparedStmtUtils.setString(pstmt,25,obj.DADFlag);
			PreparedStmtUtils.setString(pstmt,26,obj.TradeWaterNo);
			PreparedStmtUtils.setString(pstmt,27,obj.PosPayFlag);
			PreparedStmtUtils.setString(pstmt,28,obj.UploadFlag);
			PreparedStmtUtils.setInt(pstmt,29,obj.DropNum);
			PreparedStmtUtils.setInt(pstmt,30,obj.PrintedFlag);
			PreparedStmtUtils.setString(pstmt,31,obj.ReportOrderID);
			PreparedStmtUtils.setTimestamp(pstmt,32,obj.LastModifyTime);
			PreparedStmtUtils.setString(pstmt,33,obj.Remark);
			result = pstmt.executeUpdate();

		}
		catch(SQLException e)
		{
			String errorCode = ErrorCode.ERR_ADDPTDELIVERHISTORYFAIL;
			log.error("[DBERROR-("+ errorCode + ")]==>[sqlcode]:"+e.getErrorCode() + " [errmsg]:"+e.getMessage() + " [sql]:"+sql);
			throw new EduException(errorCode);
		}
		finally
		{
			dbSession.closeStatement(pstmt);
		}

		return result;
	}


	public int update(JDBCFieldArray setCols,JDBCFieldArray whereCols) throws EduException
	{
		PreparedStatement pstmt = null;
		int result = 0;

		String sql = "UPDATE PTDeliverHistory SET ";

		StringBuffer sbSet = new StringBuffer(256);
		sbSet.append(setCols.toSetSQL());
		StringBuffer sbWhere = new StringBuffer(256);
		if(whereCols != null){
			sbWhere.append(whereCols.toWhereSQL());
		}

		sql = sql + sbSet.toString() + " WHERE 1=1 " + sbWhere.toString();

		SQLLog.logUpdate(log,"PTDeliverHistory",setCols,whereCols);

		try
		{
			pstmt = conn.prepareStatement(sql);
			
			int parameterIndex = 0;
			for(int i = 0 ; i < setCols.size() ; i++){
				if(setCols.get(i).name != null){
					parameterIndex++;
					PreparedStmtUtils.setObject(pstmt,parameterIndex,setCols.get(i));
				}
			}
			
			if(whereCols != null){
				for(int j = 0 ; j < whereCols.size() ; j++){
					if(whereCols.get(j).name != null){
						parameterIndex++;
						PreparedStmtUtils.setObject(pstmt,parameterIndex,whereCols.get(j));
					}
				}
			}
			
			result = pstmt.executeUpdate();

		}
		catch(SQLException e)
		{
			String errorCode = ErrorCode.ERR_MODPTDELIVERHISTORYFAIL;
			log.error("[DBERROR-("+ errorCode + ")]==>[sqlcode]:"+e.getErrorCode() + " [errmsg]:"+e.getMessage() + " [sql]:"+sql);
			throw new EduException(errorCode);
		}
		finally
		{
			dbSession.closeStatement(pstmt);
		}

		return result;
	}


	public int delete(PTDeliverHistory obj) throws EduException
	{
		PreparedStatement pstmt = null;
		int result = 0;

		String sql = "DELETE FROM PTDeliverHistory WHERE PackageID = ?  AND CreateTime = ? ";

		log.debug("[delete sql]:" + "DELETE FROM PTDeliverHistory WHERE PackageID = " + StringUtils.addQuote(obj.PackageID) +
						" AND CreateTime = " + StringUtils.addQuote(obj.CreateTime) );

		try
		{
			pstmt = conn.prepareStatement(sql);
			PreparedStmtUtils.setString(pstmt,1,obj.PackageID);
			PreparedStmtUtils.setTimestamp(pstmt,2,obj.CreateTime);
			result = pstmt.executeUpdate();

		}
		catch(SQLException e)
		{
			String errorCode = ErrorCode.ERR_DELPTDELIVERHISTORYFAIL;
			log.error("[DBERROR-("+ errorCode + ")]==>[sqlcode]:"+e.getErrorCode() + " [errmsg]:"+e.getMessage() + " [sql]:"+sql);
			throw new EduException(errorCode);
		}
		finally
		{
			dbSession.closeStatement(pstmt);
		}

		return result;
	}


	public int delete(JDBCFieldArray whereCols) throws EduException
	{
		PreparedStatement pstmt = null;
		int result = 0;

		String sql = "DELETE FROM PTDeliverHistory WHERE 1=1 " ;

		StringBuffer sbWhere = new StringBuffer(256);
		if(whereCols != null)
		{
			sbWhere.append(whereCols.toWhereSQL());
		}

		sql = sql + sbWhere.toString();

		SQLLog.logDelete(log,"PTDeliverHistory",whereCols);

		try
		{
			pstmt = conn.prepareStatement(sql);
			
			int parameterIndex = 0;
			if(whereCols != null){
				for(int j = 0 ; j < whereCols.size() ; j++){
					if(whereCols.get(j).name != null){
						parameterIndex++;
						PreparedStmtUtils.setObject(pstmt,parameterIndex,whereCols.get(j));
					}
				}
			}
			
			result = pstmt.executeUpdate();

		}
		catch(SQLException e)
		{
			String errorCode = ErrorCode.ERR_DELPTDELIVERHISTORYFAIL;
			log.error("[DBERROR-("+ errorCode + ")]==>[sqlcode]:"+e.getErrorCode() + " [errmsg]:"+e.getMessage() + " [sql]:"+sql);
			throw new EduException(errorCode);
		}
		finally
		{
			dbSession.closeStatement(pstmt);
		}

		return result;
	}


	public boolean isExist(PTDeliverHistory obj) throws EduException
	{
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		int count = 0;

		String sql = "SELECT COUNT(*) FROM PTDeliverHistory WHERE PackageID = ?  AND CreateTime = ? ";

		log.debug("[isExist sql]:" + "SELECT COUNT(*) FROM PTDeliverHistory WHERE PackageID = " + StringUtils.addQuote(obj.PackageID) +
						" AND CreateTime = " + StringUtils.addQuote(obj.CreateTime) );

		try
		{
			pstmt = conn.prepareStatement(sql);
			PreparedStmtUtils.setString(pstmt,1,obj.PackageID);
			PreparedStmtUtils.setTimestamp(pstmt,2,obj.CreateTime);
			rset = pstmt.executeQuery();

			rset.next();
			count = rset.getInt(1);
		}
		catch(SQLException e)
		{
			String errorCode = ErrorCode.ERR_QRYPTDELIVERHISTORYFAIL;
			log.error("[DBERROR-("+ errorCode + ")]==>[sqlcode]:"+e.getErrorCode() + " [errmsg]:"+e.getMessage() + " [sql]:"+sql);
			throw new EduException(errorCode);
		}
		finally
		{
			dbSession.closeResultSet(rset);
			dbSession.closeStatement(pstmt);
		}

		return (count > 0) ? true : false;
	}


	public int isExist(JDBCFieldArray whereCols) throws EduException
	{
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		int count = 0;

		String sql = "SELECT COUNT(*) FROM PTDeliverHistory WHERE 1=1 " ;

		StringBuffer sbWhere = new StringBuffer(256);
		if(whereCols != null)
		{
			sbWhere.append(whereCols.toWhereSQL());
		}

		sql = sql + sbWhere.toString();

		SQLLog.logIsExist(log,"PTDeliverHistory",whereCols);

		try
		{
			pstmt = conn.prepareStatement(sql);
			
			int parameterIndex = 0;
			if(whereCols != null){
				for(int j = 0 ; j < whereCols.size() ; j++){
					if(whereCols.get(j).name != null){
						parameterIndex++;
						PreparedStmtUtils.setObject(pstmt,parameterIndex,whereCols.get(j));
					}
				}
			}
			
			rset = pstmt.executeQuery();

			rset.next();
			count = rset.getInt(1);
		}
		catch(SQLException e)
		{
			String errorCode = ErrorCode.ERR_QRYPTDELIVERHISTORYFAIL;
			log.error("[DBERROR-("+ errorCode + ")]==>[sqlcode]:"+e.getErrorCode() + " [errmsg]:"+e.getMessage() + " [sql]:"+sql);
			throw new EduException(errorCode);
		}
		finally
		{
			dbSession.closeResultSet(rset);
			dbSession.closeStatement(pstmt);
		}

		return count;
	}


	public PTDeliverHistory find(PTDeliverHistory obj) throws EduException
	{
		PTDeliverHistory result = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		String sql = "SELECT PackageID,CreateTime,ItemStatus,RunStatus,PPCID,ZoneID,CompanyID,RefNo,CustomerID,ParcelSize,CustomerName,CustomerAddress,CustomerMobile,TerminalNo,BoxNo,DropAgentID,DynamicCode,StoredDate,StoredTime,ExpiredTime,OpenBoxKey,TakedWay,TakedTime,ReturnAgentID,DADFlag,TradeWaterNo,PosPayFlag,UploadFlag,DropNum,PrintedFlag,ReportOrderID,LastModifyTime,Remark  FROM PTDeliverHistory WHERE PackageID = ?  AND CreateTime = ? ";

		log.debug("[find sql]:" + "SELECT PackageID,CreateTime,ItemStatus,RunStatus,PPCID,ZoneID,CompanyID,RefNo,CustomerID,ParcelSize,CustomerName,CustomerAddress,CustomerMobile,TerminalNo,BoxNo,DropAgentID,DynamicCode,StoredDate,StoredTime,ExpiredTime,OpenBoxKey,TakedWay,TakedTime,ReturnAgentID,DADFlag,TradeWaterNo,PosPayFlag,UploadFlag,DropNum,PrintedFlag,ReportOrderID,LastModifyTime,Remark  FROM PTDeliverHistory WHERE PackageID = " + StringUtils.addQuote(obj.PackageID) +
						" AND CreateTime = " + StringUtils.addQuote(obj.CreateTime) );

		try
		{
			pstmt = conn.prepareStatement(sql);
			PreparedStmtUtils.setString(pstmt,1,obj.PackageID);
			PreparedStmtUtils.setTimestamp(pstmt,2,obj.CreateTime);
			rset = pstmt.executeQuery();

			if(rset.next() == true)
			{
				result = new PTDeliverHistory();
				result.PackageID = rset.getString(1);
				result.CreateTime = rset.getTimestamp(2);
				result.ItemStatus = rset.getString(3);
				result.RunStatus = rset.getString(4);
				result.PPCID = rset.getString(5);
				result.ZoneID = rset.getString(6);
				result.CompanyID = rset.getString(7);
				result.RefNo = rset.getString(8);
				result.CustomerID = rset.getString(9);
				result.ParcelSize = rset.getString(10);
				result.CustomerName = rset.getString(11);
				result.CustomerAddress = rset.getString(12);
				result.CustomerMobile = rset.getString(13);
				result.TerminalNo = rset.getString(14);
				result.BoxNo = rset.getString(15);
				result.DropAgentID = rset.getString(16);
				result.DynamicCode = rset.getString(17);
				result.StoredDate = rset.getDate(18);
				result.StoredTime = rset.getTimestamp(19);
				result.ExpiredTime = rset.getTimestamp(20);
				result.OpenBoxKey = rset.getString(21);
				result.TakedWay = rset.getString(22);
				result.TakedTime = rset.getTimestamp(23);
				result.ReturnAgentID = rset.getString(24);
				result.DADFlag = rset.getString(25);
				result.TradeWaterNo = rset.getString(26);
				result.PosPayFlag = rset.getString(27);
				result.UploadFlag = rset.getString(28);
				result.DropNum = rset.getInt(29);
				result.PrintedFlag = rset.getInt(30);
				result.ReportOrderID = rset.getString(31);
				result.LastModifyTime = rset.getTimestamp(32);
				result.Remark = rset.getString(33);

			}
			else
			{
				throw new EduException(ErrorCode.ERR_PTDELIVERHISTORYNODATA);
			}
		}
		catch(SQLException e)
		{
			String errorCode = ErrorCode.ERR_QRYPTDELIVERHISTORYFAIL;
			log.error("[DBERROR-("+ errorCode + ")]==>[sqlcode]:"+e.getErrorCode() + " [errmsg]:"+e.getMessage() + " [sql]:"+sql);
			throw new EduException(errorCode);
		}
		finally
		{
			dbSession.closeResultSet(rset);
			dbSession.closeStatement(pstmt);
		}

		return result;
	}


	public RowSet select(JDBCFieldArray whereCols) throws EduException
	{
		RowSet cacheSet = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		String sql = "SELECT * FROM PTDeliverHistory WHERE 1=1 ";
		SQLLog.logSelectRowSet(log,sql,whereCols);

		StringBuffer sbWhere = new StringBuffer(256);
		if(whereCols != null)
		{
			sbWhere.append(whereCols.toWhereSQL());
		}

		sql = sql + sbWhere.toString();

		try
		{
			pstmt = conn.prepareStatement(sql);
			
			int parameterIndex = 0;
			if(whereCols != null){
				for(int j = 0 ; j < whereCols.size() ; j++){
					if(whereCols.get(j).name != null){
						parameterIndex++;
						PreparedStmtUtils.setObject(pstmt,parameterIndex,whereCols.get(j));
					}
				}
			}
			
			rset = pstmt.executeQuery();

			rset.setFetchSize(RSET_FETCH_SIZE);

			cacheSet = dbSession.populate(rset);
		}
		catch(SQLException e)
		{
			String errorCode = ErrorCode.ERR_QRYPTDELIVERHISTORYFAIL;
			log.error("[DBERROR-("+ errorCode + ")]==>[sqlcode]:"+e.getErrorCode() + " [errmsg]:"+e.getMessage() + " [sql]:"+sql);
			throw new EduException(errorCode);
		}
		finally
		{
			dbSession.closeResultSet(rset);
			dbSession.closeStatement(pstmt);
		}

		return cacheSet;
	}


	public RowSet select(JDBCFieldArray whereCols,List<String> orderCols) throws EduException
	{
		RowSet cacheSet = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		String sql = "SELECT * FROM PTDeliverHistory WHERE 1=1 ";
		StringBuffer sbWhere = new StringBuffer(256);
		if(whereCols != null)
		{
			sbWhere.append(whereCols.toWhereSQL());
		}

		StringBuffer sbOrder = new StringBuffer(256);
		if(orderCols != null && orderCols.size() > 0)
		{
			sbOrder.append(" ORDER BY ");
			for(int j = 0 ; j < orderCols.size() ; j++)
			{
				sbOrder.append(orderCols.get(j));
				if(j != (orderCols.size() - 1))
					sbOrder.append(",");
			}
		}

		sql = sql + sbWhere.toString() + sbOrder.toString();

		SQLLog.logSelectRowSet(log,sql,whereCols);

		try
		{
			pstmt = conn.prepareStatement(sql);
			
			int parameterIndex = 0;
			if(whereCols != null){
				for(int j = 0 ; j < whereCols.size() ; j++){
					if(whereCols.get(j).name != null){
						parameterIndex++;
						PreparedStmtUtils.setObject(pstmt,parameterIndex,whereCols.get(j));
					}
				}
			}
			
			rset = pstmt.executeQuery();

			rset.setFetchSize(RSET_FETCH_SIZE);

			cacheSet = dbSession.populate(rset);
		}
		catch(SQLException e)
		{
			String errorCode = ErrorCode.ERR_QRYPTDELIVERHISTORYFAIL;
			log.error("[DBERROR-("+ errorCode + ")]==>[sqlcode]:"+e.getErrorCode() + " [errmsg]:"+e.getMessage() + " [sql]:"+sql);
			throw new EduException(errorCode);
		}
		finally
		{
			dbSession.closeResultSet(rset);
			dbSession.closeStatement(pstmt);
		}

		return cacheSet;
	}


	public RowSet select(JDBCFieldArray whereCols,int recordBegin,int recordNum) throws EduException
	{
		RowSet cacheSet = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = "SELECT * FROM  PTDeliverHistory WHERE 1=1 ";

		StringBuffer sbWhere = new StringBuffer(256);
		if(whereCols != null)
		{
			sbWhere.append(whereCols.toWhereSQL());
		}

		sql = sql + sbWhere.toString();

		java.util.List<String> list = new java.util.LinkedList<String>();
		list.add("PackageID");
		list.add("CreateTime");

		DAOFactory daoFactory = DAOFactory.getDAOFactory(dbSession.getDatabaseType());
		UtilDAO utilDAO = daoFactory.getUtilDAO();
		sql = utilDAO.constructNMSql(sql,list,recordBegin,recordNum);

		log.debug("[RowSet sql]:" + sql );

		try
		{
			pstmt = conn.prepareStatement(sql);
			
			int parameterIndex = 0;
			if(whereCols != null){
				for(int j = 0 ; j < whereCols.size() ; j++){
					if(whereCols.get(j).name != null){
						parameterIndex++;
						PreparedStmtUtils.setObject(pstmt,parameterIndex,whereCols.get(j));
					}
				}
			}
			
			rset = pstmt.executeQuery();

			rset.setFetchSize(RSET_FETCH_SIZE);

			cacheSet = dbSession.populate(rset);
		}
		catch(SQLException e)
		{
			String errorCode = ErrorCode.ERR_QRYPTDELIVERHISTORYFAIL;
			log.error("[DBERROR-("+ errorCode + ")]==>[sqlcode]:"+e.getErrorCode() + " [errmsg]:"+e.getMessage() + " [sql]:"+sql);
			throw new EduException(errorCode);
		}
		finally
		{
			dbSession.closeResultSet(rset);
			dbSession.closeStatement(pstmt);
		}

		return cacheSet;
	}


	public String selectFunctions(String fName,JDBCFieldArray whereCols) throws EduException
	{
		String result = "";
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		String sql = "SELECT " + fName + " FROM PTDeliverHistory WHERE 1=1 ";
		SQLLog.logSelectFunction(log,sql,whereCols);

		StringBuffer sbWhere = new StringBuffer(256);
		if(whereCols != null)
		{
			sbWhere.append(whereCols.toWhereSQL());
		}

		sql = sql + sbWhere.toString();

		try
		{
			pstmt = conn.prepareStatement(sql);
			
			int parameterIndex = 0;
			if(whereCols != null){
				for(int j = 0 ; j < whereCols.size() ; j++){
					if(whereCols.get(j).name != null){
						parameterIndex++;
						PreparedStmtUtils.setObject(pstmt,parameterIndex,whereCols.get(j));
					}
				}
			}
			
			rset = pstmt.executeQuery();

			rset.next();
			result = rset.getString(1);

		}
		catch(SQLException e)
		{
			String errorCode = ErrorCode.ERR_QRYPTDELIVERHISTORYFAIL;
			log.error("[DBERROR-("+ errorCode + ")]==>[sqlcode]:"+e.getErrorCode() + " [errmsg]:"+e.getMessage() + " [sql]:"+sql);
			throw new EduException(errorCode);
		}
		finally
		{
			dbSession.closeResultSet(rset);
			dbSession.closeStatement(pstmt);
		}

		return result;
	}

}