package com.dcdzsoft.business.mb;

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
 * <p>Description: 发送邮件 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author 王中立
 * @version 1.0
 */

public class MBSendBoxUsedEmail extends ActionBean
{

    public int doBusiness(InParamMBSendBoxUsedEmail in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        int result = 0;
        RowSet rset = null;
		EmailInfo emailInfo = new EmailInfo();
		//1.验证输入参数是否有效，如果无效返回-1。
		//2.调用UtilDAO.getSysDateTime()获得系统日期时间。
		java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();
		java.sql.Date sysDate = DateUtils.toSQLDate(sysDateTime);
		String limitsql = "";
		PreparedWhereExpression whereSQL = new PreparedWhereExpression();
//		if(SysDict.EMAIL_TYPE_BOXUSED.equals(in.EmailType)){//柜体使用统计邮件
//			
//		}else if (SysDict.EMAIL_TYPE_OFFLINEALARM.equals(in.EmailType)){//离线报警邮件
//			
//		}
		if(SysDict.EMAIL_SENDASRIGHTS_OWNERAZC.equals(in.SendAsRights)){//管理员AZC区域
			 whereSQL.checkAdd("ZoneID", in.ZoneID); 
		}else if(SysDict.EMAIL_SENDASRIGHTS_ALLAZC.equals(in.SendAsRights)){//所有AZC区域
			
		}
	
	    String sql = "SELECT * FROM V_MBSendReportEmail a WHERE 1=1 " + whereSQL.getPreparedWhereSQL() + limitsql;
	    rset = dbSession.executeQuery(sql,whereSQL);
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
			emailInfo.setContent(textContent.toString());
			emailInfo.setSubject("Statistics of use of lattices");
			emailInfo.setToAddress(in.Email);//可以查询收件人信息。
			EmailSenderManager.getInstance().sendHtmlMail(emailInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
        return result;
}
}
