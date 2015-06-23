package com.wantedtech.common.xpresso.types;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.wantedtech.common.xpresso.x;
import com.wantedtech.common.xpresso.types.tuples.tuple2;

public class OrderedDict<T> extends dict<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6050559020252906298L;
	
	List<String> keys = new ArrayList<String>();
	
	public OrderedDict(Class<T> valueType){
		this.valueType = valueType;
	} 
	
	public OrderedDict(Map<String,T> map){
		throw new UnsupportedOperationException("An OrderedDict can only be initialized with an ordered type.");
	}
	
	@SuppressWarnings("unchecked")
	public OrderedDict(Iterable<?> iterable){
		try{
			for(tuple key__value : (Iterable<tuple>)iterable){
				put((String)((tuple)key__value).get(0),(T)((tuple)key__value).get(1));
				if(!dict.containsKey((String)((tuple)key__value).get(0))){
					keys.add((String)((tuple)key__value).get(0));	
				}
			}
			if(x.len(iterable) != 0){
				this.valueType = (Class<T>)((Iterable<tuple>)iterable).iterator().next().getClass();						
			}
		}catch(Exception e0){
			try{
				for(String key : (OrderedDict<T>)iterable){
					put(key,(T)((OrderedDict<T>)iterable).get(key));
					if(!dict.containsKey(key)){
						keys.add(key);	
					}
				}
				if(x.len(iterable) != 0){
					this.valueType = (Class<T>)((OrderedDict<T>)iterable).iterator().next().getClass();						
				}
			}catch(Exception e1){
				throw new IllegalArgumentException("The input iterable object has to be an Iterable<tuple> or two dimensions or an Iterable<tuple2<String,T>> or an OrderedDict");	
			}	
		}
	}
	
	public OrderedDict(Iterable<String> iterable,Class<T> valueType){
		this.valueType = valueType;
		for(String key : iterable){
			if(!dict.containsKey(key)){
				keys.add(key);	
			}
			this.dict.put(key, createNewInstanceOfT());
		}
	}
	
	public OrderedDict(){
		super();
	}
	
	@Override
	protected dict<T> put(String key, T value){
		if(!this.dict.containsKey(key)){
			keys.add(key);
		}
		super.put(key,value);
		return this;
	}
	
	@Override
	public final OrderedDict<T> update(tuple... tuples){
		for(tuple key__value : tuples){
			if(!this.dict.containsKey(key__value.get(0,String.class))){
				keys.add(key__value.get(0,String.class));
			}
		}
		super.update(tuples);
		return this;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public OrderedDict<T> update(Iterable<?> iterable){
		try{
			for(tuple key__value : (Iterable<tuple>)iterable){
				put(((tuple)key__value).get(0,String.class),(T)((tuple)key__value).get(1));
				if(!this.dict.containsKey(key__value.get(0,String.class))){
					keys.add(key__value.get(0,String.class));
				}
			}
		}catch(Exception e1){
			try{
				for(String key : (OrderedDict<T>)iterable){
					put(key,(T)((OrderedDict<T>)iterable).get(key));
					if(!dict.containsKey(key)){
						keys.add(key);	
					}
				}
				if(x.len(iterable) != 0){
					this.valueType = (Class<T>)((OrderedDict<T>)iterable).iterator().next().getClass();						
				}
			}catch(Exception e2){
				try{
					for(String key:(Iterable<String>)iterable){ 
						dict.put(key, createNewInstanceOfT());
						if(!this.dict.containsKey(key)){
							keys.add(key);
						}
					}
				}catch(Exception e3){
					throw new IllegalArgumentException("The input iterable object wasn't recognized as a valid source of data for a dict.");
				}	
			}
		}
		return this;
	}
	
	@Override
	public OrderedDict<T> update(Map<String,T> iterable){
		throw new UnsupportedOperationException("An OrderedDict can only be updated from an ordered type.");
	}
	
	@SafeVarargs
	@Override
	public final OrderedDict<T> update(tuple2<String,T>... tuples){
		for(tuple2<String,T> tuple : tuples){
			if(!this.dict.containsKey(tuple.value0)){
				keys.add(tuple.value0);
			}
			put(tuple.value0,tuple.value1);
		}
		return this;
	}
	
	@Override
	public list<tuple2<String,T>> items(){
		list<tuple2<String,T>> items = x.list();
		for(String key : keys){
			try{
				items.append(tuple2.valueOf(key,this.get(key)));	
			}catch(Exception e){
				
			}
		}
		return items;
	}
	
	@Override
	public list<String> keys(){
		return x.list(keys);
	}
	
	@Override
	public list<T> values(){
		list<T> values = x.list();
		for(String key : keys){
			values.append(get(key));
		}
		return values;
	}
	
	@Override
	public OrderedDict<String> invert(){
		throw new UnsupportedOperationException("An OrderedDict can only be updated from an ordered type.");
	}
	
	@Override
	public Iterator<String> iterator(){
		return keys.iterator();
	}
	
	public OrderedDict<T> copy(){
		return new OrderedDict<T>(this);
	}	

}
