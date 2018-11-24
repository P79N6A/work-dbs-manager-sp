package com.dcdzsoft.websocket;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

import org.apache.commons.logging.Log;

import net.sf.json.JSONObject;

import com.dcdzsoft.config.ApplicationConfig;
import com.dcdzsoft.constant.SysDict;
import com.dcdzsoft.memcached.MemCachedManager;
import com.dcdzsoft.packet.JsonPacket;
import com.dcdzsoft.packet.PacketUtils;
import com.dcdzsoft.util.StringUtils;
import com.dcdzsoft.businessproxy.BusinessProxy;

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
public class BusinessWorker implements Callable<String> {
	 private static Log log = org.apache.commons.logging.LogFactory.getLog(
			 BusinessWorker.class);
	 
	 private static ApplicationConfig apCfg = ApplicationConfig.getInstance();

	 private static WebSocketManager socketManager = WebSocketManager.getInstance();

	 private static final String DTO_PACKAGE_PREFIX = "com.dcdzsoft.dto.business.";
	 private static final String PROXY_PACKAGE_PREFIX = "com.dcdzsoft.businessproxy.";

	 private static final String PROXY_CLASS_NAME = PROXY_PACKAGE_PREFIX + ApplicationConfig.getInstance().getInterfacePackage();
	 private static final String BUSINESS_METHOD_NAME = "doBusiness";
	 
	 private static Class proxyClass = null;
	 private static BusinessProxy businessProxy = null;
	
	 static{
		 try
		 {
			 proxyClass = Class.forName(PROXY_CLASS_NAME);
			 businessProxy = (BusinessProxy)proxyClass.newInstance();
		 }catch(Exception e)
		 {
			 
		 }
	 }

	//业务消息
	private String message = "";
	private String terminalNo = "";

	public BusinessWorker(String message,String terminalNo) {
		this.message = message;
		this.terminalNo = terminalNo;
    }

	//@Override
	public String call() throws Exception
	{
		//System.out.println("request=" + message);
		if(apCfg.isLogRawMsg())
			log.info("request=" + terminalNo + "," + message);

		JSONObject json = JSONObject.fromObject(message);
		try{
			if(json != null){
				JsonPacket packet = (JsonPacket)JSONObject.toBean(json,JsonPacket.class);

				if(packet != null){
					if(packet._CmdType == JsonPacket.MSG_SENT_BY_CLIENT)
						handleRequest(packet);
				}


			}else{
				log.error("[unpack error:],msg=" + message);
			}
		}catch(Exception e){
			
			log.error("[handle request:],error= " + e.getMessage() + ",msg=" + message );
		}


		return "";
	}

	/**
	 * 处理客户端主动发起的请求交易
	 * @param packet
	 */
	protected void handleRequest(JsonPacket packet){
		String serviceName = packet._ServiceName;

		if(StringUtils.isNotEmpty(serviceName) && serviceName.length() > 10){

			String dtoName = DTO_PACKAGE_PREFIX + packet._ServiceName;

			Class inClass = null;
			
			try
			{
				inClass = Class.forName(dtoName);
				Object inParam = PacketUtils.buildBussinessDTOParam(packet,inClass);

				Method method = proxyClass.getMethod(BUSINESS_METHOD_NAME, new Class[]{inClass,String.class});
                Object result = method.invoke(businessProxy, new Object[]{inParam,terminalNo});

                //构造响应包
                String responseMsg =  PacketUtils.buildSuccessResult(packet,result);

                //System.out.println("response=" + responseMsg);
                if(apCfg.isLogRawMsg())
        			log.info("response=" + terminalNo + "," + responseMsg);

                //发送响应消息
                socketManager.sendResponseMessage(responseMsg,terminalNo);
			}
			catch(java.lang.ClassNotFoundException e0)
			{
				log.error("[ClassNotFoundException:],errmsg=" + e0.getMessage() + ",msg=" + message);
			}
			catch (NoSuchMethodException e1){
				log.error("[NoSuchMethodException:],errmsg=" + e1.getMessage() + ",msg=" + message);
			}
			catch (java.lang.reflect.InvocationTargetException e2)
            {
				log.error("[Buiness Error:],errmsg=" + e2.getTargetException().getMessage() + ",msg=" + message);

				//e2.getTargetException().printStackTrace();
				
                String errMsg = PacketUtils.buildFailResult(packet,e2.getTargetException().getMessage());
                
                try{
                	 if(apCfg.isLogRawMsg())
             			log.info("response=" + terminalNo + "," +  errMsg);
                	 
                	socketManager.sendResponseMessage(errMsg,terminalNo);
                }catch(Exception e)
                {
                	
                }
            }
			catch(Exception e3)
			{
				log.error("[Service Error:],errmsg=" + e3.getMessage() + ",msg=" + message);
			}

		}
	}


	/**
	 * 设备离线
	 */
	public static void deviceOffline(String terminalNo) {
	    
	    if(ApplicationConfig.getInstance().isMemCache()){
	        MemCachedManager memCache = MemCachedManager.getInstance();
	        String appServerID   = ApplicationConfig.getInstance().getServerID();
            WebSocketSessionBean sessionBean = (WebSocketSessionBean)memCache.get(WebSocketManager.WS_LOCKER_PREFIX+terminalNo);
            if(sessionBean!=null && !appServerID.equals(sessionBean.getAppServerID())){//终端已注册到其他应用服务器。
               return;
            }
        }
		try
		{
			//离线
			com.dcdzsoft.dto.business.InParamMBDeviceOffline inParam = new com.dcdzsoft.dto.business.InParamMBDeviceOffline();
			inParam.TerminalNo = terminalNo;
			
			businessProxy.doBusiness(inParam,terminalNo);
			
			//异常消息
			com.dcdzsoft.dto.business.InParamMBUploadDeviceAlert  alertParam = new com.dcdzsoft.dto.business.InParamMBUploadDeviceAlert();
			alertParam.TerminalNo = terminalNo;
			alertParam.AlertLevel = SysDict.ALERT_LEVEL_CRITICAL;
			alertParam.AlertType = SysDict.ALERT_TYPE_NETWORKCLOSED;
			
			businessProxy.doBusiness(alertParam, terminalNo);
		}catch(Exception e)
		{
			
		}
		
		
	}
}
