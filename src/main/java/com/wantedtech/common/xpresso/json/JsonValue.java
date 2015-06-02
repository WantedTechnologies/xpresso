/*
 * $Id: JSONValue.java,v 1.1 2006/04/15 14:37:04 platform Exp $
 * Created on 2006-4-15
 */
package com.wantedtech.common.xpresso.json;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.Collection;
import java.util.Map;

import com.wantedtech.common.xpresso.json.JsonParser;
import com.wantedtech.common.xpresso.json.ParseException;
import com.wantedtech.common.xpresso.json.JsonObject;


/**
 * @author FangYidong<fangyidong@yahoo.com.cn>
 */
public class JsonValue {	
	/**
	 * Parse JSON text into java object from the input source.
	 * 
	 * @see org.json.simple.parser.JsonParser
	 * 
	 * @param in
	 * @return Instance of the following:
	 * 	org.json.simple.JsonObject,
	 * 	org.json.simple.JsonArray,
	 * 	java.lang.String,
	 * 	java.lang.Number,
	 * 	java.lang.Boolean,
	 * 	null
	 * 
	 * @throws IOException
	 * @throws ParseException
	 */
	
	public static Object parse(String jsonString) throws ParseException{
		JsonParser parser=new JsonParser();
		return parser.parse(jsonString);
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
			builder.append(JsonObject.dumps((Map<?,?>)value));
			return builder.toString();
		}
		
		if(value instanceof Collection<?>){
			builder.append(JsonArray.dumps((Collection<?>)value));
            return builder.toString();
		}
		
		if(value instanceof Iterable<?>){
			builder.append(JsonArray.dumps((Iterable<?>)value));
            return builder.toString();
		}
		
		if(value instanceof byte[]){
			builder.append(JsonArray.dumps((byte[])value));
			return builder.toString();
		}
		
		if(value instanceof short[]){
			builder.append(JsonArray.dumps((short[])value));
			return builder.toString();
		}
		
		if(value instanceof int[]){
			builder.append(JsonArray.dumps((int[])value));
			return builder.toString();
		}
		
		if(value instanceof long[]){
			builder.append(JsonArray.dumps((long[])value));
			return builder.toString();
		}
		
		if(value instanceof float[]){
			builder.append(JsonArray.dumps((float[])value));
			return builder.toString();
		}
		
		if(value instanceof double[]){
			builder.append(JsonArray.dumps((double[])value));
			return builder.toString();
		}
		
		if(value instanceof boolean[]){
			builder.append(JsonArray.dumps((boolean[])value));
			return builder.toString();
		}
		
		if(value instanceof char[]){
			builder.append(JsonArray.dumps((char[])value));
			return builder.toString();
		}
		
		if(value instanceof Object[]){
			builder.append(JsonArray.dumps((Object[])value));
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
		}//for
	}

}
