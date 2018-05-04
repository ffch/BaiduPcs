package com.cff.baidupcs.client.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cff.baidupcs.common.Constant;
import com.cff.baidupcs.model.dto.BaiduDto;
import com.cff.baidupcs.model.dto.PcsFileDto;
import com.cff.baidupcs.model.store.BaiduClientStore;
import com.cff.baidupcs.util.OkHttpUtil;
import com.cff.baidupcs.util.StringUtil;
import com.cff.baidupcs.util.SystemUtil;
import com.cff.download.SiteFileFetch;
import com.cff.download.SiteFileFetchInter;
import com.cff.download.SiteFileFetchThread;
import com.cff.download.SiteInfoBean;

import okhttp3.FormBody;
import okhttp3.RequestBody;

public class PcsClientService {
	public static final Boolean isHTTPS = false;
	public static final int defaultAppID = 260149;
	public static String cookieStr = "";
	static Charset UTF_8 = Charset.forName("UTF-8");

	public String prepareFilesDirectoriesList(String path) throws IOException {
		if ("".equals(path)) {
			path = "/";
		}

		String subPath = "file";
		String method = "list";
		String url = "://pcs.baidu.com/rest/2.0/pcs/" + subPath;
		if (isHTTPS) {
			url = "https" + url;
		} else {
			url = "http" + url;
		}
		url = url + "?app_id=" + defaultAppID + "&method=" + method + "&path=" + path + "&by=" + "name" + "&order="
				+ "asc" + "&limit=" + "0-2147483647";
		String okHttpRes = OkHttpUtil.getInstance().doGetWithJsonResult(url);
		return okHttpRes;
	}
	
	public void downloadFile(String path) {
		if ("".equals(path)) {
			return;
		}
		String fileName = path.substring(path.lastIndexOf("/")+1);
		if(StringUtil.isEmpty(fileName))return;
		String subPath = "file";
		String method = "download";
		String url = "://pcs.baidu.com/rest/2.0/pcs/" + subPath;
		if (isHTTPS) {
			url = "https" + url;
		} else {
			url = "http" + url;
		}
		url = url + "?app_id=" + defaultAppID + "&method=" + method + "&path=" + path;
		try {
			SiteInfoBean bean = new SiteInfoBean(url,Constant.localDownloadPath, fileName, Constant.maxDownloadThread);
			SiteFileFetch fileFetch = new SiteFileFetch(bean);
			fileFetch.run();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public SiteFileFetchInter downloadFile(String path, String localPath) {
		if ("".equals(path)) {
			return null;
		}
		String fileName = localPath.substring(localPath.lastIndexOf("/")+1);
		localPath = localPath.substring(0, localPath.lastIndexOf("/"));
		if(StringUtil.isEmpty(fileName))return null;
		String subPath = "file";
		String method = "download";
		String url = "://pcs.baidu.com/rest/2.0/pcs/" + subPath;
		if (isHTTPS) {
			url = "https" + url;
		} else {
			url = "http" + url;
		}
		url = url + "?app_id=" + defaultAppID + "&method=" + method + "&path=" + path;
		try {
			SiteInfoBean bean = new SiteInfoBean(url,localPath, fileName, Constant.maxDownloadThread);
			SiteFileFetchThread fileFetch = new SiteFileFetchThread(bean,true);
			fileFetch.run();
			return fileFetch;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<PcsFileDto> filesDirectoriesMeta(String path) {
		if ("".equals(path)) {
			path = "/";
		}

		String subPath = "file";
		String method = "meta";
		String url = "://pcs.baidu.com/rest/2.0/pcs/" + subPath;
		if (isHTTPS) {
			url = "https" + url;
		} else {
			url = "http" + url;
		}
		url = url + "?app_id=" + defaultAppID + "&method=" + method;
		RequestBody formBody = null;

		Map<String, String> body = new HashMap<String, String>();
		body.put("path", path);
		FormBody.Builder formEncodingBuilder = new FormBody.Builder(UTF_8);
		for (String key : body.keySet()) {
			formEncodingBuilder.add(key, body.get(key));
		}
		formBody = formEncodingBuilder.build();
		String okHttpRes = "";
		try {
			okHttpRes = OkHttpUtil.getInstance().doPostWithBodyAndHeader(url, formBody);
		} catch (Exception e) {
			return null;
		}
		JSONObject json = JSONObject.parseObject(okHttpRes);
		if (json == null)
			return null;
		List<PcsFileDto> pcsFileDtos = new ArrayList<PcsFileDto>();
		JSONArray ja = json.getJSONArray("list");
		for (Object tmp : ja) {
			JSONObject jobj = (JSONObject) tmp;
			PcsFileDto pcsFileDto = (PcsFileDto) JSONObject.toJavaObject(jobj, PcsFileDto.class);
			pcsFileDtos.add(pcsFileDto);
		}
		return pcsFileDtos;
	}

	public void init(BaiduDto baiduDto) {
		Map<String, String> cookie = new HashMap<String, String>();
		cookie.put("BDUSS", baiduDto.getBduss());
		OkHttpUtil.addCookie("http://pcs.baidu.com", cookie);
		OkHttpUtil.addCookie("http://pan.baidu.com", cookie);
	}

	public void initial(BaiduDto baiduDto) {
		BaiduClientStore.bdClients.put(baiduDto.getUID(), baiduDto);
		BaiduClientStore.currentActiveUid = baiduDto.getUID();
		BaiduClientStore.currentActiveBaiduDto = baiduDto;
		init(baiduDto);
	}

}
