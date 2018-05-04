package com.cff.cache;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.cff.ui.file.PcsFile;

public class CacheManager {
	public static Map<String,List<PcsFile>> pcsFileMap = new ConcurrentHashMap<String,List<PcsFile>>();
	
	public static void putPcsFileMap(String path, List<PcsFile> files){
		pcsFileMap.put(path, files);
	}
	
	public static List<PcsFile> get(String path){
		return pcsFileMap.get(path);
	}
}
