package com.dcdzsoft.business.pt;

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
 * <p>Description: 取件输错密码锁定 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class PTLockUser4WrongPwd extends ActionBean
{

    public int doBusiness(InParamPTLockUser4WrongPwd in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        int result = 0;

		//1.	验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(in.PackageID))
			throw new EduException(ErrorCode.ERR_PARMERR);
		
        //3.	调用UtilDAO.getCurrentDateTime()获得系统日期时间。
        java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();
        java.sql.Date sysDate = DateUtils.toSQLDate(sysDateTime);
        
		//验证包裹单号
		PTInBoxPackageDAO parcelItemDAO = daoFactory.getPTInBoxPackageDAO();
        PTInBoxPackage inboxPack = new PTInBoxPackage();

        inboxPack.PackageID = in.PackageID;
        inboxPack.TerminalNo = in.TerminalNo;

        try{
        	inboxPack = parcelItemDAO.find(inboxPack);
        }catch(Exception e){
        	throw new EduException(ErrorCode.ERR_PACKAGENOTEXISTS);
        }
        if(!SysDict.PACKAGE_STATUS_LOCKED.equalsIgnoreCase(inboxPack.ParcelStatus)){
        	//锁定订单
        	JDBCFieldArray setCols = new JDBCFieldArray();
	        JDBCFieldArray whereCols = new JDBCFieldArray();
	        setCols.add("ParcelStatus", SysDict.PACKAGE_STATUS_LOCKED);
	        setCols.add("LastModifyTime", sysDateTime);
	        
	        whereCols.add("PackageID", in.PackageID);
	        whereCols.add("TerminalNo", in.TerminalNo);

	        result = parcelItemDAO.update(setCols, whereCols);
        }
        return result;
    }
}
