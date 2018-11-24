package com.dcdzsoft.servlet.api;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.dcdzsoft.dto.business.InParamAPIUserKeyAndDataVerity;
import com.dcdzsoft.dto.business.InParamDMCollectAgentItemQry;
import com.dcdzsoft.dto.business.InParamDMDeliveryCollected;
import com.dcdzsoft.dto.business.InParamLockerStationAddressQry;
import com.dcdzsoft.dto.business.InParamPTAddItemsFromPPC;
import com.dcdzsoft.dto.business.InParamTBLockerAddressQry;
import com.dcdzsoft.dto.business.OutParamAPIUserKeyAndDataVerity;
import com.dcdzsoft.dto.business.OutParamDMCollectAgentItemQry;
import com.dcdzsoft.dto.business.OutParamDMDeliveryCollected;
import com.dcdzsoft.dto.business.OutParamLockerStationAddressQry;
import com.dcdzsoft.dto.business.OutParamTBLockerAddressQry;
import com.dcdzsoft.util.WebUtils;

/**
 * @see 查询柜体位置信息
 * action=queryByLockerID 提交订单
 * action=queryByBounds 订单状态查询
 */
@WebServlet("/elockerserver/lockerapi")
public class LockerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    public void init(){
    	
    }
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LockerServlet() {
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
		if(!SysDict.APP_USER_TYPE_ECOMMERCE_WEBSIT.equals(userKeyOut.UserType)){
    		respMsg = Comm.getErrorMessage(ErrorCode.ERROR_PARAM_KEYMD5);    
    		
        	out.println(respMsg);
    		out.close();
    		return;
    	}
		try {
			switch(action){
			case "queryByLockerID":
				respMsg = queryByLockerID(requsetData);
				break;
			case "queryByBounds":
				respMsg = queryByBounds(requsetData);
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
	 * 通过LockerID查询柜体位置信息
	 * @param requestData
	 *   ex:{"LockerID":"20001"}
	 * @return
	 * @throws Exception
	 */
	private String queryByLockerID (String requestData) throws JSONException, EduException{
		String respMsg = "";
		JSONObject requestJson = JSONObject.fromObject(requestData);
		String lockerid = requestJson.getString("LockerID");
		lockerid = lockerid.trim();
		if(StringUtils.isEmpty(lockerid)){
			respMsg = Comm.getErrorMessage(ErrorCode.ERROR_DATA_FORMAT);
			return respMsg;
			
		}
		JSONObject resultJson = new JSONObject();
		//#start 查询柜体地址信息
    	
    	InParamTBLockerAddressQry in = new InParamTBLockerAddressQry();
		in.LockerID = lockerid;
		
		OutParamTBLockerAddressQry outQry = ELSServerApiAdapter.doBusiness(in);
		if(StringUtils.isNotEmpty(outQry.getLockerID())){
			resultJson.put("LID", outQry.getLockerID());
			resultJson.put("LNM", outQry.getLockerName());
			resultJson.put("ADS", outQry.getAddress());
			resultJson.put("LOC", outQry.getLocation());
			resultJson.put("LNG", outQry.getLongitude());
			resultJson.put("LAT", outQry.getLatitude());
			resultJson.put("CTY", outQry.getCity());
			resultJson.put("ZIP", outQry.getZipcode());
		}
		//#end
		respMsg = Comm.getSuccessMessage(resultJson);
		return respMsg;
	}
	/**
	 * 通过范围查询柜体位置信息
	 * @param requestData
	 *   ex:{"East":180,"West":-180,"South":-90,"North":90}
	 * @return
	 * @throws Exception
	 */
	private String queryByBounds (String requestData) throws JSONException, EduException{
		String respMsg = "";
		JSONObject requestJson = JSONObject.fromObject(requestData);
		double eastLng = requestJson.getDouble("East");
		double westLng = requestJson.getDouble("West");
		double southLat = requestJson.getDouble("South");
		double northLat = requestJson.getDouble("North");
		
		
		JSONArray resultsJsonArray = new JSONArray();
		//#start 查询柜体地址信息
		InParamLockerStationAddressQry in = new InParamLockerStationAddressQry();
		in.East = eastLng;
		in.West = westLng;
		in.South = southLat;
		in.North = northLat;
		
		java.util.List<OutParamLockerStationAddressQry> outList = ELSServerApiAdapter.doBusiness(in);
		Iterator<OutParamLockerStationAddressQry>  iter = outList.iterator();
		while(iter.hasNext())  {  
			JSONObject resultJson = new JSONObject();
			OutParamLockerStationAddressQry outQry = iter.next();  
			resultJson.put("LID", outQry.getTerminalNo());
			resultJson.put("LNM", outQry.getTerminalName());
			resultJson.put("ADS", outQry.getAddress());
			resultJson.put("LOC", outQry.getLocation());
			resultJson.put("LNG", outQry.getLongitude());
			resultJson.put("LAT", outQry.getLatitude());
			resultJson.put("CTY", outQry.getCity());
			resultJson.put("ZIP", outQry.getZipcode());
			resultsJsonArray.add(resultJson);
        }
		
		//#end
		respMsg = Comm.getSuccessMessage(resultsJsonArray);
		return respMsg;
		
	}
	//Clean up resources
    public void destroy()
    {
    	
    }

}
