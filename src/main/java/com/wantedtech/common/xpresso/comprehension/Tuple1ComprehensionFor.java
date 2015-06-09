package com.wantedtech.common.xpresso.comprehension;

import java.util.ArrayList;

public class Tuple1ComprehensionFor {

	Tuple1Comprehension comprehension;
	
	public Tuple1ComprehensionFor(Tuple1Comprehension comprehension){
		this.comprehension = comprehension;
	}
	
	public <T> Tuple1ComprehensionFinal in(Iterable<T> elements){
		this.comprehension.in(elements);
		return new Tuple1ComprehensionFinal(this.comprehension);
	}
	
	public <T> Tuple1ComprehensionFinal in(T element0,T element1,@SuppressWarnings("unchecked") T... elements){
		ArrayList<T> inputList = new ArrayList<T>();
		inputList.add(element0);
		inputList.add(element1);
		for (T element : elements){
			inputList.add(element);
		}
		this.comprehension.in(inputList);
		return new Tuple1ComprehensionFinal(this.comprehension);
	}
	
}
