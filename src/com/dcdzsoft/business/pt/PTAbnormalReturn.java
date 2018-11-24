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
 * <p>Title: 自提柜后台运营系统</p>
 * <p>Description: 异常回收订单 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class PTAbnormalReturn extends ActionBean
{

    public int doBusiness(InParamPTAbnormalReturn in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        int result = 0;

		//1.验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(in.OperID)
			||StringUtils.isEmpty(in.PackageID))
			throw new EduException(ErrorCode.ERR_PARMERR);

		//2.调用CommonDAO.isOnline(管理员编号)判断管理员是否在线。
		OPOperOnline operOnline = commonDAO.isOnline(in.OperID);


		//3.调用UtilDAO.getCurrentDateTime()获得系统日期时间。
		java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();
		java.sql.Date sysDate = DateUtils.toSQLDate(sysDateTime);

		//4.  解析订单号。
        String[][] itemsCodeAndTime = commonDAO.decodeBatchPackageID(in.PackageID);
        PTDeliverHistoryDAO historyPackDAO = daoFactory.getPTDeliverHistoryDAO();
        if("inbox".equalsIgnoreCase(in.ReturnFlag)){
            PTInBoxPackageDAO inBoxPackDAO = daoFactory.getPTInBoxPackageDAO();
            for(String[] itemCodeAndTime:itemsCodeAndTime){
                String itemCode = itemCodeAndTime[0];
                if(StringUtils.isEmpty(itemCode)){
                    continue;
                }
                PTInBoxPackage inboxPack = new PTInBoxPackage();
                inboxPack.PackageID = itemCode;
                try{
                    inboxPack = inBoxPackDAO.find(inboxPack);
                }catch(EduException e){
                    continue;
                }
                inBoxPackDAO.delete(inboxPack);
                
                //先删除，保证数据不重复??  --预先插入历史订单表？
                JDBCFieldArray whereCols1 = new JDBCFieldArray();
                whereCols1.add("PackageID", inboxPack.PackageID);
                whereCols1.add("CreateTime", inboxPack.CreateTime);

                historyPackDAO.delete(whereCols1);
                
                //插入历史记录
                PTDeliverHistory historyPack = new PTDeliverHistory();
                historyPack.PackageID = inboxPack.PackageID;
                
                historyPack.CreateTime = inboxPack.CreateTime;
                historyPack.ItemStatus = SysDict.ITEM_STATUS_DROP_RETURNED;
                historyPack.RunStatus  = SysDict.ITEM_RUN_STATUS_RUNNING;
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
                
                historyPack.LastModifyTime = sysDateTime;
                historyPack.Remark = inboxPack.Remark;
                
                historyPackDAO.insert(historyPack);
                
                //#start 记录订单状态更新信息
                PTItemLifeCycle itemLifeCycle = new PTItemLifeCycle();
                itemLifeCycle.PackageID  = historyPack.PackageID;
                itemLifeCycle.ItemStatus = SysDict.ITEM_STATUS_DROP_RETURNED;
                itemLifeCycle.OperatorID    = operOnline.OperID;
                itemLifeCycle.OperatorType = ""+operOnline.OperType;
                itemLifeCycle.LastModifyTime = sysDateTime;
                itemLifeCycle.Remark = "Abnormal Retrun";
                itemLifeCycle.ZoneID = inboxPack.ZoneID;
                itemLifeCycle.LastStatus = inboxPack.ItemStatus;
                itemLifeCycle.StatusTime = ((int)((sysDateTime.getTime() - inboxPack.LastModifyTime.getTime()) / 60000L));
                commonDAO.addItemLifeCycle(itemLifeCycle);
                //#end 
            }
        }else if("history".equalsIgnoreCase(in.ReturnFlag)){
            for(String[] itemCodeAndTime:itemsCodeAndTime){
                String itemCode = itemCodeAndTime[0];
                String createtime = itemCodeAndTime[1];
                
                if(StringUtils.isEmpty(itemCode) || StringUtils.isEmpty(createtime)){
                    continue;
                }
                PTDeliverHistory historyPack = new PTDeliverHistory();
                historyPack.PackageID = itemCode;
                historyPack.CreateTime = java.sql.Timestamp.valueOf(createtime);
                
                JDBCFieldArray setCols = new JDBCFieldArray();
                JDBCFieldArray whereCols = new JDBCFieldArray();
                setCols.add("ItemStatus", SysDict.ITEM_STATUS_DROP_RETURNED);
                setCols.add("LastModifyTime", sysDateTime);
                setCols.add("RunStatus", SysDict.ITEM_RUN_STATUS_RUNNING);
                
                whereCols.add("PackageID", historyPack.PackageID);
                whereCols.add("CreateTime", historyPack.CreateTime);
                historyPackDAO.update(setCols, whereCols);
                
                //#start 记录订单状态更新信息
                PTItemLifeCycle itemLifeCycle = new PTItemLifeCycle();
                itemLifeCycle.PackageID  = historyPack.PackageID;
                itemLifeCycle.ItemStatus = SysDict.ITEM_STATUS_DROP_RETURNED;
                itemLifeCycle.OperatorID    = operOnline.OperID;
                itemLifeCycle.OperatorType = ""+operOnline.OperType;
                itemLifeCycle.LastModifyTime = sysDateTime;
                itemLifeCycle.Remark = "Abnormal Retrun";
                itemLifeCycle.ZoneID = historyPack.ZoneID;
                itemLifeCycle.StatusTime = ((int)((sysDateTime.getTime() - historyPack.LastModifyTime.getTime()) / 60000L));
                commonDAO.addItemLifeCycle(itemLifeCycle);
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
