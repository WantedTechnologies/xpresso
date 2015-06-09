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
	
	public ScalarComprehensionIf<O> replace(O value){
		return apply(x.constant(value));
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
	
    public Iterator<O> iterator(){
    	return this.comprehension.transformedElements.iterator();
    }
	
}
