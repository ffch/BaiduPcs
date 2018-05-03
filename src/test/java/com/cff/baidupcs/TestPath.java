package com.cff.baidupcs;

import com.cff.baidupcs.util.StringUtil;
import com.cff.baidupcs.util.SystemUtil;

public class TestPath {
	public static void main(String[] args) throws Exception {
		String path = SystemUtil.getIn() + "/"+"file";
		System.out.println(StringUtil.cleanPath(path));
	}
}
