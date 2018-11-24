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

public class PTInBoxPackageDAOImpl implements PTInBoxPackageDAO
{
	private static final Log log = LogFactory.getLog(PTInBoxPackageDAOImpl.class.getName());

	private DBSession dbSession = LocalSessionHolder.getCurrentDBSession();

	private Connection conn = dbSession.getConnection();

	private static final int RSET_FETCH_SIZE = 50;


	public int insert(PTInBoxPackage obj) throws EduException
	{
		PreparedStatement pstmt = null;
		int result = 0;

		String sql = "INSERT INTO PTInBoxPackage(PackageID,TerminalNo,BoxNo,ItemStatus,CreateTime,PPCID,ZoneID,CompanyID,RefNo,ParcelSize,CustomerID,CustomerName,CustomerAddress,CustomerMobile,DropAgentID,DynamicCode,DropNum,StoredDate,StoredTime,ExpiredTime,ReminderDateTime,OpenBoxKey,DADFlag,TradeWaterNo,PosPayFlag,UploadFlag,ParcelStatus,PrintedFlag,ReportOrderID,LastModifyTime,Remark)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

		SQLLog.logInsert(log, obj);

		try
		{
			pstmt = conn.prepareStatement(sql);
			PreparedStmtUtils.setString(pstmt,1,obj.PackageID);
			PreparedStmtUtils.setString(pstmt,2,obj.TerminalNo);
			PreparedStmtUtils.setString(pstmt,3,obj.BoxNo);
			PreparedStmtUtils.setString(pstmt,4,obj.ItemStatus);
			PreparedStmtUtils.setTimestamp(pstmt,5,obj.CreateTime);
			PreparedStmtUtils.setString(pstmt,6,obj.PPCID);
			PreparedStmtUtils.setString(pstmt,7,obj.ZoneID);
			PreparedStmtUtils.setString(pstmt,8,obj.CompanyID);
			PreparedStmtUtils.setString(pstmt,9,obj.RefNo);
			PreparedStmtUtils.setString(pstmt,10,obj.ParcelSize);
			PreparedStmtUtils.setString(pstmt,11,obj.CustomerID);
			PreparedStmtUtils.setString(pstmt,12,obj.CustomerName);
			PreparedStmtUtils.setString(pstmt,13,obj.CustomerAddress);
			PreparedStmtUtils.setString(pstmt,14,obj.CustomerMobile);
			PreparedStmtUtils.setString(pstmt,15,obj.DropAgentID);
			PreparedStmtUtils.setString(pstmt,16,obj.DynamicCode);
			PreparedStmtUtils.setInt(pstmt,17,obj.DropNum);
			PreparedStmtUtils.setDate(pstmt,18,obj.StoredDate);
			PreparedStmtUtils.setTimestamp(pstmt,19,obj.StoredTime);
			PreparedStmtUtils.setTimestamp(pstmt,20,obj.ExpiredTime);
			PreparedStmtUtils.setTimestamp(pstmt,21,obj.ReminderDateTime);
			PreparedStmtUtils.setString(pstmt,22,obj.OpenBoxKey);
			PreparedStmtUtils.setString(pstmt,23,obj.DADFlag);
			PreparedStmtUtils.setString(pstmt,24,obj.TradeWaterNo);
			PreparedStmtUtils.setString(pstmt,25,obj.PosPayFlag);
			PreparedStmtUtils.setString(pstmt,26,obj.UploadFlag);
			PreparedStmtUtils.setString(pstmt,27,obj.ParcelStatus);
			PreparedStmtUtils.setInt(pstmt,28,obj.PrintedFlag);
			PreparedStmtUtils.setString(pstmt,29,obj.ReportOrderID);
			PreparedStmtUtils.setTimestamp(pstmt,30,obj.LastModifyTime);
			PreparedStmtUtils.setString(pstmt,31,obj.Remark);
			result = pstmt.executeUpdate();

		}
		catch(SQLException e)
		{
			String errorCode = ErrorCode.ERR_ADDPTINBOXPACKAGEFAIL;
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

		String sql = "UPDATE PTInBoxPackage SET ";

		StringBuffer sbSet = new StringBuffer(256);
		sbSet.append(setCols.toSetSQL());
		StringBuffer sbWhere = new StringBuffer(256);
		if(whereCols != null){
			sbWhere.append(whereCols.toWhereSQL());
		}

		sql = sql + sbSet.toString() + " WHERE 1=1 " + sbWhere.toString();

		SQLLog.logUpdate(log,"PTInBoxPackage",setCols,whereCols);

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
			String errorCode = ErrorCode.ERR_MODPTINBOXPACKAGEFAIL;
			log.error("[DBERROR-("+ errorCode + ")]==>[sqlcode]:"+e.getErrorCode() + " [errmsg]:"+e.getMessage() + " [sql]:"+sql);
			throw new EduException(errorCode);
		}
		finally
		{
			dbSession.closeStatement(pstmt);
		}

		return result;
	}


	public int delete(PTInBoxPackage obj) throws EduException
	{
		PreparedStatement pstmt = null;
		int result = 0;

		String sql = "DELETE FROM PTInBoxPackage WHERE PackageID = ? ";

		log.debug("[delete sql]:" + "DELETE FROM PTInBoxPackage WHERE PackageID = " + StringUtils.addQuote(obj.PackageID) );

		try
		{
			pstmt = conn.prepareStatement(sql);
			PreparedStmtUtils.setString(pstmt,1,obj.PackageID);
			result = pstmt.executeUpdate();

		}
		catch(SQLException e)
		{
			String errorCode = ErrorCode.ERR_DELPTINBOXPACKAGEFAIL;
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

		String sql = "DELETE FROM PTInBoxPackage WHERE 1=1 " ;

		StringBuffer sbWhere = new StringBuffer(256);
		if(whereCols != null)
		{
			sbWhere.append(whereCols.toWhereSQL());
		}

		sql = sql + sbWhere.toString();

		SQLLog.logDelete(log,"PTInBoxPackage",whereCols);

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
			String errorCode = ErrorCode.ERR_DELPTINBOXPACKAGEFAIL;
			log.error("[DBERROR-("+ errorCode + ")]==>[sqlcode]:"+e.getErrorCode() + " [errmsg]:"+e.getMessage() + " [sql]:"+sql);
			throw new EduException(errorCode);
		}
		finally
		{
			dbSession.closeStatement(pstmt);
		}

		return result;
	}


	public boolean isExist(PTInBoxPackage obj) throws EduException
	{
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		int count = 0;

		String sql = "SELECT COUNT(*) FROM PTInBoxPackage WHERE PackageID = ? ";

		log.debug("[isExist sql]:" + "SELECT COUNT(*) FROM PTInBoxPackage WHERE PackageID = " + StringUtils.addQuote(obj.PackageID) );

		try
		{
			pstmt = conn.prepareStatement(sql);
			PreparedStmtUtils.setString(pstmt,1,obj.PackageID);
			rset = pstmt.executeQuery();

			rset.next();
			count = rset.getInt(1);
		}
		catch(SQLException e)
		{
			String errorCode = ErrorCode.ERR_QRYPTINBOXPACKAGEFAIL;
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

		String sql = "SELECT COUNT(*) FROM PTInBoxPackage WHERE 1=1 " ;

		StringBuffer sbWhere = new StringBuffer(256);
		if(whereCols != null)
		{
			sbWhere.append(whereCols.toWhereSQL());
		}

		sql = sql + sbWhere.toString();

		SQLLog.logIsExist(log,"PTInBoxPackage",whereCols);

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
			String errorCode = ErrorCode.ERR_QRYPTINBOXPACKAGEFAIL;
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


	public PTInBoxPackage find(PTInBoxPackage obj) throws EduException
	{
		PTInBoxPackage result = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		String sql = "SELECT PackageID,TerminalNo,BoxNo,ItemStatus,CreateTime,PPCID,ZoneID,CompanyID,RefNo,ParcelSize,CustomerID,CustomerName,CustomerAddress,CustomerMobile,DropAgentID,DynamicCode,DropNum,StoredDate,StoredTime,ExpiredTime,ReminderDateTime,OpenBoxKey,DADFlag,TradeWaterNo,PosPayFlag,UploadFlag,ParcelStatus,PrintedFlag,ReportOrderID,LastModifyTime,Remark  FROM PTInBoxPackage WHERE PackageID = ? ";

		log.debug("[find sql]:" + "SELECT PackageID,TerminalNo,BoxNo,ItemStatus,CreateTime,PPCID,ZoneID,CompanyID,RefNo,ParcelSize,CustomerID,CustomerName,CustomerAddress,CustomerMobile,DropAgentID,DynamicCode,DropNum,StoredDate,StoredTime,ExpiredTime,ReminderDateTime,OpenBoxKey,DADFlag,TradeWaterNo,PosPayFlag,UploadFlag,ParcelStatus,PrintedFlag,ReportOrderID,LastModifyTime,Remark  FROM PTInBoxPackage WHERE PackageID = " + StringUtils.addQuote(obj.PackageID) );

		try
		{
			pstmt = conn.prepareStatement(sql);
			PreparedStmtUtils.setString(pstmt,1,obj.PackageID);
			rset = pstmt.executeQuery();

			if(rset.next() == true)
			{
				result = new PTInBoxPackage();
				result.PackageID = rset.getString(1);
				result.TerminalNo = rset.getString(2);
				result.BoxNo = rset.getString(3);
				result.ItemStatus = rset.getString(4);
				result.CreateTime = rset.getTimestamp(5);
				result.PPCID = rset.getString(6);
				result.ZoneID = rset.getString(7);
				result.CompanyID = rset.getString(8);
				result.RefNo = rset.getString(9);
				result.ParcelSize = rset.getString(10);
				result.CustomerID = rset.getString(11);
				result.CustomerName = rset.getString(12);
				result.CustomerAddress = rset.getString(13);
				result.CustomerMobile = rset.getString(14);
				result.DropAgentID = rset.getString(15);
				result.DynamicCode = rset.getString(16);
				result.DropNum = rset.getInt(17);
				result.StoredDate = rset.getDate(18);
				result.StoredTime = rset.getTimestamp(19);
				result.ExpiredTime = rset.getTimestamp(20);
				result.ReminderDateTime = rset.getTimestamp(21);
				result.OpenBoxKey = rset.getString(22);
				result.DADFlag = rset.getString(23);
				result.TradeWaterNo = rset.getString(24);
				result.PosPayFlag = rset.getString(25);
				result.UploadFlag = rset.getString(26);
				result.ParcelStatus = rset.getString(27);
				result.PrintedFlag = rset.getInt(28);
				result.ReportOrderID = rset.getString(29);
				result.LastModifyTime = rset.getTimestamp(30);
				result.Remark = rset.getString(31);

			}
			else
			{
				throw new EduException(ErrorCode.ERR_PTINBOXPACKAGENODATA);
			}
		}
		catch(SQLException e)
		{
			String errorCode = ErrorCode.ERR_QRYPTINBOXPACKAGEFAIL;
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

		String sql = "SELECT * FROM PTInBoxPackage WHERE 1=1 ";
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
			String errorCode = ErrorCode.ERR_QRYPTINBOXPACKAGEFAIL;
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

		String sql = "SELECT * FROM PTInBoxPackage WHERE 1=1 ";
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
			String errorCode = ErrorCode.ERR_QRYPTINBOXPACKAGEFAIL;
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
		String sql = "SELECT * FROM  PTInBoxPackage WHERE 1=1 ";

		StringBuffer sbWhere = new StringBuffer(256);
		if(whereCols != null)
		{
			sbWhere.append(whereCols.toWhereSQL());
		}

		sql = sql + sbWhere.toString();

		java.util.List<String> list = new java.util.LinkedList<String>();
		list.add("PackageID");

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
			String errorCode = ErrorCode.ERR_QRYPTINBOXPACKAGEFAIL;
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

		String sql = "SELECT " + fName + " FROM PTInBoxPackage WHERE 1=1 ";
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
			String errorCode = ErrorCode.ERR_QRYPTINBOXPACKAGEFAIL;
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