package com.cff.baidupcs;

import java.io.IOException;

import com.cff.baidupcs.system.OpsAnalysisSystem;
import com.cff.baidupcs.util.StringUtil;
import com.cff.baidupcs.util.SystemUtil;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		System.out.println("欢迎使用百度网盘！");
		
		try {
			String command = SystemUtil.getIn();
			while (!"quit".equals(command) || !"esc".equals(command) || !"exit".equals(command)) {
				if(!StringUtil.isEmpty(command)){
					SystemUtil.logLeft(command);
					OpsAnalysisSystem.analysisOps(command);
				}

				command = SystemUtil.getIn();
			}
		} catch (Exception e) {
			System.out.println("\n正在关闭...");
			return;
		}

	}
}
