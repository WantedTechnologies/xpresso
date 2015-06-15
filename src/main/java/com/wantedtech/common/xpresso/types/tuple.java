package com.wantedtech.common.xpresso.types;

import com.wantedtech.common.xpresso.Lengthful;

public interface tuple extends Comparable<tuple>, Lengthful{
	
    public int size();
    
    public int len();
    
    public <E> E get(int index, Class<E> elementClass);
    public Object get(int index);
    public Object get(String fieldName);
    
    public int getInt(int index);
    public int getInt(String fieldName);
    
    public float getFloat(int index);
    public float getFloat(String fieldName);
    
    public double getDouble(int index);
    public double getDouble(String fieldName);
    
    public boolean getBoolean(int index);
    public boolean getBoolean(String fieldName);
    
    public dict<?> getDict(int index);
    public dict<?> getDict(String fieldName);
    
    public tuple getTuple(int index);
    public tuple getTuple(String fieldName);
    
    public list<?> getList(int index);
    public list<?> getList(String fieldName);
    
    public String getString(int index);
    public String getString(String fieldName);
    
    public str getStr(int index);
    public str getStr(String fieldName);
    
    public <E> E get(String fieldName, Class<E> elementClass);
    
    public tuple name(String... fieldNames);
    
    public int compareTo(tuple o);
    boolean in(Iterable<tuple> iterable);
    
    tuple copy();

}
