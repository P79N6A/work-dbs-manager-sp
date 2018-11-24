package com.dcdzsoft.dto.business;

/**
* API用户Key及数据完整行验证
* ErrorMsg  异常消息:请求消息有效时返回null；请求消息异常，则返回错误消息。
*/

public class OutParamAPIUserKeyAndDataVerity implements java.io.Serializable
{
	public String UserID = ""; //用户ID
	public String UserType = ""; //用户类型
	public int DataFalg; //0-非法；1-正常
	public String ErrorMsg = "";//异常消息:请求消息有效时返回null；请求消息异常，则返回错误消息。

	public String getUserID()
	{
		return UserID;
	}
	public void setUserID(String UserID)
	{
		this.UserID = UserID;
	}

	public String getUserType()
	{
		return UserType;
	}
	public void setUserType(String UserType)
	{
		this.UserType = UserType;
	}

	public int getDataFalg()
	{
		return DataFalg;
	}
	public void setDataFalg(int DataFalg)
	{
		this.DataFalg = DataFalg;
	}
	/**
	 * @return the errorMsg
	 */
	public String getErrorMsg() {
		return ErrorMsg;
	}
	/**
	 * @param errorMsg the errorMsg to set
	 */
	public void setErrorMsg(String errorMsg) {
		ErrorMsg = errorMsg;
	}

}