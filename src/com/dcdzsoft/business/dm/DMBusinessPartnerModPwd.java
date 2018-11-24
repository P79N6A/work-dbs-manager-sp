package com.dcdzsoft.business.dm;

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
 * <p>Title: 自提柜后台运营系统</p>
 * <p>Description: 投递员修改密码 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class DMBusinessPartnerModPwd extends ActionBean
{

    public int doBusiness(InParamDMBusinessPartnerModPwd in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        int result = 0;

		//1.验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(in.BPartnerID)
			||StringUtils.isEmpty(in.NewPassword)
			||StringUtils.isEmpty(in.OldPassword))
			throw new EduException(ErrorCode.ERR_PARMERR);

		//2.调用CommonDAO.isOnline(操作员编号)判断操作员是否在线。
		//OPOperOnline operOnline = commonDAO.isOnline(in.OperID);


		//3.调用UtilDAO.getCurrentDateTime()获得系统日期时间。
		java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();
		java.sql.Date sysDate = DateUtils.toSQLDate(sysDateTime);
		
		IMBusinessPartnerDAO businessPartnerDAO = daoFactory.getIMBusinessPartnerDAO();
		IMBusinessPartner businessPartner = new IMBusinessPartner();
        businessPartner.BPartnerID = in.BPartnerID;
        businessPartner = businessPartnerDAO.find(businessPartner);
        
        //in.OldPassword = SecurityUtils.md5(in.OldPassword);
        
        if (!businessPartner.Password.equalsIgnoreCase(in.OldPassword))//
            throw new EduException(ErrorCode.ERR_WRONGPWDORUSERID);
        
        //in.NewPassword = SecurityUtils.md5(in.NewPassword);
        
        JDBCFieldArray setCols = new JDBCFieldArray();
        JDBCFieldArray whereCols = new JDBCFieldArray();
        setCols.add("Password", in.NewPassword);
        
        whereCols.add("BPartnerID", in.BPartnerID);

        result = businessPartnerDAO.update(setCols, whereCols);
        
		// 调用CommonDAO.addOperatorLog(OperID，功能编号，系统日期时间，“”)
		OPOperatorLog log = new OPOperatorLog();
		log.OperID = in.BPartnerID;
		log.FunctionID = in.getFunctionID();
		log.OccurTime = sysDateTime;
		log.StationAddr = "";
		log.Remark = "";

		commonDAO.addOperatorLog(log);

        return result;
    }
}
