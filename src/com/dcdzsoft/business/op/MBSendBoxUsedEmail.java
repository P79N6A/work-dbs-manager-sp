package com.dcdzsoft.business.op;

import java.sql.SQLException;

import javax.sql.RowSet;

import com.dcdzsoft.EduException;
import com.dcdzsoft.sda.db.*;
import com.dcdzsoft.util.*;
import com.dcdzsoft.dto.function.*;
import com.dcdzsoft.dto.business.*;
import com.dcdzsoft.dao.*;
import com.dcdzsoft.dao.common.*;
import com.dcdzsoft.email.EmailInfo;
import com.dcdzsoft.email.EmailSenderManager;
import com.dcdzsoft.constant.*;
import com.dcdzsoft.business.ActionBean;

/**
 * <p>Title: 智能自助包裹柜系统</p>
 * <p>Description: 格口使用统计每天用电子邮件发送</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author 王中立
 * @version 1.0
 */
public class MBSendBoxUsedEmail extends ActionBean
{
	
    public boolean doBusiness(EmailInfo in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        RowSet rset = null;
        
//        if (StringUtils.isEmpty(in.getMailServerHost())
//        	|| StringUtils.isEmpty(in.getUserName())
//        	|| StringUtils.isEmpty(in.getPassword())
//        	)
//			throw new EduException(ErrorCode.ERR_PARMERR);
        
		java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();
		String reportID = DateUtils.timestampToString(sysDateTime);
		
		String sql = "SELECT '" + reportID + "' AS ReportID,TerminalNo,TerminalName,Location,"
				+ "COUNT(*) AS BoxNum,"
				+ "SUM(UsedNum) AS UsedNum,"
				+ "SUM(FreeSmallNum) AS FreeSmallNum,"
				+ "SUM(FreeMediumNum) AS FreeMediumNum,"
				+ "SUM(FreeLargeNum) AS FreeLargeNum,"
				//+ "SUM(FreeSuperNum) AS FreeSuperNum,"
				//+ "SUM(FreeFreshNum) AS FreeFreshNum,"
				+ "SUM(FaultNum) AS FaultNum "
				+ " FROM V_TBBoxStat a WHERE 1=1 "
				+ " GROUP BY TerminalNo,TerminalName,Location "
				+ " ORDER BY TerminalNo ASC ";

        rset = dbSession.executeQuery(sql);
        //将格口使用情况查询出来写进电子邮件发送。
        String str = "<table><tr>"
        		+ "<th>LockerId</th>"
        		+ "<th>LockerName</th>"
        		+ "<th>Location</th>"
        		+ "<th>BoxNum</th>"
        		+ "<th>UsedNum</th>"
        		+ "<th>FreeSmallNum</th>"
        		+ "<th>FreeMediumNum</th>"
        		+ "<th>FreeLargeNum</th>"
        		+ "<th>FaultNum</th>"
        		+ "</tr>";
        StringBuffer textContent = new StringBuffer(str);
        try {
			while(RowSetUtils.rowsetNext(rset)){	
				str = "<tr><td>"+rset.getString("TerminalNo")+"</td>"
					+ "<td>"+rset.getString("TerminalName")+"</td>"
					+ "<td>"+rset.getString("Location")+"</td>"
					+ "<td>"+rset.getString("BoxNum")+"</td>"
					+ "<td>"+rset.getString("UsedNum")+"</td>"
					+ "<td>"+rset.getString("FreeSmallNum")+"</td>"
					+ "<td>"+rset.getString("FreeMediumNum")+"</td>"
					+ "<td>"+rset.getString("FreeLargeNum")+"</td>"
					+ "<td> "+rset.getString("FaultNum")+"</td>"
					+ "</tr>";
				textContent.append(str);
			}
			textContent.append("</table>");
			in.setContent(textContent.toString());
			in.setSubject("Statistics of use of lattices");
//			in.setToAddress("chenyufeng@hzdongcheng.cn");//可以查询收件人信息。
			EmailSenderManager.getInstance().sendHtmlMail(in);
	        return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
    }
}
