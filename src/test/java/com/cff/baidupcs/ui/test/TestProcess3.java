package com.cff.baidupcs.ui.test;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.Timer;

public class TestProcess3 {
	JFrame frame = new JFrame("www.jb51.net - 当前进度指示...");
	// 创建一条垂直进度条
	JProgressBar bar = new JProgressBar(JProgressBar.HORIZONTAL);
	JLabel tipLabel = new JLabel("提示：", JLabel.LEFT);
	JLabel contentLabel = new JLabel("任务完成之前请不要关闭窗口，否则将取消当前操作...", JLabel.LEFT);
	JLabel statusLabel = new JLabel(" ", JLabel.CENTER);

	public void init() {
		frame.setLayout(new FlowLayout());
		frame.setResizable(false);
		tipLabel.setFont(new Font("Serif", Font.PLAIN, 14));
		contentLabel.setFont(new Font("Serif", Font.PLAIN, 14));
		statusLabel.setFont(new Font("Serif", Font.PLAIN, 14));
		JPanel panel = new JPanel();
		// fr5.setBorder(new TitledBorder("BoxLayout - Y"));
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.add(tipLabel);
		panel.add(Box.createVerticalStrut(2));
		panel.add(contentLabel);
		panel.add(Box.createVerticalStrut(7));
		panel.add(bar);
		// panel.add(Box.createVerticalGlue());
		panel.add(Box.createVerticalStrut(2));
		panel.add(statusLabel);
		frame.add(panel, 0);
		final SimulatedTarget target = new SimulatedTarget(1000);
		// 以启动一条线程的方式来执行一个耗时的任务
		final Thread thread = new Thread(target);
		thread.start();
		// 设置在进度条中绘制完成百分比
		bar.setStringPainted(true);
		// bar.setPreferredSize(new Dimension(100, 18));
		// 设置进度条的最大值和最小值,
		bar.setMinimum(0);
		// 以总任务量作为进度条的最大值
		bar.setMaximum(target.getAmount());
		final Timer timer = new Timer(300, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 以任务的当前完成量设置进度条的value
				bar.setValue(target.getCurrent());
				if (target.getAmount() <= target.getCurrent()) {
					statusLabel.setText("处理完成,oh yes!");
				}
			}
		});
		timer.start();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		// frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				thread.interrupt();
				timer.stop();
				// 系统退出
				System.exit(0);
			}
		});
		// 该代码依据放置的组件设定窗口的大小使之正好能容纳你放置的所有组件
		frame.pack();
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		new TestProcess3().init();
	}
}

// 模拟一个耗时的任务
class SimulatedTarget implements Runnable {
	// 任务的当前完成量
	private volatile int current;
	// 总任务量
	private int amount;

	public SimulatedTarget(int amount) {
		current = 0;
		this.amount = amount;
	}

	public int getAmount() {
		return amount;
	}

	public int getCurrent() {
		return current;
	}

	// run方法代表不断完成任务的过程
	public void run() {
		while (current < amount) {
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
			}
			current++;
		}
	}

	public String getPercent() {
		return String.format("%.1f", 100.0 * current / amount) + "%";
	}
}
