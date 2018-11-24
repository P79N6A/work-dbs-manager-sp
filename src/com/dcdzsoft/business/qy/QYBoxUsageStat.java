package com.dcdzsoft.business.qy;

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
 * <p>Description: 格口使用统计 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class QYBoxUsageStat extends ActionBean
{

    public RowSet doBusiness(InParamQYBoxUsageStat in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        RowSet rset = null;

		//1.验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(in.OperID)
			||StringUtils.isEmpty(in.Coverage))
			throw new EduException(ErrorCode.ERR_PARMERR);

		//2.调用CommonDAO.isOnline(操作员编号)判断操作员是否在线。
		OPOperOnline operOnline = commonDAO.isOnline(in.OperID);

		//3.调用UtilDAO.getCurrentDateTime()获得系统日期时间。
		java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();
		java.sql.Date sysDate = DateUtils.toSQLDate(sysDateTime);
		
		String limitSQL = "";//utilDAO.getLimitSQL(operOnline.DepartmentID);
		
		if(in.BeginDate==null){
			in.BeginDate = DateUtils.addDate(sysDate, -365);//查询近2个月的订单记录
		}
		if(in.EndDate==null){
			in.EndDate = sysDate;
		}
		
		String dateFormTo = in.BeginDate.toString()+"~"+in.EndDate.toString();
		PreparedWhereExpression whereSQL = new PreparedWhereExpression();
        whereSQL.checkAdd("TerminalNo", in.TerminalNo);
        if(StringUtils.isNotEmpty(in.TerminalName))
           whereSQL.add("TerminalName", " LIKE ", "%" + in.TerminalName + "%");
        
        whereSQL.add("OccurDate", ">=",in.BeginDate);
        whereSQL.add("OccurDate", "<=",in.EndDate);
        
        String userinfo = "Total";
		if(StringUtils.isNotEmpty(in.ZoneID)){
			IMZoneDAO zoneDAO = daoFactory.getIMZoneDAO();
			IMZone zone = new IMZone();
			zone.ZoneID = in.ZoneID;
			try{
				zone = zoneDAO.find(zone);
    			userinfo = zone.ZoneName;
    			whereSQL.checkAdd("ZoneID", in.ZoneID);
    		}catch(EduException e){
    		}
		}else{
			if(StringUtils.isNotEmpty(in.CompanyID)){
				IMCompanyDAO companyDAO = daoFactory.getIMCompanyDAO();
	    		IMCompany company = new IMCompany();
	    		company.CompanyID = in.CompanyID;
	    		try{
	    			company = companyDAO.find(company);
	    			userinfo = company.CompanyName;
	    			whereSQL.addSQL(" AND a.ZoneID LIKE '"+in.CompanyID+"%'");
	    		}catch(EduException e){
	    		}
			}
		}
		
		String reportID = DateUtils.timestampToString(sysDateTime);
	    
		//生成SQL Coverage:1-Daily；2-Weekly；3-Monthly；4-Quartely；5-Yearly
        String groupby = " ";
		String sql = "SELECT '" + reportID + "' AS ReportID,"
				+ "'"+dateFormTo+"' AS DateFromTo,"
				+ "'"+userinfo+"' AS UserInfo,";
		
		
		if(StringUtils.isNotEmpty(in.TerminalName)
			|| StringUtils.isNotEmpty(in.TerminalNo)){
			sql += "TerminalNo,TerminalName,Location,";
			groupby += " TerminalNo,TerminalName,Location,";
		}else{
			sql += "'' AS TerminalNo,'' AS TerminalName,'' AS Location,";
		}
		
		String listasc = " ";
		switch(in.Coverage){
		case "1"://1-Daily
			sql += "'Daily' AS Coverage,"
					+ "a.OccurYear AS OccurYear,"//
					+ "a.OccurQuarter AS OccurQuarter,"//
					+ "a.OccurMonth AS OccurMonth,"//
					+ "a.OccurWeek AS OccurWeek,"//
					+ "a.OccurDate AS OccurDate,"; 
			groupby += "OccurDate,OccurWeek,OccurMonth,OccurQuarter,OccurYear";//
			listasc = "OccurDate ASC";
			break;
		case "2"://
			sql += "'Weekly' AS Coverage,"
					+ "a.OccurYear AS OccurYear,"
					+ "'' AS OccurQuarter,"//a.OccurQuarter
					+ "'' AS OccurMonth,"//a.OccurMonth
					+ "a.OccurWeek AS OccurWeek,"
					+ "'' AS OccurDate,"; 
			groupby += "OccurWeek,OccurYear";//,OccurQuarter
			listasc = "OccurYear,OccurWeek ASC";
			break;
		case "3"://
			sql += "'Monthly' AS Coverage,"
					+ "a.OccurYear AS OccurYear,"
					+ "a.OccurQuarter AS OccurQuarter,"//
					+ "a.OccurMonth AS OccurMonth,"
					+ "'' AS OccurWeek,"
					+ "'' AS OccurDate,"; 
			groupby += "OccurMonth,OccurQuarter,OccurYear";//
			listasc = "OccurYear,OccurMonth ASC";
			break;
		case "4"://
			sql += "'Quartely' AS Coverage,"
					+ "a.OccurYear AS OccurYear,"
					+ "a.OccurQuarter AS OccurQuarter,"
					+ "'' AS OccurMonth,"
					+ "'' AS OccurWeek,"
					+ "'' AS OccurDate,"; 
			groupby += "OccurQuarter,OccurYear";
			listasc = "OccurYear,OccurQuarter ASC";
			break;
		case "5"://5-Yearly
			sql += "'Yearly' AS Coverage,"
					+ "a.OccurYear AS OccurYear,"
					+ "'' AS OccurQuarter,"
					+ "'' AS OccurMonth,"
					+ "'' AS OccurWeek,"
					+ "'' AS OccurDate,";
			groupby += "OccurYear";
			listasc = "OccurYear ASC";
			break;
		default://default-Yearly
			sql += "'Yearly' AS Coverage,"
					+ "a.OccurYear AS OccurYear,"
					+ "'' AS OccurQuarter,"
					+ "'' AS OccurMonth,"
					+ "'' AS OccurWeek,"
					+ "'' AS OccurDate,";
			groupby += "OccurYear";
			listasc = "OccurYear ASC";
			break;
		}
		if(StringUtils.isNotEmpty(groupby.trim())){
			groupby = " GROUP BY " +groupby;
		}
		sql += "SUM(SmallNum) AS SmallCnt,"
				+ "SUM(MediumNum) AS MediumCnt,"
				+ "SUM(LargeNum) AS LargeCnt";
		sql += " FROM V_QYBoxUsage a WHERE 1=1 ";
		sql += whereSQL.getPreparedWhereSQL() + limitSQL+groupby;
		
		java.util.LinkedList list = new java.util.LinkedList();
        list.add(listasc);

        rset = dbSession.executeQuery(sql, list, in.recordBegin, in.recordNum, whereSQL);


        return rset;
    }
}
