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
 * <p>Description: 用户取件 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class PTPickupPackage extends ActionBean
{

    public OutParamPTPickupPackage doBusiness(InParamPTPickupPackage in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        OutParamPTPickupPackage out = new OutParamPTPickupPackage();

		//1.	验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(in.PackageID))
			throw new EduException(ErrorCode.ERR_PARMERR);
		
		//3.	调用UtilDAO.getCurrentDateTime()获得系统日期时间。
		java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();
		java.sql.Date sysDate = DateUtils.toSQLDate(sysDateTime);
		
		//#start验证包裹单号
        PTDeliverHistoryDAO historyPackDAO = daoFactory.getPTDeliverHistoryDAO();
        
        ///////////////////////////////////////////////////////////////////////////
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
        inboxPack.TerminalNo = in.TerminalNo;

        // 有可能取件记录先传上来，投递记录后传上来
        try{
        	inboxPack = inboxPackDAO.find(inboxPack);
        	
        	//////////不是此前发生的这笔交易//////////////////////////////////////////////////////
          	//if(StringUtils.isNotEmpty(in.Remark) && !in.Remark.equalsIgnoreCase(inboxPack.TradeWaterNo)){
          	//	throw new EduException(ErrorCode.ERR_PACKAGENOTEXISTS);
          	//}
        }catch(Exception e){
        	/*if(StringUtils.isNotEmpty(in.Remark)) //同一个在箱记录
    		{
    			JDBCFieldArray whereCols01 = new JDBCFieldArray();
    			whereCols01.add("Remark", in.Remark);
            	
    			if(historyPackDAO.isExist(whereCols01) > 0)
            	{
            		out.ServerTime = sysDateTime;
            		
            		return out;
            	}else{
            		throw new EduException(ErrorCode.ERR_PACKAGENOTEXISTS);
            	}
    		}
        	else*/
        	{
    			throw new EduException(ErrorCode.ERR_PACKAGENOTEXISTS);
    		}
        }
        //#end
        

        //从在箱信息里面删除
        inboxPackDAO.delete(inboxPack);
        
        if(in.OccurTime.before(inboxPack.StoredTime)){
            in.OccurTime = sysDateTime;
        }
        //先删除，保证数据不重复??  --预先插入历史订单表？
        JDBCFieldArray whereCols1 = new JDBCFieldArray();
        whereCols1.add("PackageID", inboxPack.PackageID);
        whereCols1.add("TerminalNo", inboxPack.TerminalNo);
        whereCols1.add("StoredTime", inboxPack.StoredTime);

        historyPackDAO.delete(whereCols1);
        
        //插入历史记录
        PTDeliverHistory historyPack = new PTDeliverHistory();

        historyPack.PackageID = inboxPack.PackageID;
        historyPack.CreateTime = inboxPack.CreateTime;
        historyPack.ItemStatus = SysDict.ITEM_STATUS_DROP_TAKEOUT;
        historyPack.RunStatus  = SysDict.ITEM_RUN_STATUS_TAKEOUT_CUSOTMER;
        
        historyPack.PPCID = inboxPack.PPCID;
        historyPack.ZoneID = inboxPack.ZoneID;
        historyPack.CompanyID = inboxPack.CompanyID;
        historyPack.RefNo = inboxPack.RefNo;
        
        historyPack.CustomerID = inboxPack.CustomerID;
        historyPack.CustomerMobile = inboxPack.CustomerMobile;
        historyPack.CustomerName = inboxPack.CustomerName;
        historyPack.CustomerAddress = inboxPack.CustomerAddress;
        
        historyPack.TerminalNo = in.TerminalNo;
        historyPack.BoxNo = inboxPack.BoxNo;
        historyPack.DropAgentID = inboxPack.DropAgentID;
        historyPack.DynamicCode = in.DynamicCode;
        
        historyPack.StoredTime = inboxPack.StoredTime;
        historyPack.StoredDate = inboxPack.StoredDate;
        historyPack.ExpiredTime = inboxPack.ExpiredTime;
        historyPack.OpenBoxKey = inboxPack.OpenBoxKey;
        
        historyPack.TakedTime = in.OccurTime;
        historyPack.ReturnAgentID = "";//in.PostmanID
        historyPack.DADFlag = inboxPack.DADFlag;
        historyPack.TradeWaterNo = in.TradeWaterNo;
        historyPack.PosPayFlag = inboxPack.PosPayFlag;
        historyPack.UploadFlag = inboxPack.UploadFlag;
        historyPack.DropNum = inboxPack.DropNum;
        historyPack.ReportOrderID = inboxPack.ReportOrderID;
        historyPack.PrintedFlag   = inboxPack.PrintedFlag;
        historyPack.ParcelSize    = inboxPack.ParcelSize;
        historyPack.LastModifyTime = sysDateTime;
        historyPack.Remark = inboxPack.Remark;
        
        historyPackDAO.insert(historyPack);
        
        //"Takeout"
        String[] itemDesc= {historyPack.TerminalNo, historyPack.CustomerMobile};
        
        //#start 添加投递订单周期记录。
        PTItemLifeCycle itemLifeCycle = new PTItemLifeCycle();
        itemLifeCycle.PackageID      = historyPack.PackageID;
        itemLifeCycle.ItemStatus     = historyPack.ItemStatus;
        itemLifeCycle.OperatorID     = historyPack.CustomerMobile;
        itemLifeCycle.OperatorType   = SysDict.OPER_TYPE_CUSTOMER;
        itemLifeCycle.LastModifyTime = sysDateTime;
        itemLifeCycle.ZoneID = inboxPack.ZoneID;
        itemLifeCycle.LastStatus = inboxPack.ItemStatus;
        itemLifeCycle.StatusTime = ((int)((sysDateTime.getTime() - inboxPack.LastModifyTime.getTime()) / 60000L));
        commonDAO.addItemLifeCycle(Constant.ACTION_CODE_PICKUPBYCUSTOMER, itemLifeCycle, itemDesc);
        //#end
        
        //#start 订单数据加入发送队列进行发送(状态更新)
    	commonDAO.addItemStatusUpdate(historyPack.PPCID, historyPack.ItemStatus, historyPack, SysDict.TRANSFER_TYPE_TAKEOUT_BY_CUSTOMER);
        //#end
    	
        out.ServerTime = sysDateTime;
        
		//#start修改短信里面的包裹状态
		commonDAO.updatePwdSMSStauts(inboxPack.PackageID, inboxPack.TerminalNo, inboxPack.StoredTime, historyPack.ItemStatus, sysDateTime);
        //#end
		
		// ////////////////////////////////////////////////////////////////////////
        //#start 生成取件短信，并提交发送
        // 发送短信
        if (StringUtils.isNotEmpty(ApplicationConfig.getInstance().getSendShortMsg())) {
            // ///////////////////////////////////////
            modifySMSStat(in.TerminalNo);

            // ///////////////////////////////////////
            TBTerminalDAO terminalDAO = daoFactory.getTBTerminalDAO();
            TBTerminal terminal = new TBTerminal();
            terminal.TerminalNo = in.TerminalNo;
            terminal = terminalDAO.find(terminal);
            
            ///////////////
            SMSInfo smsInfo = new SMSInfo();
            smsInfo.PackageID = in.PackageID;
            smsInfo.TerminalNo = in.TerminalNo;
            smsInfo.TerminalName = terminal.TerminalName;
            smsInfo.TakeoutDateTime = in.OccurTime;
            smsInfo.Location = terminal.Location;
            //
            smsInfo.sysDateTime = sysDateTime;
            smsInfo.CustomerMobile = inboxPack.CustomerMobile;
            smsInfo.OfBureau = "";
            smsInfo.BoxNo = in.BoxNo;
            smsInfo.TerminalName = terminal.TerminalName;
            smsInfo.TakeoutDateTime = in.OccurTime;           
            smsInfo.TerminalType = terminal.TerminalType;
            smsInfo.ZoneID   = inboxPack.ZoneID;
            smsInfo.PostmanID = in.PostmanID;
            smsInfo.CompanyName = "";
            smsInfo.ItemStatus = inboxPack.ItemStatus;
            smsInfo.MsgType = SMSInfo.MSG_TYPE_TAKEDOUT;
            smsInfo.Latitude = terminal.Latitude;
            smsInfo.Longitude = terminal.Longitude;
            
            smsInfo.CompanyID = inboxPack.CompanyID;
            /*IMCompanyDAO companyDAO = daoFactory.getIMCompanyDAO();
            IMCompany company = new IMCompany();
            company.CompanyID = inboxPack.CompanyID;
            try {
                company = companyDAO.find(company);
            } catch (Exception e) {
            }*/
            
            /*smsInfo.CompanyName = company.CompanyName;
            smsInfo.TrailerMsg = company.SMS_Notification;
            smsInfo.expireddays = company.ExpiredDays;*/
            
            SMSManager.getInstance().sentPickupSMS(smsInfo);
        }
        //#end
		
        ///////////////////////////////////////////////////////////
		//反馈
		feedback(historyPack);
		
        return out;
    }
    
    private void modifySMSStat(String TerminalNo) throws EduException {
        MBSmsStatDAO smsStatDAO = daoFactory.getMBSmsStatDAO();
        MBSmsStat smsStat = new MBSmsStat();
        smsStat.TerminalNo = TerminalNo;
        smsStat.OccurYear = DateUtils.currentYear();
        smsStat.OccurMonth = DateUtils.currentMonth();

        if (smsStatDAO.isExist(smsStat)) {
            JDBCFieldArray setCols = new JDBCFieldArray();
            JDBCFieldArray whereCols = new JDBCFieldArray();

            setCols.addSQL(" TotalNum=TotalNum+1 ");
            setCols.addSQL(" PickupNum=PickupNum+1 ");
            
            whereCols.add("TerminalNo", TerminalNo);
            whereCols.add("OccurYear", smsStat.OccurYear);
            whereCols.add("OccurMonth", smsStat.OccurMonth);

            smsStatDAO.update(setCols, whereCols);
        } else {
            smsStat.TotalNum = 1;
            smsStat.PwdNum = 0;
            smsStat.ReminderNum = 0;
            smsStat.ExpireNum = 0;
            smsStat.DynamicNum = 0;
            smsStat.PickupNum = 1;
            smsStat.OtherNum = 0;

            smsStatDAO.insert(smsStat);
        }
    }
    private void feedback(PTDeliverHistory historyPack){
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
        	String tacktype = "PickUp by customer";
        	String itemcode = historyPack.PackageID;
        	String storedtime = historyPack.StoredTime.toString();
        	String expiredtime = "";//historyPack.ExpiredTime.toString();
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
