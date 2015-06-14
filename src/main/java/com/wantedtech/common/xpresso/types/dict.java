package com.wantedtech.common.xpresso.types;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.wantedtech.common.xpresso.ObjectFactory;
import com.wantedtech.common.xpresso.x;
import com.wantedtech.common.xpresso.helpers.Helpers;
import com.wantedtech.common.xpresso.types.tuples.tuple2;

public class dict<T> implements Iterable<String>, Serializable, Comparable<dict<T>>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1455774484923292726L;

	//used by the construction list.setAt(index).value(val);
	protected String setAtKey = "default";
	
	HashMap<String,T> dict = new HashMap<String,T>();

	Class<T> valueType; 
	
	T createNewInstanceOfT() {
		if(valueType == null){
			throw new NullPointerException("The type of values in the dict has not been initialized.");
		}
		return ObjectFactory.createValueOfType(valueType);
	}
	
	public dict(Class<T> valueType){
		this.valueType = valueType;
	} 
	
	public dict(Map<String,T> map){
		if(x.len(map)!=0){
			this.valueType = (Class<T>)(map.values().iterator().next().getClass());	
		}
		this.dict = Helpers.newHashMap(map);
	}
	
	@SuppressWarnings("unchecked")
	public dict(Iterable<?> iterable){
		try{
			if(x.len(iterable) != 0){
				this.valueType = (Class<T>)(((dict<T>)iterable).values().iterator().next().getClass());
			}
			this.dict.putAll(((dict<T>)iterable).toHashMap());
		}catch(Exception e0){
			e0.printStackTrace();
			try{
				for(T element:(Bag<T>)iterable){
					try{
						T tmp = (T)(new String(""));
					}catch(Exception e1){
						new IllegalArgumentException("To be converted into a dict, the input Bag object has to be a Bag<String>");
					}
					this.valueType = (Class<T>)(Integer.class);
					this.dict.put(element.toString(), createNewInstanceOfT());	
				}
			}catch(Exception e1){
				e1.printStackTrace();
				try{
					if(x.len(iterable) != 0){
						this.valueType = (Class<T>)((Iterable<tuple>)iterable).iterator().next().getClass();						
					}
					for(tuple key__value : (Iterable<tuple>)iterable){
						put((String)((tuple)key__value).get(0),(T)((tuple)key__value).get(1));
					}
				}catch(Exception e3){
					throw new IllegalArgumentException("The input iterable object has to be an Iterable<String> or an Iterable<tuple2<String,T>>");	
				}
			}
		}
	}
	
	public dict(Iterable<String> iterable,Class<T> valueType){
		this.valueType = valueType;
		for(String key : iterable){
			this.dict.put(key, createNewInstanceOfT());
		}
	}
	
	public dict(){
		this.dict = new HashMap<String,T>();
	}
		
	public HashMap<String,T> toHashMap(){
		return Helpers.newHashMap(this.dict);
	}
	
	public T get(String key) throws NoSuchFieldException{
		if(this.dict.containsKey(key)){
			return this.dict.get(key);
		}else{
			throw new NoSuchFieldException(key);
		}
	}
	
	protected dict<T> put(String key, T value){
		this.dict.put(key,value);
		if(value != null){
			this.valueType = (Class<T>)(value.getClass());	
		}
		return this;
	}
	
	@SuppressWarnings("unchecked")
	public dict<T> update(tuple... tuples){
		for(tuple key__value : tuples){
			put(key__value.get(0,String.class),(T)key__value.get(1));
		}
		return this;
	}
	
	@SuppressWarnings("unchecked")
	public dict<T> update(Iterable<?> iterable){
		try{
			HashMap<String,T> addOn = ((dict<T>)iterable).toHashMap();
			if(addOn.size() != 0){
				this.valueType = (Class<T>)(addOn.values().iterator().next().getClass());
			}
			this.dict.putAll(addOn);
		}catch(Exception e0){
			try{
				for(tuple key__value : (Iterable<tuple>)iterable){
					put((String)((tuple)key__value).get(0),(T)((tuple)key__value).get(1));
				}
			}catch(Exception e1){
				try{
					for(String key:(Iterable<String>)iterable){ 
						dict.put(key, createNewInstanceOfT());
					}	
				}catch(Exception e2){
					throw new IllegalArgumentException("The input iterable object wasn't recognized as a valid source of data for a dict.");
				}
			}
		}
		return this;
	}
	
	@SuppressWarnings("unchecked")
	public dict<T> update(Map<String,T> iterable){
		if(iterable.size() != 0){
			this.valueType = (Class<T>)(iterable.values().iterator().next().getClass());
		}
		this.dict.putAll((Map<String,T>)iterable);
		return this;
	}
	
	public dict<T> update(tuple2<String,T>... tuples){
		for(int i=0;i<tuples.length;i++){
			put(tuples[i].value0,tuples[i].value1);
		}
		return this;
	}
	
	public dict<T> setAt(String key){
		setAtKey = key;
		return this;
	}
	
	public final void value(T value){
		put(setAtKey, value);
	}
	
	public boolean contains(String string){
		return ((HashMap<String,T>)dict).containsKey(string);
	}
	
	public list<tuple2<String,T>> items(){
		list<tuple2<String,T>> items = x.list();
		for(String key:this){
			try{
				items.append(tuple2.valueOf(key,this.get(key)));	
			}catch(Exception e){
				
			}
		}
		return items;
	}
	
	public list<String> keys(){
		return x.list(this.dict.keySet());
	}
	
	public list<T> values(){
		return x.list(this.dict.values());
	}
	
	public dict<String> inverted(){
		ArrayList<tuple> items = new ArrayList<tuple>();
		for(String key:this){
			try{
				items.add(x.tuple(this.get(key).toString(),key));	
			}catch(Exception e){
				
			}
		}
		return x.dict(items);
	}
	
	public Iterator<String> iterator(){
		return new Iterator<String>(){
			Iterator<Map.Entry<String, T>> entrySet = dict.entrySet().iterator();
			public boolean hasNext(){
				return entrySet.hasNext();
			}
			public String next(){
				return entrySet.next().getKey();
			}
		};
	}
	
	public dict<T> copy(){
		return new dict<T>(this);
	}
	
	@Override
	public String toString(){
		return "{"+x.String(", ").join(this.items())+"}";
	}
	
	dict<T> clear(){
		dict.clear();
		return this;
	}
	
	public int compareTo(dict<T> anotherDict){
		return this.toString().compareTo(anotherDict.toString());
	} 
}
