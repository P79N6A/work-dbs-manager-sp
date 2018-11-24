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
 * <p>Description: 分拣区域中心增加 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class IMZoneAdd extends ActionBean
{

    public int doBusiness(InParamIMZoneAdd in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        int result = 0;

		//1.	验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(in.OperID)
			||StringUtils.isEmpty(in.ZoneName)
			||StringUtils.isEmpty(in.CompanyID))
			throw new EduException(ErrorCode.ERR_PARMERR);

		//2.	调用CommonDAO.isOnline(管理员编号)判断管理员是否在线。
		OPOperOnline operOnline = commonDAO.isOnline(in.OperID);

		//3.	调用UtilDAO.getCurrentDateTime()获得系统日期时间。
		java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();
		java.sql.Date sysDate = DateUtils.toSQLDate(sysDateTime);
		
		//检查服务商是否存在
		IMCompanyDAO companyDAO = daoFactory.getIMCompanyDAO();
		IMCompany company = new IMCompany();
		company.CompanyID = in.CompanyID;
		if(!companyDAO.isExist(company)){
			throw new EduException(ErrorCode.ERR_IMCOMPANYNODATA);
		}
		
		//4.	调用IMZoneDAO.isExist()判断分拣区域名称是否存在，如果存在返回ERR_ZONENAMEEXISTS。
		IMZoneDAO zoneDAO = daoFactory.getIMZoneDAO();

		in.ZoneName = StringUtils.normalizeName(in.ZoneName);
		
        JDBCFieldArray whereCols0 = new JDBCFieldArray();
        whereCols0.add("ZoneName", in.ZoneName);//分拣区域名称不允许相同
        if (zoneDAO.isExist(whereCols0) > 0){
            throw new EduException(ErrorCode.ERR_ZONENAMEEXISTS);
        }

        //5.	生成分拣区域编号。
        IMZone obj = new IMZone();
        //5位数字：CompanyID+xxx
    	String maxID = "";
        int iMaxID   = 0;
    	JDBCFieldArray whereCols2 = new JDBCFieldArray();
        whereCols2.add("CompanyID", in.CompanyID);
        maxID = zoneDAO.selectFunctions("MAX(ZoneID)", whereCols2);
        if (StringUtils.isEmpty(maxID)) {
            obj.ZoneID = in.CompanyID +  Constant.ZONE_START_ID;//"001"
        } else {
        	iMaxID = NumberUtils.parseInt(maxID) + 1;
            obj.ZoneID = StringUtils.leftPad(String.valueOf(iMaxID), 5, '0');
        }
        
        JDBCFieldArray whereCols1 = new JDBCFieldArray();
        whereCols1.add("ZoneID", obj.ZoneID);
		
        if (zoneDAO.isExist(whereCols1) > 0){
            throw new EduException(ErrorCode.ERR_ZONEEXISTS);
        }
        
        //6.	调用IMZoneDAO.insert()插入分拣区域信息。
        obj.ZoneName      = in.ZoneName;
        obj.CompanyID     = in.CompanyID;
        obj.Description   = in.Description;
        obj.CollectCharge = in.CollectCharge;
        obj.MandatoryFlag = 0;
        if(SysDict.MANDATORY_REPORT_PRINT_YES.equals(in.R1Mandatory)){
        	obj.MandatoryFlag |= 1;
        }
        if(SysDict.MANDATORY_REPORT_PRINT_YES.equals(in.R2Mandatory)){
        	obj.MandatoryFlag |= 2;
        }
        if(SysDict.MANDATORY_REPORT_PRINT_YES.equals(in.R3Mandatory)){
        	obj.MandatoryFlag |= 4;
        }
        if(SysDict.MANDATORY_REPORT_PRINT_YES.equals(in.R4Mandatory)){
        	obj.MandatoryFlag |= 8;
        }
        if(SysDict.MANDATORY_REPORT_PRINT_YES.equals(in.R5Mandatory)){
        	obj.MandatoryFlag |= 16;
        }
        if(SysDict.MANDATORY_REPORT_PRINT_YES.equals(in.R6Mandatory)){
        	obj.MandatoryFlag |= 32;
        }
        if(SysDict.MANDATORY_REPORT_PRINT_YES.equals(in.R7Mandatory)){
        	obj.MandatoryFlag |= 64;
        }
        if(SysDict.MANDATORY_REPORT_PRINT_YES.equals(in.R8Mandatory)){
        	obj.MandatoryFlag |= 128;
        }
        obj.CreateTime  = sysDateTime;
        obj.Remark      = in.Remark;
        
        result = zoneDAO.insert(obj);
        
		// 调用CommonDAO.addOperatorLog(OperID，功能编号，系统日期时间，“”)
		OPOperatorLog log = new OPOperatorLog();
		log.OperID = in.OperID;
		log.FunctionID = in.getFunctionID();
		log.OccurTime = sysDateTime;
		log.StationAddr = operOnline.LoginIPAddr;
		log.Remark = in.ZoneName;

        commonDAO.addOperatorLog(log);

        return result;
    }
}
