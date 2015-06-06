package com.wantedtech.common.xpresso.comprehension;

import java.io.Serializable;
import java.util.Iterator;

import com.wantedtech.common.xpresso.Helpers;
import com.wantedtech.common.xpresso.functional.Predicate;
import com.wantedtech.common.xpresso.types.list;
import com.wantedtech.common.xpresso.types.tuple.tuple;

public class Tuple1ComprehensionFinal implements Serializable, Iterable<tuple> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6039386204034459534L;
	Tuple1Comprehension comprehension;
	
	public Tuple1ComprehensionFinal(Tuple1Comprehension comprehension){
		this.comprehension = comprehension;
	}
	
	public Iterable<tuple> ifElement(Predicate<Object> predicate){
		this.comprehension.ifElement(predicate);
		return Helpers.newArrayList(this.comprehension.iterator());
	}
	public Iterable<tuple> ifNotElement(Predicate<Object> predicate){
		this.comprehension.ifNotElement(predicate);
		return Helpers.newArrayList(this.comprehension.iterator());
	}
	public Iterable<tuple> ifElementNot(Predicate<Object> predicate){
		this.comprehension.ifElementNot(predicate);
		return Helpers.newArrayList(this.comprehension.iterator());
	}
	
	public Iterator<tuple> iterator(){
		return this.comprehension.iterator();
	}
}
