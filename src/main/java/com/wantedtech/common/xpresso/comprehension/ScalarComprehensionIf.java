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
	
	/*protected ScalarComprehensionElse<O> ifElementNot(Predicate<?> scalarPredicate){
		this.comprehension.ifElementNot(scalarPredicate);
		return new ScalarComprehensionElse<O>(this.comprehension);
	}*/
	
	public ScalarComprehensionFinal<O> forIter(Iterable<?> listOfElements){
		this.comprehension.forIter(listOfElements);
		return new ScalarComprehensionFinal<O>(this.comprehension);
	}
	public ScalarComprehensionFinal<O> forIter(list<?> listOfElements){
		this.comprehension.forIter(listOfElements);
		return new ScalarComprehensionFinal<O>(this.comprehension);
	}
	
	public ScalarComprehensionFinal<O> forIter(Object scalar0,Object scalar1,Object... scalars){
		this.comprehension.forIter(scalar0,scalar1,scalars);
		return new ScalarComprehensionFinal<O>(this.comprehension);
	}

}
