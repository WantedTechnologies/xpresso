package com.wantedtech.common.xpresso.helpers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.wantedtech.common.xpresso.types.dict;

public class Helpers {
	public static <T> ArrayList<T> newArrayList(){
		ArrayList<T> newArrayList = new ArrayList<T>();
		return newArrayList;
	}
	
	public static <T> ArrayList<T> newArrayList(Iterator<T> iterator){
		ArrayList<T> newArrayList = new ArrayList<T>();
		while(iterator.hasNext()){
			newArrayList.add(iterator.next());
		}
		return newArrayList;
	}
	
	public static <T> ArrayList<T> newArrayList(Iterable<T> iterable){
		ArrayList<T> newArrayList = new ArrayList<T>();
		for (T element : iterable){
			newArrayList.add(element);	
		}
		return newArrayList;
	}
	
	public static <T> ArrayList<T> newArrayList(T[] values){
		ArrayList<T> newArrayList = new ArrayList<T>();
		for (T element : values){
			newArrayList.add(element);	
		}
		return newArrayList;
	}
	
	@SafeVarargs
	public static <T> ArrayList<T> newArrayList(T element0,T element1,T... elements){
		ArrayList<T> newArrayList = new ArrayList<T>();
		newArrayList.add(element0);
		newArrayList.add(element1);
		for (T element : elements){
			newArrayList.add(element);
		}
		return newArrayList;
	}
	
	public static <T0,T1> HashMap<T0,T1> newHashMap(Map<T0,T1> map){
		HashMap<T0,T1> newHashMap = new HashMap<T0,T1>();
		newHashMap.putAll(map);
		return newHashMap; 
	}
	
	public static <T> HashMap<String,T> newHashMap(dict<T> dict){
		return dict.toHashMap();
	}
	
	public static int hashCode(Object o, String... excludeFields){
		return new HashCode(o,excludeFields).hashCode();
	}

	public static int Equals(Object o, String... excludeFields){
		return new HashCode(o,excludeFields).hashCode();
	}
	
}
