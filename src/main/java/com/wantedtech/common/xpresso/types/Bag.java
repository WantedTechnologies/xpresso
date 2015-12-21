package com.wantedtech.common.xpresso.types;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.wantedtech.common.xpresso.x;
import com.wantedtech.common.xpresso.types.tuples.tuple2;

public class Bag<T> implements Iterable<T>{
	
	Map<T,Integer> map = new HashMap<T,Integer>();
	
	T toBeSetAt;
	
	public Bag(Map<T,Integer> map){
		for(T element : map.keySet()){
			this.map.put(element, map.get(element));
		}
	}
	
	public Bag(){}
	
	public Bag(Iterable<T> iterable){
		for(T element : iterable){
			if(!map.containsKey(element)){
				map.put(element, 1);
			}else{
				map.put(element, map.get(element)+1);
			}
		}
	}
	
	public Bag<T> put(T element){
		this.map.put(element, map.get(element)+1);
		return this;
	}
	
	public Bag<T> put(Iterable<T> iterable){
		for(T element : iterable){
			if(!map.containsKey(element)){
				map.put(element, 1);
			}else{
				map.put(element, map.get(element)+1);
			}
		}
		return this;
	}
	
	public Bag<T> put(Map<T,Integer> map){
		for(T element : map.keySet()){
			if(!this.map.containsKey(element)){
				this.map.put(element, 1);
			}else{
				this.map.put(element, this.map.get(element)+map.get(element));
			}
		}
		return this;
	}
	
	public Bag<T> remove(T element){
		if(!map.containsKey(element)){
			return this;
		}
		if(map.get(element) == 1){
			map.remove(element);
		}else{
			this.map.put(element, map.get(element)-1);	
		}
		return this;
	}
	
	public Bag<T> remove(Iterable<T> iterable){
		for(T element : iterable){
			if(map.containsKey(element)){
				int newQuantity = map.get(element)-1;
				if(newQuantity <= 0){
					map.remove(element);
				}else{
					map.put(element, newQuantity);	
				}
			}
		}
		return this;
	}
	
	public Bag<T> remove(Map<T,Integer> map){
		for(T element : map.keySet()){
			if(this.map.containsKey(element)){
				int newQuantity = this.map.get(element)-map.get(element);
				if(newQuantity <= 0){
					this.map.remove(element);
				}else{
					this.map.put(element, newQuantity);	
				}
			}
		}
		return this;
	}
	
	//iterator for bag's elements
	public Iterable<T> elements(){
		Iterable<T> generator = new Iterable<T>(){
			public Iterator<T> iterator(){
				final Iterator<T> iter = map.keySet().iterator();
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
							currentElementMaxCount = map.get(currentElement);
						}
						currentElementCurrentCount++;
						return currentElement;
					}
					
					@Override
					public void remove() {

					}
					
				};
			}
		};
		return generator;
	}
	
	public int count(T element){
		if(map.containsKey(element)){
			return map.get(element);
		}
		return 0;
	}
	
	public list<tuple2<T,Integer>> items(){
		list<tuple2<T,Integer>> result = x.list();
		for(T element : this){
			result.append(tuple2.valueOf(element, map.get(element)));
		}
		return result;
	}
	
	public set<T> tokens(){
		return x.set(map.keySet());
	}
	
	public Bag<T> reset(){
		map = new HashMap<T,Integer>();
		return this;
	}
	
	public list<T> mostCommonN(int N){
		class KeyValue<K extends Comparable<K>,V> implements Comparable<KeyValue<K,V>>, Serializable {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = -6209178251903971368L;
			
			private K key;
			private V value;
			
			public KeyValue(K key, V value){
				this.key = key;
				this.value = value;
			}
			
			public K getKey(){
				return this.key;
			}
			
			public V getValue(){
				return this.value;
			}
			
			public int compareTo(KeyValue<K,V> keyValue){
				return this.key.compareTo(keyValue.getKey());
			}
			
			@Override
			public String toString(){
				return this.key.toString() + '~' + this.value.toString();
			}
		}

		ArrayList<KeyValue<Integer,T>> kvList = new ArrayList<KeyValue<Integer,T>>();
		for(T element : map.keySet()){
			kvList.add(new KeyValue<Integer,T>(map.get(element),element));
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
		return mostCommonN(x.len(map));
	}
	public list<T> leastCommonN(int N){
		return x.list(x.reverse(mostCommonN())).sliceTo(N);
	}
	public list<T> leastCommonN(){
		return x.list(x.reverse(mostCommonN()));
	}

	@Override
	public Iterator<T> iterator() {
		return map.keySet().iterator();
	}
	
	@Override
	public String toString() {
		list<tuple2<T,Integer>> items = this.items();
		items = x.list(x.sort(items, x.<Integer>lambdaF("x : x[1]"), true));
		return "{" + x.String(items.toString()).slice(1,-1) + "}";
	}
}
