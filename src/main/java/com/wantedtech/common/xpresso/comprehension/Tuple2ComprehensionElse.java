package com.wantedtech.common.xpresso.comprehension;

import com.wantedtech.common.xpresso.x;
import com.wantedtech.common.xpresso.functional.Function;

public class Tuple2ComprehensionElse {

	Tuple2Comprehension comprehension;
	
	public Tuple2ComprehensionElse(Tuple2Comprehension comprehension){
		this.comprehension = comprehension;
	}	
	
	public Tuple2ComprehensionFor applyOtherwise(Function<Object,?> function0, Function<Object,?> function1){
		this.comprehension.applyOtherwise(function0,function1);
		return new Tuple2ComprehensionFor(this.comprehension);
	}
	
	public Tuple2ComprehensionFor valueOtherwise(Object value0,Object value1){
		return applyOtherwise(x.constant(value0),x.constant(value1));
	}
	
}
