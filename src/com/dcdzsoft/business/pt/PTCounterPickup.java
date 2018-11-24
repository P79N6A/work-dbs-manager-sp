package com.dcdzsoft.business.pt;

import javax.sql.RowSet;

import com.dcdzsoft.EduException;
import com.dcdzsoft.sda.db.*;
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
 * <p>Title: 智能自助包裹柜系统</p>
 * <p>Description: 从投递柜台取件 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author 王中立
 * @version 1.0
 */

public class PTCounterPickup extends ActionBean
{

    public int doBusiness(InParamPTCounterPickup in) throws EduException
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
		
		//4.解析订单号。
		String[][] itemsCodeAndTime = commonDAO.decodeBatchPackageID(in.PackageID);
		//5.更新订单状态。
		String nextStatus = SysDict.ITEM_STATUS_DROP_TAKEOUT;
		String nowStatus  = in.ItemStatus;
		//#start 更新订单状态。
		PTReadyPackageDAO readyPackDAO = daoFactory.getPTReadyPackageDAO();
		for(String[] itemCodeAndTime:itemsCodeAndTime){
			String itemCode = itemCodeAndTime[0];
			if(StringUtils.isEmpty(itemCode)){
				continue;
			}
			PTInBoxPackageDAO inboxPackDAO = daoFactory.getPTInBoxPackageDAO();
	        PTInBoxPackage inboxPack = new PTInBoxPackage();
	        inboxPack.PackageID = itemCode;
	        try{
	        	inboxPack = inboxPackDAO.find(inboxPack);
	        }catch(Exception e){
    			throw new EduException(ErrorCode.ERR_PACKAGENOTEXISTS);
    		}
	        //从在箱信息里面删除
	        inboxPackDAO.delete(inboxPack);
	        //先删除，保证数据不重复??  --预先插入历史订单表？
	        JDBCFieldArray whereCols1 = new JDBCFieldArray();
	        whereCols1.add("PackageID", inboxPack.PackageID);
	        whereCols1.add("TerminalNo", inboxPack.TerminalNo);
	        whereCols1.add("StoredTime", inboxPack.StoredTime);
	        PTDeliverHistoryDAO historyPackDAO = daoFactory.getPTDeliverHistoryDAO();
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
	        
	        historyPack.TerminalNo = inboxPack.TerminalNo;
	        historyPack.BoxNo = inboxPack.BoxNo;
	        historyPack.DropAgentID = inboxPack.DropAgentID;
	        historyPack.DynamicCode = inboxPack.DynamicCode;
	        
	        historyPack.StoredTime = inboxPack.StoredTime;
	        historyPack.StoredDate = inboxPack.StoredDate;
	        historyPack.ExpiredTime = inboxPack.ExpiredTime;
	        historyPack.OpenBoxKey = inboxPack.OpenBoxKey;
	        
	        historyPack.TakedTime = sysDateTime;
	        historyPack.ReturnAgentID = "";//in.PostmanID
	        historyPack.DADFlag = inboxPack.DADFlag;
	        historyPack.TradeWaterNo = inboxPack.TradeWaterNo;
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
	        String[] itemDesc= {"Counter-"+historyPack.TerminalNo, historyPack.CustomerMobile};
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
	        
			//#start修改短信里面的包裹状态
			commonDAO.updatePwdSMSStauts(inboxPack.PackageID, inboxPack.TerminalNo, inboxPack.StoredTime, historyPack.ItemStatus, sysDateTime);
	        //#end
			
	        ///////////////////////////////////////////////////////////
			//反馈
			feedback(historyPack);
			
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
