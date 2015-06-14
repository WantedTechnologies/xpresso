package com.wantedtech.common.xpresso.time;

import java.util.concurrent.TimeUnit;

public class Time {
	//get time in milliseconds
	public double time(){
		double time = ((double)(System.currentTimeMillis())) / 1000d;
		return time;
	}
	public void sleep(int seconds){
		try {
			TimeUnit.SECONDS.sleep(seconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
