package com.wantedtech.common.xpresso.comprehension;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.wantedtech.common.xpresso.x;
import com.wantedtech.common.xpresso.functional.Function;
import com.wantedtech.common.xpresso.functional.Predicate;
import com.wantedtech.common.xpresso.helpers.Helpers;
import com.wantedtech.common.xpresso.types.tuple;

public abstract class AbstractTupleComprehension implements Iterable<tuple>{

	boolean before_for = true;
	List<Function<Object,?>> if_functions = Helpers.newArrayList();
	List<Function<Object,?>> else_functions = Helpers.newArrayList();
	Predicate<Object> if_predicate = x.TRUE;
	ArrayList<Object> original_elements = new ArrayList<Object>();
	ArrayList<tuple> elements = new ArrayList<tuple>();
	ArrayList<tuple> transformed_elements = new ArrayList<tuple>();
	
	List<String> outputFieldNames = Helpers.newArrayList();
	List<String> elementFieldNames = Helpers.newArrayList();

	public void when(Predicate<Object> predicate){
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
	
	
	public void unless(Predicate<Object> predicate){
		when(x.NOT(predicate));
	}
	/*public void ifElementNot(Predicate<Object> predicate){
		when(x.NOT(predicate));
	}*/
		
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
