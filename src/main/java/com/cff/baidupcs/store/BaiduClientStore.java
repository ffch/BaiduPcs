package com.cff.baidupcs.store;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.cff.baidupcs.model.BaiduDto;

public class BaiduClientStore {
	public static Map<String,BaiduDto> bdClients = new ConcurrentHashMap<String,BaiduDto>();
	public static String currentActiveUid = "";
	public static BaiduDto currentActiveBaiduDto = null;
}
