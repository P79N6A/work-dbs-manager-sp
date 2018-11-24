package com.dcdzsoft.dto.business;

/**
* 修改投递员权限
*/

public class InParamPMPostmanModRight implements java.io.Serializable
{
	public String FunctionID = "311066"; //功能编号

	public String OperID = ""; //管理员编号
	public String PostmanID = ""; //邮递员编号
	public String DropRight = ""; //投递权限
	public String ReturnRight = ""; //回收权限
	public String CollectRight = ""; //揽件权限
	public String DADRight = ""; //直投权限
	public String Remark = ""; //备注

	public String getFunctionID()
	{
		if(this.FunctionID == null || this.FunctionID.compareTo("") == 0)
			return "311066";
		else
			return FunctionID;
	}
	public void setFunctionID(String FunctionID)
	{
		if(FunctionID == null || FunctionID.compareTo("") == 0)
			this.FunctionID = "311066";
		else
			this.FunctionID = FunctionID;
	}

	public String getOperID()
	{
		return OperID;
	}
	public void setOperID(String OperID)
	{
		this.OperID = OperID;
	}

	public String getPostmanID()
	{
		return PostmanID;
	}
	public void setPostmanID(String PostmanID)
	{
		this.PostmanID = PostmanID;
	}

	public String getDropRight()
	{
		return DropRight;
	}
	public void setDropRight(String DropRight)
	{
		this.DropRight = DropRight;
	}

	public String getReturnRight()
	{
		return ReturnRight;
	}
	public void setReturnRight(String ReturnRight)
	{
		this.ReturnRight = ReturnRight;
	}

	public String getCollectRight()
	{
		return CollectRight;
	}
	public void setCollectRight(String CollectRight)
	{
		this.CollectRight = CollectRight;
	}

	public String getDADRight()
	{
		return DADRight;
	}
	public void setDADRight(String DADRight)
	{
		this.DADRight = DADRight;
	}

	public String getRemark()
	{
		return Remark;
	}
	public void setRemark(String Remark)
	{
		this.Remark = Remark;
	}

}