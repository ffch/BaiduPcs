package com.cff.download.model;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class DownloadDomain {
	String totalSize;
	String downloaded;
	JSONArray blockList = null;

	public DownloadDomain(String totalSize, String downloaded, JSONArray blockList) {
		this.totalSize = totalSize;
		this.downloaded = downloaded;
		this.blockList = blockList;
	}
	
	public String toJsonString(){
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("totalSize", totalSize);
		jsonObject.put("downloaded", downloaded);
		if(blockList != null)
			jsonObject.put("blockList", blockList.toString());
		return jsonObject.toString();
	}

	public String getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(String totalSize) {
		this.totalSize = totalSize;
	}

	public String getDownloaded() {
		return downloaded;
	}

	public void setDownloaded(String downloaded) {
		this.downloaded = downloaded;
	}

	public JSONArray getBlockList() {
		return blockList;
	}

	public void setBlockList(JSONArray blockList) {
		this.blockList = blockList;
	}
}
