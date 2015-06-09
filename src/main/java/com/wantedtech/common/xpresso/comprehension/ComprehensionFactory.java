package com.wantedtech.common.xpresso.comprehension;

public class ComprehensionFactory {
	public static <O> ScalarComprehensionStart<O> scalar(){
		ScalarComprehension<O> comprehension = new ScalarComprehension<O>();
		return new ScalarComprehensionStart<O>(comprehension);
	}
	public static Tuple1ComprehensionStart tuple(String fieldName0){
		Tuple1Comprehension comprehension = new Tuple1Comprehension(fieldName0);
		return new Tuple1ComprehensionStart(comprehension);
	}
	public static Tuple2ComprehensionStart tuple(String fieldName0,String fieldName1){
		Tuple2Comprehension comprehension = new Tuple2Comprehension(fieldName0,fieldName1);
		return new Tuple2ComprehensionStart(comprehension);
	}
}
