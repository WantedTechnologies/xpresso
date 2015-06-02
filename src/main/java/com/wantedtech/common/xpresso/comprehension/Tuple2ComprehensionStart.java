package com.wantedtech.common.xpresso.comprehension;

import java.util.ArrayList;

import com.wantedtech.common.xpresso.functional.Function;

public class Tuple2ComprehensionStart {

	Tuple2Comprehension comprehension;
	
	public Tuple2ComprehensionStart(Tuple2Comprehension comprehension){
		this.comprehension = comprehension;
	}
	
	public Tuple2ComprehensionIf transformWith(Function<Object,?> function0,Function<Object,?> function1){
		this.comprehension.transformWith(function0,function1);
		return new Tuple2ComprehensionIf(this.comprehension);
	}
	
	public Tuple2ComprehensionFinal forElementIn(Iterable<?> elements){
		this.comprehension.forElementIn(elements);
		return new Tuple2ComprehensionFinal(this.comprehension);
	}
	
	public Tuple2ComprehensionFinal forElementIn(Object element0,Object element1,Object... elements){
		ArrayList<Object> inputList = new ArrayList<Object>();
		inputList.add(element0);
		inputList.add(element1);
		for (Object element : elements){
			inputList.add(element);
		}
		this.comprehension.forElementIn(inputList);
		return new Tuple2ComprehensionFinal(this.comprehension);
	}
	
}
