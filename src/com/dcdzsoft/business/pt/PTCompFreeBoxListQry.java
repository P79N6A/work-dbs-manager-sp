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
 * <p>Description: 公司可用空箱列表查询 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class PTCompFreeBoxListQry extends ActionBean
{

    public OutParamPTCompFreeBoxListQry doBusiness(InParamPTCompFreeBoxListQry in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        OutParamPTCompFreeBoxListQry out = new OutParamPTCompFreeBoxListQry();

		//1.	验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(in.CompanyID)
			||StringUtils.isEmpty(in.TerminalNo))
			throw new EduException(ErrorCode.ERR_PARMERR);
		
        java.util.Set<String> boxSet = commonDAO.getCompFreeBoxSet(in.CompanyID, in.TerminalNo, in.BoxType, 0);
   
        String boxList = "";
        int boxNum = 0;
        for(String boxNo:boxSet){
        	if(!boxList.isEmpty()){
				boxList +=",";
			}
			boxList += boxNo;
			boxNum++;
        }
		out.CompanyID   = in.CompanyID;
		out.TerminalNo  = in.TerminalNo;
		out.BoxType     = in.BoxType;
		out.FreeBoxNum  = boxNum;
		out.FreeBoxList = boxList;
		
        return out;
    }
}
