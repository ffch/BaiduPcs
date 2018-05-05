package com.cff.baidupcs.ui.test;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.jdesktop.swingx.JXBusyLabel;
import org.jdesktop.swingx.JXFrame;

public class TestCircle {
	public static void main(String args[]) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException{
		JFrame frame = new JFrame();
		frame.setIconImage(null);  
		frame.setLayout(new BorderLayout());
		JXBusyLabel label = new JXBusyLabel();
		 frame.add(label,BorderLayout.EAST);
		 label.setText("正在加载中，请稍后。。");
//		 frame.setSize(40, 80);
		 //...
		 label.setBusy(true);
		 frame.pack();
//        // ...
//		InfiniteProgressPanel glasspane = new InfiniteProgressPanel();
//
//            Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
//                glasspane.setBounds(10, 10, (dimension.width) / 2, (dimension.height) / 2);
//        frame.setGlassPane(glasspane);
//        glasspane.setText("Loading data, Please wait ...");
//        glasspane.start();//开始动画加载效果
        frame.setVisible(true);
	}
}
