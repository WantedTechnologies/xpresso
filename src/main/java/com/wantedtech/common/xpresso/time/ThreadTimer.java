package com.wantedtech.common.xpresso.time;

import com.wantedtech.common.xpresso.x;

public class ThreadTimer {
	
	public static final ThreadLocal<Double> startThreadTimer = new ThreadLocal<Double>();
	
	public static final ThreadLocal<Double> endThreadTimer = new ThreadLocal<Double>();
	
	public static final ThreadLocal<Boolean> startedThreadTimer = new ThreadLocal<Boolean>();
	
	public Timer stop(){
		if(!startedThreadTimer.get()){
			throw new IllegalStateException("Cannot stop the timer that doesn't run. Use start() method first.");
		}
		endThreadTimer.set(x.time());
		startedThreadTimer.set(false);
		return new Timer(startThreadTimer.get(),endThreadTimer.get());
	}
	
	public void start(){
		if(x.isTrue(startedThreadTimer.get())){
			throw new IllegalStateException("Cannot start the timer that already runs. Use stop() method first.");
		}
		startThreadTimer.set(x.time());
		startedThreadTimer.set(true);
	}
}
