package com.wantedtech.common.xpresso.comprehension;

import java.util.ArrayList;

import com.wantedtech.common.xpresso.x;
import com.wantedtech.common.xpresso.experimental.helpers.Helpers;
import com.wantedtech.common.xpresso.functional.Function;
import com.wantedtech.common.xpresso.types.list;
import com.wantedtech.common.xpresso.types.tuple;

class Tuple1Comprehension extends AbstractTupleComprehension{
	
	public Tuple1Comprehension(int index0){
		this.outputIndices[0] = index0; 
	}
	
	public void apply(Function<Object,?> function){
		this.if_function_0 = function;
	}
	
	public void applyOtherwise(Function<Object,Object> function){
		this.else_function_0 = function;
	}

	@SuppressWarnings("unchecked")
	@Override
	void forIter(Iterable<?> elements){
		if(!(elements instanceof Iterable<?>)){
			throw new IllegalArgumentException("The input of forIter has to be an Iterable.");
		}
		original_elements = Helpers.newArrayList((Iterable<Object>)elements);
		for(Object element : elements){
			tuple outputElement;
			if(if_predicate.apply(element)){
				if(element instanceof tuple){
					outputElement = x.tuple(if_function_0.apply((((tuple)element).get(outputIndices[0]))));	
				}else if(element instanceof Iterable<?>){
					list<?> elementAsList = x.list((Iterable<?>)element); 
					outputElement = x.tuple(if_function_0.apply(((elementAsList).get(outputIndices[0]))));
				}else{
					outputElement = null;
				}
			}else{
				if(element instanceof tuple){
					outputElement = x.tuple(else_function_0.apply((((tuple)element).get(outputIndices[0]))));	
				}else if(element instanceof Iterable<?>){
					list<?> elementAsList = x.list((Iterable<?>)element);
					outputElement = x.tuple(else_function_0.apply(((elementAsList).get(outputIndices[0]))));
				}else{
					outputElement = null;
				}
			}
			original_elements.add(element);
			transformed_elements.add(outputElement);
		}
		this.elements = transformed_elements;
		before_for = false;
	}
	
	public void forIter(Object element0,Object element1,Object... elements){
		ArrayList<Object> lst = Helpers.newArrayList(element0,element1,elements);
		forIter(lst);
	}
	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		for(tuple item: this){
			result.append(item.toString()+", ");
		}
		return result.toString();
	}
	
}
