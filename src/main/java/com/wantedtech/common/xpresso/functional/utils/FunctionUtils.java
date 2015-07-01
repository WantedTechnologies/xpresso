package com.wantedtech.common.xpresso.functional.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.wantedtech.common.xpresso.x;
import com.wantedtech.common.xpresso.functional.Function;
import com.wantedtech.common.xpresso.types.tuple;
import com.wantedtech.common.xpresso.types.tuples.tuple1;
import com.wantedtech.common.xpresso.types.tuples.tuple2;
import com.wantedtech.common.xpresso.types.tuples.tuple3;
import com.wantedtech.common.xpresso.types.tuples.tuple4;

public class FunctionUtils {

	public static <O> Function<tuple,O> Function(Class<?> theClass, String methodName) {
        Method method = null;
		try {
			method = theClass.getMethod(methodName, new Class<?>[] {});
		} catch (NoSuchMethodException | SecurityException e) {
			Method[] methods = theClass.getMethods();
			int goodMethodsCounter = 0;
			for (Method m : methods) {
				if (m.getName().equals(methodName)) {
					method = m;
					goodMethodsCounter++;
					if (goodMethodsCounter > 1) {
						throw new RuntimeException("More than one method with name " + methodName + " in the class " + theClass.getName() + ". In this case use the x.Function with the method's input type class objects as parameters.");
					}
				}
			}
		}

		final Method theMethod = method;
		
        return new Function<tuple,O>() {
        	@SuppressWarnings("unchecked")
			public O apply(tuple input) {
        		Class<?>[] types = theMethod.getParameterTypes();
        		try {
        			switch (x.len(types)) {
        				case 0:
        					return (O)theMethod.invoke(null);
        				case 1:
        					return (O)theMethod.invoke(null, types[0].cast(input.get(0)));
        				case 2:
        					return (O)theMethod.invoke(null, types[0].cast(input.get(0)), types[1].cast(input.get(1)));
        				case 3:
        					return (O)theMethod.invoke(null, types[0].cast(input.get(0)), types[1].cast(input.get(1)), types[2].cast(input.get(2)));
        				case 4:
        				default:
        					return (O)theMethod.invoke(null, types[0].cast(input.get(0)), types[1].cast(input.get(1)), types[2].cast(input.get(2)), types[3].cast(input.get(3)));
        			}
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | IndexOutOfBoundsException e) {
					e.printStackTrace();
					throw new RuntimeException("Tried to interpret the input " + input + " as a tuple" + x.len(types) + " of types " + x.list(types) + " but did not succeed.");
				}
        	}
        };

	}
	
	public static <I,O> Function<I,O> Function(Class<?> theClass, String methodName, Class<?> parameterType) {
		
        final Method method;
		try {
			method = theClass.getMethod(methodName, new Class<?>[] {parameterType});
		} catch (NoSuchMethodException | SecurityException e) {
			throw new RuntimeException(e);
		}

        return new Function<I,O>() {
        	@SuppressWarnings("unchecked")
			public O apply(I input) {
        		try {
					Object o = method.invoke(null, input);
					return (O)o;
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					throw new RuntimeException(e);
				}
        	}
        };

	}
	
	public static <I0,I1,O> Function<tuple2<I0,I1>,O> Function(Class<?> theClass, String methodName, Class<?> parameterType0, Class<?> parameterType1) {
		
        final Method method;
		try {
			method = theClass.getMethod(methodName, new Class<?>[] {parameterType0, parameterType1});
		} catch (NoSuchMethodException | SecurityException e) {
			throw new RuntimeException(e);
		}

        return new Function<tuple2<I0,I1>,O>() {
        	@SuppressWarnings("unchecked")
			public O apply(tuple2<I0,I1> input) {
        		try {
					Object o = method.invoke(null, input.key, input.value);
					return (O)o;
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					throw new RuntimeException(e);
				}
        	}
        };

	}
	
	public static <I0,I1,I2,O> Function<tuple3<I0,I1,I2>,O> Function(Class<?> theClass, String methodName, Class<?> parameterType0, Class<?> parameterType1, Class<?> parameterType2) {
		
        final Method method;
		try {
			method = theClass.getMethod(methodName, new Class<?>[] {parameterType0, parameterType1, parameterType2});
		} catch (NoSuchMethodException | SecurityException e) {
			throw new RuntimeException(e);
		}

        return new Function<tuple3<I0,I1,I2>,O>() {
        	@SuppressWarnings("unchecked")
			public O apply(tuple3<I0,I1,I2> input) {
        		try {
					Object o = method.invoke(null, input.left, input.middle, input.right);
					return (O)o;
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					throw new RuntimeException(e);
				}
        	}
        };

	}
	
	public static <I0,I1,I2,I3,O> Function<tuple4<I0,I1,I2,I3>,O> Function(Class<?> theClass, String methodName, Class<?> parameterType0, Class<?> parameterType1, Class<?> parameterType2, Class<?> parameterType3) {
		
        final Method method;
		try {
			method = theClass.getMethod(methodName, new Class<?>[] {parameterType0, parameterType1, parameterType2, parameterType3});
		} catch (NoSuchMethodException | SecurityException e) {
			throw new RuntimeException(e);
		}

        return new Function<tuple4<I0,I1,I2,I3>,O>() {
        	@SuppressWarnings("unchecked")
			public O apply(tuple4<I0,I1,I2,I3> input) {
        		try {
					Object o = method.invoke(null, input.value0, input.value1, input.value2, input.value3);
					return (O)o;
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					throw new RuntimeException(e);
				}
        	}
        };

	}

}
