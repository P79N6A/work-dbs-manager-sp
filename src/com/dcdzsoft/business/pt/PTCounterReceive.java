package com.dcdzsoft.business.pt;

import javax.sql.RowSet;

import com.dcdzsoft.EduException;
import com.dcdzsoft.sda.db.*;
import com.dcdzsoft.sda.security.SecurityUtils;
import com.dcdzsoft.sequence.SequenceGenerator;
import com.dcdzsoft.sms.SMSInfo;
import com.dcdzsoft.util.*;
import com.dcdzsoft.util.LockUtil.MyLock;
import com.dcdzsoft.dto.function.*;
import com.dcdzsoft.dto.business.*;
import com.dcdzsoft.dao.*;
import com.dcdzsoft.dao.common.*;
import com.dcdzsoft.config.ApplicationConfig;
import com.dcdzsoft.constant.*;
import com.dcdzsoft.business.ActionBean;

/**
 * <p>Title: 智能自助包裹柜系统</p>
 * <p>Description: 投递柜台接收包裹 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author 王中立
 * @version 1.0
 */

public class PTCounterReceive extends ActionBean
{

    public int doBusiness(InParamPTCounterReceive in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        int result = 0;

		//1.验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(in.OperID)
			||StringUtils.isEmpty(in.PackageID)
			||StringUtils.isEmpty(in.ItemStatus))
			throw new EduException(ErrorCode.ERR_PARMERR);


		//2.调用CommonDAO.isOnline(管理员编号)判断管理员是否在线。
		OPOperOnline operOnline = commonDAO.isOnline(in.OperID);


		//3.调用UtilDAO.getCurrentDateTime()获得系统日期时间。
		java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();
		java.sql.Date sysDate = DateUtils.toSQLDate(sysDateTime);
		//回滚已取件的订单状态

		//4.解析订单号。
        String[][] itemsCodeAndTime = commonDAO.decodeBatchPackageID(in.PackageID);
        for(String[] itemCodeAndTime:itemsCodeAndTime){
			String itemCode = itemCodeAndTime[0];
			if(StringUtils.isEmpty(itemCode)){
				continue;
			}
			//#start 待投递订单检查
			PTReadyPackageDAO readyPackDAO = daoFactory.getPTReadyPackageDAO();
			PTReadyPackage readyPack = new PTReadyPackage();
			readyPack.PackageID   = itemCode;
			try{
				readyPack = readyPackDAO.find(readyPack);
			}catch(EduException e){
				throw new EduException(ErrorCode.ERR_PACKAGENOTEXISTS);//ERR_PTREADYPACKAGENODATA
			}
			in.CustomerMobile = readyPack.CustomerMobile;
			in.TerminalNo = readyPack.TerminalNo;
			in.DropAgentID = readyPack.DropAgentID;
			
			//#start 判断是否已经投递过
	        PTInBoxPackageDAO inboxPackDAO = daoFactory.getPTInBoxPackageDAO();
			JDBCFieldArray whereCols10 = new JDBCFieldArray();
			whereCols10.add("PackageID", itemCode);
			whereCols10.add("TerminalNo", in.TerminalNo);

			if (inboxPackDAO.isExist(whereCols10) > 0)
			// throw new EduException(ErrorCode.ERR_PACKHAVEDELIVERYD);
			{	
				PTInBoxPackage inboxPack = new PTInBoxPackage();
				inboxPack.PackageID = itemCode;
				inboxPack.TerminalNo = in.TerminalNo;
				inboxPack = inboxPackDAO.find(inboxPack);
				
				////////////////////////等待下一个交易重新排队发送(强制排队)/////////////////////
				if(!inboxPack.TradeWaterNo.equalsIgnoreCase(in.TradeWaterNo)){
					System.out.println("包裹已经做过投递");
					continue;
				}				
			}
			//#start 生成开箱密码
			String pwd = generatePwd();

			if (StringUtils.isEmpty(in.OpenBoxKey)) {
				in.OpenBoxKey = SecurityUtils.md5(pwd);
			}

			// 判断密码是否重复,同一个柜体开箱密码不重复
			JDBCFieldArray whereCols0 = new JDBCFieldArray();
			whereCols0.add("OpenBoxKey", in.OpenBoxKey);
			whereCols0.add("TerminalNo", in.TerminalNo);

			if (inboxPackDAO.isExist(whereCols0) > 0) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
				}
				
				pwd = generatePwd();

				in.OpenBoxKey = SecurityUtils.md5(pwd);

				JDBCFieldArray whereCols00 = new JDBCFieldArray();
				whereCols00.add("OpenBoxKey", in.OpenBoxKey);
				whereCols00.add("TerminalNo", in.TerminalNo);

				if (inboxPackDAO.isExist(whereCols00) > 0) {
					throw new EduException(ErrorCode.ERR_OPENBOXKEYEXISTS);
				}
			}
			//#end
			//验证投递员
			PMPostmanDAO postmanDAO = daoFactory.getPMPostmanDAO();
			PMPostman postman = new PMPostman();
			postman.PostmanID = in.DropAgentID;
			try {
				postman = postmanDAO.find(postman);
			} catch (Exception e) {
				throw new EduException(ErrorCode.ERR_POSTMANNOTEXISTS);
			}
			IMZoneDAO zoneDAO = daoFactory.getIMZoneDAO();
	        IMZone zone = new IMZone();
	        zone.ZoneID = postman.ZoneID;
	        try {
	        	zone = zoneDAO.find(zone);
	        } catch (Exception e) {
	            throw new EduException(ErrorCode.ERR_FORBIDOPERATORLOCK);
	        }
			//#start 插入在箱信息， 删除待投递记录
			PTInBoxPackage inboxPack = new PTInBoxPackage();
			switch(postman.PostmanType){
	        case SysDict.POSTMAN_TYPE_DAD://"82"
	        case SysDict.POSTMAN_TYPE_DAD_BUSINESSPARTNER://"83"    
	            //Direct Drop
	            inboxPack.ItemStatus      = SysDict.ITEM_STATUS_DROP_D_DROPPED;
	            if(SysDict.POSTMAN_TYPE_DAD.equals(postman.PostmanType)){
	                in.DADFlag = "1";//直投，回收订单不进入Pane3，回收以后可以直接投递
	            }else{
	                in.DADFlag = "0";//非直投，回收以后订单进入Pane3
	            }
	            break;
	        default:
	            inboxPack.ItemStatus = SysDict.ITEM_STATUS_DROP_DROPPED;
	            in.DADFlag = "0";//非直投，回收以后订单进入Pane3
	            break;
	        }
			inboxPack.PackageID    = itemCode;
			inboxPack.TerminalNo   = in.TerminalNo;
			inboxPack.DropAgentID  = in.DropAgentID;
			inboxPack.DynamicCode  = in.DynamicCode;
			inboxPack.StoredTime   = sysDateTime;
			inboxPack.StoredDate   = sysDate;
			inboxPack.OpenBoxKey   = in.OpenBoxKey;
			inboxPack.DADFlag      = in.DADFlag;
			inboxPack.TradeWaterNo = in.TradeWaterNo;
			inboxPack.PosPayFlag     = in.PosPayFlag;
			inboxPack.UploadFlag     = SysDict.UPLOAD_FLAG_NO;
			inboxPack.ParcelStatus   = SysDict.PACKAGE_STATUS_NORMAL;
			inboxPack.LastModifyTime = sysDateTime;
			
			//获取超期时间、催领时间
	        in.ExpiredTime = commonDAO.getExpiredTime(readyPack.CompanyID);
	        java.sql.Timestamp reminderDateTime = commonDAO.getReminderTime(readyPack.CompanyID, true);//第一次获取催领时间，催领间隔+1
	        
			inboxPack.ExpiredTime = in.ExpiredTime;
			inboxPack.ReminderDateTime = reminderDateTime;
			inboxPack.BoxNo           = in.BoxNo;
			inboxPack.CreateTime      = readyPack.CreateTime;
			inboxPack.ZoneID          = zone.ZoneID;//readyPack.ZoneID;
			inboxPack.CompanyID       = zone.CompanyID;//readyPack.CompanyID;
			inboxPack.PPCID           = readyPack.PPCID;
			inboxPack.RefNo           = readyPack.RefNo;
			inboxPack.ParcelSize      = readyPack.BoxType;//包裹尺寸，与箱类型一致
			inboxPack.CustomerID      = readyPack.CustomerID;
			inboxPack.CustomerMobile  = in.CustomerMobile;
			inboxPack.CustomerName    = readyPack.CustomerName;
			inboxPack.CustomerAddress = readyPack.CustomerAddress;
			inboxPack.DropNum         = readyPack.DropNum+1;
			inboxPack.PrintedFlag     = readyPack.PrintedFlag;
			inboxPack.ReportOrderID   = readyPack.ReportOrderID;
			inboxPack.Remark          = readyPack.Remark;
			inboxPackDAO.insert(inboxPack);
			
			// 删除待投递记录
			readyPackDAO.delete(readyPack);	
			//#end
			
			//DAD-B
			if(SysDict.POSTMAN_TYPE_DAD_BUSINESSPARTNER.equals(postman.PostmanType)){
				DMCollectionParcelDAO collectionParcelDAO = daoFactory.getDMCollectionParcelDAO();
				JDBCFieldArray whereCols00 = new JDBCFieldArray();
				whereCols00.add("PackageID", itemCode);
				//whereCols00.add("TerminalNo", in.TerminalNo);//只能在订单创建时选择的客户收件柜体进行投递
				//whereCols00.add("ReturnFlag","<>", "1");
				//whereCols00.add("ItemStatus", SysDict.ITEM_STATUS_COLLECT_CREATED);
				//String ItemStatus = SysDict.ITEM_STATUS_COLLECT_CREATED;//+","//50
				//whereCols00.addSQL(utilDAO.getFlagInSQL("ItemStatus", ItemStatus));
				
				//java.util.List<String> orderByField = new java.util.LinkedList<String>();
		        //orderByField.add("CreateTime DESC");
				
				RowSet rset = collectionParcelDAO.select(whereCols00);
		        if(RowSetUtils.rowsetNext(rset)){
		        	DMCollectionParcel collectionParcel = new DMCollectionParcel();
		        	collectionParcel.PackageID  = itemCode;
		        	collectionParcel.CreateTime = RowSetUtils.getTimestampValue(rset, "CreateTime");
		        	collectionParcel = collectionParcelDAO.find(collectionParcel);
		        	
		        	//投递成功，生成消费记录
		        	commonDAO.doBusinessPartnerPay(collectionParcel.TradeWaterNo, collectionParcel.BPartnerID, collectionParcel.PackageID);
					DMHistoryItemDAO historyItemDAO = daoFactory.getDMHistoryItemDAO();
		 	    	
			        //#start 先删除，保证数据不重复
			        JDBCFieldArray whereCols3 = new JDBCFieldArray();
			        whereCols3.add("PackageID", collectionParcel.PackageID);
			        whereCols3.add("CreateTime", collectionParcel.CreateTime);
			        
			        historyItemDAO.delete(whereCols3);
			        //#end
			        
			        //#start 插入历史记录
			        String nextStatus = SysDict.ITEM_STATUS_COLLECT_TOBE_D_DEOPPED;
			        DMHistoryItem historyItem = new DMHistoryItem();
			        historyItem.CreateTime = collectionParcel.CreateTime;
			        historyItem.PackageID  = collectionParcel.PackageID;
			        historyItem.BPartnerID = collectionParcel.BPartnerID;
			        historyItem.ItemStatus = nextStatus;//collectionParcel.ItemStatus
			        historyItem.ServiceID = collectionParcel.ServiceID;
			        historyItem.ServiceAmt = collectionParcel.ServiceAmt;
			        historyItem.CollectionFlag = collectionParcel.CollectionFlag;
			        historyItem.CollectionAmt = collectionParcel.CollectionAmt;
			        historyItem.ReturnFlag = collectionParcel.ReturnFlag;
			        historyItem.ZoneID = collectionParcel.ZoneID;
			        historyItem.CompanyID = collectionParcel.CompanyID;
			        historyItem.TradeWaterNo = collectionParcel.TradeWaterNo;
			        historyItem.CustomerAddress = collectionParcel.CustomerAddress;
			        historyItem.CustomerName = collectionParcel.CustomerName;
			        historyItem.CustomerMobile = collectionParcel.CustomerMobile;
			        historyItem.CustomerID = collectionParcel.CustomerID;
			        historyItem.TerminalNo = collectionParcel.TerminalNo;
			        historyItem.BoxNo = collectionParcel.BoxNo;
			        historyItem.ParcelSize = collectionParcel.ParcelSize;
			        historyItem.CollectZoneID = collectionParcel.CollectZoneID;
			        historyItem.CollectionType = collectionParcel.CollectionType;
			        historyItem.CollectionAgentID = collectionParcel.CollectionAgentID;
			        historyItem.CollectionType = collectionParcel.CollectionType;
			        historyItem.CollectionTime = collectionParcel.CollectionTime;
			        historyItem.PPCID = collectionParcel.PPCID;
			        historyItem.PrintedFlag = collectionParcel.PrintedFlag;
			        historyItem.ReportOrderID = "";
			        historyItem.LastModifyTime = sysDateTime;
			        
			        historyItemDAO.insert(historyItem);
			        
			        collectionParcelDAO.delete(collectionParcel);
			        //#end
			        
			        //#start 记录订单状态更新信息
			        DMItemLifeCycle itemLifeCycle = new DMItemLifeCycle();
		            itemLifeCycle.PackageID  = historyItem.PackageID;
		            itemLifeCycle.ItemStatus = nextStatus;
		            itemLifeCycle.OperatorID    = postman.PostmanID;
		            itemLifeCycle.OperatorType  = postman.PostmanType;
		            itemLifeCycle.LastModifyTime = sysDateTime;
		            itemLifeCycle.Remark = "B-Dropped";
	                itemLifeCycle.ZoneID = readyPack.ZoneID;
	                itemLifeCycle.LastStatus = readyPack.ItemStatus;
	                if(readyPack.LastModifyTime == null){
	                	readyPack.LastModifyTime = sysDateTime;
	                }
	                itemLifeCycle.StatusTime = ((int)((sysDateTime.getTime() - readyPack.LastModifyTime.getTime()) / 60000L));
		            commonDAO.addItemLifeCycle(itemLifeCycle);
		            //#end 	  
		        }
			}
			
			//"Dropped("+postman.PostmanID+"-"+postman.PostmanName+"=>"+inboxPack.TerminalNo+"-"+inboxPack.BoxNo+")"
			String[] itemDesc= {postman.PostmanID,postman.PostmanName, "Counter-"+inboxPack.TerminalNo,inboxPack.BoxNo};
	        
		    // #start添加投递订单周期记录。
			PTItemLifeCycle itemLifeCycle = new PTItemLifeCycle();
	        itemLifeCycle.PackageID      = itemCode;
	        itemLifeCycle.ItemStatus     = inboxPack.ItemStatus;
	        itemLifeCycle.OperatorID     = in.DropAgentID;
	        itemLifeCycle.OperatorType   = postman.PostmanType;
	        itemLifeCycle.RecordLevel    = 0;//0-9
	        itemLifeCycle.LastModifyTime = sysDateTime;
            itemLifeCycle.ZoneID = readyPack.ZoneID;
            itemLifeCycle.LastStatus = readyPack.ItemStatus;
            if(readyPack.LastModifyTime == null){
            	readyPack.LastModifyTime = sysDateTime;
            }
            itemLifeCycle.StatusTime = ((int)((sysDateTime.getTime() - readyPack.LastModifyTime.getTime()) / 60000L));
	        commonDAO.addItemLifeCycle(Constant.ACTION_CODE_DROP, itemLifeCycle, itemDesc);
	        // #end
	        
	        //#start 订单数据加入发送队列进行发送(状态更新)
	    	commonDAO.addItemStatusUpdate(readyPack.PPCID, inboxPack.ItemStatus, inboxPack, "");
	        //#end
	    	
			
			// ////////////////////////////////////////////////////////////////////////
			//#start 生成密码短信，并提交发送
			// 发送短信
			if (StringUtils.isNotEmpty(ApplicationConfig.getInstance().getSendShortMsg())) {
				// ///////////////////////////////////////
				modifySMSStat(in.TerminalNo, false);

				// ///////////////////////////////////////
				TBTerminalDAO terminalDAO = daoFactory.getTBTerminalDAO();
		        TBTerminal terminal = new TBTerminal();
		        terminal.TerminalNo = in.TerminalNo;
		        terminal = terminalDAO.find(terminal);
		        
				// //////////////////////////////////
				MBPwdShortMsgDAO shortMsgDAO = daoFactory.getMBPwdShortMsgDAO();
	            MBPwdShortMsg shortMsg = new MBPwdShortMsg();
	            SequenceGenerator seqGen = SequenceGenerator.getInstance();
	            shortMsg.WaterID = seqGen.getNextKey(SequenceGenerator.SEQ_WATERID);
	            shortMsg.TerminalNo = in.TerminalNo;
	            shortMsg.PackageID = itemCode;
	            shortMsg.StoredTime = sysDateTime;
	            shortMsg.StoredDate = sysDate;
	            shortMsg.OpenBoxKey = pwd;
	            shortMsg.CustomerMobile = inboxPack.CustomerMobile;
	            shortMsg.LastModifyTime = sysDateTime;
	            shortMsg.ReSendNum      = 0;
	            shortMsg.PackageStatus  = inboxPack.ItemStatus;
	            shortMsg.SendStatus = "0"; // 0:未发送 1:发送进行中 2:发送成功 4:发送失败
	            shortMsg.MsgType    = SMSInfo.MSG_TYPE_DELIVERY;//
	            shortMsg.UMID       = StringUtils.getUUID();
	            shortMsg.ScheduleDateTime = null;
	            shortMsgDAO.insert(shortMsg);
				
				///////////////
				SMSInfo smsInfo = new SMSInfo();
				smsInfo.PackageID = itemCode;
				smsInfo.TerminalNo = in.TerminalNo;
				smsInfo.StoredTime = sysDateTime;
				smsInfo.sysDateTime = sysDateTime;
				smsInfo.CustomerMobile = inboxPack.CustomerMobile;
				smsInfo.OfBureau = "";
				smsInfo.BoxNo = in.BoxNo;
				smsInfo.OpenBoxKey = pwd;
				smsInfo.TerminalName = terminal.TerminalName;
				smsInfo.Location = terminal.Location;
				smsInfo.TerminalType = terminal.TerminalType;
				smsInfo.ZoneID   = inboxPack.ZoneID;
				smsInfo.PostmanID = in.DropAgentID;
				smsInfo.PostmanName = postman.PostmanName;
				smsInfo.CompanyID = inboxPack.CompanyID;
				smsInfo.CompanyName = "";
				smsInfo.ItemStatus = inboxPack.ItemStatus;
				smsInfo.MsgType = SMSInfo.MSG_TYPE_DELIVERY;
				smsInfo.WaterID = shortMsg.WaterID;
				smsInfo.UMID    = shortMsg.UMID;
				smsInfo.ScheduledDateTime = shortMsg.ScheduleDateTime;
				
				IMCompanyDAO companyDAO = daoFactory.getIMCompanyDAO();
		        IMCompany company = new IMCompany();
		        company.CompanyID = inboxPack.CompanyID;
		        try {
		        	company = companyDAO.find(company);
		        } catch (Exception e) {
		        }
		        smsInfo.CompanyID = company.CompanyID;
	            smsInfo.CompanyName = company.CompanyName;
				smsInfo.Latitude = terminal.Latitude;
				smsInfo.Longitude = terminal.Longitude;
				smsInfo.TrailerMsg = company.SMS_Notification;
				smsInfo.expireddays = company.ExpiredDays;
				
				//发送到同一个手机的多条短信，延迟发送
				commonDAO.scheduleSendSMS(smsInfo, shortMsg, sysDateTime);
			}
			//#end			
        }
		// 调用CommonDAO.addOperatorLog(OperID，功能编号，系统日期时间，“”)
		OPOperatorLog log = new OPOperatorLog();
		log.OperID = in.OperID;
		log.FunctionID = in.getFunctionID();
		log.OccurTime = sysDateTime;
		log.StationAddr = operOnline.LoginIPAddr;
		log.Remark = "";

		commonDAO.addOperatorLog(log);


        return result;
    }
    
	private void modifySMSStat(String TerminalNo, boolean isReminderFlg) throws EduException {
		MBSmsStatDAO smsStatDAO = daoFactory.getMBSmsStatDAO();
		MBSmsStat smsStat = new MBSmsStat();
		smsStat.TerminalNo = TerminalNo;
		smsStat.OccurYear = DateUtils.currentYear();
		smsStat.OccurMonth = DateUtils.currentMonth();

		if (smsStatDAO.isExist(smsStat)) {
			JDBCFieldArray setCols = new JDBCFieldArray();
			JDBCFieldArray whereCols = new JDBCFieldArray();

			setCols.addSQL(" TotalNum=TotalNum+1 ");
			if(isReminderFlg){
			    setCols.addSQL(" ReminderNum=ReminderNum+1 ");
			}else{
			    setCols.addSQL(" PwdNum=PwdNum+1 ");
			}
			
			whereCols.add("TerminalNo", TerminalNo);
			whereCols.add("OccurYear", smsStat.OccurYear);
			whereCols.add("OccurMonth", smsStat.OccurMonth);

			smsStatDAO.update(setCols, whereCols);
		} else {
			smsStat.TotalNum = 1;
			if(isReminderFlg){
			    smsStat.PwdNum = 0;
	            smsStat.ReminderNum = 1;
			}else{
			    smsStat.PwdNum = 1;
	            smsStat.ReminderNum = 0;
			}
			smsStat.ExpireNum = 0;
			smsStat.DynamicNum = 0;
			smsStat.PickupNum = 0;
			smsStat.OtherNum = 0;

			smsStatDAO.insert(smsStat);
		}
	}

	private String generatePwd() {
		ControlParam ctrlParam = ControlParam.getInstance();

		// 生成开箱密码
		int pwdLen = NumberUtils.parseInt(ctrlParam.getTakeOutPwdLen());
		String pwd = "";

		if (SysDict.TAKEOUTPWD_FORM_NUMBER.equals(ctrlParam.getTakeOutPwdFormat()))
			pwd = RandUtils.generateNumber(pwdLen);
		else if (SysDict.TAKEOUTPWD_FORM_CHAR.equals(ctrlParam.getTakeOutPwdFormat()))
			pwd = RandUtils.generateCharacter(pwdLen);
		else if (SysDict.TAKEOUTPWD_FORM_NUMBERCHAR.equals(ctrlParam.getTakeOutPwdFormat()))
			pwd = RandUtils.generateString(pwdLen);

		return pwd;
	}
	private void sentReminderSMS(PTInBoxPackage inboxPack, java.sql.Timestamp sysDateTime) throws EduException{
	    if(sysDateTime.after(inboxPack.ExpiredTime)){
	        return;
	    }
	    MBPwdShortMsgDAO shortMsgDAO = daoFactory.getMBPwdShortMsgDAO();
        MBPwdShortMsg shortMsg = new MBPwdShortMsg();
        
        JDBCFieldArray whereCols0 = new JDBCFieldArray();
        whereCols0.add("PackageID", inboxPack.PackageID);
        whereCols0.add("TerminalNo", inboxPack.TerminalNo);
        whereCols0.add("StoredTime", inboxPack.StoredTime);//add by zxy :MBPwdShortMsg表中相同PackageID、TerminalNo的可能有多条记录，故增加StoredTime条件
        
        RowSet rset = shortMsgDAO.select(whereCols0);
        if(RowSetUtils.rowsetNext(rset))
        {
            shortMsg.WaterID = RowSetUtils.getLongValue(rset, "WaterID");
        }
        
        if(shortMsg.WaterID <= 0){
            return;
        }
        shortMsg = shortMsgDAO.find(shortMsg);
        
        //插入催领流水
        MBReminderWaterDAO waterDAO = daoFactory.getMBReminderWaterDAO();
        MBReminderWater water = new MBReminderWater();

        SequenceGenerator seqGen = SequenceGenerator.getInstance();
        water.WaterID = seqGen.getNextKey(SequenceGenerator.SEQ_WATERID);
        water.TerminalNo = inboxPack.TerminalNo;
        water.CustomerMobile = inboxPack.CustomerMobile;
        water.PackageID = inboxPack.PackageID;
        water.PostmanID = inboxPack.DropAgentID;
        water.StoredDate = inboxPack.StoredDate;
        water.StoredTime = inboxPack.StoredTime;
        water.ReminderNum = 1;
        water.LastModifyTime = utilDAO.getCurrentDateTime();

        waterDAO.insert(water);
        
        //发送催领短信
        if(StringUtils.isNotEmpty(ApplicationConfig.getInstance().getSendShortMsg()))
        {
            //////////////////////////////////////////
            modifySMSStat(inboxPack.TerminalNo,true);
            
            ///////////////////////////////////////////////////
            TBTerminalDAO terminalDAO = daoFactory.getTBTerminalDAO();
            TBTerminal terminal = new TBTerminal();
            terminal.TerminalNo = inboxPack.TerminalNo;
            
            terminal = terminalDAO.find(terminal);
            
            // //////////////////////////////////
            //重置预定发送时间，并设置为未发送状态，可由发送线程进行短信发送
            shortMsg.UMID = StringUtils.getUUID();
            shortMsg.MsgType = SMSInfo.MSG_TYPE_REMINDER;
            shortMsg.ScheduleDateTime = null;
            
            
            JDBCFieldArray setCols1 = new JDBCFieldArray();
            JDBCFieldArray whereCols1 = new JDBCFieldArray();
            
            setCols1.add("SendStatus", "0");//0:未发送 1:发送进行中 2:发送成功 4:发送失败
            setCols1.add("UMID", shortMsg.UMID);
            setCols1.add("MsgType", shortMsg.MsgType);
            setCols1.add("PackageStatus", inboxPack.ItemStatus);
            setCols1.add("ScheduleDateTime",shortMsg.ScheduleDateTime);
            setCols1.add("LastModifyTime", sysDateTime);
            
            whereCols1.add("WaterID", shortMsg.WaterID);
            
            shortMsgDAO.update(setCols1, whereCols1);
            
            ///////////////
            SMSInfo smsInfo = new SMSInfo();
            smsInfo.PackageID = inboxPack.PackageID;
            smsInfo.TerminalNo = inboxPack.TerminalNo;
            smsInfo.StoredTime = inboxPack.StoredTime;
            smsInfo.CustomerMobile = inboxPack.CustomerMobile;
            smsInfo.OpenBoxKey = shortMsg.OpenBoxKey;
            smsInfo.TerminalName = terminal.TerminalName;
            smsInfo.Location = terminal.Location;
            smsInfo.TerminalType = terminal.TerminalType;
            smsInfo.MsgType = SMSInfo.MSG_TYPE_REMINDER;
            smsInfo.UMID  = shortMsg.UMID;
            smsInfo.ScheduledDateTime = shortMsg.ScheduleDateTime;

            IMCompanyDAO companyDAO = daoFactory.getIMCompanyDAO();
            IMCompany company = new IMCompany();
            company.CompanyID = inboxPack.CompanyID;
            try {
                company = companyDAO.find(company);
            } catch (Exception e) {
            }
            smsInfo.CompanyID = company.CompanyID;
            smsInfo.CompanyName = company.CompanyName;
            smsInfo.Latitude = terminal.Latitude;
            smsInfo.Longitude = terminal.Longitude;
            smsInfo.TrailerMsg = company.SMS_Notification;
            smsInfo.expireddays = company.ExpiredDays;
            
            //发送到同一个手机的多条短信，延迟发送
            commonDAO.scheduleSendSMS(smsInfo, shortMsg, sysDateTime);
        }
	}
}
