package com.cff.ui.file;

public class PcsFile {
	char fileType;
	String fileName;
	String path;

	public PcsFile(char fileType, String path) {
		this.fileType = fileType;
		this.path = path;
		this.fileName = path.substring(path.lastIndexOf("/") == -1 ? 0 : path.lastIndexOf("/"));
	}

	public PcsFile(String serverFilename, String path, int isDir) {
		this.fileName = serverFilename;
		this.path = path;
		this.fileType = isDir == 1 ? I_fileSystem.DIRECTORY : I_fileSystem.FILE;
	}

	public char getFileType() {
		return fileType;
	}

	public void setFileType(char fileType) {
		this.fileType = fileType;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public boolean isDirectory() {
		if (fileType == I_fileSystem.DIRECTORY)
			return true;
		return false;
	}

	@Override
	public String toString() {
		return "PcsFile [fileType=" + fileType + ", fileName=" + fileName + ", path=" + path + "]";
	}
}
