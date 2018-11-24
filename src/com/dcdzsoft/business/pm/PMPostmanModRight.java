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
 * <p>Description: 修改投递员权限 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class PMPostmanModRight extends ActionBean
{

    public int doBusiness(InParamPMPostmanModRight in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        int result = 0;

		//1.	验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(in.OperID)
			||StringUtils.isEmpty(in.PostmanID)
			||StringUtils.isEmpty(in.DropRight)
			||StringUtils.isEmpty(in.ReturnRight)
			||StringUtils.isEmpty(in.CollectRight)
			||StringUtils.isEmpty(in.DADRight))
			throw new EduException(ErrorCode.ERR_PARMERR);

		//2.	调用CommonDAO.isOnline(管理员编号)判断管理员是否在线。
		OPOperOnline operOnline = commonDAO.isOnline(in.OperID);

		//检查邮递员编号
        PMPostmanDAO postmanDAO = daoFactory.getPMPostmanDAO();
        PMPostman postman = new PMPostman();
        postman.PostmanID = in.PostmanID;
        postman = postmanDAO.find(postman);
        
		//3.	调用UtilDAO.getCurrentDateTime()获得系统日期时间。
		java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();
		java.sql.Date sysDate = DateUtils.toSQLDate(sysDateTime);
		
        //4.	调用IMPostmanDAO.Update()更新信息。
        JDBCFieldArray setCols = new JDBCFieldArray();
        JDBCFieldArray whereCols = new JDBCFieldArray();
        
        if("1".equalsIgnoreCase(in.DropRight)){
            in.DropRight = SysDict.POSTMAN_RIGHT_YES;
        }else{
            in.DropRight = SysDict.POSTMAN_RIGHT_NO;
        }
        if("1".equalsIgnoreCase(in.ReturnRight)){
            in.ReturnRight = SysDict.POSTMAN_RIGHT_YES;
        }else{
            in.ReturnRight = SysDict.POSTMAN_RIGHT_NO;
        }
        if("1".equalsIgnoreCase(in.CollectRight)){
            in.CollectRight = SysDict.POSTMAN_RIGHT_YES;
        }else{
            in.CollectRight = SysDict.POSTMAN_RIGHT_NO;     
        }
        if("1".equalsIgnoreCase(in.DADRight)){
            in.DADRight = SysDict.POSTMAN_RIGHT_YES;
        }else{
            in.DADRight = SysDict.POSTMAN_RIGHT_NO;
        }
        
        switch(postman.PostmanType){
        case SysDict.POSTMAN_TYPE_POST:
            in.DADRight = SysDict.POSTMAN_RIGHT_NO;        
        	break;
        case SysDict.POSTMAN_TYPE_DAD:
        case SysDict.POSTMAN_TYPE_DAD_BUSINESSPARTNER:
        	in.DropRight    = SysDict.POSTMAN_RIGHT_NO;
        	in.CollectRight = SysDict.POSTMAN_RIGHT_NO;
        	break;
        case SysDict.POSTMAN_TYPE_RETURN:
        	in.DADRight     = SysDict.POSTMAN_RIGHT_NO;
        	in.DropRight    = SysDict.POSTMAN_RIGHT_NO;
        	in.CollectRight = SysDict.POSTMAN_RIGHT_NO;
        	break;
        default:
        	in.DADRight     = SysDict.POSTMAN_RIGHT_NO;
        	in.ReturnRight  = SysDict.POSTMAN_RIGHT_NO;
        	break;
        }
        
        setCols.add("DropRight",in.DropRight);
        setCols.add("ReturnRight",in.ReturnRight);
        setCols.add("CollectRight",in.CollectRight);
        setCols.add("DADRight",in.DADRight);
        
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
		log.Remark = in.PostmanID+":"+in.DropRight+","+in.ReturnRight+","+in.CollectRight+","+in.DADRight+",result="+result;

		commonDAO.addOperatorLog(log);

        return result;
    }
}
