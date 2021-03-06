package com.cff.baidupcs.client.service;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cff.baidupcs.common.Constant;
import com.cff.baidupcs.model.dto.BaiduDto;
import com.cff.baidupcs.model.dto.PcsFileDto;
import com.cff.baidupcs.model.store.BaiduClientStore;
import com.cff.baidupcs.util.OkHttpUtil;
import com.cff.baidupcs.util.StringUtil;
import com.cff.baidupcs.util.SystemUtil;

public class PcsCdService {
	PcsClientService pcsClientService;

	public PcsCdService(PcsClientService pcsClientService) {
		this.pcsClientService = pcsClientService;
	}

	public PcsCdService() {
		this.pcsClientService = new PcsClientService();
	}

	public void run(String path) {
		BaiduDto baiduDto = BaiduClientStore.currentActiveBaiduDto;
		String workDir = StringUtil.cleanPath(baiduDto.getWorkdir());
		path = StringUtil.cleanPath(path);
		if (!path.startsWith("/")) {
			path = StringUtil.cleanPath(workDir + "/" + path);
		}

		List<PcsFileDto> pcsFileDtos = pcsClientService.filesDirectoriesMeta(path);
		if (pcsFileDtos == null) {
			SystemUtil.logError("路径不存在！");
			return;
		}
		PcsFileDto pcsFileDto = pcsFileDtos.get(0);
		if (pcsFileDto.getIsDir() > 0) {
			workDir = path;
			baiduDto.setWorkdir(workDir);
		} else {
			SystemUtil.logError("路径不能是文件！");
		}
	}

	public List<PcsFileDto> getPcsPath(String path) throws IOException {
		BaiduDto baiduDto = BaiduClientStore.currentActiveBaiduDto;
		String workDir = StringUtil.cleanPath(baiduDto.getWorkdir());
		path = StringUtil.cleanPath(path);
		if (!path.startsWith("/")) {
			path = StringUtil.cleanPath(workDir + "/" + path);
		}
		List<PcsFileDto> pcsFileDtos = new ArrayList<PcsFileDto>();
		String res = pcsClientService.prepareFilesDirectoriesList(path);
		JSONObject json = JSONObject.parseObject(res);
		if (json == null)
			return null;
		JSONArray ja = json.getJSONArray("list");
		for (Object tmp : ja) {
			JSONObject jobj = (JSONObject) tmp;
			PcsFileDto pcsFileDto = (PcsFileDto) JSONObject.toJavaObject(jobj, PcsFileDto.class);
			pcsFileDtos.add(pcsFileDto);
		}
		return pcsFileDtos;
	}

}
