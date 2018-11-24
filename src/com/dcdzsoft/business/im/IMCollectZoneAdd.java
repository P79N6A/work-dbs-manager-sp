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
 * <p>Description: 揽件区域中心增加 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class IMCollectZoneAdd extends ActionBean
{

    public int doBusiness(InParamIMCollectZoneAdd in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        int result = 0;

		//1.	验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(in.OperID)
			||StringUtils.isEmpty(in.CollectZoneName))
			throw new EduException(ErrorCode.ERR_PARMERR);

		//2.	调用CommonDAO.isOnline(管理员编号)判断管理员是否在线。
		OPOperOnline operOnline = commonDAO.isOnline(in.OperID);

		//3.	调用UtilDAO.getCurrentDateTime()获得系统日期时间。
		java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();
		java.sql.Date sysDate = DateUtils.toSQLDate(sysDateTime);
		
		IMZoneDAO zoneDAO = daoFactory.getIMZoneDAO();
		IMZone zone = new IMZone();
		zone.ZoneID = in.ZoneID;
		
        if (!zoneDAO.isExist(zone)){
            throw new EduException(ErrorCode.ERR_IMZONENODATA);
        }
        
		//4.	判断揽件区域名称是否存在，如果存在返回ERR_COLLECTZONENAMEEXISTS。
		IMCollectZoneDAO collectZoneDAO = daoFactory.getIMCollectZoneDAO();

        in.CollectZoneName = StringUtils.normalizeName(in.CollectZoneName);
        
        JDBCFieldArray whereCols0 = new JDBCFieldArray();
        whereCols0.add("CollectZoneName", in.CollectZoneName);
        if (collectZoneDAO.isExist(whereCols0) > 0)
            throw new EduException(ErrorCode.ERR_COLLECTZONENAMEEXISTS);
        
        //生成编号
        //5位序号
        String maxID = "";
        int iMaxID = 0;
        IMCollectZone obj = new IMCollectZone();
        
        JDBCFieldArray whereCols1 = new JDBCFieldArray();
        whereCols1.add("CollectZoneID", "<>", "99999");
        maxID = collectZoneDAO.selectFunctions("MAX(CollectZoneID)", whereCols1);
        
        if (StringUtils.isEmpty(maxID)) {
            obj.CollectZoneID = "00001";//"01"
        } else {
        	iMaxID = NumberUtils.parseInt(maxID) + 1;
            obj.CollectZoneID = StringUtils.leftPad(String.valueOf(iMaxID), 5, '0');
        }
        
        JDBCFieldArray whereCols2 = new JDBCFieldArray();
        whereCols2.add("CollectZoneID", in.CollectZoneID);
        if (collectZoneDAO.isExist(whereCols2) > 0){
            throw new EduException(ErrorCode.ERR_COLLECTZONEEXISTS);
        }
        
        //插入信息
        obj.CollectZoneName = in.CollectZoneName;
        obj.CollectZoneDesc = in.CollectZoneDesc;
        obj.ZoneID  = in.ZoneID;
        obj.Remark      = in.Remark;
        
        result = collectZoneDAO.insert(obj);
        
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
