package com.dcdzsoft.business.dm;

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
 * <p>Description: 寄件订单数量查询 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class DMDeliveryQryCount extends ActionBean
{

    public int doBusiness(InParamDMDeliveryQryCount in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        int result = 0;

		//1.	验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(in.OperID))
			throw new EduException(ErrorCode.ERR_PARMERR);

		String viewName = "";
        String limitSQL = "";
        if("2".equals(in.QryFlag)){//揽件员查询
            
            viewName = "V_DMDeliveryItems";
            PMPostmanDAO postmanDAO = daoFactory.getPMPostmanDAO();
            PMPostman postman = new PMPostman();
            postman.PostmanID = in.OperID;
            postman = postmanDAO.find(postman);
            if(!postman.PostmanStatus.equals("0"))//投递员状态
            {
                throw new EduException(ErrorCode.ERR_POSTMANHAVECANCELED);
            }
            //?????揽件权限检查
            if(!SysDict.POSTMAN_RIGHT_YES.equals(postman.CollectRight)){
                throw new EduException(ErrorCode.ERR_COLLECT_NO_COLLECT_RIGHT);
            }
            limitSQL += " AND a.CollectZoneID="+StringUtils.addQuote(postman.CollectZoneID)+"";
        }else{//操作员查询
            OPOperOnline operOnline = commonDAO.isOnline(in.OperID);
            viewName = "V_DMAllDeliveryItems";
            
            if(StringUtils.isEmpty(in.ItemStatus)){
                limitSQL = " AND "
                        + "("
                          + "("
                            + "a.ItemStatus IN('50','51')"
                          + ") "
                          + "OR "
                          + "("
                            + "a.ItemStatus IN('53','54') AND a.ZoneID='"+operOnline.ZoneID+"'"
                          + ")"
                        + ")";
            }else{
                if(!"50".equals(in.ItemStatus) 
                       &&!"51".equals(in.ItemStatus)){//50-CREATED,51-TOBECOLLECTED，不受限制都可以查询
                    limitSQL = commonDAO.getQueryZoneLimitSQL(operOnline.OperID, operOnline.ZoneID);
                }
            }
            
            /*if(false){
                in.ItemStatus = SysDict.ITEM_STATUS_COLLECT_TOBECOLLECTED+","//51
                        //+ SysDict.ITEM_STATUS_COLLECT_SCHEDULED+","//52
                        + SysDict.ITEM_STATUS_COLLECT_INTRANSIT_COLLECTED+","//53
                        + SysDict.ITEM_STATUS_COLLECT_INWARD_RECEIVED+","//54
                        //+ SysDict.ITEM_STATUS_COLLECT_TRANSFER+","//55
                        //+ SysDict.ITEM_STATUS_COLLECT_TOBE_DEOPPED+","//56
                        //+ SysDict.ITEM_STATUS_COLLECT_TOBE_D_DEOPPED+","//57
                        //+ SysDict.ITEM_STATUS_COLLECT_CANCELLED+","//58
                        //+ SysDict.ITEM_STATUS_COLLECT_RETURNED+","//59
                        + SysDict.ITEM_STATUS_COLLECT_CREATED//50
                        ; 
            }*/
        }
		
		//3.	调用UtilDAO.getCurrentDateTime()获得系统日期时间。
		java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();
		java.sql.Date sysDate = DateUtils.toSQLDate(sysDateTime);
		
		PreparedWhereExpression whereSQL = new PreparedWhereExpression();
        
        whereSQL.checkAdd("CreateTime", ">=", in.BeginDate);
        if(in.EndDate != null)
        	whereSQL.checkAdd("CreateTime", "<=", DateUtils.addDate(in.EndDate,1));
        
        if(StringUtils.isNotEmpty(in.PackageID)){//模糊查询
        	whereSQL.addSQL(" AND (PackageID ");
        	whereSQL.addSQL(" LIKE '"+in.PackageID+"%'");
        	whereSQL.addSQL(" )");
        }
        if(StringUtils.isNotEmpty(in.BPartnerID)){//模糊查询
        	whereSQL.addSQL(" AND (BPartnerID ");
        	whereSQL.addSQL(" LIKE '"+in.BPartnerID+"%'");
        	whereSQL.addSQL(" )");
        }
        whereSQL.checkAdd("PPCID", in.PPCID);
        whereSQL.checkAdd("ZoneID", in.ZoneID);
        whereSQL.checkAdd("CompanyID", in.CompanyID);
        whereSQL.checkAdd("CollectZoneID", in.CollectZoneID);
        whereSQL.checkAdd("CustomerMobile", in.CustomerMobile);
        whereSQL.checkAdd("CustomerID", in.CustomerID);
        if(StringUtils.isNotEmpty(in.ItemStatus)){
        	whereSQL.addSQL(utilDAO.getFlagInSQL("a.ItemStatus", in.ItemStatus));
        }
        whereSQL.checkAdd("PostmanID", in.PostmanID);
        
        String sql = "SELECT COUNT(PackageID) FROM " + viewName + " a WHERE 1=1 " + whereSQL.getPreparedWhereSQL() + limitSQL;
        result = dbSession.executeQueryCount(sql,whereSQL);
        
        return result;
    }
}
