package com.dcdzsoft.business.dm;

import java.util.UUID;

import javax.sql.RowSet;

import com.dcdzsoft.EduException;
import com.dcdzsoft.sda.db.*;
import com.dcdzsoft.sda.security.SecurityUtils;
import com.dcdzsoft.sms.SMSManager;
import com.dcdzsoft.util.*;
import com.dcdzsoft.dto.function.*;
import com.dcdzsoft.dto.business.*;
import com.dcdzsoft.dao.*;
import com.dcdzsoft.dao.common.*;
import com.dcdzsoft.config.ApplicationConfig;
import com.dcdzsoft.constant.*;
import com.dcdzsoft.business.ActionBean;

/**
 * <p>Title: 自提柜后台运营系统</p>
 * <p>Description: 商业伙伴账户余额更新 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class DMBPBalanceUpdate extends ActionBean
{

    public OutParamDMBPBalanceUpdate doBusiness(InParamDMBPBalanceUpdate in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        OutParamDMBPBalanceUpdate out = new OutParamDMBPBalanceUpdate();

		//1.验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(in.OperID)
			||StringUtils.isEmpty(in.AdminPassword)
			||StringUtils.isEmpty(in.BPartnerID)
			||in.Amount <= 0 
			||StringUtils.isEmpty(in.TradeWaterNo))
			throw new EduException(ErrorCode.ERR_PARMERR);


		//2.调用CommonDAO.isOnline(操作员编号)判断操作员是否在线。
		OPOperOnline operOnline = commonDAO.isOnline(in.OperID);

		//3.调用UtilDAO.getCurrentDateTime()获得系统日期时间。
		java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();
		java.sql.Date sysDate = DateUtils.toSQLDate(sysDateTime);
		
		in.AdminPassword = SecurityUtils.md5(in.AdminPassword);
		
		//验证操作员密码
		OPOperatorDAO operatorDAO = daoFactory.getOPOperatorDAO();
	    OPOperator opOperator = new OPOperator();
	    opOperator.OperID = in.OperID;
	    opOperator = operatorDAO.find(opOperator);

	    //如果旧密码和查询出的密码不符，返回ERR_OPERPWDWRONG。
	    if(!opOperator.OperPassword.equals(in.AdminPassword))
	    	throw new EduException(ErrorCode.ERR_OPERPWDWRONG);
	       
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
            	throw new EduException(ErrorCode.ERR_BUSINESSPARTNERNOTEXISTS);
            }
        }
        in.BPartnerID = partnerid;
        
		IMBusinessPartner businessPartner = new IMBusinessPartner();
		businessPartner.BPartnerID = in.BPartnerID;
		businessPartner = businessPartnerDAO.find(businessPartner);
		//#end
		
		//#start 创建充值请求
		PYTopUpReqDAO topUpReqDAO = daoFactory.getPYTopUpReqDAO();
		PYTopUpReq topUpReq = new PYTopUpReq();
		topUpReq.TradeWaterID = in.TradeWaterNo;
		if(!topUpReqDAO.isExist(topUpReq)){
			topUpReq.UserID     = businessPartner.BPartnerID;
			topUpReq.UserCode   = businessPartner.BPartnerName;
			topUpReq.Amount     = in.Amount;
			topUpReq.Status     = SysDict.TOPUP_STATUS_DOING;//1-充值中，2-充值成功；3-充值失败；
			topUpReq.TimeoutIns = 60*60*12;//设置请求超时时间，12小时？？？？？？？？
			topUpReq.CreateTime = sysDateTime;
			topUpReq.LastModifyTime = sysDateTime;
			topUpReq.Remark = in.Remark;
			topUpReqDAO.insert(topUpReq);
		}
		//#end
		topUpReq = topUpReqDAO.find(topUpReq);
		
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
			/*out.TradeWaterNo = topUpReq.TradeWaterID;
			out.Amount       = topUpReq.Amount;
			out.BPartnerID   = businessPartner.BPartnerID;
			out.BPartnerName = businessPartner.BPartnerName;
			out.Balance      = businessPartner.Balance;
			return out;*/
			throw new EduException(ErrorCode.ERR_UPTOP_TRADENO_INVALID);
		case SysDict.TOPUP_STATUS_TIMEOUT:
			throw new EduException(ErrorCode.ERR_UPTOP_TRADE_TIMEOUT);
		default:
			throw new EduException(ErrorCode.ERR_UPTOP_TRADE_NOT_EXISTS);
		}
		//#end		
		
		//#start 充值中，检查充值对象、充值金额是否一致
		if(!businessPartner.BPartnerID.equals(topUpReq.UserID)){
			throw new EduException(ErrorCode.ERR_UPTOP_USERID_UNEQUAL);
		}
		if(in.Amount < 0 || in.Amount != topUpReq.Amount){//
			throw new EduException(ErrorCode.ERR_UPTOP_AMOUNT_UNEQUAL);
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
        
        out.TradeWaterNo = in.TradeWaterNo;
        out.Amount   = in.Amount;
        out.BPartnerID   = businessPartner.BPartnerID;
		out.BPartnerName = businessPartner.BPartnerName;
        out.Balance      = businessPartner.Balance;
        
        //发送短信
        ControlParam ctrlParam = ControlParam.getInstance();
        if(StringUtils.isNotEmpty(ApplicationConfig.getInstance().getSendShortMsg()) 
        		&&commonDAO.isPhoneNumber(businessPartner.Mobile)
        		&&"1".equals(ctrlParam.getSendTopupMsgSMS()))
        {
        	SMSManager.getInstance().sentTopUpMsg(businessPartner.Username, 
        			Constant.dateFromat.format(sysDateTime),
        			out.Amount,
        			out.Balance,
        			out.TradeWaterNo,
        			businessPartner.Mobile);
        }
        
		// 调用CommonDAO.addOperatorLog(OperID，功能编号，系统日期时间，“”)
		OPOperatorLog log = new OPOperatorLog();
		log.OperID = in.OperID;//
		log.FunctionID = in.getFunctionID();
		log.OccurTime = sysDateTime;
		log.StationAddr = operOnline.LoginIPAddr;
		log.Remark = "Manual-TOPUP:"+businessPartner.BPartnerID+"-"+in.TradeWaterNo+"-"+in.Amount+"-"+businessPartner.Balance;

		commonDAO.addOperatorLog(log);

        return out;
    }
}
