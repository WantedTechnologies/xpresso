package com.wantedtech.common.xpresso.comprehension;

import com.wantedtech.common.xpresso.functional.Function;

public class Tuple2ComprehensionElse {

	Tuple2Comprehension comprehension;
	
	public Tuple2ComprehensionElse(Tuple2Comprehension comprehension){
		this.comprehension = comprehension;
	}	
	
	public Tuple2ComprehensionElse apply(Function<Object,?> function0, Function<Object,?> function1){
		this.comprehension.apply(function0,function1);
		return new Tuple2ComprehensionElse(this.comprehension);
	}
	
	public Tuple2ComprehensionElse replace(Object value0,Object value1){
		this.comprehension.replace(value0,value1);
		return new Tuple2ComprehensionElse(this.comprehension);
	}
	
	public Tuple2ComprehensionFor where(String fieldName0,String... fieldNames){
		this.comprehension.where(fieldName0,fieldNames);
		return new Tuple2ComprehensionFor(this.comprehension);
	}
	
}
