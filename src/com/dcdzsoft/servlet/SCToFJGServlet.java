package com.dcdzsoft.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.RowSet;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;

import com.dcdzsoft.EduException;
import com.dcdzsoft.businessproxy.PushBusinessProxy;
import com.dcdzsoft.client.web.PTWebClientAdapter;
import com.dcdzsoft.config.ApplicationConfig;
import com.dcdzsoft.config.ErrorMsgConfig;
import com.dcdzsoft.dto.business.InParamPTDeliveryItemQry;
import com.dcdzsoft.sda.db.RowSetUtils;
/**
 * 同步订单信息到分拣柜系统
 * @author cyf
 *
 */
@WebServlet(name = "/scInfoToFJG", urlPatterns = { "/scInfoToFJG" })
public class SCToFJGServlet extends HttpServlet{
	private static Log log = org.apache.commons.logging.LogFactory.getLog(SCToFJGServlet.class);
	private static ApplicationConfig apCfg = ApplicationConfig.getInstance();

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)	throws ServletException, IOException {
		JSONObject respJson = new JSONObject();
		JSONArray respArr = new JSONArray();
		req.setCharacterEncoding("utf-8");
		resp.setContentType("application/json;charset=utf-8");
		StringBuilder errMsg = new StringBuilder();
		String errorMsg ="";
		//读取消息
		BufferedReader bufferReader = req.getReader();//获取头部参数信息
		StringBuffer buffer = new StringBuffer();
		String line = " ";
		while ((line = bufferReader.readLine()) != null) {
		    buffer.append(line);
		}
		String postData = buffer.toString();
		System.out.println("request:/fjg==>"+postData);
		bufferReader.close();

		JSONObject reqStr = JSONObject.fromObject(postData);
		String departmentID = reqStr.getString("departmentID");
		if(StringUtils.isNotEmpty(departmentID)){
			try{
				InParamPTDeliveryItemQry inparam = new InParamPTDeliveryItemQry();
				inparam.PaneFlag = "1";
				inparam.ZoneID = departmentID;
				RowSet rset = PTWebClientAdapter.doBusiness(inparam);
				while(RowSetUtils.rowsetNext(rset)){
					respJson.put("WaybillCode", RowSetUtils.getStringValue(rset, "PackageID"));
					respJson.put("DestSiteCode", RowSetUtils.getStringValue(rset, "TerminalNo"));
					respJson.put("OriginalSiteCode", RowSetUtils.getStringValue(rset, "ZoneID"));
					respJson.put("DepartmentID", RowSetUtils.getStringValue(rset, "ZoneID"));
					respJson.put("OrderStatus", RowSetUtils.getStringValue(rset, "ItemStatus"));
					respJson.put("CreateTime", RowSetUtils.getStringValue(rset, "CreateTime"));
					respJson.put("Remark", RowSetUtils.getStringValue(rset, "Remark"));
					respArr.add(respJson);
				}
			}catch(EduException e){
			    errMsg.append(e.getMessage());
			    errorMsg = ErrorMsgConfig.getLocalizedMessage(e.getMessage());//读取错误信息提示文件error_zh中错误代码对应的错误信息。
			    System.out.println("SCToFJG：业务错误:"+errorMsg);
			    log.info("SCToFJG：业务错误:"+errorMsg);
			}
		}
		System.out.println("SCToFJG：业务返回respArr："+respArr);
		log.info("SCToFJG：业务返回respArr:"+respArr);
		PrintWriter out = resp.getWriter();
        out.println(respArr);
        out.flush();
        out.close();
	}
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	public void destroy() {
		super.destroy();
	}
	public void init() throws ServletException {
		super.init();
	}
	
}
