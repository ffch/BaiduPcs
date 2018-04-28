package com.cff.baidupcs.system;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import com.cff.baidupcs.client.service.BaiduHttpService;
import com.cff.baidupcs.client.service.PcsClientService;
import com.cff.baidupcs.common.Constant;
import com.cff.baidupcs.model.dto.BaiduDto;
import com.cff.baidupcs.model.dto.OpsParamDto;
import com.cff.baidupcs.util.SystemUtil;

import okhttp3.Cookie;

public class BaiduLoginSystem implements OperateSystem {
	static Map<String, OpsParamDto> opsParams = new ConcurrentHashMap<String, OpsParamDto>();
	static {
		opsParams.put("-test", new OpsParamDto(1, true, false));
		opsParams.put("-loc", new OpsParamDto(2, true, false));
	}
	public List<Integer> allow = Arrays.asList(0, 1, 2, 4);

	@Override
	public void ops(String[] command) throws Exception {
		Map<String, String> opsParamsTmp = new HashMap<String, String>();
		int value = 0;
		for (int i = 1; i < command.length; i++) {
			OpsParamDto tmp = opsParams.get(command[i]);
			if (tmp == null)
				i++;
			if (tmp.getIsValue()) {
				if (i == command.length - 1) {
					SystemUtil.logError("参数错误！");
					return;
				}
				opsParamsTmp.put(command[i], command[i + 1]);
				i++;
			} else {
				opsParamsTmp.put(command[i], "");
			}
			value += tmp.getNo();
		}
		if (!checkAllow(value)) {
			SystemUtil.logError("参数不是这样用的！");
		}
		if (opsParamsTmp.size() < 1) {
			Constant.userName = SystemUtil.getIn(" # 请输入用户名： ");
			Constant.passwd = SystemUtil.getIn(" # 请输入密码： ");
			BaiduHttpService baiduHttpService = new BaiduHttpService();
			BaiduDto baiduDto = baiduHttpService.login();
			SystemUtil.logLeft("百度账号登陆成功！");
			PcsClientService pcsClientService = new PcsClientService();
			pcsClientService.init(baiduDto);
		} else {
			for (String key : opsParamsTmp.keySet()) {
				switch (key) {
				case "-test":
					loadUser();
					BaiduHttpService baiduHttpService = new BaiduHttpService();
					BaiduDto baiduDto = baiduHttpService.login();
					SystemUtil.logLeft("百度账号登陆成功！");
					PcsClientService pcsClientService = new PcsClientService();
					pcsClientService.init(baiduDto);
					break;
				case "-loc":
					BaiduDto baiduDtoFromFile = loadPcsInfo();
					SystemUtil.logLeft("百度账号登陆成功！");
					PcsClientService pcsClientServiceLoc = new PcsClientService();
					pcsClientServiceLoc.initial(baiduDtoFromFile);
					break;
				default:
					break;
				}
			}
		}

	}

	public Boolean checkAllow(int value) {
		return allow.contains(value);
	}

	public BaiduDto loadPcsInfo() throws IOException {
		Properties properties = new Properties();
		InputStream in = new FileInputStream("D:\\PcsLogin.txt");
		properties.load(in);
		BaiduDto baiduDto = new BaiduDto();
		baiduDto.setBduss(properties.getProperty("bduss"));
		baiduDto.setName(properties.getProperty("name"));
		baiduDto.setPtoken(properties.getProperty("ptoken"));
		baiduDto.setStoken(properties.getProperty("stoken"));
		baiduDto.setWorkdir(properties.getProperty("workdir"));
		baiduDto.setUID(properties.getProperty("uid"));
		baiduDto.setNameShow(properties.getProperty("nameshow"));
		return baiduDto;
	}

	public void loadUser() throws IOException {
		Properties properties = new Properties();
		InputStream in = new FileInputStream("D:\\BDLogin.txt");
		properties.load(in);
		Constant.userName = properties.getProperty("userName");
		Constant.passwd = properties.getProperty("passwd");
		SystemUtil.logDebug(Constant.userName);
		SystemUtil.logDebug(Constant.passwd);
	}
}
