package com.cff.baidupcs.Service;

import java.io.IOException;
import java.net.URL;

import com.cff.baidupcs.common.Constant;
import com.cff.baidupcs.model.BaiduDto;
import com.cff.baidupcs.store.BaiduClientStore;
import com.cff.baidupcs.util.OkHttpUtils;
import com.cff.baidupcs.util.StringUtil;

public class LsHttpService {
	PcsClientService pcsClientService;

	public LsHttpService(PcsClientService pcsClientService) {
		this.pcsClientService = pcsClientService;
	}

	public void runLs(String path) {
		try {
			getPcsPath(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void getPcsPath(String path) throws IOException {
		BaiduDto baiduDto = BaiduClientStore.currentActiveBaiduDto;
		String workDir = StringUtil.cleanPath(baiduDto.getWorkdir());
		path = StringUtil.cleanPath(path);
		if (!path.startsWith("/")) {
			path = StringUtil.cleanPath(workDir + "/" + path);
		}
		
		String res = pcsClientService.prepareFilesDirectoriesList(path);
		System.out.println("百度网盘发来贺电"+res);
	}

	
}
