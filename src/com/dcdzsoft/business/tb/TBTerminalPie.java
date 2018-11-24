package com.dcdzsoft.business.tb;

import java.sql.Connection;

import javax.sql.RowSet;

import com.dcdzsoft.EduException;
import com.dcdzsoft.business.ActionBean;

/**
 * <p>Title: 首页仪表板  饼图</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2014</p>
 *
 * <p>Company: 杭州东城电子有限公司</p>
 *
 * @author jamin
 * @version 1.0
 */
public class TBTerminalPie extends ActionBean {

	public RowSet doBusiness() throws EduException
    {
//		utilDAO = this.getUtilDAO();
//        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();

        RowSet rset = null;

        String sql = "SELECT p.DictName,IFNULL(COUNT(1),0) total FROM tbterminal t "+
"LEFT JOIN PADictionary  p ON t.TerminalStatus=p.DictID AND p.DictTypeID='21001' "+
"GROUP BY t.TerminalStatus";

        rset = dbSession.executeQuery(sql);

        return rset;
    }

}
