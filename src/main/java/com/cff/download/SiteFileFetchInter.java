package com.cff.download;

public interface SiteFileFetchInter {

	public void write_nPos();

	public boolean isOver();

	public long getDownloaded();

	public long getnFileLength();

	public int getCurThreadNum();

	public int getTotalThreadNum();

}
