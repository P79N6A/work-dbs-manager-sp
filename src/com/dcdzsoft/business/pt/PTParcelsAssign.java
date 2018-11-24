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
 * <p>Description: 为包裹分配自提柜 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class PTParcelsAssign extends ActionBean
{

    public int doBusiness(InParamPTParcelsAssign in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        int result = 0;

        //1.	验证输入参数是否有效，如果无效返回-1。
        if (StringUtils.isEmpty(in.OperID)
        	//||StringUtils.isEmpty(in.ZoneID)
        	||StringUtils.isEmpty(in.ItemStatus)
			||StringUtils.isEmpty(in.PackageID)
			||StringUtils.isEmpty(in.TerminalNo)
			||StringUtils.isEmpty(in.BoxType))
            throw new EduException(ErrorCode.ERR_PARMERR);
        
        //2.	调用CommonDAO.isOnline(管理员编号)判断管理员是否在线。
        OPOperOnline operOnline = commonDAO.isOnline(in.OperID);
        
        if(!SysDict.ITEM_STATUS_DROP_RECEIVED.equals(in.ItemStatus)){
        	throw new EduException(ErrorCode.ERR_OPERAT_EXISTSFORBIDITEM);
        }
        
        //柜体权限检查
        IMZoneLockerRightDAO zoneLockerRightDAO = daoFactory.getIMZoneLockerRightDAO();
        IMZoneLockerRight zoneLockerRight = new IMZoneLockerRight();
        zoneLockerRight.ZoneID     = operOnline.ZoneID;
        zoneLockerRight.TerminalNo = in.TerminalNo;
        if(!zoneLockerRightDAO.isExist(zoneLockerRight)){
        	throw new EduException(ErrorCode.ERR_OPERAT_FORBIDUSERLOCKER);
        }
        
        TBBoxTypeDAO boxTypeDAO = daoFactory.getTBBoxTypeDAO();
        TBBoxType boxType = new TBBoxType();
        boxType.BoxType = in.BoxType;
        if(!boxTypeDAO.isExist(boxType)){
        	throw new EduException(ErrorCode.ERR_PARMERR);
        }
    	
        
        //3.	调用UtilDAO.getCurrentDateTime()获得系统日期时间。
        java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();

        //4.	解析订单号。
        String[][] itemsCodeAndTime = commonDAO.decodeBatchPackageID(in.PackageID);
       
        //#start 更新订单状态。
        in.ItemStatus    = SysDict.ITEM_STATUS_DROP_RECEIVED;//当前订单状态判断
        String nowStatus = SysDict.ITEM_STATUS_DROP_RECEIVED;//当前订单状态判断
        String nextStatus = SysDict.ITEM_STATUS_DROP_ASSIGNED;
        
        //String TerminalNo = in.TerminalNo;
        //String BoxType    = in.BoxType;
        
        
        PTReadyPackageDAO readyPackDAO = daoFactory.getPTReadyPackageDAO();
    	for(String[] itemCodeAndTime:itemsCodeAndTime){
			String itemCode = itemCodeAndTime[0];
			if(StringUtils.isEmpty(itemCode)){
				continue;
			}			
			JDBCFieldArray setCols = new JDBCFieldArray();
	        JDBCFieldArray whereCols = new JDBCFieldArray();
	        
			//#start 查询订单信息，更新订单状态
			PTReadyPackage readyPack = new PTReadyPackage();
			readyPack.PackageID = itemCode;
			try{
				readyPack = readyPackDAO.find(readyPack);
				if(StringUtils.isEmpty(readyPack.TerminalNo)){
					setCols.add("TerminalNo", in.TerminalNo);
				}else{
					if(!in.TerminalNo.equals(readyPack.TerminalNo)
						&&"1".equals(ControlParam.getInstance().getAssignLockerMode())){
						setCols.add("TerminalNo", in.TerminalNo);
					}
				}
				
				if(StringUtils.isEmpty(readyPack.BoxType)){
					 setCols.add("BoxType", in.BoxType);
				}else{
					if(!in.BoxType.equals(readyPack.BoxType)
						&&"1".equals(ControlParam.getInstance().getAssignBoxSizeMode())){
						setCols.add("BoxType", in.BoxType);
					}
				}
			}catch(EduException e){
				continue;
			}
			
	        setCols.add("ItemStatus", nextStatus);
	        setCols.add("LastModifyTime", sysDateTime);
	        
	        whereCols.add("PackageID", itemCode);
	        whereCols.add("ItemStatus", nowStatus);

	        int count = readyPackDAO.update(setCols, whereCols);
	        if(count<=0){
	        	continue;
	        }
	        //#end
	        
	        //"Assign("+in.TerminalNo+"-"+in.BoxType+"-{BoxNo})"
	        String[] itemDesc= {in.TerminalNo,in.BoxType, "0"};
	        
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
            commonDAO.addItemLifeCycle(Constant.ACTION_CODE_ASSIGN, itemLifeCycle, itemDesc);
            //#end
            
            //#start 订单数据加入发送队列进行发送(状态更新)
        	commonDAO.addItemStatusUpdate(readyPack.PPCID, nextStatus, readyPack, "");
	        //#end
        	
        }
    	//#end
       
        return result;
    }

}
