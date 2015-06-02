package com.wantedtech.common.xpresso.types.tuple;

import java.util.List;
import java.util.Set;

import com.wantedtech.common.xpresso.types.Bag;
import com.wantedtech.common.xpresso.types.list;
import com.wantedtech.common.xpresso.types.set;

public abstract class AbstractTuple implements tuple {

    public abstract int size();
    
    public abstract <E> E get(int index, Class<E> elementClass);
    
    public int compareTo(tuple t){
    	return t.toString().compareTo(this.toString());
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
