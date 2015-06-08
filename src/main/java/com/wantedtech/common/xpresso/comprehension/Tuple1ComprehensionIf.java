package com.wantedtech.common.xpresso.comprehension;

import com.wantedtech.common.xpresso.functional.Predicate;

public class Tuple1ComprehensionIf {
	
	Tuple1Comprehension comprehension;
	
	public Tuple1ComprehensionIf(Tuple1Comprehension comprehension){
		this.comprehension = comprehension;
	}
	
	public Tuple1ComprehensionElse when(Predicate<Object> predicate){
		this.comprehension.when(predicate);
		return new Tuple1ComprehensionElse(this.comprehension);
	}
	public Tuple1ComprehensionElse unless(Predicate<Object> predicate){
		this.comprehension.unless(predicate);
		return new Tuple1ComprehensionElse(this.comprehension);
	}
	/*
	public Tuple1ComprehensionElse ifElementNot(Predicate<Object> predicate){
		this.comprehension.ifElementNot(predicate);
		return new Tuple1ComprehensionElse(this.comprehension);
	}
	*/
	Tuple1ComprehensionFinal forIter(Iterable<Object> elements){
		this.comprehension.forIter(elements);
		return new Tuple1ComprehensionFinal(this.comprehension);
	}
	@SafeVarargs
	public final Tuple1ComprehensionFinal forIter(Object element0,Object element1,Object... elements){
		this.comprehension.forIter(element0,element1,elements);
		return new Tuple1ComprehensionFinal(this.comprehension);
	}
}
