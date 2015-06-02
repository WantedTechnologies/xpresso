package com.wantedtech.common.xpresso.types;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.wantedtech.common.xpresso.KeyValue;
import com.wantedtech.common.xpresso.x;
import com.wantedtech.common.xpresso.types.tuple.tuple2;

public class Bag<T> implements Iterable<T>{
	
	Map<T,Integer> bag = new HashMap<T,Integer>();
	
	T toBeSetAt;
	
	public Bag(Iterable<T> iterable){
		for(T element : iterable){
			if(!bag.containsKey(element)){
				bag.put(element, 1);
			}else{
				bag.put(element, bag.get(element)+1);
			}
		}
	}
	
	public Bag(Map<T,Integer> map){
		for(T element : map.keySet()){
			bag.put(element, map.get(element));
		}
	}
	
	public Bag(){}
	
	public Bag<T> put(T element){
		this.bag.put(element, bag.get(element)+1);
		return this;
	}
	
	public Bag<T> put(Iterable<T> iterable){
		if(x.len(iterable) == 0){
			return this;
		}
		dict<Integer> iterableAsDict;
		if(iterable instanceof dict<?>){
			try{
				iterableAsDict = (dict<Integer>)iterable;
			}catch(ClassCastException e){
				throw new IllegalArgumentException("Input dict has to be of type Integer");
			}
			ArrayList<String> keysAsStrings = new ArrayList<String>();
			for(T element : iterable){
				keysAsStrings.add((String)element);
			}
			return put((Map<T,Integer>)(iterableAsDict.toHashMap()));
		}
		return this;
	}
	
	public Bag<T> put(Map<T,Integer> map){
		for(T element : map.keySet()){
			if(!bag.containsKey(element)){
				bag.put(element, 1);
			}else{
				bag.put(element, bag.get(element)+map.get(element));
			}
		}
		return this;
	}
	
	public Bag<T> remove(T element){
		if(!bag.containsKey(element)){
			return this;
		}
		if(bag.get(element) == 1){
			bag.remove(element);
		}else{
			this.bag.put(element, bag.get(element)-1);	
		}
		return this;
	}
	
	public Bag<T> remove(Iterable<T> iterable){
		if(x.len(iterable) == 0){
			return this;
		}
		dict<Integer> iterableAsDict;
		if(iterable instanceof dict<?>){
			try{
				iterableAsDict = (dict<Integer>)iterable;
			}catch(ClassCastException e){
				throw new IllegalArgumentException("Input dict has to be of type Integer");
			}
			ArrayList<String> keysAsStrings = new ArrayList<String>();
			for(T element : iterable){
				keysAsStrings.add((String)element);
			}
			return put((Map<T,Integer>)(iterableAsDict.toHashMap()));
		}
		return this;
	}
	
	public Bag<T> remove(Map<T,Integer> map){
		for(T element : map.keySet()){
			if(bag.containsKey(element)){
				int newQuantity = bag.get(element)-map.get(element);
				if(newQuantity <= 0){
					bag.remove(element);
				}else{
					bag.put(element, newQuantity);	
				}
			}
		}
		return this;
	}
	
	//iterator for bag's elements
	public Iterable<T> elements(){
		Iterable<T> generator = new Iterable<T>(){
			public Iterator<T> iterator(){
				final Iterator<T> iter = bag.keySet().iterator();
				return new Iterator<T>(){
					
					int currentElementCurrentCount = 0;
					int currentElementMaxCount = 0;
					T currentElement;
					
					@Override
					public boolean hasNext() { 
						if(iter.hasNext() && (currentElementMaxCount == 0 || currentElementCurrentCount < currentElementMaxCount)){
							return true;
						}
						return false;
					}

					@Override
					public T next() {
						if(currentElementCurrentCount == 0 || currentElementCurrentCount == currentElementMaxCount){
							currentElement = iter.next();
							currentElementCurrentCount = 0;
							currentElementMaxCount = bag.get(currentElement);
						}
						currentElementCurrentCount++;
						return currentElement;
					}
				};
			}
		};
		return generator;
	}
	
	public int count(T element){
		if(bag.containsKey(element)){
			return bag.get(element);
		}
		return 0;
	}
	
	public list<tuple2<T,Integer>> items(){
		list<tuple2<T,Integer>> result = x.list();
		for(T element : this){
			result.append(tuple2.valueOf(element, bag.get(element)));
		}
		return result;
	}
	
	public set<T> tokens(){
		return x.set(bag.keySet());
	}
	
	public Bag<T> reset(){
		bag = new HashMap<T,Integer>();
		return this;
	}
	
	public list<T> mostCommonN(int N){
		ArrayList<KeyValue<Integer,T>> kvList = new ArrayList<KeyValue<Integer,T>>();
		for(T element : bag.keySet()){
			kvList.add(new KeyValue<Integer,T>(bag.get(element),element));
		}
		Collections.sort(kvList);
		Collections.reverse(kvList);
		list<T> result = x.list();
		for(KeyValue<Integer,T> kv : kvList){
			result.append(kv.getValue());
		}
		return result;
	}
	public list<T> mostCommonN(){
		return mostCommonN(x.len(bag));
	}
	public list<T> leastCommonN(int N){
		return x.list(x.reversed(mostCommonN())).sliceTo(N);
	}
	public list<T> leastCommonN(){
		return x.list(x.reversed(mostCommonN()));
	}

	@Override
	public Iterator<T> iterator() {
		return bag.keySet().iterator();
	}
}
