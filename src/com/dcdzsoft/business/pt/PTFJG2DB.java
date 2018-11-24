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
 * <p>Description: 分拣柜发送建包信息到包裹柜，写入数据库 </p>
 * <p>Copyright: Copyright (c) 2018.4.17</p>
 * <p>Company: dcdzsoft</p>
 * @author 王中立
 * @version 1.0
 */

public class PTFJG2DB extends ActionBean
{
    public String doBusiness(InParamPTFJG2DB in) throws EduException
    {
    	String result = "";
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        RowSet rset = null;

		//1.验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(in.PackageID)
			||StringUtils.isEmpty(in.ZoneID)
			||StringUtils.isEmpty(in.TerminalNo))
			throw new EduException(ErrorCode.ERR_PARMERR);
		
		//调用UtilDAO.getCurrentDateTime()获得系统日期时间。
        java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();
        
		PTReadyPackageDAO readyPackDAO = daoFactory.getPTReadyPackageDAO();
		PTReadyPackage readyPack = new PTReadyPackage();
		readyPack.PackageID = in.PackageID;
		readyPack = readyPackDAO.find(readyPack);
		// 更新订单状态。
		String nextStatus = SysDict.ITEM_STATUS_DROP_ASSIGNED;//已分配状态，		
		String nowStatus = readyPack.ItemStatus;
		if(StringUtils.isEmpty(readyPack.ZoneID) && (!readyPack.ZoneID.equals(in.ZoneID))){
			return result;
		}
		if(!SysDict.ITEM_STATUS_DROP_LISTED.equals(nowStatus) 
			&&!SysDict.ITEM_STATUS_DROP_RECEIVED.equals(nowStatus)  ){ //如果不是list状态，
			return result;
		}

		JDBCFieldArray setCols = new JDBCFieldArray();
        JDBCFieldArray whereCols = new JDBCFieldArray();
        //如果订单没有分配目的地设备号，分拣柜端强投上传目的地设备编号
        if(!in.TerminalNo.equals(readyPack.TerminalNo)){
        	setCols.add("TerminalNo", in.TerminalNo);
        }
        setCols.add("ItemStatus", nextStatus);
        setCols.add("LastModifyTime", sysDateTime);
        whereCols.add("PackageID", in.PackageID);
        
		readyPackDAO.update(setCols, whereCols);
		
        //"Assign("+in.TerminalNo+"-"+in.BoxType+"-{BoxNo})"
        String[] itemDesc= {in.TerminalNo,"Sorting cabinet", "0"};
        
        //#start记录订单状态更新信息
        PTItemLifeCycle itemLifeCycle = new PTItemLifeCycle();
        itemLifeCycle.PackageID  = in.PackageID;
        itemLifeCycle.ItemStatus = nextStatus;
        itemLifeCycle.OperatorID    = in.OperID;// 建包操作员是什么？
//        itemLifeCycle.OperatorType  = ""+operOnline.OperType;
        itemLifeCycle.LastModifyTime = sysDateTime;
        itemLifeCycle.ZoneID = in.ZoneID;
        itemLifeCycle.LastStatus = readyPack.ItemStatus;
        if(readyPack.LastModifyTime == null){
        	readyPack.LastModifyTime = sysDateTime;
        }
        itemLifeCycle.StatusTime = ((int)((sysDateTime.getTime() - readyPack.LastModifyTime.getTime()) / 60000L));
        commonDAO.addItemLifeCycle(Constant.ACTION_CODE_ASSIGN, itemLifeCycle, itemDesc);
        //#end
        
        //#start 订单数据加入发送队列进行发送(状态更新)
    	commonDAO.addItemStatusUpdate(readyPack.PPCID, nextStatus, readyPack, "");

        return result;
    }
}
