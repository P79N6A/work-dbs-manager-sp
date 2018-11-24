package com.dcdzsoft.business;

import java.util.*;

import javax.sql.*;

import org.apache.commons.beanutils.BeanUtils;

import com.dcdzsoft.*;
import com.dcdzsoft.client.web.*;
import com.dcdzsoft.dto.business.*;
import com.dcdzsoft.ppcapi.impl.PPCOfSaudiPost;
import com.dcdzsoft.sda.db.*;
import com.dcdzsoft.sms.impl.MsgProxyDcdzsoft;
import com.dcdzsoft.sms.impl.MsgProxySaharaWs;
import com.dcdzsoft.sms.impl.MsgProxyWaveCell;
import com.dcdzsoft.util.DateUtils;
import com.dcdzsoft.util.NumberUtils;
import com.dcdzsoft.util.ServerMonitor;
import com.dcdzsoft.util.StringUtils;
import com.dcdzsoft.config.ApplicationConfig;
import com.dcdzsoft.constant.ControlParam;
import com.dcdzsoft.constant.SysDict;
//import com.dcdzsoft.client.web.PTWebClientAdapter;
import com.dcdzsoft.dto.business.InParamPTAutoLockOrder;

/**
 * <p>Title: 智能自助包裹柜系统</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2014</p>
 *
 * <p>Company: 杭州东城电子有限公司</p>
 *
 * @author wangzl
 * @version 1.0
 */
public class GServer {
    public static final String LOCALE_EN = "en_US";
    public static final String LOCALE_ZH = "zh_CN";
    private static final String SoftVersion = "v1.2.1sq2";
    
    
    //记录日志标志
    private HashMap<String, String> functionMap = new HashMap<String, String>();
    
    //SignKey
    private HashMap<String,String> signkeyMap = new HashMap<String, String>();
    
    private AutoLockerOrderThread autoLockerThread;
    
    
    private GServerSyncThread gServerSyncThread;
    private TermialMonitorThread terminalMoitorThread;
    private ServerMonitorPerHourThread serverMonitorPerHourThread;
    
    protected GServer() {
    	gServerSyncThread = new GServerSyncThread();
    	gServerSyncThread.start();
    	
    	autoLockerThread = new AutoLockerOrderThread();
    	autoLockerThread.start();
    	
    	serverMonitorPerHourThread = new ServerMonitorPerHourThread();
    	serverMonitorPerHourThread.start();
    	
    	terminalMoitorThread = new TermialMonitorThread();
    	terminalMoitorThread.start();
    }

    private static class SingletonHolder{
    	private static final GServer instance = new GServer();
    }

    /**
     * 静态工厂方法，返还此类的惟一实例
     * @return a GServer instance
     */
    public static GServer getInstance() {
        return SingletonHolder.instance;
    }

    //初始化内存数据
    public void initMemoryData() throws EduException{
        //加载日志标志
        loadFunctionToMemory();

        //加载控制参数
        loadCtrlParamToMemory();
        
        //加载应用配置
        loadApplicationConfigToMemory();
        
        //同步系统时间
        syncSysTimeOffset();
        
    }
    //同步系统时间
    public void syncSysTimeOffset() throws EduException {
    	InParamSMSysTimeSync iParam = new InParamSMSysTimeSync();
        SMWebClientAdapter.doBusiness(iParam);
    }
    //获取系统时间
    public java.sql.Timestamp getCurrentDateTime(){
    	//offset = sysDateTime.getTime()-new Date().getTime();
    	long timestamp = new Date().getTime()+ControlParam.getInstance().getDbServerAppServerTimeOffset();
    	java.sql.Timestamp sysDateTime = new java.sql.Timestamp(timestamp);
    	return sysDateTime;
    }
    //加载日志标志
    public void loadFunctionToMemory() throws EduException {
        InParamOPFunctionQry inParam = new InParamOPFunctionQry();

        RowSet rset = OPWebClientAdapter.doBusiness(inParam);
        while (RowSetUtils.rowsetNext(rset)) {
            functionMap.put(RowSetUtils.getStringValue(rset, "FunctionID"), RowSetUtils.getStringValue(rset, "LogFlag"));
        }
    }

    public void loadFunctionToMemory(RowSet rset) throws EduException {
        while (RowSetUtils.rowsetNext(rset)) {
            functionMap.put(RowSetUtils.getStringValue(rset, "FunctionID"), RowSetUtils.getStringValue(rset, "LogFlag"));
        }
    }

    //加载控制参数
   public void loadCtrlParamToMemory() throws EduException {
       InParamPAControlParamQry inParam = new InParamPAControlParamQry();

       RowSet rset = PAWebClientAdapter.doBusiness(inParam);

       ControlParam ctrlParam = ControlParam.getInstance();
       while (RowSetUtils.rowsetNext(rset)) {
           try
           {
               BeanUtils.setProperty(ctrlParam,RowSetUtils.getStringValue(rset,"KeyString"),RowSetUtils.getStringValue(rset,"DefaultValue"));
           }catch(Exception e){
               e.printStackTrace();
           }
       }
   }
   
   public void loadCtrlParamToMemory(RowSet rset) throws EduException {
       ControlParam ctrlParam = ControlParam.getInstance();
       while (RowSetUtils.rowsetNext(rset)) {
           try {
               BeanUtils.setProperty(ctrlParam, RowSetUtils.getStringValue(rset, "KeyString"), RowSetUtils.getStringValue(rset, "DefaultValue"));
           } catch (Exception e) {
               e.printStackTrace();
           }
       }
   }
   
    //加载应用配置
    public void loadApplicationConfigToMemory() throws EduException {
	   InParamPAControlParamQry inParam = new InParamPAControlParamQry();
	   inParam.CtrlTypeID = 11041;//1041-ApplicationConfig
       RowSet rset = PAWebClientAdapter.doBusiness(inParam);

       ApplicationConfig apCfg = ApplicationConfig.getInstance();
       while (RowSetUtils.rowsetNext(rset)) {
           try
           {
               BeanUtils.setProperty(apCfg,RowSetUtils.getStringValue(rset,"KeyString"),RowSetUtils.getStringValue(rset,"DefaultValue"));
           }catch(Exception e){
               e.printStackTrace();
           }
       }
    }
    public void loadApplicationConfigToMemory(RowSet rset) throws EduException {
        ApplicationConfig apCfg = ApplicationConfig.getInstance();
        while (RowSetUtils.rowsetNext(rset)) {
            try
            {
                BeanUtils.setProperty(apCfg,RowSetUtils.getStringValue(rset,"KeyString"),RowSetUtils.getStringValue(rset,"DefaultValue"));
            }catch(Exception e){
                e.printStackTrace();
            }
        }
     }
    public HashMap<String, String> getFunctionMap() {
        return this.functionMap;
    }

    public String getLogFlag(String FunctionID) {
        String logFlag = "";

        Object o = functionMap.get(FunctionID);
        if (o != null)
            logFlag = o.toString();

        return logFlag;
    }
    
    /**
     * 签到信息相关
     * @param terminalNo
     * @param signkey
     */
    public void addSignKey(String terminalNo,String signkey)
    {
    	signkeyMap.put(terminalNo, signkey);
    }
    
    public void removieSignKey(String terminalNo)
    {
    	signkeyMap.remove(terminalNo);
    }
    
    public String getSignKey(String terminalNo)
    {
    	return signkeyMap.get(terminalNo);
    }
    
    /**
     * 检查当前应用服务器是否作为主服务器
     */
    public boolean isMasterServer(){
    	String serverId = ApplicationConfig.getInstance().getServerID();
		String masterServerId = ControlParam.getInstance().getMasterServerID();
		if(StringUtils.isNotEmpty(serverId)&& !serverId.equals(masterServerId)){
			return false;
		}
    	return true;
    }
    public static String getSoftVersion(){
        return SoftVersion;
    }
    
    /**
	 * 发送催领短消息线程
	 * <p>Title: 智能自助包裹柜系统</p>
	 *
	 * <p>Description: </p>
	 *
	 * <p>Copyright: Copyright (c) 2014</p>
	 *
	 * <p>Company: 杭州东城电子有限公司</p>
	 *
	 * @author wangzl
	 * @version 1.0
	 */
	private class AutoLockerOrderThread extends Thread
	{
		public void run() 
		{
			try
			{
				Thread.sleep(1000*60*5);
				System.out.println("Auto Locker Order Thread Start --------------");
				while(true)
				{
					//只有主服务器才执行定期任务
					if(!GServer.getInstance().isMasterServer()){
						try
						{
							Thread.sleep(1000*60*10); //休眠10分钟
						}catch(InterruptedException ex){}
						continue;
					}
					Date nowDate = new Date();
					int hour = DateUtils.getHour(nowDate);
					
					//凌晨12点启动 
					if( hour == 2 || hour == 4 )//&&"1".equalsIgnoreCase(ControlParam.getInstance().getAutoLockOrder()) 
					{
						System.out.println("auto locker order begin --------------");
						
						InParamPTAutoLockOrder inParam = new InParamPTAutoLockOrder();
						
						try{
							PTWebClientAdapter.doBusiness(inParam);
						}
						catch(Exception e)
						{
							System.err.println("[AutoLockOrderThread business error] = " + e.getMessage());
						}
						
						System.out.println("auto locker order end --------------");
					}
					
					try
					{
						Thread.sleep(1000*60*59); //休眠一个小时
					}catch(InterruptedException ex){}
				}
			}catch(Exception e)
			{
				System.err.println("[auto locke Thread error] = " + e.getMessage());
			}
		}
	}

	/**
	 * GServerSync线程，定期同步配置
	 * <p>Title: 智能自助包裹柜系统</p>
	 *
	 * <p>Description: </p>
	 *
	 * <p>Copyright: Copyright (c) 2015</p>
	 *
	 * <p>Company: 杭州东城电子有限公司</p>
	 *
	 * @author zhengxy
	 * @version 1.0
	 */
	private class GServerSyncThread extends Thread
	{
		public void run() 
		{
			try
			{
				Thread.sleep(1000*60*2);
				System.out.println("GServerSyncThread begin --------------");
				int intervalInMs = 0;
				loadCtrlParamToMemory();
				while(true)
				{
					Date nowDate = new Date();
					int hour = DateUtils.getHour(nowDate);
					
					syncSysTimeOffset();
					//
					if( hour == 2)
					{
					    //加载控制参数
                        if(!"0".equals(ControlParam.getInstance().getLoadCtrlParamToMemory())){
                            loadCtrlParamToMemory();
                        }
                        
						//加载日志标志
				        if("1".equals(ControlParam.getInstance().getLoadFunctionToMemory())){
				        	loadFunctionToMemory();
				        }

				        //加载应用配置
				        if("1".equals(ControlParam.getInstance().getLoadApplicationConfigToMemory())){
				        	loadApplicationConfigToMemory();
				        }
					}
					System.out.println("GServerSyncThread -LoadFunc="+ControlParam.getInstance().getLoadFunctionToMemory()+",LoadCtrl="+ControlParam.getInstance().getLoadCtrlParamToMemory()+",LoadApp="+ControlParam.getInstance().getLoadApplicationConfigToMemory());
					try
					{
					    /*intervalInMs = NumberUtils.parseInt(ctrlParam.getLoadIntervalInMs());
						if(intervalInMs<60000){
							intervalInMs = 60000;//
						}*/
					    intervalInMs = (1000*60*58);
						Thread.sleep(intervalInMs); //休眠 分钟
					}catch(InterruptedException ex) {}
				}
			}catch(Exception e)
			{
				System.err.println("[GServer Sync  Thread error] = " + e.getMessage());
			}
		}
	}
	/**
	 * 
	 * <p>Title: 智能自助包裹柜系统</p>
	 *
	 * <p>Description: 终端监控线程</p>
	 *
	 * <p>Copyright: Copyright (c) 2014</p>
	 *
	 * <p>Company: 杭州东城电子有限公司</p>
	 *
	 * @author zhengxy
	 * @version 1.0
	 */
	private class TermialMonitorThread extends Thread
	{
		public void run() 
		{
			try {
				Thread.sleep(1000*60);
			} catch (InterruptedException e1) {}
			try
			{
				
				System.out.println("Termial Monitor Thread Start --------------");
				while(true)
				{
					//只有主服务器才执行定期任务
					if(!GServer.getInstance().isMasterServer()){
						try
						{
							Thread.sleep(1000*60*10); //休眠10分钟
						}catch(InterruptedException ex){}
						continue;
					}
					
					try{
						InParamTBTerminalListQry inParam0 = new InParamTBTerminalListQry();
						inParam0.TerminalStatus = SysDict.TERMINAL_STATUS_FAULT_POWERFAIL;
						RowSet rset = TBWebClientAdapter.doBusiness(inParam0);
						while(RowSetUtils.rowsetNext(rset))
						{
							InParamTBTerminalPowerRecovery inParam1 = new InParamTBTerminalPowerRecovery();
							inParam1.TerminalNo   = RowSetUtils.getStringValue(rset, "TerminalNo");
							inParam1.DurationMins = 30;//自动恢复30分钟之前的掉电状态
							TBWebClientAdapter.doBusiness(inParam1);
							Thread.sleep(50);
						}
					}catch(Exception e){
						System.err.println("[Termial Monitor Thread business error] = " + e.getMessage());
					}
					
					try
					{
						Thread.sleep(1000*60*30); //
					}catch(InterruptedException ex)
					{
						
					}
				}
			}catch(Exception e)
			{
				System.err.println("[Termial Monitor Thread error] = " + e.getMessage());
			}
		}
	}
	
	/**
     * 
     * <p>Title: 智能自助包裹柜系统</p>
     *
     * <p>Description: 系统监控线程</p>
     *
     * <p>Copyright: Copyright (c) 2016</p>
     *
     * <p>Company: 杭州东城电子有限公司</p>
     *
     * @author zhengxy
     * @version 1.0
     */
	private class ServerMonitorPerHourThread extends Thread{
	    //6点以后每小时启动的任务
	    private void doRunTaskPerHourAfterSix(int hour){
	        if(hour> 6){
	            try{
	                if(MsgProxySaharaWs.sendFailReport()){
	                    Thread.sleep(20);
	                }
	                if(MsgProxyWaveCell.sendFailReport()){
	                    Thread.sleep(20);
	                }
	                if(PPCOfSaudiPost.sendFailReport()){
	                    Thread.sleep(20);
	                }
	            }catch (Exception e) {
	                e.printStackTrace();
	            }
	        }
	        
	    }
	    //每小时启动一次的任务
        private void doRunTaskPerHour(){
            //直投订单指定取消
            InParamPTAutoDADCancel inParam = new InParamPTAutoDADCancel();
            try{
                PTWebClientAdapter.doBusiness(inParam);
            }catch(Exception e)
            {
                System.err.println("[AutoDADItemCancel business error] = " + e.getMessage());
            }
            
        }
        //每天执行一次的任务
        private void doRunTaskPerDay(int hour){
            if(hour == 1){
              //#start E1订单过期
                try{
                    InParamIMBusiPartnerListQry bpListQry = new InParamIMBusiPartnerListQry();
                    RowSet rset = IMWebClientAdapter.doBusiness(bpListQry);
                    while(RowSetUtils.rowsetNext(rset))
                    {
                        int count = 0;
                        InParamDMBPToCancelItemQry inParam = new InParamDMBPToCancelItemQry();
                        inParam.BPartnerID = RowSetUtils.getStringValue(rset, "BPartnerID");
                        RowSet rset1 = DMWebClientAdapter.doBusiness(inParam);
                        while(RowSetUtils.rowsetNext(rset1))
                        {
                            InParamDMDeliveryCancel inParam2 = new InParamDMDeliveryCancel();
                            inParam2.BPartnerID = RowSetUtils.getStringValue(rset1, "BPartnerID");
                            inParam2.PackageID  = RowSetUtils.getStringValue(rset1, "PackageID");
                            
                            DMWebClientAdapter.doBusiness(inParam2);
                            Thread.sleep(100);
                            count++;
                        }
                        if(count>0){
                            System.out.println(inParam.BPartnerID+" expired E1 items Num="+count);
                        }
                    }
                }catch(EduException | InterruptedException e){
                    System.err.println("[DMDeliveryCancel business error] = " + e.getMessage());
                }
                //#end
            }else if(hour == 5 || hour == 12 || hour == 20){
                //发送格口故障信息
                try{
                    InParamTBTerminalListQry inParam0 = new InParamTBTerminalListQry();
                    inParam0.TerminalStatus = SysDict.TERMINAL_STATUS_FAULT_BOX;
                    RowSet rset = TBWebClientAdapter.doBusiness(inParam0);
                    while(RowSetUtils.rowsetNext(rset))
                    {
                        InParamMBSendAlarmInfo inParam1 = new InParamMBSendAlarmInfo();
                        inParam1.TerminalNo   = RowSetUtils.getStringValue(rset, "TerminalNo");
                        inParam1.SendMode = 3;//发送方式（1-邮件，2-短信，3-短信&邮件，默认1）
                        MBWebClientAdapter.doBusiness(inParam1);
                        Thread.sleep(50);
                    }
                }catch(Exception e){
                    System.err.println("[Send FAULT_BOX Info error] = " + e.getMessage());
                }
            }
            
            if(hour == 20 
               || hour == 23
               || hour == 18
               || hour == 12
               || hour == 5
               || hour == 14){
                try{
                    int result = QYWebClientAdapter.doBusiness("exec func");
                    System.out.println("exec func = "+result);
                }catch(Exception e){
                    System.err.println("[exec func error] = " + e.getMessage());
                }
            }
            
            
        }
	    public void run() 
        {
            try {
                Thread.sleep(1000*30);
            } catch (InterruptedException e1) {}
            try
            {
                
                System.out.println("System Monitor Thread Start --------------");
                boolean isSendEmail = false;
                while(true)
                {
                    Date nowDate = new Date();
                    int hour = DateUtils.getHour(nowDate);
                    ServerMonitor.runMonitor();//运行时监控
                    if(hour == 3){
                        if(!isSendEmail){
                            isSendEmail = true;
                        }else{
                            isSendEmail = false;
                        }
                    }else{
                        isSendEmail = false;
                    }
                    if(isSendEmail){
                        ServerMonitor.sendMonitorEmail();//发送服务器信息到维护邮箱
                    }
                    //只有主服务器才执行定期任务
                    if(!GServer.getInstance().isMasterServer()){
                        try
                        {
                            Thread.sleep(1000*60*31); //休眠
                        }catch(InterruptedException ex){}
                        continue;
                    }
                    doRunTaskPerDay(hour);
                    //间隔一小时启动的任务
                    doRunTaskPerHourAfterSix(hour);
                    //间隔一小时启动的任务
                    doRunTaskPerHour();
                    //休眠1小时
                    try
                    {
                        Thread.sleep(1000*60*50); //
                    }catch(InterruptedException ex) {  }
                }
            }catch(Exception e)
            {
                System.err.println("[System Monitor Thread error] = " + e.getMessage());
            }
        }
	}
}
