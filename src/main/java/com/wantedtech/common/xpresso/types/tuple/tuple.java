package com.wantedtech.common.xpresso.types.tuple;

public interface tuple extends Comparable<tuple>{
	
    public int size();
    
    public <E> E get(int index, Class<E> elementClass);
    public Object get(int index);
    public int compareTo(tuple o);
    boolean in(Iterable<tuple> iterable);

}
