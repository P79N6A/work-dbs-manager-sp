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
 * <p>Description: 投递员所在揽件区域寄件订单地址查询 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class APCollectAgentAddressQry extends ActionBean
{

    public java.util.List<OutParamAPCollectAgentAddressQry> doBusiness(InParamAPCollectAgentAddressQry in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        java.util.List<OutParamAPCollectAgentAddressQry> outList = new java.util.LinkedList<OutParamAPCollectAgentAddressQry>();
        
        RowSet rset = null;

		//1.验证输入参数是否有效，如果无效返回-1。
		//if (StringUtils.isEmpty(in.PostmanID))
		//	throw new EduException(ErrorCode.ERR_PARMERR);

		//3.调用UtilDAO.getCurrentDateTime()获得系统日期时间。
		java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();
		java.sql.Date sysDate = DateUtils.toSQLDate(sysDateTime);
		
		String CollectZoneID = "";
		if(!"1".equals(in.Mode)){//查询模式：1-可以查询所有，不受揽件区域限制
			//#start判断投递员编号是否存在，两种方式：上传投递员编号或绑定的卡号，BindCardID通过PostmanID字段
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
	            	outList.clear();
	             	return outList;
	            }
	        }
	        //#end
	        
	        //#start验证投递员信息
	        PMPostman postman = new PMPostman();
	        postman.PostmanID = postmanid;
	        postman = postmanDAO.find(postman);
	        if(!postman.PostmanStatus.equals("0"))//投递员状态
	        {
	        	outList.clear();
	         	return outList;
	        }
	        if(!"1".equals(postman.CollectRight)){
	        	outList.clear();
	         	return outList;
	        }
	        if(StringUtils.isEmpty(postman.CollectZoneID)){//揽件区域
	        	outList.clear();
	         	return outList;
	        }
	        //#end
	        
	        //检查投递员所在揽件区域
	        IMCollectZoneDAO collectZoneDAO = daoFactory.getIMCollectZoneDAO();
	        IMCollectZone collectZone = new IMCollectZone();
	        collectZone.CollectZoneID = postman.CollectZoneID;
	        
	        if(!collectZoneDAO.isExist(collectZone)){
	        	outList.clear();
	         	return outList;
	        }
	        CollectZoneID = postman.CollectZoneID;
		}
        //查询投递员所在揽件区域的BP
        IMBusinessPartnerDAO businessPartnerDAO = daoFactory.getIMBusinessPartnerDAO();
		JDBCFieldArray whereCols1 = new JDBCFieldArray();
		if(StringUtils.isNotEmpty(CollectZoneID)){
			 whereCols1.add("CollectZoneID", CollectZoneID);
		}
       
        rset = businessPartnerDAO.select(whereCols1);
        while (RowSetUtils.rowsetNext(rset)) {
        	String partnerid = RowSetUtils.getStringValue(rset, "BPartnerID");
        	String ownerCollectZoneID = RowSetUtils.getStringValue(rset, "CollectZoneID");
        	
        	//查询待揽件订单
        	DMCollectionParcelDAO collectionParcelDAO = daoFactory.getDMCollectionParcelDAO();
        	JDBCFieldArray whereCols2 = new JDBCFieldArray();
            whereCols2.add("BPartnerID", partnerid);
            whereCols2.add("ReturnFlag", "<>", "1");//ReturnFlag订单不支持揽件
            whereCols2.add("ItemStatus", SysDict.ITEM_STATUS_COLLECT_TOBECOLLECTED);
            int number = collectionParcelDAO.isExist(whereCols2);
            if(number>0){
            	OutParamAPCollectAgentAddressQry out = new OutParamAPCollectAgentAddressQry();
            	out.setREG(ownerCollectZoneID);
            	out.setBID(partnerid);//BPartnerID
            	out.setBPN(RowSetUtils.getStringValue(rset, "BPartnerName"));//BPartnerName
            	out.setBPA(RowSetUtils.getStringValue(rset, "Address"));//Address
            	out.setBPM(RowSetUtils.getStringValue(rset, "Mobile"));//Mobile
            	out.setLNG(RowSetUtils.getDoubleValue(rset, "Longitude"));//Longitude
            	out.setLAT(RowSetUtils.getDoubleValue(rset, "Latitude"));//Latitude
            	out.setNUM(number);
            	
            	outList.add(out);
            }
        }

        return outList;
    }
}
