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

public class TBTerminalQryByNo extends ActionBean
{

    public TBTerminal doBusiness(TBTerminal in ) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        
        /////
        TBTerminal terminal = new TBTerminal();
        if(StringUtils.isNotEmpty(in.TerminalNo)){
        	TBTerminalDAO terminalDAO = daoFactory.getTBTerminalDAO();
        	terminal.TerminalNo = in.TerminalNo;
        	terminal = terminalDAO.find(terminal);
        }
        return terminal;
        
    }
}
