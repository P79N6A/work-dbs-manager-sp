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

import com.dcdzsoft.EduException;
import com.dcdzsoft.business.ap.APCustomerInfoQry;
import com.dcdzsoft.businessproxy.MobileProxy;
import com.dcdzsoft.config.BusiHandlerEntity;
import com.dcdzsoft.config.BusinessConfig;
import com.dcdzsoft.config.ErrorMsgConfig;
import com.dcdzsoft.constant.ErrorCode;
import com.dcdzsoft.dto.business.InParamAPCustomerGetKeyMsg;
import com.dcdzsoft.dto.business.InParamAPCustomerInfoQry;
import com.dcdzsoft.dto.business.InParamAPCustomerRegister;
import com.dcdzsoft.dto.business.InParamAPCustomerUpdate;
import com.dcdzsoft.dto.business.InParamAPLockerPoBoxQry;
import com.dcdzsoft.dto.business.OutParamAPCustomerGetKeyMsg;
import com.dcdzsoft.dto.business.OutParamAPCustomerInfoQry;
import com.dcdzsoft.dto.business.OutParamAPCustomerRegister;
import com.dcdzsoft.dto.business.OutParamAPCustomerUpdate;
import com.dcdzsoft.dto.business.OutParamAPLockerPoBoxQry;
import com.dcdzsoft.util.JsonUtils;
import com.dcdzsoft.util.StringUtils;
import com.dcdzsoft.util.WebUtils;

/**
 * 手机端用户注册ELocker
 */
@SuppressWarnings("serial")
@WebServlet("/elockerserver/register")
public class RegisterServlet extends HttpServlet {
	private static final String CONTENT_TYPE = "text/html; charset=UTF-8";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
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
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
        response.setContentType(CONTENT_TYPE);

        HttpSession session = request.getSession(true);
        String uuid = WebUtils.getStringParameter("uuid", request);
        String sUuid = WebUtils.getSessionAttribute("uuid", session);
        
        /*if (StringUtils.isEmpty(uuid) || !uuid.equals(sUuid))
        {
            PrintWriter out = response.getWriter();

            out.println(JsonUtils.getErrorJSONStr("非法的请求", null));
            out.close();

            return;
        }*/
        
        String cmd = WebUtils.getStringParameter("cmd", request);
        //BusinessConfig cfg = BusinessConfig.getInstance();
        PrintWriter out = response.getWriter();
        try
        {
	        if (StringUtils.isEmpty(cmd)) cmd = "default";
	        String resultMsg = "";
	        
	        switch(cmd){
		        case "mod"://延期+更改柜体
		        	//#start
		        	int month = WebUtils.getIntParameter("sltMonths", request);
		        	String email2 = WebUtils.getStringParameter("txtEmail", request);
		        	String code=WebUtils.getStringParameter("txtCode", request);//Verification code
		        	
		        	String modmobile = WebUtils.getStringParameter("mobile", request);
		        	String elockeridmod = WebUtils.getStringParameter("elockerid", request);
		        	
		        	request.setAttribute("mobile", modmobile);
		        	request.setAttribute("elockerid", elockeridmod);
		        	
		        	//查询locker信息
		        	InParamAPLockerPoBoxQry p112=new InParamAPLockerPoBoxQry ();
		        	p112.setLockerID(elockeridmod);
		        	OutParamAPLockerPoBoxQry elockerinfo=MobileProxy.doBusiness(p112);
		        	request.setAttribute("MobileElocker",elockerinfo);
		        	
		        	//查询Customer信息
		        	InParamAPCustomerInfoQry inmod=new InParamAPCustomerInfoQry();
		        	inmod.setCustomerID(modmobile);
		        	OutParamAPCustomerInfoQry customerInfoQry= MobileProxy.doBusiness(inmod);
		        	if(customerInfoQry==null){
		        		request.getRequestDispatcher("customer.jsp").forward(request,response);
		        		return;
		        	}
		        	customerInfoQry.setMessage(customerInfoQry.getMessage().replaceAll("\n", "<br>"));
		        	request.setAttribute("MOBILE_USER_INFO", customerInfoQry);
		        	request.setAttribute("status", customerInfoQry.getStatus());
		        	
		        	//检查校验码
		        	if (StringUtils.isEmpty(code)){
		        		request.setAttribute("err", ErrorMsgConfig.getLocalizedMessage(ErrorCode.ERR_MOBILE_VCODE_EMPTY));
		        		request.getRequestDispatcher("update.jsp").forward(request,response);
		        		return;
		        	}
		        	//延期 OR 修改柜体
		        	try{
	        			InParamAPCustomerUpdate updatein=new InParamAPCustomerUpdate();
	        			updatein.setCustomerMobile(modmobile);
		        		updatein.setLockerID(elockeridmod);//elockerinfo.getLockerID()
		        		updatein.setKeyword(code);
		        		updatein.setCustomerEmail(email2);
		        		updatein.setMonths(month);
		        		
		        		OutParamAPCustomerUpdate  updateout=MobileProxy.doBusiness(updatein);
		        		if(updateout != null && updateout.getStatus() == 1){
		        			//resultMsg=elockerinfo.getLocation();
		        			sendCustomerInfo(elockeridmod,modmobile,modmobile, request,response);
		        			return;
		        		}else{
		        			request.setAttribute("err", ErrorMsgConfig.getLocalizedMessage(ErrorCode.ERR_MOBILE_MODFAIL));
			        		request.getRequestDispatcher("update.jsp").forward(request,response);
			        		return;
		        		}
		        	}catch(EduException ex){
		        		request.setAttribute("err", ex.getMessage());
			        	request.getRequestDispatcher("update.jsp").forward(request,response);
		        	}
		        	//#end
		        	break;
		        case "reg2":
		        	//#start
		        	String username = WebUtils.getStringParameter("txtName", request);
		        	String idcode = WebUtils.getStringParameter("txtIDCode", request);
		        	String mobile = WebUtils.getStringParameter("txtMobile", request);
		        	String email = WebUtils.getStringParameter("txtEmail", request);
		        	int months = WebUtils.getIntParameter("sltmonth", request);
		        	String elockeridreg = WebUtils.getStringParameter("elockerid", request);
		        	
		        	if (StringUtils.isEmpty(username)){
		        		request.setAttribute("mobile", mobile);
		        		request.setAttribute("err", ErrorMsgConfig.getLocalizedMessage(ErrorCode.ERR_MOBILE_USERNAMEEMPTY));
		        		request.getRequestDispatcher("customer.jsp").forward(request,response);
		        		return;
		        	}
		        	if (StringUtils.isEmpty(mobile)){
		        		request.setAttribute("mobile", mobile);
		        		request.setAttribute("err", ErrorMsgConfig.getLocalizedMessage(ErrorCode.ERR_MOBILE_USERMOBILEEMPTY));
		        		request.getRequestDispatcher("customer.jsp").forward(request,response);
		        		return;
		        	}
		        	if(StringUtils.isEmpty(elockeridreg)){
		        		resultMsg=ErrorMsgConfig.getLocalizedMessage(ErrorCode.ERR_MOBILE_RESCAN);
		        		break;
		        	}
		        	request.setAttribute("elockerid", elockeridreg);
		        	
		        	InParamAPLockerPoBoxQry p11=new InParamAPLockerPoBoxQry();
		        	p11.setLockerID(elockeridreg);
		        	OutParamAPLockerPoBoxQry elocker=MobileProxy.doBusiness(p11);
		        	if(1 != elocker.getAppRegisterFlag()){
		        		resultMsg =ErrorMsgConfig.getLocalizedMessage(ErrorCode.ERR_MOBILE_NOTALLOW);
		        		break;
		        	}
		        	
		        	request.setAttribute("MobileElocker",elocker);
		        	InParamAPCustomerRegister regin=new InParamAPCustomerRegister();
		        	regin.setLockerID(elocker.getLockerID());
		        	regin.setCustomerName(username);
		        	regin.setCustomerMobile(mobile);
		        	regin.setMonths(months);
		        	regin.setCustomerIDCard(idcode);
		        	regin.setCustomerEmail(email);
		        	try{
			        	OutParamAPCustomerRegister result3=MobileProxy.doBusiness(regin);
			        	
			        	if(result3!=null && result3.getStatus()==1){
			        		//resultMsg=result3.getMessage();
			        		sendCustomerInfo(elockeridreg, mobile, mobile, request, response);
			        	}else{
			        		request.setAttribute("mobile", mobile);
			        		request.setAttribute("err", ErrorMsgConfig.getLocalizedMessage(ErrorCode.ERR_MOBILE_USERMOBILEEMPTY));
			        		request.getRequestDispatcher("customer.jsp").forward(request,response);
			        		return;
			        	}
		        	}catch(EduException ex){
		        		request.setAttribute("mobile", mobile);
		        		request.setAttribute("err", ex.getMessage());
		        		request.getRequestDispatcher("customer.jsp").forward(request,response);
		        	}
		        	//#end
		        	break;
		        case "reg1":
		        	//#start 是注册还是查询
		        	String txtMobile = WebUtils.getStringParameter("txtMobile", request);
		        	String elockerid = WebUtils.getStringParameter("elockerid", request);
		        	if (StringUtils.isEmpty(txtMobile)){
		        		request.setAttribute("err", ErrorMsgConfig.getLocalizedMessage(ErrorCode.ERR_MOBILE_USERMOBILEEMPTY));
		        		request.getRequestDispatcher("index.jsp").forward(request,response);
		        		return;
		        	}
		        	if(StringUtils.isEmpty(elockerid)){
		        		resultMsg=ErrorMsgConfig.getLocalizedMessage(ErrorCode.ERR_MOBILE_RESCAN);
		        		break;
		        	}
		        	
		        	sendCustomerInfo(elockerid, txtMobile, txtMobile, request, response);
		        	//#end
		        	break;
		        case "code":
		        	//#start
		        	//OutParamAPCustomerInfoQry uinfocode=(OutParamAPCustomerInfoQry)session.getAttribute("MOBILE_USER_INFO");
//		        	if(uinfocode==null || StringUtils.isEmpty(uinfocode.getCustomerMobile())){
//		        		resultMsg=JsonUtils.getErrorJSONStr(ErrorMsgConfig.getLocalizedMessage(ErrorCode.ERR_MOBILE_USERMOBILEEMPTY),null);
//		        		break;
//		        	}
		        	String codmobile=WebUtils.getStringParameter("mobile", request);
		        	InParamAPCustomerGetKeyMsg codein=new InParamAPCustomerGetKeyMsg ();
		        	codein.setCustomerMobile(codmobile);
		        	OutParamAPCustomerGetKeyMsg resultcode=MobileProxy.doBusiness(codein);
		        	if(resultcode==null || resultcode.getStatus()==0){
		        		resultMsg=JsonUtils.getErrorJSONStr(ErrorMsgConfig.getLocalizedMessage(ErrorCode.ERR_MOBILE_SMSFAIL),null);
		        	}else{
		        		resultMsg=JsonUtils.getSuccessJSONStr(ErrorMsgConfig.getLocalizedMessage(ErrorCode.ERR_MOBILE_SMSSUCCESS),null);
		        	}
		        	//#end
		        	break;
		        default://来源于二维码
		        	//#start
		        	String LockerID = WebUtils.getStringParameter("lockerid", request);
		        	if(StringUtils.isEmpty(LockerID)){
		        		resultMsg=ErrorMsgConfig.getLocalizedMessage(ErrorCode.ERR_MOBILE_NOTALLOW);
		        		break;
		        	}
		        	InParamAPLockerPoBoxQry p1=new InParamAPLockerPoBoxQry ();
		        	p1.setLockerID(LockerID);
		        	OutParamAPLockerPoBoxQry result1=MobileProxy.doBusiness(p1);        	
		        	if(result1!=null){
		        		if(result1.getAppRegisterFlag()>0)
			        	{
		        			//session.setAttribute("MobileElocker", result1);
		        			request.setAttribute("elockerid", LockerID);
		        			request.setAttribute("MobileElocker",result1);//add by zxy
		        			request.getRequestDispatcher("index.jsp").forward(request,response);
			        	}else{
			        		resultMsg =ErrorMsgConfig.getLocalizedMessage(ErrorCode.ERR_MOBILE_NOTALLOW);
			        	}
		        	}else{
		        		resultMsg = ErrorMsgConfig.getLocalizedMessage(ErrorCode.ERR_MOBILE_LOCKERNOTEXSISTS);
		        	}
		        	//#end
                    break;
	        }
	        
	        out.println(resultMsg);
            out.flush();
            out.close();
        }
        catch (Exception e)
        {
            String errMsg = JsonUtils.getErrorJSONStr(ErrorCode.ERR_MOBILE_SYSTME, e);
            System.err.println("[Customer Service]" + e.getMessage());

            out.println(errMsg);
            out.flush();
            out.close();
        }finally{
        	out.flush();
            out.close();
        }
        
	}
	/**
	 * 发送客户注册信息
	 * @param lockerid
	 * @param mobile
	 * @param customerid
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 * @throws EduException
	 */
	private void sendCustomerInfo(
			String lockerid, String mobile,String customerid,
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, EduException {
		/**
		 * update.jsp
		 *   mobile
		 *   elockerid
		 *   status
		 *   MobileElocker
		 *   MOBILE_USER_INFO
		 */
		
    	request.setAttribute("mobile", mobile);
    	request.setAttribute("elockerid", lockerid);
    	
    	InParamAPLockerPoBoxQry p111=new InParamAPLockerPoBoxQry ();
    	p111.setLockerID(lockerid);
    	OutParamAPLockerPoBoxQry MobileElocker=MobileProxy.doBusiness(p111);
		request.setAttribute("MobileElocker",MobileElocker);
		
		InParamAPCustomerInfoQry in=new InParamAPCustomerInfoQry();
    	in.setCustomerID(customerid);
    	OutParamAPCustomerInfoQry customerInfoOut = MobileProxy.doBusiness(in);
    	if(customerInfoOut != null){
    		request.setAttribute("mobile", customerInfoOut.CustomerMobile);
    		customerInfoOut.setMessage(customerInfoOut.getMessage().replaceAll("\n", "<br>"));
			request.setAttribute("MOBILE_USER_INFO", customerInfoOut);
			request.setAttribute("status", customerInfoOut.getStatus());
			request.setAttribute("bandlockerid", customerInfoOut.getLockerID());
			request.getRequestDispatcher("update.jsp").forward(request,response);
    	}else{
        	request.getRequestDispatcher("customer.jsp").forward(request,response);
    	}
	}

}
