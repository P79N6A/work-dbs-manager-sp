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
 * <p>Description: 订单状态记录查询 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class PTDeliveryItemDetail extends ActionBean
{

	/**
	 * 通过关键字查找订单状态详情
	 * @param in
	 * @return
	 * @throws EduException
	 */
    public java.util.List<OutParamPTDeliveryItemDetail> doBusiness(InParamPTDeliveryItemDetail in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        OutParamPTDeliveryItemDetail out = new OutParamPTDeliveryItemDetail();

        String limitSQL = "";
        
        //#start设置查询条件
        String viewName = "V_PTAllItems";

        PreparedWhereExpression whereSQL0 = new PreparedWhereExpression();
        switch(in.KeyType){
        case 1:
        	whereSQL0.add("PackageID", in.Key);
        	break;
        case 2:
        	whereSQL0.add("RefNo", in.Key);
        	break;
        default:
        	whereSQL0.add("PackageID", in.Key);
        	break;
        
        }
        
        String sql = "SELECT CreateTime,PackageID,ItemStatus FROM " + viewName //,ItemStatusName
        		+ " WHERE 1=1 " + whereSQL0.getPreparedWhereSQL() + limitSQL;
        
        java.util.List<String> orderByField0 = new java.util.LinkedList<String>();
        orderByField0.add("CreateTime DESC");//倒序，取最新的记录
        
        
        RowSet rset = dbSession.executeQuery(sql, orderByField0, 0, 0, whereSQL0);
        //#end
        
        //#start获取符合条件的订单编号
        StringBuffer itemcodeList = new StringBuffer();
        while (RowSetUtils.rowsetNext(rset)) {
        	String itemCode = RowSetUtils.getStringValue(rset, "PackageID");
        	
        	if(itemcodeList.indexOf(itemCode)>=0){
        		//包裹如果进行了重投递，同一itemCode有多条记录，进行过滤
        		continue;
        	}else{
        		itemcodeList.append(itemCode+",");
        	}
        }
        //#end
        
        //#start 查找订单状态变更记录
        java.util.List<OutParamPTDeliveryItemDetail> itemStatusList = new java.util.LinkedList<OutParamPTDeliveryItemDetail>();
        
        String[] itemCodes = itemcodeList.toString().split(",");
    	for(String itemcode : itemCodes){
    		PreparedWhereExpression whereSQL1 = new PreparedWhereExpression();
            whereSQL1.add("PackageID", itemcode);
            sql = "SELECT * FROM V_ItemHistoryDetail  WHERE 1=1 "+ whereSQL1.getPreparedWhereSQL();
            
            java.util.List orderByField1 = new java.util.LinkedList<String>();
            orderByField1.add("LastModifyTime DESC");//倒序，取最新的记录
            
            rset = dbSession.executeQuery(sql, orderByField1, 0, 0, whereSQL1);
            while (RowSetUtils.rowsetNext(rset)) {
            	
            	OutParamPTDeliveryItemDetail itemDetail = new OutParamPTDeliveryItemDetail();
            	itemDetail.ItemCode   = RowSetUtils.getStringValue(rset, "PackageID");
            	itemDetail.CreateTime = RowSetUtils.getTimestampValue(rset, "LastModifyTime");
            	itemDetail.ItemStatus    = RowSetUtils.getStringValue(rset, "ItemStatus");
            	itemDetail.ItemStatusName = RowSetUtils.getStringValue(rset, "ItemStatusName");
            	itemDetail.OperatorName = RowSetUtils.getStringValue(rset, "OperName");
            	itemDetail.Activity = RowSetUtils.getStringValue(rset, "Remark");
            	itemStatusList.add(itemDetail);
            	if(in.Mode == 0){
            		break;
            	}
            }
    	}        
    	//#end
        return itemStatusList;
    }
}
