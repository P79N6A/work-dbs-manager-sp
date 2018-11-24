package com.dcdzsoft.print;

public class ItemBean {
	private int id = 0;
	private String boxSize = "";
	private String itemCode = "";
	private String status = "";
	private String remark = "";
	private String staff = "";
	
	public String getItemCode(){
		return itemCode;
	}
	
	public void setItemCode(String itemCode){
		this.itemCode = itemCode;
	}
	
	public String getRemark(){
		return remark;
	}
	
	public void setRemark(String remark){
		this.remark = remark;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String getBoxSize() {
		return boxSize;
	}

	public void setBoxSize(String boxSize) {
		this.boxSize = boxSize;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStaff() {
		return staff;
	}

	public void setStaff(String staff) {
		this.staff = staff;
	}
}
