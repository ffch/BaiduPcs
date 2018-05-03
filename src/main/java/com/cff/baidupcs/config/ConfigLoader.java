package com.cff.baidupcs.config;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.cff.baidupcs.common.Constant;

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
}
