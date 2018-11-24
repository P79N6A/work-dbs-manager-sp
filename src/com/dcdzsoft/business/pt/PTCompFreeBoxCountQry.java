package com.dcdzsoft.business.pt;

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
 * <p>Description: 公司可用空箱类型数量查询 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class PTCompFreeBoxCountQry extends ActionBean
{

    public RowSet doBusiness(InParamPTCompFreeBoxCountQry in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        
		//1.	验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(in.OperID)
			||StringUtils.isEmpty(in.TerminalNo))
			throw new EduException(ErrorCode.ERR_PARMERR);
		
		//2.	调用CommonDAO.isOnline(操作员编号)判断操作员是否在线。
		OPOperOnline operOnline = commonDAO.isOnline(in.OperID);
		
		IMZoneDAO zoneDAO = daoFactory.getIMZoneDAO();
        IMZone zone = new IMZone();
        zone.ZoneID = operOnline.ZoneID;
        
        zone = zoneDAO.find(zone);
		
		//String limitsql = commonDAO.getQueryCompanyLimitSQL(operOnline.OperID, operOnline.ZoneID);
		
		String groupbySQL = " GROUP BY CompanyID,TerminalNo";
		PreparedWhereExpression whereSQL = new PreparedWhereExpression();
		whereSQL.add("TerminalNo", in.TerminalNo);
		whereSQL.add("CompanyID", zone.CompanyID);
		
		String sql = "SELECT CompanyID,TerminalNo,sum(FreeSmallNum) AS FreeSmallNum,sum(FreeMediumNum) AS FreeMediumNum,sum(FreeLargeNum) AS FreeLargeNum,sum(FreeSuperNum) AS FreeSuperNum,sum(FreeFreshNum) AS FreeFreshNum "
				+ " FROM V_IMCompFreeBoxStat"
				+ " WHERE 1=1 " + whereSQL.getPreparedWhereSQL() + groupbySQL;
		
		RowSet rset = dbSession.executeQuery(sql,whereSQL);
		
        return rset;
    }
}
