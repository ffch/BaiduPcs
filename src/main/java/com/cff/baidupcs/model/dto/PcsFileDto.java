package com.cff.baidupcs.model.dto;

public class PcsFileDto {
	String fsId = "";
	String userId = "";
	String appId = "";
	String path = "";
	String serverFilename = "";
	String serverMtime = "";
	String serverCtime = "";
	String localMtime = "";
	String localCtime = "";
	int isDir = 0;
	int status = 0;
	int category = 0;
	int share = 0;
	String operId = "";
	String unlist = "";
	String mtime = "";
	String ctime = "";
	int size = 0;
	
	public String getFsId() {
		return fsId;
	}
	public void setFsId(String fsId) {
		this.fsId = fsId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getServerFilename() {
		return serverFilename;
	}
	public void setServerFilename(String serverFilename) {
		this.serverFilename = serverFilename;
	}
	public String getServerMtime() {
		return serverMtime;
	}
	public void setServerMtime(String serverMtime) {
		this.serverMtime = serverMtime;
	}
	public String getServerCtime() {
		return serverCtime;
	}
	public void setServerCtime(String serverCtime) {
		this.serverCtime = serverCtime;
	}
	public String getLocalMtime() {
		return localMtime;
	}
	public void setLocalMtime(String localMtime) {
		this.localMtime = localMtime;
	}
	public String getLocalCtime() {
		return localCtime;
	}
	public void setLocalCtime(String localCtime) {
		this.localCtime = localCtime;
	}
	
	public int getIsDir() {
		return isDir;
	}
	public void setIsDir(int isDir) {
		this.isDir = isDir;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getCategory() {
		return category;
	}
	public void setCategory(int category) {
		this.category = category;
	}
	public int getShare() {
		return share;
	}
	public void setShare(int share) {
		this.share = share;
	}
	public String getOperId() {
		return operId;
	}
	public void setOperId(String operId) {
		this.operId = operId;
	}
	public String getUnlist() {
		return unlist;
	}
	public void setUnlist(String unlist) {
		this.unlist = unlist;
	}
	public String getMtime() {
		return mtime;
	}
	public void setMtime(String mtime) {
		this.mtime = mtime;
	}
	public String getCtime() {
		return ctime;
	}
	public void setCtime(String ctime) {
		this.ctime = ctime;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	@Override
	public String toString() {
		return "PcsFileDto [fsId=" + fsId + ", userId=" + userId + ", appId=" + appId + ", path=" + path
				+ ", serverFilename=" + serverFilename + ", serverMtime=" + serverMtime + ", serverCtime=" + serverCtime
				+ ", localMtime=" + localMtime + ", localCtime=" + localCtime + ", isDir=" + isDir + ", status="
				+ status + ", category=" + category + ", share=" + share + ", operId=" + operId + ", unlist=" + unlist
				+ ", mtime=" + mtime + ", ctime=" + ctime + ", size=" + size + "]";
	}
}
