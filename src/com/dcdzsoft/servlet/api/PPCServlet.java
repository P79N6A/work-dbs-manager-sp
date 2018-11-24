package com.dcdzsoft.servlet.api;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ListIterator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;

import com.dcdzsoft.EduException;
import com.dcdzsoft.business.api.Comm;
import com.dcdzsoft.business.api.ELSServerApiAdapter;
import com.dcdzsoft.business.api.Comm.ErrorCode;
import com.dcdzsoft.business.api.Comm.ResultCode;
import com.dcdzsoft.client.web.PTWebClientAdapter;
import com.dcdzsoft.constant.Constant;
import com.dcdzsoft.constant.ControlParam;
import com.dcdzsoft.constant.SysDict;
import com.dcdzsoft.dto.business.InParamPTAddItemsFromPPC;
import com.dcdzsoft.dto.business.InParamPTDeliveryItemDetail;
import com.dcdzsoft.dto.business.OutParamAPIUserKeyAndDataVerity;
import com.dcdzsoft.dto.business.OutParamPTDeliveryItemDetail;

/**
 * @see 一次请求可以包含多条订单数据
 * flag=1 提交订单
 * flag=2 订单状态查询
 */
@WebServlet("/elockerserver/ppcapi")
public class PPCServlet extends HttpServlet {
    private static Log log = org.apache.commons.logging.LogFactory.getLog(PPCServlet.class);
	private static final long serialVersionUID = 1L;
	
    public void init(){
    	
    }
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PPCServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String queryString = request.getQueryString();
		System.out.println("ELockerServer:"+queryString);
		response.setCharacterEncoding("UTF-8"); 
		response.getWriter().write("ELockerServer ppcapi "+queryString);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		
		String keymd5 = request.getParameter("keymd5");
		String datamd5 = request.getParameter("datamd5");
		String flag = request.getParameter("flag");
		String requsetData = Comm.readRequestData(request);
		
		//JSONObject errorJson = new JSONObject();
		String respMsg = "";
		OutParamAPIUserKeyAndDataVerity userKeyOut = ELSServerApiAdapter.checkApiRequest(1, keymd5, datamd5, flag, requsetData);
		
        //错误消息不为空，消息异常
		if(StringUtils.isNotEmpty(userKeyOut.getErrorMsg())){
        	respMsg = userKeyOut.getErrorMsg();
        	out.println(respMsg);
    		out.close();
    		return;
        }
		if(!SysDict.APP_USER_TYPE_PPC_SERVER.equals(userKeyOut.UserType)){
    		respMsg = Comm.getErrorMessage(ErrorCode.ERROR_PARAM_KEYMD5);    
    		
        	out.println(respMsg);
    		out.close();
    		return;
    	}
        
		String operID = "";
        try {
        	//业务处理
        	switch(flag){
			case "1"://transfer items from ppc to aac
				respMsg = transferItemFromPPC2AZC(requsetData, operID);
				break;
			case "2":
				respMsg = doPPCItemTracking(requsetData, operID);
				break;
			default :
				throw new EduException("action error!flag="+flag);
        	}
        	
        	out.println(respMsg);
    		out.close();
    		return;
        }catch(JSONException e){
			respMsg = Comm.getErrorMessage(ErrorCode.ERROR_DATA_FORMAT);
			out.println(respMsg);
    		out.close();
    		return;
        } catch (EduException e) {
			// TODO Auto-generated catch block			
        	respMsg = Comm.getErrorMessage(ErrorCode.ERROR_BUSINESS, e.getMessage());    
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
	 * transferItemFromPPC2AZC
	 * @param requestData 
	 * @return
	 * @throws Exception
	 */
	private String transferItemFromPPC2AZC (String requestData, String operID) throws JSONException, EduException{
		String respMsg = "";
		
		JSONArray itemResults = new JSONArray();//结果集
		
		JSONObject requestJson = JSONObject.fromObject(requestData);
		JSONArray itemArray = requestJson.optJSONArray("datas");
		if(itemArray==null){
			respMsg =  Comm.getErrorMessage(ErrorCode.ERROR_DATA_EMPTY);
			return respMsg;
		}
		log.debug(requestData);//手机号有变化，记录原始数据，以备验证。
		
		for (int i = 0; i < itemArray.size(); i++) {
			JSONObject itemJson = itemArray.getJSONObject(i);//订单数据
			
			JSONObject resultJson = new JSONObject();//订单处理结果
			InParamPTAddItemsFromPPC in = new InParamPTAddItemsFromPPC();
			try{
				//=================ItemID
				int itemID = itemJson.getInt("ItemID");
				resultJson.put("ItemID", itemID);
				in.Remark = "ItemID="+itemID;//
				//=================ItemCode 
				if(!itemJson.has("ItemCode")){
					resultJson.put("Status", ResultCode.ERROR_ITEMCODE_EMPTY.getIndex());
					resultJson.put("Msg", ResultCode.ERROR_ITEMCODE_EMPTY.getName());
					itemResults.add(resultJson);
					continue;
				}
				in.PackageID = itemJson.getString("ItemCode");
				resultJson.put("ItemCode", in.PackageID);
				if(in.PackageID.length()>30 || in.PackageID.length() <= 0){
					throw new EduException("ItemCode.length error()!"+in.PackageID.length());
				}
				
				//=================AZCID 
				if(itemJson.has("AZCID")&& itemJson.getString("AZCID").length()>0){
					in.ZoneID = itemJson.getString("AZCID");
				}
				//=================PPCID 
				in.PPCID = itemJson.getString("PPCID");
				if(in.PPCID.length()>10 || in.PPCID.length() <= 0){
					resultJson.put("Status", ResultCode.ERROR_PPCID_EMPTY.getIndex());
					resultJson.put("Msg", ResultCode.ERROR_PPCID_EMPTY.getName());
					itemResults.add(resultJson);
					continue;
				}
				//Mobile
				in.CustomerMobile = itemJson.getString("Mobile");
				if(in.CustomerMobile.length()>20 || in.CustomerMobile.length() <= 0){
					resultJson.put("Status", ResultCode.ERROR_MOBILE_EMPTY.getIndex());
					resultJson.put("Msg", ResultCode.ERROR_MOBILE_EMPTY.getName());
					itemResults.add(resultJson);
					continue;
				}
				//LockerID
				if(itemJson.has("LockerID")&& itemJson.getString("LockerID").length()>0){
					in.TerminalNo = itemJson.getString("LockerID");
				}
				if(itemJson.has("OwnerID")&& itemJson.getString("OwnerID").length()>0){
					in.CompanyID = itemJson.getString("OwnerID");
				}
				if(itemJson.has("CustomerID")&& itemJson.getString("CustomerID").length()>0){
					in.CustomerID  = itemJson.getString("CustomerID");
				}
				if(itemJson.has("RefNo")&& itemJson.getString("RefNo").length()>0){
					in.RefNo  = itemJson.getString("RefNo");
					if(in.RefNo.length()>30){
						throw new EduException("RefNo.length error!"+in.RefNo.length());
					}
				}
				log.debug(itemJson.toString()+","+in.CustomerMobile+","+in.PackageID);//手机号有变化，记录原始数据，以备验证。
				com.dcdzsoft.business.api.Comm.ResultCode resultCode = ELSServerApiAdapter.doBusiness(in);
				resultJson.put("Status", resultCode.getIndex());
				if(resultCode.getIndex()!=0){
					resultJson.put("Msg", resultCode.getName());
				}
				//
				itemResults.add(resultJson);
			}catch(JSONException e){
				resultJson.put("Status", ResultCode.ERROR_DATA_FORMAT.getIndex());
				resultJson.put("Msg", ResultCode.ERROR_DATA_FORMAT.getName());
				itemResults.add(resultJson);
				continue;
	        } catch (EduException e) {
				// TODO Auto-generated catch block			
	        	resultJson.put("Status", ResultCode.ERROR_BUSINESS.getIndex());
				resultJson.put("Msg", e.getMessage());
				itemResults.add(resultJson);
				continue;
			}
		}
		respMsg = Comm.getSuccessMessage(itemResults);
		return respMsg;
	}
	/**
	 * PPC订单状态跟踪
	 * @param requsetData
	 *   type 关键字类型（1-ItemCode, 2-Ref.no, 3-Mobile），默认1
	 *   key  搜索关键字
	 *   mode  搜索模式(0-last, 1-full) 默认0
	 * @return
	 *    Mode 0-null,1-full
	 * @throws JSONException, EduException
	 */
	private  String doPPCItemTracking(String requsetData, String operID)
	 throws JSONException, EduException
	{
		JSONObject requestData = JSONObject.fromObject(requsetData);
		
		/**
		 * type 关键字类型（1-ItemCode, 2-Ref.no, 3-Mobile），默认1
		 * key  搜索关键字
		 * mode  搜索模式(0-last, 1-full) 默认0
		 */
		int type = requestData.getInt("Type");
		String key = requestData.getString("Key");
		int mode = requestData.getInt("Mode");
		
		JSONObject errorJson = new JSONObject();
		JSONArray resultsArray = new JSONArray();	
		//#start输入参数检查
		if(StringUtils.isEmpty(key)){
			errorJson.put("ErrorCode", ErrorCode.SUCCESS.getIndex());
			errorJson.put("Mode", 0);
			errorJson.put("Results", resultsArray);
			return errorJson.toString();
		}
		if(type< 1 || type>2){
			type = 1;
		}
		if(mode< 0 || mode>1){
			mode = 0;
		}
		//#end
		InParamPTDeliveryItemDetail in = new InParamPTDeliveryItemDetail();
		in.Key = key;
		in.KeyType = type;
		in.Mode   = mode;
		java.util.List<OutParamPTDeliveryItemDetail> detailList =PTWebClientAdapter.doBusiness(in);
		ListIterator<OutParamPTDeliveryItemDetail> list = detailList.listIterator();
		
		net.sf.json.JSONArray recordsArray = new net.sf.json.JSONArray();
		String itemcode = "";
		int laststatus = 0;
		while(list.hasNext()){
			OutParamPTDeliveryItemDetail itemDetail = list.next();
			
			if(itemcode.isEmpty()){
				itemcode = itemDetail.ItemCode;
				laststatus = Integer.parseInt(itemDetail.ItemStatus);
				
			}
			if(!itemcode.equals(itemDetail.ItemCode)){
				//#start 添加订单号，订单状态，订单状态变更历史记录
				JSONObject itemTrack = new JSONObject();
				itemTrack.put("ItemCode", itemcode);
				itemTrack.put("ItemStatus", laststatus);
				itemTrack.put("ItemRecords", recordsArray);
				//#end
				resultsArray.add(itemTrack);
				recordsArray = new net.sf.json.JSONArray();
				itemcode = "";
			}else{
				//获取状态变更历史记录信息
				net.sf.json.JSONObject itemRecord = new net.sf.json.JSONObject();
				String dateTimeString = Constant.dateFromat.format(itemDetail.getCreateTime());
				itemRecord.put("DT", dateTimeString);//
				itemRecord.put("ST",itemDetail.getItemStatusName());//
				itemRecord.put("SN",itemDetail.getOperatorName());//
				itemRecord.put("AC",itemDetail.getActivity());//
				//添加订单状态变更历史记录信息
				recordsArray.add(itemRecord);
			}
		}
		if(!recordsArray.isEmpty()){//最后
			//#start 添加订单号，订单状态，订单状态变更历史记录
			JSONObject itemTrack = new JSONObject();
			itemTrack.put("ItemCode", itemcode);
			itemTrack.put("ItemStatus", laststatus);
			itemTrack.put("ItemRecords", recordsArray);
			//#end
			resultsArray.add(itemTrack);
		}
		//#end
		
		if(resultsArray.size() < 1){
			errorJson.put("ErrorCode", ErrorCode.SUCCESS.getIndex());
			errorJson.put("Mode", in.Mode);
			errorJson.put("Results", resultsArray);
		}else{
			errorJson.put("ErrorCode", ErrorCode.SUCCESS.getIndex());
			errorJson.put("Mode", in.Mode);
			errorJson.put("Results", resultsArray);
		}
		return errorJson.toString();
	}
	//Clean up resources
    public void destroy()
    {
    	
    }

}
