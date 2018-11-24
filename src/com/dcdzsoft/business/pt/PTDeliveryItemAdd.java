package com.dcdzsoft.business.pt;


import com.dcdzsoft.EduException;
import com.dcdzsoft.sda.db.*;
import com.dcdzsoft.util.*;
import com.dcdzsoft.dto.function.*;
import com.dcdzsoft.dto.business.*;
import com.dcdzsoft.dao.*;
import com.dcdzsoft.dao.common.*;
import com.dcdzsoft.constant.*;
import com.dcdzsoft.business.ActionBean;
import com.dcdzsoft.business.api.Comm.ResultCode;

/**
 * <p>Title: 自提柜后台运营系统</p>
 * <p>Description: 添加待投递订单 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class PTDeliveryItemAdd extends ActionBean
{

    public int doBusiness(InParamPTDeliveryItemAdd in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        int result = 0;

		//1.	验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(in.OperID)
			||StringUtils.isEmpty(in.PackageID)
			||StringUtils.isEmpty(in.PPCID)
			//||StringUtils.isEmpty(in.ZoneID)
			||StringUtils.isEmpty(in.CustomerMobile))
			throw new EduException(ErrorCode.ERR_PARMERR);

		//2.	调用CommonDAO.isOnline(管理员编号)判断管理员是否在线。
		OPOperOnline operOnline = commonDAO.isOnline(in.OperID);

		//3.	调用UtilDAO.getCurrentDateTime()获得系统日期时间。
		java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();

		//4.	调用PTReadyPackageDAO.isExist(PackageID)判断待投递订单号是否存在，如果存在返回ERR_ PACKAGEIDEXISTS。
		PTReadyPackageDAO readyPackDAO = daoFactory.getPTReadyPackageDAO();
		
		in.PackageID = commonDAO.normalizeItemCode(in.PackageID);
		
		
        // 检查订单号是否正在投递
        PreparedWhereExpression whereSQL = new PreparedWhereExpression();
        whereSQL.add("PackageID", in.PackageID);
        whereSQL.add("RunStatus", "0");//V_PTItemStatus   RunStatus=0正在分拣的订单
        String sql = "SELECT COUNT(PackageID) FROM V_PTItemStatus a WHERE 1=1 " + whereSQL.getPreparedWhereSQL() ;
        int count = dbSession.executeQueryCount(sql,whereSQL);
        if(count>0){
            throw new EduException(ErrorCode.ERR_PACKAGEIDEXISTS);
        }
        
        //5.
		IMZoneDAO zoneDAO = daoFactory.getIMZoneDAO();
		IMZone zone = new IMZone();
		zone.ZoneID = operOnline.ZoneID;
		zone = zoneDAO.find(zone);
		
		//CompanyID检查
		if(StringUtils.isEmpty(in.CompanyID)){
			in.CompanyID = zone.CompanyID;
		}
		IMCompanyDAO companyDAO = daoFactory.getIMCompanyDAO();
		IMCompany company = new IMCompany();
		company.CompanyID = in.CompanyID;
		if(!companyDAO.isExist(company)){
			throw new EduException(ErrorCode.ERR_IMCOMPANYNODATA);
		}
		
		IMPostalProcessingCenterDAO ppcDAO = daoFactory.getIMPostalProcessingCenterDAO();
		IMPostalProcessingCenter ppc = new IMPostalProcessingCenter();
		ppc.PPCID = in.PPCID;
		ppc = ppcDAO.find(ppc);
		
		//CustomerID检查
		if(StringUtils.isNotEmpty(in.CustomerID)){
			IMCustomerDAO customerDAO= daoFactory.getIMCustomerDAO();
			IMCustomer customer = new IMCustomer();
			customer.CustomerID = in.CustomerID;
			try{
				customer = customerDAO.find(customer);
				TBTerminalDAO terminalDAO = daoFactory.getTBTerminalDAO();
				TBTerminal terminal = new TBTerminal();
				terminal.TerminalNo = customer.TerminalNo;
				terminal = terminalDAO.find(terminal);
				if(!operOnline.ZoneID.equals(terminal.ZoneID)){
					throw new EduException(ErrorCode.ERR_OPERAT_ITEMBELONGOTDIFFERENTAZC);
				}
				
				in.CustomerMobile  = customer.Mobile;
				in.CustomerAddress = customer.Address;
				if(StringUtils.isEmpty(in.TerminalNo)){
					in.TerminalNo = customer.TerminalNo;
				}
			}catch(EduException e){
				in.CustomerID = "";
			}
		}
		
		
		
		if("1".equals(ControlParam.getInstance().getMobileFormatCheck())
    			&&!commonDAO.isPhoneNumber(in.CustomerMobile)){
    		throw new EduException(ErrorCode.ERR_BUSINESS_MOBILEFORMATERROR);
    	}
		
		//用户手机黑名单检查
		if("1".equals(ControlParam.getInstance().getMobileBlackList())){
			MBMobileBlackListDAO blackListDAO = daoFactory.getMBMobileBlackListDAO();
			JDBCFieldArray whereCols2 = new JDBCFieldArray();
			whereCols2.add("CustomerMobile", in.CustomerMobile);
			if(blackListDAO.isExist(whereCols2) > 0){
				throw new EduException(ErrorCode.ERR_BUSINESS_MOBILEBLACKLIST);
			}
		}
	
		//#start 插入包裹待投递订单信息。
        PTReadyPackage readyPack = new PTReadyPackage();
        readyPack.PackageID       = in.PackageID;
        readyPack.CreateTime      = sysDateTime;
        readyPack.PPCID           = in.PPCID;
        readyPack.ZoneID          = operOnline.ZoneID;
        readyPack.TerminalNo      = in.TerminalNo;//分配好箱门
        readyPack.CustomerMobile  = in.CustomerMobile;
        readyPack.CustomerAddress = in.CustomerAddress;
        readyPack.CustomerName    = in.CustomerName;
        readyPack.CustomerID      = in.CustomerID;
        readyPack.CompanyID       = in.CompanyID; 
        readyPack.RefNo           = in.RefNo;
        readyPack.TerminalNo      = in.TerminalNo;
        readyPack.PrintedFlag     = 0;//初始打印标识
        readyPack.ReportOrderID   = "";
        readyPack.LastModifyTime = sysDateTime;
        
        //readyPack.BoxNo       = "0";
        readyPack.DropNum     = 0;
        readyPack.ItemStatus  = SysDict.ITEM_STATUS_DROP_LISTED;
        readyPack.Remark      = in.Remark;
        
        readyPackDAO.insert(readyPack);
        //#end
        
        //"AddItem("+ppc.PPCName+"=>"+zone.ZoneName+")"
        String[] itemDesc= {ppc.PPCName,zone.ZoneName};
        
        //#start 添加投递订单周期记录。
        PTItemLifeCycle itemLifeCycle = new PTItemLifeCycle();
        itemLifeCycle.PackageID  = readyPack.PackageID;
        itemLifeCycle.ItemStatus = SysDict.ITEM_STATUS_DROP_LISTED;
        itemLifeCycle.OperatorID    = in.OperID;
        itemLifeCycle.OperatorType  = ""+operOnline.OperType;
        itemLifeCycle.LastModifyTime = sysDateTime;
        commonDAO.addItemLifeCycle(Constant.ACTION_CODE_ADDITEM, itemLifeCycle, itemDesc);
        //#end
        
        //#start 订单数据加入发送队列进行发送(状态更新)
        commonDAO.addItemStatusUpdate(readyPack.PPCID, readyPack.ItemStatus, readyPack, SysDict.TRANSFER_TYPE_ADD_ITEM);
        //#end

        return result;
    }
}
