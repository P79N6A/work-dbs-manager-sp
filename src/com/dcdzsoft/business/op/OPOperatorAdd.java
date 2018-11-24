package com.dcdzsoft.business.op;

import javax.sql.RowSet;

import com.dcdzsoft.EduException;
import com.dcdzsoft.sda.db.*;
import com.dcdzsoft.util.*;
import com.dcdzsoft.dto.function.*;
import com.dcdzsoft.dto.business.*;
import com.dcdzsoft.dao.*;
import com.dcdzsoft.dao.common.*;
import com.dcdzsoft.constant.*;
import com.dcdzsoft.sda.security.SecurityUtils;
import com.dcdzsoft.business.ActionBean;

/**
 * <p>Title: 自提柜后台运营系统</p>
 * <p>Description: 管理员增加 </p>
 * <p>Copyright: Copyright (c) 2015</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class OPOperatorAdd extends ActionBean
{

    public int doBusiness(InParamOPOperatorAdd in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        int result = 0;

        //1.	验证输入参数是否有效，如果无效返回-1。
        if (StringUtils.isEmpty(in.OperID)
            || StringUtils.isEmpty(in.UserID)
            || StringUtils.isEmpty(in.OperName)
            || StringUtils.isEmpty(in.OperPassword)
            ||  in.RoleID <= 0)//in.OperType---???zxy mod
            throw new EduException(ErrorCode.ERR_PARMERR);
        
        switch(in.RoleID){
        case 1://Super Admin
        case 2://Master Admin
        //case 3://Locker Admin
        //case 11://Master Admin
        //case 12://Company Admin
        //case 13://Zone Admin
        //case 14://Zone Operator
        	in.OperType = in.RoleID;///???zxy add 角色类型与操作员类型一致
        	break;
        default :
        	in.OperType = 4;//Server Admin
        	break;
        }
        
        
        //2.	调用CommonDAO.isOnline(管理员编号)判断管理员是否在线。
        OPOperOnline operOnline = commonDAO.isOnline(in.OperID);

        //3.	调用UtilDAO.getCurrentDateTime()获得系统日期时间。
        java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();
        java.sql.Date sysDate = DateUtils.toSQLDate(sysDateTime);

        in.OperName = StringUtils.normalizeName(in.OperName);

        //4.	调用OPOperatorDAO.isExist(被操作的操作员编号)判断操作员是否存在，如果存在返回ERR_OPERHAVEEXIST。
        OPOperatorDAO operatorDAO = daoFactory.getOPOperatorDAO();
        JDBCFieldArray whereCols = new JDBCFieldArray();
        whereCols.add("UpperUserID", in.UserID.toUpperCase());

        if (operatorDAO.isExist(whereCols) > 0)
            throw new EduException(ErrorCode.ERR_OPERHAVEEXIST);

        //检查登录用户名
        if(!commonDAO.checkUserName(in.UserID)){//UserID作为登录用户名
        	throw new EduException(ErrorCode.ERR_USERNAMEEXISTS);
        }
        
        //6.	调用OPOperatorDAO.insert()插入操作员信息。
        OPOperator obj = new OPOperator();
        //obj.OperID = commonDAO.getInnerUserID();
        obj.OperID = RandUtils.generateString(10).toUpperCase();
        
        obj.OperName = in.OperName;
        obj.OperPassword = SecurityUtils.md5(in.OperPassword); //MD5 加密
        obj.OperType = in.OperType;
        obj.ZoneID = in.ZoneID;
        obj.OfficeTel = in.OfficeTel;
        obj.Mobile = in.Mobile;
        obj.Email = in.Email;
        obj.OperStatus = Constant.USER_STATUS_NORMAL; //正常
        obj.UpperUserID = in.UserID.toUpperCase();
        obj.UserID = in.UserID;
        obj.CreateTime = sysDateTime;
        obj.CreateDate = DateUtils.toSQLDate(sysDateTime);
        obj.Remark = in.Remark;

        result = operatorDAO.insert(obj);

        //9. 插入操作员角色信息
        if (in.RoleID > 0) {
        	String sql = "";
        	
            //7.	插入操作员菜单信息
            /*sql = "INSERT INTO OPOperToMenu(MenuID,OperID)"
                         + " SELECT a.MenuID," +
                         StringUtils.addQuote(obj.OperID)
                         + " FROM OPRoleToMenu a"
                         + " WHERE a.RoleID = " + in.RoleID
                         + " AND NOT EXISTS(SELECT * FROM OPOperToMenu b "
                         + " WHERE b.MenuID = a.MenuID"
                         + " AND b.OperID = " +
                         StringUtils.addQuote(obj.OperID) + ")";

            result = dbSession.executeUpdate(sql);*/

            //8.  插入特殊权限信息
            sql = "INSERT INTO OPOperSpeRight(SpePrivID,OperID)"
                  + " SELECT a.SpePrivID," +
                  StringUtils.addQuote(obj.OperID)
                  + " FROM OPRoleSpeRight a"
                  + " WHERE a.RoleID = " + in.RoleID
                  + " AND NOT EXISTS(SELECT * FROM OPOperSpeRight b "
                  + " WHERE b.SpePrivID = a.SpePrivID"
                  + " AND b.OperID = " + StringUtils.addQuote(obj.OperID) +
                  ")";

            result = dbSession.executeUpdate(sql);

            OPOperRoleDAO operRoleDAO = daoFactory.getOPOperRoleDAO();
            OPOperRole operRole = new OPOperRole();
            operRole.OperID = obj.OperID;
            operRole.RoleID = in.RoleID;

            operRoleDAO.insert(operRole);
        }

        // 调用CommonDAO.addOperatorLog(OperID，功能编号，系统日期时间，“”)
        OPOperatorLog log = new OPOperatorLog();
        log.OperID = in.OperID;
        log.FunctionID = in.getFunctionID();
        log.OccurTime = sysDateTime;
        log.StationAddr = operOnline.LoginIPAddr;
        log.Remark = in.UserID;

        commonDAO.addOperatorLog(log);

        return result;
    }
}
