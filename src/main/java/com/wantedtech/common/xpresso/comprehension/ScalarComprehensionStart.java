package com.wantedtech.common.xpresso.comprehension;

import java.util.Iterator;

import com.wantedtech.common.xpresso.x;
import com.wantedtech.common.xpresso.functional.Function;
import com.wantedtech.common.xpresso.types.list;

public class ScalarComprehensionStart<O> {

	ScalarComprehension<O> comprehension;
	
	public ScalarComprehensionStart(ScalarComprehension<O> comprehension){
		this.comprehension = comprehension;	
	}

	public ScalarComprehensionIf<O> apply(Function<Object,?> scalarFunction){
		this.comprehension.apply(scalarFunction);
		return new ScalarComprehensionIf<O>(this.comprehension);
	}
	
	public ScalarComprehensionIf<O> value(O value){
		return apply(x.constant(value));
	}
	
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
	
    public Iterator<O> iterator(){
    	return this.comprehension.transformedElements.iterator();
    }
	
}
