package com.dcdzsoft.business.dm;

import java.util.UUID;

import javax.sql.RowSet;

import com.dcdzsoft.EduException;
import com.dcdzsoft.sda.db.*;
import com.dcdzsoft.sms.SMSManager;
import com.dcdzsoft.util.*;
import com.dcdzsoft.dto.function.*;
import com.dcdzsoft.dto.business.*;
import com.dcdzsoft.dao.*;
import com.dcdzsoft.dao.common.*;
import com.dcdzsoft.config.ApplicationConfig;
import com.dcdzsoft.constant.*;
import com.dcdzsoft.business.ActionBean;

/**
 * <p>Title: 自提柜后台运营系统</p>
 * <p>Description: 创建寄件订单 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class DMDeliveryCreate extends ActionBean
{

    public OutParamDMDeliveryCreate doBusiness(InParamDMDeliveryCreate in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        OutParamDMDeliveryCreate out = new OutParamDMDeliveryCreate();

		//1.	验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(in.BPartnerID)
			||StringUtils.isEmpty(in.ServiceID)
			//||StringUtils.isEmpty(in.CollectionOption)
			//||StringUtils.isEmpty(in.ReturnOption)
			//||StringUtils.isEmpty(in.CustomerName)
			||StringUtils.isEmpty(in.CustomerMobile)
			//||StringUtils.isEmpty(in.CustomerAddress)
			||StringUtils.isEmpty(in.ParcelSize))
			throw new EduException(ErrorCode.ERR_PARMERR);

		//2.	调用CommonDAO.isOnline(管理员编号)判断管理员是否在线。
		//OPOperOnline operOnline = commonDAO.isOnline(in.OperID);

		//3.	调用UtilDAO.getCurrentDateTime()获得系统日期时间。
		java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();
		java.sql.Date sysDate = DateUtils.toSQLDate(sysDateTime);
		
		//////商业合作伙伴检查
		IMBusinessPartnerDAO businessPartnerDAO = daoFactory.getIMBusinessPartnerDAO();
		IMBusinessPartner businessPartner = new IMBusinessPartner();
        businessPartner.BPartnerID = in.BPartnerID;
        
        businessPartner = businessPartnerDAO.find(businessPartner);
        
        if("1".equals(in.ReturnOption)){//退件订单
        	if(!"1".equals(businessPartner.ReturnServiceFlag)){//无退件服务
        		out.Result  = 2;//2	ERR_NOTRETURNSERVICE
        		out.Balance = businessPartner.Balance;
        		return out;
        	}
        	in.CollectionOption = "0";//退件不支持揽件
        }else{//寄件订单
        	in.ReturnOption = "0";	
        }
        
        if(!commonDAO.isPhoneNumber(in.CustomerMobile)){
    		throw new EduException(ErrorCode.ERR_BUSINESS_MOBILEFORMATERROR);
    	}
        
        if(!"1".equals(businessPartner.CollectionServiceFlag)
        		||! "1".equals(in.CollectionOption)){
        	in.CollectionOption = "0";
        }
        /*TBTerminal terminal = new TBTerminal();
        terminal.TerminalNo = in.TerminalNo;
        TBTerminalDAO  terminalDAO = daoFactory.getTBTerminalDAO();
        
        if(terminalDAO.isExist(terminal)){
            terminal = terminalDAO.find(terminal);
        }*/
        //计算服务费用
        double serviceAmt = 0.0;
        double collectionAmt = 0.0;
        double creditLimit = 0.0;
        IMServiceRateDAO serviceDAO = daoFactory.getIMServiceRateDAO();
        IMServiceRate service = new IMServiceRate();
        service.ServiceID = in.ServiceID;
        if(!serviceDAO.isExist(service)){
        	throw new EduException(ErrorCode.ERR_SERVICENOTEXISTS);
        }
        IMBPartnerServiceRightDAO serviceRightDAO = daoFactory.getIMBPartnerServiceRightDAO();
        JDBCFieldArray whereCols = new JDBCFieldArray();
    	whereCols.add("BPartnerID", in.BPartnerID);
    	whereCols.add("ServiceID", in.ServiceID);
    	if(serviceRightDAO.isExist(whereCols)<1){
    		throw new EduException(ErrorCode.ERR_SERVICNOTALLOW);
    	}
        service = serviceDAO.find(service);
        if(!"1".equals(service.Active)){
        	throw new EduException(ErrorCode.ERR_SERVICENOTACTIVE);
        }
        StringBuffer detailsBuff = new StringBuffer();
        
        /////////////????根据选择的包裹尺寸，确定服务费用
        if("2".equals(in.ParcelSize)){//大箱
        	serviceAmt = service.ServiceAmtBig;
        }else if("1".equals(in.ParcelSize)){//中箱
        	serviceAmt = service.ServiceAmtMed;
        }else{
        	serviceAmt = service.ServiceAmtSmall;
        }
        detailsBuff.append(",ParcelSize="+in.ParcelSize);
        detailsBuff.append(",ServiceAmt="+serviceAmt);
        if(businessPartner.Discount<0 || businessPartner.Discount>100){
        	businessPartner.Discount = 0;
        }
        detailsBuff.append(",Discount="+businessPartner.Discount+"%");
        serviceAmt = (serviceAmt*(100-businessPartner.Discount))/100;//折扣
        detailsBuff.append(",Amount="+serviceAmt);
        //
        IMCollectZoneDAO collectZoneDAO = daoFactory.getIMCollectZoneDAO();
    	IMCollectZone collectZone = new IMCollectZone();
    	collectZone.CollectZoneID = businessPartner.CollectZoneID;
    	
    	collectZone = collectZoneDAO.find(collectZone);
		IMZoneDAO zoneDAO = daoFactory.getIMZoneDAO();
        IMZone zone = new IMZone();
        zone.ZoneID = collectZone.ZoneID;
        zone = zoneDAO.find(zone);
        
        if("1".equals(in.CollectionOption)
        		&& zone.CollectCharge>0){
        	collectionAmt= zone.CollectCharge;
        }
        detailsBuff.append(",CollectCharge="+collectionAmt);
        double prepayAmt = (serviceAmt+collectionAmt);
        
        detailsBuff.append(",Total="+prepayAmt);
        
        //授信额度查询
        if(SysDict.BUSINESS_CREDIT_FLAG_YES.equalsIgnoreCase(businessPartner.CreditFlag)){
        	if(businessPartner.CreditLimit>0.0){
        		creditLimit = businessPartner.CreditLimit;
        	}
        	if(creditLimit>businessPartner.Balance){//授信额度不大于余额
        		creditLimit = businessPartner.Balance;
        	}
        }else{
        	creditLimit = 0.0;
        }
        //检查合作伙伴账户余额
        
		//# start ，余额不足则返回ERR_BALANCE_NOT_ENOUGH。
        if((prepayAmt) > (businessPartner.Balance+creditLimit)){
    		//throw new EduException(ErrorCode.ERR_BALANCE_NOT_ENOUGH);
    		out.Result  = 1;//1	ERR_BALANCE_NOT_ENOUGH	账户余额不足
    		out.Balance = businessPartner.Balance;
    		
    		ControlParam ctrlParam = ControlParam.getInstance();
            if(StringUtils.isNotEmpty(ApplicationConfig.getInstance().getSendShortMsg()) 
            		&&commonDAO.isPhoneNumber(businessPartner.Mobile)
            		&&"1".equals(ctrlParam.getSendBalanceSMS()))
            {
            	SMSManager.getInstance().sentBalanceMsg(businessPartner.Username, 
            			Constant.dateFromat.format(sysDateTime),
            			out.Balance,
            			businessPartner.Mobile);
            }
    		return out;
    	}
        //#end
        
		//5.	生成包裹单号。
        String itemCode = commonDAO.getE1Code(service);
        
        String tradeWaterID = commonDAO.doBusinessPartnerPrePay(businessPartner, serviceAmt, itemCode, service.ServiceID, detailsBuff.toString());
        
		
		//#start 插入订单数据。
        DMCollectionParcelDAO collectionParcelDAO = daoFactory.getDMCollectionParcelDAO();
        DMCollectionParcel collectionItem = new DMCollectionParcel();
        collectionItem.PackageID  = itemCode;
        collectionItem.CreateTime = sysDateTime;
        collectionItem.BPartnerID = in.BPartnerID;
        
        collectionItem.CollectionFlag = in.CollectionOption;
        if(SysDict.SERVICE_COLLECTION_FLAG_YES.equalsIgnoreCase(in.CollectionOption)){
            collectionItem.ItemStatus = SysDict.ITEM_STATUS_COLLECT_TOBECOLLECTED;
        }else{
        	collectionItem.ItemStatus = SysDict.ITEM_STATUS_COLLECT_CREATED;
        }
        
        collectionItem.CollectionAmt  = collectionAmt;
        collectionItem.ServiceID      = service.ServiceID;
        collectionItem.ServiceAmt     = serviceAmt;
        collectionItem.TradeWaterNo   = tradeWaterID;
        collectionItem.TerminalNo     = in.TerminalNo;
        collectionItem.CustomerID     = in.CustomerID;
        collectionItem.CustomerMobile = in.CustomerMobile;
        collectionItem.CustomerAddress = in.CustomerAddress;
        collectionItem.CustomerName    = in.CustomerName;
        collectionItem.TerminalNo      = in.TerminalNo;
        collectionItem.ParcelSize      = in.ParcelSize;
        collectionItem.ReturnFlag      = in.ReturnOption;
        
        collectionItem.CollectZoneID   = businessPartner.CollectZoneID;
        collectionItem.CompanyID       = Constant.COMPANY_SAUDIPOST_ID;//寄件包裹服务商为沙特邮政
        collectionItem.ZoneID          = zone.ZoneID;
        collectionItem.PPCID           = Constant.DEFAULT_PPC_ID;//直投默认PPC
        collectionItem.LastModifyTime  = sysDateTime;
        collectionParcelDAO.insert(collectionItem); 
        //#end
        
        // #start 添加投递订单周期记录。
        DMItemLifeCycle itemLifeCycle = new DMItemLifeCycle();
        itemLifeCycle.PackageID  = collectionItem.PackageID;
        itemLifeCycle.ItemStatus = collectionItem.ItemStatus;
        itemLifeCycle.OperatorID    = businessPartner.BPartnerID;
        itemLifeCycle.OperatorType  = SysDict.OPER_TYPE_BPARTNER;
        itemLifeCycle.RecordLevel   = 9;//0-9
        itemLifeCycle.LastModifyTime = sysDateTime;
        itemLifeCycle.Remark = "Create by "+businessPartner.BPartnerName;
        commonDAO.addItemLifeCycle(itemLifeCycle);
        // #end
        
        out.Result      = 0;
        out.PackageID   = collectionItem.PackageID;
        out.Balance     = businessPartner.Balance;
        
		// 调用CommonDAO.addOperatorLog(OperID，功能编号，系统日期时间，“”)
		OPOperatorLog log = new OPOperatorLog();
		log.OperID = collectionItem.BPartnerID;
		log.FunctionID = in.getFunctionID();
		log.OccurTime = sysDateTime;
		log.StationAddr = "";
		log.Remark = "BPartnerID:"+collectionItem.BPartnerID+",CollectionCharge="+collectionAmt;

		commonDAO.addOperatorLog(log);

        return out;
    }

}
