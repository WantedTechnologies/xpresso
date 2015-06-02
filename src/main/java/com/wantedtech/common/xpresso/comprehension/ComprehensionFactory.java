package com.wantedtech.common.xpresso.comprehension;

public class ComprehensionFactory {
	public static <O> ScalarComprehensionStart<O> scalar(){
		ScalarComprehension<O> comprehension = new ScalarComprehension<O>();
		return new ScalarComprehensionStart<O>(comprehension);
	}
	public static Tuple1ComprehensionStart tuple(int index0){
		Tuple1Comprehension comprehension = new Tuple1Comprehension(index0);
		return new Tuple1ComprehensionStart(comprehension);
	}
	public static Tuple2ComprehensionStart tuple(int index0,int index1){
		Tuple2Comprehension comprehension = new Tuple2Comprehension(index0,index1);
		return new Tuple2ComprehensionStart(comprehension);
	}
}
