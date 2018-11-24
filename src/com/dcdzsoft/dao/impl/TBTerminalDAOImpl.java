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

public class TBTerminalDAOImpl implements TBTerminalDAO
{
	private static final Log log = LogFactory.getLog(TBTerminalDAOImpl.class.getName());

	private DBSession dbSession = LocalSessionHolder.getCurrentDBSession();

	private Connection conn = dbSession.getConnection();

	private static final int RSET_FETCH_SIZE = 50;


	public int insert(TBTerminal obj) throws EduException
	{
		PreparedStatement pstmt = null;
		int result = 0;

		String sql = "INSERT INTO TBTerminal(TerminalNo,TerminalName,TerminalStatus,TerminalType,Address,Location,Longitude,Latitude,ZoneID,City,Zipcode,RegisterFlag,AppRegisterLimit,AppRegisterFlag,MaintMobile,MaintEmail,OfBureau,OfSegment,DepartmentID,MBDeviceNo,DeskNum,BoxNum,MacAddr,Brand,Model,MachineSize,MainDeskAddress,CreateTime,LastModifyTime,Remark)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

		SQLLog.logInsert(log, obj);

		try
		{
			pstmt = conn.prepareStatement(sql);
			PreparedStmtUtils.setString(pstmt,1,obj.TerminalNo);
			PreparedStmtUtils.setString(pstmt,2,obj.TerminalName);
			PreparedStmtUtils.setString(pstmt,3,obj.TerminalStatus);
			PreparedStmtUtils.setString(pstmt,4,obj.TerminalType);
			PreparedStmtUtils.setString(pstmt,5,obj.Address);
			PreparedStmtUtils.setString(pstmt,6,obj.Location);
			PreparedStmtUtils.setDouble(pstmt,7,obj.Longitude);
			PreparedStmtUtils.setDouble(pstmt,8,obj.Latitude);
			PreparedStmtUtils.setString(pstmt,9,obj.ZoneID);
			PreparedStmtUtils.setString(pstmt,10,obj.City);
			PreparedStmtUtils.setString(pstmt,11,obj.Zipcode);
			PreparedStmtUtils.setString(pstmt,12,obj.RegisterFlag);
			PreparedStmtUtils.setInt(pstmt,13,obj.AppRegisterLimit);
			PreparedStmtUtils.setString(pstmt,14,obj.AppRegisterFlag);
			PreparedStmtUtils.setString(pstmt,15,obj.MaintMobile);
			PreparedStmtUtils.setString(pstmt,16,obj.MaintEmail);
			PreparedStmtUtils.setString(pstmt,17,obj.OfBureau);
			PreparedStmtUtils.setString(pstmt,18,obj.OfSegment);
			PreparedStmtUtils.setString(pstmt,19,obj.DepartmentID);
			PreparedStmtUtils.setString(pstmt,20,obj.MBDeviceNo);
			PreparedStmtUtils.setInt(pstmt,21,obj.DeskNum);
			PreparedStmtUtils.setInt(pstmt,22,obj.BoxNum);
			PreparedStmtUtils.setString(pstmt,23,obj.MacAddr);
			PreparedStmtUtils.setString(pstmt,24,obj.Brand);
			PreparedStmtUtils.setString(pstmt,25,obj.Model);
			PreparedStmtUtils.setString(pstmt,26,obj.MachineSize);
			PreparedStmtUtils.setInt(pstmt,27,obj.MainDeskAddress);
			PreparedStmtUtils.setTimestamp(pstmt,28,obj.CreateTime);
			PreparedStmtUtils.setTimestamp(pstmt,29,obj.LastModifyTime);
			PreparedStmtUtils.setString(pstmt,30,obj.Remark);
			result = pstmt.executeUpdate();

		}
		catch(SQLException e)
		{
			String errorCode = ErrorCode.ERR_ADDTBTERMINALFAIL;
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

		String sql = "UPDATE TBTerminal SET ";

		StringBuffer sbSet = new StringBuffer(256);
		sbSet.append(setCols.toSetSQL());
		StringBuffer sbWhere = new StringBuffer(256);
		if(whereCols != null){
			sbWhere.append(whereCols.toWhereSQL());
		}

		sql = sql + sbSet.toString() + " WHERE 1=1 " + sbWhere.toString();

		SQLLog.logUpdate(log,"TBTerminal",setCols,whereCols);

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
			String errorCode = ErrorCode.ERR_MODTBTERMINALFAIL;
			log.error("[DBERROR-("+ errorCode + ")]==>[sqlcode]:"+e.getErrorCode() + " [errmsg]:"+e.getMessage() + " [sql]:"+sql);
			throw new EduException(errorCode);
		}
		finally
		{
			dbSession.closeStatement(pstmt);
		}

		return result;
	}


	public int delete(TBTerminal obj) throws EduException
	{
		PreparedStatement pstmt = null;
		int result = 0;

		String sql = "DELETE FROM TBTerminal WHERE TerminalNo = ? ";

		log.debug("[delete sql]:" + "DELETE FROM TBTerminal WHERE TerminalNo = " + StringUtils.addQuote(obj.TerminalNo) );

		try
		{
			pstmt = conn.prepareStatement(sql);
			PreparedStmtUtils.setString(pstmt,1,obj.TerminalNo);
			result = pstmt.executeUpdate();

		}
		catch(SQLException e)
		{
			String errorCode = ErrorCode.ERR_DELTBTERMINALFAIL;
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

		String sql = "DELETE FROM TBTerminal WHERE 1=1 " ;

		StringBuffer sbWhere = new StringBuffer(256);
		if(whereCols != null)
		{
			sbWhere.append(whereCols.toWhereSQL());
		}

		sql = sql + sbWhere.toString();

		SQLLog.logDelete(log,"TBTerminal",whereCols);

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
			String errorCode = ErrorCode.ERR_DELTBTERMINALFAIL;
			log.error("[DBERROR-("+ errorCode + ")]==>[sqlcode]:"+e.getErrorCode() + " [errmsg]:"+e.getMessage() + " [sql]:"+sql);
			throw new EduException(errorCode);
		}
		finally
		{
			dbSession.closeStatement(pstmt);
		}

		return result;
	}


	public boolean isExist(TBTerminal obj) throws EduException
	{
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		int count = 0;

		String sql = "SELECT COUNT(*) FROM TBTerminal WHERE TerminalNo = ? ";

		log.debug("[isExist sql]:" + "SELECT COUNT(*) FROM TBTerminal WHERE TerminalNo = " + StringUtils.addQuote(obj.TerminalNo) );

		try
		{
			pstmt = conn.prepareStatement(sql);
			PreparedStmtUtils.setString(pstmt,1,obj.TerminalNo);
			rset = pstmt.executeQuery();

			rset.next();
			count = rset.getInt(1);
		}
		catch(SQLException e)
		{
			String errorCode = ErrorCode.ERR_QRYTBTERMINALFAIL;
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

		String sql = "SELECT COUNT(*) FROM TBTerminal WHERE 1=1 " ;

		StringBuffer sbWhere = new StringBuffer(256);
		if(whereCols != null)
		{
			sbWhere.append(whereCols.toWhereSQL());
		}

		sql = sql + sbWhere.toString();

		SQLLog.logIsExist(log,"TBTerminal",whereCols);

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
			String errorCode = ErrorCode.ERR_QRYTBTERMINALFAIL;
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


	public TBTerminal find(TBTerminal obj) throws EduException
	{
		TBTerminal result = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		String sql = "SELECT TerminalNo,TerminalName,TerminalStatus,TerminalType,Address,Location,Longitude,Latitude,ZoneID,City,Zipcode,RegisterFlag,AppRegisterLimit,AppRegisterFlag,MaintMobile,MaintEmail,OfBureau,OfSegment,DepartmentID,MBDeviceNo,DeskNum,BoxNum,MacAddr,Brand,Model,MachineSize,MainDeskAddress,CreateTime,LastModifyTime,Remark  FROM TBTerminal WHERE TerminalNo = ? ";

		log.debug("[find sql]:" + "SELECT TerminalNo,TerminalName,TerminalStatus,TerminalType,Address,Location,Longitude,Latitude,ZoneID,City,Zipcode,RegisterFlag,AppRegisterLimit,AppRegisterFlag,MaintMobile,MaintEmail,OfBureau,OfSegment,DepartmentID,MBDeviceNo,DeskNum,BoxNum,MacAddr,Brand,Model,MachineSize,MainDeskAddress,CreateTime,LastModifyTime,Remark  FROM TBTerminal WHERE TerminalNo = " + StringUtils.addQuote(obj.TerminalNo) );

		try
		{
			pstmt = conn.prepareStatement(sql);
			PreparedStmtUtils.setString(pstmt,1,obj.TerminalNo);
			rset = pstmt.executeQuery();

			if(rset.next() == true)
			{
				result = new TBTerminal();
				result.TerminalNo = rset.getString(1);
				result.TerminalName = rset.getString(2);
				result.TerminalStatus = rset.getString(3);
				result.TerminalType = rset.getString(4);
				result.Address = rset.getString(5);
				result.Location = rset.getString(6);
				result.Longitude = rset.getDouble(7);
				result.Latitude = rset.getDouble(8);
				result.ZoneID = rset.getString(9);
				result.City = rset.getString(10);
				result.Zipcode = rset.getString(11);
				result.RegisterFlag = rset.getString(12);
				result.AppRegisterLimit = rset.getInt(13);
				result.AppRegisterFlag = rset.getString(14);
				result.MaintMobile = rset.getString(15);
				result.MaintEmail = rset.getString(16);
				result.OfBureau = rset.getString(17);
				result.OfSegment = rset.getString(18);
				result.DepartmentID = rset.getString(19);
				result.MBDeviceNo = rset.getString(20);
				result.DeskNum = rset.getInt(21);
				result.BoxNum = rset.getInt(22);
				result.MacAddr = rset.getString(23);
				result.Brand = rset.getString(24);
				result.Model = rset.getString(25);
				result.MachineSize = rset.getString(26);
				result.MainDeskAddress = rset.getInt(27);
				result.CreateTime = rset.getTimestamp(28);
				result.LastModifyTime = rset.getTimestamp(29);
				result.Remark = rset.getString(30);

			}
			else
			{
				throw new EduException(ErrorCode.ERR_TBTERMINALNODATA);
			}
		}
		catch(SQLException e)
		{
			String errorCode = ErrorCode.ERR_QRYTBTERMINALFAIL;
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

		String sql = "SELECT * FROM TBTerminal WHERE 1=1 ";
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
			String errorCode = ErrorCode.ERR_QRYTBTERMINALFAIL;
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

		String sql = "SELECT * FROM TBTerminal WHERE 1=1 ";
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
			String errorCode = ErrorCode.ERR_QRYTBTERMINALFAIL;
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
		String sql = "SELECT * FROM  TBTerminal WHERE 1=1 ";

		StringBuffer sbWhere = new StringBuffer(256);
		if(whereCols != null)
		{
			sbWhere.append(whereCols.toWhereSQL());
		}

		sql = sql + sbWhere.toString();

		java.util.List<String> list = new java.util.LinkedList<String>();
		list.add("TerminalNo");

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
			String errorCode = ErrorCode.ERR_QRYTBTERMINALFAIL;
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

		String sql = "SELECT " + fName + " FROM TBTerminal WHERE 1=1 ";
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
			String errorCode = ErrorCode.ERR_QRYTBTERMINALFAIL;
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