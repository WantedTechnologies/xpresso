package com.wantedtech.common.xpresso;

public interface Slicable<T> {

	public Slicable<T> plus(Iterable<T> list);
	
	@SuppressWarnings("unchecked")
	public Slicable<T> plus(T element0,T element1,T... elements);
	
	public Slicable<T> times(int times);
	
	public Slicable<T> slice(int startIndex,int endIndex);
	public Slicable<T> slice(int startIndex,int endIndex, int step);
	public Slicable<T> slice();
	public Slicable<T> slice(int step);
	public Slicable<T> slice(Slicer slicer);
	public Slicable<T> sliceTo(int endIndex, int step);
	public Slicable<T> sliceTo(int endIndex);
	public Slicable<T> sliceFrom(int startIndex, int step);
	public Slicable<T> sliceFrom(int startIndex);
	
	public T get(int index);
	
}
