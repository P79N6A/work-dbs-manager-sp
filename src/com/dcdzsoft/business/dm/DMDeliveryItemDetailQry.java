package com.dcdzsoft.business.dm;

import java.util.ListIterator;

import javax.sql.RowSet;

import net.sf.json.JSONObject;

import com.dcdzsoft.EduException;
import com.dcdzsoft.sda.db.*;
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
 * <p>Description: 寄件订单详情查询(eLocker web Portal) </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class DMDeliveryItemDetailQry extends ActionBean
{

    public OutParamDMDeliveryItemDetailQry doBusiness(InParamDMDeliveryItemDetailQry in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        OutParamDMDeliveryItemDetailQry out = new OutParamDMDeliveryItemDetailQry();
        
        RowSet rset = null;

		//1.验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(in.ItemCode))
			throw new EduException(ErrorCode.ERR_PARMERR);


		//2.调用CommonDAO.isOnline(管理员编号)判断管理员是否在线。
		//OPOperOnline operOnline = commonDAO.isOnline(in.OperID);

		
		//3.调用UtilDAO.getCurrentDateTime()获得系统日期时间。
		java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();
		java.sql.Date sysDate = DateUtils.toSQLDate(sysDateTime);

		java.sql.Date beginDate =null;
        if(StringUtils.isNotEmpty(in.BeginDate)){//
        	beginDate = commonDAO.strToDate(in.BeginDate);	
        }
        java.sql.Date endDate =null;
        if(StringUtils.isNotEmpty(in.EndDate)){
        	endDate = commonDAO.strToDate(in.EndDate);
        }
        
        OutParamItemDetail itemDetail = commonDAO.getItemRecordDetail(in.ItemCode, in.QryType, beginDate, endDate, 0);
        
        //#start 取订单记录信息
        out.ItemCode     = itemDetail.getItemCode();
        out.CreateTime   = itemDetail.getCreateTime();
        out.FromBuff     = itemDetail.getFromBuff();
        out.ToBuff       = itemDetail.getToBuff();
        out.ItemDetail   = itemDetail.getItemDetail();
        out.StoredTime   = "";//itemDetail.getStoredTime();//暂不提供存件照片
        out.StoredImgUrl = "";//itemDetail.getStoredImgUrl();
        
        out.TakedImgUrl  = ApplicationConfig.getInstance().getImgServerUri()+itemDetail.getTakedImgUrl();//"lib/images/screen.jpg";
        out.TakedTime    = itemDetail.getTakedTime();
        //#end 
        return out;
    }
}
