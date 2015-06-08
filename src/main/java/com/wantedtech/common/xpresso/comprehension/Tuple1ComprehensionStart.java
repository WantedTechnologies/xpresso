package com.wantedtech.common.xpresso.comprehension;

import java.util.ArrayList;

import com.wantedtech.common.xpresso.x;
import com.wantedtech.common.xpresso.functional.Function;

public class Tuple1ComprehensionStart {

	Tuple1Comprehension comprehension;
	
	public Tuple1ComprehensionStart(Tuple1Comprehension comprehension){
		this.comprehension = comprehension;
	}
	
	public Tuple1ComprehensionIf apply(Function<Object,Object> function){
		this.comprehension.apply(function);
		return new Tuple1ComprehensionIf(this.comprehension);
	}
	
	public Tuple1ComprehensionIf value(Object value){
		return apply(x.constant(value));
	}
	
	public Tuple1ComprehensionFinal forIter(Iterable<?> elements){
		this.comprehension.forIter(elements);
		return new Tuple1ComprehensionFinal(this.comprehension);
	}
	
	public Tuple1ComprehensionFinal forIter(Object element0,Object element1,Object... elements){
		ArrayList<Object> inputList = new ArrayList<Object>();
		inputList.add(element0);
		inputList.add(element1);
		for (Object element : elements){
			inputList.add(element);
		}
		this.comprehension.forIter(inputList);
		return new Tuple1ComprehensionFinal(this.comprehension);
	}
	
}
