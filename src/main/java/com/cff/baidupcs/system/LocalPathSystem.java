package com.cff.baidupcs.system;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.cff.baidupcs.client.service.BaiduHttpService;
import com.cff.baidupcs.client.service.DownloadService;
import com.cff.baidupcs.client.service.LsHttpService;
import com.cff.baidupcs.client.service.PcsCdService;
import com.cff.baidupcs.client.service.PcsClientService;
import com.cff.baidupcs.common.Constant;
import com.cff.baidupcs.model.dto.BaiduDto;
import com.cff.baidupcs.model.dto.OpsParamDto;
import com.cff.baidupcs.model.store.BaiduClientStore;
import com.cff.baidupcs.util.StringUtil;
import com.cff.baidupcs.util.SystemUtil;

public class LocalPathSystem implements OperateSystem {
	public List<Integer> allow = Arrays.asList(1, 2, 3);
	static Map<String, OpsParamDto> opsParams = new ConcurrentHashMap<String, OpsParamDto>();
	static {
		opsParams.put("-k", new OpsParamDto(1, "", true));
		opsParams.put("-d", new OpsParamDto(2, "", true));
	}

	@Override
	public void ops(String[] command) throws Exception {
		Map<String, OpsParamDto> opsParamsTmp = new HashMap<String, OpsParamDto>();
		int value = 0;
		for (int i = 1; i < command.length; i++) {
			OpsParamDto tmp = opsParams.get(command[i]);
			if (tmp == null)
				continue;
			if (tmp.getIsValue()) {
				if (i == command.length - 1) {
					SystemUtil.logError("参数错误！");
					return;
				}
				OpsParamDto opsParamDto= new OpsParamDto(tmp.getNo(),command[i + 1],tmp.getIsValue());
				opsParamsTmp.put(command[i],opsParamDto);
				i++;
			} else {
				OpsParamDto opsParamDto= new OpsParamDto(tmp.getNo(),true,false);
				opsParamsTmp.put(command[i], opsParamDto);
			}
			value += tmp.getNo();
		}
		if (!checkAllow(value)) {
			SystemUtil.logError("参数不是这样用的！");
		}

		for (String key : opsParamsTmp.keySet()) {
			switch (key) {
			case "-k":
				OpsParamDto opsParamDto = opsParamsTmp.get(key);
				String pathValue = opsParamDto.getValue();
				pathValue = StringUtil.cleanPath(pathValue);
				if(!pathValue.endsWith("/"))pathValue = pathValue + "/";
				File tmp = new File(pathValue);
				if(!tmp.exists()){
					SystemUtil.logError("key路径不正确!");
					return;
				}
				Constant.localPath = pathValue;
				break;
			case "-d":
				OpsParamDto dopsParamDto = opsParamsTmp.get(key);
				String dpathValue = dopsParamDto.getValue();
				dpathValue = StringUtil.cleanPath(dpathValue);
				if(!dpathValue.endsWith("/"))dpathValue = dpathValue + "/";
				File dtmp = new File(dpathValue);
				if(!dtmp.exists()){
					SystemUtil.logError("download路径不正确!");
					return;
				}
				Constant.localDownloadPath = dpathValue;
				break;
			default:
				break;
			}
		}

	}

	public Boolean checkAllow(int value) {
		return allow.contains(value);
	}

}
