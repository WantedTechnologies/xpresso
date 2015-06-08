package com.wantedtech.common.xpresso.comprehension;

public class Tuple1ComprehensionFor {
	
	Tuple1Comprehension comprehension;
	
	public Tuple1ComprehensionFor(Tuple1Comprehension comprehension){
		this.comprehension = comprehension;
	}
	
	public Tuple1ComprehensionFinal forIter(Iterable<Object> elements){
		this.comprehension.forIter(elements);
		return new Tuple1ComprehensionFinal(this.comprehension);
	}
	
	public Tuple1ComprehensionFinal forIter(Object element0,Object element1,Object... elements){
		this.comprehension.forIter(element0,element1,elements);
		return new Tuple1ComprehensionFinal(this.comprehension);
	}
}
