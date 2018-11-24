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
 * <p>Description: 柜体位置信息查询 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class LockerStationAddressQry extends ActionBean
{

    public java.util.List<OutParamLockerStationAddressQry> doBusiness(InParamLockerStationAddressQry in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        
        java.util.List<OutParamLockerStationAddressQry> outList = new java.util.LinkedList<OutParamLockerStationAddressQry>();
        
       
        
        String limitSQL = "";
        
        PreparedWhereExpression whereSQL = new PreparedWhereExpression();
        
        //经度Longitude
        if(in.West<in.East){
        	whereSQL.add("Longitude","<", in.East);//东
        	whereSQL.add("Longitude",">", in.West);//西
            
        }else{
        	whereSQL.add("Longitude",">", in.East);//东
        	whereSQL.add("Longitude","<", in.West);//西
        }
        
        //纬度Latitude
        if(in.South<in.North){
        	whereSQL.add("Latitude","<", in.North);//北
        	whereSQL.add("Latitude",">", in.South);//南
        }else{
        	whereSQL.add("Latitude","<", in.North);//北
        	whereSQL.add("Latitude",">", in.South);//南
        }
        
        whereSQL.checkAdd("TerminalNo", in.TerminalNo);
        //柜体位置查询：0-提供所有，1-提供已注册柜体，2-提供正常在线柜体
        if("1".equals(ControlParam.getInstance().getLockerStationMapQry())){
        	whereSQL.add("RegisterFlag", "1");
        }else if("2".equals(ControlParam.getInstance().getLockerStationMapQry())){
        	String terminalStatus = "0,5";//0-表示正常，5-表示存在箱故障，但柜体正常
        	whereSQL.add("RegisterFlag", "1");
        	whereSQL.addSQL(utilDAO.getFlagInSQL("TerminalStatus", terminalStatus));
        }

        String sql = "SELECT * FROM V_TBTerminal a WHERE 1=1 " + whereSQL.getPreparedWhereSQL() + limitSQL
        		+ " ORDER BY TerminalNo";

        //System.out.println("West="+in.West+",East="+in.East+",North="+in.North+",South="+in.South);
        RowSet rset = null;//terminalDAO.select(whereCols);
        rset = dbSession.executeQuery(sql, whereSQL);
        while (RowSetUtils.rowsetNext(rset)) {
        	OutParamLockerStationAddressQry out = new OutParamLockerStationAddressQry();
        	out.TerminalNo = RowSetUtils.getStringValue(rset, "TerminalNo");
        	out.TerminalName = RowSetUtils.getStringValue(rset, "TerminalName");
        	out.Address = RowSetUtils.getStringValue(rset, "Address");
        	out.Location = RowSetUtils.getStringValue(rset, "Location");
        	out.Longitude = RowSetUtils.getDoubleValue(rset, "Longitude");
        	out.Latitude = RowSetUtils.getDoubleValue(rset, "Latitude");
        	out.City     = RowSetUtils.getStringValue(rset, "City");
        	out.Zipcode     = RowSetUtils.getStringValue(rset, "Zipcode");
        	outList.add(out);
        }
        
        return outList;
    }
}
