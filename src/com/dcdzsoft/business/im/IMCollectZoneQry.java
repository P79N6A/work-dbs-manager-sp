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
 * <p>Description: 揽件区域信息查询 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class IMCollectZoneQry extends ActionBean
{

    public RowSet doBusiness(InParamIMCollectZoneQry in) throws EduException
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
        if(StringUtils.isNotEmpty(in.CollectZoneName)){
            whereSQL.add("CollectZoneName", " LIKE ", "%" + in.CollectZoneName + "%");
        }
        if(StringUtils.isNotEmpty(in.CollectZoneDesc)){
        	whereSQL.add("CollectZoneDesc", " LIKE ", "%" + in.CollectZoneDesc + "%");
        }
        whereSQL.checkAdd("ZoneID", in.ZoneID);
        String sql = "SELECT * FROM V_IMCollectZone a WHERE 1=1 " + limitsql+ whereSQL.getPreparedWhereSQL();
        
        java.util.List<String> orderByField = new java.util.LinkedList<String>();
        orderByField.add("CollectZoneID ASC");
        
        rset = dbSession.executeQuery(sql,orderByField,in.recordBegin,in.recordNum,whereSQL);
        
        return rset;
    }
}
