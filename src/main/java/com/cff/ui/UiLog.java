package com.cff.ui;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

import com.cff.baidupcs.util.DateUtil;

public class UiLog {
	static UiLog uiLog = null;
	String className;
	String logfile = "ui.log";
	public synchronized static UiLog getLog(){
		if(uiLog != null)return uiLog;
		return new UiLog();
	}
	
	public static UiLog getLog(Class T){
		getLog().className = T.getName();
		return uiLog;
	}
	
	public void info(String msg){
		File tmpFile = new File(logfile);
		msg = DateUtil.format(new Date(), DateUtil.FullDatePattern)
				+ "---" + className + ": " + msg;
		try {
			FileWriter pw = new FileWriter(tmpFile, true);
			pw.write(msg);
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
