package com.dcdzsoft.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

import org.apache.commons.logging.Log;

import net.sf.json.JSONObject;

import com.dcdzsoft.businessproxy.BusinessProxy;
import com.dcdzsoft.config.ApplicationConfig;
import com.dcdzsoft.packet.JsonPacket;
import com.dcdzsoft.packet.PacketUtils;
import com.dcdzsoft.util.StringUtils;
import com.dcdzsoft.util.WebUtils;

@WebServlet(name = "/TerminalService", urlPatterns = { "/terminalservice" })
public class TerminalService extends HttpServlet {
	private static final String CONTENT_TYPE = "text/html; charset=utf-8";

	private static Log log = org.apache.commons.logging.LogFactory.getLog(TerminalService.class);

	private static ApplicationConfig apCfg = ApplicationConfig.getInstance();

	private static final String DTO_PACKAGE_PREFIX = "com.dcdzsoft.dto.business.";
	private static final String PROXY_PACKAGE_PREFIX = "com.dcdzsoft.businessproxy.";

	private static final String PROXY_CLASS_NAME = PROXY_PACKAGE_PREFIX
			+ ApplicationConfig.getInstance().getInterfacePackage();
	private static final String BUSINESS_METHOD_NAME = "doBusiness";

	private static Class proxyClass = null;
	private static BusinessProxy businessProxy = null;

	static {
		try {
			proxyClass = Class.forName(PROXY_CLASS_NAME);
			businessProxy = (BusinessProxy) proxyClass.newInstance();
		} catch (Exception e) {

		}
	}

	//业务消息
	private String message = "";
	private String terminalNo = "";
	
	// Initialize global variables
	public void init() throws ServletException {
	}

	// Process the HTTP Post request
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// must place here
		request.setCharacterEncoding("utf-8");
		response.setContentType(CONTENT_TYPE);

		terminalNo = WebUtils.getStringParameter("TERMINALNO", request);
		message = WebUtils.getStringParameter("RECDATA", request);
		
		String resultMsg = "";

		if (apCfg.isLogRawMsg())
			log.info("request=" + terminalNo + "," + message);

		JSONObject json = JSONObject.fromObject(message);
		
		try{
			if(json != null){
				JsonPacket packet = (JsonPacket)JSONObject.toBean(json,JsonPacket.class);

				if(packet != null){
					if(packet._CmdType == JsonPacket.MSG_SENT_BY_CLIENT)
					{
						resultMsg = handleRequest(packet);
					}
				}

			}else{
				log.error("[unpack error:],msg=" + message);
			}
		}catch(Exception e){
			
			log.error("[handle request:],error= " + e.getMessage() + ",msg=" + message );
		}
		
		//返回处理结果
		PrintWriter out = response.getWriter();

        out.println(resultMsg);
        out.flush();
        out.close();
	}
	
	/**
	 * 处理客户端主动发起的请求交易
	 * @param packet
	 */
	protected String handleRequest(JsonPacket packet){
		String responseMsg = "";
		
		String serviceName = packet._ServiceName;

		if(StringUtils.isNotEmpty(serviceName) && serviceName.length() > 10){

			String dtoName = DTO_PACKAGE_PREFIX + packet._ServiceName;

			Class inClass = null;
			
			try
			{
				inClass = Class.forName(dtoName);
				Object inParam = PacketUtils.buildBussinessDTOParam(packet,inClass);
				if(proxyClass==null){
					proxyClass = Class.forName(PROXY_CLASS_NAME);
				}
				Method method = proxyClass.getMethod(BUSINESS_METHOD_NAME, new Class[]{inClass,String.class});
                Object result = method.invoke(businessProxy, new Object[]{inParam,terminalNo});

                //构造响应包
                responseMsg =  PacketUtils.buildSuccessResult(packet,result);

                //System.out.println("response=" + responseMsg);
                if(apCfg.isLogRawMsg())
        			log.info("response=" + terminalNo + "," + responseMsg);
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
				
				responseMsg = PacketUtils.buildFailResult(packet,e2.getTargetException().getMessage());
                
				 if(apCfg.isLogRawMsg())
          			log.info("response=" + terminalNo + "," +  responseMsg);
            }
			catch(Exception e3)
			{
				log.error("[Service Error:],errmsg=" + e3.getMessage() + ",msg=" + message);
			}
		}
		
		return responseMsg;
	}

}
