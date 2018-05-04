package com.cff.ui.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;

import com.cff.baidupcs.tess4j.TesseractIdentify;
import com.cff.baidupcs.util.OkHttpUtil;

public class ResourceUtil {
	public static URL getResource(String namePath){
		URL image = ResourceUtil.class.getClassLoader().getResource(namePath);
		if(image == null){
			image = ResourceUtil.class.getClassLoader().getResource("resources/" + namePath);
		}
		
		return image;
	}
	
	public static File downloadImg(String url) throws Exception {
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
		return imgFile;
	}
	
	public static String analysisImage(File imgFile) throws Exception {
		String result = TesseractIdentify.scan(imgFile);
		System.out.println("验证码本地路径为：" + imgFile.getAbsolutePath());
		return result;
	}
}
