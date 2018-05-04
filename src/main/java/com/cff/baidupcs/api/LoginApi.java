package com.cff.baidupcs.api;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.cff.baidupcs.client.BaiduLoginRes;
import com.cff.baidupcs.client.service.BaiduHttpService;
import com.cff.baidupcs.client.service.PcsClientService;
import com.cff.baidupcs.common.Constant;
import com.cff.baidupcs.config.ConfigLoader;
import com.cff.baidupcs.model.dto.BaiduDto;
import com.cff.baidupcs.util.OkHttpUtil;
import com.cff.baidupcs.util.StringUtil;
import com.cff.baidupcs.util.SystemUtil;

import okhttp3.Cookie;
import okhttp3.HttpUrl;

public class LoginApi {
	public BaiduLoginRes login(String json) {
		JSONObject jsonobj = JSONObject.parseObject(json);
		System.out.println(jsonobj.toJSONString());
		String userName = jsonobj.getString("userName");
		String passwd = jsonobj.getString("passwd");
		boolean saveKey = jsonobj.getBooleanValue("saveKey");
		boolean autoLogin = jsonobj.getBooleanValue("autoLogin");
		String code = jsonobj.getString("code");
		String codeUrl = jsonobj.getString("codeUrl");
		if (autoLogin) {
			BaiduDto baiduDto = null;
			try {
				baiduDto = ConfigLoader.loadPcsInfo();
				PcsClientService rspcsClientServiceLoc = new PcsClientService();
				rspcsClientServiceLoc.initial(baiduDto);
				return new BaiduLoginRes(baiduDto);
			} catch (IOException e) {
				try {
					Map<String, String> params = ConfigLoader.loadUser();
					if (params.size() < 1) {
						if (StringUtil.isEmpty(userName) || StringUtil.isEmpty(passwd))
							return null;
						return doLogin(userName, passwd, saveKey, code, codeUrl);
					} else {
						String userNameMap = params.get("userName");
						String passwdMap = params.get("passwd");
						return doLogin(userNameMap, passwdMap, saveKey, code, codeUrl);
					}
				} catch (IOException e1) {
					if (StringUtil.isEmpty(userName) || StringUtil.isEmpty(passwd))
						return null;
					return doLogin(userName, passwd, saveKey, code, codeUrl);
				}
			}
		} else {
			if (StringUtil.isEmpty(userName) || StringUtil.isEmpty(passwd))
				return null;
			return doLogin(userName, passwd, saveKey, code, codeUrl);

		}

	}

	public static BaiduLoginRes doLogin(String userName, String passwd, boolean saveKey, String code, String codeUrl) {
		BaiduHttpService baiduHttpService = null;
		if (StringUtil.isEmpty(code)) {
			baiduHttpService = new BaiduHttpService();
		} else {
			baiduHttpService = new BaiduHttpService(codeUrl, code);
		}
		BaiduLoginRes baiduLoginRes = baiduHttpService.baiduLogin(userName, passwd);
		if (baiduLoginRes == null)
			return new BaiduLoginRes("1111", "未知错误");

		if ("0".equals(baiduLoginRes.getErrCode())) {
			List<Cookie> cookies = OkHttpUtil.getCookie(HttpUrl.parse(Constant.BAIDU_LOGIN_URL));
			BaiduDto baiduDtoBranch = new BaiduDto();
			for (int i = 0; i < cookies.size(); i++) {
				Cookie cookie = cookies.get(i);
				if (cookie.name().equals("BDUSS")) {
					baiduDtoBranch.setBduss(cookie.value());
				}
				if (cookie.name().equals("PTOKEN")) {
					baiduDtoBranch.setPtoken(cookie.value());
				}
				if (cookie.name().equals("STOKEN")) {
					baiduDtoBranch.setStoken(cookie.value());
				}
			}

			BaiduHttpService.setBduus(baiduDtoBranch);
			PcsClientService rkpcsClientService = new PcsClientService();
			rkpcsClientService.init(baiduDtoBranch);
			if (saveKey) {
				baiduHttpService.WriteStringToFile("PcsLogin.txt", baiduDtoBranch);
				baiduHttpService.writeUserInfoToFile("BDLogin.txt", userName, passwd);
			}
		}
		return baiduLoginRes;
	}
}
