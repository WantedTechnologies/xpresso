package com.wantedtech.common.xpresso.comprehension;

import com.wantedtech.common.xpresso.functional.Function;

public class Tuple1ComprehensionElse {

	Tuple1Comprehension comprehension;
	
	public Tuple1ComprehensionElse(Tuple1Comprehension comprehension){
		this.comprehension = comprehension;
	}	
	
	public Tuple1ComprehensionElse apply(Function<Object,?> function){
		this.comprehension.apply(function);
		return new Tuple1ComprehensionElse(this.comprehension);
	}
	
	public Tuple1ComprehensionElse replace(Object value){
		this.comprehension.replace(value);
		return new Tuple1ComprehensionElse(this.comprehension);
	}
	
	public Tuple1ComprehensionFor where(String fieldName0,String... fieldNames){
		this.comprehension.where(fieldName0,fieldNames);
		return new Tuple1ComprehensionFor(this.comprehension);
	}
	
}
