package com.dcdzsoft.business.im;

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
 * <p>Description: 揽件区域列表查询 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class IMCollectZoneListQry extends ActionBean
{

    public RowSet doBusiness(InParamIMCollectZoneListQry in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        RowSet rset = null;

		//1.	验证输入参数是否有效，如果无效返回-1。
		//if (StringUtils.isEmpty(in.OperID))
		//	throw new EduException(ErrorCode.ERR_PARMERR);


		//2.	调用CommonDAO.isOnline(管理员编号)判断管理员是否在线。
		if (StringUtils.isNotEmpty(in.OperID)){
            OPOperOnline operOnline = commonDAO.isOnline(in.OperID);
        }
		
		String limitsql = "";//commonDAO.getQueryZoneLimitSQL(operOnline.OperID, operOnline.ZoneID);
		
		PreparedWhereExpression whereSQL = new PreparedWhereExpression();
        whereSQL.checkAdd("CollectZoneID", in.CollectZoneID);
        whereSQL.checkAdd("ZoneID", in.ZoneID);
        String sql = "SELECT CollectZoneID,CollectZoneName,ZoneID FROM IMCollectZone a WHERE 1=1 " 
        		+ limitsql+ whereSQL.getPreparedWhereSQL() + " ORDER BY CollectZoneID";
        
        rset = dbSession.executeQuery(sql,whereSQL);

        return rset;
    }
}
