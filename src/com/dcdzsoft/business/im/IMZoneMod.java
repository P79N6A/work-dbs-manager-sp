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
 * <p>Description: 分拣区域信息修改 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class IMZoneMod extends ActionBean
{

    public int doBusiness(InParamIMZoneMod in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        int result = 0;

		//1.	验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(in.OperID)
			||StringUtils.isEmpty(in.ZoneID)
			||StringUtils.isEmpty(in.ZoneName))//
			throw new EduException(ErrorCode.ERR_PARMERR);
		
		in.ZoneName = StringUtils.normalizeName(in.ZoneName);

		//2.	调用CommonDAO.isOnline(管理员编号)判断管理员是否在线。
		OPOperOnline operOnline = commonDAO.isOnline(in.OperID);

		//3.	调用UtilDAO.getCurrentDateTime()获得系统日期时间。
		java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();
		java.sql.Date sysDate = DateUtils.toSQLDate(sysDateTime);

		//4.	调用IMZoneDAO.isExist()判断分拣区域名称是否存在，如果存在返回ERR_ZONENAMEEXISTS。
		IMZoneDAO zoneDAO = daoFactory.getIMZoneDAO();
        JDBCFieldArray whereCols0 = new JDBCFieldArray();
        whereCols0.add("ZoneName", in.ZoneName);//分拣区域名称不允许相同
        whereCols0.add("ZoneID", "<>", in.ZoneID);
        if (zoneDAO.isExist(whereCols0) > 0)
            throw new EduException(ErrorCode.ERR_ZONENAMEEXISTS);
        
        int MandatoryFlag = 0;
        if(SysDict.MANDATORY_REPORT_PRINT_YES.equals(in.R1Mandatory)){
        	MandatoryFlag |= 1;
        }
        if(SysDict.MANDATORY_REPORT_PRINT_YES.equals(in.R2Mandatory)){
        	MandatoryFlag |= 2;
        }
        if(SysDict.MANDATORY_REPORT_PRINT_YES.equals(in.R3Mandatory)){
        	MandatoryFlag |= 4;
        }
        if(SysDict.MANDATORY_REPORT_PRINT_YES.equals(in.R4Mandatory)){
        	MandatoryFlag |= 8;
        }
        if(SysDict.MANDATORY_REPORT_PRINT_YES.equals(in.R5Mandatory)){
        	MandatoryFlag |= 16;
        }
        if(SysDict.MANDATORY_REPORT_PRINT_YES.equals(in.R6Mandatory)){
        	MandatoryFlag |= 32;
        }
        if(SysDict.MANDATORY_REPORT_PRINT_YES.equals(in.R7Mandatory)){
        	MandatoryFlag |= 64;
        }
        if(SysDict.MANDATORY_REPORT_PRINT_YES.equals(in.R8Mandatory)){
        	MandatoryFlag |= 128;
        }
        
        //5.	调用IMZoneDAO. Update()更新分拣区域信息。
        JDBCFieldArray setCols = new JDBCFieldArray();
        JDBCFieldArray whereCols = new JDBCFieldArray();
        setCols.add("ZoneName", in.ZoneName);
        //setCols.checkAdd("CompanyID", in.CompanyID);//不允许修改公司
        setCols.add("Description", in.Description);
        setCols.add("CollectCharge", in.CollectCharge);
        
        setCols.add("MandatoryFlag", MandatoryFlag);
        
        setCols.add("Remark", in.Remark);

        whereCols.add("ZoneID", in.ZoneID);

        result = zoneDAO.update(setCols, whereCols);

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
