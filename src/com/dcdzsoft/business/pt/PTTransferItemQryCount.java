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
 * <p>Description: 待发送订单数量查询 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class PTTransferItemQryCount extends ActionBean
{

    public int doBusiness(InParamPTTransferItemQryCount in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        int result = 0;

		//1.	验证输入参数是否有效，如果无效返回-1。


        String limitsql = "";//commonDAO.getQueryCompanyLimitSQL(operOnline.OperID, operOnline.ZoneID);
		
		PreparedWhereExpression whereSQL = new PreparedWhereExpression();
		whereSQL.checkAdd("WaterID", in.WaterID); 
        whereSQL.checkAdd("PackageID", in.PackageID); 
        whereSQL.checkAdd("ZoneID", in.ZoneID); 
        whereSQL.checkAdd("PPCID", in.PPCID); 
        
        //whereSQL.checkAdd("ItemType", in.ItemType);
        if(StringUtils.isNotEmpty(in.ItemType)){
        	whereSQL.addSQL(utilDAO.getFlagInSQL("a.ItemType", in.ItemType));
        }
        //whereSQL.checkAdd("SendStatus", in.SendStatus);
        if(StringUtils.isNotEmpty(in.SendStatus)){
        	whereSQL.addSQL(utilDAO.getFlagInSQL("a.SendStatus", in.SendStatus));
        }
        if(StringUtils.isNotEmpty(in.ItemStatus)){
        	whereSQL.addSQL(utilDAO.getFlagInSQL("a.ItemStatus", in.ItemStatus));
        }
        
        whereSQL.checkAdd("TransferOrderID", in.TransferOrderID);
        if(in.BeginDate != null){
        	java.sql.Timestamp begintime = new java.sql.Timestamp(in.BeginDate.getTime());
        	whereSQL.add("CreateTime", ">=",begintime);
        }
        if(in.EndDate != null){
        	java.sql.Timestamp endtime = new java.sql.Timestamp(in.EndDate.getTime());
        	whereSQL.add("CreateTime", "<=",DateUtils.addTimeStamp(endtime, 1));
        }
        
        String viewname = "V_DeliverItemTransfer";
        if("1".equals(in.QryFlag)){
            viewname = "V_PTTransferItemWater";
        }
        String sql = "SELECT COUNT(WaterID) FROM "+viewname+" a WHERE 1=1 " + whereSQL.getPreparedWhereSQL() + limitsql;
        
        result = dbSession.executeQueryCount(sql,whereSQL);

        return result;
    }
}
