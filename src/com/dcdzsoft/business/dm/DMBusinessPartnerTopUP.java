package com.dcdzsoft.business.dm;


import com.dcdzsoft.EduException;
import com.dcdzsoft.sda.db.*;
import com.dcdzsoft.sequence.SequenceGenerator;
import com.dcdzsoft.util.*;
import com.dcdzsoft.dto.function.*;
import com.dcdzsoft.dto.business.*;
import com.dcdzsoft.dao.*;
import com.dcdzsoft.constant.*;
import com.dcdzsoft.business.ActionBean;

/**
 * <p>Title: 自提柜后台运营系统</p>
 * <p>Description: 商业合作伙伴账户充值 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class DMBusinessPartnerTopUP extends ActionBean
{

    public OutParamDMBusinessPartnerTopUP doBusiness(InParamDMBusinessPartnerTopUP in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        OutParamDMBusinessPartnerTopUP out = new OutParamDMBusinessPartnerTopUP();

		//1.	验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(in.BPartnerID)
			||StringUtils.isEmpty(in.TradeWaterNo)
			||in.Amount <= 0.0 )
			throw new EduException(ErrorCode.ERR_PARMERR);

		//2.	调用CommonDAO.isOnline(操作员编号)判断操作员是否在线。
		//OPOperOnline operOnline = commonDAO.isOnline(in.OperID);

		//3.	调用UtilDAO.getCurrentDateTime()获得系统日期时间。
		java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();
		java.sql.Date sysDate = DateUtils.toSQLDate(sysDateTime);
		
		//#start 检查合作伙伴账号
		IMBusinessPartnerDAO businessPartnerDAO = daoFactory.getIMBusinessPartnerDAO();
		IMBusinessPartner businessPartner = new IMBusinessPartner();
        businessPartner.BPartnerID = in.BPartnerID;
        
		businessPartner = businessPartnerDAO.find(businessPartner);
		//#end
				
		//#start 检测交易请求
		//交易流水号
		PYTopUpReqDAO topUpReqDAO = daoFactory.getPYTopUpReqDAO();
		PYTopUpReq topUpReq = new PYTopUpReq();
		topUpReq.TradeWaterID = in.TradeWaterNo;
		try{
			topUpReq = topUpReqDAO.find(topUpReq);
		}catch(EduException e){
			throw new EduException(ErrorCode.ERR_UPTOP_TRADE_NOT_EXISTS);
		}
		
		//#start 充值中，检查充值对象、充值金额是否一致
		if(!businessPartner.BPartnerID.equals(topUpReq.UserID)){
			throw new EduException(ErrorCode.ERR_UPTOP_USERID_UNEQUAL);
		}
		if(in.Amount < 0 || in.Amount != topUpReq.Amount){//
			throw new EduException(ErrorCode.ERR_UPTOP_AMOUNT_UNEQUAL);
		}
		//#end
		
		//#start 检查账单状态
		switch(topUpReq.Status){
		case SysDict.TOPUP_STATUS_DOING:
			//超时时间检查
			long timeoutInMs = (sysDateTime.getTime() - topUpReq.CreateTime.getTime());
			if(timeoutInMs > (topUpReq.TimeoutIns*1000)){
				JDBCFieldArray setCols1 = new JDBCFieldArray();
		        JDBCFieldArray whereCols1 = new JDBCFieldArray();
		        setCols1.add("Status", SysDict.TOPUP_STATUS_TIMEOUT);
		        setCols1.add("LastModifyTime", sysDateTime);
		        
		        whereCols1.add("TradeWaterID", topUpReq.TradeWaterID);
		        topUpReqDAO.update(setCols1, whereCols1);
		        
		        throw new EduException(ErrorCode.ERR_UPTOP_TRADE_TIMEOUT);
			}
			break;
		case SysDict.TOPUP_STATUS_SUCCESS:
			out.TradeWaterNo = topUpReq.TradeWaterID;
			out.Amount       = topUpReq.Amount;
			out.UserID       = topUpReq.UserID;
			out.UserCode     = topUpReq.UserCode;
			out.UserName     = businessPartner.Username;
			out.Balance      = businessPartner.Balance;
			out.Status       = 0;
			return out;
		case SysDict.TOPUP_STATUS_TIMEOUT:
			throw new EduException(ErrorCode.ERR_UPTOP_TRADE_TIMEOUT);
		default:
			throw new EduException(ErrorCode.ERR_UPTOP_TRADE_NOT_EXISTS);
		}
		//#end
		
		//#start 更新充值请求状态
		JDBCFieldArray setCols1 = new JDBCFieldArray();
        JDBCFieldArray whereCols1 = new JDBCFieldArray();
        setCols1.add("Status", SysDict.TOPUP_STATUS_SUCCESS);
        setCols1.add("LastModifyTime", sysDateTime);
        
        whereCols1.add("TradeWaterID", topUpReq.TradeWaterID);
        topUpReqDAO.update(setCols1, whereCols1);
		//#end
		
        //#start 更新账户余额
        commonDAO.doBusinessPartnerTopUp(businessPartner, in.Amount, in.TradeWaterNo,topUpReq.Remark);	
        //#end
        
        out.Amount   = in.Amount;
        out.UserID   = businessPartner.BPartnerID;
        out.UserCode = businessPartner.BPartnerName;
        out.UserName = businessPartner.Username;
        out.TradeWaterNo = in.TradeWaterNo;
        out.Balance      = businessPartner.Balance;
        
		// 调用CommonDAO.addOperatorLog(OperID，功能编号，系统日期时间，“”)
		OPOperatorLog log = new OPOperatorLog();
		log.OperID = in.OperID;//
		log.FunctionID = in.getFunctionID();
		log.OccurTime = sysDateTime;
		log.StationAddr = "";//operOnline.LoginIPAddr
		log.Remark = "TOPUP:"+businessPartner.BPartnerID+"-"+in.TradeWaterNo+"-"+in.Amount+"-"+businessPartner.Balance;

		commonDAO.addOperatorLog(log);

        return out;
    }
}
