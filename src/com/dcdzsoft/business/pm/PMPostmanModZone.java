package com.dcdzsoft.business.pm;

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
 * <p>Description: 修改投递员所属分拣区域 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class PMPostmanModZone extends ActionBean
{

    public int doBusiness(InParamPMPostmanModZone in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        int result = 0;

		//1.	验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(in.OperID)
			||StringUtils.isEmpty(in.PostmanID)
			||StringUtils.isEmpty(in.ZoneID))
			throw new EduException(ErrorCode.ERR_PARMERR);

		//2.	调用CommonDAO.isOnline(管理员编号)判断管理员是否在线。
		OPOperOnline operOnline = commonDAO.isOnline(in.OperID);

		//3.	调用UtilDAO.getCurrentDateTime()获得系统日期时间。
		java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();
		java.sql.Date sysDate = DateUtils.toSQLDate(sysDateTime);

		//检查邮递员编号
        PMPostmanDAO postmanDAO = daoFactory.getPMPostmanDAO();
        PMPostman postman = new PMPostman();
        postman.PostmanID = in.PostmanID;
        try{
        	postman = postmanDAO.find(postman);
        }catch(EduException e){
        	//ERR_POSTMANNOTEXISTS
        	throw new EduException(ErrorCode.ERR_POSTMANNOTEXISTS);
        }
        
        
        IMZoneDAO zoneDAO = daoFactory.getIMZoneDAO();
		IMZone zone = new IMZone();
		zone.ZoneID = in.ZoneID;
		if (!zoneDAO.isExist(zone)){
            throw new EduException(ErrorCode.ERR_ZONEOFPOSTMANNOTEXISTS);
        }

        //揽件区域
        if(StringUtils.isNotEmpty(in.CollectZoneID)){
        	IMCollectZoneDAO collectZoneDAO = daoFactory.getIMCollectZoneDAO();
        	IMCollectZone collectZone = new IMCollectZone();
        	collectZone.CollectZoneID = in.CollectZoneID;
        	if (!collectZoneDAO.isExist(collectZone)){
        		in.CollectZoneID = "";
            }
        }else{
        	in.CollectZoneID = "";
        }
        
        //4.	调用IMPostmanDAO.Update()更新信息。
        JDBCFieldArray setCols = new JDBCFieldArray();
        JDBCFieldArray whereCols = new JDBCFieldArray();

        setCols.add("ZoneID",in.ZoneID);
        setCols.add("CollectZoneID",in.CollectZoneID);
        setCols.add("LastModifyTime", sysDateTime);
        setCols.add("Remark",in.Remark);

        whereCols.add("PostmanID",in.PostmanID);

        result = postmanDAO.update(setCols,whereCols);
        
		// 调用CommonDAO.addOperatorLog(OperID，功能编号，系统日期时间，“”)
		OPOperatorLog log = new OPOperatorLog();
		log.OperID = in.OperID;
		log.FunctionID = in.getFunctionID();
		log.OccurTime = sysDateTime;
		log.StationAddr = operOnline.LoginIPAddr;
		log.Remark = in.PostmanID+",ZoneID="+in.ZoneID;

		commonDAO.addOperatorLog(log);


        return result;
    }
}
