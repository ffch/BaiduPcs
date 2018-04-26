package com.cff.baidupcs.model.dict;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.cff.baidupcs.system.BaiduLoginSystem;
import com.cff.baidupcs.system.OperateSystem;
import com.cff.baidupcs.system.PcsLsSystem;

public class CommandDict {
	static Map<String,OperateSystem> ops = new ConcurrentHashMap<String,OperateSystem>();	
	static {
		ops.put("login", new BaiduLoginSystem());
		ops.put("ls", new PcsLsSystem());
	}
	
	public static OperateSystem getOperateSystem(String key){
		return ops.get(key);
	}
}
