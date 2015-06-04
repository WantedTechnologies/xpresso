// Generated from Lambda.g by ANTLR 4.5

package com.wantedtech.common.xpresso.lambda;

import java.util.*;
import com.wantedtech.common.xpresso.functional.Function;
import com.wantedtech.common.xpresso.x;

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
	 * Enter a parse tree produced by {@link LambdaParser#lambdaExpression}.
	 * @param ctx the parse tree
	 */
	void enterLambdaExpression(LambdaParser.LambdaExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link LambdaParser#lambdaExpression}.
	 * @param ctx the parse tree
	 */
	void exitLambdaExpression(LambdaParser.LambdaExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link LambdaParser#anyExpression}.
	 * @param ctx the parse tree
	 */
	void enterAnyExpression(LambdaParser.AnyExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link LambdaParser#anyExpression}.
	 * @param ctx the parse tree
	 */
	void exitAnyExpression(LambdaParser.AnyExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link LambdaParser#arithmeticExpressionE}.
	 * @param ctx the parse tree
	 */
	void enterArithmeticExpressionE(LambdaParser.ArithmeticExpressionEContext ctx);
	/**
	 * Exit a parse tree produced by {@link LambdaParser#arithmeticExpressionE}.
	 * @param ctx the parse tree
	 */
	void exitArithmeticExpressionE(LambdaParser.ArithmeticExpressionEContext ctx);
	/**
	 * Enter a parse tree produced by {@link LambdaParser#booleanExpressionB}.
	 * @param ctx the parse tree
	 */
	void enterBooleanExpressionB(LambdaParser.BooleanExpressionBContext ctx);
	/**
	 * Exit a parse tree produced by {@link LambdaParser#booleanExpressionB}.
	 * @param ctx the parse tree
	 */
	void exitBooleanExpressionB(LambdaParser.BooleanExpressionBContext ctx);
	/**
	 * Enter a parse tree produced by {@link LambdaParser#productExpressionT}.
	 * @param ctx the parse tree
	 */
	void enterProductExpressionT(LambdaParser.ProductExpressionTContext ctx);
	/**
	 * Exit a parse tree produced by {@link LambdaParser#productExpressionT}.
	 * @param ctx the parse tree
	 */
	void exitProductExpressionT(LambdaParser.ProductExpressionTContext ctx);
	/**
	 * Enter a parse tree produced by {@link LambdaParser#basicExpressionF}.
	 * @param ctx the parse tree
	 */
	void enterBasicExpressionF(LambdaParser.BasicExpressionFContext ctx);
	/**
	 * Exit a parse tree produced by {@link LambdaParser#basicExpressionF}.
	 * @param ctx the parse tree
	 */
	void exitBasicExpressionF(LambdaParser.BasicExpressionFContext ctx);
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
}