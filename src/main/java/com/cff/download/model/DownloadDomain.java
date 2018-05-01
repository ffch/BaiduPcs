package com.cff.download.model;

import java.util.List;
public class DownloadDomain {
	long totalSize;
	long totalDownloaded;
	int totalThread;
	List<DownLoadChunk> blockList = null;

	public DownloadDomain(long totalSize, long downloaded, List<DownLoadChunk> blockList) {
		this.totalSize = totalSize;
		this.totalDownloaded = downloaded;
		this.blockList = blockList;
	}
	
	public DownloadDomain(){
		
	}

	public long getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(long totalSize) {
		this.totalSize = totalSize;
	}

	public long getTotalDownloaded() {
		return totalDownloaded;
	}

	public int getTotalThread() {
		return totalThread;
	}

	public void setTotalThread(int totalThread) {
		this.totalThread = totalThread;
	}

	public void setTotalDownloaded(long totalDownloaded) {
		this.totalDownloaded = totalDownloaded;
	}

	public List<DownLoadChunk> getBlockList() {
		return blockList;
	}

	public void setBlockList(List<DownLoadChunk> blockList) {
		this.blockList = blockList;
	}
}
