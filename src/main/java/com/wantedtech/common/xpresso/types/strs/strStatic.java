package com.wantedtech.common.xpresso.types.strs;

import com.wantedtech.common.xpresso.x;
import com.wantedtech.common.xpresso.functional.Function;
import com.wantedtech.common.xpresso.functional.ParametrizedFunction;
import com.wantedtech.common.xpresso.strings.Unidecode;
import com.wantedtech.common.xpresso.types.str;

public class strStatic {
	
	private str stripAccents(str value){
	    return value.stripAccents();
	}
	public Function<Object, str> stripAccents = new Function<Object, str>() {
		public str apply(Object value) {
			return stripAccents((str)value);
		}
	};
	
	public str translit(str value){
        return x.str(x.String(value.toString()).translit());
	}
	public Function<Object, str> translit = new Function<Object, str>() {
		public str apply(Object value) {
			return translit(x.str(value.toString()));
		}
	};
	
	public str unidecode(str value){
        return x.str(x.String(value.toString()).translit());
	}
	public Function<Object, str> unidecode = new Function<Object, str>() {
		public str apply(Object value) {
			return unidecode(x.str(value.toString()));
		}
	};
	
	public ParametrizedFunction<Object,Integer> count(String substring){
		return (new ParametrizedFunction<Object,Integer>() {
			public Integer apply(Object string) {
				try{
					return x.String((String)string).count((String)this.params.get(0));	
				}catch(Exception e){
					throw new IllegalArgumentException("Could not interpret the input object as a String.");
				}
			}
		}).params(x.listOf((Object)substring));
	}
	
	public ParametrizedFunction<Object,Integer> count(str substr){
		return (new ParametrizedFunction<Object,Integer>() {
			public Integer apply(Object str) {
				try{
					return x.String((str)str).count(((str)this.params.get(0)).toString());	
				}catch(Exception e){
					throw new IllegalArgumentException("Could not interpret the input object as a str.");
				}
			}
		}).params(x.listOf((Object)substr));
	}
	
	public ParametrizedFunction<Object,Integer> count(char character){
		return (new ParametrizedFunction<Object,Integer>() {
			public Integer apply(Object string) {
				try{
					return x.String(((String)string)).count((char)this.params.get(0));	
				}catch(Exception e){
					throw new IllegalArgumentException("Could not interpret the input object as a char.");
				}
			}
		}).params(x.listOf((Object)character));
	}
	
	private str escape(str value){
		return x.str(x.String.escape(value.toString()));
	}
	public Function<Object, str> escape = new Function<Object, str>() {
		public str apply(Object value) {
			return escape((str)value);
		}
	};
	
	public Function<Object, str> toLowerCase = new Function<Object, str>() {
		public str apply(Object value) {
			String realString = value.toString();
			return x.str(realString.toLowerCase());
		}
	};
	public Function<Object, str> lower = toLowerCase;
	
	public Function<Object, str> toUpperCase = new Function<Object, str>() {
		public str apply(Object value) {
			String realString = value.toString();
			return x.str(realString.toUpperCase());
		}
	};
	public Function<Object, str> upper = toUpperCase;
}
