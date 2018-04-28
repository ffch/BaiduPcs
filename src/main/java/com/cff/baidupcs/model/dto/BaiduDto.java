package com.cff.baidupcs.model.dto;

public class BaiduDto {
	String UID; // 百度ID对应的uid
	String name; // 真实ID
	String nameShow; // 显示的用户名(昵称)
	String sex; // 性别
	int age; // 帐号年龄
	String bduss; // 百度BDUSS
	String ptoken;
	String stoken;
	String workdir = "/";

	public String getUID() {
		return UID;
	}

	public void setUID(String uID) {
		UID = uID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNameShow() {
		return nameShow;
	}

	public void setNameShow(String nameShow) {
		this.nameShow = nameShow;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getBduss() {
		return bduss;
	}

	public void setBduss(String bduss) {
		this.bduss = bduss;
	}

	public String getPtoken() {
		return ptoken;
	}

	public void setPtoken(String ptoken) {
		this.ptoken = ptoken;
	}

	public String getStoken() {
		return stoken;
	}

	public void setStoken(String stoken) {
		this.stoken = stoken;
	}

	public String getWorkdir() {
		return workdir;
	}

	public void setWorkdir(String workdir) {
		this.workdir = workdir;
	}

	@Override
	public String toString() {
		return "BaiduDto [UID=" + UID + ", name=" + name + ", nameShow=" + nameShow + ", sex=" + sex + ", age=" + age
				+ ", bduss=" + bduss + ", ptoken=" + ptoken + ", stoken=" + stoken + ", workdir=" + workdir + "]";
	}
}
