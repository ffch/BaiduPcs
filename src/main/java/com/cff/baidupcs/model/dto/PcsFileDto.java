package com.cff.baidupcs.model.dto;

public class PcsFileDto {
	String fsId = "";
	String userId = "";
	String appId = "";
	String path = "";
	String parentPath = "";
	String serverFilename = "";
	String serverMtime = "";
	String serverCtime = "";
	String localMtime = "";
	String localCtime = "";
	int isDir = 0;
	int ifHasSubdir = 0;
	int status = 0;
	int category = 0;
	int isDelete = 0;

	int extentInt3 = 0;
	int extentTinyint1 = 0;
	int extentTinyint2 = 0;
	int extentTinyint3 = 0;
	int extentTinyint4 = 0;

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

	public String getParentPath() {
		return parentPath;
	}

	public void setParentPath(String parentPath) {
		this.parentPath = parentPath;
	}

	public int getIfHasSubdir() {
		return ifHasSubdir;
	}

	public void setIfHasSubdir(int ifHasSubdir) {
		this.ifHasSubdir = ifHasSubdir;
	}

	public int getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
	}

	public int getExtentInt3() {
		return extentInt3;
	}

	public void setExtentInt3(int extentInt3) {
		this.extentInt3 = extentInt3;
	}

	public int getExtentTinyint1() {
		return extentTinyint1;
	}

	public void setExtentTinyint1(int extentTinyint1) {
		this.extentTinyint1 = extentTinyint1;
	}

	public int getExtentTinyint2() {
		return extentTinyint2;
	}

	public void setExtentTinyint2(int extentTinyint2) {
		this.extentTinyint2 = extentTinyint2;
	}

	public int getExtentTinyint3() {
		return extentTinyint3;
	}

	public void setExtentTinyint3(int extentTinyint3) {
		this.extentTinyint3 = extentTinyint3;
	}

	public int getExtentTinyint4() {
		return extentTinyint4;
	}

	public void setExtentTinyint4(int extentTinyint4) {
		this.extentTinyint4 = extentTinyint4;
	}

	@Override
	public String toString() {
		return "PcsFileDto [fsId=" + fsId + ", userId=" + userId + ", appId=" + appId + ", path=" + path
				+ ", parentPath=" + parentPath + ", serverFilename=" + serverFilename + ", serverMtime=" + serverMtime
				+ ", serverCtime=" + serverCtime + ", localMtime=" + localMtime + ", localCtime=" + localCtime
				+ ", isDir=" + isDir + ", ifHasSubdir=" + ifHasSubdir + ", status=" + status + ", category=" + category
				+ ", isDelete=" + isDelete + ", extentInt3=" + extentInt3 + ", extentTinyint1=" + extentTinyint1
				+ ", extentTinyint2=" + extentTinyint2 + ", extentTinyint3=" + extentTinyint3 + ", extentTinyint4="
				+ extentTinyint4 + ", share=" + share + ", operId=" + operId + ", unlist=" + unlist + ", mtime=" + mtime
				+ ", ctime=" + ctime + ", size=" + size + "]";
	}
}
