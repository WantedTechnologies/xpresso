package com.wantedtech.common.xpresso.comprehension;

import java.util.Iterator;

import com.wantedtech.common.xpresso.functional.Function;
import com.wantedtech.common.xpresso.types.list;

public class ScalarComprehensionStart<O> {

	ScalarComprehension<O> comprehension;
	
	public ScalarComprehensionStart(ScalarComprehension<O> comprehension){
		this.comprehension = comprehension;	
	}

	public ScalarComprehensionIf<O> transformWith(Function<Object,?> scalarFunction){
		this.comprehension.transformWith(scalarFunction);
		return new ScalarComprehensionIf<O>(this.comprehension);
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
	
    public Iterator<O> iterator(){
    	return this.comprehension.transformedElements.iterator();
    }
	
}
