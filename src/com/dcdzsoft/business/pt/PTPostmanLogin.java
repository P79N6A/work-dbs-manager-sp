package com.dcdzsoft.business.pt;

import java.util.List;

import javax.sql.RowSet;

import com.dcdzsoft.EduException;
import com.dcdzsoft.sda.db.*;
import com.dcdzsoft.sda.security.SecurityUtils;
import com.dcdzsoft.util.*;
import com.dcdzsoft.dto.function.*;
import com.dcdzsoft.dto.business.*;
import com.dcdzsoft.dao.*;
import com.dcdzsoft.dao.common.*;
import com.dcdzsoft.constant.*;
import com.dcdzsoft.business.ActionBean;

/**
 * <p>Title: 自提柜后台运营系统</p>
 * <p>Description: 投递员登陆验证 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class PTPostmanLogin extends ActionBean
{

    public OutParamPTPostmanLogin doBusiness(InParamPTPostmanLogin in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        OutParamPTPostmanLogin out = new OutParamPTPostmanLogin();

		//1.	验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(in.TerminalNo)
			||StringUtils.isEmpty(in.PostmanID)
			||StringUtils.isEmpty(in.Password))
			throw new EduException(ErrorCode.ERR_PARMERR);
		
		//3.	调用UtilDAO.getCurrentDateTime()获得系统日期时间。
		java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();
		java.sql.Date sysDate = DateUtils.toSQLDate(sysDateTime);
		
		//"B33001" Username or password is wrong 为终端错误编码，直接返回错误编码
		
        //#start 投递员编号是否存在，两种方式：上传投递员编号或绑定的卡号，BindCardID通过PostmanID字段
        String postmanid = "";
        PMPostmanDAO postmanDAO = daoFactory.getPMPostmanDAO();

        JDBCFieldArray whereCols0 = new JDBCFieldArray();
        whereCols0.add("PostmanID", in.PostmanID);
        RowSet rset = postmanDAO.select(whereCols0);
        if (RowSetUtils.rowsetNext(rset)) {
            postmanid = RowSetUtils.getStringValue(rset, "PostmanID");
        } else {
            whereCols0 = new JDBCFieldArray();
            whereCols0.add("BindCardID", in.PostmanID);
            rset = postmanDAO.select(whereCols0);

            if (RowSetUtils.rowsetNext(rset)) {
                postmanid = RowSetUtils.getStringValue(rset, "PostmanID");
            } else {
            	//用户名或密码错误
            	 throw new EduException("B33001");//ErrorCode.ERR_WRONGPWDORUSERID
            }
        }
        //#end
        
        //#start 验证投递员权限       
        PMPostman postman = new PMPostman();
        postman.PostmanID = postmanid;
        postman = postmanDAO.find(postman);
        //密码验证：md5加密传输
        if (!postman.Password.equals(in.Password))//投递员密码
            throw new EduException("B33001");//ErrorCode.ERR_WRONGPWDORUSERID
        //投递员状态检查
        if(!postman.PostmanStatus.equals("0")){
        	throw new EduException(ErrorCode.ERR_POSTMANHAVECANCELED);
        }
        //投递&回收权限检查：Drop Right,Return Right,DAD Right
        if(!"1".equals(postman.DropRight)
        	&&!"1".equals(postman.DADRight)
        	&&!"1".equals(postman.ReturnRight)){
        	throw new EduException(ErrorCode.ERR_FORBIDPOSTMALOGIN);
        }
        //验证投递员所属分拣区域
        IMZoneDAO zoneDAO = daoFactory.getIMZoneDAO();
        IMZone zone = new IMZone();
        zone.ZoneID = postman.ZoneID;
        try {
        	zone = zoneDAO.find(zone);
        } catch (Exception e) {
            throw new EduException(ErrorCode.ERR_FORBIDPOSTMALOGIN);//ERR_ZONEOFPOSTMANNOTEXISTS
        }
        
        //验证柜体权限
        commonDAO.checkManTerminalRight(postman, in.TerminalNo);
        //#end
        
        //#start 查询格口权限,通过查询包裹服务商格口权限查询
        out.BoxList = queryFreeBoxList(in.TerminalNo, zone.CompanyID, postman.PostmanID);
		//#end
        
        //#start 返回投递员信息
        out.PostmanID   = postman.PostmanID;
        out.PostmanName = postman.PostmanName;
        out.PostmanType = postman.PostmanType;
        out.CompanyID   = zone.CompanyID;
        out.OfBureau    = postman.ZoneID;
        out.InputMobileFlag = postman.InputMobileFlag;
        out.PostmanRight    = commonDAO.getPostmanRight(postman);
        out.DynamicCode  = "123456";
        out.Balance      = 0; 
        
        out.ExpiredCount = queryExpiredItemCount(postman, in.TerminalNo, zone.CompanyID);
        //#end
        
		////////////////////////////////////////////////////////////////
		if("97531468".equalsIgnoreCase(in.PostmanID)) //测试用
		out.VerifyFlag = SysDict.POSTMAN_VERIFY_CODE;
		else
		out.VerifyFlag = SysDict.POSTMAN_VERIFY_PWD;
		
        return out;
    }
    
    /**
     * 查询在本柜体超期订单数量
     * 
     **/
    private int queryExpiredItemCount(PMPostman postman, String terminalNo, String companyID) throws EduException {
    	int count = 0;
    	java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();
		ControlParam ctrlParam = ControlParam.getInstance();

		
		
    	PTInBoxPackageDAO inBoxItemDAO = daoFactory.getPTInBoxPackageDAO();
        JDBCFieldArray whereCols = new JDBCFieldArray();
        if(!"1".equals(postman.ReturnRight)){
        	return 0;
        }
        
        //"Return"类型Postman可以回收所属service owner的所有包裹，不受AZC和回收模式限制
        whereCols.add("TerminalNo",terminalNo);
        if(SysDict.POSTMAN_TYPE_RETURN.equals(postman.PostmanType)){
    		whereCols.add("CompanyID", companyID);
    	}else{
    		if("1".equals(ctrlParam.getRecoveryScope())){////回收范围 1投递员；2 AZC （后台开关）
            	whereCols.add("DropAgentID", postman.PostmanID);
            }else  if("2".equals(ctrlParam.getRecoveryScope())){
            	whereCols.add("ZoneID", postman.ZoneID);
    	   	}else{
    	   		whereCols.add("ZoneID", postman.ZoneID);
    	   	}
    		if(!"0".equals(ctrlParam.getRecoveryMode())){//回收模式 0 可以回收柜子中的所有包裹 1只能回收同类型的包裹(普通包裹或DAD包裹)
        		switch(postman.PostmanType){
                case SysDict.POSTMAN_TYPE_POST://"81"
                	whereCols.add("DADFlag","<>", SysDict.DIRECT_DROP_FLAG_YES);
                	break;
                case SysDict.POSTMAN_TYPE_DAD://"82"
                case SysDict.POSTMAN_TYPE_DAD_BUSINESSPARTNER://"83"
                	whereCols.add("DADFlag", SysDict.DIRECT_DROP_FLAG_YES);
                	break;
                }
        	}
    	}
        
        //自动超期与人工超期
        whereCols.addSQL(" AND ((");
        whereCols.addSQL(" ItemStatus="+StringUtils.addQuote(SysDict.ITEM_STATUS_DROP_EXPIRED));
        whereCols.addSQL(" OR ItemStatus="+StringUtils.addQuote(SysDict.ITEM_STATUS_DROP_M_EXPIRED));
        whereCols.addSQL(") OR ParcelStatus IN('1','3'))"); //1.在箱锁定, 3.超时
         
         count = inBoxItemDAO.isExist(whereCols);
         
         return count;
    }
    /**
     * 可用箱列表
     * @param terminalNo
     * @param companyID
     * @param postmanID
     * @return
     * @throws EduException
     */
    private String queryFreeBoxList(String terminalNo, String companyID,String postmanID) throws EduException {
    	//#start 查询格口权限,通过查询包裹服务商格口权限查询
        String BoxList = "0";
        String limitsql  ="";
        int count = 0;
        String sql = "SELECT * "
        		+ " FROM IMCompanyBoxRight a"
        		+ " WHERE a.CompanyID="+StringUtils.addQuote(companyID) 
        		+ " AND a.TerminalNo="+StringUtils.addQuote(terminalNo);
        //不包不正常的箱子
        limitsql += " AND NOT EXISTS(SELECT d.BoxNo FROM TBServerBox d WHERE d.TerminalNo=a.TerminalNo AND d.BoxNo=a.BoxNo AND d.BoxStatus <>'0')";
        //不包含已占用的箱子
        limitsql += " AND NOT EXISTS(SELECT c.BoxNo FROM PTInBoxPackage c WHERE c.TerminalNo=a.TerminalNo AND c.BoxNo=a.BoxNo)";
        //不包含预定的箱子
        limitsql += " AND NOT EXISTS(SELECT b.PackageID FROM PTReadyPackage b WHERE b.TerminalNo=a.TerminalNo AND b.BoxNo=a.BoxNo "
    			+ " AND b.DropAgentID<>"+StringUtils.addQuote(postmanID) + ") ";
        
        sql += limitsql;
        RowSet rset = dbSession.executeQuery(sql);
        StringBuffer sb = new StringBuffer();
        while (RowSetUtils.rowsetNext(rset)) {
            if (count > 0){
                sb.append(",");
            }
            sb.append(RowSetUtils.getStringValue(rset, "BoxNo"));

            count++;
        }

        if (count > 0)
            BoxList = sb.toString();
		//#end
        return BoxList;
    }
}
