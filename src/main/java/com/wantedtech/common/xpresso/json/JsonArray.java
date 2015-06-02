/*
 * $Id: JSONArray.java,v 1.1 2006/04/15 14:10:48 platform Exp $
 * Created on 2006-4-10
 */
package com.wantedtech.common.xpresso.json;

import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;

import com.wantedtech.common.xpresso.types.list;
import com.wantedtech.common.xpresso.types.tuple.tuple;

/**
 * A JSON array. JSONObject supports java.util.List interface.
 * 
 * @author FangYidong<fangyidong@yahoo.com.cn>
 */
public class JsonArray extends ArrayList{
	private static final long serialVersionUID = 3957988303675231981L;
	
	/**
	 * Constructs an empty JSONArray.
	 */
	public JsonArray(){
		super();
	}
	
	/**
	 * Constructs a JSONArray containing the elements of the specified
	 * collection, in the order they are returned by the collection's iterator.
	 * 
	 * @param c the collection whose elements are to be placed into this JSONArray
	 */
	public JsonArray(Iterable<?> c){
		super(list.newArrayList(c));
	}
	
    /**
     * Encode a list into JSON text and write it to out. 
     * If this list is also a JSONStreamAware or a JSONAware, JSONStreamAware and JSONAware specific behaviours will be ignored at this top level.
     * 
     * @see org.json.simple.JSONValue#writeJSONString(Object, Writer)
     * 
     * @param collection
     * @param out
     */
	public static <T> String dumps(Iterable<T> iterable){
		if(iterable == null){
			return ("null");
		}
		
		boolean first = true;
		Iterator<T> iter=iterable.iterator();
		
		StringBuilder builder = new StringBuilder();
		
		builder.append("[");
		while(iter.hasNext()){
            if(first)
                first = false;
            else
            	builder.append(",");
			Object value=iter.next();
			if(value == null){
				builder.append("null");
				continue;
			}
			
			builder.append(JsonValue.dumps(value));
		}
		builder.append("]");
		return builder.toString();
	}
	
	public static <T> String dumps(tuple tuple){
		if(tuple == null){
			return ("null");
		}
		
		StringBuilder builder = new StringBuilder();
		
		builder.append("[");
		
		try{
			builder.append(JsonValue.dumps(tuple.get(0)));
			Object anotherValue = tuple.get(1);
			builder.append(",");
			builder.append(anotherValue);
			anotherValue = tuple.get(2);
			builder.append(",");
			builder.append(anotherValue);
			anotherValue = tuple.get(3);
			builder.append(",");
			builder.append(anotherValue);
			anotherValue = tuple.get(4);
			builder.append(",");
			builder.append(anotherValue);
			anotherValue = tuple.get(5);
			builder.append(",");
			builder.append(anotherValue);
			anotherValue = tuple.get(6);
			builder.append(",");
			builder.append(anotherValue);
			anotherValue = tuple.get(7);
			builder.append(",");
			builder.append(anotherValue);
			anotherValue = tuple.get(8);
			builder.append(",");
			builder.append(anotherValue);
			anotherValue = tuple.get(9);
			builder.append(",");
			builder.append(anotherValue);
		}catch(Exception e0){
			e0.printStackTrace();
		}
		
		builder.append("]");
		return builder.toString();
	}

	public static String dumps(byte[] array){
		StringBuilder builder = new StringBuilder();
		if(array == null){
			builder.append("null");
		} else if(array.length == 0) {
			builder.append("[]");
		} else {
			builder.append("[");
			builder.append(String.valueOf(array[0]));
			
			for(int i = 1; i < array.length; i++){
				builder.append(",");
				builder.append(String.valueOf(array[i]));
			}
			
			builder.append("]");
		}
		return builder.toString();
	}
	
	public static String dumps(short[] array){
		StringBuilder builder = new StringBuilder();
		if(array == null){
			builder.append("null");
		} else if(array.length == 0) {
			builder.append("[]");
		} else {
			builder.append("[");
			builder.append(String.valueOf(array[0]));
			
			for(int i = 1; i < array.length; i++){
				builder.append(",");
				builder.append(String.valueOf(array[i]));
			}
			
			builder.append("]");
		}
		return builder.toString();
	}
	
	public static String dumps(int[] array){
		StringBuilder builder = new StringBuilder();
		if(array == null){
			builder.append("null");
		} else if(array.length == 0) {
			builder.append("[]");
		} else {
			builder.append("[");
			builder.append(String.valueOf(array[0]));
			
			for(int i = 1; i < array.length; i++){
				builder.append(",");
				builder.append(String.valueOf(array[i]));
			}
			
			builder.append("]");
		}
		return builder.toString();
	}
	
	public static String dumps(long[] array){
		StringBuilder builder = new StringBuilder();
		if(array == null){
			builder.append("null");
		} else if(array.length == 0) {
			builder.append("[]");
		} else {
			builder.append("[");
			builder.append(String.valueOf(array[0]));
			
			for(int i = 1; i < array.length; i++){
				builder.append(",");
				builder.append(String.valueOf(array[i]));
			}
			
			builder.append("]");
		}
		return builder.toString();
	}
	
	public static String dumps(float[] array){
		StringBuilder builder = new StringBuilder();
		if(array == null){
			builder.append("null");
		} else if(array.length == 0) {
			builder.append("[]");
		} else {
			builder.append("[");
			builder.append(String.valueOf(array[0]));
			
			for(int i = 1; i < array.length; i++){
				builder.append(",");
				builder.append(String.valueOf(array[i]));
			}
			
			builder.append("]");
		}
		return builder.toString();
	}
	
	public static String dumps(double[] array){
		StringBuilder builder = new StringBuilder();
		if(array == null){
			builder.append("null");
		} else if(array.length == 0) {
			builder.append("[]");
		} else {
			builder.append("[");
			builder.append(String.valueOf(array[0]));
			
			for(int i = 1; i < array.length; i++){
				builder.append(",");
				builder.append(String.valueOf(array[i]));
			}
			
			builder.append("]");
		}
		return builder.toString();
	}
	
	public static String dumps(boolean[] array){
		StringBuilder builder = new StringBuilder();
		if(array == null){
			builder.append("null");
		} else if(array.length == 0) {
			builder.append("[]");
		} else {
			builder.append("[");
			builder.append(String.valueOf(array[0]));
			
			for(int i = 1; i < array.length; i++){
				builder.append(",");
				builder.append(String.valueOf(array[i]));
			}
			
			builder.append("]");
		}
		return builder.toString();
	}
	
	public static String dumps(char[] array){
		StringBuilder builder = new StringBuilder();
		if(array == null){
			builder.append("null");
		} else if(array.length == 0) {
			builder.append("[]");
		} else {
			builder.append("[\"");
			builder.append(String.valueOf(array[0]));
			
			for(int i = 1; i < array.length; i++){
				builder.append("\",\"");
				builder.append(String.valueOf(array[i]));
			}
			
			builder.append("\"]");
		}
		return builder.toString();
	}

	
	public static String dumps(Object[] array){
		StringBuilder builder = new StringBuilder();
		if(array == null){
			builder.append("null");
		} else if(array.length == 0) {
			builder.append("[]");
		} else {
			builder.append("[");
			builder.append(JsonValue.dumps(array[0]));
			
			for(int i = 1; i < array.length; i++){
				builder.append(",");
				builder.append(JsonValue.dumps(array[i]));
			}
			
			builder.append("]");
		}
		return builder.toString();
	}
}
