package com.wantedtech.common.xpresso.comprehension;

import com.wantedtech.common.xpresso.functional.Function;

public class Tuple3ComprehensionElse {

	Tuple3Comprehension comprehension;
	
	public Tuple3ComprehensionElse(Tuple3Comprehension comprehension){
		this.comprehension = comprehension;
	}	
	
	public Tuple3ComprehensionElse apply(Function<Object,?> function0, Function<Object,?> function1, Function<Object,?> function2){
		this.comprehension.apply(function0,function1,function2);
		return new Tuple3ComprehensionElse(this.comprehension);
	}
	
	public Tuple3ComprehensionElse apply(Function<Object,?> function0, Function<Object,?> function1){
		this.comprehension.apply(function0,function1);
		return new Tuple3ComprehensionElse(this.comprehension);
	}
	
	public Tuple3ComprehensionElse apply(Function<Object,?> function0){
		this.comprehension.apply(function0);
		return new Tuple3ComprehensionElse(this.comprehension);
	}
	
	public Tuple3ComprehensionElse replace(Object value0,Object value1,Object value2){
		this.comprehension.replace(value0,value1,value2);
		return new Tuple3ComprehensionElse(this.comprehension);
	}

	public Tuple3ComprehensionElse replace(Object value0,Object value1){
		this.comprehension.replace(value0,value1);
		return new Tuple3ComprehensionElse(this.comprehension);
	}
	
	public Tuple3ComprehensionElse replace(Object value0){
		this.comprehension.replace(value0);
		return new Tuple3ComprehensionElse(this.comprehension);
	}
	
	public Tuple3ComprehensionFor where(String fieldName0,String... fieldNames){
		this.comprehension.where(fieldName0,fieldNames);
		return new Tuple3ComprehensionFor(this.comprehension);
	}
	
}
