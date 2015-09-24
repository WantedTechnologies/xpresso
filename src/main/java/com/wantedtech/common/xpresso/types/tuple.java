package com.wantedtech.common.xpresso.types;

import com.wantedtech.common.xpresso.helpers.Lengthful;
import com.wantedtech.common.xpresso.regex.Regex;

public interface tuple extends Comparable<Object>, Lengthful{
	
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
    
    public Regex getRegex(int index);
    public Regex getRegex(String fieldName);
    
    public str getStr(int index);
    public str getStr(String fieldName);
    
    public <E> E get(String fieldName, Class<E> elementClass);
    
    public tuple name(String... fieldNames);
    
    public int compareTo(Object o);
    boolean in(Iterable<tuple> iterable);
    list<Object> toList();
    
    tuple copy();

}
