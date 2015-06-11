package com.wantedtech.common.xpresso.comprehension;

import java.io.Serializable;
import java.util.Iterator;
import java.util.ArrayList;

import com.wantedtech.common.xpresso.x;
import com.wantedtech.common.xpresso.functional.Function;
import com.wantedtech.common.xpresso.functional.Predicate;
import com.wantedtech.common.xpresso.helpers.Helpers;
import com.wantedtech.common.xpresso.types.tuple;

class ScalarComprehension<O> implements Iterable<O>, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7495040683343678101L;

	boolean isBeforeFor = true;
			
	Function<Object,O> ifFunction = x.<Object,O>chain(x.doNothing);
	Function<Object,O> elseFunction = x.<Object,O>chain(x.doNothing);
	Predicate<Object> filterPredicate = x.TRUE;
	Predicate<Object> ifPredicate = x.TRUE;
	
	Iterable<O> transformedElements = Helpers.newArrayList();
	Iterable<Object> originalElements = Helpers.newArrayList();
	
	@SuppressWarnings("unchecked")
	protected ScalarComprehension<O> apply(Function<Object,?> scalarFunction){
		ifFunction = (Function<Object,O>)scalarFunction;
		return this;
	}
	
	@SuppressWarnings("unchecked")
	protected final ScalarComprehension<O> applyOtherwise(Function<Object,?> scalarFunction){
		elseFunction = (Function<Object,O>)scalarFunction;
		return this;
	}
	
	protected ScalarComprehension<O> forEach(Iterable<?> listOfElements){
		if(!(listOfElements instanceof Iterable<?>)){
			throw new IllegalArgumentException("The input of forIter has to be an Iterable.");
		}
		ArrayList<O> new_transformed_scalars = Helpers.newArrayList();
		ArrayList<Object> new_original_scalars = Helpers.newArrayList();
		for(Object scalar: listOfElements){
			if(ifPredicate.apply(scalar)){
				new_transformed_scalars.add(ifFunction.apply(scalar));
				new_original_scalars.add(scalar);
			}else{
				new_transformed_scalars.add(elseFunction.apply(scalar));
				new_original_scalars.add(scalar);
			}
		}
		transformedElements = new_transformed_scalars;
		originalElements = new_original_scalars;
		isBeforeFor = false;
		return this;
	}
	
	protected ScalarComprehension<O> forEach(Object scalar0,Object scalar1,Object... scalars){
		ArrayList<Object> scalarsList = Helpers.newArrayList(scalar0, scalar1, scalars);
		return forEach(scalarsList);
	}
	
	protected ScalarComprehension<O> when(Predicate<Object> scalarPredicate){
		if(isBeforeFor){
			ifPredicate = scalarPredicate;
		}else{
			ArrayList<O> new_transformed_scalars = new ArrayList<O>();
			for(tuple tuple : x.enumerate(originalElements)){
				int index = (int)(tuple.get(0));
				Object scalar = tuple.get(1);
				if(scalarPredicate.apply(scalar)){
					new_transformed_scalars.add(((ArrayList<O>)transformedElements).get(index));
				}
			}
			transformedElements = new_transformed_scalars;
		}
		return this;
	}
	
	@SuppressWarnings("unchecked")
	protected ScalarComprehension<O> unless(Predicate<?> scalarPredicate){
		return when(x.NOT((Predicate<Object>)scalarPredicate));
	}
	
    public Iterator<O> iterator(){
    	return transformedElements.iterator();
    }

}