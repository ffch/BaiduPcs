package com.cff.download;

import java.io.*;
import java.net.*;
import java.util.List;
import java.util.Map;

import com.cff.baidupcs.util.OkHttpUtil;
import com.cff.baidupcs.util.SystemUtil;

import okhttp3.Headers;

public class FileSplitterFetch extends Thread {
	String sURL; // File URL
	long nStartPos; // File Snippet Start Position
	long nEndPos; // File Snippet End Position
	int nThreadID; // Thread's ID
	boolean bDownOver = false; // Downing is over
	boolean bStop = false; // Stop identical
	FileAccessI fileAccessI = null; // File Access interface
	long startConst = 0L;
	long downLoaded = 0L;
	SiteFileFetchInter siteFileFetch;
	public FileSplitterFetch(SiteFileFetchInter siteFileFetch,String sURL, String sName, long nStart, long nEnd, int id) throws IOException {
		this.sURL = sURL;
		this.nStartPos = nStart;
		this.nEndPos = nEnd;
		startConst = nStartPos;
		nThreadID = id;
		fileAccessI = new FileAccessI(sName, nStartPos);
		this.siteFileFetch = siteFileFetch;
	}

	public void run() {
		long willRead = nEndPos - nStartPos + 1;
		while (nStartPos < nEndPos && !bStop) {
			try {
				String sProperty = "bytes=" + nStartPos + "-";				
				Headers headers = new Headers.Builder().add("User-Agent", "NetFox").add("RANGE", sProperty).build();
				InputStream input = OkHttpUtil.getSimpleInstance().doGetWithStream(sURL, headers );	
				//SystemUtil.logInfo(sProperty);
				
				// logResponseHead(httpConnection);
				byte[] b = new byte[1024];
				int nRead;
//				while ((nRead = input.read(b, 0, 1024)) > 0 && nStartPos < nEndPos && !bStop) {
//					nStartPos += fileAccessI.write(b, 0, nRead);
//				}
				
//				int curRead = (int) (willRead > 1024 ? 1024 : willRead);
				while ((nRead = input.read(b, 0, 1024)) > 0 && nStartPos < nEndPos && !bStop) {
					nStartPos += fileAccessI.write(b, 0, nRead);
					willRead -= nRead;
//					curRead = (int) (willRead > 1024 ? 1024 : willRead);
					downLoaded = nStartPos - startConst + 1;
				}
				input.close();
				if(downLoaded > (nEndPos - startConst + 1))downLoaded = nEndPos - startConst + 1;

//				SystemUtil.logInfo("Thread " + nThreadID + " is over!");
				if(nStartPos >= nEndPos){
					bDownOver = true;
					fileAccessI.close();
				}
				siteFileFetch.write_nPos();
				// nPos = fileAccessI.write (b,0,nRead);
			} catch (Exception e) {
				//e.printStackTrace();
			}
		}
	}

	// 打印回应的头信息
	public void logResponseHead(HttpURLConnection con) {
		for (int i = 1;; i++) {
			String header = con.getHeaderFieldKey(i);
			if (header != null)
				// responseHeaders.put(header,httpConnection.getHeaderField(header));
				SystemUtil.logInfo(header + " : " + con.getHeaderField(header));
			else
				break;
		}
	}

	public void splitterStop() {
		bStop = true;
	}
	
	public long getDownLoaded(){
		return downLoaded;
	}
}
