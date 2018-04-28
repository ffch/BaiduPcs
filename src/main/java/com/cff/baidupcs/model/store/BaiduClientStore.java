package com.cff.baidupcs.model.store;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.cff.baidupcs.model.dto.BaiduDto;

public class BaiduClientStore {
	public static Map<String, BaiduDto> bdClients = new ConcurrentHashMap<String, BaiduDto>();
	public static String currentActiveUid = "";
	public static BaiduDto currentActiveBaiduDto = null;
}
