package com.dcdzsoft.business.pt;

import javax.sql.RowSet;

import com.dcdzsoft.EduException;
import com.dcdzsoft.sda.db.*;
import com.dcdzsoft.util.*;
import com.dcdzsoft.util.LockUtil.MyLock;
import com.dcdzsoft.dto.function.*;
import com.dcdzsoft.dto.business.*;
import com.dcdzsoft.dao.*;
import com.dcdzsoft.dao.common.*;
import com.dcdzsoft.client.web.MBWebClientAdapter;
import com.dcdzsoft.constant.*;
import com.dcdzsoft.business.ActionBean;
import com.dcdzsoft.businessproxy.MonitorProxy;

/**
 * <p>Title: 自提柜后台运营系统</p>
 * <p>Description: 请求服务器分配可投递箱门 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class PTAllocateeBox extends ActionBean
{

    public OutParamPTAllocateeBox doBusiness(InParamPTAllocateeBox in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        OutParamPTAllocateeBox out = new OutParamPTAllocateeBox();

		//1.	验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(in.TerminalNo)
			||StringUtils.isEmpty(in.PostmanID)
			||StringUtils.isEmpty(in.BoxType)
			||StringUtils.isEmpty(in.PackageID)
			)
			throw new EduException(ErrorCode.ERR_PARMERR);

		java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();

		//#start 验证投递员权限  
		PMPostmanDAO postmanDAO = daoFactory.getPMPostmanDAO();
		PMPostman postman = new PMPostman();
		postman.PostmanID = in.PostmanID;
		try {
			postman = postmanDAO.find(postman);
		} catch (Exception e) {
			throw new EduException(ErrorCode.ERR_POSTMANNOTEXISTS);
		}
		//投递员状态检查
        if(!postman.PostmanStatus.equals("0")){
        	//投递员状态
        	throw new EduException(ErrorCode.ERR_POSTMANHAVECANCELED);
        }
		//投递权限检查，并 根据登录投递员类型，判断订单类型1-投递，2-直投，3-寄件直投
        int itemType = 0;
        //投递权限
        switch(postman.PostmanType){
        case SysDict.POSTMAN_TYPE_POST://
        	if(!"1".equals(postman.DropRight)){
        		throw new EduException(ErrorCode.ERR_FORBIDPOSTMANDROP);
        	}
        	itemType = 1;
        	break;
        case SysDict.POSTMAN_TYPE_DAD://
        	if(!"1".equals(postman.DADRight)){
        		throw new EduException(ErrorCode.ERR_FORBIDPOSTMANDROP);
        	}
        	itemType = 2;
        	break;
        case SysDict.POSTMAN_TYPE_DAD_BUSINESSPARTNER://
        	if(!"1".equals(postman.DADRight)){
        		throw new EduException(ErrorCode.ERR_FORBIDPOSTMANDROP);
        	}
        	itemType = 3;
        	break;
        default:
        	//用户名或密码错误
        	throw new EduException(ErrorCode.ERR_FORBIDPOSTMANDROP);
        }
        //验证投递员所属分拣区域
        IMZoneDAO zoneDAO = daoFactory.getIMZoneDAO();
        IMZone zone = new IMZone();
        zone.ZoneID = postman.ZoneID;
        try {
        	zone = zoneDAO.find(zone);
        } catch (Exception e) {
            throw new EduException(ErrorCode.ERR_FORBIDPOSTMANDROP);
        }
        // 验证柜体权限(登陆时候验证)
        // commonDAO.checkManTerminalRight(postman, in.TerminalNo);
		//#end
		
		//#start 用户手机黑名单查询
		MBMobileBlackListDAO blackListDAO = daoFactory.getMBMobileBlackListDAO();
		JDBCFieldArray whereCols2 = new JDBCFieldArray();
		whereCols2.add("CustomerMobile", in.CustomerMobile);
		
		if(blackListDAO.isExist(whereCols2) > 0)
			out.InBlackList = "1"; //不在黑名单中
		else
			out.InBlackList = "0";
		//#end
		
    	//#start 判断是否已经投递过
        PTInBoxPackageDAO inboxPackDAO = daoFactory.getPTInBoxPackageDAO();
 		JDBCFieldArray whereCols1 = new JDBCFieldArray();
 		whereCols1.add("PackageID", in.PackageID);
 		whereCols1.add("TerminalNo", in.TerminalNo);

 		if (inboxPackDAO.isExist(whereCols1) > 0){
 			throw new EduException(ErrorCode.ERR_PACKHAVEDELIVERYD);
 		}
 		//#end
 		
 		//待投递订单查询
 		String oldBoxNo = "";
        PTReadyPackageDAO readyPackDAO = daoFactory.getPTReadyPackageDAO();
  		PTReadyPackage readyPack = new PTReadyPackage();
  		readyPack.PackageID = in.PackageID;
        if(itemType == 1){//普通投递，检查订单号是否在待投递订单表中，并且订单状态为INTRANSIT_OUT
            JDBCFieldArray whereCols0 = new JDBCFieldArray();
            whereCols0.add("PackageID", in.PackageID);
            whereCols0.add("ItemStatus", SysDict.ITEM_STATUS_DROP_INTRANSIT_OUT);
            if(readyPackDAO.isExist(whereCols0) <= 0){
                throw new EduException(ErrorCode.ERR_PACKAGENOTEXISTS);
            }
            readyPack.PackageID = in.PackageID;
  			readyPack = readyPackDAO.find(readyPack);
  			oldBoxNo = readyPack.BoxNo;
        }else {//直投订单 itemType 2 or 3
            JDBCFieldArray whereCols00 = new JDBCFieldArray();
            whereCols00.add("PackageID", in.PackageID);
            //whereCols00.add("ItemStatus", "<>",SysDict.ITEM_STATUS_DROP_D_CREATE);
            whereCols00.addSQL(" AND (");
            whereCols00.addSQL(" ItemStatus <>"+StringUtils.addQuote(SysDict.ITEM_STATUS_DROP_D_CREATE));
            whereCols00.addSQL(" OR DropAgentID<>"+StringUtils.addQuote(in.PostmanID));
            whereCols00.addSQL(")"); 
            if(readyPackDAO.isExist(whereCols00) > 0){
                throw new EduException(ErrorCode.ERR_PACKHAVEDELIVERYD);
            }
            readyPack.PackageID = in.PackageID;
            try{
                readyPack = readyPackDAO.find(readyPack);
                oldBoxNo = readyPack.BoxNo;
                readyPackDAO.delete(readyPack);
            }catch(EduException e){
                
            }
            
            if(itemType == 3){//DAD-B
                //#start 寄件订单是否创建
                DMCollectionParcelDAO collectionParcelDAO = daoFactory.getDMCollectionParcelDAO();
                JDBCFieldArray whereCols = new JDBCFieldArray();
                whereCols.add("PackageID", in.PackageID);
                whereCols.add("TerminalNo", in.TerminalNo);//只能在订单创建时选择的客户收件柜体进行投递
                whereCols.add("ReturnFlag","<>", "1");
                //whereCols.add("ItemStatus", SysDict.ITEM_STATUS_COLLECT_CREATED);
                String ItemStatus = SysDict.ITEM_STATUS_COLLECT_CREATED;//+","//50
                whereCols.addSQL(utilDAO.getFlagInSQL("ItemStatus", ItemStatus));
                //java.util.List<String> orderByField = new java.util.LinkedList<String>();
                //orderByField.add("CreateTime DESC");
                RowSet rset = collectionParcelDAO.select(whereCols);
                if(RowSetUtils.rowsetNext(rset)){
                    DMCollectionParcel collectionParcel = new DMCollectionParcel();
                    collectionParcel.PackageID  = in.PackageID;
                    collectionParcel.CreateTime = RowSetUtils.getTimestampValue(rset, "CreateTime");
                    collectionParcel = collectionParcelDAO.find(collectionParcel);
                    
                    ControlParam ctrlParam = ControlParam.getInstance();

                    if("1".equals(ctrlParam.getModifyMobileFromLocker())
                            &&commonDAO.isPhoneNumber(in.CustomerMobile)){//允许在终端修改收件人手机号
                        collectionParcel.CustomerMobile = in.CustomerMobile;
                    }
                    if(!in.BoxType.equals(collectionParcel.ParcelSize)){//
                        throw new EduException(ErrorCode.ERR_BOXSIZEFAULT);
                    }
                    readyPack.ParcelSize = in.BoxType;
                    readyPack.BoxType    = in.BoxType;
                    readyPack.CustomerAddress = collectionParcel.CustomerAddress;
                    readyPack.CustomerName    = collectionParcel.CustomerName;
                    readyPack.CustomerMobile  = collectionParcel.CustomerMobile;
                    readyPack.CustomerID      = collectionParcel.CustomerID;
                    readyPack.Remark = "DAD-B,BP="+collectionParcel.BPartnerID+",PM="+in.PostmanID;
                }else {
                    throw new EduException(ErrorCode.ERR_COLLECT_ITEM_NOT_EXISTS);
                }
                //#end
            }else{//2-DAD
                //
                JDBCFieldArray whereCols = new JDBCFieldArray();
                whereCols.add("PackageID", in.PackageID);
                DMCollectionParcelDAO collectionParcelDAO = daoFactory.getDMCollectionParcelDAO();
                if(collectionParcelDAO.isExist(whereCols)>0){
                    throw new EduException(ErrorCode.ERR_PACKHAVEDELIVERYD);
                }
                if(!"1".equals(ControlParam.getInstance().getDadDropE1Item())){
                    DMHistoryItemDAO historyItemDAO = daoFactory.getDMHistoryItemDAO();
                    if(historyItemDAO.isExist(whereCols)>0){
                        throw new EduException(ErrorCode.ERR_PACKHAVEDELIVERYD);
                    }
                }
                
                readyPack.CustomerMobile = in.CustomerMobile;
                readyPack.ParcelSize = in.BoxType;
                readyPack.BoxType    = in.BoxType;
                readyPack.Remark = "DAD";
            }
            
            //因为历史订单表中的订单可以重新被分配投递，直投时需要对订单号进行检查
            PTDeliverHistoryDAO historyPackDAO = daoFactory.getPTDeliverHistoryDAO();
            JDBCFieldArray whereCols0 = new JDBCFieldArray();
            whereCols0.add("PackageID", in.PackageID);
            whereCols0.add("RunStatus", "0");

            if(historyPackDAO.isExist(whereCols0) >0){
                throw new EduException(ErrorCode.ERR_PACKHAVEDELIVERYD);
            }
            
            //#start 创建待投递订单
            readyPack.PackageID  = in.PackageID;
            readyPack.CreateTime = sysDateTime;
            readyPack.TerminalNo = in.TerminalNo;
            readyPack.PPCID      = Constant.DEFAULT_PPC_ID;//直投默认PPC
            readyPack.DropAgentID = in.PostmanID;
            readyPack.DropNum = 0;
            readyPack.ItemStatus = SysDict.ITEM_STATUS_DROP_D_CREATE;
            //readyPack.BoxNo     = "";
            readyPack.LastModifyTime = sysDateTime;
            readyPack.CompanyID  = zone.CompanyID;
            readyPack.ZoneID     = zone.ZoneID;
            //插入
            readyPack.ReportOrderID = commonDAO.getNextOrderID(SysDict.ORDER_ID_TYPE_D_DROP, postman.ZoneID, in.TerminalNo);
            readyPackDAO.insert(readyPack);
            //#end
        }
        
        ////////////////////////
        TBServerBoxDAO boxDAO = daoFactory.getTBServerBoxDAO();
        boolean isLockedOldBox = false;//原箱未被订单占用，但是开箱失败（箱门故障、或有遗留物品）时，锁定原箱
        if(StringUtils.isNotEmpty(oldBoxNo)){
            JDBCFieldArray whereCols12 = new JDBCFieldArray();
            whereCols12.add("BoxNo", oldBoxNo);
            whereCols12.add("TerminalNo", in.TerminalNo);

            if (inboxPackDAO.isExist(whereCols1) == 0){//未被订单占用,则锁定原箱：可能是箱门故障、或有箱中有遗留物品
                isLockedOldBox = true;
            }
        }
        if(isLockedOldBox){
            JDBCFieldArray setCols13 = new JDBCFieldArray();
            JDBCFieldArray whereCols13 = new JDBCFieldArray();

            setCols13.add("BoxStatus", "1");//1-锁定，2-故障，3-故障锁定
            whereCols13.add("BoxNo", oldBoxNo);//原始预定的箱号
            whereCols13.add("TerminalNo", in.TerminalNo);
            whereCols13.add("BoxStatus", "0");
            boxDAO.update(setCols13, whereCols13);
        }
 		
 		//分配可用箱号
 		String newBoxNo = "";
 		//#start 获取柜体锁,获取空箱
 		MyLock terminalLock = commonDAO.allocTermianlLock(in.TerminalNo);
    	try{
    		synchronized (terminalLock) {
        		//重新分配空箱
    			newBoxNo = commonDAO.getOneCompFreeBox(zone.CompanyID,in.TerminalNo, in.BoxType);
        		if(!newBoxNo.isEmpty()){
        			JDBCFieldArray setCols = new JDBCFieldArray();
                    JDBCFieldArray whereCols = new JDBCFieldArray();
                    setCols.add("BoxNo", newBoxNo);
                    setCols.add("LastModifyTime", sysDateTime);
                    
                    whereCols.add("PackageID", in.PackageID);
                    whereCols.add("TerminalNo", in.TerminalNo);
                    
                    int result = readyPackDAO.update(setCols, whereCols);
                    if(result != 1){
                    	newBoxNo = "";
                    }
        		}
        	}
    	}catch(EduException e){
    		newBoxNo = "";
    	}finally{
    		commonDAO.releaseTermianlLocks(in.TerminalNo);
    	}
    	
    	if(StringUtils.isNotEmpty(newBoxNo)){//箱分配成功
            out.BoxNo = newBoxNo;//新预定的箱号
            
            if(isLockedOldBox &&"1".equals(ControlParam.getInstance().getRenewBoxAndLockedOldBox())){
                //原箱号保持锁定，产生告警
                try{
                    //生成告警信息
                    commonDAO.insertAlert(in.TerminalNo,  SysDict.ALERT_TYPE_DROPOPENBOXFAIL, SysDict.ALERT_LEVEL_COMMON, oldBoxNo,"Open box fail!-to be locked");
                    //广播消息
                    MonitorProxy.broadcastAlert(in.TerminalNo, SysDict.ALERT_TYPE_DROPOPENBOXFAIL, SysDict.ALERT_LEVEL_COMMON, oldBoxNo, "");
                }catch (EduException e){
                }
            }else if(isLockedOldBox){
                //解除锁定
                JDBCFieldArray setCols3 = new JDBCFieldArray();
                JDBCFieldArray whereCols3 = new JDBCFieldArray();

                setCols3.add("BoxStatus", "0");//1-锁定，2-故障，3-故障锁定
                whereCols3.add("BoxNo", oldBoxNo);//原始预定的箱号
                whereCols3.add("TerminalNo", in.TerminalNo);
                whereCols3.add("BoxStatus", "1");
                boxDAO.update(setCols3, whereCols3);
            }
 		}else{
 			if(itemType == 2 || itemType == 3){//直投或寄件就近直投
 				JDBCFieldArray whereCols = new JDBCFieldArray();
                whereCols.add("PackageID", in.PackageID);
                //whereCols.add("TerminalNo", in.TerminalNo);
                //whereCols.add("ItemStatus", SysDict.ITEM_STATUS_DROP_D_CREATE);
                readyPackDAO.delete(whereCols);
 			}
 			throw new EduException(ErrorCode.ERR_NOFREEDBOX);
 		}
		
        return out;
    }
}
