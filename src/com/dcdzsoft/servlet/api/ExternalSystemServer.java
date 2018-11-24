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

import net.sf.json.JSONArray;
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
import com.dcdzsoft.dto.business.InParamIMBusiPartnerAdd;
import com.dcdzsoft.dto.business.InParamIMBusiPartnerMod;
import com.dcdzsoft.dto.business.InParamIMBusiPartnerQry;
import com.dcdzsoft.dto.business.InParamIMBusiPartnerQryCount;
import com.dcdzsoft.dto.business.InParamIMCustomerAdd;
import com.dcdzsoft.dto.business.InParamIMCustomerMod;
import com.dcdzsoft.dto.business.InParamIMCustomerQry;
import com.dcdzsoft.dto.business.InParamIMCustomerQryCount;
import com.dcdzsoft.dto.business.InParamPTAddItemsFromPPC;
import com.dcdzsoft.dto.business.InParamTBLockerAddressQry;
import com.dcdzsoft.dto.business.OutParamAPIUserKeyAndDataVerity;
import com.dcdzsoft.dto.business.OutParamDMBusinessPartnerTopUP;
import com.dcdzsoft.dto.business.OutParamDMBusinessPartnerTopUpReq;
import com.dcdzsoft.dto.business.OutParamLockerStationAddressQry;
import com.dcdzsoft.dto.business.OutParamTBLockerAddressQry;
import com.dcdzsoft.sda.db.RowSetUtils;
import com.dcdzsoft.util.JsonUtils;
import com.dcdzsoft.util.WebUtils;

/**
 * @author zxy
 */
@SuppressWarnings("serial")
@WebServlet("/elockerserver/systemapi")
public class ExternalSystemServer extends HttpServlet {
	private static final String CONTENT_TYPE = "application/Json; charset=UTF-8";//text/html
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ExternalSystemServer() {
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
		if(!SysDict.APP_USER_TYPE_EXT_SYSTEM.equals(userKeyOut.UserType)){
    		respMsg = Comm.getErrorMessage(ErrorCode.ERROR_PARAM_KEYMD5);    
    		
        	out.println(respMsg);
    		out.close();
    		return;
    	}
		String operID = userKeyOut.getUserID();
		operID = "";//
		try {
			switch(action){
			case "addBusinessPartner":
				respMsg = addBusinessPartner(requsetData, operID);
				break;
			case "modBusinessPartner":
				respMsg = modBusinessPartner(requsetData, operID);
				break;
			case "qryBusinessPartner":
				respMsg = qryBusinessPartner(requsetData, operID);
				break;
			case "updateBPBalance":
				respMsg = updateBPBalance(requsetData, operID);
				break;
			case "addCustomer":
				respMsg = addCustomer(requsetData, operID);
				break;
			case "modCustomer":
				respMsg = modCustomer(requsetData, operID);
				break;
			case "qryCustomer":
				respMsg = qryCustomer(requsetData, operID);
				break;
			case "changePOBox":
				respMsg = changePOBox(requsetData, operID);
				break;
			case "updatePOBoxValidity":
				respMsg = updatePOBoxValidity(requsetData, operID);
				break;
			default:
				throw new EduException("action error!"+action);
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
		} catch(Exception e){
        	respMsg = Comm.getErrorMessage(ErrorCode.ERROR_UNKNOWD.getIndex(), e.getMessage());    
        	
        	out.println(respMsg);
    		out.close();
    		return;
        }
	}
	/**
	 * addBusinessPartner
	 * @param requestData
	 *   Code
	 *   Name
	 *   Username
	 *   Mobile
	 *   RegionID
	 *   CreditFlag
	 *   Address
	 *   CollectionServiceFlag
	 *   ReturnServiceFlag
	 *   Email (Optional)
	 *   CreditLimit(Optional)
	 *   Discount (Optional)
	 * @return
	 * @throws Exception
	 */
	private String addBusinessPartner (String requestData, String operID) throws JSONException, EduException{
		String respMsg = "";
		JSONObject requestJson = JSONObject.fromObject(requestData);
		
		InParamIMBusiPartnerAdd in = new InParamIMBusiPartnerAdd();
		in.OperID = operID;
		//==Mandatory
		//Code
		
		in.BPartnerID  = requestJson.getString("Code");
		/*if(!StringUtils.isAlphanumeric(in.BPartnerID)){
			throw new EduException("Code (Alphanumeric) error! "+in.BPartnerID);
		}*/
		if(in.BPartnerID.length()>16 || in.BPartnerID.length()<=0){
			throw new EduException("Code.length error!"+in.BPartnerID.length());
		}
		//BPartnerName
		in.BPartnerName = requestJson.getString("Name");
		if(in.BPartnerName.length()>60 || in.BPartnerName.length()<=0){
			throw new EduException("Name.length error!"+in.BPartnerName.length());
		}
		//Username
		in.Username = requestJson.getString("Username");
		/*if(!StringUtils.isAlphanumeric(in.Username)){
			throw new EduException("Username (Alphanumeric) error! "+in.Username);
		}*/
		if(in.Username.length()>30 || in.Username.length()<=0){
			throw new EduException("Username.length error!"+in.Username.length());
		}
		//RegionID --CollectZoneID
		in.CollectZoneID = requestJson.getString("RegionID");
		if(in.CollectZoneID.length()>10 || in.CollectZoneID.length()<=0){
			throw new EduException("RegionID.length error!"+in.CollectZoneID.length());
		}
		in.Mobile = requestJson.getString("Mobile");
		if(in.Mobile.length()>20 || in.Mobile.length()<=0){
			throw new EduException("Mobile.length error!"+in.Mobile.length());
		}
		
		//CreditFlag
		in.CreditFlag= requestJson.getString("CreditFlag");
		if("n".equalsIgnoreCase(in.CreditFlag)){
			in.CreditFlag = "0";
		}else if("y".equalsIgnoreCase(in.CreditFlag)){
			in.CreditFlag = "1";
		}else{
			throw new EduException("CreditFlag(Y/N) error!"+in.CreditFlag);
		}
		//Address
		in.Address = requestJson.getString("Address");
		if(in.Address.length()>100){
			throw new EduException("Address.length error!"+in.Address.length());
		}else{
			String[] addressLines = in.Address.split("\n");
			if(addressLines.length!=3){
				throw new EduException("Address (line3):xxxxxxxxx\nRiyadh, 11468\nSaudi Arabia");
			}
		}
		//CollectionServiceFlag
		in.CollectionServiceFlag = requestJson.getString("CollectionServiceFlag");
		if("n".equalsIgnoreCase(in.CollectionServiceFlag)){
			in.CollectionServiceFlag = "0";
		}else if("y".equalsIgnoreCase(in.CollectionServiceFlag)){
			in.CollectionServiceFlag = "1";
		}else{
			throw new EduException("CollectionServiceFlag(Y/N) error!"+in.CreditFlag);
		}
		//ReturnServiceFlag
		in.ReturnServiceFlag = requestJson.getString("ReturnServiceFlag");
		if("n".equalsIgnoreCase(in.ReturnServiceFlag)){
			in.ReturnServiceFlag = "0";
		}else if("y".equalsIgnoreCase(in.ReturnServiceFlag)){
			in.ReturnServiceFlag = "1";
		}else{
			throw new EduException("CreditFlag(Y/N) error!"+in.ReturnServiceFlag);
		}
		
		//==Optional
		if(requestJson.has("Email")&&requestJson.getString("Email").length()>0){
			in.Email = requestJson.getString("Email");
			if(in.Email.length()>64){
				throw new EduException("Email.length error!"+in.Email.length());
			}
		}
		if(requestJson.has("CreditLimit")){
			in.CreditLimit = requestJson.getDouble("CreditLimit");
		}else{
			in.CreditLimit = 0;
		}
		if(requestJson.has("Discount")){
			
			in.Discount = requestJson.getInt("Discount");
			if(in.Discount<0 || in.Discount>100){
				throw new EduException("Discount% error!(0~100)"+in.Discount);
			}
		}else{
			in.Discount = 0;
		}
		in.Password  = "";
		in.Balance = 0;
		
		JSONObject resultJson = new JSONObject();
		ELSServerApiAdapter.doBusiness(in);
		
		resultJson.put("Code", in.BPartnerID);
		resultJson.put("Name", in.BPartnerName);
		
		respMsg = Comm.getSuccessMessage(resultJson);
		return respMsg;
	}
	/**
	 * modBusinessPartner
	 * @param requestData
	 * @return
	 * @throws Exception
	 */
	private String modBusinessPartner (String requestData, String operID) throws JSONException, EduException{
		String respMsg = "";
		JSONObject requestJson = JSONObject.fromObject(requestData);
		InParamIMBusiPartnerMod in = new InParamIMBusiPartnerMod();
		in.OperID = operID;
		//==Mandatory
		//Code
		in.BPartnerID  = requestJson.getString("Code");
		if(in.BPartnerID.length()<=0){
			throw new EduException("Code.length error!"+in.BPartnerID.length());
		}
		//Name
		if(requestJson.has("Name") && requestJson.getString("Name").length()>0){
			in.BPartnerName = requestJson.getString("Name");
			if(in.BPartnerName.length()>60 ){
				throw new EduException("Name.length error!"+in.BPartnerName.length());
			}
		}
		//RegionID --CollectZoneID
		if(requestJson.has("RegionID") && requestJson.getString("RegionID").length()>0){
			in.CollectZoneID = requestJson.getString("RegionID");
			if(in.CollectZoneID.length()>10 ){
				throw new EduException("RegionID.length error!"+in.CollectZoneID.length());
			}
		}
		
		//Mobile
		if(requestJson.has("Mobile") && requestJson.getString("Mobile").length()>0){
			in.Mobile = requestJson.getString("Mobile");
			if(in.Mobile.length()>20){
				throw new EduException("Mobile.length error!"+in.Mobile.length());
			}
		}
		//CreditFlag
		if(requestJson.has("CreditFlag")){
			in.CreditFlag= requestJson.getString("CreditFlag");
			if("n".equalsIgnoreCase(in.CreditFlag)){
				in.CreditFlag = "0";
			}else if("y".equalsIgnoreCase(in.CreditFlag)){
				in.CreditFlag = "1";
			}else{
				throw new EduException("CreditFlag(Y/N) error!"+in.CreditFlag);
			}
		}
		
		//Address
		if(requestJson.has("Address") && requestJson.getString("Address").length()>0){
			in.Address = requestJson.getString("Address");
			if(in.Address.length()>100){
				throw new EduException("Address.length error!"+in.Address.length());
			}else{
				String[] addressLines = in.Address.split("\n");
				if(addressLines.length!=3){
					throw new EduException("Address (line3):xxxxxxxxx\nRiyadh, 11468\nSaudi Arabia");
				}
			}
		}
		
		//CollectionServiceFlag
		if(requestJson.has("CollectionServiceFlag") && requestJson.getString("CollectionServiceFlag").length()>0){
			in.CollectionServiceFlag = requestJson.getString("CollectionServiceFlag");
			if("n".equalsIgnoreCase(in.CollectionServiceFlag)){
				in.CollectionServiceFlag = "0";
			}else if("y".equalsIgnoreCase(in.CollectionServiceFlag)){
				in.CollectionServiceFlag = "1";
			}else{
				throw new EduException("CollectionServiceFlag(Y/N) error!"+in.CreditFlag);
			}
		}
		
		//ReturnServiceFlag
		if(requestJson.has("ReturnServiceFlag") && requestJson.getString("ReturnServiceFlag").length()>0){
			in.ReturnServiceFlag = requestJson.getString("ReturnServiceFlag");
			if("n".equalsIgnoreCase(in.ReturnServiceFlag)){
				in.ReturnServiceFlag = "0";
			}else if("y".equalsIgnoreCase(in.ReturnServiceFlag)){
				in.ReturnServiceFlag = "1";
			}else{
				throw new EduException("CreditFlag(Y/N) error!"+in.ReturnServiceFlag);
			}
		}
		
		if(requestJson.has("Email") && requestJson.getString("Email").length()>0){
			in.Email = requestJson.getString("Email");
			if(in.Email.length()>64){
				throw new EduException("Email.length error!"+in.Email.length());
			}
		}
		if(requestJson.has("CreditLimit")){
			in.CreditLimit = requestJson.getDouble("CreditLimit");
		}else{
			in.CreditLimit = 0;
		}
		if(requestJson.has("Discount")){
			in.Discount = requestJson.getInt("Discount");
			if(in.Discount<0 || in.Discount>100){
				throw new EduException("Discount% error!(0~100)"+in.Discount);
			}
		}else{
			in.Discount = 0;
		}
		if(requestJson.has("Latitude") && requestJson.getDouble("Latitude") != 0.0){
			in.Latitude = requestJson.getDouble("Latitude");
		}
		if(requestJson.has("Longitude") && requestJson.getDouble("Longitude") != 0.0){
			in.Longitude = requestJson.getDouble("Longitude");
		}
		in.Balance = 0;
		
		
		ELSServerApiAdapter.doBusiness(in);
		
		JSONObject resultJson = new JSONObject();
		resultJson.put("Code", in.BPartnerID);
		
		respMsg = Comm.getSuccessMessage(resultJson);
		return respMsg;
	}
	/**
	 * qryBusinessPartner
	 * @param requestData
	 * @return
	 * @throws Exception
	 */
	private String qryBusinessPartner (String requestData, String operID) throws JSONException, EduException{
		String respMsg = "Function has not been realized";
		JSONObject requestJson = JSONObject.fromObject(requestData);
		//System.out.print("requestJson:"+requestJson.toString());
		//Code
		InParamIMBusiPartnerQry in = new InParamIMBusiPartnerQry();
		in.OperID = operID;
		if(requestJson.has("Code")){
			in.BPartnerID  = requestJson.getString("Code");
		}
		
		if(requestJson.has("Name")){
			in.BPartnerName  = requestJson.getString("Name");
		}
		//RegionID --CollectZoneID
		if(requestJson.has("RegionID")){
			in.CollectZoneID = requestJson.getString("RegionID");
		}
		if(requestJson.has("recordBegin")){
			in.recordBegin = requestJson.getInt("recordBegin");
			if(in.recordBegin<0){
				in.recordBegin = 0;
			}
		}
		if(requestJson.has("recordNum")){
			in.recordNum = requestJson.getInt("recordNum");
			if(in.recordNum<0){
				in.recordNum = 0;
			}
		}
		
		JSONArray resultsJsonArray = new JSONArray();
		int total = 0;
		//
		InParamIMBusiPartnerQryCount inCount = new InParamIMBusiPartnerQryCount();
		inCount.OperID = operID;
		inCount.BPartnerID = in.BPartnerID;
		inCount.BPartnerName = in.BPartnerName;
		inCount.CollectZoneID = in.CollectZoneID;
		total = ELSServerApiAdapter.doBusiness(inCount);
		//
		if(total>0){
			javax.sql.RowSet rset = ELSServerApiAdapter.doBusiness(in);
			while (RowSetUtils.rowsetNext(rset)) {
				JSONObject resultJson = new JSONObject();
				resultJson.put("Code", RowSetUtils.getStringValue(rset, "BPartnerID"));
				resultJson.put("Name", RowSetUtils.getStringValue(rset, "BPartnerName"));
				resultJson.put("Username", RowSetUtils.getStringValue(rset, "Username"));
				resultJson.put("Mobile", RowSetUtils.getStringValue(rset, "Mobile"));
				resultJson.put("CreditFlag", RowSetUtils.getStringValue(rset, "CreditFlagName"));
				resultJson.put("Address", RowSetUtils.getStringValue(rset, "Address"));
				resultJson.put("RegionID", RowSetUtils.getStringValue(rset, "CollectZoneID"));
				resultJson.put("RegionName", RowSetUtils.getStringValue(rset, "CollectZoneName"));
				resultJson.put("CollectionServiceFlag", RowSetUtils.getStringValue(rset, "CollectServFlagName"));
				resultJson.put("ReturnServiceFlag", RowSetUtils.getStringValue(rset, "ReturnServFlagName"));
				resultJson.put("Email", RowSetUtils.getStringValue(rset, "Email"));
				resultJson.put("CreditLimit", RowSetUtils.getDoubleValue(rset, "CreditLimit"));
				resultJson.put("Discount", RowSetUtils.getIntValue(rset, "Discount"));
				resultJson.put("Balance", RowSetUtils.getDoubleValue(rset, "Balance"));
				resultJson.put("Latitude", RowSetUtils.getDoubleValue(rset, "Latitude"));
				resultJson.put("Longitude", RowSetUtils.getDoubleValue(rset, "Longitude"));
				resultJson.put("Remark", RowSetUtils.getStringValue(rset, "Remark"));
	        	resultsJsonArray.add(resultJson);
	        }
		}
		respMsg = Comm.getSuccessMessage(resultsJsonArray, total);
		
		return respMsg;
	}
	/**
	 * updateBPBalance
	 * @param requestData
	 * @return
	 * @throws Exception
	 */
	private String updateBPBalance (String requestData, String operID) throws JSONException, EduException{
		String respMsg = "Function has not been realized";
		JSONObject requestJson = JSONObject.fromObject(requestData);
		//System.out.print("requestJson:"+requestJson.toString());
		
		InParamDMBusinessPartnerTopUpReq reqIn = new InParamDMBusinessPartnerTopUpReq();
		reqIn.BPartnerID  = requestJson.getString("Code");
		if(reqIn.BPartnerID.length()<=0){
			throw new EduException("Code.length error!"+reqIn.BPartnerID.length());
		}
		reqIn.TradeWaterNo = requestJson.getString("TradeWaterNo");
		if(reqIn.TradeWaterNo.length()>32 ||reqIn.TradeWaterNo.length()<=0){
			throw new EduException("TradeWaterNo.length error!"+reqIn.TradeWaterNo.length());
		}
		reqIn.Amount     = requestJson.getDouble("Amount");
		if(reqIn.Amount<=0.0){
			throw new EduException("Amount error!"+reqIn.Amount);
		}
		if(requestJson.has("Remark")&&requestJson.getString("Remark").length()>0){
			
			reqIn.Remark     = requestJson.getString("Remark");
			if(reqIn.Remark.length()>255){
				throw new EduException("Remark.length error!"+reqIn.Remark.length());
			}
		}
		
		OutParamDMBusinessPartnerTopUpReq reqOut = ELSServerApiAdapter.doBusiness(reqIn);
    	
    	InParamDMBusinessPartnerTopUP topUpIn= new InParamDMBusinessPartnerTopUP();
		topUpIn.OperID  = operID;
    	topUpIn.TradeWaterNo = reqOut.TradeWaterNo;
    	topUpIn.BPartnerID   = reqOut.UserID;
    	topUpIn.Amount       = reqOut.getAmount();
    	OutParamDMBusinessPartnerTopUP topUpOut = new OutParamDMBusinessPartnerTopUP();
    	topUpOut = ELSServerApiAdapter.doBusiness(topUpIn);
    	
    	JSONObject resultJson = new JSONObject();
    	resultJson.put("Code", topUpOut.UserID);
    	resultJson.put("TradeWaterNo", topUpOut.TradeWaterNo);
    	resultJson.put("Amount", topUpOut.Amount);
    	resultJson.put("Balance", topUpOut.Balance);
		
		respMsg = Comm.getSuccessMessage(resultJson);
		return respMsg;
	}
	/**
	 * addCustomer
	 * @param requestData
	 * @return
	 * @throws Exception
	 */
	private String addCustomer (String requestData, String operID) throws JSONException, EduException{
		String respMsg = "Function has not been realized";
		JSONObject requestJson = JSONObject.fromObject(requestData);
		//System.out.print("requestJson:"+requestJson.toString());
		InParamIMCustomerAdd in = new InParamIMCustomerAdd();
		in.CustomerID = requestJson.getString("Code");
		/*if(!StringUtils.isAlphanumeric(in.CustomerID)){
			throw new EduException("CustomerID (Alphanumeric) error! "+in.CustomerID);
		}*/
		if(in.CustomerID.length()>16 || in.CustomerID.length()<=0){
			throw new EduException("Code.length error!"+in.CustomerID.length());
		}
		//BPartnerName
		in.CustomerName = requestJson.getString("Name");
		if(in.CustomerName.length()>60 || in.CustomerName.length()<=0){
			throw new EduException("Name.length error!"+in.CustomerName.length());
		}
		//LockerNo
		in.TerminalNo = requestJson.getString("LockerNo");
		if( in.TerminalNo.length()<=0){//in.TerminalNo.length()>20 ||
			throw new EduException("LockerNo.length error!"+in.TerminalNo.length());
		}
		//Mobile
		in.Mobile = requestJson.getString("Mobile");
		if(in.Mobile.length()>20 || in.Mobile.length()<=0){
			throw new EduException("Mobile.length error!"+in.Mobile.length());
		}
		in.Months = requestJson.getInt("Months");
		switch(in.Months){
		case 3:
		case 6:
		case 12:
		case 24:
		case 36:
			break;
		default:
			throw new EduException("Months([3,6,12,24,36]) error!"+in.Months);
		}
		//Active
		in.Active = requestJson.getString("Active");
		if("n".equalsIgnoreCase(in.Active)){
			in.Active = "0";
		}else if("y".equalsIgnoreCase(in.Active)){
			in.Active = "1";
		}else{
			throw new EduException("Active(Y/N) error!"+in.Active);
		}
		
		//==Optional
		if(requestJson.has("Email")&&requestJson.getString("Email").length()>0){
			in.Email = requestJson.getString("Email");
			if(in.Email.length()>64){
				throw new EduException("Email.length error!"+in.Email.length());
			}
		}
		if(requestJson.has("IDCard")&&requestJson.getString("IDCard").length()>0){
			in.IDCard = requestJson.getString("IDCard");
			if(in.IDCard.length()>30){
				throw new EduException("IDCard.length error!"+in.IDCard.length());
			}
		}
		if(requestJson.has("Remark")&&requestJson.getString("Remark").length()>0){
			in.Remark = requestJson.getString("Remark");
			if(in.Remark.length()>255){
				throw new EduException("Remark.length error!"+in.Remark.length());
			}
		}
		int result = ELSServerApiAdapter.doBusiness(in);
		JSONObject resultJson = new JSONObject();
		resultJson.put("Code", in.CustomerID);
		resultJson.put("Status", result);
		respMsg = Comm.getSuccessMessage(resultJson);
		return respMsg;
	}
	/**
	 * modCustomer
	 * @param requestData
	 * @return
	 * @throws Exception
	 */
	private String modCustomer (String requestData, String operID) throws JSONException, EduException{
		String respMsg = "Function has not been realized";
		JSONObject requestJson = JSONObject.fromObject(requestData);
		//System.out.print("requestJson:"+requestJson.toString());
		InParamIMCustomerMod in = new InParamIMCustomerMod();
		in.CustomerID = requestJson.getString("Code");
		if(in.CustomerID.length()<=0){
			throw new EduException("Code.length error!"+in.CustomerID.length());
		}
		//BPartnerName
		if(requestJson.has("Name")&& requestJson.getString("Name").length()>0){
			in.CustomerName = requestJson.getString("Name");
			if(in.CustomerName.length()>60 ){
				throw new EduException("Name.length error!"+in.CustomerName.length());
			}
		}
		
		//Mobile
		if(requestJson.has("Mobile")&& requestJson.getString("Mobile").length()>0){
			in.Mobile = requestJson.getString("Mobile");
			if(in.Mobile.length()>20){
				throw new EduException("Mobile.length error!"+in.Mobile.length());
			}
		}
		//
		if(requestJson.has("Months")&& requestJson.getInt("Months")>0){
			in.Months = requestJson.getInt("Months");
			switch(in.Months){
			case 0:
			case 3:
			case 6:
			case 12:
			case 24:
			case 36:
				break;
			default:
				throw new EduException("Months([0,3,6,12,24,36]) error!"+in.Months);
			}
		}
		
		//Active
		in.Active = requestJson.getString("Active");
		if("n".equalsIgnoreCase(in.Active)){
			in.Active = "0";
		}else if("y".equalsIgnoreCase(in.Active)){
			in.Active = "1";
		}else{
			throw new EduException("Active(Y/N) error!"+in.Active);
		}
		
		//==Optional
		if(requestJson.has("Email")&&requestJson.getString("Email").length()>0){
			in.Email = requestJson.getString("Email");
			if(in.Email.length()>64){
				throw new EduException("Email.length error!"+in.Email.length());
			}
		}
		if(requestJson.has("IDCard")&&requestJson.getString("IDCard").length()>0){
			in.IDCard = requestJson.getString("IDCard");
			if(in.IDCard.length()>30){
				throw new EduException("IDCard.length error!"+in.IDCard.length());
			}
		}
		if(requestJson.has("Remark")&&requestJson.getString("Remark").length()>0){
			in.Remark = requestJson.getString("Remark");
			if(in.Remark.length()>255){
				throw new EduException("Remark.length error!"+in.Remark.length());
			}
		}
		int result = ELSServerApiAdapter.doBusiness(in);
		JSONObject resultJson = new JSONObject();
		resultJson.put("Code", in.CustomerID);
		resultJson.put("Status", result);
		respMsg = Comm.getSuccessMessage(resultJson);
		return respMsg;
	}
	/**
	 * qryCustomer
	 * @param 
	 * @return
	 * @throws Exception
	 */
	private String qryCustomer (String requestData, String operID) throws JSONException, EduException{
		String respMsg = "Function has not been realized";
		JSONObject requestJson = JSONObject.fromObject(requestData);
		//System.out.print("requestJson:"+requestJson.toString());
		InParamIMCustomerQry in = new InParamIMCustomerQry();
		in.OperID = operID;
		if(requestJson.has("Code")){
			in.CustomerID  = requestJson.getString("Code");
		}
		
		if(requestJson.has("Name")){
			in.CustomerName  = requestJson.getString("Name");
		}
		if(requestJson.has("Mobile")){
			in.Mobile  = requestJson.getString("Mobile");
		}
		if(requestJson.has("recordBegin")){
			in.recordBegin = requestJson.getInt("recordBegin");
			if(in.recordBegin<0){
				in.recordBegin = 0;
			}
		}
		if(requestJson.has("recordNum")){
			in.recordNum = requestJson.getInt("recordNum");
			if(in.recordNum<0){
				in.recordNum = 0;
			}
		}
		
		JSONArray resultsJsonArray = new JSONArray();
		int total = 0;
		//
		InParamIMCustomerQryCount inCount = new InParamIMCustomerQryCount();
		inCount.OperID = operID;
		inCount.CustomerID = in.CustomerID;
		inCount.CustomerName = in.CustomerName;
		inCount.Mobile = in.Mobile;
		total = ELSServerApiAdapter.doBusiness(inCount);
		//
		if(total>0){
			javax.sql.RowSet rset = ELSServerApiAdapter.doBusiness(in);
			while (RowSetUtils.rowsetNext(rset)) {
				JSONObject resultJson = new JSONObject();
				resultJson.put("Code", RowSetUtils.getStringValue(rset, "CustomerID"));
				resultJson.put("Name", RowSetUtils.getStringValue(rset, "CustomerName"));
				resultJson.put("Mobile", RowSetUtils.getStringValue(rset, "Mobile"));
				resultJson.put("LockerNo", RowSetUtils.getStringValue(rset, "TerminalNo"));
				resultJson.put("Address", RowSetUtils.getStringValue(rset, "Address"));
				resultJson.put("Validity", RowSetUtils.getStringValue(rset, "Validity"));
				resultJson.put("Email", RowSetUtils.getStringValue(rset, "Email"));
				resultJson.put("IDCard", RowSetUtils.getStringValue(rset, "IDCard"));
				resultJson.put("Status", RowSetUtils.getIntValue(rset, "Status"));
				resultJson.put("Remark", RowSetUtils.getStringValue(rset, "Remark"));
	        	resultsJsonArray.add(resultJson);
	        }
		}
		respMsg = Comm.getSuccessMessage(resultsJsonArray, total);
		return respMsg;
	}
	/**
	 * changePOBox
	 * @param requestData
	 * @return
	 * @throws Exception
	 */
	private String changePOBox (String requestData, String operID) throws JSONException, EduException{
		String respMsg = "Function has not been realized";
		JSONObject requestJson = JSONObject.fromObject(requestData);
		//System.out.print("requestJson:"+requestJson.toString());
		InParamIMCustomerMod in = new InParamIMCustomerMod();
		in.CustomerID = requestJson.getString("Code");
		if(in.CustomerID.length()<=0){
			throw new EduException("Code.length error!"+in.CustomerID.length());
		}
		in.TerminalNo =  requestJson.getString("NewLockerNo");
		if(in.TerminalNo.length()<=0){
			throw new EduException("NewLockerNo.length error!"+in.TerminalNo.length());
		}
		in.Active = "1";
		int result = ELSServerApiAdapter.doBusiness(in);
		JSONObject resultJson = new JSONObject();
		resultJson.put("Code", in.CustomerID);
		resultJson.put("Status", result);
		respMsg = Comm.getSuccessMessage(resultJson);
		return respMsg;
	}
	/**
	 * updatePOBoxValidity
	 * @param requestData
	 * @return
	 * @throws Exception
	 */
	private String updatePOBoxValidity (String requestData, String operID) throws JSONException, EduException{
		String respMsg = "Function has not been realized";
		JSONObject requestJson = JSONObject.fromObject(requestData);
		//System.out.print("requestJson:"+requestJson.toString());
		InParamIMCustomerMod in = new InParamIMCustomerMod();
		in.CustomerID = requestJson.getString("Code");
		if(in.CustomerID.length()<=0){
			throw new EduException("Code.length error!"+in.CustomerID.length());
		}
		if(requestJson.has("Validity")&& requestJson.getString("Validity").length()>0){
			String dataStr = requestJson.getString("Validity");
			try{
				in.ValidityDate = java.sql.Date.valueOf(dataStr);
			}catch(IllegalArgumentException e){
				throw new EduException("Validity(yyyy-mm-dd) error!"+dataStr);
			}
		}else{
			throw new EduException("Validity error!(yyyy-mm-dd)");
		}
		
		in.Active = "1";
		int result = ELSServerApiAdapter.doBusiness(in);
		JSONObject resultJson = new JSONObject();
		resultJson.put("Code", in.CustomerID);
		resultJson.put("Status", result);
		respMsg = Comm.getSuccessMessage(resultJson);
		return respMsg;
	}
	//Clean up resources
    public void destroy()
    {
    	
    }
}
