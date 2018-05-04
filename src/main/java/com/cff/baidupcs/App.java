package com.cff.baidupcs;

import java.io.IOException;

import com.cff.baidupcs.config.ConfigLoader;
import com.cff.baidupcs.system.OpsAnalysisSystem;
import com.cff.baidupcs.util.StringUtil;
import com.cff.baidupcs.util.SystemUtil;
import com.cff.ui.LoginView;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		boolean amRunningJavaW = (System.console() == null);
		if(amRunningJavaW){
			LoginView.run();
		}else{
			runWithConsole();
		}
	}
	
	public static void runWithConsole(){
		System.out.println("欢迎使用百度网盘！");
		System.out.println(ConfigLoader.getUsage());
		try {
			String command = SystemUtil.getJlineIn();
			while (!"quit".equalsIgnoreCase(command) && !"esc".equalsIgnoreCase(command) && !"exit".equalsIgnoreCase(command) && !"q".equalsIgnoreCase(command)) {
				if(!StringUtil.isEmpty(command)){
					SystemUtil.logLeft(command);
					OpsAnalysisSystem.analysisOps(command);
				}

				command = SystemUtil.getJlineIn();
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("\n正在关闭...");
			return;
		}

	}
}
