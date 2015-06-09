package com.wantedtech.common.xpresso.comprehension;

public class ScalarComprehensionFor<O> {

	ScalarComprehension<O> comprehension;
	
	public ScalarComprehensionFor(ScalarComprehension<O> comprehension){
		this.comprehension = comprehension;	
	}
	
	public ScalarComprehensionFinal<O> forEach(Iterable<?> listOfElements){
		this.comprehension.forEach(listOfElements);
		return new ScalarComprehensionFinal<O>(this.comprehension);
	}

	public ScalarComprehensionFinal<O> forEach(Object scalar0,Object scalar1,Object... scalars){
		this.comprehension.forEach(scalar0,scalar1,scalars);
		return new ScalarComprehensionFinal<O>(this.comprehension);
	}
	
}
