package com.wantedtech.common.xpresso.json;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import org.antlr.v4.runtime.*;

import com.wantedtech.common.xpresso.types.dict;
import com.wantedtech.common.xpresso.types.tuple;

public class Json<O>{

	String jsonString;
	
	public Json(String jsonString){
			this.jsonString = jsonString;
	}
	
	public Json(String usualString, boolean isUsualString){
		this.jsonString = "\"" + usualString + "\"";
	}

	public Json(Map<?,?> o){
		jsonString = dumps(o);
	}
	
	public Json(Iterable<?> o){
		if (o instanceof dict<?>){
			jsonString = dumps(((dict<?>) o).toHashMap());
		}else{
			jsonString = dumps(o);	
		}
	}
	
	public Json(dict<?> o){
		jsonString = dumps(o.toHashMap());
	}
	
	public Json(tuple o){
		jsonString = dumps(o);
	}
	
	public Json(Integer v){
		jsonString = dumps(v);
	}
	
	@Override
	public String toString(){
		return jsonString;
	}
        
	@SuppressWarnings("unchecked")
	public static <O> O parse(String jsonString) {
        // create a CharStream that reads from standard input
		ANTLRInputStream inputStream = new ANTLRInputStream(jsonString);

        // create a lexer that feeds off of input CharStream
        JSONTreeLexer lexer = new JSONTreeLexer(inputStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        // create a parser that feeds off the tokens buffer
        JSONTreeParser parser = new JSONTreeParser(tokens);
		Object value = parser.value().result;
		return (O)value;
	}
	
    /**
     * Encode an object into JSON text and write it to out.
     * <p>
     * If this object is a Map or a List, and it's also a JSONStreamAware or a JSONAware, JSONStreamAware or JSONAware will be considered firstly.
     * <p>
     * DO NOT call this method from dumps(Writer) of a class that implements both JSONStreamAware and (Map or List) with 
     * "this" as the first parameter, use JsonObject.dumps(Map, Writer) or JsonArray.dumps(List, Writer) instead. 
     * 
     * @see org.json.simple.JsonObject#dumps(Map, Writer)
     * @see org.json.simple.JsonArray#dumps(List, Writer)
     * 
     * @param value
     * @param writer
     */
	public static String dumps(Object value){
		StringBuilder builder = new StringBuilder();
		if(value == null){
			builder.append("null");
			return builder.toString();
		}
		
		if(value instanceof String){
            builder.append('\"');
			builder.append(escape((String)value));
            builder.append('\"');
			return builder.toString();
		}
		
		if(value instanceof Double){
			if(((Double)value).isInfinite() || ((Double)value).isNaN())
				builder.append("null");
			else
				builder.append(value.toString());
			return builder.toString();
		}
		
		if(value instanceof Float){
			if(((Float)value).isInfinite() || ((Float)value).isNaN())
				builder.append("null");
			else
				builder.append(value.toString());
			return builder.toString();
		}		
		
		if(value instanceof Number){
			builder.append(value.toString());
			return builder.toString();
		}
		
		if(value instanceof Boolean){
			builder.append(value.toString());
			return builder.toString();
		}
						
		if(value instanceof Map<?,?>){
			builder.append(dumpsMap((Map<?,?>)value));
			return builder.toString();
		}
		
		if(value instanceof Collection<?>){
			builder.append(dumpsList((Collection<?>)value));
            return builder.toString();
		}
		
		if(value instanceof Iterable<?>){
			if(value instanceof dict<?>){
				builder.append(dumpsMap(((dict<?>)value).toHashMap()));
			}else{
				builder.append(dumpsList((Iterable<?>)value));
			}
            return builder.toString();
		}
		
		if(value instanceof byte[]){
			builder.append(dumpsList((byte[])value));
			return builder.toString();
		}
		
		if(value instanceof short[]){
			builder.append(dumpsList((short[])value));
			return builder.toString();
		}
		
		if(value instanceof int[]){
			builder.append(dumpsList((int[])value));
			return builder.toString();
		}
		
		if(value instanceof long[]){
			builder.append(dumpsList((long[])value));
			return builder.toString();
		}
		
		if(value instanceof float[]){
			builder.append(dumpsList((float[])value));
			return builder.toString();
		}
		
		if(value instanceof double[]){
			builder.append(dumpsList((double[])value));
			return builder.toString();
		}
		
		if(value instanceof boolean[]){
			builder.append(dumpsList((boolean[])value));
			return builder.toString();
		}
		
		if(value instanceof char[]){
			builder.append(dumpsList((char[])value));
			return builder.toString();
		}
		
		if(value instanceof Object[]){
			builder.append(dumpsList((Object[])value));
			return builder.toString();
		}
		
		builder.append(value.toString());
		return builder.toString();
	}
	
	/**
	 * Escape quotes, \, /, \r, \n, \b, \f, \t and other control characters (U+0000 through U+001F).
	 * @param s
	 * @return
	 */
	public static String escape(String s){
		if(s==null)
			return null;
        StringBuffer sb = new StringBuffer();
        escape(s, sb);
        return sb.toString();
    }
	
    /**
     * @param s - Must not be null.
     * @param sb
     */
    static void escape(String s, StringBuffer sb) {
    	final int len = s.length();
		for(int i=0;i<len;i++){
			char ch=s.charAt(i);
			switch(ch){
			case '"':
				sb.append("\\\"");
				break;
			case '\\':
				sb.append("\\\\");
				break;
			case '\b':
				sb.append("\\b");
				break;
			case '\f':
				sb.append("\\f");
				break;
			case '\n':
				sb.append("\\n");
				break;
			case '\r':
				sb.append("\\r");
				break;
			case '\t':
				sb.append("\\t");
				break;
			case '/':
				sb.append("\\/");
				break;
			default:
                //Reference: http://www.unicode.org/versions/Unicode5.1.0/
				if((ch>='\u0000' && ch<='\u001F') || (ch>='\u007F' && ch<='\u009F') || (ch>='\u2000' && ch<='\u20FF')){
					String ss=Integer.toHexString(ch);
					sb.append("\\u");
					for(int k=0;k<4-ss.length();k++){
						sb.append('0');
					}
					sb.append(ss.toUpperCase());
				}
				else{
					sb.append(ch);
				}
			}
		}
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
	public static <T> String dumpsList(Iterable<T> iterable){
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
			
			builder.append(dumps(value));
		}
		builder.append("]");
		return builder.toString();
	}
	
	public static <T> String dumpsList(tuple tuple){
		if(tuple == null){
			return ("null");
		}
		
		StringBuilder builder = new StringBuilder();
		
		builder.append("[");
		
		try{
			builder.append(dumps(tuple.get(0)));
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

	public static String dumpsList(byte[] array){
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
	
	public static String dumpsList(short[] array){
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
	
	public static String dumpsList(int[] array){
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
	
	public static String dumpsList(long[] array){
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
	
	public static String dumpsList(float[] array){
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
	
	public static String dumpsList(double[] array){
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
	
	public static String dumpsList(boolean[] array){
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
	
	public static String dumpsList(char[] array){
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

	
	public static String dumpsList(Object[] array){
		StringBuilder builder = new StringBuilder();
		if(array == null){
			builder.append("null");
		} else if(array.length == 0) {
			builder.append("[]");
		} else {
			builder.append("[");
			builder.append(dumps(array[0]));
			
			for(int i = 1; i < array.length; i++){
				builder.append(",");
				builder.append(dumps(array[i]));
			}
			
			builder.append("]");
		}
		return builder.toString();
	}
 
    /**
     * Encode a map into JSON text
     * 
     * @see com.wantedtech.common.xpresso.json.JsonValue#dumps(Object)
     * 
     * @param map
     */
	public static String dumpsMap(Map<?,?> map) {
		StringBuilder builder = new StringBuilder();
		if(map == null){
			builder.append("null");
			return builder.toString();
		}
		
		boolean first = true;
		Iterator<?> iter=map.entrySet().iterator();
		
        builder.append('{');
		while(iter.hasNext()){
            if(first)
                first = false;
            else
                builder.append(',');
			Map.Entry<?,?> entry=(Map.Entry<?,?>)iter.next();
            builder.append('\"');
            builder.append(escape(String.valueOf(entry.getKey())));
            builder.append('\"');
            builder.append(':');
			builder.append(dumps(entry.getValue()));
		}
		builder.append('}');
		return builder.toString();
	}
	
}
