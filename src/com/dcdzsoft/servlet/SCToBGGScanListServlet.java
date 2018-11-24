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
import com.dcdzsoft.client.web.PTWebClientAdapter;
import com.dcdzsoft.config.ApplicationConfig;
import com.dcdzsoft.config.ErrorMsgConfig;
import com.dcdzsoft.constant.ErrorCode;
import com.dcdzsoft.dto.business.InParamPTDeliveryItemQry;
import com.dcdzsoft.dto.business.InParamPTFJG2DB;
import com.dcdzsoft.sda.db.RowSetUtils;
import com.dcdzsoft.util.DateUtils;

/**
 * 分拣柜系统建包信息上传到包裹柜
 * @author cyf
 *
 */
@WebServlet(name = "/services/smartDistribution/addScan", urlPatterns = { "/services/smartDistribution/addScan" })
public class SCToBGGScanListServlet extends HttpServlet{
	private static Log log = org.apache.commons.logging.LogFactory.getLog(SCToBGGScanListServlet.class);
	private static ApplicationConfig apCfg = ApplicationConfig.getInstance();

	protected void doPost(HttpServletRequest request, HttpServletResponse resp)	throws ServletException, IOException {
		JSONObject respJson = new JSONObject();
		request.setCharacterEncoding("utf-8");
		resp.setContentType("application/json;charset=utf-8");
		StringBuilder errMsg = new StringBuilder();
		String errorMsg ="";
		//读取消息
		BufferedReader bufferReader = request.getReader();//获取头部参数信息
		StringBuffer buffer = new StringBuffer();
		String line = " ";
		while ((line = bufferReader.readLine()) != null) {
		    buffer.append(line);
		}
		String postData = buffer.toString();
		System.out.println("request:SCToBGGScanListServlet==>"+postData);
		log.info("request:SCToBGGScanListServlet:"+postData);
		bufferReader.close();
		if(StringUtils.isNotEmpty(postData) && postData.contains("[")){
			JSONArray reqArr = JSONArray.fromObject(postData);
			JSONObject req = new JSONObject();
			InParamPTFJG2DB inparam = new InParamPTFJG2DB();
			for(Object str : reqArr){
				req = JSONObject.fromObject(str);
				try{
					inparam.PackageID = req.getString("PackageID");
					inparam.TerminalNo = req.getString("BoxSiteCode");//格口目的地代码
					inparam.ZoneID = req.getString("DepartmentID");
					inparam.CreateTime = DateUtils.stringToTimestamp(req.getString("CreateTime"));
					inparam.OperID = req.getString("CreateOperID");
					
					PTWebClientAdapter.doBusiness(inparam);
				}catch(EduException e){
				    errMsg.append(e.getMessage());
				    errorMsg = ErrorMsgConfig.getLocalizedMessage(e.getMessage());//读取错误信息提示文件error_zh中错误代码对应的错误信息。
				    System.out.println("SCToBGGScanListServlet：业务错误:"+inparam+","+errorMsg);
				    log.info("SCToBGGScanListServlet：业务错误:"+inparam+","+errorMsg);
				}
			}
			respJson.put("code", 200);
			respJson.put("message", "success");
		}else{
			respJson.put("code", Integer.valueOf(ErrorCode.ERR_PARMERR));
			respJson.put("message", ErrorMsgConfig.getLocalizedMessage(ErrorCode.ERR_PARMERR));
		}
		System.out.println("SCToBGGScanListServlet：业务返回respArr："+respJson);
		log.info("SCToBGGScanListServlet：业务返回respArr:"+respJson);
		PrintWriter out = resp.getWriter();
        out.println(respJson);
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
