package com.dcdzsoft.business.api;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;










import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;



public class Comm {
	/**
	 * CodeEnum
	 * */
	public static enum ErrorCode{
		//errorCode
		SUCCESS(0,"SUCCESS"),  //正常
		ERROR_BUSINESS(10,"ERROR_BUSINESS"),  //业务处理错误
		ERROR_SERVER_SYSTEM(100,"ERROR_SERVER_SYSTEM"),  //系统错误
		ERROR_UNKNOWD(1000,"ERROR_UNKNOWD"),  //未知错误
		ERROR_PARAM_EMPTY(1001,"ERROR_PARAM_EMPTY"),        //参数为空
		ERROR_PARAM_KEYMD5(1003,"ERROR_PARAM_KEYMD5"),       //参数keymd5无效
		ERROR_PARAM_DATAMD5(1004,"ERROR_PARAM_DATAMD5"),    //参数datamd5无效
		ERROR_DATA_EMPTY(1005,"ERROR_DATA_EMPTY"),  //提交数据为空
		ERROR_DATA_FORMAT(1006,"ERROR_DATA_FORMAT"), //提交数据格式不正确
		ERROR_TIMEOUT(10010,"ERROR_TIMEOUT"), //请求超时
		;
		// 
		private String name;
		private int index;
		// 
		private ErrorCode(int index, String name) {
			this.name = name;
			this.index = index;
		}
		public String getName(){
			return this.name;
		}
		public int getIndex(){
			return this.index;
		}
		public static ErrorCode get(int index){
			ErrorCode code = ERROR_UNKNOWD;
			ErrorCode[] codes = ErrorCode.values();
			for(int i=0; i< codes.length; i++){
				if(codes[i].getIndex() == index){
					code = codes[i];
					break;
				}
			}
			return code;
		}
		public String toString(){
			return this.name;
		}
	}
	
	/**
	 * ResultCode
	 * */
	public static enum ResultCode{
		//
		SUCCESS(0,"SUCCESS"),  //正常
		ERROR_BUSINESS(10,"ERROR_BUSINESS"),  //系统错误
		ERROR_SERVER_SYSTEM(100,"ERROR_SERVER_SYSTEM"),  //系统错误
		ERROR_UNKNOWD(1000,"ERROR_UNKNOWD"),  //未知错误
		ERROR_DATA_EMPTY(1005,"ERROR_DATA_EMPTY"),  //提交数据为空
		ERROR_DATA_FORMAT(1006,"ERROR_DATA_FORMAT"), //提交数据格式不正确
		ERROR_ITEMCODE_EMPTY(2011,"ERROR_ITEMCODE_EMPTY"),        //
		ERROR_ITEMCODE_EXISTS(2012,"ERROR_ITEMCODE_EXISTS"),        //
		ERROR_AZCID_EMPTY(2021,"ERROR_AZCID_EMPTY"),        //
		ERROR_AZCID_NOTEXISTS(2022,"ERROR_AZCID_NOTEXISTS"),       //
		ERROR_PPCID_EMPTY(2023,"ERROR_PPCID_EMPTY"),        //
		ERROR_PPCID_NOTEXISTS(2024,"ERROR_PPCID_NOTEXISTS"),       //
		ERROR_MOBILE_EMPTY(2031,"ERROR_MOBILE_EMPTY"), 
		ERROR_MOBILE_ILLEGAL(2032,"ERROR_MOBILE_ILLEGAL"),       //Illegal 用户手机号不合法（非手机号码）
		ERROR_MOBILE_INVALID(2033,"ERROR_MOBILE_INVALID"),    //Invalid 用户手机号无效（未激活的手机号）
		ERROR_MOBILE_BLACKLIST(2034,"ERROR_MOBILE_BLACKLIST"),  //提交数据为空
		;
		// 
		private String name;
		private int index;
		// 
		private ResultCode(int index, String name) {
			this.name = name;
			this.index = index;
		}
		public String getName(){
			return this.name;
		}
		public int getIndex(){
			return this.index;
		}
		public static ResultCode get(int index){
			ResultCode code = ERROR_UNKNOWD;
			ResultCode[] codes = ResultCode.values();
			for(int i=0; i< codes.length; i++){
				if(codes[i].getIndex() == index){
					code = codes[i];
					break;
				}
			}
			return code;
		}
		public static ResultCode get(String name){
			ResultCode code = ERROR_UNKNOWD;
			ResultCode[] codes = ResultCode.values();
			for(int i=0; i< codes.length; i++){
				if(codes[i].getName().equals(name)){
					code = codes[i];
					break;
				}
			}
			return code;
		}
		public String toString(){
			return this.name;
		}
	}
	public static String readResponseData(HttpResponse resp) {
		String json = null;
		StringBuilder sb = new StringBuilder();
		try {
			InputStream in = resp.getEntity().getContent();
			int len = -1;
			byte[] buffer = new byte[1024];
			while ((len = in.read(buffer)) != -1)
				sb.append(new String(buffer, 0, len));

			json = sb.toString();
		} catch (Exception e) { /* report an error */ }
		
		return json;
	}
	public static String readRequestData(HttpServletRequest request) {
 		String json = null;
 		StringBuilder sb = new StringBuilder();
 		try {
 			ServletInputStream in = request.getInputStream();
 			int len = -1;
 			byte[] buffer = new byte[1024];
 			while ((len = in.read(buffer)) != -1)
 				sb.append(new String(buffer, 0, len, "UTF-8"));

 			json = sb.toString();
 		} catch (Exception e) { /* report an error */ }
 		
 		
 		return json;
 	}
	public static String md5(String text) {
 		try {
 			java.security.MessageDigest md = java.security.MessageDigest
 					.getInstance("MD5");
 			byte[] array = md.digest(text.getBytes("UTF-8"));
 			StringBuffer sb = new StringBuffer();
 			for (int i = 0; i < array.length; ++i) {
 				sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100)
 						.substring(1, 3));
 			}
 			return sb.toString();
 		} catch (java.security.NoSuchAlgorithmException e) {
 		} catch (UnsupportedEncodingException e) {
 			// TODO Auto-generated catch block
 			//e.printStackTrace();
 		}
 		return null;
 	}
	
	/**
	 * 取异常消息
	 * @param errorCode
	 * @return
	 */
	public static String getErrorMessage(ErrorCode errorCode){
		JSONObject errorJson = new JSONObject();
		errorJson.put("ErrorCode", errorCode.getIndex());
    	errorJson.put("ErrorMsg", errorCode.getName());
    	return errorJson.toString();
	}
	/**
	 * 取异常消息
	 * @param errorCode
	 * @return
	 */
	public static String getErrorMessage(ErrorCode errorCode, String errorMsg){
		JSONObject errorJson = new JSONObject();
		errorJson.put("ErrorCode", errorCode.getIndex());
		if(StringUtils.isEmpty(errorMsg)){
			errorJson.put("ErrorMsg", errorCode.getName());
		}else{
		    String msg = com.dcdzsoft.config.ErrorMsgConfig.getLocalizedMessage(errorMsg);
			errorJson.put("ErrorMsg", errorCode.getName()+":"+msg);
			//System.out.println("getErrorMessage("+errorMsg+")="+msg);
		}
    	
    	return errorJson.toString();
	}
    public static String getErrorMessage(int errorCode, String errorMsg){
        JSONObject errorJson = new JSONObject();
        errorJson.put("ErrorCode", errorCode);
        if(StringUtils.isEmpty(errorMsg)){
            errorJson.put("ErrorMsg", ErrorCode.get(errorCode).getName());
        }else{
            String msg = com.dcdzsoft.config.ErrorMsgConfig.getLocalizedMessage(errorMsg);
            errorJson.put("ErrorMsg", ErrorCode.get(errorCode).getName()+":"+msg);
            //System.out.println("getErrorMessage("+errorMsg+")="+msg);
        }
    	
    	return errorJson.toString();
	}
	/**
	 * @param resultArray JSONArray
	 * @return
	 */
	public static String getSuccessMessage(net.sf.json.JSONArray resultArray){
		JSONObject errorJson = new JSONObject();
		errorJson.put("ErrorCode", ErrorCode.SUCCESS.getIndex());
		errorJson.put("Results", resultArray.toString());
    	return errorJson.toString();
	}
	/**
	 * @param resultArray JSONArray
	 * @param total Int
	 * @return
	 */
	public static String getSuccessMessage(net.sf.json.JSONArray resultArray,int total){
		JSONObject errorJson = new JSONObject();
		errorJson.put("ErrorCode", ErrorCode.SUCCESS.getIndex());
		errorJson.put("rawTotal", total);
		errorJson.put("Results", resultArray.toString());
    	return errorJson.toString();
	}
	/**
	 * @param resultJson JSONObject
	 * @return
	 */
	public static String getSuccessMessage(net.sf.json.JSONObject resultJson){
		JSONObject errorJson = new JSONObject();
		errorJson.put("ErrorCode", ErrorCode.SUCCESS.getIndex());
		errorJson.put("Result", resultJson.toString());
    	return errorJson.toString();
	}
	//
	public static JSONObject createSuccessMessage(net.sf.json.JSONObject resultJson){
		JSONObject errorJson = new JSONObject();
		errorJson.put("ErrorCode", ErrorCode.SUCCESS.getIndex());
		errorJson.put("Result", resultJson.toString());
    	return errorJson;
	}
}
