package com.wantedtech.common.xpresso.helpers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.wantedtech.common.xpresso.x;
import com.wantedtech.common.xpresso.functional.Function;
import com.wantedtech.common.xpresso.functional.Predicate;
import com.wantedtech.common.xpresso.types.list;
import com.wantedtech.common.xpresso.types.set;
import com.wantedtech.common.xpresso.types.str;
import com.wantedtech.common.xpresso.types.tuple;
import com.wantedtech.common.xpresso.types.tuples.tuple0;
import com.wantedtech.common.xpresso.types.tuples.tuple1;
import com.wantedtech.common.xpresso.types.tuples.tuple2;
import com.wantedtech.common.xpresso.types.tuples.tuple3;
import com.wantedtech.common.xpresso.types.tuples.tuple4;

public class XUtils {

	public static <O> Function<Object,O> asKey(final Map<?,O> map){
		return new Function<Object,O>() {
			public O apply(Object key) {
					try{
						return ((Map<?,O>)(map)).get(key);	
					}catch(Exception e1){
						throw new IllegalArgumentException("asKeyOn could not interpret the input object as a container of values.");
					}
			}
		};
	}
	
	public static Function<Object,String> joinOn(final String separator){
		return new Function<Object,String>() {
			public String apply(Object iterable) {
				if(iterable instanceof String && separator.equals("")){
					return (String)iterable;
				}
				try{
					return x.String(separator).join((Iterable<?>)iterable);	
				}catch(Exception e){
					throw new IllegalArgumentException("Could not interpret the input object as an Iterable.");
				}
			}
		};
	}
	
	@SafeVarargs
	public static <I,O> Function<I,O> chain(final Function<Object,?>... functions){
		return new Function<I,O>(){
			@SuppressWarnings("unchecked")
			@Override
			public O apply(I value) {
				Object newValue = x.doNothing.apply(value);
				for (Function<Object,?> func : functions) {
					newValue = func.apply(newValue);
				}
				return (O)newValue;
			}
		};
	}
	
	@SafeVarargs
	public static <T> Iterable<T> chain(final Iterable<T> iterable0, final Iterable<T> iterable1, final Iterable<T>... iterables){
		Iterable<T> generator = new Iterable<T>(){
			public Iterator<T> iterator(){
				final ArrayList<Iterator<T>> iterators = new ArrayList<Iterator<T>>();
				iterators.add(iterable0.iterator());
				iterators.add(iterable1.iterator());
				int i = 2;
				for(Iterable<T> iterable : iterables){
					iterators.add(iterable.iterator());
					i++;
				}
				final int numberOfInputIters = i;
				return new Iterator<T>(){
					int currentIter = 0;
					@Override
					public boolean hasNext() {
						if(currentIter < numberOfInputIters){
							if(iterators.get(currentIter).hasNext()){
								return true;
							}
						}
						return false;
					}

					@Override
					public T next() {
						currentIter++;
						return iterators.get(currentIter).next();
					}
					
					@Override
					public void remove() {

					}
					
				};
			}
		};
		return generator;
	}
	
	@SafeVarargs
	public static Iterable<String> chain(final String string0,final String string1,final String... otherStrings){
		String[] stingArr = otherStrings;
		str[] strArr = new str[stingArr.length];
		int counter = 0;
		for(String string : otherStrings){
			strArr[counter] = x.str(string);
			counter++;
		}
		return chain(x.str(string0),x.str(string1),strArr);
	}
	
	public static <T> tuple tupleOf(list<T> lst) {
		switch (x.len(lst)) {
			case 0:
				return new tuple0();
			case 1:
				return x.tuple(lst.get(0));
			case 2:
				return x.tuple(lst.get(0), lst.get(1));
			case 3:
				return x.tuple(lst.get(0), lst.get(1), lst.get(2));
			case 4:
			default:
				return x.tuple(lst.get(0), lst.get(1), lst.get(2), lst.get(3));
		}
    }
	
	public static int len(Object value){
		if(value instanceof Iterable<?>){
			int counter = 0;
			for(@SuppressWarnings("unused") Object element : (Iterable<?>)value){
				counter++;
			}
			return counter;
		}
		if(value instanceof String){
			return ((String)value).length();
		}
		if(value instanceof Number){
			return 0;
		}
		if(value instanceof Boolean){
			return 0;
		}
		if(value instanceof Lengthful){
			return ((Lengthful)value).len();
		}
		if(value instanceof Map<?,?>){
			return ((Map<?,?>)value).size();
		}
		try{
			return (Integer)(value.getClass().getMethod("length").invoke(value));
		}catch(Exception e){
			//e.printStackTrace();
			
		}
		try{
			return ((Object[])value).length;
		}catch(Exception e){
			//e.printStackTrace();
		}
		try{
			return (Integer)(value.getClass().getField("length").getInt(value));
		}catch(Exception e){
			//e.printStackTrace();
		}
		try{
			return (Integer)(value.getClass().getMethod("size").invoke(value));
		}catch(Exception e){
			
		}
		try{
			return (Integer)(value.getClass().getMethod("len").invoke(value));
		}catch(Exception e){
			
		}
		return 0;
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends Number> T avg(Iterable<T> iterable){
		x.assertNotEmpty(iterable);
		double result = (x.<T>reduce(x.<T>add(), iterable)).doubleValue()/x.len(iterable);
		Class<?> cls = iterable.iterator().next().getClass();
		if(cls.equals(Double.class)){
			return (T)(Object)result;
		}else if(cls.equals(Float.class)){
			return (T)(Object)result;
		}else{
			return (T)(Object)(int)Math.round(result);
		}
	}
	
	public static Iterable<Integer> count(final int min,final int max,final int step){
		Iterable<Integer> generator = new Iterable<Integer>(){
			public Iterator<Integer> iterator(){
				return new Iterator<Integer>(){
					int currentValue = min-step;
					@Override
					public boolean hasNext() {
						if(currentValue < max-1){
							return true;
						}
						return false;
					}

					@Override
					public Integer next() {
						currentValue += step;
						return currentValue;
					}
					
					@Override
					public void remove() {

					}
					
				};
			}
		};
		return generator;
	}
	
	public static Iterable<Integer> countFrom(final int min){
		Iterable<Integer> generator = new Iterable<Integer>(){
			public Iterator<Integer> iterator(){
				return new Iterator<Integer>(){
					int currentValue = min-1;
					@Override
					public boolean hasNext() {
						return true;
					}

					@Override
					public Integer next() {
						currentValue += 1;
						return currentValue;
					}
					
					@Override
					public void remove() {

					}
					
				};
			}
		};
		return generator;
	}
	
	public static Iterable<Double> countReal(final double min,final double max,final double step){
		Iterable<Double> generator = new Iterable<Double>(){
			public Iterator<Double> iterator(){
				return new Iterator<Double>(){
					double currentValue = min-step;
					@Override
					public boolean hasNext() {
						if(currentValue < max-1){
							return true;
						}
						return false;
					}

					@Override
					public Double next() {
						currentValue += step;
						return currentValue;
					}
					
					@Override
					public void remove() {

					}
					
				};
			}
		};
		return generator;
	}
	
	public static Iterable<Integer> countTo(int max){
		return count(0,max,1);
	}
	
	public static <T> Iterable<Integer> count(final Iterable<T> iterable, final int step){
		Iterable<Integer> generator = new Iterable<Integer>(){
			public Iterator<Integer> iterator(){
				return new Iterator<Integer>(){
					Iterator<T> iter = iterable.iterator();
					int currentValue = 0-step;
					@Override
					public boolean hasNext() {
						return iter.hasNext();
					}

					@Override
					public Integer next() {
						currentValue += step;
						for(Integer i : countTo(step)){
							currentValue+=i;
							iter.next();
						}
						return currentValue;
					}
					
					@Override
					public void remove() {

					}
					
				};
			}
		};
		return generator;
	}
	
	public static <I,O> Iterable<O> map(Function<Object,O> function, Iterable<I> iterable){
		if(iterable instanceof set<?>){
			set<O> newSet = new set<O>();
			for (I element : iterable){
				newSet.put(function.apply(element));
			}
			return newSet;
		}else{
			list<O> newList = new list<O>();
			for (I element : iterable){
				newList.append(function.apply(element));
			}
			return newList;
		}
	}
	
	public static <I,O> O reduce(Function<Object,O> function, Iterable<I> iterable, O initializer){
		x.assertNotNull(initializer);
		if(x.len(iterable) == 0) {
			return initializer;
		}
		Iterator<I> iter = iterable.iterator();
		O output = function.apply(tuple2.valueOf(initializer, iter.next()));
		while(iter.hasNext()){
			output = function.apply(tuple2.valueOf(output, iter.next()));
		}
		return output;
	}
	
	public static <I> I reduce(Function<tuple2<I,I>,I> function, Iterable<I> iterable){
		if(x.len(iterable) == 1) {
			return iterable.iterator().next();
		}
		Iterator<I> iter = iterable.iterator();
		I output = function.apply(tuple2.valueOf(iter.next(), iter.next()));
		while(iter.hasNext()){
			output = function.apply(tuple2.valueOf(output, iter.next()));
		}
		return output;
	}
	
	public static <T> Iterable<T> filter(Predicate<Object> predicate, Iterable<T> iterable){
		if(iterable instanceof set<?>){
			set<T> newSet = new set<T>();
			for (T element : iterable){
				if(predicate.apply(element)){
					newSet.put(element);	
				}
			}
			return newSet;
		}else{
			list<T> newList = new list<T>();
			for (T element : iterable){
				if(predicate.apply(element)){
					newList.append(element);	
				}
			}
			return newList;
		}
	}
	
	@SafeVarargs
	public static <T> set<T> union(Iterable<T>... iterables){
		set<T> result = x.set(iterables[0]);
		if (iterables.length > 1){
			for (int i = 1;i<iterables.length;i++){
				for (T element : iterables[i]){
					result.put(element);
				}
			}
		}
		return result;
	}
	
	public static <T> Iterable<tuple2<Integer,T>> enumerate(final Iterable<T> iterable, final int startCount){
		Iterable<tuple2<Integer,T>> generator = new Iterable<tuple2<Integer,T>>(){
			public Iterator<tuple2<Integer,T>> iterator(){
				return new Iterator<tuple2<Integer,T>>(){
					int currentCount = startCount-1;
					Iterator<T> iter = iterable.iterator();
					@Override
					public boolean hasNext() {
						return iter.hasNext();
					}
					@Override
					public tuple2<Integer,T> next() {
						currentCount++;
						return tuple2.valueOf(currentCount, iter.next());
					}
					
					@Override
					public void remove() {

					}
					
				};
			}
		};
		return generator;
	}
	
	public static <T> Iterable<T> cycle(final Iterable<T> iterable, final Integer maxCount){
		Iterable<T> generator = new Iterable<T>(){
			public Iterator<T> iterator(){
				return new Iterator<T>(){
					Iterator<T> iter = iterable.iterator();
					int currentCount = 0;
					@Override
					public boolean hasNext() {
						if(currentCount < maxCount || maxCount == null){
							if (!iter.hasNext()){
								iter = iterable.iterator();
							}
							return iter.hasNext();	
						}
						return false;
					}
					@Override
					public T next() {
						currentCount++;
						return iter.next();
					}
					
					@Override
					public void remove() {

					}
					
				};
			}
		};
		return generator;
	}
	
	public static <T> list<list<T>> divise(Iterable<T> iterable, int numberOfPieces) {
		list<T> iterableAsList = x.list(iterable);
		if (numberOfPieces > x.len(iterableAsList)) {
			return divise(iterableAsList, x.len(iterableAsList));
		}
		int avg = (int)(x.len(iterableAsList) / (double)(numberOfPieces));
		list<list<T>> out = x.list();
		double last = 0.0;
		
		while (last < x.len(iterableAsList)) {
			out.append(iterableAsList.slice((int)last,(int)(last + avg)));
			last += avg;
		}
		return out;
	}
	
	public static <T> Iterable<T> repeat(final T value, final Integer maxCount){
		Iterable<T> generator = new Iterable<T>(){
			public Iterator<T> iterator(){
				return new Iterator<T>(){
					int currentCount = 0;
					@Override
					public boolean hasNext() {
						if(currentCount < maxCount || maxCount == null){
							return true;	
						}
						return false;
					}
					@Override
					public T next() {
						currentCount++;
						return value;
					}
					
					
					@Override
					public void remove() {

					}
					
				};
			}
		};
		return generator;
	}
	
	public static Random Random = new Random();
	
	public static <T> Iterable<T> shuffle(Iterable<T> iterable) {
        int size = x.len(iterable);
        list<T> lst = x.list(iterable);
		if (size < 5) {
			for (int i=size; i>1; i--)
				lst.swap(i-1, Random.nextInt(i));
		} else {
			// Shuffle array
		    for (int i=size; i>1; i--)
		    	lst.swap(i-1, Random.nextInt(i));
		}
		return lst;
	}
	
	@SuppressWarnings({ "unchecked" })
	public static <V,K extends Comparable<K>> Iterable<V> sort(Iterable<V> iterable,Function<Object,K> function,boolean reverse){
		class KeyValue implements Comparable<KeyValue>, Serializable {
			/**
			 * 
			 */
			private static final long serialVersionUID = -6209178251903971368L;
			
			private K key;
			private V value;
			
			public KeyValue(K key, V value){
				this.key = key;
				this.value = value;
			}
			
			public K getKey(){
				return this.key;
			}
			
			public V getValue(){
				return this.value;
			}
			
			public int compareTo(KeyValue keyValue){
				return this.key.compareTo(keyValue.getKey());
			}
			
			@Override
			public String toString(){
				return this.key.toString() + '~' + this.value.toString();
			}
		}

		List<KeyValue> keyValues = new ArrayList<KeyValue>();
		for (V element: iterable){
			if(function != null){
				keyValues.add(new KeyValue(function.apply(element),element));	
			}else{
				keyValues.add(new KeyValue((K)element,element));
			}
		}
		
		Collections.sort(keyValues);
		
		if(reverse){
			Collections.reverse(keyValues);
		}
		ArrayList<V> resultList = new ArrayList<V>();
		for (KeyValue keyValue : keyValues){
			resultList.add(keyValue.getValue());
		}
		if(iterable instanceof list<?>){
			return x.list(resultList);
		}
		return resultList;
	}
	
	public static <T> Iterable<T> takeWhile(final Predicate<Object> predicate, final Iterable<T> iterable){
		return new Iterable<T>() {

			@Override
			public Iterator<T> iterator() {
				return new Iterator<T>() {

					Iterator<T> inputIter = iterable.iterator();
					
					T next = null;
					
					@Override
					public boolean hasNext() {
						next = inputIter.next();
						return inputIter.hasNext() && predicate.apply(next);
					}

					@Override
					public T next() {
						return next;
					}	
					
					@Override
					public void remove() {

					}
					
				};
			}
		};
	}
	
	public static <T> Iterable<T> dropWhile(final Predicate<Object> predicate, final Iterable<T> iterable){
		return new Iterable<T>() {

			@Override
			public Iterator<T> iterator() {
				return new Iterator<T>() {

					Iterator<T> inputIter = iterable.iterator();
					
					Predicate<Object> pred = predicate;
					
					T next = null;
					
					@Override
					public boolean hasNext() {
						while (inputIter.hasNext()) {
							next = inputIter.next();
							if (pred.apply(next)) {
								continue;
							} else {
								pred = x.FALSE;
							}
						}
						return false;
					}

					@Override
					public T next() {
						return next;
					}		
					
					@Override
					public void remove() {

					}
					
				};
			}
		};
	}
	
	public static <T> boolean contains(Iterable<T> iterable, T value){
		if (iterable instanceof Collection<?>) {
			  return ((Collection<?>)iterable).contains(value);
		}else{
			for (T val : iterable){
				if (val.equals(value)){
					return true;
				}
			}
			return false;
		}
	}
	
	public static <T0> list<tuple> zip(Iterable<T0> iterable0){
		list<tuple> result = x.list();
		try{
			for(tuple index__value : x.enumerate(iterable0)){
				@SuppressWarnings("unchecked")
				T0 value = (T0)index__value.get(1);
				result.append(x.tuple(value));
			}	
		}catch(Exception e){
			
		}
		return result;
	}
	
	public static <T0,T1> list<tuple> zip(Iterable<T0> iterable0,Iterable<T1> iterable1){
		list<tuple> result = x.list();
		list<T1> list1 = x.list(iterable1);
		try{
			for(tuple index__value : x.enumerate(iterable0)){
				int index = (int)index__value.get(0);
				@SuppressWarnings("unchecked")
				T0 value = (T0)index__value.get(1);
				result.append(x.tuple(value,list1.get(index)));
			}	
		}catch(Exception e){
			
		}
		return result;
	}
	
	public static <T0,T1,T2> list<tuple> zip(Iterable<T0> iterable0,Iterable<T1> iterable1,Iterable<T2> iterable2){
		list<tuple> result = x.list();
		list<T1> list1 = x.list(iterable1);
		list<T2> list2 = x.list(iterable2);
		try{
			for(tuple index__value : x.enumerate(iterable0)){
				int index = (int)index__value.get(0);
				@SuppressWarnings("unchecked")
				T0 value = (T0)index__value.get(1);
				result.append(x.tuple(value,list1.get(index),list2.get(index)));
			}	
		}catch(Exception e){
			
		}
		return result;
	}
	
	public static <T0,T1,T2,T3> list<tuple> zip(Iterable<T0> iterable0,Iterable<T1> iterable1,Iterable<T2> iterable2,Iterable<T3> iterable3){
		list<tuple> result = x.list();
		list<T1> list1 = x.list(iterable1);
		list<T2> list2 = x.list(iterable2);
		list<T3> list3 = x.list(iterable3);
		try{
			for(tuple index__value : x.enumerate(iterable0)){
				int index = (int)index__value.get(0);
				@SuppressWarnings("unchecked")
				T0 value = (T0)index__value.get(1);
				result.append(x.tuple(value,list1.get(index),list2.get(index),list3.get(index)));
			}	
		}catch(Exception e){
			
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public static <T0> tuple1<list<T0>> unzip(Iterable<tuple> iterable,Class<T0> class0){
		list<T0> list0 = x.list();
		for (tuple T0__ : iterable){
			list0.append((T0)T0__.get(0));
		}
		return tuple1.valueOf(list0);
	}
	
	public static <T0,T1> tuple2<list<T0>,list<T1>> unzip(Iterable<tuple> iterable,Class<T0> class0,Class<T1> class1){
		list<T0> list0 = x.list();
		list<T1> list1 = x.list();
		for (tuple T0__T1 : iterable){
			list0.append((T0)T0__T1.get(0));
			list1.append((T1)T0__T1.get(1));
		}
		return tuple2.valueOf(list0,list1);
	}
	
	public static <T0,T1,T2> tuple3<list<T0>,list<T1>,list<T2>> unzip(Iterable<tuple> iterable,Class<T0> class0,Class<T1> class1,Class<T2> class2){
		list<T0> list0 = x.list();
		list<T1> list1 = x.list();
		list<T2> list2 = x.list();
		for (tuple T0__T1__T2 : iterable){
			list0.append((T0)T0__T1__T2.get(0));
			list1.append((T1)T0__T1__T2.get(1));
			list2.append((T2)T0__T1__T2.get(2));
		}
		return tuple3.valueOf(list0,list1,list2);
	}
	
	@SuppressWarnings("unchecked")
	public static <T0,T1,T2,T3> tuple4<list<T0>,list<T1>,list<T2>,list<T3>>  unzip(Iterable<tuple> iterable,Class<T0> class0,Class<T1> class1,Class<T2> class2,Class<T3> class3){
		list<T0> list0 = x.list();
		list<T1> list1 = x.list();
		list<T2> list2 = x.list();
		list<T3> list3 = x.list();
		for (tuple T0__T1__T2__T3 : iterable){
			list0.append((T0)T0__T1__T2__T3.get(0));
			list1.append((T1)T0__T1__T2__T3.get(1));
			list2.append((T2)T0__T1__T2__T3.get(2));
			list3.append((T3)T0__T1__T2__T3.get(3));
		}
		return tuple4.valueOf(list0,list1,list2,list3);
	}
	
	public static boolean isTrue(Object value){
		if (value == null) {
			return false;
		}
		try{
			if((Boolean)value == false){
				return false;
			}	
		}catch(Exception e){
			
		}
		if(value instanceof Truthful){
			return ((Truthful)value).isTrue();
		}
		if(value instanceof Iterable<?> && len(value) == 0){
			return false;
		}
		if(value instanceof tuple && len(value) == 0){
			return false;
		}
		if(value instanceof String && len(value) == 0){
			return false;
		}
		try{
			if((Integer)value <= 0){
				return false;
			}	
		}catch(Exception e){
			
		}
		try{
			if((Double)value <= 0.0){
				return false;
			}	
		}catch(Exception e){
			
		}
		try{
			if((Float)value <= 0.0){
				return false;
			}	
		}catch(Exception e){
			
		}
		return true;
	}
	
	public static <T> Function<Object,T> invoke(final String methodName, final Object... methodParams){
		return new Function<Object,T>() {
			public T apply(Object obj) throws IllegalArgumentException{
				
				list<Object> methodParamsList;
				if(methodParams != null) {
					methodParamsList = x.list(methodParams);					
				} else{
					methodParamsList = x.list();
				}

				list<Class<?>> methodParamsTypes = x.list();
				for (tuple item : x.enumerate(methodParamsList)){
					item.name("idx","param");
					Class<?> currentType = item.get("param").getClass();
					if (currentType.equals(Integer.class)){
						currentType = int.class;
					}else if(currentType.equals(Double.class)){
						currentType = double.class;
					}else if(currentType.equals(Long.class)){
						currentType = long.class;
					}else if(currentType.equals(Float.class)){
						currentType = float.class;
					}
					methodParamsTypes.append(currentType); 
				}
				try{
					if(x.len(methodParams) > 0){
						Class<?>[] typesArr = new Class<?>[x.len(methodParamsTypes)];
						typesArr = methodParamsTypes.toArrayList().toArray(typesArr);
						return (T)(obj.getClass().getMethod(methodName, typesArr).invoke(obj, methodParamsList.toArrayList().toArray()));	
					}else{
						return (T)(obj.getClass().getMethod(methodName).invoke(obj));
					}
				}catch(Exception e){
					throw new IllegalArgumentException();
				}
			}
		};
	}
	
	public static <T extends Number> Function<tuple2<T,T>, T> add(){
		return new Function<tuple2<T,T>,T>() {
			public T apply(tuple2<T,T> values) throws IllegalArgumentException{
				T op1 = values.value0;
				T op2 = values.value1;

			    if( !(op1 instanceof Number) || !(op2 instanceof Number) ){
			        throw new IllegalArgumentException("Invalid operands for mathematical operator [+]");
			    }

			    if(op1 instanceof Double || op2 instanceof Double){
			        return (T)(Object)(((Number)op1).doubleValue() + ((Number)op2).doubleValue());
			    }

			    if(op1 instanceof Float || op2 instanceof Float){
			        return (T)(Object)(((Number)op1).floatValue() + ((Number)op2).floatValue());
			    }

			    if(op1 instanceof Long || op2 instanceof Long){
			        return (T)(Object)(((Number)op1).longValue() + ((Number)op2).longValue());
			    }

			    return (T)(Object)(((Number)op1).intValue() + ((Number)op2).intValue());
			}
		};
	}
	
	public static <T extends Number> Function<tuple2<T,T>, T> avg(){
		return new Function<tuple2<T,T>,T>() {
			public T apply(tuple2<T,T> values) throws IllegalArgumentException{
				T op1 = values.value0;
				T op2 = values.value1;

			    if( !(op1 instanceof Number) || !(op2 instanceof Number) ){
			        throw new IllegalArgumentException("Invalid operands for mathematical operator [+]");
			    }

			    if(op1 instanceof Double || op2 instanceof Double){
			        return (T)(Object)((((Number)op1).doubleValue() + ((Number)op2).doubleValue())/2);
			    }

			    if(op1 instanceof Float || op2 instanceof Float){
			        return (T)(Object)((((Number)op1).floatValue() + ((Number)op2).floatValue())/2);
			    }

			    if(op1 instanceof Long || op2 instanceof Long){
			        return (T)(Object)((((Number)op1).longValue() + ((Number)op2).longValue())/2);
			    }

			    return (T)(Object)((((Number)op1).intValue() + ((Number)op2).intValue())/2);
			}
		};
	}

}
