package com.wantedtech.common.xpresso.comprehension;

import com.wantedtech.common.xpresso.x;
import com.wantedtech.common.xpresso.functional.Function;

public class ScalarComprehensionElse<O> {

	ScalarComprehension<O> comprehension;
	
	public ScalarComprehensionElse(ScalarComprehension<O> comprehension){
		this.comprehension = comprehension;	
	}
	
	public ScalarComprehensionFor<O> applyOtherwise(Function<Object,?> scalarFunction){
		this.comprehension.applyOtherwise(scalarFunction);
		return new ScalarComprehensionFor<O>(this.comprehension);
	}
	
	public ScalarComprehensionFor<O> replaceOtherwise(O value){
		return applyOtherwise(x.constant(value));
	}

}
