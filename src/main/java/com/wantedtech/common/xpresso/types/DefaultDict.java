package com.wantedtech.common.xpresso.types;

import com.wantedtech.common.xpresso.x;
import com.wantedtech.common.xpresso.helpers.ObjectFactory;

public class DefaultDict<T> extends dict<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1888392382698596363L;

	T defaultValue;
	
	@SuppressWarnings("unchecked")
	public DefaultDict(Class<T> valueType){
		defaultValue = ObjectFactory.createValueOfType(valueType);
	}
	
	public T get(String key){
		if(x.String(key).notIn(this)){
			this.setAt(key).value(defaultValue);
		}
		return defaultValue;
	}
	
}
