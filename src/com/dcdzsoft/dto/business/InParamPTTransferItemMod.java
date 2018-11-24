package com.dcdzsoft.dto.business;

/**
* 修改订单数据发送状态
*/

public class InParamPTTransferItemMod implements java.io.Serializable
{
	public String FunctionID = "331052"; //功能编号

	public String OperID = ""; //管理员编号
	public long WaterID; //流水号
	public String SendStatus = ""; //发送状态
	public String Remark = ""; //备注
	public long TransferWaterID;//发送流水号

	public String getFunctionID()
	{
		if(this.FunctionID == null || this.FunctionID.compareTo("") == 0)
			return "331052";
		else
			return FunctionID;
	}
	public void setFunctionID(String FunctionID)
	{
		if(FunctionID == null || FunctionID.compareTo("") == 0)
			this.FunctionID = "331052";
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

	public long getWaterID()
	{
		return WaterID;
	}
	public void setWaterID(long WaterID)
	{
		this.WaterID = WaterID;
	}

	public String getSendStatus()
	{
		return SendStatus;
	}
	public void setSendStatus(String SendStatus)
	{
		this.SendStatus = SendStatus;
	}

	public String getRemark()
	{
		return Remark;
	}
	public void setRemark(String Remark)
	{
		this.Remark = Remark;
	}
    public long getTransferWaterID() {
        return TransferWaterID;
    }
    public void setTransferWaterID(long transferWaterID) {
        TransferWaterID = transferWaterID;
    }

}