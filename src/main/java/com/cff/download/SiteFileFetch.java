package com.cff.download;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Timer;

import com.alibaba.fastjson.JSONObject;
import com.cff.baidupcs.util.OkHttpUtil;
import com.cff.baidupcs.util.SystemUtil;
import com.cff.download.model.DownLoadChunk;
import com.cff.download.model.DownloadDomain;
import com.cff.download.timer.SpeedTimerTask;

import okhttp3.Headers;

public class SiteFileFetch{
	SiteInfoBean siteInfoBean = null; // 文件信息 Bean
	long[] nStartPos; // 开始位置
	long[] nEndPos; // 结束位置
	boolean[] isFinal;
	FileSplitterFetch[] fileSplitterFetch; // 子线程对象
	private long nFileLength; // 文件长度
	boolean bFirst = true; // 是否第一次取文件
	boolean bStop = false; // 停止标志
	boolean isOver = false; // 结束标志
	int curThreadNum = 0;
	File tmpFile; // 文件下载的临时信息

	public SiteFileFetch(SiteInfoBean bean) throws IOException {
		siteInfoBean = bean;
		tmpFile = new File(bean.getSFilePath() + File.separator + bean.getSFileName() + ".info");
		if (tmpFile.exists()) {
			bFirst = false;
			read_nPos();
		} else {
			nStartPos = new long[bean.getNSplitter()];
			nEndPos = new long[bean.getNSplitter()];
			isFinal = new boolean[bean.getNSplitter()];
		}
	}

	public void run() {
		// 获得文件长度
		// 分割文件
		// 实例 FileSplitterFetch
		// 启动 FileSplitterFetch 线程
		// 等待子线程返回
		try {
			if (bFirst) {
				setnFileLength(getFileSize());
				if (getnFileLength() == -1) {
					System.err.println("File Length is not known!");
				} else if (getnFileLength() == -2) {
					System.err.println("File is not access!");
				} else {
					for (int i = 0; i < nStartPos.length; i++) {
						nStartPos[i] = (long) (i * (getnFileLength() / nStartPos.length));
					}
					for (int i = 0; i < nEndPos.length - 1; i++) {
						nEndPos[i] = nStartPos[i + 1];
					}
					nEndPos[nEndPos.length - 1] = getnFileLength();
				}
			}
			// 启动子线程
			fileSplitterFetch = new FileSplitterFetch[nStartPos.length];
			for (int i = 0; i < nStartPos.length; i++) {
				fileSplitterFetch[i] = new FileSplitterFetch(this, siteInfoBean.getSSiteURL(),
						siteInfoBean.getSFilePath() + File.separator + siteInfoBean.getSFileName(), nStartPos[i],
						nEndPos[i], i);
				//SystemUtil.logInfo("Thread " + i + " , nStartPos = " + nStartPos[i] + ", nEndPos = " + nEndPos[i]);
				if (!isFinal[i]) {
					fileSplitterFetch[i].start();
				}
			}

			// 统计速度
			Timer timer = new Timer();
			SpeedTimerTask speedTimerTask = new SpeedTimerTask(this);
			timer.schedule(speedTimerTask, 0, 1000);

			// 是否结束 while 循环
			boolean breakWhile = false;
			while (!bStop) {
				write_nPos();
				SystemUtil.sleep(500);
				breakWhile = true;
				for (int i = 0; i < nStartPos.length; i++) {
					if (!fileSplitterFetch[i].bDownOver) {
						breakWhile = false;
						break;
					}
				}
				if (breakWhile)
					break;
			}
			System.err.println("文件下载结束！");
			isOver = true;
			tmpFile.delete();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean isOver(){
		return isOver;
	}

	// 获得文件长度
	public long getFileSize() {
		long nFileLength = -1;
		try {
			Headers headers = new Headers.Builder().add("User-Agent", "NetFox").build();

			Map<String, List<String>> headMap = OkHttpUtil.getSimpleInstance()
					.doHeadWithHeader(siteInfoBean.getSSiteURL(), headers);

			List<String> contentLengths = headMap.get("Content-Length");
			if (contentLengths != null && contentLengths.size() > 0) {
				nFileLength = Long.parseLong(contentLengths.get(0));
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		SystemUtil.logInfo("文件大小：" + nFileLength);
		return nFileLength;
	}

	public long getDownloaded() {
		long downloaded = 0L;
		curThreadNum = nStartPos.length;
		for (int i = 0; i < nStartPos.length; i++) {
			downloaded += fileSplitterFetch[i].getDownLoaded();
			if(fileSplitterFetch[i].bDownOver)curThreadNum--;
		}
		return downloaded;
	}
	
	public int getCurThreadNum(){
		return curThreadNum;
	}
	
	public int getTotalThreadNum(){
		return nStartPos.length;
	}

	// 保存下载信息（文件指针位置）
	public synchronized void write_nPos() {
		try {
			FileWriter pw = new FileWriter(tmpFile);
			DownloadDomain downloadDomain = new DownloadDomain();
			downloadDomain.setTotalSize(getnFileLength());
			downloadDomain.setTotalThread(nStartPos.length);
			List<DownLoadChunk> lists = new ArrayList<DownLoadChunk>();
			long downloaded = 0L;
			JSONObject json = new JSONObject();
			for (int i = 0; i < nStartPos.length; i++) {
				DownLoadChunk downLoadChunk = new DownLoadChunk();
				downLoadChunk.setStartPos(fileSplitterFetch[i].nStartPos);
				downLoadChunk.setEndPos(fileSplitterFetch[i].nEndPos);
				downLoadChunk.setFinal(fileSplitterFetch[i].bDownOver);
				lists.add(downLoadChunk);
				downloaded += fileSplitterFetch[i].getDownLoaded();
			}
			downloadDomain.setTotalDownloaded(downloaded);
			downloadDomain.setBlockList(lists);
			json = (JSONObject) JSONObject.toJSON(downloadDomain);
			pw.write(json.toJSONString());
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 读取保存的下载信息（文件指针位置）
	private void read_nPos() {
		String encoding = "UTF-8";
		Long filelength = tmpFile.length();
		byte[] filecontent = new byte[filelength.intValue()];
		try {
			FileInputStream in = new FileInputStream(tmpFile);
			in.read(filecontent);
			in.close();
			String fileJson = new String(filecontent, encoding);
			JSONObject json = JSONObject.parseObject(fileJson);
			DownloadDomain downloadDomain = json.toJavaObject(DownloadDomain.class);
			int totalThread = downloadDomain.getTotalThread();
			nStartPos = new long[totalThread];
			nEndPos = new long[totalThread];
			isFinal = new boolean[totalThread];
			List<DownLoadChunk> lists = downloadDomain.getBlockList();
			for (int i = 0; i < lists.size(); i++) {
				DownLoadChunk tmp = lists.get(i);
				nStartPos[i] = tmp.getStartPos();
				nEndPos[i] = tmp.getEndPos();
				isFinal[i] = tmp.isFinal();
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void processErrorCode(int nErrorCode) {
		System.err.println("Error Code : " + nErrorCode);
	}

	// 停止文件下载
	public void siteStop() {
		bStop = true;
		for (int i = 0; i < nStartPos.length; i++)
			fileSplitterFetch[i].splitterStop();
	}

	public long getnFileLength() {
		return nFileLength;
	}

	public void setnFileLength(long nFileLength) {
		this.nFileLength = nFileLength;
	}
}
