package com.cff.baidupcs.system;

import java.io.IOException;
import java.util.HashSet;
import org.junit.Test;
import com.cff.baidupcs.model.dict.CommandDict;
import com.cff.baidupcs.util.SystemUtil;

public class OpsAnalysisSystem {
	
	public static void analysisOps(String ops){
		String[] command = ops.split("\\s+",10);
		
		OperateSystem operateSystem = CommandDict.getOperateSystem(command[0]);
		if(operateSystem == null){
			SystemUtil.logError("命令不存在！");
			return;
		}
		
		try {
			operateSystem.ops(cheakIsRepeat(command));
		} catch (Exception e) {
			e.printStackTrace();
		}
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
            return hashSet.toArray(new String[]{});
        }
    }
	
	@Test
	public void test() throws IOException{
		String commond = "login -test   -c";
		analysisOps(commond);
	}
}
