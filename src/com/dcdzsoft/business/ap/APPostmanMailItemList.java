package com.dcdzsoft.business.ap;

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
 * <p>Description: 待揽件包裹订单列表 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class APPostmanMailItemList extends ActionBean
{

    public java.util.List<OutParamAPPostmanMailItemList> doBusiness(InParamAPPostmanMailItemList in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        
        java.util.List<OutParamAPPostmanMailItemList> outList = new java.util.LinkedList<OutParamAPPostmanMailItemList>();
        

		//1.验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(in.PostmanID))
			throw new EduException(ErrorCode.ERR_PARMERR);


		//3.调用UtilDAO.getCurrentDateTime()获得系统日期时间。
		java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();
		java.sql.Date sysDate = DateUtils.toSQLDate(sysDateTime);

        return outList;
    }
}
