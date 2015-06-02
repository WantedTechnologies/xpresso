package com.wantedtech.common.xpresso.comprehension;

import com.wantedtech.common.xpresso.functional.Predicate;
import com.wantedtech.common.xpresso.types.list;

public class ScalarComprehensionIf<O> {

	ScalarComprehension<O> comprehension;
	
	public ScalarComprehensionIf(ScalarComprehension<O> comprehension){
		this.comprehension = comprehension;	
	}
	
	public ScalarComprehensionElse<O> ifElement(Predicate<Object> scalarPredicate){
		this.comprehension.ifElement(scalarPredicate);
		return new ScalarComprehensionElse<O>(this.comprehension);
	}
	
	protected ScalarComprehensionElse<O> ifNotElement(Predicate<?> scalarPredicate){
		this.comprehension.ifNotElement(scalarPredicate);
		return new ScalarComprehensionElse<O>(this.comprehension);
	}
	
	protected ScalarComprehensionElse<O> ifElementNot(Predicate<?> scalarPredicate){
		this.comprehension.ifElementNot(scalarPredicate);
		return new ScalarComprehensionElse<O>(this.comprehension);
	}
	
	public ScalarComprehensionFinal<O> forElementIn(Iterable<?> listOfElements){
		this.comprehension.forElementIn(listOfElements);
		return new ScalarComprehensionFinal<O>(this.comprehension);
	}
	public ScalarComprehensionFinal<O> forElementIn(list<?> listOfElements){
		this.comprehension.forElementIn(listOfElements);
		return new ScalarComprehensionFinal<O>(this.comprehension);
	}
	
	public ScalarComprehensionFinal<O> forElementIn(Object scalar0,Object scalar1,Object... scalars){
		this.comprehension.forElementIn(scalar0,scalar1,scalars);
		return new ScalarComprehensionFinal<O>(this.comprehension);
	}

}
