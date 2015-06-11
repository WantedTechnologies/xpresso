package com.wantedtech.common.xpresso.functional;

import java.util.ArrayList;

import com.wantedtech.common.xpresso.x;
import com.wantedtech.common.xpresso.experimental.helpers.Helpers;
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
	public list<Object> params = x.list();
	public ParametrizedFunction<E,T> params(Iterable<Object> values){
		params = x.list(values);
		return this;
	}
	public ParametrizedFunction<E,T> params(){
		params = x.list();
		return this;
	}
}
