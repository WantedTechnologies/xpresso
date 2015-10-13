package com.wantedtech.common.xpresso.types;

import com.wantedtech.common.xpresso.helpers.Lengthful;
import com.wantedtech.common.xpresso.regex.Regex;

public abstract class tuple implements Comparable<tuple>, Lengthful {
	
    public abstract int size();
    
    public abstract int len();
    
    public abstract <E> E get(int index, Class<E> elementClass);
    public abstract Object get(int index);
    public abstract Object get(String fieldName);
    
    public abstract int getInt(int index);
    public abstract int getInt(String fieldName);
    
    public abstract float getFloat(int index);
    public abstract float getFloat(String fieldName);
    
    public abstract double getDouble(int index);
    public abstract double getDouble(String fieldName);
    
    public abstract boolean getBoolean(int index);
    public abstract boolean getBoolean(String fieldName);
    
    public abstract dict<?> getDict(int index);
    public abstract dict<?> getDict(String fieldName);
    
    public abstract tuple getTuple(int index);
    public abstract tuple getTuple(String fieldName);
    
    public abstract list<?> getList(int index);
    public abstract list<?> getList(String fieldName);
    
    public abstract String getString(int index);
    public abstract String getString(String fieldName);
    
    public abstract Regex getRegex(int index);
    public abstract Regex getRegex(String fieldName);
    
    public abstract str getStr(int index);
    public abstract str getStr(String fieldName);
    
    public abstract <E> E get(String fieldName, Class<E> elementClass);
    
    public abstract tuple name(String... fieldNames);
    
    public abstract int compareTo(tuple o);
    public abstract boolean in(Iterable<tuple> iterable);
    public abstract list<Object> toList();
    
    public abstract tuple copy();

}
