package com.wantedtech.common.xpresso.helpers;

import java.util.Set;

import com.wantedtech.common.xpresso.x;
import com.wantedtech.common.xpresso.types.set;
import com.wantedtech.common.xpresso.types.str;


public class HappyObject {
	
	Object lhs;
	
	public HappyObject(Object o){
		this.lhs = o;
	}
	
	public int hashCode(){
		return new HashCode(this.lhs, new String[0]).getHashCode();
	}
	
	public boolean equals(Object rhs){
		return Equals.reflectionEquals(this.lhs, rhs, new String[]{"hash"});
	}
	
	public int compareTo(Object rhs, String fieldName0, String... fieldNames){
		return new CompareTo(this.lhs, rhs, fieldName0, fieldNames).getComparison();
	}
	
	public boolean hasField(String fieldName){
		try {
			lhs.getClass().getField(fieldName);
			return true;
		} catch (Exception e0) {
			return false;
		}
	}
	
	public boolean hasMethod(String fieldName){
		try {
			lhs.getClass().getMethod(fieldName);
			return true;
		} catch (Exception e0) {
			return false;
		}
	}
	
	public boolean in(Iterable<?> iterable) {
		if(x.len(iterable) == 0) {
			return false;
		} else {
			Object me = lhs;
			Object sample = iterable.iterator().next();
			if (sample instanceof String) {
				if (me instanceof str)
					me = me.toString();
			}
			if (sample instanceof str) {
				if (me instanceof String)
					me = x.str(me.toString());
			}
			if (iterable instanceof set<?>) {
				return ((set<?>)(iterable)).contains(me);
			}
			if (iterable instanceof Set<?>) {
				return ((Set<?>)(iterable)).contains(me);
			}
			for (Object element : iterable) {
				if (element.equals(me)) {
					return true;
				}
			}
			return false;
		}
	}
	
	public boolean notIn(Iterable<?> iterable) {
		return !in(iterable);
	}
	
	  /**
	   * Returns a short class name for an object.
	   * This is the class name stripped of any package name.
	   *
	   * @return The name of the class minus a package name, for example
	   *         <code>ArrayList</code>
	   */
	  public String getShortClassName() {
	    if (lhs == null) {
	      return "null";
	    }
	    String name = lhs.getClass().getName();
	    int index = name.lastIndexOf('.');
	    if (index >= 0) {
	      name = name.substring(index + 1);
	    }
	    return name;
	  }
	
}
