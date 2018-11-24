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
 * <p>Description: 商业伙伴列表查询 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class IMBusiPartnerListQry extends ActionBean
{

    public RowSet doBusiness(InParamIMBusiPartnerListQry in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        RowSet rset = null;

		//1.验证输入参数是否有效，如果无效返回-1。
		//if (StringUtils.isEmpty(in.OperID))
		//	throw new EduException(ErrorCode.ERR_PARMERR);


		//2.调用CommonDAO.isOnline(管理员编号)判断管理员是否在线。
		//OPOperOnline operOnline = commonDAO.isOnline(in.OperID);


		//3.调用UtilDAO.getCurrentDateTime()获得系统日期时间。
		java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();
		java.sql.Date sysDate = DateUtils.toSQLDate(sysDateTime);
		
		PreparedWhereExpression whereSQL = new PreparedWhereExpression();
		whereSQL.checkAdd("BPartnerID", in.BPartnerID);
        if(StringUtils.isNotEmpty(in.BPartnerName)){
            whereSQL.add("BPartnerName", " LIKE ", "%" + in.BPartnerName + "%");
        }
        whereSQL.checkAdd("CollectZoneID", in.CollectZoneID);
        whereSQL.checkAdd("Username", in.Username);
        
        String sql = "SELECT BPartnerID,BPartnerName,Username FROM IMBusinessPartner a WHERE 1=1 " 
        		+ whereSQL.getPreparedWhereSQL() + " ORDER BY BPartnerID";
        
        rset = dbSession.executeQuery(sql,whereSQL);

        return rset;
    }
}
