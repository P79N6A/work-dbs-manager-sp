package com.dcdzsoft.servlet.api;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import com.dcdzsoft.EduException;
import com.dcdzsoft.business.api.Comm;
import com.dcdzsoft.business.api.ELSServerApiAdapter;
import com.dcdzsoft.business.api.Comm.ErrorCode;
import com.dcdzsoft.client.web.DMWebClientAdapter;
import com.dcdzsoft.config.ApplicationConfig;
import com.dcdzsoft.constant.SysDict;
import com.dcdzsoft.dto.business.InParamDMDeliveryCreate;
import com.dcdzsoft.dto.business.InParamDMDeliveryE1InfoQry;
import com.dcdzsoft.dto.business.OutParamAPIUserKeyAndDataVerity;
import com.dcdzsoft.dto.business.OutParamDMDeliveryCreate;
import com.dcdzsoft.dto.business.OutParamDMDeliveryE1InfoQry;
import com.dcdzsoft.print.MyPrintUtils;

/**
 */
@WebServlet("/elockerserver/createitemapi")
public class CreateItemServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static ApplicationConfig apCfg = ApplicationConfig.getInstance();
	private String reportPath = "";
	public void init()
	        throws ServletException
	{
	   ServletContext context = getServletContext();
	        
	   String realPath = context.getRealPath("/");
	   if(!realPath.endsWith("/")){
		   realPath = realPath + "/";
	   }
	   reportPath = realPath + "report/";
	        
	}
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateItemServlet() {
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
		if(!SysDict.APP_USER_TYPE_BUSINESS_WEBSIT.equals(userKeyOut.UserType)){
    		respMsg = Comm.getErrorMessage(ErrorCode.ERROR_PARAM_KEYMD5);    
    		
        	out.println(respMsg);
    		out.close();
    		return;
    	}
		String userid = userKeyOut.getUserID();
		try {
			switch(action){
			case "createMailItem":
				respMsg = createMailOrReturnItem(requsetData, userid, 0);
				break;
			case "createReturnItem":
				respMsg = createMailOrReturnItem(requsetData, userid, 1);//1-return
				break;
			case "getE1FileAgain":
				respMsg = getE1FileAgain(requsetData, userid);
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
		} catch(Exception e){
        	respMsg = Comm.getErrorMessage(ErrorCode.ERROR_UNKNOWD.getIndex(), e.getMessage());    
        	
        	out.println(respMsg);
    		out.close();
    		return;
        }
	}
	/**
	 * 商业合作伙伴网站创建订单
	 * @param requestData
	 *   ex:{"UserID":"","From":"","To":"","ServiceID":"","Size":""}
	 * @param userID  BP#
	 * @param returnFlag 1-Return item, other-Send/Mail item
	 * @return
	 * @throws Exception
	 */
	private String createMailOrReturnItem (String requestData, String userID, int returnFlag) throws JSONException, EduException{
		String respMsg = "";
		JSONObject requestJson = JSONObject.fromObject(requestData);
		String partnerid      = userID;//requestJson.getString("UserID");
		String mobile         = requestJson.getString("CustomerMobile");
		String name           = requestJson.getString("CustomerName");
		String addrLine1      = requestJson.getString("AddressLine1");
		String addrLine2      = requestJson.getString("AddressLine2");
		String addrLine3      = requestJson.getString("AddressLine3");
		String password       = requestJson.getString("Password");
		String serviceid      = requestJson.getString("ServiceID");
		int parcelsize        = requestJson.getInt("Size");
		if(StringUtils.isEmpty(partnerid)
			||StringUtils.isEmpty(mobile)
			||StringUtils.isEmpty(name)
			||StringUtils.isEmpty(addrLine1)
			||StringUtils.isEmpty(addrLine2)
			||StringUtils.isEmpty(addrLine3)
			||StringUtils.isEmpty(password)
			||StringUtils.isEmpty(serviceid)){
			respMsg = Comm.getErrorMessage(ErrorCode.ERROR_DATA_FORMAT);
			return respMsg;
		}
		if(requestJson.has("UserID")&& requestJson.getString("UserID").length()>=0){
			if(!userID.equals(requestJson.getString("UserID"))){
				throw new EduException("UserID(BP#) error!");
			}
		}
		
		//检查BP密码
		ELSServerApiAdapter.doBusiness(partnerid, password);
		
		addrLine1 = addrLine1.replaceAll("\n", " ").trim();
		addrLine2 = addrLine2.replaceAll("\n", " ").trim();
		addrLine3 = addrLine3.replaceAll("\n", " ").trim();
		if(addrLine1.length()>64 || addrLine1.length()<=0
			|| addrLine2.length()>64 || addrLine2.length()<=0
			|| addrLine3.length()>64|| addrLine3.length()<=0){
			throw new EduException("AddressLine.length error!");
		}
		if(parcelsize<0 || parcelsize>2){//0-Small,1-Medium,2-Larger
			throw new EduException("ParcelSize error!(0-Small,1-Medium,2-Larger)");
		}
		
		
		String CustomerAddress = addrLine1+"\n"+addrLine2+"\n"+addrLine3;
		JSONObject resultJson = new JSONObject();
		//#start 创建订单
		InParamDMDeliveryCreate in = new InParamDMDeliveryCreate();
		in.BPartnerID = partnerid;
		in.ParcelSize = ""+parcelsize;
		in.ServiceID = serviceid;
		in.CollectionOption = "0";
		in.CustomerAddress = CustomerAddress;
		in.CustomerName    = name;
		if(returnFlag==1){
			in.ReturnOption    = "1";
		}else{
			in.ReturnOption    = "0";
		}
		in.CustomerMobile = mobile;
		
		OutParamDMDeliveryCreate out = ELSServerApiAdapter.doBusiness(in);
		
		String errormsg =  "";
		switch(out.getResult()){//1-账户余额不足;2-无退件服务;0-成功
		case 0:
			break;
		case 1:
			throw new EduException(com.dcdzsoft.constant.ErrorCode.ERR_BALANCE_NOT_ENOUGH);
		case 2:
			throw new EduException(com.dcdzsoft.constant.ErrorCode.ERR_NOTRETURNSERVICE);
		}
		//#end
		
		//生成E1
		String itemcode = out.getPackageID();
		
		try {
			String e1fileUri = createE1File(partnerid, itemcode);
			resultJson.put("Status", "Success");
			resultJson.put("ItemCode", itemcode);
			resultJson.put("E1Uri", e1fileUri);
			respMsg = Comm.getSuccessMessage(resultJson);
			return respMsg;
		} catch (EduException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resultJson.put("Status", "Success");
			resultJson.put("ItemCode", out.getPackageID());
			resultJson.put("E1Uri", "");
    		JSONObject errorJson = Comm.createSuccessMessage(resultJson);
    		errorJson.put("ErrorMsg", e.getMessage());
    		respMsg = errorJson.toString();
    		return respMsg;
		}
	}
	/**
	 * 商业合作伙伴网站获取创建订单的E1面单文件
	 * @param requestData
	 *   ex:{"UserID":"","ItemCode":""}
	 * @param userID  BP#
	 * @return
	 * @throws Exception
	 */
	private String getE1FileAgain (String requestData, String userID) throws JSONException, EduException{
		String respMsg = "";
		JSONObject requestJson = JSONObject.fromObject(requestData);
		String partnerid      = userID;//requestJson.getString("UserID");
		String itemcode  = requestJson.getString("ItemCode");
		String password       = requestJson.getString("Password");
		if(StringUtils.isEmpty(itemcode)
			||StringUtils.isEmpty(password)){
			respMsg = Comm.getErrorMessage(ErrorCode.ERROR_DATA_FORMAT);
			return respMsg;
		}
		if(requestJson.has("UserID")&& requestJson.getString("UserID").length()>=0){
			if(!userID.equals(requestJson.getString("UserID"))){
				throw new EduException("UserID(BP#) error!");
			}
		}
		
		//检查BP密码
		ELSServerApiAdapter.doBusiness(partnerid, password);
		//
		JSONObject resultJson = new JSONObject();

		String e1fileUri = "";
		//#start检查E1是否已存在
		/*String e1filePath = apCfg.getFullE1FilePath() + "/" + partnerid;
		String e1fileName = itemcode + ".pdf";
		File elFile = new File(e1filePath+"/"+e1fileName);
		e1fileUri = apCfg.getMbUri()+apCfg.getE1FilePath()+"/" + partnerid+"/"+e1fileName;
		if(elFile.exists()){
			resultJson.put("Status", "Success");
			resultJson.put("ItemCode", itemcode);
			resultJson.put("E1Uri", e1fileUri);
			return Comm.getSuccessMessage(resultJson);
		}*/
		//#end
		e1fileUri = createE1File(partnerid, itemcode);
		resultJson.put("Status", "Success");
		resultJson.put("ItemCode", itemcode);
		resultJson.put("E1Uri", e1fileUri);
		respMsg = Comm.getSuccessMessage(resultJson);
		return respMsg;
	}
	/**
	 * 
	 * @param partnerid
	 * @param itemcode
	 * @return
	 * @throws EduException
	 */
	private String createE1File(String partnerid, String itemcode) throws EduException{
		//#start查询订单号
		InParamDMDeliveryE1InfoQry printparam = new InParamDMDeliveryE1InfoQry();
		printparam.setBPartnerID(partnerid);
		printparam.setPackageID(itemcode);
		OutParamDMDeliveryE1InfoQry presult = DMWebClientAdapter.doBusiness(printparam);
		//#end
		//#start 包裹单号不存在或已过期
		if(StringUtils.isEmpty(presult.getPackageID())){
			//String errormsg =  ErrorMsgConfig.getLocalizedMessage(com.dcdzsoft.constant.ErrorCode.ERR_COLLECT_ITEM_NOT_EXISTS);
    		//errormsg +="  "+ ErrorMsgConfig2.getLocalizedMessage(com.dcdzsoft.constant.ErrorCode.ERR_COLLECT_ITEMCODE_EXPIRED);
    		throw new EduException(com.dcdzsoft.constant.ErrorCode.ERR_COLLECT_ITEM_NOT_EXISTS);
		}
		//#end
		//设置PDF格式  
		Map<String,Object> parameters=new HashMap<String, Object>();
		parameters.put("no", presult.getPackageID());
		parameters.put("service", presult.getService());
		parameters.put("size", presult.getParcelSize());
		parameters.put("userid", presult.getBPartnerID());
		
		String strSpace="---------------------------------------------------<br />";
		if("0".equals(presult.getReturnFlag())){
			String addressto = presult.getCustomerAddress().replaceAll("\n", "<br />");
			addressto = addressto.replaceAll("\\\\n", "<br />");
			String addressfrom = presult.getAddress().replaceAll("\n", "<br />");
			addressfrom = addressfrom.replaceAll("\\\\n", "<br />");
			parameters.put("to", "To");
			parameters.put("htmlto", strSpace
					+"<b>"+presult.getCustomerName()+"</b><br />"
					+"<b>Mobile:"+presult.getCustomerMobile()+"</b><br />"
					+addressto);
			
			parameters.put("htmlfrom", strSpace
					+ "<b>"+presult.getBPartnerName()+"</b><br />"
					+ "<b>Mobile:"+presult.getMobile()+"</b><br />"
					+ addressfrom);
		}else{
			String addressto = presult.getAddress().replaceAll("\n", "<br />");
			addressto = addressto.replaceAll("\\\\n", "<br />");
			String addressfrom = presult.getCustomerAddress().replaceAll("\n", "<br />");
			addressfrom = addressfrom.replaceAll("\\\\n", "<br />");
			parameters.put("to", "Return To");
			parameters.put("htmlfrom", strSpace
					+"<b>"+presult.getCustomerName()+"</b><br />"
					+"<b>Mobile:"+presult.getCustomerMobile()+"</b><br />"
					+addressfrom);
			parameters.put("htmlto", strSpace
					+ "<b>"+presult.getBPartnerName()+"</b><br />"
					+ "<b>Mobile:"+presult.getMobile()+"</b><br />"
					+ addressto);
		}
		byte[] bytes = MyPrintUtils.printToPdf(parameters, reportPath+"EDS.jasper");
		
		String e1filePath = apCfg.getFullE1FilePath() + "/" + partnerid;
		
		File e1FilePath = new File(e1filePath);
		if (!e1FilePath.isDirectory())
			e1FilePath.mkdirs();
		
		String e1fileName = itemcode + ".pdf";
		File elFile = new File(e1filePath+"/"+e1fileName);
		String e1fileUri = apCfg.getMbUri()+apCfg.getE1FilePath()+"/" + partnerid+"/"+e1fileName;
		
		//保存E1文件
		try (FileOutputStream fop = new FileOutputStream(elFile)) {
			// if file doesn't exists, then create it
			if (!elFile.exists()) {
				elFile.createNewFile();
			}

			fop.write(bytes);
			fop.flush();
			fop.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return e1fileUri;
	}
	//Clean up resources
    public void destroy()
    {
    	
    }

}
