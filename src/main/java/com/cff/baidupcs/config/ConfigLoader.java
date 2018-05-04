package com.cff.baidupcs.config;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.cff.baidupcs.common.Constant;
import com.cff.baidupcs.model.dto.BaiduDto;
import com.cff.baidupcs.util.SystemUtil;

public class ConfigLoader {
	
	public static String getUsage(){
		InputStream in = ConfigLoader.class.getClassLoader().getResourceAsStream(Constant.usageFile);
		if(in == null){
			in = ConfigLoader.class.getClassLoader().getResourceAsStream("resources/"+Constant.usageFile);
		}
		String usage = "";
		try {
			usage = readToString(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return usage;
	}
	
	public static String readToString(InputStream in) throws IOException {  
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
	    int n = 0;
	    while ((n = in.read(buffer)) != -1) {
	        out.write(buffer, 0, n);
	    }
	    return out.toString("UTF-8");
    }  
	
	public static BaiduDto loadPcsInfo() throws IOException{
		Properties properties = new Properties();
		InputStream in = new FileInputStream("PcsLogin.txt");
		properties.load(in);
		BaiduDto baiduDto = new BaiduDto();
		baiduDto.setBduss(properties.getProperty("bduss"));
		baiduDto.setName(properties.getProperty("name"));
		baiduDto.setPtoken(properties.getProperty("ptoken"));
		baiduDto.setStoken(properties.getProperty("stoken"));
		baiduDto.setWorkdir(properties.getProperty("workdir"));
		baiduDto.setUID(properties.getProperty("uid"));
		baiduDto.setNameShow(properties.getProperty("nameshow"));
		in.close();
		return baiduDto;
	}
	
	public static Map<String, String> loadUser() throws IOException{
		Properties properties = new Properties();
		InputStream in = new FileInputStream("BDLogin.txt");
		properties.load(in);
		String userName = properties.getProperty("userName");
		String passwd = properties.getProperty("passwd");
		in.close();
		Map<String,String> map = new HashMap<String,String>();
		map.put("userName", userName);
		map.put("passwd", passwd);
		return map;
	}
}
