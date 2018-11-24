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
 * <p>Description: 投递量统计数量 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class QYStatDeliverOrderCount extends ActionBean
{

    public int doBusiness(InParamQYStatDeliverOrderCount in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        int result = 0;

		//1.验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(in.OperID))
			throw new EduException(ErrorCode.ERR_PARMERR);


		//2.调用CommonDAO.isOnline(操作员编号)判断操作员是否在线。
		OPOperOnline operOnline = commonDAO.isOnline(in.OperID);


		//3.调用UtilDAO.getCurrentDateTime()获得系统日期时间。
		java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();
		java.sql.Date sysDate = DateUtils.toSQLDate(sysDateTime);

		String reportID = DateUtils.timestampToString(sysDateTime);

        String limitSQL = commonDAO.getQueryZoneLimitSQL(operOnline.OperID, operOnline.ZoneID);
        
        
        PreparedWhereExpression whereSQL = new PreparedWhereExpression();
        whereSQL.checkAdd("OccurMonth", in.OccurMonth);
        whereSQL.checkAdd("OccurYear", in.OccurYear);   
        whereSQL.checkAdd("a.CompanyID", in.CompanyID);
        whereSQL.checkAdd("a.PostmanID", in.PostmanID);
        whereSQL.checkAdd("a.TerminalNo", in.TerminalNo);
        whereSQL.checkAdd("b.TerminalName", in.TerminalName);
        //if(StringUtils.isNotEmpty(in.TerminalName))
        //   whereSQL.add("b.TerminalName", " LIKE ", "%" + in.TerminalName + "%");
        
        String fromStr    = " ";
        String whereStr   = " ";
        String groupbyStr = " ";
        StringBuffer sqlQry  = new StringBuffer(" ");
        if("1".equals(in.StatFlag)){//1按投递公司统计
            fromStr    = " ,IMCompany c ";
            sqlQry.append(" ,a.CompanyID,c.CompanyName,'' AS PostmanID,'' AS PostmanName ");
            whereStr   = " AND a.CompanyID = c.CompanyID ";
            groupbyStr = " ,a.CompanyID,c.CompanyName ";
        }else if("2".equals(in.StatFlag)){//2按投递员统计
            fromStr    = " ,PMPostman c,IMCompany d ";
            sqlQry.append(" ,a.CompanyID,d.CompanyName,a.PostmanID,c.PostmanName ");
            whereStr   = " AND a.PostmanID = c.PostmanID AND a.CompanyID=d.CompanyID";
            groupbyStr = " ,a.PostmanID,c.PostmanName, a.CompanyID,d.CompanyName  ";
        }else{
            sqlQry.append(",'' AS CompanyID,'' AS CompanyName,'' AS PostmanID,'' AS PostmanName ");
        }
        
        //生成SQL Coverage:1-Daily；2-Weekly；3-Monthly；4-Quartely；5-Yearly
        switch(in.Coverage){
        case "1"://每天统计
            sqlQry.append(",a.OccurDate AS Period");
            groupbyStr += ",a.OccurDate ";
            break;
        case "2"://每周统计
            sqlQry.append(",a.WeekPeriod AS Period");
            groupbyStr += ",a.WeekPeriod ";
            break;
        case "3"://每月统计
            sqlQry.append(",a.MonthPeriod AS Period");
            groupbyStr += ",a.MonthPeriod ";
            break;
        case "4"://每季统计
            sqlQry.append(",a.QuarterPeriod AS Period");
            groupbyStr += ",a.QuarterPeriod ";
            break;
        case "5"://每年统计
            sqlQry.append(",a.OccurYear AS Period");
            groupbyStr += ",a.OccurYear";
            break;
        default://每年统计
            sqlQry.append(",a.OccurYear AS Period");
            groupbyStr += ",a.OccurYear";
            break;
        }
      
        String sql = "SELECT '" + reportID + "' AS ReportID,a.TerminalNo,b.TerminalName"
                + ",SUM(a.DropNum) AS DropNum,SUM(a.InBoxNum) AS InBoxNum,SUM(a.ExpiredNum) AS ExpiredNum"
                + ",SUM(a.PickUpNum) AS PickUpNum,SUM(a.ReturnNum) AS ReturnNum";
        String groupby = " GROUP BY a.TerminalNo,b.TerminalName"+groupbyStr;
        sql += sqlQry.toString();
        sql += " FROM V_QYDropOrderStat0 a,TBTerminal b "+fromStr
                + " WHERE 1=1 AND a.TerminalNo = b.TerminalNo "+whereStr
                + whereSQL.getPreparedWhereSQL() + limitSQL+groupby;
        
        java.util.LinkedList list = new java.util.LinkedList();
        list.add("a.TerminalNo");
        
        RowSet rset = dbSession.executeQuery(sql,whereSQL);
        
        result = RowSetUtils.getCountOfRowSet(rset);
        
        return result;
    }
}
