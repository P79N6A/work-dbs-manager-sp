package com.dcdzsoft.business.dm;

import java.util.UUID;

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
 * <p>Description: 商业伙伴账户充值请求 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class DMBusinessPartnerTopUpReq extends ActionBean
{

    public OutParamDMBusinessPartnerTopUpReq doBusiness(InParamDMBusinessPartnerTopUpReq in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        OutParamDMBusinessPartnerTopUpReq out = new OutParamDMBusinessPartnerTopUpReq();

		//1.	验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(in.BPartnerID)
			||in.Amount <= 0.0 )
			throw new EduException(ErrorCode.ERR_PARMERR);

		//3.	调用UtilDAO.getCurrentDateTime()获得系统日期时间。
		java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();
		java.sql.Date sysDate = DateUtils.toSQLDate(sysDateTime);

		
		//#start 验证商业伙伴存在
		String partnerid = "";
		IMBusinessPartnerDAO businessPartnerDAO = daoFactory.getIMBusinessPartnerDAO();
		JDBCFieldArray whereCols0 = new JDBCFieldArray();
        whereCols0.add("BPartnerID", in.BPartnerID);
        RowSet rset = businessPartnerDAO.select(whereCols0);
        if (RowSetUtils.rowsetNext(rset)) {
        	partnerid = RowSetUtils.getStringValue(rset, "BPartnerID");
        } else {
            whereCols0 = new JDBCFieldArray();
            whereCols0.add("Username", in.BPartnerID);
            rset = businessPartnerDAO.select(whereCols0);

            if (RowSetUtils.rowsetNext(rset)) {
            	partnerid = RowSetUtils.getStringValue(rset, "BPartnerID");
            } else {
            	throw new EduException(ErrorCode.ERR_IMBUSINESSPARTNERNODATA);
            }
        }
        in.BPartnerID = partnerid;
        
		IMBusinessPartner businessPartner = new IMBusinessPartner();
		businessPartner.BPartnerID = in.BPartnerID;
		businessPartner = businessPartnerDAO.find(businessPartner);
		//#end
		
		//#start 创建充值请求
		if(StringUtils.isEmpty(in.TradeWaterNo)){
			UUID uuid = UUID.randomUUID();
			in.TradeWaterNo = uuid.toString().replace("-", "");
		}
		PYTopUpReqDAO topUpReqDAO = daoFactory.getPYTopUpReqDAO();
		PYTopUpReq topUpReq = new PYTopUpReq();
		topUpReq.TradeWaterID = in.TradeWaterNo;
		if(!topUpReqDAO.isExist(topUpReq)){
			topUpReq.UserID     = businessPartner.BPartnerID;
			topUpReq.UserCode   = businessPartner.BPartnerName;
			topUpReq.Amount     = in.Amount;
			topUpReq.Status     = "1";//1-充值中，2-充值成功；3-充值失败；
			topUpReq.TimeoutIns = 60*60*12;//设置请求超时时间，12小时？？？？？？？？
			topUpReq.CreateTime = sysDateTime;
			topUpReq.LastModifyTime = sysDateTime;
			topUpReq.Remark = in.Remark;
			topUpReqDAO.insert(topUpReq);
		}
		//#end
		
		//#start 返回请求内容
		out.setTradeWaterNo(topUpReq.TradeWaterID);
		out.setUserID(businessPartner.BPartnerID);
		out.setUserCode(businessPartner.BPartnerName);
		out.setAmount (in.Amount);
		out.setBalance(businessPartner.Balance);
		//#end 
		
		// 调用CommonDAO.addOperatorLog(OperID，功能编号，系统日期时间，“”)
		OPOperatorLog log = new OPOperatorLog();
		log.OperID = in.BPartnerID;
		log.FunctionID = in.getFunctionID();
		log.OccurTime = sysDateTime;
		log.StationAddr = "";
		log.Remark = "TopUp Req:TradeWaterNo="+in.TradeWaterNo+",BPartnerID="+in.BPartnerID+",Amount="+in.Amount;

		commonDAO.addOperatorLog(log);


        return out;
    }
}
