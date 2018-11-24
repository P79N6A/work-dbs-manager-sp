package com.dcdzsoft.business.im;

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
 * <p>Description: 增加商业伙伴服务权限 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class IMBPartnerServiceRightAdd extends ActionBean
{

    public int doBusiness(InParamIMBPartnerServiceRightAdd in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        int result = 0;

		//1.	验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(in.OperID)
			||StringUtils.isEmpty(in.BPartnerID)
			||StringUtils.isEmpty(in.servicesList))
			throw new EduException(ErrorCode.ERR_PARMERR);

		//2.	调用CommonDAO.isOnline(管理员编号)判断管理员是否在线。
		OPOperOnline operOnline = commonDAO.isOnline(in.OperID);

		//3.	调用UtilDAO.getCurrentDateTime()获得系统日期时间。
		java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();
		java.sql.Date sysDate = DateUtils.toSQLDate(sysDateTime);
		
		IMBusinessPartnerDAO businessPartnerDAO = daoFactory.getIMBusinessPartnerDAO();
		IMBusinessPartner partner = new IMBusinessPartner();
		partner.BPartnerID = in.BPartnerID;
		//partner = businessPartnerDAO.find(partner);
		if (!businessPartnerDAO.isExist(partner)){
            throw new EduException(ErrorCode.ERR_IMBUSINESSPARTNERNODATA);
        }
		
		IMBPartnerServiceRightDAO serviceRightDAO = daoFactory.getIMBPartnerServiceRightDAO();
    	
    	//删除
    	JDBCFieldArray whereCols = new JDBCFieldArray();
    	whereCols.add("BPartnerID", in.BPartnerID);
    	serviceRightDAO.delete(whereCols);
    	
    	IMBPartnerServiceRight serviceRight = new IMBPartnerServiceRight();
    	serviceRight.BPartnerID = in.BPartnerID;
    	
    	String[] serviceArray = in.servicesList.split(",");
    	for(String serviceID: serviceArray){
    		serviceRight.ServiceID = serviceID;
    		serviceRightDAO.insert(serviceRight);
    	}
    	
		// 调用CommonDAO.addOperatorLog(OperID，功能编号，系统日期时间，“”)
		OPOperatorLog log = new OPOperatorLog();
		log.OperID = in.OperID;
		log.FunctionID = in.getFunctionID();
		log.OccurTime = sysDateTime;
		log.StationAddr = operOnline.LoginIPAddr;
		log.Remark = ""+in.BPartnerID;

		commonDAO.addOperatorLog(log);

        return result;
    }
}
