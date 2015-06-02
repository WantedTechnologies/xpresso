package com.wantedtech.common.xpresso;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ObjectFactory {
	@SuppressWarnings("unchecked")
	public static <T> T createValueOfType(Class<T> valueType){
		try {
			if(valueType.getSuperclass().equals(Number.class)){
				return valueType.getConstructor(String.class).newInstance("0");
			}else if(valueType.equals(Boolean.class)){
				return valueType.getConstructor(String.class).newInstance("false");
			}else if(valueType.equals(Character.class)){
				Constructor<?>[] constrs = valueType.getConstructors();
				constrs[0].newInstance('\u0000');
				return (T)(constrs[0].newInstance('\u0000'));
			}else{
				return valueType.newInstance();
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
			throw new IllegalArgumentException("Could not generate default value for valueType");
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			throw new IllegalArgumentException("Could not generate default value for valueType");
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			throw new IllegalArgumentException("Could not generate default value for valueType");
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			throw new IllegalArgumentException("Could not generate default value for valueType");
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			throw new IllegalArgumentException("Could not generate default value for valueType");
		} catch (SecurityException e) {
			e.printStackTrace();
			throw new IllegalArgumentException("Could not generate default value for valueType");
		}
	}
}
