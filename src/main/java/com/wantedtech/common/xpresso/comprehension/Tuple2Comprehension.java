package com.wantedtech.common.xpresso.comprehension;

import com.wantedtech.common.xpresso.x;
import com.wantedtech.common.xpresso.experimental.helpers.Helpers;
import com.wantedtech.common.xpresso.functional.Function;
import com.wantedtech.common.xpresso.types.list;
import com.wantedtech.common.xpresso.types.tuple;


class Tuple2Comprehension extends AbstractTupleComprehension{

	public Tuple2Comprehension(int... outputIndices){
		this.outputIndices = outputIndices;
	}
	
	public void apply(Function<Object,?> function0,Function<Object,?> function1){
		this.if_function_0 = function0;
		this.if_function_1 = function1;
	}
	
	public void applyOtherwise(Function<Object,?> function0,Function<Object,?> function1){
		this.else_function_0 = function0;
		this.else_function_1 = function1;
	}

	@SuppressWarnings("unchecked")
	public void forIter(Iterable<?> elements){
		if(!(elements instanceof Iterable<?>)){
			throw new IllegalArgumentException("The input of forIter has to be an Iterable.");
		}
		original_elements = Helpers.newArrayList((Iterable<Object>)elements);
		for(Object element : elements){
			tuple outputElement;
			if(if_predicate.apply(element)){
				if(element instanceof tuple){
					outputElement = x.tuple(if_function_0.apply((((tuple)element).get(outputIndices[0]))),if_function_1.apply((((tuple)element).get(outputIndices[1]))));	
				}else if(element instanceof Iterable<?>){
					list<?> elementAsList = x.list((Iterable<?>)element); 
					outputElement = x.tuple(if_function_0.apply((((list<?>)element).get(outputIndices[0]))),if_function_1.apply(((elementAsList).get(outputIndices[1]))));
				}else{
					outputElement = null;
				}
			}else{
				if(element instanceof tuple){
					outputElement = x.tuple(else_function_0.apply((((tuple)element).get(outputIndices[0]))),else_function_1.apply((((tuple)element).get(outputIndices[1]))));	
				}else if(element instanceof Iterable<?>){
					list<?> elementAsList = x.list((Iterable<?>)element);
					outputElement = x.tuple(else_function_0.apply((((list<?>)element).get(outputIndices[0]))),else_function_1.apply(((elementAsList).get(outputIndices[1]))));
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
}
