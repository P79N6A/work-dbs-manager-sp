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
 * <p>Description: 从投递清单中删除订单 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class PTDropOrderItemsDel extends ActionBean
{

    public int doBusiness(InParamPTDropOrderItemsDel in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        int result = 0;

        //1.	验证输入参数是否有效，如果无效返回-1。
        if (StringUtils.isEmpty(in.OperID)
        	//||StringUtils.isEmpty(in.ZoneID)
			||StringUtils.isEmpty(in.PackageID))
            throw new EduException(ErrorCode.ERR_PARMERR);
        
        //2.	调用CommonDAO.isOnline(管理员编号)判断管理员是否在线。
        OPOperOnline operOnline = commonDAO.isOnline(in.OperID);
        
        //3.	调用UtilDAO.getCurrentDateTime()获得系统日期时间。
        java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();
        java.sql.Date sysDate = DateUtils.toSQLDate(sysDateTime);

        //4.	解析订单号。
        String[][] itemsCodeAndTime = commonDAO.decodeBatchPackageID(in.PackageID);
        
        //5.	更新订单状态。
        String nowStatus  = SysDict.ITEM_STATUS_DROP_SCHEDULED;//当前订单状态判断
		String nextStatus = SysDict.ITEM_STATUS_DROP_ASSIGNED;
		
		PTReadyPackageDAO readyPackDAO = daoFactory.getPTReadyPackageDAO();
    	for(String[] itemCodeAndTime:itemsCodeAndTime){
			String itemCode = itemCodeAndTime[0];
			if(StringUtils.isEmpty(itemCode)){
				continue;
			}
			
			//#start 查询订单信息，更新订单状态
			PTReadyPackage readyPack = new PTReadyPackage();
			readyPack.PackageID = itemCode;
			try{
				readyPack = readyPackDAO.find(readyPack);
			}catch(EduException e){
				continue;
			}
			
			JDBCFieldArray setCols = new JDBCFieldArray();
	        JDBCFieldArray whereCols = new JDBCFieldArray();
	        setCols.add("ItemStatus", nextStatus);
	        setCols.add("ReportOrderID", "");//清空投递清单号
	        setCols.add("BoxNo", "");//清空投递清单号
	        setCols.add("LastModifyTime", sysDateTime);
	        
	        whereCols.add("PackageID", itemCode);
	        whereCols.add("ItemStatus", nowStatus);

	        int count = readyPackDAO.update(setCols, whereCols);
	        if(count<=0){
	        	continue;
	        }
	        //#end
	        
	        //"DelDropOrder("+readyPack.TerminalNo+"-"+readyPack.BoxType+"-"+readyPack.BoxNo+")"
	        String[] itemDesc= {readyPack.TerminalNo,readyPack.BoxType, readyPack.BoxNo};
            
            //#start记录订单状态更新信息
            PTItemLifeCycle itemLifeCycle = new PTItemLifeCycle();
            itemLifeCycle.PackageID  = itemCode;
            itemLifeCycle.ItemStatus = nextStatus;
            itemLifeCycle.OperatorID    = in.OperID;
            itemLifeCycle.OperatorType  = ""+operOnline.OperType;
            itemLifeCycle.LastModifyTime = sysDateTime;
            itemLifeCycle.ZoneID = readyPack.ZoneID;
            itemLifeCycle.LastStatus = readyPack.ItemStatus;
            if(readyPack.LastModifyTime == null){
            	readyPack.LastModifyTime = sysDateTime;
            }
            itemLifeCycle.StatusTime = ((int)((sysDateTime.getTime() - readyPack.LastModifyTime.getTime()) / 60000L));
            commonDAO.addItemLifeCycle(Constant.ACTION_CODE_DEL4ORDER, itemLifeCycle, itemDesc);
            //#end
	        
            //#start 订单数据加入发送队列进行发送(状态更新)
        	commonDAO.addItemStatusUpdate(readyPack.PPCID, nextStatus, readyPack, "");
	        //#end
            
        }
		
        

        return result;
    }
}
