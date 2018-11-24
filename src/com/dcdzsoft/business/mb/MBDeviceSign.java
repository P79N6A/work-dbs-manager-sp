package com.dcdzsoft.business.mb;

import javax.sql.RowSet;

import net.sf.json.JSONObject;

import com.dcdzsoft.EduException;
import com.dcdzsoft.sda.db.*;
import com.dcdzsoft.util.*;
import com.dcdzsoft.dto.function.*;
import com.dcdzsoft.dto.business.*;
import com.dcdzsoft.dao.*;
import com.dcdzsoft.dao.common.*;
import com.dcdzsoft.config.ApplicationConfig;
import com.dcdzsoft.constant.*;
import com.dcdzsoft.business.ActionBean;
import com.dcdzsoft.business.GServer;

/**
 * <p>Title: 自提柜后台运营系统</p>
 * <p>Description: 设备签到 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class MBDeviceSign extends ActionBean
{

    public OutParamMBDeviceSign doBusiness(InParamMBDeviceSign in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        OutParamMBDeviceSign out = new OutParamMBDeviceSign();

        //1.	验证输入参数是否有效，如果无效返回-1。
        if (StringUtils.isEmpty(in.TerminalNo))
            throw new EduException(ErrorCode.ERR_PARMERR);

        //3.	调用UtilDAO.getSysDateTime()获得系统日期时间。
        java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();
        out.ServerTime = sysDateTime;
        
        TBTerminal terminal = null;

        //判断安装密码是否一致
        String SoftwareVersion = "";
        String InitPasswd = "";
        String LastInitPasswd = "";
        String WelcomeInfo = "";


        SMSystemInfoDAO systemInfoDAO = daoFactory.getSMSystemInfoDAO();
        JDBCFieldArray whereColsDummy = new JDBCFieldArray();
        RowSet rset = systemInfoDAO.select(whereColsDummy);

        if(RowSetUtils.rowsetNext(rset))
        {
        	SoftwareVersion = RowSetUtils.getStringValue(rset, "SoftwareVersion");
        	InitPasswd = RowSetUtils.getStringValue(rset, "InitPasswd");
        	LastInitPasswd = RowSetUtils.getStringValue(rset, "LastInitPasswd");
            WelcomeInfo = RowSetUtils.getStringValue(rset, "UpdateContent");
        }
        
        //安装密码不一致
        if(!InitPasswd.equalsIgnoreCase(in.InitPasswd))
        {
        	if(StringUtils.isEmpty(LastInitPasswd) || !in.InitPasswd.equalsIgnoreCase(LastInitPasswd)){
        		out.RegisterFlag = SysDict.TERMINAL_REGISTERFLAG_FAILURE;
        		out.SoftwareVersion = SoftwareVersion;

        		log.error("[invalid initpasswd] " + in.TerminalNo);

        		return out;
        	}
        }


        try
        {
        	terminal = insertTerminal(in.TerminalInfo,sysDateTime);
            insertBox(in.BoxInfo,in.TerminalNo);

            out.RegisterFlag = SysDict.TERMINAL_REGISTERFLAG_YES;
        }
        catch(EduException e)
        {
        	out.RegisterFlag = SysDict.TERMINAL_REGISTERFLAG_FAILURE;

        	log.error("[register terminal failure],terminalNo=" + in.TerminalNo + ",err=" + e.getMessage());
        }


        //设备签到
        MBSignInfoDAO signInfoDAO = daoFactory.getMBSignInfoDAO();
        MBSignInfo signInfo = new MBSignInfo();
        signInfo.TerminalNo = in.TerminalNo;

        //应用服务器信息
        String appServerID   = ApplicationConfig.getInstance().getServerID();
        String appServerIP   = ApplicationConfig.getInstance().getServerIP();
        String appServerPort = ApplicationConfig.getInstance().getServerPort();
		String appServerInfo = "server["+appServerID+","+appServerIP+","+appServerPort+"]";
        if(signInfoDAO.isExist(signInfo))
        {
        	JDBCFieldArray setCols1 = new JDBCFieldArray();
        	JDBCFieldArray whereCols1 = new JDBCFieldArray();

        	setCols1.add("SignTime", sysDateTime);
        	setCols1.add("SignIP", in.SignIP);
        	setCols1.add("SoftwareVersion", in.SoftwareVersion);
        	setCols1.add("SignMac", in.SignMac);
        	setCols1.add("InitPasswd", in.InitPasswd);
        	setCols1.add("OnlineStatus","1");
        	setCols1.add("LastModifyTime", sysDateTime);
        	setCols1.add("Remark", appServerInfo);

        	whereCols1.add("TerminalNo", in.TerminalNo);

        	signInfoDAO.update(setCols1, whereCols1);
        }
        else
        {
        	signInfo.SignTime = sysDateTime;
        	signInfo.SignIP = in.SignIP;
        	signInfo.SoftwareVersion = in.SoftwareVersion;
        	signInfo.SignMac = in.SignMac;
        	signInfo.InitPasswd = in.InitPasswd;
        	signInfo.OnlineStatus = "1";
        	signInfo.LastModifyTime = sysDateTime;
        	signInfo.Remark  = appServerInfo;

        	signInfoDAO.insert(signInfo);
        }

        out.SoftwareVersion = SoftwareVersion;
        out.ServerTime = sysDateTime;
        out.WelcomeInfo = WelcomeInfo;
        
        if(ApplicationConfig.getInstance().getSysRunModel() == Constant.SYS_RUN_MODEL_OPERATE)
        {
	        out.SignKey = StringUtils.getUUID();
	        ///加入signkey到内存
	        GServer.getInstance().addSignKey(in.TerminalNo, out.SignKey);
        }else
        {
        	//想其它的办法，否则日志服务启动不了了
        	out.SignKey = "";
        	
        	//out.SignKey = StringUtils.getUUID();
 	        ///加入signkey到内存
 	        //GServer.getInstance().addSignKey(in.TerminalNo, out.SignKey);
        }

        return out;
    }

    protected TBTerminal insertTerminal(String terminalInfo,java.sql.Timestamp sysDateTime) throws EduException
    {
    	JSONObject obj = JSONObject.fromObject(terminalInfo);
    	TBTerminal terminal = (TBTerminal)JsonUtils.jsonObjectToDTO(obj, TBTerminal.class);
    	TBTerminalDAO terminalDAO = daoFactory.getTBTerminalDAO();

    	if(terminalDAO.isExist(terminal))
    	{
    		//已经存在柜体，注册失败
    		if(!SysDict.TERMINAL_REGISTERFLAG_YES.equalsIgnoreCase(terminal.RegisterFlag))
    		{
    			throw new EduException(ErrorCode.ERR_MODTERMINANO4REGISTER);
    		}

    		//更新
    		JDBCFieldArray setCols = new JDBCFieldArray();
    		JDBCFieldArray whereCols = new JDBCFieldArray();

    		setCols.add("BoxNum", terminal.BoxNum);
    		setCols.add("DeskNum", terminal.DeskNum);
    		//setCols.checkAdd("TerminalName", terminal.TerminalName);
    		setCols.checkAdd("TerminalStatus", terminal.TerminalStatus);
    		//setCols.checkAdd("Location", terminal.Location);

    		whereCols.add("TerminalNo", terminal.TerminalNo);

    		terminalDAO.update(setCols, whereCols);
    	}else
    	{
    		terminal.AppRegisterFlag = "0";
    		terminal.AppRegisterLimit = 30;
    		terminal.Latitude  = 24.711598;
    		terminal.Longitude = 46.724767;
    		terminal.TerminalType = SysDict.TERMINALTYPE_PACKAGE;//柜体第一次注册时的类型，之后只能在运维平台修改
    		//已注册成功
            terminal.RegisterFlag = SysDict.TERMINAL_REGISTERFLAG_YES;
        	terminal.CreateTime = sysDateTime;
    		terminal.LastModifyTime = sysDateTime;
    		terminalDAO.insert(terminal);
    	}

    	return terminal;
    }

    protected void insertBox(String boxInfo,String TerminalNo) throws EduException
    {
    	//JSONArray jsonArray = JSONArray.fromObject(boxInfo);
    	//java.util.List dtoList  = JsonUtils.jsonObjectToListDTO(jsonArray, TBServerBox.class);
    	if(StringUtils.isEmpty(boxInfo))
    		return;

    	String[] boxList = boxInfo.split("~");


    	TBServerBoxDAO boxDAO = daoFactory.getTBServerBoxDAO();
    	JDBCFieldArray whereCols = new JDBCFieldArray();
    	whereCols.add("TerminalNo", TerminalNo);

    	int boxCount = boxDAO.isExist(whereCols);
    	if(boxCount != boxList.length){
    		boxDAO.delete(whereCols);

    		//插入箱体信息
    		int dtoCount = boxList.length;
    		for(int i = 0;i < dtoCount; i++)
    		{
    			//TBServerBox box = (TBServerBox)dtoList.get(i);
    			String[] boxStr = boxList[i].split(",");
    			if(boxStr.length < 6)
    				continue;

    			TBServerBox box  = new TBServerBox();
    			box.TerminalNo = TerminalNo;
    			box.BoxNo = boxStr[0];
    			box.BoxName = boxStr[1];
    			box.DeskNo = NumberUtils.parseInt(boxStr[2]);
    			box.DeskBoxNo = NumberUtils.parseInt(boxStr[3]);
    			box.BoxType = boxStr[4];
    			box.BoxStatus = boxStr[5];
    			box.BoxUsedStatus = SysDict.BOX_USED_STATUS_FREE;
    			boxDAO.insert(box);
    		}
    	}
    }
}
