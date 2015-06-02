package com.wantedtech.common.xpresso.comprehension;

import java.util.ArrayList;

import com.wantedtech.common.xpresso.functional.Predicate;

public class Tuple2ComprehensionIf {

	Tuple2Comprehension comprehension;
	
	public Tuple2ComprehensionIf(Tuple2Comprehension comprehension){
		this.comprehension = comprehension;
	}
	
	public Tuple2ComprehensionElse ifElement(Predicate<Object> predicate){
		this.comprehension.ifElement(predicate);
		return new Tuple2ComprehensionElse(this.comprehension);
	}
	public Tuple2ComprehensionElse ifNotElement(Predicate<Object> predicate){
		this.comprehension.ifNotElement(predicate);
		return new Tuple2ComprehensionElse(this.comprehension);
	}
	public Tuple2ComprehensionElse ifElementNot(Predicate<Object> predicate){
		this.comprehension.ifElementNot(predicate);
		return new Tuple2ComprehensionElse(this.comprehension);
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
