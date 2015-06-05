package com.wantedtech.common.xpresso.comprehension;

import com.wantedtech.common.xpresso.x;
import com.wantedtech.common.xpresso.functional.Function;
import com.wantedtech.common.xpresso.types.list;
import com.wantedtech.common.xpresso.types.tuple.tuple;


class Tuple2Comprehension extends AbstractTupleComprehension{

	public Tuple2Comprehension(int... outputIndices){
		this.outputIndices = outputIndices;
	}
	
	public void transformWith(Function<Object,?> function0,Function<Object,?> function1){
		this.if_function_0 = function0;
		this.if_function_1 = function1;
	}
	
	public void elseTransformWith(Function<Object,?> function0,Function<Object,?> function1){
		this.else_function_0 = function0;
		this.else_function_1 = function1;
	}

	@SuppressWarnings("unchecked")
	public void forElementIn(Iterable<?> elements){
		original_elements = list.<Object>newArrayList((Iterable<Object>)elements);
		for(Object element : elements){
			tuple outputElement;
			if(if_predicate.apply(element)){
				if(element instanceof tuple){
					outputElement = x.tuple(if_function_0.apply((((tuple)element).get(outputIndices[0]))),if_function_1.apply((((tuple)element).get(outputIndices[1]))));	
				}else{
					outputElement = x.tuple(if_function_0.apply((((list<?>)element).get(outputIndices[0]))),if_function_1.apply((((list<?>)element).get(outputIndices[1]))));
				}
			}else{
				if(element instanceof tuple){
					outputElement = x.tuple(else_function_0.apply((((tuple)element).get(outputIndices[0]))),else_function_1.apply((((tuple)element).get(outputIndices[1]))));	
				}else{
					outputElement = x.tuple(else_function_0.apply((((list<?>)element).get(outputIndices[0]))),else_function_1.apply((((list<?>)element).get(outputIndices[1]))));
				}
			}
			original_elements.add(element);
			transformed_elements.add(outputElement);
		}
		this.elements = transformed_elements;
		before_for = false;
	}
}
