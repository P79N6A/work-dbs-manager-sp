package com.dcdzsoft.business.rm;

import javax.sql.RowSet;

import com.dcdzsoft.EduException;
import com.dcdzsoft.sda.db.*;
import com.dcdzsoft.sda.security.SecurityUtils;
import com.dcdzsoft.util.*;
import com.dcdzsoft.dto.function.*;
import com.dcdzsoft.dto.business.*;
import com.dcdzsoft.dao.*;
import com.dcdzsoft.dao.common.*;
import com.dcdzsoft.constant.*;
import com.dcdzsoft.business.ActionBean;

/**
 * <p>Title: 智能自助包裹柜系统</p>
 * <p>Description: 申请开箱密码 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author 王中立
 * @version 1.0
 */

public class RMRequestOpenBoxKey extends ActionBean
{

    public OutParamRMRequestOpenBoxKey doBusiness(InParamRMRequestOpenBoxKey in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        OutParamRMRequestOpenBoxKey out = new OutParamRMRequestOpenBoxKey();

		//1.	验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(in.AppealUser)
			||StringUtils.isEmpty(in.AppealType)
			||StringUtils.isEmpty(in.BoxNo)
			||StringUtils.isEmpty(in.PackageID)
			||StringUtils.isEmpty(in.CustomerMobile)
			||in.StoredTime == null )
			throw new EduException(ErrorCode.ERR_PARMERR);

		//3.	调用UtilDAO.getCurrentDateTime()获得系统日期时间。
		java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();
		//检查是否允许远程求助 //"7";//TakeOut  "10";//Intransit-back
		if("21".equals(in.AppealType)){//21-用户取件
		    PTDeliverHistoryDAO historyPackDAO = daoFactory.getPTDeliverHistoryDAO();
	        JDBCFieldArray whereCols = new JDBCFieldArray();
	        whereCols.add("PackageID", in.PackageID);
	        whereCols.add("ItemStatus", "7");
	        whereCols.add("LastModifyTime",">", DateUtils.addTimeStampByHour(sysDateTime, -12));
	        if(historyPackDAO.isExist(whereCols) < 1){
	            throw new EduException(ErrorCode.ERR_BUSINESS_FORBIDREMOTEBOX);
	        }
		}else if("12".equals(in.AppealType)){//12-确认回收后误关箱门
		    PTDeliverHistoryDAO historyPackDAO = daoFactory.getPTDeliverHistoryDAO();
            JDBCFieldArray whereCols = new JDBCFieldArray();
            whereCols.add("PackageID", in.PackageID);
            whereCols.add("ItemStatus", "10");
            whereCols.add("LastModifyTime",">", DateUtils.addTimeStampByHour(sysDateTime, -12));
            if(historyPackDAO.isExist(whereCols) < 1){
                throw new EduException(ErrorCode.ERR_BUSINESS_FORBIDREMOTEBOX);
            }
		}else{
		    throw new EduException(ErrorCode.ERR_BUSINESS_FORBIDREMOTEBOX);
		}
        
		///////////////记录远程求助日志
		RMAppealLogDAO appealLogDAO = daoFactory.getRMAppealLogDAO();
        RMAppealLog appealLog = new RMAppealLog();
        appealLog.AppealNo = in.AppealNo;
        appealLog.AppealUser = in.AppealUser;
        appealLog.AppealType = in.AppealType;
        appealLog.BoxNo = in.BoxNo;
        appealLog.PackageID = in.PackageID;
        appealLog.Mobile = in.CustomerMobile;
        appealLog.AppealTime = sysDateTime;
        appealLog.LastModifyTime = sysDateTime;
        appealLog.AppealStatus = SysDict.APPEAL_STATUS_READYOPEN;
        appealLog.TerminalNo = in.TerminalNo;
        
        appealLog.OpenBoxKey = RandUtils.generateNumber(6);
        
        appealLog.AppealContent = "PackageID     : " + in.PackageID + "\n";
        appealLog.AppealContent+= "CustomerMobile: " + in.CustomerMobile + "\n";
        appealLog.AppealContent+= "PostmanID     : " + in.PostmanID + "\n";
        appealLog.AppealContent+= "StoredTime    : " + DateUtils.timestampToString(in.StoredTime) + "\n";
        if(in.TakedTime != null)
        	appealLog.AppealContent+= "TakedTime     : " + DateUtils.timestampToString(in.TakedTime);
        else
        	appealLog.AppealContent+= "TakedTime     : ";

        appealLogDAO.insert(appealLog);
        
        //远程开箱求助，锁定服务端格口，远程开箱成功，解除锁定
        TBServerBoxDAO boxDAO = daoFactory.getTBServerBoxDAO();
        JDBCFieldArray setCols3 = new JDBCFieldArray();
        JDBCFieldArray whereCols3 = new JDBCFieldArray();

        setCols3.add("BoxStatus", "1");//1-锁定，2-故障，3-故障锁定
        whereCols3.add("BoxNo", in.BoxNo);//原始预定的箱号
        whereCols3.add("TerminalNo", in.TerminalNo);
        whereCols3.add("BoxStatus", "0");
        boxDAO.update(setCols3, whereCols3);
        
        //////////////////////////////////////////////////////////////////////////
        ControlParam ctrlParam = ControlParam.getInstance();
        
        out.AppealNo = in.AppealNo;
        out.OpenBoxKey = SecurityUtils.md5(appealLog.OpenBoxKey);
        out.ContactTel = ctrlParam.getContactTel();

        return out;
    }
}
