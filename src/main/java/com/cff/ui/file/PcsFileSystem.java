package com.cff.ui.file;

import java.awt.BorderLayout;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

import org.jdesktop.swingx.JXBusyLabel;

import com.cff.baidupcs.client.service.LsHttpService;
import com.cff.cache.CacheManager;
import com.cff.ui.util.ResourceUtil;

public class PcsFileSystem {
	static Map<String, ImageIcon> fileTypes = new ConcurrentHashMap<String, ImageIcon>();
//	static JFrame frame = new JFrame();
//	static JXBusyLabel label = new JXBusyLabel();
	static {
		URL dirImage = ResourceUtil.getResource("ui/img/dir.jpg");
		URL fileImage = ResourceUtil.getResource("ui/img/file.jpg");
		fileTypes.put("D", new ImageIcon(dirImage));
		fileTypes.put("F", new ImageIcon(fileImage));
//		
//		frame.setLayout(new BorderLayout());
//		frame.add(label, BorderLayout.EAST);
//		label.setText("正在加载中，请稍后。。");
//		label.setBusy(false);
//		frame.pack();
//		frame.setLocationRelativeTo(null);
//		frame.setVisible(false);
	}

	public static Icon getFileIcon(String fileType) {
		return fileTypes.get(fileType);
	}

	public static PcsFile[] getFiles(PcsFile theFile, boolean showHiden) {
//		frame.setVisible(true);
//		label.setBusy(true);
//		frame.pack();
		if (!theFile.isDirectory()){
//			label.setBusy(false);
//			frame.setVisible(false);
			return null;
		}
		List<PcsFile> pcsFiles = null;
		if (CacheManager.get(theFile.getPath()) != null) {
			pcsFiles = CacheManager.get(theFile.getPath());
		} else {
			LsHttpService lsHttpService = new LsHttpService();
			pcsFiles = lsHttpService.runLsUI(theFile.getPath());
			CacheManager.putPcsFileMap(theFile.getPath(), pcsFiles);
		}
		if (pcsFiles == null){
//			label.setBusy(false);
//			frame.setVisible(false);
			return null;
		}
		PcsFile[] files = new PcsFile[pcsFiles.size()];
//		label.setBusy(false);
//		frame.setVisible(false);
		return pcsFiles.toArray(files);
	}

}
