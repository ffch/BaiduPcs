package com.cff.baidupcs.model.dto;

/**
 * 开关型参数和传值型参数
 * @author fufei
 *
 */
public class OpsParamDto {
	/**
	 * 参数编号
	 */
	int no;
	/**
	 * 开关型参数的开关值
	 */
	Boolean flag = false;
	/**
	 * 传值型参数值
	 */
	String value;
	/**
	 * 参数类型 true:传值型,false:开关型
	 */
	Boolean isValue = false;

	/**
	 * 传值型参数初始化
	 * @param no 参数编号
	 * @param value 参数值
	 * @param isValue 参数类型
	 */
	public OpsParamDto(int no, String value, Boolean isValue) {
		this.no = no;
		this.value = value;
		this.isValue = isValue;
	}

	/**
	 * 开关型参数的初始化
	 * @param no 参数编号
	 * @param flag 开关值
	 * @param isValue 参数类型
	 */
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
