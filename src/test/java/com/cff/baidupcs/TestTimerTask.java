package com.cff.baidupcs;

import java.util.Timer;
import java.util.TimerTask;


public class TestTimerTask {
	public static void main(String args[]){
		Timer timer = new Timer();
		SpeedTimerTask speedTimerTask = new SpeedTimerTask();
		timer.schedule(speedTimerTask, 0, 1000);
	}
	
	
}
class SpeedTimerTask extends TimerTask {
	private static int curint = 10;
	public SpeedTimerTask(){
		
	}
	@Override
	public void run() {
		curint ++;
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(curint);
	}
	
}