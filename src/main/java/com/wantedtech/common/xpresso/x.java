package com.wantedtech.common.xpresso;

import java.io.IOException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.lang.Number;

import com.wantedtech.common.xpresso.comprehension.ComprehensionFactory;
import com.wantedtech.common.xpresso.comprehension.ScalarComprehensionStart;
import com.wantedtech.common.xpresso.comprehension.Tuple1ComprehensionStart;
import com.wantedtech.common.xpresso.comprehension.Tuple2ComprehensionStart;
import com.wantedtech.common.xpresso.functional.Function;
import com.wantedtech.common.xpresso.functional.Predicate;
import com.wantedtech.common.xpresso.lambda.LambdaFunction;
import com.wantedtech.common.xpresso.lambda.LambdaPredicate;
import com.wantedtech.common.xpresso.regex.Regex;
import com.wantedtech.common.xpresso.types.DefaultDict;
import com.wantedtech.common.xpresso.types.dict;
import com.wantedtech.common.xpresso.types.HappyFile;
import com.wantedtech.common.xpresso.types.list;
import com.wantedtech.common.xpresso.types.set;
import com.wantedtech.common.xpresso.types.str;
import com.wantedtech.common.xpresso.types.Bag;
import com.wantedtech.common.xpresso.types.tuple.tuple;
import com.wantedtech.common.xpresso.types.tuple.tuple1;
import com.wantedtech.common.xpresso.types.tuple.tuple2;
import com.wantedtech.common.xpresso.types.tuple.tuple3;
import com.wantedtech.common.xpresso.types.tuple.tuple4;

// DONE
/*
 * slicing for list, strings (via x.String), and str (supporting negative steps)
 * 
 * Regex with Function and dict as replacements
 * 
 * lambda expressions
 * 
 * dict, set, defaultdict, list, tuple
 * 
 * flattening list
 * 
 * list slice assignement:
 * a = [1, 2, 3, 4, 5]
 *>>> a[2:3] = [0, 0]
 *>>> a
 *[1, 2, 0, 0, 4, 5]
 *>>> a[1:1] = [8, 9]
 *>>> a
 *[1, 8, 9, 2, 0, 0, 4, 5]
 *
 * Sclicer object
 *
 * inverted dict
 *
 * Largest and smallest elements (x.largestN, x.slmallestN)
 *
 * n-grams for lists
 *
 * str in String, x.String in String, str in str, x.String in str
 *
 * bag
 * 
 * itertools cycle
 *
 * itertools repeat
 */

//TO DO TO DO TO DO

//dict comprehension

//itertools.product
//itertools.combinations
//itertools.permutations

//tuple in list
//tuple in set

//phrase tokenize
//word tokenize
//pos


public class x {
	
	public static StringMore String(String string){
		return new StringMore(string);
	}
	public static StringMore String(str str){
		return new StringMore(str.toString());
	}
	public static StringStatic String(){
		return new StringStatic();
	}
	
	public enum PredicateJoinType {
	    AND, OR, NOT 
	}
	
	public static abstract class ParametrizedFunction<E,T> implements Function<E,T>{
		ArrayList<Object> params = new ArrayList<Object>();
		public ParametrizedFunction<E,T> params(Object arg0,Object arg1,Object... args){
			params = list.newArrayList(arg0,arg1,args);
			return this;
		}
		public ParametrizedFunction<E,T> params(){
			params = list.newArrayList();
			return this;
		}
		public ParametrizedFunction<E,T> params(Object arg0){
			params = list.newArrayList();
			params.add(arg0);
			return this;
		}
	}
	
	public static abstract class ParametrizedPredicate<E> implements Predicate<E>{
		ArrayList<Object> params = new ArrayList<Object>();
		public ParametrizedPredicate<E> params(Object arg0,Object arg1,Object... args){
			params = list.newArrayList(arg0,arg1,args);
			return this;
		}
		public ParametrizedPredicate<E> params(Object arg0){
			params = list.newArrayList();
			params.add(0);
			return this;
		}
		public ParametrizedPredicate<E> params(){
			params = list.newArrayList();
			return this;
		}
	}
	
	public interface FunctionChain<I,O> extends Function<I,O>{}
	
	public interface ComplexFunction<E,T> extends Function<E,T>{}
	
	public interface ComplexPredicate<E> extends Predicate<E>{
		public ComplexPredicate<E> joinAs(PredicateJoinType type);
	}

	@SafeVarargs
	public static <T> LambdaPredicate<T> lambdaP(String lambdaExpression,Predicate<Object>... predicates){
		return new LambdaPredicate<T>(lambdaExpression,predicates);
	}
	public static <T> LambdaPredicate<T> lambdaP(String lambdaExpression){
		return new LambdaPredicate<T>(lambdaExpression);
	}
	
	@SafeVarargs
	public static <I,O> LambdaFunction<I,O> lambdaF(String lambdaExpression,Function<Object,?>... functions){
		return new LambdaFunction<I,O>(lambdaExpression,functions);
	}
	public static <I,O> LambdaFunction<I,O> lambdaF(String lambdaExpression){
		return new LambdaFunction<I,O>(lambdaExpression);
	}
	
	@SafeVarargs
	public static <I,O> FunctionChain<I,O> chainOf(Function<Object,?>... functions){
		return newFunctionChain(x.listOf(functions));
	}
	public static <I,O> FunctionChain<I,O> newFunctionChain(final list<Function<Object,?>> functions){
		return new FunctionChain<I,O>(){
			@SuppressWarnings("unchecked")
			@Override
			public O apply(I value) {
				Object newValue = x.doNothing().apply(value);
				for (Function<Object,?> func : functions) {
					newValue = func.apply(newValue);
				}
				return (O)newValue;
			}
		};
	}

	
	@SafeVarargs
	public static <T> Iterable<T> chainOf(final Iterable<T> iterable0, final Iterable<T> iterable1, final Iterable<T>... iterables){
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
				};
			}
		};
		return generator;
	}
	
	@SafeVarargs
	public static Iterable<String> chainOf(final String string0,final String string1,final String... strings){
		String[] stingArr = strings;
		str[] strArr = new str[stingArr.length];
		int counter = 0;
		for(String string : strings){
			strArr[counter] = x.str(string);
			counter++;
		}
		return chainOf(x.str(string0),x.str(string1),strArr);
	}
	

	
	@SafeVarargs
	public static <T> ComplexPredicate<T> newComplexPredicate(final Predicate<T>... predicates){
		return newComplexPredicate(x.listOf(predicates));
	}
	
	public static <T> ComplexPredicate<T> newComplexPredicate(){
		return newComplexPredicate(x.<T>TRUE());
	}
	
	public static <T> ComplexPredicate<T> newComplexPredicate(final list<Predicate<T>> predicates){
		final list<Predicate<T>> inputPredicates = predicates;
		return new ComplexPredicate<T>() {
			list<Predicate<T>> predicates = inputPredicates;
			PredicateJoinType joinType = PredicateJoinType.AND;
			public ComplexPredicate<T> joinAs(PredicateJoinType type){
				joinType = type;
				return this;
			}
			@Override
			public Boolean apply(T value) {
				boolean result;
				switch (joinType){
					case OR:
						result = true;
						for (Predicate<T> pred : predicates) {
							result = result || pred.apply(value);
						}
						predicates = x.<Predicate<T>>listOf();
						break;
					case NOT:
						result = !predicates.get(0).apply(value);
						break;
					default:
						result = false;
						for (Predicate<T> pred : predicates) {
							result = result && pred.apply(value);
						}
						predicates = x.<Predicate<T>>listOf();
						break;					
				}
				return result;
			}
		};
	}
	
	public static <T0> Function<tuple1<T0>,tuple1<T0>> newTuple1Function(final tuple1<Function<T0,T0>> tupleOfFunctions){
		return new Function<tuple1<T0>,tuple1<T0>>() {
			@Override
			public tuple1<T0> apply(tuple1<T0> input){
				T0 result0 = tupleOfFunctions.value.apply(input.value);
				return tuple1.<T0>valueOf(result0);
			}
		};
	}
	
	public static <T0,T1> Function<tuple2<T0,T1>,tuple2<T0,T1>> newTuple2Function(final tuple2<Function<T0,T0>,Function<T1,T1>> tupleOfFunctions){
		return new Function<tuple2<T0,T1>,tuple2<T0,T1>>() {
			@Override
			public tuple2<T0,T1> apply(tuple2<T0,T1> input){
				T0 result0 = tupleOfFunctions.value0.apply(input.value0);
				T1 result1 = tupleOfFunctions.value1.apply(input.value1);
				return tuple2.<T0,T1>valueOf(result0,result1);
			}
		};
	}
	
	public static <T0,T1,T2> Function<tuple3<T0,T1,T2>,tuple3<T0,T1,T2>> newTuple3Function(final tuple3<Function<T0,T0>,Function<T1,T1>,Function<T2,T2>> tupleOfFunctions){
		return new Function<tuple3<T0,T1,T2>,tuple3<T0,T1,T2>>() {
			@Override
			public tuple3<T0,T1,T2> apply(tuple3<T0,T1,T2> input){
				T0 result0 = tupleOfFunctions.value0.apply(input.value0);
				T1 result1 = tupleOfFunctions.value1.apply(input.value1);
				T2 result2 = tupleOfFunctions.value2.apply(input.value2);
				return tuple3.<T0,T1,T2>valueOf(result0,result1,result2);
			}
		};
	}
	
	public static <T0,T1,T2,T3> Function<tuple4<T0,T1,T2,T3>,tuple4<T0,T1,T2,T3>> newTuple4Function(final tuple4<Function<T0,T0>,Function<T1,T1>,Function<T2,T2>,Function<T3,T3>> tupleOfFunctions){
		return new Function<tuple4<T0,T1,T2,T3>,tuple4<T0,T1,T2,T3>>() {
			@Override
			public tuple4<T0,T1,T2,T3> apply(tuple4<T0,T1,T2,T3> input){
				T0 result0 = tupleOfFunctions.value0.apply(input.value0);
				T1 result1 = tupleOfFunctions.value1.apply(input.value1);
				T2 result2 = tupleOfFunctions.value2.apply(input.value2);
				T3 result3 = tupleOfFunctions.value3.apply(input.value3);
				return tuple4.<T0,T1,T2,T3>valueOf(result0,result1,result2,result3);
			}
		};
	}
		
	//tuple comprehension factory methods
	
	public static Tuple1ComprehensionStart tuple(int index0) {
        return ComprehensionFactory.tuple(index0);
    }
	
	public static Tuple2ComprehensionStart tuple(int index0,int index1) {
        return ComprehensionFactory.tuple(index0,index1);
    }
		
	//tuple factory methods
	public static <T0> tuple tupleOf(T0 value0) {
        return tuple1.valueOf(value0);
    }

    public static <T0, T1> tuple tupleOf(T0 value0, T1 value1) {
        return tuple2.valueOf(value0, value1);
    }

    public static <T0, T1, T2> tuple tupleOf(T0 value0, T1 value1, T2 value2) {
        return tuple3.valueOf(value0, value1, value2);
    }
    
    public static <T0, T1, T2, T3> tuple tupleOf(T0 value0, T1 value1, T2 value2, T3 value3) {
        return tuple4.valueOf(value0, value1, value2, value3);
    }
        
    //creates new string dict
	public static <T> dict<T> dictOf(){
		return new dict<T>();
	}
	
	//creates a string dict constructor from a list of tuples 
	public static <T> dict<T> dictOf(Iterable<tuple> tuples){
		dict<T> dict = new dict<T>();
		dict.update(tuples);
		return dict;
	}
	
	//creates a string dict constructor from a list of tuples 
	@SafeVarargs
	public static <T> dict<T> dictOf(tuple... tuples){
		dict<T> dict = new dict<T>();
		dict.update(tuples);
		return dict;
	}
		
	//creates a string dict constructor from a Map 
	public static <T> dict<T> dict(Map<String,T> map){
		return new dict<T>(map);
	}
	
	//creates a string dict constructor from another dict constructor 
	public static <T> dict<T> dict(dict<T> dict){
		return new dict<T>(dict);
	}
	
	public static <T> DefaultDict<T> defaultDict(Class<T> defaultType){
		return new DefaultDict<T>(defaultType);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> Bag<T> Bag(Iterable<T> iterable) throws IllegalArgumentException{
		if(x.len(iterable) == 0){
			return new Bag<T>();
		}
		dict<Integer> iterableAsDict;
		if(iterable instanceof dict<?>){
			try{
				iterableAsDict = (dict<Integer>)iterable;
			}catch(ClassCastException e){
				throw new IllegalArgumentException("Input dict has to be of type Integer");
			}
			ArrayList<String> keysAsStrings = new ArrayList<String>();
			for(T element : iterable){
				keysAsStrings.add((String)element);
			}
			return new Bag<T>((Map<T,Integer>)(iterableAsDict.toHashMap()));
		}
		return new Bag<T>(iterable);
	}
	public static Bag<String> Bag(String string){
		return new Bag<String>(x.str(string));
	}
	public static Bag<String> Bag(String string0,String string1,String... otherStrings){
		Iterable<String> iterable = list.newArrayList(string0,string1,otherStrings);
		return new Bag<String>(iterable);
	}
	public static Bag<Number> Bag(Number number0,Number number1,Number... otherNumbers){
		Iterable<Number> iterable = list.newArrayList(number0,number1,otherNumbers);
		return new Bag<Number>(iterable);
	}
	public static Bag<Boolean> Bag(Boolean boolean0,Boolean boolean1,Boolean... otherBooleans){
		Iterable<Boolean> iterable = list.newArrayList(boolean0,boolean1,otherBooleans);
		return new Bag<Boolean>(iterable);
	}
	public static <T> Bag<T> Bag(Map<T,Integer> map){
		return new Bag<T>(map);
	}
	/*
	public static Bag<String> Bag(dict<Integer> dict){
		return new Bag<String>(dict.toHashMap());
	}*/
	
	//high-level list len
	public static <T> int len(Iterable<T> iterable){
		if (iterable instanceof Collection<?>) {
			  return ((Collection<?>)iterable).size();
		}else{
			int counter = 0;
			for (@SuppressWarnings("unused") T value : iterable){
				counter++;
			}
			return counter;
		}
	}
	//high-level string len
	public static int len(String string){
		return string.length();
	}
	
	//high-level Map len
	public static <T0,T1> int len(Map<T0,T1> map){
		return map.size();
	}
	
	//get time in milliseconds
	public static float time(){
		return Time.time();
	}
	
	public static <T> boolean all(Iterable<T> iterable){
	    for(T element : iterable){
	    	if (x.isFalse(element)){
	    		return false;
	    	}
	    }
	    return true;
	}
	
	public static <T> boolean any(Iterable<T> iterable){
	    for(T element : iterable){
	    	if (x.isTrue(element)){
	    		return true;
	    	}
	    }
	    return false;
	}
	
	public static <T extends Comparable<? super T>> T max(Iterable<T> iterable){
		if(x.len(iterable) == 0){
			throw new IndexOutOfBoundsException();
		}
		T max = iterable.iterator().next();
	    for(T element : iterable){
	    	if(max == null || element.compareTo(max) >= 0){
	    		max = element;
	    	}
	    }
	    return max;
	}
	@SafeVarargs
	public static <T extends Comparable<? super T>> T max(T value0,T value1,T... values){
		return max(list.newArrayList(value0,value1,values));
	}
	
	public static <T extends Comparable<? super T>> T min(Iterable<T> iterable){
		if(x.len(iterable) == 0){
			throw new IndexOutOfBoundsException();
		}
		T min = iterable.iterator().next();
	    for(T element : iterable){
	    	if(min == null || element.compareTo(min) <= 0){
	    		min = element;
	    	}
	    }
	    return min;
	}
	@SafeVarargs
	public static <T extends Comparable<? super T>> T min(T value0,T value1,T... values){
		return min(list.newArrayList(value0,value1,values));
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends Number> T sum(Iterable<T> iterable){
		Number sum = new Double(0);
		for(T element : iterable){
			sum = sum.doubleValue() + element.doubleValue(); 
		}
		return (T)sum;
	}
	
	//range for integers
	public static Iterable<Integer> range(final int min,final Integer max,final int step){
		Iterable<Integer> generator = new Iterable<Integer>(){
			public Iterator<Integer> iterator(){
				return new Iterator<Integer>(){
					int currentValue = min-step;
					@Override
					public boolean hasNext() {
						if(currentValue < max-1 || max == null){
							return true;
						}
						return false;
					}

					@Override
					public Integer next() {
						currentValue += step;
						return currentValue;
					}
				};
			}
		};
		return generator;
	}
	public static Iterable<Integer> count(int min,int max){
		return range(min,max,1);
	}
	public static Iterable<Integer> countTo(int max){
		return range(0,max,1);
	}
	public static Iterable<Integer> countFrom(int min){
		return range(min,null,1);
	}

	//range for doubles
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
				};
			}
		};
		return generator;
	}
	
	//range for iterables
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
				};
			}
		};
		return generator;
	}
	public static <T> Iterable<Integer> count(final Iterable<T> iterable){
		return count(iterable,1);
	}
	
	public static <F,T> Iterable<T> transform(Iterable<F> iterable,Function<F,T> function){
		if(iterable instanceof set<?>){
			set<T> newSet = new set<T>();
			for (F element : iterable){
				newSet.add(function.apply(element));
			}
			return newSet;
		}else{
			list<T> newList = new list<T>();
			for (F element : iterable){
				newList.append(function.apply(element));
			}
			return newList;
		}
	}
	
	public static <T> Iterable<T> filter(Iterable<T> iterable,Predicate<T> predicate){
		if(iterable instanceof set<?>){
			set<T> newSet = new set<T>();
			for (T element : iterable){
				if(predicate.apply(element)){
					newSet.add(element);	
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
	
	//high-level union of sets
	@SafeVarargs
	public static <T> Iterable<T> union(Iterable<T>... iterables){
		Set<T> result = set.newHashSet(iterables[0]);
		if (iterables.length > 1){
			for (int i = 1;i<iterables.length;i++){
				for (T element : iterables[i]){
					result.add(element);
				}
			}
		}
		return result;
	}
	
	//high-level difference of sets
	public static <T> set<T> difference(set<T> set0,set<T> set1){
		set<T> newSet = new set<T>(set0);
		return newSet.difference(set1);
	}
	
	
	//high-level file open
	public static HappyFile open(String path,String operation,String encoding) throws Exception{
		try{
			return new HappyFile(path,operation,encoding);
		}catch(Exception e){
			throw e;
		}
	}
	
	//high-level file open with default encoding
	public static HappyFile open(String path,String operation) throws Exception{
		try{
			return new HappyFile(path,operation);
		}catch(Exception e){
			throw e;
		}
	}
	
	//high level python-like enumerate
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
				};
			}
		};
		return generator;
	}
	
	
	public static <T> Iterable<tuple2<Integer,T>> enumerate(Iterable<T> iterable){
		return enumerate(iterable,0);
	}
	
	@SafeVarargs
	public static <T> Iterable<tuple2<Integer,T>> enumerate(T value0, T value1, T... values){
		List<T> lst = list.newArrayList(value0,value1,values);
		return enumerate(lst,0);
	}
	
	public static <T> Iterable<tuple2<Integer,String>> enumerate(String string, int startCount){
		str str = x.str(string);
		return enumerate(str,startCount);
	}
	public static <T> Iterable<tuple2<Integer,String>> enumerate(String string){
		return enumerate(string,0);
	}
	
	//high level python-like itertools.cycle
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
				};
			}
		};
		return generator;
	}
	
	//high level python-like itertools.cycle
	public static <T> Iterable<T> cycle(final Iterable<T> iterable){
		return cycle(iterable,null);
	}
	
	//high level python-like itertools.cycle
	public static Iterable<String> cycle(final String string){
		return cycle(x.str(string));
	}
	//high level python-like itertools.cycle
	public static Iterable<String> cycle(final String string, final int maxCount){
		return cycle(x.str(string),maxCount);
	}
	
	//high level python-like itertools.repeat
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
				};
			}
		};
		return generator;
	}
	//high level python-like itertools.repeat
	public static <T> Iterable<T> repeat(final T value){
		return repeat(value, null);
	}
	
	//high level sorted
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> Iterable<T> sorted(Iterable<T> iterable,Function<Object,? extends Comparable<?>> function,boolean reverse){
		ArrayList<KeyValue<?,T>> keyValues = new ArrayList<KeyValue<?,T>>();
		for (T element: iterable){
			keyValues.add(new KeyValue(function.apply(element),element));
		}
		
		Collections.sort(keyValues);
		
		if(reverse){
			Collections.reverse(keyValues);
		}
		ArrayList<T> resultList = new ArrayList<T>();
		for (KeyValue<?,T> keyValue : keyValues){
			resultList.add(keyValue.getValue());
		}
		if(iterable instanceof list<?>){
			return x.list(resultList);
		}
		return resultList;
	}
	public static <T> Iterable<T> sorted(Iterable<T> iterable,Function<Object,? extends Comparable<?>> function){
		return sorted(iterable,function,false);
	}
	public static <T extends Comparable<T>> Iterable<T> sorted(Iterable<T> iterable, boolean reverse){
		return sorted(iterable,x.<T>doNothing(),reverse);
	}
	public static <T extends Comparable<T>> Iterable<T> sorted(Iterable<T> iterable){
		return sorted(iterable,x.<T>doNothing(),false);
	}
	
	public static str sorted(str str,Function<Object,? extends Comparable<?>> function,boolean reverse){
		return x.str(sorted(str.toArrayList(),function,reverse));
	}
	public static str sorted(str str,Function<Object,? extends Comparable<?>> function){
		return x.str(sorted(str.toArrayList(),function,false));
	}
	public static str sorted(str str){
		return x.str(sorted(str.toArrayList(),x.<String>doNothing(),false));
	}
	
	public static <T> Iterable<T> reversed(Iterable<T> iterable){
		ArrayList<T> newArrayList = list.newArrayList(iterable);
		Collections.reverse(newArrayList);
		return x.list(newArrayList);

	}
	public static str reversed(str str) throws IOException{
		return x.str(reversed(list.newArrayList(str)));
	}
	
	public static <T extends Comparable<T>> Iterable<T> largestN(Iterable<T> iterable, int N){
		return x.list(sorted(iterable,true)).sliceTo(N);
	}
	
	public static <T extends Comparable<T>> Iterable<T> smallestN(Iterable<T> iterable, int N){
		return x.list(sorted(iterable)).sliceTo(N);
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
		
	//zip for two lists
	public static <T0,T1> list<tuple> zip(Iterable<T0> iterable0,Iterable<T1> iterable1){
		list<tuple> result = x.list();
		list<T1> list1 = x.list(iterable1);
		try{
			for(tuple index__value : x.enumerate(iterable0)){
				int index = (int)index__value.get(0);
				@SuppressWarnings("unchecked")
				T0 value = (T0)index__value.get(1);
				result.append(x.tupleOf(value,list1.get(index)));
			}	
		}catch(Exception e){
			
		}
		return result;
	}
	
	//zip for three lists
	public static <T0,T1,T2> list<tuple> zip(Iterable<T0> iterable0,Iterable<T1> iterable1,Iterable<T2> iterable2){
		list<tuple> result = x.list();
		list<T1> list1 = x.list(iterable1);
		list<T2> list2 = x.list(iterable2);
		try{
			for(tuple index__value : x.enumerate(iterable0)){
				int index = (int)index__value.get(0);
				@SuppressWarnings("unchecked")
				T0 value = (T0)index__value.get(1);
				result.append(x.tupleOf(value,list1.get(index),list2.get(index)));
			}	
		}catch(Exception e){
			
		}
		return result;
	}
	
	//zip for four lists
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
				result.append(x.tupleOf(value,list1.get(index),list2.get(index),list3.get(index)));
			}	
		}catch(Exception e){
			
		}
		return result;
	}
	
	//unzip for two types
	@SuppressWarnings("unchecked")
	public static <T0,T1> tuple unzip(Iterable<tuple> iterable,Class<T0> class0,Class<T1> class1){
		list<T0> list0 = x.listOf();
		list<T1> list1 = x.listOf();
		for (tuple T0__T1 : iterable){
			list0.append((T0)T0__T1.get(0));
			list1.append((T1)T0__T1.get(1));
		}
		return x.tupleOf(list0,list1);
	}
	
	//unzip for three types
	@SuppressWarnings("unchecked")
	public static <T0,T1,T2> tuple unzip(Iterable<tuple> iterable,Class<T0> class0,Class<T1> class1,Class<T2> class2){
		list<T0> list0 = x.listOf();
		list<T1> list1 = x.listOf();
		list<T2> list2 = x.listOf();
		for (tuple T0__T1__T2 : iterable){
			list0.append((T0)T0__T1__T2.get(0));
			list1.append((T1)T0__T1__T2.get(1));
			list2.append((T2)T0__T1__T2.get(2));
		}
		return x.tupleOf(list0,list1,list2);
	}
	
	//unzip for four types
	@SuppressWarnings("unchecked")
	public static <T0,T1,T2,T3> tuple unzip(Iterable<tuple> iterable,Class<T0> class0,Class<T1> class1,Class<T2> class2,Class<T3> class3){
		list<T0> list0 = x.listOf();
		list<T1> list1 = x.listOf();
		list<T2> list2 = x.listOf();
		list<T3> list3 = x.listOf();
		for (tuple T0__T1__T2__T3 : iterable){
			list0.append((T0)T0__T1__T2__T3.get(0));
			list1.append((T1)T0__T1__T2__T3.get(1));
			list2.append((T2)T0__T1__T2__T3.get(2));
			list3.append((T3)T0__T1__T2__T3.get(3));
		}
		return x.tupleOf(list0,list1,list2,list3);
	}
	
	public static Regex Regex(String regularExpression,int flags){
		return new Regex(regularExpression,flags);
	}
	public static Regex Regex(String regularExpression){
		return new Regex(regularExpression,0);
	}
	public static Regex RegexNoCase(String regularExpression){
		return new Regex(regularExpression,Regex.CASE_INSENSITIVE);
	}
	
	public static Regex Regex(dict<String> translator,int flags){
		return new Regex(translator,flags);
	}
	public static Regex Regex(dict<String> translator){
		return new Regex(translator,0);
	}
	public static Regex RegexNoCase(dict<String> translator){
		return new Regex(translator,Regex.CASE_INSENSITIVE);
	}
	
	public Slicer slice(int startIndex,int endIndex){
		return slice(startIndex, endIndex, 1);
	}
	public Slicer slice(int startIndex,int endIndex, int step){
		return new Slicer(startIndex, endIndex, step, false);	
	}
	public Slicer slice(){
		return slice(1);
	}
	public Slicer slice(int step){
		if (step < 0){
			return new Slicer(Integer.MAX_VALUE,0,step,true);	
		}else{
			return new Slicer(0,Integer.MAX_VALUE,step,false);
		}	
	}
	public Slicer sliceTo(int endIndex, int step){
		int startIndex = 0;
		if (step < 0){
			startIndex = Integer.MAX_VALUE-1;
			return new Slicer(startIndex,endIndex,step,true);
		}
		return slice(startIndex,endIndex,step);
	}
	public Slicer sliceTo(int endIndex){
		return sliceTo(endIndex, 1);
	}
	public Slicer sliceFrom(int startIndex, int step){
		int endIndex = Integer.MAX_VALUE-1;
		if (step < 0){
			endIndex = 0;
		}
		return new Slicer(startIndex,endIndex,step,true);
	}
	public Slicer sliceFrom(int startIndex){
		return sliceFrom(startIndex,1);
	}
	
	//high level print
	public static <T0> void print(T0 object){
		System.out.println(object==null?"NullType":object);
	}
	public static <T0,T1> void print(T0 object0,T1 object1){
		System.out.println(""+(object0==null?"NullType":object0)+" "+(object1==null?"NullType":object1));
	}
	public static <T0,T1,T2> void print(T0 object0,T1 object1,T2 object2){
		System.out.println(""+(object0==null?"NullType":object0)+" "+(object1==null?"NullType":object1)+" "+(object2==null?"NullType":object2));
	}
	public static <T0,T1,T2,T3> void print(T0 object0,T1 object1,T2 object2,T3 object3){
		System.out.println(""+(object0==null?"NullType":object0)+" "+(object1==null?"NullType":object1)+" "+(object2==null?"NullType":object2)+" "+(object3==null?"NullType":object3));
	}
	public static <T0,T1,T2,T3,T4> void print(T0 object0,T1 object1,T2 object2,T3 object3,T4 object4){
		System.out.println(""+(object0==null?"NullType":object0)+" "+(object1==null?"NullType":object1)+" "+(object2==null?"NullType":object2)+" "+(object3==null?"NullType":object3)+" "+(object4==null?"NullType":object4));
	}
	public static <T0,T1,T2,T3,T4,T5> void print(T0 object0,T1 object1,T2 object2,T3 object3,T4 object4,T5 object5){
		System.out.println(""+(object0==null?"NullType":object0)+" "+(object1==null?"NullType":object1)+" "+(object2==null?"NullType":object2)+" "+(object3==null?"NullType":object3)+" "+(object4==null?"NullType":object4)+" "+(object5==null?"NullType":object5));
	}
	public static <T0,T1,T2,T3,T4,T5,T6> void print(T0 object0,T1 object1,T2 object2,T3 object3,T4 object4,T5 object5,T6 object6){
		System.out.println(""+(object0==null?"NullType":object0)+" "+(object1==null?"NullType":object1)+" "+(object2==null?"NullType":object2)+" "+(object3==null?"NullType":object3)+" "+(object4==null?"NullType":object4)+" "+(object5==null?"NullType":object5)+" "+(object6==null?"NullType":object6));
	}
	public static <T0,T1,T2,T3,T4,T5,T6,T7> void print(T0 object0,T1 object1,T2 object2,T3 object3,T4 object4,T5 object5,T6 object6,T7 object7){
		System.out.println(""+(object0==null?"NullType":object0)+" "+(object1==null?"NullType":object1)+" "+(object2==null?"NullType":object2)+" "+(object3==null?"NullType":object3)+" "+(object4==null?"NullType":object4)+" "+(object5==null?"NullType":object5)+" "+(object6==null?"NullType":object6)+" "+(object7==null?"NullType":object7));
	}
	public static <T0,T1,T2,T3,T4,T5,T6,T7,T8> void print(T0 object0,T1 object1,T2 object2,T3 object3,T4 object4,T5 object5,T6 object6,T7 object7,T8 object8){
		System.out.println(""+(object0==null?"NullType":object0)+" "+(object1==null?"NullType":object1)+" "+(object2==null?"NullType":object2)+" "+(object3==null?"NullType":object3)+" "+(object4==null?"NullType":object4)+" "+(object5==null?"NullType":object5)+" "+(object6==null?"NullType":object6)+" "+(object7==null?"NullType":object7)+" "+(object8==null?"NullType":object8));
	}

	public static str strOf(){
		return new str();
	}
	public static str strOf(String string){
		return new str(string);
	}
	public static str strOf(Iterable<String> iterable){
		return new str(x.String("").join(iterable));
	}
	public static str str(){
		return new str();
	}
	public static str str(String string){
		return new str(string);
	}
	public static str str(Iterable<String> iterable){
		return new str(x.String("").join(iterable));
	}
	
	public static <T> set<T> setOf(Iterable<T> iterable){
		set<T> constructor = new set<T>();
		for (T element:iterable){
			constructor.add(element);
		}
		return constructor;
	}
	
	//high-level set processing (plus, minus, and, or)
	public static <T> set<T> setOf(){
		return new set<T>();
	}
	@SafeVarargs
	public static <T> set<T> setOf(T... elements){
		return new set<T>(set.newHashSet(elements));
	}
	public static <T> set<T> set(Iterable<T> iterable){
		return new set<T>(iterable);
	}

	//high-level list processing (concat, product, slicing)
	public static <T> list<T> listOf(){
		return new list<T>();
	}
	
	@SafeVarargs
	public static <T> list<T> listOf(T... elements){
		list<T> list = new list<T>();
		for(T element : elements){
			list.append(element);	
		}
		return list;
	}
	
	public static <T> list<T> list(){
		return listOf();
	}
	
	public static <T> list<T> list(Iterable<T> iterable){ 
		return new list<T>(iterable);
	}
	
	public static <O> ScalarComprehensionStart<O> scalar(){
		return ComprehensionFactory.scalar();
	}
	
	public static boolean isTrue(Object value){
		if(value instanceof Iterable<?> && x.len((Iterable<?>)value) == 0){
			return false;
		}
		if(value instanceof String && x.len((String)value) == 0){
			return false;
		}
		try{
			if((Integer)value == 0){
				return false;
			}	
		}catch(Exception e){
			
		}
		if(value == null){
			return false;
		}
		return true;
	}
	
	public static boolean isFalse(Object value){
		return !isTrue(value);
	}
	
	//creates and returns new complex predicate with NOT logic applied to the input predicate
	public static <T> ComplexPredicate<T> NOT(Predicate<T> predicate){
		return newComplexPredicate(predicate).joinAs(PredicateJoinType.NOT);
	}
	
	public static Function<Object, Object> stripAccents = new Function<Object, Object>() {
		public String apply(Object string) {
			return stripAccents((String)string);
		}
	};
	public static String stripAccents(String string){
	    string = Normalizer.normalize(string, Normalizer.Form.NFD);
	    string = string.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
	    return string;
	}
	
	public static Function<Object, Object> escape = new Function<Object, Object>() {
		public String apply(Object string) {
			return escape((String)string);
		}
	};
	
	public static String escape(String string){
		return x.Regex("([\\[\\]/{}()*+?.\\\\^$\\|-])").sub("\\\\$1", string);
	}
	
	public static Function<Object, Object> strip = new Function<Object, Object>() {
		public String apply(Object string) {
			return ((String)string).trim();
		}
	};
	public static Function<Object, Object> trim = strip;
	
	public static Function<Object, String> toLowerCase = new Function<Object, String>() {
		public String apply(Object string) {
			String realString = string.toString();
			return realString.toLowerCase();
		}
	};
	public static Function<Object, String> lower = toLowerCase;
	
	public static Function<Object, String> toUpperCase = new Function<Object, String>() {
		public String apply(Object string) {
			String realString = string.toString();
			return realString.toUpperCase();
		}
	};
	public static Function<Object, String> upper = toUpperCase;
	
	public static ParametrizedFunction<Object,Integer> len(){
		return (new ParametrizedFunction<Object,Integer>() {
			public Integer apply(Object value) {
				if(value instanceof Iterable<?>){
					return len((Iterable<?>) value);	
				}else{
					return len((String) value);
				}
			}
		}).params();
	}
	
	public static <T> ParametrizedFunction<Object,T> doNothing(){
		return (new ParametrizedFunction<Object,T>() {
			@SuppressWarnings("unchecked")
			public T apply(Object input) {
				return (T)input;
			}
		}).params();
	}
	
	public static <O> ParametrizedFunction<Object,O> asKeyOn(Object dictOrMapWithStringKeys){
		return (new ParametrizedFunction<Object,O>() {
			@SuppressWarnings("unchecked")
			public O apply(Object key) {
				try{
					return ((dict<O>)(params.get(0))).get((String)key);	
				}catch(Exception e){
					return ((Map<String,O>)(params.get(0))).get((String)key);
				}
			}
		}).params(dictOrMapWithStringKeys);
	}
	
	public static ParametrizedFunction<Object,String> joinOn(String separator){
		return (new ParametrizedFunction<Object,String>() {
			public String apply(Object iterable) {
				if(iterable instanceof String && this.params.get(0).toString().equals("")){
					return (String)iterable;
				}
				return x.String(this.params.get(0).toString()).join((Iterable<?>)iterable);
			}
		}).params(separator);
	}
	
	public static <T> ParametrizedPredicate<Object> in(final Iterable<T> iterable){
		return (new ParametrizedPredicate<Object>() {
			@SuppressWarnings("unchecked")
			public Boolean apply(Object key) {
				return contains(iterable, (T)key);
			}
		}).params(iterable);
	}
	
	public static ParametrizedFunction<Iterable<String>,String> join(String separator){
		return (new ParametrizedFunction<Iterable<String>,String>() {
			public String apply(Iterable<String> iterable) {
				return x.String((String)params.get(0)).join(iterable);
			}
		}).params(separator);
	}

	//predicate that is always false no matter the input
	public static <T> ParametrizedPredicate<T> FALSE(){
		return (new ParametrizedPredicate<T>() {
			public Boolean apply(T input) {
				return false;
			}
		}).params();
	}
	
	//predicate that is always true no matter the input
	public static <T> ParametrizedPredicate<T> TRUE(){
		return (new ParametrizedPredicate<T>() {
			public Boolean apply(T input) {
				return true;
			}
		}).params();
	}
	
	public static Predicate<Object> empty = new Predicate<Object>() {
		public Boolean apply(Object iterable) {
			if(iterable instanceof String){
				return x.len((String)iterable) == 0;	
			}else{
				return x.len((Iterable<?>)iterable) == 0;
			}
		}
	};
	public static Predicate<Object> isEmpty = empty;	
	
	public static Function<Object,Integer> getHashCode = new Function<Object,Integer>() {
		public Integer apply(Object value) {
			return value.hashCode();
		}
	};
}
