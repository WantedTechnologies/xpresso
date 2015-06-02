package com.wantedtech.common.xpresso.comprehension;

public class ScalarComprehensionFor<O> {

	ScalarComprehension<O> comprehension;
	
	public ScalarComprehensionFor(ScalarComprehension<O> comprehension){
		this.comprehension = comprehension;	
	}
	
	public ScalarComprehensionFinal<O> forElementIn(Iterable<?> listOfElements){
		this.comprehension.forElementIn(listOfElements);
		return new ScalarComprehensionFinal<O>(this.comprehension);
	}

	public ScalarComprehensionFinal<O> forElementIn(Object scalar0,Object scalar1,Object... scalars){
		this.comprehension.forElementIn(scalar0,scalar1,scalars);
		return new ScalarComprehensionFinal<O>(this.comprehension);
	}
	
}
