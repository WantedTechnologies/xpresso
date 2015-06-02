package com.wantedtech.common.xpresso.comprehension;

public class Tuple1ComprehensionFor {
	
	Tuple1Comprehension comprehension;
	
	public Tuple1ComprehensionFor(Tuple1Comprehension comprehension){
		this.comprehension = comprehension;
	}
	
	public Tuple1ComprehensionFinal forElementIn(Iterable<Object> elements){
		this.comprehension.forElementIn(elements);
		return new Tuple1ComprehensionFinal(this.comprehension);
	}
	
	public Tuple1ComprehensionFinal forElementIn(Object element0,Object element1,Object... elements){
		this.comprehension.forElementIn(element0,element1,elements);
		return new Tuple1ComprehensionFinal(this.comprehension);
	}
}
