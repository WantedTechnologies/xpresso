package com.wantedtech.common.xpresso.json;

import java.util.Map;

import com.wantedtech.common.xpresso.types.dict;
import com.wantedtech.common.xpresso.types.tuple.tuple;

public class Json {
	public static String dump(Map<?,?> o){
		return JsonObject.dumps(o);
	}
	public static String dump(Iterable<?> o){
		return JsonArray.dumps(o);
	}
	public static String dump(dict<?> o){
		return JsonObject.dumps(o.toHashMap());
	}
	public static String dump(tuple o){
		return JsonArray.dumps(o);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T loads(String jsonString) throws ParseException{
		return (T)JsonValue.parse(jsonString);
	}
}
