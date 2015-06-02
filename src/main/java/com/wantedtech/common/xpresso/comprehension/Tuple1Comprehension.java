package com.wantedtech.common.xpresso.comprehension;

import java.util.ArrayList;

import com.wantedtech.common.xpresso.x;
import com.wantedtech.common.xpresso.functional.Function;
import com.wantedtech.common.xpresso.types.list;
import com.wantedtech.common.xpresso.types.tuple.tuple;

class Tuple1Comprehension extends AbstractTupleComprehension{
	
	public Tuple1Comprehension(int index0){
		this.outputIndices[0] = index0; 
	}
	
	public void transformWith(Function<Object,?> function){
		this.if_function_0 = function;
	}
	
	public void elseTransformWith(Function<Object,Object> function){
		this.else_function_0 = function;
	}

	@SuppressWarnings("unchecked")
	@Override
	void forElementIn(Iterable<?> elements){
		original_elements = list.<Object>newArrayList((Iterable<Object>)elements);
		for(Object element : elements){
			tuple outputElement;
			if(if_predicate.apply(element)){
				if(element instanceof tuple){
					outputElement = x.tupleOf(if_function_0.apply((((tuple)element).get(outputIndices[0]))));	
				}else{
					outputElement = x.tupleOf(if_function_0.apply((((list<?>)element).get(outputIndices[0]))));
				}
			}else{
				if(element instanceof tuple){
					outputElement = x.tupleOf(else_function_0.apply((((tuple)element).get(outputIndices[0]))));	
				}else{
					outputElement = x.tupleOf(else_function_0.apply((((list<?>)element).get(outputIndices[0]))));
				}
			}
			original_elements.add(element);
			transformed_elements.add(outputElement);
		}
		this.elements = transformed_elements;
		before_for = false;
	}
	
	public void forElementIn(Object element0,Object element1,Object... elements){
		ArrayList<Object> lst = list.newArrayList(element0,element1,elements);
		forElementIn(lst);
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
