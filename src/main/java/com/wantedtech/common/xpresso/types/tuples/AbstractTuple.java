package com.wantedtech.common.xpresso.types.tuples;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.wantedtech.common.xpresso.x;
import com.wantedtech.common.xpresso.types.Bag;
import com.wantedtech.common.xpresso.types.list;
import com.wantedtech.common.xpresso.types.set;
import com.wantedtech.common.xpresso.types.tuple;

public abstract class AbstractTuple implements tuple {

    public ArrayList<String> fieldNames = new ArrayList<String>();
	
    public abstract int size();
    
    public int len(){
    	return size();
    }
    
    public abstract <E> E get(int index, Class<E> elementClass);
    
    public int compareTo(tuple t){
    	return x.Object(this).compareTo(t, "value","value0","value1","value2","value3","value4","value5","value6","value7","value8","value9");
    }
    
	@Override
    public boolean equals(Object o) {
    	return x.Object(this).equals(o);
    }

    @Override
    public int hashCode() {
    	return x.Object(this).hashCode();
    }
    
    public boolean in(Iterable<tuple> iterable){
    	if(iterable instanceof Bag<?>){
    		return x.Object(this).in(((Bag<tuple>)iterable).tokens());
    	}else if(iterable instanceof list<?>){
    		return x.Object(this).in(((list<tuple>)iterable));
    	}else if(iterable instanceof set<?>){
    		return x.Object(this).in(((set<tuple>)iterable));
    	}else if(iterable instanceof List<?>){
    		return ((List<tuple>)iterable).contains(this);
    	}else if(iterable instanceof Set<?>){
    		return ((Set<tuple>)iterable).contains(this);
    	}else{
    		for(tuple element : iterable){
    			if(element.equals(this)){
    				return true;
    			}
    		}
			return false;
    	}
    }
    
	@Override
	public Object get(String fieldName) {
		if(fieldNames.contains(fieldName)){
			return get(fieldNames.indexOf(fieldName)); 
		}else{
			fieldNames.add(fieldName);
			return get(fieldNames.indexOf(fieldName));
		}
	}

	@Override
	public <E> E get(String fieldName, Class<E> elementClass) {
		if(fieldNames.contains(fieldName)){
			return elementClass.cast(get(fieldNames.indexOf(fieldName))); 
		}else{
			fieldNames.add(fieldName);
			return elementClass.cast(get(fieldNames.indexOf(fieldName)));
		}
	}
    
	@Override
	public tuple name(String... fieldNames) {
		for (String fieldName : fieldNames){
			this.fieldNames.add(fieldName);
		}
		return this;
	}
	
}
