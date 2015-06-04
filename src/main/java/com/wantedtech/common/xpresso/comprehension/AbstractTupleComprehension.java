package com.wantedtech.common.xpresso.comprehension;

import java.util.ArrayList;
import java.util.Iterator;

import com.wantedtech.common.xpresso.x;
import com.wantedtech.common.xpresso.functional.Function;
import com.wantedtech.common.xpresso.functional.Predicate;
import com.wantedtech.common.xpresso.types.tuple.tuple;

public abstract class AbstractTupleComprehension implements Iterable<tuple>{

	boolean before_for = true;
	Function<Object,?> if_function_0 = x.doNothing;
	Function<Object,?> else_function_0 = x.doNothing;
	Function<Object,?> if_function_1 = x.doNothing;
	Function<Object,?> else_function_1 = x.doNothing;
	Function<Object,?> if_function_2 = x.doNothing;
	Function<Object,?> else_function_2 = x.doNothing;
	Function<Object,?> if_function_3 = x.doNothing;
	Function<Object,?> else_function_3 = x.doNothing;
	Predicate<Object> if_predicate = x.TRUE;
	ArrayList<Object> original_elements = new ArrayList<Object>();
	ArrayList<tuple> elements = new ArrayList<tuple>();
	ArrayList<tuple> transformed_elements = new ArrayList<tuple>();
	
	int[] outputIndices = new int[]{0};

	public void ifElement(Predicate<Object> predicate){
		if(before_for){
			if_predicate = predicate;
		}else{
			ArrayList<tuple> filtered_elements = new ArrayList<tuple>(); 
			for(tuple idx__element : x.enumerate(original_elements)){
				int index = (int)idx__element.get(0);
				Object element = idx__element.get(1);
				if(predicate.apply(element)){
					filtered_elements.add(transformed_elements.get(index));
				}
				
			}
			elements = filtered_elements;
		}
	}
	
	
	public void ifNotElement(Predicate<Object> predicate){
		ifElement(x.NOT(predicate));
	}
	public void ifElementNot(Predicate<Object> predicate){
		ifElement(x.NOT(predicate));
	}
	
	abstract void forElementIn(Iterable<?> elements);
		
	public Iterator<tuple> iterator() {
		return elements.iterator();
	}
	
	public String toString() {
		StringBuilder result = new StringBuilder();
		for(tuple item: this){
			result.append(item.toString()+", ");
		}
		return result.toString();
	}
}
