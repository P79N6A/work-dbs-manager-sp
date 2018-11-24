package com.dcdzsoft.business.ap;

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
 * <p>Description: 寄件包裹由揽件员收件 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class APCollectAgentCollected extends ActionBean
{

    public int doBusiness(InParamAPCollectAgentCollected in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        OutParamAPCollectAgentCollected out = new OutParamAPCollectAgentCollected();
        
        int result = 0;

		//1.验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(in.PackageID)
			||StringUtils.isEmpty(in.PostmanID)
			||StringUtils.isEmpty(in.Password))
			throw new EduException(ErrorCode.ERR_PARMERR);

		//3.调用UtilDAO.getCurrentDateTime()获得系统日期时间。
		java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();
		java.sql.Date sysDate = DateUtils.toSQLDate(sysDateTime);
		
		//#start 投递员编号是否存在，两种方式：上传投递员编号或绑定的卡号，BindCardID通过PostmanID字段
		String postmanid = "";
        PMPostmanDAO postmanDAO = daoFactory.getPMPostmanDAO();

        JDBCFieldArray whereCols0 = new JDBCFieldArray();
        whereCols0.add("PostmanID", in.PostmanID);
        RowSet rset2 = postmanDAO.select(whereCols0);
        if (RowSetUtils.rowsetNext(rset2)) {
            postmanid = RowSetUtils.getStringValue(rset2, "PostmanID");
        } else {
            whereCols0 = new JDBCFieldArray();
            whereCols0.add("BindCardID", in.PostmanID);
            rset2 = postmanDAO.select(whereCols0);

            if (RowSetUtils.rowsetNext(rset2)) {
                postmanid = RowSetUtils.getStringValue(rset2, "PostmanID");
            } else {
            	//用户名或密码错误
            	throw new EduException(ErrorCode.ERR_WRONGPWDORUSERID);
            }
        }
        in.PostmanID = postmanid;
        //#end
        
        //#start验证投递员信息
        PMPostman postman = new PMPostman();
        postman.PostmanID = postmanid;
        postman = postmanDAO.find(postman);
        if(!postman.PostmanStatus.equals("0"))//投递员状态
        {
        	throw new EduException(ErrorCode.ERR_POSTMANHAVECANCELED);
        }
        if (!postman.Password.equalsIgnoreCase(in.Password)){//投递员密码
            throw new EduException(ErrorCode.ERR_WRONGPWDORUSERID);
        }
        if(!"1".equals(postman.CollectRight)){
            throw new EduException(ErrorCode.ERR_COLLECT_NO_COLLECT_RIGHT);
        }
        //#end
        
        //#start 更新订单状态
        PreparedWhereExpression whereSQL = new PreparedWhereExpression();
        whereSQL.add("PackageID", in.PackageID);
        
        String viewName = "V_DMAllDeliveryItems";
        String limitSQL = "";//commonDAO.getQueryZoneLimitSQL(operOnline.OperID, operOnline.ZoneID);
		String sql = "SELECT PackageID,CreateTime,ItemStatus,ItemStatusName,BPartnerName,Address,Mobile,ParcelSize FROM " + viewName 
				+ " a WHERE 1=1 " + whereSQL.getPreparedWhereSQL() + limitSQL;
        
		//java.util.List<String> orderByField = new java.util.LinkedList<String>();
        //orderByField.add("CreateTime DESC");

        RowSet rset = dbSession.executeQuery(sql, whereSQL);//orderByField, 0, 0, 
        if(RowSetUtils.rowsetNext(rset)){
            String nextStatus = SysDict.ITEM_STATUS_COLLECT_INTRANSIT_COLLECTED;
            String itemstatus = RowSetUtils.getStringValue(rset, "ItemStatus");
        	switch(itemstatus){
        	case SysDict.ITEM_STATUS_COLLECT_CREATED://未选择上门揽件的订单？？？？？？？
        	    throw new EduException(ErrorCode.ERR_COLLECT_SERVICE_NOT_SELECTED);
        	case SysDict.ITEM_STATUS_COLLECT_TOBECOLLECTED:
        		//#start 更新订单状态
        		DMCollectionParcelDAO collectionParcelDAO = daoFactory.getDMCollectionParcelDAO();
                JDBCFieldArray setCols = new JDBCFieldArray();
                JDBCFieldArray whereCols = new JDBCFieldArray();
                setCols.add("CollectionAgentID", postman.PostmanID);
                setCols.add("CollectionTime", sysDateTime);
                setCols.add("ZoneID", postman.ZoneID);
                setCols.add("ItemStatus", nextStatus);
                setCols.add("LastModifyTime", sysDateTime);
                
                whereCols.add("PackageID", in.PackageID);
                whereCols.add("CreateTime", RowSetUtils.getTimestampValue(rset, "CreateTime"));

                collectionParcelDAO.update(setCols, whereCols);
                //#end
                
                // #start添加投递订单周期记录。
                DMItemLifeCycle itemLifeCycle = new DMItemLifeCycle();
                itemLifeCycle.PackageID  = in.PackageID;
                itemLifeCycle.ItemStatus = nextStatus;
                itemLifeCycle.OperatorID    = postman.PostmanID;
                itemLifeCycle.OperatorType  = SysDict.POSTMAN_TYPE_POST;
                itemLifeCycle.RecordLevel   = 0;//0-9
                itemLifeCycle.LastModifyTime = sysDateTime;
                itemLifeCycle.Remark = "Collected by "+postman.PostmanName;
                commonDAO.addItemLifeCycle(itemLifeCycle);
                // #end
        		break;
        	default:
        	    throw new EduException(ErrorCode.ERR_COLLECT_ITEMCODE_EXPIRED);
        	}
        }else {
        	throw new EduException(ErrorCode.ERR_COLLECT_ITEM_NOT_EXISTS);
        }
        //#end
        
		// 调用CommonDAO.addOperatorLog(OperID，功能编号，系统日期时间，“”)
		OPOperatorLog log = new OPOperatorLog();
		log.OperID   = in.PostmanID;
		log.OperType = NumberUtils.parseInt(SysDict.POSTMAN_TYPE_POST);
		log.FunctionID = in.getFunctionID();
		log.OccurTime = sysDateTime;
		log.StationAddr = "";
		log.Remark = "itemcode="+in.PackageID;

		commonDAO.addOperatorLog(log);

        return result;
    }
}
