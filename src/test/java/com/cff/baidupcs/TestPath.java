package com.cff.baidupcs;

import com.cff.baidupcs.util.StringUtil;
import com.cff.baidupcs.util.SystemUtil;

public class TestPath {
	public static void main(String[] args) throws Exception {
//		String path = SystemUtil.getIn() + "/"+"file";
//		
//		System.out.println(StringUtil.cleanPath(path));
		
		String path2 = SystemUtil.getIn();
		System.out.println(path2);
		System.out.println(StringUtil.cleanPath(path2));
		path2 = StringUtil.cleanPath(path2);
		String fileName = path2.substring(path2.lastIndexOf("/")+1);
		System.out.println(fileName);
		path2 = path2.substring(0, path2.lastIndexOf("/"));
		System.out.println(path2);
	}
}
