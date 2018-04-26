package com.cff.baidupcs.client;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.alibaba.fastjson.JSONObject;
import com.cff.baidupcs.common.Constant;
import com.cff.baidupcs.util.OkHttpUtil;
import com.cff.baidupcs.util.StringUtil;
import okhttp3.OkHttpClient;
import okhttp3.Response;

public class BaiduClient {
	String serverTime;           // 百度服务器时间, 形如 "e362bacbae"
	String rsaPublicKeyModulus;
	String fpUID;               
	String traceid;
	String serverTimeUrl = "";
	String vcodestr = "";
	String verifycode = "";
	
	private static OkHttpClient client;
	
	public BaiduClient(){
		client = OkHttpUtil.getInstance().getClient();
		try {
			getBaiduServerTime();
			getBaiduRSAPublicKeyModulus();
			getTraceID();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void getBaiduServerTime() throws IOException{
		String rs = OkHttpUtil.getInstance().doGetWithJsonResult(Constant.BAIDU_SERVERTIME_URL);
		System.out.println(rs);
		JSONObject json = JSONObject.parseObject(rs);
		serverTime = json.getString("time");
		if(StringUtil.isEmpty(serverTime))serverTime="e362bacbae";
	}
	
	public void getBaiduRSAPublicKeyModulus() throws IOException{
		String rs = OkHttpUtil.getInstance().doGetWithJsonResult(Constant.BAIDU_RSA_URL);
		System.out.println(rs);
		Pattern pattern = Pattern.compile(",rsa:\"(.*?)\",error:");
		Matcher matcher = pattern.matcher(rs);
		if(matcher.find()){
			System.out.println(matcher.group(1));
			rsaPublicKeyModulus = matcher.group(1);
		}else{
			rsaPublicKeyModulus = "B3C61EBBA4659C4CE3639287EE871F1F48F7930EA977991C7AFE3CC442FEA49643212E7D570C853F368065CC57A2014666DA8AE7D493FD47D171C0D894EEE3ED7F99F6798B7FFD7B5873227038AD23E3197631A8CB642213B9F27D4901AB0D92BFA27542AE890855396ED92775255C977F5C302F1E7ED4B1E369C12CB6B1822F";
		}
		
	}
	
	public void getTraceID() throws IOException{
		Response response = OkHttpUtil.getInstance().doGetWithResponse(Constant.BAIDU_TRACEID_URL);
		traceid = response.header("Trace-Id");
		response.close();
		System.out.println(traceid);
		
	}
	
	public String getServerTime() {
		return serverTime;
	}
	public void setServerTime(String serverTime) {
		this.serverTime = serverTime;
	}
	public String getRsaPublicKeyModulus() {
		return rsaPublicKeyModulus;
	}
	public void setRsaPublicKeyModulus(String rsaPublicKeyModulus) {
		this.rsaPublicKeyModulus = rsaPublicKeyModulus;
	}
	public String getFpUID() {
		return fpUID;
	}
	public void setFpUID(String fpUID) {
		this.fpUID = fpUID;
	}
	public String getTraceid() {
		return traceid;
	}
	public void setTraceid(String traceid) {
		this.traceid = traceid;
	}
	
	public OkHttpClient getClient(){
		return client;
	}

	public String getVcodestr() {
		return vcodestr;
	}

	public void setVcodestr(String vcodestr) {
		this.vcodestr = vcodestr;
	}

	public String getVerifycode() {
		return verifycode;
	}

	public void setVerifycode(String verifycode) {
		this.verifycode = verifycode;
	}
}
