package com.cff.baidupcs;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.CharBuffer;

public class TestKeyCode {
	public static void main(String[] args) {
		try {
			System.out.print("Enter a char :");
			String inStr = getLine();
			System.out.println(inStr);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String getLine() throws IOException{
		InputStream reader = System.in;
		ByteArrayOutputStream bou = new ByteArrayOutputStream();
		int var = 0;
		while ((var = reader.read()) != -1) {
			if (var == 13 || var == 10) {
				break;
			} 
			if (var == 9){
				String tip = bou.toString();
				System.out.println("tab键触发了,提示 ： " + tip);
				bou.write("test".getBytes());
			}
			bou.write(var);
		}
		
		return bou.toString();
		
	}
}
