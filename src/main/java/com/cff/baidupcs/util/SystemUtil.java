package com.cff.baidupcs.util;

import java.util.Date;
import java.util.Scanner;

public class SystemUtil {
	static Scanner scan = new Scanner(System.in);

	public static String getIn() {
		System.out.print("BdPcs->  ");
		String inStr = scan.nextLine();
		return inStr;
	}

	public static String getIn(String msg) {
		System.out.print(msg);
		String inStr = scan.nextLine();
		return inStr;
	}

	public static void logInfo(String msg) {
		System.out.println("[ " + DateUtil.format(new Date(), DateUtil.FullDatePattern) + " ] ## " + msg);
	}
	
	public static void logInfo(Object msg) {
		System.out.println("[ " + DateUtil.format(new Date(), DateUtil.FullDatePattern) + " ] ## " + msg.toString());
	}


	public static void logLeft(String msg) {
		System.out.println(" ## " + msg);
	}
	
	public static void logClear(String msg) {
		System.out.printf("\r%6s",msg);
	}
	
	public static void logClear(String formatMsg,String format) {
		System.out.printf(format,formatMsg);
	}

	public static void logDebug(String msg) {
		System.out.println("---------" + msg);
	}
	
	public static void logDebug(Object msg) {
		System.out.println("---------" + msg);
	}

	public static void logError(String msg) {
		System.err.println("[ " + DateUtil.format(new Date(), DateUtil.FullDatePattern) + " ] ## " + msg);
	}
	
	public static void sleep(int nSecond) {
		try {
			Thread.sleep(nSecond);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
