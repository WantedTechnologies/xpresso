package com.wantedtech.common.xpresso.types.tuples;

import java.util.List;
import java.util.Set;

import com.wantedtech.common.xpresso.x;
import com.wantedtech.common.xpresso.types.Bag;
import com.wantedtech.common.xpresso.types.list;
import com.wantedtech.common.xpresso.types.set;
import com.wantedtech.common.xpresso.types.tuple;

public abstract class AbstractTuple implements tuple {

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
    		return ((Bag<tuple>)iterable).tokens().contains(this);
    	}else if(iterable instanceof list<?>){
    		return ((list<tuple>)iterable).contains(this);
    	}else if(iterable instanceof set<?>){
    		return ((set<tuple>)iterable).contains(this);
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
    
}
