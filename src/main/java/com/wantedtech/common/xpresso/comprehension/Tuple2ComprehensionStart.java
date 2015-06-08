package com.wantedtech.common.xpresso.comprehension;

import java.util.ArrayList;

import com.wantedtech.common.xpresso.x;
import com.wantedtech.common.xpresso.functional.Function;

public class Tuple2ComprehensionStart {

	Tuple2Comprehension comprehension;
	
	public Tuple2ComprehensionStart(Tuple2Comprehension comprehension){
		this.comprehension = comprehension;
	}
	
	public Tuple2ComprehensionIf apply(Function<Object,?> function0,Function<Object,?> function1){
		this.comprehension.apply(function0,function1);
		return new Tuple2ComprehensionIf(this.comprehension);
	}
	
	public Tuple2ComprehensionIf value(Object value0,Object value1){
		this.comprehension.apply(x.constant(value0),x.constant(value1));
		return new Tuple2ComprehensionIf(this.comprehension);
	}
	
	public Tuple2ComprehensionFinal forIter(Iterable<?> elements){
		this.comprehension.forIter(elements);
		return new Tuple2ComprehensionFinal(this.comprehension);
	}
	
	public Tuple2ComprehensionFinal forIter(Object element0,Object element1,Object... elements){
		ArrayList<Object> inputList = new ArrayList<Object>();
		inputList.add(element0);
		inputList.add(element1);
		for (Object element : elements){
			inputList.add(element);
		}
		this.comprehension.forIter(inputList);
		return new Tuple2ComprehensionFinal(this.comprehension);
	}
	
}
