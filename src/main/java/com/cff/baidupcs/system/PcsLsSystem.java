package com.cff.baidupcs.system;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.cff.baidupcs.client.service.BaiduHttpService;
import com.cff.baidupcs.client.service.LsHttpService;
import com.cff.baidupcs.client.service.PcsClientService;
import com.cff.baidupcs.model.dto.BaiduDto;
import com.cff.baidupcs.model.dto.OpsParamDto;
import com.cff.baidupcs.util.SystemUtil;

public class PcsLsSystem implements OperateSystem {
	public List<Integer> allow = Arrays.asList(1,2,4,3); 
	static Map<String,OpsParamDto> opsParams = new ConcurrentHashMap<String,OpsParamDto>();
	static {
		opsParams.put("-f", new OpsParamDto(1,"",true));
		opsParams.put("-d", new OpsParamDto(2,true,false));
	}
	@Override
	public void ops(String[] command) throws Exception {
		Map<String,String> opsParamsTmp = new HashMap<String,String>();
		int value = 0;
		for (int i = 1; i < command.length; i++) {
			OpsParamDto tmp = opsParams.get(command[i]);
			if(tmp == null)i++;
			if(tmp.getIsValue()){
				if(i == command.length-1 ){
					SystemUtil.logError("参数错误！");
					return;
				}
				opsParamsTmp.put(command[i], command[i+1]);
				i++;
			}else{
				opsParamsTmp.put(command[i], "");
			}
			value += tmp.getNo();
		}
		if(!checkAllow(value)){
			SystemUtil.logError("参数不是这样用的！");
		}
		
		for (String key : opsParamsTmp.keySet()) {
			switch(key){
				case "-f":
					LsHttpService lsHttpService = new LsHttpService();
					lsHttpService.runLs(opsParamsTmp.get(key));
					break;
				default:
					break;
			}
		}
	}

	public Boolean checkAllow(int value){
		return allow.contains(value);
	}
	
	
}
