package com.wantedtech.common.xpresso.experimental.helpers;

public class Slicer {
	public int startIndex;
	public int endIndex;
	public int step;
	public boolean includeEnd;
	
	public static Slicer ALL_BUT_THE_LAST_ONE = new Slicer(0,-1,1,false);
	public static Slicer ALL_BUT_THE_FIRST_ONE = new Slicer(1,Integer.MAX_VALUE,1,false);
	public static Slicer EVERY_SECOND = new Slicer(0,Integer.MAX_VALUE,2,true);
	public static Slicer EVERY_THIRD = new Slicer(0,Integer.MAX_VALUE,3,true);
	public static Slicer REVERSED = new Slicer(Integer.MAX_VALUE,0,-1,true);
	
	public Slicer(int startIndex,int endIndex, int step, Boolean includeEnd){
		this.startIndex = startIndex;
		this.endIndex = endIndex;
		this.step = step;
		this.includeEnd = includeEnd;
	}
}
