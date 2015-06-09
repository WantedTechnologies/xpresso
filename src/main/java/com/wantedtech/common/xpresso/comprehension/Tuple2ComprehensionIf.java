package com.wantedtech.common.xpresso.comprehension;

import com.wantedtech.common.xpresso.functional.Function;
import com.wantedtech.common.xpresso.functional.Predicate;

public class Tuple2ComprehensionIf {

	Tuple2Comprehension comprehension;
	
	public Tuple2ComprehensionIf(Tuple2Comprehension comprehension){
		this.comprehension = comprehension;
	}
	
	public Tuple2ComprehensionIf apply(Function<Object,?> function){
		this.comprehension.apply(function);
		return new Tuple2ComprehensionIf(this.comprehension);
	}
	
	public Tuple2ComprehensionIf apply(Function<Object,?> function0,Function<Object,?> function1){
		this.comprehension.apply(function0,function1);
		return new Tuple2ComprehensionIf(this.comprehension);
	}
	
	public Tuple2ComprehensionIf replace(Object value){
		this.comprehension.replace(value);
		return new Tuple2ComprehensionIf(this.comprehension);
	}
	
	public Tuple2ComprehensionIf replace(Object value0,Object value1){
		this.comprehension.replace(value0, value1);
		return new Tuple2ComprehensionIf(this.comprehension);
	}
	
	public Tuple2ComprehensionElse when(Predicate<Object> predicate){
		this.comprehension.when(predicate);
		return new Tuple2ComprehensionElse(this.comprehension);
	}
	public Tuple2ComprehensionElse unless(Predicate<Object> predicate){
		this.comprehension.unless(predicate);
		return new Tuple2ComprehensionElse(this.comprehension);
	}
	
	public Tuple2ComprehensionFor where(String fieldName0, String... fieldNames){
		this.comprehension.where(fieldName0, fieldNames);
		return new Tuple2ComprehensionFor(this.comprehension);
	}
}
