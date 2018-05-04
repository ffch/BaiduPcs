package com.cff.ui.timer;

import java.util.TimerTask;

import javax.swing.ProgressMonitor;

import com.cff.baidupcs.util.SystemUtil;
import com.cff.download.SiteFileFetch;
import com.cff.download.SiteFileFetchInter;

public class SpeedTimerTask implements Runnable {
	static long lastDown = 0L;
	SiteFileFetchInter siteFileFetch;
	ProgressMonitor pbar;
	public SpeedTimerTask(SiteFileFetchInter siteFileFetch,ProgressMonitor pbar) {
		this.siteFileFetch = siteFileFetch;
		this.pbar = pbar;
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
		double rate = (double) downloaded / siteFileFetch.getnFileLength() * 100;

		String speedDisplay = String.format("%.3f", speed) + "M/s";
		if (speed < 1)
			speedDisplay = String.format("%.1f", speed * 1000) + "k/s";
		if (pbar.isCanceled()) {
            pbar.close();
            System.exit(1);
        }
        pbar.setProgress((int)rate);
        pbar.setNote("Operation is " + rate + "% complete");
//		SystemUtil.logClear("下载线程：" + siteFileFetch.getCurThreadNum() + "/" + siteFileFetch.getTotalThreadNum()
//				+ ", 已下载：" + String.format("%.1f", rate) + "%, " + "当前速度：" + speedDisplay, "\r%36s");
	}

	public static void main(String args[]) {
		long a = 123445L;
		float b = (float) a / 1000;
		System.out.print(b);
	}
}
