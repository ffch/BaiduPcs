package com.cff.baidupcs.system;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;
import com.cff.baidupcs.model.dict.CommandDict;
import com.cff.baidupcs.util.StringUtil;
import com.cff.baidupcs.util.SystemUtil;

public class OpsAnalysisSystem {

	public static void analysisOps(String ops) {
		if(StringUtil.isEmpty(ops))return;
		String[] command = opsCommand(ops);
		if(command == null || command.length <1 || StringUtil.isEmpty(command[0]))return;
		OperateSystem operateSystem = CommandDict.getOperateSystem(command[0]);
		if (operateSystem == null) {
			SystemUtil.logError("命令不存在！");
			return;
		}

		try {
			operateSystem.ops(cheakIsRepeat(command));
		} catch (Exception e) {
			e.printStackTrace();
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

	/*
	 * 判断数组中是否有重复的值
	 */
	public static String[] cheakIsRepeat(String[] array) {
		HashSet<String> hashSet = new HashSet<String>();
		for (int i = 0; i < array.length; i++) {
			hashSet.add(array[i]);
		}
		if (hashSet.size() == array.length) {
			return array;
		} else {
			return hashSet.toArray(new String[] {});
		}
	}

	@Test
	public void test() throws IOException {
		String commond = "login -test   -c";
		analysisOps(commond);
	}
}
