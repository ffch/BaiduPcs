package com.cff.baidupcs.ui.test;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ProgressMonitor;
import javax.swing.Timer;

public class TestProcess2 {
	Timer timer;

	public void init() {
		final SimulatedTargetMi target = new SimulatedTargetMi(1000);
		// 以启动一条线程的方式来执行一个耗时的任务
		final Thread targetThread = new Thread(target);
		targetThread.start();
		// 创建进度对话框
		final ProgressMonitor dialog = new ProgressMonitor(null, "等待任务完成,任务完成之前请不要关闭窗口，否则将取消当前操作...", "已完成：0.00%", 0,
				target.getAmount());
		// 创建一个计时器
		timer = new Timer(300, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 以任务的当前完成量设置进度对话框的完成比例
				dialog.setProgress(target.getCurrent());
				dialog.setNote("已完成：" + target.getPercent());
				// 如果用户单击了进度对话框的”取消“按钮
				if (dialog.isCanceled()) {
					// 停止计时器
					timer.stop();
					// 中断任务的执行线程
					targetThread.interrupt();
					// 系统退出
					System.exit(0);
				}
			}
		});
		timer.start();
	}

	public static void main(String[] args) {
		new TestProcess2().init();
	}
}

// 模拟一个耗时的任务
class SimulatedTargetMi implements Runnable {
	// 任务的当前完成量
	private volatile int current;
	// 总任务量
	private int amount;

	public SimulatedTargetMi(int amount) {
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
				Thread.sleep(50);
			} catch (InterruptedException e) {
			}
			current++;
		}
	}

	public String getPercent() {
		return String.format("%.2f", 100.0 * current / amount) + "%";
	}
}
