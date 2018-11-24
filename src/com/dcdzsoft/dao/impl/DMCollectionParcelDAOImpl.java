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

public class DMCollectionParcelDAOImpl implements DMCollectionParcelDAO
{
	private static final Log log = LogFactory.getLog(DMCollectionParcelDAOImpl.class.getName());

	private DBSession dbSession = LocalSessionHolder.getCurrentDBSession();

	private Connection conn = dbSession.getConnection();

	private static final int RSET_FETCH_SIZE = 50;


	public int insert(DMCollectionParcel obj) throws EduException
	{
		PreparedStatement pstmt = null;
		int result = 0;

		String sql = "INSERT INTO DMCollectionParcel(PackageID,CreateTime,BPartnerID,ItemStatus,ServiceID,ServiceAmt,CollectionFlag,CollectionAmt,ReturnFlag,ZoneID,CompanyID,TradeWaterNo,CustomerAddress,CustomerName,CustomerMobile,CustomerID,TerminalNo,BoxNo,ParcelSize,CollectZoneID,CollectionType,CollectionAgentID,CollectionTime,PrintedFlag,ReportOrderID,PPCID,LastModifyTime,Remark)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

		SQLLog.logInsert(log, obj);

		try
		{
			pstmt = conn.prepareStatement(sql);
			PreparedStmtUtils.setString(pstmt,1,obj.PackageID);
			PreparedStmtUtils.setTimestamp(pstmt,2,obj.CreateTime);
			PreparedStmtUtils.setString(pstmt,3,obj.BPartnerID);
			PreparedStmtUtils.setString(pstmt,4,obj.ItemStatus);
			PreparedStmtUtils.setString(pstmt,5,obj.ServiceID);
			PreparedStmtUtils.setDouble(pstmt,6,obj.ServiceAmt);
			PreparedStmtUtils.setString(pstmt,7,obj.CollectionFlag);
			PreparedStmtUtils.setDouble(pstmt,8,obj.CollectionAmt);
			PreparedStmtUtils.setString(pstmt,9,obj.ReturnFlag);
			PreparedStmtUtils.setString(pstmt,10,obj.ZoneID);
			PreparedStmtUtils.setString(pstmt,11,obj.CompanyID);
			PreparedStmtUtils.setString(pstmt,12,obj.TradeWaterNo);
			PreparedStmtUtils.setString(pstmt,13,obj.CustomerAddress);
			PreparedStmtUtils.setString(pstmt,14,obj.CustomerName);
			PreparedStmtUtils.setString(pstmt,15,obj.CustomerMobile);
			PreparedStmtUtils.setString(pstmt,16,obj.CustomerID);
			PreparedStmtUtils.setString(pstmt,17,obj.TerminalNo);
			PreparedStmtUtils.setString(pstmt,18,obj.BoxNo);
			PreparedStmtUtils.setString(pstmt,19,obj.ParcelSize);
			PreparedStmtUtils.setString(pstmt,20,obj.CollectZoneID);
			PreparedStmtUtils.setString(pstmt,21,obj.CollectionType);
			PreparedStmtUtils.setString(pstmt,22,obj.CollectionAgentID);
			PreparedStmtUtils.setTimestamp(pstmt,23,obj.CollectionTime);
			PreparedStmtUtils.setInt(pstmt,24,obj.PrintedFlag);
			PreparedStmtUtils.setString(pstmt,25,obj.ReportOrderID);
			PreparedStmtUtils.setString(pstmt,26,obj.PPCID);
			PreparedStmtUtils.setTimestamp(pstmt,27,obj.LastModifyTime);
			PreparedStmtUtils.setString(pstmt,28,obj.Remark);
			result = pstmt.executeUpdate();

		}
		catch(SQLException e)
		{
			String errorCode = ErrorCode.ERR_ADDDMCOLLECTIONPARCELFAIL;
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

		String sql = "UPDATE DMCollectionParcel SET ";

		StringBuffer sbSet = new StringBuffer(256);
		sbSet.append(setCols.toSetSQL());
		StringBuffer sbWhere = new StringBuffer(256);
		if(whereCols != null){
			sbWhere.append(whereCols.toWhereSQL());
		}

		sql = sql + sbSet.toString() + " WHERE 1=1 " + sbWhere.toString();

		SQLLog.logUpdate(log,"DMCollectionParcel",setCols,whereCols);

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
			String errorCode = ErrorCode.ERR_MODDMCOLLECTIONPARCELFAIL;
			log.error("[DBERROR-("+ errorCode + ")]==>[sqlcode]:"+e.getErrorCode() + " [errmsg]:"+e.getMessage() + " [sql]:"+sql);
			throw new EduException(errorCode);
		}
		finally
		{
			dbSession.closeStatement(pstmt);
		}

		return result;
	}


	public int delete(DMCollectionParcel obj) throws EduException
	{
		PreparedStatement pstmt = null;
		int result = 0;

		String sql = "DELETE FROM DMCollectionParcel WHERE PackageID = ?  AND CreateTime = ? ";

		log.debug("[delete sql]:" + "DELETE FROM DMCollectionParcel WHERE PackageID = " + StringUtils.addQuote(obj.PackageID) +
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
			String errorCode = ErrorCode.ERR_DELDMCOLLECTIONPARCELFAIL;
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

		String sql = "DELETE FROM DMCollectionParcel WHERE 1=1 " ;

		StringBuffer sbWhere = new StringBuffer(256);
		if(whereCols != null)
		{
			sbWhere.append(whereCols.toWhereSQL());
		}

		sql = sql + sbWhere.toString();

		SQLLog.logDelete(log,"DMCollectionParcel",whereCols);

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
			String errorCode = ErrorCode.ERR_DELDMCOLLECTIONPARCELFAIL;
			log.error("[DBERROR-("+ errorCode + ")]==>[sqlcode]:"+e.getErrorCode() + " [errmsg]:"+e.getMessage() + " [sql]:"+sql);
			throw new EduException(errorCode);
		}
		finally
		{
			dbSession.closeStatement(pstmt);
		}

		return result;
	}


	public boolean isExist(DMCollectionParcel obj) throws EduException
	{
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		int count = 0;

		String sql = "SELECT COUNT(*) FROM DMCollectionParcel WHERE PackageID = ?  AND CreateTime = ? ";

		log.debug("[isExist sql]:" + "SELECT COUNT(*) FROM DMCollectionParcel WHERE PackageID = " + StringUtils.addQuote(obj.PackageID) +
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
			String errorCode = ErrorCode.ERR_QRYDMCOLLECTIONPARCELFAIL;
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

		String sql = "SELECT COUNT(*) FROM DMCollectionParcel WHERE 1=1 " ;

		StringBuffer sbWhere = new StringBuffer(256);
		if(whereCols != null)
		{
			sbWhere.append(whereCols.toWhereSQL());
		}

		sql = sql + sbWhere.toString();

		SQLLog.logIsExist(log,"DMCollectionParcel",whereCols);

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
			String errorCode = ErrorCode.ERR_QRYDMCOLLECTIONPARCELFAIL;
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


	public DMCollectionParcel find(DMCollectionParcel obj) throws EduException
	{
		DMCollectionParcel result = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		String sql = "SELECT PackageID,CreateTime,BPartnerID,ItemStatus,ServiceID,ServiceAmt,CollectionFlag,CollectionAmt,ReturnFlag,ZoneID,CompanyID,TradeWaterNo,CustomerAddress,CustomerName,CustomerMobile,CustomerID,TerminalNo,BoxNo,ParcelSize,CollectZoneID,CollectionType,CollectionAgentID,CollectionTime,PrintedFlag,ReportOrderID,PPCID,LastModifyTime,Remark  FROM DMCollectionParcel WHERE PackageID = ?  AND CreateTime = ? ";

		log.debug("[find sql]:" + "SELECT PackageID,CreateTime,BPartnerID,ItemStatus,ServiceID,ServiceAmt,CollectionFlag,CollectionAmt,ReturnFlag,ZoneID,CompanyID,TradeWaterNo,CustomerAddress,CustomerName,CustomerMobile,CustomerID,TerminalNo,BoxNo,ParcelSize,CollectZoneID,CollectionType,CollectionAgentID,CollectionTime,PrintedFlag,ReportOrderID,PPCID,LastModifyTime,Remark  FROM DMCollectionParcel WHERE PackageID = " + StringUtils.addQuote(obj.PackageID) +
						" AND CreateTime = " + StringUtils.addQuote(obj.CreateTime) );

		try
		{
			pstmt = conn.prepareStatement(sql);
			PreparedStmtUtils.setString(pstmt,1,obj.PackageID);
			PreparedStmtUtils.setTimestamp(pstmt,2,obj.CreateTime);
			rset = pstmt.executeQuery();

			if(rset.next() == true)
			{
				result = new DMCollectionParcel();
				result.PackageID = rset.getString(1);
				result.CreateTime = rset.getTimestamp(2);
				result.BPartnerID = rset.getString(3);
				result.ItemStatus = rset.getString(4);
				result.ServiceID = rset.getString(5);
				result.ServiceAmt = rset.getDouble(6);
				result.CollectionFlag = rset.getString(7);
				result.CollectionAmt = rset.getDouble(8);
				result.ReturnFlag = rset.getString(9);
				result.ZoneID = rset.getString(10);
				result.CompanyID = rset.getString(11);
				result.TradeWaterNo = rset.getString(12);
				result.CustomerAddress = rset.getString(13);
				result.CustomerName = rset.getString(14);
				result.CustomerMobile = rset.getString(15);
				result.CustomerID = rset.getString(16);
				result.TerminalNo = rset.getString(17);
				result.BoxNo = rset.getString(18);
				result.ParcelSize = rset.getString(19);
				result.CollectZoneID = rset.getString(20);
				result.CollectionType = rset.getString(21);
				result.CollectionAgentID = rset.getString(22);
				result.CollectionTime = rset.getTimestamp(23);
				result.PrintedFlag = rset.getInt(24);
				result.ReportOrderID = rset.getString(25);
				result.PPCID = rset.getString(26);
				result.LastModifyTime = rset.getTimestamp(27);
				result.Remark = rset.getString(28);

			}
			else
			{
				throw new EduException(ErrorCode.ERR_DMCOLLECTIONPARCELNODATA);
			}
		}
		catch(SQLException e)
		{
			String errorCode = ErrorCode.ERR_QRYDMCOLLECTIONPARCELFAIL;
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

		String sql = "SELECT * FROM DMCollectionParcel WHERE 1=1 ";
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
			String errorCode = ErrorCode.ERR_QRYDMCOLLECTIONPARCELFAIL;
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

		String sql = "SELECT * FROM DMCollectionParcel WHERE 1=1 ";
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
			String errorCode = ErrorCode.ERR_QRYDMCOLLECTIONPARCELFAIL;
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
		String sql = "SELECT * FROM  DMCollectionParcel WHERE 1=1 ";

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
			String errorCode = ErrorCode.ERR_QRYDMCOLLECTIONPARCELFAIL;
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

		String sql = "SELECT " + fName + " FROM DMCollectionParcel WHERE 1=1 ";
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
			String errorCode = ErrorCode.ERR_QRYDMCOLLECTIONPARCELFAIL;
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