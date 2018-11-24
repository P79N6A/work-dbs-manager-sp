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
 * <p>Description: 揽件区域信息修改 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class IMCollectZoneMod extends ActionBean
{

    public int doBusiness(InParamIMCollectZoneMod in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        int result = 0;

		//1.	验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(in.OperID)
			||StringUtils.isEmpty(in.CollectZoneID)
			||StringUtils.isEmpty(in.CollectZoneName))
			throw new EduException(ErrorCode.ERR_PARMERR);

		//2.	调用CommonDAO.isOnline(管理员编号)判断管理员是否在线。
		OPOperOnline operOnline = commonDAO.isOnline(in.OperID);

		//3.	调用UtilDAO.getCurrentDateTime()获得系统日期时间。
		java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();
		java.sql.Date sysDate = DateUtils.toSQLDate(sysDateTime);
		
		in.CollectZoneName = StringUtils.normalizeName(in.CollectZoneName);
		
		//4.	判断揽件区域名称是否存在，如果存在返回ERR_COLLECTZONENAMEEXISTS。
		IMCollectZoneDAO collectZoneDAO = daoFactory.getIMCollectZoneDAO();
        JDBCFieldArray whereCols0 = new JDBCFieldArray();
        whereCols0.add("CollectZoneName", in.CollectZoneName);
        whereCols0.add("CollectZoneID", "<>", in.CollectZoneID);
        if (collectZoneDAO.isExist(whereCols0) > 0)
            throw new EduException(ErrorCode.ERR_COLLECTZONENAMEEXISTS);
        
		if(StringUtils.isNotEmpty(in.ZoneID)){
			IMZoneDAO zoneDAO = daoFactory.getIMZoneDAO();
			IMZone zone = new IMZone();
			zone.ZoneID = in.ZoneID;
			
	        if (!zoneDAO.isExist(zone)){
	        	 in.ZoneID = "";
	        }
		}
        //
        JDBCFieldArray setCols = new JDBCFieldArray();
        JDBCFieldArray whereCols = new JDBCFieldArray();
        setCols.add("CollectZoneName", in.CollectZoneName);
        setCols.add("CollectZoneDesc", in.CollectZoneDesc);
        setCols.checkAdd("ZoneID", in.ZoneID);
        setCols.add("Remark", in.Remark);

        whereCols.add("CollectZoneID", in.CollectZoneID);

        result = collectZoneDAO.update(setCols, whereCols);
        
		// 调用CommonDAO.addOperatorLog(OperID，功能编号，系统日期时间，“”)
		OPOperatorLog log = new OPOperatorLog();
		log.OperID = in.OperID;
		log.FunctionID = in.getFunctionID();
		log.OccurTime = sysDateTime;
		log.StationAddr = operOnline.LoginIPAddr;
		log.Remark = in.CollectZoneName;

		commonDAO.addOperatorLog(log);

        return result;
    }
}
