package com.wantedtech.common.xpresso.comprehension;

public class ScalarComprehensionFor<O> {

	ScalarComprehension<O> comprehension;
	
	public ScalarComprehensionFor(ScalarComprehension<O> comprehension){
		this.comprehension = comprehension;	
	}
	
	public ScalarComprehensionFinal<O> forIter(Iterable<?> listOfElements){
		this.comprehension.forIter(listOfElements);
		return new ScalarComprehensionFinal<O>(this.comprehension);
	}

	public ScalarComprehensionFinal<O> forIter(Object scalar0,Object scalar1,Object... scalars){
		this.comprehension.forIter(scalar0,scalar1,scalars);
		return new ScalarComprehensionFinal<O>(this.comprehension);
	}
	
}
