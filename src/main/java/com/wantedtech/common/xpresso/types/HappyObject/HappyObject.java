package com.wantedtech.common.xpresso.types.HappyObject;

import com.wantedtech.common.xpresso.experimental.helpers.CompareTo;
import com.wantedtech.common.xpresso.experimental.helpers.Equals;
import com.wantedtech.common.xpresso.experimental.helpers.HashCode;

public class HappyObject {
	
	Object lhs;
	
	public HappyObject(Object o){
		this.lhs = o;
	}
	
	public int hashCode(){
		return new HashCode(this.lhs, new String[0]).getHashCode();
	}
	
	public boolean equals(Object rhs){
		return Equals.reflectionEquals(this.lhs, rhs, new String[0]);
	}
	
	public int compareTo(Object rhs, String firldName0, String... fieldNames){
		return new CompareTo(this.lhs, rhs, firldName0, fieldNames).getComparison();
	}
	
}
