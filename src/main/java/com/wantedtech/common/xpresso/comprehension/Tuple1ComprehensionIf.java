package com.wantedtech.common.xpresso.comprehension;

import com.wantedtech.common.xpresso.functional.Predicate;

public class Tuple1ComprehensionIf {
	
	Tuple1Comprehension comprehension;
	
	public Tuple1ComprehensionIf(Tuple1Comprehension comprehension){
		this.comprehension = comprehension;
	}
	
	public Tuple1ComprehensionElse ifElement(Predicate<Object> predicate){
		this.comprehension.ifElement(predicate);
		return new Tuple1ComprehensionElse(this.comprehension);
	}
	public Tuple1ComprehensionElse ifNotElement(Predicate<Object> predicate){
		this.comprehension.ifNotElement(predicate);
		return new Tuple1ComprehensionElse(this.comprehension);
	}
	public Tuple1ComprehensionElse ifElementNot(Predicate<Object> predicate){
		this.comprehension.ifElementNot(predicate);
		return new Tuple1ComprehensionElse(this.comprehension);
	}
	
	Tuple1ComprehensionFinal forElementIn(Iterable<Object> elements){
		this.comprehension.forElementIn(elements);
		return new Tuple1ComprehensionFinal(this.comprehension);
	}
	@SafeVarargs
	public final Tuple1ComprehensionFinal forElementIn(Object element0,Object element1,Object... elements){
		this.comprehension.forElementIn(element0,element1,elements);
		return new Tuple1ComprehensionFinal(this.comprehension);
	}
}
