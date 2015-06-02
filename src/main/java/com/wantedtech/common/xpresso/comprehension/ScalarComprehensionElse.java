package com.wantedtech.common.xpresso.comprehension;

import com.wantedtech.common.xpresso.functional.Function;

public class ScalarComprehensionElse<O> {

	ScalarComprehension<O> comprehension;
	
	public ScalarComprehensionElse(ScalarComprehension<O> comprehension){
		this.comprehension = comprehension;	
	}
	
	public ScalarComprehensionFor<O> elseTransformWith(Function<Object,?> scalarFunction){
		this.comprehension.elseTransformWith(scalarFunction);
		return new ScalarComprehensionFor<O>(this.comprehension);
	}

}
