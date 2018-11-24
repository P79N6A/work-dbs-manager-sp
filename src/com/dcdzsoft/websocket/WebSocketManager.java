package com.dcdzsoft.websocket;

import java.io.IOException;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.Executors;

import javax.websocket.Session;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.dcdzsoft.business.GServer;
import com.dcdzsoft.config.ApplicationConfig;
import com.dcdzsoft.memcached.MemCachedManager;
import com.dcdzsoft.packet.PacketUtils;
import com.dcdzsoft.util.StringUtils;
import com.dcdzsoft.util.WebUtils;
import com.dcdzsoft.constant.ErrorCode;
import com.dcdzsoft.EduException;

/**
 * <p>
 * Title: 智能自助包裹柜系统
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2014
 * </p>
 * 
 * <p>
 * Company: 杭州东城电子有限公司
 * </p>
 * 
 * @author wangzl
 * @version 1.0
 */
public class WebSocketManager {
	private static Log log = org.apache.commons.logging.LogFactory
			.getLog(WebSocketManager.class);
	
	private static GServer gServer = GServer.getInstance();
	
	/**
	 * 工作的线程数
	 */
	private int workerCount = 100;

	private ThreadPoolExecutor executor;
	/**
	 * socket list map
	 */
	private ConcurrentHashMap<String, Session> socketListMap;
	
	private ConcurrentHashMap<String,java.util.Date> lastActiveTimeMap;
	
	private MonitorConnAliveThread pingThread;
	
	private MemCachedManager memCache;
	public static final String WS_LOCKER_PREFIX = "ws_locker_";//Prefix
	private int lockerSessionIdle = 900;//10 minute
	
	//柜体注册的应用服务器信息
	private String appServerID = "";
    private String appServerIP = "";
    private String appServerPort = "";
	
	/**
	 * 私有默认构造函数
	 */
	private WebSocketManager() {
		workerCount = ApplicationConfig.getInstance().getWorkerCount();
		lockerSessionIdle = ApplicationConfig.getInstance().getLockerSessionIdle();
		
		appServerID   = ApplicationConfig.getInstance().getServerID();
		appServerIP   = ApplicationConfig.getInstance().getServerIP();
		appServerPort = ApplicationConfig.getInstance().getServerPort();
		
		memCache = MemCachedManager.getInstance();
		
		executor = (ThreadPoolExecutor) Executors
				.newFixedThreadPool(workerCount);

		socketListMap = new ConcurrentHashMap<String, Session>(workerCount);
		lastActiveTimeMap = new ConcurrentHashMap<String,java.util.Date>(workerCount);
		
		pingThread = new MonitorConnAliveThread();
		pingThread.start();
	}

	private static class SingletonHolder {
		private static final WebSocketManager instance = new WebSocketManager();
	}

	/**
	 * 静态工厂方法，返还此类的惟一实例
	 * 
	 * @return a WebSocketManager instance
	 */
	public static WebSocketManager getInstance() {
		return SingletonHolder.instance;
	}

	/**
	 * SocketSession 管理
	 * 
	 * @param terminalNo
	 * @param session
	 */
	public void addSocketSession(String terminalNo, Session session) {
		socketListMap.put(terminalNo, session);
		if(ApplicationConfig.getInstance().isMemCache()){
			
			WebSocketSessionBean sessionBean = new WebSocketSessionBean();
			sessionBean.setAppServerID(appServerID);
			sessionBean.setAppServerIP(appServerIP);
			sessionBean.setAppServerPort(appServerPort);
			sessionBean.setSessionID(StringUtils.getUUID());
			sessionBean.setTerminalNo(terminalNo);
			sessionBean.setLastActiveDateTime(new java.util.Date());
			
			memCache.set(WS_LOCKER_PREFIX+terminalNo, sessionBean, new java.util.Date(1000 * lockerSessionIdle*2));
		}
	}

	public Session getSocketSession(String terminalNo) {
		return socketListMap.get(terminalNo);
	}
	/**
	 * 获取locker注册的应用服务器信息
	 * @param terminalNo
	 * @return
	 */
	public WebSocketSessionBean getRemoteServerInfo(String terminalNo) {
		WebSocketSessionBean sessionBean = null;
		if(ApplicationConfig.getInstance().isMemCache()){
			sessionBean = (WebSocketSessionBean)memCache.get(WS_LOCKER_PREFIX+terminalNo);
			if(sessionBean==null){
				return null;
			}
			if(appServerIP.equalsIgnoreCase(sessionBean.getAppServerIP())
				&&appServerPort.equals(sessionBean.getAppServerPort())){
				return null;
			}
		}
		return sessionBean;
	}

	public void removeSocketSession(String terminalNo) {
		socketListMap.remove(terminalNo);
	}
	
	public Set<String> getOnlineTerminalNoList()
	{
		return socketListMap.keySet();
	}
	
	public void addLastActiveDateTime(String TerminalNo,java.util.Date nowTime)
	{
		lastActiveTimeMap.put(TerminalNo, nowTime);
		if(ApplicationConfig.getInstance().isMemCache()){
			WebSocketSessionBean sessionBean = (WebSocketSessionBean)memCache.get(WS_LOCKER_PREFIX+TerminalNo);
			if(sessionBean!=null){
				sessionBean.setLastActiveDateTime(nowTime);
				memCache.set(WS_LOCKER_PREFIX+TerminalNo, sessionBean, new java.util.Date(1000 * lockerSessionIdle*2));
			}
		}
	}
	
	public void removeLastActiveDateTime(String TerminalNo)
	{
		lastActiveTimeMap.remove(TerminalNo);
	}
	
	public java.util.Date getLastActiveDateTime(String TerminalNo)
	{
		return lastActiveTimeMap.get(TerminalNo);
	}
  
	
	public Set<String> getLastActiveDateTimeList()
	{
		return lastActiveTimeMap.keySet();
	}
	
	public void removeAllSessionInfo(String terminalNo)
	{
		removeLastActiveDateTime(terminalNo);
		gServer.removieSignKey(terminalNo);
		
		removeSocketSession(terminalNo);
	}

	/**
	 * 业务处理
	 */
	public void submitTask(String message, String terminalNo) {
		executor.submit(new BusinessWorker(message, terminalNo));
	}
	

	/**
	 * 发送响应消息
	 * 
	 * @param message
	 * @param terminalNo
	 */
	public void sendResponseMessage(String message, String terminalNo)
			throws EduException {
		Session session = getSocketSession(terminalNo);
		if (session != null && session.isOpen()) {
			// 加锁否???
			synchronized (session) {
				try {
					session.getAsyncRemote().sendText(message);
				} catch (IllegalStateException ise) {
					// An ISE can occur if an attempt is made to write to a
					// WebSocket connection after it has been closed. The
					// alternative to catching this exception is to synchronise
					// the writes to the clients along with the addSnake() and
					// removeSnake() methods that are already synchronised.

					log.error("[Network Error],msg = " + message);

					// 异常报警

					try {
						session.close();

						removeSocketSession(terminalNo);
					} catch (IOException ex) {
					}

					throw new EduException(ErrorCode.ERR_DEVICENETWORKABNORMAL);
				}

			}
		} else {
			throw new EduException(ErrorCode.ERR_DEVICENETWORKABNORMAL);
		}
	}

	/**
	 * 发送推送消息
	 * 
	 * @param message
	 * @param terminalNo
	 */
	public void sendRequestMsg(Object request, String terminalNo)
			throws EduException {
		String message = PacketUtils.createRequestPacket(request, terminalNo);
		if (StringUtils.isNotEmpty(message)
				&& PacketUtils.PACKET_FORMAT_ERROR.equalsIgnoreCase(message))
			throw new EduException(ErrorCode.ERR_WRONGPUSHMSGFORMAT);

		Session session = getSocketSession(terminalNo);
		if (session != null && session.isOpen()) {
			// 加锁否???
			synchronized (session) {
				try {
					session.getBasicRemote().sendText(message);
				} catch(java.io.IOException e) {
					// An ISE can occur if an attempt is made to write to a
					// WebSocket connection after it has been closed. The
					// alternative to catching this exception is to synchronise
					// the writes to the clients along with the addSnake() and
					// removeSnake() methods that are already synchronised.

					log.error("[Network Error],msg = " + message);

					// 异常报警

					try {
						session.close();

						removeSocketSession(terminalNo);
					} catch (IOException ex) {
					}

					throw new EduException(ErrorCode.ERR_DEVICENETWORKABNORMAL);
				}

			}
		} else {
			
			if(ApplicationConfig.getInstance().isMemCache()){
				//检查Locker是否注册在其他应用服务器
				WebSocketSessionBean sessionBean = getRemoteServerInfo(terminalNo);
				if(sessionBean==null){
					throw new EduException(ErrorCode.ERR_DEVICENETWORKABNORMAL);
				}
				//转发推送消息
				String appServerIP = sessionBean.getAppServerIP();
				String appServerPort = sessionBean.getAppServerPort();
				String sessionid = sessionBean.getSessionID();
				try{
					String url = "http://"+appServerIP+":"+appServerPort+"/pushAgent?uuid="+sessionid+"&terminalno="+terminalNo;
					HttpPost post = new HttpPost(url);
			        post.setEntity(new StringEntity(message));
			        
			        CloseableHttpClient httpclient = HttpClients.createDefault();
			        HttpResponse response =httpclient.execute(post);
			        String json = WebUtils.readResponseData(response);
			        JSONObject respJson = JSONObject.fromObject(json);
			        if(!respJson.getBoolean("success")){
			        	throw new EduException(ErrorCode.ERR_DEVICENETWORKABNORMAL);
			        }
			        //System.out.println(json);
			        
				} catch (JSONException e){
					throw new EduException(ErrorCode.ERR_DEVICENETWORKABNORMAL);
			    }catch(java.io.IOException e ) {
					throw new EduException(ErrorCode.ERR_DEVICENETWORKABNORMAL);
				}
			}else{
				throw new EduException(ErrorCode.ERR_DEVICENETWORKABNORMAL);
			}
		}
	}
	/**
	 * 转发推送消息
	 * @param message
	 * @param terminalNo
	 */
	public void sendRequestMsg(String message, String terminalNo)
			throws EduException {
		if (StringUtils.isNotEmpty(message)
				&& PacketUtils.PACKET_FORMAT_ERROR.equalsIgnoreCase(message))
			throw new EduException(ErrorCode.ERR_WRONGPUSHMSGFORMAT);

		Session session = getSocketSession(terminalNo);
		if (session != null && session.isOpen()) {
			// 加锁否???
			synchronized (session) {
				try {
					session.getBasicRemote().sendText(message);
				} catch(java.io.IOException e) {
					// An ISE can occur if an attempt is made to write to a
					// WebSocket connection after it has been closed. The
					// alternative to catching this exception is to synchronise
					// the writes to the clients along with the addSnake() and
					// removeSnake() methods that are already synchronised.

					log.error("[Network Error],msg = " + message);

					// 异常报警

					try {
						session.close();

						removeSocketSession(terminalNo);
					} catch (IOException ex) {
					}

					throw new EduException(ErrorCode.ERR_DEVICENETWORKABNORMAL);
				}

			}
		} else {
			throw new EduException(ErrorCode.ERR_DEVICENETWORKABNORMAL);
		}
	}
	/**
	 * 监控网络连接是否正常的线程
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
	private class MonitorConnAliveThread extends Thread
	{
		private void doMonitor() throws InterruptedException{
			java.util.Date nowDate = null;
			java.util.Date lastActiveDate = null;
			long difference = 0L;
			long minute = 0L;
			String terminalNo = "";
			
			long sessionIdleMinute = (lockerSessionIdle/60);
			while(true)
			{
				//改为从数据库中取吗?
				Set<String> keySet = getLastActiveDateTimeList();
				
				for (Iterator<String> it = keySet.iterator(); it.hasNext();) {
					terminalNo = it.next();
					lastActiveDate = getLastActiveDateTime(terminalNo);
					if(lastActiveDate != null)
					{
						//相差天数：long day=difference/(3600*24*1000) 
						//相差小时：long hour=difference/(3600*1000) 
						//相差分钟：long minute=difference/(60*1000) 
						//相差秒： long second=difference/1000
						
						nowDate = new java.util.Date();
						difference = nowDate.getTime() - lastActiveDate.getTime(); 
						minute = difference/(60*1000);
						
						//System.out.println("difference=" + minute);
						if(ApplicationConfig.getInstance().isMemCache()){
							WebSocketSessionBean sessionBean = (WebSocketSessionBean)memCache.get(WS_LOCKER_PREFIX+terminalNo);
							if(sessionBean==null){
								sessionBean = new WebSocketSessionBean();
								sessionBean.setAppServerID(appServerID);
								sessionBean.setAppServerIP(appServerIP);
								sessionBean.setAppServerPort(appServerPort);
								sessionBean.setSessionID(StringUtils.getUUID());
								sessionBean.setTerminalNo(terminalNo);
								sessionBean.setLastActiveDateTime(lastActiveDate);
								
								memCache.set(WS_LOCKER_PREFIX+terminalNo, sessionBean, new java.util.Date(1000 * lockerSessionIdle*2));
							}else if(!appServerID.equals(sessionBean.getAppServerID())){
							    //柜体已注册到其他应用服务器，关闭并移除会话
							    Session session = getSocketSession(terminalNo);
	                            if (session != null ) {
	                                try
	                                {
	                                    session.close();
	                                }catch(Exception ex0){
	                                    ///
	                                    removeAllSessionInfo(terminalNo);
	                                }
	                                
	                            }
	                            continue;
							}
						}
						
						if(minute > sessionIdleMinute)
						{
							Session session = getSocketSession(terminalNo);
							if (session != null ) {
								try
								{
									session.close();
								}
								catch(Exception ex0)
								{
									try
									{
									    BusinessWorker.deviceOffline(terminalNo);
									}catch(Exception ex1){}
									
									///
									removeAllSessionInfo(terminalNo);
								}
							}
						}
					}
				}
				
				Thread.sleep(1000*60*2);
			}
			
		}
		
		public void run() {
			try
			{
				Thread.sleep(1000*60*2);
				doMonitor();

				
			}catch(Exception e)
			{
				log.error("[MonitorConnAliveThread error] = " + e.getMessage());
			}
		}
	}

}
