package com.wantedtech.common.xpresso.time;

import com.wantedtech.common.xpresso.x;

public class Timer {
	private double start;
	
	private double end;
	
	private boolean started = false;
	
	public Timer(){
		start = x.time();
		started = true;
	}
	
	public Timer(double start, double end){
		this.start = start;
		this.end = end;
		this.started = false;
	}
	
	public Timer stop(){
		if(!this.started){
			throw new IllegalStateException("Cannot stop the timer that doesn't run. Use start() method first.");
		}
		end = x.time();
		started = false;
		return this;
	}
	
	public Timer start(){
		if(this.started){
			throw new IllegalStateException("Cannot start the timer that already runs. Use stop() method first.");
		}
		start = x.time();
		started = true;
		return this;
	}
	
	@Override
	public String toString(){
		if(this.started){
			throw new IllegalStateException("Cannot print the elapsed time for the running timer. Stop the timer first.");
		}
		return String.valueOf(x.round(end - start, 3))+"s";
	}
}
