package com.cff.baidupcs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cff.baidupcs.model.dto.PcsFileDto;

public class TestJson {
	public static void main(String args[]){
		String filename = "C:\\Users\\fufei\\Desktop\\111.txt";
		String res = readToString(filename); 
//		System.out.println(res);
		
		JSONObject json = JSONObject.parseObject(res);
		if(json == null)return;
		JSONArray ja = json.getJSONArray("list");
		
		for(Object tmp : ja){
			JSONObject jobj = (JSONObject) tmp;
			PcsFileDto pcsFileDto = (com.cff.baidupcs.model.dto.PcsFileDto) JSONObject.toJavaObject(jobj, PcsFileDto.class);
			System.out.println(pcsFileDto);
		}
	}
	
	public static String readToString(String fileName) {  
        String encoding = "UTF-8";  
        File file = new File(fileName);  
        Long filelength = file.length();  
        byte[] filecontent = new byte[filelength.intValue()];  
        try {  
            FileInputStream in = new FileInputStream(file);  
            in.read(filecontent);  
            in.close();  
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        try {  
            return new String(filecontent, encoding);  
        } catch (UnsupportedEncodingException e) {  
            System.err.println("The OS does not support " + encoding);  
            e.printStackTrace();  
            return null;  
        }  
    }  
	
	public static void main1(String args[]){
		String json = "{\"user\":{\"id\":\"1504040772\",\"name\":\"\u98de\u626c\u5929\u626c\",\"BDUSS\":\"h1Z2M5ZG5lTjVNeEIzUlpRS0F2emlYVkh3WjZ1aE5mRWtac0JaU09jNXVsd2RiQVFBQUFBJCQAAAAAAAAAAAEAAABE16VZt8nR78zs0e8AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAG4K4FpuCuBadU\",\"portrait\":\"44d7e9a39ee689ace5a4a9e689aca559\"},\"anti\":{\"tbs\":\"b898cf8459e698591524632174\"},\"server_time\":\"6911\",\"time\":1524632174,\"ctime\":0,\"logid\":3374382916,\"error_code\":\"0\"}";
		JSONObject jsonStr = JSONObject.parseObject(json);
		JSONObject user = (JSONObject) jsonStr.get("user");
		System.out.println(user.get("name"));
	}
}
