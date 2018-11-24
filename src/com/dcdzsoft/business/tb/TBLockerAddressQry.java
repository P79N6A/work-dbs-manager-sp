package com.dcdzsoft.business.tb;

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
 * <p>Description: 柜体信息查询 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class TBLockerAddressQry extends ActionBean
{

    public OutParamTBLockerAddressQry doBusiness(InParamTBLockerAddressQry in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        OutParamTBLockerAddressQry out = new OutParamTBLockerAddressQry();

		//1.	验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(in.LockerID))
			throw new EduException(ErrorCode.ERR_PARMERR);
		
		
		TBTerminalDAO terminalDAO = daoFactory.getTBTerminalDAO();
		TBTerminal terminal = new TBTerminal();
		terminal.TerminalNo = in.LockerID;
		
		String limitSQL = "";
        
        PreparedWhereExpression whereSQL = new PreparedWhereExpression();
        whereSQL.add("TerminalNo", in.LockerID);
        
		String sql = "SELECT * FROM V_TBTerminal a WHERE 1=1 " + whereSQL.getPreparedWhereSQL() + limitSQL
	        		+ " ORDER BY TerminalNo";
	    RowSet rset = null;//terminalDAO.select(whereCols);
	    rset = dbSession.executeQuery(sql, whereSQL);
	    
	    if(RowSetUtils.rowsetNext(rset)){
	    	//terminal.TerminalNo = in.LockerID;
	    	terminal.TerminalNo = RowSetUtils.getStringValue(rset, "TerminalNo");
	    	terminal.TerminalName = RowSetUtils.getStringValue(rset, "TerminalName");
	    	terminal.Address = RowSetUtils.getStringValue(rset, "Address");
	    	terminal.Location = RowSetUtils.getStringValue(rset, "Location");
	    	terminal.Longitude = RowSetUtils.getDoubleValue(rset, "Longitude");
	    	terminal.Latitude = RowSetUtils.getDoubleValue(rset, "Latitude");
	    	terminal.City     = RowSetUtils.getStringValue(rset, "City");
	    	terminal.Zipcode     = RowSetUtils.getStringValue(rset, "Zipcode");
        	
	    }
		/*if(terminalDAO.isExist(terminal)){
			terminal = terminalDAO.find(terminal);
		}*/
	    out.setLockerID(terminal.TerminalNo);
		out.setLockerName(terminal.TerminalName);
		out.setAddress(terminal.Address);
		out.setLocation(terminal.Location);
		out.setCity(terminal.City);
		out.setZipcode(terminal.Zipcode);
    	out.setLatitude(terminal.Latitude);
    	out.setLongitude(terminal.Longitude);
        return out;
    }
}
