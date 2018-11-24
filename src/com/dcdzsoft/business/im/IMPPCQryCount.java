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
 * <p>Description: 包裹处理中心查询数量 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class IMPPCQryCount extends ActionBean
{

    public int doBusiness(InParamIMPPCQryCount in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        int result = 0;

		//1.	验证输入参数是否有效，如果无效返回-1。
		//if (StringUtils.isEmpty(in.OperID))
		//	throw new EduException(ErrorCode.ERR_PARMERR);


		//2.	调用CommonDAO.isOnline(管理员编号)判断管理员是否在线。
        OPOperOnline operOnline = null;
        String limitsql = "";
        if(StringUtils.isNotEmpty(in.OperID)){
        	operOnline = commonDAO.isOnline(in.OperID);
        	limitsql = commonDAO.getQueryCompanyLimitSQL(operOnline.OperID, operOnline.ZoneID);
        } 
				
		PreparedWhereExpression whereSQL = new PreparedWhereExpression();
        whereSQL.checkAdd("PPCID", in.PPCID);
        if(StringUtils.isNotEmpty(in.PPCName))
            whereSQL.add("PPCName", " LIKE ", "%" + in.PPCName + "%");
        whereSQL.checkAdd("CompanyID", in.CompanyID);
        
        String sql = "SELECT COUNT(PPCID) FROM V_IMPPC WHERE 1=1 " 
        		+ whereSQL.getPreparedWhereSQL() + limitsql;
        
        result = dbSession.executeQueryCount(sql,whereSQL);

        return result;
    }
}
