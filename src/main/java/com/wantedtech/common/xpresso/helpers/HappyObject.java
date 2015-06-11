package com.wantedtech.common.xpresso.helpers;


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
	
}
