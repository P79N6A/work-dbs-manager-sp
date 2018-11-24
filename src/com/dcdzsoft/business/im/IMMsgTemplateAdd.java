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
 * <p>Description: 消息模板增加 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class IMMsgTemplateAdd extends ActionBean
{

    public int doBusiness(InParamIMMsgTemplateAdd in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        int result = 0;

		//1.验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(in.OperID)
			||StringUtils.isEmpty(in.TemplateName)
			||StringUtils.isEmpty(in.TemplateType))
			throw new EduException(ErrorCode.ERR_PARMERR);

		//2.调用CommonDAO.isOnline(管理员编号)判断管理员是否在线。
		OPOperOnline operOnline = commonDAO.isOnline(in.OperID);

		//3.调用UtilDAO.getCurrentDateTime()获得系统日期时间。
		java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();
		java.sql.Date sysDate = DateUtils.toSQLDate(sysDateTime);

		//////////////////消息模板信息共用表IMGateway
		
		//4.	调用IMGatewayDAO.isExist()判断短信邮件接口名称是否存在，如果存在返回ERR_GATEWAYNAMEEXISTS。
		IMGatewayDAO gatewayDAO = daoFactory.getIMGatewayDAO();

        in.TemplateName = StringUtils.normalizeName(in.TemplateName);
        
        JDBCFieldArray whereCols0 = new JDBCFieldArray();
        whereCols0.add("GatewayName", in.TemplateName);
        if (gatewayDAO.isExist(whereCols0) > 0){
            throw new EduException(ErrorCode.ERR_MSGTEMPLATENAMEEXISTS);
        }

		//5.	生成短信邮件接口编号。
        IMGateway obj = new IMGateway();
        
        if(StringUtils.isEmpty(in.TemplateID)){
        	//4位编码
        	String maxID = "";
            int iMaxID = 0;
            
            JDBCFieldArray whereCols1 = new JDBCFieldArray();
            whereCols1.add("GatewayID", "<>","9999");
            maxID = gatewayDAO.selectFunctions("MAX(GatewayID)", whereCols1);
            
            if (StringUtils.isEmpty(maxID)) {
                obj.GatewayID = "0001";
            } else {
            	iMaxID = NumberUtils.parseInt(maxID) + 1;
                obj.GatewayID = StringUtils.leftPad(String.valueOf(iMaxID), 4, '0');
            }
        	/*obj.GatewayID = RandUtils.generateNumber(4);*/
        }else{
        	obj.GatewayID = in.TemplateID;
        }
        
        if(gatewayDAO.isExist(obj)){
            throw new EduException(ErrorCode.ERR_MSGTEMPLATEEXISTS);
        }

		//6.	调用IMGatewayDAO.insert()插入短信邮件接口信息。
        obj.GatewayName  = in.TemplateName;
        obj.SMSTemplate  = in.SMSTemplate;
        obj.TemplateType = in.TemplateType;
        obj.LastModifyTime = sysDateTime;
        obj.Remark       = in.Remark;
        
        result = gatewayDAO.insert(obj);

        // 调用CommonDAO.addOperatorLog(OperID，功能编号，系统日期时间，“”)
        OPOperatorLog log = new OPOperatorLog();
        log.OperID = in.OperID;
        log.FunctionID = in.getFunctionID();
        log.OccurTime = sysDateTime;
        log.StationAddr = operOnline.LoginIPAddr;
        log.Remark = in.TemplateName;

        commonDAO.addOperatorLog(log);

        return result;
    }
}
