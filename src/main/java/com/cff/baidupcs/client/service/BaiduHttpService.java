package com.cff.baidupcs.client.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.alibaba.fastjson.JSONObject;
import com.cff.baidupcs.client.BaiduClient;
import com.cff.baidupcs.client.BaiduLoginRes;
import com.cff.baidupcs.common.Constant;
import com.cff.baidupcs.model.dict.PhoneModelDict;
import com.cff.baidupcs.model.dto.AuthDto;
import com.cff.baidupcs.model.dto.BaiduDto;
import com.cff.baidupcs.model.dto.TiebaDto;
import com.cff.baidupcs.model.store.BaiduClientStore;
import com.cff.baidupcs.tess4j.TesseractIdentify;
import com.cff.baidupcs.util.Md5Util;
import com.cff.baidupcs.util.OkHttpUtil;
import com.cff.baidupcs.util.RsaUtil;
import com.cff.baidupcs.util.StringUtil;
import com.cff.baidupcs.util.SystemUtil;

import okhttp3.Cookie;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class BaiduHttpService {
	static Charset UTF_8 = Charset.forName("UTF-8");
	BaiduClient baiduClient = null;

	public BaiduHttpService() {
		baiduClient = new BaiduClient();
	}

	public BaiduDto login() {
		BaiduDto baiduDto = baiduLogin();
		BaiduHttpService.setBduus(baiduDto);
		WriteStringToFile(Constant.localPath + "PcsLogin.txt", baiduDto);

		return baiduDto;
	}

	public void WriteStringToFile(String filePath, BaiduDto baiduDto) {
		try {
			File file = new File(filePath);
			PrintStream ps = new PrintStream(new FileOutputStream(file));
			ps.println("bduss=" + baiduDto.getBduss());// 往文件里写入字符串
			ps.println("ptoken=" + baiduDto.getPtoken());
			ps.println("stoken=" + baiduDto.getStoken());
			ps.println("uid=" + baiduDto.getUID());
			ps.println("name=" + baiduDto.getName());
			ps.println("nameshow=" + baiduDto.getNameShow());
			ps.println("workdir=" + baiduDto.getWorkdir());
			ps.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public BaiduDto baiduLogin() {

		try {
			String enPasswd = RsaUtil.encrypt(
					StringUtil.stringReverse(Constant.passwd.trim() + baiduClient.getServerTime()),
					baiduClient.getRsaPublicKeyModulus());
			String timestampStr = System.currentTimeMillis() / 1000 + "773_357";
			Map<String, String> body = new HashMap<String, String>();
			body.put("username", Constant.userName);
			body.put("password", enPasswd);
			body.put("verifycode", baiduClient.getVerifycode());
			body.put("vcodestr", baiduClient.getVcodestr());
			body.put("isphone", "0");
			body.put("loginmerge", "1");
			body.put("action", "login");
			body.put("uid", timestampStr);
			body.put("skin", "default_v2");
			body.put("connect", "0");
			body.put("dv",
					"tk0.408376350146535171516806245342@oov0QqrkqfOuwaCIxUELn3oYlSOI8f51tbnGy-nk3crkqfOuwaCIxUou2iobENoYBf51tb4Gy-nk3cuv0ounk5vrkBynGyvn1QzruvN6z3drLJi6LsdFIe3rkt~4Lyz5ktfn1Qlrk5v5D5fOuwaCIxUobJWOI3~rkt~4Lyi5kBfni0vrk8~n15fOuwaCIxUobJWOI3~rkt~4Lyz5DQfn1oxrk0v5k5eruvN6z3drLneFYeVEmy-nk3c-qq6Cqw3h7CChwvi5-y-rkFizvmEufyr1By4k5bn15e5k0~n18inD0b5D8vn1Tyn1t~nD5~5T__ivmCpA~op5gr-wbFLhyFLnirYsSCIAerYnNOGcfEIlQ6I6VOYJQIvh515f51tf5DBv5-yln15f5DFy5myl5kqf5DFy5myvnktxrkT-5T__Hv0nq5myv5myv4my-nWy-4my~n-yz5myz4Gyx4myv5k0f5Dqirk0ynWyv5iTf5DB~rk0z5Gyv4kTf5DQxrkty5Gy-5iQf51B-rkt~4B__");
			body.put("getpassUrl", "/passport/getpass?clientfrom=&adapter=0&ssid=&from=&authsite=&bd_page_type=&uid="
					+ timestampStr + "&pu=&tpl=wimn&u=https://m.baidu.com/usrprofile%3Fuid%3D" + timestampStr
					+ "%23logined&type=&bdcm=060d5ffd462309f7e5529822720e0cf3d7cad665&tn=&regist_mode=&login_share_strategy=&subpro=wimn&skin=default_v2&client=&connect=0&smsLoginLink=1&loginLink=&bindToSmsLogin=&overseas=&is_voice_sms=&subpro=wimn&hideSLogin=&forcesetpwd=&regdomestic=");
			body.put("mobilenum", "undefined");
			body.put("servertime", baiduClient.getServerTime());
			body.put("gid", "DA7C3AE-AF1F-48C0-AF9C-F1882CA37CD5");
			body.put("logLoginType", "wap_loginTouch");
			body.put("FP_UID", "0b58c206c9faa8349576163341ef1321");
			body.put("traceid", baiduClient.getTraceid());

			RequestBody formBody = null;

			FormBody.Builder formEncodingBuilder = new FormBody.Builder(UTF_8);
			for (String key : body.keySet()) {
				formEncodingBuilder.add(key, body.get(key));
			}
			formBody = formEncodingBuilder.build();

			Headers headers = new Headers.Builder().add("Content-Type", "application/x-www-form-urlencoded")
					.add("Accept", "application/json").add("Referer", "https://wappass.baidu.com/")
					.add("X-Requested-With", "XMLHttpRequest").add("Connection", "keep-alive").build();

			String okHttpRes = OkHttpUtil.getInstance().doPostWithBodyAndHeader(Constant.BAIDU_LOGIN_URL, formBody,
					headers);
			JSONObject json = JSONObject.parseObject(okHttpRes);
			JSONObject errorInfo = json.getJSONObject("errInfo");
			String errNo = (String) errorInfo.get("no");
			JSONObject data = json.getJSONObject("data");

			switch (errNo) {
			case "500001":
			case "500002":
				System.out.println("需要输入验证码^_^");
				String imgUrl = "https://wappass.baidu.com/cgi-bin/genimage?" + data.getString("codeString");
				System.out.println("验证码链接：" + imgUrl);
				String testCode = downloadImgAndAnalysis(imgUrl);
				System.out.println("使用Tesseract猜测验证码为：" + testCode);
				baiduClient.setVcodestr(data.getString("codeString"));
				String verifycode = SystemUtil.getJlineIn("验证码：");
				baiduClient.setVerifycode(verifycode.trim());
				return baiduLogin();
			case "0":
				BaiduLoginRes baiduLoginRes = (BaiduLoginRes) JSONObject.toJavaObject(data, BaiduLoginRes.class);
				List<Cookie> cookies = OkHttpUtil.getCookie(HttpUrl.parse(Constant.BAIDU_LOGIN_URL));
				BaiduDto baiduDto = new BaiduDto();
				for (int i = 0; i < cookies.size(); i++) {
					Cookie cookie = cookies.get(i);
					if (cookie.name().equals("BDUSS")) {
						baiduDto.setBduss(cookie.value());
					}
					if (cookie.name().equals("PTOKEN")) {
						baiduDto.setPtoken(cookie.value());
					}
					if (cookie.name().equals("STOKEN")) {
						baiduDto.setStoken(cookie.value());
					}
				}
				return baiduDto;
			default:
				System.out.println("错误码：" + errNo + "___错误信息：" + errorInfo.get("msg"));
				break;
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String downloadImgAndAnalysis(String url) throws Exception {
		String fileName = "imgcode.png";
		File imgFile = new File(fileName);
		FileOutputStream fo = new FileOutputStream(imgFile);
		byte[] b = new byte[1024];
		int nRead;
		InputStream input = OkHttpUtil.getSimpleInstance().doGetWithStream(url, null);
		while ((nRead = input.read(b, 0, 1024)) > 0) {
			fo.write(b, 0, nRead);
		}
		fo.close();
		String result = TesseractIdentify.scan(imgFile);
		System.out.println("验证码本地路径为：" + imgFile.getAbsolutePath());
		return result;
	}

	public static void main(String args[]) throws Exception {
		String imgUrl = "https://wappass.baidu.com/cgi-bin/genimage?"
				+ "jxG7f07e2152b59c151020d15619801097b6120440615023110";

		System.out.println(downloadImgAndAnalysis(imgUrl));
	}

	public static void setBduus(BaiduDto baidu) {
		try {
			BaiduDto baiduDto = getTieBaUserInfo(baidu);
			baidu.setUID(baiduDto.getUID());
			baidu.setAge(baiduDto.getAge());
			baidu.setName(baiduDto.getName());
			baidu.setNameShow(baiduDto.getNameShow());
			baidu.setSex(baiduDto.getSex());
			BaiduClientStore.bdClients.put(baiduDto.getUID(), baiduDto);
			BaiduClientStore.currentActiveUid = baiduDto.getUID();
			BaiduClientStore.currentActiveBaiduDto = baiduDto;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

	}

	public static BaiduDto getTieBaUserInfo(BaiduDto baidu) throws NoSuchAlgorithmException, IOException {
		TiebaDto tiebaDto = getUserInfoByBDUSS(baidu);
		flushUserInfo(tiebaDto);
		return tiebaDto.getBaidu();
	}

	public static void flushUserInfo(TiebaDto tiebaDto) throws NoSuchAlgorithmException, IOException {
		String uid = tiebaDto.getBaidu().getUID();
		String name = tiebaDto.getBaidu().getName();
		if (!StringUtil.isEmpty(uid)) {
			getUserInfoByUID(tiebaDto);
		} else if (!StringUtil.isEmpty(name)) {
			getUserInfoByName(tiebaDto);
		} else {
			System.err.println("Baidu uid and name are null");
		}
	}

	private static void getUserInfoByName(TiebaDto tiebaDto) throws NoSuchAlgorithmException, IOException {
		String name = tiebaDto.getBaidu().getName();
		String urlStr = "http://tieba.baidu.com/home/get/panel?un=" + name;
		String okHttpRes = OkHttpUtil.getInstance().doGetWithJsonResult(urlStr);
		JSONObject json = JSONObject.parseObject(okHttpRes);
		JSONObject data = json.getJSONObject("data");
		if (data.get("id") != null && !"".equals(data.getString("id"))) {
			tiebaDto.getBaidu().setUID(data.getString("id"));
			getUserInfoByUID(tiebaDto);
		}
	}

	private static void getUserInfoByUID(TiebaDto tiebaDto) throws NoSuchAlgorithmException, IOException {
		String uid = tiebaDto.getBaidu().getUID();
		String rawQuery = "has_plist=0&need_post_count=1&rn=1&uid=" + uid;
		String signRaw = rawQuery.replace("&", "");
		signRaw += "tiebaclient!!!";
		rawQuery += "&sign=";
		rawQuery += Md5Util.encodeByMd5(signRaw.toString()).toUpperCase();

		String urlStr = "http://c.tieba.baidu.com/c/u/user/profile?" + rawQuery;
		String okHttpRes = OkHttpUtil.getInstance().doGetWithJsonResult(urlStr);
		JSONObject json = JSONObject.parseObject(okHttpRes);
		JSONObject user = json.getJSONObject("user");
		tiebaDto.getBaidu().setName(user.getString("name"));
		tiebaDto.getBaidu().setNameShow(user.getString("name_show"));
		tiebaDto.getBaidu().setAge((int) (Float.parseFloat(user.getString("tb_age"))));
		tiebaDto.getBaidu().setSex(user.getString("sex"));
		tiebaDto.setLikeForumNum(Integer.parseInt(user.getString("like_forum_num")));
		tiebaDto.setPostNum(Integer.parseInt(user.getString("post_num")));
	}

	public static TiebaDto getUserInfoByBDUSS(BaiduDto baidu) throws NoSuchAlgorithmException, IOException {
		String timestampStr = System.currentTimeMillis() / 1000 + "922";
		Map<String, String> body = new HashMap<String, String>();
		body.put("bdusstoken", baidu.getBduss() + "|null");
		body.put("channel_id", "");
		body.put("channel_uid", "");
		body.put("stErrorNums", "0");
		body.put("subapp_type", "mini");
		body.put("timestamp", timestampStr);

		String model = getPhoneModel(baidu.getBduss());
		String imei = PhoneModelDict.genCode();

		body.put("_client_type", "2");
		body.put("_client_version", "7.0.0.0");
		body.put("_phone_imei", imei);
		body.put("from", "mini_ad_wandoujia");
		body.put("model", model);

		String rawData = baidu.getBduss() + "_" + body.get("_client_version") + "_" + imei + "_" + body.get("from");
		String cuid = Md5Util.encodeByMd5(rawData).toUpperCase() + "|" + StringUtil.stringReverse(imei);
		body.put("cuid", cuid);

		List<String> keys = new ArrayList<String>();
		for (String key : body.keySet()) {
			keys.add(key);
		}
		Collections.sort(keys);

		StringBuilder signRaw = new StringBuilder();
		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			signRaw.append(key + "=" + body.get(key));
		}
		signRaw.append("tiebaclient!!!");
		body.put("sign", Md5Util.encodeByMd5(signRaw.toString()).toUpperCase());

		String timestamp = System.currentTimeMillis() / 1000 + "416";
		Headers headers = new Headers.Builder().add("Content-Type", "application/x-www-form-urlencoded")
				.add("Cookie", "ka=open").add("net", "1").add("User-Agent", "bdtb for Android 6.9.2.1")
				.add("client_logid", timestamp).add("Connection", "keep-alive").build();

		RequestBody formBody = null;
		FormBody.Builder formEncodingBuilder = new FormBody.Builder(UTF_8);
		for (String key : body.keySet()) {
			formEncodingBuilder.add(key, body.get(key));
		}
		
		formBody = formEncodingBuilder.build();

		String result = OkHttpUtil.getInstance().doPostWithBodyAndHeader(Constant.BAIDU_TIEBA_LOGIN_URL, formBody,
				headers);
		JSONObject json = JSONObject.parseObject(result);
		JSONObject user = json.getJSONObject("user");
		JSONObject anti = json.getJSONObject("anti");

		BaiduDto baiduDto = new BaiduDto();
		baiduDto.setName(user.get("name") == null ? "" : user.getString("name"));
		baiduDto.setUID(user.get("id") == null ? "" : user.getString("id"));
		baiduDto.setBduss(user.getString("BDUSS"));
		TiebaDto tiebaDto = new TiebaDto();
		tiebaDto.setBaidu(baiduDto);
		tiebaDto.setTbs(anti.getString("tbs"));
		return tiebaDto;
	}

	public static String getPhoneModel(String bduss) {
		if (PhoneModelDict.phoneModelDataBase.length <= 0)
			return "S3";
		int len = PhoneModelDict.phoneModelDataBase.length;
		BigInteger hash = BigInteger.valueOf(2134);
		for (int i = 0; i < bduss.length(); i++) {
			hash = hash.add(hash.shiftLeft(4).add(BigInteger.valueOf((int) bduss.charAt(i))));
		}
		int loc = hash.mod(BigInteger.valueOf(len)).intValue();
		return PhoneModelDict.phoneModelDataBase[loc];
	}
}
