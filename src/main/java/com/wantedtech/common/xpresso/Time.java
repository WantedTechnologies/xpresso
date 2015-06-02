package com.wantedtech.common.xpresso;

import java.util.concurrent.TimeUnit;

public class Time {
	//get time in milliseconds
	public static float time(){
		return System.currentTimeMillis() / 1000l;
	}
	public static void sleep(){
		try {
			TimeUnit.SECONDS.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
