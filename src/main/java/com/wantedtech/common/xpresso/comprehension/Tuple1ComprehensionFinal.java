package com.wantedtech.common.xpresso.comprehension;

import java.io.Serializable;
import java.util.Iterator;

import com.wantedtech.common.xpresso.functional.Predicate;
import com.wantedtech.common.xpresso.helpers.Helpers;
import com.wantedtech.common.xpresso.types.tuple;

public class Tuple1ComprehensionFinal implements Serializable, Iterable<tuple> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8537292253517583503L;
	
	Tuple1Comprehension comprehension;
	
	public Tuple1ComprehensionFinal(Tuple1Comprehension comprehension){
		this.comprehension = comprehension;
	}
	
	public Iterable<tuple> when(Predicate<Object> predicate){
		this.comprehension.when(predicate);
		return Helpers.newArrayList(this.comprehension.iterator());
	}
	public Iterable<tuple> unless(Predicate<Object> predicate){
		this.comprehension.unless(predicate);
		return Helpers.newArrayList(this.comprehension.iterator());
	}
	
	public Iterator<tuple> iterator() {
		return this.comprehension.iterator();
	}
	
	public String toString() {
		return this.comprehension.toString();
	}
	
}
