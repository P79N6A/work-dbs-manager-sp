package com.dcdzsoft.business.tb;

import javax.sql.RowSet;

import com.dcdzsoft.EduException;
import com.dcdzsoft.business.ActionBean;
import com.dcdzsoft.constant.SysDict;
import com.dcdzsoft.dao.IMZoneDAO;
import com.dcdzsoft.dao.OPOperSpeRightDAO;
import com.dcdzsoft.dto.function.IMZone;
import com.dcdzsoft.dto.function.OPOperOnline;
import com.dcdzsoft.dto.function.OPOperSpeRight;
import com.dcdzsoft.util.StringUtils;

public class ParcelInfoQry extends ActionBean{

	public ParcelInfoQry() {
	}
	/**
	 * 
	 * @param OperID
	 * @param intType 0-柜状态统计;1-箱状态统计;2-按箱尺寸统计箱数量；3-统计投递订单状态；4-统计寄件订单状态
	 * @return
	 * @throws EduException
	 */
	public RowSet doBusiness(String OperID, int intType) throws EduException
    {
		utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        RowSet rset = null;
        
        OPOperOnline operOnline = commonDAO.isOnline(OperID);
        
        String limitsql = "";
        String sql = "";
        switch(intType){
        case 0://柜状态统计
        	limitsql = getLimitSQLByTerminal(operOnline);
        	//SELECT a.TerminalStatusName AS Status, COUNT(TerminalNo) AS Total FROM V_TBTerminalStatusName a WHERE (2=2) GROUP BY a.TerminalStatus
        	sql = "SELECT TerminalStatusName AS Status, "
        			+ "COUNT(TerminalNo) AS Total"
            		+ " FROM V_TBTerminalStatusName a"
            		+ " WHERE (2=2) " +limitsql
            		+ " GROUP BY a.TerminalStatusName";
        	break;
        case 1://箱状态统计
        	limitsql = getLimitSQLByBox(operOnline);
        	
        	//SELECT a.BoxStatusName AS Status, COUNT(BoxNo) AS Total FROM V_TBBoxStatusInfo a WHERE (2=2) GROUP BY a.BoxStatus
        	sql = "SELECT a.BoxStatusName AS Status, "
        			+ "COUNT(BoxNo) AS Total"
            		+ " FROM V_TBBoxStatusInfo a"
            		+ " WHERE (2=2) " +limitsql
            		+ " GROUP BY a.BoxStatusName";
        	break;
        case 2://按箱尺寸统计箱数量
        	//
        	limitsql = getLimitSQLByBox(operOnline);
        	sql = "SELECT BoxTypeName AS BOX, "
        			+ "COUNT(BoxNo) AS TOTAL,"
        			+ "SUM(CASE  WHEN BoxStatus IN('0') THEN 1 ELSE 0 END) AS NUMFREE,"
        			+ "SUM(CASE  WHEN BoxStatus IN('8') THEN 1 ELSE 0 END) AS NUMUSED,"
        			+ "SUM(CASE  WHEN BoxStatus NOT IN('0','8') THEN 1 ELSE 0 END) AS NUMFAULT"
            		+ " FROM V_TBBoxStatusInfo a"
            		+ " WHERE (2=2) " +limitsql
            		+ " GROUP BY a.BoxTypeName";
        	break;
        case 3://统计投递订单状态
        	//SELECT a.ItemStatusName AS ItemStatus, COUNT(PackageID) AS Total FROM V_PTItemStatus a WHERE (2=2) GROUP BY a.ItemStatus
        	limitsql = getLimitSQLByZone(operOnline);
        	sql = "SELECT a.ItemStatusName AS ItemStatus, COUNT(PackageID) AS Total "
            		+ " FROM V_PTItemStatus a"
            		+ " WHERE (2=2) " +limitsql
            		+ " GROUP BY a.ItemStatusName,a.ItemStatus "
            		+ " ORDER BY a.ItemStatus";
        	break;
        case 4://统计寄件订单状态
        	//SELECT a.ItemStatusName AS ItemStatus, COUNT(PackageID) AS Total FROM V_DMItemStatus a WHERE (2=2) GROUP BY a.ItemStatus
        	limitsql = getLimitSQLByZone(operOnline);
        	sql = "SELECT a.ItemStatusName AS ItemStatus, COUNT(PackageID) AS Total "
            		+ " FROM V_DMItemStatus a"
            		+ " WHERE (2=2) " +limitsql
            		+ " GROUP BY a.ItemStatusName,a.ItemStatus "
            		+ " ORDER BY a.ItemStatus";
        	break;
        }
        /*String strType=intType==0?"SUM(CASE BoxStatus WHEN '0' THEN 1 WHEN '8' THEN 1 ELSE 0 END)":"SUM(CASE WHEN BoxStatus='0' THEN 1 ELSE 0 END)";
        String sql = "SELECT BoxTypeName AS BOX,"+strType+" AS NUM "
        		+ "FROM V_TBBoxStatusInfo "
        		+ "GROUP BY BoxTypeName";*/
        rset = dbSession.executeQuery(sql);

        return rset;
    }
	/**
     * 获取查询限制条件语句  (分拣区域权限)
     * @param OperID
     * @param ZoneID
     * @throws EduException
     */
    private String getLimitSQLByTerminal(OPOperOnline operOnline) throws EduException
    {
    	if(operOnline.OperType == 1 //Admin
            || operOnline.OperType == 2){//Master
            	return "";
        }
    	 /////通过特殊权限管理运营方管理员，公司管理员，分拣区域管理员，分拣中心操作员访问订单数据的结果
        boolean operSpeRightCheck = true;//特殊权限检查开关
        
        //OPOperSpeRightDAO.isExist (特殊权限号, 管理员编号)查询管理员特殊权限设置信息是否存在
        OPOperSpeRightDAO operSpeRightDAO = daoFactory.getOPOperSpeRightDAO();
        OPOperSpeRight operSpeRight = new OPOperSpeRight();
        operSpeRight.OperID = operOnline.OperID;
        //主运营方数据访问权限
        if(operSpeRightCheck){
        	operSpeRight.SpePrivID = SysDict.SPECIAL_ACCESS_DATA_QUERY_MASTER;
            if(operSpeRightDAO.isExist(operSpeRight)){
            	//允许访问所有订单数据
            	operSpeRightCheck = false;
            }
        }
        String LimitSQL = "";
        
        //服务商数据访问权限
        if(operSpeRightCheck){
        	operSpeRight.SpePrivID = SysDict.SPECIAL_ACCESS_DATA_QUERY_COMPANY;
            if(operSpeRightDAO.isExist(operSpeRight)){
            	//允许访问操作员所属服务商的订单数据
            	LimitSQL = " AND EXISTS(SELECT TerminalNo From IMCompanyBoxRight b , IMZone c"
            			+ " WHERE b.CompanyID=c.CompanyID"
            			+ " AND c.ZoneID="+StringUtils.addQuote(operOnline.ZoneID)
    					+ " AND b.TerminalNo=a.TerminalNo"
    					+ ") ";
            	operSpeRightCheck = false;
            }
        }
        
        //分拣区域数据访问权限
        if(operSpeRightCheck){
        	operSpeRightCheck = false;//默认分拣区域数据访问权限
        	//只能访问管理员所属AZC的订单数据
        	 LimitSQL = " AND EXISTS(SELECT TerminalNo From IMZoneLockerRight b WHERE b.ZoneID="+StringUtils.addQuote(operOnline.ZoneID)
 					+ " AND b.TerminalNo=a.TerminalNo"
 					+ ") ";
        }
        
        return LimitSQL;
    }
	/**
     * 获取查询限制条件语句  (分拣区域权限)
     * @param OperID
     * @param ZoneID
     * @throws EduException
     */
    private String getLimitSQLByBox(OPOperOnline operOnline) throws EduException
    {
    	if(operOnline.OperType == 1 //Admin
                || operOnline.OperType == 2){//Master
                	return "";
        }
    	/////通过特殊权限管理运营方管理员，公司管理员，分拣区域管理员，分拣中心操作员访问订单数据的结果
        boolean operSpeRightCheck = true;//特殊权限检查开关
        
        //OPOperSpeRightDAO.isExist (特殊权限号, 管理员编号)查询管理员特殊权限设置信息是否存在
        OPOperSpeRightDAO operSpeRightDAO = daoFactory.getOPOperSpeRightDAO();
        OPOperSpeRight operSpeRight = new OPOperSpeRight();
        operSpeRight.OperID = operOnline.OperID;
        //主运营方数据访问权限
        if(operSpeRightCheck){
        	operSpeRight.SpePrivID = SysDict.SPECIAL_ACCESS_DATA_QUERY_MASTER;
            if(operSpeRightDAO.isExist(operSpeRight)){
            	//允许访问所有订单数据
            	operSpeRightCheck = false;
            }
        }
        String LimitSQL = "";
        
        //服务商数据访问权限
        if(operSpeRightCheck){
        	operSpeRight.SpePrivID = SysDict.SPECIAL_ACCESS_DATA_QUERY_COMPANY;
            if(operSpeRightDAO.isExist(operSpeRight)){
            	//允许访问操作员所属服务商的订单数据
            	LimitSQL = " AND EXISTS(SELECT BoxNo From IMCompanyBoxRight b, IMZone c"
            			+ " WHERE b.CompanyID=c.CompanyID"
            			+ " AND c.ZoneID="+StringUtils.addQuote(operOnline.ZoneID)
    					+ " AND b.TerminalNo=a.TerminalNo AND b.BoxNo=a.BoxNo"
    					+ ") ";
            	operSpeRightCheck = false;
            }
        }
        
        //分拣区域数据访问权限
        if(operSpeRightCheck){
        	operSpeRightCheck = false;//默认分拣区域数据访问权限
        	//只能访问管理员所属AZC的订单数据
        	 LimitSQL = " AND EXISTS(SELECT TerminalNo From IMZoneLockerRight b WHERE b.ZoneID="+StringUtils.addQuote(operOnline.ZoneID)
 					+ " AND b.TerminalNo=a.TerminalNo"
 					+ ") ";
        }
        
        return LimitSQL;
    }
    /**
     * 获取查询限制条件语句  (分拣区域权限)
     * @param OperID
     * @param ZoneID
     * @throws EduException
     */
    private String getLimitSQLByZone(OPOperOnline operOnline) throws EduException
    {
    	if(operOnline.OperType == 1 //Admin
            || operOnline.OperType == 2){//Master
            return "";
        }
    	 /////通过特殊权限管理运营方管理员，公司管理员，分拣区域管理员，分拣中心操作员访问订单数据的结果
        boolean operSpeRightCheck = true;//特殊权限检查开关
        
        //OPOperSpeRightDAO.isExist (特殊权限号, 管理员编号)查询管理员特殊权限设置信息是否存在
        OPOperSpeRightDAO operSpeRightDAO = daoFactory.getOPOperSpeRightDAO();
        OPOperSpeRight operSpeRight = new OPOperSpeRight();
        operSpeRight.OperID = operOnline.OperID;
        //主运营方数据访问权限
        if(operSpeRightCheck){
        	operSpeRight.SpePrivID = SysDict.SPECIAL_ACCESS_DATA_QUERY_MASTER;
            if(operSpeRightDAO.isExist(operSpeRight)){
            	//允许访问所有订单数据
            	operSpeRightCheck = false;
            }
        }
        String LimitSQL = "";
        
        //服务商数据访问权限
        if(operSpeRightCheck){
        	operSpeRight.SpePrivID = SysDict.SPECIAL_ACCESS_DATA_QUERY_COMPANY;
            if(operSpeRightDAO.isExist(operSpeRight)){
            	//允许访问操作员所属服务商的订单数据
            	LimitSQL = " AND EXISTS(SELECT b.ZoneID FROM IMZone b, IMZone c"
            			+ " WHERE b.ZoneID = a.ZoneID" 
            			+ " AND c.CompanyID = b.CompanyID"
            			+ " AND c.ZoneID ="+ StringUtils.addQuote(operOnline.ZoneID)
            			+ ")";
            	operSpeRightCheck = false;
            }
        }
        
        //分拣区域数据访问权限
        if(operSpeRightCheck){
        	operSpeRightCheck = false;//默认分拣区域数据访问权限
        	//只能访问管理员所属AZC的订单数据
        	 LimitSQL = " AND a.ZoneID="+StringUtils.addQuote(operOnline.ZoneID);
        }
        
        return LimitSQL;
    }
}
