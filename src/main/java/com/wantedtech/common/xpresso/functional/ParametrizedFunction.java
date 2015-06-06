package com.wantedtech.common.xpresso.functional;

import java.util.ArrayList;

import com.wantedtech.common.xpresso.types.list;

/**
 * ParametrizedFunction is an abstract class that extends a Function
 * with a possibility to take a supplementary parameter that can be used
 * in the apply method of Function.
 *
 * Example 1: @see x#joinOn(String) joinOn
 *                   
 */
public abstract class ParametrizedFunction<E,T> implements Function<E,T>{
	public ArrayList<Object> params = new ArrayList<Object>();
	public ParametrizedFunction<E,T> params(Object value0,Object value1,Object... otherValues){
		params = list.newArrayList(value0,value1,otherValues);
		return this;
	}
	public ParametrizedFunction<E,T> params(){
		params = list.newArrayList();
		return this;
	}
	public ParametrizedFunction<E,T> params(Object value){
		params = list.newArrayList();
		params.add(value);
		return this;
	}
}
