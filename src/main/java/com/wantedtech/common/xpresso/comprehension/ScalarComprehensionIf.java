package com.wantedtech.common.xpresso.comprehension;

import com.wantedtech.common.xpresso.functional.Predicate;
import com.wantedtech.common.xpresso.types.list;

public class ScalarComprehensionIf<O> {

	ScalarComprehension<O> comprehension;
	
	public ScalarComprehensionIf(ScalarComprehension<O> comprehension){
		this.comprehension = comprehension;	
	}
	
	public ScalarComprehensionElse<O> when(Predicate<Object> scalarPredicate){
		this.comprehension.when(scalarPredicate);
		return new ScalarComprehensionElse<O>(this.comprehension);
	}
	
	protected ScalarComprehensionElse<O> unless(Predicate<?> scalarPredicate){
		this.comprehension.unless(scalarPredicate);
		return new ScalarComprehensionElse<O>(this.comprehension);
	}
	
	public ScalarComprehensionFinal<O> forEach(Iterable<?> listOfElements){
		this.comprehension.forEach(listOfElements);
		return new ScalarComprehensionFinal<O>(this.comprehension);
	}
	public ScalarComprehensionFinal<O> forEach(list<?> listOfElements){
		this.comprehension.forEach(listOfElements);
		return new ScalarComprehensionFinal<O>(this.comprehension);
	}
	
	public ScalarComprehensionFinal<O> forEach(Object scalar0,Object scalar1,Object... scalars){
		this.comprehension.forEach(scalar0,scalar1,scalars);
		return new ScalarComprehensionFinal<O>(this.comprehension);
	}

}
