package com.wantedtech.common.xpresso.comprehension;

import com.wantedtech.common.xpresso.functional.Function;


public class Tuple1ComprehensionElse {
	
	Tuple1Comprehension comprehension;
	
	public Tuple1ComprehensionElse(Tuple1Comprehension comprehension){
		this.comprehension = comprehension;
	}	
	
	public Tuple1ComprehensionFor elseTransformWith(Function<Object,Object> function){
		return new Tuple1ComprehensionFor(this.comprehension);
	}
	
}
