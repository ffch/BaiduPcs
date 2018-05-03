package com.cff.baidupcs.util;

import java.io.IOException;
import java.util.Date;
import java.util.Scanner;

import com.cff.baidupcs.model.dict.CommandDict;

import jline.console.ConsoleReader;
import jline.console.completer.StringsCompleter;

public class SystemUtil {
	static Scanner scan = new Scanner(System.in);

	public static String getIn() {
		System.out.print("BdPcs->  ");
		String inStr = scan.nextLine();
		return inStr;
	}
	/**
	 * Jline输入，2.14.5版本可以用了
	 * @return
	 * @throws IOException
	 */
	public static String getJlineIn() throws IOException{
		ConsoleReader reader = new ConsoleReader();
		reader.addCompleter(new StringsCompleter(CommandDict.getKeys()));
		String line = reader.readLine("BdPcs->");
		return line;
	}
	
	public static String getJlineIn(String msg) throws IOException{
		ConsoleReader reader = new ConsoleReader();
		reader.addCompleter(new StringsCompleter(CommandDict.getKeys()));
		String line = reader.readLine(msg);
		return line;
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
