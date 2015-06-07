package com.wantedtech.common.xpresso.HappyObject;

import com.wantedtech.common.xpresso.helpers.Equals;
import com.wantedtech.common.xpresso.helpers.HashCode;

public class HappyObject {
	
	Object lhs;
	
	public HappyObject(Object o){
		this.lhs = o;
	}
	
	public int hashCode(){
		return new HashCode(this.lhs, new String[0]).getHashCode();
	}
	
	public boolean equals(Object rhs){
		return new Equals(this.lhs, rhs, new String[0]).getEquals();
	}
	
}
