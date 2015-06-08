package com.wantedtech.common.xpresso.functional;

import java.util.ArrayList;

import com.wantedtech.common.xpresso.experimental.helpers.Helpers;

/**
 * ParametrizedPredicate is an abstract class that extends a {@link Predicate}
 * with a possibility to take a supplementary parameter that can be used
 * in the apply method of {@link Predicate}.
 *
 * Example 1: @see x.in(Iterable) in
 *                   
 */
public abstract class ParametrizedPredicate<E> implements Predicate<E>{
	public ArrayList<Object> params = new ArrayList<Object>();
	public ParametrizedPredicate<E> params(Object value0,Object value1,Object... otherValues){
		params = Helpers.newArrayList(value0,value1,otherValues);
		return this;
	}
	public ParametrizedPredicate<E> params(Object value){
		params = Helpers.newArrayList();
		params.add(value);
		return this;
	}
	public ParametrizedPredicate<E> params(){
		params = Helpers.newArrayList();
		return this;
	}
}