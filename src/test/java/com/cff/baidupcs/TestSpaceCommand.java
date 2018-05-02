package com.cff.baidupcs;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.cff.baidupcs.util.SystemUtil;

public class TestSpaceCommand {
	public static void main(String args[]){
		String command = SystemUtil.getIn();
		
//		Pattern pattern = Pattern.compile("'(.*?)'");
//		Matcher matcher = pattern.matcher(command);
//		String str = "";
//		while (matcher.find()) {
//			str = matcher.group(1);
//			SystemUtil.logDebug(str);
//		}
//		SystemUtil.logDebug(str);
		
		String[] split = opsCommand(command);
		for(String element : split){
		    System.out.println("element = " + element);
		}
	}
	
	public static String[] opsCommand(String ops){
		Pattern pattern = Pattern.compile("'(.*?)'");
		Matcher matcher = pattern.matcher(ops);
		StringBuffer sb = new StringBuffer();
		List<String> params = new ArrayList<String>();
		String replace = "\\$";
		while (matcher.find()) {
			String str = "";
			str = matcher.group(1);
			params.add(str);
			matcher.appendReplacement(sb, replace);
		}
		matcher.appendTail(sb);
		ops = sb.toString();
		SystemUtil.logDebug(ops);
		String[] command = ops.split("\\s+", 10);
		int index = 0;
		for(int i = 0; i< command.length; i++){
			String element = command[i];
			if(element.contains("$")){
				String repStr = params.get(index);
				command[i] = element.replace("$", repStr);
				index ++;
			}
		}
		return command;
	}
}
