package com.dcdzsoft.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.dcdzsoft.EduException;
import com.dcdzsoft.memcached.MemCachedManager;
import com.dcdzsoft.util.JsonUtils;
import com.dcdzsoft.util.WebUtils;
import com.dcdzsoft.websocket.WebSocketManager;
import com.dcdzsoft.websocket.WebSocketSessionBean;

/**
 * Servlet implementation class PushAgentServlet
 */
@WebServlet("/pushAgent")
public class PushAgentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/html; charset=UTF-8";
	
	private static WebSocketManager socketManager = WebSocketManager.getInstance();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PushAgentServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request,response);
        
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
        response.setContentType(CONTENT_TYPE);
        
        String uuid       = WebUtils.getStringParameter("uuid", request);
        String terminalNo = WebUtils.getStringParameter("terminalno", request);
        String message    = WebUtils.readRequestData(request);
        System.out.println("uuid="+uuid+","+terminalNo+",message="+message);
        PrintWriter out = response.getWriter();
        
        if(StringUtils.isEmpty(uuid)
        	||StringUtils.isEmpty(terminalNo)
        	||StringUtils.isEmpty(message)){
        	out.println(JsonUtils.getErrorJSONStr("param error", null));
            out.close();
        	return;
        }

		MemCachedManager memCache = MemCachedManager.getInstance();
		WebSocketSessionBean sessionBean = (WebSocketSessionBean)memCache.get(WebSocketManager.WS_LOCKER_PREFIX+terminalNo);
		if(sessionBean==null){
			out.println(JsonUtils.getErrorJSONStr("locker offline", null));
			out.close();
			return;
		}
		if(!uuid.equalsIgnoreCase(sessionBean.getSessionID())){
			out.println(JsonUtils.getErrorJSONStr("session timeout", null));
			out.close();
			return;
		}
		try {
			socketManager.sendRequestMsg(message, terminalNo);
			out.println(JsonUtils.getSuccessJSONStr("successful", 0));
		} catch (EduException e) {
			// TODO Auto-generated catch block
			String errMsg = e.getMessage();
			out.println(JsonUtils.getErrorJSONStr(errMsg, null));
            out.flush();
            out.close();
		}
	}
	

}
