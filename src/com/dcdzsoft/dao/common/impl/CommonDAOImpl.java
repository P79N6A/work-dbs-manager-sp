package com.dcdzsoft.dao.common.impl;

import java.io.File;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.sql.RowSet;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.ListIterator;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;

import com.dcdzsoft.ppcapi.SendInfo;
import com.dcdzsoft.ppcapi.SendItemManager;
import com.dcdzsoft.sda.db.DBSession;
import com.dcdzsoft.sda.db.JDBCFieldArray;
import com.dcdzsoft.sda.db.LocalSessionHolder;
import com.dcdzsoft.sda.db.ProcedureUtils;
import com.dcdzsoft.sda.db.RowSetUtils;
import com.dcdzsoft.EduException;
import com.dcdzsoft.util.*;
import com.dcdzsoft.util.LockUtil.MyLock;
import com.dcdzsoft.constant.*;
import com.dcdzsoft.sequence.*;
import com.dcdzsoft.sms.SMSInfo;
import com.dcdzsoft.sms.SMSManager;
import com.dcdzsoft.dao.common.CommonDAO;
import com.dcdzsoft.dao.common.UtilDAO;
import com.dcdzsoft.dao.factory.DAOFactory;
import com.dcdzsoft.dto.business.OutParamItemDetail;
import com.dcdzsoft.dto.business.OutParamPTDeliveryItemDetail;
import com.dcdzsoft.dto.function.*;
import com.dcdzsoft.dao.*;
import com.dcdzsoft.email.EmailSenderManager;
import com.dcdzsoft.config.ApplicationConfig;
import com.dcdzsoft.business.GServer;
import com.dcdzsoft.sda.security.SecurityUtils;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.dcdzsoft.sda.db.PreparedWhereExpression;

public class CommonDAOImpl implements CommonDAO {
    private static Log log = org.apache.commons.logging.LogFactory.getLog(
            CommonDAOImpl.class);
    private static ApplicationConfig apcfg = ApplicationConfig.getInstance();
    private static ControlParam ctrlParam = ControlParam.getInstance();
    private static GServer gserver = GServer.getInstance();

    private DBSession dbSession = LocalSessionHolder.getCurrentDBSession();
    private DAOFactory daoFactory = DAOFactory.getDAOFactory(dbSession.getDatabaseType());
    private UtilDAO utilDAO = daoFactory.getUtilDAO();
    private static LockUtil terminalLocks = new LockUtil();
    /**
     * 判断操作员是否在线
     * @param OperID String
     * @return OPOperOnline
     * @throws EduException
     */
    public OPOperOnline isOnline(String OperID) throws EduException {
        OPOperOnline result = new OPOperOnline();
        OPOperOnlineDAO operOnlineDAO = daoFactory.getOPOperOnlineDAO();

        result.OperID = OperID;
        try {
            result = operOnlineDAO.find(result);

            //检查online状态 和invalidSession冲突
            if (!result.OnlineStatus.equals("1"))
                throw new EduException(ErrorCode.ERR_OPERNOLOGIN);
        } catch (EduException e) {
            if (ErrorCode.ERR_OPOPERONLINENODATA.equalsIgnoreCase(e.getMessage()))
                throw new EduException(ErrorCode.ERR_OPERNOLOGIN);
            else
                throw e;
        }

        return result;
    }

    /**
     * 记录操作员日志
     * @param log OPOperatorLog
     * @return long
     * @throws EduException
     */
    public long addOperatorLog(OPOperatorLog log) throws EduException {
        String logFlag = GServer.getInstance().getLogFlag(log.FunctionID);
        if (Constant.LOG_FLAG_AUTOMATIC.equals(logFlag)
            || Constant.LOG_FLAG_MANUAL.equals(logFlag)
            || StringUtils.isEmpty(logFlag)) {

            OPOperatorLogDAO logDAO = daoFactory.getOPOperatorLogDAO();

            SequenceGenerator seqGen = SequenceGenerator.getInstance();
            log.OperLogID = seqGen.getNextKey(SequenceGenerator.SEQ_OPERLOGID);
            if (log.OccurTime == null) {
                java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();
                log.OccurTime = sysDateTime;
            }

            log.OccurDate = DateUtils.toSQLDate(log.OccurTime);

            logDAO.insert(log);
        }

        return log.OperLogID;
    }


    /**
     * 修改操作员在线信息
     * @param operOnline OPOperOnline
     * @return boolean
     * @throws EduException
     */
    public boolean modifyOperOnline(OPOperOnline operOnline) throws EduException {
        boolean isSameLogin = true;

        OPOperOnlineDAO onlineDAO = daoFactory.getOPOperOnlineDAO();

        //????
        //operOnline.DefaultOperID = operOnline.OperID;
        //operOnline.OnlineStatus = "1";


        if (onlineDAO.isExist(operOnline) == false) {
            operOnline.LastLoginTime = operOnline.LoginInTime;
            operOnline.LastLoginIP = operOnline.LoginIPAddr;
            operOnline.PreVersion = ControlParam.getInstance().currentVersion;
            operOnline.CurrentVersion = ControlParam.getInstance().currentVersion;

            onlineDAO.insert(operOnline);
        } else {
            OPOperOnline lastOperOnline = new OPOperOnline();
            lastOperOnline.OperID = operOnline.OperID;
            lastOperOnline = onlineDAO.find(lastOperOnline);

            JDBCFieldArray setCols = new JDBCFieldArray();

            setCols.add("OperType", operOnline.OperType);

            setCols.add("OnlineStatus", operOnline.OnlineStatus);
            
            setCols.add("ZoneID", operOnline.ZoneID);//允许修改所属AZC

            if (operOnline.LoginInTime != null)
                setCols.add("LoginInTime", operOnline.LoginInTime);
            if (operOnline.LoginOutTime != null)
                setCols.add("LoginOutTime", operOnline.LoginOutTime);
            if (StringUtils.isNotEmpty(operOnline.NetCardAddr))
                setCols.add("NetCardAddr", operOnline.NetCardAddr);
            if (StringUtils.isNotEmpty(operOnline.LoginIPAddr))
                setCols.add("LoginIPAddr", operOnline.LoginIPAddr);
            if (operOnline.LastQueryTime != null)
                setCols.add("LastQueryTime", operOnline.LastQueryTime);

            setCols.add("LastLoginTime", lastOperOnline.LoginInTime);
            setCols.add("LastLoginIP", lastOperOnline.LoginIPAddr);
            setCols.add("LoginTerminal", lastOperOnline.LoginTerminal);

            if ("12".equals(operOnline.LoginTerminal)) {
                operOnline.CurrentClientVersion = ControlParam.getInstance().currentVersion;

                setCols.add("PreClientVession", operOnline.PreClientVession);
                setCols.add("CurrentClientVersion", operOnline.CurrentClientVersion);
            } else {
                setCols.add("PreVersion", lastOperOnline.CurrentVersion);
                setCols.add("CurrentVersion", ControlParam.getInstance().currentVersion);
            }

            JDBCFieldArray whereCols = new JDBCFieldArray();
            whereCols.add("OperID", operOnline.OperID);

            onlineDAO.update(setCols, whereCols);

            if (!lastOperOnline.LoginIPAddr.equalsIgnoreCase(operOnline.LoginIPAddr))
                isSameLogin = false;
        }

        //设置查询最新时间

        return isSameLogin;
    }

    /**
     * 生成管理员编号
     * @return String
     * @throws EduException
     */
    public String getInnerUserID() throws EduException {
        String operid = "";

        OPOperatorDAO operDAO = daoFactory.getOPOperatorDAO();
        operid = operDAO.selectFunctions("MAX(OperID)", null);

        if (StringUtils.isEmpty(operid) || operid.length() < 10) {
            operid = "1934554321";
        } else {
            operid = String.valueOf(NumberUtils.parseLong(operid) + 1);
        }

        return operid;
    }

    /**
     * 操作员菜单信息查询
     * @param OperID String
     * @param ModuleID String
     * @return RowSet
     * @throws EduException
     */
    public RowSet operMenuQry(String OperID, String ModuleID) throws EduException {
        RowSet rset = null;

        String whereSQL = "";
        if (StringUtils.isNotEmpty(ModuleID))
            whereSQL = " AND a.MenuID like " + StringUtils.leftQuote(ModuleID) + "%'";

        String sql = "SELECT "
                     + " a.OperID,"
                     + " b.MenuID,"
                     + " b.MenuName,"
                     + " b.MenuLevel,"
                     + " b.MenuEngName,"
                     + " b.Description,"
                     + " b.Action,"
                     + " b.HotKey,"
                     + " b.Icon,"
                     + " b.HelpContext,"
                     + " b.MenuType,"
                     + " b.LeafFlag,"
                     + " b.Remark"
                     + " FROM V_OPOperToMenu a,OPMenu b"//OPOperToMenu
                     + " WHERE a.OperID = " + StringUtils.addQuote(OperID)
                     + whereSQL
                     + " AND b.MenuID = a.MenuID";

        sql = sql + " ORDER BY b.MenuID,b.MenuLevel";

        rset = dbSession.executeQuery(sql);

        return rset;
    }

    /**
     * 检查投递员的柜体权限
     * @param postman PMPostman
     * @param terminalno String
     * @throws EduException
     */
    public void checkManTerminalRight(PMPostman postman, String terminalno) throws EduException {
    	//"Return"类型的Postman允许登录所有柜体回收过期包裹,其他Postman只允许登录其所属AZC的柜体。
        if(SysDict.POSTMAN_TYPE_RETURN.equals(postman.PostmanType)){
        	return;
        }
        ControlParam ctrlParam = ControlParam.getInstance();
        if(!"1".equals(ctrlParam.getManDADTerminalRightCheck())){
        	//直投不受AZC限制的登录柜体
        	if(SysDict.POSTMAN_TYPE_DAD.equals(postman.PostmanType)
                ||SysDict.POSTMAN_TYPE_DAD_BUSINESSPARTNER.equals(postman.PostmanType)){
                	return;
            }
        }

        if (ctrlParam.getManTerminalRightCheck().equalsIgnoreCase(SysDict.POSTMAN_LOCKRIGHT_JUDGE_COMPANY)) {
        	IMZoneDAO zoneDAO = daoFactory.getIMZoneDAO();
        	IMZone zone = new IMZone();
        	zone.ZoneID = postman.ZoneID;
        	try{
        		zone = zoneDAO.find(zone);
        	}catch(EduException e){
        		throw new EduException(ErrorCode.ERR_FORBIDOPERATORLOCK);
        	}
        	
        	IMCompanyBoxRightDAO companyRightDAO = daoFactory.getIMCompanyBoxRightDAO();
            JDBCFieldArray whereCols1 = new JDBCFieldArray();
            whereCols1.add("CompanyID", zone.CompanyID);
            whereCols1.add("TerminalNo", terminalno);

            if (companyRightDAO.isExist(whereCols1) == 0) {
                throw new EduException(ErrorCode.ERR_FORBIDOPERATORLOCK);
            }
        }else if (ctrlParam.getManTerminalRightCheck().equalsIgnoreCase(SysDict.POSTMAN_LOCKRIGHT_JUDGE_AZC)) {
            IMZoneLockerRightDAO zoneRightDAO = daoFactory.getIMZoneLockerRightDAO();
            JDBCFieldArray whereCols1 = new JDBCFieldArray();
            whereCols1.add("ZoneID", postman.ZoneID);
            whereCols1.add("TerminalNo", terminalno);

            if (zoneRightDAO.isExist(whereCols1) == 0) {
                throw new EduException(ErrorCode.ERR_FORBIDOPERATORLOCK);
            }
        } else if (ctrlParam.getManTerminalRightCheck().equalsIgnoreCase(SysDict.POSTMAN_LOCKRIGHT_JUDGE_PERSON)) {
        	PMPostmanLockerRightDAO manRightDAO = daoFactory.getPMPostmanLockerRightDAO();
            JDBCFieldArray whereCols1 = new JDBCFieldArray();
            whereCols1.add("PostmanID", postman.PostmanID);
            whereCols1.add("TerminalNo", terminalno);

            if (manRightDAO.isExist(whereCols1) == 0) {
                throw new EduException(ErrorCode.ERR_FORBIDOPERATORLOCK);
            }
        }
    }
    
    /**
     * 警报消息入库
     * @param TerminalNo
     * @param AlertType
     * @param AlertLevel
     * @param BoxNo
     * @param Remark
     * @return ReportWaterID
     * @throws EduException
     */
    public long insertAlert(String TerminalNo,String AlertType,String AlertLevel,String BoxNo,String Remark) throws EduException
    {
    	//#start
    	java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();
        java.sql.Date sysDate = DateUtils.toSQLDate(sysDateTime);
		SequenceGenerator seqGen = SequenceGenerator.getInstance();

		MBTerminalAlertLogDAO alertLogDAO = daoFactory.getMBTerminalAlertLogDAO();
		MBTerminalAlertLog alertLog = new MBTerminalAlertLog();

		alertLog.ReportWaterID = seqGen.getNextKey(SequenceGenerator.SEQ_REPORTWATERID);
		alertLog.TerminalNo = TerminalNo;
		alertLog.AlertLevel = AlertLevel;
		alertLog.AlertType = AlertType;
		alertLog.BoxNo = BoxNo;
		alertLog.LogTime = sysDateTime;
		alertLog.LogDate = sysDate;
		alertLog.Remark = Remark;
		
		
		alertLogDAO.insert(alertLog);
		//#end
		
        //更新掉电状态
        if(SysDict.ALERT_TYPE_POWERLOW.equals(AlertType)){
            TBTerminalDAO terminalDAO = daoFactory.getTBTerminalDAO();
            
            JDBCFieldArray setCols = new JDBCFieldArray();
            JDBCFieldArray whereCols = new JDBCFieldArray();

            setCols.add("TerminalStatus", SysDict.TERMINAL_STATUS_FAULT_POWERFAIL);
            setCols.add("LastModifyTime", sysDateTime);
            
            whereCols.add("TerminalNo", TerminalNo);
            whereCols.add("TerminalStatus", SysDict.TERMINAL_STATUS_NORMAL);
            terminalDAO.update(setCols, whereCols);
        }else if(SysDict.ALERT_TYPE_POWERRECOVERY.equals(AlertType)){
            TBTerminalDAO terminalDAO = daoFactory.getTBTerminalDAO();
            
            JDBCFieldArray setCols = new JDBCFieldArray();
            JDBCFieldArray whereCols = new JDBCFieldArray();

            setCols.add("TerminalStatus", SysDict.TERMINAL_STATUS_NORMAL);
            setCols.add("LastModifyTime", sysDateTime);
            
            whereCols.add("TerminalNo", TerminalNo);
            whereCols.add("TerminalStatus", SysDict.TERMINAL_STATUS_FAULT_POWERFAIL);
            terminalDAO.update(setCols, whereCols);
        }else if(SysDict.ALERT_TYPE_OPENBOXFAIL.equals(AlertType)//大物卡箱
                || SysDict.ALERT_TYPE_DROPOPENBOXFAIL.equals(AlertType)){//投递开箱失败
            //大物卡箱开箱失败，设置为锁定，防止箱分配给其他订单;投递开箱失败,表示箱不可用，设置为故障，通知维修人员。
            TBServerBoxDAO boxDAO = daoFactory.getTBServerBoxDAO();
            TBServerBox box = new TBServerBox();
            box.BoxNo = BoxNo;
            box.TerminalNo = TerminalNo;

            try{
                box = boxDAO.find(box);
                int boxStatus = NumberUtils.parseInt(box.BoxStatus);
                if(SysDict.ALERT_TYPE_OPENBOXFAIL.equals(AlertType)){
                    boxStatus = boxStatus | 1;//大物卡箱，设置为锁定
                }else{
                    boxStatus = boxStatus | 2;//投递开箱失败,设置为故障
                }
                
                if(!box.BoxStatus.equalsIgnoreCase(String.valueOf(boxStatus))){
                  //修改状态
                    JDBCFieldArray setCols1 = new JDBCFieldArray();
                    JDBCFieldArray whereCols1 = new JDBCFieldArray();

                    setCols1.add("BoxStatus", String.valueOf(boxStatus));
                    whereCols1.add("BoxNo", BoxNo);
                    whereCols1.add("TerminalNo", TerminalNo);

                    boxDAO.update(setCols1, whereCols1);
                }
            }catch(EduException e){
            }
        }
        
        //发送告警消息
		switch(AlertType){
		case SysDict.ALERT_TYPE_TOOMANYHANDLES:// = "92"; //句柄过多
		case SysDict.ALERT_TYPE_NETWORKCLOSED:// = "91"; //网络异常?????????  间隔时间进行发送	
			//不需要发送告警信息
			break;
			//发送邮件和短信
		case SysDict.ALERT_TYPE_OPENBOXFAIL:// = "95";//大物卡箱   取件时开箱失败告警，锁定箱，因为远程求助开箱，所以收到告警时不通知维护人员
		case SysDict.ALERT_TYPE_BOXNOTCLOSED:// = "43"; //箱门长时间未关
		case SysDict.ALERT_TYPE_BOXREOPENING:// = "45"; //取件重新开箱
		case SysDict.ALERT_TYPE_SHOCK:// = "11"; //震动
        case SysDict.ALERT_TYPE_RESISTANT:// = "12"; //防撬
		case SysDict.ALERT_TYPE_POWERLOW:// = "13"; //电源不足
		case SysDict.ALERT_TYPE_POWERRECOVERY:// = "14"; //电源恢复
		case SysDict.ALERT_TYPE_DRIVERERROR:// = "16"; //驱动板异常
		case SysDict.ALERT_TYPE_HIGHTEMP:// = "21"; //高温异常
		case SysDict.ALERT_TYPE_LOWTERMP:// = "23"; //低温异常
		case SysDict.ALERT_TYPE_DEVICEFAULT:// = "31"; //柜体故障
		case SysDict.ALERT_TYPE_CARDFAULT:// = "33"; //读卡器故障
        case SysDict.ALERT_TYPE_BARCODEFAULT:// = "34"; //条码扫描仪故障
        case SysDict.ALERT_TYPE_PRINTFAULT:// = "35"; //打印机故障
		case SysDict.ALERT_TYPE_BOXFAULT:// = "41"; //箱体故障
		case SysDict.ALERT_TYPE_DROPOPENBOXFAIL:// = "96";//投递开箱失败，表示箱不可用（有物品或故障），需要通知维护人员
			doSendAlarmInfo(alertLog.ReportWaterID, TerminalNo, 3);
			break;
		default:
			doSendAlarmInfo(alertLog.ReportWaterID, TerminalNo, 1);
			break;
		}
		
		return alertLog.ReportWaterID;
    }
    
    /**
     * 添加待投递订单生命周期记录
     * @param itemLifeCycle PTItemLifeCycle
     * @throws EduException
     */
    public void addItemLifeCycle(int action, PTItemLifeCycle itemLifeCycle, String[] itemDesc) throws EduException{
    	itemLifeCycle.Remark = Constant.getAction(action, itemDesc);
    	addItemLifeCycle(itemLifeCycle);
    }
    public void addItemLifeCycle(PTItemLifeCycle itemLifeCycle) throws EduException{
    	PTItemLifeCycleDAO itemLifeCycleDAO = daoFactory.getPTItemLifeCycleDAO();
    	SequenceGenerator seqGen = SequenceGenerator.getInstance();
    	itemLifeCycle.WaterID = seqGen.getNextKey(SequenceGenerator.SEQ_ITEMWATERID);
        if (itemLifeCycle.LastModifyTime == null) {
            java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();
            itemLifeCycle.LastModifyTime = sysDateTime;
        }
    	itemLifeCycleDAO.insert(itemLifeCycle);
    }
    
    /**
     * 添加寄件订单生命周期记录
     * @param itemLifeCycle DMItemLifeCycle
     * @throws EduException
     */
    public void addItemLifeCycle(DMItemLifeCycle itemLifeCycle) throws EduException{
    	DMItemLifeCycleDAO itemLifeCycleDAO = daoFactory.getDMItemLifeCycleDAO();
    	SequenceGenerator seqGen = SequenceGenerator.getInstance();
    	itemLifeCycle.WaterID = seqGen.getNextKey(SequenceGenerator.SEQ_ITEMWATERID);
        if (itemLifeCycle.LastModifyTime == null) {
            java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();
            itemLifeCycle.LastModifyTime = sysDateTime;
        }
    	itemLifeCycleDAO.insert(itemLifeCycle);
    }
    /**
     * 批量包裹单号解析
     * @param packageIDs “<ItemCode>#<Createtime>~<ItemCode>#<Createtime>”
     * @return {{<ItemCode>,<Createtime>},{<ItemCode>,<Createtime>},...}
     * @throws EduException
     */
    public String[][] decodeBatchPackageID(String packageIDs) throws EduException{
    	if(packageIDs == null){
    		throw new EduException(ErrorCode.ERR_PARAM_PACKAGEIDCODE);
    	}
    	String[][] itemAndTimes = null;
    	
    	String[] itemsIDArray = packageIDs.split("~");
		int itemsCount = itemsIDArray.length;
		if(itemsCount > 0){
			itemAndTimes = new String[itemsCount][2];
			for(int i = 0; i < itemsCount; i++){
				String itemIdCode = itemsIDArray[i];
				String itemCode = "";
				String createtime = "";
				
				String[] itemAndTime = itemIdCode.split("#");
				if(itemAndTime.length == 2){
					itemCode = itemAndTime[0];
					createtime = itemAndTime[1];
				}
				
				if(itemCode.isEmpty()){// || createtime.isEmpty()
					throw new EduException(ErrorCode.ERR_PARAM_PACKAGEIDCODE);
				}
				itemAndTimes[i][0] = itemCode.trim();
				itemAndTimes[i][1] = createtime.trim();
    		}
		}
    	
    	return itemAndTimes;
    }
    /**
     * 检查订单打印标识
     * @param itemsCodeAndTime String[][] {{PackageID，createTime},{PackageID，createTime}}
     * @param itemStatus       String
     * @param flagMandatory    int
     * @param codeMandatory    int
     * @param printErrorCode   String
     * @return isPrint : true需要打印，false 不需要打印
     * */
    public boolean checkPrintFlag(String[][] itemsCodeAndTime,
    		String itemStatus, 
    		int flagMandatory, 
    		int codeMandatory,
    		String printErrorCode) throws EduException{
    	System.out.println("flagMandatory="+flagMandatory+",codeMandatory="+codeMandatory+",flag="+(flagMandatory&codeMandatory));
    	if((flagMandatory&codeMandatory)==codeMandatory){
			//需要强制打印
			/*for(String[] itemCodeAndTime:itemsCodeAndTime){
				String itemCode = itemCodeAndTime[0];
				String createTime = itemCodeAndTime[1];
				
				if(StringUtils.isEmpty(itemCode)){
					continue;
				}
		        PreparedWhereExpression whereSQL = new PreparedWhereExpression();
				whereSQL.add("PackageID", itemCode);
				whereSQL.add("CreateTime", createTime);
				whereSQL.add("ItemStatus", itemStatus);
				whereSQL.addSQL(" AND !((PrintedFlag&"+codeMandatory+")="+codeMandatory+")");

		        String sql = "SELECT COUNT(PackageID) FROM V_PTItemPrintReport WHERE 1=1 "
		        		+whereSQL.getPreparedWhereSQL();

		        int res = dbSession.executeQueryCount(sql,whereSQL);
		        if(res>0){
		        	//存在未打印的订单，返回异常
		        	return true;
		        }
	    	}*/
			return true;
		}else{
			return false;
		}
    }
    /** 获取ID,自动更新
     * @param type     String ID类型
     * @param from     String
     * @param to       String
     * @return OrderID
     * @throws EduException
     */
    public String getNextOrderID(String type, String from, String to) throws EduException{
    	String nextOrderID = getNextOrderID(type, from, to, true);//自动更新
		return nextOrderID.toUpperCase();
	}
    /** 获取ID
     * @param type     String ID类型
     * @param from     String
     * @param to       String
     * @param isUpdate boolean
     * @return OrderID
     * @throws EduException
      */
     public String getNextOrderID(String type, String from, String to, boolean isUpdate) throws EduException{
    	String nextOrderID = "";
		
    	String zoneID = "";
    	String lockerID = "";
    	String ppcID  = "";
    	
		int counterFlag = 0;//2-柜体计数器 ;1-分拣中心计数器
		String counterType = "";
		String counterName = "";
		int cntMaxValue = 99999;
		int cntValue = 0;
		switch(type){
		
		case SysDict.ORDER_ID_TYPE_TRANSFER://TransferOrder AZC
			ppcID   = to;//To:PPCID
	    	zoneID = from;//From:AZC#
	    	
			counterFlag = 1;//1-查询AZC计数器
			counterType = SysDict.COUNTER_TYPE_AZC_TRANSFER;
			counterName = "TRCounter";
			
			//TRAZC#PPC#XXXXX
			if(StringUtils.isEmpty(ppcID)){
				throw new EduException(ErrorCode.ERR_PARMERR);
			}
			nextOrderID +="TR"+zoneID+ppcID;
			break;
		case SysDict.ORDER_ID_TYPE_RECEIVED://ReceivedOrder AZC
			zoneID = to;//To:AZC#
	    	ppcID  = from;//From:PPCID#
	    	
			counterFlag = 1;//1-查询AZC计数器
			counterType = SysDict.COUNTER_TYPE_AZC_RECEIVED;
			counterName = "RCCounter";
			
			//TRPPC#AZC#XXXXX
			if(StringUtils.isEmpty(ppcID)){
				throw new EduException(ErrorCode.ERR_PARMERR);
			}
			nextOrderID +="RC"+ppcID+zoneID;
			//System.out.println("ReportType:"+reportType+",ZoneID="+zoneID+",PPCID="+ppcID);
			break;
		case SysDict.ORDER_ID_TYPE_REDISTRIBUTE://RedistributeOrder AZC
			//zoneID = to;//To:AZC#
	    	zoneID = from;//From:AZC#

	    	if(!zoneID.equals(to)){
				throw new EduException(ErrorCode.ERR_PARMERR);
			}
			counterFlag = 1;//1-查询AZC计数器
			counterType = SysDict.COUNTER_TYPE_AZC_REDISTRIBUTE;
			counterName = "RDCounter";
			
			//RDAZC#XXXXX
			nextOrderID +="RD"+zoneID;
			break;
		case SysDict.ORDER_ID_TYPE_COLLECTION://CollectionOrder AZC
			zoneID = to;//To:AZC#
	    	//zoneID = from;//collect agent

			counterFlag = 1;//1-查询AZC计数器
			counterType = SysDict.COUNTER_TYPE_AZC_COLLECTION;
			counterName = "COCounter";
			
			//COAZC#XXXXX
			nextOrderID +="CO"+zoneID;
			break;
		case SysDict.ORDER_ID_TYPE_MISSING://MissingOrder AZC
			//zoneID = to;//To:AZC#
	    	zoneID = from;//From:AZC#

	    	if(!zoneID.equals(to)){
				throw new EduException(ErrorCode.ERR_PARMERR);
			}
			counterFlag = 1;//1-查询AZC计数器
			counterType = SysDict.COUNTER_TYPE_AZC_MISSING;
			counterName = "MICounter";
			
			//MIAZC#XXXXX
			nextOrderID +="MI"+zoneID;
			break;
		case SysDict.ORDER_ID_TYPE_LOSE://LoseOrder AZC
			//zoneID = to;//To:AZC#
	    	zoneID = from;//From:AZC#

	    	if(!zoneID.equals(to)){
				throw new EduException(ErrorCode.ERR_PARMERR);
			}
			counterFlag = 1;//1-查询AZC计数器
			counterType = SysDict.COUNTER_TYPE_AZC_LOSE;
			counterName = "LOCounter";
			
			//LOAZC#XXXXX
			nextOrderID +="LO"+zoneID;
			break;
		case SysDict.ORDER_ID_TYPE_RETURN://ReturnOrder AZC
			
			zoneID   = to;//To:AZC#
	    	lockerID = from;//From:Locker#
	    	
			counterFlag = 2;//1-查询AZC计数器,2-Locker计数器
			counterType = SysDict.COUNTER_TYPE_LOCKER_RETURNED;
			counterName = "ROCounter";
			
			//ROAZC#LOCKER#XXXXX
			if(StringUtils.isEmpty(lockerID)){
				throw new EduException(ErrorCode.ERR_PARMERR);
			}
			nextOrderID +="RO"+zoneID+StringUtils.leftPad(lockerID, 5, '0');//lockerID
			break;
		case SysDict.ORDER_ID_TYPE_DROP:
			
			lockerID   = to;//To:Locker#
	    	zoneID = from;  //From:AZC#
	    	
			counterFlag = 2;
			counterType = SysDict.COUNTER_TYPE_LOCKER_DROP;
			counterName = "DOCounter";
			
			//DOAZC#LOCKER#XXXXX
			if(StringUtils.isEmpty(lockerID)){
				throw new EduException(ErrorCode.ERR_PARMERR);
			}
			nextOrderID +="DO"+zoneID+StringUtils.leftPad(lockerID, 5, '0');//lockerID
			break;
		case SysDict.ORDER_ID_TYPE_D_DROP:
			
			lockerID   = to;//To:Locker#
	    	zoneID = from;  //From:AZC#
	    	
			counterFlag = 2;
			counterType = SysDict.COUNTER_TYPE_LOCKER_D_DROP;
			counterName = "DODCounter";
			
			//DOAZC#LOCKER#XXXXX
			if(StringUtils.isEmpty(lockerID)){
				throw new EduException(ErrorCode.ERR_PARMERR);
			}
			nextOrderID += "DOD"+zoneID+StringUtils.leftPad(lockerID, 5, '0');//lockerID
			break;
		default:
			return "";
		}
		
		java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();
		
		//获取相应计数器数值
		if(counterFlag == 1){
			//1-查询AZC计数器
			synchronized (IMZoneCounter.class) {
				//计数器取值
				IMZoneCounterDAO counterDAO = daoFactory.getIMZoneCounterDAO();
				IMZoneCounter counter = new IMZoneCounter();
				counter.ZoneID      = zoneID;
				counter.CounterType = counterType;
				
				try{
					counter = counterDAO.find(counter);
					cntValue = counter.CntValue;
				}catch(EduException e){
					cntValue = 0;
					//创建计数器
					counter.ZoneID      = zoneID;
					counter.CounterType = counterType;
					counter.CounterName = counterName;
					counter.CntValue    = cntValue;
					counter.CntMaxValue = cntMaxValue;
					counter.LastModifyTime = sysDateTime;
					
					counterDAO.insert(counter);
				}
				
				//更新计数器
				if(isUpdate){
					String sql = "update IMZoneCounter set "
			        		+ " CntValue= CASE WHEN CntValue>=CntMaxValue THEN 0 ElSE (CntValue+1) END "
			        		+ " ,LastModifyTime="+StringUtils.addQuote(sysDateTime.toString())
			         		+ "  WHERE ZoneID="+StringUtils.addQuote(zoneID)+" "
			         				+ " and CounterType="+StringUtils.addQuote(counterType);
			         
			        dbSession.executeUpdate(sql);
					
				}
		        
			}
			
		}else if(counterFlag == 2){
			//2-查询Locker计数器
			synchronized(TBTerminalCounter.class){
				//计数器取值
				TBTerminalCounterDAO counterDAO = daoFactory.getTBTerminalCounterDAO();
				TBTerminalCounter counter = new TBTerminalCounter();
				counter.TerminalNo  = lockerID;
				counter.CounterType = counterType;
				
				try{
					counter = counterDAO.find(counter);
					cntValue = counter.CntValue;
				}catch(EduException e){
					cntValue = 0;
					//创建计数器
					counter.TerminalNo  = lockerID;
					counter.CounterType = counterType;
					counter.CounterName = counterName;
					counter.CntValue    = cntValue;
					counter.CntMaxValue = cntMaxValue;
					counter.LastModifyTime = sysDateTime;
					
					counterDAO.insert(counter);
				}
				//更新计数器
				if(isUpdate){
					String sql = "update TBTerminalCounter "
			    			+ " set CntValue= CASE WHEN CntValue>=CntMaxValue THEN 0 ElSE (CntValue+1) END "
			    			+ " ,LastModifyTime="+StringUtils.addQuote(sysDateTime.toString())
			        		+ " WHERE TerminalNo="+StringUtils.addQuote(lockerID)
			        				+ " and CounterType="+StringUtils.addQuote(counterType);
			        
			    	dbSession.executeUpdate(sql);
				}
			}
			
		}
		
		//下一编号
		nextOrderID += StringUtils.leftPad(String.valueOf(cntValue), 5, '0');
		
		return nextOrderID.toUpperCase();
    }
     
    /**
     * 更新分拣中心计数器
     * 计数器更新需要同步
     * @deprecated
     */
    public int updateZoneCounter(String zoneID, String counterType) throws EduException{
    	int res = 0;
    	/*java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();
    	
        String sql = "update IMZoneCounter set "
        		+ " CntValue= CASE WHEN CntValue>=CntMaxValue THEN 0 ElSE (CntValue+1) END "
        		+ " ,LastModifyTime="+StringUtils.addQuote(sysDateTime.toString())
         		+ "  WHERE ZoneID="+StringUtils.addQuote(zoneID)+" "
         				+ " and CounterType="+StringUtils.addQuote(counterType);
         
        res = dbSession.executeUpdate(sql);*/
        return res;
    }
    /**
     * 更新柜体计数器
     * 计数器更新需要同步
     * @deprecated
     */
    public int updateLockerCounter(String lockerID, String counterType) throws EduException{
    	int res = 0;
    	/*java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();
    	String sql = "update TBTerminalCounter "
    			+ " set CntValue= CASE WHEN CntValue>=CntMaxValue THEN 0 ElSE (CntValue+1) END "
    			+ " ,LastModifyTime="+StringUtils.addQuote(sysDateTime.toString())
        		+ " WHERE TerminalNo="+StringUtils.addQuote(lockerID)
        				+ " and CounterType="+StringUtils.addQuote(counterType);
        
    	res = dbSession.executeUpdate(sql);*/
        return res;
    }
    
    /**
     * 更新柜体计数器:CustomerCounter
     * @param lockerID
     * @return CntValue
     */
    public int updateLockerCustomerCounter(String lockerID) throws EduException{
    	java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();
		int SerialNumber = 0;
		String counterType = SysDict.COUNTER_TYPE_LOCKER_CUSTOMER;
		String counterName = "CUCounter";
		int cntMaxValue    = Constant.MAX_NUM_OF_CUSTOMER_PER_LOCKER;//默认柜体注册的客户的最大数，超过编号会有重复！！！！
		
		TBTerminalCounterDAO counterDAO = daoFactory.getTBTerminalCounterDAO();
		TBTerminalCounter counter = new TBTerminalCounter();
		counter.TerminalNo  = lockerID;
		counter.CounterType = counterType;
		
		synchronized(TBTerminalCounter.class){
			try{
				counter = counterDAO.find(counter);
				SerialNumber = counter.CntValue;
			}catch(EduException e){
				SerialNumber = 1;
				//创建计数器
				counter.TerminalNo  = lockerID;
				counter.CounterType = counterType;
				counter.CounterName = counterName;
				counter.CntValue    = SerialNumber;
				counter.CntMaxValue = cntMaxValue;
				counter.LastModifyTime = sysDateTime;
				
				counterDAO.insert(counter);
			}
			//更新计数器
	    	String sql = "update TBTerminalCounter "
	    			+ " set CntValue= CASE WHEN CntValue>=CntMaxValue THEN 0 ElSE (CntValue+1) END "
	    			+ " ,LastModifyTime="+StringUtils.addQuote(sysDateTime.toString())
	        		+ " WHERE TerminalNo="+StringUtils.addQuote(lockerID)
	        				+ " and CounterType="+StringUtils.addQuote(counterType);
	        
	    	dbSession.executeUpdate(sql);
		}
		
        return SerialNumber;
    }
    /**
     * 更新计数器：serviceCounter
     * @param ServiceID
     * @param ExtraServiceID
     * @return CntValue
     */
    public int updateServiceCounter(String ServiceID, String ExtraServiceID) throws EduException{
    	java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();
    	int SerialNumber = 0;
    	String counterType = SysDict.COUNTER_TYPE_SERVICE_MASTER;

        int maxValue = 99999999;
        String fixedNumber = ExtraServiceID;
        if(StringUtils.isNumeric(fixedNumber)){
        	if(fixedNumber.length()==1){
            	maxValue = 9999999;
            }else if(fixedNumber.length()==2){
            	maxValue = 999999;
            }
        }
        
        //SMCounter
        IMServiceCounterDAO serviceCounterDAO = daoFactory.getIMServiceCounterDAO();
        IMServiceCounter counter = new IMServiceCounter();
        counter.ServiceID = ServiceID;
        counter.CounterType = counterType;
        synchronized(IMServiceCounter.class){
        	try{
        		counter = serviceCounterDAO.find(counter);
        		SerialNumber = counter.CntValue;
        	}catch(EduException e){
        		SerialNumber = 1;
    			//创建计数器
            	counter.ServiceID   = ServiceID;
    			counter.CounterType = counterType;
    			counter.CounterName = "SMCounter";
    			counter.CntValue    = SerialNumber;
    			counter.CntMaxValue = maxValue;
    			counter.LastModifyTime = sysDateTime;
                
                serviceCounterDAO.insert(counter);
        	}
        	String sql = "update IMServiceCounter "
        			+ " set CntValue= CASE WHEN CntValue>=CntMaxValue THEN 0 ElSE (CntValue+1) END "
        			+ " ,LastModifyTime="+StringUtils.addQuote(sysDateTime.toString())
            		+ " WHERE ServiceID="+StringUtils.addQuote(ServiceID)
            				+ " and CounterType="+StringUtils.addQuote(counterType);
            
        	dbSession.executeUpdate(sql);
        }
        return SerialNumber;
    }
    /**
     * 获取可用空箱
     * @param CompanyID 
     * @param TerminalNo
     * @param BoxType
     * @param boxNum >0 查询指定数量
     * @return boxNoSet
     * @throws EduException
     */
    public java.util.Set<String> getCompFreeBoxSet(String CompanyID, String TerminalNo, String BoxType, int boxNum) throws EduException{
    	
    	java.util.Set<String> boxNoSet = new java.util.HashSet<String>();
    	if(StringUtils.isEmpty(CompanyID)
    		|| StringUtils.isEmpty(TerminalNo)){
    		return boxNoSet;
    	}
    	String limitSQL = " ";
    	if(boxNum < 0){
    		boxNum = 0;
    	}
		PreparedWhereExpression whereSQL = new PreparedWhereExpression();
		whereSQL.add("CompanyID", CompanyID);
		whereSQL.add("TerminalNo", TerminalNo);
		whereSQL.checkAdd("BoxType", BoxType);
		
		String sql = "SELECT BoxNo "
				+ " FROM V_IMCompFreeBoxStat"
				+ " WHERE 1=1 " + whereSQL.getPreparedWhereSQL() + limitSQL;
		
		//RowSet rset = dbSession.executeQuery(sql,whereSQL);
		
		java.util.LinkedList list = new java.util.LinkedList();
        list.add("BoxNo ASC");
        
		RowSet rset = dbSession.executeQuery(sql, list, 0, boxNum, whereSQL);

		boxNum = RowSetUtils.getCountOfRowSet(rset);
		java.util.Set<String> boxSet = new java.util.HashSet<String>();
		while (RowSetUtils.rowsetNext(rset)) {
			boxSet.add(RowSetUtils.getStringValue(rset, "BoxNo"));
		}
		return boxSet;
    }
    /**
     * 获取一个可用空箱
     * @param CompanyID 
     * @param TerminalNo
     * @param BoxType
     * @return boxNo
     * @throws EduException
     */
    public String getOneCompFreeBox(String CompanyID, String TerminalNo, String BoxType) throws EduException{
    	String boxNo = "";
    	java.util.Set<String> boxNoSet = getCompFreeBoxSet(CompanyID, TerminalNo, BoxType,1);
    	if(boxNoSet.isEmpty()){
			//没有可用空箱
			throw new EduException(ErrorCode.ERR_NOFREEDBOX);
		}
    	//int boxnum = boxNoSet.size();
    	//int randkey=(int)(Math.random()*boxnum);
    	
    	//int i = 0;
		java.util.Iterator<String> iterator = boxNoSet.iterator();
		while(iterator.hasNext()){
			boxNo = iterator.next();
			//if(i==randkey){//可用箱列表中随机选择一个箱体
			//	break;
			//}
			//i++;
		}
		return boxNo;
    }
    
    /**
     * 分配可用空箱
     * @param CompanyID 
     * @param LockerID
     * @param BoxType
     * @param boxNum
     * @return boxNoSet
     * @throws EduException
     */
    /*public java.util.Set<String> allocFreeBox(String CompanyID, String LockerID, String BoxType, int boxNum) throws EduException{
    	
    	java.util.Set<String> boxNoSet = new java.util.HashSet<String>();
    	if(boxNum <= 0 
    		|| StringUtils.isEmpty(CompanyID)
    		|| StringUtils.isEmpty(LockerID)){
    		return null;
    	}
    	
    	//获取柜体锁,获取空箱，并且更新箱使用状态
    	MyLock terminalLock = terminalLocks.getLock(LockerID);
    	synchronized (terminalLock) {
    		//获取可用空箱
        	String limitSQL = " LIMIT 0, "+ boxNum;//LIMIT 0, 1000
        	PreparedWhereExpression whereSQL1 = new PreparedWhereExpression();
    		whereSQL1.add("CompanyID", CompanyID);
    		whereSQL1.add("TerminalNo", LockerID);
    		whereSQL1.checkAdd("BoxType", BoxType);
    		
    		String sql = "SELECT BoxNo "
    				+ " FROM V_CompFreeBoxStat"
    				+ " WHERE 1=1 " + whereSQL1.getPreparedWhereSQL() + limitSQL;
    		
    		RowSet rset = dbSession.executeQuery(sql,whereSQL1);
    		boxNum = RowSetUtils.getCountOfRowSet(rset);
    		
    		if(boxNum <= 0){
        		return null;
        	}
    		TBServerBoxDAO serverBoxDAO = daoFactory.getTBServerBoxDAO();
    		while (RowSetUtils.rowsetNext(rset)) {
    			String boxNo = RowSetUtils.getStringValue(rset, "BoxNo");
    			
    			//更新箱使用状态
    			JDBCFieldArray setCols = new JDBCFieldArray();
    	        JDBCFieldArray whereCols = new JDBCFieldArray();
    	        setCols.add("BoxUsedStatus", SysDict.BOX_USED_STATUS_SCHEDULE);
    	        
    	        whereCols.add("TerminalNo", LockerID);
    	        whereCols.add("BoxNo", boxNo);
    	        //whereCols.add("BoxType", BoxType);
    	        whereCols.add("BoxStatus", SysDict.BOX_STATUS_NORMAL);
    	        whereCols.add("BoxUsedStatus", SysDict.BOX_USED_STATUS_FREE);

    	        int count = serverBoxDAO.update(setCols, whereCols);
    	        
    	        if(count == 1){
    	        	boxNoSet.add(boxNo);
    	        }
    		}
    	}
    	System.out.println("boxNum="+boxNum+",boxNo="+boxNoSet.size());
    	return boxNoSet;
    }*/
    
    /**
     * 申请柜体锁（对象）
     * @param terminalNo  String
     * @return termianlLock MyLock
     * @throws EduException
     */
    public MyLock allocTermianlLock(String terminalNo) throws EduException{
    	//获取柜体锁
    	MyLock terminalLock = terminalLocks.getLock(terminalNo);
    	return terminalLock;
    }
    /**
     * 释放占用的箱
     * @param terminalNo
     * @throws EduException
     */
    public void releaseTermianlLocks(String terminalNo) throws EduException{
    	terminalLocks.releaseLock(terminalNo);
    }
    /**
     * 获取查询限制条件语句 (主运营方权限) 
     * @param OperID
     * @param ZoneID
     * @throws EduException
     */
    public String getQueryMasterLimitSQL(String OperID,String ZoneID) throws EduException
    {
    	 //只有主运营方数据访问权限才能访问
        boolean operSpeRightCheck = true;//特殊权限检查开关
        
        //OPOperSpeRightDAO.isExist (特殊权限号, 管理员编号)查询管理员特殊权限设置信息是否存在
        OPOperSpeRightDAO operSpeRightDAO = daoFactory.getOPOperSpeRightDAO();
        OPOperSpeRight operSpeRight = new OPOperSpeRight();
        operSpeRight.OperID = OperID;
        //主运营方数据访问权限
        if(operSpeRightCheck){
        	operSpeRight.SpePrivID = SysDict.SPECIAL_ACCESS_DATA_QUERY_MASTER;
            if(operSpeRightDAO.isExist(operSpeRight)){
            	//允许访问所有订单数据
            	operSpeRightCheck = false;
            }
        }
        String LimitSQL = "";
        
        //无访问权限
        if(operSpeRightCheck){
        	LimitSQL = " AND 1 = 2 ";
        }
        
        return LimitSQL;
    }
    /**
     * 获取查询限制条件语句(包裹服务商权限)  
     * @param OperID
     * @param ZoneID
     * @throws EduException
     */
    public String getQueryCompanyLimitSQL(String OperID,String ZoneID) throws EduException
    {
    	 //
        boolean operSpeRightCheck = true;//特殊权限检查开关
        
        //OPOperSpeRightDAO.isExist (特殊权限号, 管理员编号)查询管理员特殊权限设置信息是否存在
        OPOperSpeRightDAO operSpeRightDAO = daoFactory.getOPOperSpeRightDAO();
        OPOperSpeRight operSpeRight = new OPOperSpeRight();
        operSpeRight.OperID = OperID;
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
        	//只能访问本服务商的数据
        	IMZoneDAO zoneDAO = daoFactory.getIMZoneDAO();
            IMZone zone = new IMZone();
            zone.ZoneID = ZoneID;
            try{
            	zone = zoneDAO.find(zone);
            	
            	LimitSQL = " AND CompanyID = " + StringUtils.addQuote(zone.CompanyID);
            	operSpeRightCheck = false;
            }catch(EduException e){
            }
        }
        //无访问权限
        if(operSpeRightCheck){
        	LimitSQL = " AND 1 = 2 ";
        }
        
        return LimitSQL;
    }
    /**
     * 获取查询限制条件语句  (分拣区域权限)
     * @param OperID
     * @param ZoneID
     * @throws EduException
     */
    public String getQueryZoneLimitSQL(String OperID,String ZoneID) throws EduException
    {
    	 /////通过特殊权限管理运营方管理员，公司管理员，分拣区域管理员，分拣中心操作员访问订单数据的结果
        boolean operSpeRightCheck = true;//特殊权限检查开关
        
        //OPOperSpeRightDAO.isExist (特殊权限号, 管理员编号)查询管理员特殊权限设置信息是否存在
        OPOperSpeRightDAO operSpeRightDAO = daoFactory.getOPOperSpeRightDAO();
        OPOperSpeRight operSpeRight = new OPOperSpeRight();
        operSpeRight.OperID = OperID;
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
            			+ " AND c.ZoneID ="+ StringUtils.addQuote(ZoneID)
            			+ ")";
            	operSpeRightCheck = false;
            	/*IMZoneDAO zoneDAO = daoFactory.getIMZoneDAO();
                IMZone zone = new IMZone();
                zone.ZoneID = ZoneID;
                try{
                	zone = zoneDAO.find(zone);
                	
                	LimitSQL = " AND EXISTS(SELECT ZoneID FROM IMZone b"
                			+ " WHERE b.CompanyID=" + StringUtils.addQuote(zone.CompanyID) + ""
                			+ " AND b.ZoneID = a.ZoneID"
                			+ ")";
                	operSpeRightCheck = false;
                }catch(EduException e){
                }*/
            }
        }
        
        //分拣区域数据访问权限
        if(operSpeRightCheck){
        	//operSpeRight.SpePrivID = SysDict.SPECIAL_ACCESS_DATA_QUERY_ZONE;
        	operSpeRightCheck = false;//默认分拣区域数据访问权限
        	//只能访问管理员所属AZC的订单数据
        	 LimitSQL = " AND a.ZoneID = " + StringUtils.addQuote(ZoneID);
        }
        
        return LimitSQL;
    }
    
    /**
     * 获取查询Locker限制条件语句  
     * @param OperID
     * @param ZoneID
     * @return
     * @throws EduException
     */
    public String getQueryLockerLimitSQL(String OperID,String ZoneID) throws EduException
    {
    	 /////通过特殊权限管理运营方管理员，公司管理员，分拣区域管理员，分拣中心操作员访问订单数据的结果
        boolean operSpeRightCheck = true;//特殊权限检查开关
        
        //OPOperSpeRightDAO.isExist (特殊权限号, 管理员编号)查询管理员特殊权限设置信息是否存在
        OPOperSpeRightDAO operSpeRightDAO = daoFactory.getOPOperSpeRightDAO();
        OPOperSpeRight operSpeRight = new OPOperSpeRight();
        operSpeRight.OperID = OperID;
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
            			+ " AND c.ZoneID="+StringUtils.addQuote(ZoneID)
    					+ " AND b.TerminalNo=a.TerminalNo"
    					+ ") ";
            	operSpeRightCheck = false;
            	

            }
        }
        
        //分拣区域数据访问权限
        if(operSpeRightCheck){
        	//operSpeRight.SpePrivID = SysDict.SPECIAL_ACCESS_DATA_QUERY_ZONE;
        	operSpeRightCheck = false;//默认分拣区域数据访问权限
        	//只能访问管理员所属AZC的订单数据
        	 LimitSQL = " AND EXISTS(SELECT TerminalNo FROM IMZoneLockerRight b"
        	 		+ " WHERE b.ZoneID=" + StringUtils.addQuote(ZoneID) 
        	 		+ " AND b.TerminalNo = a.TerminalNo"
        	 		+ ")";
        }
        
        return LimitSQL;
    }
    /**
     * 获取查询Locker限制条件语句  
     * @param OperID
     * @param ZoneID
     * @return
     * @throws EduException
     */
    public String getQueryLockerBoxLimitSQL(String OperID,String ZoneID) throws EduException
    {
    	 /////通过特殊权限管理运营方管理员，公司管理员，分拣区域管理员，分拣中心操作员访问订单数据的结果
        boolean operSpeRightCheck = true;//特殊权限检查开关
        
        //OPOperSpeRightDAO.isExist (特殊权限号, 管理员编号)查询管理员特殊权限设置信息是否存在
        OPOperSpeRightDAO operSpeRightDAO = daoFactory.getOPOperSpeRightDAO();
        OPOperSpeRight operSpeRight = new OPOperSpeRight();
        operSpeRight.OperID = OperID;
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
            	//允许访问操作员所属服务商的数据
            	LimitSQL = " AND EXISTS(SELECT BoxNo From IMCompanyBoxRight b , IMZone c"
            			+ " WHERE b.CompanyID=c.CompanyID"
            			+ " AND c.ZoneID="+StringUtils.addQuote(ZoneID)
    					+ " AND b.TerminalNo=a.TerminalNo AND b.BoxNo=a.BoxNo"
    					+ ") ";
            	operSpeRightCheck = false;
            }
        }
        
        //分拣区域数据访问权限
        if(operSpeRightCheck){
        	//operSpeRight.SpePrivID = SysDict.SPECIAL_ACCESS_DATA_QUERY_ZONE;
        	operSpeRightCheck = false;//默认分拣区域数据访问权限
        	//只能访问管理员所属AZC的数据
        	LimitSQL = " AND EXISTS(SELECT BoxNo From IMCompanyBoxRight b , IMZone c, IMZoneLockerRight d"
        			+ " WHERE b.CompanyID=c.CompanyID"
        			+ " AND c.ZoneID="+StringUtils.addQuote(ZoneID)
					+ " AND b.TerminalNo=a.TerminalNo AND b.BoxNo=a.BoxNo"
					+ " AND d.ZoneID=c.ZoneID AND d.TerminalNo=a.TerminalNo"
					+ ") ";
        }
        
        return LimitSQL;
    }
    /**
     * 返回初始时间：2015-07-24 15:34:11.464 返回 2015-07-24 00:00:00.0
     * @param timestamp
     * @return
     */
    private static java.sql.Timestamp toSqlTimestampStart(java.sql.Timestamp timestamp){
		long millisecondsADay = 1000*60*60*24;
		long millisecondsInDay = (timestamp.getTime()%millisecondsADay);
		long milliseconds = timestamp.getTime() -(millisecondsInDay+1000*60*60*8);//8小时？？？
		return new java.sql.Timestamp(milliseconds);
	}
    /**
     * 获取逾期时间  
     * @param CompanyID
     * @return
     * @throws EduException
     */
    public java.sql.Timestamp getExpiredTime(String CompanyID) throws EduException{
    	java.sql.Timestamp dropDateTime = toSqlTimestampStart(utilDAO.getCurrentDateTime());
    	//获取超期时间、催领时间
        ControlParam ctrlParam = ControlParam.getInstance();
		IMCompanyDAO companyDAO = daoFactory.getIMCompanyDAO();
		IMCompany company = new IMCompany();
		try {
			company.CompanyID = CompanyID;
			company = companyDAO.find(company);
		} catch (Exception e) {
			
		}
		int expiredDays = 1;//默认最小值
		if(company.ExpiredDays < 1){
			int days = NumberUtils.parseInt(ctrlParam.getRecoveryTimeout());
			if(days>0){
				expiredDays = days;
			}
		}else{
			expiredDays = company.ExpiredDays;
		}
		expiredDays += 1;//dropDateTime为投递当天的凌晨，加1天
		java.sql.Timestamp ExpiredTime = DateUtils.addTimeStamp(dropDateTime, expiredDays);
		return ExpiredTime;
    }
    /**
     * 获取催领时间  
     * @param CompanyID
     * @param isFirst 第一次催领间隔+1天
     * @return
     * @throws EduException
     */
    public java.sql.Timestamp getReminderTime(String CompanyID, boolean isFirst) throws EduException{
    	java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();
		java.sql.Date sysDate = DateUtils.toSQLDate(sysDateTime);
    	//获取超期时间、催领时间
        ControlParam ctrlParam = ControlParam.getInstance();
		IMCompanyDAO companyDAO = daoFactory.getIMCompanyDAO();
		IMCompany company = new IMCompany();
		try {
			company.CompanyID = CompanyID;
			company = companyDAO.find(company);
		} catch (Exception e) {
		}
		int reminderDays = 1;//默认最小值
		String datatimeStr = sysDate.toString();
		if(isTime(company.ReminderTime)){
			datatimeStr = datatimeStr+" "+ company.ReminderTime;
		}else{
			datatimeStr = datatimeStr+" 12:00:00";
		}
		
		java.sql.Timestamp reminderDateTime = Timestamp.valueOf(datatimeStr);
		if(company.ReminderDays < reminderDays){
			int days = NumberUtils.parseInt(ctrlParam.getReminderDays());
			if(days>reminderDays){
				reminderDays = days;
			}
		}else{
			reminderDays = company.ReminderDays;
		}
		if(isFirst && (reminderDays == 1)){
			reminderDays += 1;//第一次催领间隔+1天
		}
		reminderDateTime = DateUtils.addTimeStamp(reminderDateTime, reminderDays);
		return reminderDateTime;
    }
    /**
     * 获取逾期天数  
     * @param CompanyID
     * @return
     * @throws EduException
     */
    public int getExpiredDays(String CompanyID) throws EduException{
    	//获取超期时间、催领时间
        ControlParam ctrlParam = ControlParam.getInstance();
		IMCompanyDAO companyDAO = daoFactory.getIMCompanyDAO();
		IMCompany company = new IMCompany();
		try {
			company.CompanyID = CompanyID;
			company = companyDAO.find(company);
		} catch (Exception e) {
			
		}
		int expiredDays = 1;//默认最小值
		if(company.ExpiredDays < 1){
			int days = NumberUtils.parseInt(ctrlParam.getRecoveryTimeout());
			if(days>0){
				expiredDays = days;
			}
		}else{
			expiredDays = company.ExpiredDays;
		}
		return expiredDays;
    }
    /**
     * 获取逾期天数  
     * @param StoredTime
     * @param ExpiredTime
     * @return
     * @throws EduException
     */
    public int getExpiredDays(java.sql.Timestamp StoredTime, java.sql.Timestamp ExpiredTime) throws EduException{
    	java.sql.Timestamp dropDateTime = toSqlTimestampStart(StoredTime);
    	java.sql.Timestamp nowDateTime = toSqlTimestampStart(ExpiredTime);
    	long days = (nowDateTime.getTime()-dropDateTime.getTime())/(1000*60*60*24);
    	return (int)days;
    }
    /**
     * 获取投递在箱的天数(不包括投递当天，当天返回：0)  
     * @param StoredTime
     * @return
     * @throws EduException
     */
    public int getDroppedDays(java.sql.Timestamp StoredTime) throws EduException{
    	java.sql.Timestamp dropDateTime = toSqlTimestampStart(StoredTime);
    	java.sql.Timestamp nowDateTime = toSqlTimestampStart(utilDAO.getCurrentDateTime());
    	long days = (nowDateTime.getTime()-dropDateTime.getTime())/(1000*60*60*24);
		return (int)days;
    }
    /**
     * 获取催领间隔天数  
     * @param CompanyID
     * @return
     * @throws EduException
     */
    public int getReminderDays(String CompanyID) throws EduException{
    	//获取超期时间、催领时间
        ControlParam ctrlParam = ControlParam.getInstance();
		IMCompanyDAO companyDAO = daoFactory.getIMCompanyDAO();
		IMCompany company = new IMCompany();
		try {
			company.CompanyID = CompanyID;
			company = companyDAO.find(company);
		} catch (Exception e) {
		}
		int reminderDays = 1;//默认最小值
		if(company.ReminderDays < reminderDays){
			int days = NumberUtils.parseInt(ctrlParam.getReminderDays());
			if(days>reminderDays){
				reminderDays = days;
			}
		}else{
			reminderDays = company.ReminderDays;
		}
		return reminderDays;
    }
    private boolean isTime(String timeStr){
		if(timeStr==null || timeStr.length()<5){
			return false;
		}
		//^(0?[1-9]|1[0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]$
		String rep = "^(0?[1-9]|1[0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]$";  
        
        Pattern p = Pattern.compile(rep);  
        return p.matcher(timeStr).matches();
	}
    public boolean isPhoneNumber(String input){  
    	if(StringUtils.isEmpty(input)){
    		return false;
    	}
    	String contoryCode = apcfg.getCountryCode();
	    String regex="1([\\d]{10})";  
	    if("CN".equalsIgnoreCase(contoryCode)){
	    	if(input.length() != 11){
	    		return false;
	    	}
	    	regex="1([\\d]{10})"; 
	    }else if("SA".equalsIgnoreCase(contoryCode)){
	        if(input.startsWith("966")){
	            input = input.substring(3);
	        }
	        if(input.startsWith("0")){
	            input = input.substring(1);
	        }
	    	if(input.length() != 9){
	    		return false;
	    	}
	    	//regex="05([\\d]{8})";
	    	regex="([\\d]{9})";
	    	
	    }else{
	    	if(input.length() != 11){
	    		return false;
	    	}
	    	regex="1([\\d]{10})";
	    }
	    Pattern p = Pattern.compile(regex); 
	    Matcher m = p.matcher(input);  
	    
	    return m.find();//boolean 
	}
    public  boolean isEmail(String input){  
    	if(StringUtils.isEmpty(input)){
    		return false;
    	}
	    String regex="\\w+@\\w+(\\.\\w{2,3})*\\.\\w{2,3}";  
	    Pattern p = Pattern.compile(regex); 
	    Matcher m = p.matcher(input);  
	    
	    return m.find();//boolean 
	}  
    /**
     * 修改在箱订单的逾期时间  
     * @param itemsCodeAndTime
     * @param TerminalNo
     * @param expiredTime
     * @return
     * @throws EduException
     */
    public int modExpireTime(
    		String OperID, 
    		String[][] itemsCodeAndTime,
    		String TerminalNo,
    		java.sql.Timestamp expiredTime
    		) throws EduException{
    	int res = 0;
    	java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();
    	
    	PTInBoxPackageDAO parcelItemDAO = daoFactory.getPTInBoxPackageDAO();
    	for(String[] itemCodeAndTime:itemsCodeAndTime){
			String itemCode = itemCodeAndTime[0];
			if(StringUtils.isEmpty(itemCode)){
				continue;
			}
			PTInBoxPackage inBoxPack = new PTInBoxPackage();
			inBoxPack.PackageID = itemCode;
			try{
				inBoxPack = parcelItemDAO.find(inBoxPack);
			}catch(EduException e){
				continue;
			}
			inBoxPack.ExpiredTime = expiredTime;
			
			JDBCFieldArray setCols = new JDBCFieldArray();
	        JDBCFieldArray whereCols = new JDBCFieldArray();
	        
	        setCols.add("ExpiredTime", inBoxPack.ExpiredTime);
	        setCols.add("LastModifyTime", sysDateTime);
	        
	        whereCols.add("PackageID", itemCode);
	        whereCols.checkAdd("TerminalNo", TerminalNo);
	        whereCols.addSQL(" AND (");
	        //whereCols.addSQL(" ItemStatus="+StringUtils.addQuote(SysDict.ITEM_STATUS_DROP_DROPPED));
	        //whereCols.addSQL(" OR ItemStatus="+StringUtils.addQuote(SysDict.ITEM_STATUS_DROP_D_DROPPED));
	        whereCols.addSQL(" ItemStatus<>"+StringUtils.addQuote(SysDict.ITEM_STATUS_DROP_EXPIRED));
	        whereCols.addSQL(" AND ItemStatus<>"+StringUtils.addQuote(SysDict.ITEM_STATUS_DROP_M_EXPIRED));
	        whereCols.addSQL(" )");
	        
	        int count = parcelItemDAO.update(setCols, whereCols);
	        if(count<=0){
	        	continue;
	        }
	        res += count;
	        //调用CommonDAO. addItemLifeCycle(PTItemLifeCycle itemLifeCycle)添加投递订单周期记录。
	        PTItemLifeCycle itemLifeCycle = new PTItemLifeCycle();
	        itemLifeCycle.PackageID  = inBoxPack.PackageID;
	        itemLifeCycle.ItemStatus = inBoxPack.ItemStatus;
	        itemLifeCycle.OperatorID    = OperID;
	        itemLifeCycle.LastModifyTime = sysDateTime;
	        itemLifeCycle.Remark = "modExpireTime:"+inBoxPack.ExpiredTime;
	        addItemLifeCycle(itemLifeCycle);
    	}
    	return res;
    }
    /**
     * 修改在箱订单的催领时间  
     * @param itemsCodeAndTime
     * @param TerminalNo
     * @param reminderDateNew
     * @return
     * @throws EduException
     */
    public int modReminderTime	(
    		String OperID, 
    		String[][] itemsCodeAndTime,
    		String TerminalNo,
    		java.sql.Date reminderDateNew//java.sql.Timestamp reminderTime
    		) throws EduException{
    	int res = 0;
    	java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();
    	
    	PTInBoxPackageDAO parcelItemDAO = daoFactory.getPTInBoxPackageDAO();
    	for(String[] itemCodeAndTime:itemsCodeAndTime){
			String itemCode = itemCodeAndTime[0];
			if(StringUtils.isEmpty(itemCode)){
				continue;
			}
			PTInBoxPackage inBoxPack = new PTInBoxPackage();
			inBoxPack.PackageID = itemCode;
			try{
				inBoxPack = parcelItemDAO.find(inBoxPack);
			}catch(EduException e){
				continue;
			}
			if(inBoxPack.ReminderDateTime==null){
				inBoxPack.ReminderDateTime = getReminderTime(inBoxPack.CompanyID, false);
			}
			
			
			java.sql.Date reminderDateOld = new java.sql.Date(inBoxPack.ReminderDateTime.getTime());
			
			String dateTimeString1 = reminderDateOld.toString();
			String dateTimeString2 = reminderDateNew.toString();
			
			java.sql.Date DateOld = java.sql.Date.valueOf(dateTimeString1);
			java.sql.Date DateNew = java.sql.Date.valueOf(dateTimeString2);
			long timeInms = (DateNew.getTime() - DateOld.getTime());
			//long timeInms = reminderDateNew.getTime()-reminderDateOld.getTime();
			int days = (int)(timeInms/(1000*60*60*24));
			/*if(timeInms>0){
				days += 1;
			}else{
				days -= 1;
			}*/
			inBoxPack.ReminderDateTime = DateUtils.addTimeStamp(inBoxPack.ReminderDateTime, days);
			
			JDBCFieldArray setCols = new JDBCFieldArray();
	        JDBCFieldArray whereCols = new JDBCFieldArray();
	        
	        setCols.add("ReminderDateTime", inBoxPack.ReminderDateTime);
	        setCols.add("LastModifyTime", sysDateTime);
	        
	        whereCols.add("PackageID", itemCode);
	        whereCols.checkAdd("TerminalNo", TerminalNo);
	        whereCols.addSQL(" AND (");
	        //whereCols.addSQL(" ItemStatus="+StringUtils.addQuote(SysDict.ITEM_STATUS_DROP_DROPPED));
	        //whereCols.addSQL(" OR ItemStatus="+StringUtils.addQuote(SysDict.ITEM_STATUS_DROP_D_DROPPED));
	        whereCols.addSQL(" ItemStatus<>"+StringUtils.addQuote(SysDict.ITEM_STATUS_DROP_EXPIRED));
	        whereCols.addSQL(" AND ItemStatus<>"+StringUtils.addQuote(SysDict.ITEM_STATUS_DROP_M_EXPIRED));
	        whereCols.addSQL(" )");
	        
	        int count = parcelItemDAO.update(setCols, whereCols);
	        if(count<=0){
	        	continue;
	        }
	        res += count;
	        //调用CommonDAO. addItemLifeCycle(PTItemLifeCycle itemLifeCycle)添加投递订单周期记录。
	        PTItemLifeCycle itemLifeCycle = new PTItemLifeCycle();
	        itemLifeCycle.PackageID  = inBoxPack.PackageID;
	        itemLifeCycle.ItemStatus = inBoxPack.ItemStatus;
	        itemLifeCycle.OperatorID    = OperID;
	        itemLifeCycle.LastModifyTime = sysDateTime;
	        itemLifeCycle.Remark = "modReminderTime:"+inBoxPack.ReminderDateTime;
	        addItemLifeCycle(itemLifeCycle);
    	}
    	return res;
    }
    /**
     * 寄件订单校验码  
     * @param serialNumberStr  8位数字
     * @return
     */
	public int getChecksumE1(String serialNumberStr){
    	int[] weight = {8,6,4,2,3,5,9,7};
    	int check = 0;
    	int serialNumber = Integer.parseInt(serialNumberStr);
        //N8 N7 N6 N5 N4 N3 N2 N1 
        int sum = 0;
        sum += ((serialNumber/10000000)%10)*weight[0];//N8
        sum += ((serialNumber/1000000)%10)*weight[1];//N7
        sum += ((serialNumber/100000)%10)*weight[2];//N6
        sum += ((serialNumber/10000)%10)*weight[3];//N5
        sum += ((serialNumber/1000)%10)*weight[4];//N4
        sum += ((serialNumber/100)%10)*weight[5];//N3
        sum += ((serialNumber/10)%10)*weight[6];//N2
        sum += ((serialNumber)%10)*weight[7];//N1
        int w = 11-(sum%11);
        if(w > 0 && w <= 9){
        	check = w;
        }else if(w == 10){
        	check = 0;
        }else{
        	check = 5;
        }
    	return check;
    }
    
    /**
     * 获取E1单号
     * @param service
     * @return
     * @throws EduException
     */
    public String getE1Code(IMServiceRate service) throws EduException{
        //service code
        String servicecode = service.ServicePrefix;
        
        //serial number
        String serialNumberStr = "";
        int num = 8;
        String fixedNumber = service.ExtraServiceID;
        if(StringUtils.isNumeric(fixedNumber)){
        	if(fixedNumber.length()==1){
            	num = 7;
            	serialNumberStr = fixedNumber;
            }else if(fixedNumber.length()==2){
            	num = 6;
            	serialNumberStr = fixedNumber;
            }
        }
        int serialNumber = updateServiceCounter(service.ServiceID, service.ExtraServiceID);
        
        //serial number fixedNumber+seqenceNumberStr
        String seqenceNumberStr = StringUtils.leftPad(String.valueOf(serialNumber), num, '0');;
        serialNumberStr+= seqenceNumberStr;
        
        //check digit
        int check = getChecksumE1(serialNumberStr);
        
        //suffix
        String suffix = "SA";
        
        String itemCode = servicecode+serialNumberStr+check+suffix;
        itemCode = itemCode.toUpperCase();
        return itemCode;
    }
    /**
     * 获取投递员权限
     * 投递员权限1~32位（低）
     * 1：投递权限（0 or 1）；
     * 2：回收权限（0 or 1）；
     * 3：揽件权限（0 or 1）；
     * 4：直投权限（0 or 1）
     * @param postman
     * @return
     */
    public int getPostmanRight(PMPostman postman){
    	/* postmanRight 投递员权限
         * 投递员权限1~32位（低）
         * 1：投递权限（0 or 1）；
         * 2：回收权限（0 or 1）；
         * 3：揽件权限（0 or 1）；
         * 4：直投权限（0 or 1）
         * */
    	
        int postmanRight = 0;
        switch(postman.PostmanType){
        case SysDict.POSTMAN_TYPE_POST://"81"
        	postmanRight = 0;
        	//投递权限
            if("1".equalsIgnoreCase(postman.DropRight)){
            	postmanRight |= 1;//1
            }
            //回收权限
            if("1".equalsIgnoreCase(postman.ReturnRight)){
            	postmanRight |= 2;//2
            }
            
            //揽件权限
            if("1".equalsIgnoreCase(postman.CollectRight)){
                postmanRight |= 4;//4
            }
        	break;
        case SysDict.POSTMAN_TYPE_DAD://"82"
        case SysDict.POSTMAN_TYPE_DAD_BUSINESSPARTNER://"83"
        	postmanRight = 0;
        	//投递权限
            if("1".equalsIgnoreCase(postman.DADRight)){
            	postmanRight |= 8;//直投权限
            }
            //回收权限
            if("1".equalsIgnoreCase(postman.ReturnRight)){
            	postmanRight |= 2;//2回收权限-回收逾期包裹
            }
        	break;
        case SysDict.POSTMAN_TYPE_RETURN://"84"
        	postmanRight = 0;
        	//回收权限
            if("1".equalsIgnoreCase(postman.ReturnRight)){
            	postmanRight |= 2;//2回收权限-回收逾期包裹
            }
        	break;
        default:
        	postmanRight =  0;
        	break;
        }
        
        return postmanRight;
    }
    /** 
    * 字符串转换成日期 
    * @param str 
    * @return date 
    */ 
    public  java.sql.Date strToDate(String str) { 
      
    if(StringUtils.isEmpty(str)){
    	return null;
    }
       SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
       Date date = null; 
       if(date == null){
    	   format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
    	   try { 
    		   
    	        date = format.parse(str); 
    	        return new java.sql.Date(date.getTime());
    	   } catch (ParseException e) { 
    	        
    	   } 
       }
       if(date == null){
    	   format = new SimpleDateFormat("yyyy-MM-dd"); 
    	   try { 
    	        date = format.parse(str); 
    	        return new java.sql.Date(date.getTime());
    	   } catch (ParseException e) { 
    	        //e.printStackTrace(); 
    	   } 
       }
       if(date == null){
    	   format = new SimpleDateFormat("dd/MM/yyyy"); 
    	   try { 
    	        date = format.parse(str); 
    	        return new java.sql.Date(date.getTime());
    	   } catch (ParseException e) { 
    	        //e.printStackTrace(); 
    	   } 
       }
       return null; 
    } 
    /**
     * 在寄件订单创建时预付费
     * @param partner
     * @param amount
     * @param itemcode
     * @param serverid
     * @param detail
     * @return tradeWaterID
     * @throws EduException
     */
    public String doBusinessPartnerPrePay(IMBusinessPartner partner, double amount, String itemcode, String serverid, String detail) throws EduException{
    	
    	java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();
    	IMBusinessPartnerDAO businessPartnerDAO = daoFactory.getIMBusinessPartnerDAO();
    	
    	String tradeWaterID = "";
    	
        synchronized (IMBusinessPartner.class) {//同步
        	//#start 检查合作伙伴账户余额
        	IMBusinessPartner businessPartner = new IMBusinessPartner();
            businessPartner.BPartnerID = partner.BPartnerID;
            businessPartner = businessPartnerDAO.find(businessPartner);//需要获取最新的账户余额
            
            //授信额度查询
        	double creditLimit = 0.0;
            if(SysDict.BUSINESS_CREDIT_FLAG_YES.equalsIgnoreCase(businessPartner.CreditFlag)){
            	if(businessPartner.CreditLimit>0.0){
            		creditLimit = businessPartner.CreditLimit;
            	}
            	if(creditLimit>businessPartner.Balance){//授信额度不大于余额
            		creditLimit = businessPartner.Balance;
            	}
            }
            //检查余额
            if((amount) > (businessPartner.Balance+creditLimit)){
        		throw new EduException(ErrorCode.ERR_BALANCE_NOT_ENOUGH);
        	}
            //#end 
            
        	//#start 生成交易账单
            PYServiceBillWaterDAO serviceBillWaterDAO = daoFactory.getPYServiceBillWaterDAO();
            UUID uuid = UUID.randomUUID();
            tradeWaterID = uuid.toString().replace("-", "");
            
            PYServiceBillWater serviceBillWater = new PYServiceBillWater();
            serviceBillWater.TradeWaterID = tradeWaterID;
    		serviceBillWater.BPartnerID  = businessPartner.BPartnerID;
    		serviceBillWater.PackageID   = itemcode;
    		serviceBillWater.BillTime    = sysDateTime;
    		serviceBillWater.BillType    = SysDict.BILL_TYPE_PREPAY;
    		serviceBillWater.BillDetails  = detail;
    		serviceBillWater.BillAmt      = (amount);
    		serviceBillWater.ServiceID    = serverid;
    		serviceBillWater.Balance      = businessPartner.Balance;
    		serviceBillWater.Remark = "";
    		serviceBillWaterDAO.insert(serviceBillWater);
            //#end
    		
    		//#start 更新账户余额	
    		businessPartner.Balance -= amount;
        	JDBCFieldArray setCols = new JDBCFieldArray();
	        JDBCFieldArray whereCols = new JDBCFieldArray();
	        //setCols.add("Balance", businessPartner.Balance);
	        setCols.addSQL("Balance=Balance-"+amount);
	        
	        whereCols.add("BPartnerID", businessPartner.BPartnerID);
        	businessPartnerDAO.update(setCols, whereCols);
        	//#end
        	
        	partner.Balance = businessPartner.Balance;//输出
		}
    	return tradeWaterID;
    }
    /**
     * 生成支付账单：1）AZC收件，2）直投，3）揽件员取件（？？？？？）
     * @param tradeWaterID
     * @param bPartnerID
     * @param itemcode
     * @return 
     * @throws EduException
     */
    public void doBusinessPartnerPay(String tradeWaterID, String bPartnerID, String itemcode) throws EduException{
    	java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();
    	//更新交易账单状态
    	synchronized (IMBusinessPartner.class) {//同步
            PYServiceBillWaterDAO serviceBillWaterDAO = daoFactory.getPYServiceBillWaterDAO();
            JDBCFieldArray setCols = new JDBCFieldArray();
	        JDBCFieldArray whereCols = new JDBCFieldArray();
	        
	        setCols.add("BillType", SysDict.BILL_TYPE_CONSUME);
	        setCols.add("BillTime", sysDateTime);
	        
	        whereCols.add("TradeWaterID", tradeWaterID);
	        whereCols.add("BPartnerID", bPartnerID);
	        whereCols.add("BillType", SysDict.BILL_TYPE_PREPAY);
	        whereCols.add("PackageID", itemcode);
	        
	        serviceBillWaterDAO.update(setCols, whereCols);
    	}
    	return;
    }
    /**
     * 取消订单时预付金额退回
     * @param businessPartner
     * @param tradeWaterID
     * @param itemcode
     * @throws EduException
     */
    public void doBusinessPartnerRefund(IMBusinessPartner businessPartner, String tradeWaterID, String itemcode) throws EduException{
    	IMBusinessPartnerDAO businessPartnerDAO = daoFactory.getIMBusinessPartnerDAO();
    	PYServiceBillWaterDAO serviceBillWaterDAO = daoFactory.getPYServiceBillWaterDAO();
    	
    	synchronized (IMBusinessPartner.class) {//同步
    		PYServiceBillWater bill = new PYServiceBillWater();
        	bill.BPartnerID = businessPartner.BPartnerID;
        	bill.TradeWaterID = tradeWaterID;
        	bill = serviceBillWaterDAO.find(bill);
        	double amount = bill.BillAmt;
        	if(amount < 0){
        		amount = -amount;
        	}
        	if(SysDict.BILL_TYPE_PREPAY.equals(bill.BillType)
        			&& itemcode.equals(bill.PackageID)){
        		//#start 更新账户余额	
        		businessPartner.Balance += amount;
            	JDBCFieldArray setCols = new JDBCFieldArray();
    	        JDBCFieldArray whereCols = new JDBCFieldArray();
    	        //setCols.add("Balance", businessPartner.Balance);
    	        setCols.addSQL("Balance=Balance+"+amount);
    	        
    	        whereCols.add("BPartnerID", businessPartner.BPartnerID);
            	businessPartnerDAO.update(setCols, whereCols);
            	//#end
            	
            	//#start 删除预付账单
            	serviceBillWaterDAO.delete(bill);
            	//#end
        	}else{
        		throw new EduException(ErrorCode.ERR_PYSERVICEBILLWATERNODATA);
        	}
    	}
    	return;
    }
    
    /**
     * Business Partner充值
     * @param partner
     * @param amount
     * @param tradeWaterID
     * @param detail : detail for the top up reason
     * @return tradeWaterID
     * @throws EduException
     */
    public String doBusinessPartnerTopUp(IMBusinessPartner partner, double amount, String tradeWaterID, String detail) throws EduException{
    	
    	java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();
    	IMBusinessPartnerDAO businessPartnerDAO = daoFactory.getIMBusinessPartnerDAO();
    	
    	if(StringUtils.isEmpty(tradeWaterID)){
            UUID uuid = UUID.randomUUID();
            tradeWaterID = uuid.toString().replace("-", "");
    	}
        synchronized (IMBusinessPartner.class) {//同步
        	//#start 检查合作伙伴账户余额
        	IMBusinessPartner businessPartner = new IMBusinessPartner();
            businessPartner.BPartnerID = partner.BPartnerID;
            businessPartner = businessPartnerDAO.find(businessPartner);//需要获取最新的账户余额  
            //#end      	
    		
    		//#start 更新账户余额	
    		businessPartner.Balance += amount;
        	JDBCFieldArray setCols = new JDBCFieldArray();
	        JDBCFieldArray whereCols = new JDBCFieldArray();
	        //setCols.add("Balance", businessPartner.Balance);
	        setCols.addSQL("Balance=Balance+"+amount);
	        
	        whereCols.add("BPartnerID", businessPartner.BPartnerID);
        	businessPartnerDAO.update(setCols, whereCols);
        	//#end
        	
        	//#start 生成交易账单
        	PYServiceBillWaterDAO serviceBillWaterDAO = daoFactory.getPYServiceBillWaterDAO();
            PYServiceBillWater serviceBillWater = new PYServiceBillWater();
            serviceBillWater.TradeWaterID = tradeWaterID;
    		serviceBillWater.BPartnerID  = businessPartner.BPartnerID;
    		serviceBillWater.PackageID   = "";
    		serviceBillWater.BillTime    = sysDateTime;
    		serviceBillWater.BillType    = SysDict.BILL_TYPE_TOPUP;
    		serviceBillWater.BillDetails  = detail;
    		serviceBillWater.BillAmt      = (amount);
    		serviceBillWater.ServiceID    = "";
    		serviceBillWater.Balance      = businessPartner.Balance;
    		serviceBillWaterDAO.insert(serviceBillWater);
            //#end
    		
        	partner.Balance = businessPartner.Balance;//输出
		}
    	return tradeWaterID;
    }
    private String getRemarkValue(String remark, String key){
        if(StringUtils.isEmpty(remark) || StringUtils.isEmpty(key)){
            return "";
        }
        String value = "";
        String temp = remark;
        key = key.trim();
        if(!key.endsWith("=")){
            key = key+"=";
        }
        int index= temp.indexOf(key);
        if(index < 0){
            return value;
        }
        temp = temp.substring(index+key.length());
        if(temp.indexOf(";")>=0){
            value = temp.substring(0, temp.indexOf(";"));
        }else{
            value = temp;
        }
        value = value.trim();
        return value;
    }
    /**
     * 订单数据发回PPC
     * @param itemTransfer
     * @return
     * @throws EduException
     */
    public int toDoTransferSend(PTDeliverItemTransfer itemTransfer) throws EduException{
    	int res=0;
    	java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();
    	//订单记录中取ItemID
        if(StringUtils.isNotEmpty(itemTransfer.Remark)){
            String itemID = getRemarkValue(itemTransfer.Remark, "ItemID");
            if(itemTransfer.WaterID == 0){
                itemTransfer.WaterID    = -NumberUtils.parseLong(itemID);//ppc service的WaterID，在系统中保存为负值，用于区别系统生成的WaterID(正值)
            }
        }
        
    	//发送队列中同一个订单只能有一个记录
    	PTDeliverItemTransferDAO itemTransferDAO = daoFactory.getPTDeliverItemTransferDAO();
    	
    	JDBCFieldArray whereCols = new JDBCFieldArray();
    	whereCols.add("PackageID", itemTransfer.PackageID);
    	
    	RowSet rset = itemTransferDAO.select(whereCols);
    	if(RowSetUtils.rowsetNext(rset))
        {
    	    //同一个订单订单使用同一个流水号
    	    itemTransferDAO.delete(whereCols);
    	    if(itemTransfer.WaterID == 0){
                itemTransfer.WaterID = RowSetUtils.getLongValue(rset, "WaterID");
    	    }
        }else{
            //订单记录中没有ItemID，生成ItemID
            if(itemTransfer.WaterID == 0){
                SequenceGenerator seqGen = SequenceGenerator.getInstance();
                itemTransfer.WaterID = seqGen.getNextKey(SequenceGenerator.SEQ_ITEMTRANSFERWATERID);
            }
        }
    	//删除记录，防止插入冲突
    	itemTransferDAO.delete(itemTransfer);
    	
    	itemTransfer.ReSendNum  = 0;
    	if(StringUtils.isEmpty(itemTransfer.TransferStatus)){
    	    itemTransfer.TransferStatus = "1";// 0:未发送 1:发送进行中 2:发送成功 4:发送失败
    	}
    	
        if (itemTransfer.LastModifyTime == null) {
            itemTransfer.LastModifyTime = sysDateTime;
        }
        //插入订单数据
    	itemTransferDAO.insert(itemTransfer);
    	
    	//插入发送流水
    	PTTransferItemWaterDAO trasnferWaterDAO = daoFactory.getPTTransferItemWaterDAO();
    	PTTransferItemWater trasnferWater = new PTTransferItemWater();
    	SequenceGenerator seqGen = SequenceGenerator.getInstance();
    	trasnferWater.WaterID = seqGen.getNextKey(SequenceGenerator.SEQ_TRANSFERWATERID);
    	trasnferWater.CreateTime = sysDateTime;
    	trasnferWater.LastModifyTime = sysDateTime;
    	trasnferWater.ItemStatus     = itemTransfer.ItemStatus;
    	trasnferWater.PackageID    = itemTransfer.PackageID;
    	trasnferWater.PPCID        = itemTransfer.PPCID;
    	trasnferWater.ZoneID       = itemTransfer.ZoneID;
    	trasnferWater.TransferID   = String.valueOf((int)Math.abs(itemTransfer.WaterID));
    	trasnferWater.ResendNum    = 0;
    	trasnferWater.TransferType = itemTransfer.TransferType;
    	trasnferWater.TransferStatus = itemTransfer.TransferStatus;
    	trasnferWaterDAO.insert(trasnferWater);
        
        if("1".equals(itemTransfer.TransferStatus)){
            //PPC
            IMPostalProcessingCenterDAO ppcDAO = daoFactory.getIMPostalProcessingCenterDAO();
            IMPostalProcessingCenter  ppc = new IMPostalProcessingCenter();
            ppc.PPCID = itemTransfer.PPCID;
            ppc  = ppcDAO.find(ppc);
            
            //创建发送信息对象
            SendInfo sendInfo     = new SendInfo();
            sendInfo.apiString    = ppc.PPCServerURL;
            sendInfo.userKey      = ppc.PPCServerPassword;
            sendInfo.userName     = ppc.PPCServerUsername;
            sendInfo.PPCInterface = getRemarkValue(ppc.Remark,"PPCInterface");
            sendInfo.uploadFlag   = getRemarkValue(ppc.Remark,"UploadFlag");
            
            sendInfo.ItemID       = itemTransfer.WaterID;
            sendInfo.ItemCode     = itemTransfer.PackageID;
            sendInfo.ItemType     = itemTransfer.TransferType; //NumberUtils.parseInt(itemTransfer.TransferType);
            sendInfo.CreateTime   = itemTransfer.CreateTime;
            
            sendInfo.ZoneID = itemTransfer.ZoneID;
            sendInfo.ItemStatus = itemTransfer.ItemStatus;
            sendInfo.TerminalNo   = itemTransfer.TerminalNo;
            
            sendInfo.TransferWaterID = trasnferWater.WaterID;
            
            SendItemManager.getInstance().sendItemsToPPC(sendInfo);
        }
    	
    	return res;
    }
    /**
     * 添加订单状态更新信息到发送队列
     * 状态更新只发送给Master PPC Server
     * @param itemTransfer
     * @return
     * @throws EduException
     */
    public int addItemStatusUpdate(PTDeliverItemTransfer itemTransfer) throws EduException{
    	int res=0;
    	if(StringUtils.isEmpty(itemTransfer.TransferType)){
    	    itemTransfer.TransferType   = SysDict.TRANSFER_TYPE_ANNOUNCING;
    	}
    	if(SysDict.TRANSFER_TYPE_ANNOUNCING.equals(itemTransfer.TransferType)){
    	    itemTransfer.PPCID         = Constant.DEFAULT_PPC_ID;//状态更新只发送给Master PPC Server
    	    itemTransfer.TransferStatus = "0";// 0:未发送 1:发送进行中 2:发送成功 4:发送失败
    	    return 0;//add by zxy 20160129 不发送进行中的订单状态,不加入发送队列
    	}else{
    	    itemTransfer.TransferStatus = "1";// 0:未发送 1:发送进行中 2:发送成功 4:发送失败
    	}
    	res = toDoTransferSend(itemTransfer);
    	
    	return res;
    }
    public int addItemStatusUpdate(String ppcId,String nextStatus, PTReadyPackage readyPack, String transferType) throws EduException{
    	int res=0;
    	PTDeliverItemTransfer itemTransfer = new PTDeliverItemTransfer();
        itemTransfer.CreateTime = readyPack.CreateTime;
        itemTransfer.PackageID  = readyPack.PackageID;
        itemTransfer.ZoneID     = readyPack.ZoneID;
        itemTransfer.PPCID      = readyPack.PPCID;
        itemTransfer.TransferOrderID = "";
        itemTransfer.TransferType = transferType;
        itemTransfer.ItemStatus   = nextStatus;
        itemTransfer.Remark       = readyPack.Remark;
        itemTransfer.TerminalNo   = readyPack.TerminalNo;
        addItemStatusUpdate(itemTransfer);
    	return res;
    }
    public int addItemStatusUpdate(String ppcId,String nextStatus, PTInBoxPackage inboxPack, String transferType) throws EduException{
    	int res=0;
    	PTDeliverItemTransfer itemTransfer = new PTDeliverItemTransfer();
        itemTransfer.CreateTime = inboxPack.CreateTime;
    	itemTransfer.PackageID  = inboxPack.PackageID;
    	itemTransfer.ZoneID     = inboxPack.ZoneID;
    	itemTransfer.PPCID      = inboxPack.PPCID;
    	itemTransfer.TransferOrderID = "";
    	itemTransfer.ItemStatus   = nextStatus;
    	if("1".equals(inboxPack.DADFlag)){
    	    itemTransfer.TransferType = SysDict.TRANSFER_TYPE_D_DROPPED;
    	}else{
    	    itemTransfer.TransferType = SysDict.TRANSFER_TYPE_DROPPED;
    	}
    	itemTransfer.Remark       = inboxPack.Remark;
    	itemTransfer.TerminalNo   = inboxPack.TerminalNo;
    	addItemStatusUpdate(itemTransfer);
    	return res;
    }
    public int addItemStatusUpdate(String ppcId,String nextStatus, PTDeliverHistory historyPack, String transferType) throws EduException{
    	int res=0;
    	PTDeliverItemTransfer itemTransfer = new PTDeliverItemTransfer();
        itemTransfer.CreateTime = historyPack.CreateTime;
    	itemTransfer.PackageID  = historyPack.PackageID;
    	itemTransfer.ZoneID     = historyPack.ZoneID;
    	itemTransfer.PPCID      = ppcId;
    	itemTransfer.TransferOrderID = "";
    	itemTransfer.ItemStatus   = nextStatus;
    	itemTransfer.TransferType = transferType;
    	itemTransfer.Remark       = historyPack.Remark;
    	itemTransfer.TerminalNo   = historyPack.TerminalNo;
    	addItemStatusUpdate(itemTransfer);
    	return res;
    }
    public int addItemStatusUpdate(String ppcId,String nextStatus, DMHistoryItem historyItem, String transferType) throws EduException{
    	int res=0;
    	PTDeliverItemTransfer itemTransfer = new PTDeliverItemTransfer();
        itemTransfer.CreateTime = historyItem.CreateTime;
    	itemTransfer.PackageID  = historyItem.PackageID;
    	itemTransfer.ZoneID     = historyItem.ZoneID;
    	itemTransfer.PPCID      = ppcId;//historyPack.PPCID   ！！！！！源PPC和目标PPC可以不一致
    	itemTransfer.ItemStatus = nextStatus;
    	itemTransfer.TransferOrderID = historyItem.ReportOrderID;
    	itemTransfer.TransferType = transferType;
    	itemTransfer.Remark       = historyItem.Remark;
    	itemTransfer.TerminalNo   = historyItem.TerminalNo;
    	addItemStatusUpdate(itemTransfer);
    	return res;
    }
    /**
     * 地址信息换行由html的'<br>'改为'\n'
     * @param addressHtml 
     * @return
     */
    public String getAddressHtmlToText(String addressHtml){
    	StringBuffer addressText = new StringBuffer();
    	if(StringUtils.isNotEmpty(addressHtml)){
    		String textStr = Html2Text(addressHtml);//去除Html标签
    		String[] addressLines = textStr.split("<br>");
    		for(String line : addressLines){
    			if(!line.trim().equals("")){
    				if(addressText.length()>0){
    					addressText.append("\n");
    				}
    				addressText.append(line.trim());
    			}
    		}
    	}
    	return addressText.toString();
    }
    /**
     * 检查用户名是否已存在
     * @param username
     * @return  true-表示可用，false-表示已存在
     * @throws EduException
     */
    public boolean checkUserName(String username) throws EduException {
    	PreparedWhereExpression whereSQL = new PreparedWhereExpression();
    	whereSQL.add("UserName", username);
    	String sql = "SELECT COUNT(UserID) FROM  V_LoginUser a WHERE 1=1 " + whereSQL.getPreparedWhereSQL();
    	int count = dbSession.executeQueryCount(sql, whereSQL);
    	if(count>0){
    		return false;
    	}else{
    		return true;
    	}
    }
    /**
     * 获取订单记录详情
     * @param ItemCode 订单编号
     * @param QryType  默认通过订单号查询 
     * @param BeginDate 开始日期
     * @param EndDate   结束日期
     * @param level
     * @return
     * @throws EduException
     */
    public OutParamItemDetail getItemRecordDetail(String ItemCode, String QryType, java.sql.Date BeginDate, java.sql.Date EndDate, int level) throws EduException {
    	OutParamItemDetail out = new OutParamItemDetail();
    	
    	RowSet rset = null;
    	//调用UtilDAO.getCurrentDateTime()获得系统日期时间。
    	java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();
		java.sql.Date sysDate = DateUtils.toSQLDate(sysDateTime);

		if(EndDate!=null){
			EndDate = DateUtils.addDate(EndDate,1);
		}
		StringBuffer fromBuff  = new StringBuffer();//订单来源信息
        StringBuffer toBuff  = new StringBuffer();//收件人信息
        StringBuffer trackBuff = new StringBuffer();//订单状态变更详情

        String storedImgUrl  = "";//投递监控照片路径
    	String takedImgUrl   = "";//取件监控照片路径
    	String StoredTimeStr    = "";//投递时间   java.sql.Timestamp
    	String TakedTimeStr     = "";//取件时间  java.sql.Timestamp
    	String itemStatus    = "";//当前订单状态
    	
		java.sql.Timestamp createTime = null;//订单创建时间，进入Elocker的最初时间
		String sql = "";
		//寄件查询
    	if(createTime == null){
    		//#start 通过寄件表查询订单创建时间
    		PreparedWhereExpression whereSQL = new PreparedWhereExpression();
    		whereSQL.checkAdd("CreateTime", ">=", BeginDate);
    		whereSQL.checkAdd("CreateTime", "<=", EndDate); 			
    		
    		whereSQL.add("PackageID", ItemCode);
            sql = "SELECT * FROM  V_DMAllDeliveryItems a WHERE 1=1 " + whereSQL.getPreparedWhereSQL();
            
    		java.util.List<String> orderByField = new java.util.LinkedList<String>();
            orderByField.add("CreateTime DESC");//倒序，取最新的订单
            
            rset = dbSession.executeQuery(sql, orderByField, 0, 0, whereSQL);
            
            
            if(RowSetUtils.rowsetNext(rset)){
            	//String itemCode = RowSetUtils.getStringValue(rset, "PackageID");
            	createTime = RowSetUtils.getTimestampValue(rset, "CreateTime");
            	
            	// #start get from info
            	String bPartnerName =  RowSetUtils.getStringValue(rset, "BPartnerName");
            	String address = RowSetUtils.getStringValue(rset, "Address");
            	String mobile =  RowSetUtils.getStringValue(rset, "Mobile");
            	if(StringUtils.isNotEmpty(bPartnerName)){
        			fromBuff.append(bPartnerName+"\n");
        		}
        		if(StringUtils.isNotEmpty(mobile)){
        			fromBuff.append(mobile+"\n");
        		}
        		if(StringUtils.isNotEmpty(address)){
        			fromBuff.append(address+"\n");
        		}
    			// #end 
    			
    			// #start get to info
    			String customerName = RowSetUtils.getStringValue(rset, "CustomerName");
    			String customerAddress = RowSetUtils.getStringValue(rset, "CustomerAddress");
        		String customerMobile  = RowSetUtils.getStringValue(rset, "CustomerMobile");
        		if(StringUtils.isNotEmpty(customerName)){
        			toBuff.append(customerName+"\n");
        		}
        		if(StringUtils.isNotEmpty(customerMobile)){
        			toBuff.append(customerMobile+"\n");
        		}
        		if(StringUtils.isNotEmpty(customerAddress)){
        			toBuff.append(customerAddress+"\n");
        		}
        		//#end
            }
            //#end
    	}
    	//投递查询
    	if(true){//createTime == null
    		//#start 通过投递表查询订单创建时间
    		PreparedWhereExpression whereSQL = new PreparedWhereExpression();
    		
    		if(createTime != null){
    			whereSQL.checkAdd("CreateTime", ">=", createTime);
    		}else{
    			whereSQL.checkAdd("CreateTime", ">=", BeginDate);
    		}
    		
    		whereSQL.checkAdd("CreateTime", "<=", EndDate);
            
    		whereSQL.add("PackageID", ItemCode);
            sql = "SELECT * FROM  V_PTAllItems a WHERE 1=1 " + whereSQL.getPreparedWhereSQL();
            
    		java.util.List<String> orderByField = new java.util.LinkedList<String>();
            orderByField.add("CreateTime ASC");//DESC-倒序，取最新的订单   ASC - 取最初订单
            
            rset = dbSession.executeQuery(sql, orderByField, 0, 0, whereSQL);
            
            while(RowSetUtils.rowsetNext(rset)){
            	//String itemCode = RowSetUtils.getStringValue(rset, "PackageID");
            	if(createTime == null){
            		createTime = RowSetUtils.getTimestampValue(rset, "CreateTime");
            	}
            	// #start from info
    			if(fromBuff.length() == 0){
    				String ppcName = RowSetUtils.getStringValue(rset, "PPCName");
    				String companyName = RowSetUtils.getStringValue(rset, "CompanyName");
    				if(StringUtils.isNotEmpty(ppcName)){
    					fromBuff.append(ppcName+"\n");
    				}
    				if(StringUtils.isNotEmpty(companyName)){
    					fromBuff.append(companyName+"\n");
    				}
    			}
    			// #end 
    			// #start to info
    			if(toBuff.length() == 0){
    				String customerName = RowSetUtils.getStringValue(rset, "CustomerName");
        			String customerAddress = RowSetUtils.getStringValue(rset, "CustomerAddress");
            		String customerMobile  = RowSetUtils.getStringValue(rset, "CustomerMobile");
            		if(StringUtils.isNotEmpty(customerName)){
            			toBuff.append(customerName+"\n");
            		}
            		if(StringUtils.isNotEmpty(customerMobile)){
            			toBuff.append(customerMobile+"\n");
            		}
            		if(StringUtils.isNotEmpty(customerAddress)){
            			toBuff.append(customerAddress+"\n");
            		}
    			}
        		// #end
    			java.sql.Timestamp StoredTime    = RowSetUtils.getTimestampValue(rset, "StoredTime");;//投递时间   java.sql.Timestamp
    			if(StoredTime.after(createTime)){
    				StoredTimeStr = Constant.dateFromat.format(StoredTime);
    			}
    			java.sql.Timestamp TakedTime     = RowSetUtils.getTimestampValue(rset, "TakedTime");//取件时间  java.sql.Timestamp
    			if(TakedTime.after(createTime)){
    				TakedTimeStr = Constant.dateFromat.format(TakedTime);
    				
    				String terminalNo    = RowSetUtils.getStringValue(rset, "TerminalNo");
        			String tradeWaterNo  = RowSetUtils.getStringValue(rset, "TradeWaterNo");
        			if(StringUtils.isNotEmpty(terminalNo)&& StringUtils.isNotEmpty(tradeWaterNo)){
        				String uploadType = Constant.TERMINAL_UPLADETYPE_PICKUPPIC;
            			String newfileName = tradeWaterNo + ".jpg";
            			String picPath = apcfg.getLockerPicFilePath()+ "/" +terminalNo + "/" + uploadType + "/" +newfileName;//"terminalLog/"+
            			
            			takedImgUrl = picPath;//取最新的
        			}
    			}
            }
    		//#end
    	}
    	
    	//单号不存在
    	if(createTime == null){
    		throw new EduException(ErrorCode.ERR_PACKAGENOTEXISTS);
    	}
    	
    	//#start 订单历史查询
    	java.util.List<OutParamPTDeliveryItemDetail> detailList = new java.util.LinkedList<OutParamPTDeliveryItemDetail>();
    	PreparedWhereExpression whereSQLDetail = new PreparedWhereExpression();
    	whereSQLDetail.add("PackageID", ItemCode);
    	whereSQLDetail.checkAdd("LastModifyTime", ">=", createTime);
    	whereSQLDetail.checkAdd("LastModifyTime", "<=", EndDate);
        sql = "SELECT * FROM V_ItemHistoryDetail  WHERE 1=1 "+ whereSQLDetail.getPreparedWhereSQL();
        
        java.util.List orderByField1 = new java.util.LinkedList<String>();
        orderByField1.add("LastModifyTime ASC");//DESC-倒序，
        rset = dbSession.executeQuery(sql, orderByField1, 0, 0, whereSQLDetail);
        while (RowSetUtils.rowsetNext(rset)) {
        	OutParamPTDeliveryItemDetail itemDetail = new OutParamPTDeliveryItemDetail();
        	itemDetail.ItemCode   = RowSetUtils.getStringValue(rset, "PackageID");
        	itemDetail.CreateTime = RowSetUtils.getTimestampValue(rset, "LastModifyTime");
        	itemDetail.ItemStatus    = RowSetUtils.getStringValue(rset, "ItemStatus");
        	itemDetail.ItemStatusName = RowSetUtils.getStringValue(rset, "ItemStatusName");
        	itemDetail.OperatorName = RowSetUtils.getStringValue(rset, "OperName");
        	itemDetail.Activity = RowSetUtils.getStringValue(rset, "Remark");
        	detailList.add(itemDetail);
        }
    	//#end
        
        //#start 获取记录信息
        
    	
    	String createTimeString = "";//订单创建时间作为订单列表的Key值
    	
        ListIterator<OutParamPTDeliveryItemDetail> list = detailList.listIterator();
        while(list.hasNext()){
        	JSONObject itemInfo = null;
			OutParamPTDeliveryItemDetail itemDetail = list.next();
			
			String dateTimeString = Constant.dateFromat.format(itemDetail.CreateTime);
			//
			switch(itemDetail.ItemStatus){
        	case SysDict.ITEM_STATUS_DROP_DROPPED:
        	case SysDict.ITEM_STATUS_DROP_D_DROPPED:
        		if(itemDetail.Activity.startsWith("modExpireTime")){
        			continue;
        		}
        		break;
        	}
			
			itemStatus = itemDetail.ItemStatusName;
			trackBuff.append(""+dateTimeString);
			trackBuff.append(", "+itemDetail.ItemStatusName);
    		trackBuff.append(", "+itemDetail.OperatorName);
    		if(level > 0){
    			trackBuff.append(", "+itemDetail.Activity);
    		}   		
    		trackBuff.append("\n");
        }
        //#end
        
        //#start 返回订单记录详情
        out.setItemCode(ItemCode);
        out.setCreateTime( Constant.dateFromat.format(createTime));
        out.setItemStatus(itemStatus);
        out.setFromBuff(fromBuff.toString());
        out.setToBuff(toBuff.toString());
        out.setItemDetail(trackBuff.toString());
        out.setStoredTime(StoredTimeStr);
        out.setStoredImgUrl(storedImgUrl);// = "lib/images/pic004.jpg";//storedImgUrl;
        out.setTakedImgUrl(takedImgUrl);//  = "lib/images/screen.jpg";//takedImgUrl;
        out.setTakedTime(TakedTimeStr);
        //#end 
    	return out;
    	
    }
    /**
     * 通过邮件发送告警信息
     * @param reportWaterID 告警流水号(0表示未知流水号，发送当前柜体状态)
     * @param terminalNo      
     * @param mode   :1-只发送邮件；2-只发送短信；其他-发送短信和邮件         
     * @return 0 发送成功 -1 柜体不存在；-2 手机和邮箱都不存在；-3邮箱不存在；-4手机不存在;-5 邮件发送失败；-6 短信发送失败
     * @throws EduException
     */
    public int doSendAlarmInfo(long reportWaterID, String terminalNo, int mode) throws EduException{
    	int result = 0;
    	//柜体信息
    	String terminalName = "";
		String location = "";
		String terminalStatus = "9";//未知
		String terminalStatusName = "";
		//String registerFlag = "0";
		String maintEmail = "";
		String maintMobile = "";
		
		PreparedWhereExpression whereSQL1 = new PreparedWhereExpression();
	    whereSQL1.add("TerminalNo", terminalNo);
	    
	    String sql = "SELECT TerminalNo,TerminalName, Location, TerminalStatus, TerminalStatusName,RegisterFlag,MaintEmail,MaintMobile FROM V_TBTerminal a WHERE 1=1 " + whereSQL1.getPreparedWhereSQL();
	    
	    RowSet rset = dbSession.executeQuery(sql, whereSQL1);
	    if(RowSetUtils.rowsetNext(rset)){
	    	//terminalNo = RowSetUtils.getStringValue(rset, "TerminalNo");
	    	terminalName = RowSetUtils.getStringValue(rset, "TerminalName");
	    	location = RowSetUtils.getStringValue(rset, "Location");
	    	
	    	terminalStatus = RowSetUtils.getStringValue(rset, "TerminalStatus");
	    	terminalStatusName    = RowSetUtils.getStringValue(rset, "TerminalStatusName");
	    	//registerFlag = RowSetUtils.getStringValue(rset, "RegisterFlag");
	    	maintEmail = RowSetUtils.getStringValue(rset, "MaintEmail");
	    	maintMobile = RowSetUtils.getStringValue(rset, "MaintMobile");
	    }else{
	    	result = -1;//柜体不存在
	    	return result;
	    }
    	
	    if(StringUtils.isEmpty(maintEmail)
			&&StringUtils.isEmpty(maintMobile)){
	    	result = -2;//邮箱和手机不存在
			return result;
		}else{
		    if(mode==1 ){//1-只发送邮件；2-只发送短信；其他-发送短信和邮件       
		        if(StringUtils.isEmpty(maintEmail)){
		            result = -3;//邮箱不存在
	                return result;
		        }
		        maintMobile = "";
		    }
		    if(mode == 2 ){//1-只发送邮件；2-只发送短信；其他-发送短信和邮件       
		    	if(StringUtils.isEmpty(maintMobile)){
		    	    result = -4;//手机不存在
		    	    return result;
		    	}
		    	maintEmail = "";
		    }
		}
	    
	    //#start 取柜体信息
	    StringBuffer lockerInfo = new StringBuffer();
	    lockerInfo.append("  LockerID : "+terminalNo+"\n");
	    lockerInfo.append("LockerName : "+terminalName+"\n");
	    lockerInfo.append("  Location : "+location+"\n");
	    lockerInfo.append("    Status : "+terminalStatusName+"\n");
	    //#end

	    String subjectMsg = ""+location;
    	/////////////////////获取报警信息
    	StringBuffer alarmInfo = new StringBuffer();
		
    	if(reportWaterID>0){
    	    subjectMsg += ",ReportID="+reportWaterID;
    		MBTerminalAlertLogDAO alertLogDAO = daoFactory.getMBTerminalAlertLogDAO();
    		MBTerminalAlertLog alertLog = new MBTerminalAlertLog();
    		alertLog.ReportWaterID = reportWaterID;
    		JDBCFieldArray whereCols = new JDBCFieldArray();
    		whereCols.add("ReportWaterID", reportWaterID);
        	whereCols.add("TerminalNo", terminalNo);
    		if(alertLogDAO.isExist(whereCols)>0){
    			//#start 获取报警信息
    			PreparedWhereExpression whereSQL = new PreparedWhereExpression();
    			whereSQL.add("ReportWaterID", reportWaterID);

    	        sql = "SELECT * FROM V_TerminalAlertLog a WHERE 1=1 " + whereSQL.getPreparedWhereSQL();
    	        
    	        rset = dbSession.executeQuery(sql,  whereSQL);
    	        if (RowSetUtils.rowsetNext(rset)) {
    	        	long ReportWaterID = RowSetUtils.getLongValue(rset, "ReportWaterID");
    	        	String AlertType     = RowSetUtils.getStringValue(rset, "AlertType");
    	        	String AlertTypeName = RowSetUtils.getStringValue(rset, "AlertTypeName");
    	        	//String AlertLevel = RowSetUtils.getStringValue(rset, "AlertLevel");
    	        	String AlertLevelName = RowSetUtils.getStringValue(rset, "AlertLevelName");
    	        	String AlertContent = RowSetUtils.getStringValue(rset, "AlertContent");
    	        	String BoxNo = RowSetUtils.getStringValue(rset, "BoxNo");
    	        	java.sql.Timestamp LogTime = RowSetUtils.getTimestampValue(rset, "LogTime");
    	        	
    	        	String timeStr = Constant.dateFromat.format(LogTime);//时间格式化
    	        	//
    	        	alarmInfo.append("ReportID : "+ReportWaterID+"\n");
    	        	alarmInfo.append("ReportTime : "+timeStr+"\n");
    	        	alarmInfo.append("AlarmType : "+AlertTypeName+"\n");
    	        	alarmInfo.append("AlarmLevel : "+AlertLevelName+"\n");
    	        	if(StringUtils.isNotEmpty(AlertContent)){
    	        		alarmInfo.append("AlarmContent : "+AlertContent+"\n");
    	        	}
    	        	if(StringUtils.isNotEmpty(BoxNo)){
    	        	    if(SysDict.ALERT_TYPE_DRIVERERROR.equals(AlertType)){
    	        	        alarmInfo.append("Side locker No. : "+BoxNo+"\n");
    	        	    }else{
    	        	        alarmInfo.append("BoxNo : "+BoxNo+"\n");
    	        	    }
    	        		
    	        	}
    	        }
    			//#end
    		}
    	}else if(SysDict.TERMINAL_STATUS_FAULT_BOX.equals(terminalStatus)){
    	    subjectMsg += ","+terminalStatusName;
    	    PreparedWhereExpression whereSQL = new PreparedWhereExpression();
            whereSQL.add("TerminalNo", terminalNo);
            whereSQL.addSQL(utilDAO.getFlagInSQL("a.BoxStatus", "1,2,3"));//1-锁定，2-故障，3-故障锁定
            sql = "SELECT * FROM V_TBBoxStatusInfo a WHERE 1=1 " + whereSQL.getPreparedWhereSQL();

            java.util.LinkedList list = new java.util.LinkedList();
            list.add("TerminalNo,DeskNo,DeskBoxNo");

            alarmInfo.append("Abnormal Box List : \n");
            RowSet rset2 = dbSession.executeQuery(sql, list, 0, 0, whereSQL);
            while(RowSetUtils.rowsetNext(rset2)){
                alarmInfo.append("BoxNo="+RowSetUtils.getStringValue(rset2, "BoxNo"));
                //alarmInfo.append(",DeskNo="+RowSetUtils.getIntValue(rset2, "DeskNo"));
                //alarmInfo.append(",DeskBoxNo="+RowSetUtils.getIntValue(rset2, "DeskBoxNo"));
                alarmInfo.append(",Status="+RowSetUtils.getStringValue(rset2, "BoxStatusName"));
                //alarmInfo.append("UsedStatus="+RowSetUtils.getStringValue(rset2, "BoxUsedStatusName"));
                /*String itemcode = RowSetUtils.getStringValue(rset2, "PackageID");
                String azc = RowSetUtils.getStringValue(rset2, "ZoneName");
                String company = RowSetUtils.getStringValue(rset2, "CompanyName");
                if(StringUtils.isNotEmpty(itemcode)){
                    alarmInfo.append(", ItemCode="+itemcode);
                }
                if(StringUtils.isNotEmpty(azc)){
                    alarmInfo.append(", AZC="+azc);
                }
                if(StringUtils.isNotEmpty(company)){
                    alarmInfo.append(", Company="+company);
                }*/
                alarmInfo.append(";\n");
            }
    	}
    	
		//发送邮件
    	if(StringUtils.isNotEmpty(maintEmail)){
    		try{
    			EmailSenderManager.sendTreminalAlarmInfo(subjectMsg, terminalNo, lockerInfo.toString(), alarmInfo.toString(), maintEmail);
    		} catch (Exception e) {
    			// TODO Auto-generated catch block
    			//e.printStackTrace();
    			result = -5;
    		}
    	}
		
		//发送短信
		if(StringUtils.isNotEmpty(maintMobile)
			&& "1".equals(ctrlParam.getSendLockerAlarmSMS())){
			try{
				String msg = "";
				if(alarmInfo.length()>0){
					msg = alarmInfo.toString();//.replaceAll("\n", ",");
				}else{
					msg = "Locker Status : "+terminalStatusName;
				}
				SMSManager.getInstance().sentLockerAlarmSMS(terminalNo, location, msg, maintMobile);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				result = -6;
				return result;
			}
		}
		return result;
    }

    /**
     * 过滤含html标签的字符串
     * @param inputString
     * @return
     */
    public String Html2Text(String inputString){
        String htmlStr = inputString; //含html标签的字符串
        String textStr ="";
        //java.util.regex.Pattern p_script;
        //java.util.regex.Matcher m_script;
        //java.util.regex.Pattern p_style;
        //java.util.regex.Matcher m_style;
        //java.util.regex.Pattern p_html;
        //java.util.regex.Matcher m_html;

        java.util.regex.Pattern p_html;
        java.util.regex.Matcher m_html;
        
        java.util.regex.Pattern p_nbsp;
        java.util.regex.Matcher m_nbsp;
        
        try{
           String regEx_html = "<(?!br).*?>"; //定义HTML标签的正则表达式,保留换行
    	   p_html = Pattern.compile(regEx_html,Pattern.CASE_INSENSITIVE);
           m_html = p_html.matcher(htmlStr);
           htmlStr = m_html.replaceAll(""); //过滤html标签
           
           String regEx_nbsp = "&nbsp;";
           p_nbsp = Pattern.compile(regEx_nbsp,Pattern.CASE_INSENSITIVE);
           m_nbsp = p_nbsp.matcher(htmlStr);
           htmlStr = m_nbsp.replaceAll(" "); //过滤html标签
           
             /*String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; //定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script> }
             String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"; //定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style> }
             String regEx_html = "<[^>]+>"; //定义HTML标签的正则表达式


             p_script = Pattern.compile(regEx_script,Pattern.CASE_INSENSITIVE);
             m_script = p_script.matcher(htmlStr);
             htmlStr = m_script.replaceAll(""); //过滤script标签

             p_style = Pattern.compile(regEx_style,Pattern.CASE_INSENSITIVE);
             m_style = p_style.matcher(htmlStr);
             htmlStr = m_style.replaceAll(""); //过滤style标签

             p_html = Pattern.compile(regEx_html,Pattern.CASE_INSENSITIVE);
             m_html = p_html.matcher(htmlStr);
             htmlStr = m_html.replaceAll(""); //过滤html标签
             */

            textStr = htmlStr;
        }catch(Exception e){
             //Manager.log.debug("neiNewsAction","Html2Text: " + e.getMessage());
        }
        return textStr;//返回文本字符串
    } 
    
    /**
     *  激活或更新POBox地址
     * @param customer
     * @param lockerid
     * @param months
     * @return
     * @throws EduException
     */
    public IMCustomer activeOrUpdatePOBoxAddress(IMCustomer customer, String lockerid, int months) throws EduException{
    	java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();
    	boolean isSendSMS = false;//只有在改变有效期和改变柜体情况下，才发生短信
    	IMCustomerDAO customerDAO = daoFactory.getIMCustomerDAO();
    	JDBCFieldArray setCols = new JDBCFieldArray();
        JDBCFieldArray whereCols = new JDBCFieldArray();
        
        if(StringUtils.isNotEmpty(lockerid)){
        	//检查是否允许修改柜体
        	if(!lockerid.equals(customer.TerminalNo)){
        		if("yes".equalsIgnoreCase(ctrlParam.getCustomerAddressChange())){
        			isSendSMS = true;
        		}else{
        			lockerid = customer.TerminalNo;
        		}
        	}
        }else{
        	lockerid = customer.TerminalNo;
        }

		//#start 检查柜体POBox服务是否可用
        TBTerminalDAO terminalDAO = daoFactory.getTBTerminalDAO();
		TBTerminal terminal = new TBTerminal();
		terminal.TerminalNo = lockerid;
		try{
			terminal = terminalDAO.find(terminal);
			if(!"1".equals(terminal.AppRegisterFlag)){
				throw new EduException(ErrorCode.ERR_POBOX_SERVICE_NOT_AVAILABLE);
			}
		}catch(EduException e){
			throw new EduException(ErrorCode.ERR_POBOX_SERVICE_NOT_AVAILABLE);
		}
		if(terminal.AppRegisterLimit <= 0){
    		terminal.AppRegisterLimit = ControlParam.getInstance().getCustomerExtendLimitDays();//默认注册激活期限
    	}
		
		IMZoneDAO zoneDAO = daoFactory.getIMZoneDAO();
		IMZone zone = new IMZone();
		zone.ZoneID = terminal.ZoneID;
		try{
			zone = zoneDAO.find(zone);//柜体未指定分拣中心，不允许注册
		}catch(EduException e){
			throw new EduException(ErrorCode.ERR_POBOX_SERVICE_NOT_AVAILABLE);
		}
        //#end
		
		//#start 设置有效期:重新激活时需要重新设置有效期，正常状态只更新POBox地址
		if(months > 0){
			if(sysDateTime.after(customer.Validity)){//已逾期
				customer.Validity = sysDateTime;
			}
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(customer.Validity);
			//calendar.setTime(new java.util.Date());
			calendar.add(Calendar.MONTH, months);
			customer.Validity = new java.sql.Timestamp(calendar.getTimeInMillis());
			
			setCols.add("keepMonths", 0);
			setCols.add("Validity", customer.Validity);
			isSendSMS = true;
		}
		//#end 
		
		//newCustomerID
		String newCustomerID = customer.CustomerID;
		if(customer.CustomerID.equals(customer.Mobile)){//未激活，CustomerID=Mobile，生成新的Customer#
			//#start 查询Locker计数器,获取序列号,更新计数器,生成个人客户号：
			int SerialNumber = updateLockerCustomerCounter(terminal.TerminalNo);
			
			if("2".equals(ControlParam.getInstance().getCustomerIDFormat())){
				//Locker.Name-SerialNumber
				newCustomerID = terminal.TerminalName+"-"+SerialNumber;
			}else{
				//Locker.Code-SerialNumber
				newCustomerID = terminal.TerminalNo+"-"+SerialNumber;
			}
			JDBCFieldArray whereCols0 = new JDBCFieldArray();
			whereCols0.add("CustomerID", newCustomerID);
			if(customerDAO.isExist(whereCols0)>0){
				throw new EduException(ErrorCode.ERR_CUSTOMEREXISTS);
			}
			//#end
		}else if(!customer.TerminalNo.equals(lockerid)){
			if("1".equals(ControlParam.getInstance().getCustomerIDMode())){//1-动态更新（随注册的柜号变化而变化
				//#start 查询Locker计数器,获取序列号,更新计数器,生成个人客户号：
				int SerialNumber = updateLockerCustomerCounter(terminal.TerminalNo);
				
				if("2".equals(ControlParam.getInstance().getCustomerIDFormat())){
					//Locker.Name-SerialNumber
					newCustomerID = terminal.TerminalName+"-"+SerialNumber;
				}else{
					//Locker.Code-SerialNumber
					newCustomerID = terminal.TerminalNo+"-"+SerialNumber;
				}
				JDBCFieldArray whereCols0 = new JDBCFieldArray();
				whereCols0.add("CustomerID", newCustomerID);
				if(customerDAO.isExist(whereCols0)>0){
					throw new EduException(ErrorCode.ERR_CUSTOMEREXISTS);
				}
				//#end
			}
		}
		
		//#start生成virual POBox address
		/**
         * Customer.Address生成规则：
         * "AZC " +AZC.Code +", "+Customer.Code
         * Locker.Address
         * (Locker.City+","+Locker.Zipcode
         * "Saudi Arabia")
         */
        StringBuffer address = new StringBuffer();
        if("3".equals(ControlParam.getInstance().getPoBoxAddressFirstLineFormat())){
        	address.append("AZC " +zone.ZoneID+"-"+terminal.TerminalNo+", "+newCustomerID+"\n");
        }else if("2".equals(ControlParam.getInstance().getPoBoxAddressFirstLineFormat())){
        	address.append("AZC " +zone.ZoneName+", "+newCustomerID+"\n");
        }else{
        	address.append("AZC " +zone.ZoneID+", "+newCustomerID+"\n");
        }
        address.append(terminal.City+", "+terminal.Zipcode+"\n");
        address.append("Saudi Arabia");
        //#end 
        
        if(!address.equals(customer.Address)){
        	customer.Address = address.toString();
        	setCols.add("Address", customer.Address);
        	isSendSMS = true;
        }
        if(!customer.CustomerID.equals(newCustomerID)){
        	setCols.add("CustomerID", newCustomerID);
        }
        
        setCols.add("Status", SysDict.CUSTOMER_STATUS_NORMAL);//正常
        setCols.add("Active", "1");//活动
        setCols.add("TerminalNo", terminal.TerminalNo);
        
        setCols.add("ActiveCode", RandUtils.generateNumber(4));
        setCols.add("LastModifyTime", sysDateTime);
        
        whereCols.add("CustomerID", customer.CustomerID);

        customerDAO.update(setCols, whereCols);
        
        //#start短信通知新的POBox地址
        if(isSendSMS
        	&&StringUtils.isNotEmpty(ApplicationConfig.getInstance().getSendShortMsg()) 
        	&&isPhoneNumber(customer.Mobile)){
        	java.sql.Date validityDate = new java.sql.Date(customer.Validity.getTime());
	        SMSManager.getInstance().sendPOBoxAddressSMS(customer.Mobile,validityDate.toString(), customer.Address);
        }
        //#end
        customer.CustomerID = newCustomerID;
		return customer;
    }
    /**
     * 获取Logo的本地文件路径
     * @param filename : logos/01.png
     * @return
     * @throws EduException
     */
    public String getCompanyLogoLocalPath(String filename)throws EduException{
    	String logoUri = "notfund.png";
    	String logoFilePath = apcfg.getFullImagesFilePath()+"/"+ filename;
        File logeFile = new File(logoFilePath);
        if(logeFile.exists()){
        	logoUri = logoFilePath;
        }
        return logoUri;
    }
    /**
     * 订单号不分大小写，统一保存为大写
     * @param code
     * @return
     */
    public  String normalizeItemCode(String code) throws EduException
    {
    	String itemcode = code.replaceAll("\\^|\"|'|:|;|<|>|@|\\*|%|!|#|\\$|,|\\?|&|~", "");
    	itemcode = itemcode.trim().toUpperCase();
        return itemcode;
    }
    
    /**
     * 取件短信、催领短信预定发送，要求如果多条短信发送到同一手机，每条短信间隔发送
     * @param smsInfo
     * @param shortMsg
     * @param sysDateTime
     * @throws EduException
     */
    public  void scheduleSendSMS (SMSInfo smsInfo,MBPwdShortMsg shortMsg, java.sql.Timestamp sysDateTime) throws EduException
    {
        //同一手机短信发送的时间间隔
        long l_timegap = NumberUtils.parseLong(ctrlParam.getSendSMSTimeGapInMs());
        if(smsInfo.ScheduledDateTime == null &&l_timegap > 0){
            
            //#start查询表中等待发送（未发送）的短信数量
            //String ItemStatus = SysDict.ITEM_STATUS_DROP_DROPPED
            //        +","+SysDict.ITEM_STATUS_DROP_D_DROPPED;//5-Dropped,6-D-Dropped，
            MBPwdShortMsgDAO shortMsgDAO = daoFactory.getMBPwdShortMsgDAO();
            JDBCFieldArray whereCols = new JDBCFieldArray();
            whereCols.add("SendStatus", "0");//未发送
            whereCols.add("CustomerMobile", smsInfo.CustomerMobile);
            //whereCols.addSQL(utilDAO.getFlagInSQL("PackageStatus", ItemStatus));
            int count = shortMsgDAO.isExist(whereCols);
            //#end
            
            //#start查询同一手机最后短信的发送时间（通过最后修改时间判断）
            JDBCFieldArray whereCols1 = new JDBCFieldArray();
            whereCols1.add("SendStatus","<>", "0");//已发送
            whereCols1.add("CustomerMobile", smsInfo.CustomerMobile);
            String sLastModifyTime = shortMsgDAO.selectFunctions("MAX(LastModifyTime)", whereCols1);
            //
            long offset = l_timegap;
            java.sql.Timestamp lastSendTime = null;
            long sysDateTimeInMs = sysDateTime.getTime();//初始默认当前时间
            if(StringUtils.isEmpty(sLastModifyTime)){
                lastSendTime = new java.sql.Timestamp((sysDateTimeInMs-l_timegap));
            }else{
                try{
                    lastSendTime = java.sql.Timestamp.valueOf(sLastModifyTime);
                    offset = (sysDateTimeInMs-lastSendTime.getTime());
                    if(offset>=l_timegap){
                        lastSendTime = new java.sql.Timestamp((sysDateTimeInMs-l_timegap));
                    }
                }catch(IllegalArgumentException e){
                    lastSendTime = new java.sql.Timestamp((sysDateTimeInMs-l_timegap));
                }
                
            }
            //#end
            long scheduledTimeInMs = lastSendTime.getTime()+l_timegap*(count);
            smsInfo.ScheduledDateTime = new java.sql.Timestamp(scheduledTimeInMs);
            
            System.out.println("++++"+Constant.dateFromat.format(sysDateTime)+":"+smsInfo.PackageID+","
                    +Constant.dateFromat.format(lastSendTime)+","+offset
                    +","+count+","+(l_timegap*(count))+","+Constant.dateFromat.format(smsInfo.ScheduledDateTime));

            SMSManager.getInstance().addSMSInfo(smsInfo, sysDateTime);
			return;
        }
        
        MBPwdShortMsgDAO shortMsgDAO = daoFactory.getMBPwdShortMsgDAO();

    	//更新发送状态
        JDBCFieldArray setCols1 = new JDBCFieldArray();
        JDBCFieldArray whereCols1 = new JDBCFieldArray();		
        setCols1.add("SendStatus", "1");//0:未发送 1:发送进行中 2:发送成功 4:发送失败
        if(SMSInfo.MSG_TYPE_EXPIRED == smsInfo.MsgType){
        	setCols1.add("PackageStatus", smsInfo.ItemStatus);
        }
        if(SMSInfo.MSG_TYPE_REMINDER == smsInfo.MsgType){
        	setCols1.add("ReSendNum", 0);
        }else if(SMSInfo.MSG_TYPE_RESENT == smsInfo.MsgType){
            setCols1.add("ReSendNum", shortMsg.ReSendNum + 1);
        }
        setCols1.add("ScheduleDateTime",shortMsg.ScheduleDateTime);
 		setCols1.add("Remark", "UMID="+smsInfo.UMID);
 		setCols1.add("LastModifyTime", sysDateTime);
 		
 		whereCols1.add("WaterID", shortMsg.WaterID);
 		
 		shortMsgDAO.update(setCols1, whereCols1);
 		
 		smsInfo.WaterID  = shortMsg.WaterID;
		//发送短信
		switch(smsInfo.MsgType){
		case SMSInfo.MSG_TYPE_DELIVERY:
		    SMSManager.getInstance().sentDeliverySMS(smsInfo);
            break;
		case SMSInfo.MSG_TYPE_TAKEDOUT:
            SMSManager.getInstance().sentPickupSMS(smsInfo);
            break;
		case SMSInfo.MSG_TYPE_RESENT:
		    if(SMSInfo.MSG_TYPE_DELIVERY == shortMsg.MsgType){
		        SMSManager.getInstance().sentDeliverySMS(smsInfo);
		    }else if(SMSInfo.MSG_TYPE_REMINDER == shortMsg.MsgType){
		        SMSManager.getInstance().sentReminderSMS(smsInfo);
		    }else{
		        SMSManager.getInstance().sentDeliverySMS(smsInfo);
		    }
			break;
		case SMSInfo.MSG_TYPE_REMINDER:
			SMSManager.getInstance().sentReminderSMS(smsInfo);
			break;
		default:
		    SMSManager.getInstance().sentDeliverySMS(smsInfo);
		    break;
		}
		
    }
    /**
     * 包裹取走以后，更新短消息状态：包括订单状态和短信发送状态
     * @param PackageID
     * @param TerminalNo
     * @param StoredTime
     * @param PackageStatus
     * @param sysDateTime
     * @throws EduException
     */
    public  void updatePwdSMSStauts (String PackageID,String TerminalNo, java.sql.Timestamp StoredTime, 
            String PackageStatus,
            java.sql.Timestamp sysDateTime) throws EduException
    {
        MBPwdShortMsgDAO shortMsgDAO = daoFactory.getMBPwdShortMsgDAO();
        MBPwdShortMsg shortMsg = new MBPwdShortMsg();
        
        JDBCFieldArray whereCols0 = new JDBCFieldArray();
        whereCols0.add("PackageID", PackageID);
        whereCols0.add("TerminalNo", TerminalNo);
        whereCols0.add("StoredTime", StoredTime);//add by zxy :MBPwdShortMsg表中相同PackageID、TerminalNo的可能有多条记录，故增加StoredTime条件
        
        RowSet rset = shortMsgDAO.select(whereCols0);
        if(RowSetUtils.rowsetNext(rset))
        {
            shortMsg.WaterID = RowSetUtils.getLongValue(rset, "WaterID");
        }
        
        if(shortMsg.WaterID <= 0){
            return;
        }
        updatePwdSMSStauts(shortMsg, PackageStatus, sysDateTime);
        
    }
    /**
     * 包裹取走以后，更新短消息状态：包括订单状态和短信发送状态
     * @param shortMsg
     * @param PackageStatus
     * @param sysDateTime
     * @throws EduException
     */
    public  void updatePwdSMSStauts (MBPwdShortMsg shortMsg, String PackageStatus,
            java.sql.Timestamp sysDateTime) throws EduException
    {
        if(shortMsg.WaterID<=0){
            return;
        }
        MBPwdShortMsgDAO shortMsgDAO = daoFactory.getMBPwdShortMsgDAO();
        //从短信发送队列中移除
        SMSInfo smsInfo = SMSManager.getInstance().getSendSMSInfo(""+shortMsg.WaterID);
        if(smsInfo!=null){
            SMSManager.getInstance().removeSMSInfo(smsInfo, sysDateTime, false);
        }
        shortMsg = shortMsgDAO.find(shortMsg);
        JDBCFieldArray setCols1 = new JDBCFieldArray();
        JDBCFieldArray whereCols1 = new JDBCFieldArray();
        setCols1.add("PackageStatus", PackageStatus);
        setCols1.add("LastModifyTime", sysDateTime);
        if("0".equals(shortMsg.SendStatus)){//短信未发送
            setCols1.add("SendStatus", "5");//0:未发送 1:发送进行中 2:发送成功 4:发送失败 5:取消发送(非在箱信息,取消发送)
        }
        whereCols1.add("WaterID", shortMsg.WaterID);
        
        shortMsgDAO.update(setCols1, whereCols1);
    }
}
