package com.cff.baidupcs.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.junit.Test;
import com.cff.baidupcs.common.Constant;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.ConnectionPool;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttpUtil {
	private final int DEFAULT_TIMEOUT = 5000;
	private static OkHttpClient client;
	private static OkHttpClient simpleClient;
	public static final MediaType FORM_CONTENT_TYPE_UTF8 = MediaType
			.parse("application/x-www-form-urlencoded; charset=utf-8");
	public static final MediaType FORM_CONTENT_TYPE_GBK = MediaType
			.parse("application/x-www-form-urlencoded; charset=gbk");
	public static final MediaType JSON_CONTENT_TYPE_UTF8 = MediaType.parse("application/json; charset=utf-8");
	private final HashMap<String, List<Cookie>> cookieStore = new HashMap<>();

	private static class OkHttpHolder {
		private static OkHttpUtil instance = new OkHttpUtil();
		private static OkHttpUtil simpleInstance = new OkHttpUtil(true);
	}

	public OkHttpUtil() {
		ConnectionPool connectionPool = new ConnectionPool(100, 30, TimeUnit.MINUTES);
		client = new OkHttpClient.Builder().connectTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS) // 设置连接超时
				.readTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS) // 设置读超时
				.writeTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS) // 设置写超时
				.retryOnConnectionFailure(true) // 是否自动重连
				.connectionPool(connectionPool).cookieJar(new CookieJar() {
					@Override
					public void saveFromResponse(HttpUrl httpUrl, List<Cookie> list) {
						cookieStore.put(httpUrl.host(), list);

					}

					@Override
					public List<Cookie> loadForRequest(HttpUrl httpUrl) {
						List<Cookie> cookies = cookieStore.get(httpUrl.host());
						return cookies != null ? cookies : new ArrayList<Cookie>();
					}
				}).build();
	}

	public OkHttpUtil(boolean b) {
		ConnectionPool connectionPool = new ConnectionPool(20, 30, TimeUnit.MINUTES);
		simpleClient = new OkHttpClient.Builder().retryOnConnectionFailure(true) // 是否自动重连
				.connectionPool(connectionPool).cookieJar(new CookieJar() {
					@Override
					public void saveFromResponse(HttpUrl httpUrl, List<Cookie> list) {
						cookieStore.put(httpUrl.host(), list);

					}

					@Override
					public List<Cookie> loadForRequest(HttpUrl httpUrl) {
						List<Cookie> cookies = cookieStore.get(httpUrl.host());
						return cookies != null ? cookies : new ArrayList<Cookie>();
					}
				}).build();
	}

	public static void addCookie(String hosts, Map<String, String> namevalue) {
		HttpUrl host = HttpUrl.parse(hosts);
		List<Cookie> list = new ArrayList<Cookie>();
		if (namevalue != null && !namevalue.isEmpty()) {
			for (String key : namevalue.keySet()) {
				Cookie Cookie = new Cookie.Builder().domain(host.host()).name(key).value(namevalue.get(key)).build();
				list.add(Cookie);
			}
		}
		getInstance().cookieStore.put(host.host(), list);
	}

	public static List<Cookie> getCookie(HttpUrl httpUrl) {
		return OkHttpHolder.instance.cookieStore.get(httpUrl.host());
	}

	public static OkHttpUtil getInstance() {
		//SystemUtil.logDebug("okhttp被调用了。");
		return OkHttpHolder.instance;
	}
	
	public static OkHttpUtil getSimpleInstance() {
		//SystemUtil.logDebug("Simple okhttp被调用了。");
		return OkHttpHolder.simpleInstance;
	}

	public OkHttpClient getClient() {
		return client;
	}

	/**
	 * 发送get请求
	 * 
	 * @param uri
	 * @return 返回结果
	 * @throws IOException
	 */
	public String doGetWithJsonResult(String uri) throws IOException {
		Request request = new Request.Builder().url(uri).build();

		Response response = client.newCall(request).execute();
		if (!response.isSuccessful())
			throw new IOException("Unexpected code " + response);

		String json = null;
		json = response.body().string();
		return json;
	}
	
	/**
	 * 发送get请求
	 * 
	 * @param uri
	 * @return 返回结果
	 * @throws IOException
	 */
	public InputStream doGetWithStream(String uri, Headers headers) throws IOException {
		Request request = new Request.Builder().url(uri).headers(headers).build();

		Response response = client.newCall(request).execute();
		if (!response.isSuccessful())
			throw new IOException("Unexpected code " + response);

		
		return response.body().byteStream();
	}

	/**
	 * 发送get请求
	 * 
	 * @param uri
	 * @return 返回结果
	 * @throws IOException
	 */
	public Response doGetWithResponse(String uri) throws IOException {
		Request request = new Request.Builder().url(uri).build();

		Response response = client.newCall(request).execute();
		if (!response.isSuccessful())
			throw new IOException("Unexpected code " + response);
		return response;
	}

	/**
	 * 默认UTF8发送post请求，application/x-www-form-urlencoded
	 * 
	 * @param uri
	 * @param paramMap
	 *            map数据
	 * @return 返回结果
	 * @throws IOException
	 */
	public String doPostWithJsonResult(String uri, Map<String, String> paramMap) throws IOException {
		RequestBody formBody = null;
		Charset UTF_8 = Charset.forName("UTF-8");
		FormBody.Builder formEncodingBuilder = new FormBody.Builder(UTF_8);
		if (paramMap != null && !paramMap.isEmpty()) {
			for (String key : paramMap.keySet()) {
				formEncodingBuilder.add(key, paramMap.get(key));
			}
		}
		formBody = formEncodingBuilder.build();

		Request request = new Request.Builder().url(uri).post(formBody).build();
		Response response = client.newCall(request).execute();
		if (!response.isSuccessful())
			throw new IOException("Unexpected code " + response);

		String json = null;
		json = response.body().string();

		return json;
	}

	/**
	 * GBK编码发送post请求，application/x-www-form-urlencoded
	 * 
	 * @param uri
	 * @param paramMap
	 *            map数据
	 * @return 返回结果
	 * @throws IOException
	 */
	public String doPostWithJsonResultGbk(String uri, Map<String, String> paramMap) throws IOException {
		RequestBody formBody = null;
		Charset gbk = Charset.forName("GBK");
		FormBody.Builder formEncodingBuilder = new FormBody.Builder(gbk);
		if (paramMap != null && !paramMap.isEmpty()) {
			for (String key : paramMap.keySet()) {
				formEncodingBuilder.add(key, paramMap.get(key));
			}
		}
		formBody = formEncodingBuilder.build();

		Request request = new Request.Builder().url(uri).post(formBody).build();
		Response response = client.newCall(request).execute();
		if (!response.isSuccessful())
			throw new IOException("Unexpected code " + response);

		String json = null;
		json = response.body().string();

		return json;
	}

	/**
	 * 默认UTF8发送同步post请求，application/json; charset=utf-8"
	 * 
	 * @param uri
	 * @param String
	 *            json字符串
	 * @return 返回结果
	 * @throws IOException
	 */
	public String doPostJsonSync(String uri, String jsonObj) throws IOException {
		RequestBody requestBody = RequestBody.create(JSON_CONTENT_TYPE_UTF8, jsonObj);
		Request request = new Request.Builder().url(uri).post(requestBody).build();
		Response response = client.newCall(request).execute();
		if (!response.isSuccessful())
			throw new IOException("Unexpected code " + response);

		String json = null;
		json = response.body().string();

		return json;
	}

	/**
	 * 默认UTF8发送异步post请求，application/json; charset=utf-8"
	 * 
	 * @param uri
	 * @param String
	 *            json字符串
	 * @return 返回结果
	 * @throws IOException
	 */
	public String doPostJsonAsyn(String uri, String jsonObj) throws IOException {
		RequestBody requestBody = RequestBody.create(JSON_CONTENT_TYPE_UTF8, jsonObj);
		Request request = new Request.Builder().url(uri).post(requestBody).build();
		Call call = client.newCall(request);
		call.enqueue(new Callback() {
			@Override
			public void onFailure(Call call, IOException e) {
				System.out.println("某次的发送失败了：" + e.getMessage());
			}

			@Override
			public void onResponse(Call call, Response response) throws IOException {
				if (response.isSuccessful()) {
					System.out.println("某次的发送成功了：" + response.body().string());
				}
			}
		});
		String json = "success";

		return json;
	}

	/**
	 * 默认UTF8发送同步post请求
	 * 
	 * @param uri
	 * @param formBody
	 *            报文体
	 * @param headers
	 *            报文头
	 * @return
	 * @throws IOException
	 */
	public String doPostWithBodyAndHeader(String uri, RequestBody formBody, Headers headers) throws IOException {
		Request request = new Request.Builder().headers(headers).url(uri).post(formBody).build();

		Response response = client.newCall(request).execute();
		if (!response.isSuccessful())
			throw new IOException("Unexpected code " + response);

		String json = null;
		json = response.body().string();

		return json;
	}

	public String doPostWithBodyAndHeader(String uri, RequestBody formBody) throws IOException {
		Request request = new Request.Builder().url(uri).post(formBody).build();

		Response response = client.newCall(request).execute();
		if (!response.isSuccessful())
			throw new IOException("Unexpected code " + response);

		String json = null;
		json = response.body().string();

		return json;
	}
	
	public Map<String, List<String>> doHeadWithHeader(String uri, Headers headers) throws IOException{
		Request request = new Request.Builder().url(uri).head().build();//.headers(headers)

		Response response = client.newCall(request).execute();
		if (!response.isSuccessful())
			throw new IOException("Unexpected code " + response);
		Map<String, List<String>> maps = response.headers().toMultimap();
		response.close();
		return maps;
	}

	@Test
	public void test() {
		System.out.println(HttpUrl.parse(Constant.BAIDU_LOGIN_URL).host());
	}
}
