package com.dcdzsoft.business.pt;

import javax.sql.RowSet;

import com.dcdzsoft.EduException;
import com.dcdzsoft.sda.db.*;
import com.dcdzsoft.sequence.SequenceGenerator;
import com.dcdzsoft.sms.SMSInfo;
import com.dcdzsoft.sms.SMSManager;
import com.dcdzsoft.util.*;
import com.dcdzsoft.dto.function.*;
import com.dcdzsoft.dto.business.*;
import com.dcdzsoft.dao.*;
import com.dcdzsoft.dao.common.*;
import com.dcdzsoft.email.EmailSenderManager;
import com.dcdzsoft.config.ApplicationConfig;
import com.dcdzsoft.constant.*;
import com.dcdzsoft.business.ActionBean;

/**
 * <p>Title: 自提柜后台运营系统</p>
 * <p>Description: 取回逾期包裹 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class PTWithdrawExpiredPack extends ActionBean
{

    public OutParamPTWithdrawExpiredPack doBusiness(InParamPTWithdrawExpiredPack in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        OutParamPTWithdrawExpiredPack out = new OutParamPTWithdrawExpiredPack();

		//1.	验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(in.TerminalNo)
			||StringUtils.isEmpty(in.PostmanID)
			||StringUtils.isEmpty(in.PackageID))
			throw new EduException(ErrorCode.ERR_PARMERR);

		//2.	调用UtilDAO.getCurrentDateTime()获得系统日期时间。
        java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();
        java.sql.Date sysDate = DateUtils.toSQLDate(sysDateTime);
        
        //验证投递员是否存在
        PMPostmanDAO postmanDAO = daoFactory.getPMPostmanDAO();
        PMPostman postman = new PMPostman();
        postman.PostmanID = in.PostmanID;
        try{
            postman = postmanDAO.find(postman);
        } catch (Exception e) {
            throw new EduException(ErrorCode.ERR_POSTMANNOTEXISTS);
        }
        
        //验证回收权限
        if(!"1".equals(postman.ReturnRight)){
        	throw new EduException(ErrorCode.ERR_FORBIDPOSTMANTAKEOUT);
    	}
        // 验证柜体权限(登陆时候验证)
        //commonDAO.checkManTerminalRight(postman, in.TerminalNo);
        
        //#start 验证包裹单号
        PTDeliverHistoryDAO historyPackDAO = daoFactory.getPTDeliverHistoryDAO();
        
		///////////////////////////////////////////////////////
		JDBCFieldArray whereCols0 = new JDBCFieldArray();
		whereCols0.add("PackageID", in.PackageID);
		whereCols0.add("TerminalNo", in.TerminalNo);
		whereCols0.add("TradeWaterNo", in.TradeWaterNo);
		
		if(historyPackDAO.isExist(whereCols0) == 1) //同一笔交易
		{
			out.ServerTime = sysDateTime;
		
			return out;
		}
		if(in.OccurTime == null){
            in.OccurTime = sysDateTime;
        }
		
        PTInBoxPackageDAO inboxPackDAO = daoFactory.getPTInBoxPackageDAO();
        PTInBoxPackage inboxPack = new PTInBoxPackage();

        inboxPack.PackageID = in.PackageID;
        //inboxPack.TerminalNo = in.TerminalNo;

        try{
        	inboxPack = inboxPackDAO.find(inboxPack);
        	if(!inboxPack.TerminalNo.equals(in.TerminalNo)){
        	    throw new EduException(ErrorCode.ERR_PACKAGENOTEXISTS);
        	}
        	//////////不是此前发生的这笔交易//////////////////////////////////////////////////////
          	//if(StringUtils.isNotEmpty(in.Remark) && !in.Remark.equalsIgnoreCase(inboxPack.TradeWaterNo)){
          	//	throw new EduException(ErrorCode.ERR_PACKAGENOTEXISTS);
          	//}
        }catch(Exception e){
        	throw new EduException(ErrorCode.ERR_PACKAGENOTEXISTS);
        }
        
        //验证包裹状态逾期
        if(!SysDict.ITEM_STATUS_DROP_EXPIRED.equalsIgnoreCase(inboxPack.ItemStatus)
        	&& !SysDict.ITEM_STATUS_DROP_M_EXPIRED.equalsIgnoreCase(inboxPack.ItemStatus)){
        	throw new EduException(ErrorCode.ERR_FORBIDPOSTMANTAKEOUT);
        }
        //#end
        
        //#start 从在箱信息里面删除,插入历史记录
        inboxPackDAO.delete(inboxPack);
        if(in.OccurTime.before(inboxPack.StoredTime)){
            in.OccurTime = sysDateTime;
        }
        //先删除，保证数据不重复??  --预先插入历史订单表？
        JDBCFieldArray whereCols1 = new JDBCFieldArray();
        whereCols1.add("PackageID", inboxPack.PackageID);
        whereCols1.add("TerminalNo", inboxPack.TerminalNo);
        whereCols1.add("CreateTime", inboxPack.CreateTime);

        historyPackDAO.delete(whereCols1);
        
        //插入历史记录
        PTDeliverHistory historyPack = new PTDeliverHistory();
        historyPack.PackageID = in.PackageID;
        historyPack.CreateTime = inboxPack.CreateTime;
        
        if(SysDict.DIRECT_DROP_FLAG_YES.equals(inboxPack.DADFlag)){
            //直投订单：如果是直投投递员取走
            switch(postman.PostmanType){
            case SysDict.POSTMAN_TYPE_DAD://"82"
                historyPack.ItemStatus = SysDict.ITEM_STATUS_DROP_TAKEOUT;
                historyPack.RunStatus  = SysDict.ITEM_RUN_STATUS_TAKEOUT_DADBACK;
                break;
            default:
                historyPack.ItemStatus = SysDict.ITEM_STATUS_DROP_INTRANSIT_BACK;
                historyPack.RunStatus  = SysDict.ITEM_RUN_STATUS_RUNNING;
                break;
            }
        }else{
        	historyPack.ItemStatus = SysDict.ITEM_STATUS_DROP_INTRANSIT_BACK;
        	historyPack.RunStatus  = SysDict.ITEM_RUN_STATUS_RUNNING;
        }
        
       
        historyPack.PPCID     = inboxPack.PPCID;
        historyPack.ZoneID    = inboxPack.ZoneID;
        historyPack.CompanyID = inboxPack.CompanyID;
        historyPack.RefNo     = inboxPack.RefNo;
        
        historyPack.CustomerID      = inboxPack.CustomerID;
        historyPack.CustomerMobile  = inboxPack.CustomerMobile;
        historyPack.CustomerName    = inboxPack.CustomerName;
        historyPack.CustomerAddress = inboxPack.CustomerAddress;
        
        historyPack.TerminalNo  = in.TerminalNo;
        historyPack.BoxNo       = in.BoxNo;
        historyPack.DropAgentID = inboxPack.DropAgentID;
        historyPack.DynamicCode = in.DynamicCode;
        
        historyPack.StoredTime = inboxPack.StoredTime;
        historyPack.StoredDate = inboxPack.StoredDate;
        historyPack.ExpiredTime = inboxPack.ExpiredTime;
        historyPack.OpenBoxKey = inboxPack.OpenBoxKey;
        
        historyPack.TakedTime = in.OccurTime;
        historyPack.ReturnAgentID = in.PostmanID;
        historyPack.DADFlag = inboxPack.DADFlag;
        historyPack.TradeWaterNo = in.TradeWaterNo;
        historyPack.PosPayFlag = inboxPack.PosPayFlag;
        historyPack.UploadFlag = inboxPack.UploadFlag;
        historyPack.DropNum = inboxPack.DropNum;
        historyPack.ReportOrderID = inboxPack.ReportOrderID;
        historyPack.PrintedFlag   =  inboxPack.PrintedFlag;
        historyPack.ParcelSize    = inboxPack.ParcelSize;
        historyPack.LastModifyTime = sysDateTime;
        historyPack.Remark = inboxPack.Remark;
        
        historyPackDAO.insert(historyPack);
        //#end

        out.ServerTime = sysDateTime;
        
        //"Return back("+inboxPack.TerminalNo+"-"+inboxPack.BoxNo+")"
        String[] itemDesc= {inboxPack.TerminalNo,inboxPack.BoxNo};
        
        //#start记录订单状态更新信息
        PTItemLifeCycleDAO itemLifeCycleDAO = daoFactory.getPTItemLifeCycleDAO();
        PTItemLifeCycle itemLifeCycle = new PTItemLifeCycle();
        itemLifeCycle.PackageID  = in.PackageID;
        itemLifeCycle.ItemStatus = historyPack.ItemStatus;
        itemLifeCycle.OperatorID    = in.PostmanID;
        itemLifeCycle.OperatorType  = postman.PostmanType;
        itemLifeCycle.LastModifyTime = sysDateTime;
        itemLifeCycle.ZoneID = inboxPack.ZoneID;
        itemLifeCycle.LastStatus = inboxPack.ItemStatus;
        itemLifeCycle.StatusTime = ((int)((sysDateTime.getTime() - inboxPack.LastModifyTime.getTime()) / 60000L));
        commonDAO.addItemLifeCycle(Constant.ACTION_CODE_PICKUPBYPOSTMAN, itemLifeCycle, itemDesc);
        //#end
        
        //#start 订单数据加入发送队列进行发送(状态更新)
        String transferType = "";
    	if(SysDict.ITEM_RUN_STATUS_TAKEOUT_DADBACK.equals(historyPack.RunStatus)){
    		transferType = SysDict.TRANSFER_TYPE_TAKEOUT_BY_DAD;
    	}
    	commonDAO.addItemStatusUpdate(historyPack.PPCID, historyPack.ItemStatus, historyPack, transferType);
        //#end
    	
        
        //#start修改短信里面的包裹状态
        commonDAO.updatePwdSMSStauts(inboxPack.PackageID, inboxPack.TerminalNo, inboxPack.StoredTime, historyPack.ItemStatus, sysDateTime);
        //#end
        
        //#start 发送逾期短信/////////////////////////////////////////////////////
    	//发送短信
        if(StringUtils.isNotEmpty(ApplicationConfig.getInstance().getSendShortMsg())
        		&& "1".equals(ControlParam.getInstance().getSendReturnSMS()))
        {
            //////////////////////////////////////////
            modifySMSStat(inboxPack.TerminalNo);
            
            //////////////////////////////////////////////////////////////
            TBTerminalDAO terminalDAO = daoFactory.getTBTerminalDAO();
        	TBTerminal terminal = new TBTerminal();
        	terminal.TerminalNo = in.TerminalNo;
        	
        	terminal = terminalDAO.find(terminal);
        	
    		/////////////////////////////////////////////
    		
    		SMSInfo smsInfo = new SMSInfo();
        	smsInfo.PackageID = in.PackageID;
        	smsInfo.TerminalNo = in.TerminalNo;
        	smsInfo.StoredTime = historyPack.StoredTime;
        	smsInfo.sysDateTime = sysDateTime;
        	smsInfo.CustomerMobile = historyPack.CustomerMobile;
        	smsInfo.BoxNo = in.BoxNo;
        	smsInfo.ItemStatus = historyPack.ItemStatus;
        	smsInfo.OpenBoxKey = "";
        	smsInfo.TerminalName = terminal.TerminalName;
        	smsInfo.OfBureau = "";
        	smsInfo.Location = terminal.Location;
        	smsInfo.TerminalType = terminal.TerminalType;
        	smsInfo.ZoneID = historyPack.ZoneID;
        	smsInfo.PostmanID = historyPack.ReturnAgentID;
    		smsInfo.CompanyID = historyPack.CompanyID;
        	smsInfo.MsgType = SMSInfo.MSG_TYPE_EXPIRED;
        	
        	long timeInMS = historyPack.ExpiredTime.getTime()-historyPack.StoredTime.getTime();
        	timeInMS = timeInMS/(60*60*24);
        	if(timeInMS==0){
        		timeInMS=1;
        	}
        	smsInfo.expireddays = (int)(timeInMS);//commonDAO.getExpiredDays(inboxPack.CompanyID);
        	
    		
        	SMSManager.getInstance().sentExpiredSMS(smsInfo);
        }
        //#end
        if(SysDict.DIRECT_DROP_FLAG_YES.equals(inboxPack.DADFlag)){
        	//反馈
    		feedback(historyPack, postman.PostmanID, postman.PostmanName);
        }
        
        return out;
    }
    private void modifySMSStat(String TerminalNo) throws EduException
    {
    	MBSmsStatDAO smsStatDAO = daoFactory.getMBSmsStatDAO();
    	MBSmsStat smsStat = new MBSmsStat();
    	smsStat.TerminalNo = TerminalNo;
    	smsStat.OccurYear = DateUtils.currentYear();
    	smsStat.OccurMonth = DateUtils.currentMonth();
    	
    	if(smsStatDAO.isExist(smsStat))
    	{
    		JDBCFieldArray setCols = new JDBCFieldArray();
    		JDBCFieldArray whereCols = new JDBCFieldArray();
    		
    		setCols.addSQL(" TotalNum=TotalNum+1 ");
    		setCols.addSQL(" ExpireNum=ExpireNum+1 ");
    		whereCols.add("TerminalNo", TerminalNo);
    		whereCols.add("OccurYear", smsStat.OccurYear);
    		whereCols.add("OccurMonth", smsStat.OccurMonth);
    		
    		smsStatDAO.update(setCols, whereCols);
    	}else
    	{
    		smsStat.TotalNum = 1;
    		smsStat.PwdNum = 0;
    		smsStat.ExpireNum = 1;
    		smsStat.ReminderNum = 0;
    		smsStat.DynamicNum = 0;
    		smsStat.PickupNum = 0;
    		smsStat.OtherNum = 0;
    		
    		smsStatDAO.insert(smsStat);
    	}
    }
    private void feedback(PTDeliverHistory historyPack,String postmanID, String postmanName){
    	IMCompanyDAO companyDAO = daoFactory.getIMCompanyDAO();
		IMCompany company = new IMCompany();
		company.CompanyID = historyPack.CompanyID;
		try{
			company = companyDAO.find(company);
		}catch(EduException e){
			return;
		}
		if(!"1".equals(company.Feedback)
				||StringUtils.isEmpty(company.Email)){
			return;
		}
		
    	String uploadType = Constant.TERMINAL_UPLADETYPE_PICKUPPIC;
		String newfileName = historyPack.TradeWaterNo + ".jpg";
		String picPath = historyPack.TerminalNo + "/"  + uploadType + "/" +newfileName;
		//send email
        if(StringUtils.isNotEmpty(ApplicationConfig.getInstance().getEmailAddress())){
        	String tacktype = "Return back by "+postmanName+"(ID:"+postmanID+")";
        	String itemcode = historyPack.PackageID;
        	String storedtime = Constant.dateFromat.format(historyPack.StoredTime);
        	String expiredtime = Constant.dateFromat.format(historyPack.ExpiredTime);
        	String takedtime = historyPack.TakedTime.toString();
        	String takedImgUri = picPath;
        	String email = company.Email;
        	try{
        		EmailSenderManager sender = EmailSenderManager.getInstance();
        		sender.sendFeedback(tacktype, itemcode, storedtime, expiredtime, takedtime, takedImgUri, email);
        	}catch(EduException e){
    			return;
    		}
        }
    }
}
