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

public class DMHistoryItemDAOImpl implements DMHistoryItemDAO
{
	private static final Log log = LogFactory.getLog(DMHistoryItemDAOImpl.class.getName());

	private DBSession dbSession = LocalSessionHolder.getCurrentDBSession();

	private Connection conn = dbSession.getConnection();

	private static final int RSET_FETCH_SIZE = 50;


	public int insert(DMHistoryItem obj) throws EduException
	{
		PreparedStatement pstmt = null;
		int result = 0;

		String sql = "INSERT INTO DMHistoryItem(PackageID,CreateTime,BPartnerID,PPCID,ItemStatus,ServiceID,ServiceAmt,CollectionFlag,CollectionAmt,ReturnFlag,ZoneID,CompanyID,TradeWaterNo,CustomerAddress,CustomerName,CustomerMobile,CustomerID,TerminalNo,BoxNo,ParcelSize,CollectZoneID,CollectionType,CollectionAgentID,CollectionTime,ReportOrderID,LastModifyTime,Remark,PrintedFlag)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

		SQLLog.logInsert(log, obj);

		try
		{
			pstmt = conn.prepareStatement(sql);
			PreparedStmtUtils.setString(pstmt,1,obj.PackageID);
			PreparedStmtUtils.setTimestamp(pstmt,2,obj.CreateTime);
			PreparedStmtUtils.setString(pstmt,3,obj.BPartnerID);
			PreparedStmtUtils.setString(pstmt,4,obj.PPCID);
			PreparedStmtUtils.setString(pstmt,5,obj.ItemStatus);
			PreparedStmtUtils.setString(pstmt,6,obj.ServiceID);
			PreparedStmtUtils.setDouble(pstmt,7,obj.ServiceAmt);
			PreparedStmtUtils.setString(pstmt,8,obj.CollectionFlag);
			PreparedStmtUtils.setDouble(pstmt,9,obj.CollectionAmt);
			PreparedStmtUtils.setString(pstmt,10,obj.ReturnFlag);
			PreparedStmtUtils.setString(pstmt,11,obj.ZoneID);
			PreparedStmtUtils.setString(pstmt,12,obj.CompanyID);
			PreparedStmtUtils.setString(pstmt,13,obj.TradeWaterNo);
			PreparedStmtUtils.setString(pstmt,14,obj.CustomerAddress);
			PreparedStmtUtils.setString(pstmt,15,obj.CustomerName);
			PreparedStmtUtils.setString(pstmt,16,obj.CustomerMobile);
			PreparedStmtUtils.setString(pstmt,17,obj.CustomerID);
			PreparedStmtUtils.setString(pstmt,18,obj.TerminalNo);
			PreparedStmtUtils.setString(pstmt,19,obj.BoxNo);
			PreparedStmtUtils.setString(pstmt,20,obj.ParcelSize);
			PreparedStmtUtils.setString(pstmt,21,obj.CollectZoneID);
			PreparedStmtUtils.setString(pstmt,22,obj.CollectionType);
			PreparedStmtUtils.setString(pstmt,23,obj.CollectionAgentID);
			PreparedStmtUtils.setTimestamp(pstmt,24,obj.CollectionTime);
			PreparedStmtUtils.setString(pstmt,25,obj.ReportOrderID);
			PreparedStmtUtils.setTimestamp(pstmt,26,obj.LastModifyTime);
			PreparedStmtUtils.setString(pstmt,27,obj.Remark);
			PreparedStmtUtils.setInt(pstmt,28,obj.PrintedFlag);
			result = pstmt.executeUpdate();

		}
		catch(SQLException e)
		{
			String errorCode = ErrorCode.ERR_ADDDMHISTORYITEMFAIL;
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

		String sql = "UPDATE DMHistoryItem SET ";

		StringBuffer sbSet = new StringBuffer(256);
		sbSet.append(setCols.toSetSQL());
		StringBuffer sbWhere = new StringBuffer(256);
		if(whereCols != null){
			sbWhere.append(whereCols.toWhereSQL());
		}

		sql = sql + sbSet.toString() + " WHERE 1=1 " + sbWhere.toString();

		SQLLog.logUpdate(log,"DMHistoryItem",setCols,whereCols);

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
			String errorCode = ErrorCode.ERR_MODDMHISTORYITEMFAIL;
			log.error("[DBERROR-("+ errorCode + ")]==>[sqlcode]:"+e.getErrorCode() + " [errmsg]:"+e.getMessage() + " [sql]:"+sql);
			throw new EduException(errorCode);
		}
		finally
		{
			dbSession.closeStatement(pstmt);
		}

		return result;
	}


	public int delete(DMHistoryItem obj) throws EduException
	{
		PreparedStatement pstmt = null;
		int result = 0;

		String sql = "DELETE FROM DMHistoryItem WHERE PackageID = ?  AND CreateTime = ? ";

		log.debug("[delete sql]:" + "DELETE FROM DMHistoryItem WHERE PackageID = " + StringUtils.addQuote(obj.PackageID) +
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
			String errorCode = ErrorCode.ERR_DELDMHISTORYITEMFAIL;
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

		String sql = "DELETE FROM DMHistoryItem WHERE 1=1 " ;

		StringBuffer sbWhere = new StringBuffer(256);
		if(whereCols != null)
		{
			sbWhere.append(whereCols.toWhereSQL());
		}

		sql = sql + sbWhere.toString();

		SQLLog.logDelete(log,"DMHistoryItem",whereCols);

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
			String errorCode = ErrorCode.ERR_DELDMHISTORYITEMFAIL;
			log.error("[DBERROR-("+ errorCode + ")]==>[sqlcode]:"+e.getErrorCode() + " [errmsg]:"+e.getMessage() + " [sql]:"+sql);
			throw new EduException(errorCode);
		}
		finally
		{
			dbSession.closeStatement(pstmt);
		}

		return result;
	}


	public boolean isExist(DMHistoryItem obj) throws EduException
	{
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		int count = 0;

		String sql = "SELECT COUNT(*) FROM DMHistoryItem WHERE PackageID = ?  AND CreateTime = ? ";

		log.debug("[isExist sql]:" + "SELECT COUNT(*) FROM DMHistoryItem WHERE PackageID = " + StringUtils.addQuote(obj.PackageID) +
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
			String errorCode = ErrorCode.ERR_QRYDMHISTORYITEMFAIL;
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

		String sql = "SELECT COUNT(*) FROM DMHistoryItem WHERE 1=1 " ;

		StringBuffer sbWhere = new StringBuffer(256);
		if(whereCols != null)
		{
			sbWhere.append(whereCols.toWhereSQL());
		}

		sql = sql + sbWhere.toString();

		SQLLog.logIsExist(log,"DMHistoryItem",whereCols);

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
			String errorCode = ErrorCode.ERR_QRYDMHISTORYITEMFAIL;
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


	public DMHistoryItem find(DMHistoryItem obj) throws EduException
	{
		DMHistoryItem result = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		String sql = "SELECT PackageID,CreateTime,BPartnerID,PPCID,ItemStatus,ServiceID,ServiceAmt,CollectionFlag,CollectionAmt,ReturnFlag,ZoneID,CompanyID,TradeWaterNo,CustomerAddress,CustomerName,CustomerMobile,CustomerID,TerminalNo,BoxNo,ParcelSize,CollectZoneID,CollectionType,CollectionAgentID,CollectionTime,ReportOrderID,LastModifyTime,Remark,PrintedFlag  FROM DMHistoryItem WHERE PackageID = ?  AND CreateTime = ? ";

		log.debug("[find sql]:" + "SELECT PackageID,CreateTime,BPartnerID,PPCID,ItemStatus,ServiceID,ServiceAmt,CollectionFlag,CollectionAmt,ReturnFlag,ZoneID,CompanyID,TradeWaterNo,CustomerAddress,CustomerName,CustomerMobile,CustomerID,TerminalNo,BoxNo,ParcelSize,CollectZoneID,CollectionType,CollectionAgentID,CollectionTime,ReportOrderID,LastModifyTime,Remark,PrintedFlag  FROM DMHistoryItem WHERE PackageID = " + StringUtils.addQuote(obj.PackageID) +
						" AND CreateTime = " + StringUtils.addQuote(obj.CreateTime) );

		try
		{
			pstmt = conn.prepareStatement(sql);
			PreparedStmtUtils.setString(pstmt,1,obj.PackageID);
			PreparedStmtUtils.setTimestamp(pstmt,2,obj.CreateTime);
			rset = pstmt.executeQuery();

			if(rset.next() == true)
			{
				result = new DMHistoryItem();
				result.PackageID = rset.getString(1);
				result.CreateTime = rset.getTimestamp(2);
				result.BPartnerID = rset.getString(3);
				result.PPCID = rset.getString(4);
				result.ItemStatus = rset.getString(5);
				result.ServiceID = rset.getString(6);
				result.ServiceAmt = rset.getDouble(7);
				result.CollectionFlag = rset.getString(8);
				result.CollectionAmt = rset.getDouble(9);
				result.ReturnFlag = rset.getString(10);
				result.ZoneID = rset.getString(11);
				result.CompanyID = rset.getString(12);
				result.TradeWaterNo = rset.getString(13);
				result.CustomerAddress = rset.getString(14);
				result.CustomerName = rset.getString(15);
				result.CustomerMobile = rset.getString(16);
				result.CustomerID = rset.getString(17);
				result.TerminalNo = rset.getString(18);
				result.BoxNo = rset.getString(19);
				result.ParcelSize = rset.getString(20);
				result.CollectZoneID = rset.getString(21);
				result.CollectionType = rset.getString(22);
				result.CollectionAgentID = rset.getString(23);
				result.CollectionTime = rset.getTimestamp(24);
				result.ReportOrderID = rset.getString(25);
				result.LastModifyTime = rset.getTimestamp(26);
				result.Remark = rset.getString(27);
				result.PrintedFlag = rset.getInt(28);

			}
			else
			{
				throw new EduException(ErrorCode.ERR_DMHISTORYITEMNODATA);
			}
		}
		catch(SQLException e)
		{
			String errorCode = ErrorCode.ERR_QRYDMHISTORYITEMFAIL;
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

		String sql = "SELECT * FROM DMHistoryItem WHERE 1=1 ";
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
			String errorCode = ErrorCode.ERR_QRYDMHISTORYITEMFAIL;
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

		String sql = "SELECT * FROM DMHistoryItem WHERE 1=1 ";
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
			String errorCode = ErrorCode.ERR_QRYDMHISTORYITEMFAIL;
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
		String sql = "SELECT * FROM  DMHistoryItem WHERE 1=1 ";

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
			String errorCode = ErrorCode.ERR_QRYDMHISTORYITEMFAIL;
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

		String sql = "SELECT " + fName + " FROM DMHistoryItem WHERE 1=1 ";
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
			String errorCode = ErrorCode.ERR_QRYDMHISTORYITEMFAIL;
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