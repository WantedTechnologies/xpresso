package com.wantedtech.common.xpresso.comprehension;

import java.io.Serializable;
import java.util.Iterator;

import com.wantedtech.common.xpresso.functional.Predicate;
import com.wantedtech.common.xpresso.types.list;
import com.wantedtech.common.xpresso.types.tuple.tuple;

public class Tuple2ComprehensionFinal implements Serializable, Iterable<tuple> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8537292253517583503L;
	
	Tuple2Comprehension comprehension;
	
	public Tuple2ComprehensionFinal(Tuple2Comprehension comprehension){
		this.comprehension = comprehension;
	}
	
	public Iterable<tuple> ifElement(Predicate<Object> predicate){
		this.comprehension.ifElement(predicate);
		return list.newArrayList(this.comprehension.iterator());
	}
	public Iterable<tuple> ifNotElement(Predicate<Object> predicate){
		this.comprehension.ifNotElement(predicate);
		return list.newArrayList(this.comprehension.iterator());
	}
	public Iterable<tuple> ifElementNot(Predicate<Object> predicate){
		this.comprehension.ifElementNot(predicate);
		return list.newArrayList(this.comprehension.iterator());
	}
	
	public Iterator<tuple> iterator() {
		return this.comprehension.iterator();
	}
	
	public String toString() {
		return this.comprehension.toString();
	}
	
}
