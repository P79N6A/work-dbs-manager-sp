package com.dcdzsoft.servlet.api;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.apache.commons.lang.StringUtils;

import com.dcdzsoft.EduException;
import com.dcdzsoft.business.api.Comm;
import com.dcdzsoft.business.api.ELSServerApiAdapter;
import com.dcdzsoft.business.api.Comm.ErrorCode;
import com.dcdzsoft.constant.SysDict;
import com.dcdzsoft.dto.business.InParamDMBusinessPartnerTopUP;
import com.dcdzsoft.dto.business.InParamDMBusinessPartnerTopUpReq;
import com.dcdzsoft.dto.business.InParamPTAddItemsFromPPC;
import com.dcdzsoft.dto.business.InParamTBLockerAddressQry;
import com.dcdzsoft.dto.business.OutParamAPIUserKeyAndDataVerity;
import com.dcdzsoft.dto.business.OutParamDMBusinessPartnerTopUP;
import com.dcdzsoft.dto.business.OutParamDMBusinessPartnerTopUpReq;
import com.dcdzsoft.dto.business.OutParamTBLockerAddressQry;
import com.dcdzsoft.util.JsonUtils;
import com.dcdzsoft.util.WebUtils;

/**
 * @see POS充值接口
 * "UserID": "100001","Amount": 100
 * @author zxy
 */
@SuppressWarnings("serial")
@WebServlet("/elockerserver/topupapi")
public class TopupapiServer extends HttpServlet {
	private static final String CONTENT_TYPE = "application/Json; charset=UTF-8";//text/html
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TopupapiServer() {
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
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8"); 
		PrintWriter out = response.getWriter();
		
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
		if(!SysDict.APP_USER_TYPE_POS_SYSTEM.equals(userKeyOut.UserType)){
    		respMsg = Comm.getErrorMessage(ErrorCode.ERROR_PARAM_KEYMD5);    
    		
        	out.println(respMsg);
    		out.close();
    		return;
    	}
		String posNo = userKeyOut.getUserID();
		try {
			switch(action){
			case "requestTopup":
				respMsg = requestTopup(requsetData);
				break;
			case "updateBalance":
				respMsg = updateBalance(requsetData, posNo);
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
			respMsg = Comm.getErrorMessage(ErrorCode.ERROR_SERVER_SYSTEM);    
			out.println(respMsg);
			out.close();
			return;
		} catch(Exception e){
        	respMsg = Comm.getErrorMessage(ErrorCode.ERROR_UNKNOWD.getIndex(), e.getMessage());    
        	
        	out.println(respMsg);
    		out.close();
    		return;
        }
	}
	/**
	 * 充值请求
	 * @param requestData
	 *   ex:{"Amount":50,"CreateTime":"2015-06-07 17:39:49","UserName":"test1"}
	 * @return
	 * @throws Exception
	 */
	private String requestTopup (String requestData) throws JSONException, EduException{
		String respMsg = "";
		JSONObject requestJson = JSONObject.fromObject(requestData);
		String createTime = requestJson.getString("CreateTime");
		String username   = requestJson.getString("UserName");
		double amount     = requestJson.getDouble("Amount");
		if(StringUtils.isEmpty(createTime)
			||StringUtils.isEmpty(username)
			|| amount <= 0.0){
			respMsg = Comm.getErrorMessage(ErrorCode.ERROR_DATA_FORMAT);
			return respMsg;
			
		}
		JSONObject resultJson = new JSONObject();
		//#start 
		InParamDMBusinessPartnerTopUpReq reqIn = new InParamDMBusinessPartnerTopUpReq();
    	reqIn.BPartnerID = username;
    	reqIn.Amount     = amount;
    	OutParamDMBusinessPartnerTopUpReq reqOut = ELSServerApiAdapter.doBusiness(reqIn);
    	if(StringUtils.isEmpty(reqOut.getUserID())){
    		resultJson.put("UserName", username);
    		resultJson.put("Amount", 0);
    		resultJson.put("UserID", "");
    		resultJson.put("UserCode", "");
    		resultJson.put("Balance", 0);
    		resultJson.put("TradeNo", "");
    	}else{
    		resultJson.put("UserName", username);
    		resultJson.put("Amount", reqOut.getAmount());
    		resultJson.put("UserID", reqOut.getUserID());
    		resultJson.put("UserCode", reqOut.getUserCode());
    		resultJson.put("Balance", reqOut.getBalance());
    		resultJson.put("TradeNo", reqOut.getTradeWaterNo());
    	}
    	
		//#end
		respMsg = Comm.getSuccessMessage(resultJson);
		return respMsg;
	}
	/**
	 * 更新余额
	 * @param updateBalance
	 *   ex:{"Amount":50,"UserID":"2015-06-07 17:39:49","TradeNo":"d7879c94fb4243ddba497bb7dc970132","OperID":"POS001"}
	 * @return
	 * @throws Exception
	 */
	private String updateBalance (String requestData, String posNo) throws JSONException, EduException{
		String respMsg = "";
		JSONObject requestJson = JSONObject.fromObject(requestData);
		String userid = requestJson.getString("UserID");
		String tradeWaterNo   = requestJson.getString("TradeNo");
		String operid = "";
		if(requestJson.has("OperID")){//(Optional)
			operid   = requestJson.getString("OperID");
		} 
		double amount     = requestJson.getDouble("Amount");
		if(StringUtils.isEmpty(userid)
			||StringUtils.isEmpty(tradeWaterNo)
			|| amount <= 0.0){
			respMsg = Comm.getErrorMessage(ErrorCode.ERROR_DATA_FORMAT);
			return respMsg;
			
		}
		if(StringUtils.isEmpty(operid)){
			operid = posNo;//POS#，记录操作的POS#
		}
		JSONObject resultJson = new JSONObject();
		//#start 
		InParamDMBusinessPartnerTopUP topUpIn= new InParamDMBusinessPartnerTopUP();
		topUpIn.OperID  = operid;
    	topUpIn.TradeWaterNo = tradeWaterNo;
    	topUpIn.BPartnerID   = userid;
    	topUpIn.Amount       = amount;
    	OutParamDMBusinessPartnerTopUP topUpOut = new OutParamDMBusinessPartnerTopUP();
    	try{
    		topUpOut = ELSServerApiAdapter.doBusiness(topUpIn);
    		resultJson.put("UserID", topUpOut.getUserID());
        	resultJson.put("TradeNo", topUpOut.getTradeWaterNo());
        	resultJson.put("Status", "Success");
    		resultJson.put("Amount", topUpOut.getAmount());
    		resultJson.put("UserName", topUpOut.getUserName());
    		resultJson.put("UserCode", topUpOut.getUserCode());
    		resultJson.put("Balance", topUpOut.getBalance());
    		respMsg = Comm.getSuccessMessage(resultJson);
    	}catch(EduException e){
    		//更新失败
    		resultJson.put("UserID", userid);
        	resultJson.put("TradeNo", tradeWaterNo);
        	resultJson.put("Status", "Failed");
    		resultJson.put("Amount", amount);
    		resultJson.put("UserName", "");
    		resultJson.put("UserCode", "");
    		resultJson.put("Balance", "");
    		JSONObject errorJson = Comm.createSuccessMessage(resultJson);
    		errorJson.put("ErrorMsg", e.getMessage());
    		respMsg = errorJson.toString();
    	}
		//#end
		
		return respMsg;
	}
	//Clean up resources
    public void destroy()
    {
    	
    }
}
