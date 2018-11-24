package com.dcdzsoft.business.pt;

import javax.sql.RowSet;

import java.util.List;
import java.util.LinkedList;

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
 * <p>Description: 下载逾期包裹单列表 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class PTExpiredPackQry extends ActionBean
{

    public java.util.List<OutParamPTExpiredPackQry> doBusiness(InParamPTExpiredPackQry in) throws EduException {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        RowSet rset = null;

        java.util.List<OutParamPTExpiredPackQry> outList = new java.util.LinkedList<OutParamPTExpiredPackQry>();

        //1.	验证输入参数是否有效，如果无效返回-1。
        if (StringUtils.isEmpty(in.TerminalNo)
            || StringUtils.isEmpty(in.PostmanID))
            throw new EduException(ErrorCode.ERR_PARMERR);

        //验证投递员是否存在
        PMPostmanDAO postmanDAO = daoFactory.getPMPostmanDAO();
        PMPostman postman = new PMPostman();
        postman.PostmanID = in.PostmanID;
        try {
            postman = postmanDAO.find(postman);
        } catch (Exception e) {
            throw new EduException(ErrorCode.ERR_POSTMANNOTEXISTS);
        }
        
        //验证投递员所属分拣区域
        IMZoneDAO zoneDAO = daoFactory.getIMZoneDAO();
        IMZone zone = new IMZone();
        zone.ZoneID = postman.ZoneID;
        try {
        	zone = zoneDAO.find(zone);
        } catch (Exception e) {
            throw new EduException(ErrorCode.ERR_FORBIDPOSTMALOGIN);
        }
        
        //验证柜体权限(登陆时候验证)
        //commonDAO.checkManTerminalRight(postman, in.TerminalNo);
        
        //验证回收权限
        if(!"1".equals(postman.ReturnRight)){
        	throw new EduException(ErrorCode.ERR_FORBIDPOSTMANTAKEOUT);
    	}
        //////////////////////////////////////////
        java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();
        java.sql.Date sysDate = DateUtils.toSQLDate(sysDateTime);
        ControlParam ctrlParam = ControlParam.getInstance();

        //int days = NumberUtils.parseInt(ctrlParam.recoveryTimeout);
        
        PTInBoxPackageDAO inBoxPackageDAO = daoFactory.getPTInBoxPackageDAO();
        JDBCFieldArray whereCols = new JDBCFieldArray();
        whereCols.add("TerminalNo",in.TerminalNo);
        
        //"Return"类型Postman可以回收所属service owner的所有包裹，不受AZC和回收模式限制
        if(SysDict.POSTMAN_TYPE_RETURN.equals(postman.PostmanType)){
    		whereCols.add("CompanyID", zone.CompanyID);
    	}else{
    		if("1".equals(ctrlParam.getRecoveryScope())){////回收范围 1投递员；2 AZC （后台开关）
            	whereCols.add("DropAgentID", postman.PostmanID);
            }else  if("2".equals(ctrlParam.getRecoveryScope())){
            	whereCols.add("ZoneID", postman.ZoneID);
    	   	}else{
    	   		whereCols.add("ZoneID", postman.ZoneID);
    	   	}
    		
    		//直投投递员只能回收直投订单，回收后订单不进入Pane3，可以直接重新投递
    		if(SysDict.POSTMAN_TYPE_DAD.equals(postman.PostmanType)){
    		    whereCols.add("DADFlag", SysDict.DIRECT_DROP_FLAG_YES);
    	    }
    	} 
        whereCols.addSQL(" AND ((");
        //whereCols.addSQL(" AND ((2=2 ");
        //whereCols.add("ItemStatus", SysDict.ITEM_STATUS_DROP_EXPIRED);//自动超期与人工超期
        whereCols.addSQL(" ItemStatus="+StringUtils.addQuote(SysDict.ITEM_STATUS_DROP_EXPIRED));
        whereCols.addSQL(" OR ItemStatus="+StringUtils.addQuote(SysDict.ITEM_STATUS_DROP_M_EXPIRED));
        whereCols.addSQL(") OR ParcelStatus IN('1','3'))"); //1.在箱锁定, 3.超时
        
        rset = inBoxPackageDAO.select(whereCols);
        while (RowSetUtils.rowsetNext(rset)) {
            OutParamPTExpiredPackQry outParam = new OutParamPTExpiredPackQry();

            outParam.PID = RowSetUtils.getStringValue(rset, "PackageID");
            outParam.BNO = RowSetUtils.getStringValue(rset, "BoxNo");
            outParam.MOB = RowSetUtils.getStringValue(rset, "CustomerMobile");
            outParam.STM = RowSetUtils.getTimestampValue(rset, "StoredTime");
            outParam.STS = RowSetUtils.getStringValue(rset, "ItemStatus");

            outList.add(outParam);
        }

        return outList;
    }
}
