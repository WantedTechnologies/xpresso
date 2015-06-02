/*
 * $Id: JsonObject.java,v 1.1 2006/04/15 14:10:48 platform Exp $
 * Created on 2006-4-10
 */
package com.wantedtech.common.xpresso.json;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * A JSON object. Key value pairs are unordered. JsonObject supports java.util.Map interface.
 * 
 * @author FangYidong<fangyidong@yahoo.com.cn>
 */
public class JsonObject<K,V> extends HashMap<K,V> implements Map<K,V>{
	
	private static final long serialVersionUID = -503443796854799292L;
	
	
	public JsonObject() {
		super();
	}

	/**
	 * Allows creation of a JsonObject from a Map. After that, both the
	 * generated JsonObject and the Map can be modified independently.
	 * 
	 * @param map
	 */
	public JsonObject(Map<K,V> map) {
		super(map);
	}


    /**
     * Encode a map into JSON text
     * 
     * @see com.wantedtech.common.xpresso.json.JsonValue#dumps(Object)
     * 
     * @param map
     */
	public static String dumps(Map<?,?> map) {
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
			builder.append(JsonValue.dumps(entry.getValue()));
		}
		builder.append('}');
		return builder.toString();
	}
		
	/**
	 * Escape quotes, \, /, \r, \n, \b, \f, \t and other control characters (U+0000 through U+001F).
	 * It's the same as JsonScalar.escape() only for compatibility here.
	 * 
	 * @see org.JsonValue.simple.JsonScalar#escape(String)
	 * 
	 * @param s
	 * @return
	 */
	public static String escape(String s){
		return JsonValue.escape(s);
	}
}
