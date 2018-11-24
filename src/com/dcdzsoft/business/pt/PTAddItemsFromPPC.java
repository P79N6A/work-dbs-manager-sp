package com.dcdzsoft.business.pt;

import javax.sql.RowSet;

import org.apache.commons.lang.StringUtils;

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
 * <p>Description: 添加待投递订单 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class PTAddItemsFromPPC extends ActionBean
{

	/**
	 * Add item from ppc
	 * @param in
	 * @return 
	 * @throws EduException
	 */
    public com.dcdzsoft.business.api.Comm.ResultCode doBusiness(InParamPTAddItemsFromPPC in) throws EduException 
    {
    	//1.	验证输入参数是否有效，如果无效返回-1。
    	if (StringUtils.isEmpty(in.PackageID)
				||StringUtils.isEmpty(in.PPCID)
				//||StringUtils.isEmpty(in.ZoneID)
				||StringUtils.isEmpty(in.CustomerMobile))
				throw new EduException(ErrorCode.ERR_PARMERR);
    	
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        
        com.dcdzsoft.business.api.Comm.ResultCode resultCode = com.dcdzsoft.business.api.Comm.ResultCode.SUCCESS;
        
        java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();
        
        in.PackageID = commonDAO.normalizeItemCode(in.PackageID);
        PTReadyPackageDAO readyPackDAO = daoFactory.getPTReadyPackageDAO();
        PTReadyPackage readyPack = new PTReadyPackage();
        readyPack.PackageID = in.PackageID;
		if(readyPackDAO.isExist(readyPack)){
			resultCode = com.dcdzsoft.business.api.Comm.ResultCode.ERROR_ITEMCODE_EXISTS;
			return resultCode;
		}
		//ppc
		IMPostalProcessingCenterDAO postalProcessingCenterDAO = daoFactory.getIMPostalProcessingCenterDAO();
        IMPostalProcessingCenter ppc = new IMPostalProcessingCenter();
        ppc.PPCID = in.PPCID;
        if(!postalProcessingCenterDAO.isExist(ppc)){
        	resultCode = com.dcdzsoft.business.api.Comm.ResultCode.ERROR_PPCID_NOTEXISTS;
			return resultCode;
        }
        ppc = postalProcessingCenterDAO.find(ppc);
        
        
        //Mobile
		//readypack.CustomerMobile = in.CustomerMobile;
		//#start CustomerID   -- Optional
        IMCustomer customer = new IMCustomer();
		if(StringUtils.isNotEmpty(in.CustomerID)){
			IMCustomerDAO customerDAO = daoFactory.getIMCustomerDAO();
			customer.CustomerID = in.CustomerID;
			try{
				customer = customerDAO.find(customer);
				if("1".equals(customer.Status)){//1-正常
					if(StringUtils.isEmpty(in.CustomerMobile)){
						in.CustomerMobile  = customer.Mobile;
					}
					in.CustomerAddress = customer.Address;
					if(StringUtils.isEmpty(in.TerminalNo)){//in.TerminalNo优先
						in.TerminalNo = customer.TerminalNo;
					}
					//
				}else{
					in.CustomerID = "";
				}
			}catch(EduException e){
				in.CustomerID = "";
				in.CustomerAddress = "";
			}
		}
		
        //#start 手机号检查:是否为空，是否合法，是否在黑名单
		//手机号合法性检查
		if(!commonDAO.isPhoneNumber(in.CustomerMobile)){
			resultCode = com.dcdzsoft.business.api.Comm.ResultCode.ERROR_MOBILE_ILLEGAL;
			return resultCode;
		}
		//手机号有效性检查，手机号是否在用  2033,"ERROR_MOBILE_INVALID"
		
		//用户手机黑名单检查
		if("1".equals(ControlParam.getInstance().getMobileBlackList())){
			MBMobileBlackListDAO blackListDAO = daoFactory.getMBMobileBlackListDAO();
			JDBCFieldArray whereCols2 = new JDBCFieldArray();
			whereCols2.add("CustomerMobile", in.CustomerMobile);
			if(blackListDAO.isExist(whereCols2) > 0){
				resultCode = com.dcdzsoft.business.api.Comm.ResultCode.ERROR_MOBILE_BLACKLIST;
				return resultCode;
			}
		}
		//OwnerID
		in.CompanyID = ppc.CompanyID;
		//
		if(StringUtils.isNotEmpty(in.TerminalNo)){
			TBTerminalDAO terminalDAO = daoFactory.getTBTerminalDAO();
			JDBCFieldArray whereCols0 = new JDBCFieldArray();
			//whereCols.add("TerminalStatus", "0");//0-正常
			whereCols0.add("TerminalNo", in.TerminalNo);
			if(terminalDAO.isExist(whereCols0) < 1){
				if(StringUtils.isNotEmpty(customer.TerminalNo)&& !in.TerminalNo.equals(customer.TerminalNo)){
					JDBCFieldArray whereCols = new JDBCFieldArray();
					whereCols.add("TerminalNo", customer.TerminalNo);
					if(terminalDAO.isExist(whereCols)>0){
						in.TerminalNo = customer.TerminalNo;
					}else{
						in.TerminalNo = "";
					}
				}else{
					in.TerminalNo = "";
				}
			}
			if(StringUtils.isNotEmpty(in.TerminalNo)){
				if(Constant.COMPANY_SAUDIPOST_ID.equals(ppc.CompanyID)){
					TBTerminal terminal = new TBTerminal();
					terminal.TerminalNo = in.TerminalNo;
					terminal = terminalDAO.find(terminal);
					if(StringUtils.isNotEmpty( terminal.ZoneID)){
						in.ZoneID = terminal.ZoneID;
					}
					
				}
			}
		}
		//azc
		if(StringUtils.isEmpty(in.ZoneID)){
			resultCode = com.dcdzsoft.business.api.Comm.ResultCode.ERROR_AZCID_EMPTY;
			return resultCode;
		}
        IMZoneDAO zoneDAO = daoFactory.getIMZoneDAO();
        IMZone zone = new IMZone();
        zone.ZoneID = in.ZoneID;
        try{
            zone = zoneDAO.find(zone);
        }catch(EduException e){
            JDBCFieldArray whereCols4 = new JDBCFieldArray();
            whereCols4.add("ZoneName", in.ZoneID);//输入ZoneName
            RowSet rset = zoneDAO.select(whereCols4);
            if (RowSetUtils.rowsetNext(rset)) {//AZC name 不能相同。
                in.ZoneID = RowSetUtils.getStringValue(rset, "ZoneID");
            }else{
                resultCode = com.dcdzsoft.business.api.Comm.ResultCode.ERROR_AZCID_NOTEXISTS;
                return resultCode;
            }
        }
        if(StringUtils.isNotEmpty(in.TerminalNo)){
        	//检查AZC是否有柜体使用权限
        	 IMZoneLockerRightDAO zoneLockerRightDAO = daoFactory.getIMZoneLockerRightDAO();
             JDBCFieldArray whereCols4 = new JDBCFieldArray();
             whereCols4.add("ZoneID", in.ZoneID);
             whereCols4.add("TerminalNo", in.TerminalNo);
             if(zoneLockerRightDAO.isExist(whereCols4)<1){
            	 in.TerminalNo = "";
             }
        }
		// 检查订单号是否正在投递
		PreparedWhereExpression whereSQL = new PreparedWhereExpression();
		whereSQL.add("PackageID", in.PackageID);
        whereSQL.add("RunStatus", "0");//V_PTItemStatus   RunStatus=0正在分拣的订单
		String sql = "SELECT COUNT(PackageID) FROM V_PTItemStatus a WHERE 1=1 " + whereSQL.getPreparedWhereSQL() ;
        int count = dbSession.executeQueryCount(sql,whereSQL);
        if(count>0){
        	resultCode = com.dcdzsoft.business.api.Comm.ResultCode.ERROR_ITEMCODE_EXISTS;
			return resultCode;
        }
        
        
        readyPack.PackageID      = in.PackageID;
        readyPack.ZoneID         = in.ZoneID;
        readyPack.CustomerID     = in.CustomerID;
        readyPack.CustomerAddress = in.CustomerAddress;
        readyPack.CustomerName    = in.CustomerName;
        readyPack.CustomerMobile  = in.CustomerMobile;
        readyPack.CompanyID      = in.CompanyID;
        readyPack.PPCID          = in.PPCID;
        readyPack.RefNo          = in.RefNo;
        readyPack.TerminalNo     = in.TerminalNo;
        readyPack.PrintedFlag    = 0;//初始打印标识
        readyPack.ReportOrderID  = "";
        readyPack.DropNum        = 0;
        readyPack.LastModifyTime = sysDateTime;
        readyPack.CreateTime     = sysDateTime;
        readyPack.Remark         = in.Remark;
        readyPack.ItemStatus     = SysDict.ITEM_STATUS_DROP_LISTED;
		
		readyPackDAO.insert(readyPack);
		
		// 调用CommonDAO. addItemLifeCycle(PTItemLifeCycle itemLifeCycle)添加投递订单周期记录。
		//"AddItem("+ppc.PPCName+"=>"+zone.ZoneName+")"
        String[] itemDesc= {ppc.PPCName,zone.ZoneName};
        
        PTItemLifeCycle itemLifeCycle = new PTItemLifeCycle();
        itemLifeCycle.PackageID  = readyPack.PackageID;
        itemLifeCycle.ItemStatus = readyPack.ItemStatus;
        itemLifeCycle.OperatorID    = "system";//系统添加
        itemLifeCycle.OperatorType  = "0";
        itemLifeCycle.LastModifyTime = sysDateTime;
        commonDAO.addItemLifeCycle(Constant.ACTION_CODE_ADDITEM_API, itemLifeCycle, itemDesc);
        
        //#start 订单数据加入发送队列进行发送(状态更新)
        commonDAO.addItemStatusUpdate(readyPack.PPCID, SysDict.ITEM_STATUS_DROP_LISTED, readyPack, "");
        //#end
        
        return resultCode;
    }
}
