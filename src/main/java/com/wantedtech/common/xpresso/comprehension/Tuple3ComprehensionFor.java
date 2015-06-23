package com.wantedtech.common.xpresso.comprehension;

import java.util.ArrayList;

public class Tuple3ComprehensionFor {

	Tuple3Comprehension comprehension;
	
	public Tuple3ComprehensionFor(Tuple3Comprehension comprehension){
		this.comprehension = comprehension;
	}
	
	public <T> Tuple3ComprehensionFinal in(Iterable<T> elements){
		this.comprehension.in(elements);
		return new Tuple3ComprehensionFinal(this.comprehension);
	}
	
	public <T> Tuple3ComprehensionFinal in(T element0,T element1,@SuppressWarnings("unchecked") T... elements){
		ArrayList<T> inputList = new ArrayList<T>();
		inputList.add(element0);
		inputList.add(element1);
		for (T element : elements){
			inputList.add(element);
		}
		this.comprehension.in(inputList);
		return new Tuple3ComprehensionFinal(this.comprehension);
	}
	
}
