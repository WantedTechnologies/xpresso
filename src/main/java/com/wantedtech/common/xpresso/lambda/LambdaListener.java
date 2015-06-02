// Generated from Lambda.g by ANTLR 4.5

package com.wantedtech.common.xpresso.lambda;

import java.util.*;
import com.wantedtech.common.xpresso.functional.Function;
import com.wantedtech.common.xpresso.functional.Predicate;

import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link LambdaParser}.
 */
public interface LambdaListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link LambdaParser#eval}.
	 * @param ctx the parse tree
	 */
	void enterEval(LambdaParser.EvalContext ctx);
	/**
	 * Exit a parse tree produced by {@link LambdaParser#eval}.
	 * @param ctx the parse tree
	 */
	void exitEval(LambdaParser.EvalContext ctx);
	/**
	 * Enter a parse tree produced by {@link LambdaParser#lambdaExp}.
	 * @param ctx the parse tree
	 */
	void enterLambdaExp(LambdaParser.LambdaExpContext ctx);
	/**
	 * Exit a parse tree produced by {@link LambdaParser#lambdaExp}.
	 * @param ctx the parse tree
	 */
	void exitLambdaExp(LambdaParser.LambdaExpContext ctx);
	/**
	 * Enter a parse tree produced by {@link LambdaParser#anyExp}.
	 * @param ctx the parse tree
	 */
	void enterAnyExp(LambdaParser.AnyExpContext ctx);
	/**
	 * Exit a parse tree produced by {@link LambdaParser#anyExp}.
	 * @param ctx the parse tree
	 */
	void exitAnyExp(LambdaParser.AnyExpContext ctx);
	/**
	 * Enter a parse tree produced by {@link LambdaParser#booleanExp}.
	 * @param ctx the parse tree
	 */
	void enterBooleanExp(LambdaParser.BooleanExpContext ctx);
	/**
	 * Exit a parse tree produced by {@link LambdaParser#booleanExp}.
	 * @param ctx the parse tree
	 */
	void exitBooleanExp(LambdaParser.BooleanExpContext ctx);
	/**
	 * Enter a parse tree produced by {@link LambdaParser#arithmeticExp}.
	 * @param ctx the parse tree
	 */
	void enterArithmeticExp(LambdaParser.ArithmeticExpContext ctx);
	/**
	 * Exit a parse tree produced by {@link LambdaParser#arithmeticExp}.
	 * @param ctx the parse tree
	 */
	void exitArithmeticExp(LambdaParser.ArithmeticExpContext ctx);
	/**
	 * Enter a parse tree produced by {@link LambdaParser#multiplyExp}.
	 * @param ctx the parse tree
	 */
	void enterMultiplyExp(LambdaParser.MultiplyExpContext ctx);
	/**
	 * Exit a parse tree produced by {@link LambdaParser#multiplyExp}.
	 * @param ctx the parse tree
	 */
	void exitMultiplyExp(LambdaParser.MultiplyExpContext ctx);
	/**
	 * Enter a parse tree produced by {@link LambdaParser#atomMathExp}.
	 * @param ctx the parse tree
	 */
	void enterAtomMathExp(LambdaParser.AtomMathExpContext ctx);
	/**
	 * Exit a parse tree produced by {@link LambdaParser#atomMathExp}.
	 * @param ctx the parse tree
	 */
	void exitAtomMathExp(LambdaParser.AtomMathExpContext ctx);
	/**
	 * Enter a parse tree produced by {@link LambdaParser#complexIdentifier}.
	 * @param ctx the parse tree
	 */
	void enterComplexIdentifier(LambdaParser.ComplexIdentifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link LambdaParser#complexIdentifier}.
	 * @param ctx the parse tree
	 */
	void exitComplexIdentifier(LambdaParser.ComplexIdentifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link LambdaParser#function}.
	 * @param ctx the parse tree
	 */
	void enterFunction(LambdaParser.FunctionContext ctx);
	/**
	 * Exit a parse tree produced by {@link LambdaParser#function}.
	 * @param ctx the parse tree
	 */
	void exitFunction(LambdaParser.FunctionContext ctx);
	/**
	 * Enter a parse tree produced by {@link LambdaParser#predicate}.
	 * @param ctx the parse tree
	 */
	void enterPredicate(LambdaParser.PredicateContext ctx);
	/**
	 * Exit a parse tree produced by {@link LambdaParser#predicate}.
	 * @param ctx the parse tree
	 */
	void exitPredicate(LambdaParser.PredicateContext ctx);
}