package com.cff.test.download;

import java.io.*;
import java.net.*;

import com.cff.baidupcs.util.SystemUtil;

public class SiteFileFetch extends Thread {
	SiteInfoBean siteInfoBean = null; // 文件信息 Bean
	long[] nStartPos; // 开始位置
	long[] nEndPos; // 结束位置
	FileSplitterFetch[] fileSplitterFetch; // 子线程对象
	long nFileLength; // 文件长度
	boolean bFirst = true; // 是否第一次取文件
	boolean bStop = false; // 停止标志
	File tmpFile; // 文件下载的临时信息
	DataOutputStream output; // 输出到文件的输出流

	public SiteFileFetch(SiteInfoBean bean) throws IOException {
		siteInfoBean = bean;
		tmpFile = new File(bean.getSFilePath() + File.separator + bean.getSFileName() + ".info");
		if (tmpFile.exists()) {
			bFirst = false;
			read_nPos();
		} else {
			nStartPos = new long[bean.getNSplitter()];
			nEndPos = new long[bean.getNSplitter()];
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
				nFileLength = getFileSize();
				if (nFileLength == -1) {
					System.err.println("File Length is not known!");
				} else if (nFileLength == -2) {
					System.err.println("File is not access!");
				} else {
					for (int i = 0; i < nStartPos.length; i++) {
						nStartPos[i] = (long) (i * (nFileLength / nStartPos.length));
					}
					for (int i = 0; i < nEndPos.length - 1; i++) {
						nEndPos[i] = nStartPos[i + 1];
					}
					nEndPos[nEndPos.length - 1] = nFileLength;
				}
			}
			// 启动子线程
			fileSplitterFetch = new FileSplitterFetch[nStartPos.length];
			for (int i = 0; i < nStartPos.length; i++) {
				fileSplitterFetch[i] = new FileSplitterFetch(siteInfoBean.getSSiteURL(),
						siteInfoBean.getSFilePath() + File.separator + siteInfoBean.getSFileName(), nStartPos[i],
						nEndPos[i], i);
				SystemUtil.logInfo("Thread " + i + " , nStartPos = " + nStartPos[i] + ", nEndPos = " + nEndPos[i]);
				fileSplitterFetch[i].start();
			}
			
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
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 获得文件长度
	public long getFileSize() {
		int nFileLength = -1;
		try {
			URL url = new URL(siteInfoBean.getSSiteURL());
			HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
			httpConnection.setRequestProperty("User-Agent", "NetFox");
			int responseCode = httpConnection.getResponseCode();
			if (responseCode >= 400) {
				processErrorCode(responseCode);
				return -2; // -2 represent access is error
			}
			String sHeader;
			for (int i = 1;; i++) {
				// DataInputStream in = new
				// DataInputStream(httpConnection.getInputStream ());
				// Utility.log(in.readLine());
				sHeader = httpConnection.getHeaderFieldKey(i);
				if (sHeader != null) {
					if (sHeader.equals("Content-Length")) {
						nFileLength = Integer.parseInt(httpConnection.getHeaderField(sHeader));
						break;
					}
				} else
					break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		SystemUtil.logInfo(nFileLength);
		return nFileLength;
	}

	// 保存下载信息（文件指针位置）
	private void write_nPos() {
		try {
			output = new DataOutputStream(new FileOutputStream(tmpFile));
			output.writeInt(nStartPos.length);
			for (int i = 0; i < nStartPos.length; i++) {
				// output.writeLong(nPos[i]);
				output.writeLong(fileSplitterFetch[i].nStartPos);
				output.writeLong(fileSplitterFetch[i].nEndPos);
			}
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 读取保存的下载信息（文件指针位置）
	private void read_nPos() {
		try {
			DataInputStream input = new DataInputStream(new FileInputStream(tmpFile));
			int nCount = input.readInt();
			nStartPos = new long[nCount];
			nEndPos = new long[nCount];
			for (int i = 0; i < nStartPos.length; i++) {
				nStartPos[i] = input.readLong();
				nEndPos[i] = input.readLong();
			}
			input.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
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
}
