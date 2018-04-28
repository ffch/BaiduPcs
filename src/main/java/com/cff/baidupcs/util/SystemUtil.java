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

	public static void logLeft(String msg) {
		System.out.println(" ## " + msg);
	}

	public static void logDebug(String msg) {
		System.out.println("---------" + msg);
	}

	public static void logError(String msg) {
		System.err.println("[ " + DateUtil.format(new Date(), DateUtil.FullDatePattern) + " ] ## " + msg);
	}
}
