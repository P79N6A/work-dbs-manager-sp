package com.dcdzsoft.business.pt;

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
 * <p>Title: 智能自助包裹柜系统</p>
 * <p>Description: 从投递柜台回收包裹 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author 王中立
 * @version 1.0
 */

public class PTCounterReturn extends ActionBean
{

    public int doBusiness(InParamPTCounterReturn in) throws EduException
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
	    PTDeliverHistoryDAO historyPackDAO = daoFactory.getPTDeliverHistoryDAO();
	    if("inbox".equalsIgnoreCase(in.ReturnFlag)){
	        PTInBoxPackageDAO inBoxPackDAO = daoFactory.getPTInBoxPackageDAO();
	        for(String[] itemCodeAndTime:itemsCodeAndTime){
	            String itemCode = itemCodeAndTime[0];
	            if(StringUtils.isEmpty(itemCode)){
	                continue;
	            }
	          //查询在箱
	            PTInBoxPackageDAO inboxPackDAO = daoFactory.getPTInBoxPackageDAO();
	            PTInBoxPackage inboxPack = new PTInBoxPackage();
	            inboxPack.PackageID = itemCode;
	 	        try{
	 	            inboxPack = inBoxPackDAO.find(inboxPack);
	 	        }catch(EduException e){
	 	            continue;
	 	        }
	    		//验证投递员是否存在
	            PMPostmanDAO postmanDAO = daoFactory.getPMPostmanDAO();
	            PMPostman postman = new PMPostman();
	            postman.PostmanID = inboxPack.DropAgentID;
	            try{
	                postman = postmanDAO.find(postman);
	            } catch (Exception e) {
	                throw new EduException(ErrorCode.ERR_POSTMANNOTEXISTS);
	            }
	            
	            //验证回收权限
	            if(!"1".equals(postman.ReturnRight)){
	            	throw new EduException(ErrorCode.ERR_FORBIDPOSTMANTAKEOUT);
	        	}
	           
	            inBoxPackDAO.delete(inboxPack);
	            
	          //先删除，保证数据不重复??  --预先插入历史订单表？
	            JDBCFieldArray whereCols1 = new JDBCFieldArray();
	            whereCols1.add("PackageID", inboxPack.PackageID);
	            whereCols1.add("TerminalNo", inboxPack.TerminalNo);
	            whereCols1.add("CreateTime", inboxPack.CreateTime);

	            historyPackDAO.delete(whereCols1);
	            
	            //插入历史记录
	            PTDeliverHistory historyPack = new PTDeliverHistory();
	            historyPack.PackageID = itemCode;
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
	            
	            historyPack.TerminalNo  = inboxPack.TerminalNo;
	            historyPack.BoxNo       = inboxPack.BoxNo;
	            historyPack.DropAgentID = inboxPack.DropAgentID;
	            historyPack.DynamicCode = inboxPack.DynamicCode;
	            
	            historyPack.StoredTime = inboxPack.StoredTime;
	            historyPack.StoredDate = inboxPack.StoredDate;
	            historyPack.ExpiredTime = inboxPack.ExpiredTime;
	            historyPack.OpenBoxKey = inboxPack.OpenBoxKey;
	            
	            historyPack.TakedTime = sysDateTime;
	            historyPack.ReturnAgentID = inboxPack.DropAgentID;
	            historyPack.DADFlag = inboxPack.DADFlag;
	            historyPack.TradeWaterNo = inboxPack.TradeWaterNo;
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
	            
	            //"Return back("+inboxPack.TerminalNo+"-"+inboxPack.BoxNo+")"
	            String[] itemDesc= {"Counter-"+inboxPack.TerminalNo,inboxPack.BoxNo};
	            
	            //#start记录订单状态更新信息
	            PTItemLifeCycleDAO itemLifeCycleDAO = daoFactory.getPTItemLifeCycleDAO();
	            PTItemLifeCycle itemLifeCycle = new PTItemLifeCycle();
	            itemLifeCycle.PackageID  = itemCode;
	            itemLifeCycle.ItemStatus = historyPack.ItemStatus;
	            itemLifeCycle.OperatorID    = inboxPack.DropAgentID;
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
	        }
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
}
