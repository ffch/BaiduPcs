package com.cff.baidupcs.client.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

import com.cff.baidupcs.model.dto.BaiduDto;
import com.cff.baidupcs.model.store.BaiduClientStore;
import com.cff.baidupcs.util.OkHttpUtil;

public class PcsClientService {
	public static final Boolean isHTTPS = false;
	public static final int defaultAppID = 260149;
	public static String cookieStr = "";

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
