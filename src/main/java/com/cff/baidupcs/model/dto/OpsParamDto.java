package com.cff.baidupcs.model.dto;

public class OpsParamDto {
	int no;
	Boolean flag = false;
	String value;
	Boolean isValue = false;

	public OpsParamDto(int no, String value, Boolean isValue) {
		this.no = no;
		this.value = value;
		this.isValue = isValue;
	}

	public OpsParamDto(int no, Boolean flag, Boolean isValue) {
		this.no = no;
		this.flag = flag;
		this.isValue = isValue;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public Boolean getFlag() {
		return flag;
	}

	public void setFlag(Boolean flag) {
		this.flag = flag;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Boolean getIsValue() {
		return isValue;
	}

	public void setIsValue(Boolean isValue) {
		this.isValue = isValue;
	}

}
