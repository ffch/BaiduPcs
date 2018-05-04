package com.cff.ui;

import javax.swing.JDialog;

import com.cff.baidupcs.client.BaiduLoginRes;

public class CodeDialog extends JDialog{
	BaiduLoginRes baiduLoginRes;
	/**
	 * 
	 */
	private static final long serialVersionUID = 8983278031106575689L;
	
	public CodeDialog(BaiduLoginRes baiduLoginRes){
		this.baiduLoginRes = baiduLoginRes;
	}
}
