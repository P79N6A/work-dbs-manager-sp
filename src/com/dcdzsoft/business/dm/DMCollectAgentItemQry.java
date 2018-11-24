package com.dcdzsoft.business.dm;

import javax.sql.RowSet;

import com.dcdzsoft.EduException;
import com.dcdzsoft.sda.db.*;
import com.dcdzsoft.sda.security.SecurityUtils;
import com.dcdzsoft.util.*;
import com.dcdzsoft.dto.function.*;
import com.dcdzsoft.dto.business.*;
import com.dcdzsoft.dao.*;
import com.dcdzsoft.dao.common.*;
import com.dcdzsoft.constant.*;
import com.dcdzsoft.business.ActionBean;

/**
 * <p>Title: 自提柜后台运营系统</p>
 * <p>Description: 揽件员查询包裹订单信息 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class DMCollectAgentItemQry extends ActionBean
{

	/**
	 * @param in
	 * @return
	 *  Status: 查询状态：0-成功；1-包裹单号无效；2-包裹单号过期；3-未选择上门取件的订单；4-无查询权限
	 * @throws EduException
	 */
    public OutParamDMCollectAgentItemQry doBusiness(InParamDMCollectAgentItemQry in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        OutParamDMCollectAgentItemQry out = new OutParamDMCollectAgentItemQry();

		//1.验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(in.PackageID)
			||StringUtils.isEmpty(in.PostmanID))
			throw new EduException(ErrorCode.ERR_PARMERR);

		//3.调用UtilDAO.getCurrentDateTime()获得系统日期时间。
		java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();
		java.sql.Date sysDate = DateUtils.toSQLDate(sysDateTime);


        return out;
    }
}
