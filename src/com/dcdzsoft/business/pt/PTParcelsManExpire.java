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
 * <p>Description: 包裹人工超期 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class PTParcelsManExpire extends ActionBean
{

    public int doBusiness(InParamPTParcelsManExpire in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        int result = 0;

		//1.	验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(in.OperID)
			||StringUtils.isEmpty(in.PackageID))
			throw new EduException(ErrorCode.ERR_PARMERR);

		//2.	调用CommonDAO.isOnline(管理员编号)判断管理员是否在线。
		OPOperOnline operOnline = commonDAO.isOnline(in.OperID);

		//3.	调用UtilDAO.getCurrentDateTime()获得系统日期时间。
		java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();
		java.sql.Date sysDate = DateUtils.toSQLDate(sysDateTime);

		//4.	解析订单号。
		String[][] itemsCodeAndTime = commonDAO.decodeBatchPackageID(in.PackageID);
        if(in.ExpiredTime==null){
        	in.ExpiredTime = sysDateTime;
        }
        if(in.ExpiredTime.after(sysDateTime)){
        	//修改逾期时间
        	result = commonDAO.modExpireTime(in.OperID, itemsCodeAndTime, in.TerminalNo, in.ExpiredTime);
        }else{
        	in.ExpiredTime = sysDateTime;
        	//订单逾期，锁定包裹，更新订单状态
        	in.ItemStatus     = SysDict.ITEM_STATUS_DROP_DROPPED;//当前订单状态判断
            String nextStatus = SysDict.ITEM_STATUS_DROP_M_EXPIRED;
            
            PTInBoxPackageDAO inBoxPackDAO = daoFactory.getPTInBoxPackageDAO();
        	for(String[] itemCodeAndTime:itemsCodeAndTime){
    			String itemCode = itemCodeAndTime[0];
    			if(StringUtils.isEmpty(itemCode)){
    				continue;
    			}
    			
    			PTInBoxPackage inBoxPack = new PTInBoxPackage();
    			inBoxPack.PackageID = itemCode;
    			try{
    				inBoxPack = inBoxPackDAO.find(inBoxPack);
    			}catch(EduException e){
    				continue;
    			}
    			
    			String nowStatus = inBoxPack.ItemStatus;
    			switch(nowStatus){
    			case SysDict.ITEM_STATUS_DROP_DROPPED:
    			case SysDict.ITEM_STATUS_DROP_D_DROPPED:
    				//#start 更新订单状态
    				JDBCFieldArray setCols = new JDBCFieldArray();
        	        JDBCFieldArray whereCols = new JDBCFieldArray();
        	        
        	        setCols.add("ItemStatus", nextStatus);
        	        setCols.add("ParcelStatus", SysDict.PACKAGE_STATUS_LOCKED);
        	        setCols.add("ExpiredTime", in.ExpiredTime);
        	        setCols.add("LastModifyTime", sysDateTime);
        	        
        	        whereCols.add("PackageID", itemCode);
        	        whereCols.checkAdd("TerminalNo", in.TerminalNo);
        	        //whereCols.add("ItemStatus", nowStatus);
        	        whereCols.addSQL(" AND (");
        	        whereCols.addSQL(" ItemStatus="+StringUtils.addQuote(SysDict.ITEM_STATUS_DROP_DROPPED));
        	        whereCols.addSQL(" OR ItemStatus="+StringUtils.addQuote(SysDict.ITEM_STATUS_DROP_D_DROPPED));
        	        whereCols.addSQL(" )");
        	        
        	        int count = inBoxPackDAO.update(setCols, whereCols);
        	        if(count<=0){
        	        	continue;
        	        }
        	        //#end
        	        
        	        //"Force-Expire("+ExpiredTime+")"
        	        String[] itemDesc= {Constant.dateFromat.format(in.ExpiredTime)};
        	        
        	        //#start记录订单状态更新信息
                    PTItemLifeCycle itemLifeCycle = new PTItemLifeCycle();
                    itemLifeCycle.PackageID  = itemCode;
                    itemLifeCycle.ItemStatus = nextStatus;
                    itemLifeCycle.OperatorID    = in.OperID;
                    itemLifeCycle.OperatorType = ""+operOnline.OperType;
                    itemLifeCycle.LastModifyTime = sysDateTime;
                    itemLifeCycle.ZoneID = inBoxPack.ZoneID;
                    itemLifeCycle.LastStatus = inBoxPack.ItemStatus;
                    itemLifeCycle.StatusTime = ((int)((sysDateTime.getTime() - inBoxPack.LastModifyTime.getTime()) / 60000L));
                    commonDAO.addItemLifeCycle(Constant.ACTION_CODE_FORCEEXPIRE, itemLifeCycle, itemDesc);
                    //#end
                    
                    //#start 订单数据加入发送队列进行发送(状态更新)
                	commonDAO.addItemStatusUpdate(inBoxPack.PPCID, nextStatus,inBoxPack, "" );
        	        //#end
                	
    				break;
    			}   
    			
    			String limit = ControlParam.getInstance().getTakeOutExpiredItemLimit();
                if("1".equalsIgnoreCase(limit) || "3".equalsIgnoreCase(limit)){
                    //推送到设备端   锁定订单
                    try{
                        InParamPTModPackageStatus param = new InParamPTModPackageStatus();
                        param.OperID     = in.OperID;
                        param.PackageID  = inBoxPack.PackageID;
                        param.TerminalNo = inBoxPack.TerminalNo;
                        param.PackageStatus = SysDict.PACKAGE_STATUS_LOCKED;
                        param.RemoteFlag = SysDict.OPER_FLAG_REMOTE;
                        com.dcdzsoft.businessproxy.PushBusinessProxy.doBusiness(param);
                    }catch(EduException e){}
                }
            }
            
        }
        
        return result;
    }
    
}
