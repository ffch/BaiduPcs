package com.cff.ui.timer;

import java.util.TimerTask;

import javax.swing.ProgressMonitor;

import com.cff.baidupcs.util.SystemUtil;
import com.cff.download.SiteFileFetch;
import com.cff.download.SiteFileFetchInter;

public class SpeedTimerTask extends TimerTask {
	long lastDown = 0L;
	int rate;
	SiteFileFetchInter siteFileFetch;
	String speedDisplay="";
	public SpeedTimerTask(SiteFileFetchInter siteFileFetch) {
		this.siteFileFetch = siteFileFetch;
	}

	@Override
	public void run() {
		if (siteFileFetch.isOver()) {
			try {
				this.finalize();
				System.gc();
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}

		long downloaded = siteFileFetch.getDownloaded();
		double speed = ((double) (downloaded - lastDown) / 1024) / 1000;
		lastDown = downloaded;
		double rateNow = (double) downloaded / siteFileFetch.getnFileLength() * 100;
		rate = (int)rateNow;
		speedDisplay = String.format("%.3f", speed) + "M/s";
		if (speed < 1)
			speedDisplay = String.format("%.1f", speed * 1000) + "k/s";
		
		System.out.println("下载线程：" + siteFileFetch.getCurThreadNum() + "/" + siteFileFetch.getTotalThreadNum()
				+ ", 已下载：" +  rate + "%, " + "当前速度：" + speedDisplay);

	}

	public static void main(String args[]) {
		long a = 123445L;
		float b = (float) a / 1000;
		System.out.print(b);
	}

	public int getAmount() {
		return 100;
	}

	public int getCurrent() {
		return rate;
	}
	
	public String getSpeedDisplay() {
		return speedDisplay;
	}
}
