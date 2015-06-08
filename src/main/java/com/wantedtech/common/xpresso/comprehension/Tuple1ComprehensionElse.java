package com.wantedtech.common.xpresso.comprehension;

import com.wantedtech.common.xpresso.x;
import com.wantedtech.common.xpresso.functional.Function;


public class Tuple1ComprehensionElse {
	
	Tuple1Comprehension comprehension;
	
	public Tuple1ComprehensionElse(Tuple1Comprehension comprehension){
		this.comprehension = comprehension;
	}	
	
	public Tuple1ComprehensionFor applyOtherwise(Function<Object,Object> function){
		this.comprehension.applyOtherwise(function);
		return new Tuple1ComprehensionFor(this.comprehension);
	}
	
	public Tuple1ComprehensionFor valueOtherwise(Object value){
		return applyOtherwise(x.constant(value));
	}
	
}
