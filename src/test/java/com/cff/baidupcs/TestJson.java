package com.cff.baidupcs;

import net.sf.json.JSONObject;

public class TestJson {
	public static void main(String args[]){
		String json = "{\"user\":{\"id\":\"1504040772\",\"name\":\"\u98de\u626c\u5929\u626c\",\"BDUSS\":\"h1Z2M5ZG5lTjVNeEIzUlpRS0F2emlYVkh3WjZ1aE5mRWtac0JaU09jNXVsd2RiQVFBQUFBJCQAAAAAAAAAAAEAAABE16VZt8nR78zs0e8AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAG4K4FpuCuBadU\",\"portrait\":\"44d7e9a39ee689ace5a4a9e689aca559\"},\"anti\":{\"tbs\":\"b898cf8459e698591524632174\"},\"server_time\":\"6911\",\"time\":1524632174,\"ctime\":0,\"logid\":3374382916,\"error_code\":\"0\"}";
		JSONObject jsonStr = JSONObject.fromObject(json);
		JSONObject user = (JSONObject) jsonStr.get("user");
		System.out.println(user.get("name"));
	}
}
