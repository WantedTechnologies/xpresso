package com.wantedtech.common.xpresso;

import java.io.Serializable;

public class KeyValue<K extends Comparable<K>,V> implements Comparable<KeyValue<K,V>>, Serializable {
	
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
