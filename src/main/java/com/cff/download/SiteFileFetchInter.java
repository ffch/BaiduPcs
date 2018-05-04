package com.cff.download;

import java.util.TimerTask;

import com.cff.ui.timer.SpeedTimerTask;

public interface SiteFileFetchInter {

	public void write_nPos();

	public boolean isOver();

	public long getDownloaded();

	public long getnFileLength();

	public int getCurThreadNum();

	public int getTotalThreadNum();
	
	public void siteStop();

}
