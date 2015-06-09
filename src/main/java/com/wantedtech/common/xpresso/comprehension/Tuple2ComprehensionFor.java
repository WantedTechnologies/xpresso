package com.wantedtech.common.xpresso.comprehension;

import java.util.ArrayList;

public class Tuple2ComprehensionFor {

	Tuple2Comprehension comprehension;
	
	public Tuple2ComprehensionFor(Tuple2Comprehension comprehension){
		this.comprehension = comprehension;
	}
	
	public <T> Tuple2ComprehensionFinal in(Iterable<T> elements){
		this.comprehension.in(elements);
		return new Tuple2ComprehensionFinal(this.comprehension);
	}
	
	public <T> Tuple2ComprehensionFinal in(T element0,T element1,@SuppressWarnings("unchecked") T... elements){
		ArrayList<T> inputList = new ArrayList<T>();
		inputList.add(element0);
		inputList.add(element1);
		for (T element : elements){
			inputList.add(element);
		}
		this.comprehension.in(inputList);
		return new Tuple2ComprehensionFinal(this.comprehension);
	}
	
}
