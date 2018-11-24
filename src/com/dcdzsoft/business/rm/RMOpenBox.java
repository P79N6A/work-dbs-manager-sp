package com.dcdzsoft.business.rm;

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
 * <p>Title: 智能自助包裹柜系统</p>
 * <p>Description: 远程求助开箱 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author 王中立
 * @version 1.0
 */

public class RMOpenBox extends ActionBean
{

    public int doBusiness(InParamRMOpenBox in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        int result = 0;

		//1.	验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(in.AppealNo)
			||StringUtils.isEmpty(in.OpenBoxKey))
			throw new EduException(ErrorCode.ERR_PARMERR);
		
		//3.	调用UtilDAO.getCurrentDateTime()获得系统日期时间。
		java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();
		
		RMAppealLogDAO appealLogDAO = daoFactory.getRMAppealLogDAO();
		JDBCFieldArray setCols = new JDBCFieldArray();
		JDBCFieldArray whereCols = new JDBCFieldArray();
		
		setCols.add("AppealStatus", SysDict.APPEAL_STATUS_OPENED);
		setCols.add("LastModifyTime", sysDateTime);
		whereCols.add("AppealNo", in.AppealNo);
		
		result = appealLogDAO.update(setCols, whereCols);
		
		//解除锁定
		RMAppealLog appealLog = new RMAppealLog();
        appealLog.AppealNo = in.AppealNo;
        appealLog = appealLogDAO.find(appealLog);
        
		TBServerBoxDAO boxDAO = daoFactory.getTBServerBoxDAO();
        JDBCFieldArray setCols3 = new JDBCFieldArray();
        JDBCFieldArray whereCols3 = new JDBCFieldArray();

        setCols3.add("BoxStatus", "0");//1-锁定，2-故障，3-故障锁定
        whereCols3.add("BoxNo", appealLog.BoxNo);//原始预定的箱号
        whereCols3.add("TerminalNo", in.TerminalNo);
        whereCols3.add("BoxStatus", "1");
        boxDAO.update(setCols3, whereCols3);

        return result;
    }
}
