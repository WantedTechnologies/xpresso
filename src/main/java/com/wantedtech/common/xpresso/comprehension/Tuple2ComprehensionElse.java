package com.wantedtech.common.xpresso.comprehension;

import com.wantedtech.common.xpresso.functional.Function;

public class Tuple2ComprehensionElse {

	Tuple2Comprehension comprehension;
	
	public Tuple2ComprehensionElse(Tuple2Comprehension comprehension){
		this.comprehension = comprehension;
	}	
	
	public Tuple2ComprehensionFor elseTransformWith(Function<Object,Object> function){
		return new Tuple2ComprehensionFor(this.comprehension);
	}
	
}
