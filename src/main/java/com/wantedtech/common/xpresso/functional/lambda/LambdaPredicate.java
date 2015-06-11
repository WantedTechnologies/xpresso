package com.wantedtech.common.xpresso.functional.lambda;

import java.util.ArrayList;

import org.antlr.v4.runtime.*;

import com.wantedtech.common.xpresso.experimental.helpers.Helpers;
import com.wantedtech.common.xpresso.functional.Function;
import com.wantedtech.common.xpresso.functional.Predicate;
import com.wantedtech.common.xpresso.types.list;
import com.wantedtech.common.xpresso.types.tuple;

public class LambdaPredicate implements Predicate<Object>{

	String lambdaExpression;
	Function<Object,?>[] functions;
	
	@SafeVarargs
	public LambdaPredicate(String lambdaExpression,Function<Object,?>... functions){
		this.lambdaExpression = lambdaExpression;
		this.functions = functions;
	}
	
	public LambdaPredicate(String lambdaExpression){
		this.lambdaExpression = lambdaExpression;
	}
        
	@SuppressWarnings("unchecked")
	public Boolean apply(Object input) {
        // create a CharStream that reads from standard input
		ANTLRInputStream inputStream = new ANTLRInputStream(lambdaExpression);

        // create a lexer that feeds off of input CharStream
        Lambda2Lexer lexer = new Lambda2Lexer(inputStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        // create a parser that feeds off the tokens buffer
        Lambda2Parser parser = new Lambda2Parser(tokens);
		Object inputObj = input;
		if(input instanceof Iterable<?>){ 
			inputObj = Helpers.newArrayList((Iterable<?>)inputObj);
		}else if(input instanceof tuple){
			inputObj = new ArrayList<Object>();
			for (int i = 0;i<((tuple)input).size();i++){
				((ArrayList<Object>)inputObj).add(((tuple)input).get(i));
			}
		}
		parser.inputValue = new Value(inputObj);
		parser.inputFunction = this.functions;
		Object value = parser.eval().value.value;
		return (boolean)value;
	}	
}
