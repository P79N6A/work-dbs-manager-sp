package com.dcdzsoft.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.RowSet;

import com.dcdzsoft.EduException;
import com.dcdzsoft.client.web.OPWebClientAdapter;
import com.dcdzsoft.config.BusinessConfig;
import com.dcdzsoft.dto.business.InParamOPOperLogQry;
import com.dcdzsoft.dto.business.InParamPADictionaryQry;
import com.dcdzsoft.util.JsonUtils;
import com.dcdzsoft.util.WebUtils;

/**
 * 仪表板提供数据
 */
@SuppressWarnings("serial")
@WebServlet("/Dashboard")
public class DashboardServlet extends HttpServlet {
 
	private static final String CONTENT_TYPE = "text/html; charset=UTF-8";
	
	/**
     * @see HttpServlet#HttpServlet()
     */
    public DashboardServlet() {
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
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
        response.setContentType(CONTENT_TYPE);
        
        HttpSession session = request.getSession(true);
        String sOperID = WebUtils.getOperIDAttribute(session);
        BusinessConfig cfg = BusinessConfig.getInstance();
        String uuid = WebUtils.getStringParameter("uuid", request);
        
        String cmd = WebUtils.getStringParameter("cmd", request);
        String outMsg="";
        
        switch(cmd){
	        case "opreatorlog":
	        	InParamOPOperLogQry param=new InParamOPOperLogQry();
	        	param.setOperID(sOperID);
	        	param.recordBegin=0;
	        	param.recordNum=13;
				try {
					RowSet rset=OPWebClientAdapter.doBusiness(param);
					
					outMsg=JsonUtils.getJSONFromRowSet(rset,13);
				} catch (EduException e) {
					e.printStackTrace();
					outMsg = JsonUtils.getErrorJSONStr(cfg.getSysErrMsg(), null);
				}
	        	break;
	        case "lockerStatusPie":
				try {
					RowSet rspie =OPWebClientAdapter.doBusiness(sOperID, 0);
					if(rspie!=null){
						outMsg=JsonUtils.getJSONFromRowSet(rspie,1);
					}
				} catch (EduException e) {
					outMsg = JsonUtils.getErrorJSONStr(cfg.getSysErrMsg(), null);
					e.printStackTrace();
				}
	        	break;
	        case "boxStatusPie":
	        	try {
					RowSet rspie =OPWebClientAdapter.doBusiness(sOperID, 1);
					if(rspie!=null){
						outMsg=JsonUtils.getJSONFromRowSet(rspie,1);
					}
				} catch (EduException e) {
					outMsg = JsonUtils.getErrorJSONStr(cfg.getSysErrMsg(), null);
					e.printStackTrace();
				}
	        	break;
	        case "boxStatusChart":
	        	try {
					RowSet rspie =OPWebClientAdapter.doBusiness(sOperID, 2);
					if(rspie!=null){
						outMsg=JsonUtils.getJSONFromRowSet(rspie,1);
					}
				} catch (EduException e) {
					outMsg = JsonUtils.getErrorJSONStr(cfg.getSysErrMsg(), null);
					e.printStackTrace();
				}
	        	break;
	        case "dropItemStatusChart":
	        	try {
					RowSet rspie =OPWebClientAdapter.doBusiness(sOperID, 3);
					if(rspie!=null){
						outMsg=JsonUtils.getJSONFromRowSet(rspie,1);
					}
				} catch (EduException e) {
					outMsg = JsonUtils.getErrorJSONStr(cfg.getSysErrMsg(), null);
					e.printStackTrace();
				}
	        	break;
	        case "collectItemStatusChart":
	        	try {
					RowSet rspie =OPWebClientAdapter.doBusiness(sOperID, 4);
					if(rspie!=null){
						outMsg=JsonUtils.getJSONFromRowSet(rspie,1);
					}
				} catch (EduException e) {
					outMsg = JsonUtils.getErrorJSONStr(cfg.getSysErrMsg(), null);
					e.printStackTrace();
				}
	        	break;
        }
        
        PrintWriter out = response.getWriter();
        out.println(outMsg);
        out.flush();
        out.close();
	}

}
