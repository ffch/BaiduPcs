package com.cff.ui.file;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.ProgressMonitor;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.ListDataListener;

import com.cff.baidupcs.client.service.DownloadService;
import com.cff.download.SiteFileFetchInter;
import com.cff.ui.timer.SpeedTimerTask;
/**
 * @author root 右侧文件显示
 */
public class FileList extends JList implements ActionListener {
	// PathNode theNode;
	FileListModel dataModel;
	static final long serialVersionUID = 10;
	static SpeedTimerTask speedTimerTask = null;
	/**
	* 
	*/
	public FileList() {
		dataModel = new FileListModel();
		setModel(dataModel);
		this.setCellRenderer(new MyCellRenderer());
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JList myList = (JList) e.getSource();
				if (e.getClickCount() == 2) {
					System.out.println("双击");
					int index = myList.getSelectedIndex(); // 已选项的下标
					FolderNode obj = (FolderNode) myList.getModel().getElementAt(index); // 取出数据
					System.out.println(obj.toString());
				}

			}

			@Override
			public void mouseReleased(MouseEvent e) {
				JList myList = (JList) e.getSource();
				JPopupMenu popupMenu = new JPopupMenu();
				JMenuItem downloadMenu = new JMenuItem("下载");
				JMenuItem cancelMenu = new JMenuItem("取消");
				popupMenu.add(downloadMenu); // 添加菜单项Open
				popupMenu.add(cancelMenu);
				if (e.getButton() == 3 && myList.getSelectedIndex() >= 0) {
					popupMenu.show(e.getComponent(), e.getX(), e.getY());
					int index = myList.getSelectedIndex(); // 已选项的下标
					FolderNode obj = (FolderNode) myList.getModel().getElementAt(index); // 取出数据
					System.out.println(obj.toString());
					cancelMenu.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							popupMenu.setVisible(false);
						}
					});
					downloadMenu.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							saveFile(obj);
						}
					});
				}
			}
		});
	}

	public static void saveFile(FolderNode obj) {
		// 弹出文件选择框
		JFileChooser chooser = new JFileChooser();

		// 下面的方法将阻塞，直到【用户按下保存按钮且“文件名”文本框不为空】或【用户按下取消按钮】
		int option = chooser.showSaveDialog(null);
		if (option == JFileChooser.APPROVE_OPTION) { // 假如用户选择了保存
			File file = chooser.getSelectedFile();
			System.out.println(file.getAbsolutePath());
			DownloadService downloadService = new DownloadService();
			SiteFileFetchInter siteFileFetchInter = downloadService.runUI(obj.getAbsPath(), file.getAbsolutePath());
			UIManager.put("ProgressMonitor.progressText", "下载进度");
		    UIManager.put("OptionPane.cancelButtonText", "关闭");
			ProgressMonitor dialog = new ProgressMonitor(null, "等待任务完成", "已完成：", 0, 100);
			speedTimerTask = new SpeedTimerTask(siteFileFetchInter,dialog);
		}
	}
	

	public void fireTreeSelectionChanged(I_fileSystem node) {
		// Vector files = node.getFiles();
		// theNode = node;
		dataModel.setNode(node);
		updateUI();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(speedTimerTask != null)
			SwingUtilities.invokeLater(speedTimerTask);
	}
}
