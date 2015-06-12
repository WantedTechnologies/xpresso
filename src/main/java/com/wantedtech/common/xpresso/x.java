/*
 * Copyright (c) 2015 Wanted Technologies, The Xpresso Authors
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.wantedtech.common.xpresso;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.lang.Iterable;
import java.lang.Number;

import com.wantedtech.common.xpresso.helpers.HappyObject;
import com.wantedtech.common.xpresso.helpers.HappyString;
import com.wantedtech.common.xpresso.helpers.HappyStringStatic;
import com.wantedtech.common.xpresso.helpers.Helpers;
import com.wantedtech.common.xpresso.helpers.HappyMySQL;
import com.wantedtech.common.xpresso.helpers.Slicer;
import com.wantedtech.common.xpresso.json.Json;
import com.wantedtech.common.xpresso.types.*;
import com.wantedtech.common.xpresso.comprehension.ComprehensionFactory;
import com.wantedtech.common.xpresso.comprehension.ScalarComprehensionStart;
import com.wantedtech.common.xpresso.comprehension.Tuple1ComprehensionStart;
import com.wantedtech.common.xpresso.comprehension.Tuple2ComprehensionStart;
import com.wantedtech.common.xpresso.functional.Function;
import com.wantedtech.common.xpresso.functional.ParametrizedFunction;
import com.wantedtech.common.xpresso.functional.ParametrizedPredicate;
import com.wantedtech.common.xpresso.functional.Predicate;
import com.wantedtech.common.xpresso.functional.lambda.LambdaFunction;
import com.wantedtech.common.xpresso.functional.lambda.LambdaPredicate;
import com.wantedtech.common.xpresso.regex.Regex;
import com.wantedtech.common.xpresso.time.ThreadTimer;
import com.wantedtech.common.xpresso.time.Time;
import com.wantedtech.common.xpresso.time.Timer;
import com.wantedtech.common.xpresso.types.strs.strStatic;
import com.wantedtech.common.xpresso.types.tuples.tuple0;
import com.wantedtech.common.xpresso.types.tuples.tuple1;
import com.wantedtech.common.xpresso.types.tuples.tuple2;
import com.wantedtech.common.xpresso.types.tuples.tuple3;
import com.wantedtech.common.xpresso.types.tuples.tuple4;
import com.wantedtech.common.xpresso.token.Token;
import com.wantedtech.common.xpresso.token.TokenStatic;

/**
 * This class contains high-level static utility methods.
 * It can be seen as a container of the highest-level methods of xpresso,
 * similar to Python's __builtin__ namespace.
 * 
 * @author Andriy Burkov
 * @since 0.1
 */

public class x {
	
	/**
	 * Creates an Assert class object that has useful assertion methods.
	 */
	public static Assert Assert = new Assert();
	
	/**
	 * Creates an Assert class object and calls its True method.
	 */
	public static void assertTrue(boolean expression){
		new Assert().True(expression);
	}
	
	/**
	 * Creates an Assert class object and calls its notNull method.
	 */
	public static <T> void assertNotNull(T expression){
		new Assert().notNull(expression);
	}
	
	/**
	 * Creates an Assert class object and calls its notEmpty method.
	 */
	public static <T> void assertNotEmpty(T expression){
		new Assert().notEmpty(expression);
	}
	
	/**
	 * 
	 * Creates an instance of a HappySQL object.
	 * 
	 * @param	dbHost
	 * @param	userName
	 * @param	password
	 * @param	dbName
	 * @return	an object that has the {@link HappySQL#execute(String)} method
	 * 		  	that takes the query and params as input;
	 * 
	 * 			Example:
	 * 			
	 * 			try (HappySQL sql = x.mysql("localhost", "user", "password", "db")) {
	 * 					sql.execute("SELECT * FROM Table WHERE ID BETWEEN ? and ? and UserName LIKE ?", 1000, 2000, "John %");
	 * 			}
	 * 
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static HappySQL mysql(String dbHost, String userName, String password, String dbName) throws ClassNotFoundException, SQLException{
		return new HappyMySQL(dbHost, userName, password, dbName);
	} 
	
	/**
	 * 
	 * Creates a new instance of a HappyMySQL object with the same parameters as
	 * those of the input HappyMySQL object.
	 * 
	 * @return	an object that has the {@link HappySQL#execute(String)} method
	 * 		  	that takes the query and params as input;
	 * 
	 * 			Example:
	 * 			
	 * 			try (HappySQL sql2 = x.mysql(sql1)) {
	 * 					sql2.execute("SELECT * FROM Table WHERE ID BETWEEN ? and ? and UserName LIKE ?", 1000, 2000, "John %");
	 * 			}
	 * 
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static HappySQL mysql(HappyMySQL sql) throws ClassNotFoundException, SQLException{
		return new HappyMySQL(sql.dbHost, sql.userName, sql.password, sql.dbName);
	} 
	
	/**
	 * Creates an instance of a HappyObject class. This class has utility methods 
	 * equals, compareTo and HashCode.
	 * 
	 * Examples:
	 * 
	 * When defining a class:
	 * 
	 * @Override
	 * int hashCode(){
	 * 		return x.Object(this).hashCode();
	 * }
	 * In the above code, xpresso first finds the members of this (via reflections) and then
	 * dynamically computes the hash code for this based on the values of its members.
	 * 
	 * @Override
	 * boolean equals(Object obj){
	 * 		return x.Object(this).equals(obj);
	 * }
	 * In the above code, xpresso first finds the members of the
	 * two objects (this and obj), and then compares the values of those members.
	 * 
	 * @Override
	 * public int compareTo(Object obj){
	 *      return x.Object(this).compareTo(obj, fieldName0, fieldName1, ...);
	 * }
	 * 
	 * In the above code, xpresso first finds the members of the
	 * two objects (this and obj). It then compares the values of those
	 * members between the two objects if those members' names are listed
	 * among the input field names fieldName0, fieldName1, etc.
	 * The order of comparisons between the member's values is the same
	 * as the order of input field names.
	 * 
	 * @param o
	 * @return
	 */
	public static HappyObject Object(Object o){
		return new HappyObject(o);
	}
	
	/**
	 * Opens a {@link HappyFile} for reading or writing, in binary or text mode.
	 * 
	 * Example:
	 * 
	 * try (HappyFile f = x.open("filename.txt","r","utf-8"){
	 * 		//do stuff
	 * }
	 * 
	 * In case of a text file, the {@link HappyFile} object is also an Iterable containing 
	 * lines of the file:
	 * 
	 * for(String line : f){
	 * 		x.print(line);
	 * }
	 * 
	 * @param path			a {@link String} object containing the path to the file
	 * @param operation		can be "r" (read in text mode), "rb" (read in binary mode),
	 * 						"w" (write in text mode), "wb" write in binary mode
	 * 						"a" append in text mode, "ab" append in binary mode
	 * @param encoding		the String object containing the encoding of the file
	 * 						(can be "utf-8" or "latin-1")
	 */
	public static HappyFile open(String path,String operation,String encoding) throws IOException{
		return new HappyFile(path,operation,encoding);
	}
	
	/**
	 * Opens a {@link HappyFile} for reading or writing, in binary or text mode.
	 * 
	 * Example:
	 * 
	 * HappyFile f = x.open("filename.txt","r","utf-8");
	 * 
	 * In case of a text file, the {@link HappyFile} object is also an Iterable containing 
	 * lines of the file:
	 * 
	 * for(String line : x.open("filename.txt","r","utf-8")){
	 * 		x.print(line);
	 * }
	 * 
	 * @param path			a {@link String} object containing the path to the file
	 * @param operation		can be "r" (read in text mode with utf-8 encoding), "rb" (read in binary mode),
	 * 						"w" (write in text mode), "wb" write in binary mode
	 * 						"a" append in text mode, "ab" append in binary mode
	 */
	public static HappyFile open(String path,String operation) throws Exception{
		try{
			return new HappyFile(path,operation);
		}catch(Exception e){
			throw e;
		}
	}
	
	/**
	 * Returns a {@link HappyString HappyString} object that extends the String object
	 * with additional methods, such as {@link HappyString#join}, {@link HappyString#split}, and {@link HappyString#in}.
	 * 
	 * 
	 * Example 1: boolean q = x.String("na").in("banana");
	 *            x.print(q);
	 *                   
	 * Console:   true
	 * 
	 * 
	 * Example 2: String s = x.String("|").join(x.listOf("a","b","c"));
	 *            x.print(s);
	 *                   
	 * Console:   a|b|c
	 * 
	 * @param     char to wrap
	 * @return    a HappyString object that wraps char
	 */
	public static HappyString String(char character){
		return new HappyString(String.valueOf(character));
	}
	
	/**
	 * Returns a {@link HappyString HappyString} object that extends the String object
	 * with additional methods, such as {@link HappyString#join}, {@link HappyString#split}, and {@link HappyString#in}.
	 * 
	 * 
	 * Example 1: boolean q = x.String("na").in("banana");
	 *            x.print(q);
	 *                   
	 * Console:   true
	 * 
	 * 
	 * Example 2: String s = x.String("|").join(x.listOf("a","b","c"));
	 *            x.print(s);
	 *                   
	 * Console:   a|b|c
	 * 
	 * @param     char to wrap
	 * @return    a HappyString object that wraps char
	 */
	public static HappyString String(Character character){
		return new HappyString(String.valueOf(character));
	}
	
	/**
	 * Returns a {@link HappyString HappyString} object that extends the String object
	 * with additional methods, such as {@link HappyString#join}, {@link HappyString#split}, and {@link HappyString#in}.
	 * 
	 * 
	 * Example 1: boolean q = x.String("na").in("banana");
	 *            x.print(q);
	 *                   
	 * Console:   true
	 * 
	 * 
	 * Example 2: String s = x.String("|").join(x.listOf("a","b","c"));
	 *            x.print(s);
	 *                   
	 * Console:   a|b|c
	 * 
	 * @param     string to wrap
	 * @return    a HappyString object that wraps string
	 */
	public static HappyString String(String string){
		return new HappyString(string);
	}
	
	/**
	 * Returns a HappyString object that extends the String object
	 * with additional methods, such as {@link HappyString#join}, {@link HappyString#split}, and {@link HappyString#in}.
	 * 
	 * 
	 * Example 1: boolean q = x.String(x.str("na")).in("banana");
	 *            x.print(q);
	 *                   
	 * Console:   true
	 * 
	 * 
	 * Example 2: String s = x.String("|").join(x.listOf("a","b","c"));
	 *            x.print(s);
	 *                   
	 * Console:   a|b|c
	 * 
	 * @param     str to wrap
	 * @return    a {@link HappyString} object that wraps the str
	 */
	public static HappyString String(str str){
		return new HappyString(str.toString());
	}
	
	/**
	 * For consistency, {@link x#String x.String} contains an instance of a utility
	 * {@link HappyStringStatic} object that implements some static methods
	 * that a usual String class has as well plus some new Function and Predicate
	 * objects that take {@link String} as input.
	 * 
	 * Example 1: String three = x.String.valueOf(3);
	 *            x.print(three);
	 *                   
	 * Console:   3
	 * 
	 * Example 2: Function<Object,String> upperFun = x.String.upper;
	 * 
	 */
	public static HappyStringStatic String = new HappyStringStatic();
	
	/**
	 * {@link x#str str} contains an instance of a utility
	 * {@link strStatic} object that implements some Function and Predicate
	 * objects that take a {@link str} as input.
	 * 
	 * Example 1: Function<Object,str> upperFun = x.str.upper;
	 * 
	 */
	public static strStatic str = new strStatic();
	
	/**
	 * Factory method that creates a new empty {@link str} object.
	 * 
	 * Example 1: str newStr = x.str();
	 * 
	 * x.print(newStr);
	 * 
	 * Console:
	 * 
	 */
	public static str str(){
		return new str();
	}
	
	/**
	 * Factory method that creates a new {@link str} object
	 * from the input {@link String} string.
	 * 
	 * Example 1: str newStr = x.str("I like xpresso!");
	 * 
	 * x.print(newStr.sliceFrom(7));
	 * 
	 * Console: xpresso!
	 * 
	 */
	public static str str(String string){
		return new str(string);
	}
	
	/**
	 * Factory method that creates a new {@link str} object
	 * from the input {@link Iterable} of type {@link String}.
	 * 
	 * Example 1: str newStr = x.str(x.list("hello", " ", "world", "!"));
	 * 
	 * x.print(newStr);
	 * 
	 * Console: hello world!
	 * 
	 */
	public static str str(Iterable<String> iterable){
		return new str(x.String("").join(iterable));
	}
	
	
	/**
	 * Factory method that creates a new {@link set} object
	 * from the input {@link Iterable} iterable of type T. The elements
	 * of the input iterable will become the members of the new set object.
	 *  
	 */
	public static <T> set<T> set(Iterable<T> iterable){
		set<T> constructor = new set<T>();
		for (T element:iterable){
			constructor.put(element);
		}
		return constructor;
	}
	
	/**
	 * Factory method that creates a new empty {@link set} object.
	 *  
	 */
	public static <T> set<T> set(){
		return new set<T>();
	}
	
	/**
	 * Factory method that creates a new {@link set} object
	 * from all input elements of type T. The elements
	 * of the input will become the members of the new set object.
	 *  
	 */
	@SafeVarargs
	public static <T> set<T> set(T element0, T element1, T... elements){
		return new set<T>(set.newHashSet(elements)).put(element0).put(element1);
	}
	
	/**
	 * Factory method that creates a new {@link set} object
	 * from the array of input elements of type T. The elements
	 * of the input array will become the members of the new set object.
	 *  
	 */
	public static <T> set<T> set(T[] elements){
		return new set<T>(set.newHashSet(elements));
	}
	
	/**
	 * Factory method that creates a new {@link set} object
	 * and puts into the set the input element.
	 *  
	 */
	public static <T> set<T> setOf(T element){
		return new set<T>().put(element);
	}

	/**
	 * Factory method that creates a new empty {@link list} object.
	 *  
	 */
	public static <T> list<T> list(){
		return new list<T>();
	}
	
	/**
	 * Factory method that creates a new empty {@link list} object.
	 *  
	 */
	public static <T> list<T> listOf(T element){
		return (new list<T>()).append(element);
	}
	
	/**
	 * Factory method that creates a new {@link list} object
	 * from the input {@link Iterable} iterable of type T. The elements
	 * of the input iterable will become the members of the new list object.
	 *  
	 */
	@SafeVarargs
	public static <T> list<T> list(T element0, T element1, T... elements){
		list<T> list = (new list<T>()).append(element0).append(element1);
		for(T element : elements){
			list.append(element);	
		}
		return list;
	}
	
	/**
	 * Factory method that creates a new {@link list} object
	 * from the input array of type T.
	 * 
	 * The elements of the input array become the elements of new list.
	 * 
	 * The order of elements in the input array is preserved in the new list.
	 *  
	 */
	public static <T> list<T> list(T[] values){ 
		return new list<T>(Helpers.newArrayList(values));
	}
	
	/**
	 * Factory method that creates a new {@link list} object
	 * from the input {@link Iterable} iterable of type T.
	 * 
	 * The elements of the input iterable become the elements of new list.
	 * 
	 * The order of elements in the input iterable is preserved in the new list.
	 *  
	 */
	public static <T> list<T> list(Iterable<T> iterable){ 
		return new list<T>(iterable);
	}
	
	/**
	 * Factory method that starts a new scalar comprehension.
	 * 
	 * The difference of a scalar comprehension from a tuple comprehension 
	 * is that scalar comprehension sees the elements of the input iterable
	 * as scalars even if the latter are tuples, or iterables or other 
	 * collection types.
	 * 
	 * Example 1, a list comprehension:
	 * 
	 * Python:
	 * foreign_trips_lower = [element.lower() for element in trips if element not in russian_cities]
	 * 
	 * xpresso:
	 * 
	 * list<String> foreignTripsLower = x.list(x.yield().apply(x.lower).forIter(trips).unless(x.in(russianCities)));
	 * 
	 */
	public static <O> ScalarComprehensionStart<O> yield(){
		return ComprehensionFactory.scalar();
	}
	
	/**
	 * Factory method that starts a new tuple comprehension.
	 * 
	 * The difference of a scalar comprehension from a tuple comprehension 
	 * is that the tuple comprehension returns tuples of values.
	 * 
	 * Usually it's used when the elements of the input iterable
	 * are themselves tuples, or iterables or other collection types.
	 * But it freely can be used with iterables of scalars if needed.
	 * 
	 * Example 1, a list comprehension:
	 * 
	 * Python:
	 * list1 = [a.lower() for a, b, c, d in list0]
	 * 
	 * xpresso:
	 * list<tuple> list1 = x.list(x.yield("a").apply(x.lower).where("a", "b", "c", "d").in(list0));
	 * 
	 */
	public static Tuple1ComprehensionStart yield(String fieldName) {
        return ComprehensionFactory.tuple(fieldName);
    }
	
	/**
	 * Factory method that starts a new tuple comprehension.
	 * 
	 * The difference of a scalar comprehension from a tuple comprehension 
	 * is that the tuple comprehension returns tuples of values.
	 * 
	 * Usually it's used when the elements of the input iterable
	 * are themselves tuples, or iterables or other collection types.
	 * But it freely can be used with iterables of scalars if needed.
	 * 
	 * Example 1, a list comprehension:
	 * 
	 * Python:
	 * list1 = [a.lower(), b.upper() for a, b, c, d in list0]
	 * 
	 * xpresso:
	 * list<tuple> list1 = x.list(x.yield("a", "b").apply(x.lower, x.upper).where("a", "b", "c", "d").in(list0));
	 * 
	 * Example 2, a list comprehension:
	 * 
	 * Python:
	 * list1 = [a.lower(), true for a, b, c, d in list0]
	 * 
	 * xpresso:
	 * list<tuple> list1 = x.list(x.yield("a", "b").apply(x.lower).replace(True).where("a", "b", "c", "d").in(list0));
	 * 
	 */
	public static Tuple2ComprehensionStart yield(String fieldName0, String fieldName1) {
        return ComprehensionFactory.tuple(fieldName0, fieldName1);
    }
	
	/**
	 * Creates and returns a {@link ParametrizedFunction} that takes as a parameter an {@link java.lang.Iterable} or a {@link java.util.Map} 
	 * and uses the Function's input value as key to get a value from the
	 * corresponding {@link java.lang.Iterable} or {@link java.util.Map}
	 *
	 * Example 1: @see x#joinOn(String) joinOn
	 * 
	 * @param iterableOrMap	an {@link java.lang.Iterable} or a {@link Map}
	 * @return a {@link ParametrizedFunction}
	 *                   
	 */
	public static <O> ParametrizedFunction<Object,O> asKeyOn(Object iterableOrMap){
		return (new ParametrizedFunction<Object,O>() {
			@SuppressWarnings("unchecked")
			public O apply(Object key) {
				try{
					return ((dict<O>)(params.get(0))).get((String)key);	
				}catch(Exception e0){
					try{
						return ((Map<?,O>)(params.get(0))).get(key);	
					}catch(Exception e1){
						try{
							return ((set<O>)(params.get(0))).get((O)key);	
						}catch(Exception e2){
							try{
								return ((list<O>)(params.get(0))).get((O)key);	
							}catch(Exception e3){
								try{
									return (Helpers.newArrayList((Iterable<O>)(params.get(0)))).get((Integer)key);	
								}catch(Exception e4){
									throw new IllegalArgumentException("asKeyOn could not interpret the input object as a container of values.");
								}	
							}	
						}
					}
				}
			}
		}).params(x.listOf(iterableOrMap));
	}
	
	/**
	 * Creates and returns a {@link ParametrizedFunction} that takes as a parameter a {@link String} separator 
	 * and concatinates the Function's input {@link java.lang.Iterable} using the separator
	 *
	 * Example 1: @see x#joinOn(String) joinOn
	 * 
	 * @param separator	an {@link String} separator
	 * @return a {@link ParametrizedFunction}
	 * 
	 */
	public static ParametrizedFunction<Object,String> joinOn(String separator){
		return (new ParametrizedFunction<Object,String>() {
			public String apply(Object iterable) {
				if(iterable instanceof String && this.params.get(0).toString().equals("")){
					return (String)iterable;
				}
				try{
					return x.String(this.params.get(0).toString()).join((Iterable<?>)iterable);	
				}catch(Exception e){
					throw new IllegalArgumentException("Could not interpret the input object as an Iterable.");
				}
			}
		}).params(x.listOf((Object)separator));
	}
	
	/**
	 * Creates and returns new {@link ParametrizedPredicate} with the NOT logic applied to the input {@link Predicate}  @param predicate
	 *
	 * Example 1: Predicate\<Object\> notEmpty = x.NOT({@link x#empty empty});
	 * 
	 * @param predicate	a {@link Predicate} to apply the NOT logic to
	 * @return a {@link ParametrizedPredicate} that negates the input @param predicate 
	 *              
	 */
	public static ParametrizedPredicate<Object> NOT(final Predicate<Object> predicate){
		return (new ParametrizedPredicate<Object>() {
			public Boolean apply(Object bool) {
				return !(predicate.apply(bool));
			}
		}).params(predicate);
	}
	
	
	/**
	 * Creates and returns new {@link ParametrizedPredicate} that returns true
	 * only if the input value of the predicate is contained within in the {@link java.lang.Iterable} @param iterable parameter.
	 *
	 * Example 1: Predicate\<Object\> notEmpty = x.NOT({@link x#empty empty});
	 * 
	 * @param iterable	an {@link kava.lang.Iterable} of type @param <T>
	 * @return a new {@link ParametrizedPredicate}
	 *             
	 */
	public static <T> ParametrizedPredicate<Object> in(final Iterable<T> iterable){
		return (new ParametrizedPredicate<Object>() {
			@SuppressWarnings("unchecked")
			public Boolean apply(Object key) {
				return contains(iterable, (T)key);
			}
		}).params(iterable);
	}
	
	/**
	 * Creates and returns new {@link ParametrizedPredicate} that returns true
	 * only if the input value of the predicate is contained within in the {@link java.lang.Iterable} @param string parameter.
	 *
	 * Example 1: Predicate\<Object\> notEmpty = x.NOT({@link x#empty empty});
	 * 
	 * @param iterable	an {@link kava.lang.Iterable} of type @param <T>
	 * @return a new {@link ParametrizedPredicate}
	 *             
	 */
	public static <T> ParametrizedPredicate<Object> in(final String string){
		return (new ParametrizedPredicate<Object>() {
			public Boolean apply(Object stringOrChar) {
				try{
					return x.String((char)stringOrChar).in((String)params.get(0));
				}catch(Exception e0){
					try{
						return x.String((Character)stringOrChar).in((String)params.get(0));
					}catch(Exception e1){
						try{
							return x.String((String)stringOrChar).in((String)params.get(0));
						}catch(Exception e2){
							throw new IllegalArgumentException("Could not intepret the input value as a character ort a string");
						}
					}
				}
			}
		}).params(string);
	}
	
	/**
	 * Creates and returns new {@link ParametrizedPredicate} that returns true
	 * only if the input value of the predicate is contained within in the {@link java.lang.Iterable} @param str parameter.
	 *
	 * Example 1: Predicate\<Object\> notEmpty = x.NOT({@link x#empty empty});
	 * 
	 * @param iterable	an {@link kava.lang.Iterable} of type @param <T>
	 * @return a new {@link ParametrizedPredicate}
	 *             
	 */
	public static <T> ParametrizedPredicate<Object> in(final str string){
		return (new ParametrizedPredicate<Object>() {
			public Boolean apply(Object stringOrChar) {
				try{
					return x.String((char)stringOrChar).in((str)params.get(0));
				}catch(Exception e0){
					try{
						return x.String((Character)stringOrChar).in((str)params.get(0));
					}catch(Exception e1){
						try{
							return x.String((String)stringOrChar).in((str)params.get(0));
						}catch(Exception e2){
							throw new IllegalArgumentException("Could not intepret the input value as a character ort a string");
						}
					}
				}
			}
		}).params(string);
	}
	
	/**
	 * Creates and returns new {@link LambdaPredicate} for a given {@link String} lambda expression
	 * 
	 * Example 1: Predicate\<Object\> isNonEmptyLongString = x.lambdaP("x : f0(x) > 5 || f1(x) != 0",x.len,x.len);
	 *
	 * @param lambdaExpression	a {@link String} containing a lambda expression
	 * @param functions (optional {@link Function}<Object,?> objects)
	 * @return a LambdaPredicate that realizes the description given in @param lambdaExpression
	 * and using @param functions 
	 *                   
	 */
	@SafeVarargs
	public static LambdaPredicate lambdaP(String lambdaExpression,Function<Object,?>... functions){
		return new LambdaPredicate(lambdaExpression,functions);
	}
	public static LambdaPredicate lambdaP(String lambdaExpression){
		return new LambdaPredicate(lambdaExpression);
	}
	
	/**
	 * Creates and returns new {@link LambdaFunction} for a given {@link String} lambda expression
	 * 
	 * Example 1: Function<Object,Integer> increment = x.\<Integer\>lambdaF("x : x + 1");
	 * 
	 * @param lambdaExpression
	 * @param functions 		functions to use in the lambda expressions (optional)
	 * @param <O>		 		output type of the LambdaFunction
	 * @return a LambdaFunction that realizes the description given in @param lambdaExpression
	 * and using @param functions 
	 *                   
	 */
	@SafeVarargs
	public static <O> LambdaFunction<O> lambdaF(String lambdaExpression,Function<Object,?>... functions){
		return new LambdaFunction<O>(lambdaExpression,functions);
	}
	public static <O> LambdaFunction<O> lambdaF(String lambdaExpression){
		return new LambdaFunction<O>(lambdaExpression);
	}
	
	/**
	 * Chains functions in the following way: ..f2(f1(f0(x))) where x is the input
	 * of the chained function and f0, f1, ... are the functions to apply to x in chain.
	 * 
	 * Example 1: Function<Integer,Integer> incrementAndMultiplyBy5 = x.chain(increment,x.\<Integer\>lambdaF("x : x * 5"));
	 *
	 * @param functions	functions to chain (optional)
	 * @param <I,O>		input and output types for the final chained function
	 * @result 			a {@link Function} that chains the input functions
	 *                   
	 */
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
	
	/**
	 * Returns an {@link java.lang.Iterable} that chains input iterables
	 * of the chained function and f0, f1, ... are the functions to apply to x in chain.
	 *
	 * @param iterable0	the first iterable
	 * @param iterable1	the second iterable
	 * @param iterables	the remaining iterables
	 * @param <T>		the type of values returned by the input iterables
	 * @return 			an {@link java.lang.Iterable}<T>
	 */
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
				};
			}
		};
		return generator;
	}
	
	/**
	 * Returns an {@link java.lang.Iterable} that iterates over the characters of
	 * the sequence of input strings, starting from the first character of the string0
	 *
	 * @param string0		the first string
	 * @param string1		the second string
	 * @param otherStrings	the remaining strings
	 * @param <T>			the type of values returned by the input iterables
	 * @return 				an {@link java.lang.Iterable}<String>
	 */
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
	
	/*
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
	*/

	/**
	 * Factory method that returns an {@link tuple} of one element.
	 *
	 * @param value			a value of any type
	 * @return 				a {@link tuple} that contains @param value
	 */
	public static tuple tupleOf(tuple value) {
		return value.copy();
    }
	
	/**
	 * Factory method that returns an {@link tuple} of one element.
	 *
	 * @param value			a value of any type
	 * @return 				a {@link tuple} that contains @param value
	 */
	public static <T0> tuple tuple(T0 value) {
        return tuple1.valueOf(value);
    }

	/**
	 * Factory method that returns an {@link tuple} of two elements.
	 *
	 * @param value0		a value of any type
	 * @param value1		a value of any type (can be different of the type of value0)
	 * @return 				a {@link tuple} that contains @param value0 as the first dimension
	 * 						and @param value1 as the second dimension
	 */
    public static <T0, T1> tuple tuple(T0 value0, T1 value1) {
        return tuple2.valueOf(value0, value1);
    }

	/**
	 * Factory method that returns an {@link tuple} of three elements.
	 *
	 * @param value0		a value of any type
	 * @param value1		a value of any type (can be different of the type of value0)
	 * @param value2		a value of any type (can be different of the types of value0 and value1)
	 * @return 				a {@link tuple} that contains @param value0 as the first dimension,
	 * 						@param value1 as the second dimension, and @param value2 as the third dimension
	 */
    public static <T0, T1, T2> tuple tuple(T0 value0, T1 value1, T2 value2) {
        return tuple3.valueOf(value0, value1, value2);
    }
    
	/**
	 * Factory method that returns an {@link tuple} of three elements.
	 *
	 * @param value0		a value of any type
	 * @param value1		a value of any type (can be different of the type of value0)
	 * @param value2		a value of any type (can be different of the types of value0 and value1)
	 * @param value3		a value of any type (can be different of the types of value0, value1, and value3)
	 * @return 				a {@link tuple} that contains @param value0 as the first dimension,
	 * 						@param value1 as the second dimension, @param value2 as the third dimension,
	 * 						and @param value3 as the fourth dimension
	 */
    public static <T0, T1, T2, T3> tuple tuple(T0 value0, T1 value1, T2 value2, T3 value3) {
        return tuple4.valueOf(value0, value1, value2, value3);
    }

	/**
	 * Factory method that returns an new empty {@link dict}.
	 *
	 */
	public static <T> dict<T> dict(){
		return new dict<T>();
	}
	
	/**
	 * Factory method that returns an new {@link dict} filled using the
	 * values of the input {@link Iterable} of tuples.
	 * 
	 * The input tuples have to be instances of {@link tuple2}<{@link String},T>. The first dimension
	 * of each tuple is used as key and the second as value for the dict.
	 *
	 * @input 		tuples
	 * @input <T> 	any type
	 *
	 */
	public static <T> dict<T> dict(Iterable<tuple> tuples){
		dict<T> dict = new dict<T>();
		dict.update(tuples);
		return dict;
	}
	
	/**
	 * Factory method that returns an new {@link dict} filled using the
	 * values of the input array of tuples.
	 * 
	 * The input tuples have to be instances of {@link tuple2}<{@link String},T>. The first dimension
	 * of each tuple is used as key and the second as value for the dict.
	 *
	 * @input 		zero or more instances of {@link tuple2}<{@link String},T>
	 * @input <T> 	any type
	 *
	 */ 
	@SafeVarargs
	public static <T> dict<T> dict(tuple... tuples){
		dict<T> dict = new dict<T>();
		dict.update(tuples);
		return dict;
	}
		
	/**
	 * Factory method that returns an new {@link dict} filled using the
	 * values of the input {@link Map}<String,T>.
	 * 	 *
	 * @input 		map	a {@link Map} of type <String,T>
	 * @input <T> 	any type
	 *
	 */  
	public static <T> dict<T> dict(Map<String,T> map){
		return new dict<T>(map);
	}
	
	/**
	 * Factory method that returns an new {@link dict} filled using the
	 * values of the input another {@link dict}.
	 * 	 *
	 * @input dict	a {@link dict}<T>.
	 * @input <T> 	any type
	 *
	 */   
	public static <T> dict<T> dict(dict<T> dict){
		return dict.copy();
	}
	
	/**
	 * Factory method that returns an new {@link DefaultDict} of type @param defaultType.
	 * 
	 * DefaultDict in xpresso works similarly to Python's defaultdict.
	 * 
	 * @see the page about Python's <a href="https://docs.python.org/2/library/collections.html#collections.defaultdict">collections</a>.
	 * 
	 * @input defaultType	a Class<T> object
	 * @input <T> 			any type
	 *
	 */   
	public static <T> DefaultDict<T> DefaultDict(Class<T> defaultType){
		return new DefaultDict<T>(defaultType);
	}
	
	/**
	 * Factory method that returns an new {@link OrderedDict} from an input {@link Iterable}.
	 * 
	 * The input iterable object has to be an Iterable<tuple> or
	 * two dimensions or an Iterable<tuple2<String,T>> or an OrderedDict
	 * 
	 * OrderedDict in xpresso works similarly to Python's OrderedDict.
	 * 
	 * @see the page about Python's <a href="https://docs.python.org/2/library/collections.html#collections.OrderedDict">collections</a>.
	 * 
	 * @input defaultType	a Class<T> object
	 * @input <T> 			any type
	 *
	 */   
	public static <T> OrderedDict<T> OrderedDict(Iterable<?> iterable){
		return new OrderedDict<T>(iterable);
	}
	
	/**
	 * Factory method that returns an new empty {@link OrderedDict}.
	 * 
	 * OrderedDict in xpresso works similarly to Python's OrderedDict.
	 * 
	 * @see the page about Python's <a href="https://docs.python.org/2/library/collections.html#collections.OrderedDict">collections</a>.
	 * 
	 * @input defaultType	a Class<T> object
	 * @input <T> 			any type
	 *
	 */   
	public static <T> OrderedDict<T> OrderedDict(){
		return new OrderedDict<T>();
	}
	
	/**
	 * Factory method that returns an new {@link Bag} obtained by 
	 * counting values in the input iterable.
	 * 
	 * {@link Bag} in xpresso works similarly to Python's Counter object.
	 * 
	 * @see the page about Python's <a href="https://docs.python.org/2/library/collections.html#collections.Counter">collections</a>.
	 * 
	 * @input iterable	an {@link Iterable} of type T
	 * @input <T> 		any type
	 *
	 */   
	public static <T> Bag<T> Bag(Iterable<T> iterable){
		return new Bag<T>(iterable);
	}
	
	/**
	 * Factory method that returns an new {@link Bag} obtained by 
	 * counting characters in the input string.
	 * 
	 * {@link Bag} in xpresso works similarly to Python's Counter object.
	 * 
	 * @see the page about Python's <a href="https://docs.python.org/2/library/collections.html#collections.Counter">collections</a>.
	 * 
	 * @input string	a {@link String}
	 *
	 */   
	public static Bag<String> Bag(String string){
		return new Bag<String>(x.str(string));
	}
	
	/**
	 * Factory method that returns an new {@link Bag} obtained by 
	 * counting strings in the input array.
	 * 
	 * {@link Bag} in xpresso works similarly to Python's Counter object.
	 * 
	 * @see the page about Python's <a href="https://docs.python.org/2/library/collections.html#collections.Counter">collections</a>.
	 * 
	 */   
	public static Bag<String> Bag(String string0,String string1,String... otherStrings){
		Iterable<String> iterable = Helpers.newArrayList(string0,string1,otherStrings);
		return new Bag<String>(iterable);
	}
	
	/**
	 * Factory method that returns an new {@link Bag} obtained by 
	 * counting numbers in the input array.
	 * 
	 * {@link Bag} in xpresso works similarly to Python's Counter object.
	 * 
	 * @see the page about Python's <a href="https://docs.python.org/2/library/collections.html#collections.Counter">collections</a>.
	 * 
	 */ 
	public static Bag<Number> Bag(Number number0,Number number1,Number... otherNumbers){
		Iterable<Number> iterable = Helpers.newArrayList(number0,number1,otherNumbers);
		return new Bag<Number>(iterable);
	}
	
	/**
	 * Factory method that returns an new {@link Bag} obtained by 
	 * counting bolleans in the input array.
	 * 
	 * {@link Bag} in xpresso works similarly to Python's Counter object.
	 * 
	 * @see the page about Python's <a href="https://docs.python.org/2/library/collections.html#collections.Counter">collections</a>.
	 * 
	 */ 
	public static Bag<Boolean> Bag(Boolean boolean0,Boolean boolean1,Boolean... otherBooleans){
		Iterable<Boolean> iterable = Helpers.newArrayList(boolean0,boolean1,otherBooleans);
		return new Bag<Boolean>(iterable);
	}
	
	/**
	 * Factory method that returns an new {@link Bag} obtained from the input {@link Map}. 
	 * The keys in the input map will used as the new Bag's counted elements, while
	 * the Integer values of the input Map will be used as the respective counts
	 * for those counted elements.
	 * 
	 * {@link Bag} in xpresso works similarly to Python's Counter object.
	 * 
	 * @see the page about Python's <a href="https://docs.python.org/2/library/collections.html#collections.Counter">collections</a>.
	 * 
	 */ 
	public static <T> Bag<T> Bag(Map<T,Integer> map){
		return new Bag<T>(map);
	}
	
	/**
	 * Returns the length of the argument, usually it is the number of elements it contains. 
	 * 
	 * @input value	any Object
	 * 
	 */ 
	public static <T> int len(Object value){
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
	
	/**
	 * Returns the time in seconds since the epoch as a floating point number. 
	 * 
	 */
	public static double time(){
		return Time.time();
	}
	
	/**
	 * Returns the running Timer object. This object can be then stopped and printed:
	 * 
	 *  Timer timer = x.Timer();
	 *  //do something
	 *  x.print(timer.stop());
	 *  
	 *  Console: 0.133s
	 * 
	 */
	public static Timer Timer(){
		return new Timer();
	}
	
	/*
	 * Starts the static {@link java.lang.ThreadLocal} timer of the {@link ThreadTimer} class.
	 * This timer can be started, stopped and printed.
	 * 
	 * The difference between x.timer based on the {@link ThreadTimer}
	 * and x.Timer() based on {@link Timer} it that if you use x.timer
	 * you don't have to 
	 * create any instance of any object. You can simply use the static
	 * reference x.timer.start() and x.timer.stop();
	 * The timer x.timer is global for the current thread and independent of
	 * any other thread. 
	 * 
	 * Example:
	 * 
	 *  x.timer.start();
	 *  //do something
	 *  x.print(x.timer.stop());
	 *  
	 *  Console: 0.133s
	 * 
	 */
	public static ThreadTimer timer = new ThreadTimer(); 
	
	/**
	 * An instance of a {@link Stemmer} object.
	 * 
	 * Example:
	 * 
	 * String stem = x.Stemmer.stem("Worker");
	 * 
	 * Example:
	 * 
	 * String stem = x.Stemmer.stem("Marcher", "french");
	 *  
	 * 
	 */
	public static TokenStatic Token = new TokenStatic();
	
	/**
	 * Return the double value number rounded to ndigits digits after the decimal point.
	 * If ndigits is omitted, it defaults to zero. The result is a double.
	 */
	public static double round(double number, int nDigits){
		return (double)Math.round(number * Math.pow(10, nDigits)) / Math.pow(10, nDigits);
	}
	
	/**
	 * Return the double value number rounded to ndigits digits after the decimal point.
	 * If ndigits is omitted, it defaults to zero. The result is a double.
	 */
	public static double round(double number){
		return (double)Math.round(number * Math.pow(10, 0)) / Math.pow(10, 0);
	}
	
	/**
	 * Only returns true if all elements of the input {@link Iterable} are true. 
	 * 
	 */
	public static boolean all(Iterable<Boolean> iterable){
	    for(Boolean element : iterable){
	    	if (x.isFalse(element)){
	    		return false;
	    	}
	    }
	    return true;
	}
	
	/**
	 * Only returns true if the input {@link Predicate}, when applied 
	 * to each element of the input {@link Iterable}, returns true. 
	 * 
	 * @param iterable		an Iterable of type <T>
	 * @param <T>			any type
	 * @param predicate		any {@link Predicate}
	 * 
	 */
	public static <T> boolean all(Iterable<T> iterable, Predicate<Object> predicate){
	    for(T element : iterable){
	    	if (x.isFalse(element)){
	    		return false;
	    	}
	    }
	    return true;
	}
	
	/**
	 * Returns true if at least one element of the input {@link Iterable} is true. 
	 * 
	 */
	public static boolean any(Iterable<Boolean> iterable){
	    for(Boolean element : iterable){
	    	if (x.isTrue(element)){
	    		return true;
	    	}
	    }
	    return false;
	}
	
	/**
	 * Returns true if the input {@link Predicate}, when applied 
	 * to at least one element of the input {@link Iterable}, returns true. 
	 * 
	 * @param iterable		an Iterable of type <T>
	 * @param <T>			any type
	 * @param predicate		any {@link Predicate}
	 * 
	 */
	public static <T> boolean any(Iterable<T> iterable, Predicate<Object> predicate){
	    for(T element : iterable){
	    	if (x.isTrue(element)){
	    		return true;
	    	}
	    }
	    return false;
	}
	
	
	/**
	 * Returns the biggest element of the input {@link Iterable}. 
	 * 
	 * @param iterable		an Iterable of type <T>
	 * @param <T>			any type
	 * 
	 */
	public static <T extends Comparable<? super T>> T max(Iterable<T> iterable){
		return x.<T>reduce(iterable, x.<T>max());
	}
	
	/**
	 * Returns the biggest element among the input values. 
	 * 
	 * @param value0, value1, ...		values of type <T>
	 * @param <T>						any type
	 * 
	 */
	@SafeVarargs
	public static <T extends Comparable<? super T>> T max(T value0,T value1,T... values){
		return max(Helpers.newArrayList(value0,value1,values));
	}
	
	/**
	 * Returns the smallest element of the input {@link Iterable}. 
	 * 
	 * @param iterable		an Iterable of type <T>
	 * @param <T>			any type
	 * 
	 */
	public static <T extends Comparable<? super T>> T min(Iterable<T> iterable){
		return x.<T>reduce(iterable, x.<T>min());
	}
	
	/**
	 * Returns the smallest element among the input values. 
	 * 
	 * @param value0, value1, ...		values of type <T>
	 * @param <T>						any type
	 * 
	 */
	@SafeVarargs
	public static <T extends Comparable<? super T>> T min(T value0,T value1,T... values){
		return min(Helpers.newArrayList(value0,value1,values));
	}
	
	
	/**
	 * Sums the elements of the input {@link Iterable} and returs the sum. 
	 * 
	 * @param iterable		an Iterable of Numbers
	 * 
	 */
	public static <T extends Number> T sum(Iterable<T> iterable){
		return x.<T>reduce(iterable, x.<T>add());
	}
	
	/**
	 * Sums the elements of the input {@link Iterable} and returs the sum. 
	 * 
	 * @param iterable		an Iterable of Numbers
	 * 
	 */
	public static <T extends Number> T avg(Iterable<T> iterable){
		x.assertNotEmpty(iterable);
		double result = (x.<T>reduce(iterable, x.<T>add())).doubleValue()/x.len(iterable);
		Class<?> cls = iterable.iterator().next().getClass();
		if(cls.equals(Double.class)){
			return (T)(Object)result;
		}else if(cls.equals(Float.class)){
			return (T)(Object)result;
		}else{
			return (T)(Object)(int)Math.round(result);
		}
	}
	
	/**
	 * Returns the first element of the input {@link Iterable}. 
	 * 
	 * @param iterable		an Iterable
	 * @param <T>			any type
	 * 
	 */
	public static <T> T first(Iterable<T> iterable){
		x.assertNotEmpty(iterable);
		return iterable.iterator().next();
	}
	
	/**
	 * Returns the last element of the input {@link Iterable}. 
	 * 
	 * @param iterable		an Iterable
	 * @param <T>			any type
	 * 
	 */
	public static <T> T last(Iterable<T> iterable){
		x.assertNotEmpty(iterable);
		return x.list(iterable).sliceFrom(-1).toScalar();
	}
	
	/**
	 * Returns the {@link Iterator} over {@link Integer} elements starting from @param min
	 * with the increment between the values given by @param step.
	 * 
	 * The last returned value by the Iterator will be the biggest {@link Integer} less than @param max. 
	 * 
	 * @param min		start value
	 * @param max		end value (not returned)
	 * @param step		the increment
	 * 
	 */
	public static Iterable<Integer> count(final int min,final Integer max,final int step){
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
	
	/**
	 * Returns the {@link Iterable} over {@link Integer} elements starting from @param min
	 * with the increment of 1 between the values.
	 * 
	 * The last returned value by the Iterator will be the biggest {@link Integer} less than @param max. 
	 * 
	 * @param min		start value
	 * @param max		end value (not returned)
	 * 
	 */
	public static Iterable<Integer> count(int min,int max){
		return count(min,max,1);
	}
	
	/**
	 * Returns the {@link Iterable} over {@link Integer} elements starting from 0
	 * with the increment of 1 between the values.
	 * 
	 * The last returned value by the Iterator will be @param max - 1. 
	 * 
	 * @param max		end value (not returned)
	 * 
	 */
	public static Iterable<Integer> countTo(int max){
		return count(0,max,1);
	}
	
	/**
	 * Returns an infinite {@link Iterable} over {@link Integer} elements
	 * starting from @param min with the increment of 1 between the values. 
	 * 
	 * @param min	start value
	 * 
	 */
	public static Iterable<Integer> countFrom(int min){
		return count(min,null,1);
	}

	/**
	 * Returns the {@link Iterable} over {@link Double} elements starting from @param min
	 * with the increment between the values given by @param step.
	 * 
	 * The last returned value by the Iterator will be the biggest {@link Double} less than @param max. 
	 * 
	 * @param min		start value
	 * @param max		end value (not returned)
	 * @param step		the increment
	 * 
	 */
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
	
	/**
	 * Returns the {@link Iterable} over {@link Integer} elements starting from 0
	 * with the increment between the values given by @param step.
	 * 
	 * The last returned value by the Iterator will be the biggest {@link Integer} less than
	 * the length of the input {@link Iterable}. 
	 * 
	 * @param min		start value
	 * @param max		end value (not returned)
	 * @param step		the increment
	 * 
	 */
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
	
	/**
	 * Returns the {@link Iterable} over {@link Integer} elements starting from 0
	 * with the increment of 1 between the values.
	 * 
	 * The last returned value by the Iterator will be the biggest {@link Integer} less than
	 * the length of the input {@link Iterable}. 
	 * 
	 * @param min		start value
	 * @param max		end value (not returned)
	 * @param step		the increment
	 * 
	 */
	public static <T> Iterable<Integer> count(final Iterable<T> iterable){
		return count(iterable,1);
	}
	
	/**
	 * Applies the input {@link Function} to each element of the input {@link Iterable}
	 * and returns a new {@link Iterable} containing the Functions' outputs of each element.
	 * 
	 * Preserves the order of elements.
	 *  
	 * @param iterable	
	 * @param <I,O>		input and output element types
	 * 
	 */
	public static <I,O> Iterable<O> map(Iterable<I> iterable,Function<Object,O> function){
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
	
	/**
	 * Applies the input {@link Function} to each element of the input {@link Iterable}
	 * and returns a new {@link Iterable} containing the Functions' outputs of each element.
	 * 
	 * Preserves the order of elements.
	 *  
	 * @param iterable	
	 * @param <I>		input and output element types
	 * 
	 */
	public static <I> I reduce(Iterable<I> iterable,Function<tuple2<I,I>,I> function){
		x.assertTrue(x.len(iterable) > 1);
		Iterator<I> iter = iterable.iterator();
		I output = function.apply(tuple2.valueOf(iter.next(), iter.next()));
		while(iter.hasNext()){
			output = function.apply(tuple2.valueOf(output, iter.next()));
		}
		return output;
	}
	
	/**
	 * Applies the input {@link Predicate} to each element of the input {@link Iterable}
	 * and returns a new {@link Iterable} containing those elements of the input {@link Iterable}
	 * for which the predicate is true.
	 * 
	 * Preserves the order of elements.
	 *  
	 * @param 		iterable	
	 * @param <T>	element types of the iterable
	 * 
	 */
	public static <T> Iterable<T> filter(Iterable<T> iterable,Predicate<T> predicate){
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
	
	/**
	 * Returns a set containing all unique elements from all input {@link Iterable}s.
	 *  	
	 * @param iterables	an array of {@link Iterable}s of type <T>
	 * @param <T>	element type of each input iterable
	 * 
	 */
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
	
	/*
	//high-level difference of sets
	public static <T> set<T> difference(set<T> set0,set<T> set1){
		set<T> newSet = new set<T>(set0);
		return newSet.difference(set1);
	}
	*/
	
	/**
	 * Returns an {@link Iterable} of {@link tuple}s (int index, T value) from the input {@link Iterable} of type T.
	 * 
	 * enumerate in xpresso works similarly to Python's enumerate.
	 * 
	 * @see the page about Python's <a href="https://docs.python.org/2/library/functions.html#enumerate">enumerate</a>
	 * 		for more details.
	 * 
	 * @input iterable		an {@link Iterable} of type T
	 * @input startCount	the value of index of the first tuple
	 * @input <T> 			any type
	 *
	 */   
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
	
	
	/**
	 * Returns an {@link Iterable} of {@link tuple}s (int index, T value) from the input {@link Iterable} of type T.
	 * 
	 * enumerate in xpresso works similarly to Python's enumerate.
	 * 
	 * @see the page about Python's <a href="https://docs.python.org/2/library/functions.html#enumerate">enumerate</a>
	 * 		for more details.
	 * 
	 * @input iterable		an {@link Iterable} of type T
	 * @input <T> 			any type
	 *
	 */  
	public static <T> Iterable<tuple2<Integer,T>> enumerate(Iterable<T> iterable){
		return enumerate(iterable,0);
	}
	
	/**
	 * Returns an {@link Iterable} of {@link tuple}s (int index, T value) from the input values.
	 * 
	 * enumerate in xpresso works similarly to Python's enumerate.
	 * 
	 * @see the page about Python's <a href="https://docs.python.org/2/library/functions.html#enumerate">enumerate</a>
	 * 		for more details.
	 * 
	 * @input iterable		an {@link Iterable} of type T
	 * @input <T> 			any type
	 *
	 */  
	@SafeVarargs
	public static <T> Iterable<tuple2<Integer,T>> enumerate(T value0, T value1, T... values){
		List<T> lst = Helpers.newArrayList(value0,value1,values);
		return enumerate(lst,0);
	}
	
	/**
	 * Returns an {@link Iterable} of {@link tuple}s (int index, String character) for the input string,
	 * thet iterates over the characters of the string.
	 * 
	 * enumerate in xpresso works similarly to Python's enumerate.
	 * 
	 * @see the page about Python's <a href="https://docs.python.org/2/library/functions.html#enumerate">enumerate</a>
	 * 		for more details.
	 * 
	 * @input string		a {@link String} object
	 * @input startCount	the value if the index of the first tuple in the output Iterable
	 * @input <T> 			any type
	 *
	 */  
	public static Iterable<tuple2<Integer,String>> enumerate(String string, int startCount){
		str str = x.str(string);
		return enumerate(str,startCount);
	}
	
	/**
	 * Returns an {@link Iterable} of {@link tuple}s (int index, String character) for the input string,
	 * thet iterates over the characters of the string.
	 * 
	 * enumerate in xpresso works similarly to Python's enumerate.
	 * 
	 * @see the page about Python's <a href="https://docs.python.org/2/library/functions.html#enumerate">enumerate</a>
	 * 		for more details.
	 * 
	 * @input string		a {@link String} object
	 * @input <T> 			any type
	 *
	 */  
	public static Iterable<tuple2<Integer,String>> enumerate(String string){
		return enumerate(string,0);
	}
	
	/**
	 * Returns an {@link Iterable} of {@link tuple}s (int index, String character) for the input array,
	 * thet iterates over the elements of the array.
	 * 
	 * enumerate in xpresso works similarly to Python's enumerate.
	 * 
	 * @see the page about Python's <a href="https://docs.python.org/2/library/functions.html#enumerate">enumerate</a>
	 * 		for more details.
	 * 
	 * @input string		a {@link String} object
	 * @input startCount	the value if the index of the first tuple in the output Iterable
	 * @input <T> 			any type
	 *
	 */  
	public static <T> Iterable<tuple2<Integer,T>> enumerate(T[] array, int startCount){
		return enumerate(Helpers.newArrayList(array),startCount);
	}
	
	/**
	 * Returns an {@link Iterable} of {@link tuple}s (int index, String character) for the input array,
	 * thet iterates over the elements of the array.
	 * 
	 * enumerate in xpresso works similarly to Python's enumerate.
	 * 
	 * @see the page about Python's <a href="https://docs.python.org/2/library/functions.html#enumerate">enumerate</a>
	 * 		for more details.
	 * 
	 * @input string		a {@link String} object
	 * @input <T> 			any type
	 *
	 */  
	public static <T> Iterable<tuple2<Integer,T>> enumerate(T[] array){
		return enumerate(Helpers.newArrayList(array),0);
	}	
	
	/**
	 * 
	 * Makes an {@link Iterable} returning elements from the input iterable and saving a copy of each.
	 * When the input  iterable is exhausted, return elements from the saved copy. Repeats maxCount times.
	 * 
	 * Equivalent to: x.cycle(x.listOf("A","B","C","D") --> A B C D A B C D A B C D ...
	 * 
	 * @param iterable
	 * @param maxCount
	 * @return
	 */
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
	
	/**
	 * 
	 * Makes an {@link Iterable} returning elements from the input iterable and saving a copy of each.
	 * When the input  iterable is exhausted, return elements from the saved copy. Repeats indifinitely.
	 * 
	 * Equivalent to: x.cycle(x.listOf("A","B","C","D") --> A B C D A B C D A B C D ...
	 * 
	 * @param iterable
	 * 
	 */
	public static <T> Iterable<T> cycle(final Iterable<T> iterable){
		return cycle(iterable,null);
	}
	
	/**
	 * 
	 * Makes an {@link Iterable} returning characters from the input string and saving a copy of these characters.
	 * When the above iterable is exhausted, return elements from the saved copy. Repeats indifinitely.
	 * 
	 * Equivalent to: x.cycle("ABCD") --> A B C D A B C D A B C D ...
	 * 
	 * @param string
	 * 
	 */
	public static Iterable<String> cycle(final String string){
		return cycle(x.str(string));
	}

	/**
	 * 
	 * Makes an {@link Iterable} returning characters from the input string and saving a copy of these characters.
	 * When the above iterable is exhausted, return elements from the saved copy. Repeats maxCount times.
	 * 
	 * Equivalent to: x.cycle("ABCD") --> A B C D A B C D A B C D ...
	 * 
	 * @param string
	 * @param maxCount
	 * 
	 */
	public static Iterable<String> cycle(final String string, final int maxCount){
		return cycle(x.str(string),maxCount);
	}
	
	/**
	 * Make an {@link Iterable} that returns the input value of type T
	 * over and over again for maxCount times.
	 * 
	 * @param value
	 * @param maxCount
	 * @return
	 */
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

	/**
	 * Make an infinite {@link Iterable} that returns the input value of type T
	 * over and over again.
	 * 
	 * @param value
	 * @return
	 */
	public static <T> Iterable<T> repeat(final T value){
		return repeat(value, null);
	}
	
	/**
	 * Sorts the values of the input {@link Iterable} of type T according to
	 * the evaluation function {@link Function} which is applied to each element
	 * of the input Iterable.
	 * 
	 * @param iterable	
	 * @param function	a function that takes an object and retursn a {@link Comparable}
	 * @param reverse	if true, then the values are sorted from biggest to smallest 
	 * @return a sorted {@link Iterable} of type T
	 * 
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> Iterable<T> sorted(Iterable<T> iterable,Function<Object,? extends Comparable<?>> function,boolean reverse){
		ArrayList<KeyValue<?,T>> keyValues = new ArrayList<KeyValue<?,T>>();
		for (T element: iterable){
			if(function != null){
				keyValues.add(new KeyValue(function.apply(element),element));	
			}else{
				keyValues.add(new KeyValue((Comparable<?>)element,element));
			}
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
	
	/**
	 * Sorts the values of the input {@link Iterable} of type T according to
	 * the evaluation function {@link Function} which is applied to each element
	 * of the input Iterable, from smallest to biggest.
	 * 
	 * @param iterable	
	 * @param function	a function that takes an object and retursn a {@link Comparable} 
	 * @return a sorted {@link Iterable} of type T
	 * 
	 */
	public static <T> Iterable<T> sorted(Iterable<T> iterable,Function<Object,? extends Comparable<?>> function){
		return sorted(iterable,function,false);
	}
	
	/**
	 * Sorts the values of the input {@link Iterable} of type T according to
	 * the function {@link T#compareTo(Object)} of type T
	 * from smallest to biggest (if reverse is false) or from bigegst to smallest
	 * (if reverse is true).
	 * 
	 * @param iterable	
	 * @param reverse	if true, then the values are sorted from biggest to smallest 
	 * @return a sorted {@link Iterable} of type T
	 * 
	 */
	public static <T extends Comparable<T>> Iterable<T> sorted(Iterable<T> iterable, boolean reverse){
		return sorted(iterable,null,reverse);
	}
	
	/**
	 * Sorts the values of the input {@link Iterable} of type T according to
	 * the function {@link T#compareTo(Object)} of type T
	 * from smallest to biggest.
	 *  
	 * @param iterable	
	 * @return a sorted {@link Iterable} of type T
	 * 
	 */
	public static <T extends Comparable<T>> Iterable<T> sorted(Iterable<T> iterable){
		return sorted(iterable,null,false);
	}
	
	/**
	 * Sorts the characters of the input {@link str} according to
	 * the input evaluation Function applied to each character
	 * from smallest to biggest (if reverse is false) or from bigegst to smallest
	 * (if reverse is true).
	 * @param str	a {@link str} string
	 * @param reverse	if true, then the values are sorted from biggest to smallest
	 * @return a sorted {@link str} of type T
	 * 
	 */
	public static str sorted(str str,Function<Object,? extends Comparable<?>> function,boolean reverse){
		return x.str(sorted(str.toArrayList(),function,reverse));
	}
	
	/**
	 * Sorts the characters of the input {@link str}
	 * according to the input evaluation {@link Function} applied to each character
	 * from smallest to biggest.
	 * @param str	a {@link str} string
	 * @param reverse	if true, then the values are sorted from biggest to smallest
	 * @return a sorted {@link str} of type T
	 * 
	 */
	public static str sorted(str str,Function<Object,? extends Comparable<?>> function){
		return x.str(sorted(str.toArrayList(),function,false));
	}
	
	/**
	 * Sorts the characters of the input {@link str} according to {@link String#compareTo(String)}
	 * applied to each chracter of the input {@link str}
	 * @param str	a {@link str} string
	 * @return a sorted {@link str} of type T
	 * 
	 */
	public static str sorted(str str){
		return x.str(sorted(str.toArrayList(),null,false));
	}
	
	/**
	 * Sorts in the given order (directe or reversed) the characters of the input {@link str}
	 * according to {@link String#compareTo(String)}
	 * applied to each chracter of the input {@link str}
	 * @param str	a {@link str} string
	 * @param reverse	
	 * @return a sorted {@link str} of type T
	 * 
	 */
	public static str sorted(str str, boolean reverse){
		return x.str(sorted(str.toArrayList(),null,reverse));
	}
	
	/**
	 * Reverse the order of elements of the input {@link Iterable}
	 * @param iterable
	 * 
	 */
	public static <T> Iterable<T> reversed(Iterable<T> iterable){
		ArrayList<T> newArrayList = Helpers.newArrayList(iterable);
		Collections.reverse(newArrayList);
		return x.list(newArrayList);

	}
	
	/**
	 * Reverse the order of charcters in the input {@link str}
	 * @param str
	 * 
	 */
	public static str reversed(str str) throws IOException{
		return x.str(reversed(Helpers.newArrayList(str)));
	}
	
	/**
	 * Returns the N largest elements of the input {@link Iterable}.
	 * @param iterable
	 * @param N
	 * 
	 */
	public static <T extends Comparable<T>> Iterable<T> largestN(Iterable<T> iterable, int N){
		return x.list(sorted(iterable,true)).sliceTo(N);
	}
	
	/**
	 * Returns the N smallest elements of the input {@link Iterable}.
	 * @param iterable
	 * @param N
	 * 
	 */
	public static <T extends Comparable<T>> Iterable<T> smallestN(Iterable<T> iterable, int N){
		return x.list(sorted(iterable)).sliceTo(N);
	}
	
	/**
	 * Only returns true if the input {@link Iterable} contains the value.
	 * @param iterable
	 * @param value
	 * 
	 */
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

	/**
	 * This method returns a list of tuples, where the i-th tuple contains the i-th element
	 * from each of the argument {@link Iterable}s. The returned list is truncated in length
	 * With a single sequence argument, it returns a list of 1-tuples. With no arguments, it returns an empty list.
	 * @param iterable0 [, iterable1[, iterable2]...]
	 * 
	 * Example:
	 * 
 	 * x.print(zip(x.listOf(1,3,5),x.listOf(2,4,6)))
 	 * 
 	 * Console:
     * [(1, 2), (3, 4), (5, 6)]
	 * 
	 */
	public static list<tuple> zip(){
		return x.list();
	}
	
	/**
	 * This method returns a list of tuples, where the i-th tuple contains the i-th element
	 * from each of the argument {@link Iterable}s. The returned list is truncated in length
	 * With a single sequence argument, it returns a list of 1-tuples. With no arguments, it returns an empty list.
	 * @param iterable0 [, iterable1[, iterable2]...]
	 * 
	 * Example:
	 * 
 	 * x.print(zip(x.listOf(1,3,5),x.listOf(2,4,6)))
 	 * 
 	 * Console:
     * [(1, 2), (3, 4), (5, 6)]
	 * 
	 */
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
	
	/**
	 * This method returns a list of tuples, where the i-th tuple contains the i-th element
	 * from each of the argument {@link Iterable}s. The returned list is truncated in length
	 * With a single sequence argument, it returns a list of 1-tuples. With no arguments, it returns an empty list.
	 * @param iterable0 [, iterable1[, iterable2]...]
	 * 
	 * Example:
	 * 
 	 * x.print(zip(x.listOf(1,3,5),x.listOf(2,4,6)))
 	 * 
 	 * Console:
     * [(1, 2), (3, 4), (5, 6)]
	 * 
	 */
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
	
	/**
	 * This method returns a list of tuples, where the i-th tuple contains the i-th element
	 * from each of the argument {@link Iterable}s. The returned list is truncated in length
	 * With a single sequence argument, it returns a list of 1-tuples. With no arguments, it returns an empty list.
	 * @param iterable0 [, iterable1[, iterable2]...]
	 * 
	 * Example:
	 * 
 	 * x.print(zip(x.listOf(1,3,5),x.listOf(2,4,6)))
 	 * 
 	 * Console:
     * [(1, 2), (3, 4), (5, 6)]
	 * 
	 */
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
	
	/**
	 * This method returns a list of tuples, where the i-th tuple contains the i-th element
	 * from each of the argument {@link Iterable}s. The returned list is truncated in length
	 * With a single sequence argument, it returns a list of 1-tuples. With no arguments, it returns an empty list.
	 * @param iterable0 [, iterable1[, iterable2]...]
	 * 
	 * Example:
	 * 
 	 * x.print(zip(x.listOf(1,3,5),x.listOf(2,4,6)))
 	 * 
 	 * Console:
     * [(1, 2), (3, 4), (5, 6)]
	 * 
	 */
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
	
	/**
	 * Does the opposite of zip.
	 * 
	 * Example:
	 * l = x.listOf(x.tupleOf(1,2), x.tupleOf(3,4), x.tupleOf(5,6));
 	 * x.print(x.unzip(l))
 	 * 
 	 * Console:
     * ([1, 3, 5], [2, 4, 6])
	 * 
	 * @param iterable of tuples
	 * @param class0 the class of first element of each tuple in the {@link Iterable}
	 * @param class1 the class of second element of each tuple in the {@link Iterable}
	 * @param class2 the class of third element of each tuple in the {@link Iterable}
	 * @param class3 the class of fourth element of each tuple in the {@link Iterable}
	 * @return a tuple of lists. Each list of type class0, class1, ... 
	 */
	@SuppressWarnings("unchecked")
	public static <T0> tuple1<list<T0>> unzip(Iterable<tuple> iterable,Class<T0> class0){
		list<T0> list0 = x.list();
		for (tuple T0__ : iterable){
			list0.append((T0)T0__.get(0));
		}
		return tuple1.valueOf(list0);
	}
	
	/**
	 * Does the opposite of zip.
	 * 
	 * Example:
	 * l = x.listOf(x.tupleOf(1,2), x.tupleOf(3,4), x.tupleOf(5,6));
 	 * x.print(x.unzip(l))
 	 * 
 	 * Console:
     * ([1, 3, 5], [2, 4, 6])
	 * 
	 * @param iterable of tuples
	 * @param class0 the class of first element of each tuple in the {@link Iterable}
	 * @param class1 the class of second element of each tuple in the {@link Iterable}
	 * @param class2 the class of third element of each tuple in the {@link Iterable}
	 * @param class3 the class of fourth element of each tuple in the {@link Iterable}
	 * @return a tuple of lists. Each list of type class0, class1, ... 
	 */
	@SuppressWarnings("unchecked")
	public static <T0,T1> tuple2<list<T0>,list<T1>> unzip(Iterable<tuple> iterable,Class<T0> class0,Class<T1> class1){
		list<T0> list0 = x.list();
		list<T1> list1 = x.list();
		for (tuple T0__T1 : iterable){
			list0.append((T0)T0__T1.get(0));
			list1.append((T1)T0__T1.get(1));
		}
		return tuple2.valueOf(list0,list1);
	}
	
	/**
	 * Does the opposite of zip.
	 * 
	 * Example:
	 * l = x.listOf(x.tupleOf(1,2), x.tupleOf(3,4), x.tupleOf(5,6));
 	 * x.print(x.unzip(l))
 	 * 
 	 * Console:
     * ([1, 3, 5], [2, 4, 6])
	 * 
	 * @param iterable of tuples
	 * @param class0 the class of first element of each tuple in the {@link Iterable}
	 * @param class1 the class of second element of each tuple in the {@link Iterable}
	 * @param class2 the class of third element of each tuple in the {@link Iterable}
	 * @param class3 the class of fourth element of each tuple in the {@link Iterable}
	 * @return a tuple of lists. Each list of type class0, class1, ... 
	 */
	@SuppressWarnings("unchecked")
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
	
	/**
	 * Does the opposite of zip.
	 * 
	 * Example:
	 * l = x.listOf(x.tupleOf(1,2), x.tupleOf(3,4), x.tupleOf(5,6));
 	 * x.print(x.unzip(l))
 	 * 
 	 * Console:
     * ([1, 3, 5], [2, 4, 6])
	 * 
	 * @param iterable of tuples
	 * @param class0 the class of first element of each tuple in the {@link Iterable}
	 * @param class1 the class of second element of each tuple in the {@link Iterable}
	 * @param class2 the class of third element of each tuple in the {@link Iterable}
	 * @param class3 the class of fourth element of each tuple in the {@link Iterable}
	 * @return a tuple of lists. Each list of type class0, class1, ... 
	 */
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
	
	/**
	 * Factory method that creates a new Json object from a valid Json string 
	 * 
	 * Example:
	 * JsonArray arr = x.Json("[["a","b","c"],["d","e","f"]]").parse(); 
	 * 
	 * @param jsonString	a string with a valid Json expression
	 * @return 				a Json object 
	 */
	public static <O> Json<O> Json(String jsonString){
		return new Json<O>(jsonString); 
	}
	
	/**
	 * Factory method that creates a new xpresso {@link Json} object from a {@link Map}. 
	 * 
	 * Example:
	 * Map<String,Integer> map = Helpers.newHashMap();
	 * map.put("a",1);
	 * map.put("b",2);
	 * String jsonString = x.Json(map).toString();
	 * 
	 * x.print(jsonString);
	 * 
	 *  Console {"a": 1, "b": 2}
	 * 
	 * @param jsonString	a Map object
	 * @return 				a Json object 
	 */
	public static <O> Json<O> Json(Map<?,?> o){
		return new Json<O>(o);
	}
	
	/**
	 * Factory method that creates a new xpresso {@link Json} object from an {@link Iterable}. 
	 * 
	 * Example 1:
	 * list<String> lst = x.list("a","b","c");
	 * String jsonString = x.Json(lst).toString();
	 * 
	 * x.print(jsonString);
	 * 
	 *  Console {"a", "b", "c"}
	 *  
	 * Example 2:
	 * dict<Integer> dic = x.list(x.tuple("a":1),x.tuple("b":2));
	 * String jsonString = x.Json(lst).toString();
	 * 
	 * x.print(jsonString);
	 * 
	 *  Console {"a": 1, "b": 2}
	 * 
	 * @param jsonString	an Iterable
	 * @return 				a Json object 
	 */
	public static <O> Json<O> Json(Iterable<?> o){
		return new Json<O>(o);
	}
	
	/**
	 * Factory method that creates a new xpresso {@link Json} object from a {@link tuple}. 
	 * 
	 * Example:
	 * Map<String,Integer> map = Helpers.newHashMap();
	 * map.put("a",1);
	 * map.put("b",2);
	 * String jsonString = x.Json(map).toString();
	 * 
	 * x.print(jsonString);
	 * 
	 *  Console {"a": 1, "b": 2}
	 * 
	 * @param jsonString	a Map object
	 * @return 				a Json object 
	 */	
	public static <O> Json<O> Json(Integer v){
		return new Json<O>(v);
	}
	
	/**
	 * Factory method that creates a new xpresso {@link Json} object from a {@link Integer}. 
	 *  
	 * @param jsonString	a Map object
	 * @return 				a Json object 
	 */	
	public static <O> Json<O> Json(tuple o){
		return new Json<O>(o);
	}
	
	/**
	 * Factory method that creates a new Regex object from a string 
	 * regular expression. I takes an optional parameter flags
	 * (the same values as {@link Pattern}'s flags).
	 * 
	 * Example:
	 * Regex mama = x.Regex("\\bmama\\b", Regex.CASE_INSENSITIVE); 
	 * 
	 * @param regularExpression	a string with a valid Java regular expression
	 * @param flags 			flags (standard {@link Pattern} flags values are accepted)
	 * @return 					a Regex object 
	 */
	public static Regex Regex(String regularExpression,int flags){
		return new Regex(regularExpression,flags);
	}
	
	/**
	 * Factory method that creates a new Regex object from a string 
	 * regular expression. I takes an optional parameter flags
	 * (the same values as {@link Pattern}'s flags).
	 * 
	 * Example:
	 * Regex mama = x.Regex("\\bmama\\b", Regex.CASE_INSENSITIVE); 
	 * 
	 * @param regularExpression	a string with a valid Java regular expression
	 * @param flags 			flags (standard {@link Pattern} flags values are accepted)
	 * @return 					a Regex object 
	 */
	public static Regex Regex(String regularExpression){
		return new Regex(regularExpression,0);
	}
	
	/**
	 * Factory method that creates a new case insensitive
	 * Regex object from a string 
	 * regular expression.
	 * 
	 * Example:
	 * Regex mama = x.RegexNoCase("\\bmama\\b"); 
	 * 
	 * @param regularExpression	a string with a valid Java regular expression
	 * @return 					a Regex object 
	 */
	public static Regex RegexNoCase(String regularExpression){
		return new Regex(regularExpression,Regex.CASE_INSENSITIVE);
	}
	
	/**
	 * Factory method that creates a new Regex object with given optional flags. 
	 * It takes a dictionary instead of the string and works as follows.
	 * 
	 * The translator dict is a dict\<String\> that contains pairs of the form:
	 * 
	 * (regular expressions string, replacement string)
	 * 
	 * When you call the {@link Regex#sub(str)} or the  {@link Regex#sub(String)}
	 * method of such a Regex object, the algorithms replaces each regular expressions string
	 * from the translator dict by the corresponding replacement string.
	 * 
	 * Example:
	 * dict\<String\> happyReplacer = x.dict(x.tuple("bad","good"),x.tuple("small","big"),x.tuple("hard","easy"));
	 *
	 * text = x.Regex(happyReplacer).sub(text); 
	 * 
	 * @param regularExpression	a string with a valid Java regular expression
	 * @return 					a Regex object 
	 */
	public static Regex Regex(dict<String> translator,int flags){
		return new Regex(translator,flags);
	}
	
	/**
	 * Factory method that creates a new Regex object with given optional flags. 
	 * It takes a dictionary instead of the string and works as follows.
	 * 
	 * The translator dict is a dict\<String\> that contains pairs of the form:
	 * 
	 * (regular expressions string, replacement string)
	 * 
	 * When you call the {@link Regex#sub(str)} or the  {@link Regex#sub(String)}
	 * method of such a Regex object, the algorithms replaces each regular expressions string
	 * from the translator dict by the corresponding replacement string.
	 * 
	 * Example:
	 * dict\<String\> happyReplacer = x.dict(x.tuple("bad","good"),x.tuple("small","big"),x.tuple("hard","easy"));
	 *
	 * text = x.Regex(happyReplacer).sub(text); 
	 * 
	 * @param regularExpression	a string with a valid Java regular expression
	 * @return 					a Regex object 
	 */
	public static Regex Regex(dict<String> translator){
		return new Regex(translator,0);
	}
	
	/**
	 * Factory method that creates a new case insensitive Regex object. 
	 * It takes a dictionary instead of the string and works as follows.
	 * 
	 * The translator dict is a dict\<String\> that contains pairs of the form:
	 * 
	 * (regular expressions string, replacement string)
	 * 
	 * When you call the {@link Regex#sub(str)} or the  {@link Regex#sub(String)}
	 * method of such a Regex object, the algorithms replaces each regular expressions string
	 * from the translator dict by the corresponding replacement string.
	 * 
	 * Example:
	 * dict\<String\> happyReplacer = x.dict(x.tuple("bad","good"),x.tuple("small","big"),x.tuple("hard","easy"));
	 *
	 * text = x.Regex(happyReplacer).sub(text); 
	 * 
	 * @param regularExpression	a string with a valid Java regular expression
	 * @return 					a Regex object 
	 */
	public static Regex RegexNoCase(dict<String> translator){
		return new Regex(translator,Regex.CASE_INSENSITIVE);
	}
	
	
	/**
	 * Factory method that creates a new Slicer object.
	 * 
	 * Slicer objects can be used as input for the slice method of any Slicable
	 * object (like {@link list} or {@link str})
	 *
	 * Example:
	 * 
	 * Slice LAST_THREE = x.sliceFrom(-3);
	 * 
	 * x.print(x.String("tic tac toe").slice(LAST_THREE));
	 * 
	 * Console: toe 
	 * 
	 * @param regularExpression	a string with a valid Java regular expression
	 * @return 					a Regex object 
	 */
	public Slicer slice(int startIndex,int endIndex){
		return slice(startIndex, endIndex, 1);
	}
	
	/**
	 * Factory method that creates a new Slicer object.
	 * 
	 * Slicer objects can be used as input for the slice method of any Slicable
	 * object (like {@link list} or {@link str})
	 *
	 * Example:
	 * 
	 * Slice LAST_THREE = x.sliceFrom(-3);
	 * 
	 * x.print(x.String("tic tac toe").slice(LAST_THREE));
	 * 
	 * Console: toe 
	 * 
	 * @param regularExpression	a string with a valid Java regular expression
	 * @return 					a Regex object 
	 */
	public Slicer slice(int startIndex,int endIndex, int step){
		return new Slicer(startIndex, endIndex, step, false);	
	}
	
	/**
	 * Factory method that creates a new Slicer object.
	 * 
	 * Slicer objects can be used as input for the slice method of any Slicable
	 * object (like {@link list} or {@link str})
	 *
	 * Example:
	 * 
	 * Slice LAST_THREE = x.sliceFrom(-3);
	 * 
	 * x.print(x.String("tic tac toe").slice(LAST_THREE));
	 * 
	 * Console: toe 
	 * 
	 * @param regularExpression	a string with a valid Java regular expression
	 * @return 					a Regex object 
	 */
	public Slicer slice(){
		return slice(1);
	}
	
	/**
	 * Factory method that creates a new Slicer object.
	 * 
	 * Slicer objects can be used as input for the slice method of any Slicable
	 * object (like {@link list} or {@link str})
	 *
	 * Example:
	 * 
	 * Slice LAST_THREE = x.sliceFrom(-3);
	 * 
	 * x.print(x.String("tic tac toe").slice(LAST_THREE));
	 * 
	 * Console: toe 
	 * 
	 * @param regularExpression	a string with a valid Java regular expression
	 * @return 					a Regex object 
	 */
	public Slicer slice(int step){
		if (step < 0){
			return new Slicer(Integer.MAX_VALUE,0,step,true);	
		}else{
			return new Slicer(0,Integer.MAX_VALUE,step,false);
		}	
	}
	
	/**
	 * Factory method that creates a new Slicer object.
	 * 
	 * Slicer objects can be used as input for the slice method of any Slicable
	 * object (like {@link list} or {@link str})
	 *
	 * Example:
	 * 
	 * Slice LAST_THREE = x.sliceFrom(-3);
	 * 
	 * x.print(x.String("tic tac toe").slice(LAST_THREE));
	 * 
	 * Console: toe 
	 * 
	 * @param regularExpression	a string with a valid Java regular expression
	 * @return 					a Regex object 
	 */
	public Slicer sliceTo(int endIndex, int step){
		int startIndex = 0;
		if (step < 0){
			startIndex = Integer.MAX_VALUE-1;
			return new Slicer(startIndex,endIndex,step,true);
		}
		return slice(startIndex,endIndex,step);
	}
	
	/**
	 * Factory method that creates a new Slicer object.
	 * 
	 * Slicer objects can be used as input for the slice method of any Slicable
	 * object (like {@link list} or {@link str})
	 *
	 * Example:
	 * 
	 * Slice LAST_THREE = x.sliceFrom(-3);
	 * 
	 * x.print(x.String("tic tac toe").slice(LAST_THREE));
	 * 
	 * Console: toe 
	 * 
	 * @param regularExpression	a string with a valid Java regular expression
	 * @return 					a Regex object 
	 */
	public Slicer sliceTo(int endIndex){
		return sliceTo(endIndex, 1);
	}
	
	/**
	 * Factory method that creates a new Slicer object.
	 * 
	 * Slicer objects can be used as input for the slice method of any Slicable
	 * object (like {@link list} or {@link str})
	 *
	 * Example:
	 * 
	 * Slice LAST_THREE = x.sliceFrom(-3);
	 * 
	 * x.print(x.String("tic tac toe").slice(LAST_THREE));
	 * 
	 * Console: toe 
	 * 
	 * @param regularExpression	a string with a valid Java regular expression
	 * @return 					a Regex object 
	 */
	public Slicer sliceFrom(int startIndex, int step){
		int endIndex = Integer.MAX_VALUE-1;
		if (step < 0){
			endIndex = 0;
		}
		return new Slicer(startIndex,endIndex,step,true);
	}
	
	/**
	 * Factory method that creates a new Slicer object.
	 * 
	 * Slicer objects can be used as input for the slice method of any Slicable
	 * object (like {@link list} or {@link str})
	 *
	 * Example:
	 * 
	 * Slice LAST_THREE = x.sliceFrom(-3);
	 * 
	 * x.print(x.String("tic tac toe").slice(LAST_THREE));
	 * 
	 * Console: toe 
	 * 
	 * @param regularExpression	a string with a valid Java regular expression
	 * @return 					a Regex object 
	 */
	public Slicer sliceFrom(int startIndex){
		return sliceFrom(startIndex,1);
	}
	
	/**
	 * A less verbose console print method. Preferrred over System.out.println.
	 * 
	 * x.print is also more versatile. It can take values of any type as input.
	 * 
	 * Example:
	 * 
	 * x.print("Hello World", 1, true, x.list(1, 2, 3), null);
	 * 
	 * Console: Hello World 1 true [1, 2, 3] NullType 
	 * 
	 * @param regularExpression	a string with a valid Java regular expression
	 * @return 					a Regex object 
	 */
	public static <T0> void print(T0 object){
		System.out.println(object==null?"NullType":object);
	}
	
	/**
	 * A less verbose console print method. Preferrred over System.out.println.
	 * 
	 * x.print is also more versatile. It can take values of any type as input.
	 * 
	 * Example:
	 * 
	 * x.print("Hello World", 1, true, x.list(1, 2, 3), null);
	 * 
	 * Console: Hello World 1 true [1, 2, 3] NullType 
	 * 
	 * @param regularExpression	a string with a valid Java regular expression
	 * @return 					a Regex object 
	 */
	public static <T0,T1> void print(T0 object0,T1 object1){
		System.out.println(""+(object0==null?"NullType":object0)+" "+(object1==null?"NullType":object1));
	}
	
	/**
	 * A less verbose console print method. Preferrred over System.out.println.
	 * 
	 * x.print is also more versatile. It can take values of any type as input.
	 * 
	 * Example:
	 * 
	 * x.print("Hello World", 1, true, x.list(1, 2, 3), null);
	 * 
	 * Console: Hello World 1 true [1, 2, 3] NullType 
	 * 
	 * @param regularExpression	a string with a valid Java regular expression
	 * @return 					a Regex object 
	 */
	public static <T0,T1,T2> void print(T0 object0,T1 object1,T2 object2){
		System.out.println(""+(object0==null?"NullType":object0)+" "+(object1==null?"NullType":object1)+" "+(object2==null?"NullType":object2));
	}
	
	/**
	 * A less verbose console print method. Preferrred over System.out.println.
	 * 
	 * x.print is also more versatile. It can take values of any type as input.
	 * 
	 * Example:
	 * 
	 * x.print("Hello World", 1, true, x.list(1, 2, 3), null);
	 * 
	 * Console: Hello World 1 true [1, 2, 3] NullType 
	 * 
	 * @param regularExpression	a string with a valid Java regular expression
	 * @return 					a Regex object 
	 */
	public static <T0,T1,T2,T3> void print(T0 object0,T1 object1,T2 object2,T3 object3){
		System.out.println(""+(object0==null?"NullType":object0)+" "+(object1==null?"NullType":object1)+" "+(object2==null?"NullType":object2)+" "+(object3==null?"NullType":object3));
	}
	
	/**
	 * A less verbose console print method. Preferrred over System.out.println.
	 * 
	 * x.print is also more versatile. It can take values of any type as input.
	 * 
	 * Example:
	 * 
	 * x.print("Hello World", 1, true, x.list(1, 2, 3), null);
	 * 
	 * Console: Hello World 1 true [1, 2, 3] NullType 
	 * 
	 * @param regularExpression	a string with a valid Java regular expression
	 * @return 					a Regex object 
	 */
	public static <T0,T1,T2,T3,T4> void print(T0 object0,T1 object1,T2 object2,T3 object3,T4 object4){
		System.out.println(""+(object0==null?"NullType":object0)+" "+(object1==null?"NullType":object1)+" "+(object2==null?"NullType":object2)+" "+(object3==null?"NullType":object3)+" "+(object4==null?"NullType":object4));
	}
	
	/**
	 * A less verbose console print method. Preferred over System.out.println.
	 * 
	 * x.print is also more versatile. It can take values of any type as input.
	 * 
	 * Example:
	 * 
	 * x.print("Hello World", 1, true, x.list(1, 2, 3), null);
	 * 
	 * Console: Hello World 1 true [1, 2, 3] NullType 
	 * 
	 * @param regularExpression	a string with a valid Java regular expression
	 * @return 					a Regex object 
	 */
	public static <T0,T1,T2,T3,T4,T5> void print(T0 object0,T1 object1,T2 object2,T3 object3,T4 object4,T5 object5){
		System.out.println(""+(object0==null?"NullType":object0)+" "+(object1==null?"NullType":object1)+" "+(object2==null?"NullType":object2)+" "+(object3==null?"NullType":object3)+" "+(object4==null?"NullType":object4)+" "+(object5==null?"NullType":object5));
	}
	
	/**
	 * A less verbose console print method. Preferrred over System.out.println.
	 * 
	 * x.print is also more versatile. It can take values of any type as input.
	 * 
	 * Example:
	 * 
	 * x.print("Hello World", 1, true, x.list(1, 2, 3), null);
	 * 
	 * Console: Hello World 1 true [1, 2, 3] NullType 
	 * 
	 * @param regularExpression	a string with a valid Java regular expression
	 * @return 					a Regex object 
	 */
	public static <T0,T1,T2,T3,T4,T5,T6> void print(T0 object0,T1 object1,T2 object2,T3 object3,T4 object4,T5 object5,T6 object6){
		System.out.println(""+(object0==null?"NullType":object0)+" "+(object1==null?"NullType":object1)+" "+(object2==null?"NullType":object2)+" "+(object3==null?"NullType":object3)+" "+(object4==null?"NullType":object4)+" "+(object5==null?"NullType":object5)+" "+(object6==null?"NullType":object6));
	}
	
	/**
	 * A less verbose console print method. Preferrred over System.out.println.
	 * 
	 * x.print is also more versatile. It can take values of any type as input.
	 * 
	 * Example:
	 * 
	 * x.print("Hello World", 1, true, x.list(1, 2, 3), null);
	 * 
	 * Console: Hello World 1 true [1, 2, 3] NullType 
	 * 
	 * @param regularExpression	a string with a valid Java regular expression
	 * @return 					a Regex object 
	 */
	public static <T0,T1,T2,T3,T4,T5,T6,T7> void print(T0 object0,T1 object1,T2 object2,T3 object3,T4 object4,T5 object5,T6 object6,T7 object7){
		System.out.println(""+(object0==null?"NullType":object0)+" "+(object1==null?"NullType":object1)+" "+(object2==null?"NullType":object2)+" "+(object3==null?"NullType":object3)+" "+(object4==null?"NullType":object4)+" "+(object5==null?"NullType":object5)+" "+(object6==null?"NullType":object6)+" "+(object7==null?"NullType":object7));
	}
	
	/**
	 * A less verbose console print method. Preferrred over System.out.println.
	 * 
	 * x.print is also more versatile. It can take values of any type as input.
	 * 
	 * Example:
	 * 
	 * x.print("Hello World", 1, true, x.list(1, 2, 3), null);
	 * 
	 * Console: Hello World 1 true [1, 2, 3] NullType 
	 * 
	 * @param regularExpression	a string with a valid Java regular expression
	 * @return 					a Regex object 
	 */
	public static <T0,T1,T2,T3,T4,T5,T6,T7,T8> void print(T0 object0,T1 object1,T2 object2,T3 object3,T4 object4,T5 object5,T6 object6,T7 object7,T8 object8){
		System.out.println(""+(object0==null?"NullType":object0)+" "+(object1==null?"NullType":object1)+" "+(object2==null?"NullType":object2)+" "+(object3==null?"NullType":object3)+" "+(object4==null?"NullType":object4)+" "+(object5==null?"NullType":object5)+" "+(object6==null?"NullType":object6)+" "+(object7==null?"NullType":object7)+" "+(object8==null?"NullType":object8));
	}
	
	
	/**
	 * This static method evaluates an input object as true or false.
	 * 
	 * An object is evaluates as true only in following cases:
	 * 
	 * 1) It's an {@link Boolean} that equals to <code>false</code>
	 * 2) It's an {@link Double} that equals to 0.0
	 * 3) It's an {@link Double} that equals to 0.0
	 * 4) It's an {@link Float} that equals to 0.0
	 * 5) It's an empty {@link String}
	 * 6) It's an empty {@link Iterable}
	 * 7) It's a {@link tuple0}
	 * 8) It's a <code>null</code>
	 * 9) It's a {@link Truthful} whose isTrue() method returns <code>false</code>
	 *  
	 * @param value
	 * @return
	 */
	public static boolean isTrue(Object value){
		try{
			if((Boolean)value == false){
				return false;
			}	
		}catch(Exception e){
			
		}
		if(value == null){
			return false;
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
			if((Integer)value == 0){
				return false;
			}	
		}catch(Exception e){
			
		}
		try{
			if((Double)value == 0.0){
				return false;
			}	
		}catch(Exception e){
			
		}
		try{
			if((Float)value == 0.0){
				return false;
			}	
		}catch(Exception e){
			
		}
		return true;
	}
	
	/**
	 * This static method evaluates an input object as false or true.
	 * 
	 * An object is evaluates as true only in following cases:
	 * 
	 * 1) It's an {@link Boolean} that equals to <code>false</code>
	 * 2) It's an {@link Double} that equals to 0.0
	 * 3) It's an {@link Double} that equals to 0.0
	 * 4) It's an {@link Float} that equals to 0.0
	 * 5) It's an empty {@link String}
	 * 6) It's an empty {@link Iterable}
	 * 7) It's a {@link tuple0}
	 * 8) It's a <code>null</code>
	 * 9) It's a {@link Truthful} whose isTrue() method returns <code>false</code>
	 *  
	 * @param value
	 * @return
	 */
	public static boolean isFalse(Object value){
		return !isTrue(value);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T memo(T object){
		return (T)(Memoizer.memoize(object));
	}
	
	/**
	 * A synonym to {@link x#String.escape}
	 */
	public static Function<Object, String> escape = x.String.escape;
	
	/**
	 * A synonym to {@link x#String.escape(String)}
	 */
	public static String escape(String string){
		return x.String.escape(string);
	}
	
	/**
	 * A synonym to {@link x#String.strip}
	 */
	public static Function<Object, String> strip = x.String.strip;
	
	/**
	 * A synonym to {@link x#String.trim}
	 */
	public static Function<Object, String> trim = x.String.strip;
	
	/**
	 * A synonym to {@link x#String.toLowerCase}
	 */
	public static Function<Object, String> toLowerCase = x.String.toLowerCase;
	
	/**
	 * A synonym to {@link x#String.lower}
	 */
	public static Function<Object, String> lower = x.String.toLowerCase;
	
	/**
	 * A synonym to {@link x#String.toUpperCase}
	 */
	public static Function<Object, String> toUpperCase = x.String.toUpperCase;
	
	/**
	 * A synonym to {@link x#String.upper}
	 */
	public static Function<Object, String> upper = x.String.toUpperCase;
	
	/**
	 * A {@link Function} that takes any object as input and returns its "length", e.g.
	 * the number of elements in case of Iterable or tuple. 
	 */
	public static Function<Object, Integer> len = new Function<Object, Integer>() {
		public Integer apply(Object value) {
			return len(value);
		}
	};
	
	/**
	 * A {@link Function} that takes any object as input and returns the same object.
	 */
	public static Function<Object, Object> doNothing = new Function<Object, Object>() {
		public Object apply(Object string) {
			return string;
		}
	};
	
	/**
	 * A {@link Function} that takes any object as input and returns the same object.
	 */
	public static <T> ParametrizedFunction<Object,T> constant(final T obj){
		return (new ParametrizedFunction<Object,T>() {
			public T apply(Object input) {
				return (T)obj;
			}
		}).params(x.listOf((Object)obj));
	}
	
	/**
	 * A {@link Function} that takes an object's method name and method's
	 * parameters values as input, invokes that method with those parameters
	 * and returns the output.
	 */
	public static <T> ParametrizedFunction<Object,T> invoke(final String methodName, Object... methodParams){
		return (new ParametrizedFunction<Object,T>() {
			public T apply(Object obj) throws IllegalArgumentException{
				String methodName = params.get(0).toString();
				list<Object> methodParams = params.sliceFrom(1);
				list<Class<?>> methodParamsTypes = x.list();
				for (tuple item : x.enumerate(methodParams)){
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
						return (T)(obj.getClass().getMethod(methodName, typesArr).invoke(obj, methodParams.toArrayList().toArray()));	
					}else{
						return (T)(obj.getClass().getMethod(methodName).invoke(obj));
					}
				}catch(Exception e){
					throw new IllegalArgumentException();
				}
			}
		}).params(x.listOf((Object)methodName).extend(methodParams));
	}
	
	/**
	 * A {@link Function} that takes a tuple of arguments an adds them up.
	 */
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
	
	/**
	 * A {@link Function} that takes a tuple of arguments an adds them up.
	 */
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
	
	/**
	 * A {@link Function} that takes a tuple of arguments an adds them up.
	 */
	public static <T extends Comparable<? super T>> Function<tuple2<T,T>, T> min(){
		return new Function<tuple2<T,T>,T>() {
			public T apply(tuple2<T,T> values) throws IllegalArgumentException{
				if(values.value0.compareTo(values.value1) < 0){
					return values.value0;
				}else{
					return values.value1;
				}
			}
		};
	}
	
	/**
	 * A {@link Function} that takes a tuple of arguments an adds them up.
	 */
	public static <T extends Comparable<? super T>> Function<tuple2<T,T>, T> max(){
		return new Function<tuple2<T,T>,T>() {
			public T apply(tuple2<T,T> values) throws IllegalArgumentException{
				if(values.value0.compareTo(values.value1) > 0){
					return values.value0;
				}else{
					return values.value1;
				}
			}
		};
	}
	
	/**
	 * A {@link Predicate} that is always false no matter the input. 
	 */
	public static Predicate<Object> FALSE = new Predicate<Object>() {
		public Boolean apply(Object string) {
			return false;
		}
	};
	
	/**
	 * A {@link Predicate} that is always true no matter the input. 
	 */
	public static Predicate<Object> TRUE = new Predicate<Object>() {
		public Boolean apply(Object string) {
			return true;
		}
	};
	
	/**
	 * A {@link Predicate} that only true if {@link x#len(Object)} returns 0 for the input value. 
	 */
	public static Predicate<Object> empty = new Predicate<Object>() {
		public Boolean apply(Object value) {
			return len(value) == 0;
		}
	};
	
	/**
	 * A synonym to {@link x#empty}. 
	 */
	public static Predicate<Object> isEmpty = empty;	
	
	/**
	 * A {@link Function} that retursn the value of the hash code of the input object. 
	 */
	public static Function<Object,Integer> getHashCode = new Function<Object,Integer>() {
		public Integer apply(Object value) {
			return value.hashCode();
		}
	};
}
