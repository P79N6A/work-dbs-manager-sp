package com.dcdzsoft.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.dcdzsoft.client.web.QYWebClientAdapter;
import com.dcdzsoft.dto.business.InParamQYOperationReportPackNum;
import com.dcdzsoft.util.JsonUtils;
import com.dcdzsoft.util.WebUtils;

@WebServlet(name = "/actionservice", urlPatterns = { "/actionservice" })
public class ActionServlet extends HttpServlet {
	private static final String CONTENT_TYPE = "text/html; charset=utf-8";

	// Initialize global variables
	public void init() throws ServletException {
	}

	// Process the HTTP Post request
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// must place here
		request.setCharacterEncoding("utf-8");
		response.setContentType(CONTENT_TYPE);
		JSONObject json = new JSONObject();
		String outmsg = "";
		PrintWriter out = response.getWriter();
		String AZC = WebUtils.getStringParameter("AZC", request);
		String Locker = WebUtils.getStringParameter("Locker", request);
		String QueryFlag = WebUtils.getStringParameter("QueryFlag", request);
		String OperID = WebUtils.getStringParameter("OperID", request);
		try {
			InParamQYOperationReportPackNum p1 = new InParamQYOperationReportPackNum();
			p1.setZoneID(AZC);
			p1.setTerminalNo(Locker);
			p1.setQueryFlag(QueryFlag);
			p1.setOperID(OperID);

			Map<String, String> map = QYWebClientAdapter.doBusiness(p1);
			for (Map.Entry<String, String> entry : map.entrySet()) {
				String key = entry.getKey();
				String value = entry.getValue();
				json.put(key, value);
			}

			out.println(json);
		} catch (Exception e) {
			// e.printStackTrace();
			outmsg = JsonUtils.getErrorJSONStr("", e);
			out.println(outmsg);
		} finally {
			out.close();
		}
	}

	// Clean up resources
	public void destroy() {
	}
}
