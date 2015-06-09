package com.wantedtech.common.xpresso.json;

import java.util.Collection;
import java.util.Map;

import com.wantedtech.common.xpresso.types.dict;
import com.wantedtech.common.xpresso.types.tuple;
import com.wantedtech.common.xpresso.json.JsonValue;

public class Json {
	
	String jsonString;
	
	public Json(Map<?,?> o){
		jsonString = JsonObject.dumps(o);
	}
	
	public Json(Iterable<?> o){
		if (o instanceof dict<?>){
			jsonString = JsonObject.dumps(((dict<?>) o).toHashMap());
		}else{
			jsonString = JsonArray.dumps(o);	
		}
	}
	
	public Json(dict<?> o){
		jsonString = JsonObject.dumps(o.toHashMap());
	}
	
	public Json(tuple o){
		jsonString = JsonArray.dumps(o);
	}
	
	public Json(String jsonString){
		this.jsonString = jsonString;
	}
	
	public Json(Integer v){
		jsonString = JsonValue.dumps(v);
	}
	
	@Override
	public String toString(){
		return jsonString;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T parse() throws ParseException{
		return (T)JsonValue.parse(this.jsonString);
	}
	
	/*
	public static <T> T toXpresso(Object value){
		Object result;
		
			if(value == null){
				throw new NullPointerException("value has to be non null.");
			}
			
			if(value instanceof JsonValue){
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
				if(value instanceof dict<?>){
					builder.append(JsonObject.dumps(((dict<?>)value).toHashMap()));
				}else{
					builder.append(JsonArray.dumps((Iterable<?>)value));
				}
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
		
	}*/
	
}
