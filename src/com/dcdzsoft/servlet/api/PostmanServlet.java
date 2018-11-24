package com.dcdzsoft.servlet.api;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import com.dcdzsoft.EduException;
import com.dcdzsoft.business.api.Comm;
import com.dcdzsoft.business.api.ELSServerApiAdapter;
import com.dcdzsoft.business.api.Comm.ErrorCode;
import com.dcdzsoft.client.web.DMWebClientAdapter;
import com.dcdzsoft.constant.SysDict;
import com.dcdzsoft.dto.business.InParamAPCollectAgentAddressQry;
import com.dcdzsoft.dto.business.InParamAPCollectAgentCollected;
import com.dcdzsoft.dto.business.InParamAPCollectAgentItemQry;
import com.dcdzsoft.dto.business.InParamAPCollectedByDepartment;
import com.dcdzsoft.dto.business.InParamAPIUserKeyAndDataVerity;
import com.dcdzsoft.dto.business.InParamAPPostmanCheck;
import com.dcdzsoft.dto.business.InParamDMBusinessPartnerTopUpReq;
import com.dcdzsoft.dto.business.InParamDMCollectAgentItemQry;
import com.dcdzsoft.dto.business.InParamDMDeliveryCollected;
import com.dcdzsoft.dto.business.InParamPTAddItemsFromPPC;
import com.dcdzsoft.dto.business.OutParamAPCollectAgentAddressQry;
import com.dcdzsoft.dto.business.OutParamAPCollectAgentCollected;
import com.dcdzsoft.dto.business.OutParamAPCollectAgentItemQry;
import com.dcdzsoft.dto.business.OutParamAPCollectedByDepartment;
import com.dcdzsoft.dto.business.OutParamAPIUserKeyAndDataVerity;
import com.dcdzsoft.dto.business.OutParamAPPostmanCheck;
import com.dcdzsoft.dto.business.OutParamDMBusinessPartnerTopUpReq;
import com.dcdzsoft.dto.business.OutParamDMCollectAgentItemQry;
import com.dcdzsoft.dto.business.OutParamDMDeliveryCollected;
import com.dcdzsoft.dto.business.OutParamLockerStationAddressQry;
import com.dcdzsoft.util.WebUtils;

/**
 */
@WebServlet("/elockerserver/postmanapi")
public class PostmanServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    public void init(){
    	
    }
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PostmanServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		response.setCharacterEncoding("UTF-8"); 
		PrintWriter out = response.getWriter();
		
		//#start 合法性检查 前台必须通过url?的形式把以下两个参数传递过来
		/*HttpSession session = request.getSession(true);
        String uuid = WebUtils.getStringParameter("uuid", request);
        String sUuid = WebUtils.getSessionAttribute("uuid", session);
        
        if (StringUtils.isEmpty(uuid) || !uuid.equals(sUuid))
        {
            out.println(JsonUtils.getErrorJSONStr("非法的请求", null));
            out.close();

            return;
        }*/
        //#end
        
		//#start获取请求数据
		String keymd5 = request.getParameter("keymd5");
		String datamd5 = request.getParameter("datamd5");
		String action = request.getParameter("action");
		String requsetData = Comm.readRequestData(request);
		//#end
		
		String respMsg = "";
		OutParamAPIUserKeyAndDataVerity userKeyOut = ELSServerApiAdapter.checkApiRequest(1, keymd5, datamd5, action, requsetData);
        //错误消息不为空，消息异常
		if(StringUtils.isNotEmpty(userKeyOut.getErrorMsg())){
        	respMsg = userKeyOut.getErrorMsg();
        	out.println(respMsg);
    		out.close();
    		return;
        }
		if(!SysDict.APP_USER_TYPE_APP_POSTMAN.equals(userKeyOut.UserType)){
    		respMsg = Comm.getErrorMessage(ErrorCode.ERROR_PARAM_KEYMD5);    
    		
        	out.println(respMsg);
    		out.close();
    		return;
    	}

		try {
			switch(action){
			case "checkPostman":
				respMsg = doCheckPostman(requsetData);
				break;
			case "qryCollectedAddress":
				respMsg = qryCollectedAddress(requsetData);
				break;
			case "qryCollectItemInfo":
				respMsg = qryCollectItemInfo(requsetData);
				break;
			case "confirmCollected":
				respMsg = confirmCollected(requsetData);
				break;
			case "collectionAPP":
				respMsg = collectionAPP(requsetData);
				break;
			default:
				respMsg = Comm.getErrorMessage(ErrorCode.ERROR_PARAM_EMPTY);
	        	System.out.println("action="+action);
			}
			out.println(respMsg);
    		out.close();
    		return;
		}catch(JSONException e){
			respMsg = Comm.getErrorMessage(ErrorCode.ERROR_DATA_FORMAT);
			out.println(respMsg);
    		out.close();
    		return;
        }catch (EduException e) {
			// TODO Auto-generated catch block			
			respMsg = Comm.getErrorMessage(ErrorCode.ERROR_BUSINESS, e.getMessage());    
			out.println(respMsg);
			out.close();
			return;
		}  catch(Exception e){
        	respMsg = Comm.getErrorMessage(ErrorCode.ERROR_UNKNOWD.getIndex(), e.getMessage());    
        	
        	out.println(respMsg);
    		out.close();
    		return;
        }
	}
	/**
	 * 投递员登录验证
	 * @param requestData
	 *   ex:{"PostmanID":"01001R5dEh","Password":"670b14728ad9902aecba32e22fa4f6bd"}
	 * @return
	 * @throws Exception
	 */
	private String doCheckPostman (String requestData) throws JSONException, EduException{
		String respMsg = "";
		JSONObject requestJson = JSONObject.fromObject(requestData);
		String postmanid = requestJson.getString("PostmanID");
		if(StringUtils.isEmpty(postmanid)){
			throw new EduException("PostmanID.length error!");
		}
		String password  = requestJson.getString("Password");
		if(StringUtils.isEmpty(password)){
			throw new EduException("Password.length error!");
		}
		JSONObject resultJson = new JSONObject();
		//#start 
		InParamAPPostmanCheck in = new InParamAPPostmanCheck();
		in.PostmanID = postmanid;
		in.Password  = password;
		OutParamAPPostmanCheck out = new OutParamAPPostmanCheck();
		try{
			out = ELSServerApiAdapter.doBusiness(in);
			resultJson.put("PostmanID", out.getPostmanID());
			resultJson.put("Status", "Success");
			resultJson.put("PostmanName", out.getPostmanName());
			resultJson.put("AZC", out.getZoneName());
			resultJson.put("Company", out.getCompanyName());
			resultJson.put("PostmanRight", out.getPostmanRight());
			respMsg = Comm.getSuccessMessage(resultJson);
		}catch(EduException e){
			//登录失败
			resultJson.put("PostmanID", postmanid);
			resultJson.put("Status", "Failed");
			resultJson.put("PostmanName", "");
			resultJson.put("AZC", "");
			resultJson.put("Company", "");
			resultJson.put("PostmanRight", 0);
    		JSONObject errorJson = Comm.createSuccessMessage(resultJson);
    		errorJson.put("ErrorMsg", e.getMessage());
    		respMsg = errorJson.toString();
		}
		//#end
		
		return respMsg;
	}
	/**
	 * 查询投递员所属揽件区域内待揽件的订单所在地址
	 * @param requestData
	 *   ex:{"PostmanID":"01001R5dEh"}
	 * @return
	 * @throws Exception
	 */
	private String qryCollectedAddress (String requestData) throws JSONException, EduException{
		String respMsg = "";
		JSONObject requestJson = JSONObject.fromObject(requestData);
		
		String mode  = requestJson.getString("Mode");
		if(StringUtils.isEmpty(mode)){
			throw new EduException("mode.length error!");
		}
		String postmanid = "";
		if(!"1".equals(mode)){
			postmanid = requestJson.getString("PostmanID");
			if(StringUtils.isEmpty(postmanid)){
				throw new EduException("PostmanID.length error!");
			}
		}
		
		
		//#start 
		JSONArray resultsJsonArray = new JSONArray();
		InParamAPCollectAgentAddressQry in = new InParamAPCollectAgentAddressQry();
		in.PostmanID = postmanid;
		in.Mode      = mode;
		java.util.List<OutParamAPCollectAgentAddressQry> outList = ELSServerApiAdapter.doBusiness(in);
		Iterator<OutParamAPCollectAgentAddressQry>  iter = outList.iterator();
		while(iter.hasNext())  {  
			
			OutParamAPCollectAgentAddressQry out = iter.next();
			JSONObject resultJson = JSONObject.fromObject(out);
			
			resultsJsonArray.add(resultJson);
        }
		respMsg = Comm.getSuccessMessage(resultsJsonArray);
		//#end
		
		return respMsg;
	}
	/**
	 * 查询包裹的订单信息
	 * @param requestData
	 * @return
	 * @throws Exception
	 */
	private String qryCollectItemInfo (String requestData) throws JSONException, EduException{
		String respMsg = "";
		JSONObject requestJson = JSONObject.fromObject(requestData);
		String itemcode  = requestJson.getString("ItemCode");
		if(StringUtils.isEmpty(itemcode)){
			throw new EduException("ItemCode.length error!");
		}
		//#start 
		InParamAPCollectAgentItemQry in = new InParamAPCollectAgentItemQry();
		in.PackageID = itemcode;
		
		OutParamAPCollectAgentItemQry out = ELSServerApiAdapter.doBusiness(in);
		
		respMsg = Comm.getSuccessMessage(JSONObject.fromObject(out));
		//#end
		
		return respMsg;
	}
	/**
	 * 揽件员确认收件
	 * @param requestData
	 * @return
	 * @throws Exception
	 */
	private String confirmCollected (String requestData) throws JSONException, EduException{
		String respMsg = "";
		JSONObject requestJson = JSONObject.fromObject(requestData);
		String postmanid = requestJson.getString("PostmanID");
		if(StringUtils.isEmpty(postmanid)){
			throw new EduException("PostmanID.length error!");
		}
		String itemcode  = requestJson.getString("ItemCode");
		if(StringUtils.isEmpty(itemcode)){
			throw new EduException("ItemCode.length error!");
		}
		String password  = requestJson.getString("Password");
		if(StringUtils.isEmpty(password)){
			throw new EduException("Password.length error!");
		}
		//#start 
		JSONObject resultJson = new JSONObject();
		
		InParamAPCollectAgentCollected in = new InParamAPCollectAgentCollected();
		in.PostmanID = postmanid;
		in.Password  = password;
		in.PackageID = itemcode;
		
		ELSServerApiAdapter.doBusiness(in);
		
		resultJson.put("Status", "Success");
        respMsg = Comm.getSuccessMessage(resultJson);
		//#end
		
		return respMsg;
	}
	/**
	 * 揽件部门收件
	 * @param requestData
	 * @return
	 * @throws Exception
	 */
	private String collectionAPP (String requestData) throws JSONException, EduException{
		String respMsg = "";
		JSONObject requestJson = JSONObject.fromObject(requestData);
		
		InParamAPCollectedByDepartment in = new InParamAPCollectedByDepartment();
		in.OperID = requestJson.getString("OperID");
		if(StringUtils.isEmpty(in.OperID)){
			throw new EduException("OperID.length error!");
		}
		in.PackageID  = requestJson.getString("ItemCode");
		if(StringUtils.isEmpty(in.PackageID)){
			throw new EduException("ItemCode.length error!");
		}
		in.Password  = requestJson.getString("Password");
		if(StringUtils.isEmpty(in.Password)){
			throw new EduException("Password.length error!");
		}
		
		if(requestJson.has("Action")&& requestJson.getString("Action").length()>0){
			in.Action = requestJson.getString("Action");
		}
		if(requestJson.has("PPCID")&&requestJson.getString("PPCID").length()>0){
			in.PPCID    = requestJson.getString("PPCID");
		}
		if(requestJson.has("AZCID")&&requestJson.getString("AZCID").length()>0){
			in.ZoneID    = requestJson.getString("AZCID");
		}
		 
		JSONObject resultJson = new JSONObject();
		ELSServerApiAdapter.doBusiness(in);
		resultJson.put("Status", 0);
		resultJson.put("Message", "Successful");
		
		respMsg = Comm.getSuccessMessage(resultJson);
		return respMsg;
	}
	//Clean up resources
    public void destroy()
    {
    	
    }

}
